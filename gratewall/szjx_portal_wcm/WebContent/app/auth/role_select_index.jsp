<%
/** Title:			channel_select.jsp
 *  Description:
 *		WCM5.2 页面,用于“对话框选择角色(单选/多选)”.
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-13 15:11
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-13/2004-12-13
 *	Update Logs:
 *		CH@2004-12-13 created the file 
 *  //TODO 预先选择,树状输出
 *
 *  Parameters:
 *		see channel_select.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>

<%!boolean IS_DEBUG = true;%>
<%
//4.初始化(获取数据)	
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
<title></TITLE>
<LINK href="style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用,隐藏参数输出,都在public_client_addedit.jsp中 --->
<%@include file="../wcm52/include/public_client_normal.jsp"%>

<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/auth.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'onOK',
		name : wcm.LANG.AUTH_TRUE||'确定'
	}],
	size : [600, 400]
};
</script>

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
		} catch(NaN) {
			alert(NaN.message);
		}
		var returnValue = null;
		//根据ID获取名称
		if(m_bTransferName){
			if(sValue.length == 0){
				returnValue = [[], []];
			}else{
				var sRoleNames = getRoleNameByIds(sValue);
				returnValue = [sValue.split(","), sRoleNames.split(",")];
			}
		}else{
			returnValue = sValue;
		}
		notifyFPCallback(returnValue);
	}
</script>

</head>
<BODY style="margin:0px">			
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6">
<!--~--- ROW3 ---~-->
<TR>
  <TD valign="top" bgcolor="white" height="98%">	
	<!--WCM 栏目树开始-->
	<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
	<script src="../js/wcm52/CTRSTree.js"></script>
	<script src="../js/wcm52/CWCMTreeDepends.js"></script>	
	<script src="../js/wcm52/CTRSTree_res_auth.js"></script>
	<!--区别选择角色列表和组列表时的图标-->
	<script>
		Ext.apply(TREE_ICON_RES, {
			'icon_n_0'   : IMG_SRC_PREX + 'trstree_role.gif'
		});
	</script>
	<script src="../auth/role_tree.jsp?<%=CMyString.filterForHTMLValue(currRequestHelper.toURLParameters())%>"></script>
	<!--WCM 栏目树结束-->
  </TD>
</TR>
</TABLE>
</BODY>
</HTML>