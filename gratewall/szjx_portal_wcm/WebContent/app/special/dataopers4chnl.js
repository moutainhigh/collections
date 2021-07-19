(function(){
var mgr = wcm.special.data.OperMgr;
mgr.addOpers([{
	type:"CHNL",
	desc:"新建子栏目",
	oprKey:"Channel_new",
	iconCls:"create",
	order:1,
	cmd:function(context){
		var ObjId = context.property.getAttribute("objId");
		var parentId = context.property.getAttribute("ParentId");//context.property.parentNode.getAttribute("objId");
		var oParams = {
			objectId : ObjId,
			parentId : parentId,
			bDataOper4Chnl : true
		};
		chnlAddEdit(oParams, context);
	}
},{
	type:"CHNL",
	desc:"修改栏目",
	oprKey:"Channel_edit",
	iconCls:"edit",
	visible:true,
	order:2,
	cmd:function(context){
		var objectId = context.property.getAttribute("objId");
		var parentId = context.property.getAttribute("ParentId");;
		var oParams = {
			objectId : objectId,
			parentId : parentId,
			bDataOper4Chnl : true
		};
		chnlAddEdit(oParams, context);
	}
},{
	type:"CHNL",
	desc:"删除栏目",
	oprKey:"Channel_delete",
	iconCls:"delete",
	order:3,
	cmd:function(context){
		var objId = context.property.getAttribute("objId");
		var params = {
			ObjectIds: objId,
			Drop:false
		}
		Ext.Msg.confirm('确定要删除这个栏目吗？', {
			yes : function(){
				BasicDataHelper.call("wcm61_channel",'delete',params,true,function(transport, json){
					var widget = wcm.special.widget.InstanceMgr.find(context.property);
					if(widget) wcm.special.widget.InstanceMgr.refresh(widget);
				})
			}
		});;
	}
},{
	type:"CHNL",
	desc:"导入栏目",
	oprKey:"Channel_import",
	iconCls:"import",
	order:4,
	cmd:function(context){
		var chnlId = context.property.getAttribute("objId");
		var chnlType = context.property.getAttribute("objType");
		var oParams = {
			parentid : chnlId,
			objecttype : chnlType
		};
		importChnl(oParams, context);
	}
},{
	type:"CHNL",
	desc:"导出栏目",
	oprKey:"Channel_export",
	iconCls:"export",
	order:5,
	cmd:function(context){
		var chnlId = context.property.getAttribute("objId");
		var oParams = {
			ObjectIds : chnlId
		};
		wcm.CrashBoarder.get('chnl_export').show({
			title : '导出栏目',
			src : 'channel/channel_export.html?' + $toQueryStr(oParams),
			width:'320px',
			height:'100px',
			maskable:true,
			callback : function(params){
				//导出成功的信息
			}
		});
	}
},{
	type:"CHNLINCHNL",
	desc:"新建栏目",
	oprKey:"ChnlInChnl_new",
	iconCls:"create",
	order:1,
	cmd:function(context){
		var objId = context.property.getAttribute("objId");
		var oParams = {
			objectId : objId,
			parentId : objId,
			bDataOper4Chnl : true
		};
		chnlAddEdit(oParams, context);
	}
},{
	type:"CHNLINCHNL",
	desc:"导入栏目",
	oprKey:"ChnlInChnl_import",
	iconCls:"import",
	order:4,
	cmd:function(context){
		var chnlId = context.property.getAttribute("objId");
		var chnlType = context.property.getAttribute("objType");
		var oParams = {
			parentid : chnlId,
			objecttype : chnlType
		};
		importChnl(oParams, context);
	}
}]);
})();

function chnlAddEdit(_oParams, _oContext){
	var cbr = wcm.CrashBoarder.get('chnl_edit');
	cbr.show({
		title : '新建/修改栏目',
		src : 'channel/channel_addedit.jsp?' + $toQueryStr(_oParams),
		width : '750px',
		height :'300px',
		maskable:true,
		callback : function(params){
		}
	});
	cbr.getCrashBoard().on('beforeclose', function(){
		cbr.getCrashBoard().un('beforeclose', arguments.callee);
		var widget = wcm.special.widget.InstanceMgr.find(_oContext.property);
		if(widget) wcm.special.widget.InstanceMgr.refresh(widget);
	});
}

function importChnl(_oParams, _oContext){
	wcm.CrashBoarder.get('chnl_import').show({
		title : '导入栏目',
		src : 'channel/channel_import.html?' + $toQueryStr(_oParams),
		width:'400px',
		height:'100px',
		maskable:true,
		callback : function(params){
			if(params == 'true'){
				var widget = wcm.special.widget.InstanceMgr.find(_oContext.property);
				if(widget) wcm.special.widget.InstanceMgr.refresh(widget);
				this.close();
			}
		}
	});
}