<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.cms.content.ExtendedField" %>
<%@ page import="com.trs.cms.content.ExtendedFields" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocumentShowFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%@include file="../channel/document_field_init.jsp"%>
<%@include file="../system/status_locale.jsp"%>
<%
//2. 获取业务数据
	//获取STRIP_DOCTITLE_TAGS参数：去除文档列表标题html标签样式参数，默认为false，保留
	String sStripDoctitleTags = ConfigServer.getServer().getSysConfigValue("STRIP_DOCTITLE_TAGS","false");
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	ViewDocuments viewDocuments = (ViewDocuments)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	String sChannelIds = oMethodContext.getValue("ChannelIds");
	String[] aChannelIds = sChannelIds.split(",");
	String sSiteIds = oMethodContext.getValue("SiteIds");
	String[] aSiteIds = sSiteIds.split(",");
	String sChannelIdForGetChnl = oMethodContext.getValue("ChannelId");
	boolean bSingleChnlAndCustomList = false;
	boolean bContainsChildren = false;
	String sSelectFields = "";
	String sFieldsWidth = "";
	int nColspan = 8;//默认8列
	int nChannelId = 0;
	if(sChannelIdForGetChnl!=null){
		nChannelId = Integer.parseInt(sChannelIdForGetChnl);
		Channel oChnl = Channel.findById(nChannelId);
		sSelectFields = oChnl.getPropertyAsString("SHOWFIELDS");
		sFieldsWidth = oChnl.getPropertyAsString("FIELDSWIDTH");
		if(!CMyString.isEmpty(sSelectFields)){
			if(sSelectFields.charAt(0) == ','){
				sSelectFields = sSelectFields.substring(1);
			}
			if(sFieldsWidth.charAt(0) == ','){
				sFieldsWidth = sFieldsWidth.substring(1);
			}
			bSingleChnlAndCustomList=true;
			nColspan = 3 + sSelectFields.split(",").length;
		}
		if(oMethodContext.containsParameter("ContainsChildren")) {
			bContainsChildren = oMethodContext.getValue("ContainsChildren", false);
		}else{
			bContainsChildren = oChnl.isContainsChildren();
		}
	}

	List list = XMLConfigServer.getInstance().getConfigObjects(DocumentShowFieldConfig.class);
	if(list == null) list = new java.util.ArrayList();
	StringBuffer sbFieldName = new StringBuffer();
	StringBuffer sbFieldDesc = new StringBuffer();
	DocumentShowFieldConfig currDocumentShowFieldConfig = null;
	for(java.util.Iterator it=list.iterator(); it.hasNext(); ) {
		currDocumentShowFieldConfig = (DocumentShowFieldConfig)it.next();
		if(!currDocumentShowFieldConfig.isRead())continue;
		if(sbFieldName.length() == 0){
			sbFieldName.append(currDocumentShowFieldConfig.getFieldname());
			sbFieldDesc.append(currDocumentShowFieldConfig.getDesc());
		}else{
			sbFieldName.append(",").append(currDocumentShowFieldConfig.getFieldname());
			sbFieldDesc.append(",").append(currDocumentShowFieldConfig.getDesc());
		}
	}
	String[] allFields = sbFieldName.toString().split(",");
	String[] aFieldsDesc = sbFieldDesc.toString().split(",");
	HashMap allHasField = new HashMap();
	for(int t=0;t<allFields.length;t++){
		allHasField.put(allFields[t],aFieldsDesc[t]);
	}


//3.为构造检索框搜索字段所添加的变量.
	StringBuffer sSearchFieldInfo = new StringBuffer("[");
	if(!bSingleChnlAndCustomList){
		//sSearchFieldInfo.append("{name: 'DOCTITLE', desc: '文档标题', type: 'string'},{name: 'CRUSER', desc: '发稿人', type: 'string'},{name: 'DOCKEYWORDS', desc:  '关键词', type: 'string'}");

		sSearchFieldInfo.append("{name: 'DOCTITLE', desc: '");
		sSearchFieldInfo.append(LocaleServer.getString("document_query.jsp.label.doctitle", "文档标题"));
		sSearchFieldInfo.append("', type: 'string'},{name: 'CRUSER', desc:'");
		sSearchFieldInfo.append(LocaleServer.getString("document_query.jsp.label.cruser", "发稿人"));
		sSearchFieldInfo.append("', type: 'string'},{name: 'DOCKEYWORDS', desc: '");
		sSearchFieldInfo.append(LocaleServer.getString("document_query.jsp.label.keyword", "关键词"));
		sSearchFieldInfo.append("', type: 'string'}");
	}else{
		//sSearchFieldInfo.append("{name: 'DOCTITLE', desc: wcm.LANG.DOCUMENT_PROCESS_177 || '文档标题', type: 'string'}");
		String[] aSearchField = sSelectFields.split(",");
		for (int m = 0; m < aSearchField.length; m++){			
			String sSearchField = aSearchField[m].split("\\.")[1];						
			if(sSearchField.toUpperCase().equalsIgnoreCase("DOCCHANNEL"))continue;
			if(sSearchField.toUpperCase().equalsIgnoreCase("DOCSTATUS"))continue;
			if(allHasField.get(aSearchField[m])==null)continue;
			if("[".equals(sSearchFieldInfo.toString())){
				sSearchFieldInfo.append("{name: '");
			}else{
				sSearchFieldInfo.append(",{name: '");
			}
			sSearchFieldInfo.append(sSearchField);
			sSearchFieldInfo.append("', desc: '");
			sSearchFieldInfo.append(allHasField.get(aSearchField[m]));
			sSearchFieldInfo.append("', type: 'string'}");//这个类型还要想办法
		}
	}
%>
<%
/**
*遍历当前栏目下扩展字段,提取为检索字段，仅当系统配置“EXTFIELD_QUERY”设置为true时生效
*涉及到输入变量sSearchFieldInfo，oMethodContext
*涉及到输出变量sSearchFieldInfo
*/
%>
<%@include file="document_query_import_4_extfield_query.jsp"%>

<%
//4. 构造分页参数,这段逻辑应该都可以放到服务器端	TODO
	int nPageSize = -1, nPageIndex = 1;
	String strSelectedIds = "", strExcludeDocIds = "";
	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
		strSelectedIds = ","+CMyString.showNull(oMethodContext.getValue("SelectIds"))+",";
		strExcludeDocIds = ","+CMyString.showNull(oMethodContext.getValue("ExcludeDocIds"))+",";
	}	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(viewDocuments.size());
	String sCurrOrderBy = CMyString.showNull(oMethodContext.getValue("OrderBy"));
	out.clear();
%>
<%!
	public String getOrderFlag(String field, String currOrderBy){
		if(CMyString.isEmpty(currOrderBy))return "";
		String[] orderBy = currOrderBy.toLowerCase().split(" ");
		field = field.toLowerCase();
		if(!orderBy[0].equals(field))return "";
		return "&nbsp;" + ("asc".equals(CMyString.showEmpty(orderBy[1], "asc"))?"↑":"↓");
	}
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table<%=bSingleChnlAndCustomList?" CustomList":""%>" borderColor="gray" style="width:100%">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll('RecId');" width="55" WCMAnt:param="document_query.jsp.selectall" class="selAll">全选</td>
		<td WCMAnt:param="abslist.head.preview" width="40" class="td_preview">预览</td>
		<%
			if(!bSingleChnlAndCustomList){				
		%>
		<td grid_sortby="wcmdocument.doctitle"><span WCMAnt:param="documentlist.head.doctitle">文档标题</span><%=getOrderFlag("wcmdocument.doctitle", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.crtime" width="80"><span WCMAnt:param="documentlist.head.crtime">创建时间</span><%=getOrderFlag("wcmchnldoc.crtime", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.cruser" width="70"><span WCMAnt:param="documentlist.head.publishman">发稿人</span><%=getOrderFlag("wcmchnldoc.cruser", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.docchannel" width="70"><span WCMAnt:param="documentlist.head.docchannel">所在栏目</span><%=getOrderFlag("wcmchnldoc.docchannel", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.docstatus" width="60"><span WCMAnt:param="documentlist.head.status">状态</span><%=getOrderFlag("wcmchnldoc.docstatus", sCurrOrderBy)%></td>
		<%
			}else{
				String[] sFields = sSelectFields.split(",");
				String[] sWidth = sFieldsWidth.split(",");
				for(int i=0;i<sFields.length;i++){
					String sHeadTitle="";
					if(allHasField.get(sFields[i])==null){//那就是扩展字段
						String sWhere = "OBJID=? and EXTFIELDID in (select EXTFIELDID from WCMEXTFIELD where FIELDNAME=?)";
						WCMFilter filter = new WCMFilter("WCMCONTENTEXTFIELD",sWhere,"","");
						filter.addSearchValues(nChannelId);
						filter.addSearchValues(sFields[i].split("\\.")[1]);
						ContentExtFields m_oContentExtFields = ContentExtFields.openWCMObjs(null,filter);
						if(m_oContentExtFields.size()>=1){
							ContentExtField m_oContentExtField = (ContentExtField)m_oContentExtFields.getAt(0);
							sHeadTitle = m_oContentExtField.getDesc();
						}
					}else{
						sHeadTitle = allHasField.get(sFields[i]).toString();
					}
					
		%>
				<td grid_sortby="<%=sFields[i]%>" width="<%=sWidth[i]%>" title="<%=sHeadTitle%>"><span><%=sHeadTitle%></span><%=getOrderFlag(sFields[i], sCurrOrderBy)%></td>
		<%
				}
			}
		%>
		<td grid_sortby="wcmdocument.doctype" width="45"><span WCMAnt:param="abslist.head.view">查看</span><%=getOrderFlag("wcmdocument.doctype", sCurrOrderBy)%></td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	String sLoginUser = loginUser.getName();
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ViewDocument viewDocument = (ViewDocument)viewDocuments.getAt(i - 1);
			if (viewDocument == null)
				continue;
			boolean bCanDetail = hasRight(loginUser,viewDocument,34);
			boolean bCanPreview = hasRight(loginUser,viewDocument,38);
			int nRecId = viewDocument.getChnlDocProperty("RECID", 0);
			int nDocId = viewDocument.getDocId();
			//排除的DOCID处理
			bCanDetail = bCanDetail && strExcludeDocIds.indexOf(","+nDocId+",")==-1;
			boolean bIsSelected = strSelectedIds.indexOf(","+nRecId+",")!=-1;
			int nChnlId = viewDocument.getChannelId();
			int nDocChannelId = viewDocument.getDocChannelId();
			Channel docChannel = null;
			if(oMethodContext.getValue("ChannelId",0)!=0){
				docChannel = viewDocument.getDocChannel();
			}
			else{
				docChannel = viewDocument.getChannel();
			}
			String sRightValue = viewDocument.getRightValue(loginUser).toString();
			boolean bTopped = viewDocument.isTopped();
			int nDocType = viewDocument.getPropertyAsInt("DOCTYPE", 0);
			//TODO
			int nModal = viewDocument.getChnlDocProperty("MODAL", 0);
			//chnldoc
			//ChnlDoc currChnlDoc = ChnlDoc.findByDocAndChnl(nDocId,nChnlId);
			ChnlDoc currChnlDoc = viewDocument.getChnlDoc();
			boolean bTopForever = false;//是否永久置顶
			CMyDateTime dtTopInvalidTime = currChnlDoc.getInvalidTime();
			if(dtTopInvalidTime != null && bTopped && dtTopInvalidTime.toString() == null){
				bTopForever = true;
			}
			String sDocTypeName = viewDocument.getTypeString();
			int nStatusId = viewDocument.getStatusId();
			String nStatusName = LocaleServer.getString("document_query.label.unknown", "未知");
			if(viewDocument.getStatus()!=null){
				nStatusName = viewDocument.getStatus().getDisp();
			}
			boolean bDraft = false;
			if(nStatusId == Status.STATUS_ID_DRAFT){
				bDraft = true;
			}
			String sTitle = null;
			if("true".equals(sStripDoctitleTags.toLowerCase())) {
				sTitle = CMyString.filterForHTMLValue(CMyString.stripHTMLTags(viewDocument.getPropertyAsString("DOCTITLE")));
			}else {
				//用transdisplay有点问题,&nbsp;会转成空格,暂时先改成filterForHTMLValue
				sTitle = CMyString.filterForHTMLValue(viewDocument.getPropertyAsString("DOCTITLE"));
			}
			String sCrUser = CMyString.filterForHTMLValue(viewDocument.getPropertyAsString("CrUser"));
			CMyDateTime dtValue = new CMyDateTime();
			if( nModal == ChnlDoc.MODAL_LINK || nModal == ChnlDoc.MODAL_MIRROR){
				dtValue = currChnlDoc.getPropertyAsDateTime("CrTime");
			}else{
				dtValue = viewDocument.getPropertyAsDateTime("CrTime");
			}
			String sCrTime = convertDateTimeValueToString(oMethodContext,dtValue);
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";

			String sDocLinkToCls = "", sDocMirrorToCls = "";
			if(nModal == ChnlDoc.MODAL_ENTITY && sLoginUser.equals(sCrUser)){
				String sDocLinkTo = viewDocument.getPropertyAsString("DocLinkTo");
				if(!CMyString.isEmpty(sDocLinkTo)){
					sDocLinkToCls = "linkto";
				}

				String sDocMirrorTo = viewDocument.getPropertyAsString("DocMirrorTo");
				if(!CMyString.isEmpty(sDocMirrorTo)){
					sDocMirrorToCls = "mirrorto";
				}
			}
			int nSiteId = viewDocument.getChannel().getSiteId();
			int nDocKind = viewDocument.getChnlDocProperty("dockind", 0);
%>
	<tr id="tr_<%=nRecId%>" rowid="<%=nRecId%>" class="grid_row <%=sRowClassName%><%=(bIsSelected)?" grid_row_active":""%> <%=(bCanDetail)?"grid_selectable_row":"grid_selectdisable_row"%>" right="<%=sRightValue%>" doctype="<%=nDocType%>" isTopped="<%=bTopped%>" channelid="<%=nDocChannelId%>" currchnlid="<%=nChnlId%>" docid="<%=nDocId%>" doctitle="<%=sTitle%>" channelType="<%=docChannel.getType()%>" dockind="<%=nDocKind%>" docstatusId="<%=nStatusId%>"siteId=<%=nSiteId%> bDraft=<%=bDraft%>>
		<td><input type="checkbox" id="cb_<%=nRecId%>" class="grid_checkbox" name="RowId" value="<%=nRecId%>" <%=(bCanDetail)?"":"disabled"%> <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRecId%>"><%=i%></span></td>
		<td class="td_preview"><span class="<%=(bCanPreview)?"object_preview":"objectcannot_preview"%>" style="width:30px" grid_function="preview" right_index="38">&nbsp;</span></td>
		<%
			if(!bSingleChnlAndCustomList){
				
		%>
		<td class="doctitle"><a contextmenu="1" unselectable="on" href="#" onclick="return false;" grid_function="chnldoc_edit" title="<%=LocaleServer.getString("ViewDocument.label.DocId","文档ID")%>:[<%=nDocId%>]" id="doctitle_<%=nRecId%>" right_index="32"><span class="<%=(bTopped)?(bTopForever?"document_topped_forEver":"document_topped"):""%>"></span><span class="document_modal_<%=nModal%> <%=sDocLinkToCls%><%=sDocMirrorToCls%>"></span><%=sTitle%><span class="<%=(viewDocument.getPropertyAsInt("AttachPic", 0)>0)?"document_attachpic":""%>"></span></a></td>
		<td><%=sCrTime%></td>
		<td><%=sCrUser%></td>
		<td title="<%=CMyString.filterForHTMLValue(docChannel.getDispDesc())%> [ID-<%=docChannel.getId()%>]"><a unselectable="on" href="#" onclick="return false;" grid_function="open_channel" ext_channelid="<%=docChannel.getId()%>" channelType="<%=docChannel.getType()%>" rightValue="<%=getRightValue(loginUser,docChannel)%>"><%=CMyString.filterForHTMLValue(docChannel.getDispDesc())%></a></td>
		<td id="docstatus_<%=nRecId%>"><%=getStatusLocale(nStatusName)%></td>
		<%
			}else{
				String[] sFields = sSelectFields.split(",");
				for(int z=0;z<sFields.length;z++){
					String dbField = sFields[z].split("\\.")[1];
					if(dbField.toUpperCase().equalsIgnoreCase("DOCTITLE")){
%>
		<td class="doctitle"><a contextmenu="1" unselectable="on" href="#" onclick="return false;" grid_function="chnldoc_edit" title="<%=LocaleServer.getString("ViewDocument.label.DocId","文档ID")%>:[<%=nDocId%>]" id="doctitle_<%=nRecId%>" right_index="32"><span class="<%=(bTopped)?(bTopForever?"document_topped_forEver":"document_topped"):""%>"></span><span class="document_modal_<%=nModal%> <%=sDocLinkToCls%><%=sDocMirrorToCls%>"></span><%=sTitle%><span class="<%=(viewDocument.getPropertyAsInt("AttachPic", 0)>0)?"document_attachpic":""%>"></span></a></td>
<%
						continue;
					}
					String sValue = CMyString.filterForHTMLValue(viewDocument.getPropertyAsString(dbField));
					if(dbField.toUpperCase().equalsIgnoreCase("DOCCHANNEL")){
						sValue = CMyString.filterForHTMLValue(docChannel.getDispDesc());
					}else if(dbField.toUpperCase().equalsIgnoreCase("DOCSTATUS")){
						sValue = nStatusName;
					}
					if(CMyString.isEmpty(sValue))sValue = CMyString.filterForHTMLValue(viewDocument.getChnlDocProperty(dbField));
		%>
				<td><%=sValue%></td>
		<%
				}
			}
		%>
		<td><span class="<%=(bCanDetail)?"doctype_"+nDocType:"cannot_doctype_"+nDocType%>" title="<%=sDocTypeName%>" style="width:30px;border-right:0;cursor:pointer;float:none;" <%=(bCanDetail)?"grid_function=\"chnldoc_view\"":""%> right_index="34">&nbsp;
		</span></td>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	sSearchFieldInfo.append("]");
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr>
			<td colspan="<%=nColspan%>" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
		</tr>
	</tbody>
</table>
<script>
	try{
		getSearchFieldInfo(<%=sSearchFieldInfo.toString()%>);
		initDisMode(<%=bContainsChildren%>);
		Ext.apply(PageContext, {
			CanSort : <%=viewDocuments.canSort()%>
		});
		wcm.Grid.init({
			OrderBy : '<%=sCurrOrderBy%>',
			RecordNum : <%=currPager.getItemCount()%>
		});
		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
	}catch(err){
		//Just skip it.
	}
</script>

<%!
	private String getPageAttributes(CPager _pager) {
		String sRetVal = "";
		sRetVal += "Num:"+String.valueOf(_pager.getItemCount());
		if (_pager.getPageSize() > 0){
			sRetVal += ",PageSize:"+String.valueOf(_pager.getPageSize());
			sRetVal += ",PageCount:"+String.valueOf(_pager.getPageCount());
			sRetVal += ",CurrPageIndex:"+String.valueOf(_pager.getCurrentPageIndex());
		}
		return sRetVal;
	}
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		if(_objCurrent instanceof ViewDocument){
			return ((ViewDocument)_objCurrent).hasRight(_currUser,_nRightIndex);
		}
		else if(_objCurrent instanceof Document){
			return DocumentAuthServer.hasRight(_currUser,((Document)_objCurrent).getChannel(),(Document)_objCurrent,_nRightIndex);
		}
		else if(_objCurrent instanceof ChnlDoc){
			return DocumentAuthServer.hasRight(_currUser,(ChnlDoc)_objCurrent,_nRightIndex);
		}
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
	private String convertDateTimeValueToString(MethodContext _methodContext, CMyDateTime _dtValue) {
		String sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
		if (_methodContext != null) {
			sDateTimeFormat = _methodContext.getValue("DateTimeFormat");
			if (sDateTimeFormat == null) {
				sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
			}
		}
		String sDtValue = _dtValue.toString(sDateTimeFormat);
		return sDtValue;
	}
	private String getRightValue(User loginUser, Channel _channel) throws WCMException{
        String rightValue = "";
        if (loginUser.isAdministrator()
                || loginUser.equals(_channel.getCrUser())) {
            rightValue = RightValue.getAdministratorValues();
        } else {
            rightValue = getRightValueByMember(_channel, loginUser)
                    .toString();
        }
		return rightValue;
	}
%>