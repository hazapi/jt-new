package com.jt.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;
import com.jt.order.vo.SysResult;

import lombok.experimental.PackagePrivate;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * @RequestBody  将json数据直接转化为对象
	 * @ResponseBody 将对象转化为json
	 * 
	 * @param orderJSON
	 * @return
	 */
	@RequestMapping("/create")
	public SysResult saveOrder(String orderJSON) {
		try {
			Order order = objectMapper.readValue(orderJSON, Order.class);
			String orderId = 
					orderService.saveOrder(order);
			if(!StringUtils.isEmpty(orderId)) {
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"订单入库失败");
	}
	
	//根据orderId查询订单信息
	@RequestMapping("/query/{orderId}")
	public Order findOrderById(@PathVariable String orderId) {
		
		return orderService.findOrderById(orderId);
	}
	
	
	
	
	
	
	
	
	
}
