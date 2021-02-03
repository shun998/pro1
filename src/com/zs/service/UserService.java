package com.zs.service;

import com.zs.core.common.Page;

import com.zs.model.User;

//用户业务层接口
public interface UserService {
	User existsUser(User user);
	boolean addUser(User user);
	boolean existsUserName(String userName);
	User existsAdmin(User user);
	User getPersonalInfo(int loginUserId);
	void updatePersonalInfo(User user);
	void updatePersonalPassword(User user);
	Page<User> getAdminUsers(String userName, String userSex, String sort);
	void deleteUser(String userId);
	void deleteUsersByIds(String[] userIds);
	void updateUser(User user);
	
}
