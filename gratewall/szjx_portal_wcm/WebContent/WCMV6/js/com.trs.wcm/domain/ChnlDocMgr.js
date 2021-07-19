$package('com.trs.wcm.domain');

$import('com.trs.dialog.Dialog');
var $chnlDocMgr = com.trs.wcm.domain.ChnlDocMgr = Object.extend(Object.clone($documentMgr), {
	serviceId : 'wcm6_viewdocument',
	ObjectType : 605,
	changestatus : function(_sDocIds,_oPageParams){
		var oPostData = {
			'ChannelIds' : _oPageParams['channelids'],
			'ObjectIds' : _sDocIds
		}
		FloatPanel.open('./document/change_status.jsp?' + $toQueryStr(oPostData), '文档-改变状态', 400, 140);
	},
	setright : function(_sDocIds,_oPageParams){// to docids
		$openCenterWin("../auth/right_set.jsp?ObjType=" + this.ObjectType + "&ObjId=" + _oPageParams.docids, "document_right_set", 900, 600, "resizable=yes");
	},
	publish : function(_sDocIds, _sMethodName){
		_sMethodName = _sMethodName || 'publish';
		var oPostData = {'ObjectIds' : _sDocIds};
		this.getHelper().call(this.serviceId, _sMethodName, oPostData, true, function(_transport,_json){
			$publishMgr.doAfterPublish(oPostData, _sMethodName,_transport,_json);
		}.bind(this));
	},
	preview : function(_sDocIds,_oPageParams){
		//$publishMgr.serviceId = this.serviceId;
		$publishMgr.preview(_sDocIds, '600', null, this.serviceId);
	},
	recallpublish : function(_sDocIds,_oPageParams){
		this.publish(_sDocIds, 'recallPublish');
	},
	copy : function(_sDocIds,_oPageParams){
		var oPostData = this.__preparePostData(_sDocIds,_oPageParams);
		FloatPanel.open('./document/document_copyto.html?' + $toQueryStr(oPostData), '文档-文档复制到...', 400, 350);
	},
	"move" : function(_sDocIds,_oPageParams){
		var oPostData = this.__preparePostData(_sDocIds,_oPageParams);
		FloatPanel.open('./document/document_moveto.html?' + $toQueryStr(oPostData), '文档-文档移动到...', 400, 350);
	},
	quote : function(_sDocIds,_oPageParams){
		var oPostData = this.__preparePostData(_sDocIds,_oPageParams);
		FloatPanel.open('./document/document_quoteto.html?' + $toQueryStr(oPostData), '文档-文档引用到...', 400, 450);
	},
	trash : function(_sDocIds,_oPageParams){
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		var params = {
			objectids: _sDocIds,
			operation: '_trash'
		}
		this.doOptionsAfterDisplayInfo(params, function(){
			var aTop = (top.actualTop||top);
			var mpb = new aTop.ModalProcessBar('放入废稿箱');
			this.getHelper().call(this.serviceId, 'delete', Object.extend(_oPageParams,{"ObjectIds": _sDocIds, "drop": false}), true, 
				function(){
					setTimeout(function(){
						mpb.next();
						setTimeout(function(){
							mpb.next();
							$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
						},500);
					},500);
				}
			);
		}.bind(this));
	},
	"delete" : function(_sDocIds,_oPageParams){
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		var params = {
			objectids: _sDocIds,
			operation: '_forcedelete'
		}
//		if (confirm('确实要将这' + sHint + '篇文档强制删除吗? ')){
			this.doOptionsAfterDisplayInfo(params, function(){
				var aTop = (top.actualTop||top);
				var mpb = new aTop.ModalProcessBar('强制删除文档');
				this.getHelper().call(this.serviceId, 'delete', Object.extend(_oPageParams,{"ObjectIds": _sDocIds, "drop": true}), true, 
					function(){
						setTimeout(function(){
							mpb.next();
							setTimeout(function(){
								mpb.next();
								$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
							},500);
						},500);
					}
				);
			}.bind(this));
//		}
	},
	doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
		var DIALOG_DOCUMENT_INFO = 'document_info_dialog';
		var aTop = (top.actualTop||top);
		TRSCrashBoard.setMaskable(true);
		aTop.m_eDocumentInfo = TRSDialogContainer.register(DIALOG_DOCUMENT_INFO, '系统提示信息'
		, './document/document_info.html', '500px', '280px', true);
		aTop.m_eDocumentInfo.setAdjustedPlacing(true);

		aTop.m_eDocumentInfo.onFinished = _fDoAfterDisp;
		
		TRSDialogContainer.display(DIALOG_DOCUMENT_INFO, _params);	
	},
/*	
	"delete" : function(_sDocIds,_oPageParams){
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '篇文档删除吗? ')){
			this.getHelper().call(this.serviceId,'delete', Object.extend(_oPageParams,{"ObjectIds": _sDocIds, "drop": true}), true, 
				function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				}
			);
		}
	},
//*/
	"export" : function(_sDocIds,_oPageParams){
		var oPostData = this.__preparePostData(_sDocIds,_oPageParams);
		oPostData['DocumentIds'] = _oPageParams['docids'];
		oPostData['ChannelIds'] = _oPageParams['channelids'];
		//alert($toQueryStr(_oPageParams) + '\n' + $toQueryStr(oPostData))
		FloatPanel.open('./document/document_export.jsp?' + $toQueryStr(oPostData), '文档-导出文档', 300, 100);
	},
	'exportall' : function(_sDocIds,_oPageParams){
		if(confirm('此操作可能需要较长时间。确实要导出所有文档吗？')) {
			var oPostData = {ExportAll: 1};
			if(_oPageParams['channelid']) {
				oPostData['ChannelId'] = _oPageParams['channelid'];
			}else if(_oPageParams['siteid']) {
				oPostData['SiteId'] = _oPageParams['siteid'];
			}
			//alert($toQueryStr(_oPageParams) + '\n' + $toQueryStr(oPostData))
			FloatPanel.open('./document/document_export.jsp?' + $toQueryStr(oPostData), '文档-导出所有文档', 300, 100);
		}	
	},
	backup : function(_sDocIds,_oPageParams){
		//var oPostData = this.__preparePostData(_sDocIds,_oPageParams);
		var oPostData = {
			docids: _oPageParams.docids,
			ExcludeTrashed: _oPageParams['ExcludeTrashed'] || false
		};
		this.getHelper().call('wcm6_documentBak','backup', oPostData, true,
			function(_transport,_json){
				ReportsDialog.show(_json,'文档版本保存结果');
			}
		);
	},
	recoverBak : function(_nVersion,_oPageParams,_fCallBack){
		var oPostData = {
			DocumentId : _oPageParams.DocumentId,
			Version : _nVersion
		};
		this.getHelper().call('wcm6_documentBak','recover', oPostData, true,
			function(_transport,_json){
				$timeAlert("成功恢复文档[ID="+_oPageParams.DocumentId+"]为版本[版本号="+(parseInt(_nVersion,10)+1)+"]!",5,null,null,2);
				if(_fCallBack)_fCallBack();
				else{
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
				}
			}
		);
	},
	deleteBak : function(_nVersion,_oPageParams,_fCallBack){
		var oPostData = {
			DocumentId : _oPageParams.DocumentId,
			ObjectIds : _nVersion
		};
		this.getHelper().call('wcm6_documentBak','delete', oPostData,true,
			function(_transport,_json){
				try{
					PageContext.RefreshList();
				}catch(err){}
				if(_fCallBack)_fCallBack();
			}
		);
	},
	viewBak : function(_nVersion,_oPageParams){
		var oPostData = {
			DocumentId : _oPageParams.DocumentId,
			Version : _nVersion
		};
		$openMaxWin('../document/document_backup_show.html?' + $toQueryStr(oPostData));
	},
	backupmgr : function(_sDocIds,_oPageParams){
		var sDocId = _oPageParams.docids || _sDocId;
		FloatPanel.open('./document/document_backupmgr.html?DocumentId=' + sDocId, '文档-版本管理', 600, 300);
	},
	edit : function(_sDocId,_oPageParams){
		var oParams = {
			DocumentId : _oPageParams.docids || _sDocId,
			ChannelId : _oPageParams["channelid"] || _oPageParams["channelids"] ||0,
			SiteId : _oPageParams["siteid"] || 0,
			FromEditor : 1
		}
		if(oParams.DocumentId=='0'&&oParams.ChannelId==0){
			//站点新建文档,分成两步,1,选择栏目;2,在相应栏目上新建
			FloatPanel.open('./document/document_siteadd_step1.html?' + $toQueryStr(oParams), '选择要新建文档的栏目', 400, 350);
			return;
		}
		setTimeout(function(){
			$openMaxWin("../document/document_addedit.jsp?" + $toQueryStr(oParams));
		},10);
	},

    "docpositionset" : function(_sDocId,_oPageParams){
		var oParams = {
			DocumentId : _oPageParams.docids || _sDocId,
			ChannelId : _oPageParams["currchnlid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
	     FloatPanel.open('./document/document_position_set.jsp?' + $toQueryStr(oParams), '文档-改变位置到...', 300, 150);
	},


	view : function(_sDocId,_oPageParams){
		var oParams = {
			DocumentId : _oPageParams.docids || _sDocId,
			ChnlDocId : _oPageParams["chnldocid"],
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0,
			FromRecycle : _oPageParams["fromrecycle"] || 0
		}
		
		if (_oPageParams['sitetype']=='2') {
			FloatPanel.open('./video/player.jsp?docId=' + oParams["DocumentId"],'回收站-播放视频',640,560);
		} else {
			$openMaxWin('../document/document_detail_show.jsp?' + $toQueryStr(oParams));
		}
//		$openMaxWin('../document/document_detail_show.html?' + $toQueryStr(oParams));
	},
	saveorder : function(_iDocId,_iPosition,_iTargetDocId,_iChannelId,_oCallBacks){
		_oCallBacks = _oCallBacks || {};
		this.getHelper().call(this.serviceId,'changeOrder',
			{"FromDocId":_iDocId,"ToDocId":_iTargetDocId,"position":_iPosition,"channelid":_iChannelId}, false, 
			_oCallBacks["onSuccess"],_oCallBacks["onFailure"],_oCallBacks["onFailure"]
		);
	},
	commentmgr : function(_sDocId, _oPageParams){
		var oParams = {
			DocumentId : _oPageParams.docids || _sDocId,
			ChannelId : _oPageParams["channelids"] || 0,
			SiteId : _oPageParams["siteids"] || 0
		}
		var sUrl = '../../comment/comment_mgr.jsp?' + $toQueryStr(oParams);
		$openMaxWin(sUrl);
	},
	__preparePostData : function(_sDocIds,_oPageParams){
		if(Array.isArray(_sDocIds)){
			_sDocIds = _sDocIds.reverse();
		}
		else if(String.isString(_sDocIds)){
			_sDocIds = _sDocIds.split(',').reverse().join(',');
		}
		return {
			'objectids': _sDocIds,
			'channelids' : _oPageParams['channelids']
		};
	},
	copyToTop : function(_sDocIds, _sDocTitles, _oPageParams){
		(top.actualTop||top)._QuickDataCenter_ = {
			ChnlDocIds : _sDocIds,
			ChnlDocTitles : _sDocTitles,
			ChannelId : _oPageParams['channelid']
		};
	},
	cutToTop : function(_sDocIds, _sDocTitles, _oPageParams){
		(top.actualTop||top)._QuickDataCenter_ = {
			IsCutting : true,
			ChnlDocIds : _sDocIds,
			ChnlDocTitles : _sDocTitles,
			ChannelId : _oPageParams['channelid']
		};
	},
	_checkHostRight : function(_oPageParams){
		if(!_oPageParams['channelid'])return false;
		if(_oPageParams['IsVirtual']!='0')return false;
		if(_oPageParams['RightValue'] && !isAccessable4WcmObject(_oPageParams['RightValue'], 31)){
			return false;
		}
		return true;
	},
	_PasteInTo : function(_sMethod, _oPageParams){
		var oActualTop = (top.actualTop||top);
		if(oActualTop._QuickDataCenter_==null)return;
		if(!this._checkHostRight(_oPageParams))return;
		var _sDocIds = oActualTop._QuickDataCenter_.ChnlDocIds;
		var _sDocTitles = oActualTop._QuickDataCenter_.ChnlDocTitles;
		var bIsCutting = oActualTop._QuickDataCenter_.IsCutting;
		if(bIsCutting && _sMethod=='quote'){
			return;
		}
		_sMethod = (bIsCutting && _sMethod=='copy')?'move':'copy';
		var sDisplay = (_sMethod=='quote') ? '引用' : ((bIsCutting)?'移动':'复制');
		var sConfirmTip = '您确定要' + sDisplay + '以下文档？           \n';
		if(_sDocTitles){
			var arrIds = _sDocIds.split(',');
			var arrTitles = _sDocTitles.split(',');
			for (var i = 0; i < arrIds.length; i++){
				sConfirmTip += '  '+(i+1)+'、[文档-'+arrIds[i]+']：'+(arrTitles[i]||'')+'\n';
			}
		}
		else{
			sConfirmTip += _sDocIds;
		}
		if(!confirm(sConfirmTip)){
			return;
		}
		var oPostData = null;
		if(_sMethod=='move'){
			oPostData = {
				"ObjectIds" : _sDocIds,
				"ToChannelId" : _oPageParams['channelid']
			}
		}
		else{
			oPostData = {
				"ObjectIds" : _sDocIds,
				"ToChannelIds" : _oPageParams['channelid']
			}
		}
		_sMethod = _sMethod || 'quote';
		this.getHelper().Call(this.serviceId, _sMethod, oPostData, true,
			function(_transport,_json){
				(top.actualTop||top).ReportsDialog.show(_json, '文档' + sDisplay + '结果', function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
				});
			}
		);
		oActualTop._QuickDataCenter_ = null;
	},
	pasteFromTop : function(_oPageParams){
		this._PasteInTo('copy', _oPageParams);
	},
	quoteFromTop : function(_oPageParams){
		var oActualTop = (top.actualTop||top);
		if(oActualTop._QuickDataCenter_!=null){
			if(oActualTop._QuickDataCenter_.ChannelId == _oPageParams['channelid'])
				return;
			this._PasteInTo('quote', _oPageParams);
		}
	}
});

Object.extend($chnlDocMgr, {
	//取消置顶
	settopdocument : function(_sDocIds, _oPageParams){
		var oPostData = {
			channelid : _oPageParams["channelid"],
			documentid : _oPageParams["docids"],
			targetdocumentid : 0,
			topflag : 0
		};
		this.getHelper().Call("wcm6_document", "settopdocument", oPostData, true,
			function(_transport,_json){
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
		});
	}
});
