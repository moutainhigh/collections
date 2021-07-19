<!DOCTYPE html><%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/sys-config/taglib/gw-tag.tld" prefix="gw"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="WebRoot" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>统计分析-列表</title>
<link rel="stylesheet" type="text/css"
	href="<c:out value="${WebRoot}"/>/framework/component/ext2.0/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"
	href="<c:out value="${WebRoot}"/>/gwaic/css/style/default/gwssi.css" />
<script type="text/javascript"
	src="<c:out value="${WebRoot}"/>/gwaic/script/style/default/gview.js"></script>
<script type="text/javascript"
	src="<c:out value="${WebRoot}"/>/framework/script/validate/validator.js"></script>
<!--  
<script language="JavaScript" type='text/javascript'
	src='<c:out value='${WebRoot}'/>/ajax/engine.js'></script>
-->	
<script type="text/javascript"
	src="<c:out value="${WebRoot}"/>/gwaic/script/dialog.js"></script>
<script type="text/javascript"
	src="<c:out value="${WebRoot}"/>/page/report/jquery-1.8.3-min.js"></script>
<style type="text/css">
body {
	margin-left: 5px;
	margin-top: 5px;
	margin-right: 5px;
}

.STYLE1 {
	color: #FF0000
}
</style>
<script type="text/javascript">
$(function(){
	
	queryReportList();
});

function queryReportList(){
	$.ajax({
        url: "../../../report/queryReportList.do",
        // 数据发送方式
        type: "post",
        // 接受数据格式
        dataType : "json",
        // 要传递的数据
        data :{
        	
        },
        async:false,
        // 回调函数，接受服务器端返回给客户端的值，即result值
        success :function (result){
        	
        }
	});
	
}
function getParameter(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r!=null) { 
		return unescape(r[2]); 
	} 
	return ''; 
}

function goQuery() {
	 var cp = window.getElement("currentPage");
	 cp.value="1";
	 showLoading();
	 $("#queryReportForm").submit();
}
</script>
</head>
<body>
<c:out value="${WebRoot}"/>
	<form id="queryReportForm" name="reportListForm" method="post" >
	<div id="msg" style="position:absolute;display:none;top:0px;left:0px;width:100%;height:100%;background-color:#bbbbbb;filter:Alpha(opacity=80);">
	</div>	
 <table cellspacing='0' cellpadding='0' width='100%' height="99%" border="0" align='center'>
  <tr>
    <td width="2" class="gridTopLeft"></td>
    <td width="100%" class="gridTopCenter">
   		<table cellspacing='0' cellpadding='0' width='100%' border="0">
        <tr>
        	<td width="30" nowrap align="center"><img src="<c:out value="${WebRoot}"/>/gwaic/images/icon/application_view_list.png" align="absmiddle"></td>
        	<td width="100%" class="titleFont">查询条件</td>
        	<td width="10%" nowrap><img src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridHidenButton.gif" align="absmiddle" style="cursor:hand" >&nbsp;&nbsp;</td>
        </tr>
      </table>
    </td>
    <td width="2" height="25" class="gridTopRight"></td>
  </tr> 	
  <tr>
    <td width="2" class="gridMiddleLeft"></td>
    <td width="*" class="gridBackgroundColor" valign="top">
    	<table cellspacing='0' cellpadding='0' width='100%' height="100%" border="0">
    		 <tr height="0">
    		 	  <td colspan=3></td>
    		 </tr>
         <tr height="*">
        	  <td width="4"></td>
        	  <td valign="top">
		<table id="gridContent" cellspacing='1' cellpadding='0' width='100%'
			height="100%" border="0" class="gridBorderColor">
			<tr id="queryContent">
				<td valign="top">
					<table cellspacing='0' cellpadding='0' width='100%' border="0">
						<tr>
							<td class="gridWhiteColor" height="40" valign="top">
								<input name="reportCategory" id="reportCategory" value='<c:out value="${reportCategory}" />' type='hidden' class="x-form-text x-form-field" style="width: 190px" />
								<table align="center" cellspacing='0' cellpadding='0'
									width="100%" border=0 style="margin-left:  145px">
									<tr>
										<td width='q0%' align="right" nowrap="nowrap">报表名称：</td>
										<td width='23%'>
											<input name="reportName" id="reportName" value='<c:out value="${reportName}" />' class="x-form-text x-form-field" style="width: 190px" />
										</td>
										<td width='10%' align="right" nowrap="nowrap">说明：</td>
										<td width='23%'>
											<input name="reportRemark" id="reportRemark"  value='<c:out value="${reportRemark}" />' class="x-form-text x-form-field" style="width: 190px" />
										</td>
										<td width='10%' align="right" nowrap="nowrap">&nbsp;</td>
										<td width='23%'>&nbsp;</td>
									</tr>
	
								</table>
							</td>
						</tr>
						<tr>
							<td height="1"></td>
						</tr>
						<tr>
							<td class="gridWhiteColor" height="32">
								<table cellspacing="0" cellpadding="0" border="0" align="center">
									<tr>
										<td><input type="button" class="defaultButton" value="查询"
											onClick="goQuery()">&nbsp;&nbsp;</td>
										<td>&nbsp;&nbsp;<input type="reset" class="defaultButton"
											value="重填" ></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
				<%-- <tr>
					<td class="gridButtonBack">
						 <table cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td>&nbsp;<input type="button" class="defaultButton"
									value="新增"
									onClick="window.location.href='<c:out value="${WebRoot}"/>/report/reportManagerAction.do?method=queryReport'"></td>
							</tr>
						</table> 
					</td>
				</tr> --%>
				<tr height="*">
				<td class="gridWhiteColor" valign="top">
					<table id="gridContent" cellspacing='1' cellpadding='0'
						height='100%' width='100%' border="0">
						<tr>
							<td width="2"><img
								src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnSplit.gif"></td>
							<td width="40" align="center"
								background="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnBack.gif">&nbsp;序号</td>
							<td width="2"><img
								src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnSplit.gif"></td>
							<td width="300"
								background="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnBack.gif">&nbsp;报表名称</td>
							<td width="2"><img
								src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnSplit.gif"></td>
							<td width="400"
								background="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnBack.gif">&nbsp;说明</td>
							<td width="2"><img
								src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnSplit.gif"></td>
							<td width="100"
								background="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnBack.gif">&nbsp;状态</td>
							<td width="2"><img
								src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnSplit.gif"></td> 
							<td width="300"
								background="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnBack.gif">&nbsp;操作</td>
							<td width="2"><img
								src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridColumnSplit.gif"></td>
						</tr>
						<% int i=1; %>
						<%
							out.println("查看列表返回情况：");
							out.println(request.getAttribute("RESULT"));
						%>
						<c:forEach var="item" items="${RESULT.content}" varStatus="status">
							
							<tr onMouseOver="onMouseOverRow('gridTr<%=i%>')"
								onMouseOut="onMouseOutRow('gridTr<%=i%>')"
								onClick="onMouseClickRow('gridTr<%=i%>')" id="gridTr<%=i%>">
								
								<td><img src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridRowSplit.gif"></td>
								<td align="center" background="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridRowBack.gif">
									<%=i%>
								<td><img src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridRowSplit.gif"></td>
								<td nowrap>&nbsp;<c:out value="${item[2]}" />
								</td>
								<td><img src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridRowSplit.gif"></td>
								<td nowrap>&nbsp;<c:out value="${item[5]}" />
								</td>
								<td><img src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridRowSplit.gif"></td>
							 	<td align="center" nowrap>&nbsp;<c:out value="${item[3]}" />
								</td>
								<td><img src="<c:out value="${WebRoot}"/>/gwaic/images/style/default/grid/gridRowSplit.gif"></td>
								 <td nowrap align="center">&nbsp;
									

									<a href="<c:out value="${WebRoot}"/>/page/report/show.html?code=<c:out value="${item[1]}" />">查看</a>&nbsp;&nbsp;
									<%-- <c:if test="${item[4] eq '0'}">
										<a href="javascript:void(0)" onclick="fav('<c:out value="${item[0]}" />','1');return false;">收藏</a>
									</c:if> 
									<c:if test="${item[4] eq '1'}">
										<a href="javascript:void(0)" onclick="fav('<c:out value="${item[0]}" />','-1');return false;">取消收藏</a>
									</c:if>  --%>
								</td>
								
							</tr>
							<%
								i++;
							%>
						</c:forEach>
						<tr height="*">
							<td colspan="14" class="gridRowBox">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="gridButtonBack" colspan="14"><c:import
						url="/framework/component/scrollpage/defaultScrollBar.jsp"
						charEncoding="UTF-8">
						<c:param name="action"
							value="/report/reportListAction.do" />
						<c:param name="method" value="queryReportList" />
						<c:param name="formName" value="queryReportForm" />
						<c:param name="otherParam" value="&reportCategory=${param.reportCategory }" />
					</c:import>
				</td>
			</tr>
		</table>
  </td>
        	  <td width="4"></td>
    		 </tr>
      </table>





    </td>
    
	<td width="2" class="gridMiddleRight"></td>
  </tr>
  <tr height="0">
    <td width="2" class="gridMiddleLeft"></td>
    <td width="*" class="gridBackgroundColor"></td>
    <td width="2" class="gridMiddleRight"></td>
  </tr>
  <tr height="2">
    <td width="2" class="gridBottomLeft"><img width="2" height="0"></td>
    <td width="*" class="gridBottomCenter"></td>
    <td width="2" class="gridBottomRight"><img width="2" height="0"></td>
  </tr> 
 </table>
	</form>
</body>
</html>
