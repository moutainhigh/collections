$package('com.trs.wcm');
$import('com.trs.crashboard.CrashBoarder');

var DIALOG_TEMPLATE_SELECTOR = 'Template_Select_Multi';
var CLASS_TEMPLATE_ENTITY = 'tempEntity';
var ID_TEMPLATE = '_tempid';
var NAME_TEMPLATE = '_tempname';

com.trs.wcm.TemplateSelector = Class.create('com.trs.wcm.TemplateSelector');
com.trs.wcm.TemplateSelector.prototype = {
	m_eTempSelector: null,
	initialize: function(_context, _bIsTop, _bSelfIsTop){
		if(_context == null) {
			alert('need a context to initialize the com.trs.wcm.TemplateSelector!');
			return;
		}
		this.context = _context;
		// ge gfc add @ 2007-4-5 10:03
		// if the cb is display on the top page
		// note: 'true' as default
		this.m_bIsTop = (_bIsTop == null ? true : _bIsTop); 

		// ge gfc add @ 2007-7-4 15:04
		// if the current window which the cb is displaying is top itself
		// note: 'false' as default
		this.m_bSelfIsTop = (_bSelfIsTop == null ? false : _bSelfIsTop); 


		// init the dialog
		var sUrl = (this.m_bIsTop ? './' : '../') + 'template/template_select_multi.html';
		this.m_eTempSelector = TRSDialogContainer.register(DIALOG_TEMPLATE_SELECTOR
			, '选择模板', sUrl, '370px', '320px', false);
		TRSCrashBoard.setMaskable(true);
	},
	selectTemps : function(_bMulti, _sContainerId, _nType,_onFinished){
		var others = this.__getTempEntity(_sContainerId);
		var oParams = Object.extend({
				templatetype: _nType,
				templateids: others.ids.clone(),
				templates: others.temps.clone(),
				multi: _bMulti
		},this.context);
		Object.extend(oParams, PageContext.tsp);
		this.m_eTempSelector.onFinished = (typeof _onFinished=="function")?function(_args){
				_onFinished(_args.selectedIds,_args.selectedTemps);
			}:function(_args){
			var selectedIds = _args.selectedIds;
			//alert(_args.selectedIds.length + ', ' + selectedIds.join(','))
			if(selectedIds.join(',') == others.ids.join(',')) {
				return;// 没有变化, 什么更新也不做
			}
			if(selectedIds.length == 0) {
				Element.update($(_sContainerId), '无');
				return;
			}
			//else
			var sContent = '';
			if(_bMulti) {
				var selectedTemps = _args.selectedTemps;
				var arrContent = [];
				for (var i = 0; i < selectedTemps.length; i++){
					var entity = selectedTemps[i];
					arrContent.push('<span ' + ID_TEMPLATE +'="' + entity.id 
						+ '" ' + NAME_TEMPLATE + '="' + entity.name 
						+ '" class="' + CLASS_TEMPLATE_ENTITY + '">' + entity.name + '</span>');
				}
				sContent = arrContent.join(',&nbsp;&nbsp;')
			}else{
				var selectedTemp = _args.selectedTemps[0];
				sContent = '<span ' + ID_TEMPLATE +'="' + selectedTemp.id 
						+ '" ' + NAME_TEMPLATE + '="' + selectedTemp.name 
						+ '" class="' + CLASS_TEMPLATE_ENTITY + '">' + selectedTemp.name + '</span>'
			}
			// update html
			Element.update($(_sContainerId), sContent);
		}
		if(this.m_bSelfIsTop) {
			TRSDialogContainer.display(DIALOG_TEMPLATE_SELECTOR, oParams);
			return;
		}
		//else
		//var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));
		var positions = Position.getPageInTop(Event.element(Event.findEvent()));
		//TRSDialogContainer.display(DIALOG_TEMPLATE_SELECTOR, oParams,positions[0]+20, positions[1]+20);
		TRSDialogContainer.display(DIALOG_TEMPLATE_SELECTOR, oParams);
	},
	//fillOutlineTempData('spOutlineTemp', 'spOthersTemp', 'hdOutlineTemps');
	fillOutlineTempData : function(_sOutlineTempId, _sOthersTempId, _sSubmitInputId){
		var arrOutline = this.__getTempEntity(_sOutlineTempId).ids;
		if(arrOutline.length == 0) arrOutline = [0];
		var arrOthers = this.__getTempEntity(_sOthersTempId).ids;
		var sVal = arrOutline.concat(arrOthers).join(',');
		if(sVal.trim() == '') {
			$(_sSubmitInputId).value = 0;
		}else{
			$(_sSubmitInputId).value = sVal;
		}
	},
	//fillDetailTempData('spDetailTemp', 'hdDetailTemp');
	fillDetailTempData : function(_sDetailTempId, _sSubmitInputId){
		var arrDetail = this.__getTempEntity(_sDetailTempId).ids;
		var sVal = arrDetail.join(',');
		if(sVal.trim() == '') {
			$(_sSubmitInputId).value = 0;
		}else{
			$(_sSubmitInputId).value = sVal;
		}
	},

	__getTempEntity : function(_sContainerId){
		var tempEntity = document.getElementsByClassName(CLASS_TEMPLATE_ENTITY, $(_sContainerId));
		var arrIds = [];
		var arrTemps = [];
		for (var i = 0; i < tempEntity.length; i++){
			var spTemp = tempEntity[i];
			var nId = parseInt(spTemp.getAttribute(ID_TEMPLATE, 2));
			if(isNaN(nId) || nId == 0) {
				continue;
			}
			arrIds.push(nId);
			arrTemps.push({id: nId, name: spTemp.getAttribute(NAME_TEMPLATE, 2)});
		}
		return {ids: arrIds, temps: arrTemps};
	}
	
}
//e.g.
//var templateSelector = new com.trs.wcm.TemplateSelector(PageContext.tsp);
Object.extend(Array.prototype, {
	clone: function(_arr){
		_arr = _arr || this;
		if(_arr == null || _arr.length == 0) {
			return [];
		}
		var result = [];
		for (var i = 0; i < _arr.length; i++){
			result.push(_arr[i]);
		}
		result = result.compact();
		return result;
	}
});