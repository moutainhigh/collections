<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataDefCacheMgr"%>
<%!
	//处理视图选择
	private String dealWithSelMetaViewField(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		/**实现步骤*/
		//1 获取对象初始值_currWidgetParameter.getDefaultValue()；通过_bAdd可以查询到已经选择的值。
		//   获取资源变量名称_currWidgetParameter.getWidgetParamName();
		String sParamName = CMyString.filterForHTMLValue(_currWidgetParameter.getWidgetParamName());
		String sDefaultValue = _currWidgetParameter.getDefaultValue();
		String sParameterValue;
		if(_bAdd){
			sParameterValue = sDefaultValue;
		}else{
			sParameterValue = getParameterValue(oWidgetInstance, _currWidgetParameter);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<DIV class='metaViewField'><INPUT id='" + sParamName + "'");
		sb.append("' type=hidden value='" + CMyString.filterForHTMLValue(sParameterValue) + "' name='" + sParamName + "' bName='true'");
		sb.append(" validation=\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80'\"");
		sb.append("/>");
		sb.append("<DIV class=viewField-select id='viewFieldNameSelectBtn-"+sParamName+"' onclick=\"selectMetaView('"+sParamName+"')\">" + "</DIV>");
		sb.append("<DIV class=viewFieldName-text id='viewFieldNameText-"+sParamName+"'>" + CMyString.transDisplay(sParameterValue) + "</DIV></DIV>");
		return sb.toString();
	}

%>

<style type="text/css">
.metaViewField .viewField-select{
	width:16px;
	height:20px;
	cursor:pointer;
	background:url(../images/region_select.gif) center center no-repeat;
}

.viewFieldName-text{
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
function selectMetaView(sParamName){
	var viewObj = document.getElementsByClassName('ViewNameDv');
	if(viewObj.length !=1) {alert("此资源块没有包含视图或者视图数量大于1。"); return;}
	var currViewID = Element.first(viewObj[0]).value;
	if(!currViewID){
		alert("没有选择视图！");
		return;
	}
	var currViewFieldName = $('viewFieldNameText-'+sParamName).innerHTML;
	var currViewFieldIdEl = $(sParamName);
	var currViewFieldId = currViewFieldIdEl.value || 0;
	var srcUrl = WCMConstants.WCM6_PATH + 'metaviewfield/viewfieldforwidget_select_list.jsp';
	var inputElValue = currViewFieldIdEl.value;
	wcm.CrashBoarder.get('select_viewField').show({
		title : '选择视图字段',
		src : srcUrl,
		width:'750px',
		height:'400px',
		params : {ViewId:currViewID, ViewFieldName:currViewFieldId},
		maskable:true,
		callback : function(params){
			debugger;
			var currViewFieldId = params['fieldNames'][0];
			var sViewName = params['anotherFieldNames'][0];
			$('viewFieldNameText-'+sParamName).innerHTML = currViewFieldId;
			currViewFieldIdEl.value = currViewFieldId;
			
			this.close();
		}
	});
	
}	
//-->
</script>