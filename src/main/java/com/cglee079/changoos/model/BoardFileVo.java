package com.cglee079.changoos.model;

import lombok.Data;

@Data
public class BoardFileVo {
	private int seq;
	private int boardSeq;
	private String pathname;
	private String filename;
	private long size;
	private String status;

}
