package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.manage.service.ItemService;

//@Controller+@ResponseBody
@RestController
@RequestMapping("/web/item")
public class WebItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/findItemById")
	public Item findItemById(Long itemId) {
		return  itemService.findItemById(itemId);
	}
	
	//根据itemDesc查询ItemDescduxiiang 
	@RequestMapping("/findItemDescById")
	public ItemDesc findItemDescById(Long itemId) {
		return  itemService.findItemDesc(itemId);
	}

}
