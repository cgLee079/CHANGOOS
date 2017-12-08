package com.cglee079.portfolio.model;

public class BoardVo {
	private int seq;
	private String type;
	private int sort;
	private String sect;
	private String title;
	private String contents;
	private String date;
	private int hits;

	public BoardVo() {
	}

	public BoardVo(int seq, String type, int sort, String sect, String title, String contents, String date, int hits) {
		this.seq = seq;
		this.type = type;
		this.sort = sort;
		this.sect = sect;
		this.title = title;
		this.contents = contents;
		this.date = date;
		this.hits = hits;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getSect() {
		return sect;
	}

	public void setSect(String sect) {
		this.sect = sect;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

}
