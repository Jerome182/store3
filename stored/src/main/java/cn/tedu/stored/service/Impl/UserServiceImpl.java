package cn.tedu.stored.service.Impl;

import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.UUID;

import org.apache.tomcat.websocket.DigestAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.stored.Entity.User;
import cn.tedu.stored.mapper.UserMapper;
import cn.tedu.stored.service.IUserService;
import cn.tedu.stored.service.ex.InsertException;
import cn.tedu.stored.service.ex.PasswordNotMatchException;
import cn.tedu.stored.service.ex.UpdateException;
import cn.tedu.stored.service.ex.UserNotFoundException;
import cn.tedu.stored.service.ex.UsernameDuplicateException;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	UserMapper mapper;

	@Override
	public void reg(User user) {
		String username = user.getUsername();
		User result = mapper.findByUsername(username);
		if (result != null) {
			throw new UsernameDuplicateException("注册失败，用户名已存在");
		}
		String salt = UUID.randomUUID().toString();
		user.setSalt(salt);
		String password = user.getPassword();
		String oldPassword = getmd5Password(password, salt);
		user.setPassword(oldPassword);
		user.setIsDelete(0);

		user.setCreatedUser("许春坡");
		user.setCreatedTime(new Date());
		user.setModifiedUser("许春坡");
		user.setModifiedTime(new Date());
		Integer row = mapper.addnew(user);
		if (row != 1) {
			throw new InsertException("注册失败，请联系客服");
		}
	}

	private String getmd5Password(String password, String salt) {
		String msg = salt + password + salt;
		for (int i = 1; i < 3; i++) {
			msg = DigestUtils.md5DigestAsHex(msg.getBytes());
		}
		return msg;
	}

	@Override
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		User user = mapper.findByUsername(username);
		if (user == null) {
			throw new UserNotFoundException("用户名不存在!");
		}
		Integer isDelete = user.getIsDelete();
		if (isDelete.equals(1)) {
			throw new UserNotFoundException("用户名不存在!");
		}
		String salt = user.getSalt();
		String md5Password = getmd5Password(password, salt);
		if (!md5Password.equals(user.getPassword())) {
			throw new PasswordNotMatchException("密码错误!");
		}
		user.setIsDelete(null);
		user.setPassword(null);
		user.setSalt(null);
		return user;
	}

	@Override
	public void changelogin(Integer uid, String newPassword, String oldPassword, String modifiedUser, Date modifiedTime)
			throws UserNotFoundException, PasswordNotMatchException, UpdateException {

		// 使用uid查询用户数据
		User user = mapper.findByUid(uid);
		System.err.println(user);
		// 判断返回结果是否为null
		if (user == null) {
			// 是：UserNotFoundException
			throw new UserNotFoundException("修改密码异常！用户数据不存在");
		}

		// 判断isDelete是否为1
		if (user.getIsDelete().equals(1)) {
			// 是：UserNotFoundException
			throw new UserNotFoundException("修改密码异常！用户数据不存在");
		}

		// 获取查询到的盐值
		String salt = user.getSalt();
		// 对旧密码进行加密
		String oldMd5Password = getmd5Password(oldPassword, salt);
		// 比较加密结果和查询到的用户密码是否不一致
		if (!oldMd5Password.equals(user.getPassword())) {
			// 是：PasswordNotMatchException
			throw new PasswordNotMatchException("修改密码异常！原始密码错误");
		}

		// 对新密码进行加密
		String newMd5Password = getmd5Password(newPassword, salt);
		// 调用updatePassword()更新密码
		Integer row = mapper.updatePassword(uid, newMd5Password, modifiedUser, new Date());
		// 判断受影响的行数是否不为1
		if (!row.equals(1)) {
			// 是：UpdateException
			throw new UpdateException("修改密码异常！请联系管理员");
		}
	}

	@Override
	public User updateInformation(Integer uid) throws UserNotFoundException {
		User result = mapper.findByUid(uid);
		if(result ==null) {
			throw new UserNotFoundException("更新信息失败，请联系管理员!");
		}
		if(result .getIsDelete().equals(1)) {
			throw new UserNotFoundException("更新信息失败，请联系管理员!");
		}
		User user = new User();
		user.setEmail(user.getEmail());
		user.setGender(user.getGender());
		user.setPhone(user.getPhone());
		return user;
	}

}
