PageContext = {
	m_hNodeInfos : {}, /*{NodeId, NodeName}*/
	m_hNodeBranchInfos : {}, /*{NodeId, [Object]:BranchInfoData}*/
	m_hNodePreparedBranchInfos : {},
	m_hReferredToNodesInfo:{},/*referredto,[Object]{NodeId:XXX,NodeName:XXX}*/
	m_arrNotifyTypes : ['email', 'message', 'sms'],
	m_arrAddedOptions : [ //附加的操作，注意：它们在此数组中是有顺序的
		{label : (wcm.LANG['IFLOWCONTENT_18'] || '<签收>'), value : 'doAccept'}, //value值也就是最后的操作
		{label : (wcm.LANG['IFLOWCONTENT_19'] ||'<拒绝>'), value : 'refuse'},
		{label : (wcm.LANG['IFLOWCONTENT_20'] ||'<要求返工>'), value : 'backTo'},
		{label : (wcm.LANG['IFLOWCONTENT_100'] ||'<暂不流转>'), value : 'hold'},
		{label : '<打回>',value:'referredto'},
		{label : '<申请延期>',value:'applyExtension'}
	],
	m_arrApprovalOptions : [ //并联审批的一个分支被打回来的处理逻辑
		{label : '<重新选择>', value : 'reSelect'},
		{label : '<取消审批>', value : 'cancelApproval'}
	],
	loadPage : function(_params){
		PageContext.params = _params;
		//输出文档标题，收集id参数
		if($('divDocs')){
			$('divDocs').innerHTML = $transHtml(_params['title']);
			$('divDocs').title = _params['title'];
		}
		//其他处理
		this.__loadNextNodes();
		//$('txtComment').focus();
	},
	__loadNextNodes : function(){
		var nFlowDocId = PageContext.params['flowid'];
		if(!(nFlowDocId > 0)) {
			Ext.Msg.alert(String.format("不合法的flowdocid参数[{0}]!",nFlowId)); 
			return;
		}
		var oPostData = {
			ObjectId: nFlowDocId,
			Resubmit: (PageContext.params['resubmited'] == true)
		}
		//并联审批开始节点
		if(PageContext.params['nodemodal'] == 2 && PageContext.params['applyExtension'] != 1){
			//返工或者拒绝，并且不是申请延期
			if(PageContext.params['reworked'] == 1 || PageContext.params['refuse'] == 1){
				this.initSepratorStartInfoForApproval(oPostData);
				return;
			}
			//如果不是申请延期并联审批开始节点，并且不需要指定分支，而是根据选择的用户个数，来选择分支的个数
			else if(bCanSelectSepNode == 'false'){
				this.initSepratorStartInfo(oPostData);
				return;
			}
		}
		BasicDataHelper.call('wcm6_process', 'getNextNodes', oPostData, true, function(_trans, _json){
			var arNodes = $a(_json, 'FlowNodes.FlowNode');
			var selNextNodes = $('selNextNodes');
			if(arNodes != null && arNodes.length > 0) {
				//0.如果是并联审批的开始节点，显示并联审批节点的选择
				var bIsSeperatorStart = (PageContext.params['workmodal'] == 2);
				if(bIsSeperatorStart) {
					for (var i = 0; i < arNodes.length; i++){
						var node =arNodes[i];
						$('spSeperatorSelection').innerHTML += '<input type="checkbox" value="'
							+ $v(node, 'NODEID') + '" checked>&nbsp;'
							+ $v(node, 'NODENAME') + '&nbsp;'; 
					}
					Element.show('divSeperatorSelection');
				}else{
					Element.hide('divSeperatorSelection');
				}
				//1.显示下一个节点信息
				//var selNextNodes = $('selNextNodes');
				for (var i = 0; i < arNodes.length; i++){
					var node =arNodes[i];
					var nNodeId = $v(node, 'NODEID');
					var sNodeName = $v(node, 'NODENAME');
					this.__appendSelectOption(selNextNodes, nNodeId, sNodeName, node);
					//保存节点信息
					PageContext.m_hNodeInfos[nNodeId] = sNodeName;
				}
				PageContext.m_nCurrNodeId = selNextNodes.value;
				//comment by liuyou@20080814 返工还可以继续打回，暂不流转，签收，拒绝等
				//除了不能选择其他节点和指定处理人外不应该有太多特别之处
				
				//add by liuhm 返工是可以层层进行的
				/*
				if (PageContext.params['reworked'] == 1){// 对于返工，不能选择下一个节点，
					//也不能指定处理人或者处理方式 TODO
					$('selNextNodes').disabled = true;
					this.displayBranchInfo(PageContext.params['nodeid']);
					return;
				}
				*/
				if (PageContext.params['applyExtension'] == 1){//
					//也不能指定处理人或者处理方式 TODO
					$('selNextNodes').disabled = true;
					this.displayBranchInfo(PageContext.params['nodeid']);
					return;
				}
				//else
				//2.适当地增加节点，用于判断最后的操作（“重新指派”操作除外）
				 this.__appandOtherNextNodes();
				//3.绑定节点选择事件
				this.__bindingNodesSelectInfo(selNextNodes);

				//4.在获取下一个节点的基础上，获取branch信息并显示
				var nNextNode = selNextNodes.value;
				this.displayBranchInfo(nNextNode);
				if(PageContext.params['resubmited'] == true){
					$('selNextNodes').disabled = true;
				}
				this.__controllReasignUser(selNextNodes);
				
			}else if(PageContext.params['workmodal'] == 1){ // 没有返回任何节点，且为会签
				this.displayTogetherInfo();
				this.__appandOtherNextNodes();
				//绑定节点选择事件
				this.__bindingNodesSelectInfo(selNextNodes);

				//4.在获取下一个节点的基础上，获取branch信息并显示
				//var nNextNode = selNextNodes.value;
				//this.displayBranchInfo(nNextNode);

				//绑定选择进行会签还是其他节点时页面的显示信息
				Event.observe(selNextNodes, 'change', function(){
					if(selNextNodes.options[selNextNodes.selectedIndex].innerHTML =='进行会签'){
						Element.show('dvTogetherInfo');
						Element.hide('divOptionalParams');
					}
					else{
						Element.show('divOptionalParams');
						Element.hide('dvTogetherInfo');
					}
				});
				Element.hide('divOptionalParams');
				Element.show('dvTogetherInfo');	
			}
			
			// ge gfc add @ 2007-8-8 10:29 如果是并联审批开始节点的话，改变流转提示信息
			if(PageContext.params['workmodal'] == 2){
				$('spOptionTip').innerHTML = '&nbsp;' + (wcm.LANG['IFLOWCONTENT_22'] ||'节点设置：');
			}
			//并联审批的结束节点不允许拒绝
			if(PageContext.params['workmodal'] == 3){
				var options = $('selNextNodes').options;
				 for(var index=0; index < options.length; index++){
					 if($('selNextNodes').options[index].value == 'refuse'){
						  $('selNextNodes').removeChild(options[index]);
					 }
				 }
			}
		}.bind(this));
	},
	initSepratorStartInfoForApproval : function(oPostData){
		//初始化通知方式信息，默认用短消息通知
		$('chk_message').checked = true;
		$('spOptionTip').innerHTML = '&nbsp;选择操作：';
		Element.show('dvWorkModalSepratorEndNodes');
		//初始化并联审批结束节点的集合
		BasicDataHelper.call('wcm6_process', 'getEndNodes', {ObjectId : PageContext.params['flowid']}, true, function(_trans, _json){
			var endNodes = $a(_json, 'FlowNodes.FlowNode');
			if(!endNodes)
				return;
			for (var i = 0; i < endNodes.length; i++){
				var nEndNodeId = $v(endNodes[i], 'NODEID');
				var sEndNodeName =  $v(endNodes[i], 'NODENAME');
				PageContext.__appendSelectOption(selWorkModalSepratorEndNodes, nEndNodeId, sEndNodeName, null);
			}
		});
		var selNextNodes = $('selNextNodes');
		//初始化下一个节点的信息
		for (var i = 0; i < this.m_arrApprovalOptions.length; i++){
			var option = this.m_arrApprovalOptions[i];
			this.__appendSelectOption(selNextNodes, option['value'], option['label']);
		}
		Element.hide('dvWorkModalSepratorEndNodes');
		//绑定下一个节点的选择事件
		Event.observe(selNextNodes, 'change', function(){
			var currValue = selNextNodes.value;
			//默认为重新选择，此时显示用户选择区域
			if(currValue == 'reSelect'){
				Element.show('divOptionalParams');
				Element.hide('dvWorkModalSepratorEndNodes');
			}else if(currValue == 'cancelApproval'){
			//如果为取消审批，则不显示任何额外的信息
				Element.hide('divOptionalParams');
				Element.show('dvWorkModalSepratorEndNodes');
			}
		});
	},
	initSepratorStartInfo : function(oPostData){
		//初始化通知方式信息，默认用短消息通知
		$('chk_message').checked = true;
		//将显示下一个节点的区域隐藏
		Element.hide('dvNextNode');
		//将节点信息暂存到缓存中，便于在最后提交数据的时候组织下一个节点的信息
		BasicDataHelper.call('wcm6_process', 'getNextNodes', oPostData, true, function(_trans, _json){
			var arNodes = $a(_json, 'FlowNodes.FlowNode');
			if(arNodes != null && arNodes.length > 0) {
				for (var i = 0; i < arNodes.length; i++){
					var node =arNodes[i];
					var nNodeId = $v(node, 'NODEID');
					var sNodeName = $v(node, 'NODENAME');
					//保存节点信息
					PageContext.m_hNodeInfos[nNodeId] = sNodeName;
				}
			}
		});
	},
	getNextNodesSize : function(){
		var nSize = 0;
		for(var nodeid in PageContext.m_hNodeInfos){
			nSize++;
		}
		return nSize;
	},
	__controllReasignUser : function(selNextNodes){
		//获取节点上设置的是否可以指定接收用户的属性
		var bReasignUsers = selNextNodes.options[selNextNodes.selectedIndex].getAttribute('bReasignUsers');
		if(bReasignUsers == 1){Element.show('SpResign');}
		else Element.hide('SpResign');
	},
	__appandOtherNextNodes : function(){
		if(PageContext.params['resubmited'] != true) {
			var nStart = 0;
			//ge gfc modify @ 2007-8-17 15:11 排除掉那些对于已经签收的文档
			//，或者从列表上直接过来的
			//if (PageContext.params['accepted'] == 1){ //排除掉那些对于已经签收的文档
			if (PageContext.FromUrl != true
				|| PageContext.params['accepted'] == 1){ 
				nStart = 1;
			}
			//判断是否需要将“打回”操作添加进来，如果没有任何已经流转过的节点，这不需要加入
			var bAppandReferredto = false;
			 BasicDataHelper.call('wcm6_process', 'getProcessedNodes', {ObjectId: PageContext.params['flowid']}, true, function(_trans, _json){
				var processedNodes = $a(_json, 'FlowNodes.FlowNode');
				if(processedNodes && processedNodes.length > 0)
					bAppandReferredto = true;
				for (var i = nStart; i < this.m_arrAddedOptions.length; i++){
					if(this.params['reworked'] == 1 && i==1)continue;
					var option = this.m_arrAddedOptions[i];
					// 拒绝的文档，不能再设置拒绝或返工
					if(this.params['refuse'] == 1){
						if(option.value == "backTo" || option.value =="refuse") continue;
					}
					// 返工后的文档不能再拒绝
					if(this.params['reworked'] == 1){
						if(option.value =="refuse") continue;
					}
					if(bStartNode == 'true'&& (option['value'] != 'doAccept' || option['value'] != 'hold'))continue;
					if(!bAppandReferredto && option['value'] == 'referredto') continue;
					if(this.params['takebacked'] == 1){
						if(option.value == 'refuse' || option.value == 'backTo' || option.value == 'referredto' || option.value == 'applyExtension') continue;
					}
					this.__appendSelectOption(selNextNodes, option['value'], option['label']);
				}
			}.bind(this));
		}
	},
	__bindingNodesSelectInfo : function(selNextNodes){
		Event.observe(selNextNodes, 'change', function(){
			if($('cb_asRule')){
				Element.hide('sp_asRule');
				$('cb_asRule').checked = false;
			}
			//在显示change后的节点信息前，需要更新一下缓存中的branchinfo信息
			if(!isNaN(parseInt(PageContext.m_nCurrNodeId))) {
				PageContext.m_hNodePreparedBranchInfos[PageContext.m_nCurrNodeId] = this.getBranchInfo();
			}

			var val = selNextNodes.value;
			//记录一下当前选中的节点
			PageContext.m_nCurrNodeId = val;
			$('txtComment').disabled = (val == 'doAccept');
			Element.hide('dvReferredToNodes');
			if(isNaN(parseInt(val))) { //说明是附加的操作节点
				Element.hide('divOptionalParams');
				PageContext.m_sCurrSpecialOption = val;
				//如果是并联审批开始节点的话，需要将并联审批的节点选择给禁止掉
				if(PageContext.params['workmodal'] == 2){
					$('fdsSeperatorSelection').disabled = true;
				}	
				//兼容了会签节点的时候，如果还有尚未签收的用户，这时节点的value是空的，会进入到这个分支中
				if(val == '')$('spOptionTip').innerHTML = '&nbsp;' + (wcm.LANG['IFLOWCONTENT_94'] || '下一个节点：');
				else $('spOptionTip').innerHTML = '&nbsp;' + (wcm.LANG['IFLOWCONTENT_92'] || '其他操作：');
				//如果是转交，可以指定转交到哪个节点
				if(val == 'referredto'){
					Element.show('dvReferredToNodes');
					var data = {
						ObjectId: PageContext.params['flowid']
					}
					//获取可以转交到的节点集合
					//先从缓存中获取
					if(!PageContext.m_hReferredToNodesInfo['referredto']){
						 BasicDataHelper.call('wcm6_process', 'getProcessedNodes', data, true, function(_trans, _json){
							var processedNodes = $a(_json, 'FlowNodes.FlowNode');
							if(!processedNodes)
								return;
							this.m_hReferredToNodesInfo['referredto'] = processedNodes;
							for (var i = 0; i < processedNodes.length; i++){
								var nRefferToNodeId = $v(processedNodes[i], 'NODEID');
								var sRefferNodeName =  $v(processedNodes[i], 'NODENAME');
								//保存节点信息
								PageContext.m_hNodeInfos[nRefferToNodeId] = sRefferNodeName;
								this.__appendSelectOption(selReferredToNodes, nRefferToNodeId, sRefferNodeName, null);
							}
							if(selReferredToNodes.selectedIndex != -1){
								var val = selReferredToNodes.value;
								//记录一下当前选中的节点
								PageContext.m_nCurrNodeId = val;
								PageContext.m_sCurrSpecialOption = 'referredto';
								this.displayBranchInfo(val);
							}
							//绑定事件
							Event.observe('selReferredToNodes', 'change', function(){
								if($('sp_asRule'))Element.hide('sp_asRule');
								if($('cb_asRule'))$('cb_asRule').checked = false;
								//在显示change后的节点信息前，需要更新一下缓存中的branchinfo信息
								if(!isNaN(parseInt(PageContext.m_nCurrNodeId))) {
									PageContext.m_hNodePreparedBranchInfos[PageContext.m_nCurrNodeId] = this.getBranchInfo();
								}

								var val = selReferredToNodes.value;
								//记录一下当前选中的节点
								PageContext.m_nCurrNodeId = val;
								PageContext.m_sCurrSpecialOption = 'referredto';
								this.displayBranchInfo(val);
							}.bind(this), false);
						}.bind(this));
						
					}
					if(selReferredToNodes.selectedIndex != -1){
						var val = selReferredToNodes.value;
						//记录一下当前选中的节点
						PageContext.m_nCurrNodeId = val;
						PageContext.m_sCurrSpecialOption = 'referredto';
						this.displayBranchInfo(val);
					}
					return;
				}
				//兼容在文档编辑页面使用的时候，流转页面高度的随着当前流转的信息来展示
				if(parent.setIframeHeight){
					parent.setIframeHeight();
				}
				return;
			}
			//else
			Element.show('divOptionalParams');
			PageContext.m_sCurrSpecialOption = null;
			this.displayBranchInfo(selNextNodes.value);
			//如果是并联审批开始节点的话，需要将并联审批的节点选择给释放出来
			if(PageContext.params['workmodal'] == 2){
				$('fdsSeperatorSelection').disabled = false;
				$('spOptionTip').innerHTML = '&nbsp;' + (wcm.LANG['IFLOWCONTENT_93'] || '节点设置：');
			}else{
				$('spOptionTip').innerHTML = '&nbsp;' + (wcm.LANG['IFLOWCONTENT_94'] || '下一个节点：');
			}
			this.__controllReasignUser(selNextNodes);
		}.bind(this), false);
	},
	/**为指定的select元素附加一个option*/
	__appendSelectOption : function(_select, _sValue, _sText, nodeJson){
		var eSelect = $(_select);
		if(eSelect == null) {
			return;
		}
		//else
		var eOption = document.createElement("OPTION");
		eSelect.appendChild(eOption);
		eOption.value = _sValue;
		eOption.innerHTML = _sText;	
		if(nodeJson){
			var nWorkModal = $v(nodeJson, 'WorkModal');
			eOption.setAttribute("workmodal", nWorkModal);
			var nBreasignUsers = $v(nodeJson, 'bReasignUsers');
			eOption.setAttribute("bReasignUsers", nBreasignUsers);
		}
		delete eSelect;
	},
	displayTogetherInfo : function(){
		//将下一个节点信息提示，固定为“进行会签”
		var eOption = document.createElement("OPTION");
		$('selNextNodes').appendChild(eOption);
		//eOption.value = PageContext.params['nodeid'];
		eOption.innerHTML = wcm.LANG['IFLOWCONTENT_25'] || '进行会签';
		//注释掉的原因是:会签节点的用户可以选择拒绝返工等操作
		//$('selNextNodes').disabled = true;

		//获取信息后，绑定数据并显示
		var postData = {
			FlowDocId: PageContext.params['flowid'],
			NextNodeId: 0,
			Resubmit: (PageContext.params['resubmited'] == true)				
		};
		BasicDataHelper.call('wcm6_process', 'getBranchInfo', postData, true, function(_trans, _json){
			//显示相关的处理人员
			//..已经会签的
			var users = $v(_json, 'BranchInfo.UsersPassed.User');
			var sValue = TempEvaler.evaluateTemplater('template_users2', users, null);
			Element.update($('spPassedUsers'), sValue);
			//..尚未会签的
			users = $a(_json, 'BranchInfo.Users.User');
			// ge gfc modify @ 2007-10-15 20:13 限定了用户选择范围（在特定组内选择）
			//sValue = TempEvaler.evaluateTemplater('template_users', users, null);			
			sValue = '';
			var groupRange = $v(_json, 'BranchInfo.GroupRange');
			if(groupRange) {
				this.__displayGroupRange(groupRange);
			}else{
				sValue = TempEvaler.evaluateTemplater('template_users2', users, null);			
			}

			Element.update($('spUnpassedUsers'), sValue);
		}.bind(this));	

	},
	displayBranchInfo : function(_nNextNodeId){
		var sNodeName = PageContext.m_hNodeInfos[_nNextNodeId];
		if(sNodeName && sNodeName.trim() == (wcm.LANG['IFLOWCONTENT_26'] || '结束')) {
			//结束节点没有通知方式和处理人员信息
			Element.hide('divOptionalParams');
			return;
		}
		Element.show('divOptionalParams');
		var postData = {
			FlowDocId: PageContext.params['flowid'],
			NextNodeId: _nNextNodeId,
			Resubmit: (PageContext.params['resubmited'] == true)
		};
		//为了提高性能、减少和服务器端交互次数，先从缓存中获取数据
		var branchInfo = PageContext.m_hNodePreparedBranchInfos[_nNextNodeId];
		if(branchInfo != null) {
			this.__initBranchInfo(branchInfo);
			return;
		}
		//else 请求服务器数据
		BasicDataHelper.call('wcm6_process', 'getBranchInfo', postData, true, function(_trans, _json){
			//添加缓存数据
			PageContext.m_hNodeBranchInfos[_nNextNodeId] = _json;
			var data = PageContext.__prepareBachInfo(_json, _nNextNodeId);
			PageContext.m_hNodePreparedBranchInfos[_nNextNodeId] = data;
			this.__initBranchInfo(data, _json);
		}.bind(this));		
	},
	__displayGroupRange : function(_groupRange){
		var sGroupId = $v(_groupRange, 'GroupId');
		var sGroupName = $v(_groupRange, 'GroupName');
		var groupInfo = {
			'GroupId'	: sGroupId,
			'GroupName' : sGroupName 
		};
		//在全局变量中记录，以备后面用户选择时使用（限定所在组）
		PageContext['GroupRange'] = groupInfo;
		$('lnkResign').innerHTML = String.format("在<font color='red'>{0}</font>中选择用户",sGroupName);		
	},
	__prepareBachInfo : function(_json, _nNextNodeId){
		var arrNotifyTypes = [];
		var arrUsers = [];
		//1 通知方式
		var notifyTypes = $v(_json, 'BranchInfo.NOTIFYSTYLES');
		//alert(notifyTypes)
		if(notifyTypes) {
			var types = notifyTypes.split(',');
			for (var i = 0; i < types.length; i++){
				arrNotifyTypes.push(types[i].trim().toLowerCase());
			}
		}
		//2 处理人员
		var users = null;
		var nextWorkModal = 0;
		if(PageContext.params['reworked'] == 1 || PageContext.params['applyExtension'] == 1){
			//如果是下个节点是会签节点的话，默认的接收用户应该是节点上设置的用户，而不是要求返工用户
			nextWorkModal =  $v(_json, 'BranchInfo.NEXTWORKMODAL');
			if(nextWorkModal == 1){
				users = $a(_json, 'BranchInfo.Users.User');
			}else{
				users = $a(_json, 'BranchInfo.postUsers.User');
			}
		}else{
			users = $a(_json, 'BranchInfo.Users.User');
		}
		if(users && users.length > 0) {
			for (var i = 0; i < users.length; i++){
				arrUsers.push({
					USERID: $v(users[i], 'userid'),
					USERNAME: $v(users[i], 'username'),
					TRUENAME: $v(users[i], 'truename')
				});
			}
		}	
		//3.其他信息，如会签时的“passedusers”和“unpassedusers”
		//4.已选用户和workmodal
		var arrOptions = $('selNextNodes').options;
		var currOption = null;
		for(var i=0;i<arrOptions.length;i++){
			if(arrOptions[i].value == _nNextNodeId){
				currOption = arrOptions[i];
				break;
			}
		}
		var nWorkModal = 0;
		if(currOption!=null){
			nWorkModal = currOption.getAttribute("workmodal", 2);
		}
		//返回信息
		return {
			ToUsers: arrUsers,
			NotifyTypes: arrNotifyTypes,
			WorkModal : nextWorkModal==1 ? nextWorkModal : nWorkModal,
			SelectedUsers : null
		}
	},
	__initBranchInfo : function(_branchInfo, _json){
		//1.获取数据
		var data = _branchInfo;
		if(data == null) {
			return;
		}
		//else 显示
		//2.1 显示通知方式
		var notifyTypes = data['NotifyTypes'];//$v(data, 'BranchInfo.NOTIFYSTYLES');
		//alert(notifyTypes.length)
		var allTypes = PageContext.m_arrNotifyTypes;
		for (var i = 0; i < allTypes.length; i++){
			$('chk_' + allTypes[i]).checked = (notifyTypes.include(allTypes[i]));
		}
		//2.2 显示处理人员
		var users = data['ToUsers'];//$v(data, 'BranchInfo.Users.User');
		//alert(users.length)
		// ge gfc modify @ 2007-10-15 20:13 限定了用户选择范围（在特定组内选择）
		var sValue = '';
		var groupRange = $v(_json, 'BranchInfo.GroupRange');
		if(groupRange) {
			this.__displayGroupRange(groupRange);
		}else{
			if(users && users.length > 0) {
				var sValue = TempEvaler.evaluateTemplater('template_users', users, null);
				Element.update($('spUsers'), sValue);
				setTimeout(function(){
					var arrSelectedUserIdEles = document.getElementsByName("selectedUserIds");
					var arrSelectedUserIds = data['SelectedUsers'];
					if(arrSelectedUserIds!=null){//已经设置过并被缓存的数据
						var sSelectedUserIds = ','+arrSelectedUserIds.join()+',';
						for (var i=0; i<arrSelectedUserIdEles.length; i++){
							arrSelectedUserIdEles[i].checked = 
								sSelectedUserIds.indexOf(','+arrSelectedUserIdEles[i].value+',')!=-1;
						}
					}else{//初始化时的数据
						if(arrSelectedUserIdEles.length==1 || data['WorkModal']==1){
							for (var i=0; i<arrSelectedUserIdEles.length; i++){
								arrSelectedUserIdEles[i].checked = true;
							}
						}
					}
				}, 10);
				Element.hide('sp_asRule');
			}else{
				Element.update($('spUsers'), '');
				Element.show('sp_asRule');
			}			
		}
		//兼容在文档编辑页面使用的时候，流转页面高度的随着当前的用户信息来展示
		if(parent.setIframeHeight){
			parent.setIframeHeight();
		}

	},
	getBranchInfo : function(){
		var arrNotifyTypes = [];
		var arrUsers = [];
		//通知方式
		var allTypes = PageContext.m_arrNotifyTypes;
		for (var i = 0; i < allTypes.length; i++){
			var chk = $('chk_' + allTypes[i]);
			if(!chk.checked) {
				continue;
			}
			//else
			arrNotifyTypes.push(chk.value);
		}
		//处理人员
		var eUsers = $('spUsers').getElementsByTagName('span');
		for (var i = 0; i < eUsers.length; i++){
			var nUserId = eUsers[i].getAttribute('_uid', 2);
			if(nUserId == null || isNaN(parseInt(nUserId))) {
				continue;
			}
			arrUsers.push({
				USERID: nUserId,
				USERNAME: eUsers[i].getAttribute('_uname', 2),
				TRUENAME: eUsers[i].getAttribute('_truename', 2)
			});
		}
		var nWorkModal = 0;
		if($('selNextNodes').selectedIndex!=-1){
			var currOption = $('selNextNodes').options[$('selNextNodes').selectedIndex];
			nWorkModal = currOption.getAttribute("workmodal", 2);
		}
		var arrSelectedUsersEle = document.getElementsByName("selectedUserIds");
		var arrSelectedUsers = [];
		for(var i=0;i<arrSelectedUsersEle.length;i++){
			if(arrSelectedUsersEle[i].checked){
				arrSelectedUsers.push(arrSelectedUsersEle[i].value);
			}
		}
		//alert(arrNotifyTypes + '\n' + arrUsers.length);
		//alert(arrSelectedUsers + '\n' + nWorkModal);
		return {
			ToUsers: arrUsers,
			NotifyTypes: arrNotifyTypes,
			WorkModal : nWorkModal,
			SelectedUsers : arrSelectedUsers
		}
	},
	buildPostData : function(_postData){
		//并联审批开始节点，并且不是申请延期
		if(PageContext.params['nodemodal'] == 2  && PageContext.params['applyExtension'] != 1){
			//如果是拒绝或者返工
			if(PageContext.params['reworked'] == 1 || PageContext.params['refuse'] == 1){
				return this.buildSepratorStartPostDataForApproval(_postData);
			}
			//如果是不能选择分支，只能根据选择的用户来分配分支
			else if(bCanSelectSepNode == 'false'){
				return this.buildSepratorStartPostData(_postData);
			}
		}
		//在提交前进行一次当前节点的信息收集，可能是冗余的操作，但是可以保证信息收集的准确性
		PageContext.m_hNodePreparedBranchInfos[PageContext.m_nCurrNodeId] = this.getBranchInfo();

		var aCombine = [];
		var hBranchInfo = {};
		
		if(PageContext.params['workmodal'] == 2 && !$('fdsSeperatorSelection').disabled) {
			var chks = $('spSeperatorSelection').getElementsByTagName('INPUT');//.childNodes;
			for (var i = 0; i < chks.length; i++){
				var chk = chks[i];
				if(chk.checked) {
					hBranchInfo[chk.value] = PageContext.m_hNodePreparedBranchInfos[chk.value];//注意，有可能缓存中尚未存在
				}
			}
			//注释掉，并联审批提交请求的顺序是有限制的,为0的这个必须是
			//附加一个nodeid=0的请求信息，表示所选的节点均已作为并联审批节点处理
			//hBranchInfo[0] = {};
		}else{
			var currNodeId = PageContext.m_nCurrNodeId || "";
			hBranchInfo[currNodeId] = PageContext.m_hNodePreparedBranchInfos[PageContext.m_nCurrNodeId];
		}

		var sMethod = (PageContext.params['resubmited']) ? 'reSubmitTo' : 'submitTo';
		for( var sNodeId in hBranchInfo){
			var data = Object.extend({}, _postData);
			data['NextNodeId'] = sNodeId;
			var branchInfo = hBranchInfo[sNodeId];
			if(branchInfo) {//如果信息不存在，有可能是尚未请求其getBranchInfo，此时服务器端会根据默认设置进行操作
				Object.extend(data, {
					NotifyTypes: branchInfo['NotifyTypes'],
					ToUserIds: PageContext.__getUserIds(branchInfo['ToUsers'], branchInfo['SelectedUsers'])
				});
				if(data['NotifyTypes'] == '') {//指定为“不通知”
					data['NotifyTypes'] = 'none';
				}
			}
			aCombine.push(BasicDataHelper.Combine('wcm6_process', sMethod, data));
		}

		//附加一个nodeid=0的请求信息，表示所选的节点均已作为并联审批节点处理
		if(PageContext.params['workmodal'] == 2 && !$('fdsSeperatorSelection').disabled){
			var data = Object.extend({}, _postData);
			data['NextNodeId'] = 0;
			aCombine.push(BasicDataHelper.Combine('wcm6_process', sMethod, data));
		}
		
		return aCombine;
	},
	makeNotifyTypesForSepratorStartPostData : function(arrNotifyTypes){
		var allTypes = PageContext.m_arrNotifyTypes;
		for (var i = 0; i < allTypes.length; i++){
			var chk = $('chk_' + allTypes[i]);
			if(!chk.checked) {
				continue;
			}
			//else
			arrNotifyTypes.push(chk.value);
		}
		var sNotifyTypes = arrNotifyTypes.join(",");
		if(sNotifyTypes == '') {//指定为“不通知”
			sNotifyTypes = 'none';
		}
		return sNotifyTypes;
	},
	buildSepratorStartPostDataForApproval : function(_postData){
		var aCombine = [];
		var hBranchInfo = {};
		//获取通知消息
		var arrNotifyTypes = [];
		var sNotifyTypes = this.makeNotifyTypesForSepratorStartPostData(arrNotifyTypes);
		//获取指定的人
		var arrSelectedUserIds = this.getCurrSelectedUserIds();
		var currValue = $('selNextNodes').value;
		if(currValue == 'reSelect'){
			var data = Object.extend({}, _postData);
			Object.extend(data, {
				NextNodeId : PageContext.params['prenodeid'],
				NotifyTypes: sNotifyTypes,
				ToUserIds: arrSelectedUserIds.join(",")
			});
			var sMethod = (PageContext.params['resubmited']) ? 'reSubmitTo' : 'submitTo';
			aCombine.push(BasicDataHelper.Combine('wcm6_process', sMethod, data));
		}else if(currValue == 'cancelApproval'){
			var data = Object.extend({}, _postData);
			Object.extend(data, {
				NextNodeId : $('selWorkModalSepratorEndNodes').value,
				NotifyTypes: 'none',
				ToUserIds: ''
			});
			var sMethod = (PageContext.params['resubmited']) ? 'reSubmitTo' : 'autoToNextNode';
			aCombine.push(BasicDataHelper.Combine('wcm6_process', sMethod, data));
		}
		return aCombine;
	},
	buildSepratorStartPostData : function(_postData){
		var aCombine = [];
		var hBranchInfo = {};
		//获取通知消息
		var arrNotifyTypes = [];
		var sNotifyTypes = this.makeNotifyTypesForSepratorStartPostData(arrNotifyTypes);
		//获取指定的人
		var arrSelectedUserIds = this.getCurrSelectedUserIds();

		//得到暂存的下个节点的信息
		var nextNodes = PageContext.m_hNodeInfos;
		//遍历指定的用户，将下一个节点信息和通知类型添加上去
		var nUserIndex = 0;
		for(var sNodeId in nextNodes){
			if(nUserIndex == arrSelectedUserIds.length)
				break;
			var data = Object.extend({}, _postData);
			data['NextNodeId'] = sNodeId;
			Object.extend(data, {
				NotifyTypes: sNotifyTypes,
				ToUserIds: arrSelectedUserIds[nUserIndex]
			});
			var sMethod = (PageContext.params['resubmited']) ? 'reSubmitTo' : 'submitTo';
			aCombine.push(BasicDataHelper.Combine('wcm6_process', sMethod, data));
			nUserIndex ++;
		}
		//附加一个nodeid=0的请求信息，表示所选的节点均已作为并联审批节点处理
		if(PageContext.params['workmodal'] == 2 && !$('fdsSeperatorSelection').disabled){
			var data = Object.extend({}, _postData);
			data['NextNodeId'] = 0;
			data['ToUserIds'] = arrSelectedUserIds.join(",");
			aCombine.push(BasicDataHelper.Combine('wcm6_process', sMethod, data));
		}		
		return aCombine;
	},
	getCurrSelectedUserIds : function(){
		var eUsers = document.getElementsByName('selectedUserIds');
		var arrUsers = [];
		for (var i = 0; i < eUsers.length; i++){
			if(!eUsers[i].checked)continue;
			var nUserId = eUsers[i].value;
			if(nUserId == null || isNaN(parseInt(nUserId))) {
				continue;
			}
			arrUsers.push(nUserId);
		}
		return arrUsers;
	},
	__getUserIds : function(_toUsers, _arrSelectedIds){
		if(_arrSelectedIds!=null){
			return _arrSelectedIds.join();
		}
		if(_toUsers == null || !(_toUsers.length > 0)) {
			return '';
		}
		//else
		var arrUserIds = [];
		for (var i = 0; i < _toUsers.length; i++){
			arrUserIds.push(_toUsers[i]['USERID']);
		}
		return arrUserIds.join(',');
	}
};

function initPage(_params){
	PageContext.loadPage(_params);
}

function doOK(){
	var oPostData = {
		PostDesc: $('txtComment').value,
		ObjectIds: PageContext.params['flowid'],
		ContentType: PageContext.params['ctype'],
		ContentId: PageContext.params['cid']
	}
	//Object.extend(oPostData, PageContext.getBranchInfo());
	//var sMethod = PageContext.m_sCurrSpecialOption || ((PageContext.params['resubmited']) ? 'reSubmitTo' : 'submitTo');

	var aCombine = [];
	if(PageContext.m_sCurrSpecialOption == 'hold'){
		return null;
	}
	if(PageContext.m_sCurrSpecialOption != null){
		if(PageContext.m_sCurrSpecialOption == 'referredto'){
			//转交给一个指定的节点
			var nReferredToNodeId = $('selReferredToNodes').value;
			if(!nReferredToNodeId){
				alert("转交必须选择一个目标节点！");
				return false;
			}
			oPostData['ReffererTo'] = true;
			aCombine = PageContext.buildPostData(oPostData);
		}else{
			var method = PageContext.m_sCurrSpecialOption;
			if(PageContext.m_sCurrSpecialOption == 'backTo'){
				oPostData['NotifyTypes'] = 'email,message';
			}
			if(PageContext.m_sCurrSpecialOption == 'applyExtension'){
				//申请延时还是走返工的逻辑，内部设置一个标识来处理
				oPostData['NotifyTypes'] = 'email,message';
				oPostData['ApplyExtension'] = '1';
				method = "backTo";
			}
			aCombine.push(BasicDataHelper.Combine('wcm6_process', method, oPostData));	
		}
	}else{
		aCombine = PageContext.buildPostData(oPostData);
	}
	//ge gfc add @ 2007-8-14 9:21 对非并联审批节点，强制要求填写处理人员
	if(Element.visible('divOptionalParams') 
		&& PageContext.params['workmodal'] != 2) {
		var nodeParams = PageContext.m_hNodePreparedBranchInfos[PageContext.m_nCurrNodeId];
		if(nodeParams && nodeParams['SelectedUsers'] && !(nodeParams['SelectedUsers'].length > 0)){
			if(($('cb_asRule') && !$('cb_asRule').checked)|| $('cb_asRule')==undefined){
				Ext.Msg.warn((wcm.LANG['IFLOWCONTENT_28'] || '请指定下一节点的处理人员!'));
				return false;
			}
		}
	}
		//liuyou add @ 2007-8-14 9:21 对并联审批节点，强制要求填写处理人员
	if(Element.visible('divOptionalParams') 
		&& PageContext.params['workmodal'] == 2) {
		//TODO
	}
	if(!$('txtComment').disabled){
		var sLen = 0;
		if($('txtComment').value.trim() == ''){
			if(!confirm(wcm.LANG['IFLOWCONTENT_13'] || '没有填写处理意见！仍要继续提交吗？')){
				//兼容了该页面嵌入到别的页面的时候，控件不可见引起的问题
				try{
					$('txtComment').select();
					$('txtComment').focus();
				}catch(ex){}
				return false;
            };
		}else if((sLen = $('txtComment').value.byteLength()) > 500){
			var sErrorInfo = '<span style="width:180px;overflow-y:auto;">' + String.format("处理意见限制为500个字符长度,当前为{0}字节。<br><br><b>提示:</b>每个汉字长度为2",sLen) + '</span>';
			Ext.Msg.alert(sErrorInfo, function(){
				//兼容了该页面嵌入到别的页面的时候，控件不可见引起的问题
				try{
					$('txtComment').select();
					$('txtComment').focus();
				}catch(ex){}
			});
			return false;
		}
	}
	var cbr = wcm.CrashBoarder.get(window);
	if(cbr)
	cbr.hide();
	BasicDataHelper.multiCall(aCombine, function(_trans, _json){
		cbr.notify(_trans);
		cbr.close();
		/*
		Ext.Msg.$success((wcm.LANG['IFLOWCONTENT_30'] ||'您已经成功处理了流转文档！') + '<br><br><b>' + (wcm.LANG['IFLOWCONTENT_16'] ||'提示') + '：</b>'+ (wcm.LANG['IFLOWCONTENT_31'] ||'当前窗口在您点击“确认”后关闭。'), function(){
			 if(cbr){
				 cbr.notify(_trans);
				 cbr.close();
			 }
		});
		*/
	});
	return false;
}
function reasignUsers(){
	var sUrl = WCMConstants.WCM6_PATH + 'include/select_index.jsp';
	var groupInfo = PageContext['GroupRange'];
	if(groupInfo != null && groupInfo['GroupId'] > 0) {
		sUrl = WCMConstants.WCM6_PATH + 'include/grouped_select_index.jsp?GroupIds=' + groupInfo['GroupId'];
	}
	var arrUsers = [];
	/*
	var eUsers = $('spUsers').getElementsByTagName('span');
	for (var i = 0; i < eUsers.length; i++){
		arrUsers.push(eUsers[i].getAttribute('_uid', 2));
	}
	*/
	var arrSelectedUsersEle = document.getElementsByName("selectedUserIds");
	for(var i=0;i<arrSelectedUsersEle.length;i++){
		if(arrSelectedUsersEle[i].checked){
			arrUsers.push(arrSelectedUsersEle[i].value);
		}
	}
	var cbParams = {
		id : 'select_user_or_group',
		title :  wcm.LANG['IFLOWCONTENT_88'] || '选择用户/组织',
		reloadable : true,
		url : sUrl,
		src : sUrl,
		width: '850px',
		height: '550px',
		params : {UserIds : arrUsers.join(','),FromProcess:1},
		maskable : true,
		callback : function(arOwners){
			if(arOwners == null){
				return;
			}
			if(arOwners.length < 2){
				Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_32'] || "返回值不正确！");
				return;
			}
			var arrBranchInfos = [];
			var arrUserIds = arOwners[0].split(',');
			var arrUserNames = arOwners[1].split(',');
			for (var i = 0; i < arOwners[0].length; i++){
				if(arrUserIds[i] == null || arrUserIds[i].trim() == '') {
					continue;
				}
				arrBranchInfos.push({
					USERID: arrUserIds[i],
					USERNAME: arrUserNames[i]
				});
			}
			if(arrBranchInfos.length>0){
				if($('cb_asRule')){
					Element.hide('sp_asRule');
					$('cb_asRule').checked = false;
				}
			}
			else{
				Element.show('sp_asRule');
				$('cb_asRule').checked = true;
			}
			var sValue = TempEvaler.evaluateTemplater('template_users', arrBranchInfos, null);
			Element.update($('spUsers'), sValue); 
			var arrSelectedUserIds = document.getElementsByName("selectedUserIds");
			for (var i=0; i<arrSelectedUserIds.length; i++){
				arrSelectedUserIds[i].checked = true;
			}
			this.close();
			//兼容在文档编辑页面使用的时候，流转页面高度的随着当前的用户信息来展示
			if(parent.setIframeHeight){
				parent.setIframeHeight();
			}
			return;
		}
	}	
	if(wcm.CrashBoarder){
		wcm.CrashBoarder.get('select_user_or_group').show(cbParams);
	}else{
		var cb = parent.wcm.CrashBoard.get(cbParams);
		cb.show();
	}
}
function doCancel(){ 
	return window.close();
}
