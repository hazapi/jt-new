package com.jt.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Cart;
import com.jt.common.po.User;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.thread.UserThreadLocal;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private HttpClientService httpClient;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public List<Cart> findCartListById(Long userId) {
		String url = "http://cart.jt.com/cart/query/"+userId;
		String sysJSON = httpClient.doGet(url);
		List<Cart> cartList = new ArrayList<>();
		try {
			SysResult sysResult = 
			objectMapper.readValue(sysJSON, SysResult.class);
			cartList = (List<Cart>) sysResult.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartList;
	}

	@Override
	public void updateCartNum(Cart cart) {
		String url = 
		"http://cart.jt.com/cart/update/num/"
		+cart.getUserId()
		+"/"+cart.getItemId()
		+"/"+cart.getNum();
		
		//将请求发给后台数据
		httpClient.doGet(url);
	}

	@Override
	public void deleteCart(Long userId, Long itemId) {
		String url = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		
		httpClient.doGet(url);
	}

	@Override
	public void saveCart(Cart cart) {
		String url = "http://cart.jt.com/cart/save";
		try {
			String cartJSON = 
					objectMapper.writeValueAsString(cart);
			Map<String,String> params = new HashMap<String, String>();
			params.put("cartJSON", cartJSON);
			httpClient.doPost(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
