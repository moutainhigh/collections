//元数据操作信息和Mgr定义
Ext.ns('wcm.domain.MetaDBFieldMgr');
(function(){
	var m_oMgr = wcm.domain.MetaDBFieldMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function addEdit(_id,event){
		var url = 'metadbfield/fieldinfo_add_edit.jsp?objectId=' + _id;
		var sTilte = null;
		if(_id == 0){
			sTitle = "新建元数据字段"+"<span style='font-size:12px;'>"+String.format("--新建第<font color=\'blue\'>[1]</font>个")+"</span>";
		}else{
			sTitle =  wcm.LANG.METADBFIELD_43 || "修改元数据字段";			
		}
		url += "&tableinfoid="+event.getContext().params["TABLEINFOID"];

		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + url,
			title : sTitle,
			callback : function(){				
				CMSObj[_id>0 ? 'afteredit' : 'afteradd'](event)();				
			}
		});			
	}
	Ext.apply(wcm.domain.MetaDBFieldMgr, {
		add : function(event){
			addEdit(0,event);
		},
		edit : function(event){			
			addEdit(event.getObj().getId(),event);		
		},
		'delete' : function(event){							
			var sId = event.getObjs().getIds();
			var nCount = sId.length;
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var msg =  String.format("确实要将这{0}个元数据字段删除吗?",sHint);
			Ext.Msg.confirm(msg ,{
				 yes : function(){
					getHelper().call("wcm61_metadbfield","deleteDBFieldInfo",{"ObjectIds":sId},true, function(){
					event.getObjs()["afterdelete"]()});
				},
				no : function(){}
				});		
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.MetaDBFieldMgr;
	var reg = wcm.SysOpers.register;	
	reg({
		key : 'edit',
		type : 'MetaDBField',
		desc : '修改这个字段',
		title : '修改这个字段...',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['edit'],
		quickKey : ['E']
	});
	reg({
		key : 'delete',
		type : 'MetaDBField',
		desc : '删除这个字段',
		title : '删除这个字段...',
		rightIndex : -1,
		order : 3,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'MetaDBFieldInRoot',
		desc : '新建一个字段',
		title : '新建一个字段...',
		rightIndex : -1,
		order : 4,
		fn : pageObjMgr['add'],
		quickKey : ['N']
	});
	reg({
		key : 'delete',
		type : 'MetaDBFields',
		desc : '删除这些字段',
		title : '删除这些字段...',
		rightIndex : -1,
		order : 5,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
})();