package com.tuyano.springboot;

import java.util.Date;

import javax.persistence.Id;

public class MyDataMongo {

	@Id
	private String id;

	private String name;
	private String memo;
	private Date date;
	public MyDataMongo(String name, String memo) {
		super();
		this.name = name;
		this.memo = memo;
		this.date = new Date();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
