PageContext = {
	temp_rules: null,
	loadRules : function(_json){
		PageContext.temp_rules = _json || PageContext.nodeContext.obj.rules;
		var json = {RULES: com.trs.util.JSON.toUpperCase(PageContext.temp_rules)};
		//alert(Object.parseSource(json))
		var sValue = TempEvaler.evaluateTemplater('rules_template', json, {})
		Element.update($('divRuleSkimList'), sValue);		
	}

};

var m_sUserIds = "";
var m_sUserNames = "";
var m_sGroupIds = "";
var m_sGroupNames = "";
WCM52_INCLUDE_ROOT_PATH = '../../include/';
WCM52_AUTH_ROOT_PATH = '../../auth/';

function setOwner(bGroup){
	var oTRSAction = new CTRSAction(WCM52_INCLUDE_ROOT_PATH + "select_index.jsp");
	oTRSAction.setParameter("UserIds", m_sUserIds);
	oTRSAction.setParameter("GroupIds", m_sGroupIds);
	oTRSAction.setParameter("IsGroup", bGroup);
	oTRSAction.setParameter("FromProcess", 1);
	var arOwners = oTRSAction.doDialogAction(800, 550);
	if(arOwners == null){
		return;
	}

	if(arOwners.length < 4){
		Ext.Msg.alert("返回值不正确！");
		return;
	}
	var regexp = /,/g;
	m_sUserNames = arOwners[1] || "";
	m_sUserIds = arOwners[0] || "";
	m_sGroupNames = arOwners[3] || "";
	m_sGroupIds = arOwners[2] || "";
	m_sUserNames = m_sUserNames.replace(regexp, ",");
	m_sGroupNames = m_sGroupNames.replace(regexp, ",");
	
	//document.all("trUsers").innerHTML = m_sUserNames;
	document.all("trUsers").value = m_sUserNames;
	//document.all("trGroups").innerHTML = m_sGroupNames;
	document.all("trGroups").value = m_sGroupNames;
	return;
}
function setOwner2(bGroup){
	var oTRSAction = new CTRSAction(WCM52_INCLUDE_ROOT_PATH + "select_index.jsp");
	oTRSAction.setParameter("GroupIds", $('hdToUsersGroupIds').value);
	oTRSAction.setParameter("IsGroup", bGroup);
	oTRSAction.setParameter("FromProcess", 1);
	var arOwners = oTRSAction.doDialogAction(800, 550);
	if(arOwners == null){
		return;
	}

	if(arOwners.length < 4){
		Ext.Msg.alert("返回值不正确！");
		return;
	}
	var regexp = /,/g;
	var sGroupNames = arOwners[3] || '';
	var sGroupIds = arOwners[2] || '';
	var sGroupNames = sGroupNames.replace(regexp, ',');
	
	$('hdToUsersGroupIds').value = sGroupIds;
	$('txtToUsersGroupNames').value = sGroupNames;
	return;
}
function setOwner3(){
	var oTRSAction = new CTRSAction(WCM52_INCLUDE_ROOT_PATH + "select_index.jsp");
	oTRSAction.setParameter("UserIds", $('hdToUsersUserIds').value);
	oTRSAction.setParameter("FromProcess", 1);
	var arOwners = oTRSAction.doDialogAction(800, 550);
	if(arOwners == null){
		return;
	}

	if(arOwners.length < 4){
		Ext.Msg.alert("返回值不正确！", function(){});
		return;
	}
	var regexp = /,/g;
	var sUserNames = arOwners[1] || '';
	var sUserIds = arOwners[0] || '';
	var sUserNames = sUserNames.replace(regexp, ',');
	$('hdToUsersUserIds').value = sUserIds;
	$('txtToUsersUserNames').value = sUserNames;
	return;
}
function setRoleOwner(){
	var oTRSAction = new CTRSAction(WCM52_AUTH_ROOT_PATH + "role_select_index.jsp");
	oTRSAction.setParameter("RoleIds", $('hdToUsersRoleIds').value);
	oTRSAction.setParameter("TreeType", 1);
	oTRSAction.setParameter("TreeWidth", 692);
	oTRSAction.setParameter("TransferName", 1);
	var oRoleValue = oTRSAction.doDialogAction(700, 500);
	if(oRoleValue == null){
		return;
	}
	$('txtToUsersRoleNames').value = oRoleValue[1].join(',');
	$('hdToUsersRoleIds').value = oRoleValue[0].join(',');
	return;
}
function checkSpecialChars(_sCode) {
	var regExp = /[<>\[\]{}#*%$%&^!~\-`]/g;
	var sResult = _sCode.match(regExp)
	return TRSArray.distinct(sResult);
}

function compareLength(strValue,minLength,maxLength,msg,msg2){
	var totallength=0;

	for (var i=0;i<strValue.length;i++)
	{
		var intCode=strValue.charCodeAt(i);
		if (intCode>=0&&intCode<=128){
			totallength=totallength+1;	//非中文单个字符长度加 1
		}else{
			totallength=totallength+2;	//中文字符长度则加 2
		}
	} //end for

	if(!msg2) {							//如果只传入一条信息
		if ( totallength<minLength||totallength>maxLength )  
		{
			Ext.Msg.alert(msg, function(){});
			//Ext.Msg.alert( msg );
			return false;
		}
	} else {										//如果传入两条信息		
		if ( totallength<minLength )
		{
			Ext.Msg.alert(msg, function(){});
			//Ext.Msg.alert( msg );
			return false;
		}
		if ( totallength>maxLength )
		{
			Ext.Msg.alert(msg2, function(){});
			//Ext.Msg.alert( msg2 );
			return false;
		}
	}//end if

	return true;

} //英文字符和中文字符长度的比较
function myDebugArgs(){

}
//初始化页面
function initPage(){
	//设置名称和描述
	var dialogArguments = (window.top.getDialogArguments||myDebugArgs)();
	PageContext.nodeContext = dialogArguments;
	//ge gfc add @ 2007-9-27 17:03
	PageContext.infoviewId = 0;
	var rawFlow = PageContext.nodeContext.Flow;
	if(rawFlow && rawFlow.getInfoviewId) {
		PageContext.infoviewId = rawFlow.getInfoviewId();
	}
	//alert(Object.parseSource(PageContext.nodeContext))
	var node = dialogArguments.obj;
	$('nodeName').value = node.name.trim() || '';
	$('nodeDesc').value = node.desc.trim() || '';
	$('bReasignUsers').checked = node.bReasignUsers == 1;
	$('bReasignSepNodes').checked = node.bReasignSepNodes == 1;
	if((node.actions & 1)!= 0)
		$("action_edit").checked =  true;
	if((node.actions & 2)!= 0)
		$("action_del").checked =  true;
	if((node.actions & 4)!= 0)		
		$("action_pub").checked = true;
	/*if((node.together == 1))
		$("action_together").checked = true;
	if((node.seperator == 1))
		$("action_seperator").checked = true;//*/
	if(node.workmodal != null && $('workmodal_' + node.workmodal) != null) {
		$('workmodal_' + node.workmodal).checked = true;
	}else{
		$('workmodal_0').checked = true;
	}
	//显示用户列表页面
	var oTRSAction = null;
	for(var userIndex = 0; userIndex < node.users.length; userIndex++){
		if(m_sUserIds != "")	m_sUserIds += ",";
		if(m_sUserNames != "")	m_sUserNames += ",";
		var aUser = node.users[userIndex];
		if(aUser == null) continue;
		m_sUserIds += aUser.userID;
		m_sUserNames += aUser.userName;
	}

	if(m_sUserNames.length > 0)
		document.all("trUsers").value = m_sUserNames;

	//显示用户组列表页面
	var groupList = "";
	for(var groupIndex = 0; groupIndex < node.groups.length; groupIndex++){
		if(m_sGroupIds != "")	m_sGroupIds += ",";
		if(m_sGroupNames != "")	m_sGroupNames += ",";
		var aGroup = node.groups[groupIndex];
		if(aGroup == null) continue;
		m_sGroupIds += aGroup.groupID;
		m_sGroupNames += aGroup.groupName;
	}

	if(m_sGroupNames.length > 0)
		document.all("trGroups").value = m_sGroupNames;
	//gfc 
	PageContext.loadRules();
	
	$('nodeName').select();
	$('nodeName').focus();
	Element.hide('frmRuleDef');
	//首先加载完成config，以使得以后各个需要的地方不再需要异步加载
	$configurator.loadWorkflowConfig(function(_config){
		var tousers = node['tousers'];
		var tousersparams = node['tousersparams'];
		if(tousers != null && tousers.trim() != '') {
			$('chkUserRule').checked = true;
			displayUserSelection(false, tousers, tousersparams);
			//alert(Object.parseSource(_config))
			if(tousersparams != null && tousersparams.trim() != '') {
				
			}
		}
	});
	//ge gfc add @ 2007-9-26 15:38
	if(PageContext.infoviewId > 0) {
		$('sp_opt_right').style.width = '26%';
		Element.show('sp_field_opt');
		var fields = node.fields;
		var arReadFields = [], arWriteFields = [];
		var hAllFields = {};
		if(fields && fields.length > 0) {
			//alert($toQueryStr(fields[0]));
			for (var i = 0; i < fields.length; i++){
				var field = fields[i];
				hAllFields[field['fieldname']] = field;
			}
			window.m_hAllFields = hAllFields;
			updateFieldsDisplay();
		}
	}
	ValidationHelper.initValidation();
	$('okbutton').disabled = false;
	$('lnkToggle').disabled = false;
}
window.m_hAllFields = {};
function updateFieldsDisplay(){
	var arReadFields = [], arWriteFields = [], arInitedFields;
	var fields = window.m_hAllFields;
	var sReadFields = '', sWriteFields = '', sInitedFields = '';
	for( var sName in fields){
		var field = fields[sName];
		var sFieldHtml = makeFieldSpan(field);
		if(field['readfield'] == 0) {
			sReadFields += sFieldHtml;
		}
		if(field['writefield'] == 0) {
			sWriteFields += sFieldHtml;
		}
		if((field['readfield'] != 0 && field['writefield'] != 0) 
			&& (!__isEmpty($transHtml(field['initvalue'])) || !__isEmpty(field['initvaluecreator']))) {
			sInitedFields += sFieldHtml;
		}
	}
	Element.update('spReadFields', __showNull(sReadFields));
	Element.update('spWriteFields', __showNull(sWriteFields));
	Element.update('spInitedFields', __showNull(sInitedFields));
}
function __isEmpty(_str){

	return (_str == null || _str.length == 0);
}
function __showNull(_str){
	return (__isEmpty(_str) ? '<span class="sp_flownode_field_none">'+ (wcm.LANG['NONE_1'] || '无') + '</span>' : _str);
}

var m_hFieldTypeDescs = ['','(分组) ', '(视图) '];

function makeFieldSpan(_field){
	var sInitVal = $transHtml(_field['initvalue']);
	if(sInitVal == null || sInitVal == '') {
		sInitVal = _field['initvaluecreator'];
		if(sInitVal != null && sInitVal != '') {
			sInitVal = '(变量)' + sInitVal;
		}else{
			sInitVal =  '(无)';
		}
	}
	var nFieldType = parseInt(_field['fieldtype']);
	if(!(nFieldType >= 0) || nFieldType > 2) {
		nFieldType = 0;
	}
	var result = '<span class="sp_flownode_field_' + nFieldType
		+ '" title="' + m_hFieldTypeDescs[nFieldType] + (nFieldType == 0 ? String.format("初始值:{0}",sInitVal) : '') + '">'
		+ trimPrexPath(_field['fieldname']) + '</span>';
	return result;
}
function trimPrexPath(_sXPath){
	if(_sXPath == null) return '';
	var aXPaths = _sXPath.split('/');

	return aXPaths.pop().replace(/^[^:]*?:/,'');
}
function selectFields(_nType){
	var oTRSAction = new CTRSAction("../infoview/infoview_workflow_setting.jsp");
	oTRSAction.setParameter('InfoViewId', PageContext.infoviewId);
	
	$configurator.loadWorkflowConfig(function(_config){
		var args = {
			'data': window.m_hAllFields,
			'config': (_config || {})['initvalue_creators']
		};
		var dem = getMaxDialogDem();
		var result = oTRSAction.doDialogAction(dem.width, dem.height, args);
		if(result == null) {
			return;
		}
		
		window.m_hAllFields = Object.extend({}, result);

		updateFieldsDisplay();
	});	

}

//执行确定操作：创建新的节点或更新节点
function doOK(){
	if(!ValidationHelper.doValid('divHeader')) {
		return;
	}
	//fangxiang@2002-03-27:检查节点名称和节点描述的长度
	var sNodeName = window.document.all.nodeName.value.trim() || "";
	var sNodeDesc = window.document.all.nodeDesc.value.trim() || "";

	var specialChars = checkSpecialChars(sNodeName);
	if(specialChars){
		Ext.Msg.alert(String.format("您输入的节点名称不能包含以下特殊字符 {0} ", specialChars), function(){
			$('nodeName').focus();
			$('nodeName').select();
		});
		return false;
	}
	specialChars = checkSpecialChars(sNodeDesc);
	if(specialChars){
		Ext.Msg.alert(String.format("您输入的节点描述不能包含以下特殊字符 {0} ",specialChars), function(){
			$('nodeName').focus();
			$('nodeName').select();
		});
		return false;
	}
	
	//判断节点是否存在
	var dialogArguments = window.top.getDialogArguments();
	var isNode = dialogArguments.Flow.findNodeByName(window.document.all.nodeName.value.trim());
	//如果不存在，则添加新的节点或更新节点
	var node = dialogArguments.obj;
	if(isNode == null || isNode == node){
		var _actions = 0;
		if($("action_edit").checked == true)_actions |= 1;
		if($("action_del").checked == true)_actions |= 2;
		if($("action_pub").checked == true)_actions |= 4;
		node.actions = _actions;
		if($('chkUserRule').checked) {
			var selHandlers = $('selHandlers');
			node.tousers = selHandlers.value;
			node.clearUsers();
			node.clearGroups();
			var eOption = selHandlers.options[selHandlers.selectedIndex];
			dialogArguments.groupDesc = eOption.innerText;

			//ge gfc add @ 2007-11-2 增加tousers-creator参数规则
			config = $configurator.getWorkflowConfig()['touser'];
			var currTousers = null;
			if(config != null && (currTousers = config[eOption.value]) != null) {
				var tousersparams = null;
				if(currTousers['groupids'] != null) { // 根据指定GroupIds, 特定组织或部门经理
					tousersparams = $('hdToUsersGroupIds').value.trim();
					if(tousersparams.length == 0) {
						Ext.Msg.alert(wcm.LANG['FLOW_15'] || '请指定用户组织或部门！');
						return;
					}
					tousersparams ='GroupIds='  + tousersparams + ';GroupNames=' + $('txtToUsersGroupNames').value.trim();
				}else if(currTousers['userids'] != null) { // 根据指定GroupIds, 特定组织或部门经理
					tousersparams = $('hdToUsersUserIds').value.trim();
					if(tousersparams.length == 0) {
						Ext.Msg.alert(wcm.LANG['FLOW_16'] || '请指定用户！');
						return;
					}
					tousersparams ='UserIds='  + tousersparams;
				}else if(currTousers['fieldname'] != null) {//根据指定FieldName, 特定表单文档字段查询经理				
					tousersparams = $('txtToUsersFieldName').value.trim();
					if(tousersparams.length == 0) {
						$('txtToUsersFieldName').select();
						$('txtToUsersFieldName').focus();
						Ext.Msg.alert(wcm.LANG['FLOW_17'] || '请填写表单字段名！');
						return;
					}
					tousersparams = 'FieldName=' + tousersparams;
				}else if(currTousers['roleids'] != null) {   // 根据指定RoleIds, 特定角色成员
					tousersparams = $('hdToUsersRoleIds').value.trim();
					if(tousersparams.length == 0) {
						Ext.Msg.alert(wcm.LANG['FLOW_86'] || '请指定用户角色！');
						return;
					}
					tousersparams ='RoleIds='  + tousersparams + ';RoleNames=' + $('txtToUsersRoleNames').value.trim();
				}
				if(tousersparams != null) {
					node.tousersparams = tousersparams;
				}
			}
		}else{
			node.tousers = null;
			//重新设置节点所属的用户
			var arUserIds = m_sUserIds.split(",");
			var arUserNames = m_sUserNames.split(",");
			node.users = null;
			node.users = new Array();
			for(var i = 0; i < arUserIds.length; i++){
				if(arUserIds[i] == 0) continue;
				node.addUser(arUserNames[i], arUserIds[i]);
			}
			dialogArguments.userDesc = m_sUserNames;

			//重新设置节点所属的用户组
			var arGroupIds = m_sGroupIds.split(",");
			var arGroupNames = m_sGroupNames.split(",");
			node.groups = null;
			node.groups = new Array();
			for(var i = 0; i < arGroupIds.length; i++){
				if(arGroupIds[i] == 0) continue;
				node.addGroup(arGroupNames[i], arGroupIds[i]);
			}
			dialogArguments.groupDesc = m_sGroupNames;		
		}

		//设置节点的名称和描述
		node.name = sNodeName;
		node.desc = sNodeDesc;
		node.bReasignUsers = $('bReasignUsers').checked ? 1 : 0;
		node.bReasignSepNodes = $('bReasignSepNodes').checked ? 1 : 0;
		/*node.together = $("action_together").checked ? 1 : 0;
		node.seperator = $("action_seperator").checked ? 1 : 0;//*/
		for (var i = 0; i < 4; i++){
			if($('workmodal_' + i).checked) {
				node.workmodal = i;
				break;
			}
		}
		
		//gfc rules
		var frmRuleDef = $('frmRuleDef');
		if(frmRuleDef.contentWindow && frmRuleDef.contentWindow.PageContext.rules) {
			node.rules = Object.clone(frmRuleDef.contentWindow.PageContext.rules, true);
		}

		//ge gfc add @ 2007-9-26 16:50
		if(PageContext.infoviewId > 0) {
			var hAllFields = window.m_hAllFields;
			var retFields = [];
			for( var sName in hAllFields){
				var field = hAllFields[sName];
				if(!field['fieldname']) {
					continue;
				}
				//alert($toQueryStr(field))
				if(field['writefield'] == null) {
					field['writefield'] = 1;
				}
				if(field['readfield'] == null) {
					field['readfield'] = 1;
				}
				retFields.push(field);	
			}
			node.fields = retFields;//Object.clone(retFields, true);
		}

		//返回用户选择的结果给调用窗口
		window.returnValue = "true";
		//关闭窗口
		window.close();
	}
	else{ //如果节点已经存在，显示提示信息后直接返回等待用户的进一步处理
		Ext.Msg.alert(wcm.LANG['FLOW_18'] || "节点已经存在!", function(){
			$('nodeName').focus();
			$('nodeName').select();
		});
		return false;
	}
}
function doCancel(){
	window.close();
}

var ex_define = null;
var ex_skim = null;
var m_bToDispRuleDefine = false;
function defineRules(){
	if($('lnkToggle').disabled == true) {
		return false;
	}
	$('lnkToggle').disabled = true;

	var frmRuleDef = $('frmRuleDef');
	var divContent = $('divContent');
	if(ex_define == null) {
		ex_define = function() {
			if (m_bToDispRuleDefine){
				$('lnkToggle').innerHTML = wcm.LANG['FLOW_12'] || '返回属性页面';
				$('lnkToggle').disabled = false;
				$('frmRuleDef').style.height="406px";
				Element.hide('divContent');
				Element.hide('divRuleSkimList');
				Element.hide('divOptions');
				Element.show('frmRuleDef');
				$('okbutton').disabled = true;
			}else{
				Element.hide('frmRuleDef');
				Element.show('divContent');
				Element.show('divOptions');
				Element.show('divRuleSkimList');
				ex_skim();
				window.setTimeout(function(){
					PageContext.loadRules(frmRuleDef.contentWindow.PageContext.rules);
				}, 10);

				//ge gfc add@2008-4-16 16:50 通知iframe窗口进行隐藏页面时需要做的操作
				if($('frmRuleDef').contentWindow['doBeforeHide']) {
					$('frmRuleDef').contentWindow.doBeforeHide();
				}
				$('okbutton').disabled = false;
			}
		};
	}
	if(ex_skim == null) {
		ex_skim =  function() {
			if(m_bToDispRuleDefine){
				Element.hide('divRuleSkimList');
				ex_define();
			}else{
				Element.show('divRuleSkimList');
				$('lnkToggle').innerHTML = wcm.LANG['FLOW_START_DEFINE'] || '开始规则定义';
				$('lnkToggle').disabled = false;
			}
		};
	}
	m_bToDispRuleDefine = !m_bToDispRuleDefine;
	if(m_bToDispRuleDefine) { //将要展现"规则定义"
		if(frmRuleDef && frmRuleDef.contentWindow && frmRuleDef.contentWindow.init) {
			var rules = Object.clone(PageContext.temp_rules || PageContext.nodeContext.obj.rules, true);
			frmRuleDef.contentWindow.init(rules);
		}else{
			Event.observe(frmRuleDef, 'load', function(){
				frmRuleDef.contentWindow.init(rules);
			}, false);
		}
		window.setTimeout(function(){
			ex_skim();
		}, 100);
		
	}else{
		ex_define();
	}
}

function refreshMe(){
	var rules = Object.clone(PageContext.temp_rules || PageContext.nodeContext.obj.rules, true);
	var frmRuleDef = $('frmRuleDef');
	frmRuleDef.src = 'workflow_rules_define.html';
	Event.observe(frmRuleDef, 'load', function(){
		window.setTimeout(function(){
			frmRuleDef.contentWindow.init(rules);
		},1000);
	}, false);
	
}

function getCenterDialogDem(){
	var _WIN_WIDTH = window.screen.availWidth;
	var _WIN_HEIGHT = window.screen.availHeight;
	var y = _WIN_HEIGHT * 0.12;
	var x = _WIN_HEIGHT * 0.17;
	var w = _WIN_WIDTH - 2 * x;
	var h = w * 0.618;

	return {
		width : w,
		height: h
	}
}
function getMaxDialogDem(){
	var _WIN_WIDTH = window.screen.width - 12;
	var _WIN_HEIGHT = window.screen.height - 60;
	var w = _WIN_WIDTH;
	var h = _WIN_HEIGHT;

	return {
		width : w,
		height: h
	}
}
Event.observe(document, 'click', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	if(dom.className == "action_name"){
		var _id = dom.id;
		if($('action_'+_id).checked){
			$('action_'+_id).checked = false;
		}else{
			$('action_'+_id).checked = true;
		}
	}
	if(dom.className == "modal_name"){
		var _id = dom.id;
		if($('workmodal'+_id).checked){
			$('workmodal'+_id).checked = false;
		}else{
			$('workmodal'+_id).checked = true;
		}
	}
})
function $transHtml(_sContent) {
	if (_sContent == null)
		return '';

	var nLen = _sContent.length;
	if (nLen == 0)
		return '';

	var result = '';
	for (var i = 0; i < nLen; i++) {
		var cTemp = _sContent.charAt(i);
		switch (cTemp) {
		case '<': // 转化：< --> &lt;
			result += '&lt;';
			break;
		case '>': // 转化：> --> &gt;
			result += '&gt;';
			break;
		case '"': // 转化：" --> &quot;
			result += '&quot;';
			break;
		default:
			result += cTemp;
		}// case
	}// end for
	return result;
}