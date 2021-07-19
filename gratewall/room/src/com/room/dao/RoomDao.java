package com.room.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.room.basedao.BaseDao;
import com.room.bean.Room;

@Repository
public class RoomDao extends BaseDao {

	public List getRoomListById(String id) {
		String hql = "From Room t where t.roomdId in( :roomdId)";
		Query query = this.getSession().createQuery(hql);

		if (id.contains(",")) {
			String[] params = id.split(",");
			query.setParameterList("roomdId", params);
		} else {
			query.setParameter("roomdId", id);
		}

		List list = query.list();
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public void save(Room room) {
		this.getSession().save(room);
	}
	
	
	
}
