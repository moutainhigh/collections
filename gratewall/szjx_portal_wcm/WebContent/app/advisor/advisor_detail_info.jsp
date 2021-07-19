<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.advisor.Advisor" %>
<%@ page import="com.trs.components.wcm.advisor.Advisors" %>
<%@ page import="com.trs.components.wcm.advisor.Steps" %>
<%@ page import="com.trs.components.wcm.advisor.Step" %>
<%@ page import="com.trs.components.wcm.advisor.OptionGroups" %>
<%@ page import="com.trs.components.wcm.advisor.OptionGroup" %>
<%@ page import="com.trs.components.wcm.advisor.Options" %>
<%@ page import="com.trs.components.wcm.advisor.Option" %>
<%@ page import="java.util.HashMap"%>
<%@include file="../include/public_processor.jsp"%>
<%
	String sAdvisorId = processor.getParam("AdvisorId");
	int nAdvisorId = Integer.parseInt(sAdvisorId);
	Advisor currAdvisor = Advisor.findById(nAdvisorId);
	if(currAdvisor == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("advisor_detail_info.jsp.notfindadvisorid", "没有找到ID为{0}的顾问！"), new int[]{nAdvisorId}));
	}
	String sAdvisorName = currAdvisor.getAdvisorName();
%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="advisor_detail_info.jsp.advisordetailmaintainpage"> 顾问详细维护页面 </title>
	<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/simplemenu/resource/SimpleMenu.css" rel="stylesheet" type="text/css" />
	<link href="./advisor_detail_info.css" rel="stylesheet" type="text/css">
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
	<script src="./advisor_detail_info.js"></script>
	<script language="javascript">
	<!--
		var m_nAdvisorId = <%=nAdvisorId%>;
	//-->
	</script>
</head>
<body>
<div class="container" id="container">
	<div class="header" id="<%=nAdvisorId%>" relationMenu="showAdvisorMenu">
		<span class="titlesp" WCMAnt:param="advisor_detail_info.jsp.advisorname">顾问名称【<%=CMyString.transDisplay(sAdvisorName)%>】</span>
		<div class="tipinfo" id="operationTip">
			<DIV style="FLOAT: left" WCMAnt:param="advisor_detail_info.jsp.youcanclicktitlemodifymoreenternext">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您可以单击标题进行修改，在相应的条目右键进行更多操作，选项回车新建下一个！</DIV>
			<DIV style="FLOAT: right"><A  WCMAnt:paramattr="title:advisor_detail_info.jsp.iknowneverdisplay" title="我知道了，以后不再显示！" onclick="closeTip();return false;" href="#"  WCMAnt:param="advisor_detail_info.jsp.iknow">我知道了》</A>&nbsp;&nbsp;</DIV>
		</div>
	</div>
	<div class="content" id="content">
<%

	HashMap parameters = new HashMap();
	parameters.put("ObjectId", String.valueOf(nAdvisorId));
	Steps oSteps = (Steps)processor.excute("wcm61_advisorcenter", "queryStepsByAdvisor", parameters);
	for(int nStepIndex=0;nStepIndex<oSteps.size();nStepIndex++){
		Step oStep = (Step)oSteps.getAt(nStepIndex);
		if(oStep == null)continue;
		String sStepName = oStep.getStepName();
		boolean bMultiSelect = oStep.isMultiSelect();
		String sSelectTypeDesc = bMultiSelect ? Step.getSelectTypeString(Step.MULTI_SELECT_TYPE) : Step.getSelectTypeString(Step.SINGLE_SELECT_TYPE);
%>
		<!--构造步骤信息的开始-->
		<div class="advisorstep" id="<%=oStep.getId()%>">
			<div class="stepheader" relationMenu="showStepMenu">
				<span class="steptitlesp" relationHandler="editTitle" title="<%=CMyString.filterForHTMLValue(CMyString.showNull(oStep.getStepDesc()))%>"><%=CMyString.transDisplay(sStepName)%></span>
				<span onclick="PageHandler.toogleDataType(this);return false;" class="selecttypesp">
					<span class="oldSelectType"><%=CMyString.transDisplay(sSelectTypeDesc)%></span>
					<span style="display:none" class="selType">
						<select name='selfieldtype' class="selfieldtype" onchange="PageHandler.changeType(this);return false;">
							<option value="0"  <%=bMultiSelect ? "" : "selected"%>><%=Step.getSelectTypeString(Step.SINGLE_SELECT_TYPE)%></option>
							<option value="1" <%=bMultiSelect ? "selected" : ""%>><%=Step.getSelectTypeString(Step.MULTI_SELECT_TYPE)%></option>
						</select> 
					</span>
				</span>
			</div>

<%
		//获取选项组信息
		int nStepId = oStep.getId();
		parameters.put("ObjectId", String.valueOf(nStepId));
		OptionGroups oOptionGroups = (OptionGroups)processor.excute("wcm61_advisorcenter", "queryOptionGroupsByStep", parameters);
		for(int nOptionGroupIndex=0;nOptionGroupIndex<oOptionGroups.size();nOptionGroupIndex++){
			OptionGroup oOptionGroup = (OptionGroup)oOptionGroups.getAt(nOptionGroupIndex);
			if(oOptionGroup == null)continue;
			String sOptionGroupName = oOptionGroup.getOptionGroupName();
%>
			<!--构造选项组信息的开始-->
			<div class="optionGroup" id="<%=oOptionGroup.getId()%>">
				<fieldset class="groupFieldSet">
					<legend>
						<span class="grouptitlesp" relationHandler="editTitle" relationMenu="showGroupMenu" title="<%=CMyString.filterForHTMLValue(CMyString.showNull(oOptionGroup.getOptionGroupDesc()))%>"><%=CMyString.transDisplay(sOptionGroupName)%></span>
					</legend>
<%
					//获取选项信息
					int noOptionGroupId = oOptionGroup.getId();
					parameters.put("ObjectId", String.valueOf(noOptionGroupId));
					Options oOptions = (Options)processor.excute("wcm61_advisorcenter", "queryOptionsByGroup", parameters);
					for(int nOptionIndex=0;nOptionIndex<oOptions.size();nOptionIndex++){
						Option oOption = (Option)oOptions.getAt(nOptionIndex);
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
		<!--构造步骤信息的结束-->
		</div>
<%
	}//end for steps
%> 
	</div>
</div>
<div id="stepTemplate" style="display:none;">
	<div class="advisorstep"  id="0">
		<div class="stepheader" relationMenu="showStepMenu">
			<span class="steptitlesp" relationHandler="editTitle" title=""><input type='text' value='' _value='' class='titleinput' onblur='PageHandler.saveTitle(this);return false;'/></span>
			<span onclick="PageHandler.toogleDataType(this);return false;" class="selecttypesp">
				<span class="oldSelectType"><%=Step.getSelectTypeString(Step.SINGLE_SELECT_TYPE)%></span>
				<span style="display:none" class="selType">
					<select name='selfieldtype' class="selfieldtype" onchange="PageHandler.changeType(this);return false;">
						<option value="0"><%=Step.getSelectTypeString(Step.SINGLE_SELECT_TYPE)%></option>
						<option value="1"><%=Step.getSelectTypeString(Step.MULTI_SELECT_TYPE)%></option>
					</select> 
				</span>
			</span>
		</div>
	</div>
</div>
<div id="optionGroupTemplate" style="display:none;">
	<div class="optionGroup" id="0">
		<fieldset class="groupFieldSet">
			<legend>
				<span class="grouptitlesp" relationHandler="editTitle" relationMenu="showGroupMenu" title=""><input type='text' value='' _value='' class='titleinput' onblur='PageHandler.saveTitle(this);return false;'/></span>
			</legend>
		</fieldset>
	</div>
</div>
<div id="optionTemplate" style="display:none;">
	<li class="optionBox" id="0" relationMenu="showOptionMenu" searchCondition="">
		<span>
			<input type="text" value="" _value=""  title="" name="optionName" class="optionname_text" onblur="optionNameBlur(this);return false;" onfocus="optionNameFocus(this);return false;"/>
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