package com.jt.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.ContentCategory;
import com.jt.common.vo.SysResult;
import com.jt.manage.mapper.ContentCategoryMapper;
import com.jt.manage.vo.EasyUITree;
import com.mysql.fabric.xmlrpc.base.Data;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private ContentCategoryMapper contentCategoryMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Autowired
	private ObjectMapper ObjectMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EasyUITree> findContentCategory(Long parentId) {
		String key = "CONTENT_CATEGORY"+parentId;
		String sysJSON = jedisCluster.get(key);
		List<EasyUITree> treeList=new ArrayList<>();
		try {
		if(StringUtils.isEmpty(sysJSON)) {
			//用户第一次访问
			treeList = getContentCategory(parentId);
			//将对象转化成json串
					String json = 
						ObjectMapper.writeValueAsString(treeList);
					//保存到redis缓存中
					jedisCluster.set(key, json);
		}else {
			//数据库中有缓存,将缓存转化为对象
			treeList =
			ObjectMapper.readValue(sysJSON,treeList.getClass());
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return treeList;
	}


	public List<EasyUITree> getContentCategory(Long parentId){
		//查询id为parenId的对象将其封装到list集合里面
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setParentId(parentId);
		List<ContentCategory> ccList = contentCategoryMapper.select(contentCategory);
		System.out.println(ccList);
		//进行tree的数据转换
		List<EasyUITree> treeList=new ArrayList<>();
		for (ContentCategory contentCategoryTemp : ccList) {
			EasyUITree tree = new EasyUITree();
			Long id = contentCategoryTemp.getId();
			String name = contentCategoryTemp.getName();
			String state = 
					contentCategoryTemp.getIsParent() ? "closed" : "open";
			tree.setId(id);
			tree.setText(name);	
			tree.setState(state);
			treeList.add(tree);
		}
		return treeList;
	}


	@Override
	public void createContentCategory(Long parentId,String name) {
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		contentCategory.setName(name);
		contentCategory.setStatus(1);
		contentCategoryMapper.insert(contentCategory);
		
		//判断父类节点的isparent是否为turu,不为true则更改为true
		ContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}	
		jedisCluster.del("CONTENT_CATEGORY"+parentNode.getParentId());
	}


	@Override
	public void updateContentCategoryName(Long id,String name) {
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		jedisCluster.del("CONTENT_CATEGORY"+contentCategoryMapper.selectByPrimaryKey(id).getParentId());
	}


	@Override
	public SysResult deleteContentCategory(Long id) {
		//获取对象
		ContentCategory ccate = contentCategoryMapper.selectByPrimaryKey(id);
		jedisCluster.del("CONTENT_CATEGORY"+contentCategoryMapper.selectByPrimaryKey(id).getParentId());
		ContentCategory contentCategory=new ContentCategory();
		//判断是否为父级目录,是则判断是否含有子类,不是则删除
		if(!ccate.getIsParent()) {
			contentCategory.setIsParent(ccate.getIsParent());
			int rows = contentCategoryMapper.selectCount(contentCategory);
			contentCategoryMapper.deleteByPrimaryKey(id);
			if(rows == 1) {
				ContentCategory cc = new ContentCategory();
				cc.setUpdated(new Date());
				cc.setIsParent(false);
				contentCategoryMapper.updateByPrimaryKeySelective(cc);
			}
		}else {
			contentCategory.setParentId(id);
			int rows = contentCategoryMapper.selectCount(contentCategory);
			System.out.println(rows);
			if(rows==0) {
				ccate.setIsParent(false);
				contentCategoryMapper.deleteByPrimaryKey(id);
			}else {
				return SysResult.build(201, "请先删除子级目录");
			}
		}
		return SysResult.oK();
	}
}
