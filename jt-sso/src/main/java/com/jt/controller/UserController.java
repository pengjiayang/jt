package com.jt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jc;
	/**
	 * 业务说明
	 * 检验用户是否存在
	 * http://sso.jt.com/user/check
	 * 返回值:sysresult
	 * 由于是跨域的请求 所以返回值必须特殊处理callback(json)
	 */
	@GetMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param, 
								 @PathVariable Integer type,
								 String callback) {
		JSONPObject object = null;
		try {
			boolean flag = userService.checkUser(param,type);
			object = new JSONPObject(callback, SysResult.ok(flag));
		} catch (Exception e) {
			e.printStackTrace();
			object = new JSONPObject(callback, SysResult.fail());
		}
		return object;
	} 
	
	//@PostMapping("/register")
	public SysResult registerUser(String username,String password,String phone,String email) {
		SysResult obj = null;
		try {
			userService.registerUser(username,password,phone,email);
			obj = SysResult.ok(username);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		return obj;
	}
	@RequestMapping("/register")
	public SysResult saveUser(String userJSON) {
		try {
			User user = ObjectMapperUtil.toObject(userJSON, User.class);
			userService.saveUser(user);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	//利用跨域实现用户信息回显
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,
										String callback ) {
		String userJson = jc.get(ticket);
		if(!StringUtils.isEmpty(userJson)) {
			//回传数据需要经过200秒判断
			return new JSONPObject(callback, SysResult.ok(userJson));
		}else {
			return new JSONPObject(callback, SysResult.fail());
		}
	}
	
}



















