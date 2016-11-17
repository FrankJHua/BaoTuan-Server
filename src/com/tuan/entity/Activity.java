package com.tuan.entity;

import java.util.Date;

public class Activity {

	private long   ID;					//活动ID
	private long   publisher;			//发布人ID
	private String name;				//活动名称
	private String province;			//省
	private String city;				//市
	private String district;			//区
	private String position;			//详细地址
	private Date   time;				//活动时间
	private float  fee;					//费用
	private int    number;				//活动人数
	private String description;			//活动描述
	private String type;				//活动类型
	private String publisher_avator_url;//获取活动发布者头像的url
	private String activity_cover_url;	//获取活动封面的url
	
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public long getPublisher() {
		return publisher;
	}
	public void setPublisher(long publisher) {
		this.publisher = publisher;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public float getFee() {
		return fee;
	}
	public void setFee(float fee) {
		this.fee = fee;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPublisher_avator_url() {
		return publisher_avator_url;
	}
	public void setPublisher_avator_url(String publisher_avator_url) {
		this.publisher_avator_url = publisher_avator_url;
	}
	public String getActivity_cover_url() {
		return activity_cover_url;
	}
	public void setActivity_cover_url(String activity_cover_url) {
		this.activity_cover_url = activity_cover_url;
	}
}
