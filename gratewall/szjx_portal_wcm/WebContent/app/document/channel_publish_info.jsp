<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.ajaxservice.JSONHelper" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@include file="../include/public_server.jsp"%>
<%!
	private String getUrl(CMSObj obj)throws WCMException{
		String sPageHttpURL = null;
		IPublishFolder folder = (IPublishFolder)PublishElementFactory.makeElementFrom(obj);
		if (folder.isLink()) {
			sPageHttpURL = folder.getLinkUrl();
		} else {
			PublishPathCompass compass = new PublishPathCompass();
			sPageHttpURL = compass.getAbsoluteHttpPath(folder);
		}
		return sPageHttpURL;
	}

	private void pushItem(Channel channel, ArrayList items, boolean isAbsoluteUrl) throws WCMException{
		try{
			String sDesc = channel.getDesc();
			String sPageHttpURL = getUrl(channel);

			//获取相对站点的地址
			if(!isAbsoluteUrl){
				WebSite site = channel.getSite();
				String sSiteHttpURL = getUrl(site);
				sPageHttpURL = sPageHttpURL.substring(sSiteHttpURL.length());
				if(!sPageHttpURL.startsWith("/")){
					sPageHttpURL = "/" + sPageHttpURL;
				}
			}

			HashMap oHashMap = new HashMap();
			oHashMap.put("ID",""+channel.getId());
			oHashMap.put("DESC",sDesc);
			oHashMap.put("PUBURL",sPageHttpURL);
			items.add(oHashMap);
		}catch(Throwable e){
			e.printStackTrace();			
		}
	}
%>
<%
	response.setHeader("ReturnJson", "true");
	String sChannelIds = currRequestHelper.getString("ChannelIds");
	boolean bIsAbsoluteUrl = currRequestHelper.getBoolean("isAbsoluteUrl", false);
	IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	ArrayList arrayList = new ArrayList();
	Channel channel = null;  

	if(sChannelIds!=null){
		String[] arrChannelIds = sChannelIds.split(",");
		for(int i=0;i<arrChannelIds.length;i++){
			try{
				int nChannelId = Integer.parseInt(arrChannelIds[i]);
				if(nChannelId > 0){
					channel  = Channel.findById(nChannelId);
					if(channel != null){
						pushItem(channel,arrayList, bIsAbsoluteUrl);
					}
				}
			}catch(Exception ex){
			}
		}
	}

	out.clear();
	HashMap[] hashMaps = new HashMap[arrayList.size()];
	arrayList.toArray(hashMaps);
	HashMap oHashMap = new HashMap();
	oHashMap.put("CHANNELS",hashMaps);
	out.println(JSONHelper.toSimpleJSON2(oHashMap,0));
%>