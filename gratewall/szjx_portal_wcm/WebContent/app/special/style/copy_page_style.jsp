<%
/** Title:              view_system_skin_addedit.jsp
 *  Description:
 *        风格的编辑/新建页面。
 *  Copyright:      www.trs.com.cn
 *  Company:        TRS Info. Ltd.
 *  Author:             Archer
 *  Created:            2010年3月25日
 *  Vesion:             1.0
 *  Last EditTime   :none
 *  Update Logs:    none
 *  Parameters:     see view_system_skin_addedit.xml
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page  import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
    //获取参数
    int nSourcePageStyleId = currRequestHelper.getInt("SourcePageStyleId",0);
    
    // 获取 风格（Id:nSourcePageStyleId） 对象
    PageStyle oPageStyle = PageStyle.findById(nSourcePageStyleId); 
    if(oPageStyle == null){
        throw new WCMException(CMyString.format(LocaleServer.getString("copy_page_style.jsp.fail2get_page_style", "获取页面风格[Id:{0}]失败！"), new int[]{nSourcePageStyleId}));
    }
    boolean bHasRight = SpecialAuthServer.hasRight(loginUser, oPageStyle, SpecialAuthServer.STYLE_ADD);
    if (!bHasRight) {
        throw new WCMException(LocaleServer.getString("copy_page_style.jsp.label.have_noright2_create_style", "您没有权限新建风格！"));
    }
    String sPageStyleName = CMyString.showNull(oPageStyle.getStyleName(),"");
    String sPageStyleDesc = CMyString.showNull(oPageStyle.getStyleDesc(),"");
    int nIsImport = oPageStyle.getIsImport();
    out.clear();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title WCMAnt:param="copy_page_style.jsp.title">新建风格</title>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../js/source/wcmlib/com.trs.validator/css/validator.css"/>
    <!--基础的js-->
    <script src="../../js/easyversion/lightbase.js"></script>
    <script src="../../js/source/wcmlib/WCMConstants.js"></script>
    <script src="../../js/easyversion/extrender.js"></script>
    <script src="../../js/easyversion/elementmore.js"></script>
    <script src="../js/adapter4Top.js"></script>
    <!--使用ajax发送请求的js-->
    <script src="../../js/easyversion/ajax.js"></script>
    <script src="../../js/easyversion/basicdatahelper.js"></script>
    <script src="../../js/easyversion/web2frameadapter.js"></script>
    <!--validator start-->
    <script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
    <script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
    <script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
    <script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
    <!--validator end-->
    <style type="text/css">
        html{
            background-color: #ffffff;
            height:100%;
            width:100%;
            margin:0px;
            padding:0px;
            overflow-y:auto;
            font:normal normal normal 14px/20px "宋体",Arial;
            color:#000000;
            scrollbar-face-color:#f6f6f6;
            scrollbar-highlight-color:#ffffff;
            scrollbar-shadow-color:#cccccc;
            scrollbar-3dlight-color:#cccccc;
            scrollbar-arrow-color:#330000;
            scrollbar-track-color:#f6f6f6;
            scrollbar-darkshadow-color:#ffffff;
        }
        body{
            overflow-y:hidden;
        }
        .info_content{
            margin:0px;
            width:100%;
            border:1px solid #acddfd;
        }
        .info_content input{
            width:200px;
        }
        a{
            font:normal normal normal 12px/18px "宋体",Arial;
        }
        a:link,a:visited,a:active{
            color: #228fb7;
            text-decoration: none;
        }
        a:hover{
            color: #ff8010;
            text-decoration: underline;
        }
        input{
            vertical-align:middle;
            width:200px;
            height:18px;
            border:1px solid #b4b4b4;
        }
        td{
            padding:3px 0px;
            vertical-align:top;
            height:24px;
        }
        .info_content{
            margin:0px 0px 0px 0px;
            width:100%;
        }
        .title_td{
            width:75px;
            text-align:center;
            padding-top:5px;
        }
        .sc {
            font-family: "宋体";
            font-size: 12px;
            color: #333333;
            text-decoration: none;
            line-height: 18px;
            font-weight: normal;
        }
        .sc1 {
            font-family: "宋体";
            font-size: 12px;
            color: #0063B8;
            text-decoration: none;
            line-height: 18px;
            font-weight: normal;
        }
        .msg{
            height:20px;
        }
    </style>
    <script type="text/javascript">
    <!--
        // 定义 crashboard 的内容，标题、尺寸、按钮
        window.m_cbCfg = {
            btns : [
                {
                    text : '确定',
                    id : 'btnSave',
                    cmd : function(){
                        // 数据校验
                        if(!ValidationHelper.doValid("frm")){
                            ValidationHelper.failureRPCCallBack();
                            return false;
                        }
                        // 获取数据
                        var eForm = $("frm");
                        var oPageStyle = {
                            Id : eForm.PageStyleId.value||0,// 风格Id
                            Name : eForm.StyleName.value||"",// 风格名称
                            Desc : eForm.StyleDesc.value||"",// 风格英文名称
                            IsImport : <%=nIsImport%>
                        };
                        var cb = wcm.CrashBoarder.get(window);
                        if($("input_name").getAttribute("NameCanUsed")=="true"){
                            cb.notify(oPageStyle);
                        }else{
                            BasicDataHelper.Call('wcm61_pagestyle', 'checkStyleName', {StyleName : $('input_name').value, objectId : 0}, true,function(_transport,_json){
                                    var bExsit = _json['RESULT'];
                                    if(bExsit == 'true'){
                                        $("input_name").setAttribute("NameCanUsed", false);
                                        ValidationHelper.failureRPCCallBack("当前的英文名字已经被使用，请重新输入！");
                                    }else{
                                        $('input_name').setAttribute("NameCanUsed", true);
                                        ValidationHelper.successRPCCallBack();
                                        cb.notify(oPageStyle);
                                    }
                            });
                        }
                        return false;
                    }
                },{
                    text : '取消',
                    extraCls : 'wcm-btn-close'
                }
            ]
        }
        function init(){
            ValidationHelper.addValidListener(function(){
                //按钮有效处理
                wcmXCom.get('btnSave').enable();
            }, "frm");
            ValidationHelper.addInvalidListener(function(){
                //按钮失效处理
                wcmXCom.get('btnSave').disable();
            }, "frm");
            ValidationHelper.initValidation();
        }
        function checkNameUsed(_sStyleName){
            var _sStyleName = $('input_name').value;
            BasicDataHelper.Call('wcm61_pagestyle', 'checkStyleName', {StyleName : _sStyleName, objectId : 0}, true, 
                function(_transport,_json){
                    var bExsit = _json['RESULT'];
                    if(bExsit == 'true'){
                        $('input_name').setAttribute("NameCanUsed", false);
                        ValidationHelper.failureRPCCallBack("当前的英文名字已经被使用，请重新输入！");
                    }else{
                        $('input_name').setAttribute("NameCanUsed", true);
                        ValidationHelper.successRPCCallBack();
                    }
                }
            );
        }
    //-->
    </script>
</head>
<body>
<form id="frm" >
    <table border=0 cellspacing=0 cellpadding=0 class="info_content">
        <tr style="display:none">
            <td class="title_td" WCMAnt:param="copy_page_style.jsp.styleId">风格ID：</td><td ><input type="text" name="PageStyleId" value="<%=nSourcePageStyleId%>"></td>
        </tr>
        <tr>
            <td class="title_td" WCMAnt:param="copy_page_style.jsp.name">名　称：</td>
            <td >
                <input type="text" name="StyleDesc" validation="desc:'名称',required:true,type:'string',showid:'desc_msg',max_len:50" value="">
                <div id="desc_msg" class="msg">&nbsp;</div>
            </td>
        </tr>
        <tr>
            <td class="title_td" WCMAnt:param="copy_page_style.jsp.en_name_a">英文名：</td>
            <td >
                <input type="text" name="StyleName" id="input_name" validation="desc:'英文名',required:true,type:'common_char',showid:'name_msg',max_len:50,rpc:'checkNameUsed'" value="" NameCanUsed="false" validation_desc="英文名"  WCMAnt:paramattr="validation_desc:copy_page_style.jsp.en_name"/>
                <div id="name_msg" class="msg">&nbsp;</div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>