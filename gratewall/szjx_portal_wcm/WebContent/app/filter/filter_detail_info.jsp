<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.filter.Filter" %>
<%@ page import="com.trs.components.wcm.filter.Filters" %>
<%@ page import="com.trs.components.wcm.filter.FilterOptionGroup" %>
<%@ page import="com.trs.components.wcm.filter.FilterOptionGroups" %>
<%@ page import="com.trs.components.wcm.filter.FilterOptions" %>
<%@ page import="com.trs.components.wcm.filter.FilterOption" %>
<%@ page import="java.util.HashMap"%>
<%@include file="../include/public_processor.jsp"%>
<%
	String sFilterId = processor.getParam("FilterId");
	int nFilterId = Integer.parseInt(sFilterId);
	Filter currFilter = Filter.findById(nFilterId);
	if(currFilter == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("filter_detail_info.jsp.notfindfilterid", "没有找到ID为{0}的筛选器！"), new int[]{nFilterId}));
	}
	String sFilterName = currFilter.getFilterName();
%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="filter_detail_info.jsp.filterdetailmaintainpage"> 筛选器详细维护页面 </title>
	<script src="../../app/js/easyversion/cssrender.js"></script>
	<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/simplemenu/resource/SimpleMenu.css" rel="stylesheet" type="text/css" />
	<link href="./filter_detail_info.css" rel="stylesheet" type="text/css">
	<script src="../../app/js/easyversion/lightbase.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/easyversion/extrender.js"></script>
	<script src="../../app/js/easyversion/elementmore.js"></script>
	<script src="../../app/js/easyversion/ajax.js"></script>
	<script src="../../app/js/easyversion/basicdatahelper.js"></script>
	<script src="../../app/js/easyversion/web2frameadapter.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/source/wcmlib/Observable.js"></script>
	<script src="../../app/js/source/wcmlib/simplemenu/SimpleMenu.js"></script>
	<!--validator-->
	<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<!--wcm-dialog start-->
	<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<script src="../../app/js/source/wcmlib/Component.js"></script>
	<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
	<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
	<script src="./filter_detail_info.js"></script>
	<script language="javascript">
	<!--
		var m_nFilterId = <%=nFilterId%>;
	//-->
	</script>
</head>
<body>
<div class="container" id="container">
	<div class="header" id="<%=nFilterId%>" relationMenu="showFilterMenu">
		<span class="titlesp" WCMAnt:param="filter_detail_info.jsp.filtername">筛选器名称【<%=CMyString.transDisplay(sFilterName)%>】</span>
		<div class="tipinfo" id="operationTip">
			<DIV style="FLOAT: left" WCMAnt:param="filter_detail_info.jsp.youcanclicktitlemodifyitemrightkeymoreoperateenternext">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您可以单击标题进行修改，在相应的条目右键进行更多操作，选项回车新建下一个！</DIV>
			<DIV style="FLOAT: right"><A title="我知道了，以后不再显示！" WCMAnt:paramattr="title:iknowneverdisplay" onclick="closeTip();return false;" href="#" WCMAnt:param="filter_detail_info.jsp.iknow">我知道了》</A>&nbsp;&nbsp;</DIV>
		</div>
	</div>
	<div class="content" id="content">
<%

	HashMap parameters = new HashMap();
	parameters.put("ObjectId", String.valueOf(nFilterId));
	FilterOptionGroups oOptionGroups = (FilterOptionGroups)processor.excute("wcm61_filtercenter", "queryFilterOptionGroupsByFilter", parameters);
	for(int nGroupIndex=0;nGroupIndex<oOptionGroups.size();nGroupIndex++){
		FilterOptionGroup oOptionGroup = (FilterOptionGroup)oOptionGroups.getAt(nGroupIndex);
		if(oOptionGroup == null)continue;
		String sGroupName = oOptionGroup.getOptionGroupName();
%>
			<!--构造选项组信息的开始-->
			<div class="optionGroup" id="<%=oOptionGroup.getId()%>">
				<fieldset class="groupFieldSet">
					<legend>
						<span class="grouptitlesp" relationMenu="showGroupMenu" title="<%=CMyString.filterForHTMLValue(CMyString.showNull(oOptionGroup.getOptionGroupDesc()))%>"><%=CMyString.transDisplay(sGroupName)%></span>
					</legend>
<%
					//获取选项信息
					int nOptionGroupId = oOptionGroup.getId();
					parameters.put("ObjectId", String.valueOf(nOptionGroupId));
					FilterOptions oFilterOptions = (FilterOptions)processor.excute("wcm61_filtercenter", "queryFilterOptionsByGroup", parameters);
					for(int nOptionIndex=0;nOptionIndex<oFilterOptions.size();nOptionIndex++){
						FilterOption oOption = (FilterOption)oFilterOptions.getAt(nOptionIndex);
						if(oOption == null)continue;
						String sOptionName = oOption.getOptionName();
						String sSearchCondition = CMyString.showNull(oOption.getSearchCondition());
%>
						<!--构造选项信息-->
						<li class="optionBox" id="<%=oOption.getId()%>" relationMenu="showOptionMenu" searchCondition="<%=CMyString.filterForHTMLValue(sSearchCondition)%>">
							<span>
								<input type="text" value="<%=CMyString.filterForHTMLValue(sOptionName)%>" name="optionName" class="optionname_text" _value="<%=CMyString.filterForHTMLValue(sOptionName)%>" title="<%=CMyString.filterForHTMLValue(CMyString.showNull(oOption.getOptionDesc()))%>"/>
							</span>
						</li>
<%
					}//end for Option
%>
			
			<!--构造选项组信息的结束-->
				</fieldset>
			</div>
<%
		}//end for OptionGroup	
%> 
	</div>
</div>
<div id="optionGroupTemplate" style="display:none;">
	<div class="optionGroup" id="0">
		<fieldset class="groupFieldSet">
			<legend>
				<span class="grouptitlesp" relationMenu="showGroupMenu" title=""><input type='text' value='' _value='' class='titleinput' onblur='PageHandler.saveTitle(this);return false;'/></span>
			</legend>
		</fieldset>
	</div>
</div>
<div id="optionTemplate" style="display:none;">
	<li class="optionBox" id="0" relationMenu="showOptionMenu" searchCondition="">
		<span>
			<input type="text" value="" _value=""  name="optionName" class="optionname_text" onblur="optionBlur(this);return false;" onfocus="optionFocus(this);return false;" title=""/>
		</span>
	</li>
</div>
<div id="inputarea" style="display:none;">
	<div style="margin-top:10px;">         
		<textarea rows="8" cols="40">{0}</textarea>
	</div>
</div>
</body>
</html>