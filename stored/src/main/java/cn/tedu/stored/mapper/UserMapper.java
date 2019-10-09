package cn.tedu.stored.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.stored.Entity.User;

public interface UserMapper {
	/**
	    * 注册插入数据
	 * @param user
	 * @return
	 */
	Integer addnew(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return 
	 */
	Integer updateInfo(User user);
	
	/**
	 * 修改密码
	 */
	Integer updatePassword(@Param("uid")Integer uid,
			@Param("password")String password,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime);
	
	/**
	 * 注册之前查下之前是否已经有用户名
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
	
	
	/**
	 * 根据uid查询用户数据
	 */
	User findByUid(Integer uid);
	
	
}
