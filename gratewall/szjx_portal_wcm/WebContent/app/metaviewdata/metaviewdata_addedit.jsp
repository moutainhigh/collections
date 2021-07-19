<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.metadata.service.DefaultChannelMakerCenter" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.cms.auth.domain.DepartmentMgrImpl" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.service.UserRelateInfoMaker" %>
<%@ page import="com.trs.components.metadata.service.DefaultChannelMakerCenter" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefMgr" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.ajaxservice.xmlconvertors.AppendixToXML" %>
<%@ page import="com.trs.ajaxservice.xmlconvertors.RelationToXML" %>
<%@ page import="com.trs.components.wcm.content.domain.AppendixMgr" %>
<%@ page import="com.trs.components.wcm.content.domain.RelationMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.components.wcm.content.persistent.Relations" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page buffer="64kb" %>
<%@include file="../include/public_server.jsp"%>

<%
    int nObjectId = currRequestHelper.getInt("ObjectId", 0);
    if(nObjectId == 0){
        nObjectId = currRequestHelper.getInt("DocumentId", 0);
    }
    
    int nChannelId = currRequestHelper.getInt("ChannelId", 0);      
    int nViewId = currRequestHelper.getInt("ViewId", 0);
	
%>
	<script>
		window.location.href = "../application/<%=nViewId%>/metaviewdata_addedit.jsp?<%=request.getQueryString()%>"
	</script>
<%
	if(true) return;

    int nSiteId = 0;
    Channel channel = null;
    Document document = null;       
    Channels oChannelsOfMetaView = null;
    MetaView metaView = null;       
    if(nObjectId>0){
        document  = Document.findById(nObjectId,"DOCCHANNEL,DOCTITLE,DOCID,DOCKIND");
        if(document == null) throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("addedit.doc.id.zero","没有找到文档[Id={0}]"),new int[]{nObjectId}));
        if(nChannelId > 0){
            channel = Channel.findById(nChannelId);
            if(channel == null){
                throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("addedit.channel.id.zero","没有找到栏目[Id={0}]"),new int[]{nChannelId}));
            }
        }else{
            channel = document.getChannel();
        }
        if(nViewId == 0){
            nViewId = document.getKindId();
            metaView = MetaView.findById(nViewId);
        }
    }else{
        if(nChannelId > 0){
            channel = Channel.findById(nChannelId);
            if(channel == null){
                throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("addedit.channel.notFound","没有找到栏目[Id={0}]"),new int[]{nChannelId}));
            }
        }else{
            if(nViewId == 0) throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,LocaleServer.getString("addedit.paraWrong","参数错误"));
            metaView = MetaView.findById(nViewId);
            if(metaView == null) throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("addedit.view.notFound","没有找到视图[Id={0]]"),new int[]{nViewId}));
            DefaultChannelMakerCenter maker = DefaultChannelMakerCenter.getInstance();
            channel = maker.makeDefaultChannel(loginUser, nViewId);
            if(channel == null){
                oChannelsOfMetaView = maker.getAvailableChannels(loginUser,nViewId);
                if(oChannelsOfMetaView == null || oChannelsOfMetaView.isEmpty()){
                    throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("addedit.oobj.not.found","没有找到用户默认的存储栏目！"));
                }else if(oChannelsOfMetaView.size() == 1){
                    //如果只剩下一个栏目，则这个栏目为目标栏目，而不需要进行选择
                    channel = (Channel)oChannelsOfMetaView.getAt(0);
                }
            }
        }
        document = new Document();
    }
    
    if(metaView == null){
        IMetaViewEmployerMgr employerMgr = (IMetaViewEmployerMgr)DreamFactory.createObjectById("IMetaViewEmployerMgr");
        metaView = employerMgr.getViewOfEmployer(channel);
        if(metaView == null){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("addedit.channel.view.not.found","栏目没有分配视图或分配的视图没有找到！"));
        }
        nChannelId = channel.getId();
        nSiteId = channel.getSiteId();
        nViewId = metaView.getId(); 
    }       
    
    int nFlowDocId = currRequestHelper.getInt("FlowDocId", 0);  
    IMetaDataDefMgr metaDataDefMgr = (IMetaDataDefMgr) DreamFactory.createObjectById("IMetaDataDefMgr");
    MetaViewFields metaViewFields = metaDataDefMgr.getViewFields(loginUser,metaView,null);

    //初始化分类
    Map mDefaultClassInfo = new HashMap(5,1.0f);
    if(nObjectId == 0){ 
        IMetaDataDefCacheMgr oMetaDataDefCacheMgr = (IMetaDataDefCacheMgr) DreamFactory.createObjectById("IMetaDataDefCacheMgr");
        Map mViewFieldsOfClassInfo = oMetaDataDefCacheMgr.getMetaViewFields(nViewId,MetaDataConstants.FIELD_TYPE_CLASS);                
        // 循环比对每一个分类法字段
        Iterator iterator = mViewFieldsOfClassInfo.values().iterator();
        int nRootId = 0;

        int nCurrClassInfoId = currRequestHelper.getInt("ClassInfoId", 0);
        if(nCurrClassInfoId>0){
            ClassInfo currClassInfo = ClassInfo.findById(nCurrClassInfoId);
            if(currClassInfo == null){
                throw new WCMException(CMyString.format(LocaleServer.getString("addedit.class.notFound","系统没有找到指定的分类！[ID={0}]"),new int[]{nCurrClassInfoId}));
            }
            nRootId = currClassInfo.getRootId();
            while (iterator.hasNext()) {
                MetaViewField oViewField = (MetaViewField) iterator.next();
                if (oViewField.getClassId() == nRootId) {
                    mDefaultClassInfo.put(oViewField.getDBName().toUpperCase(),new String[]{String.valueOf(nCurrClassInfoId),currClassInfo.getName()});
                    break;
                }
            }           
        }
        mViewFieldsOfClassInfo.clear();
    }   

    //细览模板
    int nDocTemplateId = 0;
    String sDocTemplateName = "";
    if(nObjectId > 0) {
        IPublishFolder folder = (IPublishFolder)PublishElementFactory.makeElementFrom(channel);
        IPublishContent content =  PublishElementFactory.makeContentFrom(Document.findById(nObjectId), folder);
        com.trs.service.ITemplateService currTemplateService = ServiceHelper.createTemplateService();
        Template  docTemplate = currTemplateService.getDetailTemplate(content);
        if(docTemplate != null) {
            nDocTemplateId = docTemplate.getId();
        }
        sDocTemplateName = getTemplateAsString(docTemplate);
    }

    MetaViewData metaViewData = null;
    if(nObjectId == 0){
        metaViewData = new MetaViewData(metaView);
    }else{
        metaViewData = MetaViewData.findById(nObjectId);
    }
    out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE WCMAnt:param="metaviewdata_addedit.jsp.newormodifyrecord">新建/修改记录</TITLE>

<script language="javascript">
<!--
    var m_nSiteId = <%=nSiteId%>;
    var m_nChannelId = <%=nChannelId%>;
    var m_nDocumentId = <%=nObjectId%>;
    var m_nFlowDocId = <%=nFlowDocId%>;
    var m_nViewId = <%=nViewId%>;
    var m_arRelationIds = [];
    var m_FileUploaderIds = [];
    var m_Relations = {};
    var m_EditorIds = [];
    var m_SuggestionIds = [];
//-->
</script>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metaviewdata.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<!-- CarshBoard Inner Start -->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<!-- CarshBoard Inner End -->
<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar_Obj.js" type="text/javascript"></script>
<script language="javascript" src="../../app/js/source/wcmlib/calendar/calendar_lang/cn.js" type="text/javascript" WCMAnt:locale="../../app/js/source/wcmlib/calendar/calendar_lang/$locale$.js"></script>
<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar.js" type="text/javascript"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<!-- ~Template CSS End~ -->
<link href="metaviewdata_addedit.css" rel="stylesheet" type="text/css" />
<!-- ~Template CSS End~ -->
<%
if(currRequestHelper.getInt("FromEditor", 0) == 1){
    out.print("<style>" + "html,body{height:100%;overflow:auto;}"
             + ".objectForm_div{height:95%;overflow:auto;}" + "</style>");
}else{
    out.print("<style>" + "html,body{overflow:auto;}"
             + ".objectForm_div{height:100%;overflow:auto;}" + "</style>");
}
%>
<script language="javascript">
<!--    
var m_oOtherTableFields = new Object();
function pushOtherTableFields(_sTableName, _sDBFieldName, _sAnotherName, _sFieldName){
    if(!m_oOtherTableFields[_sTableName]){
        m_oOtherTableFields[_sTableName] = [];
    }
    m_oOtherTableFields[_sTableName].push([_sDBFieldName, _sAnotherName, _sFieldName]);
}

<%
    MetaViewField metaViewField = null;
    for(int i=0,size=metaViewFields.size();i<size;i++){
        metaViewField = (MetaViewField)metaViewFields.getAt(i);
        if(metaViewField == null || metaViewField.isFromMainTable()) continue;
        
        String otherTableName = metaViewField.getTableName();
        String otherDBFieldName = metaViewField.getDBField().getName();
        String otherAnotherName = metaViewField.getAnotherName();
        String otherViewFieldName = metaViewField.getName();

%>
    pushOtherTableFields("WCMMetaTable<%=otherTableName%>", "<%=otherDBFieldName%>", "<%=otherAnotherName%>", "<%=otherViewFieldName%>");
<%}%>   
//-->
</script>
<script src="../../app/template/TemplateSelector.js"></script>
<script src="../../app/classinfo/ClassInfoSelector.js"></script>
<script src="../../app/js/source/wcmlib/util/YUIConnection.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
<script language="javascript" src="metaviewdata_addedit.js" type="text/javascript"></script>
</head>

<body>
<%if(nObjectId > 0){%>
<script>
    LockerUtil.register(<%=nObjectId%>,<%=MetaViewData.OBJ_TYPE%>,false);
</script>
<%}%>
<div name="objectForm" id="objectForm" class="objectForm_div" onsubmit="return false;">
    <div id="objectContainer">
        <%@include file="metaviewdata_addedit_include.jsp"%>        
        <div class="row" <%=(metaView.getPropertyAsBoolean("HIDDENFILEAPPENDIX", false) || metaView.getPropertyAsBoolean("HIDDENIMGAPPENDIX", false) || metaView.getPropertyAsBoolean("HIDDENLINKAPPENDIX", false))?"style='display:none'":""%>>
            <span class="label" WCMAnt:param="metaviewdata_addedit.jsp.appendixMgr">附件管理：</span>
            <span class="value">
                <div id="releatedAppendix" class="releatedAppendix" clickFn="dealWithAppendix"></div>
            </span>
        </div>
        <div class="row">
            <input type="hidden" name="param_tempid_leafgray" id="param_tempid_leafgray" value="<%=nDocTemplateId%>">
            <span class="label" WCMAnt:param="metaviewdata_addedit.jsp.templateSel">选择模板：</span>
            <span class="value">
                <IMG border="0" style="cursor:pointer;" align="absmiddle" title="设置模板" WCMAnt:paramattr="title:metaviewdata_addedit.jsp.templateSet" src="../images/icon/TempSelect.gif" clickFn="selTemplate">
                <span id="spDetailTemp">
                    <span class="attr_suggestion tempEntity" style="width:130px;overflow:hidden;"><%=sDocTemplateName%></span>
                </span>
            </span>
        </div>
        
        <%
            IDocumentService currDocumentService = (IDocumentService)DreamFactory.createObjectById("IDocumentService");
            //置顶信息
            boolean bIsCanTop = false;//是否在当前栏目有置顶权限
            //有修改文档的权限时才可做置顶设置
            bIsCanTop = DocumentAuthServer.hasRight(loginUser, channel, document, WCMRightTypes.DOC_EDIT);
            boolean bTopped = false;//是否置顶
            boolean bTopForever = false;//是否永久置顶
            CMyDateTime dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
            if(nObjectId>0){
                dtTopInvalidTime = currDocumentService.getTopTime(document, channel);
                bTopped = currDocumentService.isDocumentTopped(document, channel);
                if(bTopped && dtTopInvalidTime == null)
                    bTopForever = true;
                if(dtTopInvalidTime == null){
                    dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
                }
            }
            String sTopInvalidTime = dtTopInvalidTime.toString("yyyy-MM-dd HH:mm");
            Documents toppedDocuments = null;
            if(bIsCanTop && channel != null) {
                WCMFilter filter = new WCMFilter("", "DOCORDERPRI>0", "", "DocId, DocTitle, DocChannel");
                IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
                toppedDocuments = currChannelService.getDocuments(channel, filter);
            }
        %>
        <div class="row" style="display:<%=bIsCanTop?"":"none"%>;">
            <div class="attr_row" style="height:24px;line-height:24px;">
                <span class="attr_name" style="width:100px;font-weight:bold;font-size:12px" WCMAnt:param="metaviewdata_addedit.jsp.topSet_a">置顶设置：</span>
            </div>
            <div class="attr_row" style="padding-left:2px;overflow:visible;height:auto;">
                <div class="topset_row" _action="topset">
                    <input type="radio" id="pri_set_0" name="TopFlag" value="0">
                    <label for="pri_set_0" WCMAnt:param="metaviewdata_addedit.jsp.noSet">不置顶</label>
                </div>
                <div class="topset_row" _action="topset">
                    <input type="radio" id="pri_set_2" name="TopFlag" value="2">
                    <label for="pri_set_2" WCMAnt:param="metaviewdata_addedit.jsp.topForEver">永久置顶</label>
                </div>
                <div class="topset_row">
                    <span _action="topset">
                        <input type="radio" id="pri_set_1" name="TopFlag" value="1">
                        <label for="pri_set_1" WCMAnt:param="metaviewdata_addedit.jsp.topTimeVal">限时置顶</label>
                    </span>
                    <span id="pri_set_deadline" style="display:<%=(!bTopped || bTopForever)?"none":""%>">
                        <span>
                            <script>                    
                                TRSCalendar.render({
                                    id : 'TopInvalidTime',
                                    value : "<%=sTopInvalidTime%>",
                                    timeable : true,
                                    sumbit : true                               
                                });
                            </script>
                        </span>
                    </span>
                </div>
            </div>
            <br/>
            <div class="attr_row" id="topset_order" style="display:<%=(!bTopped)?"none":""%>">
                <div class="attr_row" style="height:24px;line-height:24px;">
                    <span class="attr_name" style="width:100px;font-weight:bold;font-size:12px" WCMAnt:param="metaviewdata_addedit.jsp.topOrder_a">置顶排序：</span>
                </div>
                <div class="attr_row" style="overflow:visible;height:auto;">
                    <span class="attr_table" id="topset_order_table">
                        <table border=0 cellspacing=1 cellpadding=0 style="width:100%;table-layout:fixed;background:gray;">
                        <thead>
                            <tr bgcolor="#CCCCCC" align=center valign=middle>
                                <td width="32" WCMAnt:param="metaviewdata_addedit.jsp.order">序号</td>
                                <td WCMAnt:param="metaviewdata_addedit.jsp.docTitle">文档标题</td>
                                <td width="40" WCMAnt:param="metaviewdata_addedit.jsp.listOrder">排序</td>
                            </tr>
                        </thead>
                        <tbody id="topset_order_tbody">
                        <%
                            if(toppedDocuments==null||toppedDocuments.size()==0){
                        %>
                            <tr bgcolor="#FFFFFF" align=center valign=middle>
                                <td>&nbsp;</td>
                                <td align=left>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                        <%
                            }else{
                                for(int i=0, n=toppedDocuments.size(); i<n; i++){
                                    Document topDoc = (Document)toppedDocuments.getAt(i);
                                    if(topDoc==null)continue;
                                    int nTopDocId = topDoc.getId();
                                    String sDocTitle = CMyString.truncateStr(topDoc.getTitle(), 55, "...");
                                    String sDocTitle2 = PageViewUtil.toHtmlValue(topDoc.getTitle());
                                    String sDocTitle3 = PageViewUtil.toHtmlValue(sDocTitle);
                                    if(nTopDocId!=nObjectId){
                        %>
                            <tr bgcolor="#FFFFFF" align=center valign=middle _docid="<%=nTopDocId%>" _doctitle="<%=sDocTitle3%>">
                                <td><%=i+1%></td>
                                <td align=left title="<%=nTopDocId%>-<%=sDocTitle2%>"><div style="overflow:hidden"><%=sDocTitle%></div></td>
                                <td>&nbsp;</td>
                            </tr>
                        <%
                                        continue;
                                    }//end if
                        %>
                            <tr bgcolor="#FFFFCF" align=center valign=middle _currdoc="1">
                                <td><%=i+1%></td>
                                <td align=left style="color:red;" WCMAnt:param="metaviewdata_addedit.jsp.currDocument">--当前文档--</td>
                                <td>
                                    <span class="topset_up" title="上移" _action="topsetUp" WCMAnt:paramattr="title:metaviewdata_addedit.jsp.upper">&nbsp;</span>
                                    <span class="topset_down" title="下移" _action="topsetDown" WCMAnt:paramattr="title:metaviewdata_addedit.jsp.lower">&nbsp;</span>
                                </td>
                            </tr>
                        <%
                                }//end for
                            }
                        %>
                        </tbody>
                        </table>
                    </span>
                </div>
            </div>
        </div>
        
        <%if(nChannelId == 0 && oChannelsOfMetaView != null && oChannelsOfMetaView.size() > 0){%>
        <div class="row">
            <span class="label" WCMAnt:param="metaviewdata_addedit.jsp.docchannel">存放栏目：</span>
            <span class="value">
                <select name="channelIdOfMetaView" id="channelIdOfMetaView" ignore="true">
                <%
                    for (int i = 0, size = oChannelsOfMetaView.size(); i < size; i++) {
                        Channel oChannel = (Channel) oChannelsOfMetaView.getAt(i);
                        if (oChannel == null) continue;
                %>
                    <option value="<%=oChannel.getId()%>"><%=oChannel.getDesc()%>[<%=oChannel.getId()%>]</option>
                <%}%>
                </select>
            </span>
        </div>
        <%}%>
    </div>
    <div id="validTip" class="validTip"></div>  
</div>
<textarea style="display:none" id="appendix_<%=Appendix.FLAG_DOCAPD%>">
<%=getAppendixsXml(document,Appendix.FLAG_DOCAPD)%>
</textarea>
<textarea style="display:none" id="appendix_<%=Appendix.FLAG_DOCPIC%>">
<%=getAppendixsXml(document,Appendix.FLAG_DOCPIC)%>
</textarea>
<textarea style="display:none" id="appendix_<%=Appendix.FLAG_LINK%>">
<%=getAppendixsXml(document,Appendix.FLAG_LINK)%>
</textarea>
<script>
function $F(el){
    el = $(el);
    var tagNm = el.tagName, eleType = (el.type||'').toLowerCase();
    if(tagNm=='INPUT' &&
        (eleType=='checkbox' || eleType=='radio')){
        return el.getAttribute('isboolean', 2) ?
                (el.checked?1:0) : (el.checked?el.value:null);
    }
    return el.value;
}


PgC.IsCanTop = <%=bIsCanTop%>;
PgC.TopFlag = !<%=bTopped%> ? 0 : (!<%=bTopForever%> ? 1 : 2);
$('pri_set_' + PgC.TopFlag).checked = true;

</script>
</body>
</html>