<%--
/** Title:			logo_addedit.jsp
 *  Description:
 *		Logo的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2006-03-28 19:56:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-28 / 2006-03-28
 *	Update Logs:
 *		TRS WCM 5.2@2006-03-28 产生此文件
 *
 *  Parameters:
 *		see logo_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.publish.logo.Logo" %>
<%@ page import="com.trs.components.wcm.publish.logo.Logos" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nLogoId = currRequestHelper.getInt("LogoId", 0);
	Logo currLogo = null;
	if(nLogoId > 0){
		currLogo = Logo.findById(nLogoId);
		if(currLogo == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nLogoId+"]的Logo失败！");
		}
	}else{//nLogoId==0 create a new group
		currLogo = Logo.createNewInstance();
	}
//5.权限校验

//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 添加修改Logo</TITLE>
<BASE TARGET="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function submitForm(){
	var frmData = document.frmData;
	WCMAction.doPost(frmData, document.frmAction);	
}

function resetForm(){
	var frm = document.all("frmData");
	frm.reset();
}
</SCRIPT>

</HEAD>

<BODY>
<FORM NAME="frmAction" ID="frmAction" METHOD=POST
	ACTION="./logo_addedit_dowith.jsp" style="margin-top:0"><%=currRequestHelper.toHTMLHidden()%>
<INPUT TYPE="hidden" NAME="ObjectXML" Value=""></FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="0">
	<TR>
		<TD height="25"><SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT> <SCRIPT
			LANGUAGE="JavaScript">
		WCMDialogHead.draw("添加/修改Logo");
	</SCRIPT></TD>
	</TR>
	<TR>
		<TD align="center" valign="top" class="tanchu_content_td">

		<FORM NAME="frmData" ID="frmData" onSubmit="return false;"><INPUT
			TYPE="hidden" name="LogoId" value="<%=nLogoId%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
			<TR>
				<TD class="tanchu_content">
				<fieldset style="padding:5px"><legend><b>[基本属性]</b></legend>
					<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
						<!-- 请注意在此修改相应字段名，addedit页面的字段名必须与该对象数据表字段名相对应 -->
						
						<TR>
							<TD width="60" align="left">所属对象类型：</TD>
							<TD><INPUT name="HOSTTYPE" type="text" style="width:100px" elname="所属对象类型属性" pattern="integer" not_null="1" value="<%=currLogo.getHostType()%>"></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						

						<TR>
							<TD width="60" align="left">所属对象ID：</TD>
							<TD><INPUT name="HOSTID" type="text" style="width:100px" elname="所属对象ID属性" pattern="integer" not_null="1" value="<%=currLogo.getHostId()%>"></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						

						<TR>
							<TD width="60" align="left">Logo文件名：</TD>
							<TD><INPUT name="FILENAME" type="text" style="width:250px" elname="Logo文件名属性" pattern="string" not_null="1" value="<%=PageViewUtil.toHtml(currLogo.getFileName())%>"></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						

						<TR>
							<TD width="60" align="left">排列序号：</TD>
							<TD><INPUT name="LOGOORDER" type="text" style="width:100px" elname="排列序号属性" pattern="integer" not_null="0" value="<%=currLogo.getLogoOrder()%>"><span class="font_red">*</span></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						

						<TR>
							<TD width="60" align="left">是否更新过：</TD>
							<TD><INPUT id="UPDATED" name="UPDATED" type="checkbox" elname="是否更新过属性" IsBoolean="1">
 <script>
 // 初始化显示checkbox
 function init(){
 // 是否是否更新过
 var hasAdItems = document.getElementById("UPDATED");
 hasAdItems.checked = <%=currLogo.isUpdated()%> ;
 }
 window.onload = init;
 </script></TD>
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
						oTRSButtons.addTRSButton("重填", "resetForm()");
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