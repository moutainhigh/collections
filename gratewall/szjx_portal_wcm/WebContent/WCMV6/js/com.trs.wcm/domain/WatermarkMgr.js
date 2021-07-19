$import('com.trs.dialog.Dialog');
var WatermarkMgr = {
	serviceId : 'wcm6_watermark',
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	add : function(_sObjectIds, _parameters){
		Object.extend(_parameters,{ObjectId:0});
		FloatPanel.open("./photo/watermark_addedit.html?" + $toQueryStr(_parameters), '上传水印', 680, 200);
	},

	edit : function(_sObjectIds, _parameters){
		if(_sObjectIds == null || _sObjectIds.trim().length == 0){
			$alert("请选择需要编辑的水印");
			return false;
		}
		Object.extend(_parameters,{ObjectId:_sObjectIds});
		FloatPanel.open("./photo/watermark_addedit.html?" + $toQueryStr(_parameters), '编辑水印', 680, 200);
	},		

	"delete" : function(_sDocIds,_oPageParams){		
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		$confirm('确实要将这' + sHint + '个水印删除吗? ',function(){
			$dialog().hide();
			BasicDataHelper.call("wcm6_watermark",'delete', Object.extend({"ObjectIds": _sDocIds},{"LibId":_oPageParams['siteid']}), false, function(){
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
			});
		},function(){
			$dialog().hide();
		},"删除确认");
		/*
		if (confirm('确实要将这' + sHint + '个水印删除吗? ')){
			this.getHelper().call(this.serviceId,'delete', Object.extend({"ObjectIds": _sDocIds},{"LibId":_oPageParams['siteid']}), false, 
				function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				}
			);
		}*/
	}	
};
