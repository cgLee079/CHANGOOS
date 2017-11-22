package com.cglee079.portfolio.model;

public class PhotoVo {
	private int seq;
	private String image;
	private String name;
	private String desc;
	private String location;
	private String date;
	private String people;
	private String tag;
	private int like;

	public PhotoVo() {
	}

	public PhotoVo(int seq, String image, String name, String desc, String location, String date, String people,
			String tag, int like) {
		this.seq = seq;
		this.image = image;
		this.name = name;
		this.desc = desc;
		this.location = location;
		this.date = date;
		this.people = people;
		this.tag = tag;
		this.like = like;
	}

	
	@Override
	public String toString() {
		return "PhotoVo [seq=" + seq + ", image=" + image + ", name=" + name + ", desc=" + desc + ", location="
				+ location + ", date=" + date + ", people=" + people + ", tag=" + tag + ", like=" + like + "]";
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

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

}
