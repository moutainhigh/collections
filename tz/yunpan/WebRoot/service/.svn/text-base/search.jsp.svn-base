<%@page import="org.apache.struts2.json.JSONUtil"%>
<%@page import="com.yunpan.bean.Resource"%>
<%@page import="com.yunpan.util.StringUtils"%>
<%@page import="com.yunpan.dao.ResourceDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	//获取分类	
	String t = request.getParameter("type");
	//获取查询的关键字
	String keyword = request.getParameter("keyword");
	//当前页
	String pageNo = request.getParameter("pageNo");
	String pageSize = request.getParameter("pageSize");
	Integer type = null;
	if(StringUtils.isNotEmpty(t))type = new Integer(t);
	//调用查询出来的文件信息
	if(StringUtils.isEmpty(pageNo))pageNo = "0";
	if(StringUtils.isEmpty(pageSize))pageSize = "10";
	//查询资源总数
	int count = ResourceDao.countResources(type,keyword);
	List<Resource> resources = ResourceDao.findResources(type,keyword,new Integer(pageNo),new Integer(pageSize));
	
	//作用里的数据
	request.setAttribute("resources", resources);
	request.setAttribute("itemcount", count);
	
	//跳转到我们的模板页面
	request.getRequestDispatcher("template.jsp").forward(request, response);
%>