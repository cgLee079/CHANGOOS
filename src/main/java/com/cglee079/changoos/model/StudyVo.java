package com.cglee079.changoos.model;

public class StudyVo {
	private int seq;
	private String category;
	private String codeLang;
	private String title;
	private String contents;
	private String date;
	private int hits;
	private int comtCnt;

	public String getCodeLang() {
		return codeLang;
	}

	public void setCodeLang(String codeLang) {
		this.codeLang = codeLang;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public int getComtCnt() {
		return comtCnt;
	}

	public void setComtCnt(int comtCnt) {
		this.comtCnt = comtCnt;
	}
}
