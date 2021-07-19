window.m_cbCfg = {
	btns : [
		{
			text : '确定',
			cmd : function(){
				if(!ValidationHelper.doValid("table_cnt")){
					return false;
				}
				var cbr = wcm.CrashBoarder.get(window);
				cbr.hide();
				var params = buildValues();
				cbr.notify(params);
				return false;
			}
		},
		{text : '取消'}
	]
};

/**构造参数，点击“确定”后将数据返回给回调函数*/
function buildValues(){
	var fieldNames=[];
	var anotherFieldName=[];
	var linkFieldName;
	var chks = document.getElementsByName('fieldName');
	for(var i=0; i< chks.length; i++){
		var chk = chks[i];
		if(!chk.checked) continue;
		fieldNames.push(chk.getAttribute('_chkName'));
		anotherFieldName.push(chk.getAttribute('_chkAnotherName'));
	}
	return {fieldNames:fieldNames,anotherFieldNames:anotherFieldName};
}

/** ----------------------检索功能------------------------------*/
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name: 'queryAnotherName', desc: '字段名称', type: 'string'}
			//{name: 'CrUser', desc: '创建者', type: 'string'},
			//{name: 'viewFieldInfoId', desc: '字段ID', type: 'int'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			var sCurrQueryJson = getUrlJson();
			sCurrQueryJson.queryAnotherName = "";
			sCurrQueryJson.CrUser = "";
			sCurrQueryJson.viewFieldInfoId = "";
			Ext.apply(sCurrQueryJson, params);
			var queryString = $toQueryStr2(sCurrQueryJson);
			window.location.href = 'viewfieldforwidget_select_list.jsp?' + queryString;
		}
	});
});

/**----begin----检索时获取的参数处理函数------*/
function $toQueryStr2(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + value);
	}
	return rst.join('&');
}
function getUrlJson(){
	var json = {};
	var sUrl = location.href;
	if(sUrl.indexOf('?') > 0){
		sUrl = sUrl.substring(sUrl.indexOf('?') + 1, sUrl.length);
		json = sUrl.parseQuery();
	}
	return json;
}
Ext.apply(String.prototype, {
	 parseQuery : function() {
		var pairs = this.match(/^\??(.*)$/)[1].split('&');
		return pairs.inject({}, function(params, pairString) {
			var pair = pairString.split('=');
			params[pair[0]] = pair[1];
			return params;
		});
	}
});
Ext.apply(Array.prototype, {
	each: function(iterator) {
		try {
			for (var i = 0; i < this.length; i++){
				iterator(this[i], i);
			}
		} catch (e) {
			if (e != Ext.$break) throw e;
		}
	},
	inject: function(memo, iterator) {
		this.each(function(value, index) {
			memo = iterator(memo, value, index);
		});
		return memo;
	}
});
/**----end----检索时获取的参数处理函数------*/

/**------begin:   适用于当选择了以后，记住选择机制------*/
var hasSelected;
function init(params){
	var viewFieldName = new String(params['ViewFieldName']);
	hasSelected = viewFieldName;
	var selectedViewFieldName = hasSelected; 
	var chks = document.getElementsByName('fieldName');
	for (var i = 0; i < chks.length; i++){
		var chk = chks[i];
		if(selectedViewFieldName == chk.value){
			chk.checked = true;
		}
	}
}
/** end*/