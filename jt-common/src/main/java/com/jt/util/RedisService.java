package com.jt.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {
	
//	@Autowired(required=false)//表示调用时才注入
//	private JedisSentinelPool jedisSentinelPool;
//	
//	//封装方法 get
//	public String get(String key) {
//		Jedis jedis =jedisSentinelPool.getResource();
//		String result = jedis.get(key);
//		jedis.close();
//		return result;
//	}
//	//封装方法set方法
//	public void set(String key,String value) {
//		Jedis jedis =jedisSentinelPool.getResource();
//		jedis.set(key, value);
//		jedis.close();
//	}
//	//封装方法setex
//	public void setex(String key,int seconds,String value) {
//		Jedis jedis =jedisSentinelPool.getResource();
//		jedis.setex(key, seconds, value);
//		jedis.close();
//	}
}


























