<%!
	//栏目选择树
	private String dealWithSelChannel(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		//return "<input type='text' name='"+CMyString.filterForHTMLValue(_currWidgetParameter.getWidgetParamName())+"'/>";
		String sDesc = "";
		String sDefaultValue = _currWidgetParameter.getDefaultValue();
		String sParameterValue = getParameterValue(oWidgetInstance,_currWidgetParameter);
		if(_bAdd){
			sParameterValue = sDefaultValue;
		}
		Channels oChannels = Channels.findByIds(null,
				sParameterValue);
		StringBuffer sbResult = new StringBuffer(oChannels.size() * 20);
		for (int i = 0, nSize = oChannels.size(); i < nSize; i++) {
			Channel currChannel = (Channel) oChannels.getAt(i);
			if (currChannel == null)
				continue;
			sbResult.append(currChannel.getName());
			sbResult.append(",");
		}
		if (sbResult.length() > 0) {
			sbResult.setLength(sbResult.length() - 1);
		}
		sDesc = sbResult.toString();

		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XChannelTree({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(CMyString.filterForJs(sParameterValue));
		sb.append("', desc : '");
		sb.append(CMyString.filterForJs(sDesc));
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