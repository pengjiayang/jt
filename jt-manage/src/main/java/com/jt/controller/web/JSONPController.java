package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JSONPController {

//	@RequestMapping("/web/testJSONP")
	public String testJSONP(String hello) {
		User user = new User();
		user.setId(100L);
//		user.setName("叶增辉我儿子");
		String json = ObjectMapperUtil.toJSON(user);
		return hello+"("+json+")";
	}
	@RequestMapping("/web/testJSONP")
	public JSONPObject jsonp(String hello) {
		User user = new User();
		user.setId(100L);
//		user.setName("叶增辉我儿子");
		JSONPObject object = new JSONPObject(hello,user);
		return object;
	}
}
