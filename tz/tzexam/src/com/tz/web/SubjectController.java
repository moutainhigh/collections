package com.tz.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tz.dao.SubjectDao;
import com.tz.model.Subject;


@Controller
@RequestMapping("/admin/subject")
public class SubjectController {
	 
	@Autowired
	private SubjectDao subjectDao;
	
	
	@ResponseBody
	@RequestMapping(value="/loadData",method=RequestMethod.POST)
	public String loadData(HttpServletRequest request) throws JSONException{
		//获取传递过来的参数
		String name = request.getParameter("name");
		List<Subject> subjects = subjectDao.findSubjects(name);
		if(subjects!=null && subjects.size()>0){
			String json = JSONUtil.serialize(subjects);
			return json;
		}else{
			return "empty";
		}
	}
}
