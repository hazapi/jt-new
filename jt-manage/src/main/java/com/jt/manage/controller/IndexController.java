package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	//跳转到京淘后台首页
	@RequestMapping("/index")
	public String index(){
		
		return "index";
	}
	
	/**
	 * RESTFul 参数接收的一种形式.
	 * 1.url中参数必须使用/进行分割   /page/item-add/name
	 * 2.requestMapper接收参数时,采用{参数名称} 进行接收
	 * 3.方法接收参数时 采用注解+参数名称形式获取数据
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String item_add(@PathVariable String moduleName){

		return moduleName;
	}

	
	
	
	
}
