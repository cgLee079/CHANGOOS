package com.cglee079.changoos.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.params.MapSolrParams;
import org.jsoup.select.QueryParser;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.SearchResultVo;
import com.cglee079.changoos.util.HTMLHandler;

@Repository
public class SolrSearchDao {
	
	public static final String SOLR_URL = "http://localhost:8983/solr/";
	
	private SolrClient client;

	public SolrSearchDao() {
		client = new HttpSolrClient.Builder(SOLR_URL)
				.withConnectionTimeout(10000)
				.withSocketTimeout(60000)
				.build();
	}
	
	public QueryResponse search(String core, Map<String, String> param) throws SolrServerException, IOException {
		MapSolrParams queryParams = new MapSolrParams(param);
		QueryResponse response = client.query(core, queryParams);
		return response;
	}
	
	public QueryResponse getFacets(String core, Map<String, String> param) throws SolrServerException, IOException{
		MapSolrParams queryParams = new MapSolrParams(param);
		QueryResponse response = client.query(core, queryParams);
		return response;
	}
	
	public static void main(String[] args) throws SolrServerException, IOException {
		String value = "피카츄만있으면";
		Map<String, String> param = new HashMap<String, String>();
		param.put("q", "contents:" +  ClientUtils.escapeQueryChars(value));
		param.put("sort", "score desc");
		param.put("hl", "on");
		param.put("hl.fragsize", "1000");
		param.put("hl.fl", "contents");
		param.put("hl.simple.pre", "<sch-val>");
		param.put("hl.simple.post", "</sch-val>");
		
		QueryResponse response = new SolrSearchDao().search("changoos_board", param);
		
		List<SearchResultVo> results = response.getBeans(SearchResultVo.class);
		Map<String, Map<String, List<String>>> highlights = response.getHighlighting();
		
		SearchResultVo result = null;
		String highlight = null;
		int schIndex = -1;
		for(int i = 0; i < results.size(); i++) {
			result = results.get(i);
			result.setContents(HTMLHandler.extractHTMLText(result.getContents()));
			System.out.println("#########");
			System.out.println(result.getId());
			highlight = HTMLHandler.removeHTML(highlights.get(result.getId()).get("contents").get(0));
		
			System.out.println(highlight);
			System.out.println("#########");
			schIndex = highlight.indexOf("<sch-val>");		
			System.out.println( highlight.indexOf("<sch-val>"));
			if(schIndex > 100) {
				highlight = highlight.substring(schIndex - 100, highlight.length());
				System.out.println( highlight.indexOf("<sch-val>"));
			}
			
			
			System.out.println("#########");
		}
	}
}
