<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.cms.process.definition.Flows" %>
<%@ page import="com.trs.ajaxservice.WebSiteServiceProvider" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Flows objs = (Flows)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	int nCurrFlowId = oMethodContext.getValue("CurrFlowId", 0);
%>

<%!
	private String getSpecialProperty(Flow _currObj){      
		int nOwnerId = _currObj.getOwnerId();
	 try {
			if (_currObj.getOwnerType() == 1) {
				return  WebSiteServiceProvider.findSiteTypeDesc(nOwnerId);
			}
			// else
			BaseObj baseObj = _currObj.getOwner();
			if (baseObj == null) {
				return   LocaleServer.getString("flow.query.notknown", "未知");
			}
			return  baseObj.toString();
	 } catch (Throwable e) {
               return   LocaleServer.getString("flow.query.notknown", "未知");
                }
}
%>
<div style="background:#ffffff; padding-top: 2px;">
	<table id="tblContent" border=0 align="center" cellspacing=0 cellpadding=3 style="font-size:12px; width: 100%;">
	<tbody>
<%
//5. 遍历生成表现
	int num = 0;
	boolean bHasChecked = false;
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Flow obj = (Flow)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nFlowId = obj.getId();
			String sName = CMyString.transDisplay(obj.getPropertyAsString("flowname"),false);
			String crTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("crtime"));
			String crUser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
			int nType = obj.getPropertyAsInt("ownertype",0);
			boolean bChecked = nCurrFlowId==nFlowId;
			bHasChecked = bHasChecked || bChecked;
			//boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			//String sRightValue = getRightValue(obj, loginUser).toString();
			//boolean bCanPreview = hasRight(loginUser,obj,0);
			if(num%2==0){
				out.println("<tr>");
			}
			num++;
%>

			<td width="50%" style="line-height:14px;" height="20" title="<%=LocaleServer.getString("flow.select.query.flowname","工作流名称")%>: <%=sName%>&#13;<%=LocaleServer.getString("flow.select.query.owner","所属位置")%>: <%=getSpecialProperty(obj)%>&#13;<%=LocaleServer.getString("flow.select.query.cruser","创建者")%>: <%=crUser%>&#13;<%=LocaleServer.getString("flow.select.query.crtime","创建时间")%>: <%=crTime%>">
				<span style="float:left;width: 13px;padding-top:3px;"><%=i%>.</span><input style="float:left;padding-left:13px;" type="radio" class="chk_<%=nFlowId%>" name="FlowId" value=<%=nFlowId%> id="chk_<%=nFlowId%>" _name=<%=sName%> <%=bChecked?" checked":""%>>
			<span style="float:left;padding-left:14px;padding-top:0px;" class="flag_own_<%=nType%>">&nbsp;</span>
			<span class="sp_name" _id="<%=nFlowId%>" style="padding-top:2px;padding-left:10px;cursor: pointer; width: 100px;float:left; white-space:nowrap; text-overflow:ellipsis; overflow:hidden" for="chk_<%=nFlowId%>"><%=CMyString.transDisplay(sName)%></span>
			</td>
<%
			if(num%2==0){
				out.println("</tr>");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	if(num%2!=0){
		out.println("<td>&nbsp;</td></tr>");
	}
%>
	</tbody>
	</table>
<%
	if(objs.size()==0){
%>
<div id="divNoneFound">
	<div class="no_object_found" WCMAnt:param="flow_select_query.jsp.none">不好意思, 没有找到符合条件的工作流...-____-|||</div>
</div>
<%}%>
<script>
	try{
		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
		var bHasChecked = <%=bHasChecked%>;
		if(bHasChecked){
			$('chkNone').checked = false;
		}
		Ext.get('tblContent').on('click', function(){
			var arrEles = document.getElementsByName('FlowId');
			for(var i=0,n=arrEles.length;i<n;i++){
				if(arrEles[i].checked){
					$('chkNone').checked = false;
					break;
				}
			}
		});
	}catch(err){
		//Just skip it.
	}
</script>