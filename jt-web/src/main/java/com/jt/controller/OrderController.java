package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.service.DubboOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String orderCreate(){
        return "";
    }
}
