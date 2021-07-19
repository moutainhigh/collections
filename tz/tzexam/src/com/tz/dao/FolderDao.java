package com.tz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tz.model.Folder;

@Repository
public class FolderDao  extends BaseDao{

	
	/**
	 * 
	 * 根据账号和密码查询用户信息
	 * @author Administrator
	 * @param account
	 * @param password
	 * @return
	 * @since JDK 1.6
	 */
	public List<Folder> findFolders(TzParams params){
		//String hql ="FROM Folder WHERE isDelete= 0 AND status = 1 AND subjectId = 1 ORDER BY createTime DESC";
		StringBuilder builder = new StringBuilder();
		builder.append(" FROM Folder WHERE isDelete= 0 ");
		if(params!=null){
			if(params.getStatus()!=null){
				builder.append(" AND status = "+params.getStatus());
			}
			if(params.getSubjectId()!=null){
				builder.append(" AND subjectId = "+params.getSubjectId());
			}
		}
		
		builder.append(" ORDER BY createTime DESC ");
		return getSession().createQuery(builder.toString()).list();
	}
	
	/**
	 * 保存文件夹信息
	 * @author Administrator
	 * @return
	 * @since JDK 1.6
	 */
	public Folder saveFolder(Folder folder){
		try {
			getSession().save(folder);
			return folder;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 
	 * 根据id和名称更新目录名称
	 * @author Administrator
	 * @param name
	 * @param id
	 * @return
	 * @since JDK 1.6
	 */
	public boolean updateFolder(String name,Integer id){
		String sql = "update tz_folder set name = ? where id = ?";
		int count = getSession().createSQLQuery(sql)
			.setString(0, name)
			.setInteger(1, id)
			.executeUpdate();
		return count > 0?true:false;
	}
	
	/**
	 * 
	 * 根据id和名称更新目录名称
	 * @author Administrator
	 * @param name
	 * @param id
	 * @return
	 * @since JDK 1.6
	 */
	public boolean deleteFolder(Integer isDelete,Integer id){
		//String sql = "delete from tz_folder  where id = ?";
		String sql = "update tz_folder set is_delete = ? where id = ?";
		int count = getSession().createSQLQuery(sql)
			.setInteger(0, isDelete)
			.setInteger(1, id)
			.executeUpdate();
		return count > 0?true:false;
	}
	
}
