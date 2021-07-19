<%
/** Title:			channel_select.jsp
 *  Description:
 *		WCM5.2 页面，用于“对话框选择组织（单选/多选）”。
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
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

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
<title>TRS WCM 5.2 <%=LocaleServer.getString("group.label.select", "组织的选择")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_addedit.jsp中 --->

<%@include file="../include/public_client_normal.jsp"%>

<script>
	var m_bTransferName = <%=bTransferName%>;
	var m_arGroupId		= new Array();
	var m_arGroupName	= new Array();

	function init(){
		if(!window.top.getDialogArguments())
			return;
		var dialogArguments = window.top.getDialogArguments();
		if(!dialogArguments.split){//Array			
			if(m_bTransferName && dialogArguments.length<2){
				CTRSAction_alert("您传入的数据有误！\n应该为二维数组，第一个为用户组ID，第二个为用户组名");
				return;
			}			
			
			if(m_bTransferName){
				m_arGroupId =dialogArguments[0];
				m_arGroupName = dialogArguments[1];
			}
			else{
				m_arGroupId = dialogArguments;
				m_arGroupName = TRSArray.clone(m_arGroupId);
			}			
		}
		else{//String
			if(m_bTransferName){
				CTRSAction_alert("您传入的数据有误！\n应该为二维数组，第一个为用户组ID，第二个为用户组名");
				return;
			}
			m_arGroupId = dialogArguments.split(",");
			m_arGroupName = TRSArray.clone(m_arUserId);
		}		
	}

	init();

	function getGroupIds(){
		return m_arGroupId.join(",");
	}

	function getGroupNameByIds(_sIds){
		if(_sIds.length == 0)return "";

		var oAction = new CTRSAction("../auth/grpname_get_by_ids.jsp");
		oAction.setParameter("GroupIds", _sIds);
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
		} catch(NaN){}
		
		//根据ID获取名称
		if(m_bTransferName){
			if(sValue.length == 0){
				top.returnValue = [[], []];
			}else{
				var sGroupNames = getGroupNameByIds(sValue);
				// 防止sValue就一个单独的数字，不支持split，做如下处理
				sValue += "";

				top.returnValue = [sValue.split(","), sGroupNames.split(",")];
			}
		}else{
			top.returnValue = sValue;
		}
		top.close();
	}
</script>

</head>
<BODY style="margin:0px">	

<!--~== Dialog Head TABLE ==~-->
	<script src="../js/CWCMDialogHead.js"></script>
	<script>WCMDialogHead.draw("<%=LocaleServer.getString("group.label.select", "选择组织")%>");</script>
<!--~- END Dialog Head TABLE -~-->

		
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="86%" border="0" cellpadding="0" cellspacing="1" class="navigation_table"　id="idNavigationTable">
<!--~--- ROW3 ---~-->
<TR>
  <TD valign="top" bgcolor="white" height="98%">	
	<!--WCM 栏目树开始-->
	<script src="../js/CTRSTree.js"></script>
	<script src="../js/CWCMTreeDepends.js"></script>	
	<script src="../js/CTRSHTMLElement.js"></script>
	<script src="../js/CTRSTree_res_auth.js"></script>
	<script src="../auth/group_tree.jsp?<%=currRequestHelper.toURLParameters()%>&ShowAll=1"></script>
	<!--WCM 栏目树结束-->
  </TD>
</TR>
<!--~- END ROW3 -~-->
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
		oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "top.close();");
		
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