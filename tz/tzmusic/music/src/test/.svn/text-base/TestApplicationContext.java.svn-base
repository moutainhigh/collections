package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tz.dao.CommentDao;
import com.tz.dao.MusicPeriodDao;
import com.tz.dao.UserDao;
import com.tz.model.TzMusicComment;
import com.tz.model.TzMusicPeriod;
import com.tz.model.TzMusicUser;
import com.tz.util.TmStringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/applicationContext.xml")
public class TestApplicationContext {

	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void handle() throws SQLException{
		jdbcTemplate.query("select * from tz_music", new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				System.out.println(rs.getInt("id"));
				System.out.println(rs.getString("title"));
			}
		});
	}
	
	@Test
	public void handle2(){
		SessionFactory sessionFactory = context.getBean(SessionFactory.class);
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		TzMusicPeriod music = new TzMusicPeriod();
		music.setTitle("xxxxxx");
		session.save(music);
		tx.commit();
		session.close();
	}
	
	@Autowired
	private MusicPeriodDao musicPeriodDao;
	
	
	@Test
	public void handle3(){
		TzMusicPeriod period = musicPeriodDao.getMusicPeriod(1);
		System.out.println(period.getDescription());
	}
	
	@Test
	public void handle33(){
		List<TzMusicPeriod> periods = musicPeriodDao.findPeriods("", null);
		for (TzMusicPeriod tzMusicPeriod : periods) {
			System.out.println(tzMusicPeriod.getTitle());
		}
	}
	
	
	@Autowired
	private UserDao userDao;
	@Test
	public void handle4(){
		TzMusicUser musicUser = new TzMusicUser();
		musicUser.setUsername("keke");
		musicUser.setAccount("keke123");
		musicUser.setPassword(TmStringUtils.md5Base64("123456"));
		musicUser.setIsDelete(0);
		userDao.saveUser(musicUser);
	}
	
	
	@Autowired
	private CommentDao commentDao;
	@Test
	public void handle05(){
		TzMusicComment comment = new TzMusicComment();
		comment.setPeriod(new TzMusicPeriod(1));
		comment.setContent("我评论你音乐");
		comment.setUser(new TzMusicUser(1));
		comment.setStatus(1);
		comment.setIsDelete(0);
		commentDao.save(comment);
	}
	
	
	@Test
	public void handle06(){
		List<TzMusicComment> comments = commentDao.findComments(1, 10, 1);
		for (TzMusicComment tzMusicComment : comments) {
			System.out.println(tzMusicComment.getContent());
		}
	}
}
