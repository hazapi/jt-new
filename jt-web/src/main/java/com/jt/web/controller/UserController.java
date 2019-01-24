package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	
	//实现页面通用跳转  /user/register.html
	@RequestMapping("/{moduleName}")
	public String module(@PathVariable String moduleName){
		
		return moduleName;
	}
	
	//前台实现用户注册
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户新增失败");
	}
	
	/**
	 *   实现用户登陆
	 *   删除cookie  cookie.setMaxAge(0);
	 * 会话关闭后删除  cookie.setMaxAge(-1)
	 * @param user
	 * @return
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult findUserByUP(User user,
			HttpServletResponse response) {
		try {
			//一.用户信息发送到后台做校验
			String token = 
					userService.findUserByUP(user);
			
			//二.获取token数据,保存到cookie中
			if(!StringUtils.isEmpty(token)) {
				
				Cookie cookie = new Cookie("JT_TICKET", token);
				cookie.setMaxAge(3600 * 24 * 7); //7天免密登录
				cookie.setPath("/");			 //表示当前网站根目录
				response.addCookie(cookie);      //将数据保存到Cookie中
				
				return SysResult.oK();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户登陆失败");
	}
	
	
	//jt-web 使用用户登出操作
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//1.获取cookie
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			
			if("JT_TICKET".equals(cookie.getName())) {
				
				token = cookie.getValue();
				break;
			}
		}
		//2.删除redis
		jedisCluster.del(token);
		
		//3.删除cookie
		Cookie cookie = new Cookie("JT_TICKET","");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/index.html";
	}
	
	
	
	
	
	
	
}	
