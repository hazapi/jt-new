package com.jt.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.order.pojo.OrderShipping;

public interface OrderShippingMapper extends BaseMapper<OrderShipping>{
	
    int deleteByPrimaryKey(String orderId);

    OrderShipping selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderShipping record);

    int updateByPrimaryKey(OrderShipping record);
}