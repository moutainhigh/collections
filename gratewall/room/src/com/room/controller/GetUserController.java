package com.room.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.room.bean.User;
import com.room.dao.FavDao;
import com.room.dao.UserDao;
import com.room.service.UserService;

@Service
public class GetUserController {

	@Autowired
	private UserService userService;

	public List getUserFavList(HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("userId");
		List list = userService.getFavListByUserId(userId);
		if (list != null & list.size() > 0) {
			return list;
		} else {
			return null;

		}
	}

	public void save(@RequestBody User user) {
	}
}
