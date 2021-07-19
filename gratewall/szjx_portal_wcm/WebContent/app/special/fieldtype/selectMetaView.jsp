<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataDefCacheMgr"%>
<%!
	//处理视图选择
	private String dealWithMetaView(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		String sDefaultValue = _currWidgetParameter.getDefaultValue();
		String sParameterValue;
		if(_bAdd){
			sParameterValue = sDefaultValue;
		}else{
			sParameterValue = getParameterValue(oWidgetInstance, _currWidgetParameter);
		}
		
		//初始化nCurrViewInfoId变量
		int nCurrViewInfoId = 0;
		if(!CMyString.isEmpty(sParameterValue)){
			nCurrViewInfoId = Integer.parseInt(sParameterValue);
		}
		String sParamName = CMyString.filterForHTMLValue(_currWidgetParameter.getWidgetParamName());
		MetaView metaView = MetaView.findById(nCurrViewInfoId);
		String sAnatherName =  "";
		if(metaView != null)
			sAnatherName = metaView.getDesc();
		StringBuffer sb = new StringBuffer();
		sb.append("<DIV class='ViewNameDv'><INPUT id='" + sParamName + "'");
		sb.append("' type=hidden value='" + CMyString.filterForHTMLValue(sParameterValue) + "' name='" + sParamName + "' bName='true'");
		sb.append(" validation=\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80'\"");
		sb.append("/>");
		sb.append("<DIV class=view-select id='viewNameSelectBtn-"+sParamName+"' onclick=\"selectView('"+sParamName+"')\">" + "</DIV>");
		sb.append("<DIV class=viewName-text id='viewNameText-"+sParamName+"'>" + CMyString.transDisplay(sAnatherName) + "</DIV></DIV>");
		return sb.toString();
	}

%>

<style type="text/css">
.ViewNameDv .view-select{
	width:16px;
	height:20px;
	cursor:pointer;
	background:url(../images/region_select.gif) center center no-repeat;
}

.viewName-text{
	 
}
.fieldNameDv .field-select{
	width:16px;
	height:20px;
	cursor:pointer;
	background:url(../images/region_select.gif) center center no-repeat;
}
.fieldName-text{
	 
}

.base_block_content{
	padding-top:10px;
}
.label{
	width:120px;
}
.value{
	margin-left:130px;
}
.widthsDv{
	padding-top:3px;
}
.fieldNameDv{
	padding-top:3px;
}
.linkFieldNameDv{
	padding-top:3px;
}
#.orderNums{
	padding-top:3px;
}
</style>


<script language="javascript">
<!--
function selectView(sParamName){
	var currViewName = $('viewNameText-'+sParamName).innerHTML;
	var currViewIdEl = $(sParamName);
	var currViewId = currViewIdEl.value || 0;
	var srcUrl = WCMConstants.WCM6_PATH + 'metaview/view_select_list.html';
	var inputElValue = currViewIdEl.value;
	wcm.CrashBoarder.get('select_view').show({
		title : '选择视图',
		src : srcUrl,
		width:'750px',
		height:'400px',
		params : {selectIds:currViewId},
		maskable:true,
		callback : function(params){
			var nViewId = params['ViewId'];
			var sViewName = params['selectedNames'];
			$('viewNameText-'+sParamName).innerHTML = sViewName;
			currViewIdEl.value = nViewId;
			window.globlaViewID = nViewId;
			if(nViewId != inputElValue){
				/*
				$('列表宽度').value = "";
				$('列表显示字段').value = "";
				$('链接字段').value = "";
				$('显示字数').value = "";
				$('fieldNameTextDv').innerText = "";
				$('widthsTextDv').innerText = "";
				$('linkfieldNameTextDv').innerText = "";
				$('displayNumDv').innerText = "";
				*/
			}
			this.close();
		}
	});
}	
//-->
</script>