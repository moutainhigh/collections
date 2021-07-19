<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS @ BEGIN -->
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page  import="com.trs.components.common.publish.widget.ResourceStyle" %>
<%@ page  import="com.trs.components.common.publish.widget.ContentStyle" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page  import="com.trs.infra.common.WCMException" %>
<%@ page  import="com.trs.components.common.publish.widget.StylePathHelper" %>
<%@ page  import="java.io.File" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<!-- WCM IMPORTS @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	// 获取参数
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
	PageStyle oPageStyle = PageStyle.findById(nPageStyleId);
	String sStyleThumb = "";
	boolean bHasRight = false;
	if(oPageStyle!=null){
		 sStyleThumb = CMyString.showNull(oPageStyle.getStyleThumb(),"");
		//权限判断
		bHasRight = SpecialAuthServer.hasRight(loginUser, oPageStyle, SpecialAuthServer.STYLE_EDIT);
	}else{
		int nResourceStyleId = currRequestHelper.getInt("ResourceStyleId",0);
		ResourceStyle oResourceStyle = ResourceStyle.findById(nResourceStyleId);
		if(oResourceStyle != null){
			sStyleThumb = CMyString.showNull(oResourceStyle.getStyleThumb(),"");
			bHasRight = SpecialAuthServer.hasRight(loginUser, oResourceStyle, SpecialAuthServer.STYLE_EDIT);
		}else{
			int nContentStyleId = currRequestHelper.getInt("ContentStyleId",0);
			ContentStyle oContentStyle = ContentStyle.findById(nContentStyleId);
			if(oContentStyle!=null){
				sStyleThumb = CMyString.showNull(oContentStyle.getStyleThumb(),"");
				bHasRight = SpecialAuthServer.hasRight(loginUser, oContentStyle, SpecialAuthServer.STYLE_EDIT);
			}else{
				throw new WCMException(LocaleServer.getString("for_style.obj.not.found","没有获取到任何有效的风格对象！"));
			}
		}
	}
	if(!bHasRight){
		throw new WCMException(LocaleServer.getString("for_style.noRight","您没有权限修改风格的缩略图！"));
	}
	sStyleThumb = CMyString.filterForHTMLValue( sStyleThumb );
	out.clear();
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="author" content="TRSArcher">
	<title WCMAnt:param="for_style.title">为当前风格上传图片</title>
	<style type="text/css">
		body{
			margin:0px;
			padding:0px;
		}
		.border{ 
			border:1px solid #020203;
			padding:1px;
		}
		.sc {
			font-family: "宋体";
			font-size: 12px;
			color: #333333;
			text-decoration: none;
			line-height: 18px;
			font-weight: normal;
		}
		.sc1 {
			font-family: "宋体";
			font-size: 12px;
			color: #0063B8;
			text-decoration: none;
			line-height: 18px;
			font-weight: normal;
		}
	</style>
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<script type="text/javascript">
	<!--
		// 自定义 crashboard 的按钮
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						var sPublicSkinThumb = encodeURIComponent($("input_PublicSkinThumb").value)
						// 回调
						this.notify(sPublicSkinThumb);
					}
				},
				{
					text : '取消',
					extraCls : 'wcm-btn-close'
				}
			]
		};
		// 处理上传到临时目录的图片，在 upload_dowith 页面中使用
		function dealWithUploadedPicFile(_saveFileHttpPath, _saveFileName, _picFilePurpose){
			if(_saveFileHttpPath.indexOf("webpic")<0){
				Ext.Msg.alert("上传文件失败");
			}
			if(!_saveFileHttpPath&&_saveFileHttpPath=="")
				return;
			if(_picFilePurpose=="thumb"){
				$("img_PublicSkinThumb").src = "../file/read_image.jsp?FileName=" + _saveFileName;//_saveFileHttpPath;
				$("input_PublicSkinThumb").value = _saveFileName;
			}
		}
		// 将缩略图还原为“”的状态
		function resumeThumb(){
			Ext.Msg.confirm(
				'您确定要清除风格缩略图吗？', 
				{
				yes : function(){
					$("img_PublicSkinThumb").src = "../images/zt_wt.gif";
					$("input_PublicSkinThumb").value = "";
				}
			});
		}
	//-->
	</script>
</head>
<body>
<input type="hidden" name="PublicSkinThumb" id="input_PublicSkinThumb" value="<%=sStyleThumb%>">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" >
	<tr>
		<td align="center" valign="middle" style="padding-left:12px;">
			<TABLE class="border" border=0 cellSpacing=0 cellPadding=0>
			<%
				if(CMyString.isEmpty(sStyleThumb)){
					sStyleThumb =  "../images/zt_wt.gif";
				}else{
					sStyleThumb = "../file/read_image.jsp?FileName=" + sStyleThumb;
				}
				
			%>
				<TR>
					<TD align="middle"><IMG style="CURSOR: pointer" id="img_PublicSkinThumb" src="<%=sStyleThumb%>" width=300 height=200></TD>
				</TR>
			</TABLE>
			<table border=0 cellspacing=0 cellpadding=0 align="center" height="21" style="margin-top:5px;" width="120px">
				<tr>
					<td valign="top" align="center" width="60px">
						 <span style="padding-left:10px;padding-top:5px;cursor:pointer;" onclick="resumeThumb()"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
					</td>
					<td valign="top" align="right" width="60px"><IFRAME src="./public_thumb_upload.jsp" id="PublicSkinThumbUpload" name="PublicSkinThumbUpload" frameborder="no" border="0" framespacing="0" width="50px" height="22px" scrolling="no" ></IFRAME></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>