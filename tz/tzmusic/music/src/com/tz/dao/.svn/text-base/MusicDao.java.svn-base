package com.tz.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tz.model.TzMusic;

/**
 * 
 * 音乐数据层
 * MusicDao
 * 创建人:keke
 * 时间：2015年3月26日-下午11:36:08 
 * @version 1.0.0
 *
 */
@Repository
@Transactional
public class MusicDao extends BaseDao<TzMusic, Integer>{
	
	/**
	 * 保存音乐
	 * 方法名：save
	 * 创建人：xuchengfei 时间：2015年3月26日-下午11:35:51 
	 * @param music
	 * @return TzMusic
	 * @exception 
	 * @since  1.0.0
	 */
	public TzMusic save(TzMusic music){
		try {
			getSession().save(music);
			return music;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 修改音乐
	 * 方法名：save
	 * 创建人：xuchengfei 时间：2015年3月26日-下午11:35:51 
	 * @param music
	 * @return TzMusic
	 * @exception 
	 * @since  1.0.0
	 */
	public TzMusic update(TzMusic music){
		try {
			updateDefault(music);
			return music;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 修改音乐
	 * 方法名：save
	 * 创建人：xuchengfei 时间：2015年3月26日-下午11:35:51 
	 * @param music
	 * @return TzMusic
	 * @exception 
	 * @since  1.0.0
	 */
	public boolean delete(Integer id){
		try {
			TzMusic tzMusic= (TzMusic) getSession().load(TzMusic.class, id);
			getSession().delete(tzMusic);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
