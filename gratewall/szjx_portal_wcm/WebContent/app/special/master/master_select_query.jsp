<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.Masters" %>
<%@ page import="com.trs.components.common.publish.widget.Master" %>
<%@include file="../../include/list_base.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/convertor_helper.jsp"%>
<%
	MethodContext oMethodContext1 = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Masters)){
		throw new WCMException("服务(com.trs.ajaxservice.TemplateServiceProvider.query)返回的对象集合类型不为Masters，而为"+(result.getClass())+"，请确认。");
	}
	Masters objs = (Masters)result;
	
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Master obj = (Master)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nMasterId = obj.getId();
			//boolean bIsSelected = strSelectedIds.indexOf(","+nMasterId+",")!=-1;	
			String sMName = obj.getMName();
			String sFileName = obj.getFileName();
			String sPicFileName = mapFile(obj.getPicFileName());
			String sThumbPicFileName = obj.getPicFileName().equals("0") ? sPicFileName : "../../../file/read_image.jsp?FileName=" + obj.getPicFileName() + " &ScaleWidth=172";
			//String sCrUser = obj.getCrUserName();
			//String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
%>
<div class="thumb" id="thumb_item_<%=nMasterId%>">
	<div style="margin-left:8px;">
		<a class="piclook"  href="<%=CMyString.filterForHTMLValue(sPicFileName)%>">
			<img border=0 alt="" title="看大图" src="<%=CMyString.filterForHTMLValue(sThumbPicFileName)%>" width="100px" height="100px" style="display:block;" />
		</a>
		<input type="radio" name="MasterId" value="<%=nMasterId%>" id="chk_<%=nMasterId%>" _name="<%=CMyString.transDisplay(sMName)%>" style="vertical-align:middle;line-height:20px;" />
		<label class="sp_name" for="chk_<%=nMasterId%>" style="cursor:pointer;display:inline-block;vertical-align:middle;_padding-top:5px;" _id="<%=nMasterId%>" title="<%=CMyString.transDisplay(sMName)%>">
			<%=CMyString.transDisplay(CMyString.truncateStr(sMName,12))%>
		</label>
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
	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
%>
<script type="text/javascript">
imgFly();
</script>
