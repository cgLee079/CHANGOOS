package com.cglee079.changoos.model;

import lombok.Data;

@Data
public class BoardImageVo {
	private int seq;
	private int boardSeq;
	private String editorID;
	private String filename;
	private String pathname;
	private String status;
}
