<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.filter.FilterOptionGroup" %>
<%@ page import="com.trs.components.wcm.filter.FilterOption" %>
<%@ page import="com.trs.components.wcm.filter.FilterOptions" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_processor.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	String sGroupId = processor.getParam("GroupId");
	int nGroupId = Integer.parseInt(sGroupId);
	FilterOptionGroup currGroup = FilterOptionGroup.findById(nGroupId);
	if(currGroup == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("filter_option_order.jsp.notfindoptiongroupid", "没有找到ID为{0}的选项组！"), new int[]{nGroupId}));
	}
	HashMap parameters = new HashMap();
	parameters.put("ObjectId", String.valueOf(nGroupId));
	FilterOptions oFilterOptions = (FilterOptions)processor.excute("wcm61_filtercenter", "queryFilterOptionsByGroup", parameters);
	out.clear();
%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="filter_option_order.jsp.trswcmfilteroptionorder">TRS WCM ::::::筛选器选项排序</title>
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
</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px;overflow:hidden;">
<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="1" class="list_table">
	<TR>
		<TD valign="top">
		<TABLE width="100%" border="0" cellpadding="2" height="100%" cellspacing="0" bgcolor="#FFFFFF">
			<TR>
				<TD align="left" valign="top" height="20">
				<div style="height:345px;overflow:auto;padding:0 10px 5px 5px;" id="databox">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
					class="list_table" id="tablebox">
					<TR bgcolor="#BEE2FF" class="list_th">
						<TD bgcolor="#BEE2FF" WCMAnt:param="filter_option_order.jsp.optionname">选项名称</TD>
						<TD bgcolor="#BEE2FF" WCMAnt:param="filter_option_order.jsp.order">排序</TD>
					</TR>
					<tbody id="table_tbody">
		<%
			 for(int i=0;i<oFilterOptions.size();i++){
				 FilterOption filterOption = (FilterOption)oFilterOptions.getAt(i);
				 int nOptionId = filterOption.getId();
				 int nOrder = filterOption.getOrder();
		%>
					<TR class="list_tr" id="tr_<%=nOptionId%>">
						<TD align="center"><%=CMyString.transDisplay(filterOption.getOptionName())%></TD>
						<TD align="center"><input type="text" name="orderText" id="orderText_<%=nOptionId%>" value="<%=nOrder%>" class="orderText" _id="<%=nOptionId%>" rowNum ="<%=nOrder%>" _value="<%=nOrder%>"/>
						</TD>
					</TR>
		<%
			}
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

window.m_cbCfg = {
	btns : [
		{
			text : '关闭',
			cmd : function(){
				var cbr = wcm.CrashBoarder.get(window);
				cbr.notify();
				cbr.hide();
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
		if(newValue > this.count) newValue = (this.count);
		if(newValue < 1) newValue = 1;
		dom.value = newValue;
		//发送请求保存顺序
		var oPostData = {
			ObjectId : dom.getAttribute("_id"),
			FOrder : newValue
		}
		BasicDataHelper.call("wcm61_filtercenter", 'saveFilterOption', oPostData, true, function(transport, json){
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
</script>
</BODY>
</HTML>