<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.components.wcm.filter.FilterOption" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@include file="../../include/public_server.jsp"%>
<%
	int nOptionId = currRequestHelper.getInt("OptionId",0);
	String sPicFieldName = currRequestHelper.getString("PicFieldName");
	FilterOption currOption = FilterOption.findById(nOptionId);
	if(currOption == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("option_pictrue_set.jsp.notfindoptionid", "没有找到ID为{0}的选项！"), new int[]{nOptionId}));
	}
	int nGroupId = currOption.getGroupId();
	String sPictrueFile = currOption.getPropertyAsString(sPicFieldName);
%>
<%out.clear();%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="option_pictrue_set.jsp.setoptionpic">设置选项图片</title>
	<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
	<script src="../../app/js/easyversion/lightbase.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/easyversion/extrender.js"></script>
	<script src="../../app/js/easyversion/elementmore.js"></script>
	<script src="../../app/js/easyversion/ajax.js"></script>
	<script src="../../app/js/easyversion/basicdatahelper.js"></script>
	<script src="../../app/js/easyversion/web2frameadapter.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/source/wcmlib/Observable.js"></script>
	<!-- CarshBoard Inner Start -->
	<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<script src="../../app/js/source/wcmlib/Component.js"></script>
	<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
	<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
	<!-- CarshBoard Inner End -->
	<style type="text/css">
		*{
			padding:0px;
			margin:0px;
		}
		html,body{
			height:100%;
			width:100%;
			overflow:hidden;
		}
	</style>
	<script type="text/javascript">
	<!--
		// 自定义 crashboard 的按钮
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						var basicDataHelper = new com.trs.web2frame.BasicDataHelper();
						basicDataHelper.call("wcm61_filtercenter","saveFilterOption",{
							ObjectId : <%=nOptionId%>,
							GroupId : <%=nGroupId%>,
							<%=sPicFieldName%> : $('option_img_input').value

						},true,function(_trans,_json){
							
						});
					}
				},
				{
					text : '取消'
				}
			]
		};
		// 处理上传到临时目录的图片，在 upload_dowith 页面中使用
		function dealWithUploadedPicFile(_saveFileHttpPath, _saveFileName, _picFilePurpose){
			if(_saveFileHttpPath.indexOf("protect")<0){
				Ext.Msg.alert("上传文件失败");
			}
			if(!_saveFileHttpPath && _saveFileHttpPath=="")
				return;
			if(_picFilePurpose=="thumb"){
				$("option_img").src = "../file/read_image.jsp?FileName=" + _saveFileName;
				$("option_img_input").value = _saveFileName;
			}
		}
		// 将缩略图还原为“”的状态
		function resumeThumb(){
			Ext.Msg.confirm(
				'您确定要清除图片吗？', 
				{
				yes : function(){
					$("option_img").src = "../advisor/zt_wt.gif";
					$("option_img_input").value = "";
				}
			});
		}
	//-->
	</script>
</head>
<body>
<input type="hidden" name="option_img_input" id="option_img_input" value="<%=CMyString.filterForHTMLValue(sPictrueFile)%>">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" >
	<tr>
		<td align="center" valign="middle" style="padding-left:12px;">
			<TABLE class="border" border=0 cellSpacing=0 cellPadding=0>
			<%
				if(CMyString.isEmpty(sPictrueFile)){
					sPictrueFile =  "../advisor/zt_wt.gif";
				}else{
					sPictrueFile = "../file/read_image.jsp?FileName=" + sPictrueFile;
				}
				
			%>
				<TR>
					<TD align="middle"><IMG style="CURSOR: pointer" id="option_img" src="<%=sPictrueFile%>" width=150 height=150></TD>
				</TR>
			</TABLE>
			<table border=0 cellspacing=0 cellpadding=0 align="center" height="21" style="margin-top:5px;" width="120px">
				<tr>
					<td valign="top" align="center" width="60px">
						 <span style="padding-left:10px;padding-top:5px;cursor:pointer;" onclick="resumeThumb()"><img src="../advisor/zt-xj_an1.gif" border=0 alt="" /></span>
					</td>
					<td valign="top" align="right" width="60px"><IFRAME src="../advisor/public_pic_upload.jsp" frameborder="no" border="0" framespacing="0" width="50px" height="22px" scrolling="no" ></IFRAME></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>