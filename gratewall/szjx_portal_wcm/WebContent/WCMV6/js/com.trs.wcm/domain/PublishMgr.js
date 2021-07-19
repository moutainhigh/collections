$package('com.trs.wcm.domain');
$import('com.trs.dialog.Dialog');
var $publishMgr = com.trs.wcm.domain.PublishMgr = {
	serviceId : 'wcm6_publish',
	helpers : {},
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	publish : function(_sIds, _iObjectType, _sMethodName ,_oExtraParams, _sServiceId){
		//TODO 提示发布
		_sMethodName = _sMethodName || 'publish';
		var postData = Object.extend(_oExtraParams || {},{
			'ObjectIds' : _sIds, 
			'ObjectType' : _iObjectType
		});
		this.getHelper().call((_sServiceId || this.serviceId), _sMethodName, postData, true, function(_transport,_json){
			this.doAfterPublish(postData, _sMethodName,_transport,_json);
		}.bind(this));
	},
	doAfterPublish : function(_postData, _sMethodName,_transport,_json){
		if(_json!=null&&_json["REPORTS"]){
			var oReports = _json["REPORTS"];
			var stJson = com.trs.util.JSON;
			var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
			if(bIsSuccess=='false'){
				ReportsDialog.show(_json,'发布校验结果');
				return;
			}
		}
		var bIsPublished = (_sMethodName.toLowerCase() != 'recallpublish');
		$timeAlert('已经将您的发布操作提交到后台了...', 3, null, null, 2);
		//TODO 刷新main和oap中需要刷新的字段
		var params = Object.extend({}, _postData);
		params['StatusName'] = bIsPublished ? '已发' : '已否';
		params['StatusValue'] = bIsPublished ? '10' : '15';
		var objectids = params['ObjectIds'];
		if(!Array.isArray(objectids)) {
			params['ObjectIds'] = [objectids];
		}
		
		/* 不能立即更新状态
		$MessageCenter.sendMessage('main', 'PageContext.updateAfterPublish', 
			'PageContext', params);

		
		if(params['ObjectIds'].length == 1) {
			$MessageCenter.sendMessage('oper_attr_panel', 'PageContext.updateAfterPublish', 
				'PageContext', params);
		}//*/
	},
	_checkPreview_ : function(_sIds, _iObjectType, _extraParams, _sServiceId){
		this.getHelper().call((_sServiceId || this.serviceId),'preview', Object.extend(Object.extend({},_extraParams||{}),{
				objectIds: _sIds, 
				objectType: _iObjectType
		}), false, function(transport, json){
			var urlCount = com.trs.util.JSON.value(json, "URLCOUNT");
			if((''+_sIds).indexOf(",") >= 0){
				if(urlCount == 0){
					var message = "";
					var dataArray = com.trs.util.JSON.array(json,"DATA");
					for (var i = 0; i < dataArray.length; i++){
						message += dataArray[i].EXCEPTION + "<br>";
					}
					$alert(message, function(){$dialog().hide();});
				}else{
					this.__openPreviewPage(_sIds, _iObjectType, _extraParams);
				}
			}else{
				if(urlCount == 0){
					var fault = {
						code: 500,
						message: com.trs.util.JSON.value(json, "DATA[0].EXCEPTION"),
						detail: com.trs.util.JSON.value(json, "DATA[0].EXCEPTIONDETAIL")
					}
					FaultDialog.show(fault, '预览出错');
					/*$alert(com.trs.util.JSON.value(json, "DATA[0].EXCEPTION"), function(){
						$dialog().hide();
					});//*/
				}else{
					var urls = com.trs.util.JSON.value(json, "DATA[0].URLS");
					if(urls.length == 1){
						window.open(urls);
						return;
					}
					this.__openPreviewPage(_sIds, _iObjectType, _extraParams);
				}
			}			
		}.bind(this));
	},
	preview : function(_sIds, _iObjectType,_extraParams, _sServiceId){
		this._checkPreview_(_sIds,_iObjectType,_extraParams, _sServiceId);
	},
	__openPreviewPage : function(_sIds, _iObjectType,_extraParams){
		window.open('/wcm/WCMV6/preview/index.htm?objectType='+ _iObjectType + '&objectids=' + _sIds + '&'+$toQueryStr(_extraParams||{}), 'preview_page');
	}
};
