<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Replace" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.infra.common.WCMTypes"%>

<%@include file="../include/public_server.jsp"%>

<%
	int nObjId = currRequestHelper.getInt("ObjectId",0);
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	Replace currReplace = null;
	if(nObjId>0){
		currReplace = Replace.findById(nObjId);
		if(currReplace == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nObjId),WCMTypes.getLowerObjName(Replace.OBJ_TYPE)}));
		}
	}else{
		currReplace = Replace.createNewInstance();
	}
	String sReplaceName = CMyString.transDisplay(currReplace.getName());
	String sReplaceContent = CMyString.transDisplay(currReplace.getContent());
	String sTitle = (nObjId>0)?LocaleServer.getString("replace_addedit.label.edit", "修改替换内容"):LocaleServer.getString("replace_addedit.label.new", "新建替换内容");
%>
<HEAD>
<TITLE><%=sTitle%></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	input{
		height:20px;
	}
</style>

</head>

<body>
<form id="replaceForm" style="display:none" onsubmit="return false;">
<table border=0 cellspacing=0 cellpadding=0 style="width:100%;font-size:12px;">
	<tr style="height:20px;">
		<td style="width:50px;" align="right" WCMAnt:param="replace_add_edit.jsp.ReplaceName">标题:</td>
        <td>
            <input type="text" name="replaceName" id="replaceName" value="<%=sReplaceName%>" validation="type:'string',max_len:'50',showid:'replaceNameValidDesc',required:'',rpc:'checkReplaceName'"><span id="replaceNameValidDesc"></span>
        </td>
	</tr>
	<tr>
		<td style="width:50px;" align="right" valign="top" WCMAnt:param="replace_add_edit.jsp.ReplaceContent">内容:</td>
        <td>
            <textarea name="replaceContent" id="replaceContent" rows="6" cols="40" validation="type:'string',max_len:'500',showid:'replaceContentValidDesc',required:''" style="border:1px solid lightblue;"><%=sReplaceContent%></textarea>
        </td>
	</tr>
</table>
<div id="replaceContentValidDesc" style="padding-left:50px;height:20px;"></div>
</form>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../js/data/locale/replace.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>

<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'saveReplace',
		name : wcm.LANG.TRUE||'确定'
	}],
	size : [500, 200]
};
</script>
<script language="javascript">
<!--	
    var objType = 105;
    var channelId = getParameter("channelid") || 0;
    var objectId = getParameter("objectid") || 0;

	//Validation的附加部分，主要用于处理多语种问题
	var m_arrValidations = [{
			renderTo : 'replaceName',
			desc : wcm.LANG.REPLACENAME||'标题'
			
		}, {
			renderTo : 'replaceContent',
			desc : wcm.LANG.REPLACECONTENT||'内容'
			
		}
	];

    if(objectId != 0){
        LockerUtil.register2(objectId, objType, true, 'saveReplace');
    }

	Event.observe(window, 'load', function(){
		initCheck();
		$('replaceForm').style.display = '';
	})

    function initCheck(){
		ValidationHelper.registerValidations(m_arrValidations);
        ValidationHelper.addValidListener(function(){
            FloatPanel.disableCommand('saveReplace', false);
        }, "replaceForm");
        ValidationHelper.addInvalidListener(function(){
            FloatPanel.disableCommand('saveReplace', true);
        }, "replaceForm");
        ValidationHelper.initValidation();
    }


    function saveReplace(){
		if(!ValidationHelper.doValid('replaceForm')){
			return false;
		}
		var oHelper = new com.trs.web2frame.BasicDataHelper();
        oHelper.Call('wcm6_replace', 'save', {
            channelid:channelId,
            ObjectId:objectId,
            replaceName: $('replaceName').value,
            replaceContent:$('replaceContent').value
        }, true, function(_trans){
			notifyFPCallback(_trans);
            FloatPanel.close();
        });   
        return false;
    }

    function checkReplaceName(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
        oHelper.Call('wcm6_replace', 'existsSimilarName', {
            channelid:channelId,
            ObjectId:objectId,
            replaceName: $('replaceName').value
         }, true, function(transport, json){
            if(com.trs.util.JSON.value(json, "result") == 'false'){
                ValidationHelper.successRPCCallBack();
            }else{
                ValidationHelper.failureRPCCallBack(wcm.LANG.REPLACE_ALERT||"标题已经存在");
            }
        });     
    }
</script>
</body>
</html>