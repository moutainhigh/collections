<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.infra.persistent.NullValue" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ViewDocuments)){
		throw new WCMException(CMyString.format(LocaleServer.getString("docrecycle_query.jsp.servicenoobject", "服务(com.trs.ajaxservice.viewdocumentServiceProvider.query)返回的对象集合类型不为ViewDocuments，而为{0}，请确认。"), new Object[]{result.getClass()}));
	}
	ViewDocuments objs = (ViewDocuments)result;
%>
<%!
	public CMyDateTime convertToDateTime(Object objValue) {
        if (objValue instanceof CMyDateTime)
            return (CMyDateTime) objValue;

        CMyDateTime dtValue = new CMyDateTime();
        if (objValue == null || objValue instanceof NullValue)
            return dtValue;

        try {
            dtValue.setDateTimeWithString(objValue.toString());
            return dtValue;
        } catch (Exception e) {
            e.printStackTrace();
            return new CMyDateTime();
        }
    }
%>

<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="docrecycle_list.head.restore" width="40">还原</td>
<td grid_sortby="wcmDocument.DocTitle"><span WCMAnt:param="docrecycle_list.head.DocTitle">文档标题</span><%=getOrderFlag("wcmDocument.DocTitle", sCurrOrderBy)%></td>
<td grid_sortby="wcmchnldoc.docchannel" width="100"><span WCMAnt:param="docrecycle_list.head.chnlid">所属栏目</span><%=getOrderFlag("wcmchnldoc.docchannel", sCurrOrderBy)%></td>
<td width="50"><span WCMAnt:param="docrecycle_list.head.view">查看</span><%=getOrderFlag("view", sCurrOrderBy)%></td>
<td grid_sortby="WCMChnlDoc.OperTime" width="120"><span WCMAnt:param="docrecycle_list.head.OperTime">删除时间</span><%=getOrderFlag("WCMChnlDoc.OperTime", sCurrOrderBy)%></td>
<td grid_sortby="WCMChnlDoc.OperUser" width="60"><span WCMAnt:param="docrecycle_list.head.OperUser">删除者</span><%=getOrderFlag("WCMChnlDoc.OperUser", sCurrOrderBy)%></td>
<td WCMAnt:param="docrecycle_list.head.delete" width="60">删除</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	String sLoginUser = loginUser.getName();
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ViewDocument obj = (ViewDocument)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRecId = obj.getChnlDocProperty("RECID", 0);
			int nDocId = obj.getDocId();
			int nRowId = nRecId;
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			//String sRightValue = getRightValue(obj, loginUser).toString();
			String sRightValue = obj.getRightValue(loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			boolean bCanRestore = hasRightRecy(loginUser,obj,33);
			boolean bCanDelete = hasRightRecy(loginUser,obj,33);
			boolean bCanView = hasRightRecy(loginUser,obj,34);
			int nDocType = obj.getType();
			String sDocType = obj.getTypeString();
			int nDocChannelId = obj.getDocChannelId();
			boolean bTopped = obj.isTopped();
			int nChnlId = obj.getChannelId();
			Channel docChannel = null;
			ChnlDoc currChnlDoc = ChnlDoc.findByDocAndChnl(nDocId,nChnlId);
			boolean bTopForever = false;//是否永久置顶
			CMyDateTime dtTopInvalidTime = currChnlDoc.getInvalidTime();
			if(bTopped && dtTopInvalidTime.toString() == null){
				bTopForever = true;
			}

			if(oMethodContext.getValue("ChannelId",0)!=0){
				docChannel = obj.getDocChannel();
			}
			else{
				docChannel = obj.getChannel();
			}
			String sChnlName = CMyString.transDisplay(docChannel.getName());
			String sChnlDesc = CMyString.transDisplay(docChannel.getDesc());
			int nModal = obj.getChnlDocProperty("MODAL", 0);
			String sCrUser = obj.getPropertyAsString("CrUser");
			String sDocLinkToCls = "", sDocMirrorToCls = "";
			if(nModal == ChnlDoc.MODAL_ENTITY && sLoginUser.equals(sCrUser)){
				String sDocLinkTo = obj.getPropertyAsString("DocLinkTo");
				if(!CMyString.isEmpty(sDocLinkTo)){
					sDocLinkToCls = "linkto";
				}

				String sDocMirrorTo = obj.getPropertyAsString("DocMirrorTo");
				if(!CMyString.isEmpty(sDocMirrorTo)){
					sDocMirrorToCls = "mirrorto";
				}
			}
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>" docid="<%=nDocId%>" channelid="<%=nDocChannelId%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="<%=(bCanRestore)?"object_restore":"objectcannot_restore"%> grid_function" grid_function="restore" title="还原" WCMAnt:paramattr="title:docrecycle_list.head.restore">&nbsp;</span></td>
<td id="DocTitle_<%=nRowId%>" style="text-align:left;padding-left:10px" title="[<%=LocaleServer.getString("ViewDocument.label.DocId", "文档ID")%>-<%=nDocId%>]" >
		<span class="<%=(bTopped)?(bTopForever?"document_topped_forEver":"document_topped"):""%>"></span>
		<span class="document_modal_<%=nModal%> <%=sDocLinkToCls%> <%=sDocMirrorToCls%>"></span>
		<%=CMyString.transDisplay(obj.getPropertyAsString("DocTitle"))%><span class="<%=(obj.getPropertyAsInt("AttachPic", 0)==1)?"document_attachpic":""%>"></span></td>
<td id="chnlid_<%=nRowId%>" title="<%=sChnlDesc%> [ID-<%=docChannel.getId()%>]">
		<%=sChnlDesc%></td>
<td><span class="<%=(bCanView)?"object_view":"objectcannot_view"%> grid_function doctype_<%=nDocType%>" grid_function="view" title="<%=sDocType%>">&nbsp;</span></td>
<td id="OperTime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext,convertToDateTime(obj.getChnlDocProperty("OperTime")))%></td>
<td id="OperUser_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getChnlDocProperty("OperUser"))%></td>
<td><span class="<%=(bCanDelete)?"object_delete":"objectcannot_delete"%> grid_function" grid_function="delete" title="删除" WCMAnt:paramattr="title:docrecycle_list.head.delete">&nbsp;</span></td>

	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="8" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	try{
		wcm.Grid.init({
			OrderBy : '<%=CMyString.filterForJs(sCurrOrderBy)%>',
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
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("docrecycle.jsp.runtimeex", "chnldoc_query.jsp运行期异常!"), tx);
}
%>
<%!
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
	private boolean hasRightRecy(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
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

%>