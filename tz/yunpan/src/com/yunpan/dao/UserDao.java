package com.yunpan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import com.yunpan.bean.User;

/**
 * 
 * 用户的业务类
 * UserDao<BR>
 * 创建人:潭州学院-keke <BR>
 * 时间：2014年11月27日-下午10:46:33 <BR>
 * @version 1.0.0
 *
 */
public class UserDao {
	
	
	/**
	 * 根据用户名和密码查询用户信息
	 * 方法名：isExsitLogin<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月27日-下午10:54:37 <BR>
	 * @param account
	 * @param password void<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	
	public static User isExsitLogin(String account,String password){
		String sql = "SELECT * FROM tm_user WHERE account = ? AND `password` = ? AND is_delete = 0 ";
		System.out.println(sql);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		User user = null;
		try{
			//初始化链接对象
			connection = ConnectionUtil.getConnection();
			//执行sql
			statement = connection.prepareStatement(sql);
			//设置参数
			statement.setString(1, account);
			statement.setString(2, password);
			rs = statement.executeQuery();
			if(rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setAccount(rs.getString("account"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("username"));
				user.setCreatetime(new Date(rs.getTimestamp("createtime").getTime()));
				user.setUpdatetime(rs.getDate("updatetime"));
				user.setIsDelete(rs.getInt("is_delete"));
			}
			return user;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			ConnectionUtil.closeResultset(rs);
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}
	
	
	public static void main(String[] args) {
		User user = isExsitLogin("keke", "1234561");
		System.out.println(user.getAccount());
		System.out.println(user.getPassword());
		System.out.println(user.getCreatetime());
		System.out.println(user.getUpdatetime());
	}
}
