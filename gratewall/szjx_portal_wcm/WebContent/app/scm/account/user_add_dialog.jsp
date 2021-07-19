<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@include file="../../include/public_server.jsp"%>
<%
	String sUserIds = currRequestHelper.getString("UserIds");
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title></title>
  <script src="../../../js/CTRSArray.js"></script>
  <script language="javascript">
<!--
	var m_cbCfg = {
		btns : [
			{//绘制确定按钮
				text: '确定',
				cmd : function(){
					var sUserIds = getBackUserIds();
					if(sUserIds == ""){
						alert("请至少选择一个维护人员进行添加！");
						return false;
					}
					var paramsback={
						GroupId:m_params.SCMGroupId,
						UserIds:sUserIds
					}
					this.notify(paramsback);//执行回调方法
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
<script language="javascript">
	var m_params = null;
	var m_bTransferName = 0;
	var m_arUserId		= new Array();
	var m_UserIds = "<%=CMyString.filterForJs(sUserIds)%>";
	m_arUserId = m_UserIds.split(",");

	//页面加载时会触发该函数
	function g_init(params){
		//这里接受外围传入的参数		
		m_params = params;		
	}	
	
	function addUserId(_nUserId, _sUserName){
		var nIndex = m_arUserId.length;
		if(m_arUserId[0] == null || m_arUserId[0] == "null"){
			nIndex = 0;
		}		
		m_arUserId[nIndex] = _nUserId;		
	}
	
	function getBackUserIds(){
		return m_arUserId.join(",");
	}

	function getUserIds(){
		return m_UserIds;
	}

	function removeUserId(_nUserId, _sUserName){		
		TRSArray.remove(m_arUserId, _nUserId);		
	}	
</script>
</head>


<FRAMESET cols="200,*" frameborder="NO" border="0" framespacing="0">
	<FRAME src="../../auth/user_select_left.jsp?TreeType=0&FilterRight=0" name="left" scrolling="no" noresize>
	<FRAME src="../../auth/user_select_list.jsp?TransferName=1&AllUser=0&FilterRight=0" name="main" scrolling="no">
</FRAMESET>
<NOFRAMES>
<BODY>
</BODY>
</NOFRAMES>
</html>