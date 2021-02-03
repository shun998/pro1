package com.zs.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zs.core.common.Page;
import com.zs.model.User;
import com.zs.service.UserService;
//管理用户控制层
@Controller
@RequestMapping("/admin/user")
public class AdminUserAction extends BaseAction{
	@Autowired
	UserService userService;
	
	@RequestMapping("/getAdminUsers")
	public String getAdminUsers(@RequestParam(required=false) String userName,
			@RequestParam(required=false) String userSex,
			@RequestParam(required=false) String sort,
			Map<String,Page<User>> m){
		Page<User> users=userService.getAdminUsers(userName,userSex,sort);
		m.put("users", users);
		System.out.println("users:"+users);
		return "/admin/user/userList";
	}
	
	@RequestMapping("/handleUser")
	public String handleUser(@RequestParam(required=false) String userId,
			Map<String,User> m){
		if(userId!=null && !userId.equals("")){
			User user=userService.getPersonalInfo(Integer.parseInt(userId));
			m.put("user", user);
		}
		
		return "/admin/user/handleUser";
	}
	
	@RequestMapping("/doHandleUser")
	public String doHandleUser(User user){
				
		try {
			if(user.getUserId()==0){
				boolean f=userService.addUser(user);
				if(f){
					this.addMessage("添加用户成功");
					this.addRedirURL("返回","/admin/user/getAdminUsers");
				}
				else{
					this.addMessage("对不起，该用户名已被占用，请更换");
					this.addRedirURL("返回","@back");
				}
			}
			else{
				userService.updateUser(user);
				this.addMessage("修改用户成功");
				this.addRedirURL("返回",getReferUrl());			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("操作用户失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/delUser")
	public String delUser(@RequestParam String userId){
		try{
			userService.deleteUser(userId);
			this.addMessage("删除用户成功");
			this.addRedirURL("返回",getReferUrl());	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("目前仍有这个用户的订单，无法删除该用户");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/delUsersByIds")
	public String delUsersByIds(@RequestParam String[] userIds){
		try{
			userService.deleteUsersByIds(userIds);
			this.addMessage("批量删除用户成功");
			this.addRedirURL("返回",getReferUrl());
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("选中的用户中仍有用户有订单，批量删除用户失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
}
