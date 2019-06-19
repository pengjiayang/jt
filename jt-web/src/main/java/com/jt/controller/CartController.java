package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Reference(timeout=3000,check=false)
	private DubboCartService ds;
	/**
	 * 实现商品的列表信息的展现
	 */
	@RequestMapping("/show")
	public String findCartList(Model model) {
		User user = UserThreadLocal.get();
		List<Cart>  cartlist = ds.findCartListByUserId(user.getId());
		model.addAttribute("cartList", cartlist);
		return "cart";
	}

	/**
	 * 实现购物车数量的修改
	 * 
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart) {
		User user = UserThreadLocal.get();
		try {
			cart.setUserId(user.getId());
			ds.updataCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("/delete/{itemId}")
	public String delectCart(Cart cart) {
		User user = UserThreadLocal.get();
		cart.setUserId(user.getId());
			ds.delectByItemId(cart);
		return "redirect:/cart/show.html";
	}

	/**
	 * 新增购物车
	 * 
	 */
	@RequestMapping("/add/{itemId}")
	public String insertCart(Cart cart) {
		User user = UserThreadLocal.get();
		cart.setUserId(user.getId());
		ds.insertCart(cart);
		return "redirect:/cart/show.html";
	}








































}
