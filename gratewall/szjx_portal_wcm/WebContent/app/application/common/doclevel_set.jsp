<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ include file="/app/system/doclevel_locale.jsp"%>
<%
	
	boolean bHasRight = false;//是否在当前栏目有设置密级的权限
	//有设置文档权限时才可做密级设置
	bHasRight = DocumentAuthServer.hasRight(loginUser, channel, document, WCMRightTypes.DOC_RIGTH_SET);
	
	

%>
<%
	if(bHasRight){
		// 文档密级 add by ffx @2010-12-20
		DocLevel currDocLevel = document.getDocLevel();
		int nCurrLevelId = currDocLevel.getId();
		String sLevelName = currDocLevel.getLName();
		processor = new JSPRequestProcessor(request, response);
		String sServiceId = "wcm61_doclevel", sMethodName = "query";
		DocLevels docLevels = (DocLevels)processor.excute(sServiceId, sMethodName);
%>
<div class="row">
	<span class="label" WCMAnt:param="doclevel_set.jsp.respectivedense">所属密级</span>
	<div class="sep">：</div>
	<div class="value">
		<select id="docLevel_slt" class="attr_input_text" style="width:142px;height:20px;">
			<%
				for (int i = 0, nSize = docLevels.size(); i < nSize; i++) {
					DocLevel level = (DocLevel) docLevels.getAt(i);
					if (level == null)
						continue;
					int nDocLevelId = level.getId();
			%>
				<option value="<%=nDocLevelId%>" <%=(nDocLevelId == nCurrLevelId)?"selected selected=true" :""%> ><%=CMyString.transDisplay(getDocLevelLocale(level.getLName()))%>
			<%}%>
		</select>
	</div>
</div>
<%}%>