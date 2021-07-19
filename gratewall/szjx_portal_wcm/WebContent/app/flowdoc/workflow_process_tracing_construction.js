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
	var actionDesc = "";
	if((_actionVal & 1) != 0) actionDesc += (wcm.LANG['IFLOWCONTENT_34'] || "编辑;");
	if((_actionVal & 2) != 0) actionDesc += (wcm.LANG['IFLOWCONTENT_35'] ||"删除;");
	if((_actionVal & 4) != 0) actionDesc += (wcm.LANG['IFLOWCONTENT_36'] ||"发布;");
	return actionDesc;
}
function doCancel(){
	window.opener = null;
	window.close();
}

var PageContext = {
	params: {}
};

function initPage(_params){
	//首先判断当前浏览器是否支持floweditor
	if(flowEditor == null || flowEditor.document == null) {
		doWhenCannotDraw();
		return false;
	}

	PageContext.params = _params;
	//else
	var postData = {
		ObjectId:		_params['FlowId'],
		ContentId:		_params['cid'],
		ContentType:	_params['ctype']
	};
	var aCombine = [];
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	aCombine.push(oHelper.Combine('wcm6_process', 'toXml', postData));
	aCombine.push(oHelper.Combine('wcm6_process', 'getFlowDocsOfContent', postData));
	oHelper.MultiCall(aCombine, function(_trans, _json){
		try{
			drawFlow(_trans.responseXML, _json);
			//lock when loaded while unlock before edit window closing
			try{
				if(!PageContext.params['readonly']) {
					LockerUtil.render(_params['objectid'], 401);
				}
				if(LockerUtil.failedToLock == true) {//加锁失败时，禁用提交按钮
					window.saveBtn.disable();
				}				
			}catch(err){
				//just skip it
			}
			
		}catch(err){
			throw err;
			doWhenCannotDraw();
		}		
	});
}
function doWhenCannotDraw(){
	var sWarning = (wcm.LANG['IFLOWCONTENT_37'] ||'您当前使用的浏览器不支持TRSWCM工作流编辑器！\n\n请检查：\n 1.浏览器为IE5+；\n 2.已从服务器端下载并正确安装了Activex控件，同时该Activex已正常启用；\n 3.已将当前站点设置为信任站点。');
		
	Ext.Msg.alert(sWarning);
	try{
		//doCancel();	
	}catch(err){
		//just skip it
	}
}
var m_hFlowDocNodeMap = null;
function initFlowDocNodeMap(_json){
	_json = _json['MULTIRESULT'];
	var flowdocs = $a(_json, 'FlowDocs.FlowDoc');
	if(!flowdocs || !(flowdocs.length > 0)) {
		return;
	}

	m_hFlowDocNodeMap = {'开始' : true };

	for (var i = flowdocs.length - 1; i >= 0; i--){
		var flowdoc = flowdocs[i];
		var sNodeName = $v(flowdoc, 'node.name');
		if(sNodeName == null || $v(flowdoc, 'flag.id') == 7){//TODO 暂时无法显示强行结束节点
			continue;
		}
		//ge gfc add @ 2007-8-13 15:23
		if($v(flowdoc, 'workmodal') == 3 && $v(flowdoc, 'tousers') == '') {//并联审批结束节点，如果尚未处理就不显示
			continue;
		}
		//else
		//alert(sNodeName + ', ' + preNode + ', ' + preNode['workflag'])
		var preNode = m_hFlowDocNodeMap[$v(flowdoc, 'prenode.name')];
		if(preNode != null) {
			preNode['workflag'] = $v(flowdoc, 'flag.id');
		}

		m_hFlowDocNodeMap[sNodeName] = flowdoc;	
	}
}
function setFlowDocNodeFlag(){
	if (m_hFlowDocNodeMap == null){
		return;		
	}	
	//else 
	for (var sNodeName in m_hFlowDocNodeMap){
		var flowdoc = m_hFlowDocNodeMap[sNodeName];
		if (flowdoc == null) continue;
		//else
		if($v(flowdoc, 'worked') == 1 || sNodeName == (wcm.LANG['IFLOWCONTENT_38'] ||'开始')) {
			flowEditor.AddDocViewNode(sNodeName);
		}else{
			flowEditor.SetCurrDocViewNode(sNodeName);
		}		
	}
}

function getPrettyTime(_sDateTime, _bShortYear, _bShortTime){
	if(_sDateTime.trim() == '') return _sDateTime;

	var result = '';
	try{
		var arrPart1 = _sDateTime.split('-');
		var arrPart2 = arrPart1[2].split(' ');
		var dt = {fullYear: arrPart1[0], month: arrPart1[1], date: arrPart2[0], time: arrPart2[1]};
		if(_bShortYear != false && dt.fullYear.length > 2) {
			var temp = dt.fullYear.substr(2);
			if(parseInt(temp) != 0) {
				dt.year = temp;
			}else{
				dt.year = dt.fullYear;
			}
		}else{
			dt.year = dt.fullYear;
		}
		if(_bShortTime != false && dt.time.length > 5) {
			dt.time = dt.time.substr(0, 5);
		}
		// diff the date 
		var dtNow = new Date();
		if(dtNow.getFullYear() == parseInt(dt.fullYear)) {
			if(dtNow.getMonth() == parseInt(dt.month) - 1) {
				if(dtNow.getDate() == parseInt(dt.date)) {
					return dt.time;
				}
			}
			return dt.month + '-' + dt.date + ' ' + dt.time;
		}
		
		return dt.year + '-' + dt.month + '-' + dt.date + ' ' + dt.time;
	}catch(err){
		//TODO
		Ext.Msg.alert(String.format("[{0}]不符合[yyyy-MM-dd hh:mm:ss]格式的日期时间\n",_sDateTime) + '@' + err.message);
		return null;
	}
	return result;
}
function makeFlowDocDesc(_flowdoc){
	if(_flowdoc == null) {
		return '';
	}
	//else
	var result = '';
	result += $v(_flowdoc, 'tousers');
	var sProcessInfo = '';
	if($v(_flowdoc, 'worked') == 1) {//已经处理过
		var sWorktime = $v(_flowdoc, 'worktime');
		if(sWorktime) {
			sProcessInfo = getPrettyTime(sWorktime, true, true) + ' ' + __getActualFlagName(_flowdoc);
		}
	}else{//尚未处理
		var sReceivetime = $v(_flowdoc, 'receivetime');
		if(sReceivetime) {
			sProcessInfo = getPrettyTime(sReceivetime, true, true) + ' ' + (wcm.LANG['IFLOWCONTENT_42'] ||'收到');
		}else{
			sProcessInfo = wcm.LANG['IFLOWCONTENT_41'] ||'尚未收到';
		}		
	}
	if(sProcessInfo != '') {
		result = result +'(' + sProcessInfo + ')';
	}
	return result;
}

var m_hWorkflagNameMap = null;
function __getActualFlagName(_flowdoc){
	//alert(_flowdoc['workflag'])
	var result = wcm.LANG['IFLOWCONTENT_43'] || '处理';
	var workflag = null;;
	if(_flowdoc && (workflag = _flowdoc['workflag']) != null) {
		if(m_hWorkflagNameMap == null) {
			m_hWorkflagNameMap = {
				'2' :  wcm.LANG['IFLOWCONTENT_44'] || '要求返工',
				'7' : wcm.LANG['IFLOWCONTENT_45'] || '强行结束',
				'8' : wcm.LANG['IFLOWCONTENT_46'] || '拒绝',
				'10' : wcm.LANG['IFLOWCONTENT_47'] || '参与会签',
				'11' : wcm.LANG['IFLOWCONTENT_48'] || '参与并联审批'
			}
		}
		var workflagName = m_hWorkflagNameMap[workflag];
		if(workflagName != null) {
			result = workflagName;
		}
	}
	

	return result;
	
}
function drawFlow(_xml, _json){
	if(_xml == null){
		return;
	}
	//else
	var xmlWorkFlow = _xml.documentElement.selectNodes('*');
	m_oFlow.fromXML(xmlWorkFlow[0], true);
	initFlowDocNodeMap(_json);
	
	//添加节点
	var bNodePassed = false;
	var flowdoc = null;
	for (var nodeIndex = 0; nodeIndex < m_oFlow.nodes.length; nodeIndex++)
	{
		var aNode = m_oFlow.nodes[nodeIndex];
		if (aNode == null) continue;
		//ge gfc add @ 2007-8-10 13:42
		flowdoc = m_hFlowDocNodeMap[aNode.name];
		bNodePassed = (flowdoc != null);
		
		flowEditor.AddNodeWithPoint2(aNode.name,aNode.center.x,aNode.center.y, !bNodePassed);
		if (aNode.name ==  (wcm.LANG['IFLOWCONTENT_38'] || "开始")) continue;

		if(aNode.tousers == null || aNode.tousers.trim() == '') {
			//用户信息
			var userDesc = "";
			if(bNodePassed) {
				userDesc = makeFlowDocDesc(flowdoc);//$v(flowdoc, 'tousers');
				flowEditor.SetNodeDescription(aNode.name, userDesc, '');
			}else{
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
			}
		}else{
			if(bNodePassed) {
				userDesc = makeFlowDocDesc(flowdoc);//$v(flowdoc, 'tousers');
				flowEditor.SetNodeDescription(aNode.name, userDesc, '');				
			}else{
				flowEditor.SetNodeDescription(aNode.name, '', aNode.tousers);
			}
		}

		//
		var szNodeDesc = "";
		if (aNode.desc != "") szNodeDesc = "描述:"+ aNode.desc +"\n";
		if(aNode.name != (wcm.LANG['IFLOWCONTENT_26'] ||"结束")){
			if (actionToStr(aNode.actions) != "") szNodeDesc += ((wcm.LANG['IFLOWCONTENT_49']||"操作:")+actionToStr(aNode.actions));
				flowEditor.SetNodeDescription2(aNode.name, szNodeDesc);
		}else{
			flowEditor.SetNodeDescription2(aNode.name, "描述:" + (wcm.LANG['IFLOWCONTENT_26'] ||"结束"));
		}
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
	//initNodeFlag(xmlFlowDocNodes);
	$('spFlowName').innerHTML = m_oFlow.flowname;
	$('spFlowName').title = m_oFlow.flowdesc;
	//flowName.value = m_oFlow.flowname;
	//flowDesc.value = m_oFlow.flowdesc;
	
	flowEditor.SetViewMode(true);
	setFlowDocNodeFlag();

	flowEditor.click();
}

//load from url
Event.onDOMReady(function(){
	var params = {
		objectid: getParameter('FlowDocId'),
		cid: getParameter('ContentId'),
		ctype: getParameter('ContentType'),
		title: decodeURIComponent(getParameter('Title')),
		cruser: decodeURIComponent(getParameter('CrUser')),
		crtime: decodeURIComponent(getParameter('CrTime')),
		FlowId : getParameter('FlowId')
	};	
		$('spTitle').innerHTML		= $transHtml(params['title']||"");
	$('spTitle').title			= params['title'];
	$('spCrUser').innerHTML		= params['cruser'];
	$('spCrTime').innerHTML		= params['crtime'];
	return window.initPage(params);
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