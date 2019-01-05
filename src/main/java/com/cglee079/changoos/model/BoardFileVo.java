package com.cglee079.changoos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardFileVo {
	private int seq;
	private int boardSeq;
	private String pathname;
	private String filename;
	private long size;
	private String status;

}
