package com.tuan.entity;

public class Result {

	private int status;
	private String msg;
	
	public Result(){}
	
	public Result( int status, String message ){
		this.status = status;
		this.msg = message;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
