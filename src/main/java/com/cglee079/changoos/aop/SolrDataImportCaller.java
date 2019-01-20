package com.cglee079.changoos.aop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.cglee079.changoos.service.SolrService;

@Aspect
public class SolrDataImportCaller {

	@Autowired
	private SolrService solrService;

	@After("@annotation(com.cglee079.changoos.config.annotation.SolrData)")
	public void solrDoIndex(JoinPoint joinPoint) throws SolrServerException, IOException {
		solrService.dataimport();
	}
}