package com.tz.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tz.dao.UserDao;


@Controller
public class UserController {
	 
	@Autowired
	private UserDao userDao;

	
}
