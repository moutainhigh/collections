<%!
	//处理下拉文本
	private String dealWithSelect(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XSelect({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', items : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getEnmvalue()));
		sb.append("'");
		// 判断是否是必填选项，如果是则去掉“--请选择--”
		if(_currWidgetParameter.getNotnull()==0)
			sb.append(", blank : {label : \"--请选择--\", value : -1}");
		sb.append(",value : '");
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