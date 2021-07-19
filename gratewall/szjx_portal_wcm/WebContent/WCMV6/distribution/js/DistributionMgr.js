$import('com.trs.dialog.Dialog');
var DistributionMgr = {
	serviceId : 'wcm6_distribution',
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	
	//=====================================================//
	//====================业务行为==========================//
	//=====================================================//
	//删除单个和多个对象
	"delete" : function(_sObjectIds, _oPageParams){
		_sObjectIds = '' + _sObjectIds;
		var nCount = _sObjectIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '个分发点删除吗? ')){

			ProcessBar.init('删除进度');
			ProcessBar.addState('正在删除...', 2);
			ProcessBar.start();

			this.getHelper().call(this.serviceId, 
				'delete', //远端方法名				
				Object.extend(_oPageParams, {"ObjectIds": _sObjectIds}), //传入的参数
				false, //异步
				function(){//响应函数
					ProcessBar.next();
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				}
			);
		}
	},	

	add : function(_sObjectIds, _parameters){
		this._doAddOrEdit(0, _parameters);
	},

	edit : function(_sObjectIds, _parameters){
		this._doAddOrEdit(_sObjectIds, _parameters);
	},
	
	//启用单个和多个对象
	"enable" : function(_sObjectIds, _oPageParams){
		_sObjectIds = '' + _sObjectIds;
		var nCount = _sObjectIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '个分发点都启用吗? ')){
			ProcessBar.init('启用进度');
			ProcessBar.addState('正在启用...', 2);
			ProcessBar.start();
			Object.extend(_oPageParams, {Enable:true});
			this.updateStatus(_sObjectIds, _oPageParams);
		}
	},

	//禁用单个和多个对象
	"disable" : function(_sObjectIds, _oPageParams){
		_sObjectIds = '' + _sObjectIds;
		var nCount = _sObjectIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '个分发点都禁用吗? ')){
			ProcessBar.init('禁用进度');
			ProcessBar.addState('正在禁用...', 2);
			ProcessBar.start();
			Object.extend(_oPageParams, {Enable:false});
			this.updateStatus(_sObjectIds, _oPageParams);
		}
	},
	//禁用/启用单个和多个对象
	updateStatus : function(_sObjectIds, _oPageParams){
		this.getHelper().call(this.serviceId, 
			'updateStatus', //远端方法名				
			Object.extend(_oPageParams, {"ObjectIds": _sObjectIds}), //传入的参数
			false, //异步
			function(){//响应函数
				ProcessBar.next();
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', [_sObjectIds.split(","), false]);
			}
		);
	},
	//禁用所有对象
	disableall : function(_sObjectIds, _oPageParams){
		if (confirm('确实要将所有分发点都禁用吗? ')){
			ProcessBar.init('禁用进度');
			ProcessBar.addState('正在禁用...', 2);
			ProcessBar.start();
			Object.extend(_oPageParams, {Enable:false});
			this.updateStatusAll(_sObjectIds, _oPageParams);
		}		
	},
	//启用所有对象
	enableall : function(_sObjectIds, _oPageParams){
		if (confirm('确实要将所有分发点都启用吗? ')){
			ProcessBar.init('启用进度');
			ProcessBar.addState('正在启用...', 2);
			ProcessBar.start();
			Object.extend(_oPageParams, {Enable:true});
			this.updateStatusAll(_sObjectIds, _oPageParams);
		}		
	},	
	//禁用/启用所有对象
	updateStatusAll : function(_sObjectIds, _oPageParams){
		if(_oPageParams["siteid"] && _oPageParams["siteid"] != 0){
			Object.extend(_oPageParams, {FolderType:103, FolderId:_oPageParams["siteid"]});
		}else{
			Object.extend(_oPageParams, {FolderType:101, FolderId:_oPageParams["channelid"]});
		}
		this.getHelper().call(this.serviceId, 
			'updateStatusAll', //远端方法名				
			_oPageParams, //传入的参数
			false, //异步
			function(){//响应函数
				ProcessBar.next();
				var mainWin = $MessageCenter.iframes["main"].contentWindow;
				var ids = mainWin.Grid ? mainWin.Grid.getRowIds() : [];
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', [ids]);
			}
		);
	},

	//=====================================================//
	//====================内部方法==========================//
	//=====================================================//
	_doAddOrEdit : function(_nObjectId, _parameters){
		var tempObj = Object.extend({objectid:_nObjectId}, _parameters||{});
		FloatPanel.open('./distribution/distribution_add_edit.html?'+$toQueryStr(tempObj), '新建/修改站点分发', 350, 250);
	}
};
