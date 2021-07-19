package com.tz.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tz.dao.ExamDao;
import com.tz.dao.TzParams;
import com.tz.model.Exam;


@Controller
@RequestMapping("/admin/exam")
public class ExamController extends BaseController{
	 
	@Autowired
	private ExamDao examDao;
	
	@RequestMapping(value="/list")
	public String list(TzParams params,HttpServletRequest request,HttpSession session){
		List<Object[]> exams = examDao.findExams(params);
		request.setAttribute("exams",exams);
		return "template";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Integer  list(Exam exam){
		exam.setUserId(getUserId());
		exam.setIsDelete(0);
		exam.setStatus(1);
		examDao.saveExam(exam);
		return exam.getId();
	}
	

}
