<%
/** Title:				view_system_skin_addedit.jsp
 *  Description:
 *        风格的编辑/新建页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:				Archer
 *  Created:			2010年3月25日
 *  Vesion:				1.0
 *  Last EditTime	:none
 *  Update Logs:	none
 *  Parameters:		see view_system_skin_addedit.xml
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page import="com.trs.components.common.publish.widget.PageStyle"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%
	//获取参数
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
	
	// 判断当前是编辑还是新建
	boolean bIsEdit = true;
	if(nPageStyleId==0){
		bIsEdit = false;
	}

	// 获取对象
	PageStyle oPageStyle = null;
	boolean bHasRight = false;
	if(bIsEdit){// 编辑
		// 获取 风格（Id:nPageStyleId） 对象
		oPageStyle = PageStyle.findById(nPageStyleId);
		if(oPageStyle == null){
			throw new WCMException(CMyString.format(LocaleServer.getString("pagestyle_addedit.jsp.fail2get_style", "获取风格[Id:{0}]失败！"), new int[]{nPageStyleId}));
		}
		bHasRight = SpecialAuthServer.hasRight(loginUser, oPageStyle, SpecialAuthServer.STYLE_EDIT);
		if (!bHasRight) {
			throw new WCMException(CMyString.format(LocaleServer.getString("pagestyle_addedit.jsp.have_noright2_alter_style", "您没有权限修改风格【{0}】！"), new String[]{oPageStyle.getStyleDesc()}));
		}
	}else{// 新建 创建新的对象
		oPageStyle = new PageStyle();
		bHasRight = SpecialAuthServer.hasRight(loginUser, oPageStyle, SpecialAuthServer.STYLE_ADD);
		if (!bHasRight) {
			throw new WCMException(LocaleServer.getString("pagestyle_addedit.jsp.have_noright2_create_style", "您没有权限新建风格！"));
		}
	}
	String sPageStyleName = CMyString.showNull(oPageStyle.getStyleName(),"");
	String sPageStyleDesc = CMyString.showNull(oPageStyle.getStyleDesc(),"");
	out.clear();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><%=bIsEdit?(LocaleServer.getString("pagestyle_addedit.jsp.edit_style","编辑风格")):(LocaleServer.getString("pagestyle_addedit.jsp.new_style","新建风格"))%></title>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
	<link href="./pagestyle_addedit.css" rel="stylesheet" type="text/css" />
	<!--基础的js-->
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<!--使用ajax发送请求的js-->
	<script src="../../js/easyversion/ajax.js"></script>
	<script src="../../js/easyversion/basicdatahelper.js"></script>
	<script src="../../js/easyversion/web2frameadapter.js"></script>
	<!--validator start-->
	<script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<!--validator end-->
	<script src="pagestyle_addedit.js"></script>
</head>
<body>
<form id="frm">
	<table border=0 cellspacing=0 cellpadding=0 class="info_content">
		<tr style="display:none">
			<td class="title_td" WCMAnt:param="pagestyle_addedit.jsp.styleId">风格ID：</td><td ><input type="text" name="ObjectId" id="ObjectId" value="<%=nPageStyleId%>"></td>
		</tr>
		<tr height="5"></tr>
		<tr>
			<td class="title_td"  WCMAnt:param="pagestyle_addedit.jsp.display_name">显示名称：</td>
			<td >
				<input type="text" name="StyleDesc" id="StyleDesc" DescCanUsed="false" validation="desc:'名称',required:true,type:'string',showid:'desc_msg',max_len:50,rpc:'checkStyleDesc'" value="<%=CMyString.filterForHTMLValue(sPageStyleDesc)%>" validation_desc="名称"  WCMAnt:paramattr="validation_desc:pagestyle_addedit.jsp.name"/>
				<div id="desc_msg" class="msg">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td class="title_td"  WCMAnt:param="pagestyle_addedit.jsp.en_name_full">英文名称：</td>
			<td >
				<input type="text" name="StyleName" id="StyleName" validation_desc="英文名"  WCMAnt:paramattr="validation_desc:pagestyle_addedit.jsp.en_name" validation="desc:'英文名',required:true,type:'COMMON_CHAR',showid:'name_msg',max_len:50,rpc:'checkStyleName'" value="<%=CMyString.filterForHTMLValue(sPageStyleName)%>" NameCanUsed="<%=bIsEdit?"true":"false"%>" <%=bIsEdit?"disabled='true'":""%>>
				<div id="name_msg" class="msg">&nbsp;</div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>