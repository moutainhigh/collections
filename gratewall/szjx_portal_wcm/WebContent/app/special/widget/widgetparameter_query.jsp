<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameter" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameters" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameterType" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameterTypes" %>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetConstants" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@include file="../../include/list_base.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/convertor_helper.jsp"%>

<%!
	private String getWidgetParamTypeDesc(int _nWidgetParamType) throws WCMException{
		WidgetParameterTypes m_oWidgetParameterType = WidgetConstants.WidgetParameter_TYPES;
		WidgetParameterType currWidgetParameterType = m_oWidgetParameterType.getWidgetParameterTypeById(_nWidgetParamType);
		if(currWidgetParameterType == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("widgetparameter_query.null","变量类型对象为空."));
		}
		String sWidgetParamTypeDesc = currWidgetParameterType.getWidgetParameterTypeDesc();
		return sWidgetParamTypeDesc;
	}

	private boolean isEnumValueType(int _ParamType){
		if(_ParamType == WidgetConstants.FIELD_TYPE_RADIO || _ParamType == WidgetConstants.FIELD_TYPE_SELECT || _ParamType == WidgetConstants.FIELD_TYPE_CHECKBOX || _ParamType == WidgetConstants.FIELD_TYPE_INPUT_SELECT || _ParamType == WidgetConstants.FIELD_TYPE_SUGGESTION)
			return true;
		return false;
	}

	private String getDisplayValue(WidgetParameter oWidgetParameter, String _sDefaultValue)throws WCMException{
		if(oWidgetParameter==null)return "";
		String sEnumValue = oWidgetParameter.getEnmvalue();
		if(CMyString.isEmpty(sEnumValue))return "";
		String[] items = sEnumValue.split("~");
		for (int i = 0; i < items.length; i++) {
			String[] item = items[i].split("`");
			String sParamValue = item.length>1?item[1]:item[0];
			if(_sDefaultValue.equals(sParamValue))
				return item[0];
		}
		return "";
	}
%>

<%
	WidgetParameters objs = (WidgetParameters)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	boolean bCanAdd = SpecialAuthServer.hasRight(loginUser, Widget.createNewInstance(), SpecialAuthServer.WIDGET_ADD);
	response.setHeader("bCanAdd",  String.valueOf(bCanAdd));
	String sHeadDisplayValue = WidgetConstants.FIEXED_PARAMETERS[2][4];
	int nHeadDisplayId = 0 ;
%>

<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td width="40" WCMAnt:param="list.selectall" id="selAll" class="selAll">全选</td>
		<td width="100" WCMAnt:param="widgetparameter_query.name">资源变量名称</td>
		<td ><span WCMAnt:param="widgetparameter_query.name.show">资源变量显示名称</span></td>
		<td width="100" WCMAnt:param="widgetparameter_query.name.type">资源变量类型</td>
		<td width="140" WCMAnt:param="widgetparameter_query.name.acquiesce">资源变量默认值</td>
		<td width="40" WCMAnt:param="widgetparameter_query.order">排序</td>
		<td width="40" WCMAnt:param="widgetparameter_query.modify">修改</td>
		<td width="40" WCMAnt:param="widgetparameter_query.delete">删除</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(), j = 0; i <= currPager.getLastItemIndex(); i++) {
		try{
			WidgetParameter obj = (WidgetParameter)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			String sWidgetParamName = CMyString.transDisplay(obj.getWidgetParamName());
			if(WidgetConstants.FIEXED_PARAMETERS[2][0].equals(sWidgetParamName)){
				nHeadDisplayId = nRowId;
				sHeadDisplayValue = CMyString.transDisplay(obj.getDefaultValue());
			}
			//几个固有变量不需要列出来
			if(WidgetConstants.isFixedParameter(sWidgetParamName))continue;

			String sWidgetParamDesc = CMyString.transDisplay(obj.getWidgetParamDesc());
			int nWidgetParamType = obj.getWidgetParamType();
			String sWidgetParamTypeDesc = getWidgetParamTypeDesc(nWidgetParamType);
			int nOrder = obj.getOrder();
			String sDefaultValue = CMyString.transDisplay(obj.getDefaultValue());
			if(nWidgetParamType == WidgetConstants.FIELD_TYPE_TRUEORFALSE && !CMyString.isEmpty(sDefaultValue)){
				sDefaultValue = "0".equals(sDefaultValue)?LocaleServer.getString("widgetparameter_query.no","否"):LocaleServer.getString("widgetparameter_query.yes","是");
			}else if(isEnumValueType(nWidgetParamType)){
				sDefaultValue = getDisplayValue(obj,sDefaultValue);
			}
			//是否需要权限的处理
			boolean bCanEdit = SpecialAuthServer.hasRight(loginUser, obj,
                SpecialAuthServer.WIDGET_EDIT);
			boolean bCanDelete = SpecialAuthServer.hasRight(loginUser, obj,
                SpecialAuthServer.WIDGET_DELETE);
			String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
			j++;

%>

	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" itemId="<%=nRowId%>" class="grid_row">

		<td style="text-align:center;"><input type="checkbox" id="cbx_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" /></td>
		<td id="widgetparamname_<%=nRowId%>" style="text-align:center;">
				<%=sWidgetParamName%></td>
		<td id="widgetparamdesc_<%=nRowId%>" style="text-align:left;padding-left:10px">
				<%=sWidgetParamDesc%></td>
		<td id="widgetparamtype_<%=nRowId%>" style="text-align:center;">
				<%=sWidgetParamTypeDesc%></td>
		<td id="defaultvalue_<%=nRowId%>" style="text-align:center;">
				<%=sDefaultValue%></td>
		<td style="text-align:center;"><input type="text" class="order-cls" name="order" rowid="<%=nRowId%>" value="<%=j%>" _value="<%=j%>" /></td>
		<td><span class="object_edit <%=bCanEdit?"":"disableCls"%>" style="display:inline-block;" cmd="edit">&nbsp;</span></td>
		<td><span class="object_delete <%=bCanDelete?"":"disableCls"%>" style="display:inline-block;" cmd="delete">&nbsp;</span></td>

	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	
</table>
<table border="0" cellspacing="0" cellpadding="0" style="margin-top:3px;">
<tbody>
	<tr><td style="text-align:left;padding-left:10px;" WCMAnt:param="widgetparameter_query.head">显示资源头部：<input type="radio" name="HEADDISPLAY" id="HEADDISPLAY_1" objId="<%=nHeadDisplayId%>" value="1" <%="1".equals(sHeadDisplayValue)?"checked":""%>/ WCMAnt:param="widgetparameter_query.yes">是 <input type="radio" name="HEADDISPLAY" id="HEADDISPLAY_0" value="0" <%="0".equals(sHeadDisplayValue)?"checked":""%>/ WCMAnt:param="widgetparameter_query.no">否</td></tr>
</tbody>
</table>