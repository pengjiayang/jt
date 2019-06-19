package com.jt.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisClusterConfig {
	@Value("${redis.nodess}")
	private String redisNodes;
	@Bean
	public JedisCluster jedisCluster() {
		String[] str = redisNodes.split(",");
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		for (String node : str) {
			String host = node.split(":")[0];
			int port = Integer.parseInt(node.split(":")[1]);
			HostAndPort HP = new HostAndPort(host, port);
			nodes.add(HP);
		}
		return new JedisCluster(nodes);
	}
}



























