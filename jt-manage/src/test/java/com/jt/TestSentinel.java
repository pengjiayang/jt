package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinel {
	//测试哨兵的操作
	@Test
	public void test01() {
		//masterName主机的变量名称
		//sentinels Set<String> IP:端口
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.92.135:26379");
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster",sentinels);
		Jedis jedis = sentinelPool.getResource();
		jedis.set("cc", "端午节过后没假了!!!");
		System.out.println(jedis.get("cc"));
		jedis.close();
	}
}
