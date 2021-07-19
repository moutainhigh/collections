<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.service.IInfoViewService"%>
<%@ page import="com.trs.service.impl.InfoViewService"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.List"%>
<%@ page import="com.trs.components.infoview.persistent.InfoView"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.addHeader("Cache-Control", "no-store"); //Firefox
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", -1);
    response.setDateHeader("max-age", 0);
%>
<%
    int ownerType = currRequestHelper.getInt("OwnerType", 0);
    int ownerId = currRequestHelper.getInt("OwnerId", 0);
    int nSiteId = 0;
    int nInfoViewId = 0;
    if (ownerType == 101) {
        Channel oChannel = Channel.findById(ownerId);
        if (oChannel == null) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    CMyString.format(LocaleServer.getString(
                            "flowemployee.object.not.found.channel",
                            LocaleServer.getString("flow_addedit.jsp.label.chnlnotfind", "没有找到ID为{0}的栏目")), new int[] { ownerId }));
        }
        nSiteId = oChannel.getSiteId();
        if (nSiteId == 0) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    CMyString.format(LocaleServer.getString(
                            "flowemployee.object.not.found.site",
                            LocaleServer.getString("flow_addedit.jsp.label.sitenotfind", "没有找到ID为{0}的站点")),
                            new int[] { ownerId }));
        }
        ownerId = nSiteId;
        ownerType = 103;
        int nChnlType = oChannel.getType();
        if (nChnlType == Channel.TYPE_INFOVIEW) {
            IInfoViewService oInfoviewService = new InfoViewService();
            List oList = oInfoviewService
                    .getEmployedInfoViews(oChannel);
            InfoView infoview = null;
            if (oList != null && oList.size() > 0) {
                infoview = (InfoView) oList.get(0);
            }
            nInfoViewId = (infoview == null) ? 0 : infoview.getId();
        }
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="flow_addedit.jsp.flowedit">工作流编辑器</title>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" />
<script language="javascript">
<!--
var m_sLoginUser = '<%=loginUser.getName()%>';	
var ownerId = <%=ownerId%>;
var ownerType = <%=ownerType%>;
var infoviewId = <%=nInfoViewId%>;
//-->
</script>
<!--FloatPanel Inner Start-->
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/data/locale/flow.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<!--FloatPanel Inner End-->
<!-- CarshBoard Inner Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Inner End -->
<!-- dialog  Start -->
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT> 
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<!-- dialog  End -->
<!--my import-->
<script language="javascript" src="../system/status_create.jsp" type="text/javascript"></script>
<script src="../../app/js/source/wcmlib/com.trs.workflow/WorkFlow.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSArray.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<SCRIPT src="../../app/js/source/wcmlib/core/LockUtil.js"></SCRIPT>
<script LANGUAGE="JavaScript">
var sErrorInfo = "";
window.m_bModified = false;
var cbr = wcm.CrashBoarder.get("Dialog_Workflow_Editor");
if(cbr){
	var cb = cbr.getCrashBoard();
	if(cb){
			cb.on('beforeclose', function(){
				return BeforeUnloadSave();
				return false;
			});
	}
}

Event.observe(window, 'beforeunload', function(){
	if($('saveflowbtn'))
		BeforeUnloadSave();
}, false);

function BeforeUnloadSave(){
	try{
		if(PageContext.params['readonly']) {
				return;
		}
		if($('flowName').value.trim() == '' || byteLength($('flowName').value) > 50 || byteLength($('flowDesc').value) > 50 || checkSpecialChars($('flowName').value)){
			return;
		}
		if(window.m_bModified == false) {
			if($('flowName').value != $('flowName').getAttribute('_oldval')
				|| $('flowDesc').value != $('flowDesc').getAttribute('_oldval')) {
				window.m_bModified = true;
			}
		}
		if(window.m_bModified == false) {
				return;
		}
		if(confirm(wcm.LANG['FLOW_25'] || '当前工作流的某些属性可能已经修改,是否保存?')) {
			doSave();
			return false;
		}
		$destroy(flowEditor);
		return false;
	}catch(err){
		//just skip it
	}
}
function checkSpecialChars(_sCode) {
	var regExp = /[<>\[\]{}#*%$%&^!~\-`]/g;
	var sResult = _sCode.match(regExp)
	return TRSArray.distinct(sResult);
}
function byteLength(str){
	var length = 0;
	str.replace(/[^\x00-\xff]/g,function(){length++;});
	return str.length+length;
}
//TODO 关闭后解锁
</script>
<!--Page scope-->
<script language="javascript" src="../system/status_create.jsp" type="text/javascript"></script>
<script language="javascript" src="../infoview/infoview_config_js.jsp"></script>
<script language="javascript" src="flow_addedit.js" type="text/javascript"></script>
<script language="javascript">
	window.m_cbCfg = {
		btns : [
			{
				id : 'btnSubmit',
				hidden : getParameter('readonly') == 'true',
				text : wcm.LANG['FLOW_SAVE'] || '保存',
				cmd : function(){return doSave();}
			},
			{
				hidden : getParameter('readonly') == 'true',
				text : wcm.LANG['FLOW_CANCEL'] ||　'取消',
				cmd : function(){
					window.cancelBtnClicked = true;
				}
			}
		]
	};
</script>
<style type="text/css">
	body, table{
		font-size: 12px;
		margin: 0;
		padding: 0;
	}
	.attr_title{
		font-size: 14px;
	}
	#selInfoviews{
		width:150px;
	}

</style>
</HEAD>
<body style="background: #ffffff">
<center>
<input TYPE="hidden" name="FlowId" value="0">
<table id="tblEditorContainer" style="background: #f6f6f6;table-layout:fixed;" width="100%" height="100%" border="0" cellpadding="2" cellspacing="5" align="center">
	<tr>
		<td width="100%" valign="top" align="left" style="border-bottom: solid 1px silver;height:30px;">
			<div id="divHeader">
				<span class="attr_title" WCMAnt:param="flow_addedit.jsp.flowName">工作流名称:</span>
				<input name="flowName" class="txt_common" _oldval="" id="flowName" type="text" validation="required:'true',type:'string',max_len:'50',desc:'工作流名称'" validation_desc="工作流名称" WCMAnt:paramattr="validation_desc:flow_addedit.jsp.flowNameTitle" /><span class="spFieldReqiredTip">*</span>&nbsp;&nbsp;
				<span class="attr_title" WCMAnt:param="flow_addedit.jsp.flowDescc">工作流描述:</span>
				<input name="flowDesc" class="txt_common" _oldval="" id="flowDesc" type="text" validation="type:'string',max_len:'200',desc:'工作流描述'" validation_desc="工作流描述"　WCMAnt:paramattr="validation_desc:flow_addedit.jsp.flowNameDesc"/>
<span id="spInfoviewRel" style="display: none">
				&nbsp;&nbsp;&nbsp;
				<span class="attr_title" WCMAnt:param="flow_addedit.jsp.infoview">关联的表单:</span>
				<span id="spInfoviews" style="display: none"><select id="selInfoviews" class="txt_common"><option value="0" WCMAnt:param="flow_addedit.jsp.select">请选择</option></select></span><span id="spLoading" style="color:gray" WCMAnt:param="flow_addedit.jsp.onload"></span>
</span>
			</div>
		</td>
	</tr>
	<tr>
		<td id="tdFlowEditor" width="100%" valign="top" align="center" style="padding-top: 0px; padding-left:2px; cursor: pointer">
			<div id="divFlowEditor" style="text-align: center;height:100%;">
				<OBJECT id="flowEditor" style="width:100%;height:100%;left: 0px; top: 0px; border: 0px solid gray;" codeBase="../flow/WorkFlowEditor_zh.ocx#Version=1,0,0,25" classid="clsid:0ECFCF01-C9C8-40AF-9542-14C4EACF187D">
					<PARAM NAME="_Version" VALUE="65536">
					<PARAM NAME="_ExtentX" VALUE="21167">
					<PARAM NAME="_ExtentY" VALUE="15875">
					<PARAM NAME="_StockProps" VALUE="0">
				</OBJECT>
			</div>
		</td>
	</tr>
<%
    int isButton = currRequestHelper.getInt("ShowButtons", 0);
    int nLoadView = currRequestHelper.getInt("LoadView", 0);
    if (isButton == 1 && nLoadView == 2) {
%>
	<tr>
		<td align="center" id="ButtonContainer" style="height:25px;"><span><button type="button" id="saveflowbtn" onclick="saveFlow(false)"><span WCMAnt:param="flow_addedit.jsp.save">保存</span></button></span></td>
	</tr>
<%
    }
    if (isButton == 1 && nLoadView == 1) {
%>
	<tr>
		<td style="padding-top: 10px; font-size: 12px; color: gray; text-align: right; padding-right: 5px;height: 60px;">
		<span WCMAnt:param="flow_addedit.jsp.read">当前为查看(只读)模式</span>
		</td>
	</tr>
<%
    }
%>
</table>
</center>
<script language="javascript">
<!--
	// zxj add @ 2013-04-01 17:03 注释掉下面的事件绑定，原因是IE10在绑定了下面事件的情况下控件会被反复隐藏影响工作流编辑操作
	
	/*try{
		Event.observe(tblEditorContainer, 'deactivate', function(){
			window.setTimeout(function(){
				Element.hide('divFlowEditor');
			}, 40);
		}, false);
		Event.observe(tblEditorContainer, 'activate', function(){
			window.setTimeout(function(){
				Element.show('divFlowEditor');
			}, 40);
		}, false);
	}catch(error){
		//just skip it
	}*/
	//gfc add @ 2008-2-19 17:06 进行是否配置表单选件的判断
	try{
		if(infoview_config.enable == true) {
			Element.show('spInfoviewRel');
		}
	}catch(err){
		//just skip it
	}
//-->
</script>
</body>
</html>