package com.cglee079.changoos.dao;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class SolrDao {
	
	@Value("#{solr['solr.url']}")				private String solrUrl;
	
	private SolrClient client;

	@PostConstruct
	public void init() {
		client = new HttpSolrClient.Builder(solrUrl)
				.withConnectionTimeout(10000)
				.withSocketTimeout(60000)
				.build();
	}
	
	public QueryResponse query(String core, Map<String, String> param) throws SolrServerException, IOException {
		MapSolrParams queryParams = new MapSolrParams(param);
		QueryResponse response = client.query(core, queryParams);
		return response;
	}
	
	
}
