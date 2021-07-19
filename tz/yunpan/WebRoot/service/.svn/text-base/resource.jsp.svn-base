<%@page import="com.yunpan.util.StringUtils"%>
<%@page import="org.apache.struts2.json.JSONUtil"%>
<%@page import="com.yunpan.bean.Resource"%>
<%@page import="com.yunpan.dao.ResourceDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	/*
	 	业务：添加文件信息,利用ajax
	 	时间:2014-11-23 01:34
	 	作者:keke老师
	 	描述:通过文件上传实现百度云盘的技术
	 	类:ResourceDao.saveResource(Resource resource)
	*/

	//获取客户端传递过来的参数,字符串
	String name = request.getParameter("name");
	String newName = request.getParameter("newName");
	String size = request.getParameter("size");
	String sizeString = request.getParameter("sizeString");
	String ext = request.getParameter("ext");
	String url = request.getParameter("url");
	String width = request.getParameter("width");
	String height = request.getParameter("height");
	String description = request.getParameter("description");
	String folderId = request.getParameter("folderId");
	
	
	//设置客户端传递过来的参数
	Resource resource = new Resource();
	resource.setName(name);
	resource.setSize(size==null?0:new Integer(size));
	resource.setSizeString(sizeString);
	resource.setIsDelete(0);//0未删除
	resource.setStatus(1);//1已发布
	resource.setNewName(newName);
	resource.setExt(ext);
	resource.setUrl(url);
	resource.setUserId(1);
	resource.setType(getType(ext));
	resource.setWidth(width==null?null:Integer.parseInt(width));
	resource.setHeight(height==null?null:Integer.parseInt(height));
	resource.setDescription(description);
	resource.setFolderId(folderId==null ? null :new Integer(folderId));
	//调用ResourceDao保存方法
	resource  = ResourceDao.saveResource(resource);
	if(resource != null){
		out.print(JSONUtil.serialize(resource));//request/response.getWriter()
	}else{
		out.print("fail");
	}
%>

<%!
	//
	public static int getType(String ext){
		int type = 4;//其他
		if(StringUtils.isDoc(ext)){
			type = 1;
		}else if(StringUtils.isImage(ext)){
			type = 2;
		}else if(StringUtils.isVideo(ext)){
			type = 3;
		}
		return type;
	}
%>