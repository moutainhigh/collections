$package('com.trs.wcm.domain');
com.trs.wcm.domain.ExampleMgr = {
	serviceId : 'wcm6_document',
	
	//=====================================================//
	//====================业务行为==========================//
	//=====================================================//
	preview : function(_sObjectIds, _parameters){
		alert("对选中的数据[Ids="+_sObjectIds+"]进行预览操作!");
	},

	"delete" : function(_sObjectIds, _parameters){
		alert("对选中的数据[Ids="+_sObjectIds+"]进行删除操作!");
	},

	add : function(_sObjectIds, _parameters){
		this._doAddOrEdit(_sObjectIds, _parameters);
	},

	edit : function(_sObjectIds, _parameters){
		this._doAddOrEdit(_sObjectIds, _parameters);
	},
	

	//=====================================================//
	//====================内部方法==========================//
	//=====================================================//
	_doAddOrEdit : function(_nObjectId, _parameters){
		if(_nObjectId == 0){
			alert("新增数据");
		}else{
			alert("修改数据[ID="+_nObjectId+"]");
		}
	}
};
