package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 需要根据分页查询数据信息
	 * 1.查询记录总数
	 * 2.查询商品信息
	 * 	SELECT * FROM tb_item LIMIT 起始位置,查询条数
		SELECT * FROM tb_item LIMIT 0,20    #第一页
		SELECT * FROM tb_item LIMIT 20,20   #第二页
		SELECT * FROM tb_item LIMIT 40,20   #第三页
		SELECT * FROM tb_item LIMIT (page - 1) * ROWS,20   #第N页
	 */
	@Override
	public EasyUIResult findItemListByPage
				(Integer page, Integer rows) {
		//int count = itemMapper.findItemCount();
		
		//利用通用Mapper实现count求和
		int count = itemMapper.selectCount(null);
		
		//分页操作
		int start = (page - 1) * rows;	//定义起始位置
		List<Item> itemList = 
				itemMapper.findItemList(start,rows);
		
		return new EasyUIResult(count, itemList);
	}

	@Override
	public String findItemCatNameById(Long itemId) {
		
		return itemMapper.findItemCatById(itemId);
	}
	
	/**
	 * 数据库查询的原理
	 * INSERT INTO USER VALUES(NULL,"测试",100,"男");
	   SELECT LAST_INSERT_ID();
	 */
	@Override
	public void saveItem(Item item,String desc) {
		item.setStatus(1); 		//商品状态正常
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		//新增商品详情
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());	//获取item回传的Id
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void updateStatus(Long[] ids, int status) {
		
		itemMapper.updateStatus(ids,status);
		
	}

	@Override
	public ItemDesc findItemDesc(Long itemId) {
		
		return itemDescMapper.selectByPrimaryKey(itemId);
	}
	
	//如果遇到了存储过程的问题.一般不会直接更新缓存 直接删除缓存
	@Override
	public void updateItem(Item item, String desc) {
		//完善数据
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		
		//更新商品详情
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		//删除缓存数据
		jedisCluster.del("ITEM_"+item.getId());
		
		
	}

	//商品表和商品详情表一对一
	@Override
	public void deleteItems(Long[] ids) {
		
		itemMapper.deleteByIDS(ids);
		itemDescMapper.deleteByIDS(ids);
		
		//同步更新缓存
		for (Long id : ids) {
			jedisCluster.del("ITEM_"+id);
		}
		
	}

	@Override
	public Item findItemById(Long itemId) {
		
		return itemMapper.selectByPrimaryKey(itemId);
	}
	
	
}
