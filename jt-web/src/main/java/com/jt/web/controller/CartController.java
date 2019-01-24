package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Cart;
import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//根据用户Id查询购物车信息
	@RequestMapping("/show")
	public String findCartListById(Model model,HttpServletRequest request) {
		//User user = 
				//(User) request.getSession().getAttribute("JT_USER");
		User user = UserThreadLocal.get();
		Long userId = user.getId(); 
		List<Cart> cartList = 
				cartService.findCartListById(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	//jt-web项目 controller
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart,HttpServletRequest request) {
		try {
			//User user = 
					//(User) request.getSession().getAttribute("JT_USER");
			User user = UserThreadLocal.get();
			Long userId = user.getId(); 
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品数量修改失败");
	}
	
	//实现购物车删除操作
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId,HttpServletRequest request){
		//User user = 
				//(User) request.getSession().getAttribute("JT_USER");
		User user = UserThreadLocal.get();
		Long userId = user.getId(); 
		cartService.deleteCart(userId,itemId);

		return "redirect:/cart/show.html"; //重定向到列表页面
	}
	
	//jt-web 购物车新增   cart封装购物车数据 itemTitle/price..
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart,HttpServletRequest request) {
		//User user = 
				//(User) request.getSession().getAttribute("JT_USER");
		User user = UserThreadLocal.get();
		Long userId = user.getId(); 
		cart.setUserId(userId);
		cartService.saveCart(cart);
		//跳转到购物车展现页面
		return "redirect:/cart/show.html";
		
	}
	
	
	
}
