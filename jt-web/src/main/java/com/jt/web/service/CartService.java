package com.jt.web.service;

import java.util.List;

import com.jt.common.po.Cart;

public interface CartService {

	List<Cart> findCartListById(Long userId);

	void updateCartNum(Cart cart);

	void deleteCart(Long userId, Long itemId);

	void saveCart(Cart cart);

}
