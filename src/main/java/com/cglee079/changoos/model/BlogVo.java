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
