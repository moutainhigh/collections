//列表内部打开新列表
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_GRIDROW,
	beforeclick : function(event){
		event.cancelBubble = true;
	},
	afterclick : function(event){
		event.cancelBubble = true;
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CELL,
	beforeclick : function(event){
		event.cancelBubble = true;
	},
	afterclick : function(event){
		event.cancelBubble = true;
	}
});
$MsgCenter.on({
	sid : 'sys_allcmsobjs_cancel',
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		event.cancelBubble = true;
	}
});
PageContext.m_CurrPage = $MsgCenter.$currPage();
var bInFloatPanel = getParameter("_fromfp_")=='1';
if(!bInFloatPanel){
	var m_oCommands = {};
	var m_tmpBtn = '<input id="ipt_{1}" type="button" value="{0}"/>';
	function addCommand(_sCmdId,_sCmdHtml,_fnCmd,_oScope,_arrArgs){
		var sBtnId = 'cmd_'+_sCmdId;
		var btnCmd = document.createElement("SPAN");
		btnCmd.innerHTML = String.format(m_tmpBtn, _sCmdHtml, _sCmdId);
		btnCmd.className = 'command_btn';
		btnCmd.id = sBtnId;
		m_oCommands[sBtnId] = {
			fn : _fnCmd,
			scope : _oScope,
			args : _arrArgs
		};
		$('buttons_container').appendChild(btnCmd);
	}
	function addCloseCommand(_sCmdHtml){
		addCommand("_close_", _sCmdHtml||"取消", '_close_');
	}
	Event.observe(window, 'load', function(){
		var divBtns = document.createElement('DIV');
		divBtns.id = 'buttons_container';
		divBtns.align = 'center';
		document.body.appendChild(divBtns);
		if(window.m_fpCfg && window.m_fpCfg.m_arrCommands){
			var bHasClose = (window.m_fpCfg.withclose===false) || false;
			for(var i=0,n=window.m_fpCfg.m_arrCommands.length;i<n;i++){
				var o = window.m_fpCfg.m_arrCommands[i];
				if(o.cmd=='close'){
					bHasClose = true;
					addCloseCommand(o.name);
					continue;
				}
				addCommand(o.cmd, o.name, o.cmd, o.scope, o.args);
			}
			if(!bHasClose){
				addCloseCommand();
			}
		}else{
			addCommand('onOk', '确定', 'onOk', null);
			addCloseCommand();
		}
		var bodyStyle = document.body.style;
		bodyStyle.paddingLeft = bodyStyle.paddingRight
			= (document.body.offsetWidth - m_fpCfg.size[0])/2;
		bodyStyle.paddingTop = bodyStyle.paddingBottom
			= (document.body.offsetHeight - m_fpCfg.size[1])/2;
	});
	Event.observe(window, 'unload', function(){
		document.onclick = null;
		document.onkeydown = null;
	});
	function findCommand(target){
		while(target!=null && target.tagName!='BODY'){
			if(target.className=='command_btn')return target;
			target = target.parentNode;
		}
		return null;
	}
	document.onclick = function(event){
		event = event || window.event;
		var target = event.target || event.srcElement;
		target = findCommand(target);
		if(target==null)return;
		if(target.disabled)return false;
		var cmdId = target.id;
		var cmdInfo = m_oCommands[cmdId];
		if(cmdInfo.fn=='_close_'){
			window.opener = null;
			window.close();
			return;
		}
		var retVal = window[cmdInfo.fn].apply(cmdInfo.scope, []);
		if(retVal!==false){
			window.opener = null;
			window.close();
		}
		return false;
	};
}
function setFPCmdDisable(_cmdName, disable, hide){
	if(parent.setCmdDisable){
		return parent.setCmdDisable(_cmdName, disable, hide);
	}
	var sBtnId = 'ipt_'+_cmdName;
	var oButton = document.getElementById(sBtnId);
	if(!oButton)return;
	oButton.disabled = disable;
	oButton.parentNode.style.display = hide===true ? 'none' : '';
}
function notifyFPCallback(){
	if(Ext.isFunction(parent.m_winFromCallback)){
		window.__notifyFPResult = parent.m_winFromCallback.apply(null, arguments);
		return;
	}
	window.__notifyFPResult = true;
}
var FloatPanel = {
	close : function(){
		closeWindow();
	},
	hide : function(){
		Element.hide(parent.window.frameElement);
	},
	addCommand : function(){
	},
	setTitle : function(_title){
		if(parent.replaceTitleWith){
			return parent.replaceTitleWith(_title);
		}
		window.title = _title;
	},
	disableCommand : setFPCmdDisable,
	dialogArguments : (function(){
		return parent.m_winDialogArguments || {};
	})()
}
function closeWindow(){
	if(window.__notifyFPResult===false)return;
	if(Ext.isFunction(parent.closeMe)){
		parent.closeMe();
	}
	else if(window.frameElement){
		window.frameElement.src = Ext.isSecure?SSL_SECURE_URL:'';
	}else{
		window.opener = null;
		window.close();
	}
}
Event.observe(document, 'keydown', function(event){
	event = event || window.event;
	if(event.keyCode==27)FloatPanel.close();
});
Ext.apply(wcm.LANG,{
	FLOW : '工作流',
	FLOW_CONFIRM : '确定',
	FLOW_CLOSE : '关闭',
	FLOW_SAVE :'保存',
	FLOW_CANCEL : '取消',
	FLOW_REFRESH : '刷新',
	/*channel_list.js*/
	FLOW_0 : '确实要取消该栏目上的工作流分配吗',
	FLOW_1 : '确实要取消所选栏目上的工作流分配吗',
	FLOW_2 : '个',
	FLOW_3 : '栏目',
	/*flow_addedit.js */
	FLOW_EDIT : '编辑;',
	FLOW_DELETE1 : "删除",
	FLOW_PUBLISH : "发布;",
	FLOW_4 : "您输入的工作流名称不能包含以下特殊字符 ",
	FLOW_5 : '正在保存工作流..',
	FLOW_6 : '工作流保存成功！',
	FLOW_7 : '工作流保存结果',
	FLOW_8 : '无表单可供选择',
	FLOW_9 :'您当前使用的浏览器不支持TRSWCM工作流编辑器！\n\n请检查：\n'
		+ '1.浏览器为IE5+；\n'
		+ '2.已从服务器端下载并正确安装了Activex控件，同时该Activex已正常启用；\n'
		+ '3.已将当前站点设置为信任站点。',
	FLOW_START : '开始',
	FLOW_FINISH : '结束',
	FLOW_OPER : '操作:',
	FLOW_NODE : '节点',
	FLOW_DESC : '描述:',
	FLOW_NEWSTATUS : '新稿状态',
	/*flow_list.js*/
	FOLW_LIST : '工作流列表',
	FLOW_NAME : '工作流名称',
	FLOW_DESC : '工作流描述',
	SELECT_CRUSER : '创建者',
	FLOW_ID : '工作流ID',
	/*flow_property.js*/
	FLOW_10 : '您输入的工作流名称不能包含以下特殊字符 ',
	FLOW_11 : '定义工作流规则',
	FLOW_12 : '返回属性页面',
	FLOW_START_DEFINE : '开始规则定义',
	RETURN_VALUE_FALSE : '返回值不正确！',
	GROUP : '(分组) ',
	VIEW : '(视图) ',
	VARIABLE : '(变量)',
	NONE : '(无)',
	NONE_1 : '无',
	INIT_VALUE : '初始值: ',
	FLOW_13 : '您输入的节点名称不能包含以下特殊字符 ',
	FLOW_14 : '您输入的节点描述不能包含以下特殊字符 ',
	FLOW_15 : '请指定用户组织或部门！',
	FLOW_16 : '请指定用户！',
	FLOW_17 : '请填写表单字段名！',
	FLOW_18 : '节点已经存在!',
	/*workflow_rules_define.js*/
	CONDITION : '\n条件:  ',
	NOPER : '\n操作: ',
	OPER_RULE : '新建/修改规则',
	ADD_CONDITION : '增加条件', 
	ADD_OPER : '增加操作',
	FLOW_19 : '确认要删除此规则吗？',
	FLOW_20 : '确认要删除此条件吗？',
	FLOW_21 : '确认要删除此操作吗？',
	FLOW_22 : '配置信息错误。没有指定条件的参数！',
	/*channel_select.html*/
	FLOW_23 : '请选择要设置的栏目！',
	FLOW_24 : '设置成功！',
	/*flow_addedit.jsp*/
	FLOW_25 : '当前工作流的某些属性可能已经修改，是否保存？',
	/*flow_import.html*/
	FLOW_26 : '执行进度，请稍候...',
	FLOW_27 : '正在导入工作流..',
	FLOW_28 : '成功导入工作流!',
	FLOW_29 : '文件路径为空，上传失败！',
	FLOW_30 : '工作流导入结果',
	/*flow_query.jsp*/
	FLOW_31 :  '未知',
	/*flowline_property.html*/
	FLOW_FINISH_PROCESS :'结束流转',
	/*OPERSflow.js*/
	FLOW_ADD_EDIT : '新建/修改工作流',
	FLOW_EMPLOYEE : '分配流程',
	FLOW_IMPORT  :  '工作流导入',
	FLOW_32 : '系统提示信息',
	FLOW_33 : '没有任何要操作的工作流！',
	FLOW_34 : '确实要取消该栏目上的流程吗? ',
	FLOW_EXPORT : '导出这个工作流',
	FLOW_35  : '将当前工作流以xml文件导出',
	FLOW_ADD : '新建工作流',
	FLOW_36 : '新建工作流并分配到当前栏目',
	FLOW_SET : '设置工作流',
	FLOW_EDIT : '编辑这个工作流',
	FLOW_DELETE : '删除这个工作流',
	FLOW_ASSIGN : '分配这个工作流到栏目',
	FLOW_37 : '导入工作流',
	FLOW_38 : '从外部导入工作流',
	FLOWS_DELETE : '删除这些工作流',
	FLOWS_EXPORT : '导出这些工作流',
	FLOW_39 : '将当前工作流以xml文件导出',
	/*workflow_cond_add.js*/
	FLOW_40 : '没有找到关于[condition]的配置定义！',
	/*workflow_cond_edit.js*/
	FLOW_41 : '参数值',
	FLOW_42 : '由于该[条件]尚未提交，此举将把您刚刚新建的这个[条件]移除！\n\n(提示：点击”取消“可以回到[条件]编辑页)',
	/*workflow_action_add.js*/
	FLOW_43 : '没有找到关于[action]的配置定义！',
	/*workflow_action_add.js*/
	FLOW_44 : '[action]没有被初始化！\n',
	FLOW_45 : '由于该[操作]尚未提交，此举将把您刚刚新建的这个[操作]移除！\n\n(提示：点击”取消“可以回到[操作]编辑页)',
	/*channel_list.js*/
	FLOW_CANCEL_USE : '取消分配',
	FLOW_46 : '递归显示',
	FLOW_47 : '仅显示当前',
	FLOW_48 : '请选择一个工作流或者点击取消选择',
	FLOW_49 : '确实要取消所选栏目上的工作流分配吗?',
	FLOW_50 : '新建',
	FLOW_51 : '请选择要删除的工作流',
	FLOW_52 : '删除这个/些工作流',
	FLOW_53 : '刷新列表',
	FLOW_54 : '工作流列表管理',
	FLOW_55 : '设置',
	FLOW_56 : '栏目工作流配置',
	FLOW_57 : '请选择要取消的栏目',
	FLOW_58 : '确实要取消这些栏目上的流程吗? ',
	FLOW_59 : '配置该工作流的栏目列表',
	FLOW_60 : '选择工作流',
	FLOW_61 : '点击切换递归显示所有工作流&#13;当前为: 递归显示',
	FLOW_62 : "点击切换递归显示所有工作流&#13;当前为: ",
	FLOW_63 : '收到前',
	FLOW_64 : '收到后',
	FLOW_65 : '签收后',
	FLOW_66 : '处理中',
	FLOW_67 : '处理后',
	FLOW_68 : '要求返工后',
	FLOW_69 : '拒绝后',
	FLOW_70 : '分配工作流',
	FLOW_71 : '无法获取当前栏目[ID=',
	FLOW_72 : ']所属站点的信息！',
	FLOW_73 : '点击转到大的编辑区域',
	FLOW_74 : '编辑参数',
	FLOW_75 : '操作标签名称的长度不能大于50',
	FLOW_76 : '导入',
	FLOW_77 : '编辑',
	FLOW_78 : '导出',
	FLOW_79 : '导出这个/些工作流',
	FLOW_80 : '分配',
	FLOW_81 : '分配这个工作流到栏目',
	FLOW_82 : '您输入的操作名称不能包含以下特殊字符',
	FLOW_83 : '您输入的操作标签名称不能包含以下特殊字符',
	FLOW_84 : '点击切换递归显示所有工作流\n当前为:',
	FLOW_85 :　'',
	FLOW_86 : '请指定用户角色！',
	FLOW_87 : '点击切换到OR',
	FLOW_88 : '点击切换到AND'
});
