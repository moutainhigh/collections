<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.cms.process.definition.Flows" %>
<%@ page import="com.trs.ajaxservice.WebSiteServiceProvider" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.cms.content.WCMSystemObject" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Flows objs = (Flows)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
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

	private RightValue getRightValue(Flow flow, MethodContext context,
            User loginUser) throws WCMException {
        RightValue rightValue = null;
        if (loginUser.isAdministrator()
                || loginUser.getName().equals(flow.getCrUserName())) {
            rightValue = new RightValue(RightValue.VALUE_ADMINISTRATOR);
        } else {
            rightValue = (RightValue) context.getContextCacheData("OwnerValue");
            if (rightValue == null) {
                // 从缓存获取
                CMSObj owner = (CMSObj) context.getContextCacheData("Owner");

                // 如果不存在直接从Flow上获取
                if (owner == null) {
                    owner = (CMSObj) flow.getOwner();
                }

                // 构造权限值
                if (owner != null && !(owner instanceof WCMSystemObject)) {
                    if (owner instanceof Channel) {
                        owner = ((Channel) owner).getSite();
                    }
                    rightValue = getRightValueByMember(owner, loginUser);
                } else {
                    rightValue = new RightValue(0);
                }
                context.putContextCacheData("OwnerValue", rightValue);
                
            }
           
        }
        return rightValue;
    }
%>

<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="flow_list.selectall" class="selAll">全选</td>
		<td grid_sortby="FlowName"><span WCMAnt:param="flow_list.head.flowname">工作流名称</span><%=getOrderFlag("FlowName", sCurrOrderBy)%></td>
		<td grid_sortby="FlowDesc"><span WCMAnt:param="flow_list.head.flowdesc">工作流描述</span><%=getOrderFlag("FlowDesc", sCurrOrderBy)%></td>
		<td grid_sortby="OwnerId" width="120"><span WCMAnt:param="flow_list.head.ownerid">所属位置</span><%=getOrderFlag("OwnerId", sCurrOrderBy)%></td>
		<td grid_sortby="CrTime" width="90"><span WCMAnt:param="flow_list.head.crtime">创建时间</span><%=getOrderFlag("CrTime", sCurrOrderBy)%></td>
		<td grid_sortby="CrUser" width="90"><span WCMAnt:param="flow_list.head.cruser">创建者</span><%=getOrderFlag("CrUser", sCurrOrderBy)%></td>
		<td WCMAnt:param="flow_list.head.listChnlsUsingMe" width="60">配置情况</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Flow obj = (Flow)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			RightValue rightValue = getRightValue(obj, oMethodContext, loginUser);
			String sRightValue = rightValue.toString();
			//String sRightValue = getRightValue(obj, loginUser).toString();
			boolean bCanPreview = rightValue.isHasRight(42);//hasRight(loginUser,obj,42);
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			int nOwnerId = obj.getOwnerId();
			int nOwnerType = obj.getOwnerType();
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> grid_selectable_row <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>" ownerId="<%=nOwnerId%>" ownerType="<%=nOwnerType%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td id="flowname_<%=nRowId%>" class="rowtd"> <a href="#" onclick="return false;" grid_function="edit" title="<%=CMyString.format(LocaleServer.getString("flow.query.jsp.flowtitle","工作流ID:{0}"), new int[]{nRowId}) %>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("flowname"))%></a></td>
		<td id="flowdesc_<%=nRowId%>" class="rowtd" title="<%=CMyString.transDisplay(obj.getPropertyAsString("flowdesc"))%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("flowdesc"))%></td>
		<td id="ownerid_<%=nRowId%>">
		<%=getSpecialProperty(obj)%></td>
		<td id="crtime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("crtime"))%></td>
		<td id="cruser_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("cruser"))%></td>
		<td><span class="<%=(bCanPreview)?"object_setflow":"object_cannot_setflow"%> grid_function" style="width:30px" grid_function="listChnlsUsingMe">&nbsp;</span></td>

	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="7" class="no_object_found" WCMAnt:param="flow.query.jsp.none">不好意思, 没有找到符合条件的对象!</td></tr>
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