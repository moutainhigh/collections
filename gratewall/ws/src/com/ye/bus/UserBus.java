package com.ye.bus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ye.dao.UserDao;

@Transactional
@Service
public class UserBus {

	public static Logger log = LogManager.getLogger(UserBus.class);

	@Autowired
	private UserDao userDao;
}
