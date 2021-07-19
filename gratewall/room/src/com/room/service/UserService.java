package com.room.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room.dao.FavDao;
import com.room.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private FavDao favDao;

	public List getFavListByUserId(String uid) {

		return null;

	}

	public void saveFav() {

	}

}
