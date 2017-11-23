package com.cglee079.portfolio.model;

public class ItemVo {
	private int seq;
	private String name;
	private String sect;
	private String desc;
	private String content;
	private String snapsht;
	private String date;
	private String gitURL;
	private boolean git;
	private String developer;
	private int hits;

	public ItemVo() {
	}

	public ItemVo(int seq, String name, String sect, String desc, String content, String snapsht, String date,
			String gitURL, boolean git, String developer, int hits) {
		this.seq = seq;
		this.name = name;
		this.sect = sect;
		this.desc = desc;
		this.content = content;
		this.snapsht = snapsht;
		this.date = date;
		this.gitURL = gitURL;
		this.git = git;
		this.developer = developer;
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

	public String getGitURL() {
		return gitURL;
	}

	public void setGitURL(String gitURL) {
		this.gitURL = gitURL;
	}

	public boolean isGit() {
		return git;
	}

	public void setGit(boolean git) {
		this.git = git;
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