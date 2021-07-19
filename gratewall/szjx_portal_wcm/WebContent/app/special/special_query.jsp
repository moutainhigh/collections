<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="error_for_dialog.jsp"%>
<%@ page import="com.trs.components.special.Special" %>
<%@ page import="com.trs.components.special.Specials" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	MethodContext oMethodContext1 = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Specials)){
		throw new WCMException(CMyString.format(LocaleServer.getString("special_query.jsp.service","com.trs.ajaxservice.SpecialServiceProvider.query)返回的对象集合类型不为Specials，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	Specials objs = (Specials)result;
	
	//设置专题新建权限样式
	String sHostRightValue = SpecialAuthServer.getRightValue(loginUser, Special.class).toString();
	response.setHeader("HostRightValue",  sHostRightValue);
	//构造分页参数
	int nPageSize = -1, nPageIndex = 1;
	if (oMethodContext1 != null) {
		nPageSize = oMethodContext1.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 6);
		nPageIndex = oMethodContext1.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
	}	
	CPager newCpage = new CPager(nPageSize);
	newCpage.setCurrentPageIndex(nPageIndex);
	newCpage.setItemCount(objs.size());
	out.clear();
%>

<%
//5. 遍历生成表现
	for (int i = newCpage.getFirstItemIndex(); i <= newCpage.getLastItemIndex(); i++) {
		try{
			Special obj = (Special)objs.getAt(i-1);
			if (obj == null)
				continue;
			String sSepcialName = obj.getSpecialName();
			String sSepcialDesc = obj.getSpecialDesc();
			String sViewThumb = obj.getViewThumb();
			String sViewThumn_url = "images/zt_wt.gif";
			if(!(sViewThumb == null || sViewThumb.equals("null") || sViewThumb.trim().equals(""))){
				sViewThumn_url = mapFile(sViewThumb);
			}
			String sCrUser = obj.getCrUserName();
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
			//权限的处理
			String sRightValue = SpecialAuthServer.getRightValue(loginUser,obj).toString();
			boolean bCanDelete = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.SPECIAL_DELETE);
			boolean bCanEdit = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.SPECIAL_EDIT);
			boolean bCanBrowse = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.SPECIAL_BROWSE);
			boolean bCanPub = SpecialAuthServer.hasRight(loginUser, obj, SpecialAuthServer.SPECIAL_PUBLISH);
			IPublishFolder folder =(IPublishFolder) PublishElementFactory.lookupElement(
                    obj.getHostType(), obj.getHostId());
			boolean bHasTemplateRight = validateRight(loginUser, folder, WCMRightTypes.TEMPLATE_OUTLINE);
%>
<div class="thumb" itemid="<%=obj.getId()%>" id="thumb_<%=obj.getId()%>" hostId="<%=obj.getHostId()%>" canPub="<%=bCanPub%>" canEdit="<%=bCanEdit%>" objectId="<%=obj.getId()%>" rightValue="<%=sRightValue%>" hasTemplateRight="<%=bHasTemplateRight%>">
	<div class="r">
		<div class="c">
			<img src="<%=sViewThumn_url%>" border=0 alt="" class="pic" width="130px" height="70px"/>
			<div class="sname" onclick="<%=bCanEdit?"edit("+obj.getId()+")":""%>" title="<%=LocaleServer.getString("special.label.ObjectId", "编号")%>:&nbsp;<%=obj.getId()%>&#13;<%=LocaleServer.getString("special.label.specialName", "专题名称")%>:&nbsp;<%=CMyString.filterForHTMLValue(sSepcialName)%>&#13;<%=LocaleServer.getString("special.label.cruser", "创建者")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrUser)%>&#13;<%=LocaleServer.getString("special.label.crtime", "创建时间")%>:&nbsp;<%=sCrTime%>"><%=CMyString.transDisplay(CMyString.truncateStr(sSepcialName,36))%></div>
			<div class="sdesc" title="<%=CMyString.filterForHTMLValue(sSepcialDesc)%>">
				<%=CMyString.transDisplay(CMyString.truncateStr(sSepcialDesc,78))%>
			</div>
			<div class="cmds">
				<li class="preview <%=bCanBrowse ? "" : "disableCls"%>" cmd="preview"  WCMAnt:param="special_query.jsp.preview">预览</li>
				<li class="design <%=bCanEdit ? "" : "disableCls"%>" cmd="design" WCMAnt:param="special_query.jsp.design">设计</li>
				<li class="delete <%=bCanDelete ? "" : "disableCls"%>" cmd="delete" WCMAnt:param="special_query.jsp.delete">删除</li>
				<li class="more <%=bCanEdit ? "" : "disableCls"%>" cmd="moreCmds" WCMAnt:param="special_query.jsp.more_oper">更多操作</li>
			</div>			
		</div>
	</div>
</div>

<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
<script>
	try{
		PageContext.drawNavigator({
			Num : <%=newCpage.getItemCount()%>,
			PageSize : <%=newCpage.getPageSize()%>,
			PageCount : <%=newCpage.getPageCount()%>,
			CurrPageIndex : <%=newCpage.getCurrentPageIndex()%>
		});
	}catch(err){
		//Just skip it.
	}
</script>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("special_query.jsp.label.runtimeexception", "special_query.jsp运行期异常!"), tx);
}
%>
<%!
	private String mapFile(String _sFileName){
		return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
	}
	public boolean validateRight(User _loginUser, IPublishFolder _folder,
			int _nRightIndex) throws WCMException {
		if (AuthServer.hasRight(_loginUser, _folder.getSubstance(),
				_nRightIndex)) {
			return true;
		}

		IPublishFolder parent = _folder.getParent();
		if (parent == null || parent == _folder) {
			return false;
		}

		return validateRight(_loginUser, parent, _nRightIndex);
	}
%>