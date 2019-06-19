package com.jt.service;

import com.jt.pojo.Cart;

import java.util.List;

public interface DubboOrderService {
    List<Cart> findCartListByUserId(Long userId);
}
