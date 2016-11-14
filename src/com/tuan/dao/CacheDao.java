package com.tuan.dao;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import com.tuan.util.MemcachedUtil;

import net.spy.memcached.MemcachedClient;

public class CacheDao {

	private MemcachedClient mcc = null;
	
	public static final int EXP_HOUR = 3600;
	public static final int EXP_DAY = 3600*24;
	public static final int EXP_WEEK = 3600*24*7;
	
	public CacheDao(){
		
		Map<String,String> params = MemcachedUtil.getMemcachedConfig();
		String hostAddress = params.get("HOST");
		int port = Integer.parseInt(params.get("PORT"));
		try {
			mcc = new MemcachedClient(new InetSocketAddress(hostAddress, port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean set( String key, int exp, Object value ){
		if(null==mcc) return false;
		mcc.add(key, exp, value);
		return true;
	}
	
	public String get( String key ){
		if(null==mcc) return null;
		Object value = mcc.get(key);
		if(null==value){
			return null;
		}
		return value.toString();
	}
	
	public boolean delete( String key ){
		if(null==mcc) return false;
		mcc.delete(key);
		return true;
	}
	
	public void close(){
		if(null!=mcc){
			mcc.shutdown();
		}
	}
}
