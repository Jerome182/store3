package cn.tedu.stored.service;

import java.util.Date;

import cn.tedu.stored.Entity.User;
import cn.tedu.stored.service.ex.InsertException;
import cn.tedu.stored.service.ex.PasswordNotMatchException;
import cn.tedu.stored.service.ex.UpdateException;
import cn.tedu.stored.service.ex.UserNotFoundException;
import cn.tedu.stored.service.ex.UsernameDuplicateException;

public interface IUserService {
	/*
	 * 注册用户名方法
	 */
	void reg(User user)throws UsernameDuplicateException,InsertException;
	
	/*
	 * 用户登录方法
	 */
	User login(String username,String password)throws UserNotFoundException,PasswordNotMatchException;
	
	/**
	 * 密码修改
	 */
	void changelogin(Integer uid,String newPassword,
			String oldPassword,String modifiedUser,
			Date modifiedTime)throws UserNotFoundException,PasswordNotMatchException,UpdateException;
	
	User updateInformation(Integer uid) throws UserNotFoundException;
}
