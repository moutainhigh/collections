<%
/** Title:			channel_select.jsp
 *  Description:
 *		WCM5.2 页面，用于“对话框选择角色（单选/多选）”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-13 15:11
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-13/2004-12-13
 *	Update Logs:
 *		CH@2004-12-13 created the file 
 *  //TODO 预先选择、树状输出
 *
 *  Parameters:
 *		see channel_select.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>

<%!boolean IS_DEBUG = true;%>
<%
//4.初始化（获取数据）	
	int nTransferName = currRequestHelper.getInt("TransferName", 0);
	boolean bTransferName = nTransferName>0;
	

//5.权限校验

//6.业务代码	
	

//7.结束
	out.clear();
%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2 <%=LocaleServer.getString("role.label.select", "角色的选择")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_addedit.jsp中 --->

<%@include file="../include/public_client_normal.jsp"%>

<script>
	var m_bTransferName = <%=bTransferName%>;

	function getRoleNameByIds(_sIds){
		if(_sIds.length == 0)return "";

		var oAction = new CTRSAction("../auth/rolename_get_by_ids.jsp");
		oAction.setParameter("RoleIds", _sIds);
		return oAction.getRemoteData();
	}

	function onOK(){
		var sValue = "";
		try {
			if(oTRSTree.nType == 1){//多选
				sValue = oTRSTree.getCheckValue();
			}else{
				sValue = oTRSTree.getRadioValue();
			}
		} catch(NaN) {}
		
		//根据ID获取名称
		if(m_bTransferName){
			if(sValue.length == 0){
				window.returnValue = [[], []];
			}else{
				var sRoleNames = getRoleNameByIds(sValue);				
				window.returnValue = [sValue.split(","), sRoleNames.split(",")];
			}
		}else{
			window.returnValue = sValue;
		}
		
		//将数组调整成原始窗口的数组，避免对象已经释放的脚步错误
		window.returnValue = adapterArray(window.returnValue);
		window.close();
	}

	function adapterArray(array){
		try{
			var win = top.dialogArguments[3];
			if(!win) return array;
			return adapterArray0(win, new win.Array(), array);
		}catch(error){
			alert(error.message);
		}
	}

	function adapterArray0(win, src, dest){
		for (var i = 0; i < dest.length; i++){
			if(dest[i] instanceof Array){
				src[i] = new win.Array();
				adapterArray0(win, src[i], dest[i]);
			}else{
				src.push(dest[i]);
			}
		}
		return src;
	}
</script>

</head>
<BODY style="margin:0px">	

<!--~== Dialog Head TABLE ==~-->
	<script src="../js/CWCMDialogHead.js"></script>
	<script>WCMDialogHead.draw("<%=LocaleServer.getString("role.label.select", "选择角色")%>");</script>
<!--~- END Dialog Head TABLE -~-->

		
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="86%" border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6">

<!--~--- ROW3 ---~-->
<TR>
  <TD valign="top" bgcolor="white" height="98%">	
	<!--WCM 栏目树开始-->
	<script src="../js/CTRSTree.js"></script>
	<script src="../js/CWCMTreeDepends.js"></script>
	<script src="../js/CTRSTree_res_auth.js"></script>
	<script src="../auth/role_tree.jsp?<%=CMyString.filterForHTMLValue(currRequestHelper.toURLParameters())%>"></script>
	<!--WCM 栏目树结束-->
  </TD>
</TR>
</TABLE>
<TABLE width="100%"  border="0" cellpadding="0" cellspacing="1" >
<!--~--- ROW3 ---~-->
<TR>
  <TD valign="top"  height=26 align=center>
	<script src="../js/CTRSButton.js"></script>
	<script>
		//定义一个TYPE_ROMANTIC_BUTTON按钮
		var oTRSButtons = new CTRSButtons();
		
		oTRSButtons.cellSpacing	= "0";
		oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;

		oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确定")%>", "onOK();");
		oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "window.close();");
		
		oTRSButtons.draw();
	</script>
  </TD>
</TR>
<!--~- END ROW3 -~-->

</TABLE>
</BODY>
</HTML>
<%
//6.资源释放

%>