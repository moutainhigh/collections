<%
/** Title:			right_set.jsp
 *  Description:
 *		WCM5.2 设置权限页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2004-12-29 13:54
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-29 / 2004-12-29	
 *	Update Logs:
 *		CH@2004-12-29 产生
 *		wenyh@2006-03-17,如果是站点/频道判断是否已删除
 *		caohui@2006-9-1 减少不必要的依赖，改为Host自己Validate
 *
 *  Parameters:
 *		see right_set.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.Rights" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.config.OperationConfig" %>
<%@ page import="com.trs.cms.auth.config.OperationRelatedConfig" %>
<%@ page import="com.trs.cms.auth.config.RightConfigServer" %>
<%@ page import="com.trs.cms.auth.domain.IRightHost" %>
<%@ page import="com.trs.cms.auth.domain.IRightMgr" %>
<%@ page import="com.trs.cms.auth.domain.RightHostFactory" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Right" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.infra.util.CMyException" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
//4.初始化(获取数据)
	int nObjType = currRequestHelper.getInt("ObjType", 0);
	String sObjIds = request.getParameter("ObjId");
	if(sObjIds == null || sObjIds.equals("0"))
		throw new WCMException("新建模式不允许查看/设置权限！Type:["+nObjType+"] Id:["+sObjIds+"]");

	String[] sObjIdArray = sObjIds.split(",");
	IRightMgr currMgr = (IRightMgr) DreamFactory.createObjectById("IRightMgr");
	Rights	currRights = null;
	IRightHost rightHost = null;
	
	boolean bHasChildren = false;
	String sHostInfo = null;
	int nObjId = 0;
	for(int i = 0; i < sObjIdArray.length; i++){//循环进行权限校验
		nObjId = Integer.parseInt(sObjIdArray[i]);
		if(nObjType != 1){
			try{
				rightHost = RightHostFactory.makeRightHostFrom(nObjType, nObjId);
				if(rightHost == null || rightHost.getId() == 0){
					throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到指定的对象！Type:["+nObjType+"] Id:["+nObjId+"]");
				}

				//权限校验
				//rightHost.validateCanDoSetRight(loginUser);

				IRightHost[] children = rightHost.getChildren();
				bHasChildren = children != null && children.length>0;
					
				currRights = currMgr.getRights(rightHost.getSubstance());
				sHostInfo = rightHost.getInfo();
			}catch(Throwable t){
				int number = 0;
				if(t instanceof CMyException){
					number = ((CMyException)t).getErrNo();
				}
%>	
				<script>
					try{
						top.FaultDialog.show({
							code		: '<%=number%>' || '',
							message		: '<%=t.getMessage()%>' || '',
							detail		: '<%=t.getMessage()%>' || ''
						}, '与服务器交互时出现错误', function(){
							top.$nav().refreshSiteType('0', null, function(){
								top.getFirstHTMLChild(top.$nav().$("r_0")).click();
							});
						});
					}catch(error){}
				</script>
<%
				return;
			}
		}else{
			//权限校验
			//if(!loginUser.isAdministrator())
			//	throw new WCMException("当前用户["+loginUser.getName()+"]没有系统属性权限进行设置！");

			//业务代码
			currRights = currMgr.getRights(1,nObjId);

			sHostInfo = "系统";
		}
	}

	String[] pRightTypeIds = rightHost.getRightTypeIds();
	String[] pRightTypeNames = rightHost.getRightTypeNames();
	

//7.结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 6.0 <%=LocaleServer.getString("auth.label.rightset", "权限设置")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<base target="_self">
<LINK href="../css/wcm52/style.css" rel="stylesheet" type="text/css">
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/auth_index.css" rel="stylesheet" type="text/css" />

<script src="../js/wcm52/CTRSHTMLElement.js"></script>
<script src="../js/wcm52/CTRSHTMLTr.js"></script>  
<script src="../js/wcm52/CTRSSimpleTab.js"></script>
<script src="../js/wcm52/CTRSArray.js"></script>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObj.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObjHelper.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></script>  

<script src="../js/wcm52/CTRSHashtable.js"></script>
<script src="../js/wcm52/CTRSRequestParam.js"></script>
<script src="../js/wcm52/CTRSAction.js"></script>

<script src="../js/com.trs.util/Common.js"></script>
<script>
	$import('com.trs.dialog.Dialog');	
	$import('com.trs.wcm.MessageCenter');	
</script>
<script src="../js/wcm52/CNewWCMRightHelper.js"></script>
<script language="javascript">
<!--
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.web2frame.AjaxRequest');
	$import('com.trs.crashboard.CrashBoard');<%-- /* 需要引入,否则单独页面出错 */--%>
    $import('com.trs.crashboard.CrashBoarder');
//-->
</script>

<script>
if((top.actualTop||top).showHideAttrPanel){
	(top.actualTop||top).showHideAttrPanel(false, false);
}

var m_nObjType = <%=nObjType%>;
var m_nObjId = <%=nObjId%>;
var needRefresh = false;
var OBJ_WEBSITE = 103;
var OBJ_CHANNEL = 101;
var OBJ_DOCUMENT = 605;
var OBJ_SYSATTRIBUTE = 1;
<% 
	int loginUserId = loginUser.getId();
	int objUserType = 204; 
%>
var OBJ_USER = <%=objUserType%>;

var m_bExtended=<%=(nObjId==2)?true:false%>;
<%
	writeRightDefsScript(out, nObjType, request.getParameter("IsVirtual"));
%>

<%
//初始化权限数据
	Right currRight = null;
	CMSObj oOperator = null;
	boolean ifBreak = false;
	for(int i=0; i<currRights.size(); i++){
		currRight = (Right)currRights.getAt(i);
		if(currRight == null)continue;

		oOperator = currRight.getOperator();
		if(oOperator == null)continue;
%>
		NewWCMRightHelper.addRight(<%=currRight.getOperatorType()%>, <%=currRight.getOperatorId()%>, "<%=CMyString.filterForJs(getOperatorDesc(oOperator))%>", "<%=currRight.getValue()%>", <%=currRight.getId()%>);	
<%
	}
%>
<%
	//if(currRights.size() == 0 && nObjType == WCMTypes.OBJ_DOCUMENT){
%>
	//NewWCMRightHelper.addRight(<%=Right.OPRTYPE_USER%>, <%=loginUser.getId()%>, "<%=CMyString.filterForJs(loginUser.getName())%>", "0", "0");		
<%
	//}
%>
	function init(){
		// ge gfc add @ 2007-4-3 17:09 如果有RotatingBar显示，先隐藏之
		if((top.actualTop || top).RotatingBar) {
			(top.actualTop || top).RotatingBar.stop();
		}
<%
for (int i = 0; i < pRightTypeIds.length; i++) {
	out.println("NewWCMRightHelper.draw(\""+pRightTypeIds[i]+"\", document.all(\"id_TRSSimpleTab"+i+"\"));");
	out.println("window._" + pRightTypeIds[i] + "_ = " + i);
}	
%>
		initTabStatus();
	}

	function initTabStatus(){//init tab is visible or hidden
		var isVirtual = getParameter("IsVirtual");
		if(isVirtual == "1"){
			var channelType = getParameter("channelType");
			if(channelType == "" || channelType == 0) return;
			var tableObj = document.getElementById("id_TRSSimpleTab").childNodes[0];
			if(!tableObj)return;
			tableArray = tableObj.getElementsByTagName("table");
			//tableArray[window["_document_"]].parentNode.parentNode.style.display = 'none';
			tableArray[window["_template_"]].parentNode.parentNode.style.display = 'none';
			//tableArray[window["_flow_"]].parentNode.parentNode.style.display = 'none';
		}
	}

	window.onload = init;

	var currMode = "view";
	if(m_nObjType == OBJ_DOCUMENT && getParameter('mode') != 'view'){
		currMode = 'edit';
		Event.observe(window, 'load', function(){
			var imgObj = $('imgObj');
			if(imgObj){
				imgObj.style.display = 'none';	
			}	
			$('tabTd').style.display = 'none';
			$('saveDiv').style.display = '';		
			$('passTable').style.display = '';
			$('comDiv').style.display = '';

			if(<%=sObjIds.indexOf(",")%> >= 0){//设置多篇文档权限时，权限初始化为零
				var inputArray =  $('tabContentTd').getElementsByTagName('input');
				for(i = 0; i < inputArray.length; i++){
					if(inputArray[i].getAttribute("type") == 'checkbox'){
						inputArray[i].checked = false;
					}
				}
				$('hostInfoTd').innerText = '重新设置多篇文档的权限';
			}
		});
	}

	function getIMGHTML(){
		return	"<TABLE><TR VALIGN='top'><TD class='wcm_pointer'>" +
					"<img id='imgObj' src='../images/auth/" + (currMode == 'edit' ? "edit.gif" : "view.gif") +  
					"' title='" + (currMode == 'edit' ? "当前为修改模式，&#13;单击转为查看模式'" : "当前为查看模式，&#13;单击转为修改模式'") + " onclick='changeMode(this);'>" +
				"</TD></TR></TABLE>";			 
	}
	function changeMode(imgObj){
		if(currMode == 'edit'){//改为查看模式

			if(window.GRightChanged || NewWCMRightHelper.bUpdate){//如果做过修改，提示用户保存修改
				var sMsg = "已经修改过用户权限，请先单击<b style='color:blue'>保存按钮</b>保存权限修改.";
				sMsg += "否则当前所作的权限修改将<b style='color:red'>不会保存</b>";
				sMsg += ",从而导致修改丢失，你确定放弃修改吗？";
				$confirm(sMsg, function(){
					$dialog().hide();
					window.location.reload();
				}, function(){
					$dialog().hide();
				});
				return;
			}

			imgObj.setAttribute('src', "../images/auth/view.gif");
			imgObj.setAttribute('title', "当前为查看模式，\n单击转为修改模式");

			//隐藏commandBtn
			$('saveDiv').style.display = 'none';		
			$('passTable').style.display = 'none';
			$('comDiv').style.display = 'none';
			currMode = 'view';
		}else{//改为编辑模式
			imgObj.setAttribute('src', "../images/auth/edit.gif");
			imgObj.setAttribute('title', "当前为修改模式，\n单击转为查看模式");
			$('saveDiv').style.display = '';		
			$('passTable').style.display = '';
			$('comDiv').style.display = '';
			currMode = 'edit';
		}
		init();
	}
</script>

<script>
//覆盖删除的通知函数，以便刷新页面
function CNewWCMRightHelper_notifyRemove(_elEvent){
	//权限是否修改的标记
	window.GRightChanged = true;
	init();
}
TRSSimpleTab.openItem = function(){
	CTRSSimpleTab_openItem.apply(this, arguments);
	initTabStatus()
}


function addOperator(_nType, sReturnValue){
	if(sReturnValue == null || sReturnValue.length == 0)return;

	var arDeleteIndex = NewWCMRightHelper.getIndexArray(_nType);
	var arDeleteId = NewWCMRightHelper.getIdArray(_nType);

	var arSelectId = sReturnValue[0];
	var arSelectName = sReturnValue[1]; 
	
	var bRemove = false;

	for(var i= arDeleteId.length - 1; i>=0; i--){
		bRemove = true;
		for(var j=0; j<arSelectId.length;j++){
			if(arDeleteId[i] == arSelectId[j]){
				arSelectId[j]=-1;
				bRemove = false;
			}
		}
		if(bRemove) NewWCMRightHelper.removeAt(arDeleteIndex[i]);
	}
	
	//遍历添加
	for(var i=0; i<arSelectId.length; i++){
		if (arSelectId[i] == -1) continue;
		NewWCMRightHelper.putOperator(_nType, arSelectId[i], arSelectName[i]);
	}
	if(!NewWCMRightHelper.bUpdate)return;

//刷新页面	
	init();
}

function addOperatorNoRemove(_nType, arSelectId, arSelectName, _sRightValue){
	//遍历添加
	for(var i=0; i<arSelectId.length; i++){
		NewWCMRightHelper.putOperator(_nType, arSelectId[i], arSelectName[i], _sRightValue);
	}
	if(!NewWCMRightHelper.bUpdate)return;

	//刷新页面	
	init();
}

function addUser(){
	var _nType = TYPE_WCMOBJ_USER;
	FloatPanel.open(getOperatorSelectURL(_nType) + "?TransferName=1", '用户选择', 600, 400);
}
function getArguments(index){
	var typeArray = [TYPE_WCMOBJ_USER, TYPE_WCMOBJ_GROUP, TYPE_WCMOBJ_ROLE];
	return [NewWCMRightHelper.getIdArray(typeArray[index]), NewWCMRightHelper.getNameArray(typeArray[index])];
}
function addGroup(){
	var _nType = TYPE_WCMOBJ_GROUP;
	var url = getOperatorSelectURL(_nType) + "?TransferName=1&GroupIds=" + NewWCMRightHelper.getIdArray(_nType) + "&TreeType=1";
	FloatPanel.open(url, '用户组选择', 600, 400);
}

function addRole(){
	var _nType = TYPE_WCMOBJ_ROLE;
	var url = getOperatorSelectURL(_nType) + "?TransferName=1&RoleIds=" + NewWCMRightHelper.getIdArray(_nType);
	FloatPanel.open(url, '用户组选择', 600, 400);
}

function getOperatorSelectURL(_nType){
	switch(_nType){
		case TYPE_WCMOBJ_USER:
			return "auth/user_select_index.jsp";
		case TYPE_WCMOBJ_GROUP:
			return "auth/group_select_index.jsp";
		case TYPE_WCMOBJ_ROLE:
			return "auth/role_select_index.jsp";
		default:
			return "auth/user_select_index.jsp";
	}
}

var m_nImpartMode = 0;
function selectImpartMode(){
	m_nImpartMode = 0;
	<%-- 
		/**
		 *	wenyh@2008-12-10 引入CrashBoard后,
		 *	url: auth/impart_mode_select.html-->impart_mode_select.html
		 */
	--%>
	var oSelectImpartModeDialog = TRSDialogContainer.register('DIALOG_SELECT_IMPART_MODE',
		'选择给所有子栏目的权限传递模式', 'impart_mode_select.html', '320px', '200px');
	oSelectImpartModeDialog.onFinished = function(_args){		
		m_nImpartMode = _args.ImpartMode;
		postRights2Server(true);
	}

	TRSDialogContainer.display('DIALOG_SELECT_IMPART_MODE');	
}

function postRights2Server(_bSelectImpartMode){
	if(!_bSelectImpartMode)
		$dialog().hide();

	window._changed_ = true;
	var frmAction = document.frmAction;
	if(frmAction == null){
		CTRSAction_alert("没有定义");
	}
	ProcessBar.init('进度执行中，请稍候...');
	ProcessBar.addState('正在执行保存操作');			
	ProcessBar.addState('保存完成');
	ProcessBar.start();
	frmAction.RightsXML.value = NewWCMRightHelper.toXMLInfo();
	//var oResetChildren = document.getElementById("resetChildren");
	//var oResetChildren = passToChild;
	//if(oResetChildren != null)
	//frmAction.ResetChildrenRights.value = (oResetChildren.checked ? "1" : "0");
	
	var objIdArray = getParameter("ObjId").split(",");
	for(var i = 0; i < objIdArray.length; i++){
		var postData = {
			ImpartModes : m_nImpartMode,
			ObjType : $F('ObjType'),
			ObjId : objIdArray[i],
			ResetChildrenRights : $F('ResetChildrenRights'),
			RightsXML : $F('RightsXML')
		};
		new Ajax.Request("/wcm/WCMV6/auth/right_set_dowith.jsp", {
			method:'post', 
			parameters:$H(postData).toQueryString(), 
			onSuccess : success.bind(window, postData),
			on500 : on500,
			onFailure:failure,
			contentType:'application/x-www-form-urlencoded'
		});
	}
	var tempInt = 0;
	function success(postData, originalRequest){
		window.GRightChanged = false;
		NewWCMRightHelper.bUpdate = false;
		tempInt++;
		if(!needRefresh && originalRequest.responseText.trim() == "true"){
				needRefresh = true;
		}
		if(tempInt == objIdArray.length){
			ProcessBar.next();
			setTimeout(function(){
				ProcessBar.exit();
				if(OBJ_DOCUMENT == m_nObjType){
					window.close();
				}else if(needRefresh){
					$nav().doAfterModifyFocusNode(true, $nav().refreshMain);
				}else if($('imgObj')){
					$('imgObj').fireEvent('onclick');
				}
			}, 100);
		}
	}
	function failure(){
		ProcessBar.next();
		setTimeout(function(){
			ProcessBar.exit();
			$timeAlert("修改失败,已经成功修改了" + tempInt + "个",3,function(){$dialog().hide();});
			//var dlg = $alert("修改失败,已经成功修改了" + tempInt + "个", function(){$dialog().hide();});
		}, 100);	
	}
	function on500(transport, json){
		ProcessBar.exit();
		var textDoc = transport.responseText.stripScripts();
		$('errorInfo').innerHTML = textDoc;
		FaultDialog.show({
			code		: $('errorNumber').innerText || '',
			message		: $('errorMessage').innerText || '',
			detail		: $('msgForCopy').innerText || ''
		}, '与服务器交互时出现错误', function(){
			$nav().refreshSiteType('0', null, function(){
				getFirstHTMLChild($nav().$("r_0")).click();
			});
		});
	}
}

var m_bHasChildren = <%=bHasChildren%>;
function onSave(passToChild){
	if(m_nObjType == OBJ_DOCUMENT && 
			NewWCMRightHelper.arRight.length != 0){//当前有对象
		var found = false;
		for(var i = 0, nSize = NewWCMRightHelper.arRight.length; i < nSize; i++){
			var oRight = NewWCMRightHelper.arRight[i];
			if(oRight.getOperatorType() ==  <%=Right.OPRTYPE_USER%> 
					&& oRight.getOperatorId() == <%=loginUser.getId()%>){
				found = true;
				break;
			}
		}
		if(!found){
			var msg = "您没有添加当前登录用户，会造成当前登录用户对当前设置文档的权限丢失。\n";
			msg += "需要立刻添加当前用户并设置其权限吗？";
			if(confirm(msg)){
				addOperatorNoRemove(<%=Right.OPRTYPE_USER%>, 
									[<%=loginUser.getId()%>],
									[' <%=CMyString.filterForJs(loginUser.getName())%>'],
									"111111111111111111111111111111111111111111111111111111111111111"
				);
				return;
			}
		}
	}
	var msg = "";
	/*var oResetChildren = document.getElementById("resetChildren") || {};
	if(oResetChildren.checked){
		msg += "确定保存当前对权限的修改，并传递给子对象吗?";
		msg += "<div style='color:red;font-weight:bold;font-size:14px;'>注意，这样将覆盖所有子栏目的权限设置</div>";
	}else{
		msg = "确定保存当前对权限的修改?";
	}*/
	msg = "确定保存当前对权限的修改?";
	
	if(m_bHasChildren){
		selectImpartMode();
	}else{
		$confirm(msg, postRights2Server, function(){
			$dialog().hide();
		});
	}


	/*$confirm(msg, postRights2Server, function(){
		$dialog().hide();
	});*/
}

Event.observe(window, 'beforeunload', function(){
	try{
		if(window.opener && window._changed_){
			var topWin = window.opener.top.actualTop || window.opener.top;
			topWin.$MessageCenter.getMain().PageContext.refresh(topWin.location_search.replace("?", ''));
		}
	}catch(error){
		//兼容来自52页面的使用，需要定义 refreshMe 函数
		try{
			if(window.opener && window.opener.refreshMe) {
				window.opener.refreshMe();
			}
		}catch(err){
			//TODO
			alert(err.message);
		}
	}
});
</script>
<style>
	#id_TRSSimpleTab{
		float:left;
		width:30px;
		height:100%;
		position:absolute;
		margin-left:-1px;
	}
</style>
</HEAD>

<BODY>
<form name="frmAction" method="post">
	<INPUT TYPE="hidden" name="ObjType" id="ObjType" value="<%=nObjType%>">
	<INPUT TYPE="hidden" name="ObjId" id="ObjId" value="<%=nObjId%>">
	<INPUT TYPE="hidden" name="ResetChildrenRights" id="ResetChildrenRights" value="0">
	<INPUT TYPE="hidden" name="RightsXML" id="RightsXML" value="">
</form>
<table id="table_body" class="wcm_table_layout" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td valign="top" bgcolor="#fff" style="height:29px;">
		<div class="pageinfo">
			<div class="pageinfo_left"></div>
			<div class="pageinfo_right"></div>
			<div style="float:right">
				<script>
					if((m_nObjType == OBJ_WEBSITE && isAccessable4WcmObject(getParameter("RightValue"), 7))
							|| (m_nObjType == OBJ_CHANNEL && isAccessable4WcmObject(getParameter("RightValue"), 55))){
						document.write(getIMGHTML());
					}
				</script>
			</div>	
			<div style="float:right; display:none;" id="saveDiv">
				<script>
					var oButtons = new CTRSButtons("bt_table_noborder");
					oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.saveright", "保存修改")%>", "onSave();", "../images/auth/save.png","<%=LocaleServer.getString("auth.tip.saveright", "保存修改")%>");
					oButtons.draw();
					delete oButtons;
				</script>
			</div>
			<!--<table style="float:right;height:29px; display:none;" id="passTable"><TR valign="top"><TD>//-->
			<div style="float:right; display:none;margin-top:3px;" id="passTable">
			<%if(bHasChildren){%>
					<!--input type="checkbox" name="resetChildren" id="resetChildren">传递给子对象-->
				<!--
				<script>
					var oButtons = new CTRSButtons("bt_table_noborder");
					oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.saveright", "保存修改并传递给子对象")%>", "onSave(true);", "../images/auth/savetochild.png","<%=LocaleServer.getString("auth.tip.saveright", "保存修改同时传递到子对象")%>");
					oButtons.draw();
					delete oButtons;
				</script>
				//-->
			<%}%>
			</div>
			<!--</TD></TR></table>//-->
			<div style="float:right; display:none;" id="comDiv">
			<script>		
				oButtons = new CTRSButtons("bt_table_noborder");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.adduser", "添加用户")%>", "addUser();", "../images/wcm52/icon_user.gif","<%=LocaleServer.getString("auth.tip.adduser", "添加用户")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.addgroup", "添加用户组")%>", "addGroup();", "../images/wcm52/icon_group.gif","<%=LocaleServer.getString("auth.tip.addgroup", "添加用户组")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.addrole", "添加角色")%>", "addRole();", "../images/wcm52/icon_role.gif","<%=LocaleServer.getString("auth.tip.addrole", "添加角色")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.dropall", "全部删除")%>", "NewWCMRightHelper.removeAll();", "../images/wcm52/button_delete.gif","<%=LocaleServer.getString("auth.tip.dropall", "删除全部设置")%>");
				oButtons.draw();
				delete oButtons;
			</script>	
			</div>
			<TABLE style="float:left;height:29px;"><TR VALIGN='MIDDLE'><TD id="hostInfoTd">&nbsp;<%=CMyString.transDisplay(sHostInfo)%></TD></TR></TABLE>
		</div>	
	</td>
	<td></td>
</tr>
<tr>
	<td valign="top">
		<div style="width:100%; height:100%; overflow-y:auto;">
		<TABLE width="100%" height="100%" style="border:0px;border-left:1px solid #474747;" cellpadding="0" cellspacing="0">
			<TR>		  
			  <TD bgcolor="whitesmoke" align=center valign="top" id="tabContentTd">
				<div id="id_TRSSimpleTab0" style="display:inline;"></div>				
				<div id="id_TRSSimpleTab1" style="display:none"></div>
				<div id="id_TRSSimpleTab2" style="display:none"></div>			
				<div id="id_TRSSimpleTab3" style="display:none"></div>		
				<div id="id_TRSSimpleTab4" style="display:none"></div>
			  </TD>
			</TR>	
		</TABLE>
		</div>
	</td>
	<td valign="top" style="width:50px;" id="tabTd">
		<script>		
			TRSSimpleTab.nCurrIndex = 0;	
<%
for (int i = 0; i < pRightTypeNames.length; i++) {
	out.println("TRSSimpleTab.addItem(\""+pRightTypeNames[i]+"操作权限\");");
}	
%>
			if(m_nObjType != OBJ_DOCUMENT){
						TRSSimpleTab.draw();
			}
		</script>
	</td>
</tr>
</table>
<script>
	var footer = (top.actualTop||top).$('footer');
	if(footer){
		footer.style.width = (top.actualTop||top).$('footer_td').offsetWidth - 50;
		Event.observe(window,'unload',function(){
			footer.style.width = '100%';
		});
	}
</script>

<div id="errorInfo" style="display:none;"></div>
</BODY>
</HTML>
<%!		
	public String getOperatorDesc(CMSObj _rightHost)throws WCMException{
		switch(_rightHost.getWCMType()){
			case User.OBJ_TYPE:
				return ((User)_rightHost).getName() ;
			case Group.OBJ_TYPE:
				return ((Group)_rightHost).getName();
			case Role.OBJ_TYPE:
				return ((Role)_rightHost).getName();
			default:
				return "";
		}
	}

	public String getStatusIndexes(User _loginUser)throws WCMException{
		Statuses currStatuses = Statuses.openWCMObjs(_loginUser, null);
        Status currStatus=null;
        
        //动态获取Status的权限值
       String rightIndexes="";
        for (int i = 0; i < currStatuses.size(); i++) {
            currStatus = (Status)currStatuses.getAt(i);
    		if(currStatus==null || currStatus.getRightIndex()==0 || !currStatus.isUsed())
    			continue;
    		else rightIndexes += (rightIndexes==""?"":",")+currStatus.getRightIndex();
            
        }
		return rightIndexes;
	}

	private void writeRightDefsScript(JspWriter out, int nObjType, String isVirtual) throws Exception {
        XMLConfigServer oXMLConfigServer = XMLConfigServer.getInstance();
        List listOperation = oXMLConfigServer
                .getConfigObjects(OperationConfig.class);
        for (int i = 0, nSize = listOperation.size(); i < nSize; i++) {
            OperationConfig operationConfig = (OperationConfig) listOperation
                    .get(i);
            if (operationConfig == null)
                continue;
			//filter start ................
			if(nObjType == WCMTypes.OBJ_DOCUMENT){
				if(operationConfig.getName().equalsIgnoreCase("Document_outline")
						|| operationConfig.getName().equalsIgnoreCase("Document_add")){
					continue;
				}
			}
			if(isVirtual != null && isVirtual.equals("1")){
				if(nObjType == WCMTypes.OBJ_CHANNEL){
					
				}
			}
			//filter end ...................
			String sDepends = getDepends(operationConfig), sSimilar = getSimilar(operationConfig);
            

            out.println("NewWCMRightHelper.addRightDef("
                    + operationConfig.getIndex()
                    + ", \""
                    + CMyString.filterForJs(operationConfig.getDispName())
                    + "\", \""
                    + CMyString.filterForJs(CMyString.showNull(operationConfig
                            .getDesc())) + "\", \""
                    + CMyString.showNull(operationConfig.getType()) + "\", "+sDepends+", "+sSimilar+");");
        }
    }
    
    private String getSimilar(OperationConfig operationConfig){
        int[] pResult = RightConfigServer.getInstance().getSimilarIndexs(
                operationConfig.getIndex());
        if(pResult == null || pResult.length == 0)return null;
        
        int nSize = pResult.length;
        StringBuffer sbResult = new StringBuffer(nSize*4+2);
        sbResult.append("\"");
        boolean bFirst = true;
        for (int i = 0; i < nSize; i++) {            
            if (pResult[i] == 0)
                continue;
            
            if(bFirst){                
                bFirst = false;
            }else{
                sbResult.append(",");
            }
            sbResult.append(pResult[i]);
        }
        sbResult.append("\"");
        if(bFirst)return null;
        return sbResult.toString();
    }
    
    private String getDepends(OperationConfig operationConfig) {
        OperationRelatedConfig includes = operationConfig
                .getDepends();
        if (includes == null)
            return null;        
        
        ArrayList elements = includes.getOperations();
        int nSize = elements.size();
        StringBuffer sbResult = new StringBuffer(nSize*4);
        boolean bFirst = true;
        sbResult.append("\"");
        for (int i = 0; i < nSize; i++) {
            OperationConfig includeOperation = (OperationConfig) elements
                    .get(i);
            if (includeOperation == null)
                continue;
            
            if(bFirst){                
                bFirst = false;
            }else{
                sbResult.append(",");
            }
            sbResult.append(includeOperation.getIndex());
        }
        sbResult.append("\"");
        return sbResult.toString();
    }
%>