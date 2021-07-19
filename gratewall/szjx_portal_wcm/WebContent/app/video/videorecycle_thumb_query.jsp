<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.video.content.VideoDoc"%>
<%@ page import="com.trs.components.video.content.VideoDocs"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.components.video.VSConfig"%>
<%@ page import="com.trs.components.video.VideoDocUtil"%>
<%@ page import="com.trs.components.video.persistent.XVideo"%>
<%@ page import="com.trs.components.video.domain.XVideoMgr"%>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	MethodContext oMethodContext1 = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof VideoDocs)){
		throw new WCMException(CMyString.format(
				LocaleServer.getString("videorecycle_thumb_query.jsp.confirm","服务(com.trs.ajaxservice.PhotoServiceProvider.query)返回的对象集合类型不为VideoDocs，而为[{0}]，请确认。"),new Object[] {result.getClass()}));
			
	}
	int nCurrDocId = 0;
	if (oMethodContext1 != null) {
		nCurrDocId = oMethodContext1.getValue("CurrDocId", 0);
	}
	VideoDocs videoDocs = (VideoDocs)result;
	//FilesMan currFilesMan = FilesMan.getFilesMan();
	int nChannelId = currRequestHelper.getInt("Channelid",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			VideoDoc videoDoc = (VideoDoc)videoDocs.getAt(i-1);
			if (videoDoc == null)
				continue;
			
			//------------------------------------------------------
			boolean bCanDetail = hasRight(loginUser, videoDoc, 34);
			int nRowId = videoDoc.getChnlDocProperty("RECID", 0);
			int nDocId = videoDoc.getDocId();
			int nChnlId = videoDoc.getChannelId();
			int nDocChannelId = videoDoc.getDocChannelId();
			String sRightValue = videoDoc.getRightValue(loginUser).toString();
			boolean bTopped = videoDoc.isTopped();
			int nDocType = videoDoc.getPropertyAsInt("DOCTYPE", 0);
			int nModal = videoDoc.getChnlDocProperty("MODAL", 0);
			int nStatusId = videoDoc.getStatusId();
			String nStatusName = LocaleServer.getString("videorecycle_thumb_query.jsp.unkown","未知");
			if (videoDoc.getStatus() != null) {
				nStatusName = videoDoc.getStatus().getDisp();
			}
			String sTitle = CMyString.transDisplay(videoDoc.getPropertyAsString("DOCTITLE"));
			String sCrUser = videoDoc.getPropertyAsString("CrUser");
			String sCrTime = convertDateTimeValueToString(oMethodContext, videoDoc.getPropertyAsDateTime("CrTime"));
			String sFileNameLike = videoDoc.getPropertyAsString("DOCRELWORDS");
			boolean bIsSelected = nCurrDocId==nDocId || strSelectedIds.indexOf(","+nRowId+",")!=-1;

			List xvideos = videoDoc.getXvideos();
			if(xvideos == null ){
				continue;
			}
			StringBuffer tokens = new StringBuffer();
			//	fjh@2009.2.2  默认按bitrate从小到大排列；缩略图显示为高清缩略图
			XVideo xvideo = new XVideo();
			for (int k = 0; k < xvideos.size(); k++) {
				xvideo = (XVideo) xvideos.get(k);
				if (xvideo == null || xvideo.getFileName() == null) {
					continue;
				}
				tokens.append(xvideo.getFileName()).append(';');
			}
			String srcFileName = xvideo.getSrcFileName();
			/*
				兼容数据迁移到双码流srcFileName为空
				此时srcFileName=token（原来都是11位）
			*/
			if(srcFileName == null || "".equals(srcFileName.trim())) {
				srcFileName = xvideo.getFileName().substring(0,11);
			}
			String thumbRoot = "";
			thumbRoot = VSConfig.getThumbsHomeUrl();
			//if(xvideo.getCreateType()==50)
			//{
				//thumbRoot=VSConfig.getThumbRootUrl();
		//	}
			String thumb = thumbRoot +"/"+ xvideo.getThumb();
			int convertFlag = xvideo.getConvertStatus();
			boolean converting = (convertFlag == 0);
			boolean convertFail = (convertFlag == -1);
			
			String thumbUrl = XVideoMgr.getThumbUrl(xvideo);
			String _srcFileName = CMyString.filterForHTMLValue(CMyString.showNull(srcFileName));
%>
    <div class="thumb_item<%=(bIsSelected)?" thumb_item_active":""%>" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" right="<%=sRightValue%>" docId="<%=nDocId%>" channelid="<%=nDocChannelId%>" currchnlid="<%=nChnlId%>" >
   		<div class="thumb" id="thumb_<%=nRowId%>">
			<img id="thumb_<%=_srcFileName%>" style="cursor:hand;position:absolute;" src="<%=adapatedImgSrc(convertFlag, thumbUrl)%>" title="视频<%=nDocId%>&#13;<%=nStatusName%>" WCMAnt:paramattr="title:videorecycle_thumb_query.jsp.video" onload="resizeIfNeed(this);" class="<%=(converting)?"imgConverting ":""%>wcm_pointer" docid="<%=nDocId%>" title="<%=sTitle%>" videoToken="<%=_srcFileName%>"  onclick="<%=getImgClickScript(bCanDetail, converting, convertFail, tokens.toString())%>" >		
		</div> 
        <div class="attrs" id="thumb_attrs_<%=nRowId%>">
            <input id="cbx_<%=nRowId%>" type="checkbox"<%=(bIsSelected)?" checked":""%>/>
			<%
				if(nModal == 2){
			%>
			<span class="document_modal_<%=nModal%>" style="width:15px;">&nbsp;</span>
			<%	
				}
			%>
            <span id="desc_<%=nRowId%>" title="<%=sTitle%>" style="color:<%=nStatusId == 10 ? "green" :""%>;text-align:left;"><%=filter(sTitle)%></span>
        </div>
    </div>

<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
<input id="thumbUrl" type="hidden" value="<%=VSConfig.getThumbsHomeUrl()%>"/>
<script>
	try{
		myThumbList.init({
			SelectedIds : '<%=sOrigSelectedIds%>',
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
	throw new WCMException(LocaleServer.getString("videorecycle_thumb_query.jsp.runError","watermark_query.jsp运行期异常!"), tx);
}
%>
<div id="thumb_NoObjectFound" style="display:none;">
	<div class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</div>
</div>
<%!
	private String filter(String sTitle) throws WCMException{
		return sTitle.length() > 10 ? sTitle.substring(0,8) + ".." : sTitle;
	}

	private String adapatedImgSrc(String thumb, boolean sConverting) {
		//	alert("thumb=[" + thumb + "]\nsConverting=[" + sConverting + "]\n" + (sConverting ? "converting" : "finish"));
		if (sConverting) {
			return "./image/inConverting.jpg";
		} else {
			return thumb + "?"
			+ Math.random();
		}
	}

	private String adapatedImgSrc(int converFlag, String thumb) {
		if (converFlag == 0) {
			return "./image/inConverting.jpg";
		} else if (converFlag == -1) {
			return "./image/convertFail.gif";
		} else if (converFlag == -2 || converFlag == -3) {
			//fjh@20090317: 切割失败的提示
			return "./image/convertFail.gif";
		} else {
			return thumb + "?"
			+ Math.random();
		}
	}

	private String playlink(String tokens) {
		String sFeature = "'location=no,resizable=no,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width=640,height=450'";
		return "javascript:window.open('./player.jsp?v=" + tokens
				+ "', '_blank'," + sFeature + ");";
	}

	private String getImgClickScript(boolean bCanDetail, boolean converting, boolean convertFail, String tokens) {
		return (bCanDetail && !converting && !convertFail) ? playlink(tokens):"return false;";
	}

	private String statusColor(String status, String title) {
		if (status == LocaleServer.getString("videorecycle_thumb_query.jsp.yifa","已发")) {
			return "<font color='green'>" + title + "</font>";
		} else {
			return title;
		}
	}
%>