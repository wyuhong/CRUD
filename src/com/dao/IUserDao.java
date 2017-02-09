package com.dao;

import java.util.List;

import com.entity.UserInfo;

public interface IUserDao {
	
	public boolean findUserByNamePwd(UserInfo ui);

	public List<UserInfo> findAllUser();

	public int deleteUserById(int id);

	public UserInfo findUserById(int id);

	public int updateUser(UserInfo ui);

	public int doAdd(UserInfo ui);
}
