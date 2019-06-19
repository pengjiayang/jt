package com.jt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestRedisShards {
	
	/***
	 * 操作时将多台的redis当做一台
	 */
	@Test
	public void testShards() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo sedisShardInfo1 = new JedisShardInfo("192.168.92.135",6379);
		JedisShardInfo sedisShardInfo2 = new JedisShardInfo("192.168.92.135",6380);
		JedisShardInfo sedisShardInfo3 = new JedisShardInfo("192.168.92.135",6381);
		shards.add(sedisShardInfo1);
		shards.add(sedisShardInfo2);
		shards.add(sedisShardInfo3);
		//操作分片redis对象工具类
		ShardedJedis shardedJedis = new ShardedJedis(shards);
		shardedJedis.set("1902","1902班");
		System.out.println(shardedJedis.get("1902"));
		shardedJedis.hset("id", "name", "1920");
		System.out.println(shardedJedis.hgetAll("id"));
	}
}
