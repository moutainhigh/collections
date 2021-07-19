<%--
/** Title:			metaviewfieldgroup_list.jsp
 *  Description:
 *		MetaViewFieldGroup列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2011-06-11 03:19:07
 *  Vesion:			1.0
 *  Last EditTime:	2011-06-11 / 2011-06-11
 *	Update Logs:
 *		TRS WCM 5.2@2011-06-11 产生此文件
 *
 *  Parameters:
 *		see metaviewfieldgroup_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroup" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroups" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CPager"%>
<%@ page import="com.trs.presentation.util.PageHelper"%>
<%@ page import="com.trs.presentation.util.PageViewUtil"%>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());
	int nMetaViewId = currRequestHelper.getInt("MetaViewId",0);
	if(nMetaViewId <= 0){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("metaviewfieldgroup_list.jsp.label.param_value_error", "参数【MetaViewId】的值必须大于0！"));
	}

//5.权限校验

//6.业务代码
	String sSelectFields = "";//"${Outline_Fields}";
	String sWhere = currRequestHelper.getWhereSQL();
	if(CMyString.isEmpty(sWhere)){
		sWhere = "METAVIEWID=?";
	}else{
		sWhere+=" AND METAVIEWID=?";
	}
	String sOrderSql = "";
	if("".equals(sOrderField)){
		sOrderSql = "GROUPORDER ASC ";
	}else{
		sOrderSql = currRequestHelper.getOrderSQL();
	}
	WCMFilter filter = new WCMFilter("",sWhere,
	sOrderSql, sSelectFields);
	filter.addSearchValues(nMetaViewId);

	MetaViewFieldGroups currMetaViewFieldGroups = MetaViewFieldGroups.openWCMObjs(loginUser, filter);

	CPager currPager = new CPager(-1);
	currPager.setItemCount(currMetaViewFieldGroups.size() );	
	currPager.setCurrentPageIndex(currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="metaviewfieldgroup_list.jsp.title">TRS WCM ::::::视图字段分组列表页面</title>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css">
<style type="text/css">

html,body{
	overflow:hidden;
}
.grid_row_active{
	background-color: rgb(255, 255, 204);
	font-size:14px;
	height:22;
	vertical-align:bottom;
}
.orderText{
	width:30px;
	border-top:0px;
	border-left:0px;
	border-right:0px;
	border-bottom:1px solid gray;
	background:none;
	text-align:center;
}
</style>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLTr.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLElement.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></SCRIPT>

<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<!-- Component Start -->
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<!-- Component End -->
<%=currRequestHelper.toTRSRequestParam()%>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px;overflow:hidden;">

<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="1" class="list_table">
	<TR>
		<TD valign="top">
		<TABLE width="100%" border="0" cellpadding="2" height="100%"
			cellspacing="0" bgcolor="#FFFFFF">
			<TR>
				<TD height="15">
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
					<TR>
						<TD align="left" valign="top">
							<script>
								//定义一个单行按钮
								var oTRSButtons = new CTRSButtons();
								oTRSButtons.addTRSButton("新建", "addNew();", "../../images/button_new.gif", "新建MetaViewFieldGroup");
								oTRSButtons.addTRSButton("删除", "deleteMetaViewFieldGroup(getMetaViewFieldGroupIds());", "../../images/button_delete.gif", "删除选定的MetaViewFieldGroup");
								oTRSButtons.addTRSButton("刷新", "CTRSAction_refreshMe();", "../../images/button_refresh.gif", "刷新当前页面");
								oTRSButtons.draw();	
							</script>
						</TD>
						<TD width="280" nowrap>
							<form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">
								&nbsp; <input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
								<select name="SearchKey">
									<option value="GROUPNAME" WCMAnt:param="metaviewfieldgroup_list.jsp.all">全部</option>
									<option value="GROUPNAME" WCMAnt:param="metaviewfieldgroup_list.jsp.classfy_name">分组名称</option>
								</select> 
								<input type="submit" value="检索" WCMAnt:paramattr="value:metaviewfieldgroup_list.jsp.search" >
							</form>
						</TD>						
						<script>
							document.frmSearch.SearchKey.value = "<%=currRequestHelper.getSearchKey()%>";
						</script>
					</TR>
				</TABLE>
				</TD>
			</TR>
			<TR>
				<TD align="left" valign="top" height="20">
				<div style="height:345px;overflow:auto;padding:0 10px 5px 5px;" id="databox">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
					class="list_table" id="tablebox">
					<TR bgcolor="#BEE2FF" class="list_th">
						<TD width="40" height="20" NOWRAP><a
							href="javascript:TRSHTMLElement.selectAllByName('MetaViewFieldGroupIds');" WCMAnt:param="metaviewfieldgroup_list.jsp.select_all">全选</a></TD>
						<TD width="100" bgcolor="#BEE2FF" WCMAnt:param="metaviewfieldgroup_list.jsp.alter">修改</TD>
						<TD bgcolor="#BEE2FF" WCMAnt:param="metaviewfieldgroup_list.jsp.classfy_name">分组名称</TD>
						<TD bgcolor="#BEE2FF" WCMAnt:param="metaviewfieldgroup_list.jsp.belong_father_classfy">所属父组</TD>
						<TD bgcolor="#BEE2FF" WCMAnt:param="metaviewfieldgroup_list.jsp.order">排序</TD>
					</TR>
					<tbody id="table_tbody">
		<%
			MetaViewFieldGroup currMetaViewFieldGroup = null;
			for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
			{//begin for
				String sParentGroupName = "";
				int nOrder = 0;
				int nGroupId = 0;
				try{
					currMetaViewFieldGroup = (MetaViewFieldGroup)currMetaViewFieldGroups.getAt(i-1);
					int nParentGroupId = currMetaViewFieldGroup.getParentId();
					if(nParentGroupId > 0){
						MetaViewFieldGroup parentGroup = MetaViewFieldGroup.findById(nParentGroupId);
						if(parentGroup != null){
							sParentGroupName = parentGroup.getGroupName();
						}
					}
					nGroupId = currMetaViewFieldGroup.getId();
					nOrder = currMetaViewFieldGroup.getOrder();
				} catch(Exception ex){
					throw new WCMException(CMyString.format(LocaleServer.getString("metaviewfieldgroup_list.jsp.fail2get_MetaViewFieldGroup", "获取第[{0}]篇MetaViewFieldGroup失败！"), new int[]{i}),ex);
				}
				if(currMetaViewFieldGroup == null){
					throw new WCMException(CMyString.format(LocaleServer.getString("metaviewfieldgroup_list.jsp.fail2get_MetaViewFieldGroup", "获取第[{0}]篇MetaViewFieldGroup失败！"), new int[]{i}));
				}
				try{
		%>
					<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);" id="tr_<%=nGroupId%>">
						<TD width="40" align="center" NOWRAP><INPUT TYPE="checkbox" NAME="MetaViewFieldGroupIds"
							VALUE="<%=nGroupId%>"></TD>
						<TD align="center">&nbsp;<A
							onclick="edit(<%=currMetaViewFieldGroup.getId()%>);return false;"
							href="#"><IMG border="0" src="../../images/icon_edit.gif"></A></TD>
						<TD align="center"><%=CMyString.transDisplay(currMetaViewFieldGroup.getGroupName())%></TD>
						<TD align="center"><%=CMyString.transDisplay(sParentGroupName)%></TD>
						<TD align="center"><input type="text" name="orderText" id="orderText_<%=nGroupId%>" value="<%=nOrder%>" class="orderText" _id="<%=nGroupId%>" rowNum ="<%=nOrder%>" _value="<%=nOrder%>"/>
						</TD>
					</TR>
		<%
				} catch(Exception ex){
					throw new WCMException(CMyString.format(LocaleServer.getString("metaviewfieldgroup_list.jsp.fail2get_Attribute", "获取第[{0}]篇MetaViewFieldGroup的属性失败！"), new int[]{i}),ex);
				}
			}//end for	
		%>
				</tbody>
				</TABLE>
				</div>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
<script>

function getMetaViewFieldGroupIds(){
	return TRSHTMLElement.getElementValueByName('MetaViewFieldGroupIds');
}
function addNew(){
	var sURL = "metaviewfieldgroup_addedit.jsp?MetaViewId=" + <%=nMetaViewId%>;
	var oTRSAction = new CTRSAction(sURL);
	var bResult = oTRSAction.doDialogAction(400, 200);
	if(bResult){
		CTRSAction_refreshMe();
	}
}
function edit(_nMetaViewFieldGroupId){	
	var oTRSAction = new CTRSAction("metaviewfieldgroup_addedit.jsp");
	oTRSAction.setParameter("MetaViewFieldGroupId", _nMetaViewFieldGroupId);
	var bResult = oTRSAction.doDialogAction(400, 200);
	if(bResult){
		CTRSAction_refreshMe();
	}
}
function deleteMetaViewFieldGroup(_sMetaViewFieldGroupIds){
	//参数校验
	if(_sMetaViewFieldGroupIds == null || _sMetaViewFieldGroupIds.length <= 0){
		alert("请选择需要删除的分组!");
		return;
	}
	if(!confirm("您确定需要删除这些分组吗?"))
		return;
	
	//var oTRSAction = new CTRSAction("metaviewfieldgroup_delete.jsp");
	//oTRSAction.setParameter("MetaViewFieldGroupIds", _sMetaViewFieldGroupIds);		
	//oTRSAction.doDialogAction(200, 100);
	new com.trs.web2frame.BasicDataHelper().call('wcm61_metaviewfieldgroup', "deleteMetaViewFieldGroups", {
		MetaViewFieldGroupIds : _sMetaViewFieldGroupIds
	}, true, function(){
		CTRSAction_refreshMe();
	});
	return true;
}
window.m_cbCfg = {
	btns : [
		{
			text : '关闭',
			cmd : function(){
				var cbr = wcm.CrashBoarder.get(window);
				cbr.notify();
				cbr.close();
				return false;
			}
		}
	]
};
Event.observe(window,'load',function(){
	Event.observe('table_tbody', 'keydown', function(event){
		event = window.event || event;
		var srcEl = Event.element(event);
		if(!Element.hasClassName(srcEl,'orderText'))
			return;
		OrderHandler.changeOrder(event,srcEl);
	});
})

var OrderHandler = {
	count : null,
	els: document.getElementsByName('orderText'),
	initOrderElementsCount : function(){
		var elsLength = this.els.length;
		return elsLength;
	},
	changeOrder : function(event, dom){
		if(event.keyCode != 13){
			return;
		}
		if(!this.valid(dom)){
			return;
		}
		this.changeValue(event, dom);
	},
	valid : function(dom){
		if(!/^\d+$/.test(dom.value)){
			Ext.Msg.alert('请输入合法的数字');
			dom.select();
			dom.focus();
			return false;
		}
		return true;
	},
	changeValue : function(event, dom){
		this.count = this.count || this.initOrderElementsCount();
		//获取到当前操作的input元素
		if(dom.tagName != 'INPUT' || dom.name != 'orderText'){
			return;
		}
		var newValue = parseInt(dom.value);
		var oldValue = parseInt(dom.getAttribute("_value"));
		if(newValue == oldValue){
			return;
		}
		var bPosition;
		if(newValue > this.count-1) newValue = (this.count-1);
		if(newValue < 0) newValue = 0;
		dom.value = newValue;
		//发送请求保存顺序
		var oPostData = {
			MetaViewFieldGroupId : dom.getAttribute("_id"),
			GroupOrder : newValue
		}
		BasicDataHelper.call("wcm6_MetaDataDef", 'changeViewFieldGroupOrder', oPostData, true, function(transport, json){		 //获取当前操作的本行元素
			var rowId = dom.getAttribute("_id");
			var currRow = $('tr_' + rowId);
			var tableBoxEl = $('table_tbody');
			var targetEl = null;
			var els = document.getElementsByName('orderText');
			var el = null;
			if(newValue > oldValue){
				//修改所有原来位置后面,新位置之前的元素，依次减1
				for(var k=0; k < els.length; k++){
					el = els[k];
					if(el.id == "orderText_" + rowId)continue;
					var elValue = parseInt(el.value);

					if(elValue == newValue + 1){
						targetEl = el;
					}
					if(elValue > oldValue && elValue <= newValue){
						el.value = elValue - 1;
						el.setAttribute("_value", el.value);
					}
				}
				if(targetEl){
					//获取到要插入的目标元素
					var targetRow = $("tr_" + targetEl.getAttribute("_id"));
					tableBoxEl.insertBefore(currRow,targetRow);
				}else{
					tableBoxEl.appendChild(currRow);
				}
			}
			if(newValue < oldValue){
				for(var i = 0;i <els.length;i++){
					el = els[i];
					if(el.id == "orderText_" + rowId)continue;
					var elValue = parseInt(el.value);
					if(elValue == newValue){
						targetEl = el;
					}
					if(elValue >= newValue && elValue < oldValue){
						el.value = elValue + 1;
						el.setAttribute("_value", el.value);
					}
				}
				if(targetEl){
					//alert(targetEl.innerHTML);
					//获取到要插入的目标元素
					var targetRow = $("tr_" + targetEl.getAttribute("_id"));
					tableBoxEl.insertBefore(currRow,targetRow);
				}
			}
			dom.setAttribute("_value", dom.value);
		});
		
	}
};
function saveGroupOrder(param){
	//params = {fromId : currId, toId : targetId, position : bPosition};
}
</script>
</BODY>
</HTML>
<%
//6.资源释放
	currMetaViewFieldGroups.clear();
%>