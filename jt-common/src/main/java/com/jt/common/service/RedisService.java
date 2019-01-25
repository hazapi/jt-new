package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
	
	@Autowired(required=false)
	private JedisSentinelPool sentinelPool;
	
	public void set(String key,String value){
		Jedis jedis = sentinelPool.getResource();
		jedis.set(key, value);
		jedis.close();
	}
	
	public String get(String key){
		Jedis jedis = sentinelPool.getResource();
		String value = jedis.get(key);
		jedis.close();
		return value;
	}
	
	
	
	
	
	/**
	 * 利用工具API实现redis操作
	 * @Autowired(required=false)
	 * tomcat服务器启动时,不会立即注入对象.
	 * 当程序正常调用时,才会注入对象.
	 * 因为:有些程序 不一定会使用redis,所以可能没有
	 * 实例化该对象.
	 */
	/*@Autowired(required=false)
	private ShardedJedisPool shardedJedisPool;
	
	public void set(String key,String value){
		ShardedJedis jedis = 
				shardedJedisPool.getResource();
		jedis.set(key, value);
		jedis.close();	//还池
	}
	
	public void set(String key,String value,int seconds){
		ShardedJedis jedis = 
				shardedJedisPool.getResource();
		jedis.set(key, value);
		jedis.expire(key, seconds);
		//jedis.setex(key, seconds, value);
		jedis.close();
	}
	public String get(String key){
		ShardedJedis jedis = 
				shardedJedisPool.getResource();
		String value = jedis.get(key);
		jedis.close();
		return value;
	}*/
	
	
}
