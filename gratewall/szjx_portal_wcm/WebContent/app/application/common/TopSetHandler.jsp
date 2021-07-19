<%
	IDocumentService currDocumentService = (IDocumentService)DreamFactory.createObjectById("IDocumentService");
	//置顶信息
	boolean bIsCanTop = false;//是否在当前栏目有置顶权限
	//有修改文档的权限时才可做置顶设置
	if(channel != null) {
		bIsCanTop = DocumentAuthServer.hasRight(loginUser, channel, document, WCMRightTypes.DOC_POSITIONSET);
	}
	boolean bTopped = false;//是否置顶
	boolean bTopForever = false;//是否永久置顶
	CMyDateTime dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
	if(nObjectId>0){
		dtTopInvalidTime = currDocumentService.getTopTime(document, channel);
		bTopped = currDocumentService.isDocumentTopped(document, channel);
		if(bTopped && dtTopInvalidTime == null)
			bTopForever = true;
		if(dtTopInvalidTime == null){
			dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
		}
	}
	String sTopInvalidTime = dtTopInvalidTime.toString("yyyy-MM-dd HH:MM:ss");
	Documents toppedDocuments = null;
	if(bIsCanTop && channel != null) {
		WCMFilter filter = new WCMFilter("", "DOCORDERPRI>0", "", "DocId, DocTitle, DocChannel");
		IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
		toppedDocuments = currChannelService.getDocuments(channel, filter);
	}
%>
<div style="display:<%=bIsCanTop?"''":"none"%>;">
	<div class="label" WCMAnt:param="metaviewdata_addedit.jsp.topSet">置顶设置</div>
	<div class="sep">：</div>
	<div class="value">
		<div class="topset_row" _action="topset">
			<input type="radio" id="pri_set_0" name="TopFlag" value="0">
			<label for="pri_set_0" WCMAnt:param="metaviewdata_addedit.jsp.noSet">不置顶</label>
		</div>
		<div class="topset_row" _action="topset">
			<input type="radio" id="pri_set_2" name="TopFlag" value="2">
			<label for="pri_set_2" WCMAnt:param="metaviewdata_addedit.jsp.topForEver">永久置顶</label>
		</div>
		<div class="topset_row">
			<span _action="topset">
				<input type="radio" id="pri_set_1" name="TopFlag" value="1">
				<label for="pri_set_1" WCMAnt:param="TopSetHandler.jsp.topTimeVal">限时置顶</label>
			</span>
			<span id="pri_set_deadline" style="display:<%=(!bTopped || bTopForever)?"none":""%>">
				<input type="text" name="TopInvalidTime" id="TopInvalidTime" elname="限时置顶" value="<%=sTopInvalidTime%>" WCMAnt:paramattr="elname:TopSetHandler.jsp.topTimeVal"/>
				<button type="button" id="TopInvalidTime-btn"><img src="../../images/icon/TRSCalendar.gif" border=0 alt=""></button>
				<script>					
					wcm.TRSCalendar.get({
						input : 'TopInvalidTime',
						handler : 'TopInvalidTime-btn',
						withtime : true,
						dtFmt : 'yyyy-mm-dd HH:MM:ss'
					});
				</script>
			</span>
		</div>	
	</div>
</div>
<div id="topset_order" style="display:<%=(!bIsCanTop)?"none":"''"%>">
	<div class="label" WCMAnt:param="metaviewdata_addedit.jsp.topOrder">置顶排序</div>
	<div class="sep">：</div>
	<div class="value" id="topset_order_table">
		<table border=0 cellspacing=1 cellpadding=0 style="width:88%;table-layout:fixed;background:gray;">
		<thead>
			<tr bgcolor="#CCCCCC" align=center valign=middle>
				<td width="32" WCMAnt:param="metaviewdata_addedit.jsp.order">序号</td>
				<td WCMAnt:param="metaviewdata_addedit.jsp.docTitle">文档标题</td>
				<td width="40" WCMAnt:param="metaviewdata_addedit.jsp.listOrder">排序</td>
			</tr>
		</thead>
		<tbody id="topset_order_tbody">
		<%
			if(toppedDocuments==null||toppedDocuments.size()==0){
		%>
			<tr bgcolor="#FFFFFF" align=center valign=middle>
				<td>&nbsp;</td>
				<td align=left>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		<%
			}else{
				for(int i=0, n=toppedDocuments.size(); i<n; i++){
					Document topDoc = (Document)toppedDocuments.getAt(i);
					if(topDoc==null)continue;
					int nTopDocId = topDoc.getId();
					String sDocTitle = CMyString.truncateStr(topDoc.getTitle(), 55, "...");
					String sDocTitle2 = PageViewUtil.toHtmlValue(topDoc.getTitle());
					String sDocTitle3 = PageViewUtil.toHtmlValue(sDocTitle);
					if(nTopDocId!=nObjectId){
		%>
			<tr bgcolor="#FFFFFF" align=center valign=middle _docid="<%=nTopDocId%>" _doctitle="<%=sDocTitle3%>">
				<td><%=i+1%></td>
				<td align=left title="<%=nTopDocId%>-<%=sDocTitle2%>"><div style="overflow:hidden"><%=sDocTitle%></div></td>
				<td>&nbsp;</td>
			</tr>
		<%
						continue;
					}//end if
		%>
			<tr bgcolor="#FFFFCF" align=center valign=middle _currdoc="1">
				<td><%=i+1%></td>
				<td align=left style="color:red;" WCMAnt:param="metaviewdata_addedit.jsp.currDocument">--当前文档--</td>
				<td>
					<span class="topset_up" title="上移" _action="topsetUp" WCMAnt:paramattr="title:metaviewdata_addedit.jsp.upper">&nbsp;</span>
					<span class="topset_down" title="下移" _action="topsetDown" WCMAnt:paramattr="title:metaviewdata_addedit.jsp.lower">&nbsp;</span>
				</td>
			</tr>
		<%
				}//end for
			}
		%>
		</tbody>
		</table>	
	</div>
</div>
<script language="javascript">
<!--
PgC.IsCanTop = <%=bIsCanTop%>;
PgC.TopFlag = !<%=bTopped%> ? 0 : (!<%=bTopForever%> ? 1 : 2);
$('pri_set_' + PgC.TopFlag).checked = true;	
//-->
</script>