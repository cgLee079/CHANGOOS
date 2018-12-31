package com.cglee079.changoos.model;

import lombok.Data;

@Data
public class SessionLogVo {
	private int seq;
	private String ip;
	private String agnt;
	private String createDate;
}
