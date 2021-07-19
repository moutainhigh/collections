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
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("filter_option_group_properties.jsp.found", "没有找到ID为{0}的选项组！"), new int[]{nGroupId}));
	}
	out.clear();
%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="filter_option_group_properties.jsp.title">TRS WCM ::::::筛选器其他属性</title>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css">
<style type="text/css">

html,body{
	padding:0px;
	margin:0px;
	height:100%;
	width:100%;
}
.label{
	width:100px;
	text-align:right;
	padding-right:5px;
}
input{
	border:1px solid silver;
	width:150px;
}
</style>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
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
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<script>
window.m_cbCfg = {
	btns : [
		{
			id : 'my-ok-btn',
			text : '确定',
			cmd : function(){
				//发送请求保存步骤的信息
				BasicDataHelper.call('wcm61_filtercenter', "saveFilterOptionGroup", 'data', true, function(_oTrans, _json){
					var cbr = wcm.CrashBoarder.get(window);
					cbr.close();
				});
				return false;
			}
		},
		{
			text : '关闭'
		}
	]
};

Event.observe(window, 'load', function(){

	//注册校验成功时执行的回调函数
	ValidationHelper.addValidListener(function(){
			wcmXCom.get('my-ok-btn').enable();
	}, "data");

	//注册校验失败时执行的回调函数
	ValidationHelper.addInvalidListener(function(){
			wcmXCom.get('my-ok-btn').disable();
	}, "data");

	//初始化页面中需要校验的元素
	ValidationHelper.initValidation();
});

</script>
</head>
<body>
<form id="data">
<input type="hidden" name="ObjectId" id="ObjectId" value="<%=nGroupId%>" />
<table border="0" cellspacing="2" cellpadding="0" style="width:100%;">
<tbody>
	<tr>
		<td class="label" WCMAnt:param="filter_option_group_properties.jsp.min">拉杆最小值:</td>
		<td>
			<input type="text" name="minValue" id="minValue" value="<%=CMyString.showNull(currGroup.getPropertyAsString("minValue"))%>" validation="type:int;no_desc:'';min:1" />
		</td>
	</tr>
	<tr>
		<td class="label" WCMAnt:param="filter_option_group_properties.jsp.max">拉杆最大值:</td>
		<td>
			<input type="text" name="max_Value" id="max_Value" value="<%=CMyString.showNull(currGroup.getPropertyAsString("max_Value"))%>" validation="type:int;no_desc:'';min:1" />
		</td>
	</tr>
	<tr>
		<td class="label" WCMAnt:param="filter_option_group_properties.jsp.display_column">选项显示列数:</td>
		<td>
			<input type="text" name="columnCount" id="columnCount" value="<%=CMyString.showNull(currGroup.getPropertyAsString("columnCount"))%>" validation="type:int;no_desc:'';min:1" />
		</td>
	</tr>
	<tr>
		<td class="label" WCMAnt:param="filter_option_group_properties.jsp.selction_group_type">选项组类型:</td>
		<td>
			<select name="optGroupType" id="optGroupType" style="width:150px;">
				<option value="0" WCMAnt:param="filter_option_group_properties.jsp.muti">多选</option>
				<option value="1" WCMAnt:param="filter_option_group_properties.jsp.radio">单选</option>
				<option value="2" WCMAnt:param="filter_option_group_properties.jsp.lagan">拉杆</option>
				<option value="3" WCMAnt:param="filter_option_group_properties.jsp.pic">图片</option>
			</select>
			<script language="javascript">
			<!--
				var optGroupType = '<%=currGroup.getPropertyAsString("optGroupType")%>';
				if(optGroupType != ''){
					$('optGroupType').value = optGroupType;
				}
			//-->
			</script>
		</td>
	</tr>
</tbody>
</table>
</form>
</body>
</html>