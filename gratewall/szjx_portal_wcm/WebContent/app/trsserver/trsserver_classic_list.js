Ext.apply(PageContext, { 
	serviceId : 'wcm61_viewdocument',
	methodName : 'trsQuery',
	contextMenuEnable  : true,
	/**/
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});
/*-------------指定列表上的过滤器--------------*/
PageContext.filterEnable = false;

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['DOC_UNIT'] || '篇',
	TypeName : wcm.LANG['DOCUMENT'] || '文档'
});

//检索信息
Event.observe(window, 'load', function(){
		ClassicList.makeLoad();
        wcm.ListQuery.register({
            /*检索控件追加到的容器*/
            container : 'query_box', 
            /*是否追加“全部”这个检索项, default to false*/
            appendQueryAll : true,
            /*是否自动加载, default to true*/
            autoLoad : true,
            /*检索项的内容*/
            items : [
    			{name: 'doctitle', desc: wcm.LANG.DOCUMENT_PROCESS_177 || '文档标题', type: 'string'}
            ],
            /*执行检索按钮时执行的回调函数*/
            callback : function(params){
               PageContext.loadList(params);

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
PageContext.operEnable = false;

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'trsserver'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_TRSSERVER
});

ClassicList.cfg = {
	toolbar : [
		{
			id : 'trsserver_config',
			fn : function(event, elToolbar){
				var oPageParams = {};
				var sId = event.getIds().join();
				Object.extend(oPageParams,{"ObjectId":sId});
				Object.extend(oPageParams,{"siteId":event.getHost().getId()});
				FloatPanel.open(WCMConstants.WCM6_PATH +
						'trsserver/trsserver_config.jsp?' + $toQueryStr(oPageParams), wcm.LANG.TRSSERVER_4 || '配置窗口', CMSObj.afteredit(event)
				);
			},
			name : wcm.LANG.TRSSERVER_1 || '配置TRSServer信息',
			desc : wcm.LANG.TRSSERVER_1 || '配置TRSServer信息',
			isVisible : function(){
				return true;
			},
			isDisabled : function(event){
				if(wcm.AuthServer.isAdmin()){
					return false;
				}
				return true;
			},
			rightIndex : -1
		}, {
			id : 'trsserver_pick',
			fn : function(event, elToolbar){
				var sIds = event.getObjs().getIds();
				var args = {
					IsRadio : 1,
					CurrSiteType : 0,
					ExcludeTop : 1,
					ExcludeLink : 1,
					ExcludeVirtual : 1,
					ExcludeInfoView : 1,
					ExcludeOnlySearch : 1,
					ShowOneType : 1,
					NotSelect : 1,
					canEmpty : true
				};
				FloatPanel.open(
					WCMConstants.WCM6_PATH + 'include/channel_select.html?close=1',
					wcm.LANG.TRSSERVER_7 || '选择所属栏目',
					function(selectIds, selectChnlDescs){
						if(!selectIds||selectIds.length==0) {
							Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_32 ||'请选择当前文档要复制到的目标栏目!');
							return false;
						}
						var oPostData = {
							ServerIds :sIds,
							ChannelId : selectIds[0]
						}
						var oHelper = new com.trs.web2frame.BasicDataHelper();
						oHelper.Call('wcm6_document', 'trsSaveToWCM', oPostData, true, function(_oTrans, _json){ 
							alert(wcm.LANG.DOCUMENT_PROCESS_237 || "提取成功！");
							FloatPanel.close();
							PageContext.loadList(PageContext.params);
						},function(){
							alert(wcm.LANG.DOCUMENT_PROCESS_238 || "提取失败！");
						});
					},
					dialogArguments = args
				);
			},
			name : wcm.LANG.TRSSERVER_5 || '提取',
			desc : wcm.LANG.TRSSERVER_6 || '提取为WCM数据',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : -1
		}, 
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.TREE_REFRESH || '刷新',
			desc : wcm.LANG.TRSSERVER_2 || '刷新列表'
		}
	],
	listTitle : wcm.LANG['TRSSERVER_3'] || 'TRSServer数据列表'
}

/*-------------列表上操作入口的函数--------------*/
function view(_sId,_nPageIndex,_nPageSize){
	var oParams = {
		ServerId : _sId,
		PageIndex : _nPageIndex,
		PageSize : _nPageSize
	};
	$openMaxWin(WCMConstants.WCM6_PATH +
			'trsserver/trsserver_show.jsp?' + $toQueryStr(oParams));
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