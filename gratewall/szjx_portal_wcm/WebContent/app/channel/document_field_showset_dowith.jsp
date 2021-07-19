<%@ page contentType="text/xml;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.Reports" %>
<%@include file="../../include/public_server.jsp"%>
<%
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	String sFields = currRequestHelper.getString("SHOWFIELDS");
	String sFieldsWidth = currRequestHelper.getString("FIELDSWIDTH");
	String sExtFields = currRequestHelper.getString("SHOWEXTFIELDS");
	String sExtFieldsWidth = currRequestHelper.getString("EXTFIELDSWIDTH");
	String sExtFieldsIds = currRequestHelper.getString("EXTFIELDSIDS");
	String sSelectedChnlIds = currRequestHelper.getString("SelectedChnlIds");
	Channel currChannel = Channel.findById(nChannelId);
	Reports reports = new Reports("");
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.channel", "没有找到ID为{0}的栏目"), new int[]{nChannelId}));
	}
	if(currChannel != null){
		if(!currChannel.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED, CMyString.format(LocaleServer.getString("document_field_showset_dowith.jsp.channellocked", "栏目被用户[{0}]锁定！"), new String[]{currChannel.getLockerUserName()}));
		}
	}
	
	Channels oSelectedChnls = Channels.findByIds(loginUser,sSelectedChnlIds);;
	Channel oChannel = new Channel();
	//设置的字段不包含扩展字段
	if(CMyString.isEmpty(sExtFieldsIds)){
		//当前栏目的文档字段设置
		currChannel.setPropertyWithString("SHOWFIELDS",sFields);
		currChannel.setPropertyWithString("FIELDSWIDTH",sFieldsWidth);
		currChannel.save(loginUser);
		reports.addSucessedReport(CMyString.format(LocaleServer.getString("document_field_showset_dowith_a.jsp.setchannelsdoclist", "设置当前栏目【{0}】文档列表显示字段成功！"), new String[]{currChannel.getDesc()}));

		//其它栏目的文档字段设置
		for(int i=0;i<oSelectedChnls.size();i++){
			oChannel = (Channel)oSelectedChnls.getAt(i);
			if(oChannel == null) continue;
			if(!oChannel.canEdit(loginUser)){
				reports.addFailedReport(CMyString.format(LocaleServer.getString("document_field_showset_dowith.jsp.fail2synchannel", "同步设置栏目【{0}】文档列表显示字段失败！栏目被用户【{1}】锁定！"), new String[]{oChannel.getDesc(),oChannel.getLockerUserName()}),null);
			}else{
				oChannel.setPropertyWithString("SHOWFIELDS",sFields);
				oChannel.setPropertyWithString("FIELDSWIDTH",sFieldsWidth);
				oChannel.save(loginUser);
				reports.addSucessedReport(CMyString.format(LocaleServer.getString("document_field_showset_dowith.jsp.success2updatedoclist", "同步设置栏目【{0}】文档列表显示字段成功！"), new String[]{oChannel.getDesc()}));
			}
		}
	}else{
		//当前栏目的文档字段设置
		//wenyh@2010-12-22,可能只选择了扩展字段
		if(CMyString.isEmpty(sFields)){
			currChannel.setPropertyWithString("SHOWFIELDS",sExtFields);
		}else{
			currChannel.setPropertyWithString("SHOWFIELDS",sFields+","+sExtFields);
		}		
		if(CMyString.isEmpty(sFieldsWidth)){
			currChannel.setPropertyWithString("FIELDSWIDTH",sExtFieldsWidth);
		}else{
			currChannel.setPropertyWithString("FIELDSWIDTH",sFieldsWidth+","+sExtFieldsWidth);
		}
		currChannel.save(loginUser);
		reports.addSucessedReport(CMyString.format(LocaleServer.getString("document_field_showset_dowith.jsp.setchannelsdoclist", "设置当前栏目【{0}】文档列表显示字段成功！"), new String[]{currChannel.getDesc()}));
		//"设置当前栏目【"+currChannel.getDesc()+"】文档列表显示字段成功！");

		String[] aExtFieldIds = sExtFieldsIds.split(",");
		String[] aExtFields = sExtFields.split(",");
		String[] aExtFieldsWidth = sExtFieldsWidth.split(",");
		String sExtFields1 = "";
		String sExtFieldsWidth1 = "";
		String sFields1 = "";
		String sFieldsWidth1 = "";
		ContentExtField oExtField = new ContentExtField();

		//其它栏目的文档字段设置
		for(int i=0;i<oSelectedChnls.size();i++){
			oChannel = (Channel)oSelectedChnls.getAt(i);
			if(oChannel == null) continue;
			if(!oChannel.canEdit(loginUser)){
					reports.addFailedReport(CMyString.format(LocaleServer.getString("document_field_showset_dowith.jsp.fail2synchannel", "同步设置栏目【{0}】文档列表显示字段失败！栏目被用户【{1}】锁定！"), new String[]{oChannel.getDesc(),oChannel.getLockerUserName()}),null);
					//"同步设置栏目【"+oChannel.getDesc()+"】文档列表显示字段失败！"+"栏目被用户【"+oChannel.getLockerUserName()+"】锁定！",null);
				continue;
			}
			sExtFields1 = "";
			sExtFieldsWidth1 = "";
			sFields1 = sFields;
			sFieldsWidth1 = sFieldsWidth;

			for(int j=0;j<aExtFieldIds.length;j++){
				oExtField = ContentExtField.findById(Integer.parseInt(aExtFieldIds[j]));
				if(oExtField == null) continue;

				//判断是否为要设置栏目的扩展字段
				if(assertCorrectData(oChannel,oExtField)){
					if(sExtFields1 == ""){
						sExtFields1 = aExtFields[j];
						sExtFieldsWidth1 = aExtFieldsWidth[j];
					}else{
						sExtFields1 += (","+aExtFields[j]);
						sExtFieldsWidth1 += (","+aExtFieldsWidth[j]);
					}
				}
			}
			if(sExtFields1 != ""){
				sFields1 += (","+sExtFields1);
				sFieldsWidth1 += (","+sExtFieldsWidth1);
			}
			oChannel.setPropertyWithString("SHOWFIELDS",sFields1);
			oChannel.setPropertyWithString("FIELDSWIDTH",sFieldsWidth1);
			oChannel.save(loginUser);
			reports.addSucessedReport(CMyString.format(LocaleServer.getString("document_field_showset_dowith.jsp.setchannelsdoclist", "同步设置栏目【{0}】文档列表显示字段成功！"), new String[]{oChannel.getDesc()}));
		}
	}
	reports.setTitle(CMyString.format(LocaleServer.getString("document_field_showset_dowith.jsp.success2setchannel", "设置文档列表显示字段：总共[{0}]个栏目，成功{1}个"), new int[]{reports.getReportsNum(),reports.getSucessedReporter().size()}));
out.clear();%><%=reports.toXML(true)%>
<%!
//判断扩展字段是否属于指定栏目
public boolean assertCorrectData(Channel _oChannel, ContentExtField _oExtField){
	try{
		IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
		ContentExtFields currExtendedFields  = currChannelService.getExtFields(_oChannel, null);
		ContentExtField extField = new ContentExtField();

		if(currExtendedFields.size() <= 0) return false;

		for(int i=0;i<currExtendedFields.size();i++){
			extField = (ContentExtField)currExtendedFields.getAt(i);
			if(extField == null) continue;
			if(extField.getExtFieldId() == _oExtField.getExtFieldId()){
				return true;
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return false;
}
%>