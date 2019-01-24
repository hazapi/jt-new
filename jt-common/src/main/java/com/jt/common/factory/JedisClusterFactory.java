package com.jt.common.factory;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

//配置spring工厂模式
public class JedisClusterFactory implements FactoryBean<JedisCluster>{

	//通过set注入形式,动态获取redis.properties文件
	private Resource redisNodes;
	
	@Autowired
	private JedisPoolConfig poolConfig;
	
	@Override
	public JedisCluster getObject() throws Exception {
		//获取redis节点信息
		//Set<HostAndPort> nodes = getNodes();
		Set<HostAndPort> nodes = getNodeEntry();
		return new JedisCluster(nodes, poolConfig);
	}
	
	public Set<HostAndPort> getNodes(){
		Set<HostAndPort> nodes = new HashSet<>();
		try {
			Properties properties = new Properties();
			properties.load(redisNodes.getInputStream());
			
			//将pro文件中的数据遍历,获取rediscluster节点信息
			for (Object key : properties.keySet()) {
				String strKey = (String) key;
				if(strKey.startsWith("redis.cluster")){
					//192.168.126.174:7000
					String value = (String) properties.get(strKey);
					String[] args = value.split(":");
					String host = args[0];
					int port = Integer.parseInt(args[1]);
					nodes.add(new HostAndPort(host, port));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return nodes;
	}
	
	@Override
	public Class<?> getObjectType() {
		
		return JedisCluster.class;
	}

	@Override
	public boolean isSingleton() {
		
		return true;
	}

	public Resource getRedisNodes() {
		return redisNodes;
	}

	public void setRedisNodes(Resource redisNodes) {
		this.redisNodes = redisNodes;
	}	
	
	public Set<HostAndPort> getNodeEntry(){
		Set<HostAndPort> nodes = new HashSet<>();
		Properties properties = new Properties();
		try {
			properties.load(redisNodes.getInputStream());
			for (Entry<Object,Object> entry :properties.entrySet()) {
				
				String key = (String) entry.getKey();
				if(key.startsWith("redis.cluster")){
					//IP:端口
					String value = (String) entry.getValue();
					String[] args = value.split(":");
					nodes.add(new HostAndPort(args[0],Integer.parseInt(args[1])));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return nodes;
	}
}
