$import('com.trs.dialog.Dialog');
var ReplaceMgr = {
	serviceId : 'wcm6_replace',
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
		if (confirm('确实要将这' + sHint + '个替换内容删除吗? ')){

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
	
	//=====================================================//
	//====================内部方法==========================//
	//=====================================================//
	_doAddOrEdit : function(_nObjectId, _parameters){
		FloatPanel.open('./replace/replace_add_edit.html?channelid=' + _parameters["channelid"] + "&objectid=" + _nObjectId, '新建/修改替换内容', 500, 200);
	}
};
