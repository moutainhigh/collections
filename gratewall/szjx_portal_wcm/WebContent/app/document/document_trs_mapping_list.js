//TO Extend
Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : false,
	/**/
	objectType : new Date().getTime(),
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return PageContext.objectType;
	},
	rowInfo : {
		filename : true
	}
});
Ext.apply(PageContext, {
	_doBeforeLoad : function(){
		return false;
	},
	getContext : function(){
		var context = this.getContext0();
		//var bIsChannel = !!getParameter("ChannelId");
		//var bIsSite = !!getParameter("SiteId");
		//Ext.apply(context, {
		//	relateType : bIsChannel ? 'docrecycleInChannel' :
		//		(bIsSite ? 'docrecycleInSite' : 'docrecycleInRoot')
		//});
		return context;
	},
	//**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		//Ext.applyIf(this.params, {
		//	HostType : PageContext.intHostType,
		//	hostId : PageContext.hostId
		//});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	//*/
	/**/
	pageFilters : null,
	pageTabs : null,
	gridFunctions : function(){
		wcm.Grid.addFunction('trsmapping_edit', function(event){
			PageContext.editTrsMapping(event.getObj().getPropertyAsString('filename', ''));
		});
		wcm.Grid.addFunction('trsmapping_delete', function(event){
			PageContext.deleteTrsMapping(event.getObj().getPropertyAsString('filename', ''));
		});
	},
	CreateNewFile : function(event){
		var validInfo = ValidatorHelper.valid($('NewFileName'));
		var warning = validInfo.getWarning();
		isValid = validInfo.isValid();
		if(!isValid){
			Ext.Msg.$alert(warning);
			return false;
		}
		var sFileName = $F('NewFileName');
		if(sFileName.trim()==''){
			Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_55 || '文件名输入为空!');
			$('NewFileName').focus();
			return;
		}
		if(!sFileName.match(/\.xml$/ig)){
			sFileName = sFileName+'.xml';
		}
		var oPostData = {
			"FileName" : sFileName
		}
		BasicDataHelper.JspRequest('../document/document_trs_mapping_add.jsp',oPostData,false,function(transport){
			$('NewFileName').value = '';
			if(transport.responseText.trim()!=''){
				Ext.Msg.$alert(transport.responseText);
				return;
			}
			//this.LoadPage();
			window.location.reload();
		}.bind(this));
	},
	editTrsMapping : function(_sFileName){
		var sUrl = './document/document_trs_mapping_edit.jsp?FileName='
				+ _sFileName + '&ChannelId=' + getParameter("ChannelId");
		var oParams = {
			FileName : _sFileName,
			ChannelId : getParameter("ChannelId")
		};
		var aTop = $MsgCenter.getActualTop();
		if(aTop.oMappingEditDialog == null) {
			aTop.oMappingEditDialog = wcm.CrashBoarder.get('Trs_Mapping_Edit').show({
				title : wcm.LANG.DOCUMENT_PROCESS_56 || '编辑映射规则',
				src : WCMConstants.WCM6_PATH + 'document/document_trs_mapping_edit.jsp',
				width:'420px',
				height:'300px',
				maskable:true,
				params : oParams
			});
		}else{
			aTop.oMappingEditDialog.setUrl(sUrl);
		}
	},
	deleteTrsMapping : function(_sFileName){
		var aTop = $MsgCenter.getActualTop();
		if(aTop.oMappingEditDialog && aTop.oMappingEditDialog.visible == true) {
			aTop.oMappingEditDialog.close();
		}
		if(confirm(wcm.LANG.DOCUMENT_PROCESS_57 || '您确定要删除这个映射文件?')){
			var oPostData = {
				"FileName" : _sFileName
			}
			BasicDataHelper.JspRequest('../document/document_trs_mapping_delete.jsp',oPostData,false,function(){
				//this.LoadPage();
				window.location.reload();
			}.bind(this));
		}
	}

});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.DOCUMENT_PROCESS_3 || '个',
	TypeName : wcm.LANG.DOCUMENT_PROCESS_58 || '映射关系'
});

Event.observe('trueBtn', 'click', function(event){
	PageContext.CreateNewFile();
})

$MsgCenter.un($MsgCenter.getListener('sys_gridrow'));
//$MsgCenter.un($MsgCenter.getListener('sys_gridcell'));
$MsgCenter.un($MsgCenter.getListener('sys_allcmsobjs'));
