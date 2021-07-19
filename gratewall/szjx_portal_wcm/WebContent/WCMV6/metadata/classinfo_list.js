Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_ClassInfo',
	ObjectMethodName : 'jQueryClassInfos',
	AbstractParams : {
		orderBy : 'crtime desc'
	},//服务所需的参数

	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : ClassInfoMgr,

	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType	: 'ClassInfo'
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '分类法'
});

Object.extend(Grid,{
	draggable : false,
	keyD : function(event){//Trash
		this._delete(event);
	},
	keyDelete : function(event){//Trash
		this._delete(event);
	},
	keyN : function(event){//Edit
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("add")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		PageContext.ObjectMgr.add(0, PageContext.params);		
	},
	keyE : function(event){//Edit
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("edit")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length == 1 ){
			PageContext.ObjectMgr.edit(pSelectedIds[0], PageContext.params);
		}
	},
	
	//==================内部方法========================//
	_hasRight : function(_sOperation){
		return true;
	},

	_delete : function(event){//Trash
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("delete")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length>0 ){
			PageContext.ObjectMgr["delete"](pSelectedIds.join(','), PageContext.params);
		}
	},

	//使用传入的权限
	_getRight : function(){
		//return getParameter("RightValue");
		return PageContext.params["RightValue"];
	}
});

Event.observe(window, 'load', function(){
	var arQueryFields = [
		{name: 'queryName', desc: '分类法名称', type: 'string'},
		{name: 'queryDesc', desc: '分类法描述', type: 'string'},
		{name: 'CrUser', desc: '创建者', type: 'string'},
		{name: 'queryId', desc: '分类法ID', type: 'int'}
	];
	SimpleQuery.register('query_box', arQueryFields, function(_params){
		Object.extend(PageContext.params, _params);
		PageContext.RefreshList();
	}, true);
});