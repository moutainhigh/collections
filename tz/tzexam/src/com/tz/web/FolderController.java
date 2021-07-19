package com.tz.web;

import static com.tz.util.TzConstant.SESSION_USER_ID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tz.dao.FolderDao;
import com.tz.model.Folder;


@Controller
@RequestMapping("/admin/folder")
public class FolderController {
	 
	@Autowired
	private FolderDao folderDao;
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Folder saveFolder(HttpServletRequest request,HttpSession session){
		String sid = request.getParameter("sid");
		String name =request.getParameter("name");
		Integer userId = (Integer)session.getAttribute(SESSION_USER_ID);
		Folder folder  = new Folder();
		folder.setName(name);
		folder.setUserId(userId);
		folder.setSubjectId(Integer.parseInt(sid));
		folder.setIsDelete(0);
		folder.setStatus(1);
		folder =  folderDao.saveFolder(folder);
		if(folder!=null){
			return folder;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * 删除
	 * @author Administrator
	 * @param id
	 * @return
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	public String deleteFolder(@PathVariable("id")Integer id){
		boolean flag =  folderDao.deleteFolder(1, id);
		if(flag){
			return "success";
		}else{
			return "fail";
		}
	}
	
	/**
	 * 
	 * 更新
	 * @author Administrator
	 * @param name
	 * @param id
	 * @return
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String updateFolder(@PathVariable("id")Integer id,HttpServletRequest request){
		String name = request.getParameter("name");
		boolean flag =  folderDao.updateFolder(name, id);
		if(flag){
			return "success";
		}else{
			return "fail";
		}
	}

}
