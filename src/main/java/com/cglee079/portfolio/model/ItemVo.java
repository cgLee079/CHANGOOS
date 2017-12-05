package com.cglee079.portfolio.model;

public class ItemVo {
	private int seq;
	private int sort;
	private String name;
	private String sect;
	private String desc;
	private String content;
	private String snapsht;
	private String date;
	private String sourcecode;
	private String developer;
	private int hits;

	public ItemVo() {
	}

	public ItemVo(int seq, int sort, String name, String sect, String desc, String content, String snapsht, String date,
			String sourcecode, String developer, int hits) {
		this.seq = seq;
		this.sort = sort;
		this.name = name;
		this.sect = sect;
		this.desc = desc;
		this.content = content;
		this.snapsht = snapsht;
		this.date = date;
		this.sourcecode = sourcecode;
		this.developer = developer;
		this.hits = hits;
	}

	@Override
	public String toString() {
		return "ItemVo [seq=" + seq + ", sort=" + sort + ", name=" + name + ", sect=" + sect + ", desc=" + desc
				+ ", content=" + content + ", snapsht=" + snapsht + ", date=" + date + ", sourcecode=" + sourcecode
				+ ", developer=" + developer + ", hits=" + hits + "]";
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSect() {
		return sect;
	}

	public void setSect(String sect) {
		this.sect = sect;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSnapsht() {
		return snapsht;
	}

	public void setSnapsht(String snapsht) {
		this.snapsht = snapsht;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSourcecode() {
		return sourcecode;
	}

	public void setSourcecode(String sourcecode) {
		this.sourcecode = sourcecode;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

}