<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%!
	//文档选择
	private String dealWithSelDocument(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		//return "<input type='text' name='"+CMyString.filterForHTMLValue(_currWidgetParameter.getWidgetParamName())+"'/>";
		String sDesc = "";
		String sChnlId = "";
		String sSiteId = "";
		String sDefaultValue = _currWidgetParameter.getDefaultValue();
		String sParameterValue = getParameterValue(oWidgetInstance,_currWidgetParameter);
		if(_bAdd){
			sParameterValue = sDefaultValue;
		}
		if(!CMyString.isEmpty(sParameterValue)) {
			int docId = Integer.parseInt(sParameterValue);
			ChnlDoc currDoc = ChnlDoc.findById(docId);
			if(currDoc != null) {
				sDesc = Document.findById(currDoc.getDocId()).getTitle();
				sChnlId = CMyString.numberToStr(currDoc.getChannelId());
				sSiteId = CMyString.numberToStr(currDoc.getSiteId());
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XDocSelect({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(CMyString.filterForJs(sParameterValue));
		sb.append("', desc : '");
		sb.append(CMyString.filterForJs(sDesc));
		sb.append("', chnlId : '");
		sb.append(CMyString.filterForJs(sChnlId));
		sb.append("', siteId : '");
		sb.append(CMyString.filterForJs(sSiteId));
		sb.append("', treeType : '");
		sb.append(_currWidgetParameter.getRadorchk());
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',desc:'");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamDesc()));
		sb.append("'\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}
%>