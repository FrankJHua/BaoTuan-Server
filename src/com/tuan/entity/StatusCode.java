package com.tuan.entity;

public interface StatusCode {

	public static final int SUCCESS = 200;				//请求成功
	public static final int SERVER_ERROR = 500;			//服务器内部错误
	public static final int SERVER_MAINTENANCE = 503;	//服务器维护
	public static final int ERROR = 400;				//请求失败
	
}
