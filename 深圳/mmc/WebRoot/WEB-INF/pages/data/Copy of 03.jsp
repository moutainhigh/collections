<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@include file="/pub/pub.jsp"%>
</head>
<body>
	<table>
		<tr>
			<td>pripid 主体身份代码CA14</td>
			<td>uniscid 统一信用代码</td>
			<td>entname 企业名笱</td>
			<td>regno 注册号</td>
			<!-- <td>enttype 市场主体类型</td>
			<td>enttype_cn 市场主体类型中文名称</td>
			<td>industryphy 行业门类</td>
			<td>industryco 行业代码</td>
			<td>estdate 成立日期</td>
			<td>regorg 登记机关CAaa</td>
			<td>regorg_cn 登机机关中文名称</td>
			<td>opscotype 业务范围</td>
			<td>opscotype_cn 业务范围中文</td> -->
			<!-- <td>opscope</td> -->
			<!-- <td>opfrom 经营期限自</td>
			<td>opto 经营期限至</td>
			<td>regstate</td>
			<td>regstate_cn</td>
			<td>domdistrict</td>
			<td>dom</td>
			<td>regcap</td>
			<td>regcapcur</td>
			<td>regcapcur_cn</td>
			<td>regcapusd</td>
			<td>reccap</td>
			<td>reccapusd</td>
			<td>country</td>
			<td>empnum</td>
			<td>town</td>
			<td>name</td>
			<td>reporttype</td>
			<td>apprdate</td>
			<td>s_ext_fromnode</td>
			<td>s_ext_datatime</td> -->
			<td>oldenttype 市场主体类型（金信）</td>
			<td>specflag</td>
		</tr>
		<c:forEach var="item" items="${list}" varStatus="status">
			<tr>
				<td>${item.pripid }</td>
				<td>${item.uniscid }</td>
				<td>${item.entname }</td>
				<td>${item.regno }</td>
				<%-- <td>${item.enttype }</td>
				<td>${item.enttype_cn }</td>
				<td>${item.industryphy }</td>
				<td>${item.industryco }</td>
				<td>${item.estdate }</td>
				<td>${item.regorg }</td>
				<td>${item.regorg_cn }</td>
				<td>${item.opscotype }</td>
				<td>${item.opscotype_cn }</td> --%>
				<%-- <td>${item.opscope }</td> --%>
				<%-- <td>${item.opfrom }</td>
				<td>${item.opto }</td>
				<td>${item.regstate }</td>
				<td>${item.regstate_cn }</td>
				<td>${item.domdistrict }</td>
				<td>${item.dom }</td>
				<td>${item.regcap}</td>
				<td>${item.regcapcur}</td>
				<td>${item.regcapcur_cn}</td>
				<td>${item.regcapusd}</td>
				<td>${item.reccap}</td>
				<td>${item.reccapusd}</td>
				<td>${item.country}</td>
				<td>${item.empnum}</td>
				<td>${item.town}</td>
				<td>${item.name}</td>
				<td>${item.reporttype}</td>
				<td>${item.apprdate}</td>
				<td>${item.s_ext_fromnode}</td>
				<td>${item.s_ext_datatime}</td> --%>
				<td>${item.oldenttype}</td>
				<td>${item.specflag}</td>
			</tr>
		</c:forEach>
	</table>