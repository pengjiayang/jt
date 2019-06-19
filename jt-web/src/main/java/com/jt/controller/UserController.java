package com.jt.controller;

import java.util.Enumeration;

import javax.servlet.AsyncContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.service.UserServeice;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller()
@RequestMapping("/")
public class UserController {
	//导入bubbo用户接口
	@Reference(timeout=3000,check=false)
	private DubboUserService userService;
	@Autowired
	private JedisCluster jc;
//	@Autowired
//	private UserServeice userService;
	
	@GetMapping("user/{moduleName}")
	public String index(@PathVariable String moduleName) {
		return moduleName;
	}
	
	//@PostMapping("service/user/doRegister")
	@ResponseBody
	public SysResult doRegister(String username,String password,String phone) {
		SysResult obj = null;
		try {
			obj = userService.doRegisterUser(username,password,phone);
		} catch (Exception e) {
			e.printStackTrace();
			obj = SysResult.fail();
		}
		return obj; 
	}
	
	@PostMapping("service/user/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	//实现用户u登陆
	@RequestMapping("service/user/doLogin")
	@ResponseBody
	public SysResult lodin(User user,HttpServletResponse response) {
		try {
			String token = userService.findUserByUP(user);
			//如果数据不为空,将数据保存到cookie中
			//cookie中的key固定值JT_TICKET
			if(!StringUtils.isEmpty(token)) {
				Cookie cookie = new Cookie("JT_TICKET", token);
				cookie.setMaxAge(7*24*3600);//cookie的生命周期
				cookie.setDomain("jt.com");//设置全部的二级域名都能获取cookie信息
				cookie.setPath("/");
				response.addCookie(cookie);
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.fail();
	}
	/**
	 * 0. 注销用户,执行页面跳转
	 * 1. 删除cookie
	 * 2. 删除reids缓存
	 * 
	 * http://www.jt.com/user/logout.html
	 */
	@RequestMapping(value="user/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		try {
			Cookie[] cookie = request.getCookies();
			for (Cookie e : cookie) {
				if(e.getName().equals("JT_TICKET")){
					Cookie cc= new Cookie("JT_TICKET", "");
					cc.setMaxAge(0);
					e.setMaxAge(0);
					cc.setPath("/");
					e.setPath("/");
					cc.setDomain("jt.com");
					e.setDomain("jt.com");
					System.out.println(jc.get(e.getValue()));
					jc.del(e.getValue());
					response.addCookie(e);
					//response.addCookie(cc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//执行页面跳转
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
