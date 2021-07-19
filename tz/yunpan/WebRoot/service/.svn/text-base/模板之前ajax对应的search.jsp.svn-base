<%@page import="org.apache.struts2.json.JSONUtil"%>
<%@page import="com.yunpan.bean.Resource"%>
<%@page import="com.yunpan.util.StringUtils"%>
<%@page import="com.yunpan.dao.ResourceDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	//获取分类	
	String t = request.getParameter("t");
	//当前页
	String pageNo = request.getParameter("pageNo");
	String pageSize = request.getParameter("pageSize");
	Integer type = null;
	if(StringUtils.isNotEmpty(t))type = new Integer(t);
	//调用查询出来的文件信息
	if(StringUtils.isEmpty(pageNo))pageNo = "0";
	if(StringUtils.isEmpty(pageSize))pageSize = "10";
	//查询资源总数
	int count = ResourceDao.countResources(type);
	HashMap<String,Object> map = new HashMap<String,Object>();
	map.put("total", count);
	List<Resource> resources = ResourceDao.findResources(type,new Integer(pageNo),new Integer(pageSize));
	map.put("datas", JSONUtil.serialize(resources));
	out.print(JSONUtil.serialize(map));
	//客户端和服务端之前数据的传递一定是：字符串(json/xml,有格式的字符串，因为方便数据解析和获取)
%>