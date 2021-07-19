<%--
/** Title:			contentlinktype_addedit.jsp
 *  Description:
 *		ContentLinkType的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WenYehui
 *  Created:		2006-12-11 13:13:09
 *  Vesion:			1.0
 *  Last EditTime:	2006-12-11 / 2006-12-11
 *	Update Logs:
 *		WenYehui@2006-12-11 产生此文件
 *		WenYehui@2007-02-27 添加校验:类型名称/描述只能由普通字符组成.(只做客户端校验)
 *
 *  Parameters:
 *		see contentlinktype_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkType" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkTypes" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nContentLinkTypeId = currRequestHelper.getInt("ContentLinkTypeId", 0);
	ContentLinkType currContentLinkType = null;
	if(nContentLinkTypeId > 0){
		currContentLinkType = ContentLinkType.findById(nContentLinkTypeId);
		if(currContentLinkType == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nContentLinkTypeId+"]的热词分类失败！");
		}
	}else{//nContentLinkTypeId==0 create a new group
		currContentLinkType = ContentLinkType.createNewInstance();
	}
//5.权限校验

//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 添加修改热词分类</TITLE>
<BASE TARGET="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function submitForm(){		
	var pattern = /^[\w\u4e00-\u9fa5]*$/;

	var typeName = $("TYPENAME");	
	var value = typeName.value;
	
	if(!pattern.test(value)){
		CTRSAction_alert("类型名称只能由数字、字母和下划线组成！");		
		focusElement(typeName);
		return false;
	}

	if(exists(value)){
		CTRSAction_alert("类型["+value+"]已存在！");
		focusElement(typeName);
		return false;
	}

	var typeDesc = $("TYPEDESC");
	value = typeDesc.value;
	if(!pattern.test(value)){
		CTRSAction_alert("类型描述只能由数字、字母和下划线组成！");
		focusElement(typeDesc);
		return false;
	}

	var frmData = document.frmData;
	WCMAction.doPost(frmData, document.frmAction);	
}

function focusElement(_element){
	_element.focus();
	if(_element.select){
		_element.select();
	}
}

function exists(_name){	
	var name = _name || '';
	if(name.length <=0) return false;

	var checkAction = new CTRSAction("contentlinktype_get_byname.jsp");
	checkAction.setParameter("TypeName",name);
	checkAction.setParameter("TypeId",<%=nContentLinkTypeId%>);
	var result = checkAction.doXMLHttpAction();	

	eval("var val=" + result);	
	return (true == val.Result);
}

function resetForm(){
	var frm = document.all("frmData");
	frm.reset();
}
</SCRIPT>

</HEAD>

<BODY>
<FORM NAME="frmAction" ID="frmAction" METHOD="POST"
	ACTION="./contentlinktype_addedit_dowith.jsp" style="margin-top:0"><%=currRequestHelper.toHTMLHidden()%>
<INPUT TYPE="hidden" NAME="ObjectXML" Value=""></FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="0">
	<TR>
		<TD height="25"><SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT> <SCRIPT
			LANGUAGE="JavaScript">
		WCMDialogHead.draw("添加/修改热词分类");
	</SCRIPT></TD>
	</TR>
	<TR>
		<TD align="center" valign="top" class="tanchu_content_td">

		<FORM NAME="frmData" ID="frmData" onSubmit="return false;"><INPUT
			TYPE="hidden" name="ContentLinkTypeId" value="<%=nContentLinkTypeId%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
			<TR>
				<TD class="tanchu_content">
				<fieldset style="padding:5px"><legend><b>[基本属性]</b></legend>
					<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
						<!-- 请注意在此修改相应字段名，addedit页面的字段名必须与该对象数据表字段名相对应 -->
						<TR>
							<TD width="60" align="left">分类名称：</TD>
							<TD><INPUT name="TYPENAME" type="text" style="width:250px" elname="分类名称属性" pattern="string" not_null="1" max_len="20" value="<%=PageViewUtil.toHtml(currContentLinkType.getTypeName())%>"><span style="color:red;padding-left:4px">*</span></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						<TR>
							<TD width="60" align="left">分类描述：</TD>
							<TD><INPUT name="TYPEDESC" type="text" style="width:250px" elname="分类描述属性" pattern="string" not_null="1" max_len="100" value="<%=PageViewUtil.toHtml(currContentLinkType.getTypeDesc())%>"><span style="color:red;padding-left:4px">*</span></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
					</TABLE>
				</fieldset>
				</TD>
			</TR>
			<TR>
				<TD align="center">
				<script src="../js/CTRSButton.js"></script> 
					<script>
						//定义一个TYPE_ROMANTIC_BUTTON按钮
						var oTRSButtons		= new CTRSButtons();
						
						oTRSButtons.cellSpacing	= "0";
						oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;

						oTRSButtons.addTRSButton("确定", "submitForm()");
						//oTRSButtons.addTRSButton("重填", "resetForm()");
						oTRSButtons.addTRSButton("取消", "window.close();");
						
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