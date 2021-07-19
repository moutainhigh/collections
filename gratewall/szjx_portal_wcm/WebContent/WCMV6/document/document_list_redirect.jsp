<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%boolean IS_DEBUG = false;%>
<%
	int nChannelId = currRequestHelper.getInt("channelid", 0);
	int nSiteId = currRequestHelper.getInt("siteid", 0);
	Channel currChannel = null;
	WebSite currWebSite = null;
	try{
		if(nChannelId>0){
			currChannel = Channel.findById(nChannelId);	
			if(currChannel == null){
				throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取栏目ID为["+nChannelId+"]的栏目失败！");
			}
			currWebSite = currChannel.getSite();
		}
		else if(nSiteId>0){
			currWebSite = WebSite.findById(nSiteId);	
			if(currWebSite == null){
				throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取站点ID为["+nSiteId+"]的站点失败！");
			}
		}
		else{
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "未指定ChannelId和SiteId.");
		}
	}catch(Throwable t){
%>	
		<script>
			try{
				top.FaultDialog.show({
					code		: '<%=ExceptionNumber.ERR_OBJ_NOTFOUND%>' || '',
					message		: '<%=t.getMessage()%>' || '',
					detail		: '<%=t.getMessage()%>' || ''
				}, '与服务器交互时出现错误', function(){
					top.$nav().refreshSiteType('0', null, function(){
						top.getFirstHTMLChild(top.$nav().$("r_0")).click();
					});
				});
			}catch(error){
				//TODO logger
				//alert(error.message);
			}
		</script>
<%
		return;
	}
	String sDefaultListPage = "../document/document_normal_index.html";
	switch(currWebSite.getPropertyAsInt("SiteType",0)){
		case 0:
			//文字库
			break;
		case 1:
			//TODO
			//图片库
			sDefaultListPage = "../photo/photo_list.html";
			break;
		case 2:
			//视频库
			sDefaultListPage = "../video/video_list.jsp";
			break;
	}
	String sRedirectPage = sDefaultListPage;
	String s404Message = "";
	if(currChannel != null){
		if(currChannel.isLink()){
%>
	<script>
/*
		var a = document.createElement('A');
		a.style.display = 'none';
		a.target = '_blank';
		a.href = '<%=currChannel.getLinkUrl()%>';
		document.body.appendChild(a);
		a.click();
*/	
		var src = 'document/document_list_4_linkchannel.html?linkUrl='
			+encodeURIComponent('<%=currChannel.getLinkUrl()%>')+
			'&<%=request.getQueryString()%>';
		(top.actualTop||top).$MessageCenter.changeSrc("main",
			src);
	</script>
<%
			return;
		}
		sRedirectPage = currChannel.getContentListPage();
		if("".equals(sRedirectPage)||"../document/document_list_of_channel.jsp".equals(sRedirectPage)){
			sRedirectPage = sDefaultListPage;
		}
		s404Message = "[栏目-"+currChannel.getId()+"]<b>"
			+currChannel.getDispDesc()+"</b>配置的文档列表页面不存在，请确认。";
	}
	if(IS_DEBUG){
		System.out.println(sRedirectPage);
	}
	String sTarget = "";
	if(sRedirectPage.indexOf("?")==-1){
		sTarget = sRedirectPage+"?"+request.getQueryString();
	}
	else{
		sTarget = sRedirectPage+"&"+request.getQueryString();
	}
	s404Message = com.trs.infra.util.CMyString.filterForJs(s404Message);
%>
	<script>
		(top.actualTop||top).$MessageCenter.changeSrc("main",'document/<%=CMyString.filterForJs(sTarget)%>',null,true,'<%=CMyString.filterForJs(s404Message)%>','document/document_list_404.html?<%=CMyString.filterForJs(request.getQueryString())%>','<%=CMyString.filterForJs(sRedirectPage)%>');
	</script>