<%
	String[] tempInfo = getTemplate(nObjectId);
%>
<img border="0" style="cursor:pointer;" align="absmiddle" title="设置模板" WCMAnt:paramattr="title:metaviewdata_addedit.jsp.templateSet" src="../../images/icon/TempSelect.gif" id="selectTemplate" />
<span style="width:130px;overflow:hidden;" id="spDetailTemp" tempIds="<%=tempInfo[0]%>"><%=CMyString.transDisplay(tempInfo[1])%></span>
<%!
	/**
	*返回文档设置的模板信息{templateId, templateName}.
	*/
	private String[] getTemplate(int nDocId) throws WCMException{
		String sNone = LocaleServer.getString("metaviewdata.label.none", "无");
		if(nDocId <= 0) return new String[]{"0", sNone};
		ITemplateEmployCacheMgr cacheMgr = (ITemplateEmployCacheMgr)DreamFactory.createObjectById("ITemplateEmployCacheMgr");
		Template template = cacheMgr.getDetailTemplate(Document.OBJ_TYPE, nDocId);
		if(template == null) return new String[]{"0", sNone};
		return new String[]{String.valueOf(template.getId()), template.getName()};
	}
%>