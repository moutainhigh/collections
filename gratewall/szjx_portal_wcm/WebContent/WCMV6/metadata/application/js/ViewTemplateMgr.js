var	ViewTemplateMgr = window.$chnlDocMgr ? Object.clone($chnlDocMgr) : {};
ViewTemplateMgr = Object.extend(ViewTemplateMgr, {
	//服务相关参数
	servicesName		: 'wcm6_MetaDataCenter',
	jQueryMethodName	: 'jQueryViewDatas',
	queryMethodName		: 'queryViewDatas',
	findMethodName		: 'findViewDataById',
	deleteMethodName	: 'deleteViewDatas',
	saveMethodName		: 'saveMetaViewData',
	existsMethodName	: 'exists',
	exportMethodName	: 'exportViewDatas',
	exportAllMethodName : 'exportAllViewDatas',

	
	getHelper : function(){
		return new com.trs.web2frame.BasicDataHelper();
	},
	
	/**
	*本来不应该在此引入与页面有关的逻辑，但由于有些地方用到的是
	*recId,有些地方用到的是docId,所以在此进行一下预处理
	*@params _sObjectIds -> recId
	*@return docId
	*/
	preProcessParams : function(_sObjectIds){
		try{
			var isArray = true;
			if(!Array.isArray(_sObjectIds)){
				_sObjectIds = _sObjectIds.split(",");
				isArray = false;
			}
			var mainWindow = $main();
			var aDocIds = []; 
			var aChnlIds = [];
			for (var i = 0; i < _sObjectIds.length; i++){
				var oRow = mainWindow.$("row_" + _sObjectIds[i]);
				if(oRow){
					aDocIds.push(oRow.getAttribute("docid"));
					aChnlIds.push(oRow.getAttribute("currchnlid"));
				}else{
					aDocIds.push(0);
					aChnlIds.push(0);
				}
			}
			if(!isArray){
				aDocIds = aDocIds.join();
				aChnlIds = aChnlIds.join();
			}
			return {
				docIds		: aDocIds,
				channelIds	: aChnlIds
			}
		}catch(error){
			//just skip it.
		}
	},

	//=====================================================//
	//====================业务行为==========================//
	//=====================================================//
	move : function(_sDocIds,_oPageParams){
		var hostParams = this.preProcessParams(_sDocIds);
		var oPostData = {
			'channelids' : hostParams['channelIds'],
			'objectids' : _sDocIds
		}
		FloatPanel.open('./metadata/record_moveto.html?' + $toQueryStr(oPostData), '移动记录', 400, 350);
	},
	quote : function(_sDocIds,_oPageParams){
		var hostParams = this.preProcessParams(_sDocIds);
		var oPostData = {
			'channelids' : hostParams['channelIds'],
			'objectids' : _sDocIds
		}
		FloatPanel.open('./metadata/record_quoteto.html?' + $toQueryStr(oPostData), '引用记录', 400, 350);
	},
	copy : function(_sDocIds,_oPageParams){
		var hostParams = this.preProcessParams(_sDocIds);
		var oPostData = {
			'channelids' : hostParams['channelIds'],
			'objectids' : _sDocIds
		}
		FloatPanel.open('./metadata/record_copyto.html?' + $toQueryStr(oPostData), '复制记录', 400, 350);
	},

	changestatus : function(_sDocIds,_oPageParams){
		var hostParams = this.preProcessParams(_sDocIds);
		var oPostData = {
			'objDesc'	 : encodeURIComponent('记录'),
			'ChannelIds' : hostParams['channelIds'],
			'ObjectIds' : _sDocIds
		}
		FloatPanel.open('./document/change_status.jsp?' + $toQueryStr(oPostData), '记录-改变状态', 400, 80);
	},

    "docpositionset" : function(_sDocId,_oPageParams){
		//fix me.需要传入chnldocid.
		var hostParams = this.preProcessParams(_sDocId);
		var oParams = {
			DocumentId : hostParams["docIds"],
			ChannelId : hostParams["channelIds"] || 0
		}
	     FloatPanel.open('./metadata/record_position_set.jsp?' + $toQueryStr(oParams), '记录-改变位置到...', 300, 150);
	},
	
	createfromexcel : function(_sDocIds,_oPageParams, _oOtherParams){
		try{
			if(!_oOtherParams || !_oOtherParams["viewId"]){
				_oOtherParams = $main().getPageParams() || {};
				if(!_oOtherParams["viewId"]){
					$alert("没有指定视图ID[viewId]");
					return;
				}
			}
		}catch(error){
			alert("ViewTemplateMgr.createfromexcel:" + error.message);
		}
		this.download("/wcm/WCMV6/metadata/read_excel.jsp?ViewId=" + _oOtherParams["viewId"]);
	},

	download : function(sURL){
		var frm = (top.actualTop||top).$('iframe4download');
		if(frm == null) {
			frm = (top.actualTop||top).document.createElement('IFRAME');
			frm.id = "iframe4download";
			frm.style.display = 'none';
			(top.actualTop||top).document.body.appendChild(frm);
		}
		frm.src = sURL;		
	},

	setsynrule : function(_sDocIds,_oPageParams, _oOtherParams){
		var channelId = _oOtherParams["channelid"];
		FloatPanel.open('./metadata/syn_rule_set.html?channelId=' + channelId, '设置数据同步到WCMDocument的规则', 500, 380);
	},

	"import" : function(_sId,_oPageParams, _oOtherParams){
		try{
			if(!_oOtherParams || !_oOtherParams["viewId"]){
				_oOtherParams1 = $main().getPageParams() || {};
				if(!_oOtherParams1["viewId"]){
					$alert("没有指定视图ID[viewId]");
					return;
				}
				_oOtherParams["viewId"] = _oOtherParams1["viewId"];
			}
		}catch(error){
			alert("ViewTemplateMgr.import:" + error.message);
		}
		var oParams = {
			DocumentId : _sId,
			ViewId	   : _oOtherParams["viewId"],
			ChannelId : _oOtherParams["channelid"] || 0,
			SiteId : _oOtherParams["siteid"] || 0
		}
		if(oParams.ChannelId==0){
			//站点智能创建文档,分成两步,1,选择栏目;2,在相应栏目上新建
			FloatPanel.open('./document/document_siteimport_step1.html?' + $toQueryStr(oParams), '选择要记录导入的目标栏目', 400, 350);
			return;
		}
		FloatPanel.open('./metadata/view_data_import.jsp?' + $toQueryStr(oParams), '导入记录', 500, 300);
	},

	"export" : function(_sId, _oPageParams, _oOtherParams){
		var _sObjectIds = this.preProcessParams(_sId)["docIds"];
		_sObjectIds = '' + _sObjectIds;
		try{
			if(!_oOtherParams || !_oOtherParams["viewId"]){
				_oOtherParams1 = $main().getPageParams() || {};
				if(!_oOtherParams1["viewId"]){
					$alert("没有指定视图ID[viewId]");
					return;
				}
				_oOtherParams["viewId"] = _oOtherParams1["viewId"];
			}
		}catch(error){
			alert("ViewTemplateMgr.export:" + error.message);
		}
		FloatPanel.open('./metadata/record_export.jsp?' + $toQueryStr({
			viewId		: _oOtherParams["viewId"],
			ObjectIds	: _sObjectIds					
		}), '导出记录', 480, 120);
		return;

		ProcessBar.init('导出进度');
		ProcessBar.addState('正在导出...', 2);
		ProcessBar.start();
		this.getHelper().call(
				this.servicesName, //远端服务名				
				this.exportMethodName, //远端方法名				
				{
					viewId		: _oOtherParams["viewId"],
					ObjectIds	: _sObjectIds					
				},//提交数据
				true, //post,get
				function(transport, json){//响应函数
					ProcessBar.next();
					var sURL = "../file/read_file.jsp?FileName=" + transport.responseText;
					this.download(sURL);
				}.bind(this)
		);
	},
	exportall : function(_sId, _oPageParams, _oOtherParams){
		try{
			if(!_oOtherParams || !_oOtherParams["viewId"]){
				_oOtherParams1 = $main().getPageParams() || {};
				if(!_oOtherParams1["viewId"]){
					$alert("没有指定视图ID[viewId]");
					return;
				}
				_oOtherParams["viewId"] = _oOtherParams1["viewId"];
			}
		}catch(error){
			alert("ViewTemplateMgr.export:" + error.message);
		}
		FloatPanel.open('./metadata/record_export.jsp?' + $toQueryStr({
			viewId		: _oOtherParams["viewId"] || 0,
			channelId	: _oOtherParams["channelid"] || 0,
			exportall   : 1
		}), '导出所有记录', 480, 120);
		return;

		ProcessBar.init('导出进度');
		ProcessBar.addState('正在导出...', 2);
		ProcessBar.start();
		this.getHelper().call(
				this.servicesName, //远端服务名				
				this.exportAllMethodName, //远端方法名				
				{
					viewId		: _oOtherParams["viewId"] || 0,
					channelId	: _oOtherParams["channelid"] || 0,
					exportall   : 1
				},//提交数据
				true, //post,get
				function(transport, json){//响应函数
					ProcessBar.next();
					var sURL = "../file/read_file.jsp?FileName=" + transport.responseText;
					this.download(sURL);
				}.bind(this)
		);
	},

	//删除单个和多个对象
	"delete" : function(_sObjectIds, _oPageParams){
		//_sObjectIds = this.preProcessParams(_sObjectIds)["docIds"];
		_sObjectIds = '' + _sObjectIds;
		var nCount = _sObjectIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '条记录放入废稿箱吗? ')){
			ProcessBar.init('删除进度');
			ProcessBar.addState('正在删除...', 2);
			ProcessBar.start();
			this.getHelper().call(
				//this.servicesName, //远端服务名		
				"wcm6_viewdocument", 
				//this.deleteMethodName, //远端方法名				
				"delete", //远端方法名				
				{"ObjectIds": _sObjectIds}, //传入的参数
				true, //post,get
				function(transport, json){//响应函数
					ProcessBar.next();
					var bSucess = $v(json, 'REPORTS.IS_SUCCESS') || true;
					if(bSucess == false || bSucess == 'false'){
						ReportsDialog.show(json, '删除数据结果', function(){
							$dialog().hide();
							$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
						});
					}else{
						$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
					}
				}
			);
		}
	},	
	
	view : function(_sObjectIds, _parameters, _oOtherParams){
		_sObjectIds = this.preProcessParams(_sObjectIds)["docIds"];
		try{
			if(!_oOtherParams || !_oOtherParams["viewId"]){
				_oOtherParams = $main().getPageParams() || {};
				if(!_oOtherParams["viewId"]){
					$alert("没有指定视图ID[viewId]");
					return;
				}
			}
		}catch(error){
			alert("ViewTemplateMgr._doAddOrEdit:" + error.message);
		}
		var urlParams = "?objectId=" + _sObjectIds;
		if(_parameters){
			urlParams += "&" + $toQueryStr(_parameters);
		}
		var url = "./metadata/application/" + _oOtherParams["viewId"] + "/detail_of_view.jsp" + urlParams;
		//FloatPanel.hide();
		FloatPanel.open(url, '查看对象', 450, 200);
		return false;
	},

	findById : function(_sobjectId, _oParams, _fBeforeSave, _fAfterSave, _f500){
		(_fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.servicesName, this.findMethodName,
				Object.extend({ObjectId : _sobjectId}, _oParams), true, function(transport, json){
			(_fAfterSave || Prototype.emptyFunction)(transport, json);
		}, _f500);
	},
	
	save : function(_sobjectId, _oParams, _fBeforeSave, _fAfterSave){
		var result = (_fBeforeSave || Prototype.emptyFunction)();
		if(result === false) return;
		if(typeof _oParams == 'string'){//form id.
			var params = _oParams;
		}else{
			var params = Object.extend({ObjectId : _sobjectId}, _oParams);
		}
		$beginSimplePB("正在保存数据");
		this.getHelper().call(this.servicesName, this.saveMethodName, params, true, function(transport, json){
			$endSimplePB();
			(_fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},

	add : function(_sObjectIds, _parameters, _oOtherParams){
		this._doAddOrEdit(0, _parameters, _oOtherParams);
	},

	edit : function(_sObjectIds, _parameters, _oOtherParams){
		_parameters = Object.clone(_parameters);
		_oOtherParams = Object.clone(_oOtherParams);
		if(_sObjectIds != 0){
			 var params = this.preProcessParams(_sObjectIds);
			 if(params){
				 _sObjectIds = params["docIds"];
				 if(_oOtherParams && _oOtherParams['channelid']){
					 _oOtherParams['channelid'] = params['channelIds'];
				 }else if(_parameters && _parameters["channelid"]){
					_parameters["channelid"] = params['channelIds'];
				 }
			 }
		}
		this._doAddOrEdit(_sObjectIds, _parameters, _oOtherParams);
	},
	
	_doAddOrEdit : function(_nObjectId, _parameters, _oOtherParams){
		var urlParams = "?objectId=" + _nObjectId;
		if(_oOtherParams && _oOtherParams["channelid"]){
			urlParams += "&channelid=" + _oOtherParams["channelid"];
		}else if(_parameters && _parameters["channelid"]){
			//urlParams += "&" + $toQueryStr(_parameters);
			//wenyh@2008-12-10 只获取channelid,其它参数(如检索)无用,且可能会导致错误
			urlParams += "&channelid=" + _parameters["channelid"];
		}
		try{
			if(!_oOtherParams || !_oOtherParams["viewId"]){
				_oOtherParams = $main().getPageParams() || {};
				if(!_oOtherParams["viewId"]){
					$alert("没有指定视图ID[viewId]");
					return;
				}
			}
		}catch(error){
			alert("ViewTemplateMgr._doAddOrEdit:" + error.message);
		}

		try{
			var mainWin = $main();
			if(mainWin && mainWin["openMethod"]){
				var oParams = urlParams.toQueryParams() || {};
				oParams["viewId"] = _oOtherParams["viewId"];
				mainWin["openMethod"].call(mainWin, oParams);
				return;
			}
		}catch(error){
		}
		/*
		if(_parameters){
			urlParams += "&" + $toQueryStr(_parameters);
		}
		*/
		var url = "./metadata/application/" + _oOtherParams["viewId"] + "/addedit_of_view.jsp" + urlParams;
		//FloatPanel.hide();
		FloatPanel.open(url, '新建/修改对象', 450, 200);
	},

	exists : function(oParams, fCallBack){
		this.getHelper().Call(this.servicesName, this.existsMethodName, oParams, true, function(transport, json){
			(fCallBack || Prototype.emptyFunction)(transport, json);
		});
	}
});