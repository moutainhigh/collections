/**
 * Project Name:tzexam
 * File Name:UserDaoTest.java
 * Package Name:com.tz.user
 * Date:2015年10月19日上午12:56:20
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.tz.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tz.dao.UserDao;
import com.tz.model.User;
import com.tz.util.TzStringUtils;

/**
 * ClassName:UserDaoTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年10月19日 上午12:56:20 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void handler(){
		String password = TzStringUtils.md5Base64("123456");
		User user = userDao.getLoginUser("keke", password);
		System.out.println(user.getId());
		
//		Object[]  params = {"校长","星期六","23423423"};
//		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		String str3 = context.getMessage("name",params, Locale.CHINA);
//		System.out.println(str3);
	}

}

