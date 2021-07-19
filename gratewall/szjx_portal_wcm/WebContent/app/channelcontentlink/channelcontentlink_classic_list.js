/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	serviceId : 'wcm61_channelcontentlink',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"FromEditor" : 1
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//{desc:wcm.LANG.CURRPOSITON || '当前位置', type:1}
	//TODO type filters here
]);

/*-------------指定各种情况下右侧的操作面板--------------*/
PageContext.setRelateType(
	!!PageContext.getParameter("ChannelId") ? "channelcontentlinkInChannel" :
		(!!PageContext.getParameter("SiteId") ? "channelcontentlinkInSite" : "channelcontentlinkInRoot")
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.CHANNELCONTENTLINK_UNIT || '个',
	TypeName : wcm.LANG.CHANNELCONTENTLINK_MGR || '热词'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){//单击列表上修改图标时执行的操作
		var myMgr = wcm.domain.ChannelContentLinkMgr;
		myMgr['edit'](event);
	},
	'delete' : function(event){//单击列表上删除图标时执行的操作
		var myMgr = wcm.domain.ChannelContentLinkMgr;
		myMgr['delete'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'channelcontentlink'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK
});

//检索框信息
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
			{name : 'LINKNAME', desc : wcm.LANG.CHANNELCONTENTLINK_NAME || '热词名称', type : 'string'},
			{name : 'LINKTITLE', desc : wcm.LANG.CHANNELCONTENTLINK_DESC || '热词描述', type : 'string'}
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
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}

});
/*----------列表拖动逻辑------------*/
Ext.apply(PageContext, {
	contextMenuEnable : true,
	gridDraggable : true,
	getParameter : function(paramName){
		paramName = paramName.toLowerCase();
		if(['channelid', 'siteid'].include(paramName)){
			return getParameter(paramName) || getParameter(paramName + 's') || '';
		}
		return getParameter.apply(null, arguments);
	},
	initDraggable : function(){
		var docGridDragDrop = new wcm.dd.GridDragDrop({
			id : 'wcm_table_grid', 
			rootId : 'grid_body',
			captureEnable:false
		});
		docGridDragDrop.addListener('dispose', function(){
			top.DragAcross = null;
			delete this.hintInSelf;
			delete this.hintInTree;
		});
		Ext.apply(docGridDragDrop, {
			_getHint : function(row){
				if(this.hintInSelf) return this.hintInSelf;
				if(!top.DragAcross){
					top.DragAcross = {};
				}
				var sCurrId = row.getObjId();
				var aSelectedIds = wcm.Grid.getRowIds(true);
				if(!aSelectedIds.include(sCurrId)) aSelectedIds.push(sCurrId);			
				Object.extend(top.DragAcross,{
					ObjectType : 1952046669 ,
					FolderId :  PageContext.getParameter("channelid"),
					ObjectId : sCurrId,
					ObjectIds : aSelectedIds
				});
				if(PageContext.params["CONTAINSSITE"]){
					return wcm.LANG.CHANNELCONTENTLINK_FN_23 || "当前热词列表不支持排序";
				}
				if(PageContext.params["ORDERBY"]){
					return wcm.LANG.CHANNELCONTENTLINK_FN_24 || "自动排序列表不支持手动排序";
				}
				return String.format("[热词-{0}]",sCurrId);
			},
			_isSortable : function(row){
				if(PageContext.params["CONTAINSSITE"] || PageContext.params["ORDERBY"]) return false;
				var sRight = row.dom.getAttribute("right", 2);
				if(sRight!=null&&!wcm.AuthServer.checkRight(sRight, 32)){
					return false;
				}
				return true;
			},
			_move : function(srcRow, iPosition, dstRow, eTargetMore){
				//iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
				if(PageContext.params["CONTAINSSITE"] || PageContext.params["ORDERBY"]) return false;
				var rowId = srcRow.getAttribute('rowId');
				var order = dstRow.getAttribute('linkorder');
				if(iPosition == 1){
					order++;
				}
				if(!confirm(wcm.LANG.CHANNELCONTENTLINK_FN_26 || '您确定要调整热词的顺序?')) return false;
				var oPostData = {
					linkid:rowId,
					neworder:order,
					channelid: PageContext.getParameter("channelid")
				};
				BasicDataHelper.call('wcm6_contentlink', 'changeorder', oPostData, true, 
					function(){
						PageContext.updateCurrRows(rowId);
					},
					function(trans,json){
						wcm.FaultDialog.show({
							code		: $v(json,'fault.code'),
							message		: $v(json,'fault.message'),
							detail		: $v(json,'fault.detail'),
							suggestion  : $v(json,'fault.suggestion')
						}, wcm.LANG.CHANNELCONTENTLINK_FN_27 || '与服务器交互时出现错误' , function(){
							PageContext.updateCurrRows();
						});
					});
				return true;
			}
		});
	}
});

ClassicList.cfg = {
	toolbar : [
		{
			id : 'channelcontentlink_new',
			fn : function(event, elToolbar){
				wcm.domain.ChannelContentLinkMgr.add(event);
			},
			name : wcm.LANG.CHANNELCONTENTLINK_FN_17 || '新建',
			desc : wcm.LANG.CHANNELCONTENTLINK_FN_2 || '新建栏目热词...',
			rightIndex : 13,
			isHost	: true
		}, {
			id : 'channelcontentlink_delete',
			fn : function(event, elToolbar){
				wcm.domain.ChannelContentLinkMgr['delete'](event);
			},
			name : wcm.LANG.CHANNELCONTENTLINK_FN_18 || '删除',
			desc : wcm.LANG.CHANNELCONTENTLINK_FN_7 || '删除选定栏目热词',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 13
		},{
			id : 'channelcontentlink_import',
			fn : function(event, elToolbar){
					wcm.domain.ChannelContentLinkMgr['set'](event);
				},
			name : wcm.LANG.CHANNELCONTENTLINK_FN_19 || '导入',
			desc : wcm.LANG.CHANNELCONTENTLINK_FN_8 || '导入系统热词',
			rightIndex : 13,
			isHost	: true
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.CHANNELCONTENTLINK_FN_21 || '刷新',
			desc : wcm.LANG.CHANNELCONTENTLINK_FN_22 || '刷新列表'
		}
	],
	listTitle : wcm.LANG.CHANNELCONTENTLINK_FN_20 || '热词列表管理',
	path : [
			'<div class="view_mode_normal" style="display:none" id="divDispMode" title="',
			wcm.LANG['CHANNELCONTENTLINK_FN_34'] || "显示站点的热词",
			'" onclick="switcSiteDisp()" WCMAnt:paramattr="title:document_list.html.dispmode"></div>'
			].join('')
}

Event.observe(window, 'load', function(){
	if(PageContext.hostType == WCMConstants.OBJ_TYPE_CHANNEL){
		Element.show('divDispMode');
	}
});

//是否显示子对象
function switcSiteDisp(){
	var _eFirer = $("divDispMode");
	if(_eFirer.className =="view_mode_normal"){
		Object.extend(PageContext.params,{"containsSite":true});
		_eFirer.title =  wcm.LANG['CHANNELCONTENTLINK_FN_35'] || '隐藏站点的热词';
		_eFirer.className = "view_mode_recursive";
	}else{
		Object.extend(PageContext.params,{"containsSite":false});
		_eFirer.title = wcm.LANG['CHANNELCONTENTLINK_FN_34'] || '显示站点的热词';
		_eFirer.className = "view_mode_normal";
	}
	 PageContext.loadList(PageContext.params);
}
function initDisMode(bShowChildren){
	var _eFirer = $("divDispMode");
	if(!bShowChildren){
		_eFirer.title = wcm.LANG['CHANNELCONTENTLINK_FN_34'] || '显示站点的热词';
		_eFirer.className = "view_mode_normal";
	}else{
		_eFirer.title =  wcm.LANG['CHANNELCONTENTLINK_FN_35'] || '隐藏站点的热词';
		_eFirer.className = "view_mode_recursive";
	}	
}