package com.cglee079.changoos.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectVo {
	private int seq;
	private String subtitle;
	private String title;
	private String desc;
	private String contents;
	private String thumbnail;
	private String sourcecode;
	private String developer;
	private int hits;
	private boolean enabled;
	private int comtCnt;
	private List<BoardImageVo> images;
	private List<BoardFileVo> files;
	
	
}