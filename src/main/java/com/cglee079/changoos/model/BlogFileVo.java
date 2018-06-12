package com.cglee079.changoos.model;

public class BlogFileVo {
	private int seq;
	private int blogSeq;
	private String pathNm;
	private String realNm;
	private long size;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getBlogSeq() {
		return blogSeq;
	}

	public void setBlogSeq(int blogSeq) {
		this.blogSeq = blogSeq;
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
