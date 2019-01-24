package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;
import com.jt.sso.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	//完成用户校验
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(@PathVariable String param,
			@PathVariable Integer type,
			String callback) {
		boolean flag =
		userService.findCheckUser(param,type);
		
		return new JSONPObject(callback, SysResult.oK(flag));
	}
	
	//实现用户新增
	@RequestMapping("/register")
	public SysResult saveUser(User user) {
		
		try {
			userService.saveUser(user);
			System.out.println("成功sso");
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("失败，sso");
		return SysResult.build(201, "用户新增失败");
	}
	

	//jt-sso
	@RequestMapping("/login")
	public SysResult findUserByUP(User user) {
		try {
			String token = 
					userService.findUserByUP(user);
			//System.out.println("7:"+token);
			if(!StringUtils.isEmpty(token)) {
				//System.out.println("2");
				return SysResult.oK(token);
				
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("3");
		return SysResult.build(201, "用户登录失败");
	}
	
	//SSO,利用ticket查询用户信息
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,
			String callback) {
		String userJSON = jedisCluster.get(ticket);
		return new JSONPObject(callback, SysResult.oK(userJSON));
	}

}
