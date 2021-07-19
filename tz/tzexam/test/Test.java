import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tz.dao.UserDao;


public class Test {

	
	public static void main(String[] args) throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		
		UserDao userDao = (UserDao)context.getBean("userDao");
		System.out.println(userDao);
//		String password = TzStringUtils.md5Base64("123456");
//		User user = userDao.getLoginUser("keke", password);
//		System.out.println(user.getId());
	}
}
