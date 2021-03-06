<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.advisor.OptionGroup" %>
<%@ page import="com.trs.components.wcm.advisor.Options" %>
<%@ page import="com.trs.components.wcm.advisor.Option" %>
<%@ page import="java.util.HashMap"%>
<%@include file="../include/public_processor.jsp"%>
<%
	String sOptionId = processor.getParam("OptionId");
	String sGroupId = processor.getParam("GroupId");
	String sSelectFromOtherSteps = processor.getParam("SelectFromOtherSteps");
	int nOptionId = 0;
	int nGroupId = 0;
	int nStepId = 0;
	String sHasSelectedOptionIds = "";
	if("true".equalsIgnoreCase(sSelectFromOtherSteps)){
		if(!CMyString.isEmpty(sOptionId)){
			nOptionId = Integer.parseInt(sOptionId);
			Option option = Option.findById(nOptionId);
			if(option != null){
				sHasSelectedOptionIds = option.getLastOptions();
				nStepId = option.getStepId();
			}
		}else if(!CMyString.isEmpty(sGroupId)){
			nGroupId = Integer.parseInt(sGroupId);
			OptionGroup optionGroup = OptionGroup.findById(nGroupId);
			if(optionGroup != null){
				sHasSelectedOptionIds = optionGroup.getLastOptions();
				nStepId = optionGroup.getStepId();
			}
		}
	}else{
		Option option = Option.findById(Integer.parseInt(sOptionId));
		sHasSelectedOptionIds = option.getNotAllowedSelectOptions();
	}	
	int[] selectedOptionIds = null;
	if(!CMyString.isEmpty(sHasSelectedOptionIds)){
		selectedOptionIds = CMyString.splitToInt(sHasSelectedOptionIds, ",");
	}

	Options options = new Options(loginUser);
	if("true".equalsIgnoreCase(sSelectFromOtherSteps)) {
		HashMap parameters = new HashMap();
		parameters.put("StepId",String.valueOf(nStepId));
		options = (Options)processor.excute("wcm61_advisorcenter", "queryOptionsOtherSteps",parameters);
	}else{
		options = (Options)processor.excute("wcm61_advisorcenter", "queryOtherOptionsInOneGroup");
	}
		

%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="option_select.jsp.optionselect"> ???????????? </title>
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
	<!--wcm-dialog start-->
	<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<script src="../../app/js/source/wcmlib/Component.js"></script>
	<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
	<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
	<script language="javascript">

	<!--
		// ????????? crashboard ?????????
		window.m_cbCfg = {
			btns : [
				{
					text : '??????',
					cmd : function(){
						var getSelectedOptionIds = getSelectedValues();
						var cbr = wcm.CrashBoarder.get(window);
						cbr.notify(getSelectedOptionIds);
						cbr.hide();
						return false;
					}
				},
				{
					text : '??????'
				}
			]
		};
		//???????????????option
		function moveOptions(_oSelect1, _oSelect2){
			_oSelect1=$(_oSelect1);
			_oSelect2=$(_oSelect2);
			cancelSelected(_oSelect2);
			var oOption;
			var optionlength = _oSelect1.options.length;
			for(var i=0;i<optionlength;i++){
				oOption = _oSelect1.options[i];
				if(oOption && oOption.selected){
					var oNewOption = document.createElement("OPTION");
					_oSelect2.appendChild(oNewOption);
					oNewOption.value = oOption.value;
					oNewOption.innerText = oOption.innerText;	
				}
			}
			for(var k= optionlength-1; k>=0; k--){
				oOption = _oSelect1.options[k];
				if(oOption && oOption.selected){
					_oSelect1.removeChild(oOption);
					delete _oSelect1;
				}
			}
		}
		function cancelSelected(_oSelect){
			for(var k=0;k<_oSelect.options.length;k++){
				if(_oSelect.options[k].selected){
					_oSelect.options[k].selected = false;
				}
			}
		}
		function getSelectedValues(){
			var optionsSelected = $('OptionsSelected').options;
			var result = [];
			for(var i=0;i<optionsSelected.length;i++){
				var optionSelected = optionsSelected[i];
				result.push(optionSelected.value);
			}
			return result.join(",");
		}
	//-->
	</script>
</head>
<body>
<div id="container" style="height:100%;width:100%;">
	<table border="0" cellspacing="0" cellpadding="0" height="100%" width="100%">
	<tbody>
		<tr>
			<td width="270">
				<div style="height:330px;overflow:hidden;width:270px;">
					<select name="OptionsForSelect" id="OptionsForSelect" style="width:270px;height:330px;" multiple>
					<%
						//?????????????????????????????????
						for(int i=0; i<options.size();i++){
							Option option = (Option)options.getAt(i);
							if(option == null)continue;
							int nCurrOptionId = option.getId();
							boolean bHasUsed = false;
							if(selectedOptionIds != null){
								for(int k=0;k<selectedOptionIds.length;k++){
									if(nCurrOptionId == selectedOptionIds[k]){
										bHasUsed = true;
										break;
									}
								}
							}
							if(bHasUsed)continue;
					%>
						<option value="<%=nCurrOptionId%>"><%=CMyString.transDisplay(option.getOptionName())%></option>
					<%
						}	
					%>
					</select>
				</div>
			</td>
			<td width="90" align="center">
				<div style="margin-top:25px; text-align:center; width:90px;">
					<input type="button" class="button" value="<%=LocaleServer.getString("option.select.jsp.addbtn","?????? >>")%>" style="width:90px" onclick="moveOptions('OptionsForSelect','OptionsSelected')">
					<br>
					<br>
					<input type="button" class="button" value="<%=LocaleServer.getString("option.select.jsp.removebtn","<< ??????")%>" style="width:90px" onclick="moveOptions('OptionsSelected','OptionsForSelect')">
				</div>
		</td>
			<td width="270">
				<div style="height:330px;overflow:hidden;width:270px;">
					<select name="OptionsSelected" id="OptionsSelected" style="width:270px;height:330px;" multiple>
					<%
							//???????????????????????????
							if(selectedOptionIds != null){
								for(int i=0; i < selectedOptionIds.length;i++){
									int nOptionSelectedId = selectedOptionIds[i];
									Option optionSelected = Option.findById(nOptionSelectedId);
									if(optionSelected == null)continue;
					%>
								<option value="<%=optionSelected.getId()%>"><%=CMyString.transDisplay(optionSelected.getOptionName())%></option>
					<%	
								}
							}
					%>
					</select>
				</div>
			</td>
		</tr>
	</tbody>
	</table>
</div>
</body>
</html>