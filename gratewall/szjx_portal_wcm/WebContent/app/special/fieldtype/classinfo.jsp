<%!
	//处理分类法
	private String dealWithClassInfo(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		String sDesc = "";
		String sDefaultValue = _currWidgetParameter.getDefaultValue();
		String sParameterValue = getParameterValue(oWidgetInstance,_currWidgetParameter);
		if(_bAdd){
			sParameterValue = sDefaultValue;
		}
		ClassInfos classInfos = ClassInfos.findByIds(null,
				sParameterValue);
		StringBuffer sbResult = new StringBuffer(classInfos.size() * 20);
		for (int i = 0, nSize = classInfos.size(); i < nSize; i++) {
			ClassInfo classInfo = (ClassInfo) classInfos.getAt(i);
			if (classInfo == null)
				continue;
			sbResult.append(classInfo.getName());
			sbResult.append(",");
		}
		if (sbResult.length() > 0) {
			sbResult.setLength(sbResult.length() - 1);
		}
		sDesc = sbResult.toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XClassInfo({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(CMyString.filterForJs(sParameterValue));
		sb.append("', desc : '");
		sb.append(CMyString.filterForJs(sDesc));
		sb.append("', rootId : '");
		sb.append(_currWidgetParameter.getClassId());
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