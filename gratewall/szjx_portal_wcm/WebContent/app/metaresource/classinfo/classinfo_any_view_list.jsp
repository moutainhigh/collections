<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
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
<%@ page import="com.trs.DreamFactory" %> 
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.cms.auth.domain.IObjectMemberMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@include file="../../include/public_processor.jsp"%>
<%@include file="../../channel/document_field_init.jsp"%>

<%
try{
%>

<%
	//调用服务，获取字段集合
	processor.setAppendParameters(new String[]{"OrderBy", "crtime desc"});
	Documents oDocuments = (Documents) processor.excute("wcm61_ClassInfoView", "queryDocuments");
	
	int nChannelId = processor.getParam("ChannelId",0);
	String sChnlDocSelectFields = "WCMChnlDoc.DOCKIND,WCMChnlDoc.DOCID,WCMChnlDoc.ChnlId,WCMChnlDoc.DocStatus,WCMChnlDoc.DocChannel,WCMChnlDoc.DocOrderPri,WCMChnlDoc.Modal,WCMChnlDoc.RecId";//oMethodContext.getValue("ChnlDocSelectFields");
	String sDocumentSelectFields = "DOCID,DocTitle,DocType,CrUser,CrTime,AttachPic,FLOWOPERATIONMARK";//oMethodContext.getValue("DocumentSelectFields");
	
	String strSelectedIds = "", strExcludeDocIds = "";
	String selectItem = CMyString.showEmpty(processor.getParam("SelectItem"), "DocTitle,CrUser");
	String searchValue = "";
	if(selectItem.indexOf(",")>-1){
		String sItem = selectItem.split(",")[0];
		searchValue = CMyString.showEmpty(processor.getParam(sItem), "");
	}else{
		searchValue = CMyString.showEmpty(processor.getParam(selectItem), "");
	}


	//将字段集合进行分页设置
	CPager currPager = new CPager(processor.getParam("PageSize", 20));
	currPager.setCurrentPageIndex(processor.getParam("CurrPage", 1));
	currPager.setItemCount(oDocuments.size());
%>

<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 字段库管理 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/common.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<script src="../js/jquery-wcm-ui-extend.js"></script>
	<script src="../js/PageRefreshMgr.js"></script>
	<script src="classinfo_any_view_list.js"></script>

	<link href="../css/blue/list_common.css" rel="stylesheet" type="text/css" />
	<link href="../css/blue/jquery-wcm-ui-extend.css" rel="stylesheet" type="text/css" />
	<link href="../js/button/button.css" rel="stylesheet" type="text/css" />
	<link href="classinfo_any_view_list.css" rel="stylesheet" type="text/css" />

	<script language="javascript">
<!--
	$(document).ready(function(){
		try{
			$.PageContext.drawNavigator({
				Num : <%=currPager.getItemCount()%>,
				PageSize : <%=currPager.getPageSize()%>,
				PageCount : <%=currPager.getPageCount()%>,
				CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
			});
		}catch(err){
			alert(err.message);
			//Just skip it.
		}
	});
//-->
</script>
</head>

<body>
<script src="../js/cssrender.js"></script>
<div class="container hideNavTree" id="container">
	<div class="left">
		&nbsp;
	</div>
	<div class="seperator">
		<div class="sepBar" id="sepBar"></div>
	</div>
	<div class="right">
		<!-- 页面头部区域，提供的是操作按钮栏，描述信息和检索框 -->
		<div class="header">
			<!-- 描述信息区域 -->
			<div class="desc" style="display:none;"></div>
			<div class="oper_field">
				<!-- 操作按钮栏区域 -->
				<div class="toolbar" id="toolbar">
					<a href="#"  onclick="publishRes();">发布</a>
					<a href="#"  onclick="previewRes();">预览</a>
					<a href="#"  onclick="deleteRes();">删除</a>
				</div>
				<!-- 检索框区域 -->
				<div class="querySearch" id="querySearch">
					<form method="get" id="search_form" action="metafield_list.jsp">
						<input type="hidden" name="viewId" id="viewId" value="" />
						<input type="text" name="" id="search_input" class="search_input" value="<%=CMyString.filterForHTMLValue(searchValue)%>" />
						<select name="SelectItem" id="search_select" class="search_select" value="">
							<option value="DocTitle,CrUser" >全部</option>
							<option value="DocTitle" <%="DocTitle".equals(selectItem)?"selected=true":""%>>文档标题</option>
							<option value="CrUser" <%="CrUser".equals(selectItem)?"selected=true":""%>>创建者</option>
						</select>
						<input type="button" name="" id="search_btn" class="search_btn" value="" />
					</form>
				</div>
			</div>
		</div>

		<!-- 数据区域 -->
		<div class="content" id="list-data">

			<table cellspacing=0 border="0" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
				<tr id="grid_head" class="grid_head">
					<td onclick="" width="55" class="head_td selAll">全选</td>
					<td class="head_td td_edit">修改</td>
					<td class="head_td"><span >文档标题</span></td>
					<td class="head_td" width="80"><span >创建时间</span></td>
					<td class="head_td" width="70"><span >发稿人</span></td>
					<td class="head_td" width="70"><span>所在栏目</span></td>
					<td class="head_td" width="60"><span >状态</span></td>
					<td class="head_td_last" width="45"><span >删除</span></td>
				</tr>
				<tbody class="grid_body" id="grid_body">
			<%
				String sLoginUser = loginUser.getName();
				for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
					try{
						Document document = (Document)oDocuments.getAt(i - 1);
						if (document == null)
							continue;
						int nChnlId = document.getChannelId();
						ChnlDoc currChnlDoc = ChnlDoc.findByDocAndChnl(document.getId(), nChnlId);
						ViewDocument viewDocument = ViewDocument.findById(document.getChannel(), currChnlDoc.getId(),sChnlDocSelectFields, sDocumentSelectFields);

						boolean bCanDetail = hasRight(loginUser,viewDocument,34);
						boolean bCanEdit = hasRight(loginUser,document,32);
						boolean bCanDelete = hasRight(loginUser,document,33);

						int nRecId = viewDocument.getChnlDocProperty("RECID", 0);
						int nDocId = viewDocument.getDocId();
						//排除的DOCID处理
						bCanDetail = bCanDetail && strExcludeDocIds.indexOf(","+nDocId+",")==-1;
						boolean bIsSelected = strSelectedIds.indexOf(","+nRecId+",")!=-1;
						//int nChnlId = viewDocument.getChannelId();
						int nDocChannelId = viewDocument.getDocChannelId();
						Channel docChannel = null;
						if(nChannelId != 0){
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
						//用transdisplay有点问题,&nbsp;会转成空格,暂时先改成filterForHTMLValue
						String sTitle = CMyString.filterForHTMLValue(viewDocument.getPropertyAsString("DOCTITLE"));
						String sCrUser = CMyString.filterForHTMLValue(viewDocument.getPropertyAsString("CrUser"));
						CMyDateTime dtValue = new CMyDateTime();
						if( nModal == ChnlDoc.MODAL_LINK || nModal == ChnlDoc.MODAL_MIRROR){
							dtValue = currChnlDoc.getPropertyAsDateTime("CrTime");
						}else{
							dtValue = viewDocument.getPropertyAsDateTime("CrTime");
						}
						String sCrTime = dtValue.toString(CMyDateTime.DEF_DATETIME_FORMAT_PRG);
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
					<tr id="tr_<%=nRecId%>" rowid="<%=nRecId%>" class="grid_row <%=sRowClassName%><%=(bIsSelected)?" grid_row_active":""%> <%=(bCanDetail)?"grid_selectable_row":"grid_selectdisable_row"%>" right="<%=sRightValue%>" doctype="<%=nDocType%>" isTopped="<%=bTopped%>" channelid="<%=nDocChannelId%>" currchnlid="<%=nChnlId%>" docid="<%=nDocId%>" doctitle="<%=sTitle%>" channelType="<%=docChannel.getType()%>" dockind="<%=nDocKind%>" siteId=<%=nSiteId%>>
						<td><input type="checkbox" id="cb_<%=nRecId%>" class="grid_checkbox" name="RowId" value="<%=nRecId%>" <%=(bCanDetail)?"":"disabled"%> <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRecId%>"><%=i%></span></td>
						<td><span class="<%=bCanEdit ? "object_edit":"objectcannot_edit no_right"%> grid_function" grid_function="edit">&nbsp;</span></td>
						<td class="doctitle"><a contextmenu="1" unselectable="on" href="#" onclick="return false;" grid_function="chnldoc_edit" title="<%=LocaleServer.getString("ViewDocument.label.DocId","文档ID")%>:[<%=nDocId%>]" WCMAnt:paramattr="title:document_query.jsp.titleTip" id="doctitle_<%=nRecId%>" right_index="32"><span class="<%=(bTopped)?(bTopForever?"document_topped_forEver":"document_topped"):""%>"></span><span class="document_modal_<%=nModal%> <%=sDocLinkToCls%><%=sDocMirrorToCls%>"></span><%=sTitle%><span class="<%=(viewDocument.getPropertyAsInt("AttachPic", 0)==1)?"document_attachpic":""%>"></span></a></td>
						<td><%=sCrTime%></td>
						<td><%=sCrUser%></td>
						<td title="<%=CMyString.filterForHTMLValue(docChannel.getDispDesc())%> [ID-<%=docChannel.getId()%>]"><a unselectable="on" href="#" onclick="return false;" grid_function="open_channel" ext_channelid="<%=docChannel.getId()%>" channelType="<%=docChannel.getType()%>" rightValue="<%=getRightValue(loginUser,docChannel)%>"><%=CMyString.filterForHTMLValue(docChannel.getDispDesc())%></a></td>
						<td id="docstatus_<%=nRecId%>"><%=nStatusName%></td>
						<td><span class="<%=bCanDelete ? "object_delete":"objectcannot_delete no_right"%> grid_function" grid_function="delete">&nbsp;</span></td>
					</tr>
						<%
								}catch(Exception ex){
									ex.printStackTrace();
								}
							}
						%>
				</tbody>
			<%if(oDocuments.isEmpty()){%>
				<tbody id="grid_NoObjectFound">
					<tr><td colspan="7" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
				</tbody>
			<%}%>
			</table>
			
		</div>

		<!-- 底部区域，主要是当需要的时候，做翻页的区域 -->
		<div class="footer">
			<div class="page-nav" id="list_navigator"></div>
		</div>
	</div>
</div>

	<%
	}catch(Throwable tx){
		tx.printStackTrace();
		throw new WCMException("libfield_list.jsp运行期异常!", tx);
	}
	%>
</body>
</html>

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
	public String getOrderFlag(String field, String currOrderBy){
		if(CMyString.isEmpty(currOrderBy))return "";
		String[] orderBy = currOrderBy.toLowerCase().split(" ");
		field = field.toLowerCase();
		if(!orderBy[0].equals(field))return "";
		return "&nbsp;" + ("asc".equals(CMyString.showEmpty(orderBy[1], "asc"))?"↑":"↓");
	}
	public RightValue getRightValueByMember(CMSObj obj, User user) throws WCMException {
		try {
			if (user.isAdministrator()) {
				return RightValue.getAdministratorRightValue();
			}
			RightValue oRightValue;
			if(obj instanceof BaseChannel){
				if(user.getName().equalsIgnoreCase(obj.getCrUserName())){
					return RightValue.getAdministratorRightValue();
				}
				IObjectMemberMgr oObjectMemberMgr = (IObjectMemberMgr) DreamFactory.createObjectById("IObjectMemberMgr");
				if(oObjectMemberMgr.canOperate(user, obj.getWCMType(), obj.getId())){
					oRightValue = AuthServer.getRightValue(obj, user);
				}else{
					oRightValue = new RightValue();
				}
			}else{
				oRightValue = AuthServer.getRightValue(obj, user);
			}
			return oRightValue;
		} catch (Exception e) {
			throw new WCMException("构造[" + obj + "]权限信息失败!", e);
		}
	}
%>