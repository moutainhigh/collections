<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<%@ page import="org.dom4j.Element" %>
<%@ page import="com.trs.cms.process.FlowEngineHelper" %>
<%@ page import="com.trs.cms.process.ProcessConstants" %>
<%@ page import="com.trs.cms.process.definition.FlowNode" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.process.ProcessConstants" %>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.cms.process.engine.ContentProcessInfo" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.cms.process.definition.FlowNode" %>
<%@ page import="com.trs.components.gkml.sqgk.process.element.FlowContentApplyFormImpl" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%@include file="../system/status_locale.jsp"%>
<%
    Object result = request
                .getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	//int  nViewType = request.getAttribute("ViewType");

	int nViewType = oMethodContext.getValue("ViewType",1);
	if(!(result instanceof IFlowContent[])){
            throw new WCMException(CMyString.format(LocaleServer.getString("iflowcontent_query.jsp.servicenoobject","服务(com.trs.ajaxservice.ProcessService.query)返回的对象集合类型不为IFlowContents,而为{0},请确认."), new Object[]{result.getClass()}));
	}
	IFlowContent[] objs = (IFlowContent[])result;
	currPager = new CPager();
	currPager.setItemCount(objs.length);
	currPager.setPageSize(oMethodContext.getPageSize());
    currPager.setCurrentPageIndex(oMethodContext.getPageIndex());
%>
<%!private FlowDoc getFlowDoc(IFlowContent _flowContent){
		FlowDoc currFlowDoc = null;
		if (_flowContent.getFlowDoc() != null) {
			currFlowDoc = _flowContent.getFlowDoc();
		}
		return currFlowDoc;
	}
	private int getStartFlowDocId(IFlowContent _flowContent, FlowDoc _flowDoc, User _loginUser) throws WCMException { 
		if(_flowDoc == null) return 0;
		WCMFilter filter = new WCMFilter(
                "",
                "ObjType=? and ObjId=? and PostUser=? and ParentId=0 and Flag=?",
                "FlowDocId Desc");
        filter.addSearchValues(_flowContent.getType());
        filter.addSearchValues(_flowContent.getId());
        filter.addSearchValues(_loginUser.getName());
		filter.addSearchValues(ProcessConstants.FLAG_START);
        filter.setMaxRowNumber(1);

        FlowDocs flowDocs = new FlowDocs(null);
        flowDocs.setMaxBufferSize(2);
        flowDocs.open(filter);
		if(flowDocs == null) 
			return 0;
		else 
			return flowDocs.getAt(0).getId();
	}
	
	private String[] getFlowDocComments(IFlowContent _flowContent){
			FlowDoc currFlowDoc = getFlowDoc(_flowContent);
            String[] pComments = FlowEngineHelper.parseComments(currFlowDoc);
			return pComments;
    }
	private String getFlowStatusInfo(int _nDocFlag){
		switch(_nDocFlag){
			case ProcessConstants.FLAG_BACK : 
				return LocaleServer.getString("flowdoc.label.back","返工");
			case ProcessConstants.FLAG_FORCEEND : 
				return LocaleServer.getString("flowdoc.label.forceend","强行结束");
			case ProcessConstants.FLAG_REFUSED : 
				return LocaleServer.getString("flowdoc.label.refuse","拒绝");
			 default:
				 return " ";
		}
		
	}
    private String makeProcessInfo(FlowDoc _flowDoc) throws WCMException {
        if (_flowDoc == null)
            return LocaleServer.getString("flowdoc.label.flowover", "流转结束.");

        String sDateFormat = LocaleServer.getString("iflowcontent.query.dataformat", "yy年MM月dd日 HH:mm");

		//真实用户名的处理
		User oPostUser = _flowDoc.getPostUser();
        String sPostUserName = oPostUser.getName();
        String sPostUserTrueName = oPostUser.getTrueName();
        if(!(sPostUserTrueName == null || "".equals(sPostUserTrueName)))
            sPostUserName = sPostUserTrueName;
        String sToUserNames = _flowDoc.getToUserTrueNames();

        // 单人的情况
        if (_flowDoc.getToUserId() > 0) {
            if (_flowDoc.isWorked()) {
                return CMyString.format(LocaleServer.getString("flowdoc.label.danrendedeal","{0}[{1}]交给 {2}的事情已于 [{3}]处理完成."), new String[] {sPostUserName, _flowDoc.getPostTime().toString(sDateFormat),sToUserNames, _flowDoc.getPropertyAsDateTime("WorkTime").toString(sDateFormat)});
            }

            if (_flowDoc.isReceived()) {
                // 已经收到,同时也签收
                if (_flowDoc.isAccepted()) {

                    return CMyString.format(LocaleServer.getString("flowdoc.label.sdbingqianshou","{0}已于[{1}]交给{2}处理,{2}已于[{3}]决定开始处理."),
                            new String[] {
                                    sPostUserName,
                                    _flowDoc.getPostTime()
                                            .toString(sDateFormat),
                                    sToUserNames,
                                    _flowDoc.getAcceptTime().toString(
                                            sDateFormat) });

                }

                // 已经收到,但是未签收 
                return CMyString
                        .format(LocaleServer.getString("flowdoc.label.sdwqianshou","{0}已于[{1}]交给{2}处理,{2}已于[{3}]收到,但尚未决定是否处理."),
                                new String[] {
                                        sPostUserName,
                                        _flowDoc.getPostTime().toString(
                                                sDateFormat),
                                        sToUserNames,
                                        _flowDoc.getReceiveTime().toString(
                                                sDateFormat) });
            }

            // 尚未收到
            return CMyString.format(LocaleServer.getString("flowdoc.label.notqianshou","{0}已于 [{1}]交给{2}处理,但{2}尚未收到."), new String[]{sPostUserName,_flowDoc.getPostTime().toString(sDateFormat),sToUserNames });
        }

        // 处理多人的情况
        // ge add by gfc @2007-9-21 上午10:55:38 防止在ToUsers字段为空时抛异常
        if (CMyString.isEmpty(_flowDoc.getToUserIds())) {
            // ge modify by gfc @2008-5-4 下午04:09:18 实际上并不需要输出任何warn信息
            // logger.warn("文档流转记录[" + _flowDoc.getId() + "]有误!");
            // return "<错误的数据!>";
            return "";
        }
        FlowDocs flowDocs = _flowDoc.getChildren(null, null);
        StringBuffer sbResult = new StringBuffer(flowDocs.size() * 200);
        sbResult.append(CMyString.format(LocaleServer.getString("flowdoc.label.duorenshiqing", "{0}[{1}]交给{2}的事情."),new String[] { sPostUserName,_flowDoc.getPostTime().toString(sDateFormat),sToUserNames}));

        boolean bFirst = true;
        for (int i = 0, nSize = flowDocs.size(); i < nSize; i++) {
            // 校验一些必要的前提条件
            FlowDoc childFlowDoc = (FlowDoc) flowDocs.getAt(i);
            if (childFlowDoc == null)
                continue;
            User toUser = childFlowDoc.getToUser();
            if (toUser == null)
                continue;

			//真实用户名的处理
			String sToUserName = toUser.getName();
            String sToUserTrueName = toUser.getTrueName();
            if(sToUserTrueName != null && !"".equals(sToUserTrueName))
                sToUserName = sToUserTrueName;
            // 不同人处理状态的分隔符
            if (bFirst) {
                bFirst = false;
            } else {
                //sbResult.append(";");
            }

            // 处理完成
            if (childFlowDoc.isWorked()) {
                sbResult.append(CMyString.format(LocaleServer.getString("flowdoc.label.duorenwancheng", "{0}已于 [{1}]处理完成."),new String[] { sToUserName,childFlowDoc.getPropertyAsDateTime("WorkTime").toString(sDateFormat) }));
                continue;
            }

            // 收到或者签收
            if (childFlowDoc.isReceived()) {
                // 已经收到,同时也签收
                if (childFlowDoc.isAccepted()){sbResult.append(CMyString.format(LocaleServer.getString("flowdoc.label.duorenkaishichuli","{0}已于 [{1}]决定开始处理."),new String[] {sToUserName,childFlowDoc.getAcceptTime().toString(sDateFormat)}));
                    continue;
                }

                // 已经收到,但是未签收
                sbResult.append(CMyString.format(LocaleServer.getString("flowdoc.label.duorenweiqianshou","{0}已于 [{1}]收到,但尚未决定是否处理."), new String[]{sToUserName,childFlowDoc.getReceiveTime().toString(sDateFormat) }));
                continue;
            }

            // 尚未收到
            sbResult.append(CMyString.format(LocaleServer.getString("flowdoc.label.duorenweishoudao", "{0}尚未收到."),new String[]{sToUserName}));

        }

        return sbResult.toString();

    }
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray" style="width:100%;">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td width="60"><span WCMAnt:param="iflowcontent_list.head.tracing">处理过程</span></td>
		<td width="45%"><span WCMAnt:param="iflowcontent_list.head.showDocOptionItems">文档标题</span></td>
<%if(nViewType!=3){%>
		<td width="80"><span WCMAnt:param="iflowcontent_list.head.FlagDesc">操作</span></td>
<%}
if(nViewType ==3){%>
		<td width="100"><span WCMAnt:param="iflowcontent_list.head.StartTime">发起时间</span></td>
		<td width="35%"><span WCMAnt:param="iflowcontent_list.head.ProcessInfo">当前状态</span></td>
<%}
if(nViewType!=3){%>
		<td width="20%"><span WCMAnt:param="iflowcontent_list.head.Comment">意见</span></td>
		<td grid_sortby="PostTime" width="100"><span WCMAnt:param="iflowcontent_list.head.PostTime">时间</span><%=getOrderFlag("PostTime", sCurrOrderBy)%></td>
<% 
	}
%>
		<td width="50"><span WCMAnt:param="iflowcontent_list.head.Status">文档状态</span></td>
<%
if(nViewType==2){%>
		<td width="60"><span WCMAnt:param="iflowcontent_list.head.reasign">重新指派</span></td>

<% }
if(nViewType==1){%>
		<td width="60"><span WCMAnt:param="iflowcontent_list.head.dealdoc">查看</span></td>
<%} 
if(nViewType==3){
%>
<td width="60"><span WCMAnt:param="iflowcontent_list.head.cease">重新流转</span></td>
<%}%>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%!private String outputProps(FlowDoc oFlowDoc, IFlowContent obj, MethodContext oMethodContext, int nViewType) throws WCMException{
		int nFlowDocId = 0;
		int nIsEnd = 1;
		int nAccepted = 0;
		int nFlagId = 0;
		int nWorked = 0;
		int nNodeId = 0;
		String sNodeName = "";
		int nWorkModal = 0;
		int nReWorked = 0;
		int nReceived = 0;

		if(oFlowDoc != null){
			nFlowDocId = oFlowDoc.getId();
			nIsEnd = 0;
			nAccepted = oFlowDoc.isAccepted() ? 1 : 0;
			nFlagId = oFlowDoc.getFlag();
			nWorked = oFlowDoc.isWorked() ? 1 : 0;
			nReceived = oFlowDoc.isReceived() ? 1 : 0;
			FlowNode currNode = oFlowDoc.getNode();
			if(currNode!=null){
				nNodeId = currNode.getId();
				sNodeName = currNode.getName();
			}
			nWorkModal = oFlowDoc.getWorkModal();
			nReWorked = (oFlowDoc.getFlag() == ProcessConstants.FLAG_BACK) ? 1 : 0;
		}
		String sComments = "";
		if(nViewType!=3){
			String[] pComments = getFlowDocComments(obj);
			if (pComments != null) {
				for (int k = 0; k < pComments.length; k++) {
					 sComments += pComments[k] + ":&nbsp;";
					 k++; 
					 sComments +=CMyString.filterForHTMLValue(pComments[k]);
				}
			}
		}
		StringBuffer sbResult = new StringBuffer();
		sbResult.append(" docid=\"");
		sbResult.append(obj.getId());
		sbResult.append("\"");
		sbResult.append(" flowdocid=\"");
		sbResult.append(nFlowDocId);
		sbResult.append("\"");
		sbResult.append(" contenttitle=\"");
		sbResult.append(CMyString.filterForHTMLValue(obj.getDesc()));
		sbResult.append("\"");
		sbResult.append(" cruser=\"");
		sbResult.append(obj.getSubinstance().getCrUserName());
		sbResult.append("\"");
		sbResult.append(" crtime=\"");
		sbResult.append(obj.getSubinstance().getCrTime().toString("yy-MM-dd HH:mm:ss"));
		sbResult.append("\"");
		sbResult.append(" isend=\"");
		sbResult.append(nIsEnd);
		sbResult.append("\"");
		sbResult.append(" bCanPublish=\"");
		sbResult.append(obj.getType() == Document.OBJ_TYPE);
		sbResult.append("\"");
		sbResult.append(" contenttype=\"");
		sbResult.append(obj.getContentType());
		sbResult.append("\"");
		sbResult.append(" accepted=\"");
		sbResult.append(nAccepted);
		sbResult.append("\"");
		sbResult.append(" flagid=\"");
		sbResult.append(nFlagId);
		sbResult.append("\"");
		sbResult.append(" editPage=\"");
		sbResult.append(CMyString.transDisplay(obj.getContentAddEditPage()));
		sbResult.append("\"");
		sbResult.append(" deletepage=\"");
		sbResult.append(CMyString.transDisplay(obj.getDeletePage()));
		sbResult.append("\"");
		sbResult.append(" showpage=\"");
		sbResult.append(CMyString.transDisplay(obj.getContentShowPage()));
		sbResult.append("\"");
		sbResult.append(" worked=\"");
		sbResult.append(nWorked);
		sbResult.append("\"");
		sbResult.append(" received=\"");
		sbResult.append(nReceived);
		sbResult.append("\"");
		sbResult.append(" nodeid=\"");
		sbResult.append(nNodeId);
		sbResult.append("\"");
		sbResult.append(" nodename=\"");
		sbResult.append(sNodeName);
		sbResult.append("\"");
		sbResult.append(" workmodal=\"");
		sbResult.append(nWorkModal);
		sbResult.append("\"");
		sbResult.append(" reworked=\"");
		sbResult.append(nReWorked);
		sbResult.append("\"");
		sbResult.append("comments=\"");
		sbResult.append(sComments);
		sbResult.append("\"");
		sbResult.append("Processinfo=\"");
		sbResult.append(makeProcessInfo(getFlowDoc(obj)));
		sbResult.append("\"");
		return sbResult.toString();
	}%>

<%
//5. 遍历生成表现
       for (int i = 0; i < objs.length; i++) {
		try{
			IFlowContent obj = (IFlowContent)objs[i];
			if (obj == null)
				continue;
			//int nFlowDocId = getFlowDoc(obj).getId();
			//System.out.println(nRowId);
			String sRightValue = "";//getRightValue(obj, loginUser).toString();
			String sRowClassName = (i-1)%2==0?"grid_row_even":"grid_row_odd";
			int nFlowDocId = 0;
			int nAccepted = 0;
			int nFlagId = 0;
			int nWorked = 0;
			FlowDoc oFlowDoc = getFlowDoc(obj);
			FlowNode currNode = null;
			int nCanBackRefuse = 1; //是否可以执行拒绝和返工，此变量只适用于前一个节点是开始节点的时候
			if(oFlowDoc != null){
				FlowNode preNode = oFlowDoc.getPreNode();
				if(obj instanceof FlowContentApplyFormImpl){
					if(preNode.isStartNode()) nCanBackRefuse = 0;
				}
				nFlowDocId = oFlowDoc.getId();
				nAccepted = oFlowDoc.isAccepted() ? 1 : 0;
				nFlagId = oFlowDoc.getFlag();
				nWorked = oFlowDoc.isWorked() ? 1 : 0;
				currNode = oFlowDoc.getNode();

			}
			int nRowId = nFlowDocId;
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			boolean bCanRubmit = nFlagId !=2 && nFlagId !=8 && nFlagId != 14  && nWorked != 1;

			boolean bCanEditInFlow = WCMProcessServiceHelper.hasFlowingActionRight(loginUser, nFlowDocId, WCMRightTypes.DOC_EDIT);
		 //权限的处理
            int nRightValue = 0;
            if (currNode != null && !oFlowDoc.isWorked() && loginUser.getId() == oFlowDoc.getToUserId()) {
                // 只有轨迹尚处理处理中，处理人和当前用户是同一个人，才能操作
                nRightValue = currNode.getDocActionsAsInt();
            }
			sRightValue = Integer.toString(nRightValue, 2);
			int nDocId = obj.getSubinstanceId();
			int nSubinstanceType = obj.getContentType();
			String sChnlInfo = "";
			if(nSubinstanceType==605){
				Document oDocument = Document.findById(nDocId,"DOCID,DOCCHANNEL");
				String sChannelName = oDocument.getChannelName();
				sChnlInfo = CMyString.format(LocaleServer.getString("iflowcontent.query.label.chnnelname", "所属栏目:{0}"),new String[]{sChannelName});
			}
			String sStatus = getFlowStatusInfo(nFlagId);
			String sShowStatus = sStatus.equals(" ") ? "" : CMyString.format(LocaleServer.getString("iflowcontent.query.label.status","状态:{0}"), new String[]{getStatusLocale(sStatus)});
			int nTrueFlowDoc = 0;
			if(nViewType == 3) 
				nTrueFlowDoc = getStartFlowDocId(obj, oFlowDoc, loginUser);
			//2011-11-04 by cc 
			//过滤掉在《我的工作列表-已处理列表》中由我发起的文档，即过滤操作列为"提交审批(flag==0)"的文档
			//if(nViewType == 2 && getFlowDoc(obj).getFlag() == 0) continue;

%>
	<tr id="tr_<%=i%>" rowid="<%=i%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>" nCanBackRefuse="<%=nCanBackRefuse%>"  flowdocid="<%=nFlowDocId%>" <%=nViewType == 3 ? "trueFlowDocId=" + nTrueFlowDoc : ""%>  <%=outputProps(oFlowDoc, obj, oMethodContext, nViewType)%> CanEditInFlow="<%=bCanEditInFlow%>">
		<td><input type="checkbox" id="cb_<%=i%>" class="grid_checkbox" name="RowId" value="<%=i%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i+1%></span></td>
		<td><a grid_function="tracing" href="#" onclick="return false;" title="点击查看该文档的流转轨迹" WCMAnt:paramattr="title:iflowcontent_query.jsp.title" WCMAnt:param="iflowcontent_query.jsp.show">查看</a></td>
<td id="<%=nRowId%>" class="doctitle" style="TEXT-ALIGN: left" title="<%=LocaleServer.getString("iflowcontent.query.label.docid", "文档id:")%> <%=nDocId%>&#13;<%=sChnlInfo%>"><span class="icon_flag_<%=nFlagId%>" title="<%=sShowStatus%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><a href="#" onclick="return false;" grid_function="<%=nViewType==1 ? "dealing" : "show"%>" class="<%=(nViewType==1 && nAccepted==0) ? "title_unreaded" : ""%>">
		<%=CMyString.filterForHTMLValue(obj.getDesc())%></a>&nbsp;&nbsp;<%if(nViewType==1 && nAccepted==0){%><span title="点击进行签收" WCMAnt:paramattr="title:iflowcontent_query.jsp.titlesign" grid_function="signFlowDoc" id="sp_signIcon_<%=nFlowDocId%>" class=" icon_accepted_0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><%}%></td>
<%if(nViewType!=3){%>
<td id="FlagDesc_<%=nRowId%>" ><%=getFlowDoc(obj).getFlagDesc()%></td>
<%}
if(nViewType ==3){%>
<td id="StartTime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext,
		obj.getSubinstance().getCrTime())%></td>
<td id="ProcessInfo_<%=nRowId%>" class="rowtd">
		<a href="#" onclick="return false;"  grid_function="showDetailProcessInfo"><span><%=makeProcessInfo(getFlowDoc(obj))%></span></td>
<%}

if(nViewType!=3){%>
<td id="Comment_<%=nRowId%>" class="rowtd">
<a href="#" onclick="return false;"  grid_function="showDetailComments">
<%
			String[] pComments = getFlowDocComments(obj);
			if (pComments != null) {
                for (int k = 0; k < pComments.length; k++) { 
%>
	<span style="padding-right: 10px;">
			<span style="color: gray"><%=pComments[k]%>:&nbsp;</span><%k++;%><%=CMyString.filterForHTMLValue(pComments[k])%>
	</span>
               
<% }
		}%>
</a>
</td>
<td id="PostTime_<%=nRowId%>"><%=convertDateTimeValueToString(oMethodContext, getFlowDoc(obj).getPostTime())%></td>
<%
	}
%>
	<td id="Status_<%=nRowId%>"><%=getStatusLocale(obj.getStatusDesc())%></td>
<%
if(nViewType==2){%>
<td><span class="object_reasign grid_function <%=(bCanRubmit)?"func_reasign_1":"func_reasign_0"%>" grid_function="<%=(bCanRubmit)?"reasign":""%>">&nbsp;</span></td>
<% }
if(nViewType==1){%>
<td><span class="object_render grid_function func_show" grid_function="show">&nbsp;</span></td>
<%} 
if(nViewType==3){
%>
<td><span class="object_cease grid_function func_cease_<%=(obj.getFlowDoc() == null ? "1" : "0")%>" grid_function="cease" title="<%=obj.getFlowDoc() == null ? LocaleServer.getString("flowcontent.label.title3","将已结束流转的文档重新投入流转") : LocaleServer.getString("flowcontent.label.title4","强行结束正在流转中的文档的流转")%>">&nbsp;</span></td>
<%}%>

	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
<% if(objs.length==0){%>
	<tbody id="grid_NoObjectFound">
		<tr><td colspan="8" class="no_object_found" WCMAnt:param="list.NoObjectFound" WCMAnt:param="iflowcontent_query.jsp.none">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
<%}%>
</table>
<script>
	try{
		wcm.Grid.init({
			OrderBy : '<%=sCurrOrderBy%>',
			RecordNum : <%=objs.length%>
		});
		PageContext.drawNavigator({
			Num : <%=objs.length%>,
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
	throw new WCMException(LocaleServer.getString("iflowcontent_query.jsp.runtimeex", "iflowcontent_query.jsp运行期异常!"), tx);
}
%>