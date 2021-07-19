<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBField" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBFields" %>
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

<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
//2. 获取业务数据
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	MetaDBFields objects = (MetaDBFields)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

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
//5. 遍历生成表现
	String sDBTypeContainer = getDBTypeContainer();
	String sFieldTypeContainer = getFieldTypeContainer();
	String sClassInfoContainer = getClassInfoContainer();
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			MetaDBField object = (MetaDBField)objects.getAt(i - 1);
			if (object == null)
				continue;
			String sObjectId = String.valueOf(object.getId());
			String sObjectName = CMyString.transDisplay(object.getName());
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
%>
	<DIV class="grid_row wcm_pointer" grid_rowid="<%=sObjectId%>" id="row_<%=sObjectId%>" realId="<%=sObjectId%>" objectName="<%=sObjectName%>">
		<SPAN class="grid_column" style="width:30px">
			<input title="<%=sObjectId%>" type="checkbox" class="wcm_pointer grid_checkbox" id="ckb_<%=sObjectId%>" name="ObjectId" value="<%=sObjectId%>"/>
		</SPAN>
		<SPAN class="grid_column object_edit" id="editOrSave_<%=sObjectId%>" style="width:30px" grid_function="editInRow"></SPAN>
		<SPAN class="grid_column ObjectDesc" style="width:130px;" title="<%=sObjectAnotherName%>">
			<span><a href="#" class="anotherName" grid_function="edit" onclick="return false;" id="anotherName_<%=sObjectId%>"><%=sObjectAnotherName%></a></span>
			<input validation="type:'string',required:'',max_len:'100',desc:'中文名称'" formElement="true" type="text" name="anotherName" value="<%=sObjectAnotherName%>" style="display:none;" class="input_text">
		</SPAN>
		<SPAN class="grid_column" style="width:120px;font-size:12px;text-overflow:ellipsis;" align="left" title="<%=sObjectName%>">			
			<span id="fieldName_<%=sObjectId%>"><%=sObjectName%></span>
			<input validation="type:'common_char2',required:'',max_len:'30',desc:'英文名称'" formElement="true" type="text" name="fieldName" value="<%=sObjectName%>" style="display:none;" class="input_text">
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
		<SPAN class="grid_column" style="width:100px">
			<span _id="<%=sDBTypeID%>" id="dbType_<%=sObjectId%>"><%=sDBType%></span>
			<%=sDBTypeContainer%>
		</SPAN>
		<SPAN id="delete_<%=sObjectId%>" class="grid_column object_delete" style="width:30px;border-right:0" grid_function="delete" title="删除"></SPAN>
		<SPAN id="cancel_<%=sObjectId%>" class="grid_column object_cancel" style="width:30px;border-right:0;display:none;" grid_function="cancel" title="撤销"></SPAN>
	</DIV>
<%
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	int nBlankStartIndex = 0;
%>
<%@include file="./fieldinfo_blankrows_include.jsp"%>