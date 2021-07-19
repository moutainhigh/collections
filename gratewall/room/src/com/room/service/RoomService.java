package com.room.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room.dao.RoomDao;


@Service
public class RoomService {

	
	@Autowired
	private RoomDao roomDao;
	
	public List getRoomListById(String id) {
		return null;
	}

}
