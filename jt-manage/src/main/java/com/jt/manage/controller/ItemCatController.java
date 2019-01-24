package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	//查询商品分类信息 
	/**
	 * 问题:	
	 * 	1.参数Id 其实代表parentId 定义不精确
	 *  2.当没有点击节点时,参数id并不会传值. 需要设定默认值0
	 *  @RequestParam(value="url参数名称",
	 *  		defaultValue="当参数没有传递时,添加默认值",
	 *  		required=true/false 改参数是否为必传  默认为false
	 * @param id
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITree> findItemCatAll(
			@RequestParam(value="id",defaultValue="0") Long parentId){
		//查询一级商品分类信息
		//long parentId = id == null ? 0L : id;
		return itemCatService.findItemCatCache(parentId);
	}
	
	
	
	
	
}
