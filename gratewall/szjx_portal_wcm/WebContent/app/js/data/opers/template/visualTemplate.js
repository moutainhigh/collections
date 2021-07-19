Ext.ns('wcm.domain.TemplateMgr');
(function(){
	var m_oTemplateMgr = wcm.domain.TemplateMgr;
	Ext.apply(m_oTemplateMgr, {
		 'addOrEditVisualTemplate' : function(event, bAdd){
			var postData = {};
			var context = event.getContext();
			var host = event.getHost();
			if(context) {
				if(host.getType() == null) {
					postData = context;
				}else{
					postData['hosttype'] = host.getIntType();
					postData['hostid'] = host.getId();
				}
			}
			postData['objectid'] = (bAdd == true ? 0 : event.getIds()[0]);
			if(context && context.isTypeStub) {
				postData['typestub'] = 1;
			}
			wcm.CrashBoarder.get('visualtemplate_addedit').show({
				title : wcm.LANG.visualTemplate_101 || '新建修改可视化模板',
				src : WCMConstants.WCM6_PATH + 'template/visualtemplate_add_edit.jsp',
				width:'700px',
				height:'400px',
				maskable:true,
				params : postData,
				callback : function(){
					CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_TEMPLATE});
					this.hide();
				}
			});
		},
		'editGeneralTemplate' : function(event){
			var sParams = m_oTemplateMgr.__getCurrentParams(event);
			var params = event.getContext();
			if(params && params.isTypeStub) {
				sParams += '&typestub=1';
			}
			$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + sParams);
		},
		designVisualTemplate : function(event){
			var nTempId = event.getIds();
			var hostType = event.getHost().getIntType();
			var hostId = event.getHost().getId();
			window.open('../special/design.jsp?templateId=' + nTempId + 
					 '&HostType=' + hostType + '&HostId=' + hostId);
		},
		edit : function(event){
			var bVisual = event.getObj().getPropertyAsString("VisualAble");
			if(bVisual == 'true'){
				m_oTemplateMgr.addOrEditVisualTemplate(event, false);
			}else{
				m_oTemplateMgr.editGeneralTemplate(event);
			}
		}
	})
	var editTemplate = wcm.SysOpers.getOperItem('template','edit');
	Ext.apply(editTemplate, {
		fn : function(event){
			var bVisual = event.getObj().getPropertyAsString("VisualAble");
			if(bVisual == 'true'){
				m_oTemplateMgr.addOrEditVisualTemplate(event, false);
			}else{
				m_oTemplateMgr.edit(event);
			}
		}
	 })
	Ext.apply(m_oTemplateMgr, {
		'addVisualTemplate' : function(event){
			m_oTemplateMgr.addOrEditVisualTemplate(event, true);
		}
	})
})();
(function(){
	var pageObjMgr = wcm.domain.TemplateMgr;
	var reg = wcm.SysOpers.register;
	var fnIsVisible = function(event){
		var host = event.getHost();
		var context = event.getContext();
		if(Ext.isTrue(host.isVirtual) && host.getPropertyAsInt("chnlType", 0) != 0){
			return false;
		}
		return true;
	};
	reg({
		key : 'newvisualtemplate',
		type : 'templateInChannel',
		desc : wcm.LANG.visualTemplate_102 || '新建可视化模板',
		title:'新建可视化模板...',
		rightIndex : 21,
		order : 7,
		fn : pageObjMgr['addVisualTemplate'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'newvisualtemplate',
		type : 'templateInSite',
		desc : wcm.LANG.visualTemplate_102 || '新建可视化模板',
		title:'新建可视化模板...',
		rightIndex : 21,
		order : 12,
		fn : pageObjMgr['addVisualTemplate'],
		isVisible : fnIsVisible
	});
	

})();
		
 