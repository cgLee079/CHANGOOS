package com.cglee079.changoos.model;

public class BlogFileVo {
	private int seq;
	private int blogSeq;
	private String path;
	private String pathname;
	private String filename;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
