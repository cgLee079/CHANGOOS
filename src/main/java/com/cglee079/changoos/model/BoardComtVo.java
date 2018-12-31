package com.cglee079.changoos.model;

import lombok.Data;

@Data
public class BoardComtVo {
	private int seq;
	private int boardSeq;
	private String username;
	private String password;
	private String contents;
	private String date;
	private Integer parentComt;
}
