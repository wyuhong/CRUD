package com.biz;

import java.util.List;

import com.entity.UserInfo;

public interface IUserBiz {

	public boolean login(UserInfo ui);

	public List<UserInfo> findAllUser();

	public int deleteUserById(int id);

	public UserInfo findUserById(int id);

	public int updateUser(UserInfo ui);

	public int doAdd(UserInfo ui);
}
