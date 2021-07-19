<%--
/** Title:			keyword_addedit.jsp
 *  Description:
 *		Keyword的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2009-06-23 13:44:49
 *  Vesion:			1.0
 *  Last EditTime:	2009-06-23 / 2009-06-23
 *	Update Logs:
 *		TRS WCM 5.2@2009-06-23 产生此文件
 *
 *  Parameters:
 *		see keyword_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Keyword" %>
<%@ page import="com.trs.components.wcm.resource.Keywords" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nKeywordId = currRequestHelper.getInt("KeywordId", 0);
	Keyword currKeyword = null;
	if(nKeywordId > 0){
		currKeyword = Keyword.findById(nKeywordId);
		if(currKeyword == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("keyword_addedit.key.getFailed","获取ID为[{0}]的Keyword失败！"),new int[]{nKeywordId}));
		}
	}else{//nKeywordId==0 create a new group
		currKeyword = Keyword.createNewInstance();
	}
//5.权限校验

//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="keyword_addedit.jsp.addmodify">TRS WCM 5.2 添加修改Keyword</TITLE>
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
<script type="text/javascript" src="../js/CTRSCalendar_Obj.js"></script>
<script type="text/javascript" src="../js/CTRSCalendar.js"></script>
<script type="text/javascript" src="../js/calendar_lang/cn_utf8.js"></script>
<style type="text/css"> @import url("../js/calendar_style/calendar-blue.css"); </style>
</HEAD>

<BODY>
<FORM NAME="frmAction" ID="frmAction" METHOD=POST
	ACTION="./keyword_addedit_dowith.jsp" style="margin-top:0"><%=currRequestHelper.toHTMLHidden()%>
<INPUT TYPE="hidden" NAME="ObjectXML" Value=""></FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="0">
	<TR>
		<TD height="25"><SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT> <SCRIPT
			LANGUAGE="JavaScript">
		WCMDialogHead.draw(wcm.LANG.keyword_addedit_1000 || "添加/修改Keyword");
	</SCRIPT></TD>
	</TR>
	<TR>
		<TD align="center" valign="top" class="tanchu_content_td">

		<FORM NAME="frmData" ID="frmData" onSubmit="return false;"><INPUT
			TYPE="hidden" name="KeywordId" value="<%=nKeywordId%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
			<TR>
				<TD class="tanchu_content">
				<fieldset style="padding:5px"><legend><b WCMAnt:param="keyword_addedit.jsp.property">[基本属性]</b></legend>
					<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
						<!-- 请注意在此修改相应字段名，addedit页面的字段名必须与该对象数据表字段名相对应 -->
						
						<TR>
							<TD width="60" align="left">ID：</TD>
							<TD><INPUT name="KID" type="text" style="width:100px" elname="ID属性" WCMAnt:paramattr="elname:keyword_addedit.jsp.idproperty" pattern="integer" not_null="1" value="<%=currKeyword.getId()%>"></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						

						<TR>
							<TD width="60" align="left" WCMAnt:param="keyword_addedit.jsp.keywordname">关键词名称：</TD>
							<TD><INPUT name="KNAME" type="text" style="width:250px" elname="关键词名称属性" WCMAnt:paramattr="elname:keyword_addedit.jsp.keywordproperty" pattern="string" not_null="1" value="<%=PageViewUtil.toHtml(currKeyword.getKNAME())%>"></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						

						<TR>
							<TD width="60" align="left" WCMAnt:param="keyword_addedit.jsp.insite">所属站点：</TD>
							<TD><INPUT name="SITEID" type="text" style="width:100px" elname="所属站点属性" WCMAnt:paramattr="elname:keyword_addedit.jsp.siteproperty" pattern="integer" not_null="1" value="<%=currKeyword.getSITEID()%>"></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						

						<TR>
							<TD width="60" align="left" WCMAnt:param="keyword_addedit.jsp.usefrequency">使用频率：</TD>
							<TD><INPUT name="KFREQ" type="text" style="width:100px" elname="使用频率属性" WCMAnt:paramattr="elname:keyword_addedit.jsp.useproperty" pattern="integer" not_null="1" value="<%=currKeyword.getKFREQ()%>"></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						

						<TR>
							<TD width="60" align="left" WCMAnt:param="keyword_addedit.jsp.creator">创建者：</TD>
							<TD><INPUT name="CRUSER" type="text" style="width:250px" elname="创建者属性" WCMAnt:paramattr="elname:keyword_addedit.jsp.creatorproperty" pattern="string" not_null="1" value="<%=PageViewUtil.toHtml(currKeyword.getCrUserName())%>"></TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
						</TR>
						

						<TR>
							<TD width="60" align="left" WCMAnt:param="keyword_addedit.jsp.creattime">创建时间：</TD>
							<TD><SCRIPT LANGUAGE="JavaScript">
<!--
TRSCalendar.drawWithoutTime("CRTIME", "<%=(currKeyword.getCrTime() != null ? currKeyword.getCrTime().toString("yyyy-MM-dd") : com.trs.infra.util.CMyDateTime.now().toString("yyyy-MM-dd"))%>");
//-->
</SCRIPT></TD>
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

						oTRSButtons.addTRSButton(wcm.LANG.keyword_addedit_2000 || "确定", "submitForm()");
						oTRSButtons.addTRSButton(wcm.LANG.keyword_addedit_3000 || "重填", "resetForm()");
						oTRSButtons.addTRSButton(wcm.LANG.keyword_addedit_4000 || "取消", "window.close();");
						
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