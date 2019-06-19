package com.jt.service;

import com.jt.pojo.User;
import com.jt.vo.SysResult;

public interface DubboUserService {

	void saveUser(User user);

	SysResult doRegisterUser(String username, String password, String phone);

	String findUserByUP(User user);

}
