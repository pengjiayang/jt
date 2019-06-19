package com.jt.service;

import com.jt.pojo.User;
import com.jt.vo.SysResult;

public interface UserServeice {
	SysResult doRegisterUser(String username, String password, String phone);

	void saveUser(User user);
}
