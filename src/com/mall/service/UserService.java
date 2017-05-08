package com.mall.service;

import java.sql.SQLException;

import com.mall.domain.User;

public interface UserService {
	public boolean existsUsername(String username);

	public boolean existsEmail(String email);
	
	public boolean registUser(User user);
	
	public String activeUser(String activeCode);
	
	public User  login(User user);

	public void changePassword(User sessionUser)throws SQLException;
}
