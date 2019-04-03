package com.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.UserDao;
import com.project.entity.User;
import com.project.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao<User> userDao;

	@Override
	public List<User> addUser(User user) {
		userDao.save(user);
		return viewUsers();
	}

	@Override
	public List<User> deleteUser(long userId) {
		userDao.delete(userId);
		return viewUsers();
	}

	@Override
	public List<User> editUser(long userId, User user) {
		userDao.save(user);
		return viewUsers();
	}

	@Override
	public List<User> viewUsers() {
		Iterable<User> lstItr = userDao.findAll();
		List<User> lstUsers = new ArrayList<>();
		lstItr.forEach(lstUsers::add);
		return lstUsers;
	}

	@Override
	public List<User> sortUsers(long sortType) {
		// TODO Auto-generated method stub
		Iterable<User> lstItr = null;
		List<User> lstUsers = new ArrayList<>();
		if(sortType ==1) {
			lstItr = userDao.findAllByOrderByFirstNameAsc();
		}else if(sortType ==2) {
			lstItr = userDao.findAllByOrderByLastNameAsc();
		}else if(sortType ==3) {
			lstItr = userDao.findAllByOrderByEmployeeIdAsc();
		}else {
			lstItr = userDao.findAllByOrderByFirstNameAsc();
		}
		
		lstItr.forEach(lstUsers::add);
		return lstUsers;
	}
	
	@Override
	public List<User> searchUserByName(String lastName){
		Iterable<User> lstItr = null;
		List<User> lstUsers = new ArrayList<>();
		lstItr = userDao.findByLastNameContainingIgnoreCase(lastName);
		lstItr.forEach(lstUsers::add);
		return lstUsers;
		
	}

	
}
