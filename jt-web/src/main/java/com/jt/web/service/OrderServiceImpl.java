package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Order;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private HttpClientService httpClient;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public String saveOrder(Order order) {
		String url = "http://order.jt.com/order/create";
		try {
			String orderJSON = 
					objectMapper.writeValueAsString(order);
			Map<String,String> params = new HashMap<>();
			params.put("orderJSON", orderJSON);
			String sysJSON = 
			httpClient.doPost(url, params);
			SysResult sysResult = objectMapper
					.readValue(sysJSON, SysResult.class);
			if(sysResult.getStatus() == 200) {
				
				return (String) sysResult.getData();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Order findOrderById(String id) {
		String url = "http://order.jt.com/order/query/"+id;
		String orderJSON = httpClient.doGet(url);
		Order order = null;
		try {
			order = objectMapper.readValue(orderJSON, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}
	
	
	
	
	
	
	
}
