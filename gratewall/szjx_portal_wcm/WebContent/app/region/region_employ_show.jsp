<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.publish.region.RegionInfo"%>
<%@ page import="com.trs.DreamFactory"%>
<%@ page import="com.trs.cms.content.CMSObj"%>
<%@ page import="com.trs.components.common.publish.domain.template.TemplateEmployMgr"%>
<%@ page import="com.trs.components.wcm.publish.region.RegionEmployMgr"%>
<%@ page import="com.trs.components.wcm.publish.region.RegionEmploy"%>
<%@ page import="com.trs.components.wcm.publish.region.RegionEmploys"%>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement"%>
<%@ page import="com.trs.components.common.publish.persistent.template.Template"%>
<%@ page import="com.trs.components.common.publish.persistent.template.TemplateEmploy"%>
<%@ page import="com.trs.components.common.publish.persistent.template.TemplateEmploys"%>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.presentation.locale.LocaleServer"%>
<%@include file="../include/public_server.jsp"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.addHeader("Cache-Control", "no-store"); //Firefox
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", -1);
    response.setDateHeader("max-age", 0);
%>
<%
	int nRegionId = currRequestHelper.getInt("objectId", 0);
	RegionInfo currRegionInfo = null;
	if(nRegionId > 0){
		currRegionInfo = RegionInfo.findById(nRegionId);
	}
	if(currRegionInfo == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    CMyString.format(LocaleServer.getString("flowemployee.object.not.found.lead","没有找到ID为{0}的导读"), new int[] { nRegionId }));
	}
	int nSize = 0;
	RegionEmployMgr regionEmployMgr = (RegionEmployMgr) DreamFactory
                .createObjectById("RegionEmployMgr");
	RegionEmploys oRegionEmploys = regionEmployMgr.getRegionEmploysByRegionAndType(
                nRegionId, new int[] {Template.OBJ_TYPE});
	TemplateEmploys objs = new TemplateEmploys(loginUser);
	if (oRegionEmploys != null) {
		for (int i = 0; i < oRegionEmploys.size(); i++) {
			RegionEmploy oRegionEmploy = (RegionEmploy) (oRegionEmploys
					.getAt(i));
			if (oRegionEmploy == null)
				continue;
			int nTemplateId = oRegionEmploy.getObjId();
			Template template = Template.findById(nTemplateId);
			if (template == null)
				continue;
			TemplateEmployMgr m_oEmployMgr = (TemplateEmployMgr) DreamFactory
					.createObjectById("TemplateEmployMgr");
			TemplateEmploys employs = m_oEmployMgr.getTemplateEmployers(
					template, null);
			for(int index = 0, length = employs.size(); index < length; index++){
				TemplateEmploy anEmploy = (TemplateEmploy) employs.getAt(index);
				IPublishElement publishElement = anEmploy.getEmployer();
				CMSObj tempObj = publishElement.getSubstance();
				if(anEmploy == null || isDeleted(tempObj)) continue;
				objs.addElement(anEmploy);
			}
		}
		nSize = objs.size();
	} else {
		nSize = 0;
	}
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title WCMAnt:param="region_employ_show.jsp.introconfinfo">导读配置使用信息</title>
</head>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/locale/cn.js" WCMAnt:locale="../../app/js/locale/$locale$.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<!-- CarshBoard Inner Start -->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<style type="text/css">
html, body{
	font-size:12px;
}
.num{
	color : blue;
}
.container{
	padding:0px;
	height:100%;
	width:100%;
	overflow-x:hidden;
	overflow-y:auto;
	scrollbar-face-color: #f6f6f6;
	scrollbar-highlight-color: #ffffff;
	scrollbar-shadow-color: #cccccc; 
	scrollbar-3dlight-color: #cccccc; 
	scrollbar-arrow-color: #330000; 
	scrollbar-track-color: #f6f6f6; 
	scrollbar-darkshadow-color: #ffffff;
}
.liner_row{
	height: 20px;
	line-height: 20px;
	font-size: 12px;
	float: left;
	width: 190px;
	font-family: Georgia;
	overflow: hidden;
	text-overflow:ellipsis;
	white-space:nowrap;
}
.contentsp{
	overflow:hidden;
	text-overflow:ellipsis;
	white-space:nowrap;
}
</style>
<script language="javascript">
<!--
	window.m_cbCfg = {
        btns : [
            {
                text : wcm.LANG.region_employ_show_2000 || '关闭'
            } 
        ]
    };
//-->
</script>
<body style="overflow:hidden;">
<div class="container"> 
<span WCMAnt:param="region_employ_show.jsp.bei">被<span class="num"><%=nSize%></span><span WCMAnt:param="region_employ_show.jsp.website_usethis">个站点或栏目使用：</span></span></br>
<%
	for(int i = 0; i < objs.size(); i++){
		TemplateEmploy anEmploy = (TemplateEmploy) objs.getAt(i);
        IPublishElement publishElement = anEmploy.getEmployer();
		CMSObj tempObj = publishElement.getSubstance();
		int nObjType = tempObj.getWCMType();
		int nObjId = tempObj.getId();
%>
	<div class="liner_row">
		<span ><%=(i+1)%>.</span>&nbsp;<span class="" title="<%=getName(tempObj)%>"><%=getName(tempObj)%></span>
	</div>
<%
	}
%>
</div>
</body>
</html>
<%!
	private boolean isDeleted(CMSObj obj)throws Exception{
        if(obj == null) return true;
        switch(obj.getWCMType()){
            case Channel.OBJ_TYPE :
            case WebSite.OBJ_TYPE : 
                return ((BaseChannel)obj).isDeleted();
            case Document.OBJ_TYPE :
                return ((Document)obj).isDeleted();
        }
        return false;
    }
	private String getName(CMSObj obj)throws Exception{
		if(obj == null) return "";
		String sName = "";
		switch(obj.getWCMType()){
			case WebSite.OBJ_TYPE : 
				sName = CMyString.format(LocaleServer.getString("region_employ_show.jsp.website_name", "{0}站点-{1}"), new Object[]{((WebSite)obj).getName(),String.valueOf(obj.getId())}) ;
				break;
			case Channel.OBJ_TYPE :
				sName = CMyString.format(LocaleServer.getString("region_employ_show.jsp.channel_name", "{0}栏目-{1}"), new Object[]{((Channel)obj).getName(),String.valueOf(obj.getId())});
				break;
			case Document.OBJ_TYPE :
				sName = CMyString.format(LocaleServer.getString("region_employ_show.jsp.doc_name", "{0}文档-{1}"), new Object[]{((Document)obj).getTitle(),String.valueOf(obj.getId())});
				break;
		}
		return sName;
	}
%>