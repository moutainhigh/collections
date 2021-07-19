Object.extend(PageContext, {
	servicesName : 'wcm6_MetaDataDef',
	queryMethodName : 'queryViews',

	loadPage : function(){
		var oParams = {fieldsToHtml: 'VIEWDESC,VIEWNAME', PageSize : 12};
		Object.extend(oParams, this.params);
		resetDisplay();
		window.setTimeout(function(){
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.Call(this.servicesName, this.queryMethodName,
				oParams, true, function(_trans, _json){
					var iStartIndex = parseInt(PageContext.params["CurrPage"]-1)*parseInt(PageContext.PageSize);
					var sValue = TempEvaler.evaluateTemplater('objectTemplates', _json,
						{"START_INDEX":iStartIndex});
					var bInputType = (PageContext.params.multi === true ? 'checkbox' : 'radio');
					sValue = sValue.replace(/_INPUT_TYPE_/g, bInputType);
					Element.update('objects', sValue);	

					//set selected.
					if(PageContext.selectIds){
						var ids = PageContext.selectIds.split(",");
						for (var i = 0; i < ids.length; i++){
							if($('input_' + ids[i])){
								$('input_' + ids[i]).checked = true;
							}
						}
					}
					PageContext.PageCount = _json["METAVIEWS"]["PAGECOUNT"];
					PageContext.RecordNum = _json["METAVIEWS"]["NUM"];
					PageContext.PageSize = _json["METAVIEWS"]["PAGESIZE"];
					PageContext.drawNavigator();

					Element.hide('divWaiting');
					Element.show('objects');
			}.bind(this));
		}.bind(this), 100);

	},
	initEvent : function(){
		Event.observe('refreshObjects', 'click', function(event){
			PageContext.loadPage();
			Event.stop(window.event || event);
		});

		Event.observe('objects', 'click', function(event){
			var srcElement = Event.element(window.event || event);
			var objectId = srcElement.getAttribute("_id");
			if(objectId){
				$('input_' + objectId).checked = true;
			}
		});
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '视图',
	go : function(_iPage,_maxPage){
		if(_iPage>_maxPage){
			_iPage = _maxPage;
		}
		Object.extend(PageContext.params, {'CurrPage': _iPage});
		PageContext.loadPage();
	}
});

function init(_params) {
	PageContext.params = {
		queryViewName : '',
		queryViewDesc : '',
		CrUser : '',
		queryViewId : ''
	};
	PageContext.selectIds = _params.ids;
	PageContext.loadPage();
}

function resetDisplay(){
	Element.hide('objects');
	Element.show('divWaiting');
}

function submitData(){
	if (window.parent.notifyParent2CloseMe){
		var oContainsChildren = $('ContainsChildren');
		var isContainsChildren = oContainsChildren.checked ? true : false;
		var _args = {ContainsChildren:isContainsChildren};
		if(!$('chkNone').checked){
			var oInputs = document.getElementsByName("objectId");
			var selectIds = [];
			var selectNames = [];
			for (var i = 0; i < oInputs.length; i++){
				if(oInputs[i].checked){
					selectIds.push(oInputs[i].value);
					selectNames.push(oInputs[i].getAttribute("_name"));
				}
			}
			if(oInputs.length > 0 && selectIds.length <= 0){
				$alert("请选择一个视图");
				return false;
			}
			Object.extend(_args, {ids:selectIds, names:selectNames});
		}
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
		window.parent.notifyParentOnFinished(document.FRAME_NAME, _args);
	}
	resetDisplay();
}

var m_bFirstShowMask = true;
function deSelect(isChecked){
	if(isChecked){	
		if(m_bFirstShowMask) {
			Position.clone($('tblContent'), $('divMask'));
			m_bFirstShowMask = false;
		}
		Element.hide('selQueryType');
		Element.show('divMask');
	}else{
		Element.show('selQueryType');
		Element.hide('divMask');
	}
}

function closeframe(){
	if (window.parent.notifyParent2CloseMe){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
	}
	resetDisplay();
}

Event.observe(window, 'load', function(){
	var arQueryFields = [
		{name: 'queryViewDesc', desc: '视图名称', type: 'string'},
		{name: 'CrUser', desc: '创建者', type: 'string'},
		{name: 'queryViewId', desc: '视图ID', type: 'int'}
	];
	SimpleQuery.register('query_box', arQueryFields, function(_params){
		Object.extend(PageContext.params, _params);
		PageContext.loadPage();
	}, true);

	new $WCMButton({
		ButtonType	: $ButtonType.OK,
		Action		: 'submitData()',
		Container	: 'ButtonContainer'
	}).loadButton();
	new $WCMButton({
		ButtonType	: $ButtonType.CANCEL,
		Action		: 'closeframe()',
		Container	: 'ButtonContainer'
	}).loadButton();

	PageContext.initEvent();
});

Event.observe(window, 'beforeunload', function(){
	$('chkNone').checked = false;
});
Event.observe(window, 'unload', function(){
	$destroy(PageContext);
} , false);
