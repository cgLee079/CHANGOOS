package com.cglee079.changoos.model;

import com.cglee079.changoos.config.code.ImageStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardImageVo {
	private int seq;
	private int boardSeq;
	private String editorID;
	private String filename;
	private String pathname;
	private ImageStatus status;
}
