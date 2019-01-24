package com.jt.manage.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.User;
import com.jt.manage.service.UserService;

@Controller								//当前对象所在的容器是谁
public class UserController{
	
	@Autowired
	private UserService userService;
	
	
	//查询用户全部信息,并且跳转到user.jsp页面中进行
	//数据展现.
	@RequestMapping("/findAll")
	public String findAll(Model model,HttpServletRequest request){
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		/*page/request/session/context 域对象*/
		
		return "userList";  //返回页面名称
	}

	/*@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println(applicationContext);
	}*/
}
