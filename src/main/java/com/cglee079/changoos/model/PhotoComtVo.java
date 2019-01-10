package com.cglee079.changoos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoComtVo {
	private int seq;
	private int photoSeq;
	private String username;
	private String password;
	private String contents;
	private String date;
	private Integer parentSeq;
	private boolean enabled;

}