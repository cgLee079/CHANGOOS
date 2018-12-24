package com.cglee079.changoos.model;

public class BlogImageVo {
	private int seq;
	private int blogSeq;
	private String editorID;
	private String path;
	private String filename;
	private String pathname;
	private String status;

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

	public String getEditorID() {
		return editorID;
	}

	public void setEditorID(String editorID) {
		this.editorID = editorID;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "StudyImageVo [seq=" + seq + ", path=" + path + ", filename=" + filename + ", status=" + status + "]";
	}
	
	

}
