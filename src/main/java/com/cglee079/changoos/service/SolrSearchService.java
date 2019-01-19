package com.cglee079.changoos.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.SolrSearchDao;
import com.cglee079.changoos.model.SearchResultVo;
import com.cglee079.changoos.util.HTMLHandler;

@Service
public class SolrSearchService{

	@Autowired
	private SolrSearchDao solrSearchDao;
	
	private static final String CORE = "changoos_board";
	private static final int MAX_HL_PRE_LENGTH = 100; 
	
	public List<SearchResultVo> search(String value) throws SolrServerException, IOException {
		String hlSimplePre = "<sch-val>";
		String hlSimplePost	= "</sch-val>";
		Map<String, String> param = new HashMap<String, String>();
		param.put("q", "contents:" +  ClientUtils.escapeQueryChars(value) + "AND enabled = true");
		param.put("hl", "on");
		param.put("hl.fragsize", "1000");
		param.put("hl.fl", "contents");
		param.put("hl.simple.pre", hlSimplePre);
		param.put("hl.simple.post", hlSimplePost);
		
		QueryResponse response = solrSearchDao.search(CORE, param);
		
		List<SearchResultVo> results = response.getBeans(SearchResultVo.class);
		Map<String, Map<String, List<String>>> highlights = response.getHighlighting();
		
		SearchResultVo result = null;
		String highlight = null;
		List<String> hlList = null;
		int schIndex = -1;
		for(int i = 0; i < results.size(); i++) {
			result = results.get(i);
			result.setContents(HTMLHandler.extractHTMLText(result.getContents()));
			
			hlList = highlights.get(result.getId()).get("contents");
			if(hlList == null) { //SOLR 버그? 똑같은 쿼리를 N번 호출 하면 M번에서 Highlight가 NULL인 상황이 발생.
				highlight = result.getContents();
			} else {
				highlight= HTMLHandler.removeHTML(hlList.get(0));
				schIndex = highlight.indexOf(hlSimplePre);		
				if(schIndex > MAX_HL_PRE_LENGTH) {
					highlight = highlight.substring(schIndex - MAX_HL_PRE_LENGTH, highlight.length());
				}
			}
			
			result.setHighlight(highlight);
		}
		
		return results;
		
	}
	
	public JSONArray getFacets(String value) throws SolrServerException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("q", "");
		map.put("facet", "on");
		map.put("facet.field", "contents");
		map.put("facet.prefix", value.toLowerCase());

		QueryResponse response = solrSearchDao.getFacets(CORE, map);
		List<Count> facets = response.getFacetFields().get(0).getValues();
		
		JSONArray result = new JSONArray();
		for(int i = 0; i < facets.size(); i++) {
			result.put(facets.get(i).getName());
		}
			
		return result;
		
	}
	
}
