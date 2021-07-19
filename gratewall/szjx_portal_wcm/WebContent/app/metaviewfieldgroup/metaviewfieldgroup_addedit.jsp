<%--
/** Title:			metaviewfieldgroup_addedit.jsp
 *  Description:
 *		MetaViewFieldGroup的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2011-06-11 03:19:07
 *  Vesion:			1.0
 *  Last EditTime:	2011-06-11 / 2011-06-11
 *	Update Logs:
 *		TRS WCM 5.2@2011-06-11 产生此文件
 *
 *  Parameters:
 *		see metaviewfieldgroup_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroup" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroups" %>
<%@ page import="com.trs.components.metadata.definition.IMetaViewFieldGroupMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroups" %>
<%@ page import="com.trs.components.metadata.definition.MetaView"%>
<%@ page import="com.trs.DreamFactory"%>


<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nMetaViewFieldGroupId = currRequestHelper.getInt("MetaViewFieldGroupId", 0);
	int nMetaViewId = currRequestHelper.getInt("MetaViewId", 0);
	MetaViewFieldGroup currMetaViewFieldGroup = null;
	int nParentGroupId = 0;
	if(nMetaViewFieldGroupId > 0){
		currMetaViewFieldGroup = MetaViewFieldGroup.findById(nMetaViewFieldGroupId);
		if(currMetaViewFieldGroup == null){
			throw new WCMException(CMyString.format(LocaleServer.getString("metaviewfieldgroup_addedit.jsp.fail2get_MetaViewFieldGroup", "获取ID为[{0}]的MetaViewFieldGroup失败！!"), new int[]{nMetaViewFieldGroupId}));
		}
		nMetaViewId = currMetaViewFieldGroup.getMetaViewId();
		nParentGroupId = currMetaViewFieldGroup.getParentId();
	}else{
		currMetaViewFieldGroup = MetaViewFieldGroup.createNewInstance();
	}
	if(nMetaViewId <= 0){
		 throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("metaviewfieldgroup_addedit.jsp.label.valueof_MetaViewId_error","参数【MetaViewId】的值必须大于0！"));
	}
	boolean bCanSelectParent = true;
	MetaViewFieldGroups oMetaViewFieldGroups = null;
	IMetaViewFieldGroupMgr groupMgr = (IMetaViewFieldGroupMgr)DreamFactory.createObjectById("IMetaViewFieldGroupMgr");
	//判断当前组是不是已经是别的组的父组，如果是，则不允许再给自己选择父组
	MetaViewFieldGroups childGroups = groupMgr.queryChildGroups(MetaView.findById(nMetaViewId), currMetaViewFieldGroup);
	if(currMetaViewFieldGroup.getId() == 0 || childGroups.size() == 0){
		oMetaViewFieldGroups = groupMgr.queryGroupsForSelectParent(loginUser,currMetaViewFieldGroup,nMetaViewId);	
	}
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="metaviewfieldgroup_addedit.jsp.title">TRS WCM 添加修改视图字段分类信息</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../js/easyversion/lightbase.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/data/locale\wcm52.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></script>
<%=currRequestHelper.toTRSRequestParam()%>
<!--Form 数据有效性的校验 BEGIN-->
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/TRSBase.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSValidator.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSValidator_res_default.js"></SCRIPT>
<!--Form 数据有效性的校验 END-->
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObjHelper.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObj.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSBitsValue.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMDialogHead.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
function unlockObj(_nObjId, _nObjType){
	var oTRSAction = new CTRSAction("../../include/object_unlock.jsp");
	oTRSAction.setParameter("ObjId", _nObjId);
	oTRSAction.setParameter("ObjType", _nObjType);
	oTRSAction.doXMLHttpAction();
	return;
}

function unlock(_nObjId, _nObjType){
	if(_nObjId>0) {
		unlockObj(_nObjId, _nObjType);
	}
}

</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
function submitForm(){
	var frmData = document.getElementById('frmData');
	if(!TRSValidator.validate(frmData)) {
		return;
	}
	if(groupNameExist(frmData.METAVIEWID.value,frmData.GROUPNAME.value)) {
		alert("该视图下已经存在同名的分组，请重新输入！");
		return;
	}
	WCMAction.doPost(frmData, document.frmAction);	
	
}
function groupNameExist(_metaViewId, _groupName){
	var oTRSAction = new CTRSAction("./metaviewfieldgroup_exist.jsp");
	oTRSAction.setParameter("MetaViewId",  _metaViewId);
	oTRSAction.setParameter("GroupName",  _groupName);
	oTRSAction.setParameter("MetaViewFieldGroupId",  <%=nMetaViewFieldGroupId%>);
	var sResult = oTRSAction.doXMLHttpAction();
	if(sResult == "true") {
		return true;
	}
	return false;
}
function resetForm(){
	var frm = document.getElementById('frmData');
	frm.reset();
}
</SCRIPT>

</HEAD>

<BODY>
<FORM NAME="frmAction" ID="frmAction" METHOD=POST
	ACTION="./metaviewfieldgroup_addedit_dowith.jsp" style="margin-top:0"><%=currRequestHelper.toHTMLHidden()%>
<INPUT TYPE="hidden" NAME="ObjectXML" Value=""></FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="0">
	<TR>
		<TD align="center" valign="top" class="tanchu_content_td">

		<FORM NAME="frmData" ID="frmData" onSubmit="return false;">
		<INPUT TYPE="hidden" name="MetaViewFieldGroupId" value="<%=nMetaViewFieldGroupId%>">
		<INPUT TYPE="hidden" name="METAVIEWID" value="<%=nMetaViewId%>">
		
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
			<TR>
				<TD class="tanchu_content">
				 
					<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
						<!-- 请注意在此修改相应字段名，addedit页面的字段名必须与该对象数据表字段名相对应 -->
						<TR>
							<TD width="80" align="left" WCMAnt:param="metaviewfieldgroup_addedit.jsp.classfy_name">分组名称：</TD>
							<TD><INPUT name="GROUPNAME" type="text" style="width:250px" WCMAnt:paramattr="elname:metaviewfieldgroup_addedit.jsp.classfy_name_attr"  elname="分组名称" pattern="string" not_null="1" max_len="50" value="<%=PageViewUtil.toHtml(currMetaViewFieldGroup.getGroupName())%>"></TD>
						</TR>
					<%
						if(oMetaViewFieldGroups != null){
					%>
						<TR>
							<TD width="80" align="left" WCMAnt:param="metaviewfieldgroup_addedit.jsp.select_father_classfy">选择父组：</TD>
							<TD><select name="parentId" id="parentId" value="<%=nParentGroupId%>" style="width:100px;">
							<option value=0></option>
							<%
								for(int i=0;i<oMetaViewFieldGroups.size();i++){
									MetaViewFieldGroup oMetaViewFieldGroup = (MetaViewFieldGroup)oMetaViewFieldGroups.getAt(i);
									String sGroupName = oMetaViewFieldGroup.getGroupName();
									int nGroupId = oMetaViewFieldGroup.getId();
							%>
								<option value="<%=nGroupId%>" <%=(nGroupId == nParentGroupId ? "selected" :"")%>><%=CMyString.transDisplay(sGroupName)%></option>
							<%
								}	
							%>
							</select></TD>
						</TR>
					<%
						}			
					%>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
					</TABLE>
		 
				</TD>
			</TR>
			<TR>
				<TD align="center">
				<script src="../js/wcm52/CTRSButton.js"></script> 
					<script>
						//定义一个TYPE_ROMANTIC_BUTTON按钮
						var oTRSButtons		= new CTRSButtons();

						oTRSButtons.cellSpacing	= "0";
						oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;

						oTRSButtons.addTRSButton("确定", "submitForm()");
						oTRSButtons.addTRSButton("重填", "resetForm()");
						oTRSButtons.addTRSButton("取消", "top.close();");
						oTRSButtons.draw();	
					</script>
				</TD>
			</TR>
		</TABLE>
		</FORM>
		</TD>
	</TR>
</TABLE>
</BODY>
</HTML>