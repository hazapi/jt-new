package com.jt.order.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	/**
	 * 1.入库order表
	 * 2.入库订单物流
	 * 3.入库订单商品
	 * //"inser into tb_order_item(...) values(第一个数据...),(第二个数据...),,,,"
	 */
	@Override
	@Transactional
	public String saveOrder(Order order) {
		String orderId = "" + order.getUserId() 
						+ System.currentTimeMillis();
		Date date = new Date(); 
		//入库订单信息
		order.setOrderId(orderId);
		order.setStatus(1); 	//设定订单状态
		order.setCreated(date);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单表入库成功!!!!");
		OrderShipping orderShipping = 
				order.getOrderShipping();
		orderShipping.setOrderId(orderId); //主键
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功!!!");
		List<OrderItem> list = order.getOrderItems();
		for (OrderItem orderItem : list) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单商品入库成功!!!!");
		
		return orderId;
	}
	
	//3张表一起查询
	@Override
	public Order findOrderById(String orderId) {
		OrderShipping orderShipping = 
				orderShippingMapper.selectById(orderId);
		QueryWrapper<OrderItem> queryWrapper = 
				new QueryWrapper<>();
		queryWrapper.eq("order_id", orderId);
		List<OrderItem> list = 
				orderItemMapper.selectList(queryWrapper);
		Order order = orderMapper.selectById(orderId);
		order.setOrderShipping(orderShipping);
		order.setOrderItems(list);
		return order;
	}
	
	/**
	 * 要求:如果订单提交超过30分钟,则将订单关闭
	 *判断条件:   created < agoDate
	 *
	 *sql:
	 *	update tb_order set status = 6,updated = 当前时间
	 *  where created < agoDate and status = 1
	 *	
	 */
	/*
	 * @Override public void updateOrderStatus() { Calendar calendar =
	 * Calendar.getInstance(); calendar.add(Calendar.MINUTE, -30); //先去30分钟 Date
	 * agoDate = calendar.getTime(); Order order = new Order(); order.setStatus(6);
	 * order.setUpdated(new Date());
	 * 
	 * UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>(); updateWrapper
	 * .eq("status", 1) .lt("created", agoDate); orderMapper.update(order,
	 * updateWrapper); }
	 */
	
	
		
}
