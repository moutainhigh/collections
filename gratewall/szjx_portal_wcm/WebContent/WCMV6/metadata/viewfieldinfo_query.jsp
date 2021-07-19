<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.webframework.xmlserver.ConvertException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataTypes" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataType" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.components.metadata.definition.IClassInfoMgr" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.right.AuthServerMgr" %>

<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
//2. 获取业务数据
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	MetaViewFields objects = (MetaViewFields)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(objects == null){
		response.setHeader("IsNull", "true");
		return;
	}
	
//3. 构造分页参数，这段逻辑应该都可以放到服务器端	TODO
	int nPageSize = -1, nPageIndex = 1;
	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
	}	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(objects.size());

	response.setHeader("Num",String.valueOf(currPager.getItemCount()));
	response.setHeader("PageSize",String.valueOf(currPager.getPageSize()));
	response.setHeader("PageCount",String.valueOf(currPager.getPageCount()));
	response.setHeader("CurrPageIndex",String.valueOf(currPager.getCurrentPageIndex()));
	out.clear();
%>
<%		
	MetaViewField oViewField = null;
	boolean bHasRight = false;
	MetaView oMetaView = (MetaView)oMethodContext.getObjectValue("View");
	bHasRight= AuthServerMgr.hasRight(ContextHelper.getLoginUser(), oMetaView);
	response.setHeader("HasRight", String.valueOf(bHasRight));

	int nTitleFieldObjectId = -1;
%>
<%
//5. 遍历生成表现
	String sDBTypeContainer = getDBTypeContainer();
	String sFieldTypeContainer = getFieldTypeContainer();
	String sClassInfoContainer = getClassInfoContainer();
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			MetaViewField object = (MetaViewField)objects.getAt(i - 1);
			if (object == null)
				continue;
			String sObjectId = String.valueOf(object.getId());
			String sObjectName = CMyString.transDisplay(object.getName());
			String sDBFieldName = CMyString.transDisplay(object.getDBName());			
			String sTableName = CMyString.transDisplay(object.getTableName());			
			String sObjectAnotherName = CMyString.transDisplay(object.getAnotherName());
			String sFieldTypeID = String.valueOf(object.getType());
			String sFieldType = object.getTypeDesc();
			String sDBTypeID = String.valueOf(object.getDBType());
			String sDBType = object.getDBTypeDesc();
			int classId = object.getClassId();
			String sClassInfoName = CMyString.filterForHTMLValue(CMyString.showNull(object.getClassName()));
			String sEnmValue = CMyString.filterForHTMLValue(CMyString.showNull(object.getEnmValue()));

			String sDisplayEnmValue = "none";
			String sDisplayClassId = "none";
			switch(object.getType()){
				case MetaDataConstants.FIELD_TYPE_RADIO:
				case MetaDataConstants.FIELD_TYPE_SELECT:
				case MetaDataConstants.FIELD_TYPE_CHECKBOX:
				case MetaDataConstants.FIELD_TYPE_INPUT_SELECT:
				case MetaDataConstants.FIELD_TYPE_SUGGESTION:
					sDisplayEnmValue = "";
					break;
				case 10:
					sDisplayClassId = "";
					break;
			}
			int iInOutline = object.getPropertyAsInt("inOutline", 0);
			String sInOutline = iInOutline == 1 ? "是" : "否";

			String sTitleFieldCls = "";
			String sTitleFieldDesc = "";
			String sInOutlineDisabled = "";
			if(object.isTitleField()){
				nTitleFieldObjectId = object.getId();
				sTitleFieldCls = " titleField";
				sTitleFieldDesc = " title='标题字段'";
				sInOutlineDisabled = " disabled";
			}			
%>

	<DIV class="grid_row wcm_pointer" grid_rowid="<%=sObjectId%>" id="row_<%=sObjectId%>" realId="<%=sObjectId%>" objectName="<%=sObjectName%>"  hasRight='<%=bHasRight?"1":"0"%>'>
		<SPAN class="grid_column<%=sTitleFieldCls%>" <%=sTitleFieldDesc%> style="width:30px">
			<input type="checkbox" id="cbx_<%=sObjectId%>" class="wcm_pointer grid_checkbox" name="ObjectId" value="<%=sObjectId%>"/>
		</SPAN>
		<SPAN class="grid_column object_edit<%=bHasRight?"":" no_right"%>" id="editOrSave_<%=sObjectId%>" style="width:30px" grid_function="editInRow"></SPAN>
		<SPAN class="grid_column" style="width:100px;font-size:12px;text-overflow:ellipsis;" align="left" title="<%=sObjectName%>">			
			<span id="fieldName_<%=sObjectId%>"><%=sObjectName%></span>
			<input validation="type:'common_char2',required:'',max_len:'30',desc:'英文名称'" formElement="true" type="text" name="fieldName" value="<%=sObjectName%>" style="display:none;" class="input_text">
		</SPAN>
		<SPAN class="grid_column ObjectDesc" style="width:110px;" title="<%=sObjectAnotherName%>">
			<span><a href="#" class="anotherName" grid_function="edit" onclick="return false;" id="anotherName_<%=sObjectId%>"><%=sObjectAnotherName%></a></span>
			<input validation="type:'string',required:'',max_len:'100',desc:'中文名称'" formElement="true" type="text" name="anotherName" value="<%=sObjectAnotherName%>" style="display:none;" class="input_text">
		</SPAN>
		<SPAN class="grid_column" style="width:100px;font-size:12px;text-overflow:ellipsis;" align="left" title="<%=sTableName%>.<%=sDBFieldName%>">			
			<span id="dbfieldName_<%=sObjectId%>"><%=sDBFieldName%></span>
		</SPAN>
		<SPAN class="grid_column ObjectType" grid_dywidth="ObjectType,1.0">
			<span _id="<%=sFieldTypeID%>" id="fieldType_<%=sObjectId%>"><%=sFieldType%></span>
			<%=sFieldTypeContainer%>
			<span id="enmValueContainer_<%=sObjectId%>" style="display:<%=sDisplayEnmValue%>;">
				<span id="enmValue_<%=sObjectId%>"><%=sEnmValue%></span>
				<input formElement="true" type="text" name="enmValue" value="<%=sEnmValue%>" style="display:none;width:130px;" class="input_text" _title="格式为:item~item<br><span style='white-space:nowrap;overflow:visible;'>其中item为：label`value或label</span><br>如:<li>中国`1~美国`2~英国`3<br><li>中国~美国~英国<br><font color='red'>双击打开构造页面</font>">
			</span>
			<span id="classIdContainer_<%=sObjectId%>" style="display:<%=sDisplayClassId%>;">
				<span _id="<%=classId%>" id="classId_<%=sObjectId%>"><%=sClassInfoName%></span>
				<%=sClassInfoContainer%>
			</span>
		</SPAN>
		<SPAN class="grid_column" style="width:70px;text-align:center;">
			<span id="inOutline_<%=sObjectId%>" class=""><%=sInOutline%></span>
			<input type="checkbox" name="inOutline" value="" style="display:none;" formElement="true"<%=sInOutlineDisabled%>>
		</SPAN>
		<SPAN class="grid_column" style="width:80px">
			<span _id="<%=sDBTypeID%>" id="dbType_<%=sObjectId%>"><%=sDBType%></span>
			<%=sDBTypeContainer%>
		</SPAN>
		<SPAN id="delete_<%=sObjectId%>" class="grid_column object_delete<%=bHasRight?"":" no_right"%>" style="width:30px;border-right:0" grid_function="delete" title="删除"></SPAN>
		<SPAN id="cancel_<%=sObjectId%>" class="grid_column object_cancel" style="width:30px;border-right:0;display:none;" grid_function="cancel" title="撤销"></SPAN>
	</DIV>
<%
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	int nBlankStartIndex = 0;
%>
<script language="javascript">
<!--
	PageContext.lastTitleFieldObjectId = <%=nTitleFieldObjectId%>;
//-->
</script>
<%!
	private String getDBTypeContainer(){
		StringBuffer sb = new StringBuffer();
		sb.append("<select formElement='true' name='dbType' style='display:none;'>");            
		MetaDataTypes dbDataTypes = MetaDataConstants.DB_DATA_TYPES;
		for(int i = 0, length = dbDataTypes.getDataTypesCount(); i < length; i++){
			MetaDataType dbDataType = dbDataTypes.getDataTypeAt(i);
			sb.append("<option _maxLength='" + dbDataType.getMaxLength() + "' _value='" + dbDataType.getKey() + "' value='" + dbDataType.getDataType() + "'>" + dbDataType.getDataTypeDesc() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	private String getFieldTypeContainer(){
		StringBuffer sb = new StringBuffer();
		sb.append("<select formElement='true' changeEvent='dataTypeChange' name='fieldType' style='display:none;'>");
		MetaDataTypes dataTypes = MetaDataConstants.DATA_TYPES;
		for(int i = 0, length = dataTypes.getDataTypesCount(); i < length; i++){
			MetaDataType dataType = dataTypes.getDataTypeAt(i);
			sb.append("<option _value='" + dataType.getKey() + "' value='" + dataType.getDataType() + "'>" + dataType.getDataTypeDesc() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	private String getClassInfoContainer()throws WCMException{
		IClassInfoMgr m_oClassInfoMgr = (IClassInfoMgr) DreamFactory.createObjectById("IClassInfoMgr");
		StringBuffer sb = new StringBuffer();
		sb.append("<select formElement='true' name='classId' style='display:none;'>");
		sb.append("<option value='0'>--请选择一个分类--</option>");
		ClassInfos oClassInfos = m_oClassInfoMgr.queryChildren(null, null, null);
		for(int i = 0, length = oClassInfos.size(); i < length; i++){
			ClassInfo oClassInfo = (ClassInfo)oClassInfos.getAt(i);
			sb.append("<option value='" + oClassInfo.getId() + "'>" + CMyString.transDisplay(oClassInfo.getName()) + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
%>