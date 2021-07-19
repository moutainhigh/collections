package com.tz.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tz.model.TzMusicComment;

@Repository
@Transactional
public class CommentDao extends BaseDao{
	
	/**
	 * 根据期刊查询对应评论并且分页
	 * 方法名：findComments
	 * 创建人：xuchengfei 时间：2015年3月25日-上午12:23:12 
	 * @param peroidId
	 * @param pageNo
	 * @param pageSize
	 * @return List<TzMusicComment>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<TzMusicComment> findComments(Integer peroidId,Integer pageNo,Integer pageSize){
		//查询出已发布的并且未删除的评论
		String hql = "from TzMusicComment where period.id = ? and isDelete = 0 and status = 1 order by createTime desc";
		List<TzMusicComment> comments = getSession().createQuery(hql).setInteger(0, peroidId).setFirstResult(pageNo).setMaxResults(pageSize).list();
		return comments;
	}
	
	public Integer countComments(Integer peroidId){
		//查询出已发布的并且未删除的评论
		String countSql = "select count(1) from TzMusicComment where period.id = ? and isDelete = 0 and status = 1";
		Number number   = (Number)getSession().createQuery(countSql).setInteger(0, peroidId).uniqueResult();
		return number!=null?number.intValue():0;
	}
	
	
	/**
	 * 保存评论
	 * 方法名：save
	 * 创建人：xuchengfei 时间：2015年3月24日-下午10:17:12 
	 * @param comment
	 * @return TzMusicComment
	 * @exception 
	 * @since  1.0.0
	 */
	public TzMusicComment save(TzMusicComment comment){
		try {
			getSession().save(comment);
			return comment;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
