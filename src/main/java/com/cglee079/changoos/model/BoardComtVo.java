package com.cglee079.changoos.model;

public class BoardComtVo {
	private int seq;
	private int boardSeq;
	private String username;
	private String password;
	private String contents;
	private String date;
	private Integer parentComt;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getBoardSeq() {
		return boardSeq;
	}

	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
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

	public Integer getParentComt() {
		return parentComt;
	}

	public void setParentComt(Integer parentComt) {
		this.parentComt = parentComt;
	}

}
