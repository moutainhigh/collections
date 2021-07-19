//TO Extend

/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : WCMConstants.WCM6_PATH + 'keyword/keyword_list_dowith.jsp',
	methodName : '',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//TODO type filters here
]);

//检索框信息

Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		container : 'search', 
		appendQueryAll : true,
		autoLoad : true,
		items : [
			{name : 'KNAME', desc : wcm.LANG.KEYWORD_1 || '关键词名称', type : 'string'},
			{name : 'CrUser', desc : wcm.LANG.KEYWORD_11 || '创建者', type : 'string'}
		],
		callback : function(params){
			PageContext.loadList(Ext.apply(PageContext.params, params));
		}
	});
});


/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型；
*第二块和第三块面板的类型通过如下方式获取：
*如果列表页面没有选择记录，则为导航树节点对应的类型(如：website,channel);
*如果列表当前选中一条记录，则类型为当前列表的rowType(如：chnldoc);
*如果列表当前选中多条记录，则类型为当前列表的rowType+s(如：chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中；
*/
PageContext.setRelateType(
	//!!PageContext.getParameter("ChannelId") ? "replaceInChannel" :
	//	(!!PageContext.getParameter("SiteId") ? "replaceInSite" : "replaceInRoot")
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.KEYWORD_9 || '个',
	TypeName : wcm.LANG.KEYWORD_10 || '关键词'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'keyword_edit' : function(event){
		PageContext.editKeyword(event.getObj().getPropertyAsInt('KeywordId'));
	},
	'keyword_delete' : function(event){
		PageContext.deleteKeyword(event.getObj().getPropertyAsInt('KeywordId'));
	}
});

Ext.apply(PageContext, {
	CreateNewKey : function(){
		/*if(!ValidationHelper.doValid('keywordForm')){
			return false;
		}*/
		var sKName = $F('keywordName');
		if(sKName.trim()==''){
			Ext.Msg.$alert(wcm.LANG.KEYWORD_2 || '关键词名输入为空!');
			$('keywordName').focus();
			return;
		}
		if(sKName.length > 50){
			Ext.Msg.$alert(wcm.LANG.KEYWORD_13 || '关键词名不能超过50个字符!');
			$('keywordName').focus();
			return;
		}
		var oPostData = {
			KName : sKName,
			siteType : $('siteType').value,
			siteId : $('siteId').value
		}
		BasicDataHelper.JspRequest('../keyword/keyword_add.jsp',oPostData,false,function(transport){
			$('trueBtn').disabled = true;
			if(transport.responseText.trim()!=''){
				Ext.Msg.$alert(transport.responseText);
				return;
			}
			$('keywordName').value = '';
			PageContext.loadList();
		}.bind(this));
	},
	editKeyword : function(_KeywordId){
		var oParams = {
			KeywordId : _KeywordId,
			siteId : $('siteId').value
		};
		var cbr = wcm.CrashBoarder.get('keyword_Edit');
		cbr.show({
			title : wcm.LANG.KEYWORD_3 || '修改关键词',
			src : WCMConstants.WCM6_PATH + '/keyword/keyword_edit.jsp',
			width:'380px',
			height:'90px',
			maskable:true,
			params : oParams,
			callback : function(){
				PageContext.loadList();
			}
		});
	},
	deleteKeyword : function(_KeywordId){
		if(confirm(wcm.LANG.KEYWORD_4 || '您确定要删除这个关键词？')){
			var oPostData = {
				KeywordId : _KeywordId
			}
			BasicDataHelper.JspRequest('../keyword/keyword_delete.jsp',oPostData,false,function(){
				PageContext.loadList();
			}.bind(this));
		}
	}


});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	//displayNum : 6,
	//activeTabType : 'replace'
});


/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : new Date().getTime()
});
/*Event.observe('trueBtn', 'click', function(event){
	Event.stop(event || window.event);
	setTimeout(function(){
		if($('trueBtn').disabled){
			$('trueBtn').blur();
			return;
		}
		PageContext.CreateNewKey();
	}, 0);
});*/
Event.observe('keywordName', 'focus', function(event){
	$('keywordName').select();
});

var m_arrValidations = [{
		renderTo : "keywordName",
		desc : wcm.LANG.KEYWORD_1 || '关键词名称'
	}
];
function init(){
	ValidationHelper.registerValidations(m_arrValidations);
	ValidationHelper.addValidListener(function(){
			wcmXCom.get('trueBtn').enable();
        }, "keywordName");
        ValidationHelper.addInvalidListener(function(){
            wcmXCom.get('trueBtn').disable();
        }, "keywordName");
	ValidationHelper.initValidation();
};

function checkKeywordName(){
	var sKName = $F('keywordName');
	var oPostData = {
		KName : sKName,
		siteId : $('siteId').value,
		siteType : $('siteType').value
	}
	BasicDataHelper.JspRequest(
		WCMConstants.WCM6_PATH + '/keyword/keyword_exists.jsp',
		oPostData,  false,
		function(transport){
			if(transport.responseText == 'false'){
				ValidationHelper.successRPCCallBack();
			}else{
				ValidationHelper.failureRPCCallBack(wcm.LANG.KEYWORD_14 || "已经存在这个关键词.");
			}
		}
	);
}

window.m_cbCfg = {
	btns : [
		{
			id : 'btnRef',
			text : wcm.LANG.REFRESH || '刷新',
			cmd : function(){
				PageContext.loadList(PageContext.params);
				return false;
			}
		},
		{
			id : 'btnClose',
			text : wcm.LANG.KEYWORD_5 || '关闭',
			cmd : function(){
				
			}
		}
	]
}

new wcm.Button({
	id : 'trueBtn',
	text : wcm.LANG.KEYWORD_6 || '确定',
	tip : '',
	renderTo : 'btnbox1',
	cmd : function(){
		PageContext.CreateNewKey();
	}
}).show();

$MsgCenter.un($MsgCenter.getListener('sys_gridrow'));
//$MsgCenter.un($MsgCenter.getListener('sys_gridcell'));
$MsgCenter.un($MsgCenter.getListener('sys_allcmsobjs'));
