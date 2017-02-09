package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.dao.IUserDao;
import com.entity.UserInfo;
import com.util.JdbcUtils;

public class UserDaoImpl implements IUserDao {

	@Override
	public boolean findUserByNamePwd(UserInfo ui) {
		boolean bl = false;
		try{
			int i = JdbcUtils.querySingle("select count(*) from userinfo where userName=? and userPwd=? ", new Object[]{ui.getUserName(),ui.getUserPwd()});
			if(i>0){
			bl = true;
		}
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
			List list = JdbcUtils.query("select * from userinfo");
		    for (Object object : list) {
		    	//???功能
		    	ulist.add(JSON.parseObject(JSON.toJSONString(object), UserInfo.class));
			}
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
			i = JdbcUtils.update("delete from userinfo where id=?", new Object[]{id});
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
			List list = JdbcUtils.query("select * from userinfo where id=? ", new Object[]{id});
			ui = JSON.parseObject(JSON.toJSONString(list.get(0)), UserInfo.class);
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
			i = JdbcUtils.update("update userinfo set userName=?,userPwd=? where id=?", new Object[]{ui.getUserName(),ui.getUserPwd(),ui.getId()});
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
			i = JdbcUtils.update("insert into userinfo(userName,userPwd) value(?,?)", new Object[]{ui.getUserName(),ui.getUserPwd()});
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}
		
}
