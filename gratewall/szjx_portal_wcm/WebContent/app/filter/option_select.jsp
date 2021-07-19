<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.filter.FilterOption" %>
<%@ page import="com.trs.components.wcm.filter.FilterOptions" %>
<%@ page import="java.util.HashMap"%>
<%@include file="../include/public_processor.jsp"%>
<%
	String sOptionId = processor.getParam("OptionId");
	int nOptionId = Integer.parseInt(sOptionId);
	FilterOption currOption = FilterOption.findById(nOptionId);
	String sSelectedOptionIds = currOption.getNotAllowedSelectOptions();
	int[] selectedOptionIds = null;
	if(!CMyString.isEmpty(sSelectedOptionIds)){
		selectedOptionIds = CMyString.splitToInt(sSelectedOptionIds,",");
	}
	FilterOptions options = (FilterOptions)processor.excute("wcm61_filtercenter", "queryOtherOptions");
%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="option_select.jsp.selectoption"> 选择选项 </title>
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
		// 自定义 crashboard 的按钮
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						var getSelectedOptionIds = getSelectedValues();
						var cbr = wcm.CrashBoarder.get(window);
						cbr.notify(getSelectedOptionIds);
						cbr.hide();
						return false;
					}
				},
				{
					text : '取消'
				}
			]
		};
		//添加、删除option
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
						//构造未被使用的选项列表
						for(int i=0; i<options.size();i++){
							FilterOption option = (FilterOption)options.getAt(i);
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
					<input WCMAnt:paramattr="value:option_select.jsp.add" type="button" class="button" value="添加 &gt;&gt;" style="width:80px" onclick="moveOptions('OptionsForSelect','OptionsSelected')">
					<br>
					<br>
					<input WCMAnt:paramattr="value:option_select.jsp.move" type="button" class="button" value="&lt;&lt; 移除" style="width:80px" onclick="moveOptions('OptionsSelected','OptionsForSelect')">
				</div>
		</td>
			<td width="270">
				<div style="height:330px;overflow:hidden;width:270px;">
					<select name="OptionsSelected" id="OptionsSelected" style="width:270px;height:330px;" multiple>
					<%
							//输出已经使用的列表
							if(selectedOptionIds != null){
								for(int i=0; i < selectedOptionIds.length;i++){
									int nOptionSelectedId = selectedOptionIds[i];
									FilterOption optionSelected = FilterOption.findById(nOptionSelectedId);
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