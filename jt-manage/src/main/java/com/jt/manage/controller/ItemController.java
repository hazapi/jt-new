package com.jt.manage.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//根据分页实现商品信息的查询
	@RequestMapping("/query")
	@ResponseBody		//转化为JSON串
	public EasyUIResult findItemListByPage(Integer page,Integer rows){
		
		return itemService.findItemListByPage(page,rows);
	}
	
	//根据商品分类Id查询商品分类名称
	@RequestMapping(value="/cat/queryItemName",produces="text/html;charset=utf-8")
	@ResponseBody
	public String findItemCatNameById(Long itemId){
		
		return itemService.findItemCatNameById(itemId);
	}

	/**
	 * Spring4版本需要注意乱码
	 * StringHttpMessageConverter
	 * 当返回String类型时 编码规则采用ISO-8859-1格式
	 * 
	 * AbstractJackson2HttpMessageConverter
	 * 当返回对象时,编码规则采用UTF-8格式
	 */
	
	//实现商品新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){
		try {
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			//异常处理机制  工具API
		}
		return SysResult.build(201,"商品新增失败");
	}
	
	
	//商品的下架
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long[] ids){
		try {
			int status = 2;	//表示下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201,"商品下架失败");
	}
	
	//商品的下架
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids){
		try {
			int status = 1;	//表示下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品下架失败");
	}
	
	
	//根据itemId查询商品详情信息
	//http://localhost:8091/item/query/item/desc/1474392089
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById
					(@PathVariable Long itemId){
		try {
			ItemDesc itemDesc = 
					itemService.findItemDesc(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品详情查询失败");
	}
	
	//商品信息的修改
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		try {
			itemService.updateItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品修改失败");
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItems(Long[] ids){
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品删除失败");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
