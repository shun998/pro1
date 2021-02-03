package com.zs.model;
//用户实体类
public class User { 

	private int userId;
	private String userName;
	private String userPass;
	private int userSex;
	private String userEmail;
	private int userRank;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(int userId, int userAge, String userName, String userPass, int userSex, String userEmail,
			int userRank) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPass = userPass;
		this.userSex = userSex;
		this.userEmail = userEmail;
		this.userRank = userRank;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public int getUserSex() {
		return userSex;
	}
	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getUserRank() {
		return userRank;
	}
	public void setUserRank(int userRank) {
		this.userRank = userRank;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPass=" + userPass
				+ ", userSex=" + userSex + ", userEmail=" + userEmail + ", userRank=" + userRank + "]";
	}

	
}
