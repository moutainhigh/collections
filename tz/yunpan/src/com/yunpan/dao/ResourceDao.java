package com.yunpan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.yunpan.bean.Resource;
import com.yunpan.util.StringUtils;

/**
 * 
 * 资源文件的数据库的操作类
 * ResourceDao<BR>
 * 创建人:潭州学院-keke <BR>
 * 时间：2014年11月23日-上午1:04:02 <BR>
 * @version 1.0.0
 *
 */
public class ResourceDao {

	
	//curd-----添加  查询 修改 删除
	
	
//	/**
//	 * 保存文件方法--旧版本
//	 * 方法名：saveResource<BR>
//	 * 创建人：潭州学院-keke <BR>
//	 * 时间：2014年11月23日-上午1:08:32 <BR>
//	 * @return boolean<BR>
//	 * @exception <BR>
//	 * @since  1.0.0
//	 */
//	public static boolean saveResource(Resource resource){
//		String sql = "INSERT INTO tm_resource (NAME,size,sizeString,is_delete,STATUS,new_name,ext,user_id,width,height,description,folder_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
//		Connection connection = null;
//		PreparedStatement statement = null;
//		try{
//			//获取获取库链接对象
//			connection = ConnectionUtil.getConnection();
//			//实例并且执行的预处理块
//			//new java.sql.Date(new Date().getTime())
//			//设置参数
//			statement = connection.prepareStatement(sql);
//			statement.setString(1, resource.getName());
//			statement.setInt(2, resource.getSize());
//			statement.setString(3, resource.getSizeString());
//			statement.setInt(4, resource.getIsDelete());
//			statement.setInt(5, resource.getStatus());
//			statement.setString(6, resource.getNewName());
//			statement.setString(7, resource.getExt());
//			statement.setInt(8, resource.getUserId());
//			statement.setInt(9, resource.getWidth());
//			statement.setInt(10, resource.getHeight());
//			statement.setString(11, resource.getDescription());
//			statement.setInt(12, resource.getFolderId());
//			//执行sql
//			int count = statement.executeUpdate();
//			return count >0 ? true: false;
//		}catch(Exception ex){
//			ex.printStackTrace();
//			return false;
//		}finally{
//			ConnectionUtil.closeConnection(connection);
//			ConnectionUtil.closeStatement(statement);
//		}
//	}
	
	
	
	/**
	 * 保存文件方法
	 * 方法名：saveResource<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月23日-上午1:08:32 <BR>
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static Resource saveResource(Resource resource){
		String sql = "INSERT INTO tm_resource (NAME,size,sizeString,is_delete,STATUS,new_name,ext,user_id,width,height,description,folder_id,url,type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection connection = null;
		PreparedStatement statement = null;
		try{
			//获取获取库链接对象
			connection = ConnectionUtil.getConnection();
			//实例并且执行的预处理块
			//new java.sql.Date(new Date().getTime())
			//设置参数
			statement = connection.prepareStatement(sql);
			statement.setString(1, resource.getName());
			statement.setInt(2, resource.getSize());
			statement.setString(3, resource.getSizeString());
			statement.setInt(4, resource.getIsDelete());
			statement.setInt(5, resource.getStatus());
			statement.setString(6, resource.getNewName());
			statement.setString(7, resource.getExt());
			statement.setInt(8, resource.getUserId());
			statement.setInt(9, resource.getWidth());
			statement.setInt(10, resource.getHeight());
			statement.setString(11, resource.getDescription());
			statement.setInt(12, resource.getFolderId());
			statement.setString(13, resource.getUrl());
			statement.setInt(14, resource.getType());
			//执行sql
			int count = statement.executeUpdate();
			if(count>0){
				ResultSet rs =  statement.getGeneratedKeys();
				if(rs.next()){
					resource.setId(rs.getInt(1));
				}
				return resource;
			}else{
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}
	
	/**
	 * 查看文件信息
	 * 方法名：findResources<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月23日-上午2:06:11 <BR>
	 * @return List<Resource><BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static List<Resource> findResources(Integer type,String keyword,Integer pageNo,Integer pageSize){
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM tm_resource where is_delete = 0 ");
		
		if(type!=null){
			builder.append(" and type = "+type);
		}
		
		if(StringUtils.isNotEmpty(keyword)){
			builder.append(" and name like '%"+keyword+"%'");
		}
		
		builder.append("  order by createtime desc");
		builder.append(" limit ?,?");
		String sql = builder.toString();//查询没有删除的文件信息
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Resource> resources = null;
		try{
			//获取获取库链接对象
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement(sql);
			//设置页面和查询记录数
			statement.setInt(1, pageNo);
			statement.setInt(2, pageSize);
			rs = statement.executeQuery();
			resources = new ArrayList<Resource>();
			Resource resource = null;
			while(rs.next()){
				resource = new Resource();
				resource.setId(rs.getInt("id"));
				resource.setName(rs.getString("name"));
				resource.setSize(rs.getInt("size"));
				resource.setSizeString(rs.getString("sizeString"));
				resource.setIsDelete(rs.getInt("is_delete"));
				resource.setStatus(rs.getInt("status"));
				resource.setNewName(rs.getString("new_name"));
				resource.setExt(rs.getString("ext"));
				resource.setUserId(rs.getInt("user_id"));
				resource.setType(rs.getInt("type"));
				resource.setWidth(rs.getInt("width"));
				resource.setCreateTime(rs.getTimestamp("createtime"));
				resource.setHeight(rs.getInt("height"));
				resource.setFolderId(rs.getInt("folder_id"));
				resource.setDescription(rs.getString("description"));
				resource.setUrl(rs.getString("url"));
				resources.add(resource);
			}
			return resources;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			ConnectionUtil.closeResultset(rs);
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}
	
	
	/**
	 * 求取资源文件的总数
	 * 方法名：countResources<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月29日-下午11:04:52 <BR>
	 * @param type 分类
	 * @return int<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static int countResources(Integer type,String keyword){
		StringBuffer countBuffer = new StringBuffer();
		countBuffer.append("select count(1) from tm_resource where is_delete = 0 ");
		if(type!=null){
			countBuffer.append(" and type = "+type);
		}
		
		if(StringUtils.isNotEmpty(keyword)){
			countBuffer.append(" and name like '%"+keyword+"%'");
		}
		
		String sql = countBuffer.toString();//查询没有删除的文件信息
		System.out.println(sql);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try{
			//获取获取库链接对象
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement(sql);
			//设置页面和查询记录数
			rs = statement.executeQuery();
			int count = 0;
			if(rs.next()){
				count = rs.getInt(1);
			}
			return count;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}finally{
			ConnectionUtil.closeResultset(rs);
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}
	
	
	/**
	 * 根据主键ID删除文件方法
	 * 方法名：saveResource<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月23日-上午1:08:32 <BR>
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static boolean deleteResource(Integer id){
		String sql = "DELETE FROM tm_resource WHERE id =?";
		Connection connection = null;
		PreparedStatement statement = null;
		try{
			//获取获取库链接对象
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			int count = statement.executeUpdate();
			return count >0 ? true : false;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}
	
	
	/**
	 * 
	 * 方法名：deleteResources<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月25日-下午9:27:19 <BR>
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static boolean deleteResources(String ids){
		String sql =transferSql("tm_resource",ids);
		Connection connection = null;
		PreparedStatement statement = null;
		try{
			//获取连接
			connection = ConnectionUtil.getConnection();
			//处理sql
			statement = connection.prepareStatement(sql);
			//执行sql 受影响的行: 2
			int count = statement.executeUpdate();
			return count > 0 ? true :false;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}
	
	/**
	 * 转换sql
	 * 方法名：transferSql<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月25日-下午9:53:44 <BR>
	 * @param table
	 * @param param
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String transferSql(String table,String param){
		String sql = "delete from "+table+" where id in(";
		if(StringUtils.isNotEmpty(param)){
			String[] params = param.split(",");
			for (int i = 0; i < params.length; i++) {
				if(i==params.length-1){
					sql+= params[i];
				}else{
					sql+= params[i]+",";
				}
			}
		}
		sql+=")";
		return sql;
	}
	
	
	/**
	 * 增:savexx/insertxxx
	 * 删:removexxx/deletexxx/delxxx
	 * 查 :findxxx/searchxxx/queryxxx --针对多个情况  getxxx--返回单个的情况
	 * 改:updatexxx/modifyxxx
	 * 方法名：updateResource<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月23日-下午10:33:23 <BR>
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static boolean updateResource(Integer id,String name){
		String sql = "UPDATE tm_resource SET name = ? where id = ? ";
		Connection connection = null;
		PreparedStatement statement = null;
		try{
			//获取获取库链接对象
			connection = ConnectionUtil.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setInt(2, id);
			int count = statement.executeUpdate();
			return count >0 ? true : false;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			ConnectionUtil.closeStatement(statement);
			ConnectionUtil.closeConnection(connection);
		}
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		boolean flag = deleteResources("610,611,612");
		System.out.println(flag);
		
		
//		System.out.println(countResources(null));
		
		//updateResource(111,"我是keke老师");
//		deleteResource(77);
//		List<Resource> resources = findResources(null,0,100);
//		for (Resource resource : resources) {
//			System.out.println(resource.getId()+"===="+resource.getName()+"==="+resource.getCreateTime());
//		}
		
		
//		Resource resource = new Resource();
//		resource.setName("1.txt");
//		resource.setSize(100);
//		resource.setSizeString("100 MB");
//		resource.setIsDelete(0);
//		resource.setStatus(1);
//		resource.setNewName("1sdfsdfsdfsdf.txt");
//		resource.setExt("txt");
//		resource.setUserId(1);
//		resource.setWidth(100);
//		resource.setHeight(100);
//		resource.setDescription("xxxxx");
//		resource.setFolderId(1);
//		boolean flag = saveResource(resource);
//		if(flag){
//			System.out.println("添加成功");
//		}else{
//			System.out.println("添加失败");
//		}
	}
}
