package com.jt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.tools.classfile.Method;

@Controller
public class IndexController {
	
	//实现系统首页跳转
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(){
		
		return "index";
	}
}
