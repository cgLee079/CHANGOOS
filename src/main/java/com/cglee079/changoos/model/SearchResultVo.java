package com.cglee079.changoos.model;

import org.apache.solr.client.solrj.beans.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResultVo {
	@Field("id") private String id;
	@Field("seq") private String seq;
	@Field("title") private String title;
	@Field("type") private String type;
	@Field("contents") private String contents;
	@Field("highlight") private String highlight;
	@Field("enabled") private boolean enabled;
}
