package com.cglee079.changoos.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.MapSolrParams;
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
	
	public List<SearchResultVo> search(String value) throws SolrServerException, IOException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("q", "contents:" + value);
		param.put("hl", "on");
		param.put("hl.fragsize", "500");
		param.put("hl.fl", "contents");
		param.put("hl.simple.pre", "<sch-val>");
		param.put("hl.simple.post", "</sch-val>");
		
		QueryResponse response = solrSearchDao.search(CORE, param);
		
		List<SearchResultVo> results = response.getBeans(SearchResultVo.class);
		Map<String, Map<String, List<String>>> highlights = response.getHighlighting();
		
		SearchResultVo result = null;
		for(int i = 0; i < results.size(); i++) {
			result = results.get(i);
			result.setContents(result.getContents().replaceAll("(\r\n|\r|\n|\n\r)", " "));
			result.setHighlight(HTMLHandler.removeHTML(highlights.get(result.getId()).get("contents").get(0)));
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
