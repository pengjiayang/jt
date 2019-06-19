package com.jt;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedis2 {
	@Test
	public void testHash() {
	Jedis jedis = new Jedis("192.168.92.135",6379);
	jedis.hset("user1", "id", "200");
	jedis.hset("user2", "name", "tomcat服务器");
	String result = jedis.hget("user1", "id");
	System.out.println(result);
	System.out.println(jedis.hgetAll("user1"));
		
		
	}
	//编辑list集合
	@Test
	public void list() {
		Jedis jedis = new Jedis("192.168.92.135",6379);
		jedis.lpush("list", "1","2","3","4","5");
		System.out.println(jedis.rpop("list"));
	}

}
