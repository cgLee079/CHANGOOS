package com.cglee079.portfolio.model;

public class ItemVo {
	private int seq;
	private String name;
	private String desc;
	private String content;
	private String snapsht;
	private String wrDate;
	private String dirtURL;
	private boolean dirt; 
	private int hits;

	public ItemVo() {
	}
	
	public ItemVo(int seq, String name, String desc, String content, String snapsht, String wrDate, String dirtURL,
			boolean dirt, int hits) {
		super();
		this.seq = seq;
		this.name = name;
		this.desc = desc;
		this.content = content;
		this.snapsht = snapsht;
		this.wrDate = wrDate;
		this.dirtURL = dirtURL;
		this.dirt = dirt;
		this.hits = hits;
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

	public String getWrDate() {
		return wrDate;
	}

	public void setWrDate(String wrDate) {
		this.wrDate = wrDate;
	}

	public String getDirtURL() {
		return dirtURL;
	}

	public void setDirtURL(String dirtURL) {
		this.dirtURL = dirtURL;
	}

	public boolean isDirt() {
		return dirt;
	}

	public void setDirt(boolean dirt) {
		this.dirt = dirt;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	
}