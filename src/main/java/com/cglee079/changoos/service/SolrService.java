package com.cglee079.changoos.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.util.NamedList;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.SolrDao;
import com.cglee079.changoos.model.SolrSearchResultVo;
import com.cglee079.changoos.util.HTMLHandler;

@Service
public class SolrService {

	@Autowired
	private SolrDao solrDao;

	@Value("#{solr['core.id.changoos.board']}") private String core;
	@Value("#{solr['query.hl.max.pre.length']}")private int maxHlPreLength;
	@Value("#{solr['query.hl.frag.length']}")	private String hlfragLength;
	@Value("#{solr['query.hl.simple.pre']}")	private String hlSimplePre;
	@Value("#{solr['query.hl.simple.post']}")	private String hlSimplePost;

	public List<SolrSearchResultVo> search(String value) throws SolrServerException, IOException {
		String q = "(title:" + value + " OR " + "contents: " + value + ") AND enabled:true";
		Map<String, String> param = new HashMap<String, String>();

		param.put("q", ClientUtils.encodeLocalParamVal(q));
		param.put("hl", "on");
		param.put("hl.fragsize", hlfragLength);
		param.put("hl.fl", "title,contents");
		param.put("hl.simple.pre", hlSimplePre);
		param.put("hl.simple.post", hlSimplePost);

		QueryResponse response = solrDao.query(core, param);

		List<SolrSearchResultVo> results = response.getBeans(SolrSearchResultVo.class);
		Map<String, Map<String, List<String>>> hls = response.getHighlighting();

		SolrSearchResultVo result = null;
		String highlight = null;
		Map<String, List<String>> hl = null;
		int schIndex = -1;
		for (int i = 0; i < results.size(); i++) {
			result = results.get(i);
			result.setContents(HTMLHandler.extractHTMLText(result.getContents()));

			hl = hls.get(result.getId());

			if (hl.containsKey("title")) {
				result.setTitle(hl.get("title").get(0));
			}

			if (hl.containsKey("contents")) {
				//Solr에서 HTML코드가 제거됬지만, CodeSinppet 안에 HTML 코드는 제거되지 않는다. 별도로 제거.
				highlight = HTMLHandler.removeHTML(hl.get("contents").get(0)); 
				schIndex = highlight.indexOf(hlSimplePre);
				if (schIndex > maxHlPreLength) {
					highlight = highlight.substring(schIndex - maxHlPreLength, highlight.length());
				}

			} else {
				highlight = result.getContents();
			}

			result.setHighlight(highlight);
		}

		return results;

	}

	public JSONArray getFacets(String value) throws SolrServerException, IOException {
		Map<String, String> param = new HashMap<>();
		param.put("q", "");
		param.put("facet", "on");
		param.put("facet.field", "contents");
		param.put("facet.prefix", value.toLowerCase());

		QueryResponse response = solrDao.query(core, param);
		List<Count> counts = response.getFacetFields().get(0).getValues(); // CONTENTS

		List<String> facets = new ArrayList<String>();
		for (int i = 0; i < counts.size(); i++) {
			facets.add(counts.get(i).getName());
		}

		return new JSONArray(facets);
	}

	public void dataimport() throws SolrServerException, IOException {
		Map<String, String> param = new HashMap<String, String>();

		param.put("qt", "/dataimport");
		param.put("command", "full-import");
		param.put("clean", "true");
		param.put("commit", "true");
		
		solrDao.query(core, param);
	}

}
