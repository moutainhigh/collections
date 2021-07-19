$package('com.trs.wcm.domain');

//$import('com.trs.wcm.datasource.channel.Channels');

$import('com.trs.dialog.Dialog');
$import('com.trs.wcm.domain.PublishMgr');
$import('com.trs.wcm.domain.TemplateMgr');

com.trs.wcm.domain.ChannelMgr=Class.create('wcm.domain.channel.ChannelMgr');
com.trs.wcm.domain.ChannelMgr.prototype = {
	initialize: function(){
		this.objType = 101;
		this.serviceId = 'wcm6_channel';
		this.helpers = {};
	},
	getInstance : function(){
		if($channelMgr == null) {
			$channelMgr = new com.trs.wcm.domain.ChannelMgr();
		}
		return $channelMgr;
	},
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
/*
		_sServceFlag = (!_sServceFlag)? 'basic' : _sServceFlag.toLowerCase();
		if(!this.helpers[_sServceFlag]) {
			var oHelper = null;
			switch(_sServceFlag){
				case 'basic' :
					oHelper = BasicDataHelper;
					break;
				case 'channel' :
					oHelper = $dataHelper(com.trs.wcm.datasource.channel.Channels);
					break;
				default :
					alert('ChannelMgr不支持"'+_sServceFlag+'"数据源');
					break;
			}
			this.helpers[_sServceFlag] = oHelper;
		}
		return this.helpers[_sServceFlag];
*/
	},
	/********************************************************
	* 定义业务逻辑
	*********************************************************
	*/
	'new' : function(){
		this.edit(0);
	},
	edit : function(_sId, _bReadOnly){
		var oParams = {
			channelid:	_sId,
			parentid:	PageContext.params.channelid || 0,
			siteid:		PageContext.params.siteid || 0
		};
		if(_bReadOnly === true) {
			oParams.readonly = true;
		}
		FloatPanel.open('./channel/channel_add_edit.html?' + $toQueryStr(oParams), '新建/修改栏目', 550, 550);
	},
	newFromMenu : function(_sId, _oPageParams){
		this.editFromMenu(_sId, _oPageParams);
	},
	editFromMenu : function(_sId, _oPageParams){
		var oParams = {
			channelid:	_sId,
			parentid:	_oPageParams.channelid || 0,
			siteid:		_oPageParams.siteid || 0,
			readonly:	(_oPageParams.readonly  === true ? true : false)
		};
		FloatPanel.open('./channel/channel_add_edit.html?' + $toQueryStr(oParams), '新建/修改栏目', 550, 550);
	},
	view : function(_sId){
		this.edit(_sId, true);
	},
	preview : function(_sIds){
		$publishMgr.preview(_sIds, this.objType);
	},
	publish : function(_sIds, _sServiceId){
		$publishMgr.publish(_sIds, this.objType, _sServiceId);
	},
	increasingpublish : function(_sIds){
		this.publish(_sIds, 'increasingPublish');
	},
	fullypublish : function(_sIds){
		this.publish(_sIds, 'fullyPublish');
	},
	refreshpublish : function(_sIds){
		this.publish(_sIds, 'refreshPublish');
	},
	solopublish : function(_sIds){
		this.publish(_sIds, 'soloPublish');
	},
	recallpublish : function(_sIds){
		this.publish(_sIds, 'recallPublish');
	},
	trash : function(_sIds){
		_sIds = _sIds + '';
		var nCount = (_sIds.indexOf(',') == -1) ? 1 : _sIds.split(',').length;
		if (confirm('确实要将' + (nCount == 1 ? '此' : '这 ' + nCount +' 个') + '栏目放入回收站吗? ')){
			$beginSimplePB('正在将栏目放入回收站..', 2);
			this.getHelper().call(this.serviceId, 'delete', {ObjectIds: _sIds, drop: false}, true, function(){
				$endSimplePB();
				$nav().doAfterDelChannel(_sIds);
				window.setTimeout(function(){
					//TODO
					var eMain = $MessageCenter.getMain();
					if(eMain && eMain.Channels) {
						var hostId = eMain.PageContext.params.channelid;
						if(hostId == _sIds) { // 删除自身
							$nav().refreshMain();
						}else{
							$MessageCenter.sendMessage('main', 'Channels.updateChannels', 'Channels', true);
						}
					}else{
						try{
							$nav().refreshMain();
						}catch(err){
							//TODO logger
							//alert(err.message);
						}
					}
				}, 500);
			});
		}
	},
	trace : function(_sChannelId, _bIsSite, _sRights, _channelType){
		var nSiteType = 0;
		try{
			nSiteType = $nav().findFocusNodeSiteType() || 0;
		}catch(error){
			nSiteType = 0;
		}
		var oParams = {
			SiteType : nSiteType,
			RightValue : _sRights || '0',
			ChannelType : _channelType || '0'
		};
		if(_bIsSite) {
			oParams.siteid = _sChannelId;
		}else{
			oParams.channelid = _sChannelId;
		}
		(top.actualTop||top).location_search = '?' + $toQueryStr(oParams);
		document.location.href = '../channel/channel_thumbs_index.html?' + $toQueryStr(oParams);
		// ge gfc add @ 2007-4-3 17:15 加入页面切换的过度页面
		if((top.actualTop || top).RotatingBar) {
			(top.actualTop || top).RotatingBar.start();
		}
	},
	rename : function(_oPostData){
		this.getHelper().call(this.serviceId, 'save', _oPostData, true, function(){
			if(Channels.getSelectedIds().length == 1) {
				//TODO
				$MessageCenter.sendMessage('oper_attr_panel', 'updateDesc', null, _oPostData.ChnlDesc);
				window.setTimeout(function(){
					$nav().doAfterModifyChannel(_oPostData.ObjectId, true);
				}, 500);				
			}
		});
	},
	queryChannels : function(){
		$MessageCenter.sendMessage('main', 'Channels.updateChannels', 'Channels', true);
		return false;
	},
	'import' : function(objectId, oParams){
		if(oParams == null) {
			return;
		}
		//else  直接传入[siteid]或者[channelid]，例如从菜单或者右键菜单中来
		var params = null;
		if(oParams['siteid'] || oParams['channelid']) {
			if(oParams['channelid']) { // 栏目中导入
				params = {
					parentid: oParams['channelid'],
					objecttype: 101
				};
			}else{ // 站点中导入
				params = {
					parentid: oParams['siteid'],
					objecttype: 103
					
				};
			}
		}
		//从操作面板中来
		else{
			var nSiteId = PageContext.siteid;
			if(isNaN(nSiteId) || nSiteId == 0) { // 栏目中导入
				params = {
					parentid:	objectId,
					objecttype: 101
				};
			}else{ // 站点中导入
				params = {
					parentid:	nSiteId,
					objecttype: 103
				};		
			}
		}

		FloatPanel.open('./channel/channel_import.html?' + $toQueryStr(params), '栏目导入', 400, 100);
	},
	afterImport : function(_args){
		if(String.isString(_args)) {
			_args = eval(_args);
		}
		if(_args.folderType == '103') {
			$nav().refreshSite(_args.folderId);
		}else{
			$nav().refreshChannel(_args.folderId);
		}
		if($main() && $main().Channels) {
			window.setTimeout(function(){
				$MessageCenter.sendMessage('main', 'Channels.updateChannels', 'Channels', true);	
			}, 10);
		}
	},
	'export' : function(_sIds, _oHostInfos){
		// ge gfc modify @ 2006-12-20 8:52 兼容从非栏目视图时执行导出操作 
		//var hostPanelType = _oHostInfos.hostPanelType;
		var hostPanelType = (_oHostInfos && _oHostInfos.hostPanelType) ? _oHostInfos.hostPanelType : 'channel';
		
		var oPostData = {};
		if(hostPanelType == 'websiteHost') {//导出站点下所有子栏目
			oPostData.parentSiteId = _oHostInfos.hostObjectId;
		}
		else if(hostPanelType == 'channelHost') { // 导出栏目下所有子栏目
			oPostData.parentChannelId = _oHostInfos.hostObjectId;
		}else if(hostPanelType == 'channelMaster') { // 导出当前栏目
			oPostData.ObjectIds = _oHostInfos.hostObjectId;
		}else if(hostPanelType == 'channel' || hostPanelType == 'channels') { // 导出选中的栏目
			oPostData.ObjectIds = _sIds;
		}
		FloatPanel.open('./channel/channel_export.html?' + $toQueryStr(oPostData)
			, '栏目导出', 300, 80);
	},
	move : function(_sSrcId, _params){
		var sUrl = './channel/channel_select_move.html?srcId=' + _sSrcId;
		if(_params && _params.hostObjectType) {
			var sFolderInfo = 'folderType=' + (_params.hostObjectType == 'website'? 103 : 101);
			sFolderInfo += '&folderId=' + _params.hostObjectId;
			sUrl += '&' + sFolderInfo;
		}
		
		FloatPanel.open(sUrl, '栏目移动', 300, 370);
	},
	renderMove : function(_args){
		//alert($toQueryStr(_args));
		this.moveAsChild(_args.srcId, _args.dstId, (_args.dstType == 103));
	},
	moveAsChild : function(_nSrcChnlIds, _nTargetId, _bIsSite){
		var oPostData = {};
		oPostData.srcChannelIds = _nSrcChnlIds;
		if(_bIsSite === true) {
			oPostData.DstSiteId = _nTargetId;
		}else{
			oPostData.DstChannelId = _nTargetId;
		}
		$beginSimplePB('正在移动栏目..', 2);
		this.getHelper().call(this.serviceId, 'moveAsChild', oPostData, true, function(){
			$endSimplePB();
			//通知其他页面刷新
			try{
				$nav().doAfterMove(oPostData.srcChannelIds, _nTargetId, _bIsSite, true, function(){
					$nav().refreshMain()
				});	
			}catch(err){
				//TODO logger
				//alert(err.message);
			}
		});
		
	},
	moveAsChildNoRefresh : function(_nSrcChnlIds, _nTargetId, _bIsSite){
		var oPostData = {};
		oPostData.srcChannelIds = _nSrcChnlIds;
		if(_bIsSite === true) {
			oPostData.DstSiteId = _nTargetId;
		}else{
			oPostData.DstChannelId = _nTargetId;
		}
		$beginSimplePB('正在移动栏目..', 2);
		this.getHelper().call(this.serviceId, 'moveAsChild', oPostData, true, function(){
			$endSimplePB();
			//通知其他页面刷新
			$nav().doAfterMove(oPostData.srcChannelIds, _nTargetId, _bIsSite, true, function(){
				$nav().refreshMain();			
			});
		});
		
	},
	changeOrder : function(_sSrcChnlId, _sPreChnlId, _oCallBacks){
		_oCallBacks = _oCallBacks||{};
		this.getHelper().call(this.serviceId, 'changeOrder', {SrcChannelId:_sSrcChnlId, DstChannelId:_sPreChnlId}, true, function(){
			$nav().refreshFocused();
		},_oCallBacks["onFailure"],_oCallBacks["onFailure"]);	
		
	},
	likecopy : function(_sSrcId){
		FloatPanel.open('./channel/channel_select_likecopy.html?srcId=' + _sSrcId, '类似创建', 300, 370);
	},
	renderLikecopy : function(_args){
		var oPostData = {srcChannelId: _args.srcId};
		if(_args.dstType == 103) {
			oPostData.DstSiteId = _args.dstId;
		}else{
			oPostData.DstChannelId = _args.dstId;
		}
		var nType = _args.dstType, nDstSiteId = oPostData.DstSiteId, nDstChnlId = oPostData.DstChannelId;
		this.getHelper().call(this.serviceId, 'createFrom', oPostData, true, function(_trans, _json){
			var bSucess = $v(_json, 'REPORTS.IS_SUCCESS');
			ReportsDialog.show(_json, '栏目类似创建结果', function(){
				if(bSucess != 'true') {
					return;
				}
				//通知其他页面刷新
				if(nType == 103) {
					$nav().refreshSite(nDstSiteId, null, function(){
						$nav().refreshMain();
					});
				}else{
					$nav().refreshChannel(nDstChnlId, null, function(){
						$nav().refreshMain();
					});
				}	
			});

		});
	},
	synch : function(_sId){
		/*
		if(!confirm('确实要同步模板到子栏目吗？\n可能会覆盖栏目模板...')) {
			return;
		}//*/
		
		//else
		//wenyh@2007-12-04 改为用$confirm提示,突出提示
		var sHtml = "确实要同步模板到子栏目吗？<br><span style='color:red;font:16px;'>注意：<br>该操作会覆盖更改所有子栏目的模板设置！</span>";
		var _self = this;
		var okFunc = function(){
			$dialog().hide();
			$beginSimplePB('正在同步模板到子栏目..', 2);
			$templateMgr.synchTemplates(_sId, _self.objType, function(){
				setTimeout(function(){
					$endSimplePB();
					$timeAlert('成功将当前栏目模板同步到了其子栏目下！', 3, null, null, 2);
				}, 100);
			});
		}

		$confirm(sHtml,okFunc,function (){$dialog().hide()},"操作确认");
	},
	settempargs : function(_sId){
		var sParams = 'channelid=' + _sId;
		FloatPanel.open('./template/template_args_index.html?' + sParams, '栏目模板变量', 500, 450);
	},
	commentmgr : function(_sId, _oPageParams){
		var oParams = {
			ChannelId : _sId
		}
		var sUrl = '../../comment/comment_mgr.jsp?' + $toQueryStr(oParams);
		$openMaxWin(sUrl);
	},
	logomanage : function(_sId, _oPageParams){
		var oParams = {
			HostId : _sId,
			HostType : 101,
			HostDesc : encodeURIComponent('栏目-' + _sId)
		}
		var sUrl = '../logo/logo_list.jsp?' + $toQueryStr(oParams);
		FloatPanel.open(sUrl, '栏目LOGO管理', 700, 450);
	}
}

var $channelMgr = new com.trs.wcm.domain.ChannelMgr();
