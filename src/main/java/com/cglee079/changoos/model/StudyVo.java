package com.cglee079.changoos.model;

import java.util.List;

import lombok.Data;

@Data
public class StudyVo {
	private int seq;
	private String category;
	private String codeLang;
	private String title;
	private String contents;
	private String date;
	private int hits;
	private boolean enabled;
	private int comtCnt;
	private List<BoardFileVo> files;
	private List<BoardImageVo> images;
}
