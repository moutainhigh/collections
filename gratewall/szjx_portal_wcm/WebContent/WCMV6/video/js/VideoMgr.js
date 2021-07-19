$import('com.trs.dialog.Dialog');
var VideoMgr = {
	serviceId : 'wcm6_document',
	helpers : {},

	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	
	//=====================================================//
	//====================业务行为==========================//
	//=====================================================//

	upload: function(_sObjectIds, _parameters) {
		//alert("upload(执行[Ids="+_sObjectIds+"], params=[" + _parameters + "]).\n" + Object.parseSource(_parameters));
		var chnId = _parameters.channelid;
		var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width=800'+',height=640';
		window.open("../video/video_add.jsp?ChannelId=" + chnId, "_blank", sFeature);
//		FloatPanel.open("./video/video_add.jsp?ChannelId=" + chnId, '上传新视频', 800, 640);
	},
	
	record: function(_sObjectIds, _parameters) {
		//alert("record(执行[Ids="+_sObjectIds+"], params=[" + _parameters + "]).\n" + Object.parseSource(_parameters));
		var chnId = _parameters.channelid;
		var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=0,left=0,border=0,width=800'+',height=640';
		window.open("../video/video_record.jsp?ChannelId=" + chnId, "_blank", sFeature);
	},

	live: function(_sObjectIds, _parameters) {
		var chnId = _parameters.channelid;
		var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=0,left=0,border=0,width=800'+',height=640';
		window.open("../video/live_add.jsp?ChannelId=" + chnId, "_blank", sFeature);
	},
	
	"import" : function(_sId,_oPageParams){
		var oParams = {
			DocumentId : _sId,
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		FloatPanel.open('./document/document_import.jsp?' + $toQueryStr(oParams), '文档-导入视频文档', 500, 300);
	},

	play: function(_sObjectIds, _parameters) {
//		alert("VideoMgr.play([Ids="+_sObjectIds+"], params=[" + Object.parseSource(_parameters));
		window.open('../video/player.jsp?docId=' + _parameters['docids'], '_blank', 'width=640,height=480,location=yes');
	},

/*
// ls@2007-4-17 注释掉下列貌似多余的方法! TODO 确认后删除
	"delete" : function(_sDocIds,_oPageParams){
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '篇文档删除吗? ')){
			this.getHelper().call(this.serviceId,'delete', Object.extend(_oPageParams,{"ObjectIds": _sDocIds, "drop": true}), false, 
				function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				}
			);
		}
	},

	trash : function(_sDocIds,_oPageParams){
		alert("_sDocIds=" + _sDocIds + ", _oPageParams=" + _oPageParams);
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '个视频放入回收站吗? ')){
			this.getHelper().call(this.serviceId, 'delete', Object.extend(_oPageParams,{"ObjectIds": _sDocIds, "drop": false}), false, 
				function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				}
			);
		}
	},

	preview : function(_sObjectIds, _parameters){
		alert("对选中的数据[Ids="+_sObjectIds+"]进行预览操作!");
	},

	add : function(_sObjectIds, _parameters){
		this._doAddOrEdit(_sObjectIds, _parameters);
	},

	edit : function(_sObjectIds, _parameters){
		this._doAddOrEdit(_sObjectIds, _parameters);
	},
	
	publish : function(_sDocIds, _sMethodName){
		$documentMgr.publish(_sDocIds,_sMethodName);//wenyh@2007-01-17 发布视频操作,TODO需要Mr.刘珅完善
	},
*/

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
