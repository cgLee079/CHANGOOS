package com.cglee079.changoos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoVo {
	private int seq;
	private String filename;
	private String pathname;
	private String thumbnail;
	private String name;
	private String desc;
	private String date;
	private String time;
	private String location;
	private String tag;
	private String device;
	private int likeCnt;
	private boolean like;
	private boolean enabled;

}
