package com.project.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.User;

@Repository
public interface UserDao<P> extends CrudRepository<User,Long>  {
	public List<User> findAllByOrderByLastNameAsc();
	public List<User> findAllByOrderByEmployeeIdAsc();
	public List<User> findAllByOrderByFirstNameAsc();
	public List<User> findByLastNameContainingIgnoreCase(String lastName);
}
