package com.example.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.UserDao;
import com.example.entity.User;

@Service("userService")
@Transactional
public class UserService implements UserDao {
	
	@Resource
	private UserDao userDao;

	public List getAll() {
		return userDao.getAll();
	}
	
	public List<User> queryAccount(String account) {
		return userDao.queryAccount(account);
	}
    
    public List getUserById(int id) {
		return userDao.getUserById(id);
	}
    
    public List getCondition(String username) {
    	return userDao.getCondition(username);
    }
    
    public int deleteId(int id) {
		return userDao.deleteId(id);
	}
    
    public int updatePassWord(User user) {
    	return userDao.updatePassWord(user);
    }
    
    public int updateUserInfo(User user) {
    	return userDao.updateUserInfo(user);
    }
    
    public int insertUserInfo(User user) {
    	return userDao.insertUserInfo(user);
    }
    
}

