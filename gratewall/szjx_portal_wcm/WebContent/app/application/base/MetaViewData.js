Ext.ns('wcm.XMetaViewData');
wcm.XMetaViewData = function(){
	this.addEvents('render', 'beforesave', 'save');
};

Ext.extend(wcm.XMetaViewData, wcm.util.Observable, {
	getChannelId : function(){
		return m_nChannelId || 0;
	},
	getObjectId : function(){
		return m_nDocumentId || 0;
	},
	getFlowDocId : function(){
		return m_nFlowDocId || 0;
	},
	getViewId : function(){
		return m_nViewId || 0;
	},
	render : function(){
		this.fireEvent('render', this);
	},
	save : function(){
		ProcessBar.start(wcm.LANG.MetaViewdata_1000 || "保存记录信息");
		if(this.fireEvent('beforesave') === false){
			ProcessBar.exit();
			return;
		}
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		this.fireEvent('save', aCombine, oHelper);
		oHelper.JspMultiCall('../../metaviewdata/metaviewdata_addedit_dowith.jsp', aCombine, function(){
			ProcessBar.exit();
			var cbr = wcm.CrashBoarder.get(window);
			cbr.notify();
			cbr.close();
		});		
	}
});

//define the global object: oXMetaViewData. 
var oXMetaViewData = new wcm.XMetaViewData();
//Event.observe(window, 'load', oXMetaViewData.render.bind(oXMetaViewData));

/**
*注册页面中的元数据字段
*/
oXMetaViewData.addListener('render', function(){
	var box = $('objectForm');
	var tagNames = ['input', 'select', 'textarea'];
	for (var i = 0; i < tagNames.length; i++){
		var doms = box.getElementsByTagName(tagNames[i]);
		for (var j = 0; j < doms.length; j++){
			var objType = doms[j].getAttribute("objType");
			if(objType == null) continue;
			wcm.XFieldMgrFactory.getFieldMgr(objType).register(doms[j].name);
		}
	}
});

/**
*对所有已经加载了的视图字段mgr的初始化
*/
oXMetaViewData.addListener('render', function(){
	var mgrs = wcm.XFieldMgrFactory.getLoadedMgrs();
	for (var i = 0; i < mgrs.length; i++){
		try{
			mgrs[i].beforeRender();
		}catch(error){
			alert(error.message);
		}
		try{
			mgrs[i].render();
		}catch(error){
			alert(error.message);
		}
	}
});


/**
*"附件管理"处理器
*/
wcm.XAppendixsHandler = function(){
	var loaded = false;
	var oAppendixs;
	return {
		isLoaded : function(){
			return loaded;
		},
		loadAppendixs : function(){
			loaded = true;
			try{
				oAppendixs = {
					Type_10:
						Ext.Json.parseXml(
							Ext.Xml.loadXML($('appendix_10').value)),
					Type_20:
						Ext.Json.parseXml(
							Ext.Xml.loadXML($('appendix_20').value)),
					Type_40:
						Ext.Json.parseXml(
							Ext.Xml.loadXML($('appendix_40').value))
				}
			}catch(err){
				oAppendixs = {
					Type_10:{},
					Type_20:{},
					Type_40:{}
				}
			}			
		},
		getAppendixs : function(){
			if(!loaded) this.loadAppendixs();
			return oAppendixs;
		},
		setAppendixs : function(appendixs){
			loaded = true;
			oAppendixs = Object.deepClone(appendixs);				
		},
		getAppendix : function(type){
			if(!loaded) this.loadAppendixs();
			return oAppendixs['Type_' + type];
		},
		/**
		 *  <OBJECTS>
		 *		<OBJECT ID="" APPFILE="" SRCFILE="" APPLINKALT="" APPFLAG="" APPDESC=""/>
		 *	</OBJECTS>
		 */
		getAppendixesXML : function(type){
			var appendixs = this.getAppendix(type);
			var arr = Ext.Json.array(appendixs,"APPENDIXES.APPENDIX")||[];
			var sParams = ["APPENDIXID","APPFILE","SRCFILE","APPLINKALT","APPFLAG","APPDESC"];
			var myValue = Ext.Json.value;
			var sRetVal = '<OBJECTS>';
			for(var i=0;i<arr.length;i++){
				var oAppendix = arr[i];
				sRetVal += '<OBJECT';
				for(var j=0;j<sParams.length;j++){
					var sName = sParams[j];
					var sValue = myValue(oAppendix,sName)||'';
					if(sName=='APPENDIXID'){
						if(isNaN(sValue)) sValue = 0;
					}
					if(type==20&&sName=='APPFILE'){
						sRetVal += ' APPFILE="'+((myValue(oAppendix,'APPFILE.FILENAME')||'')+'').escape4Xml()+'"';
					}
					else if(sName=='APPENDIXID'){
						sRetVal += ' ID="'+sValue+'"';
					}
					else{
						sRetVal += ' '+sName+'="'+(sValue+'').escape4Xml()+'"';
					}
				}
				sRetVal += '/>';
			}
			sRetVal += '</OBJECTS>';
			return sRetVal;
		},
		/**
		*单击附件管理按钮时做的处理
		*/
		handleAppendixs : function(){
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'document/document_attachments.html',
				title : wcm.LANG.METAVIEWDATA_95 || '附件管理',		
				callback : function(info){
					wcm.XAppendixsHandler.setAppendixs(info);
				},
				dialogArguments : wcm.XAppendixsHandler.getAppendixs()
			});
		}
	};
}();

/**
*注册单击"附件管理"的处理
*/
wcm.ClickEventHandler.register('handleAppendixs', wcm.XAppendixsHandler.handleAppendixs);

/**
*保存附件信息
*/
oXMetaViewData.addListener('save', function(aCombine, oHelper){
	ProcessBar.next(wcm.LANG.MetaViewdata_1001 || "构造需要保存的附件信息");
	var nDocId = oXMetaViewData.getObjectId();
	var nFlowDocId = oXMetaViewData.getFlowDocId();
	var appendixTypes = [10, 20, 40];
	for (var i = 0; i < appendixTypes.length; i++){
		aCombine.push(oHelper.Combine('wcm6_document', 'saveAppendixes', {
			"DocId" : nDocId,
			"FlowDocId" : nFlowDocId,
			"AppendixType" : appendixTypes[i],
			"AppendixesXML" : wcm.XAppendixsHandler.getAppendixesXML(appendixTypes[i])
		}));
	}
});


/**
*注册单击"模板选择"的处理
*/
wcm.ClickEventHandler.register('selectTemplate', function(){
	var nChnlId = oXMetaViewData.getChannelId();
	var params = {channelId:nChnlId,templateType:2,selectType:"radio",templateIds:$("spDetailTemp").getAttribute('tempIds')||0};
	wcm.TemplatSelector.selectTemplate(params, function(_args){
		var sName = _args.selectedNames[0] || wcm.LANG.MetaViewdata_1002 || "无";
		var sId = _args.selectedIds[0] || 0;
		Element.update("spDetailTemp", sName);
		$("spDetailTemp").setAttribute('tempIds', sId);
	});
});

/**
*保存细览模板信息
*/
oXMetaViewData.addListener('save', function(aCombine, oHelper){
	ProcessBar.next(wcm.LANG.MetaViewdata_1003 || "构造需要保存的模板信息");
	aCombine.push(oHelper.Combine('wcm6_publish','saveDocumentPublishConfig', {
		"ObjectId" : oXMetaViewData.getObjectId(),
		"DetailTemplate" : $("spDetailTemp").getAttribute('tempIds') || 0
	}));
});

/**
*初始化置顶信息
*/
oXMetaViewData.addListener('render', function(){
	PgC.init();
});


/**
*保存置顶信息
*/
oXMetaViewData.addListener('save', function(aCombine, oHelper){
	ProcessBar.next(wcm.LANG.MetaViewdata_1004 || "构造需要保存的置顶信息");
	var info = PgC.getTopsetInfo();
	if(info.TopFlag == 2){
		info.TopFlag = 3;
	}
	var postdata = {
		"TopFlag" : info.TopFlag,
		"ChannelId" : oXMetaViewData.getChannelId(),
		"Position" : info.Position,
		"DocumentId" : oXMetaViewData.getObjectId(),
		"TargetDocumentId" : info.TargetDocumentId,
		"InvalidTime" : (PgC.TopFlag==1)?$('TopInvalidTime').value:'',
		FlowDocId : oXMetaViewData.getFlowDocId()
	};
	aCombine.push(oHelper.Combine('wcm6_document','setTopDocument', postdata));
});

/**
*页面按钮信息
*/
window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG.METAVIEWDATA_100 || '保存',
			cmd : function(){
				oXMetaViewData.save();
				return false;
			},
			id : "btnSave"
		},
		{text : wcm.LANG.METAVIEWDATA_101 || '关闭'}
	]
};

function autoJusty(){
	var cbSelf = wcm.CrashBoarder.get(window).getCrashBoard();
	if(!cbSelf) return;
	try{
		var box = Ext.isStrict?document.documentElement:document.body;
		var minWidth = 700, minHeight = 250, maxWidth = 900, maxHeight = (window.screen.width>1024)?600:450;
		var realWidth = box.scrollWidth;		
		var realHeight = box.scrollHeight;
		realWidth = realWidth > maxWidth ? maxWidth : (realWidth < minWidth ? minWidth : realWidth);
		realHeight = realHeight > maxHeight ? maxHeight : (realHeight < minHeight ? minHeight : realHeight);		
	}catch(e){
		Ext.Msg.alert(e.message)
	}
	cbSelf.setSize(realWidth+"px",realHeight+"px");		
	box.style.overflowY = 'auto';
	box.style.overflowX = 'hidden';	
	if(Ext.isGecko){//如果IE设上这个,会出现两个滚动条
		document.body.style.overflowY = 'auto';
		document.body.style.overflowX = 'hidden';
	}
	cbSelf.center();
}


function init(){
	autoJusty();
	oXMetaViewData.render();
}

//初始化校验
oXMetaViewData.addListener('render', function(aCombine, oHelper){
	ValidationHelper.addValidListener(function(){
		wcmXCom.get('btnSave').enable();		
	});
	ValidationHelper.addInvalidListener(function(){
		wcmXCom.get('btnSave').disable();
	});
	ValidationHelper.initValidation();
});

//校验合法性
oXMetaViewData.addListener('beforesave', function(aCombine, oHelper){
	ProcessBar.next(wcm.LANG.MetaViewdata_1005 || "执行信息的校验");
	var mgrs = wcm.XFieldMgrFactory.getLoadedMgrs();
	for (var i = 0; i < mgrs.length; i++){
		try{
			if(!mgrs[i].valid()) return false;
		}catch(error){
			alert(error.message);
		}
	}
});

//上传附件信息
oXMetaViewData.addListener('beforesave', function(aCombine, oHelper){
	var mgr = wcm.XFieldMgrFactory.findFieldMgr(wcm.XFieldConstants.TYPE_APPENDIX);
	if(!mgr || mgr.isUploadAll()) return true;
	setTimeout(function(){
		ProcessBar.start(wcm.LANG.MetaViewdata_1006 ||"对附件字段进行上传");
	}, 0);
	mgr.upload(function(){//for asynchronize request.
		oXMetaViewData.save();
	});
	return false;
});

//保存对象自身属性
oXMetaViewData.addListener('save', function(aCombine, oHelper){
	ProcessBar.next(wcm.LANG.MetaViewdata_1007 ||"构造对象自身的保存信息");
	var postData = {
		objectId : this.getObjectId(),
		flowDocId : this.getFlowDocId(),
		channelId : this.getChannelId()
	};
	var mgrs = wcm.XFieldMgrFactory.getLoadedMgrs();
	for (var i = 0; i < mgrs.length; i++){
		try{
			Ext.apply(postData, mgrs[i].getFieldsData());
		}catch(error){
			alert(error.message);
		}
	}
	aCombine.push(oHelper.Combine("wcm61_metaviewdata", 'saveMetaViewData', postData));		
});

//注册加锁处理
oXMetaViewData.addListener('render', function(){
	LockerUtil.register(oXMetaViewData.getObjectId(), 1936280531, true, {
		failToLock : function(_msg, _json){
			wcmXCom.get('btnSave').disable();
			Ext.Msg.$timeAlert(String.format('<b>提示：</b>{0}', _msg)5);
		}
	}, true);
});