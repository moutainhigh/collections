<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- c标签 -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 自定义函数标签 -->
<%@taglib uri="/WEB-INF/tld/tz.tld" prefix="tz" %>
<c:choose>
	<c:when test="${tz:getLength(resources)==0}">
		<h1>暂无上传任何数据</h1>	
	</c:when>
	<c:otherwise>
		<c:forEach var="resource" items="${resources}" varStatus="vr">
			<li id="tm-items-${resource.id}" class="items" data-opid="${resource.id}">
				<c:if test="${vr.index==0}"><input type="hidden" id="resourctotal" value="${itemcount}" style="width: 20px;"></c:if>
				<div class="col c1 fl" style="width: 50%;">
	                <span class="fl icon"><input type="checkbox"  name="ridchk" value="${resource.id}" class="chk fl"><img src="images/text.png"></span>
	               
	                <div class="name fl"><span title="${resource.name}"  <c:if test="${resource.type==2}">data-bigsrc="${resource.url}"</c:if> class="name-text">${resource.type}:${resource.name}</span></div>
	            </div>
	            <div class="col" style="width: 16%" title="字节大小:${resource.size}">${resource.sizeString}</div>
	            <div class="col" style="width: 23%;color:green">${tz:dateFormat(resource.createTime,'yyyy-MM-dd HH:mm:ss')}（${tz:dateString2(resource.createTime)}）</div>
	            <div class="col buttons" style="width: 10%">
	            	<a href="javascript:void(0);" class="edit"><img src='images/edit.png'></a>&nbsp;&nbsp;
<!-- 			    <a href="javascript:void(0);" data-opid="${resource.id}" onclick="tm_deleteresource(this);"><img src='images/delete.gif'></a> -->
	            	<a href="javascript:void(0);" data-opid="${resource.id}" class="delete"><img src='images/delete.gif'></a>
	            </div>
			</li>
		</c:forEach>
	</c:otherwise>	
</c:choose>