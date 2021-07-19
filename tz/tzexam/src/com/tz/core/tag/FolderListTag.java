/**
 * Project Name:tzexam
 * File Name:MessageTag.java
 * Package Name:com.tz.core.tag
 * Date:2015年10月20日下午10:00:20
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.tz.core.tag;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tz.dao.FolderDao;
import com.tz.dao.TzParams;
import com.tz.model.Folder;
import com.tz.util.TzStringUtils;

/**
 * ClassName:MessageTag <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年10月20日 下午10:00:20 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */

public class FolderListTag extends BodyTagSupport {

	private Integer subjectId;
	private String var;
	private Integer status;
	private Iterator<Folder> iterator;
	private int count;

	@Override
	public int doStartTag() throws JspException {
		// 怎么让自定义标签和Spring进行管理
		ServletContext context = this.pageContext.getRequest()
				.getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(context);
		// 拿到国际化的对象
		FolderDao folderDao = (FolderDao) applicationContext
				.getBean("folderDao");
		
		TzParams params = new TzParams();
		params.setSubjectId(subjectId);
		params.setStatus(status==null?1:status);
		if(TzStringUtils.isEmpty(var))var="folder";
		List<Folder> folders = folderDao.findFolders(params);
		if(folders!=null && folders.size()>0){
			iterator = folders.iterator();
			this.pageContext.setAttribute(var, iterator.next());
			count =0; 
			this.pageContext.setAttribute(var+"_index", count);
			return EVAL_BODY_INCLUDE;
		}else{
			return SKIP_BODY;
		}
		
	}
	

	@Override
	public int doAfterBody() throws JspException {
		if(iterator.hasNext() ){
			count++;
			Folder folder = iterator.next();
			this.pageContext.setAttribute(var, folder);
			this.pageContext.setAttribute(var+"_index", count);
			return EVAL_BODY_AGAIN;
		}else{
			return SKIP_PAGE;
		}
	}


	@Override
	public int doEndTag() throws JspException {
		count = 0;
		iterator = null;
		return EVAL_PAGE;
	}


	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
