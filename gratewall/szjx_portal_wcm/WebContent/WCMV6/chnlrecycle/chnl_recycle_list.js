Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_channel',
	ObjectMethodName : 'getRecycleChannels',
	AbstractParams : {
			'SelectFields' : '',
			//记录一下全局的权限
			'RightValue'	: getParameter('RightValue') || '0000000000000000',
			fieldsToHtml: 'chnldesc,chnlname,parent.name'
	},//服务所需的参数

	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : ChnlRecycleMgr,

	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType	: 'chnlrecycle',
	returnBack : function(){
		var oParams = {
			RightValue: getParameter('RightValue')
		}
		var myActualTop = (top.actualTop||top);
		var sSearch = myActualTop.location_search;
		document.location.href = '../channel/channel_thumbs_index.html' + sSearch;
		if((top.actualTop || top).RotatingBar) {
			(top.actualTop || top).RotatingBar.start();
		}
	},
	_doBeforeRefresh : function(){
		var queryStr = $toQueryStr(PageContext.params);
		var sNewParams = 'siteid=' + getParameter('siteid', queryStr) 
			+ '&channelid=' + getParameter('channelid', queryStr) 
			+ '&objectdesc=栏目回收站';

		PageContext.drawLiterator('literator_path', sNewParams);
	},
	rightIndexs : {
		'delete': 12,
		'restore': 12
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '栏目'
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
