package com.jt.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

public class UserInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Autowired
	private ObjectMapper objectMapper;
	/**
	 * 如何判断用户是否登录???
	 * 	1.通过request请求获取Cookie
	 *  2.获取Cookie中的ticket 
	 *  	if(null){用户没有登陆,跳转登陆页面}
	 *  	else{ 
	 *  		if(redis.get(ticket)){
	 *  			再次登录
	 *  		}else{
	 *  		直接跳转页面,放行
	 *  	}
	 *  	
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ticket = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {	
			if("JT_TICKET".equals(cookie.getName())) {
				ticket = cookie.getValue();
				break;
			}
		}
		if(!StringUtils.isEmpty(ticket)) {
			//判断redis中是否有数据
			String userJSON = jedisCluster.get(ticket);
			if(!StringUtils.isEmpty(userJSON)) {
				
				User user = 
				objectMapper.readValue(userJSON, User.class);
				//利用Session 一次请求中共享
				//request.getSession().setAttribute("JT_USER", user);
				UserThreadLocal.set(user);
				return true;	//拦截放行
			}
		}
		//重定向到用户登录页面 redirect:/user/login.html
		response.sendRedirect("/user/login.html");
		return false; //请求拦截 一般在false之前都会重定向
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//防止服务器内存泄漏
		//request.getSession().removeAttribute("JT_USER");
		
		UserThreadLocal.remove();
	}

}
