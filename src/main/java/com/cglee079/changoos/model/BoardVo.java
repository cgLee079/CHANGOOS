package com.cglee079.changoos.model;

public class BoardVo {
	private int seq;
	private int sort;
	private String sect;
	private String codeLang;
	private String title;
	private String contents;
	private String date;
	private int hits;
	private int comtCnt;

	@Override
	public String toString() {
		return "BoardVo [seq=" + seq + ", sort=" + sort + ", sect=" + sect + ", title=" + title + ", contents="
				+ contents + ", date=" + date + ", hits=" + hits + "]";
	}

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

	public int getComtCnt() {
		return comtCnt;
	}

	public void setComtCnt(int comtCnt) {
		this.comtCnt = comtCnt;
	}
}
