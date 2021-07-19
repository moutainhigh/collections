var m_oFlow = new Workflow("", 0, m_sLoginUser);
var m_oTRSAction = null;
var m_oTempNode = null;
var m_oTempLine = null;

function flowNodeArgs(_obj,_Flow){
	this.obj = _obj;
	this.Flow = _Flow;
	this.userDesc = "";
	this.groupDesc = "";
}
function actionToStr(_actionVal){
	var actionDesc = [];
	if((_actionVal & 1) != 0) actionDesc.push(wcm.LANG['FLOW_EDIT'] || "编辑;");
	if((_actionVal & 2) != 0) actionDesc.push(wcm.LANG['FLOW_DELETE1'] || "删除;");
	if((_actionVal & 4) != 0) actionDesc.push(wcm.LANG['FLOW_PUBLISH'] || "发布;");
	return actionDesc.join(", ");
}
function doSave(_bSavedQuietly){
	var sFlowName = flowName.value.trim();
	var sFlowDesc = flowDesc.value.trim();
	flowName.setAttribute('_oldval', flowName.value);
	flowDesc.setAttribute('_oldval', flowDesc.value);
	//1 参数校验
	//1.1 判空和长度

	if(!ValidationHelper.doValid('divHeader', !_bSavedQuietly ? doAlert : function(){return;})) {
		return false;
	}
	//1.2 特殊字符
	var specialChars = checkSpecialChars(sFlowName);
	if(specialChars){
		if(!_bSavedQuietly) {
			 Ext.Msg.alert(String.format("您输入的工作流名称不能包含以下特殊字符 {0} ",specialChars), function(){
            }); 
		}
		return false;
	}

	//2 准备参数
	m_oFlow.setName(sFlowName);
	m_oFlow.setDesc(sFlowDesc);
	var params = {
		objectid: PageContext.params['FlowId'],
		flowXml: m_oFlow.toXML(),
		ownerType: ownerType, 
		ownerId: ownerId,
		'UpdateOptMarkEnum': true 
	}

	//3 提交数据
	if(!_bSavedQuietly) {
		//$beginSimplePB(wcm.LANG.FLOW_5 || '正在保存工作流..', 2);
	}

	BasicDataHelper.call('wcm6_process', 'fromXML', params, true, function(_trans, _json){

		window.m_bModified = false;
		if(_bSavedQuietly) {
			return false;
		}
		//$endSimplePB();
		var bIsSucc = ($v(_json, 'REPORTS.IS_SUCCESS') == 'true');
		if(bIsSucc) {
			Element.hide('divFlowEditor');
			var args = {
				id: $v(_json, 'REPORTS.OBJECTIDS.OBJECTID'),
				name: sFlowName 
			};
			var cbr = wcm.CrashBoarder.get("Dialog_Workflow_Editor");
			cbr.hide();
			if(PageContext.LoadView == 2) {
				Ext.Msg.$timeAlert(wcm.LANG['FLOW_6'] || '工作流保存成功！', 3, function(){
					cbr.notify(args);
				});
			}
			return false;
		}
		//else 保存失败时，提示失败的信息
		Element.hide('divFlowEditor');
		Ext.Msg.report(_json, wcm.LANG['FLOW_7'] || '工作流保存结果', function(){
			Element.show('divFlowEditor');
		});
	}, function(_trans, _json){
		if(_bSavedQuietly) {
			return;
		}
		Element.hide('divFlowEditor');
		$render500Err(_trans, _json, false, function(){
			Element.show('divFlowEditor');
		});
	});
	return false;
}

function saveFlow(_bSavedQuietly){
	var sFlowName = flowName.value.trim();
	var sFlowDesc = flowDesc.value.trim();
	flowName.setAttribute('_oldval', flowName.value);
	flowDesc.setAttribute('_oldval', flowDesc.value);
	//1 参数校验
	//1.1 判空和长度
	if(!ValidationHelper.doValid('divHeader', !_bSavedQuietly ? doAlert : function(){return;})) {
		return false;
	}
	//1.2 特殊字符
	var specialChars = checkSpecialChars(sFlowName);
	if(specialChars){
		if(!_bSavedQuietly) {
			 Ext.Msg.alert(String.format("您输入的工作流名称不能包含以下特殊字符 {0}" ,specialChars), function(){
            }); 
		}
		return false;
	}

	//2 准备参数
	m_oFlow.setName(sFlowName);
	m_oFlow.setDesc(sFlowDesc);
	var params = {
		objectid: PageContext.params['FlowId'],
		flowXml: m_oFlow.toXML(),
		ownerType: ownerType, 
		ownerId: ownerId,
		'UpdateOptMarkEnum': true 
	}

	//3 提交数据
	if(!_bSavedQuietly) {
		//$beginSimplePB(wcm.LANG.FLOW_5 || '正在保存工作流..', 2);
	}
	
	BasicDataHelper.call('wcm6_process', 'fromXML', params, true, function(_trans, _json){
		window.m_bModified = false;
		if(_bSavedQuietly) {
			return false;
		}
		//$endSimplePB();
		var bIsSucc = ($v(_json, 'REPORTS.IS_SUCCESS') == 'true');
		if(bIsSucc) {
			//Element.hide('divFlowEditor');
			var args = {
				id: $v(_json, 'REPORTS.OBJECTIDS.OBJECTID'),
				name: sFlowName 
			};
			if(PageContext.LoadView == 2) {
				Ext.Msg.$timeAlert(wcm.LANG['FLOW_6'] || '工作流保存成功！', 3, function(){
					$('tblEditorContainer').focus();
					$MsgCenter.$main().afteredit();
				});
			}
			return false;
		}
		//else 保存失败时，提示失败的信息
		Element.hide('divFlowEditor');
		Ext.Msg.report(_json, wcm.LANG['FLOW_7'] || '工作流保存结果', function(){
			Element.show('divFlowEditor');
		});
	}, function(_trans, _json){
		if(_bSavedQuietly) {
			return false;
		}
		Element.hide('divFlowEditor');
		$render500Err(_trans, _json, false, function(){
			Element.show('divFlowEditor');
		});
	});
	return false;
}

function doCancel(){
	if (window.parent && window.parent.notifyParent2CloseMe){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
	}
}
function doAlert(_sAlertion, _sFocusId){
	Element.hide('divFlowEditor');
	 Ext.Msg.alert(_sAlertion, function(){
		$(_sFocusId).focus();
		$(_sFocusId).select();	
		Element.show('divFlowEditor');
	});
}
var PageContext = {
	params: {}
};

function init(_params){
	if(screen.width <= 1024){
		$('flowName').style.width = "80px";
		$('flowDesc').style.width = "80px";
		$('selInfoviews').style.width = "100px";
	}
	//ValidationHelper.initValidation();
	//首先判断当前浏览器是否支持floweditor
	if(flowEditor == null || flowEditor.document == null) {
		doWhenCannotDraw();
		return;
	}
	PageContext.params = _params;
	//如果是新建的话，不需要从服务器端获取数据
	if(_params == null || _params.FlowId == 0 || _params.FlowId.trim() == '') {
		try{
			drawFlow();
		}catch(err){
			doWhenCannotDraw();
		}	
		return;
	}
	//else
	BasicDataHelper.call('wcm6_process', 'toXml', {objectid: _params['FlowId']}, false, function(_trans, _json){
		try{
			drawFlow(_trans.responseXML);
			//lock when loaded while unlock before edit window closing
			try{
				if(!PageContext.params['readonly']) {
					LockerUtil.render(_params['FlowId'], 401, null, function(){
						if(wcmXCom.get('btnSubmit'))
							wcmXCom.get('btnSubmit').disable();
						if($('saveflowbtn'))
							$('saveflowbtn').disabled = true;
					});
				}
				if(LockerUtil.failedToLock == true) {//加锁失败时，禁用提交按钮
					if(wcmXCom.get('btnSubmit'))
						wcmXCom.get('btnSubmit').disable();
					if($('saveflowbtn'))
						$('saveflowbtn').disabled = true;
				}				
			}catch(err){
				//just skip it
			}
		}catch(err){
			Ext.Msg.alert(err.message);
			//doWhenCannotDraw();
		}		
	});
}

function loadInfoview(_nInfoviewId){
	//TODO 可能需要限定选取表单的范围 
	//gfc add @ 2008-2-19 17:06 判定是否配置了表单，否则不再执行后面的请求操作
	try{
		if(infoview_config.enable != true) {
			return;
		}
	}catch(err){
		//just skip it
		return;
	}

	var params = {
		infoviewId: _nInfoviewId,
		pagesize : -1
	};
	BasicDataHelper.call('wcm6_infoview', 'queryFlowInfoviews', params, true, function(_trans, _json){
		var arInfoviews = $a(_json, 'Infoviews.Infoview');
		if(!arInfoviews || !(arInfoviews.length > 0)) {
			Element.update('spLoading', wcm.LANG['FLOW_8'] || '无表单可供选择');
			return;
		}
		//else
		eOptions = $('selInfoviews').options;
		if(_nInfoviewId > 0) {//编辑，不可重新选
			$('selInfoviews').disabled = true;
			if(eOptions.length > 0) {
				for (var i = eOptions.length; i >0;){
					eOptions.remove(--i);
				}
			}
		}
		for (var i = arInfoviews.length-1; i >=0; i--){	
			var infoview = arInfoviews[i];
			var eOption = document.createElement("OPTION");
			eOptions.add(eOption);
			if(infoviewId && infoviewId == $v(infoview, 'infoviewId')){
				eOption.selected = "true";
				$('selInfoviews').disabled = true;
			}
			eOption.value = $v(infoview, 'infoviewId');
			eOption.innerText = $v(infoview, 'infoviewName');
			eOption.title = $v(infoview, 'infoviewDesc');
		}
		//Element.hide('spLoading');
		Element.show('spInfoviews');
	});
	
	Object.extend(m_oFlow, {
		getInfoviewId : function(){
			var nInfoviewId = parseInt($('selInfoviews').value);
			if(isNaN(nInfoviewId)) {
				nInfoviewId = 0;
			}

			return nInfoviewId;
		}
	});
}
function doWhenCannotDraw(){
	var btn = wcmXCom.get('btnSubmit')
	if(btn) btn.disable();
	if($('ButtonContainer'))$('ButtonContainer').style.display = 'none';
	window.m_bModified = false;
	
	var sWarning = '您当前使用的浏览器不支持TRSWCM工作流编辑器！\n\n请检查：\n 1.浏览器为IE5+；\n 2.已从服务器端下载并正确安装了Activex控件，同时该Activex已正常启用；\n 3.已将当前站点设置为信任站点。';
		 
		Ext.Msg.alert(sWarning, function(){});

	try{
		doCancel();	
	}catch(err){
		//just skip it
	}
}
function drawFlow(_xml){
	if(_xml != null){
		m_oFlow.fromXML(_xml);
		var nInfoviewId = parseInt(m_oFlow['infoviewid']);
		if(isNaN(nInfoviewId)) {
			nInfoviewId = 0;
		}
		loadInfoview(nInfoviewId);
	}
	else{ //新建
		var startNode = new WorkflowNode(0, wcm.LANG['FLOW_START'] || '开始', m_sLoginUser, '');
		startNode.center = new WorkflowPoint(10 ,10);
		m_oFlow.addNode(startNode);

		var endNode = new WorkflowNode(0, wcm.LANG['FLOW_FINISH'] || '结束', m_sLoginUser, '');
		endNode.center = new WorkflowPoint(200,200);
		m_oFlow.addNode(endNode);

		loadInfoview(0);
	}

	//添加节点
	for (var nodeIndex = 0; nodeIndex < m_oFlow.nodes.length; nodeIndex++)
	{
		var aNode = m_oFlow.nodes[nodeIndex];
		if (aNode == null) continue;
		flowEditor.AddNodeWithPoint2(aNode.name,aNode.center.x,aNode.center.y, false);
		if ((aNode.name == (wcm.LANG['FLOW_START'] || "开始")) || (aNode.name == (wcm.LANG['FLOW_FINISH'] || "结束"))) continue;

		if(aNode.tousers == null || aNode.tousers.trim() == '') {
			//用户信息
			var userDesc = "";
			for (var userIndex = 0; userIndex < aNode.users.length; userIndex++)
			{
				//var aUser = m_oFlow.findUserByID(aNode.users[userIndex]);
				var aUser = aNode.users[userIndex];
				if (aUser == null) continue;
				if (userDesc == "") userDesc = aUser.userName;
				else userDesc += "," + aUser.userName;
			}
			//组信息
			var groupDesc = "";
			for (var groupIndex = 0; groupIndex < aNode.groups.length; groupIndex++)
			{
				//var aGroup = m_oFlow.findGroupByID(aNode.groups[groupIndex]);
				var aGroup = aNode.groups[groupIndex];
				if (aGroup == null) continue;
				if (groupDesc == "") groupDesc = aGroup.groupName;
				else groupDesc += "," + aGroup.groupName;
			}
			flowEditor.SetNodeDescription(aNode.name, userDesc, groupDesc);
		}else{
			flowEditor.SetNodeDescription(aNode.name, '', aNode.tousers);
		}

		//
		var szNodeDesc = "";
		//if (nodeProp.desc != "") szNodeDesc = "描述:"+ aNode.desc +"\n";
		if ( aNode.desc != "" ) szNodeDesc = String.format("描述:{0}\n",aNode.desc);
		if (actionToStr(aNode.actions) != "") szNodeDesc += ((wcm.LANG['FLOW_OPER'] || "操作:") + actionToStr(aNode.actions));
		flowEditor.SetNodeDescription2(aNode.name, szNodeDesc);
	}
	//添加连线
	for (var nodeIndex = 0; nodeIndex < m_oFlow.nodes.length; nodeIndex++)
	{
		var aNode = m_oFlow.nodes[nodeIndex];
		if (aNode == null) continue;
		for (var lineIndex = 0; lineIndex < aNode.lines.length; lineIndex++)
		{
			var aLine = aNode.lines[lineIndex];
			if (aLine == null) continue;
			flowEditor.AddLine(aLine.start, aLine.end);
			flowEditor.SetLineDescription(aLine.start, aLine.end, aLine.bName);
			flowEditor.SetLineNotifyType(aLine.start, aLine.end , getAdaptedNotifyType(aLine.notifyType));
		}
	}
	//fang.xiang@2002-03-20:添加工作的名称和描述
	flowName.value = m_oFlow.flowname;
	flowDesc.value = m_oFlow.flowdesc;
	flowName.setAttribute('_oldval', m_oFlow.flowname);
	flowDesc.setAttribute('_oldval', m_oFlow.flowdesc);
	
	flowEditor.click();

	if(PageContext.params['readonly']) {
		flowName.readOnly = true;
		flowDesc.readOnly = true;
		Element.addClassName(flowName, 'readonly_text');
		Element.addClassName(flowDesc, 'readonly_text');
		flowEditor.SetViewMode(true);
		//Element.hide('divOptions');
		//Element.show('divTips');
	}else{
		observeEvents();
		//Element.hide('divTips');
		//Element.show('divOptions');
	}
}

var m_hObserversMap = [];
function observe4Editor(_sName, _fun){
	flowEditor.attachEvent(_sName, _fun);
	m_hObserversMap.push(_fun);
}
function stopAllObservings4Editor(){
	for( var sName in m_hObserversMap){
		flowEditor.detachEvent(sName, m_hObserversMap[sName]);
	}
	delete m_hObserversMap;
}
Event.observe(window, 'unload', function(){
	try{
		stopAllObservings4Editor();
	}catch(err){
		//just skip it
	}
}, false);

function observeEvents(){
	//弹出编辑flow属性的窗口
	observe4Editor('ShowFlowProp', function(){
		window.m_bModified = true;
		m_oFlow.flowname = flowName.value;
		m_oFlow.flowdesc = flowDesc.value;
		m_oTRSAction = new CTRSAction("./flow_property.html");
		var bReturnValue = m_oTRSAction.doDialogAction(560, 520, m_oFlow);

		if (bReturnValue){
			flowName.value = m_oFlow.flowname;
			flowDesc.value = m_oFlow.flowdesc;
		}	
	});
	//新建节点
	observe4Editor('NewNode', function(){
		window.m_bModified = true;
		var nodeProp = new WorkflowNode( 0 , wcm.LANG['FLOW_NODE'] || '节点', m_sLoginUser, '') ;
		nodeProp.setPoint( flowEditor.GetPointX(),flowEditor.GetPointY() );
		var dlgArgs = new flowNodeArgs( nodeProp , m_oFlow );
		//TODO 弹出编辑flowNode属性的窗口
		m_oTRSAction = new CTRSAction("./flownode_property.html");
		var bResult = m_oTRSAction.doDialogAction(560, 560, dlgArgs);
		if(bResult){
			//如果不存在同名的工作流节点，则创建新的工作流节点
			nodeProp.updated = true;
			flowEditor.AddNode(nodeProp.name);
			m_oFlow.addNode( nodeProp );
			
			var szNodeDesc = "";
			if ( nodeProp.desc != "" )
				szNodeDesc = (wcm.LANG['FLOW_DESCRPTION'] || "描述:")+ nodeProp.desc +"\n";
			if ( actionToStr(nodeProp.actions) != "" )
				szNodeDesc = szNodeDesc + (wcm.LANG['FLOW_OPER'] || "操作:")+actionToStr(nodeProp.actions);
			
			flowEditor.SetNodeDescription( nodeProp.name , dlgArgs.userDesc , dlgArgs.groupDesc );
			flowEditor.SetNodeDescription2( nodeProp.name , szNodeDesc );
			flowEditor.DoRefresh();
		}//*/	
	});

	//显示节点属性
	observe4Editor('ShowNodeProp', function(){
		window.m_bModified = true;
		var oldNodeName = flowEditor.GetCurrentNodeName();
		var nodeProp = m_oFlow.findNodeByName(flowEditor.GetCurrentNodeName());
		var dlgArgs = new flowNodeArgs(nodeProp, m_oFlow);

		//TODO 弹出查看flowNode属性的窗口
		m_oTRSAction = new CTRSAction("./flownode_property.html");
		var bResult = m_oTRSAction.doDialogAction(560, 560, dlgArgs);

		if(bResult){
			nodeProp.updated = true;
			flowEditor.SetNodeName(oldNodeName, nodeProp.name);
			flowEditor.SetNodeDescription(nodeProp.name, dlgArgs.userDesc, dlgArgs.groupDesc);
			//
			var szNodeDesc = "";
			if(nodeProp.desc != "")
				szNodeDesc = (wcm.LANG['FLOW_DESCRPTION'] || "描述:")+ nodeProp.desc +"\r\n";
			if(actionToStr(nodeProp.actions) != "")
				szNodeDesc = szNodeDesc + (wcm.LANG['FLOW_OPER'] || "操作:")+actionToStr(nodeProp.actions);
			//
			flowEditor.SetNodeDescription2(nodeProp.name, szNodeDesc);
			if(oldNodeName != nodeProp.name){
				m_oFlow.renameNode(oldNodeName, nodeProp.name);
			}
		}//*/	
	});
	//删除节点前的处理
	observe4Editor('PreDeleteNode', function(){
		m_oTempNode = flowEditor.GetCurrentNodeName();
	});

	//删除节点后的处理
	observe4Editor('PostDeleteNode', function(){
		window.m_bModified = true;
		if ( m_oTempNode != null ){
			m_oFlow.removeNode( m_oTempNode ); 
			m_oTempNode = null;
		}	
	});

	//移动节点前的处理
	observe4Editor('PreNodeMove', function(){
	
	});
	//移动节点后的处理
	observe4Editor('PostNodeMove', function(){
		window.m_bModified = true;
		var strEndName = flowEditor.GetCurrentNodeName();
		var nodeStart = m_oFlow.findNodeByName( strEndName );
		if ( nodeStart != null ){
			nodeStart.setPoint( flowEditor.getCurrentPointX(),flowEditor.getCurrentPointY() );
		}
	});
	//Trashed
	observe4Editor('NodeEvent', function(){
	});

	//设置结束节点的处理
	observe4Editor('SetFinishNode', function(){
	});
	//设置开始节点的处理
	observe4Editor('SetStartNode', function(){
	});
	observe4Editor('PreNodeEvent', function(){
	});
	observe4Editor('PostNodeEvent', function(){
	});

	//添加新的连线
	observe4Editor('NewLine', function(){
		window.m_bModified = true;
		var strStartName = flowEditor.GetDropNodeName();
		var strEndName = flowEditor.GetCurrentNodeName();
		if ( strStartName != null && strEndName != null && strStartName.length > 0  && strEndName.length > 0 ){
			var newLine = new WorkflowLine( strEndName , strStartName , "1" , wcm.LANG['FLOW_NEWSTATUS'] || "新稿状态" );
			var nodeStart = m_oFlow.findNodeByName( strEndName );

			//TODO 弹出编辑flowLine属性的窗口
			m_oTRSAction = new CTRSAction("./flowline_property.html");
			var bReturnValue = m_oTRSAction.doDialogAction(400, 350, newLine);
			if(bReturnValue){
				nodeStart.addLine( newLine );
				flowEditor.AddLine( strEndName , strStartName );
				flowEditor.SetLineDescription( "" , "" , newLine.bName );
				flowEditor.SetLineNotifyType( "","", getAdaptedNotifyType(newLine.notifyType) );
			}//*/
		} else {
			var nodeStart = Flow.findNodeByName( strEndName );
			if ( nodeStart != null )	{
				nodeStart.setPoint( flowEditor.getCurrentPointX(),flowEditor.getCurrentPointY() );
			}
		}
	});
	//显示连线属性
	observe4Editor('ShowLineProp', function(){
		window.m_bModified = true;
		var lineName = flowEditor.GetCurrentLineName();
		var nodeNames = lineName.toString().split("-");
		var nodeStart = m_oFlow.findNodeByName(nodeNames[1]);
		var lineProp = nodeStart.findLineByEnd(nodeNames[0]);
		if(lineProp != null){
			nodeStart.updated = true;
			//TODO 弹出查看flowLine属性的窗口
			m_oTRSAction = new CTRSAction("./flowline_property.html");
			var bReturnValue = m_oTRSAction.doDialogAction(400, 350, lineProp);
			if(bReturnValue){
				flowEditor.SetLineDescription("", "", lineProp.bName);
				flowEditor.SetLineNotifyType("","",getAdaptedNotifyType(lineProp.notifyType));
				flowEditor.DoRefresh();
			}//*/
		}
	});
	//删除连线前的处理
	observe4Editor('PreDeleteLine', function(){
		m_oTempLine = flowEditor.GetCurrentLineName();
	});
	//删除连线后的处理
	observe4Editor('PostDeleteLine', function(){
		window.m_bModified = true;
		if ( m_oTempLine != null ){
			var nodeNames = m_oTempLine.toString().split("-");
			var nodeStart = m_oFlow.findNodeByName( nodeNames[1] );
			nodeStart.removeLine( nodeNames[0] );
			m_oTempLine = null;
		}
	});
}

//load from url
Event.onDOMReady(function(){
	var params = {
		FlowId: getParameter('FlowId')
	};	
	var nLoadView = parseInt(getParameter('LoadView'));
	if(isNaN(nLoadView) && window!=top) {
		return;
	}
	//else
	if(nLoadView == 1) {
		Object.extend(params, {
			readonly: 1
		});
		Element.hide('spCloseWin');
		//$('divTips').style.background = '#fff';
	}
	if(nLoadView == 2) {
		//$('divOptions').style.background = '#fff';
	}
	PageContext.LoadView = nLoadView || (window!=top?2:0);;
	if(parseInt(getParameter('ShowButtons'))==1){
		window.init(params);
	}
});

//为了让编辑器的连线上的通知方式图标能正确显示，将第0位和第1位调换（email和message颠倒了）
function getAdaptedNotifyType(_rawType){
	var temp = _rawType & 3;
	if(temp != 1 && temp != 2) {
		return _rawType;
	}	
	//else
	var result = _rawType;
	result ^= 3;

	//alert(_rawType + ', ' + result)
	return result;
}