<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/WEB-INF/tlds/tz.tld" prefix="tz" %>
<c:forEach items="${periods}" var="period">
	<tr class="tz_items" id="tm_peroid_${period.id}">
		<td>${period.id}</td>
		<td><img src="${basePath}/${period.img}" width="40" height="40"></td>
		<td><a href="${basePath}/music/${period.id}" title="${period.title}" target="_blank">${period.title}</a></td>
		<td>${period.musicType.name}</td>
		<td>${period.hit}</td>
		<td>${period.period}</td>
		<td>${tz:formateDate(period.createTime,'yyyy年MM月dd日 ')}</td>
		<td><a href="javascript:void(0);"  data-opid="${period.id}"  data-status="${period.status}" onclick="tz_publish(this)" class="${period.status==1?'green':'red'}">${period.status==1?"发布":"未发布"}</a></td>
		<td><a href="${basePath}/music/${period.id}" title="${period.title}" target="_blank">预览</a>&nbsp;
		<a href="${basePath}/period/editmusic/${period.id}" target="_blank">修改</a>&nbsp;
		<a href="javascript:void(0);" data-opid="${period.id}" onclick="tm_deleteperiod(this)">删除</a>&nbsp;</td>
	</tr>
</c:forEach>