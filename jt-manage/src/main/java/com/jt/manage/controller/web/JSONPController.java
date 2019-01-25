package com.jt.manage.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.manage.pojo.User;

@RestController
public class JSONPController {
	
	@RequestMapping("/web/testJSONP")
	public JSONPObject find(String callback){
		User user = new User();
		user.setId(1809);
		user.setName("tomcat");
		
		return new JSONPObject(callback, user);
	}
}
