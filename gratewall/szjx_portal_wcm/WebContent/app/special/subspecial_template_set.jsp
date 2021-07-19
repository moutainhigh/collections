<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="error_for_dialog.jsp"%>
<%@ page import="com.trs.components.special.Special" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.PublishConstants" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nObjectId = currRequestHelper.getInt("SpecialId",0);
	int nHostId = currRequestHelper.getInt("HostId",0);
	int nChannelId = currRequestHelper.getInt("nChannelId",0);
	int nHostType = currRequestHelper.getInt("HostType",0);
	int nTemplateId = currRequestHelper.getInt("TemplateId",0);
	// 判断模板的类型
	Template currTemplate = Template.findById(nTemplateId);
	boolean isOutline = false;
	if(currTemplate!=null)
		if(currTemplate.getType() == PublishConstants.TEMPLATE_TYPE_OUTLINE)
			isOutline=true;

	Special special = Special.findById(nObjectId);
	if(special != null){
		//权限校验
		if (!SpecialAuthServer.hasRight(loginUser, special,SpecialAuthServer.SPECIAL_EDIT)) {
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("template_set.noRight",
					"您没有权限修改专题【{0}】！"),new String[]{special.getSpecialName()}));
		}
		nHostId = special.getHostId();
		nHostType = Channel.OBJ_TYPE;
	}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE WCMAnt:param="template_set.title"> 维护结构 </TITLE>
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
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						ok();
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
			var id = document.getElementById("navTree").contentWindow.getCheckValues();
			if(id==""){
				Ext.Msg.alert("请选择栏目！");
				return;
			}
			var type = 1;
			var elements = document.getElementsByName("templateType");
			for(var i =0,j= elements.length;i <j; i++){
				if(elements[i].checked){
					type = elements[i].value;
					break;
				}
			}
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			var oPostData = {
				channelId:id,
				tempType:type
			};
			oHelper.JspRequest(
				"gettempid.jsp",
				oPostData,  true,
				function(transport, json){
					var result = transport.responseText.trim();
					var array = result.split(",");
					if(array[0] != '0'){
						if(array[1] =='true'){
							var url = "design.jsp?specialId=" + document.getElementById("specialId").value + "&templateId=" + array[0] + "&nChannelId=" + id;
							wcm.CrashBoarder.get(window).notify(url);
						}else{
							Ext.Msg.alert("模板不是可视化模板，不能进行可视化编辑！");
						}
					}else{
						Ext.Msg.alert("栏目未配置当前类型模板！");
					}		
				}
			);
		}
	//-->
	</script>
	<style type="text/css">
		.box-border{
			width:100%;
			height:98%;
			height:305px\9;
		}
		.box{
			width:348px;
			height:95%;
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
	<span id="" class="" >选择要切换的栏目：</span>
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="box">
	  <tr>
		<td valign="top">
		  <!--内容区-->
		  <table border="0" align="center" cellpadding="0" cellspacing="0" class="box-border">
			 
			 <tr>
			  <td valign="top" style="padding:5px;">
				<iframe src="../nav_tree/nav_tree_select_node.jsp?objId=<%=nHostId%>&objType=<%=nHostType%>&IsRadio=1&ShowHead=1" width="100%" height="100%" class="frame" style="border:1px solid #ACA899" id="navTree"></iframe>
			  </td>
			</tr>
			<tr style="height:10%;">
			  <td>
				<fieldset style="margin:5px 6px;">
						<legend WCMAnt:param="subspecial_template_set.jsp.temlateselect" WCMAnt:param="template_set.choice">选择要切换的栏目页面：</legend>
						<div class="select">
							<div style="padding-top:5px;">
							<label for="indexTemp"><input type="radio" name="templateType" id="indexTemp" value="1" <%=isOutline?"checked='checked'":""%> WCMAnt:param="template_set.survey"/>栏目首页</label><span width="5px">&nbsp;</span>
							<label for="detailTemp"><input type="radio" name="templateType" id="detailTemp" value="2" <%=isOutline?"":"checked='checked'"%> WCMAnt:param="template_set.detail"/>栏目细览页面</label>
							</div>
						</div>
					</fieldset>
			  </td>
			 </tr>
		  </table>
		</td>
	  </tr>
	</table>
	<input name="specialId" value="<%=nObjectId%>" type="hidden" id="specialId"/>
 </BODY>
</HTML>