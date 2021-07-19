<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.special.Special" %>
<%@include file="../../include/public_server.jsp"%>
<%
	//接受页面参数
	//栏目Id，parentId，parentType
	//接受页面参数
	int nParentId = currRequestHelper.getInt("ParentId",0);
	int nObjectId = currRequestHelper.getInt("ObjectId",0);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE WCMAnt:param="channel_addedit.jsp.title"> 维护结构 </TITLE>
  <META NAME="Generator" CONTENT="EditPlus">
  <META NAME="Author" CONTENT="">
  <META NAME="Keywords" CONTENT="">
  <META NAME="Description" CONTENT="">
  <link href="../css/style.css" rel="stylesheet" type="text/css" />
  <script src="../../js/runtime/myext-debug.js"></script>
  <script src="../../js/source/wcmlib/core/MsgCenter.js"></script>
  <script src="../../js/source/wcmlib/WCMConstants.js"></script>
  <script src="../../js/source/wcmlib/WCMConstants.js"></script>
  <script src="../../js/source/wcmlib/Observable.js"></script>
  <script src="../../js/data/locale/classinfo.js"></script>
  <link rel="stylesheet" type="text/css" href="../../css/wcm-common.css">
  <script src="../../js/data/locale/ajax.js"></script>
  <script src="../../js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
  <!--wcm-dialog start-->
  <SCRIPT src="../../js/source/wcmlib/Observable.js"></SCRIPT>
  <SCRIPT src="../../js/source/wcmlib/Component.js"></SCRIPT>
  <!-- Tree start -->
  <script src="../../js/data/locale/tree.js"></script>
  <script src="../../js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
  <script src="../../js/source/wcmlib/com.trs.tree/TreeManager.js"></script>
  <link rel="stylesheet" type="text/css" href="../../js/source/wcmlib/com.trs.tree/resource/TreeNav.css">
  <link rel="stylesheet" type="text/css" href="../../js/source/wcmlib/com.trs.tree/resource/TreeManager.css">
  <!--Tree end-->
  <SCRIPT language=JavaScript src="../../classinfo/DelayedTask.js"></SCRIPT>
  <SCRIPT language=JavaScript src="../special_2.js"></SCRIPT>
  <script src="../js/adapter4Top.js"></script>
  <script language="javascript">
	<!--
		m_nObjectId = <%=nObjectId%>;
		m_nParentId = <%=nParentId%>;
	//-->
	</script>
 </HEAD>
 <BODY>
	<table width="697px" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
	  <tr>
		<td valign="top">
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
					<legend WCMAnt:param="channel_addedit.jsp.quickkeyintro">快捷键说明：</legend>
					<div class="tip">1.<span class="keyword">Insert</span><span WCMAnt:param="channel_addedit.jsp.create_child_node">创建选中节点的子节点</span></div>
					<div class="tip">2.<span class="keyword">Enter</span><span WCMAnt:param="channel_addedit.jsp.create_sibling_node">创建选中节点的兄弟节点</span></div>
					<div class="tip">3.<span class="keyword">F2</span><span WCMAnt:param="channel_addedit.jsp.edit_sibling_node">编辑选中的节点</span></div>
					<div class="tip">4.<span class="keyword">Delete</span><span WCMAnt:param="channel_addedit.jsp.delete_sibling_node">删除选中的节点</span></div>
					<div class="tip">5.<span class="keyword">Esc</span><span WCMAnt:param="channel_addedit.jsp.cancel_input_state">撤销当前输入状态</span></div>
					<div class="tip">6.<span class="keyword" WCMAnt:param="channel_addedit.jsp.arrow_key">方向键</span><span>切换选中的节点</span></div>
					<div class="tip">7.<span class="keyword" WCMAnt:param="channel_addedit.jsp.shift_key">shift+方向键</span><span  WCMAnt:param="channel_addedit.jsp.sort_selected_node">排序选中的节点</span></div>
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
 <input type="hidden" id="hostId" name="hostId" value="<%=nParentId%>">
 </BODY>
</HTML>