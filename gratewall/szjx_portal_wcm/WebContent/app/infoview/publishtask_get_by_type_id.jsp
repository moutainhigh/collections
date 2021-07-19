<%
/** Title:          publishtask_show.jsp
 *  Description:
 *      WCM5.2 发布任务详细信息显示页面
 *  Copyright:      www.trs.com.cn
 *  Company:        TRS Info. Ltd.
 *  Author:         WSW
 *  Created:        2004/11/19
 *  Vesion:         1.0
 *  Last EditTime:  2004/11/19 /2004/11/25  
 *  Update Logs:
 *      WSW@2004-11-25 修改头部标注。
 *      wenyh@20060316,对删除的栏目/站点进行判断
 *  Parameters:
 *      see publishtask_show.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.common.publish.PublishConstants" %>
<%@ page import="com.trs.components.common.publish.domain.taskdispatch.IPublishProgressMonitor" %>
<%@ page import="com.trs.components.common.publish.domain.taskdispatch.PublishTaskDispatcherImpl" %>
<%@ page import="com.trs.components.common.publish.persistent.taskdispatch.PublishErrorLog" %>
<%@ page import="com.trs.components.common.publish.persistent.taskdispatch.PublishErrorLogs" %>
<%@ page import="com.trs.components.common.publish.persistent.taskdispatch.PublishTask" %>
<%@ page import="com.trs.components.common.publish.persistent.taskdispatch.PublishTasks" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IPublishService" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="java.util.Iterator"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%!
private boolean idInString(int _nId, String _strIds) {
    if(_strIds == null)
        return false;
    String[] arIds = _strIds.split(",");
    int nId = 0;
    for(int i=0; i<arIds.length; i++) {
        try {
            nId = Integer.parseInt(arIds[i]);
        } catch(NumberFormatException ex){
            continue;
        }
        if(nId == _nId)
            return true;
    }
    return false;
}
%>
<%
//4.初始化(获取数据)
    int nFolderType = currRequestHelper.getInt("FolderType", 0);
    int nFolderId = currRequestHelper.getInt("FolderId", 0);
    int nChannelId = currRequestHelper.getInt("ChannelId", 0);
    int nTaskIndex = currRequestHelper.getInt("TaskIndex", 0);
//5.权限校验
    BaseChannel currBaseChannel = null;
    if(nFolderType == WebSite.OBJ_TYPE) {
        currBaseChannel = WebSite.findById(nFolderId);
        if(!AuthServer.hasRight(loginUser, currBaseChannel, WCMRightTypes.SITE_PUB_INC)){
            throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,  CMyString.format(LocaleServer.getString("get_by_type_id.station.not.found","对不起！您没有查看站点[{0}]发布历史的权限！"),new String[]{currBaseChannel.getDesc()}));
        }
        
        if(currBaseChannel == null){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("get_by_type_id.station_id.not.found","没有找到[id={0}]的站点!"),new int[]{nFolderId}));
        }
        
        if(currBaseChannel.isDeleted()){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("get_by_type_id.channel.hasBeenDeleted","{0}已被删除!请刷新您的栏目树."),new Object[]{currBaseChannel}));
        }
    } else if(nFolderType == Channel.OBJ_TYPE) {
        currBaseChannel = Channel.findById(nFolderId);
        if(!AuthServer.hasRight(loginUser, currBaseChannel, WCMRightTypes.CHNL_PUB_INC)){
            throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("get_by_type_id.channel.noRight", "对不起！您没有查看栏目[{0}]发布历史的权限！"),new String[]{currBaseChannel.getDesc()}));
        }
        
        if(currBaseChannel == null){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("get_by_type_id.channel.notFound","没有找到[id={0}]的栏目!"),new int[]{nFolderId}));
        }
        
        if(currBaseChannel.isDeleted()){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("get_by_type_id.channel.hasBeenDeleted","{0}已被删除!请刷新您的栏目树."),new Object[]{currBaseChannel}));
        }
    } else {
        Document currDoc=Document.findById(nFolderId);
        if(!AuthServer.hasRight(loginUser, currDoc, WCMRightTypes.DOC_PUBLISH)){
            throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("get_by_type_id.document.noRight", "对不起！您没有查看文档[{0}]发布历史的权限！"),new String[]{currDoc.getTitle()}));
        }
    }

//6.业务代码
    //拼凑Where语句
    boolean bNeedCheck = false;
    String strWhere = "";
    if(nFolderType == Document.OBJ_TYPE) {
        strWhere = "CONTENTTYPE=" + nFolderType + " AND CONTENTIDS like '%" + nFolderId + "%'";
        if(nChannelId > 0) {
            strWhere += " AND FolderType=" + Channel.OBJ_TYPE + " AND FolderId=" + nChannelId;
            bNeedCheck = true;
        }
    } else {
        strWhere = "FolderType=" + nFolderType + " AND FolderId=" + nFolderId;
    }

    WCMFilter filter = new WCMFilter("", strWhere, "CrTime desc");

    IPublishService currPublishService = (IPublishService)DreamFactory.createObjectById("IPublishService");
    PublishTasks currPublishTasks = null;
    if(loginUser.isAdministrator()) {
        currPublishTasks = currPublishService.getAllTasks(filter);
    } else {
        currPublishTasks = currPublishService.getMyTasks(filter);
    }

    java.util.List lstPublishTasks = new java.util.ArrayList();
    PublishTask tmpPublishTask = null;
    for(int i=0; i<currPublishTasks.size(); i++) {
        tmpPublishTask = (PublishTask)currPublishTasks.getAt(i);
        if(tmpPublishTask == null)
            continue;
        if(bNeedCheck) {
            if(!idInString(nFolderId, tmpPublishTask.getContentIds())) {
                continue;
            }
        }
        lstPublishTasks.add(tmpPublishTask);
    }
    currPublishTasks.clear();
    
    PublishTask currPublishTask = null;
    if(lstPublishTasks.size() > nTaskIndex) {
        currPublishTask = (PublishTask)lstPublishTasks.get(nTaskIndex);
    }
//7.结束
    out.clear();
%>

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="publishtask_get_by_type_id.jsp.wcmpubdetail">TRS WCM 发布详细信息:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<style type="text/css">
    body, table, tr, td, div, iframe{
    scrollbar-3dlight-color:B4B4B4;
    scrollbar-arrow-color:333333;
    scrollbar-base-color:EEEEEE;
    scrollbar-darkshadow-color:FFFFFF;
    scrollbar-face-color:eeeeee;
    scrollbar-highlight-color:FFFFFF;
    scrollbar-shadow-color:B4B4B4;
}
BODY {
    MARGIN-TOP: 0px;
    MARGIN-left: 0px;
    MARGIN-right: 0px;
    MARGIN-bottom: 0px;
    FONT-SIZE: 12px;
    BACKGROUND: #EDEDED;
    color: #000000;
}
TABLE{
    font-size:9pt;
}
.tanchu_content_td {
    padding-left: 10px;
    padding-right: 10px;
    padding-top: 10px;
    padding-bottom: 10px;
}
.tanchu_content {
    background-color: #Ffffff;
    border: 1px solid #A6A6A6;
    font-size: 12px;
    text-decoration: none;
}
.list_table{
    background-color: #a6a6a6;
}
</style>
</head>
<BODY  topmargin="0" leftmargin="0" style="overflow:hidden;">
<div style="height:100%;width:100%;overflow:auto;">
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
  <TD align="center" valign="top" class="tanchu_content_td">
  <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
  <TR>
    <TD>
    <TABLE width="100%" border="0" cellpadding="10" cellspacing="1" class="list_table">
<%
if(currPublishTask != null) {
%>
    <TR>
      <TD height="150" valign="top" bgcolor="#FFFFFF">
        
      <TABLE width="100%" border="0" cellpadding="2" cellspacing="1" class="list_table">
<%
try{
%>
      <TR bgcolor="#F6F6F6">
    <TD width="80" bgcolor="#F6F6F6" NOWRAP WCMAnt:param="publishtask_get_by_type_id.jsp.pubtask"> 发布任务</TD> 
    <TD width="610" bgcolor="#F6F6F6">&nbsp;<%=PageViewUtil.toHtml(currPublishTask.getTitle())%></TD>
      </TR>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="type_id.currState"> 当前状态</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(LocaleServer.getString("publish.label.task_status_"+currPublishTask.getStatus(), "UNKNOWN"))%></TD>
      </TR>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="publishtask_get_by_type_id.jsp.startposition"> 起始位置</TD> 
    <TD>&nbsp;<%=WCMTypes.getObjName( currPublishTask.getFolderType(),true )%>
        &nbsp;<%=currPublishTask.getFolderId()%>:&nbsp;&nbsp;
        <%=getHostFullPath(currPublishTask.getFolderType(), currPublishTask.getFolderId())%>
    </TD>
      </TR>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="publishtask_get_by_type_id.jsp.pubtype"> 发布类型</TD> <TD>&nbsp;<%=PublishConstants.getPublishTypeName(currPublishTask.getPublishType(),"unknown")%></TD>
      </TR>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="publishtask_get_by_type_id.jsp.pageaddr"> 页面地址</TD> <TD>
    <%
        String[] arTaskURLs = currPublishTask.getURLs();
        if(arTaskURLs != null){
            for(int i=0; i<arTaskURLs.length; i++){
                out.println("&nbsp;<a target=_blank href=\""+arTaskURLs[i]+"\">"+arTaskURLs[i]+"</a><BR>\n");
            }
        }
    %>
    </TD>
      </TR>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="publishtask_get_by_type_id.jsp.submituser"> 提交用户</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currPublishTask.getCrUserName())%></TD>
      </TR>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="publishtask_get_by_type_id.jsp.submittime"> 提交时间</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currPublishTask.getCrTime().toString("yy-MM-dd HH:mm:ss"))%></TD>
      </TR>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="publishtask_get_by_type_id.jsp.starttime"> 开始时间</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currPublishTask.getStartTime().toString("yy-MM-dd HH:mm:ss"))%></TD>
      </TR>
<%
    CMyDateTime     oTempDate   = currPublishTask.getEndTime();
%>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="publishtask_get_by_type_id.jsp.endtime"> 结束时间</TD> <TD>&nbsp;<%= oTempDate == null ? "" : PageViewUtil.toHtml(oTempDate.toString("yy-MM-dd HH:mm:ss")) %></TD>
      </TR>
<%
    if (currPublishTask.getPublishType() != PublishConstants.PUBLISH_RECALL)
    {
%>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="type_id.pubPage"> 发布页面</TD> <TD>&nbsp; <%=CMyString.format(LocaleServer.getString("type_id.xilan","细览:{0}"),new Object[]{String.valueOf(currPublishTask.getDetailPageCount())})%>;&nbsp;&nbsp;&nbsp; <%=CMyString.format(LocaleServer.getString("type_id.gailan","概览: {0}"),new Object[]{String.valueOf(currPublishTask.getOutlinePageCount())}) %></TD>
      </TR>
<%
    }
%>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="publishtask_get_by_type_id.jsp.totalusetime"> 共计用时</TD> <TD>&nbsp;<%= CMyDateTime.formatTimeUsed(currPublishTask.getTimeUsed()) %></TD>
      </TR>
      <TR bgcolor="#F6F6F6">
    <TD WCMAnt:param="publishtask_get_by_type_id.jsp.detaildesc"> 详细描述 </TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currPublishTask.getDesc())%></TD>
      </TR>
<%
    if (currPublishTask.getStatus() == PublishConstants.TASK_STATUS_RUNNING)
    {
        PublishTaskDispatcherImpl   dispatcher = (PublishTaskDispatcherImpl) DreamFactory.createObjectById("IPublishTaskDispatcher");
        Iterator monitors = dispatcher.getRunningMonitors(currPublishTask.getId());
        if (monitors != null)
        {
%>
  <TR bgcolor="#F6F6F6">
    <TD colspan="2">
<%
            while (monitors.hasNext())
            {
                IPublishProgressMonitor monitor = (IPublishProgressMonitor) monitors.next();
                if (monitor != null)
                {
%>
      <hr size="1">
      <b WCMAnt:param="publishtask_get_by_type_id.jsp.thread">线程:</b><%= monitor.getId() %><br>
<%
                    Iterator messages   = monitor.buffer();
                    while (messages.hasNext())
                    {
%>
      <%= messages.next() %><br>
<%
                    }
                }
            }
%>
    </TD>
  </TR>
<%
        }
    }
%>

<%
    if( currPublishTask.getStatus() != PublishConstants.TASK_STATUS_FINISHED ){
%>
    <TR bgcolor="#F6F6F6">
    <TD colspan="2">
<%
        PublishErrorLogs logs = null;
        PublishErrorLog  aLog = null;
        try{
            logs = currPublishTask.getErrorLogs(loginUser, null);
            if(logs != null){
                for( int i=0; i<logs.size(); i++ ){
                    aLog = (PublishErrorLog)logs.getAt(i);
                    if( aLog==null ) continue; 
     %>
            <hr size="1">
            <% if( aLog.getResult()==PublishConstants.TASK_STATUS_FINISHED_WITHWARNINGS ){ %>
                <font color="#DDDD00"><b WCMAnt:param="publishtask_get_by_type_id.jsp.warning">警告:</b></font>
            <% }else{ %>
                <font color="red"><b WCMAnt:param="publishtask_get_by_type_id.jsp.error">错误:</b></font>
            <% } %>
            &nbsp;<%=aLog.getDesc()%> &nbsp;&nbsp;[<%=aLog.getCrTime().toString("yy-MM-dd HH:mm")%>]
            <br>
            <%=CMyString.transDisplay(aLog.getErrorDetail())%>
     <%
                }//endfor
            }//end if
        } catch( Exception ex ) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,LocaleServer.getString("type_id.pubFaile","提取并显示发布任务错误日志失败!"),ex);
        } finally {
            if( logs!=null ) logs.clear();
        }

        //why@2004-12-2 增加Task本身的错误信息显示
         String sTaskError = currPublishTask.getAnalyzeError();
         if( sTaskError!=null && sTaskError.length()>0 ){
             out.println("<hr size=\"1\">");
             out.println("<font color=\"red\"><b>"+LocaleServer.getString("type_id.error","错误:")
                 +"</b></font>");
             out.println(CMyString.transDisplay(sTaskError));
         }
    }//endif
 %>
    </TD>
      </TR>
      <!--~ END ROW24 ~-->
<%
}catch(Exception ex){
  ex.printStackTrace();
  throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID,LocaleServer.getString("type_id.attribute.wrong" ,"获取发布任务属性值不正确而失败中止！"), ex);
}
%>
      <!--~ END ROW34 ~-->
      </TABLE>
      <!--~ END TABLE13 ~-->
      </TD>
    </TR>
<%
} else {
%>
      <TR>
      <TD height="150" valign="top" bgcolor="#FFFFFF" WCMAnt:param="type_id.noHistory">
      无发布历史
      </TD>
      </TR>
<%
}
%>
    <!--~ END ROW14 ~-->
    </TABLE>
    
    <!--~ END TABLE11 ~-->
    </TD>
  </TR>
  <!--~ END ROW13 ~-->
  <!--~-- ROW35 --~-->
<%if(lstPublishTasks.size() > 0) {%>
  <TR>
    <TD align='right' height='1'>
    <font color='#104194'>
     <%=CMyString.format(LocaleServer.getString("type_id.every","第({0})条"),new int[]
    {nTaskIndex + 1})%>  &nbsp;&nbsp;
    <%=CMyString.format(LocaleServer.getString("type_id.total","共({0})条"),new int[]{lstPublishTasks.size()})%> 
    &nbsp;&nbsp;&nbsp;
    <%if(nTaskIndex != 0) {%>
    <a href='?FolderType=<%=nFolderType%>&FolderId=<%=nFolderId%>&TaskIndex=0'><font color='#104194' WCMAnt:param="publishtask_get_by_type_id.jsp.first">第一条</font></a>
    <a href='?FolderType=<%=nFolderType%>&FolderId=<%=nFolderId%>&TaskIndex=<%=nTaskIndex - 1%>'><font color='#104194' WCMAnt:param="type_id.jsp.previous">上一条</font></a>
    <%}%>
    <%if(nTaskIndex != lstPublishTasks.size() - 1){%>
    <a href='?FolderType=<%=nFolderType%>&FolderId=<%=nFolderId%>&TaskIndex=<%=nTaskIndex + 1%>'><font color='#104194' WCMAnt:param="type_id.jsp.next">下一条</font></a>
    <a href='?FolderType=<%=nFolderType%>&FolderId=<%=nFolderId%>&TaskIndex=<%=lstPublishTasks.size() - 1%>'><font color='#104194' WCMAnt:param="type_id.jsp.last">最后一条</font></a>
    <%}%>
    </font>
    &nbsp;&nbsp;&nbsp;
    </TD>
  </TR>
<%}%>
  <!--~ END ROW35 ~-->
  </TABLE>
  <!--~ END TABLE4 ~-->
  </TD>
</TR>
<!--~- END ROW5 -~-->
</TABLE>
</div>
</BODY>
<script language="javascript">
<!-- 
    function onOk(){
        window.close();
    }
//-->
</script>
</HTML>

<%!
    private String getHostFullPath(int _nType, int _nId) throws Exception{
        if( _nType==WebSite.OBJ_TYPE ){
            WebSite site = WebSite.findById(_nId);
            return site==null?(LocaleServer.getString("type_id.unknow","未知")):site.getDesc();
        }

        //else
        Channel channel = Channel.findById(_nId);
        if( channel==null ) return LocaleServer.getString("type_id.unknow","未知");
        //else
        WebSite site = channel.getSite();
        String  sPath = null;
        do{
            sPath = channel.getName() + sPath==null?"":("&nbsp;&gt;&gt;&nbsp;"+sPath);
            channel = channel.getParent();
        }while(channel!=null);
        return (site==null?LocaleServer.getString("type_id.unknowSite","未知站点"):(site.getDesc()
            +"&nbsp;&gt;&gt;&nbsp;"+sPath));
    }
%>