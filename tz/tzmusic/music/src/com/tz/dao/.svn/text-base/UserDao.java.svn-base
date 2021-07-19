package com.tz.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.tz.model.TzMusicUser;

/**
 * 
 * 用户数据层 UserDao 创建人:keke 时间：2015年3月17日-下午10:58:19
 * 
 * @version 1.0.0
 * 
 */
@Repository
@Transactional
public class UserDao extends BaseDao {

	//sql
//	TzMusicUser musicUser2= (TzMusicUser) getSession()
//		.createSQLQuery("select * from tz_music_user where account = ? and password = ? and is_delete=0").addEntity(TzMusicUser.class)
//				.setString(0, account).setString(1, password).uniqueResult();
	
	//qbc 
//	DetachedCriteria criteria = DetachedCriteria.forClass(TzMusicUser.class);
//	criteria.add(Restrictions.eq("account",account));
//	criteria.add(Restrictions.eq("password",password));
//	criteria.add(Restrictions.eq("isDelete",0));
//	TzMusicUser musicUser3= (TzMusicUser) criteria.getExecutableCriteria(getSession()).uniqueResult();
	
	/**
	 * 根据用户账号和密码
	 * 方法名：getMusicUser
	 * 创建人：xuchengfei 时间：2015年3月17日-下午11:11:38 
	 * @param account
	 * @param password
	 * @return TzMusicUser
	 * @exception 
	 * @since  1.0.0
	 */
	public TzMusicUser getMusicUser(String account, String password) {
		//hql语句的写法,是面向对象的查询
		TzMusicUser musicUser = (TzMusicUser) getSession()
				.createQuery("from TzMusicUser where account = ? and password = ? and isDelete=0")
				.setString(0, account).setString(1, password).uniqueResult();
		return musicUser;
	}
	
	
	public TzMusicUser saveUser(TzMusicUser musicUser){
		try {
			Assert.notNull(musicUser, "entity不能为空");
			getSession().save(musicUser);
			return musicUser;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存实体对象失败" );
		}
	}
}
