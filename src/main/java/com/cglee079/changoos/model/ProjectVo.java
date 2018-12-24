package com.cglee079.changoos.model;

import java.util.List;

public class ProjectVo {
	private int seq;
	private String subtitle;
	private String title;
	private String desc;
	private String contents;
	private String snapsht;
	private String sourcecode;
	private String developer;
	private int hits;
	private int comtCnt;
	private List<ProjectImageVo> images;
	private List<ProjectFileVo> files;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSnapsht() {
		return snapsht;
	}

	public void setSnapsht(String snapsht) {
		this.snapsht = snapsht;
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

	public int getComtCnt() {
		return comtCnt;
	}

	public void setComtCnt(int comtCnt) {
		this.comtCnt = comtCnt;
	}

	public List<ProjectImageVo> getImages() {
		return images;
	}

	public void setImages(List<ProjectImageVo> images) {
		this.images = images;
	}

	public List<ProjectFileVo> getFiles() {
		return files;
	}

	public void setFiles(List<ProjectFileVo> files) {
		this.files = files;
	}
}