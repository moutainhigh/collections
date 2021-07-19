<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.HashMap" %>
<%@ include file="../../include/public_server.jsp"%>
<%
	String sGroupName = currRequestHelper.getString("GroupNames");
	String[] pGroupName = sGroupName.split(",");
	String sGroupIds = currRequestHelper.getString("GroupIds");
	String[] pGroupId = sGroupIds.split(",");
%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
	<link rel="stylesheet" type="text/css" href="../css/select_has_right_padding.css" />	
	<script src="../js/jquery-1.7.2.min.js"></script>
	<script src="../js/scm_common.js"></script>
	<script src="../js/jquery.ui.select.js"></script>
	<script src="../js/iframe_public.js"></script>
	<script src="configure_workflow.js"></script>
	<script>
		$(function(){
			$(".groupSelect").sSelect();
		});
	</script>
</head>
<body>
	<%if(pGroupName[0].equals("null")){%>
		您管理的所有分组均已配置了审核工作流。
	<%}else{%>
	<div style="font-size:14px;" id="createGroup">分组名称：
		<%
			for(int i = 0; i < pGroupName.length; i++){
				if(CMyString.isEmpty(pGroupName[i])){
					continue;
				}
		%>
				<input type="radio" name="groupId" value="<%=pGroupId[i]%>"><%=CMyString.transDisplay(pGroupName[i])%></option>
		<%
			}
		}
		%>
	</div>
	<div class="clearFloat"></div>
<script language="javascript">
<!--
	var m_cbCfg = {
		btns : [
			{//绘制确定按钮
				text: '确定',
				cmd : function(){
					if($("#createGroup").find("input:checked").val()!="undefined"){
					var jasonParams={GroupId:$("#createGroup").find("input:checked").val()};
					this.notify(jasonParams);
					}else{
						alert("请选择一个分组进行工作流配置！");
						return false;
					}
				}
			},
			{//绘制取消按钮
				text: '取消',
				cmd : function(){
					var params={
						triggerClose : 1,//为1是触发关闭事件,为0不触发关闭事件
						param1 : "value1"
					};
					this.close(params);
				}
			}
		]
	};
//-->
</script>
</body>
</html>