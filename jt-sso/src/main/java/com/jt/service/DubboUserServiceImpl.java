package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Service(timeout=3000)
public class DubboUserServiceImpl implements DubboUserService{
	@Autowired
	private UserMapper usermapper;
	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public void saveUser(User user) {
		//1.将密码加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass)
			.setEmail(user.getEmail())
			.setCreated(new Date())
			.setUpdated(user.getCreated());
		usermapper.insert(user);
	}

	@Override
	public SysResult doRegisterUser(String username, String password, String phone) {
		return null;
	}

	/**
	 * 1.校验用户名或密码是否正确
	 * 2.如果数据不正确,返回null
	 * 3.如果数据正确
	 * 	>1.生成加密秘钥:(JT_TICKET_+username+当前毫秒数),再MD5加密
	 * 	>2.将userDB数据转化为userJSON
	 * 	>3.将数据保存到reids中,7天超时
	 * 3.返回TOKEN
	 */
	@Override
	public String findUserByUP(User user) {
		String token =null;
		//1.将密码进行加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		User userDB = usermapper.selectOne(queryWrapper);
		//2.判断数据正确执行下列代码
		if(userDB!=null) {
			token = "JT_TICKET_"+userDB.getUsername()+System.currentTimeMillis();
			token = DigestUtils.md5DigestAsHex(token.getBytes());
			//脱敏处理
			userDB.setPassword("你猜猜");
			String userJSON = ObjectMapperUtil.toJSON(userDB);
			jedisCluster.setex(token, 7*24*3600, userJSON);
		}
		return token;
	}
	
}
