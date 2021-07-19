<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@include file="../include/public_processor.jsp"%>
<%@include file="../system/status_locale.jsp"%>
<%@include file="website_add_edit_init.jsp"%>
<% 
try{    
%>
<HTML>
<HEAD>
<TITLE WCMAnt:param="website_add_edit.jsp.title">新建/修改站点</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../../app/js/runtime/myext-debug.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/template/TemplateSelector.js"></script>

<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/source/wcmlib/tabpanel/TabPanel.js"></script>
<link href="../../app/js/source/wcmlib/tabpanel/resource/tabpanel.css" rel="stylesheet" type="text/css" />

<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<script src="../../app/template/TemplateSelector.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/data/locale/website.js"></script>
<script language="javascript" src="../../app/js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../app/js/easyversion/calendar_lang/$locale$.js" type="text/javascript"></script>
<script language="javascript" src="../../app/js/easyversion/calendar2.js" type="text/javascript"></script>
<link href="../../app/js/easyversion/resource/calendar.css" rel="stylesheet" type="text/css" />
<script language="javascript">
<!--
    var hasEditRight = <%=hasEditRight%>;
    var nObjectId = <%=nObjectId%>;
//-->
</script>
<script src="website_add_edit.js"></script>
<link href="website_add_edit.css" WCMAnt:locale="website_add_edit_$locale$.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .wcm-tab-panel{
        width:520px;
        height:420px;
        scrollbar-face-color: #f6f6f6;
        scrollbar-highlight-color: #ffffff;
        scrollbar-shadow-color: #cccccc; 
        scrollbar-3dlight-color: #cccccc; 
        scrollbar-arrow-color: #330000; 
        scrollbar-track-color: #f6f6f6; 
        scrollbar-darkshadow-color: #ffffff;
    }
</style>
<script language="javascript">
<!--
    
    var oTabPanel;
    Event.observe(window, 'load', function(){
        oTabPanel = new wcm.TabPanel({
            id : 'tabPanel',
            activeTab : 'tab-general'
        }).show();
    });
//-->
</script>
</head>

<body >

<input type="hidden" id="saveInterval" value=""/>
<div id="formData" name="formData">
    <input type="hidden" name="status" id="status" value="<%=nStatus%>" ignore="true">
    <input type="hidden" name="rightValue" id="rightValue" value="<%=sRightValue%>" ignore="true">
    <input type="hidden" name="objectId" id="objectId" value="<%=nObjectId%>">
    <input type="hidden" name="siteType" id="siteType" value="<%=nSiteType%>">
	<input type="hidden" name="ChnlNameFieldLen" id="ChnlNameFieldLen" value="<%=nDBChnlNameFieldLen%>">
    <input type="hidden" name="pageEncoding" id="pageEncoding">
    <input type="hidden" name="outlineTemplates" id="outlineTemplates"> 
    <input type="hidden" name="scheduleMode" id="scheduleMode">     
    <div class="wcm-tab-panel" id="tabPanel" style="display:none;overflow:auto;overflow-x:hidden;">
        <div class="head-box" style="padding-left:20px;">
            <div class="tab-head" item="tab-general"><a href="#" WCMAnt:param="website_add_edit.jsp.norman">常规</a></div>
            <div class="tab-head" style="display:none" item="tab-advance" id="tab-advance-header"><a href="#" WCMAnt:param="website_add_edit.jsp.high">高级</a></div>
        </div>
        <div class="body-box" style="padding:10px;">
            <div class="tab-body" id="tab-general">
                <div class="block_box">
                    <div class="header_row_left">
                        <div class="header_row_right">
                            <div class="header_row_center">
                                <div class="header_row_text" WCMAnt:param="website_add_edit.jsp.desc">基本描述</div>
                            </div>
                        </div>
                    </div>
                    <div class="body_box">
                        <div class="row">
                            <span class="leftTip leftTipen" WCMAnt:param="website_add_edit.jsp.name">
                                唯一标识：
                            </span>
                            <span>
                                <input name="siteName" id="siteName" type="text" class="kuang_as controlWidth" 
                                value="<%=CMyString.filterForHTMLValue(sSiteName)%>" _value="<%=CMyString.filterForHTMLValue(sSiteName)%>"/>
                            </span>
                        </div>
                        <div class="row" style="height:20px;overflow:hidden;">
                            <span class="leftTip  leftTipen">&nbsp;</span>
                            <span id='siteNameValidDesc'></span>
                        </div>
                        <div class="row">
                            <span class="leftTip  leftTipen" WCMAnt:param="website_add_edit.jsp.showname">
                                显示名称：
                            </span>
                            <span>
                                <input name="siteDesc" id="siteDesc" type="text" class="kuang_as controlWidth" value="<%=CMyString.filterForHTMLValue(sSiteDesc)%>"/>
                            </span>
                        </div>
                        <div class="row" style="height:20px;overflow:hidden;">
                            <span class="leftTip  leftTipen">&nbsp;</span>
                            <span id='siteDescValidDesc'></span>
                        </div>
                        <div class="row">
                            <span class="leftTip  leftTipen" WCMAnt:param="website_add_edit.jsp.fontsite">
                                前一个站点：
                            </span>
                            <span>
                                <select name="siteOrder" class="controlWidth" _value="<%=nOrder%>">
                                    <option value="-1" WCMAnt:param="website_add_edit.jsp.font">最前面</option>
                                    <%
                                        for (int i = 0, length = oSiblings.size(); i < length; i++) {
                                            WebSite website = (WebSite) oSiblings.getAt(i);
                                            if (website == null)
                                                continue;
                                    %>
                                        <option value="<%=website.getOrder()%>"><%=CMyString.transDisplay(website.getDesc())%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="block_box">
                    <div class="header_row_left">
                        <div class="header_row_right">
                            <div class="header_row_center">
                                <div class="header_row_text" WCMAnt:param="website_add_edit.jsp.pubtemp">发布模板</div>
                            </div>
                        </div>
                    </div>
                    <div class="body_box">
                        <div class="row">
                            <span class="leftTip leftTipmiden">
                                <span WCMAnt:param="website_add_edit.jsp.outline" class="leftTipen">首页模板</span>
                                <a href="#" name="template" index="0" class="template_select" title="选择模板" WCMAnt:paramattr="title:website_add_edit.jsp.seltemp" style="display:none;"></a>：
                            </span>
                            <input type="hidden" name="outlineTemplate" value="<%=nOutlineTemplateId%>">
                            <span class="spAttrItem spAttrSpcItem" id="spOutlineTemp">                          
                                <%=sOutlineTemplateName%>
                            </span>
                        </div>
                        <div class="row">
                            <span class="leftTip leftTipmiden">
                                <span WCMAnt:param="website_add_edit.jsp.detailtemp" class="leftTipen">细览模板</span>
                                <a href="#" name="template" index="1" class="template_select" title="选择模板" WCMAnt:paramattr="title:website_add_edit.jsp.seltemp" style="display:none;"></a>：
                            </span>
                            <input type="hidden" name="detailTemplate" value="<%=nDetailTemplateId%>">
                            <span class="spAttrItem spAttrSpcItem" id="spDetailTemp">                           
                                <%=sDetailTemplateName%>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="block_box">
                    <div class="header_row_left">
                        <div class="header_row_right">
                            <div class="header_row_center">
                                <div class="header_row_text" WCMAnt:param="website_add_edit.jsp.pubfile">发布文件规则</div>
                            </div>
                        </div>
                    </div>
                    <div class="body_box">
                        <div class="row">
                            <span class="leftTip" WCMAnt:param="website_add_edit.jsp.datapath">
                                存放位置：
                            </span>
                            <span>
                                <input name="dataPath" id="dataPath" type="text"
                                class="kuang_as controlWidth" value="<%=CMyString.filterForHTMLValue(sDataPath)%>" _value="<%=CMyString.filterForHTMLValue(sDataPath)%>"/> 
                            </span>                    
                        </div>
                        <div class="row" style="height:20px;">
                            <span id='dataPathValidDesc'></span>
                        </div>
                        <div class="row">
                            <span class="leftTip" WCMAnt:param="website_add_edit.jsp.rootdomain">
                                站点HTTP：
                            </span>
                            <span>
                                <input name="rootDomain" id="rootDomain" type="text" 
                                class="kuang_as controlWidth" value="<%=CMyString.filterForHTMLValue(sRootDomain)%>"/> 
                            </span>
                        </div>
                        <div class="row" style="height:20px;overflow:hidden;">
                            <span id='rootDomainValidDesc'></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-body" id="tab-advance">
                <div class="block_box">
                    <div class="header_row_left">
                        <div class="header_row_right">
                            <div class="header_row_center">
                                <div class="header_row_text" WCMAnt:param="website_add_edit.jsp.pubtemp">发布模板</div>
                            </div>
                        </div>
                    </div>
                    <div class="body_box">
                        <div class="row">                       
                            <span class="leftTipspi leftTiptemp">
                                <span WCMAnt:param="website_add_edit.jsp.otheroutline">其他概览模板</span>
                                <a href="#" name="template" index="2" class="template_select" title="选择模板" WCMAnt:paramattr="title:website_add_edit.jsp.seltemps" style="display:none;"></a>：
                            </span>
                            <input type="hidden" name="otherTemplates" value="<%=sOtherTemplateIds%>">
                            <span class="spAttrItem spAttrSpcItem" id="spOtherTemps">
                            <%
                                String sTempNames = "";
                                for (int i = 0, length = oOtherTemplates.size(); i < length; i++) {
                                    Template tempate = (Template) oOtherTemplates.getAt(i);
                                    if (tempate == null)
                                        continue;
                                    if(i!=length-1){
                                        sTempNames += (CMyString.transDisplay(tempate.getName()) + ",");
                            %>
                                    <%=CMyString.transDisplay(tempate.getName())%>, 
                            <%
                                    }else{
                                        sTempNames += CMyString.transDisplay(tempate.getName());
                            %>
                                    <%=CMyString.transDisplay(tempate.getName())%>
                            <%
                                    }
                                }
                                if(oOtherTemplates.size() <= 0) out.print(LocaleServer.getString("website_addedit.label.none", "无"));
                            %>
                            </span>
                        </div>
                        <div>
                            <span style="padding-left:10px;"><input type="hidden" name="PublishLimit" id="PublishLimit" isAttr="1"  value="<%=sPublishLimit%>">
                            <input type="checkbox" name="PublishLimitCtrl" id="PublishLimitCtrl" value="1" onclick="onPublishLimit();" <%=(sPublishLimit=="1")?"checked":""%>/><label for="PublishLimitCtrl" WCMAnt:param="channel_add_edit.jsp.pubstartdate" >发布后仅显示指定时间开始撰写的文档</label></span><span id="showPubLimit">&nbsp;&nbsp;<input type="text" name="PUBSTARTDATE" id="PUBSTARTDATE" elname="文档发布开始时间" WCMAnt:paramattr="elname:channel_add_edit.jsp.pubstarttime" value='<%=sPubStartDate%>' isAttr="1" class="kuang_as"><button id="pubstart" class="calendarShow" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0 style="margin-top:-1px;margin-left:-1px"></button></span>
                        </div>
                    </div>
                </div>
                <div class="block_box">
                    <div class="header_row_left">
                        <div class="header_row_right">
                            <div class="header_row_center">
                                <div class="header_row_text" WCMAnt:param="website_add_edit.jsp.pageencode">页面编码</div>
                            </div>
                        </div>
                    </div>
                    <div class="body_box">
                        <div class="row">
                            <span class="leftTipmin leftTipcode" WCMAnt:param="website_add_edit.jsp.pageencodemaohao">
                                页面编码：
                            </span>
                            <span>
                                <SELECT name="siteLanguage" id="siteLanguage" class="languagewidth" _value="<%=nSiteLanguage%>"> 
                                    <OPTION value=8 selected Encode="UTF-8">UTF-8</OPTION> 
                                    <OPTION value=1 Encode="GBK" WCMAnt:param="website_add_edit.jsp.chianness">简体中文</OPTION> 
                                    <OPTION value=2 Encode="iso-8859-1" WCMAnt:param="website_add_edit.jsp.english">英语</OPTION> 
                                    <OPTION value=3 Encode="iso-8859-1" WCMAnt:param="website_add_edit.jsp.french">法语</OPTION>
                                    <OPTION value=4 Encode="windows-1251" WCMAnt:param="website_add_edit.jsp.eyu">俄语</OPTION> 
                                    <OPTION value=5 Encode="iso-8859-1" WCMAnt:param="website_add_edit.jsp.xibanya">西班牙语</OPTION>
                                    <OPTION value=6 Encode="windows-1256" WCMAnt:param="website_add_edit.jsp.alabo">阿拉伯语</OPTION> 
                                    <OPTION value=7 Encode="big5" WCMAnt:param="website_add_edit.jsp.chianness2">中文繁体</OPTION>
                                    <OPTION value=1252 Encode="iso-8859-1" WCMAnt:param="website_add_edit.jsp.butaoya">葡萄牙语</OPTION>
                                    <OPTION value=1031 Encode="iso-8859-1" WCMAnt:param="website_add_edit.jsp.deyu">德语</OPTION>
                                    <OPTION value=1040 Encode="iso-8859-1" WCMAnt:param="website_add_edit.jsp.yidali">意大利语</OPTION> 
                                    <OPTION value=1043 Encode="iso-8859-1" WCMAnt:param="website_add_edit.jsp.helan">荷兰语</OPTION> 
                                    <OPTION value=1026 Encode="windows-1251" WCMAnt:param="website_add_edit.jsp.baojialiya">保加利亚语</OPTION>
                                    <OPTION value=1045 Encode="iso-8859-2" WCMAnt:param="website_add_edit.jsp.bolan">波兰语</OPTION>
                                    <OPTION value=1048 Encode="windows-1250" WCMAnt:param="website_add_edit.jsp.luomaniya">罗马尼亚语</OPTION> 
                                    <OPTION value=1038 Encode="iso-8859-2" WCMAnt:param="website_add_edit.jsp.xiongyali">匈牙利语</OPTION>
                                    <OPTION value=1029 Encode="windows-1250" WCMAnt:param="website_add_edit.jsp.jieke">捷克语</OPTION>
                                    <OPTION value=1042 Encode="euc-kr" WCMAnt:param="website_add_edit.jsp.chaoxian">朝鲜语</OPTION> 
                                    <OPTION value=1054 Encode="windows-874" WCMAnt:param="website_add_edit.jsp.taiguo">泰国语</OPTION>
                                    <OPTION value=1055 Encode="windows-1254" WCMAnt:param="website_add_edit.jsp.tuerqi">土耳其语</OPTION> 
                                    <OPTION value=1066 Encode="windows-1258" WCMAnt:param="website_add_edit.jsp.yuenan">越南语</OPTION>
                                    <OPTION value=1104 Encode="windows-1251" WCMAnt:param="website_add_edit.jsp.menggu">蒙古语</OPTION> 
                                    <OPTION value=1057 Encode="iso-8859-1" WCMAnt:param="website_add_edit.jsp.yinni">印尼语</OPTION>
                                    <OPTION value=932 Encode="euc-jp" WCMAnt:param="website_add_edit.jsp.japanness">日语</OPTION>
                                </SELECT>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="block_box">
                    <div class="header_row_left">
                        <div class="header_row_right">
                            <div class="header_row_center">
                                <div class="header_row_text" WCMAnt:param="website_add_edit.jsp.pubstatus">发布状态</div>
                            </div>
                        </div>
                    </div>
                    <div class="body_box">
                        <div class="row">
                            <span class="leftTipmid" WCMAnt:param="website_add_edit.jsp.canpub">
                                可发布文档的状态：
                            </span>
                            <span id="statusesCanDoPubBox" class="CanDoPubBox" _value="<%=CMyString.filterForHTMLValue(sStatusesCanDoPub)%>">
                            <%
                                for (int i = 0, length = publishStatuses.size(); i < length; i++) {
                                    Status status = (Status) publishStatuses.getAt(i);
                                    if (status == null)
                                        continue;
									int nStatusId = status.getId();
									if(nStatusId == Status.STATUS_ID_DRAFT)
										continue;
                            %>
                                    <input id="status_<%=i%>"  type="checkbox" name="statusesCanDoPub"  value="<%=nStatusId%>"><label for="status_<%=i%>"><%=CMyString.transDisplay(getStatusLocale(status.getDisp()))%></label>
                            <%
                                }
                            %>
                            </span>
                        </div>
                        <div class="row" id="publicstatustip" style="padding-left:35px;color:green;display:none;" WCMAnt:param="website_add_edit.jsp.publicstatustip">如果仅选择已发状态，将导致非已发状态下的文档都不能发布出去哦</div>
                        <div class="row">
                            <span class="leftTipStatus" WCMAnt:param="website_add_edit.jsp.statusafteredit">
                                文档被引用到当前站点后状态：
                            </span>
                            <span>
                                <select name="statusIdAfterModify" _value="<%=nStatusIdAfterModify%>">
                                    <%
                                        for (int i = 0, length = documentStatuses.size(); i < length; i++) {
                                            Status status = (Status) documentStatuses.getAt(i);
                                            if (status == null)
                                                continue;
											int nStatusId = status.getId();
											if(nStatusId == Status.STATUS_ID_DRAFT)
												continue;
											 
                                    %>
                                        <option value="<%=nStatusId%>"><%=CMyString.transDisplay(getStatusLocale(status.getDisp()))%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="block_box">
                    <div class="header_row_left">
                        <div class="header_row_right">
                            <div class="header_row_center">
                                <div class="header_row_text" WCMAnt:param="website_add_edit.jsp.planpub">计划发布</div>
                            </div>
                        </div>
                    </div>
                    <div class="body_box" id="ScheduleModeBox" _value="<%=nScheduleMode%>">
                        <div>
                            <span class="spAttrShort">
                                <input type="radio" id="rdSchdMode_0" name="rdSchdMode" value="0" onclick="switchSchdDisp(0)"/>
                            </span>
                            <span class="spAttrSpcItem">
                                <label for="rdSchdMode_0" WCMAnt:param="website_add_edit.jsp.notpub">不设置计划发布</label>
                            </span>
                        </div>
                        <div style="margin-top: 0px;">
                            <span class="spAttrShort">
                                <input type="radio" id="rdSchdMode_1" name="rdSchdMode" value="1" onclick="switchSchdDisp(1)"/>
                            </span>
                            <span class="spAttrSpcItem">
                                <label for="rdSchdMode_1" WCMAnt:param="website_add_edit.jsp.onetime">每天运行一次</label>
                            </span>
                        </div>
                        <div style="display:none; margin-top: 0px; padding-bottom: 5px;" id="trSchdRunOnce">
                            <div style="padding-left:50px;">
                                <span WCMAnt:param="website_add_edit.jsp.runtime">运行时间:</span>
                                <input name="execTime" type="hidden" id="execTime" value=""/>
                                <input name="execTimeHour" class="kuang_as time" type="text" id="execTimeHour" value="<%=CMyString.filterForHTMLValue(sExecTimeHour)%>" ignore=1 maxlength=2/><span>:</span><input type="text" class="kuang_as time" name="execTimeMinute" id="execTimeMinute" value="<%=CMyString.filterForHTMLValue(sExecTimeMinute)%>" ignore=1 maxlength=2/>
                                <span id='exec'></span>
                            </div>
                        </div>  
                        <div style="margin-top: 0px;">
                            <span class="spAttrShort">
                                <input type="radio" id="rdSchdMode_2" name="rdSchdMode" value="2" onclick="switchSchdDisp(2)"/>
                            </span>
                            <span class="spAttrSpcItem">
                                <label for="rdSchdMode_2" WCMAnt:param="website_add_edit.jsp.manytimes">每天运行多次</label>
                            </span>
                        </div>
                        <div style="display:none; margin-top: 0px; padding-bottom: 5px;" id="trSchdRunTimes">
                            <div style="padding-left:38px;">
                                <span WCMAnt:param="website_add_edit.jsp.starttime" class="timewidth">开始时间:</span>
                                <input name="startTime" id="startTime" type="hidden" value=""/>
                                <input name="startTimeHour" class="kuang_as time" type="text" id="startTimeHour" value="<%=CMyString.filterForHTMLValue(sStartTimeHour)%>" ignore=1 maxlength=2/><span>:</span><input type="text" class="kuang_as time" name="startTimeMinute" id="startTimeMinute" value="<%=CMyString.filterForHTMLValue(sStartTimeMinute)%>" ignore=1 maxlength=2/>
                                <span id='start'></span>
                            </div>
                            <div style="padding-left:38px;">
                                <span WCMAnt:param="website_add_edit.jsp.endtime" class="timewidth">结束时间:</span>
                                <input name="endTime" id="endTime" type="hidden" value=""/>
                                <input name="endTimeHour" class="kuang_as time" type="text" id="endTimeHour" value="<%=CMyString.filterForHTMLValue(sEndTimeHour)%>" ignore=1 maxlength=2/><span>:</span><input type="text" class="kuang_as time" name="endTimeMinute" id="endTimeMinute" value="<%=CMyString.filterForHTMLValue(sEndTimeMinute)%>" ignore=1 maxlength=2/>
                                <span id='end'></span>
                            </div>   
                            <div style="padding-left:38px;">
                                <span WCMAnt:param="website_add_edit.jsp.interval" class="timewidth">间隔时间:</span>
                                <input name="interval" id="interval" type="hidden" value=""/>
                                <input name="intervalHour" class="kuang_as time" type="text" id="intervalHour" value="<%=sIntervalHour%>" ignore=1 maxlength=2/><span>:</span><input type="text" class="kuang_as time" name="intervalMinute" id="intervalMinute" value="<%=sIntervalMinute%>" ignore=1 maxlength=2/>
                                <span id='interv'></span>
                            </div>  
                        </div>  
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script language="javascript">
<!--
    wcm.TRSCalendar.get({
        input : 'PUBSTARTDATE',
        handler : 'pubstart',
        top : 20
    });
//-->
</script>
</body>
</html>
<%
}catch(Exception e) {
    e.printStackTrace();
}   
%>