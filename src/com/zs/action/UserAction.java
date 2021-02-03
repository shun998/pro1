package com.zs.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zs.core.Constants;
import com.zs.model.User;
import com.zs.service.UserService;
//用户控制层
@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction{

	@Autowired
	private UserService userService; 
	
	/**
	 * 用户登录
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Map<String,String> login(User user,HttpSession session){
		System.out.println("userlogin:"+user);
		User dbUser=userService.existsUser(user);
		//System.out.println(user.getUserName()+","+user.getPassword()+"--------"+user1);
		Map<String,String> m=new HashMap<String,String>();
		if(dbUser!=null){		
			session.setAttribute(Constants.LOGIN_USER, dbUser);
			m.put("login", "yes");	
			m.put("redirUrl", (String)session.getAttribute("redirUrl"));
			
		}
		else{
			//this.addMessage(Constants.LOGIN_ERR);
			//this.addRedirURL("返回登录页面", INDEX_PAGE);
			m.put("login","no");	
		}
		System.out.println("login:"+m);
		return m; 
	}
	
	/**
	 * 用户登出
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public Map<String,String> logout() throws Exception {
		getSession().invalidate();
		Map<String,String> m=new HashMap<String,String>();
		m.put("logout", "yes");
		return m;
	}
	
	/**
	 * 用户注册
	 */
	@ResponseBody
	@RequestMapping("/reg")
	public Map<String,String> reg(User user) throws Exception {	
		System.out.println(user);
		boolean f=userService.addUser(user);
		Map<String,String> m=new HashMap<String,String>();
		if(f){
			m.put("reg", "yes");
		}
		else{
			m.put("reg", "no");
		}
		return m;
	}
	
	/**
	 * 检查用户是否重复
	 */
	@ResponseBody
	@RequestMapping("/checkUserName")
	public Map<String,Boolean> existsUserName(@RequestParam String userName) throws Exception {	
		boolean f=userService.existsUserName(userName);
		Map<String,Boolean> m=new HashMap<String,Boolean>();
		m.put("available", f);
		return m;
	}
	
	@RequestMapping("/getPersonalInfo")
	public String getPersonalInfo(Map<String,User> m){
		System.out.println("userId:"+this.getLoginUserId());
		User user=userService.getPersonalInfo(this.getLoginUserId());
		m.put("user", user);
		System.out.println("user:"+user);
		return "/usercenter/updatePersonalInfo";
	}

	@RequestMapping("/updatePersonalInfo")
	public String updatePersonalInfo(User user){
		try{
			userService.updatePersonalInfo(user);
			this.addMessage("修改个人信息成功");
			this.addRedirURL("返回", "/user/getPersonalInfo");
			
		}catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			this.addMessage("修改个人信息失败");
			this.addRedirURL("返回", "@back");
			
		}
		return EXECUTE_RESULT;
	}
	@RequestMapping("/getPersonalPassword")
	public String getPersonalPassword(){
		
		return "/usercenter/updatePersonalPassword";
	}
	@RequestMapping("/updatePersonalPassword")
	public String updatePersonalPassword(@RequestParam String userPass,@RequestParam String newUserPass){
		try{
			User user=new User();
			user.setUserId(this.getLoginUserId());
			if(userPass.equals(userService.getPersonalInfo(this.getLoginUserId()).getUserPass())){
				user.setUserPass(newUserPass);
				userService.updatePersonalPassword(user);
				this.addMessage("修改密码成功");
				this.addRedirURL("返回", "/user/getPersonalPassword");
			}
			else{
				this.addMessage("输入的旧密码不正确");
				this.addRedirURL("返回", "@back");
			}
			
		}catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			this.addMessage("修改密码失败");
			this.addRedirURL("返回", "@back");
			
		}
		return EXECUTE_RESULT;
	}
	
	
	
	
	/**
	 * 管理员登录
	 */
	@ResponseBody
	@RequestMapping("/adminlogin")
	public Map<String,String> adminlogin(User user,HttpSession session){
		System.out.println("--------"+user);
		User dbUser=userService.existsAdmin(user);
		//System.out.println(user.getUserName()+","+user.getPassword()+"--------"+user1);
		Map<String,String> m=new HashMap<String,String>();
		if(dbUser!=null){		
			session.setAttribute(Constants.LOGIN_USER, dbUser);
			m.put("login", "yes");	
			m.put("redirUrl", (String)session.getAttribute("redirUrl"));
		}
		else{
			//this.addMessage(Constants.LOGIN_ERR); 
			//this.addRedirURL("返回登录页面", INDEX_PAGE);
			m.put("login","no");	
		}
		System.out.println("adminlogin:"+m);
		return m; 
	}

}
