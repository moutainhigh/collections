<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.IImageLibConfig" %>
<%@ page import="com.trs.wcm.photo.IMagicImage" %>
<%@ page import="com.trs.wcm.photo.impl.MagicImageImpl" %>
<%@include file="/app/include/public_server.jsp"%>
<%
	IImageLibConfig oImageLibConfig = 
			(IImageLibConfig)DreamFactory.createObjectById("IImageLibConfig");
	IMagicImage oMagicImage = new MagicImageImpl();
	int[] arrScaleSizes = oImageLibConfig.getScaleSizes();
	int nDefaultIndex = 2;
	if(arrScaleSizes.length - 1 < nDefaultIndex) {
		nDefaultIndex = arrScaleSizes.length - 1;
	}
	out.clear();
%>
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE WCMAnt:param="fck_photolib.jsp.trswcmpicinserttopiclib">TRS WCM插入图片库图片</TITLE>
	<script src="../../../../../js/runtime/myext-debug.js"></script>
	<script src="../../../../../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../../../../js/data/locale/photo.js"></script>
	<script src="../../../../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../../../../js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../../../../../js/source/wcmlib/core/AuthServer.js"></script>
	<link rel="stylesheet" type="text/css" href="../../../../css/wcm-common.css">
	<!--wcm-dialog start-->
	<SCRIPT src="../../../../../js/source/wcmlib/Observable.js"></SCRIPT>
	<script src="../../../../../js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../../../../../js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<SCRIPT src="../../../../../js/source/wcmlib/Component.js"></SCRIPT>
	<SCRIPT src="../../../../../js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
	<SCRIPT src="../../../../../js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
	<!--wcm-dialog end-->
	<!--AJAX-->
	<script src="../../../../../js/data/locale/system.js"></script>
	<script src="../../../../../js/data/locale/ajax.js"></script>
<script src="../../../../../js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<!--CrashBoard-->
	<SCRIPT src="../../../../../js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
	<SCRIPT src="../../../../../js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
	<link href="../../../../../js/resource/widget.css" rel="stylesheet" type="text/css" />
	<!--Calendar-->
	<script language="javascript" src="../../../../../js/source/wcmlib/calendar/CTRSCalendar_Obj.js" type="text/javascript"></script>
	<script language="javascript" src="../../../../../js/source/wcmlib/calendar/calendar_lang/cn.js" type="text/javascript" WCMAnt:locale="../../../../../js/source/wcmlib/calendar/calendar_lang/$locale$.js"></script>
	<script language="javascript" src="../../../../../js/source/wcmlib/calendar/CTRSCalendar.js" type="text/javascript"></script>
	<script language="javascript" src="photolib.js" type="text/javascript"></script>
	<link href="photolib.css" rel="stylesheet" type="text/css" />
</HEAD>

<BODY style="margin:0;padding:0;">
		<table height="100%" width="100%">
			<tr>
				<td align="left" valign="top">
	<TABLE width="800" height="100%" border="0" cellpadding="0" cellspacing="0">
		<TR>
			<TD valign="top">
				<DIV id="docs_explorer" style="padding:4px;height:632px;width:800px;overflow:hidden;">
					<TABLE width="800" height="620" border="0" cellpadding="0" cellspacing="1" style="font-size:12px">
						<TR height="620">
							<TD width="196" valign="top" style="overflow:hidden;">
								<iframe src="../../../../../nav_tree/nav_tree_inner.html?siteTypes=1" scrolling="no" frameborder="0" style="height:450px;border:1px solid gray;overflow:hidden;"width="100%"></iframe>
								<div style="height:166px;margin-top:2px;border:1px solid gray;overflow:auto;width:196px;background:#FFF">
<%
	int nScaleSize = 0;
	String strScaleDesc = null;
	for(int i=0; i<arrScaleSizes.length; i++) {
		nScaleSize = arrScaleSizes[i];
		strScaleDesc = oImageLibConfig.getScaleDescAt(i);
%>
<input type="radio" value="<%=i%>" name="ScaleSize" <%=nDefaultIndex==i?"checked":""%> id="ScaleSize_<%=nScaleSize%>">
<label for="ScaleSize_<%=nScaleSize%>">
<%if(i == 0) {%>
	<%=strScaleDesc%>(<%=nScaleSize%> x <%=nScaleSize%>)
<%} else {%>
	<%=strScaleDesc%>(<%=nScaleSize%>)
<%}%>
</label><br>
<%
	}
%>
									<input type="checkbox" name="allReplace" id="allReplace" value="1"><label for="allReplace" WCMAnt:param="fck_photolib.jsp.localURL">使用本地路径</label><br/>
									<input type="checkbox" name="canlink" id="canlink" value="1" checked="true"><label for="canlink" WCMAnt:param="fck_photolib.jsp.canlink">点击图片可链接到原图</label>
								</div>
							</TD>
							<TD width="600">
								<iframe name="photo_list" id='photo_list' scrolling="no" frameborder="0" style="width:100%;height:100%;border:1px solid gray;" src="../../../../../photo/photo_list_editor.html?siteType=1"></iframe>
							</TD>
						</TR>
					</TABLE>
				</DIV>
			</TD>
		</TR>
	</TABLE>
				</td>
				<td width="10" align="center" valign="top">
					<div style="width:2px;border-left:1px solid gray;background:white;overflow:hidden;height:623px;"></div></td>
				<td width="130" valign="top" id="otherContainer">
					<input id="btnOk" class="input_btn" style="margin-left:20px;width:80px" onclick="onOk();"
						type="button" value="确定" fcklang="DlgBtnOK" WCMAnt:paramattr="value:fck_photolib.jsp.ok"/>
					<div style="height:5px;overflow:hidden"></div>
					<input name="button2" class="input_btn" style="margin-left:20px;width:80px" type="button" value="取消" WCMAnt:paramattr="value:fck_photolib.jsp.cancel" onclick="onCancel();" fcklang="DlgBtnCancel">
					<div style="height:6px;overflow:hidden"></div>
					<div style="height:2px;width:120px;border-top:1px solid gray;background:white;overflow:hidden;"></div>
					<div style="height:6px;overflow:hidden"></div>
					<span class="message" WCMAnt:param="fck_photolib.jsp.pictobeInsert">待插入的图片:</span><br>
					<div style="position:absolute;width:120px;height:540px;border:1px solid gray;background:#FFF;overflow:auto;overflow-x:hidden;" id="selected_photos">
					</div>
				</td>
			</tr>
		</table>
</BODY>
</HTML>