package com.laoda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.laoda.dao.GoodDao;

@Controller
public class GoodController {

	@Autowired
	private GoodDao goodDao;
	
	public List getGoodList(int pageIndex) {
		return null;
		
	}
}
