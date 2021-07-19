<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@include file="../include/public_processor.jsp"%>
<%
	//接受页面参数
    int nSrcClassInfoId = processor.getParam("classinfoId",0);
	String sDocIds = processor.getParam("docIds");	
	String sServiceId = "wcm6_ClassInfoView", sMethodName = "queryClassInfos";	
	ClassInfos result = (ClassInfos) processor.excute(sServiceId,sMethodName);	
	ClassInfo currInfo = null;
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="record_moveto_cls.html.title">记录移动到...</title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metarecdata.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--ProcessBar Start-->
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<!--ProcessBar End-->
<script language="javascript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'onOk',
			name : wcm.LANG.METARECDATA_BUTTON_1 || '确定'
		}],
		size : [480, 350]
	};
</script>
<style type="text/css">
    *{
        margin:0px;
        padding:0px;
    }
    .box{
        position:relative;
        width:100%;
        height:100%;
        padding-top:28px;
    }
    .navContainer{
        position:absolute;
        left:0px;
        top:0px;
        width:100%;
        height:28px;
        border-bottom:1px solid silver;
    }
    .bodyContainer{
        width:100%;
        height:100%;
    }
    .navItem{
        width:45%;
        white-space:nowrap;
        overflow:hidden;
    }
    .ClassInfoContainer{
        position:absolute;
        width:100%;
        height:100%;
        padding-top:28px;
    }
    .tip{
        position:absolute;
        left:5px;
        top:0px;
        height:28px;
        line-height:28px;
        white-space:nowrap;
        overflow:hidden;
        color:red;
        font-weight:bold;
    }
    .row{
        height:28px;
        line-height:28px;
        width:100%;
        padding-left:8px;
        white-space:nowrap;
        overflow:hidden;
    }
</style>
<script language="javascript">
<!--
    var nSrcClassInfoId = getParameter("classInfoId") || 0;
    var sDocIds = getParameter("docIds") || 0;

    Event.observe(window, 'load', function(){
		var radioCls = $('r_' + nSrcClassInfoId);
		if(radioCls){
			radioCls.checked = true;
		}
    });

	function onOk(){
        var sSrcClassInfoIds = getSrcClassInfos();
		try{
			oResult = $("selectTree").contentWindow.getCheckedValues();
		}catch(err){
			oResult = {ids:0, names:""};
		}
        var sSelIds = oResult["ids"];
		if(!sSelIds||sSelIds.length==0) {
			Ext.Msg.alert(wcm.LANG.METARECDATA_ALERT_1 || '请选择要当前记录要移动到的目标分类!');
			return false;
		}
		else if(sSelIds.length==1){
            /*
            if(("," + sSrcClassInfoIds + ",").indexOf("," + sSelIds[0] + ",") >= 0){
    			$alert(wcm.LANG.record_moveto_cls_1000 || '不能将当前记录从当前分类移动到自身!');
	    		return false;
            }
            */
		}
        
        ProcessBar.init(wcm.LANG.METARECDATA_ALERT_2 || '执行进度');
        ProcessBar.addState(wcm.LANG.METARECDATA_ALERT_3 || '提交数据');
        ProcessBar.addState(wcm.LANG.METARECDATA_ALERT_4 || '成功执行完成');
        ProcessBar.start();
        var oPostData = {
            "ObjectIds" : sDocIds,
            "SrcClassInfoIds" : sSrcClassInfoIds,
            "DestClassInfoId" : sSelIds
        }
        var oHelper = new com.trs.web2frame.BasicDataHelper();
        oHelper.Call('wcm6_MetaDataCenter','moveViewDatas',oPostData, true,
            function(_transport,_json){
                ProcessBar.close();
				notifyFPCallback();
				FloatPanel.close();
            },
            function(_transport,_json){
                $render500Err(_transport,_json);
                FloatPanel.close();
            }
        );
        return false;
	}

    function toggle(sContainer){
        sContainer = sContainer.toUpperCase();
        var oContainer = $('bodyContainer');
        var childNodes = oContainer.childNodes;
        for(var index = 0; index < childNodes.length; index++){
            if(childNodes[index].nodeType != 1){
                continue;
            }
            var sMethod = sContainer == childNodes[index].id.toUpperCase() ? "show" : "hide";
            Element[sMethod](childNodes[index]);
        }
    }

    function getSrcClassInfos(){
        var result = [];
        var radios = document.getElementsByName("srcClassInfo");
        for(var index = 0; index < radios.length; index++){
            if(radios[index].checked){
                result.push(radios[index].value);
            }
        }
        return result.join(",");
    }
//-->
</script>
</HEAD>

<BODY>
    <div class="box">
        <div class="navContainer">
            <span class="navItem">
                <input type="radio" name="navItem" id="src_nav" onclick="toggle('srcContainer');">
                <label for="src_nav" WCMAnt:param="record_moveto_cls.jsp.srcClass">源分类：</label>
            </span>
            <span class="navItem">
                <input type="radio" name="navItem" id="dest_nav" checked onclick="toggle('destContainer');">
                <label for="dest_nav" WCMAnt:param="record_moveto_cls.jsp.targetClass">目标分类：</label>
            </span>
        </div>
        <div class="bodyContainer" id="bodyContainer">
            <div id="srcContainer" class="ClassInfoContainer" style="display:none;">
                <div class="tip" WCMAnt:param="record_moveto_cls.jsp.srcSelect">当前记录涉及到以下分类，请选择从哪些源分类进行移动：</div>
                <div id="srcClassInfoContainer" style="width:100%;height:100%;overflow:auto;">
					<%
						if(result.size() > 0){
							for(int i=0;i<result.size();i++){
								currInfo = (ClassInfo)result.getAt(i);
					%>
						<div class="row">
							<input type="checkbox" name="srcClassInfo" id="r_<%=currInfo.getId()%>" value="<%=currInfo.getId()%>" checked>
							<label for="r_<%=currInfo.getId()%>"><%=CMyString.transDisplay(currInfo.getName())%>[<%=currInfo.getId()%>]</label>
						</div>
					<%
							}
						}
					%>
				</div>
            </div>
            <div id="destContainer" class="ClassInfoContainer">
                <div class="tip" WCMAnt:param="record_moveto_cls.jsp.targetSelect">请选择当前记录要移动到的目标分类：</div>
				<iframe width='100%' height='100%' src='classinfo_select_tree2.html?objectId=<%=nSrcClassInfoId%>&treeType=2' id='selectTree' frameborder = 'no'></iframe>
            </div>
        </div>
    </div>
    <div id="divNoObjectFound" style="display:none;" WCMAnt:param="record_moveto_cls.jsp.noClass">没有找到源分类</div>
</BODY>
</HTML>