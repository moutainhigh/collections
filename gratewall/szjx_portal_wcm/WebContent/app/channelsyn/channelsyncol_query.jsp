<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelSyn" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelSyns" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ChannelSyns)){
		throw new WCMException(CMyString.format(LocaleServer.getString("channelsyncol_query.jsp.servicenoobject","服务(com.trs.ajaxservice.DocumentSynServiceProvider.query)返回的对象集合类型不为ChannelSyns,而为{0},请确认."),new Object[]{result.getClass()}));
	}
	ChannelSyns objs = (ChannelSyns)result;
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="channelsyncol_list.head.edit" width="40">修改</td>
<td grid_sortby="SRCChannel"><span WCMAnt:param="channelsyncol_list.head.ToChannelName">来源栏目</span><%=getOrderFlag("SRCChannel", sCurrOrderBy)%></td>
<td grid_sortby="sDate" width="150"><span WCMAnt:param="channelsyncol_list.head.DocsDate">汇总执行时间</span><%=getOrderFlag("sDate", sCurrOrderBy)%></td>
<td grid_sortby="Docsdate" width="150"><span WCMAnt:param="channelsyncol_list.head.DocsEdate">文档创建开始时间</span><%=getOrderFlag("Docsdate", sCurrOrderBy)%></td>
<td grid_sortby="ATTRIBUTE" width="100"><span WCMAnt:param="channelsyncol_list.head.TransmitType">汇总模式</span><%=getOrderFlag("ATTRIBUTE", sCurrOrderBy)%></td>
<td WCMAnt:param="channelsyncol_list.head.delete" width="40">删除</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ChannelSyn obj = (ChannelSyn)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			Channel srcChannel = obj.getSrcChannel();
			boolean bCanEdit = hasRight(loginUser,srcChannel,13);
			boolean bCanDel = hasRight(loginUser,srcChannel,13);
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			String sRightValue = getRightValueByMember(srcChannel, loginUser).toString();
%>
		<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="<%=(bCanEdit)?"object_edit":"objectcannot_edit"%> grid_function" grid_function="edit">&nbsp;</span></td>
		<td id="SrcChannelName_<%=nRowId%>" title="<%=getChnlPath(obj.getSrcChannel())%>" style="text-align:left">
				<%=CMyString.filterForHTMLValue(obj.getSrcChannel().getDesc())%></td>
		<td id="Date_<%=nRowId%>">
				<%=convertDateTimeValueToString(oMethodContext, obj.getStartTime())%> / <%=convertDateTimeValueToString(oMethodContext, obj.getEndTime())%></td>
		<td id="DocDate_<%=nRowId%>">
				<%=convertDateTimeValueToString(oMethodContext, obj.getDocStartTime())%></td>
		<td id="TransmitType_<%=nRowId%>">
				<%=typeConvert(obj.getTransmitType())%></td>
		<td><span class="<%=(bCanDel)?"object_delete":"objectcannot_delete"%> grid_function" grid_function="delete">&nbsp;</span></td>

	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="7" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
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
	throw new WCMException(LocaleServer.getString("channelsyncol_query.jsp.chnlsyncolqueryruntimeex","channelsyncol_query.jsp运行期异常!"), tx);
}
%>
<%!
	private String typeConvert(int _nType) throws WCMException{
		switch(_nType){
			case 1 :
				return LocaleServer.getString("channelsyncol_query.label.copy", "复制");
			case 2 :
				return LocaleServer.getString("channelsyncol_query.label.quote", "引用");
			case 3 :
				return LocaleServer.getString("channelsyncol_query.label.mirror", "镜像");
			default:
				return LocaleServer.getString("channelsyncol_query.label.unknown", "未知");
		}
	}
	private String getChnlPath(Channel _channel) throws WCMException {
        if (_channel == null) {
            return "";
        }

        StringBuffer buff = new StringBuffer(128);
        buff.append(CMyString.filterForHTMLValue(_channel.getSite().getDesc()));
        buff.append("&nbsp;/&nbsp;");
        Channel parent = _channel.getParent();
        while (parent != null) {
            buff.append(CMyString.filterForHTMLValue(parent.getDesc()));
            parent = parent.getParent();
            buff.append("&nbsp;/&nbsp;");
        }

        buff.append(CMyString.filterForHTMLValue(_channel.getDesc()));

        return buff.toString();
    }
%>