package com.project.service;

import java.util.List;

import com.project.entity.User;

public interface UserService {

	public List<User> addUser(User user);
	public List<User> deleteUser(long userId);
	public List<User> editUser(long userId,User user);
	public List<User> viewUsers();
	public List<User> sortUsers(long sortType);
	public List<User> searchUserByName(String userName);
	
}
