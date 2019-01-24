package com.jt.cart.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Configuration  //标识当前类是一个配置类
@PropertySource
("classpath:/properties/redis.properties")
public class JedisClusterConfig {
	
	@Value("${redis.minIdle}")
	private int minIdle;
	@Value("${redis.maxIdle}")
	private int maxIdle;
	@Value("${redis.maxTotal}")
	private int maxTotal;
	@Value("${redis.clusterNodes}")
	private String clusterNodes;  //ip:端口,ip:port
	
	@Bean //表示将return返回的对象交给spring管理
	public JedisCluster getJedisCluster(){
		
		//1.定义poolConfig
		JedisPoolConfig poolConfig = 
				new JedisPoolConfig();
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxTotal(maxTotal);
		
		Set<HostAndPort> nodes = new HashSet<>();
		//将字符串根据","号进行分割  ip:port
		String[] jedisNodes = 
				clusterNodes.split(",");
		//遍历数组  ip:port
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
