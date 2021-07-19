/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	gridDraggable : false,
	serviceId : 'wcm61_channelsyncol',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"CHANNELASTARGET" : true
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//{desc:wcm.LANG.CURRPOSITON || '当前位置', type:1}
	//TODO type filters here
]);

/*-------------指定各种情况下右侧的操作面板--------------*/
PageContext.setRelateType(
	!!PageContext.getParameter("ChannelId") ? "channelsyncolInChannel" :
		(!!PageContext.getParameter("SiteId") ? "channelsyncolInSite" : "channelsyncolInRoot")
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.CHANNELSYN_UNIT || '个',
	TypeName : wcm.LANG.CHANNELSYN_DOCUMENTSYN || '文档同步'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){//单击列表上修改图标时执行的操作
		var myMgr = wcm.domain.ChannelSynColMgr;
		myMgr['edit'](event);
	},
	'delete' : function(event){//单击列表上删除图标时执行的操作
		var myMgr = wcm.domain.ChannelSynColMgr;
		myMgr['delete'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'channelsyn'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_CHANNELSYNCOL
});

/*-------------操作面板上下文环境信息--------------*/
Ext.apply(PageContext, {
	_buildParams : function(wcmEvent, actionType, valueDom){
		if(actionType=='save'){
			var sFieldName = valueDom.getAttribute('_fieldName', 2);
			if(sFieldName && ['TRANSMITTYPE'].include(sFieldName.toUpperCase())){
				return {
					Attribute : [
						sFieldName,
						'=',
						valueDom.getAttribute('_fieldValue', 2)
					].join(''),
					ChannelId : getParameter("channelId")
				}
			}
			return {
				channelId : getParameter("channelId")
			};
		}
	}
});

//检索框信息
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search',
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : false,
		/*检索项的内容*/
		items : [
			/*
			{name : 'TempDesc', desc : '显示名称', type : 'string'},
			{name : 'TempName', desc : '唯一标识', type : 'string'},
			//*/
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
});
//路径信息
Ext.apply(PageContext.literator, {
	enable : true,
	width : 1
});
Ext.apply(PageContext.literator.params, {
	tracesite : false
});

function convertStatus(){
	location.href = WCMConstants.WCM6_PATH + 'channelsyn/channelsyn_list.html' + location.search;
	return;
}
function buildDateCompMethod(cName, fnCompare){
	return function(){
		var element = this.field;
		if(element == null || element.value.trim() == '')return true;
		var cDate = $(cName+'_SPAN').getAttribute("_fieldValue", 2);
		cDate = Date.parseDate(cDate, "%y-%m-%d").getTime();
		var myDate = Date.parseDate(element.value, "%y-%m-%d").getTime();
		return fnCompare.call(this, myDate, cDate);
	}
}
function calGetValue(spId){
	return function(){
		var el = $(spId);
		return el.tagName=='INPUT' ? el.value : el.getAttribute("_fieldValue", 2);
	}
}
function calSetValue(spId){
	return function(v){
		var el = $(spId);
		if(el.tagName=='INPUT'){
			el.value = v;
		}else{
			var inputDom = document.createElement('INPUT');
			inputDom.value = v;
			inputDom.name = spId.split('_')[0];
			wcm.PageOper.transHelper.setCalendarValue(el, inputDom);
		}
	}
}
var m_oDateValidJson = {
	type :'date',
	required :true,
	asyn : false
};
ValidationHelper.bindValidations([
	{
		renderTo : 'WHERESQL',
		type :'string',
		required :false,
        asyn : true,
		methods : function(){
			var element = this.field;
			if(element != null && element.value.trim() != '') {
				var oPostData = {
					queryby : element.value,
					channelid : getParameter("channelId")
				};
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.call('wcm6_channel', 'checkSQLValid', oPostData, true, function(_trans, _json){
				    var sSql = $v(_json, 'sql');
					var sError = $v(_json, 'error');
					var sDesc = null;
					if(sError != null){
						sDesc = "ERROR : " + sError + "  SQL : " + $transHtml(sSql);
					}
					ValidatorHelper.execCallBack(element, sDesc);
				});
			}
		}
	},
	Ext.applyIf({
		renderTo : 'SDATE',
		methods : buildDateCompMethod('EDATE', function(myV, compV){
			var rst = myV <= compV;
			this.warning += rst ? "" : (wcm.LANG.CHANNELSYN_VALID_1 ||"汇总开始时间大于汇总结束时间");
			return rst;
		})
	}, m_oDateValidJson),
	Ext.applyIf({
		renderTo : 'EDATE',
		methods : buildDateCompMethod('SDATE', function(myV, compV){
			var rst = myV >= compV;
			this.warning += rst ? "" : (wcm.LANG.CHANNELSYN_VALID_1 ||"汇总开始时间大于汇总结束时间");
			return rst;
		})
	}, m_oDateValidJson),
	Ext.applyIf({
		renderTo : 'DOCSDATE',
		methods : buildDateCompMethod('DOCEDATE', function(myV, compV){
			var rst = myV <= compV;
			this.warning += rst ? "" : (wcm.LANG.CHANNELSYN_VALID_2 ||"文档创建时间范围错误");
			return rst;
		})
	}, m_oDateValidJson),
	Ext.applyIf({
		renderTo : 'DOCEDATE',
		methods : buildDateCompMethod('DOCSDATE', function(myV, compV){
			var rst = myV >= compV;
			this.warning += rst ? "" : (wcm.LANG.CHANNELSYN_VALID_2 ||"文档创建时间范围错误");
			return rst;
		})
	}, m_oDateValidJson)
]);