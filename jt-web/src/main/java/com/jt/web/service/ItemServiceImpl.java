package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.service.HttpClientService;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemServiceImpl implements ItemService {
	
	//注入工具API
	@Autowired
	private HttpClientService httpClient;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public Item findItemById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemById";
		Map<String,String> params = new HashMap<>();
		params.put("itemId", itemId+"");
		//通过http请求获取远程服务器数据
		String resultJSON = 
				httpClient.doGet(url, params);
		Item item = null;
		try {
			//将json转化为对象 set方法为对象赋值
			item = objectMapper.readValue(resultJSON,Item.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return item;
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById";
		Map<String,String> params = new HashMap<>();
		params.put("itemId", itemId+"");
		//通过http请求获取远程服务器数据
		String resultJSON = 
				httpClient.doGet(url, params);
		ItemDesc itemDesc = null;
		try {
			//将json转化为对象 set方法为对象赋值
			itemDesc = objectMapper.readValue(resultJSON,ItemDesc.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return itemDesc;
	}
	
	/**
	 * 1.首先用户查询缓存
	 *   有: 直接返回数据  itemJSON 转化为对象
	 *   没有数据: 访问后台
	 */
	@Override
	//@Cache(key = "asdfasdf") //自定义注解
	public Item findItemCacheById(Long itemId) {
		String key = "ITEM_"+itemId;	//写法必须按照要求
		String itemJSON = jedisCluster.get(key);
		Item item = null;
		try {
			if(StringUtils.isEmpty(itemJSON)) {
				//表示缓存中没有数据
				item = findItemById(itemId);
				String itemjson = 
						objectMapper.writeValueAsString(item);
				jedisCluster.setex(key, 7 * 24 * 3600, itemjson);
				System.out.println("第一次查询!!!!");
			}else {
				
				item = objectMapper.readValue(itemJSON, Item.class);
				System.out.println("用户查询缓存!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return item;
	}
	
	
	
	
	
	
}
