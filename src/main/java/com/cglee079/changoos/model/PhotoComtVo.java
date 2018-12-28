package com.cglee079.changoos.model;


public class PhotoComtVo {
	private int seq;
	private int photoSeq;
	private String username;
	private String password;
	private String contents;
	private String date;
	private Integer parentSeq;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getPhotoSeq() {
		return photoSeq;
	}

	public void setPhotoSeq(int photoSeq) {
		this.photoSeq = photoSeq;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getParentSeq() {
		return parentSeq;
	}

	public void setParentSeq(Integer parentSeq) {
		this.parentSeq = parentSeq;
	}

}