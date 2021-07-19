<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.webframework.xmlserver.ConvertException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMException" %>

<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
//2. 获取业务数据
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	ClassInfos objects = (ClassInfos)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

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
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ClassInfo object = (ClassInfo)objects.getAt(i - 1);
			if (object == null)
				continue;
			String sObjectId = String.valueOf(object.getId());
			String sObjectName = CMyString.transDisplay(object.getName());
			String sObjectDesc = CMyString.transDisplay(object.getDesc());
			String sCrUser = object.getPropertyAsString("CrUser");
			String sCrTime = convertDateTimeValueToString(oMethodContext, object.getPropertyAsDateTime("CrTime"));
%>
	<DIV class="grid_row wcm_pointer" grid_rowid="<%=sObjectId%>" id="row_<%=sObjectId%>" objectName="<%=sObjectName%>">
		<SPAN class="grid_column" style="width:30px">
			<input title="<%=sObjectId%>" type="checkbox" class="wcm_pointer grid_checkbox" name="ClassInfoId" value="<%=sObjectId%>"/>
		</SPAN>
		<SPAN class="grid_column object_edit" style="width:30px" grid_function="config"></SPAN>
		<SPAN title="<%=sObjectName%>" class="grid_column ObjectName" style="text-align:left;">
			<%=sObjectName%>
		</SPAN>
		<SPAN class="grid_column ObjectDesc" title="<%=sObjectDesc%>" style="text-align:left;padding-left:3px;">
			<%=sObjectDesc%>
		</SPAN>
		<SPAN class="grid_column" style="width:70px">
			<%=sCrUser%>
		</SPAN>
		<SPAN class="grid_column" style="width:120px">
			<%=sCrTime%>
		</SPAN>
		<SPAN class="grid_column object_delete" style="width:30px;border-right:0" grid_function="delete"></SPAN>
	</DIV>

<%
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
%>
<%!
	private String convertDateTimeValueToString(MethodContext _methodContext, CMyDateTime _dtValue) {
		String sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
		if (_methodContext != null) {
			sDateTimeFormat = _methodContext.getValue("DateTimeFormat");
			if (sDateTimeFormat == null) {
				sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
			}
		}
		String sDtValue = _dtValue.toString(sDateTimeFormat);
		return sDtValue;
	}
%>
