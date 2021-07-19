com.trs.wcm.TemplateMaster = Class.create("wcm.TemplateMaster");
Object.extend(com.trs.wcm.TemplateMaster.prototype,com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.TemplateMaster.prototype,{
	call : function(){
		try{
			EMPLOYERS_SIZE = 3;
			var oPostData = {
					ObjectId: this.params.ObjectId,
					PageSize: EMPLOYERS_SIZE,
					fieldsToHtml: 'TempName,TempDesc,Folder.Name,Employer.Name',
					ContainsRight: true
				};
			// ge gfc add @ 2007-4-28 17:02 加入 hostid, hosttype 字段
			oPostData['HostId'] = this.params['HostObjectId'];
			oPostData['HostType'] = (this.params['HostObjectType'] == 'website') ? '103' : '101';

			if(this.isHostObjectAttribute){
				Object.extend(oPostData,{"ContainsRight":true});
			}
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			var aCombine = [];
			aCombine.push(oHelper.Combine('wcm6_template','findByIdWithoutText'
				,oPostData));
			// 根据模板类型选择其中一个方法执行
			if(this.params['temptype'] == 0) {
				aCombine.push(oHelper.Combine('wcm6_template','queryTemplatesNested'
					,oPostData));
			}else{
				aCombine.push(oHelper.Combine('wcm6_template','queryEmployers'
					, oPostData));

				aCombine.push(oHelper.Combine('wcm6_template','queryNestedTemplates'
					,oPostData));			
			}
			var fOnSuccess = this.getOnSuccess();
			oHelper.MultiCall(aCombine, function(_oXmlTrans, _json){
				try{
					//TODO
					var tempType = $v(_json, 'multiresult.template.temptype.id');
					tempType = parseInt(tempType);
					window.m_nCurrTemplateType = tempType;

					fOnSuccess(_oXmlTrans, _json);
					switch(tempType){
						case 0: //嵌套
							Element.show('divTempNestersAttr');
							PageContext.showMoreNesters(_json, EMPLOYERS_SIZE, this.params.ObjectId);
							break;
						case 1: // 概览
							Element.show('divTempOutputFileAttr');
							Element.show('divTempFileExtAttr');
							Element.show('divTempRefedAttr');
							Element.show('divTempNestedAttr');
							PageContext.showArrangeEmployment(this.ObjectRight, this.params.ObjectId, _json);
							PageContext.showMoreEmployers(_json, EMPLOYERS_SIZE, this.params.ObjectId);
							PageContext.showMoreNested(_json, EMPLOYERS_SIZE, this.params.ObjectId);
							break;
						case 2: // 细览
							Element.show('divTempFileExtAttr');
							Element.show('divTempRefedAttr');
							Element.show('divTempNestedAttr');
							PageContext.showArrangeEmployment(this.ObjectRight, this.params.ObjectId, _json);
							PageContext.showMoreEmployers(_json, EMPLOYERS_SIZE, this.params.ObjectId);
							PageContext.showMoreNested(_json, EMPLOYERS_SIZE, this.params.ObjectId);
							break;
						default:
							break;
					}
				}catch(err){
					//TODO logger
					//alert(err.message);
				}

			}.bind(this));

			delete oPostData;	
		}catch (ex){
			//TODO logger
			//alert(ex.stack || ex.message);
		}
	},
	extraParams : function(){
		return this.getHostParams();
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters['objectrights'],'0');
		
		//设置第一块操作面板的类型
		var sHostObjectType = _oPageParameters['hosttype'];
		var	sHostObjectId = _oPageParameters['hostid'];
		if(sHostObjectType == '101'){
			this.setOperPanelType(1,'templateInChannel');
			sHostObjectType = 'channel';
		}else if(sHostObjectType == '103'){
			this.setOperPanelType(1,'templateInSite');
			sHostObjectType = 'website';
		}else{
			alert('无法获取hosttype[' + sHostObjectType + ']下的模板属性！' );
			return;
		}

		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){
			//一般与HostObject类型一致
			this.setOperPanelType(2, sHostObjectType);			
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "template");			
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "templates");			
		}
		//设置Host的信息
		this.setHostObject(sHostObjectType, sHostObjectId);		
		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}		

		Object.extend(this.params, {
			temptype: _oPageParameters['temptype'] || 1
		});
	},
	saveSuccess : function(_bIgnoreUpdate){
		//if(!_bIgnoreUpdate){
			//$MessageCenter.sendMessage('main','Grid.updateColumn','Grid',this.RowColumnInfo);
		$MessageCenter.sendMessage('main','PageContext.refreshCurrRows', 'PageContext', null);
		//}
	},
	saveFailure : function(transport){
		//TODO
		//$alert(transport.responseXML);
		$render500Err(transport, com.trs.util.JSON.parseXml(transport.responseXML));
	},
	exec : function(_sOperType, _sOperName){//操作响应
		//获取足够的参数?
		var oParams = {};
		Object.extend(oParams, {
			hostPanelType: _sOperType, 
			hostObjectType: OperAttrPanel.params["HostObjectType"], 
			hostObjectId: OperAttrPanel.params["HostObjectId"],
			objectIds: this.getObjectIds(),
			containsChildren: PageContext.params.containsChildren || false,
			isSolo: _sOperType == 'template',
			num: PageContext.params.ListRecNum || 0
		});
		var sObjectIds = this.getObjectIds();
//		var oHostParams = this.getHostParams();
		switch(_sOperType){
			case 'template':
			case 'templates':
				$templateMgr[_sOperName.toLowerCase()](sObjectIds, oParams);
				break;
			case 'templateInChannel':
			case 'templateInSite':
				$templateMgr[_sOperName.toLowerCase()](0, oParams);
				break;
			case 'channel':
				$channelMgr[_sOperName.toLowerCase()](sObjectIds);
				break;
			case 'website':
				$webSiteMgr[_sOperName.toLowerCase()](sObjectIds);
				break;
			default:
				alert('错误的操作类型.');
		}
	}
});

// ge gfc add @ 2007-12-26 21:18 显示“分配”
PageContext.showArrangeEmployment = function(_sRight, _nTempId, _json){
	//拥有模板查看权限，即可进行分配操作
	if(!isAccessable4WcmObject(_sRight, 24)) {
		return;
	}
	//else
	var nHostType = parseInt($v(_json, 'multiresult.template.folder.type'), 10);
	var nHostId   = parseInt($v(_json, 'multiresult.template.folder.id'), 10);
	if(!(nHostType > 0) || !(nHostId > 0)) {
		return;
	}
	//ge gfc add @ 2008-4-2 16:41 加入模板类型信息
	var nTempType   = parseInt($v(_json, 'multiresult.template.temptype.id'), 10);
	Event.observe($('lnkArrangeEmployment'), 'click', function(){
		var url = './template/channel_select.html?TemplateId=' + _nTempId + '&TemplateType=' + nTempType
			+ '&HostType=' + nHostType + '&HostId=' + nHostId;
		//需要同时传入是否对当前站点是否可以修改（设置模板）的权限
		if(nHostType == '103' && isAccessable4WcmObject(_sRight, 1)) {
			url += '&SiteModify=1';
		}
		FloatPanel.open(url, '将模板分配给栏目或站点使用', 300, 370);
		return false;
	}, false);	
}

PageContext.showMoreEmployers = function(_json, _nEmpSize, _nTempId){
	//ge gfc add @ 2008-4-2 16:41 加入模板类型信息
	var nTempType   = parseInt($v(_json, 'multiresult.template.temptype.id'), 10);

	var employersNum = parseInt($v(_json, 'multiresult.TemplateEmploys.num'));
	if(!isNaN(employersNum) && employersNum > _nEmpSize) {
		Element.show('divMoreEmployers');
		Event.observe($('lnkShowMoreEmp'), 'click', function(){
			FloatPanel.open('./template/template_employers.html?viewmode=1&ObjectId=' + _nTempId + '&TemplateType=' + nTempType, '查看模板被栏目或站点使用的情况', 420, 300);
			return false;
		}, false);
	}
}
PageContext.showMoreNested = function(_json, _nEmpSize, _nTempId){
	var employersNum = parseInt($v(_json, 'multiresult.Templates.num'));
	if(!isNaN(employersNum) && employersNum > _nEmpSize) {
		Element.show('divMoreNested');
		Event.observe($('lnkShowMoreNested'), 'click', function(){
			FloatPanel.open('./template/template_employers.html?viewmode=3&ObjectId=' + _nTempId, '查看模板嵌套使用其他模板的情况', 420, 300);
			return false;
		}, false);
	}
}
PageContext.switchTypedDisplay = function(_eSelector){
	var tempType = parseInt(_eSelector.value);
	switch(tempType){
		case 0: //嵌套
			Element.hide('divTempOutputFileAttr');
			Element.hide('divTempFileExtAttr');
			Element.hide('divTempRefedAttr');
			Element.show('divTempNestersAttr');
			Element.hide('divMoreEmployers');
			break;
		case 1: // 概览
			Element.show('divTempOutputFileAttr');
			Element.show('divTempFileExtAttr');
			Element.show('divTempRefedAttr');
			Element.show('divTempNestedAttr');
			Element.hide('divTempNestersAttr');
			Element.hide('divMoreNesters');
			break;
		case 2: // 细览
			Element.hide('divTempOutputFileAttr');
			Element.show('divTempFileExtAttr');
			Element.show('divTempRefedAttr');
			Element.show('divTempNestedAttr');
			Element.hide('divTempNestersAttr');
			Element.hide('divMoreNesters');
			break;
		default:
			break;
	}

	delete _eSelector;
}

//ge gfc add @ 2008-2-27 根据模板使用关系输出一些提示信息
function getAdaptedUsenessTip(_isDefault){
	//alert(window.m_nCurrTemplateType)
	if(window.m_nCurrTemplateType == 2) {
		return '作为细览模板使用';
	}
	return _isDefault == '1' ? '作为首页模板使用' : '作为其他概览模板使用';
}
function getAdaptedUsenessColor(_isDefault){
	return _isDefault == '1' ? '#010101' : '#C44A3B';
}

function previewFolder(_nEmployerId, _nEmployerType, _nTempId){
	_nEmployerType	= parseInt(_nEmployerType, 10);
	if(window.m_nCurrTemplateType != 1) { //如果不是概览，则进行提示
		$confirm('当前模板并不是概览模板。<br><br>点击[确定]将按<b>默认首页模板</b>预览该' + (_nEmployerType == 103 ? '栏目' : '站点') + '。', function(){
			$dialog().hide();
			$templateMgr.previewFolder(_nEmployerId, _nEmployerType, 0);
		}, function(){
			$dialog().hide();
		});
		
		return;
	}
	$templateMgr.previewFolder(_nEmployerId, _nEmployerType, _nTempId);
}
