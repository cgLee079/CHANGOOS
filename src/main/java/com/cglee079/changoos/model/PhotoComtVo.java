package com.cglee079.changoos.model;

import lombok.Data;

@Data
public class PhotoComtVo {
	private int seq;
	private int photoSeq;
	private String username;
	private String password;
	private String contents;
	private String date;
	private Integer parentSeq;

}