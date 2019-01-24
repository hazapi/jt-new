package com.jt.test.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

public class TestRedis {
	
	//测试string类型操作
	@Test
	public void testString(){
		Jedis jedis =
				new Jedis("192.168.126.174",6379);
		jedis.set("1809","学习redis");
		String result = 
				jedis.get("1809");
		System.out.println("获取数据:"+result);
	}
	
	
	//测试hash值
	@Test
	public void testHash(){
		Jedis jedis = new Jedis("192.168.126.174",6379);
		jedis.hset("person","name","西门大官人");
		jedis.hset("person", "age", "25");
		Map<String,String> perMap = 
				jedis.hgetAll("person");
		System.out.println(perMap);
		//通过hash直接获取对象  通过工具方法 做转化
	}
	
	//在执行一遍 结果是2    3 4 1 2 3 4 1 2 3 4 
	@Test
	public void testList(){
		Jedis jedis = new Jedis("192.168.126.174",6379);
		jedis.lpush("list","1","2","3","4"); //左侧压栈
		String num = jedis.rpop("list");
		System.out.println(num);
	}
	
	//在执行一遍 结果是2    3 4 1 2 3 4 1 2 3 4 
	@Test
	public void testTx(){
		Jedis jedis = new Jedis("192.168.126.174",6379);
		Transaction transaction = jedis.multi();//开启事务
		transaction.set("aa", "aaa");
		transaction.set("bb", "bbb");
		transaction.set("cc", "ccc");
		//transaction.exec();	//事务提交
		transaction.discard();  //事务回滚
	}
	
	/**
	 * redis如何保存对象????
	 * 对象存储:
	 * 	对象~~~~~~~~对象的JSON  {属性:属性值....}
	 *  redis.set()
	 *  
	 *  /get()
	 *  对象的JSON~~~~~~对象    jsonLib  jackson
	 * @throws IOException 
	 */
	
	@Test
	public void toJSON() throws IOException{
		Jedis jedis = new Jedis("192.168.126.174",6379);
		User user = new User();
		user.setId(100);
		user.setName("json测试");
		user.setAge(100);
		user.setSex("男");
		ObjectMapper objectMapper = 
				new ObjectMapper();
		String userJSON = 
				objectMapper.writeValueAsString(user);
		System.out.println(userJSON);
		//将用户信息保存到redis中
		jedis.set("user", userJSON);
		String user_json = jedis.get("user");
		//将json串转化为对象
		User user2 = 
		objectMapper.readValue(user_json,User.class);
		System.out.println(user2);
	}
	
	//将List集合转化json
	@Test
	public void listToJSON() throws IOException{
		List<User> userList = new ArrayList<>();
		for(int i=0;i<4;i++){
			User user = new User();
			user.setId(i);
			user.setName("变形金刚"+i);
			user.setAge(10+i);
			user.setSex("男");
			userList.add(user);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String userListJSON = 
		objectMapper.writeValueAsString(userList);
		System.out.println(userListJSON);
		
		//将listJSON转化为集合
		User[] users = 
		objectMapper.readValue(userListJSON, User[].class);
		/*List<User> u_list =
		objectMapper.readValue(userListJSON,userList.getClass());*/
		System.out.println(Arrays.asList(users));
	}
	
	@Test
	public void getJSON() throws IOException{
		List<User> userList = new ArrayList<>();
		for(int i=0;i<4;i++){
			User user = new User();
			user.setId(i);
			user.setName("变形金刚"+i);
			user.setAge(10+i);
			user.setSex("男");
			userList.add(user);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String userListJSON = 
		objectMapper.writeValueAsString(userList);
		System.out.println(userListJSON);
		//读取json  获取json中下标为2的元素 
		JsonNode jsonNode = 
		objectMapper.readTree(userListJSON).get(2);
		System.out.println(jsonNode);
		//获取json属性值
		String name = jsonNode.get("name").asText();
		System.out.println("获取json串中的值:"+name);
	}
	
	
	//实现redis分片的操作 分片后的redis应该当做整体进行链接
	@Test
	public void testShards(){
		//定义池大小
		JedisPoolConfig poolConfig = 
				new JedisPoolConfig();
		poolConfig.setMaxTotal(2000); //定义最大连接数2000
		poolConfig.setMaxIdle(500); //最大空闲链接
		poolConfig.setMinIdle(100); //最小空闲链接
		
		//准备分片的List信息
		List<JedisShardInfo> shards = 
				new ArrayList<>();
		shards.add(new JedisShardInfo("192.168.126.174",6379));
		shards.add(new JedisShardInfo("192.168.126.174",6380));
		shards.add(new JedisShardInfo("192.168.126.174",6381));
		ShardedJedisPool pool = 
		new ShardedJedisPool(poolConfig, shards);
		ShardedJedis shardedJedis = 
				pool.getResource();
		shardedJedis.set("1809", "tomcat猫");
		System.out.println(shardedJedis.get("1809"));
		shardedJedis.close(); //关闭链接
		
	}
	
	@Test
	public void testSentinel(){
		//定义哨兵  IP:端口
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.126.174:26379");
		//定义链接池大小
		JedisPoolConfig poolConfig = 
				new JedisPoolConfig();
		poolConfig.setMaxTotal(2000); 
		JedisSentinelPool pool = 
		new JedisSentinelPool("mymaster", sentinels, poolConfig);
		
		Jedis jedis = pool.getResource();
		jedis.set("aa", "哨兵测试!!!");
		System.out.println("获取结果"+jedis.get("aa"));
	}
	
	//通过API操作redis集群
	@Test
	public void testCluster(){
		//1.定义redis配置文件 池大小
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(2000);
		Set<HostAndPort> nodes = new HashSet<>();
		System.out.println(new HostAndPort("192.168.126.174",7000));
		nodes.add(new HostAndPort("192.168.126.174",7000));
		nodes.add(new HostAndPort("192.168.126.174",7001));
		nodes.add(new HostAndPort("192.168.126.174",7002));
		nodes.add(new HostAndPort("192.168.126.174",7003));
		nodes.add(new HostAndPort("192.168.126.174",7004));
		nodes.add(new HostAndPort("192.168.126.174",7005));
		nodes.add(new HostAndPort("192.168.126.174",7006));
		nodes.add(new HostAndPort("192.168.126.174",7007));
		nodes.add(new HostAndPort("192.168.126.174",7008));
		
		JedisCluster cluster = 
				new JedisCluster(nodes, config);
		cluster.set("1809","redis集群搭建完成!!!");
		System.out.println(cluster.get("1809"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
