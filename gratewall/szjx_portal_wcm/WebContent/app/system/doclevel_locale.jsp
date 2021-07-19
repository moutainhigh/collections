<%@ page import="java.util.*" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.presentation.locale.LocaleServer"%>
<%!
	private Map doclevelMapping = new HashMap();
	{
		doclevelMapping.put("普通", "doclevel.general");
		doclevelMapping.put("绝密", "doclevel.top-secret");
		doclevelMapping.put("机密", "doclevel.confidence");
		doclevelMapping.put("秘密", "doclevel.secret");
	}

	private String getDocLevelLocale(String sDocLevel){
		String sKey = (String) doclevelMapping.get(sDocLevel);
		if(CMyString.isEmpty(sKey)){
			return sDocLevel;
		}
		return LocaleServer.getString(sKey, sDocLevel);
	}
%>