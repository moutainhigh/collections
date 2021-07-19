/////////Line Object Begin/////////
WorkflowLine = Class.create('wcm.WorkflowLine');
WorkflowLine.prototype = {
	initialize : function(_start, _end, _status, _desc, _bname, _operationMark, _operationMarkEnable){
		this.start = _start;
		this.end = _end;
		this.status = _status;
		this.statusDesc = _desc || '';

		//fangxiang@2002-03-27:连线默认通知方式是短消息(2)
		this.notifyType = 1;
		this.updated = false;
		this.branchID = 0;
		this.nodeID = 0;

		//gfc add @ 2007-6-18 17:31 指定分支的处理名称
		this.bName = _bname || '';

		//ge gfc add @ 2007-10-18 17:28 增加操作标签
		this.operationMark = _operationMark;
		this.operationMarkEnable = _operationMarkEnable
	},
	getProps : function(){
		var result = {
			'BRANCHID' : this.branchID,
			'NODEID' : this.nodeID,
			'NEXTNAME' : this.end,
			'STATUS' : this.status,
			'NOTIFY' : this.notifyType,
			'BNAME' : this.bName,
			'OPERATIONMARK' : this.operationMark,
			'OPERATIONMARKENABLE' : this.operationMarkEnable == 1 ? '1' : '0'
		};
		return result;
	},
	setStatus : function(){
		this.status = _status;
		this.desc = _desc;		
	}
}

////////WorkflowPoint Object Begin/////////
WorkflowPoint = Class.create('wcm.WorkflowPoint');
WorkflowPoint.prototype = {
	initialize : function(_x, _y){
		this.x = _x;
		this.y = _y;
	}
}
////////Node Object Begin/////////
WorkflowNode = Class.create('wcm.WorkflowNode');
WorkflowNode.prototype = {
	initialize : function(_id, _name, _user,_desc){
		this.name = _name || '';
		this.desc = _desc || '';
		this.id = _id;
		this.bReasignUsers = 1;
		this.bReasignSepNodes = 1
		
		this.lines = new Array();
		this.users = new Array();
		this.groups = new Array();
		this.rules = [];

		this.cruser = _user;
		this.center = new WorkflowPoint(0, 0);
		this.updated = false;
		this.actions = 3;
		this.WorkflowID = 0;
		this.together = 0;

		this.workmodal = 0;

		//caohui 2003-6-11
		//增加记录事件的XML属性
		this.eventXML = "<WCMFLOWNODEEVENTS Version=\"6.0\" />";

		//ge gfc add @ 2007-6-21 16:33 增加接收规则字段
		this.tousers = null;
		this.tousersparams = null;

		this.properties = {};
	},
	getProps : function(){
		var result = {
			'NODEID' : this.id,
			'NODENAME' : this.name,
			'NODEDESC' : this.desc,
			'BREASIGNUSERS':this.bReasignUsers,
			'BREASIGNSEPNODES':this.bReasignSepNodes,
			'CENTERX' : this.center.x,
			'CENTERY' : this.center.y,
			'ACTIONS' : this.actions,
			'TOGETHER' : (this.workmodal == 1) ? '1' : '0',//this.together,
			'TOUSERSCREATOR' : this.tousers ==null ? "" : this.tousers,
			'TOUSERSCREATORPARAMS' : this.tousersparams,
			'WORKMODAL' : this.workmodal
		};
		return result;
	},
	setPoint : function(_x, _y){
		this.updated = true;
		this.center.x = _x;
		this.center.y = _y;
	},

	getPoint : function(){
		return this.center;
	},

	setName : function(_name){
		this.name = _name;
	},

	addUser : function(_userName, _userId){
		this.users.push(new WorkflowUser(_userName, _userId));
	},
	addField : function(_props){
		this.fields.push(new WorkflowField(_props));
	},
	clearUsers : function(){
		if(this.users.length == 0) {
			return;
		}
		//else
		for (var i = 0; i < this.users.length; i++){
			delete this.users[i];
		}
		this.users = [];
	},
	clearFields : function(){
		if(this.fields.length == 0) {
			return;
		}
		//else
		for (var i = 0; i < this.fields.length; i++){
			delete this.fields[i];
		}
		this.fields = [];
	},
	findUserByID : function(_id){
		for(var i = 0; i < this.users.length; i++){
			if(this.users[i].userID == _id){
				return this.users[i];
			}
		}
		return null;
	},

	findGroupByID : function(_id){
		for(var i = 0; i < this.groups.length; i++){
			if(this.groups[i].groupID == _id){
				return this.groups[i];
			}
		}
		return null;
	},

	addGroup : function(_groupName, _groupId){
		this.groups.push(new WorkflowGroup(_groupName, _groupId));
	},
	clearGroups : function(){
		if(this.groups.length == 0) {
			return;
		}
		//else
		for (var i = 0; i < this.groups.length; i++){
			delete this.groups[i];
		}
		this.groups = [];
	},
	findLineByEnd : function(_end){
		for(var i = 0; i < this.lines.length; i++){
			if(this.lines[i].end == _end) return this.lines[i];
		}
		return null;
	},

	addLine : function(_line){
		this.updated = true;
		this.lines.push(_line);
	},

	removeLine : function(_end){
		for(var i = 0; i < this.lines.length; i++){
			if(this.lines[i].end == _end){
				this.lines.splice(i, 1);
				break;
			}
		}
		return null;
	}
}
////////Node Object End/////////

////////WorkflowGroup Object Begin/////////
WorkflowGroup = Class.create('wcm.WorkflowGroup');
WorkflowGroup.prototype = {
	initialize : function(_groupName, _groupID){
		this.groupName = _groupName;
		this.groupID = _groupID;
	}
}
////////WorkflowGroup Object End/////////

////////WorkflowUser Object Begin/////////
WorkflowUser = Class.create('wcm.WorkflowUser');
WorkflowUser.prototype = {
	initialize : function(_userName, _userID){
		this.userName = _userName;
		this.userID = _userID;
	}
}
////////WorkflowUser Object End/////////
////////WorkflowCondition Object Begin/////////
WorkflowCondition = Class.create('wcm.WorkflowCondition');
WorkflowCondition.prototype = {
	initialize : function(_props){
		Object.extend(this, _props);
	}
}
////////WorkflowCondition Object End/////////
////////WorkflowAction Object Begin/////////
WorkflowAction = Class.create('wcm.WorkflowAction');
WorkflowAction.prototype = {
	initialize : function(_props){
		Object.extend(this, _props);
	}
}
////////WorkflowAction Object End/////////
////////WorkflowRule Object Begin/////////
WorkflowRule = Class.create('wcm.WorkflowRule');
WorkflowRule.prototype = {
	initialize : function(_props){
		Object.extend(this, _props);
		this.conditions = [];
		this.actions = [];
	}
}
WorkflowRule.create = function(_arRules){
	var result = [];
	var rules = WorkflowHelper.createNodes('WorkflowRule', _arRules);
	for (var ruleIndex = 0; ruleIndex < _arRules.length; ruleIndex++){
		var aNode = _arRules[ruleIndex];
		var aRule = rules[ruleIndex];
		aRule.conditions = WorkflowHelper.createNodes('WorkflowCondition', aNode.selectNodes('XWCMFLOWCONDITIONS/XWCMFLOWCONDITION'));
		aRule.actions = WorkflowHelper.createNodes('WorkflowAction', aNode.selectNodes('XWCMFLOWACTIONS/XWCMFLOWACTION'));
		result.push(aRule);
	}
	return result;
}
////////WorkflowRule Object End/////////
////////WorkflowField Object Begin/////////
WorkflowField = Class.create('wcm.WorkflowField');
WorkflowField.prototype = {
	initialize : function(_props){
		Object.extend(this, _props);
	},
	getProps : function(){
		var result = {
			'FIELDNAME'			: this.fieldname,
			'FIELDTYPE'			: this.fieldtype,
			'INITVALUE'			: this.initvalue,
			'READFIELD'			: this.readfield,
			'WRITEFIELD'		: this.writefield,
			'INITVALUECREATOR'	: this.initvaluecreator
		};
		return result;
	},
	getProps2 : function(){
		var result = {
			'fieldname'			: this.fieldname,
			'fieldtype'			: this.fieldtype,
			'initvalue'			: this.initvalue,
			'readfield'			: this.readfield,
			'writefield'		: this.writefield,
			'initvaluecreator'	: this.initvaluecreator
		};
		return result;		
	}
}
WorkflowField.create = function(_arFields){
	var result = [];
	var fields = WorkflowHelper.createNodes('WorkflowField', _arFields);
	for (var fieldIndex = 0; fieldIndex < _arFields.length; fieldIndex++){
		var aField = fields[fieldIndex];
		result.push(aField);
	}
	return result;
}
////////WorkflowField Object End/////////
////////Workflow Object Begin/////////
Workflow = Class.create('wcm.Workflow');
Workflow.prototype = {
	initialize : function(_name, _id, _user){
		//已经修改为flowname, flowid
		this.flowname = _name || '';
		this.flowid = _id;
		this.nodes = [];
		this.monUsers = [];
		this.rules = [];
		this.flowdesc = '';
		this.cruser = _user;
		//TODO 暂时为 siteid
		this.ownerType = 0;
		this.ownerId = 0;
		
		this.sname = wcm.LANG['FLOW_START'] || "开始";
		this.ename = wcm.LANG['FLOW_FINISH'] || "结束";
	},
	getProps : function(){
		var result = {
			'FLOWID' : this.flowid,
			'FLOWNAME' : this.flowname,
			'FLOWDESC' : this.flowdesc,
			'SNAME' : this.sname,
			'ENAME' : this.ename,
			'SITEID' : this.siteid
		};
		if(this.getInfoviewId) {
			result['INFOVIEWID'] = this.getInfoviewId();
		}
		

		return result;
	},
	addNode : function(_node){
		this.nodes.push(_node);
	},
	setName : function(_name){
		this.flowname = _name;
	},
	setDesc : function(_desc){
		this.flowdesc = _desc;
	},
	getNodes : function(){
		return this.nodes;
	},
	findNodeByID : function(_id){
		for(var i = 0; i < this.nodes.length; i++){
			if(this.nodes[i].id == _id) return this.nodes[i];
		}
		return null;
	},
	findNodeByName : function(_name){
		for(var i = 0; i < this.nodes.length; i++){
			if(this.nodes[i].name == _name) return this.nodes[i];
		}
		return null;
	},

	removeNode : function(_nodeName){
		for(var i = 0; i < this.nodes.length; i++){
			if(this.nodes[i].name == _nodeName){
				this.nodes.splice(i, 1);
				//
				break;
			}
		}
		//remove the lines end with this node
		for(var i = 0; i < this.nodes.length; i++){
			for(var j = 0; j < this.nodes[i].lines.length; j++){
				if(this.nodes[i].lines[j].end == _nodeName){
					this.nodes[i].lines.splice(j, 1);
					break;
				}
			}
		}
	},

	renameNode : function(_oldNodeName, _newNodeName){
		//rename Node Name
		for(var i = 0; i < this.nodes.length; i++){
			for(var j = 0; j < this.nodes[i].lines.length; j++){
				if(this.nodes[i].lines[j].end == _oldNodeName){
					this.nodes[i].lines[j].end = _newNodeName;
				}else if(this.nodes[i].lines[j].start == _oldNodeName){
					this.nodes[i].lines[j].start = _newNodeName;
				}
			}
		}
	},

	getUserNamesByIds : function(_sUserIds){
		var sUserIds = _sUserIds || "";
		if(!sUserIds || sUserIds.length <= 0 || sUserIds.toLowerCase() == "null")
			return;
		var sUrl = '../auth/username_get_by_ids.jsp';
		var options = {
			method: 'get', 
			asynchronous: false, 
			parameters: 'UserIds=' + sUserIds + '&FromProcess=1'
		};
		var result = this.__requestRelativeInfo('../auth/username_get_by_ids.jsp', options);
		return result.trim();
	},

	getGroupNamesByIds : function(_sGroupIds){
		var sGroupIds = _sGroupIds || "";
		if(!sGroupIds || sGroupIds.length <= 0 || sGroupIds.toLowerCase() == "null")
			return;
		var options = {
			method: 'get', 
			asynchronous: false, 
			parameters: 'GroupIds=' + sGroupIds + '&FromProcess=1'
		};
		var result = this.__requestRelativeInfo('../auth/grpname_get_by_ids.jsp', options);
		return result.trim();
	},

	__requestRelativeInfo : function(_sUrl, _options){
		var ajaxRequest = new Ajax.Request(_sUrl, _options);
		var isNotLogin = ajaxRequest.header('TRSNotLogin');
		if(isNotLogin=='true' && window.top.DoNotLogin){
			window.top.DoNotLogin();
			return null;
		}
		return ajaxRequest.transport.responseText;
	},

	toXML : function(){
		var pNodes = [];
		for (var iNodeIndex = 0; iNodeIndex < this.nodes.length; iNodeIndex++){
			var node = this.nodes[iNodeIndex];
			var pNode = {'PROPERTIES' : node.getProps()};
			//lines
			var pLines = [];
			for (var iLineIndex = 0; iLineIndex < node.lines.length; iLineIndex++){
				var pLine = {'PROPERTIES' : node.lines[iLineIndex].getProps()};
				pLines.push({'WCMFLOWBRANCH' : pLine});
			}
			pNode['WCMFLOWBRANCHS'] = pLines;
			//rules
			this.__appendRulesJSON(pNode, node.rules);
			//users and groups
			var arUserNames = [], arGroupNames = [];
			for (var iUserIndex = 0; iUserIndex < node.users.length; iUserIndex++){
				arUserNames.push({
					'WCMUSER' : {
						'PROPERTIES' : {
							'USERID' : node.users[iUserIndex]['userID'] 
						}
					}
				});
			}
			for (var iGroupIndex = 0; iGroupIndex < node.groups.length; iGroupIndex++){
				arGroupNames.push({
					'WCMGROUP' : {
						'PROPERTIES' : {
							'GROUPID' : node.groups[iGroupIndex]['groupID'] 
						}
					}
				});
			}
			pNode['WCMUSERS'] = arUserNames;
			pNode['WCMGROUPS'] = arGroupNames;

			//ge gfc add @ 2007-9-25 15:24 增加表单字段的提交
			/*var arFlowFields = [];
			for (var iFieldIndex = 0; iFieldIndex < node.fields.length; iFieldIndex++){
				alert(node.fields[iFieldIndex][''])
				arFlowFields.push({
					'XWCMFLOWNODEFIELD' : {
						'PROPERTIES' : WorkflowHelper.renderCollection(_rules, 'XWCMFLOWRULE', 'flowruleid')node.fields[iFieldIndex].getProps()
					}
				});
			}//*/
			pNode['XWCMFLOWNODEFIELDS'] = WorkflowHelper.renderCollection(node.fields, 'XWCMFLOWNODEFIELD', null);

			pNodes.push({'WCMFLOWNODE' : pNode});
		}
		var pFlow = {
			'PROPERTIES' : this.getProps(),
			'WCMFLOWNODES' : pNodes
		};
		//rules
		this.__appendRulesJSON(pFlow, this.rules);
		
		var result = com.trs.util.JSON.parseJson2Xml('WCMFLOW', pFlow, true);
		return result.xml;
	},
	__appendRulesJSON : function(_obj, _rules){
		if(_obj == null || _rules == null || _rules.length == 0) {
			return;
		}
		//else
		var pRules = WorkflowHelper.renderCollection(_rules, 'XWCMFLOWRULE', 'flowruleid');
		for (var i = 0; i < _rules.length; i++){
			var rule = _rules[i];
			var pRule = pRules[i];
			if(rule.conditions.length != 0) {
				pRule['XWCMFLOWRULE']['XWCMFLOWCONDITIONS'] = WorkflowHelper.renderCollection(rule.conditions, 'XWCMFLOWCONDITION', 'flowconditionid');
			}
			if(rule.actions.length != 0) {
				pRule['XWCMFLOWRULE']['XWCMFLOWACTIONS'] = WorkflowHelper.renderCollection(rule.actions, 'XWCMFLOWACTION', 'flowactionid');
			}
		}

		_obj['XWCMFLOWRULES'] = pRules;
	},
	fromXML : function(_xml, _bIsDoc){
		var xmlWorkFlow = _xml;
		if(!_bIsDoc) {
			xmlWorkFlow = _xml.documentElement;
		}
		//1.flow properties
		this.__loadFlowProps(xmlWorkFlow.selectNodes("PROPERTIES/*"));

		this.__loadRules(xmlWorkFlow.selectNodes("XWCMFLOWRULES/XWCMFLOWRULE"));

		//2.flow nodes
		this.__loadFlowNodes(xmlWorkFlow.selectNodes("WCMFLOWNODES/WCMFLOWNODE"));
	},
	/**读取所有的flow nodes
	*/
	__loadFlowProps : function(_arFlowProps){
		for (var i = 0; i < _arFlowProps.length; i++){
			var oNode = _arFlowProps[i];
			if(oNode == null || oNode.tagName == null) {
				continue;
			}
			this[oNode.tagName.toLowerCase()] = oNode.text;
		}		
	},
	__loadRules : function(_arRules){
		this.rules = WorkflowRule.create(_arRules);
	},
	/**加载flow所有节点flow nodes
	*/
	__loadFlowNodes : function(_arFlowNodes){
		for(var nodeIndex = 0; nodeIndex < _arFlowNodes.length; nodeIndex++){
			var nodeName="",nodeDesc="",nodeID="",nodeCrUser="",nodeCrTime="";
			var nodeCenterX = 0, nodeCenterY = 0, flowID = 0;
			var xmlNode = _arFlowNodes[nodeIndex];
			//1.读取每一个flownode的属性，包括: 
			//nodeid, nodename, nodedesc, flowid, cruser, centerx, centery, together, workmodal
			var arNodeProps = xmlNode.selectNodes("PROPERTIES/*");

			var nodeProp = WorkflowHelper.parseNodeProp(arNodeProps);
			//alert(nodeProp['nodename'] + ':' + $toQueryStr(nodeProp))
			//set default decimal properties
			if(nodeProp['centerx'] == '') nodeProp['centerx'] = 0;
			if(nodeProp['centery'] == '') nodeProp['centery'] = 0;
			if(nodeProp['flowid']  == '') nodeProp['flowid']  = 0;
			var node = new WorkflowNode(nodeProp['nodeid'], nodeProp['nodename'],
										nodeProp['cruser'], nodeProp['nodedesc']);
			node.bReasignUsers = nodeProp['breasignusers'];
			node.bReasignSepNodes = nodeProp['breasignsepnodes'];
			
			node.setPoint(nodeProp['centerx'], nodeProp['centery']);
			node.flowID = nodeProp['flowid'];
			node.cruser = nodeProp['cruser'];
			node.actions = nodeProp['actions'];
			node.together = nodeProp['together'];
			node.properties = nodeProp;
			//ge gfc modify @ 2007-8-8 14:47 为了兼容以前(例如wcm52)的数据，如果together=1，则强制workmodal=1
			//node.workmodal = nodeProp['workmodal'];
			node.workmodal = (node.together == 1) ? 1 : nodeProp['workmodal'];

			node.tousers = nodeProp['touserscreator'];
			node.tousersparams = nodeProp['touserscreatorparams'];
			node.props = nodeProp;
			//2.rules
			node.rules = WorkflowRule.create(xmlNode.selectNodes("XWCMFLOWRULES/XWCMFLOWRULE"));
			//3.users
			if(node.tousers == null || node.tousers.trim() == '') {
				usersInfo = this.__getUsersInfo(xmlNode.selectNodes("WCMUSERS/WCMUSER"));
				if(usersInfo.ids.length > 0) {
					for(var userIdIndex=0; userIdIndex < usersInfo.ids.length; userIdIndex++){
						node.addUser(usersInfo.names[userIdIndex], usersInfo.ids[userIdIndex]);
					}
				}
				
				//4.group
				groupsInfo = this.__getGroupsInfo(xmlNode.selectNodes("WCMGROUPS/WCMGROUP"));
				if(groupsInfo.ids.length > 0) {
					for(var groupIdIndex=0; groupIdIndex < groupsInfo.ids.length; groupIdIndex++){
						node.addGroup(groupsInfo.names[groupIdIndex], groupsInfo.ids[groupIdIndex]);
					}
				}
			}
			//ge gfc add @ 2007-9-25 15:07 增加表单字段信息
			node.fields = WorkflowField.create(xmlNode.selectNodes("XWCMFLOWNODEFIELDS/XWCMFLOWNODEFIELD"));

			//5.line
			var lines = this.__makeFlowNodeLines(xmlNode.selectNodes("WCMFLOWBRANCHS/WCMFLOWBRANCH"), nodeProp['nodename']);
			if(lines.length > 0) {
				for (var i = 0; i < lines.length; i++){
					node.addLine(lines[i]);
				}
			}

			//6.add the prepared node
			this.addNode(node);	
		}
	},
	/**获取跟节点相关的users信息
	*/
	__getUsersInfo : function(_aUsers){
		var sUserIds = "";
		for(var userIndex =  0; userIndex < _aUsers.length; userIndex++){
			//处理了xml结构变化引起的问题，由于导出这个xml的时候将用户名也导出引起的问题
			var userIdNode = _aUsers[userIndex].firstChild.firstChild;
			sUserIds += userIdNode.text + ",";
			if(sUserIds.length > 0 && userIndex == _aUsers.length-1)
				sUserIds = sUserIds.substring(0, sUserIds.length-1);
		}
		var sUserNames = this.getUserNamesByIds(sUserIds);
		var arUserIds = [], arUserNames= [];
		if(sUserNames != null && sUserNames.indexOf("<") < 0){
			arUserIds = sUserIds.split(",");
			arUserNames = sUserNames.split(",");
		}
		return {ids: arUserIds, names: arUserNames};
	},
	/**获取跟节点相关的gronps信息
	*/
	__getGroupsInfo : function(_aGroups){
		var sGroupIds = "";
		for(var groupIndex = 0; groupIndex < _aGroups.length; groupIndex++){
			sGroupIds += _aGroups[groupIndex].text + ",";
			if(sGroupIds.length > 0 && groupIndex == _aGroups.length-1)
				sGroupIds = sGroupIds.substring(0, sGroupIds.length-1);
		}
		var sGroupNames = this.getGroupNamesByIds(sGroupIds);
		var arGroupIds = [], arGroupNames = [];
		if(sGroupNames != null && sGroupNames.indexOf("<") < 0){
			arGroupIds = sGroupIds.split(",");
			arGroupNames = sGroupNames.split(",");
		}	
		return {ids: arGroupIds, names: arGroupNames};
	},
	/**组织形成跟节点相关的lines
	*/
	__makeFlowNodeLines : function(_aBranchs, _sNodeName){
		var result = [];
		for(var branchIndex = 0; branchIndex < _aBranchs.length; branchIndex++){
			var lineStatus =  0, sEndNodeName = '' ,lineDesc = '', sBName = '', sOperationMark = '', sOperationMarkEnable = false;
			var arBranchProps = _aBranchs[branchIndex].selectNodes("PROPERTIES/STATUS");
			if(arBranchProps.length > 0){
				var nStatus = arBranchProps[0].text;
				var sStatusName = wcm.LANG['FLOW_31'] || '未知';
				lineStatus = nStatus;
				var arAllStatus = com.trs.wcm.AllDocStatus;
				if(arAllStatus && arAllStatus.length >0) {
					for (var iStatusIndex = 0; iStatusIndex < arAllStatus.length; iStatusIndex++){
						var status = arAllStatus[iStatusIndex];
						if(status.value == nStatus) {
							sStatusName = status.label;
							break;
						}
					}
				}
				
				lineDesc = sStatusName;
			}
			arBranchProps = _aBranchs[branchIndex].selectNodes("PROPERTIES/NEXTNAME");
			if(arBranchProps.length > 0) sEndNodeName = arBranchProps[0].text;
			arBranchProps = _aBranchs[branchIndex].selectNodes("PROPERTIES/BNAME");
			if(arBranchProps.length > 0) sBName = arBranchProps[0].text;
			//ge gfc add @ 2007-10-18 17:28 增加操作标签
			arBranchProps = _aBranchs[branchIndex].selectNodes("PROPERTIES/OPERATIONMARK");
			if(arBranchProps.length > 0) sOperationMark = arBranchProps[0].text;
			arBranchProps = _aBranchs[branchIndex].selectNodes("PROPERTIES/OPERATIONMARKENABLE");
			if(arBranchProps.length > 0) sOperationMarkEnable = arBranchProps[0].text;
		
			var aLine = new WorkflowLine(_sNodeName, sEndNodeName, lineStatus, lineDesc, sBName, sOperationMark, sOperationMarkEnable);
			//line: notify
			arBranchProps = _aBranchs[branchIndex].selectNodes("PROPERTIES/NOTIFY");
			if(arBranchProps.length > 0) aLine.notifyType = transformNotify(arBranchProps[0].text);
			//line: branchid
			arBranchProps = _aBranchs[branchIndex].selectNodes("PROPERTIES/BRANCHID");
			if(arBranchProps.length > 0) aLine.branchID = arBranchProps[0].text;
			//line: nodeid
			arBranchProps = _aBranchs[branchIndex].selectNodes("PROPERTIES/NODEID");
			if(arBranchProps.length > 0) aLine.nodeID = arBranchProps[0].text;

			result.push(aLine);
		}					

		return result;
	}
}
////////Workflow Object End/////////
WorkflowHelper = {
	parseNodeProp : function(_arNodeProps){
		var nodeProp = {};
		for (var i = 0; i < _arNodeProps.length; i++){
			var oNode = _arNodeProps[i];
			if(oNode == null || oNode.tagName == null) {
				continue;
			}
			nodeProp[oNode.tagName.toLowerCase()] = oNode.text || '';
		}
		return nodeProp;
	},
	createNodes : function(_sConstructor, _arNodes){
		var result = [];
		for (var nodeIndex = 0; nodeIndex < _arNodes.length; nodeIndex++){
			//1.读取每一个action的属性，包括: 
			//nodeid, nodename, nodedesc, flowid, cruser, centerx, centery
			var aNode = _arNodes[nodeIndex];
			var arProps = aNode.selectNodes("PROPERTIES/ *");
			var nodeProp = WorkflowHelper.parseNodeProp(arProps);
			eval('var entity = new ' + _sConstructor + '(nodeProp)');
			result.push(entity);
		}
		return result;
	},
	getProperties : function(_obj, _sIdName){
		if(_obj == null) {
			return null;
		}
		//else
		var result = {};
		for( var sName in _obj){
			var val = _obj[sName];
			var name = sName.toUpperCase();
			if(_sIdName != null && name == _sIdName.toUpperCase()) {
				result[name] = Math.floor(val);
			}else{
				var sType = typeof(val);
				if(sType == 'string' || sType == 'number' || sType == 'boolean') {
					result[name] = val;
				}
			}
		}

		return result;
	},
	renderCollection : function(_arRawColl, _sName, _sIdName){
		if(_arRawColl == null || _arRawColl.length == 0) {
			return null;
		}
		//else
		var result = [];
		for (var i = 0; i < _arRawColl.length; i++){
			var pRule = {'PROPERTIES' : WorkflowHelper.getProperties(_arRawColl[i], _sIdName)};
			var json = {};
			json[_sName] = pRule;
			result.push(json);
		}
		return result;
	},
	getRuleTypeName : function(_type){
		var result = null;
		if(WorkflowHelper.m_arrRuleTypeName == null) {
			WorkflowHelper.m_arrRuleTypeName = {
				'5' : wcm.LANG['FLOW_63'] || '收到前',
				'0' : wcm.LANG['FLOW_64'] || '收到后',
				'1' : wcm.LANG['FLOW_65'] || '签收后',
				'2' : wcm.LANG['FLOW_66'] || '处理中',
				'3' : wcm.LANG['FLOW_67'] || '处理后',
				'4' : wcm.LANG['FLOW_68'] || '返工后',
				'6' : wcm.LANG['FLOW_69'] || '拒绝后',
				'7' : '打回后'
			};
		}
		result = WorkflowHelper.m_arrRuleTypeName[_type];
		if(result == null) {
			result = wcm.LANG['FLOW_31'] || '未知';
		}
		return result;
	}
}


/**WCM提醒类别：在线提醒 */
var REMIND_ONLINE   = 0;
/**WCM提醒类别：Email提醒 */
var REMIND_BYEMAIL  = 1;
/**WCM提醒类别：手机提醒 */
var REMIND_BYMOBILE = 2;

CTRSBitsValue = Class.create('wcm.CTRSBitsValue');
CTRSBitsValue.prototype = {
	initialize : function(_nLowValue, _nHighValue){
		//Define Properties
		this.nLowValue	= _nLowValue || 0;
		this.nHighValue = _nHighValue || 0;
	},
	getBit : function(_nIndex){
		if(_nIndex >= 32){
			return this.nHighValue & Math.pow(2, (_nIndex-32));
		}else{
			return this.nLowValue & Math.pow(2, _nIndex);
		}
	},
	setValue : function(_nLowValue, _nHighValue){
		this.nLowValue	= _nLowValue	|| 0;
		this.nHighValue = _nHighValue	|| 0;
	},
	setBit : function(_nIndex, _bValue){
		var nIndex = _nIndex, nValue = this.nLowValue;
		if(_nIndex >= 32){
			nIndex = _nIndex - 32;
			nValue = this.nHighValue;
		}
		
		if(nValue == 0 && !_bValue)return;

		var nTemp;
		if(_bValue){
			nValue = (nValue | Math.pow(2, nIndex));
		}else{//^ &
			nTemp = ~Math.pow(2, nIndex);
			nValue = (nValue & nTemp);
		}

		if(_nIndex > 31){
			this.nHighValue = nValue;
		}else{
			this.nLowValue = nValue;
		}
	}
}
function transformNotify(_sValue){
	return _sValue;
	/*
	var aBitsValue = new CTRSBitsValue(_sValue);
	if(aBitsValue.getBit(REMIND_ONLINE) && aBitsValue.getBit(REMIND_BYEMAIL))
		return _sValue;
	if(!aBitsValue.getBit(REMIND_ONLINE) && !aBitsValue.getBit(REMIND_BYEMAIL))
		return _sValue;

	if(aBitsValue.getBit(REMIND_ONLINE)){
		aBitsValue.setBit(REMIND_BYEMAIL, true);
		aBitsValue.setBit(REMIND_ONLINE, false);
	}else if(aBitsValue.getBit(REMIND_BYEMAIL)){
		aBitsValue.setBit(REMIND_BYEMAIL, false);
		aBitsValue.setBit(REMIND_ONLINE, true);
	}
	return aBitsValue.nLowValue;//*/
}

//ge gfc add @ 2007-9-21 11:14 兼容在52中使用
var WORKED_WITH_WCM52 = false;