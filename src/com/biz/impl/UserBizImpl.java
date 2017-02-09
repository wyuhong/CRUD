package com.biz.impl;

import java.util.ArrayList;
import java.util.List;

import com.biz.IUserBiz;
import com.dao.IUserDao;
import com.dao.impl.UserDaoImpl;
import com.entity.UserInfo;
import com.util.JdbcUtils;

public class UserBizImpl implements IUserBiz {

	IUserDao udao = new UserDaoImpl();
	@Override
	public boolean login(UserInfo ui) {
		boolean bl = false;
		try{
			bl = udao.findUserByNamePwd(ui);
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			return bl;
	}
	@Override
	public List<UserInfo> findAllUser() {
		List<UserInfo> ulist = new ArrayList<UserInfo>();
		try{
			ulist = udao.findAllUser();
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			return ulist;
	}
	@Override
	public int deleteUserById(int id) {
		int i = 0;
		try{
			i = udao.deleteUserById(id);
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			return i;
	}
	@Override
	public UserInfo findUserById(int id) {
		UserInfo ui = new UserInfo();
		try{
			ui = udao.findUserById(id);
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			return ui;
	}
	@Override
	public int updateUser(UserInfo ui) {
		int i = 0;
		try{
			i = udao.updateUser(ui);
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			return i;
	}
	@Override
	public int doAdd(UserInfo ui) {
		int i = 0;
		try{
			i = udao.doAdd(ui);
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			return i;
	}

}
