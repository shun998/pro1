package com.zs.mapper;

import java.util.List;

import com.zs.core.common.Page;
import com.zs.model.User;
//用户mapper接口
public interface UserMapper {	
	
	User existsUser(User user);
	int addUser(User user);
	int existsUserName(User user);
	User existsAdmin(User user);
	User getPersonalInfo(int loginUserId);
	void updatePersonalInfo(User user);
	void updatePersonalPassword(User user);
	List<User> getAdminUsers(Page<User> page);
	void deleteUser(String userId);
	void deleteUsersByIds(String[] userIds);
}
