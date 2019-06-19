package com.jt.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

//表示redis配置类
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class SentinelConfig {
//	@Value("${redis.nodes}")
//	private String redisNodes;
//	@Value("${jedis.host}") private String host;
//	@Value("${jedis.port}") private Integer port;
//	@Bean public Jedis jedis() { return new Jedis(host, port); }
//	@Bean
//	public ShardedJedis shardedJedis() {
//
//		List<JedisShardInfo> shards = new ArrayList<>();
//
//		String[] nodes = redisNodes.split(",");
//		for (String node : nodes) {
//			String host = node.split(":")[0];
//			int port = Integer.parseInt(node.split(":")[1]);
//			JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port);
//			shards.add(jedisShardInfo);
//		}
//		return new ShardedJedis(shards);
//	}
}
