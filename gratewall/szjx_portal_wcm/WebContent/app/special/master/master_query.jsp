<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>

<%@ page import="com.trs.components.common.publish.widget.Master"%>
<%@ page import="com.trs.components.common.publish.widget.Masters"%>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@ page import="com.trs.infra.util.CMyString"%>

<%@include file="../../include/list_base.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/convertor_helper.jsp"%>

<%
	 String sHostRightValue = SpecialAuthServer.getRightValue(loginUser, Master.class).toString();
	response.setHeader("HostRightValue",  sHostRightValue);
	Masters masters = (Masters)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
%>
<%
	Master currMaster = null;
	if(masters.size() != 0){
		for(int i = currPager.getFirstItemIndex()-1; i < currPager.getLastItemIndex(); i++){
			try{
				currMaster = (Master)masters.getAt(i);
				if(currMaster == null)
					continue;

				//权限处理TODO
				String sRightValue = SpecialAuthServer.getRightValue(loginUser,currMaster).toString();
				int nMasterId = currMaster.getId();
				String sMName = currMaster.getMName();
				String sFileName = currMaster.getFileName();
				String sPicFileName = mapFile(currMaster.getPicFileName());
				String sThumbPicFileName = currMaster.getPicFileName().equals("0") ? sPicFileName : "../../../file/read_image.jsp?FileName=" + currMaster.getPicFileName() + "&ScaleWidth=172";
				String sCrUser = currMaster.getCrUserName();
				String sCrTime = convertDateTimeValueToString(oMethodContext, currMaster.getPropertyAsDateTime("CrTime"));
				boolean bCanEdit = SpecialAuthServer.hasRight(loginUser, currMaster, SpecialAuthServer.WIDGET_EDIT);
				boolean bCanExport = SpecialAuthServer.hasRight(loginUser, currMaster, SpecialAuthServer.WIDGET_BROWSE);
				boolean bCanDelete = SpecialAuthServer.hasRight(loginUser, currMaster, SpecialAuthServer.WIDGET_DELETE);
%>
		<div class="thumb" id="thumb_item_<%=nMasterId%>" itemId="<%=nMasterId%>"  rightValue="<%=sRightValue%>" id="thumb_<%=nMasterId%>" title="<%=LocaleServer.getString("widget.label.masterId", "编号")%>:&nbsp;<%=nMasterId%>&#13;<%=LocaleServer.getString("widget.label.mname", "母板名称")%>:&nbsp;<%=CMyString.filterForHTMLValue(sMName)%>&#13;<%=LocaleServer.getString("widget.label.cruser", "创建者")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrUser)%>&#13;<%=LocaleServer.getString("widget.label.crtime", "创建时间")%>:&nbsp;<%=sCrTime%>">
			<div class="desc">
				<input type="checkbox" name="" value="<%=nMasterId%>" id="cbx_<%=nMasterId%>"/> <label for="cbx_<%=nMasterId%>"><%=CMyString.transDisplay(sMName)%></label>
			</div>
			<div><a href="<%=CMyString.filterForHTMLValue(sPicFileName)%>"><img  src="<%=CMyString.filterForHTMLValue(sThumbPicFileName)%>" width="172px" height="112px" border="0" alt = "母板大图"  title="" /></a></div>
			<div class="cruser">
				<%
					out.print(CMyString.format(LocaleServer.getString("master_query.jsp.creator", "创建者：{0}"), new String[]{CMyString.truncateStr(CMyString.transDisplay(sCrUser), 16)}));
				%>
			</div>
			<div class="crtime">
				<%
					out.print(CMyString.format(LocaleServer.getString("master_query.jsp.crtime", "时间：{0}"), new String[]{sCrTime+""}));
				%>
			</div>
			<div class="cmds">
				<li class="edit <%=bCanEdit?"":"disableCls"%>" cmd="edit"  WCMAnt:param="master_query.jsp.alter">修改</li>
				<li class="export <%=bCanExport?"":"disableCls"%>" cmd="export" WCMAnt:param="master_query.jsp.export">导出</li>
				<li class="delete  <%=bCanDelete?"":"disableCls"%>" cmd="delete" WCMAnt:param="master_query.jsp.delete">删除</li>
			</div>
		</div>
<%			}catch(Exception ex){
				ex.printStackTrace();
			}

			
		}
	}
%>
<script>
	try{
		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
	}catch(err){
		//Just skip it.
	}
	jQuery('#list-data a').flyout({ 
		inOpacity:0,
		loader : 'master-loader',
		closeTip : "点击关闭",
		inSpeed:600,
		outSpeed:600
	});
</script>

<%!
	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
%>