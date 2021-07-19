<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nTemplateId = currRequestHelper.getInt("TemplateId",0);
	Template template = Template.findById(nTemplateId);

	int nFolderType = template.getFolderType();
	int nFolderId = template.getFolderId();

	//可视化模板的类似创建，目前只在同一个站点内部实现
	if(nFolderType == WebSite.OBJ_TYPE){
		nFolderId = 0;
	}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE WCMAnt:param="copyVisualTemplateToChnls.jsp.title"> 选择栏目 </TITLE>
  <META NAME="Generator" CONTENT="EditPlus">
  <META NAME="Author" CONTENT="">
  <META NAME="Keywords" CONTENT="">
  <META NAME="Description" CONTENT="">
  <script src="../../app/js/runtime/myext-debug.js"></script>
  <script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
  <script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
  <script src="../../app/js/source/wcmlib/Observable.js"></script>
  <!--wcm-dialog start-->
  <SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
  <SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
  <script src="js/adapter4Top.js"></script>
  <script language="javascript">
	<!--
		var nNUM = 1;
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						//避免发生连续点击确定按钮多次，类似创建多份模板
						if(nNUM == 1){
							++nNUM;
							ok();
						}
						return false;
					}
				},
				{
					extraCls : 'wcm-btn-close',
					text : '取消'
				}
			]
		};
		function ok(){
			var ids = document.getElementById("navTree").contentWindow.getCheckValues();
			if(ids==""){
				Ext.Msg.alert("请选择栏目！");
				return;
			}

			var oHelper = new com.trs.web2frame.BasicDataHelper();
			var oPostData = {
				toChannelIds:ids,
				templateId:document.getElementById('templateId').value
			};
			c_bWin = wcm.CrashBoarder.get(window);
			oHelper.call('wcm61_visualtemplate', 'copyVisualTemplate', oPostData, true, function(transport, json){
					Ext.Msg.report(json, '类似创建模板结果', function(){
						c_bWin.hide();
						c_bWin.notify('true');
					});
				}
			);
		}
	//-->
	</script>
	<style type="text/css">
		.box-border{
			width:100%;
			height:98%;
		}
		.box{
			width:300px;
			height:100%;
		}
		/*scrollbar*/		html,body,iframe{scrollbar-face-color:#e6e6e6;scrollbar-highlight-color:#fff;scrollbar-shadow-color:#eeeeee;scrollbar-3dlight-color:#eeeeee;scrollbar-arrow-color:#000;scrollbar-track-color:#fff;scrollbar-darkshadow-color:#fff;}		
		html,body,iframe{font-size:12px;}
		td{font-size:12px;}
		body.ext-ie7{position:absolute;width:100%;height:100%;}
		.select{
			cursor:pointer;
			padding-left:15px;
		}
		.ext-gecko .frame{
			height:92%;
		}
	</style>
 </HEAD>
 <BODY>
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="box">
	  <tr>
		<td valign="top">
		  <!--内容区-->
		  <table border="0" align="center" cellpadding="0" cellspacing="0" class="box-border">
			 <tr>
			  <td valign="top" style="padding:5px;">
				<iframe src="../nav_tree/nav_tree_select.jsp?CurrChannelId=<%=nFolderId%>&MultiSites=0&ShowOneType=1&SiteTypes=0&ExcludeSelf=1&ExcludeTop=1" width="100%" height="100%" class="frame" style="border:1px solid #ACA899" id="navTree"></iframe>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
	<input name="templateId" value="<%=nTemplateId%>" type="hidden" id="templateId"/>
 </BODY>
</HTML>