package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * true 表示用户已经存在  false 表示用
	 */
	@Override
	public boolean checkUser(String param, Integer type) {
		String column = type==1?"username":(type==2?"phone":"email");
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(column,param);
		Integer count = userMapper.selectCount(queryWrapper);
		return count ==1?true:false;
	}
	@Transactional
	@Override
	public void registerUser(String username,String password,String phone,String email) {
		User user = new User();
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		user.setUsername(username).setPhone(phone).setPassword(password).setEmail(email);
		System.out.println(user);
		userMapper.insert(user);
	}
	@Transactional
	@Override
	public void saveUser(User user) {
		user.setEmail(user.getPhone())
		.setCreated(new Date())
		.setUpdated(user.getCreated());
		userMapper.insert(user);
		//int a = 1/0; 测试当用户入库失败后用户展现情况
	}





































}
