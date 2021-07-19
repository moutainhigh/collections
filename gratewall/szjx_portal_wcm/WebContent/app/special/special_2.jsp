<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="error_for_dialog.jsp"%>
<%@ page import="com.trs.components.special.Special" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nObjectId = currRequestHelper.getInt("ObjectId",0);
	int nIsNew = currRequestHelper.getInt("isNew",0);
	String sSpecialName = currRequestHelper.getString("specialName");
	
	Special special = Special.findById(nObjectId);
	if(special == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("special_2.jsp.special_notfound", "没有找到ID为[{0}]的专题!"), new int[]{nObjectId}));
	}
	//权限校验
	if (!SpecialAuthServer.hasRight(loginUser, special,SpecialAuthServer.SPECIAL_EDIT)) {
            throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("specia2_1.jsp.have_noright_edit", "您没有权限修改专题【{0}】！"), new String[]{special.getSpecialName()}));
	}
	int nHostId = special.getHostId();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE WCMAnt:param="special_2.jsp.title"> 维护结构 </TITLE>
  <META NAME="Generator" CONTENT="EditPlus">
  <META NAME="Author" CONTENT="">
  <META NAME="Keywords" CONTENT="">
  <META NAME="Description" CONTENT="">
  <link href="css/style.css" rel="stylesheet" type="text/css" />
  <script src="../../app/js/runtime/myext-debug.js"></script>
  <script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
  <script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
  <script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
  <script src="../../app/js/source/wcmlib/Observable.js"></script>
  <script src="../../app/js/data/locale/classinfo.js"></script>
  <link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
  <script src="../../app/js/data/locale/ajax.js"></script>
  <script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
  <!--wcm-dialog start-->
  <SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
  <SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
  <!-- Tree start -->
  <script src="../../app/js/data/locale/tree.js"></script>
  <script src="../../app/js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
  <script src="../../app/js/source/wcmlib/com.trs.tree/TreeManager.js"></script>
  <link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/com.trs.tree/resource/TreeNav.css">
  <link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/com.trs.tree/resource/TreeManager.css">
  <!--Tree end-->
  <SCRIPT language=JavaScript src="../classinfo/DelayedTask.js"></SCRIPT>
  <SCRIPT language=JavaScript src="special_2.js"></SCRIPT>
  <script src="js/adapter4Top.js"></script>
  <script language="javascript">
	<!--
		window.m_cbCfg = {
			btns : [
				{
					text : '上一步',
					id	:	'ParambtnUp',
					cmd : function(){
						wcmXCom.get('ParambtnUp').disable();	
						location.href = "special_1.jsp?ObjectId=<%=nObjectId%>&isNew=<%=nIsNew%>";
						this.setSize("729px","437px");
						return false;
					}
				},
				{
					text : '<%=nIsNew != 0 ? "下一步":"确定"%>',
					id	:  'ParambtnDown',
					cmd : function(){
						this.hide();
						this.notify(<%=nObjectId%>);
						return false;
					}
				},
				{
					extraCls : 'wcm-btn-close',
					id	:	'ParambtnCancel',
					text : '取消',
					cmd : function(){
						this.notify(0);
						return false;
					}
				}
			]
		};		
		//m_nObjectId、m_nParentId只用于栏目的新建、修改
		var m_nObjectId = 0;
		var m_nParentId = 0;
	//-->
	</script>
 </HEAD>
 <BODY>
	<table width="697" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
	  <tr>
		<td valign="top">
		  <!--步骤图-->
		  <table width="98%" align="center" height="40" border="0" cellspacing="0" cellpadding="0" class="table-grey">
			<tr>
			  <td class="font" WCMAnt:param="special_2.jsp.step">您需要按以下几个步骤创建专题：</td>
			</tr>
			<tr>
			  <td >&nbsp;</td>
			</tr>
			<tr>
			  <td align="left" style="width:200px;"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="box-focus">
				  <tr>
					<td class="font-focus">1. <span class="font-focus01" WCMAnt:paramattr="title:special_2.jsp.maintain_special_info" title="该步骤将进行专题基本信息的维护"><a href="#" onclick="wcm.CrashBoarder.get(window).getCrashBoard().setSize('729px','437px');location.href='special_1.jsp?ObjectId=<%=nObjectId%>&isNew=<%=nIsNew%>'" style="font-family:幼圆" WCMAnt:param="special_2.jsp.input_basic_info">录入基本信息</a></span></td>
				  </tr>
			  </table></td>
			  <td align="center" style="width:40px;"><img src="images/zt_jt.gif" width="31" height="22" /></td>
			  <td align="center" style="width:180px;"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="box-focus">
				  <tr>
					<td class="font-focus">2. <span class="font-focus01" WCMAnt:paramattr="title:special_2.jsp.this_step_channel"  title="进行专题所属栏目的组织结构维护"><a href="#" onclick="location.href=location.href;" style="font-family:幼圆;font-weight:bold;" WCMAnt:param="special_2.jsp.maintain_stuctrue">维护结构</a></span></td>
				  </tr>
			  </table></td>
			  <td align="center" style="width:40px;"><img src="images/zt_jt.gif" width="31" height="22" style="display:<%=nIsNew == 0? "none":""%>"/></td>
			  <td align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="box-gray" style="display:<%=nIsNew == 0? "none":""%>">
				  <tr>
					<td class="font-gray">3. <span class="font-gray01" style="font-family:幼圆;font-weight:bold;" WCMAnt:paramattr="title:special_2.jsp.this_step_special_page" title="该步骤将进入专题设计页面">
					<%
						if(nIsNew != 0){
					%>
						<a href="#" onclick="wcm.CrashBoarder.get(window).hide();wcm.CrashBoarder.get(window).notify(<%=special.getId()%>)">
						设计表现
						</a>
					<%
						}
					%></span></td>
				  </tr>
			  </table></td>
			</tr>
			<tr>
			  <td >&nbsp;</td>
			</tr>
		  </table>
		  <!--内容区-->
		  <table border="0" align="center" cellpadding="0" cellspacing="0" class="box-border">
			<tr>
			  <td valign="top" width="50%" style="padding-top:10px;">
				<div class="TreeView" id="TreeView">
					<div><a href="#"></a></div><UL></UL>
				</div>
			  </td>
			  <td valign="top">
				<fieldset style="margin:10px 10px;">
					<legend WCMAnt:param="classinfo_config.html.quitekeyintro" WCMAnt:param="special_2.jsp.shortcutkey_intro">快捷键说明：</legend>
					<div class="tip">1.<span class="keyword">Insert</span><span WCMAnt:param="special_2.jsp.create_son_node">创建选中节点的子节点</span></div>
					<div class="tip">2.<span class="keyword">Enter</span><span WCMAnt:param="special_2.jsp.create_sibling_node">创建选中节点的兄弟节点</span></div>
					<div class="tip">3.<span class="keyword">F2</span><span WCMAnt:param="special_2.jsp.alter_node_name">修改选中节点名</span></div>
					<div class="tip">4.<span class="keyword">Delete</span><span WCMAnt:param="special_2.jsp.deletee_node">删除选中的节点</span></div>
					<div class="tip">5.<span class="keyword">Esc</span><span WCMAnt:param="special_2.jsp.cancel_input_state">撤销当前输入状态</span></div>
					<div class="tip">6.<span class="keyword" WCMAnt:param="special_2.jsp.arrow">方向键</span><span WCMAnt:param="special_2.jsp.design_display"  WCMAnt:param="special_2.jsp.switch_node">切换选中的节点</span></div>
					<div class="tip">7.<span class="keyword"  WCMAnt:param="special_2.jsp.shift_arrow">shift+方向键</span><span  WCMAnt:param="special_2.jsp.sort_nodes">排序选中的节点</span></div>
				</fieldset>
			  </td>
			</tr>
			<tr>
			  <td>&nbsp;</td>
			 </tr>
		  </table>
		  <!--页脚-->
		  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="yejiao">
			<tr>
			  <td></td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
 <input type="hidden" id="hostId" name="hostId" value="<%=nHostId%>">
 <input type="hidden" id="specialName" name="specialName" value="<%=CMyString.filterForHTMLValue(sSpecialName)%>">
 </BODY>
</HTML>