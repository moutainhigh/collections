<%!
	//处理普通文本
	private String dealWithNormalText(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XText({");
		sb.append("disabled : ");
		sb.append(_currWidgetParameter.getNotEdit());
		sb.append(",name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(CMyString.filterForJs(getParameterValue(oWidgetInstance,_currWidgetParameter)));
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