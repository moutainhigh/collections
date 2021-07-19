//TO Extend
Object.extend(PageContext,{
	enableAttrPanel : false,//能否激活属性面板
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_documentBak',
	ObjectMethodName : 'query',
	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : $chnlDocMgr,
	AbstractParams : {
		"FieldsToHTML" : "DocTitle",
		"DateTimeFormat" : "MM-dd HH:mm"
	},
	_doBeforeRefresh : function(_params){
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '文档版本'
});

Object.extend(Grid,{
	draggable : false,/*是否可拖动*/
	_exec : function(_sFunctionType,_sRowId,_eRow,_eSrcElement){
		var sFunctionType = _sFunctionType.toLowerCase();
		var nVersion = parseInt(_sRowId)-1;//显示的时候做了+1操作
		switch(sFunctionType){
			case 'docbak_recover':
				PageContext.ObjectMgr["recoverBak"](nVersion,PageContext.params);
				break;
			case 'docbak_view':
				PageContext.ObjectMgr["viewBak"](nVersion,PageContext.params);
				break;
			case 'docbak_delete':
				PageContext.ObjectMgr["deleteBak"](nVersion,PageContext.params);
		}
		return false;
	},
	/*键盘操作的特殊实现*/
	keyD : function(event){//Trash
		var ObjectIds = this.getRowIds();
		var nVersions = [];
		if(ObjectIds&&ObjectIds.length>0){
			ObjectIds.each(function(_sObjectId){
				nVersions.push(parseInt(_sObjectId)-1);//显示的时候做了+1操作
			});
			PageContext.ObjectMgr.deleteBak(nVersions.join(','),PageContext.params);
		}
		return false;
	},
	keyDelete : function(event){//Trash
		return this.keyD(event);
	}
});