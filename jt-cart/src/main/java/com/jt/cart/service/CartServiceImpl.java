package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartListById(Long userId) {
		QueryWrapper<Cart> queryWrapper= new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}

	@Override
	@Transactional
	public void updateCartNum(Cart cart) {
		//补全数据
		Cart cartDB = new Cart();
		cartDB.setCreated(new Date());
		cartDB.setUpdated(cartDB.getCreated());
		cartDB.setNum(cartDB.getNum());
		
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
		
		updateWrapper.eq("user_id", cartDB.getUserId());
		updateWrapper.eq("item_id", cartDB.getItemId());
		
		cartMapper.update(cartDB, updateWrapper);
		
		
		
	}

	@Override
	@Transactional
	public void deleteCart(Cart cart) {
		
		QueryWrapper<Cart> queryWrapper=
				new QueryWrapper<>(cart);
		
		cartMapper.delete(queryWrapper);
		
	}

	@Override
	@Transactional
	public void saveCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper=
				new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", cart.getUserId())
		            .eq("item_id", cart.getItemId());
		
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		
		if (cartDB==null) {
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);  //cart
		}else {
			int num =
			cart.getNum() + cartDB.getNum();

			Cart cartLmp = new Cart();
			cartLmp.setNum(num);
			cartLmp.setUpdated(new Date());

			QueryWrapper<Cart> queryWrapper1=
					new QueryWrapper<Cart>();
			queryWrapper1.eq("id", cartDB.getId());
			cartMapper.update(cartLmp,queryWrapper1);
		}
		
		
	}
	
	
	
	
	
	
	
	
}
