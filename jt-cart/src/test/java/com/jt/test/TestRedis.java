package com.jt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.JedisCluster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Test
	public void testRedis() {
		jedisCluster.set("1809", "redis集群整合成功!!!");
		System.out.println(jedisCluster.get("1809"));
	}
}
