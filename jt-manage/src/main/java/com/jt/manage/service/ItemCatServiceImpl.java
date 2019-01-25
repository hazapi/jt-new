package com.jt.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.ItemCat;
import com.jt.common.service.RedisService;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private JedisCluster jedisCluster;
	//private RedisService redisService;
	//private Jedis jedis;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/*if(itemCatTemp.getIsParent()){
	//是父级  closed
	easyUITree.setState("closed");
}else{
	//是子级  open
	easyUITree.setState("open");
}*/
	//根据parentId查询商品分类信息
	@Override
	public List<EasyUITree> findItemCatAll(long parentId) {
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		List<ItemCat> itemCatList = 
				itemCatMapper.select(itemCat);
		//需要将数据进行转换
		List<EasyUITree> treeList = new ArrayList<>();
		for (ItemCat itemCatTemp : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCatTemp.getId());
			easyUITree.setText(itemCatTemp.getName());
			String state = 
			itemCatTemp.getIsParent() ? "closed" : "open";
			easyUITree.setState(state);
			treeList.add(easyUITree);
		}
		
		return treeList;
	}

	/*
	 * 1.先查询缓存
	 * 2.判断redis中是否有数据
	 *   为null:查询数据库  将查询的结果存入缓存
	 */
	@SuppressWarnings("unchecked") //压制警告
	@Override
	public List<EasyUITree> findItemCatCache(Long parentId) {
		String key = "ITEM_CAT_"+ parentId;
		String listJSON = jedisCluster.get(key);
		List<EasyUITree> treeList = new ArrayList<>();
		try {
			if(StringUtils.isEmpty(listJSON)){
				//表示用户第一次查询
				treeList = findItemCatAll(parentId);
				//将对象转化为JSON串
				String json = 
				objectMapper.writeValueAsString(treeList);
				jedisCluster.set(key, json);
				//System.out.println("用户第一次查询!!!");
				
			}else{
				//表示redis中数据不为null,将json转化为对象
				treeList =
				objectMapper.readValue(listJSON,treeList.getClass());
				//System.out.println("用户查询缓存!!!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return treeList;
	}
	

	
	
	
	
	
	
	
	
	
}
