package com.cglee079.changoos.controller;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.service.SolrService;

@Controller
public class SolrController {
	
	@Autowired
	private SolrService solrSearchService;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String solrDoSearch(Model model, String value) throws SolrServerException, IOException {
		model.addAttribute("schValue", value);
		model.addAttribute("results", solrSearchService.search(value));

		return "search/search_result";
	}
	
	@ResponseBody
	@RequestMapping(value = "/search/facets", method = RequestMethod.GET)
	public String solrGetFacets(String value) throws SolrServerException, IOException {
		
		JSONArray facets = solrSearchService.getFacets(value);
		
		return facets.toString();
	}
}
