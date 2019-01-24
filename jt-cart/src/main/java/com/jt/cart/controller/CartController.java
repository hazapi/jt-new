package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.cart.vo.SysResult;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ObjectMapper objectMapper;

	
	@RequestMapping("/say")
	public String hello() {
		
		return "hello";
	}
	
	@RequestMapping("/query/{userId}")
	public SysResult findCartList(@PathVariable Long userId) {
		try {
			List<Cart> cartList = cartService.findCartListById(userId);
			return SysResult.oK(cartList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"购物车查询失败");
	}
	
	
	public SysResult updateCartNum(Cart cart) {
		try {
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201, "购物车数量修改失败");
	}
	
	//删除
	@RequestMapping("/delete/{userId}/{itemId}")
	public SysResult deleteCart(Cart cart) {
		try {
			cartService.deleteCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201, "购物车删除失败");
	}
	
	//新增
    @RequestMapping("/save")
	public SysResult saveCart(String cartJSON) {
		try {
			Cart cart = objectMapper.readValue(cartJSON, Cart.class);
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201, "购物车新增失败");
	}
	
	
}
