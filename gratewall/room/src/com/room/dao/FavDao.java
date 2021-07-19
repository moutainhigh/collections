package com.room.dao;

import org.springframework.stereotype.Repository;

import com.room.basedao.BaseDao;
import com.room.bean.Fav;


@Repository
public class FavDao extends BaseDao {

	public void save(Fav fav) {
		this.getSession().save(fav);
	}
}
