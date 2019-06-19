package com.jt.service;

import java.util.List;

import com.jt.pojo.Cart;

public interface DubboCartService {

	List<Cart> findCartListByUserId(Long userId);

	void updataCartNum(Cart cart);

	void delectByItemId(Cart cart);

	void insertCart(Cart cart);
}
