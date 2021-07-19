<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="/app/application/common/error_for_dialog.jsp"%>
<%@ include file="/app/application/common/metaviewdata_query_include_import.jsp" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="java.util.List,java.util.Map,java.util.Iterator,java.util.ArrayList" %>
<%out.clear();%>
<%
	String sViewId = request.getParameter("viewId");
%>
var $GImgSelectFields = '<%=getImageFields(Integer.parseInt(sViewId))%>';
<%!

	private IMetaDataDefCacheMgr getMetaDataDefCacheMgr(){
		 return (IMetaDataDefCacheMgr) DreamFactory.createObjectById("IMetaDataDefCacheMgr");
	}

	/**
	*获取视图下所有的图片视图字段，含有分辨率的图片
	*/
	private String getImageFields(int nViewId)throws WCMException{
		Map appendixFields = getMetaDataDefCacheMgr().getMetaViewFields(nViewId, MetaDataConstants.FIELD_TYPE_APPENDIX);
		StringBuffer sbResult = new StringBuffer(appendixFields.size() * 10);
		

        for (Iterator iterator = appendixFields.values().iterator(); iterator.hasNext();) {
            MetaViewField oViewField = (MetaViewField) iterator.next();
			String sFieldName = oViewField.getName();

			//the format is xxx_200_300
			String[] info = sFieldName.split("_");
			if(info.length < 3){
				continue;
			}
			String sWidth = info[info.length - 2];
			String sHeight = info[info.length - 1];
			int nWidth = 0;
			int nHeight = 0;
			try{
				nWidth = Integer.parseInt(sWidth);
				nHeight = Integer.parseInt(sHeight);
			}catch(Exception e){
				//just skip it.
				continue;
			}

			sbResult.append(sFieldName).append(",");
        }		
		if(sbResult.length() > 0){
			sbResult.setLength(sbResult.length() - 1);
		}
		return sbResult.toString();
	}

%>