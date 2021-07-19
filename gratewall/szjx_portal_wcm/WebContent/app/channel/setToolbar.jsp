<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@include file="../include/public_server.jsp"%>
<%
	//获取参数
	int nSynType = currRequestHelper.getInt("SynType", 0);
	String sRelateIds = currRequestHelper.getString("RelIds");	
	int nChannelId = currRequestHelper.getInt("ObjectId",0);	
	String sToolbar = currRequestHelper.getString("Toolbar");	
	String sAdvToolbar = currRequestHelper.getString("AdvToolbar");
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.channel", "没有找到ID为{0}的栏目"), new int[]{nChannelId}));
	}
	//业务处理
	if(nSynType == 0){
		save(currChannel,sToolbar,sAdvToolbar,loginUser);
	}else if(nSynType == 1){
		//同步到文字库下所有符合条件的栏目
		WCMFilter filter = new WCMFilter("","chnlType = ?","");
		filter.addSearchValues(0,Channel.TYPE_NORM);
		Channels channels = Channels.openWCMObjs(loginUser,filter);
		Channel temp = null;
		WebSite website = null;
		for(int i=0, nSize = channels.size(); i < nSize ; i++){
			temp = (Channel) channels.getAt(i);
			if(temp == null) continue;
			website = temp.getSite();
			if(website == null || website.getType() != 0) continue;
			save(temp,sToolbar,sAdvToolbar,loginUser);
		}
	}else if(nSynType == 2){
		//特定栏目
		if(sRelateIds == null) {
			save(currChannel,sToolbar,sAdvToolbar,loginUser);
		}else{
			save(currChannel,sToolbar,sAdvToolbar,loginUser);
			Channel temp = null;
			for(int k=0, nLength = sRelateIds.split(",").length; k < nLength ; k++){
				temp = Channel.findById(Integer.parseInt(sRelateIds.split(",")[k]));
				if(temp == null) continue;
				save(temp,sToolbar,sAdvToolbar,loginUser);
			}
		}
	}
	//结束
	out.clear();
%>
<%!
	private void save(Channel currChannel,String sToolbar,String sAdvToolbar,User loginUser) throws WCMException{
		currChannel.setPropertyWithString("Toolbar",sToolbar);
		currChannel.setPropertyWithString("ADVToolbar",sAdvToolbar);
		currChannel.save(loginUser);
	}
%>