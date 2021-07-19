$package('com.trs.wcm.domain');

$import('com.trs.dialog.Dialog');
var $documentMgr = com.trs.wcm.domain.DocumentMgr = {
	serviceId : 'wcm6_document',
	helpers : {},
	/*Document.OBJ_TYPE*/
	ObjectType : 605,
	detail : function(_sDocId,_oPageParams){
		alert("d"+_sDocId);
	},
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
		/*
		_sServceFlag = (!_sServceFlag)?'basic':_sServceFlag.toLowerCase();
		if(!this.helpers[_sServceFlag]) {
			var oHelper = null;
			switch(_sServceFlag){
				case 'document' :
					oHelper = $dataHelper(com.trs.wcm.datasource.document.Documents);
					break;
				case 'pageinfo':
					oHelper = $dataHelper(com.trs.wcm.datasource.document.PageInfo);
					break;
				case 'basic' :
					oHelper = BasicDataHelper;
					break;
				default :
					alert('DocumentMgr不支持"'+_sServceFlag+'"数据源');
					break;
			}
			this.helpers[_sServceFlag] = oHelper;
		}
		return this.helpers[_sServceFlag];
		*/
	},
	'new' : function(_sId,_oPageParams){
		this.edit(0,_oPageParams);
	},
	quicknew : function(_sId,_oPageParams){
		var oParams = {
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		if(oParams.ChannelId==0){
			//站点智能创建文档,分成两步,1,选择栏目;2,在相应栏目上新建
			FloatPanel.open('./document/document_sitequicknew_step1.html?' + $toQueryStr(oParams), '选择要智能创建文档的栏目', 400, 350);
			return;
		}
		FloatPanel.open('./document/document_quicknew.html?' + $toQueryStr(oParams), '文档-智能创建Office文档', 360, 100);
	},
	importoffice : function(_sId,_oPageParams){
		var oParams = {
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		if(oParams.ChannelId==0){
			//站点智能创建文档,分成两步,1,选择栏目;2,在相应栏目上新建
			FloatPanel.open('./document/document_siteimportoffice_step1.html?' + $toQueryStr(oParams), '选择要批量导入Office文档的栏目', 400, 350);
			return;
		}
		FloatPanel.open('./document/document_importoffice.html?' + $toQueryStr(oParams), '文档-批量导入Office文档', 500, 260);
	},
    "docpositionset" : function(_sDocId,_oPageParams){
		var oParams = {
			DocumentId : _oPageParams.docids || _sDocId,
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		FloatPanel.open('./document/document_position_set.jsp?' + $toQueryStr(oParams), '文档-调整顺序', 400, 120);
	},
	"import" : function(_sId,_oPageParams){
		var oParams = {
			DocumentId : _sId,
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		if(oParams.ChannelId==0){
			//站点智能创建文档,分成两步,1,选择栏目;2,在相应栏目上新建
			FloatPanel.open('./document/document_siteimport_step1.html?' + $toQueryStr(oParams), '选择要文档导入的目标栏目', 400, 350);
			return;
		}
		FloatPanel.open('./document/document_import.jsp?' + $toQueryStr(oParams), '文档-导入文档', 500, 300);
	},
	view : function(_sDocId,_oPageParams){
		var oParams = {
			DocumentId : _sDocId,
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		$openMaxWin('../document/document_detail_show.html?' + $toQueryStr(oParams));
	},
	edit : function(_sDocId,_oPageParams){
		var oParams = {
			DocumentId : _sDocId,
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0,
			FromEditor : 1
		}
		if(oParams.DocumentId=='0'&&oParams.ChannelId==0){
			//站点新建文档,分成两步,1,选择栏目;2,在相应栏目上新建
			FloatPanel.open('./document/document_siteadd_step1.html?' + $toQueryStr(oParams), '选择要新建文档的栏目', 400, 350);
			return;
		}
		var iWidth = window.screen.availWidth - 12;
		var iHeight = window.screen.availHeight - 30;
		var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
		setTimeout(function(){
			window.open("/wcm/WCMV6/document/document_addedit.jsp?" + $toQueryStr(oParams), "_blank" , sFeature);
		}, 10);
	},
	copy : function(_sDocIds,_oPageParams){
		var oParams = {
			DocumentIds : _sDocIds,
			FromChannelId : _oPageParams["channelid"] || 0,
			FromSiteId : _oPageParams["siteid"] || 0
		}
		FloatPanel.open('./document/document_copyto.html?' + $toQueryStr(oParams), '文档-文档复制到...', 400, 350);
	},
	"move" : function(_sDocIds,_oPageParams){
		var oParams = {
			DocumentIds : _sDocIds,
			FromChannelId : _oPageParams["channelid"] || 0,
			FromSiteId : _oPageParams["siteid"] || 0
		}
		FloatPanel.open('./document/document_moveto.html?' + $toQueryStr(oParams), '文档-文档移动到...', 400, 350);
	},
	"quoteTo" : function(_sDocIds,_nTargetChannelId){
		$beginSimplePB('正在引用文档..', 2);
		var oPostData = {
			"ObjectIds" : _sDocIds,
			"ToChannelIds" : _nTargetChannelId
		}
		this.getHelper().call('wcm6_viewdocument', 'quote', oPostData, true, function(transport,_json){
			$endSimplePB();
			//通知其他页面刷新
			ReportsDialog.show(_json,'文档引用结果',function(){
				return;
/*
				$nav().doAfterMove(_nTargetChannelId);
				window.setTimeout(function(){
					$nav().refreshMain();
				}, 500);
//*/
			});
		});
	},
	quote : function(_sDocIds,_oPageParams){
		var oParams = {
			DocumentIds : _sDocIds,
			FromChannelId : _oPageParams["channelid"] || 0,
			FromSiteId : _oPageParams["siteid"] || 0
		}
		FloatPanel.open('./document/document_quoteto.html?' + $toQueryStr(oParams), '文档-文档引用到...', 400, 450);
	},
	changestatus : function(_sDocIds,_oPageParams){
		var oParams = {
			DocumentIds : _sDocIds,
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		FloatPanel.open('./document/change_status.jsp?' + $toQueryStr(oParams), '文档-改变状态', 400, 80);
	},
	setright : function(_sDocIds,_oPageParams){
		/*alert('setright'+_sDocIds);*/
		window.open("../auth/right_set.jsp?ObjType=" + this.ObjectType + "&ObjId=" + _sDocIds, "document_right_set", 'height=600,width=900,resizable=yes');
	},
	"export" : function(_sDocIds,_oPageParams){
		var oParams = {
			DocumentIds : _sDocIds,
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		FloatPanel.open('./document/document_export.jsp?' + $toQueryStr(oParams), '文档-导出文档', 300, 100);
	},
	exportAll : function(_sDocIds, _oPageParams){
		var oParams = {
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		alert("导出所有文档:" + $toQueryStr(oParams));
		//FloatPanel.open('./document/document_export.jsp?' + $toQueryStr(oParams), '文档-导出全部文档', 300, 100);
	},
	preview : function(_sDocIds,_oPageParams){
		var oParams = {
			FolderId : _oPageParams["channelid"] || _oPageParams["siteid"] || 0,
			FolderType : (_oPageParams["channelid"])?101:103
		};
		$publishMgr.preview(_sDocIds,this.ObjectType,oParams);
	},
	trash : function(_sDocIds,_oPageParams){
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '篇文档放入废稿箱吗? ')){
			var mpb = new (top.actualTop||top).ModalProcessBar('放入废稿箱');
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
		}
	},
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
	publish : function(_sDocIds, _sMethodName,_oExtraParams){
		$publishMgr.publish(_sDocIds,this.ObjectType,_sMethodName,_oExtraParams);
	},
	basicpublish : function(_sDocIds,_oPageParams){
		this.publish(_sDocIds, 'basicPublish');
	},
	detailpublish : function(_sDocIds,_oPageParams){
		this.publish(_sDocIds, 'detailPublish');
	},
	recallpublish : function(_sDocIds,_oPageParams){
		var oExtraParams = {
			FolderId : _oPageParams["channelid"] || _oPageParams["siteid"] || 0,
			FolderType : (_oPageParams["channelid"])?101:103
		};
		this.publish(_sDocIds, 'recallDocuments',oExtraParams);
	},
	backup : function(_sDocIds,_oPageParams){
		//TODO根据返回值重新调整一下提示信息
		this.getHelper().call(this.serviceId,'backup',Object.extend(_oPageParams,{"DocIds": _sDocIds}),true,
			function(_transport,_json){
				/*
				if(_sDocIds.indexOf(',')==-1){
					try{
						var iVersion = _json["RESULT"]["NODEVALUE"]+1;
					}catch(err){}
					$timeAlert('[文档-'+_sDocIds+'(版本'+iVersion+')]保存成功.',6);
				}
				else{
					$timeAlert('多篇文档('+_sDocIds+')的新版本成功保存.',6);
				}
				*/
				ReportsDialog.show(_json,'文档版本保存结果');
			}
		);
	},
	backupmgr : function(_sDocIds,_oPageParams){
		window.open("../document/document_backupmgr.jsp?DocumentId=" + _sDocIds, "document_backupmgr", 'height=500;width=800');
	},
	saveorder : function(_iDocId,_iPosition,_iTargetDocId,_iChannelId){
		this.getHelper().call(this.serviceId,'changeOrder',
			{"FromDocId":_iDocId,"ToDocId":_iTargetDocId,"position":_iPosition,"channelid":_iChannelId}, false, 
			function(){
				//$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
			}
		);
	}
};
