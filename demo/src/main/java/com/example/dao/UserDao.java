package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface UserDao {

	public List getAll();
	
	public List getUserById(int id);
	
	public List queryAccount(String account);
	
	public List getCondition(String username);
	
	public int deleteId(int id);
	
	public int updatePassWord(User user);
	
	public int updateUserInfo(User user);
	
	public int insertUserInfo(User user);
	
}