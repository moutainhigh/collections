<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataTypes" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataType" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.database.TableInfo" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Iterator" %>

<%
	response.setHeader("ReturnJSON", "true");
	out.println("top.KeyWords = {");

	//元数据表的默认字段
	MetaDataTypes defaultFields = MetaDataConstants.DEFAULT_FIELDS;
	for(int i = 0, length = defaultFields.getDataTypesCount(); i < length; i++){
		MetaDataType field = defaultFields.getDataTypeAt(i);
		out.println("'" + field.getKey().toLowerCase() + "'\t\t\t\t:true,");
	}

	DBManager dbmanager = DBManager.getDBManager();
	//WCMDocument表中的字段
	if(!"true".equals(request.getParameter("ignoredoc"))){
		TableInfo tableInfo = dbmanager.getTableInfo("WCMDOCUMENT");
		Enumeration fieldNames = tableInfo.getFieldNames();
		while(fieldNames.hasMoreElements()){
			String fieldName = (String) fieldNames.nextElement();
			out.println("'" + fieldName.toLowerCase() + "'\t\t\t\t:true,");
		}	
	}
	out.println("'document'\t\t\t\t:true");


	Iterator iteratorKeywords = dbmanager.iteratorKeywords();
	String sKeywords = null;
	StringBuffer buff = new StringBuffer(256);
	while(iteratorKeywords.hasNext()){
		sKeywords = (String)iteratorKeywords.next();
		buff.append(",'").append(sKeywords.toLowerCase()).append("':true");
	}
	out.print(buff);
	buff.setLength(0);

	out.println("}");
%>