package com.cglee079.changoos.model;

public class ImageVo {
	private int seq;
	private int boardSeq;
	private String editorID;
	private String filename;
	private String pathname;
	private String status;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public int getBoardSeq() {
		return boardSeq;
	}

	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}

	public String getEditorID() {
		return editorID;
	}

	public void setEditorID(String editorID) {
		this.editorID = editorID;
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

}
