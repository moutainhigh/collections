$import('com.trs.dialog.Dialog');
$import('wcm52.CTRSAction');
$import('wcm52.CTRSHashtable');
$import('wcm52.CTRSRequestParam');
var PhotoMgr = {
	serviceId : 'wcm6_photo',	

	add : function(_sObjectIds, _parameters){
		FloatPanel.open("./photo/photodoc_edit.html?DocIds=0","编辑图片信息",680,350);
	},

	edit : function(_sObjectIds, _parameters){		
		//Object.extend(_parameters,{DocId:_sObjectIds});		
		FloatPanel.open("./photo/photodoc_edit.html?"+$toQueryStr(_parameters),"编辑图片信息",680,350);
	},
	
	upload: function(_sObjectIds, _parameters) {		
		FloatPanel.open("./photo/photo_upload.html?" + $toQueryStr(_parameters), '上传图片', 680, 350);
	},
/*
	trash : function(_sDocIds,_oPageParams){		
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '个图片放入回收站吗? ')){
			//$documentMgr['trash'](_sDocIds,_oPageParams);
			BasicDataHelper.call("wcm6_viewdocument",'delete', Object.extend(_oPageParams,{"ObjectIds": _sDocIds, "drop": false}), false, 
				function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				}
			);
		}
	},	

	"delete" : function(_sDocIds,_oPageParams){
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '篇图片删除吗? ')){
			BasicDataHelper.call("wcm6_viewdocument",'delete', Object.extend(_oPageParams,{"ObjectIds": _sDocIds, "drop": true}), false, 
				function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				}
			);
		}
	},
*/
	//code from chnldocMgr.
	"delete" : function(_sDocIds,_oPageParams){
		_sDocIds = _sDocIds + '';
		var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		var params = {
			objectids: _sDocIds,
			operation: '_trash'
		}
		this.doOptionsAfterDisplayInfo(params, function(){
			var aTop = (top.actualTop||top);
			var mpb = new aTop.ModalProcessBar('删除图片');
			BasicDataHelper.call("wcm6_viewdocument", 'delete', Object.extend(_oPageParams,{"ObjectIds": _sDocIds, "drop": true}), true, 
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
	doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
		var DIALOG_PHOTO_INFO = 'photo_info_dialog';
		var aTop = (top.actualTop||top);
		TRSCrashBoard.setMaskable(true);
		aTop.m_eDocumentInfo = TRSDialogContainer.register(DIALOG_PHOTO_INFO, '系统提示信息'
		, './photo/photo_info.html', '500px', '280px', true, true, true);
		aTop.m_eDocumentInfo.onFinished = _fDoAfterDisp;
		
		TRSDialogContainer.display(DIALOG_PHOTO_INFO, _params);	
	},
	importsysphoto : function(_sDocIds,_oPageParams){
		/*
		var nWidth	= window.screen.availWidth;
		var nHeight	= window.screen.availHeight;
		TRSAction_ROOT_PATH = "../../";		
		var oTRSAction = new CTRSAction("../../photo/photo_import.jsp");
		oTRSAction.setParameter("ChannelId", _oPageParams['channelid']||0);
		var sResult = oTRSAction.doDialogAction(nWidth*0.8, nHeight*0.8);
		if(sResult){
			$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
		}*/
		var winUrl = "../photo/photo_import_syspics.html?";
		var nChnlId = _oPageParams['channelid']||0;
		var nSiteId = _oPageParams['siteid']||0;
		if(nChnlId > 0){
			winUrl += "ChannelId=" + nChnlId;
		}else{
			winUrl += "SiteId=" + nSiteId;
		}
		$openMaxWin(winUrl);
	},
	quote : function(_sDocIds,_oPageParams){		
		var currId = 0;
		if(_oPageParams){
			currId = _oPageParams["channelid"] || 0;
		}
		m_nCurrId = currId;
		$channelSelector.setParams({ObjectIds:_sDocIds,channelids:currId});
		$channelSelector.selectOtherKinds(currId);		
	},
	move : function(_sDocIds,_oPageParams){		
		var currId = 0;
		if(_oPageParams){
			currId = _oPageParams["channelid"] || 0;			
		}
		$channelSelector.setParams({ObjectIds:_sDocIds,FromChannelId:currId});
		m_nCurrId = currId;
		$channelSelector.selectMainKind(currId);
	},
	changestatus : function(_sDocIds,_oPageParams){
		var oPostData = {
			'ChannelIds' : _oPageParams['channelid'] || 0,
			'ObjectIds' : _sDocIds,
			'IsPhoto' : "true"
		}
		FloatPanel.open('./document/change_status.jsp?' + $toQueryStr(oPostData), '图片-改变状态', 400, 80);
	},
	docpositionset : function(_sDocId,_oPageParams){
		var oParams = {
			ChnlDocId : _oPageParams.docids || _sDocId,
			ChannelId : _oPageParams["channelid"] || 0,
			SiteId : _oPageParams["siteid"] || 0
		}
		FloatPanel.open('./photo/photo_position_set.jsp?' + $toQueryStr(oParams), '文档-调整顺序', 400, 120);
	}	
};

var m_nCurrId = 0;

var DIALOG_IMAGEKIND_SELECTOR = "Dialog_ImageKind_Selector";
ChannelSelector = Class.create("ChannelSelector");

ChannelSelector.prototype = {
	m_oParams : {},
	initialize : function(){		
		//TRSCrashBoard.setMaskable(true);
	},
	registerOnFinish : function(){
		var self = this;
		var mytop = top || top.actualTop;
		mytop.m_eSelector = TRSDialogContainer.register(DIALOG_IMAGEKIND_SELECTOR, '选择图片分类', './photo/channel_select.html', '250px', '300px', false);		
		mytop.m_eSelector.onFinished = function(_args){			
			if(_args.mode == "radio"){						
				if(_args.ids && m_nCurrId != _args.ids){
					var oPostData = Object.extend(self.m_oParams,{ToChannelId:_args.ids});					
					BasicDataHelper.call("wcm6_viewdocument","move",oPostData,true,function(_transport,_json){
						var r = $v(_json,"Reports.Is_Success");
						if(r == "false"){
							var reports = $a(_json,"Reports.Report");
							var sHtml = "";//"<span style='color:red;text-align:center'>重新分类图片失败！</span><br />";
							var report = null;
							var title = null;
							for(var i=0,len=reports.length;i<len;i++){
								report = reports[i];
								r = $v(report,"Is_Success");
								if(r == "false"){
									title = $v(report,"Title");										
									var ix = title.indexOf("[")+1;
									var ixx = title.indexOf("]");									
									var newTitle = "重新分类图片[";
									newTitle += title.substring(ix,ixx);
									newTitle += "]到分类[";
									title = title.substring(ixx+1);
									ix = title.indexOf("[")+1;
									ixx = title.indexOf("]");									
									newTitle += title.substring(ix,ixx);
									newTitle +="]失败！<span style='color:red'>";
									newTitle += title.substring(ixx+4).replace(/文档/gi,"图片")+"</span>";
									sHtml += (i+1)+"."+ newTitle + "<br />";
								}
							}
							$alert(sHtml);
						}else{
							$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
						}
					});
				}				
			}else{									
				if(_args.ids && _args.ids.length > 0 && m_nCurrId != _args.ids){
					var oPostData = Object.extend(self.m_oParams,{ToChannelIds:_args.ids});					
					BasicDataHelper.call("wcm6_viewdocument","quote",oPostData,true,function(_transport,_json){				
						//alert("将图片引用到其它分类成功！");
						$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
					});
				}
			}
		}
	},
	selectMainKind : function(_currId){		
		this.registerOnFinish();
		var id = _currId || 0;
		var pContext = {mode:"radio",SelectedIds:id,CurrId:id};
		var dialog = TRSDialogContainer.DialogsMap[DIALOG_IMAGEKIND_SELECTOR];
		dialog.refreshTitle("选择主分类");
		var evt = Event.findEvent();
		if(evt){//跨页面event可能捕捉不到
			var positions = Position.getAbsolutePositionInTop(Event.element(evt));		
			TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, pContext, positions[0]-200,positions[1]);		
		}else{
			TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, pContext);
		}		
	},
	selectOtherKinds : function(_currId){
		this.registerOnFinish();
		var dialog = TRSDialogContainer.DialogsMap[DIALOG_IMAGEKIND_SELECTOR];		
		dialog.refreshTitle("选择图片分类");
		var pContext = {mode:"multi",SelectedIds:_currId,CurrId:_currId};
		var evt = Event.findEvent();
		if(evt){//跨页面event可能捕捉不到
			var positions = Position.getAbsolutePositionInTop(Event.element(evt));		
			TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, pContext, positions[0]-200,positions[1]);		
		}else{
			TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, pContext);
		}		
	},
	setParams : function(_params){
		Object.extend(this.m_oParams,_params ||{});
	}
};

$channelSelector = new ChannelSelector();