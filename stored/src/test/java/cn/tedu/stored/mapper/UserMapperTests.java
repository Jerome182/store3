package cn.tedu.stored.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.stored.Entity.User;
import cn.tedu.stored.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {
	
	@Autowired
	UserMapper mapper;
	
	@Test
	public void addnew(){
		User user = new User();
		user.setUsername("r");
		user.setPassword("123456");
		user.setCreatedUser("许春坡");
		user.setCreatedTime(new Date());
		user.setModifiedUser("许春坡");
		user.setModifiedTime(new Date());
		Integer row = mapper.addnew(user);
	}
	
	@Test
	public void findByUsername() {
		String username = "r";
		User user  = mapper.findByUsername(username);
		System.err.println(user);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 4;
		User user = mapper.findByUid(uid);
		System.err.println(user);
	}
	
	@Test
	public void update() {
		Integer uid = 8;
		String password = "6789";
		String modifiedUser = "刘备";
		Date modifiedTime = new Date();
		Integer row = mapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		System.err.println(row);
	}
	
	@Test
	public void updateInfo() {
		User user = new User();
		user.setUid(16);
		user.setGender(1);
		user.setEmail("981485718@qq.com");
		user.setPhone("13552121323");
		user.setModifiedUser("许春坡");
		user.setModifiedTime(new Date());
		Integer row =mapper.updateInfo(user);
		System.err.println(row);
	}
	
}
