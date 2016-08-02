package com.tonto.wx.dao;

import com.tonto.wx.model.User;


public interface UserDao{
	
	User getUser(String username);
}