package com.punuo;

import com.punuo.bean.User;
import com.punuo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.nio.cs.US_ASCII;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceForIpcApplicationTests {

	@Autowired
	UserService userService;

	@Test
	public void contextLoads() {
		System.out.println(userService.getUserByUserName("test"));
	}

}
