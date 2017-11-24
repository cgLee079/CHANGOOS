package com.cglee079.portfolio.model;

public class ItemVo {
	private int seq;
	private String name;
	private String sect;
	private String desc;
	private String content;
	private String snapsht;
	private String date;
	private String sourcecode;
	private String developer;
	private String tools;
	private int hits;

	public ItemVo() {
	}

	public ItemVo(int seq, String name, String sect, String desc, String content, String snapsht, String date,
			String sourcecode, String developer, String tools, int hits) {
		this.seq = seq;
		this.name = name;
		this.sect = sect;
		this.desc = desc;
		this.content = content;
		this.snapsht = snapsht;
		this.date = date;
		this.sourcecode = sourcecode;
		this.developer = developer;
		this.tools = tools;
		this.hits = hits;
	}
	
	@Override
	public String toString() {
		return "ItemVo [seq=" + seq + ", name=" + name + ", sect=" + sect + ", desc=" + desc + ", content=" + content
				+ ", snapsht=" + snapsht + ", date=" + date + ", sourcecode=" + sourcecode + ", developer=" + developer
				+ ", tools=" + tools + ", hits=" + hits + "]";
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

	public String getTools() {
		return tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

}