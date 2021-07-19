(function(){
var mgr = wcm.special.data.OperMgr;
mgr.addOpers([{
	type:"Doc",
	desc:"编辑文档",
	oprKey:"Doc_edit",
	iconCls:"edit",
	visible:true,
	order:1,
	cmd:function(context){
		var chnldocId = context.property.getAttribute("recId");
		var docId = context.property.getAttribute("objId");
		var channelid = context.property.getAttribute("channelId");
		var oParams = {
			ChnlDocId : chnldocId,
			DocumentId : docId,
			ChannelId :channelid,
			FromEditor : 1
		};
		openEditor(context,oParams);
	}
},{
	type:"Doc",
	desc:"删除文档",
	oprKey:"Doc_delete",
	iconCls:"delete",
	visible:function(){
		var property = this.getArgs().property;
		return true;
	},
	order:2,
	cmd:function(context){
		var objid = context.property.getAttribute("recId");
		var channelid = context.property.getAttribute("channelId");
		var params = {
			ObjectIds: objid,
			ChannelId:channelid,
			Drop:true
		}
		Ext.Msg.confirm('确定要删除此文档吗？', {
			yes : function(){
				BasicDataHelper.call("wcm61_viewdocument",'delete',params,true,function(transport, json){
					var widget = wcm.special.widget.InstanceMgr.find(context.property);
					if(widget) wcm.special.widget.InstanceMgr.refresh(widget);
				})
			},
			no : function(){
			}
		});;
	}
},{
	type:"Doc",
	desc:"查看文档",
	oprKey:"Doc_view",
	iconCls:"view",
	order:3,
	cmd:function(context){
		var chnldocId = context.property.getAttribute("recId");
		var docId = context.property.getAttribute("objId");
		var channelid = context.property.getAttribute("channelId");
		var oParams = {
			DocumentId : docId,
			ChannelId :channelid,
			ChnlDocId : chnldocId,
			FromRecycle : 0
		};
		$openMaxWin(WCMConstants.WCM6_PATH +
				'document/document_show.jsp?' + $toQueryStr(oParams));
	}
},{
	type:"Doc",
	desc:"导出文档",
	oprKey:"Doc_export",
	iconCls:"export",
	order:4,
	cmd:function(context){
		var chnldocId = context.property.getAttribute("recId");
		var channelid = context.property.getAttribute("channelId");
		var oParams = {
			ObjectIds : chnldocId,
			ChannelIds :channelid
		};
		wcm.CrashBoarder.get('doc_export_crash').show({
			title : '文档-导出文档',
			src : 'document/document_export.jsp?' + $toQueryStr(oParams),
			width:'300px',
			height:'150px',
			maskable:true,
			callback : function(params){
			}
		});	
	}
},{
	type:"DocInChnl",
	desc:"新建文档",
	oprKey:"DocInChnl_create",
	iconCls:"create",
	order:1,
	cmd:function(context){
		var channelid = context.property.getAttribute("objid");
		var oParams = {
			DocumentId : 0,
			ChannelId :channelid,
			FromEditor : 1
		};
		openEditor(context,oParams);
	}
},{
	type:"DocInChnl",
	desc:"导入文档",
	oprKey:"DocInChnl_import",
	iconCls:"import",
	order:2,
	cmd:function(context){
		var channelid = context.property.getAttribute("objid");
		var oParams = {
			ChannelId :channelid
		};
		wcm.CrashBoarder.get('doc_import_crash').show({
			title : '文档-导入文档',
			src : 'document/document_import.jsp?' + $toQueryStr(oParams),
			width:'500px',
			height:'200px',
			maskable:true,
			callback : function(params){
				var widget = wcm.special.widget.InstanceMgr.find(context.property);
				if(widget) wcm.special.widget.InstanceMgr.refresh(widget);
			}
		});	
	}
}]);
})();

function afterSaveDocument(){
	var context = afterSaveDocument.lastArgs;
	var widget = wcm.special.widget.InstanceMgr.find(context.property);
	if(widget) wcm.special.widget.InstanceMgr.refresh(widget);
}

function openEditor(context,params){
	afterSaveDocument.lastArgs = context;
	params.callback = 'afterSaveDocument';
	var iWidth = window.screen.availWidth - 12;
	var iHeight = window.screen.availHeight - 30;
	var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
	window.open(WCMConstants.WCM6_PATH + "document/document_addedit.jsp?" + $toQueryStr(params), "special_open" , sFeature);
}

function $openMaxWin(_sUrl, _sName, _bReplace, _bResizable){
	var nWidth	= window.screen.width - 12, nHeight = window.screen.height - 60;
	var nLeft	= 0, nTop	= 0, sName	= _sName || "";
	sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
	var oWin = window.open(_sUrl, sName, "resizable=" 
		+ (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" 
		+ nLeft + ",menubar =no,toolbar =no,width=" 
		+ nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no", _bReplace);
	if(oWin)oWin.focus();
	return oWin;
}