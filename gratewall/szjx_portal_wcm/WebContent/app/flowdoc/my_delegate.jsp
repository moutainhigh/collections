<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.process.engine.WorkflowDelegate" %>
<%@ page import="com.trs.cms.process.engine.WorkflowDelegates" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../app/include/public_processor.jsp"%>
<% 
    //01 获取当前操作用户，声明相关参数
    int userId = loginUser.getId();
    int nEnable = 0;
    String sUserNames = "";
    String sUserIds = "";
    int nObjectId = 0;
    
    //02 构造服务相关的参数
    String sServiceId = "wcm61_workflowdelegate", sMethodName = "getDelegateByUser";
    HashMap parameters = new HashMap();
    parameters.put("CurrUserId",String.valueOf(userId));

    //03 执行服务，获取委托对象
    WorkflowDelegate delegate = (WorkflowDelegate) processor.excute(sServiceId,sMethodName,parameters);
    //04 假如获取到委托对象，则给参数赋值，对页面进行初始化
    if(delegate != null){
        nObjectId = delegate.getId();
        nEnable = delegate.isIsEnable()?1:0;
        sUserIds = delegate.getDelegateUserIds();
        //out.println(nObjectId+"开关开启状态："+delegate.isIsEnable()+"委托ids："+sUserIds);
        if(!CMyString.isEmpty(sUserIds)){
            Users users = Users.findByIds(loginUser, sUserIds);
            StringBuffer sb = new StringBuffer();
            for (int i = 0, nSize = users.size(); i < nSize; i++) {
                User user = (User) users.getAt(i);
                if (user == null)
                    continue;
                String tempName = user.getName();
                sb.append(","+tempName);
            }
            if(sb.length() > 0){
                sb.deleteCharAt(0);
                sUserNames = sb.toString();
            }
        }
    }
    
%>
<%out.clear();
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 委托新建/修改页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/locale/cn.js" WCMAnt:locale="../js/locale/$locale$.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<!--ajax相关的js-->
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<!--ajax相关的js-->
<!--floatpanel-->
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<style type="text/css">
    body{
        margin:0 auto;
        background-color:white;
        font-size:12px;
    }
    .row{
        height:30px;
    }
    #switch{
        
    }
    #switchBtn{
        width:80px;
        height:20px;
        cursor:pointer;
    }
    .switchBtnClose{
        background:url(../images/close.gif) no-repeat center center;
    }
    .switchBtnOpen{
        background:url(../images/open.gif) no-repeat center center;
    }
    .selUserBtnClose{
        background:url(../images/unselect.gif) no-repeat center center;
        cursor:default;
    }
    .selUserBtnOpen{
        background:url(../images/select.gif) no-repeat center center;
        cursor:pointer;
    }
    .label{
        width:80px;
        text-align:right;
        float:left;
        margin-top:5px;
        height:20px;
        line-height:20px;
    }
    .value{
        margin-left:90px;
        position:relative;
        overflow:auto;
        width:210px;
        word-wrap:break-word;
    }
    #selUserBtn{
        width:80px;
        height:30px;
    }
</style>
</HEAD>
<body>
    <div style="text-align:center;margin:20px 0px 30px 0px;font-size:18px;font-weight:bold;">
        工作流委托设置
        <div style="border:1px solid #f1f0f0"></div>
    </div>
    <div id="switch" class="row">
        <div class="label">开关状态：</div>
        <div id="switchBtn" class="value switchBtnClose">
        </div>
    </div>
    <div id="selUser" class="row">
        <div class="label">选择用户：</div>
        <div id="selUserBtn" class="value selUserBtnClose">
        </div>
    </div>
    <div  class="row">
        <div class="label">已选用户：</div>
        <div id="userInfo" class="value" style="padding-bottom:5px;padding-top:5px;height:110px;"><%= sUserNames%></div>
    </div>
    <div style="position:absolute;bottom:5px;left:15px;right:15px;color:gray;">
        委托功能是用来改变工作流节点处理人的一个功能，通过设置委托用户，可将流转文档转交给委托用户处理。
    </div>
    <input type="hidden" name="" id="sUserIds" value="<%=sUserIds%>" />
    <input type="hidden" name="" id="sUserNames" value="<%=sUserNames%>" />
<script language="javascript">
<!--
//参数IsEnable，CurrUserId，DelegateUserIds,ObjectId
    
    var m_nObjectId = parseInt("<%=nObjectId%>");
    var m_nSwitchState = parseInt("<%=nEnable%>");
    Event.observe($('switchBtn'),'click',switchStatue);
    Event.observe($('selUserBtn'),'click',selUserFn);
    function init(){
        var selUserBtn = $('selUserBtn');
        var switchBtn = $('switchBtn');
        //在用户第一次新建委托的时候，开关默认处于关闭状态
        if(m_nSwitchState > 0){
            changeBgImg(switchBtn,"switchBtnClose","switchBtnOpen");
            changeBgImg(selUserBtn,"selUserBtnClose","selUserBtnOpen");
        }
    }
    /*改变元素样式*/
    function changeBgImg(e,cls1,cls2){
        if(Element.hasClassName(e,cls1)){
            Element.removeClassName(e,cls1);
        }
        Element.addClassName(e,cls2);
    }

    //点击开关按钮时的事件处理函数
    function switchStatue(){
        /*1 切换开关状态*/
        //var switchState = (m_nSwitchState > 0)?true:false;
        if(m_nSwitchState == 0){
            m_nSwitchState = 1;
        }else{
            m_nSwitchState = 0;
        }
        //switchState = !switchState;
        //1.3 发送ajax请求保存数据
        BasicDataHelper.call("wcm61_workflowdelegate","setEnableDelegate", {ObjectId:m_nObjectId,IsEnable:m_nSwitchState},false,function(_trans){
            //假如保存成功，则切换开关按钮和选择用户按钮状态
            //改变按钮背景
            var m_nObjectId = Ext.result(_trans);
            var selUserBtn = $('selUserBtn');
            var switchBtn = $('switchBtn');
            if(m_nSwitchState > 0){
                changeBgImg(switchBtn,"switchBtnClose","switchBtnOpen");
                changeBgImg(selUserBtn,"selUserBtnClose","selUserBtnOpen");
            }else{
                changeBgImg(switchBtn,"switchBtnOpen","switchBtnClose");
                changeBgImg(selUserBtn,"selUserBtnOpen","selUserBtnClose");
            }
        });
    }

    function selUserFn(){
        if(m_nSwitchState <= 0) return;
        FloatPanel.open({
            src : "../app/auth/user_select_index.jsp?TransferName=1",
            title : '用户选择',
            dialogArguments :getArguments(),
            callback :function(args){
                var tempUserIds = args[0];
                var tempUserNames = args[1];
                //1 发送ajax请求，保存用户信息
                BasicDataHelper.call("wcm61_workflowdelegate","saveDelegateUsers", {ObjectId:m_nObjectId,DelegateUserIds:tempUserIds},false, 
                //发送请求执行成功之后执行的函数
                function(){
                    $("userInfo").innerHTML = tempUserNames.join(",");
                    $("sUserNames").value =  tempUserNames.join(",");
                    $("sUserIds").value = tempUserIds.join(",");
                });
            }
        });
    }
    //构造传入floatpanel窗口的参数
    function getArguments(){
        var sUserIds = $("sUserIds").value;
        var sUserNames = $("sUserNames").value
        if(!sUserIds){return [[],[]]}
        var item1 = [];
        item1 = sUserIds.split(",");
        var item2 = [];
        item2 = sUserNames.split(",");
        var item = [item1,item2];
        return item;
    }
</script>
</body>
</html>