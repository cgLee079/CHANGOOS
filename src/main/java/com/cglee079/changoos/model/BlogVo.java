package com.cglee079.changoos.model;

import java.util.List;

import lombok.Data;

@Data
public class BlogVo {
	private int seq;
	private String thumbnail;
	private String title;
	private String contents;
	private String date;
	private String tag;
	private int hits;
	private int comtCnt;
	private boolean enabled;
	private List<BoardImageVo> images;
	private List<BoardFileVo> files;
	
}
