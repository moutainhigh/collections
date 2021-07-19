<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.message.Message" %>
<%@ page import="com.trs.components.common.message.Messages" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.components.common.message.MessageServer" %>
<%@ page import="java.lang.StringBuffer" %>

<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	MethodContext mc = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	int nReadFlag = mc.getValue("readflag",0);
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Messages)){
		throw new WCMException(CMyString.format(LocaleServer.getString("message_query.type","服务(com.trs.ajaxservice.MessageServiceProvider.query)返回的对象集合类型不为Messages，而为({0})，请确认。"),new Object[]{result.getClass()}));
	}
	Messages objs = (Messages)result;
%>
<%!
private String getReceiver(Message message)throws WCMException{
	/* 改为高性能查询 */
	return com.trs.infra.persistent.db.DBManager.getDBManager().sqlExecuteStringQuery("select ReceiverName from WCMMsgReceiver where MsgId=?", new int[]{message.getId()});
	/*
	try {
		Users receivers = message.getReceivers();
        if (receivers == null || receivers.isEmpty()) {
                return  "";
            }
        int nSize = receivers.size();
        StringBuffer sbResult = null;
        for (int i = 0; i < nSize; i++) {
            User user = (User) receivers.getAt(i);
            if (user == null)
                 continue;
            if (sbResult == null) {
                 sbResult = new StringBuffer(nSize * 20);
             } else {
                 sbResult.append(",");
             }
             sbResult.append(user.getName());
         }
            return  sbResult.toString();
	 } catch (Exception e) {
			e.printStackTrace();
        }
		return null;
		*/
}
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray" style="width:100%;">
	<tr id="grid_head" class="grid_head" readFlag="<%=nReadFlag%>">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
<td grid_sortby="mbody"><span WCMAnt:param="message_list.head.mbody">短信息</span><%=getOrderFlag("mbody", sCurrOrderBy)%></td>
<% if(nReadFlag!=1){%>
<td grid_sortby="cruser" width="60"><span WCMAnt:param="message_list.head.cruser">发送人</span><%=getOrderFlag("cruser", sCurrOrderBy)%></td>
<td grid_sortby="crtime" width="90"><span WCMAnt:param="message_list.head.receivetime">到达时间</span><%=getOrderFlag("crtime", sCurrOrderBy)%></td>
<td width="60"><span WCMAnt:param="message_list.head.reply">回复处理</span></td>
<% }if(nReadFlag==1){%>
<td grid_sortby="msgreceiver" width="60"><span WCMAnt:param="message_list.head.msgreceiver">收件人</span><%=getOrderFlag("msgreceiver", sCurrOrderBy)%></td>
<td grid_sortby="crtime" width="90"><span WCMAnt:param="message_list.head.crtime">发送时间</span><%=getOrderFlag("crtime", sCurrOrderBy)%></td>
<%}%>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Message obj = (Message)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValue(obj, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			boolean bReaded = MessageServer.isMessageReaded(obj, loginUser);
			String contentClass = getContentClass(nReadFlag,bReaded);
			String mbody = obj.getPropertyAsString("mbody");
			//System.out.println(obj.getPropertyAsInt("MsgType",0));
			String dealMsgClass = obj.getPropertyAsInt("MsgType",0) == 3 ? "func_post" : "func_reply";
			String sDealTitle = obj.getPropertyAsInt("MsgType",0) == 3 ? LocaleServer.getString("message.query.dealdoc","处理文档") :  LocaleServer.getString("message.query.replymsg","回复消息");
			String sCrTime = obj.getPropertyAsDateTime("crtime").toString("yy-MM-dd HH:mm:ss");
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>" cruser="<%=CMyString.transDisplay(obj.getPropertyAsString("cruser"))%>" title="<%=CMyString.transDisplay(obj.getPropertyAsString("title"))%>" mbody="<%=CMyString.filterForHTMLValue(obj.getBody())%>" readed="<%=bReaded%>" receiver="<%=getReceiver(obj)%>" crtime="<%=sCrTime%>" editClassName=<%=dealMsgClass%>>
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td id="mbody_<%=nRowId%>" class="rowtd" style="PADDING-LEFT: 10px; TEXT-ALIGN: left">
<a href="#" onclick="return false;"  grid_function="showDetail"><span title="<%=CMyString.transDisplay(obj.getPropertyAsString("title"))%>" style="font-size: 14px;" class="<%=contentClass%>"><%=CMyString.transDisplay(obj.getPropertyAsString("title"))%></span><span style="color: gray;"> - <%=stripTag(obj.getBody())%></span></a>
 </td>
<% if(nReadFlag!=1){%>
<td id="cruser_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("cruser"))%></td>
<td id="CrTime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext, obj.getCrTime())%></td>
<td><span class="object_reply grid_function <%=dealMsgClass%>" grid_function="reply" title="<%=sDealTitle%>" WCMAnt:paramattr="title:message_list.html.reply.title">&nbsp;</span></td>
<% }if(nReadFlag==1){%>
<td id="msgreceiver_<%=nRowId%>">
		<%=getReceiver(obj)%></td>
<td id="CrTime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext, obj.getCrTime())%></td>
<%}//END IF%>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="<%=(nReadFlag==1?4:5)%>" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	try{
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
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("message_query.runExce","message_query.jsp运行期异常!"), tx);
}
%>

<%!
	private String stripTag(String sSource){
		if(CMyString.isEmpty(sSource)) return sSource;
		//return sSource;
		//return sSource.replaceAll("<[^>]+>", "");
		if(sSource.indexOf("<a>") < 0){
			return sSource.replaceAll("<[^>]+>", "");
		}
		else{
			sSource.replaceAll("<P>","");
			sSource.replaceAll("</P>","");
			sSource.replaceAll("<br>","");
			return sSource;
		}
	}
	private String getContentClass(int nReadFlag, boolean bReaded){
		if(nReadFlag!=0) return "";
		else return bReaded ? "title_normal" : "title_unreaded";
	}
	 
%>