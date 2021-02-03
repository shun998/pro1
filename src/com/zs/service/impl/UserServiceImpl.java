package com.zs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zs.core.common.Page;
import com.zs.mapper.UserMapper;
import com.zs.model.Goods;
import com.zs.model.User;
import com.zs.service.UserService;



@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper mapper;

	

	@Override
	public User existsUser(User user) {
		// TODO Auto-generated method stub
		return mapper.existsUser(user);
	}
	
	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		if(mapper.existsUserName(user)==0){
			try{
				System.out.println(mapper.addUser(user));	
				//System.out.println(user);
				return true;
			}
			catch(Exception e){
				
				return false;
			}
		}
		else{
			return false;
		}
	}

	@Override
	public boolean existsUserName(String userName) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setUserName(userName);
		if(mapper.existsUserName(user)==0){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public User existsAdmin(User user) {
		// TODO Auto-generated method stub
		return mapper.existsAdmin(user);
	}

	@Override
	public User getPersonalInfo(int loginUserId) {
		// TODO Auto-generated method stub
		return mapper.getPersonalInfo(loginUserId);
	}

	@Override
	public void updatePersonalInfo(User user) {
		// TODO Auto-generated method stub
		mapper.updatePersonalInfo(user);
	}

	@Override
	public void updatePersonalPassword(User user) {
		// TODO Auto-generated method stub
		mapper.updatePersonalPassword(user);
	}

	@Override
	public Page<User> getAdminUsers(String userName, String userSex, String sort) {
		// TODO Auto-generated method stub
		Page<User> page = new Page<User>(15);
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("userName", userName);
		m.put("userSex", userSex);
		m.put("sort", sort);
		page.setParams(m);
		List<User> userList=mapper.getAdminUsers(page);
		page.setList(userList);
		return page;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		mapper.deleteUser(userId);
	}

	@Override
	public void deleteUsersByIds(String[] userIds) {
		// TODO Auto-generated method stub
		mapper.deleteUsersByIds(userIds);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		mapper.updatePersonalInfo(user);
		mapper.updatePersonalPassword(user);
	}
}
