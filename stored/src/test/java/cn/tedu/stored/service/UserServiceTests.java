package cn.tedu.stored.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.stored.Entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
	
	@Autowired
	IUserService service;
	
	@Test
	public void reg() {
		User user = new User();
		user.setUsername("Tom");
		user.setPassword("1234");
		service.reg(user);
	}
	@Test
	public void login() {
		String username = "abc";
		String password  = "1234";
		User user = service.login(username, password);
		System.err.println(user);
	}
	@Test
	public void changelogin() {
		Integer uid =11;
		String newPassword = "6789";
		String oldPassword = "1234";
		String modifiedUser = "xuchunpo";
		service.changelogin(uid, newPassword, oldPassword, modifiedUser, new Date());
	}
	
	@Test
	public void changeInformation() {
		Integer uid = 13;
		User user = service.updateInformation(uid);
		System.err.println(user);
	}
	
}
