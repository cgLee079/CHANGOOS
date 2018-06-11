package com.cglee079.changoos.model;

public class ProjectFileVo {
	private int seq;
	private int projectSeq;
	private String pathNm;
	private String realNm;
	private long size;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getProjectSeq() {
		return projectSeq;
	}

	public void setProjectSeq(int projectSeq) {
		this.projectSeq = projectSeq;
	}

	public String getPathNm() {
		return pathNm;
	}

	public void setPathNm(String pathNm) {
		this.pathNm = pathNm;
	}

	public String getRealNm() {
		return realNm;
	}

	public void setRealNm(String realNm) {
		this.realNm = realNm;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
