<%
/** Title:			document_addedit_other_properties.jsp
 *  Description:
 *		WCM5.2 管理文档的其他属性
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/11/19
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see document_addedit_other_properties.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.database.DataType" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.service.IChannelService" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="java.sql.Types" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = true;%>
<%
//4.初始化（获取数据）
	int nDocumentId  = currRequestHelper.getInt("DocumentId", 0);
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Document currDocument = Document.findById(nDocumentId);
	Channel docChannel = null;
	int nDocChannelId = 0;
	if(nDocumentId > 0){
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到ID为["+nDocumentId+"]的文档");
		}
		docChannel = currDocument.getChannel();
		if(docChannel!=null){
			nDocChannelId = docChannel.getId();
		}
	}
	if(nDocChannelId!=nChannelId && nChannelId > 0){
		docChannel = Channel.findById(nChannelId);
	}
	if(docChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到ID为["+nChannelId+"]的频道");
	}

//5.权限校验
	
//6.业务代码
	ContentExtFields currExtendedFields = null;
	IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	if(docChannel != null)
		currExtendedFields  = currChannelService.getExtFields(docChannel, null);
	String strDBName = DBManager.getDBManager().getDBType().getName();
//7.结束
	out.clear();
	System.out.println("a:"+nDocChannelId+','+nChannelId);
	if(nDocChannelId==nChannelId && currExtendedFields != null && !currExtendedFields.isEmpty()){
		ContentExtField currExtendedField = null;
		String sExtFieldName = "";
		String sExtFieldValue = "";
		for(int j=0; j<currExtendedFields.size(); j++){
			currExtendedField = (ContentExtField)currExtendedFields.getAt(j);
			if(currExtendedField == null) continue;
			sExtFieldName = PageViewUtil.toHtml(currExtendedField.getName());

			if(currExtendedField.getType().getType() == java.sql.Types.TIMESTAMP) {
				CMyDateTime tmpDateTime = currDocument.getPropertyAsDateTime(currExtendedField.getName());
				if(tmpDateTime == null) {
					sExtFieldValue = "";
				} else {
					sExtFieldValue= PageViewUtil.toHtmlValue(tmpDateTime.toString("yyyy-MM-dd HH:mm"));
				}
			} else {
				sExtFieldValue= PageViewUtil.toHtmlValue(currDocument.getPropertyAsString(currExtendedField.getName()));
			}
%>
															<TR class="attr_extendfield_row" valign="middle">
																<TD class="attr_extendfield_column"  style="width:90px;" title="<%=sExtFieldName%>">
																	<%=PageViewUtil.toHtml(currExtendedField.getDesc())%>[<%=PageViewUtil.toHtml(toDescription(currExtendedField.getType(), strDBName))%>]
																</TD>
																<TD class="attr_extendfield_column">
																	<%=getHtml(currExtendedField,sExtFieldValue)%>
																</TD>
															</TR>
<%
		}//end for
	}
	else if(currExtendedFields != null && !currExtendedFields.isEmpty()){
		ContentExtField currExtendedField = null;
		String sFieldName = "";
		for(int i=0; i<currExtendedFields.size(); i++){
			try{
				currExtendedField = (ContentExtField) currExtendedFields.getAt(i);
			} catch(Exception ex){
				throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取第["+(i+1)+"]个扩展字段失败！", ex);
			}//end try-catch

			try{
				sFieldName = currExtendedField.getName();
%>
															<TR class="attr_extendfield_row" valign="middle">
																<TD class="attr_extendfield_column"  style="width:90px;" title="<%=PageViewUtil.toHtml(sFieldName)%>">
																	<%=PageViewUtil.toHtml(currExtendedField.getDesc())%>[<%=PageViewUtil.toHtml(toDescription(currExtendedField.getType(), strDBName))%>]
																</TD>
																<TD class="attr_extendfield_column">
																	<%=getHtml(currExtendedField)%>
																</TD>
															</TR>
<%
			} catch(Exception ex){
				throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+(i+1)+"]个扩展字段的属性失败！", ex);
			}//end try-catch
		}//end for
	}
	else{
%>
															<TR class="attr_extendfield_norow" valign="middle">
																<TD class="attr_extendfield_column"  style="width:90px;" title="" colSpan="2">
																	无扩展字段
																</TD>
															</TR>
<%
	}
%>
<%!
private String getPattern(int _nType){
	switch(_nType){
		case Types.BIGINT:
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.NUMERIC:
			return "pattern=integer";
		case Types.FLOAT:
		case Types.DOUBLE:			
			return "pattern=float";
		case Types.DATE:
		case Types.TIMESTAMP:
			return "pattern=datetime DateFormat=\"yyyy-mm-dd hh:mm\"";
			//return " ";
		case Types.CLOB:
			return "";
		default:
			return "pattern=string";
	
	}
}

private String getHtml(ContentExtField _currField) throws Exception{
	if(_currField == null || _currField.getType() == null) return "";
	int nDataType = _currField.getType().getType();
	String sHtml = "";
	//时间型使用控件显示
	if(nDataType == java.sql.Types.DATE || nDataType == java.sql.Types.TIMESTAMP) {
		sHtml = "<SCRIPT>TRSCalendar.drawWithTime(\""+_currField.getName()+"\", \"\", null, true, true);</SCRIPT>";
		return sHtml;
	}

	String sElementInfo = " NAME="+_currField.getName()+" elname="+_currField.getDesc()+" "+getPattern(nDataType);
	if(_currField.getType().isLengthDefinedByUser()) {
		sElementInfo += " max_len="+_currField.getMaxLength()+" ";
	} else if(nDataType == java.sql.Types.BIGINT) {
		sElementInfo += " max_value=9223372036854775807 ";
	} else if(nDataType == java.sql.Types.INTEGER) {
		sElementInfo += " max_value=2147483647 ";
	} else if(nDataType == java.sql.Types.SMALLINT) {
		sElementInfo += " max_value=32767 ";
	}
	if(nDataType == java.sql.Types.LONGVARCHAR || nDataType == java.sql.Types.VARCHAR)
		sHtml = "<TEXTAREA cols=30 rows=5 " + sElementInfo + "></TEXTAREA>";
	else
		sHtml = "<INPUT " + sElementInfo + ">";
	return sHtml;
}
private String getHtml(ContentExtField _currField,String _sValue) throws Exception{
	if(_currField == null || _currField.getType() == null) return "";
	int nDataType = _currField.getType().getType();
	String sHtml = "";
	//时间型使用控件显示
	if(nDataType == java.sql.Types.DATE || nDataType == java.sql.Types.TIMESTAMP) {
		sHtml = "<SCRIPT>TRSCalendar.drawWithTime(\""+_currField.getName()+"\", \""+_sValue+"\", null, true, true);</SCRIPT>";
		return sHtml;
	}

	String sElementInfo = " NAME="+_currField.getName()+" elname="+_currField.getDesc()+" "+getPattern(nDataType);
	if(_currField.getType().isLengthDefinedByUser()) {
		sElementInfo += " max_len="+_currField.getMaxLength()+" ";
	} else if(nDataType == java.sql.Types.BIGINT) {
		sElementInfo += " max_value=9223372036854775807 ";
	} else if(nDataType == java.sql.Types.INTEGER) {
		sElementInfo += " max_value=2147483647 ";
	} else if(nDataType == java.sql.Types.SMALLINT) {
		sElementInfo += " max_value=32767 ";
	}
	if(nDataType == java.sql.Types.LONGVARCHAR || nDataType == java.sql.Types.VARCHAR)
		sHtml = "<TEXTAREA cols=30 rows=5 " + sElementInfo + ">"+_sValue+"</TEXTAREA>";
	else
		sHtml = "<INPUT " + sElementInfo + " value="+_sValue+">";
	return sHtml;
}

	// 将字段类型转换成中文
	private String toDescription(DataType _currDataType, String _strDBName){
		if(_currDataType == null || _strDBName == null) {
			return "";
		}
		int nDataType = _currDataType.getType();
		switch(nDataType) {
			case java.sql.Types.FLOAT :
				if(_strDBName.equalsIgnoreCase("Oracle")) {
					return "整数型";
				}
				return "小数型";
			case java.sql.Types.SMALLINT:
			case java.sql.Types.INTEGER :
			case java.sql.Types.NUMERIC :
				return "整数型";
			case java.sql.Types.VARCHAR :
				return "文本型";
			case java.sql.Types.TIMESTAMP :
				return "时间型";
			default :
				return _currDataType.getName();
		}
	}
%>