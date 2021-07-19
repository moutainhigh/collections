$package('com.trs.wcm.domain');

var $templateMgr = com.trs.wcm.domain.TemplateMgr = {
	serviceId : 'wcm6_templateArg',
	helpers : {},
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
		/*
		_sServceFlag = (!_sServceFlag)?'basic':_sServceFlag.toLowerCase();
		if(!this.helpers[_sServceFlag]) {
			var oHelper = null;
			switch(_sServceFlag){
				case 'basic' :
					oHelper = BasicDataHelper;
					break;
				case 'templateArg' :
					oHelper = $dataHelper(com.trs.wcm.datasource.template.Templates);
					break;
				default :
					alert('TemplateArgMgr不支持"'+_sServceFlag+'"数据源');
					break;
			}
			this.helpers[_sServceFlag] = oHelper;
		}
		return this.helpers[_sServceFlag];
		*/
	},
	save : function(_sDocId,_oPageParams){
		alert('edit-'+_sDocId);
	}
};
