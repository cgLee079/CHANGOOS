package com.cglee079.portfolio.model;

public class PhotoVo {
	private int seq;
	private String image;
	private String snapsht;
	private String name;
	private String desc;
	private String location;
	private String date;
	private String tag;
	private String device;
	private int like;

	public PhotoVo() {
	}

	public PhotoVo(int seq, String image, String snapsht, String name, String desc, String location, String date,
			String tag, String device, int like) {
		this.seq = seq;
		this.image = image;
		this.snapsht = snapsht;
		this.name = name;
		this.desc = desc;
		this.location = location;
		this.date = date;
		this.tag = tag;
		this.device = device;
		this.like = like;
	}
	
	@Override
	public String toString() {
		return "PhotoVo [seq=" + seq + ", image=" + image + ", snapsht=" + snapsht + ", name=" + name + ", desc=" + desc
				+ ", location=" + location + ", date=" + date + ", tag=" + tag + ", device=" + device + ", like=" + like
				+ "]";
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSnapsht() {
		return snapsht;
	}

	public void setSnapsht(String snapsht) {
		this.snapsht = snapsht;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

}
