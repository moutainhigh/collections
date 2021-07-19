<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS @ BEGIN -->
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyles" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<!-- WCM IMPORTS @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	//获取当前用户的权限，以便于控制“快速通道”中显示的入口
	 
	//获取参数
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);

	//获取风格对象
	PageStyle oPageStyle = PageStyle.findById(nPageStyleId);
	if(oPageStyle==null){
		throw new WCMException(CMyString.format(LocaleServer.getString("pagestyle_edit_main_index.jsp.fail2get_page_style", "获取页面风格[Id={0}]失败！"), new int[]{nPageStyleId}));
	}
	//权限判断
	boolean bHasRight = SpecialAuthServer.hasRight(loginUser, oPageStyle,
                SpecialAuthServer.STYLE_EDIT);
	if (!bHasRight) {
		throw new WCMException(CMyString.format(LocaleServer.getString("pagestyle_edit_main_index.jsp.have_noright_alter_style", "您没有权限修改风格【{0}】！"), new String[]{oPageStyle.getStyleDesc()}));
	}

	//获取风格
	String sPageStyleName = oPageStyle.getStyleName();
	String sPageStyleDesc = oPageStyle.getStyleDesc();
	sPageStyleDesc = CMyString.transDisplay(CMyString.showNull(sPageStyleDesc));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title WCMAnt:param="pagestyle_edit_main_index.jsp.title">TRS风格管理</title>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<link rel="stylesheet" type="text/css" href="pagestyle_edit_main_index.css"/>
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script type="text/javascript" src="pagestyle_edit_main_index.js"></script>
<script type="text/javascript">
		// 保存最后一次点击的资源类别
		var M_LastClick_A= null;
		window.onload = function () {
			M_LastClick_A = $("a_resource_0");
		}
		//生成样式文件
		function makeCssFile(_nPageStyleId){
			_nPageStyleId = _nPageStyleId || 0;
			// 发送 导出 请求
			var sPostStr = "serviceid=wcm61_Pagestyle&methodname=makeCssFile";
			sPostStr += "&ObjectId="+_nPageStyleId;
			var sUrl = "/portal/center.do";
			var successFun = function (){
				top.window.ProcessBar.close();
			};
			executeAjaxService(sPostStr,sUrl,successFun,"get");
		}

	//-->
	</script>
</head>
<body>
<!-- 主内容区域 @ BEGIN -->
<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="4" valign="top" background="./images/lbg.gif"></td>
		<td width="100%" valign="top" bgcolor="#FFFFFF" height="100%" >
			<table border=0 cellspacing=0 cellpadding=0 height="100%" width="100%" bgcolor="#FFFFFF">
				<tr>
				<!-- 资源分类 @ begin -->
					<td width="160px" height="100%" valign="top">
						<table width="160px" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<!--左侧导航 begin-->
							<tr>
								<td colspan="2" class="rightbg" valign="top">
									<Div class="TreeView_customize"   id="TreeView_customize" style="OVERFLOW-y: hidden; WIDTH: 100%;height:100%;padding:10 0 0 10;">
										<div id="div_resource_0">
											<img src="./images/fenggejian.gif" width="29px" height="13px" border="0" class="img_margin_right"/><A href="#" id="a_resource_0" class="a_selected" title="<%=sPageStyleDesc%>" onclick="changeStyleTab(this,0,<%=nPageStyleId%>);return false;"><%=CMyString.truncateStr(sPageStyleDesc, 13)%></A>
										</div>
										<div id="div_resource_1" class="div_padding_left">
											<img src="./images/jibentexing.gif" width="26px" height="21px" border="0" class="img_margin_right"/><A href="#" id="a_resource_1" onclick="changeStyleTab(this,1,<%=nPageStyleId%>);return false;" WCMAnt:param="pagestyle_edit_main_index.jsp.basic_feature">基本特性</A>
										</div>
										<div id="div_resource_2" class="div_padding_left">
											<img src="./images/ziyuankeyong.gif" width="29px" height="21px" border="0" class="img_margin_right"/><A href="#" id="a_resource_2" onclick="changeStyleTab(this,2,<%=nPageStyleId%>);return false;" WCMAnt:param="pagestyle_edit_main_index.jsp.useful_widget_style">资源可用风格</A>
										</div>
										<div id="div_resource_3" class="div_padding_left">
											<img src="./images/neirongkeyong.gif" width="29px" height="21px" border="0" class="img_margin_right"/><A href="#" id="a_resource_3" onclick="changeStyleTab(this,3,<%=nPageStyleId%>);return false;" WCMAnt:param="pagestyle_edit_main_index.jsp.useful_content_style">内容可用风格</A>
										</div>
										<!--
										<div id="div_resource_4" class="div_padding_left">
											<img src="./images/otherdom.gif" width="29px" height="21px" border="0" class="img_margin_right"/><A href="#" id="a_resource_5" onclick="changeStyleTab(this,5,<%=nPageStyleId%>);return false;">页面其他元素</A>
										</div>
										-->
										<div id="div_resource_4" class="div_padding_left">
											<img src="./images/zidingyi.gif" width="30px" height="18px" border="0" class="img_margin_right"/><A href="#" id="a_resource_4" onclick="changeStyleTab(this,4,<%=nPageStyleId%>);return false;" WCMAnt:param="pagestyle_edit_main_index.jsp.self_define">自定义</A>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td width="160"><div class="dvbottom">
								</div></td>
							</tr>
							 
							<!--左侧导航 end-->
						</table>
					</td>
				<!-- 资源分类 @ end -->
					<td width="100%" height="100%" valign="top">
						<iframe src="style_name_addedit.jsp?PageStyleId=<%=nPageStyleId%>" width="100%" height="100%" id="iframe_trswidget_list" frameborder="0" scrolling ="no"></iframe>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- 主内容区域 @ END -->
</body>
</html>