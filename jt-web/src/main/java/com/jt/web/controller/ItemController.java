package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.web.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 根据商品的Id查询商品信息
	 * @param itemId
	 * @param model
	 * @return
	 * 用户通过前台直接查询后台的数据库,这样的效率较慢.
	 * 2.同时后台数据库访问压力很高.
	 * 3.所以可以redis缓存机制.快速返回数据
	 * 实现思路:
	 * 	用户查询时,首先查询缓存.如果缓存中没有数据则查询后台真实数据库.
	 */
	@RequestMapping("/{itemId}")
	public String findItemById(@PathVariable Long itemId,
			Model model){
		
		Item item = itemService.findItemCacheById(itemId);
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		//ItemDesc
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";  //跳转到商品展现页面
	}
}
