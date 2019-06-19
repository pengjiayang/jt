package com.jt.service;

import com.jt.pojo.User;

public interface UserService {
	boolean checkUser(String param, Integer type);
	void registerUser(String username,String password,String phone,String email);
	void saveUser(User user);

}
