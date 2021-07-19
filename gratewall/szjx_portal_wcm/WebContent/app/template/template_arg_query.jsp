<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Templates" %>
<%@ page import="com.trs.components.common.publish.domain.template.TemplateArgumentMgr" %>
<%@ page import="com.trs.components.common.publish.domain.tagparser.TemplateParameterList" %>
<%@ page import="com.trs.components.common.publish.domain.tagparser.TemplateParameter" %>
<%@ page import="com.trs.components.common.publish.domain.tagparser.TemplateParameter.Token" %>
<%@ page import="java.util.List" %>
<%@ page import="org.dom4j.DocumentHelper" %>
<%@ page import="org.dom4j.Element" %>
<%@ page import="org.dom4j.Document" %>

<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		<td><span WCMAnt:param="template_arg_list.head.name">名称</span></td>
		<td width="200"><span WCMAnt:param="template_arg_list.head.EnumValue">参数值</span></td>
		<td width="60"><span WCMAnt:param="template_arg_list.head.edit">修改</span> </td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
		Document document = DocumentHelper.parseText(result.toString()); 
        Element root = document.getRootElement();
        List templates = root.elements("Template");
		StringBuffer sbJson = new StringBuffer();
		int num = 0;
		sbJson.append("[null");
        for(int i=0; i<templates.size(); i++){//start for1
		   Element template = (Element)templates.get(i);
		   String templateName = template.attributeValue("Name");
		   String nTemplateId = template.attributeValue("ID");
		   Element argsElement = ((Element) templates.get(i)).element("Args");
		   String sIdentity = template.attributeValue("Identity");
		   String sNesterName = template.attributeValue("NesterName");
           List argElement = argsElement.elements("Arg");
%>
			<tr style="width: 100%; border-bottom: 1px solid gray">
				<td colspan="4" style="PADDING-LEFT: 10px; TEXT-ALIGN: left">
					<span  style="font-size:14px; font-weight: bold; text-align: left; border-right: 0; padding-left: 5px; font-family: Courier New; margin-top: 10px; color: #010101;" align="left">
						<%=i+1%>.&nbsp; <%=templateName%>
						<%if((sIdentity!=null) && (!sIdentity.equals(""))){%>
						<span style="font-weight: normal;">
							（Identity:<span style="color: red; font-weight: bold"><%=sIdentity%></span> , <span WCMAnt:param="template_arg_list.head.beimuban">被模板</span>[<span style="font-weight: bold"><%=sNesterName%></span>]<span WCMAnt:param="template_arg_list.head.qiantao">嵌套</span>）
						</span>
						<% }%>
					</span>&nbsp;							
				</td>
			</tr>

<%
			for(int k=0; k <argElement.size(); k++){//start for2
				num++;
				Element arg = ((Element) argElement.get(k));
				String sArgName = arg.elementText("Name");
				String sType = arg.elementText("Type");
				String sEnumSplit = arg.elementText("EnumSplit");
				String sDefaultValue = arg.elementText("DefaultValue");
				String sPrexName = arg.elementText("PrexName");
				String sValue = arg.elementText("Value");
				String sCanCustom = arg.elementText("CanCustom");
			
				if(sType.equals("BOOLEAN")){
					sValue = (sValue == null || sValue.equals(""))? ((sDefaultValue.equals("是") || sDefaultValue.toUpperCase().equals("TRUE")) ? "1"  : "0") : sValue.trim().toLowerCase();
					if(sValue.equals("1") || "true".equals(sValue)) {
						sValue = (sDefaultValue.equals("是") || sDefaultValue.equals("否")) ? LocaleServer.getString("template.arg.query.booleantrue","是"): "true" ;
					}
					else sValue =  (sDefaultValue.equals("是") || sDefaultValue.equals("否")) ? LocaleServer.getString("template.arg.query.booleanfalse","否") : "false";
				}
				String sTrueDefaultVlaue = dealWithDefaultValue(sDefaultValue,sEnumSplit);
				if(sValue==null || sValue.equals(""))sValue = sTrueDefaultVlaue;
				String sExtraAttrs = " _fieldName='ArgValue' _argName=" + sArgName
								+ " _templateId=" + nTemplateId + " _prexName=" + sPrexName;
				Element EnumValues = (Element) arg.element("EnumValues");
				List EnumValueList = EnumValues.elements("EnumValue");
				for(int t=0; t < EnumValueList.size(); t++){//start for3
					Element EnumValue = (Element)EnumValueList.get(t);
					String sEnumValue = CMyString.transDisplay(EnumValue.attributeValue("Value"));
					String sDisPlay = EnumValue.attributeValue("Display");
					sbJson.append(",{");
					sbJson.append("name:'");
					sbJson.append(sArgName);
					sbJson.append("',enumvalue:'");
					sbJson.append(sEnumValue);
					sbJson.append("',display:'");
					sbJson.append(sDisPlay);
					sbJson.append("'}");
				}//end for3
				String sRowId = i + "_" + k;
				boolean bIsSelected = strSelectedIds.indexOf(","+sRowId+",")!=-1;
				String sRowClassName = k%2==0?"grid_row_even":"grid_row_odd";

%>
	<tr id="tr_<%=sRowId%>" rowid="<%=sRowId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" argName="<%=sArgName%>" type=<%=sType%> DefaultValue="<%=CMyString.transDisplay(sDefaultValue)%>" value="<%=CMyString.transDisplay(sValue)%>" templateId="<%=nTemplateId%>" canCustom=<%=sCanCustom%> PrexName="<%=sPrexName%>">

		<td><input type="checkbox" id="cb_<%=sRowId%>" class="grid_checkbox" name="RowId" value="<%=k%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=sRowId%>"><%=k+1%></span></td>
		<td id="flowname_<%=sRowId%>" style="PADDING-LEFT: 10px; TEXT-ALIGN: left">
		<%=CMyString.transDisplay(sArgName)%></td>
		<td id="value_<%=sRowId%>"><span id="value_sp_<%=sRowId%>" class="valueclass"><%=CMyString.transDisplay(sValue)%></span></td>
		<td><span class="object_edit grid_function" grid_function="edit">&nbsp;</span></td>
	</tr>	 
<%	
			}//end for2
		}//end for1
		sbJson.append("]");
%>
	 
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="4" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	try{
		prepareArgs(<%=sbJson.toString()%>);
	}catch(err){
		alert(err.message);
		//Just skip it.
	}
	try{
		wcm.Grid.init({
			OrderBy : '',
			RecordNum : <%=num%>
		});
	}catch(err){
		alert(err.message);
		//Just skip it.
	}
</script>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("template_arg_query.jsp.label.runtimeexception", "template_arg_query.jsp运行期异常!"), tx);
}
%>
<%!
	 private String dealWithDefaultValue(String sDefaultValue, String sEnumSplit){
		 if((sDefaultValue==null || sDefaultValue.equals("")) || (sEnumSplit == null || sEnumSplit.equals("")) || sDefaultValue.indexOf(sEnumSplit) == -1){
			return  sDefaultValue;
		}
		
		String[] arrVals = sDefaultValue.split(sEnumSplit);
		return arrVals[0];
	 }
	 
%>