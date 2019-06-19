package com.jt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jt.pojo.Item;
import com.jt.pojo.User;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;
//微服务下失效
@Service
public class UserServiceImpl implements UserServeice {
	
	@Autowired
	private HttpClientService httpClientService;

	@Override
	public SysResult doRegisterUser(String username, String password, String phone) {
		String url = "http://sso.jt.com/user/register";
		Map<String,String> params = new HashMap<>();
		params.put("username", username+"");
		params.put("password", password+"");
		params.put("phone", phone+"");
		params.put("email", phone+"");
		String result = httpClientService.doPost(url,params);
		SysResult obj = ObjectMapperUtil.toObject(result, SysResult.class);
		return obj;
	}

	@Override
	public void saveUser(User user) {
		String url = "http://sso.jt.com/user/register";
		//将密码加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		String userJSON = ObjectMapperUtil.toJSON(user);
		Map<String,String> params = new HashMap<>();
		//将数据封装到map集合中参数key=userJSON
		params.put("userJSON", userJSON);
		String result = httpClientService.doPost(url, params);
		//判断返回值是否正确
		SysResult sysResult = ObjectMapperUtil.toObject(result, SysResult.class);
		if(sysResult.getStatus() == 201) {
			//说明后台程序运行出错.业务停止
			throw new RuntimeException();
		}
	}

}
