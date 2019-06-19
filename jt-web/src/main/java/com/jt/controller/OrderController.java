package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Reference(timeout = 3000,check = false)
    private DubboOrderService ds;

    /**
     * 跳转订单确认页面
     * url:
     */
    @RequestMapping("/create")
    public String orderCreate(Model model){
        Long userId = UserThreadLocal.get().getId();
        List<Cart> carts = ds.findCartListByUserId(userId);
        model.addAttribute("carts",carts);
        return "order-cart";
    }
}
