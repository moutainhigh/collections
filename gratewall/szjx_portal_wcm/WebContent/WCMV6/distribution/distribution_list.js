Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_distribution',
	ObjectMethodName : 'query',
	//各个页面同时传递的参数
	AbstractParams : {
			"SelectFields"	: "",
			"FolderType"	: "103",
			//"FolderId"		: getParameter("siteid") || 1,
			//记录一下全局的权限
			"RightValue"	: getParameter("RightValue") || "0000000000000000"
	},//服务所需的参数

	//页面过滤器的相关配置
	PageFilterDisplayNum : 3,
	PageFilters : [
		{Name:'全部服务',Type:0,IsDefault:true},
		{Name:'已启用',Type:1},
		{Name:'未启用',Type:2}
	],

	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : DistributionMgr,
	
	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType	: 'distribution',
	
	_doBeforeRefresh : function(paramsStr){
		var params = paramsStr.toQueryParams();
		Object.extend(PageContext.params,{
			"FolderId":params["siteid"]
		});
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '分发点'
});

Object.extend(Grid,{
	/*键盘操作的特殊实现，规则为Key+键*/ 
/*
	keyY : function(event){
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("preview")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length>0 ){			
			PageContext.ObjectMgr.preview(pSelectedIds.join(','), PageContext.params);
		}
	},
*/
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
	
	//回车键
	keyReturn : function(event){//Detail
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("detail")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length == 1 ){
			PageContext.ObjectMgr.detail(pSelectedIds[0], PageContext.params);
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
