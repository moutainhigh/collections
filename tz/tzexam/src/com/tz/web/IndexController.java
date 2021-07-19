package com.tz.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tz.dao.SubjectDao;
import com.tz.model.Subject;


@Controller
@RequestMapping("/admin")
public class IndexController {
	 
	@Autowired
	private SubjectDao subjectDao;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		List<Subject> subjects = subjectDao.findSubjects(null);
		request.setAttribute("subjects", subjects);
		return "index";
	}
	
	
	
	
}
