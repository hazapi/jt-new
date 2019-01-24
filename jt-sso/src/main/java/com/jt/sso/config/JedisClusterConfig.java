package com.jt.sso.config;

import java.util.HashSet;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

//标识当前类是一个配置类
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class JedisClusterConfig {
	
	@Value("${redis.minIdle}")
	private int minIdle;
	@Value("${redis.maxIdle}")
	private int maxIdle;
	@Value("${redis.maxTotal}")
	private int maxTotal;
	@Value("${redis.clusterNodes}")
	private String clusterNodes;
	
	@Bean
	public JedisCluster getJedisCluster() {	
		//1.定义config
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxTotal(maxTotal);
		
		//
		String[] jedisNodes = clusterNodes.split(",");
		HashSet<HostAndPort> nodes = new HashSet<>();
		//遍历
		for (String jNode : jedisNodes) {
			String[] args = jNode.split(":");
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			nodes.add(new HostAndPort(host, port));			
			
		}
		
		return new JedisCluster(nodes, poolConfig);
		
	}
	
	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

}
