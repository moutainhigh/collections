Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_website',
	ObjectMethodName : 'getRecycleSites',
	AbstractParams : {
			'SelectFields' : '',
			'SiteType' : getParameter("SiteType") || 0,
			//记录一下全局的权限
			'RightValue'	: getParameter('RightValue') || '0',
			fieldsToHtml: 'sitedesc,sitename'
	},//服务所需的参数

	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : SiteRecycleMgr,

	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType	: 'siterecycle',
	_doBeforeRefresh : function(){
//		alert($toQueryStr(PageContext.params) + '\n' + getParameter('SiteType'));
		PageContext.drawLiterator('literator_path', null, function(_sParamName){
			return getParameter(_sParamName, $toQueryStr(PageContext.params));
		});
	},
	
	returnBack : function(){
		var oParams = {
			//RightValue: getParameter('RightValue') || '0'
		}
		oParams.SiteType = PageContext.params['SiteType'];
		document.location.href = '../website/website_thumbs_index.html?' + $toQueryStr(oParams);
		
		if((top.actualTop || top).RotatingBar) {
			(top.actualTop || top).RotatingBar.start();
		}
	},
	rightIndexs : {
		'restore' : 2,
		'delete' : 2 
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '站点'
});

Object.extend(Grid,{
	draggable : false,
	keyD : function(event){//delete
		this._delete(event);
	},
	keyR : function(event){//restore
		this._restore(event);
	},
	keyDelete : function(event){//delete
		this._delete(event);
	},
	//==================内部方法========================//
	_hasRight : function(_sOperation){
		return true;
	},

	_delete : function(event){//Trash
		var pSelectedIds = this.checkRight('delete', '删除');		
		if( pSelectedIds && pSelectedIds.length>0 ){
			PageContext.ObjectMgr["delete"](pSelectedIds.join(','), PageContext.params);
		}
	},
	_restore : function(event){//Trash
		var pSelectedIds = this.checkRight('restore', '还原');			
		if( pSelectedIds && pSelectedIds.length>0 ){
			PageContext.ObjectMgr["restore"](pSelectedIds.join(','), PageContext.params);
		}
	}
});

function trace(){
	this.trace.apply($channelMgr, arguments);
}
