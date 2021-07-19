/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_layout',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"pageSize" : "10"
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '布局'
});
//register for list query
wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			LayoutName : sValue
		});
	}
});

//register for toolbar
wcm.Toolbar.register({
	// addedit a layout
	add_layout : function(){
		wcm.CrashBoarder.get('layout-addedit').show({
			title : '布局新建/修改页面',
			src : 'layout/layout_addedit.jsp?LayoutId=0',
			maskable:true,
			width:'480px',
			height:'240px',
			callback : function(params){
				// refresh layout list 
				refresh();
				this.close();
			}
		});		
	},
	layout_select: function(){
		wcm.CrashBoarder.get('layout-query').show({
			title : '布局选择页面',
			src : 'layout/layout_select.jsp',
			maskable:true,
			width:'570px',
			height:'320px',
			callback : function(params){
				//this.close();
				alert("receive the params:\n" + params);
			}
		});		
	},
	'delete' : function(){
		var selectedIds = wcm.ThumbList.getSelectedThumbItemIds().join(",")
		if(selectedIds==null ||selectedIds.length==0){
			Ext.Msg.alert("请选择要删除的布局");
			return;
		}
		Ext.Msg.confirm("确定要删除选中的布局吗？",function(){
			BasicDataHelper.Call('wcm61_layout','delete',{ObjectIds : selectedIds},true,function(_trans, _json){
				// refresh list-data
				refresh();
		});
	});
	}
});
// refresh list-data
function refresh(){
	PageContext.loadList(PageContext.params);
}
//register for thumb list cmds.
wcm.ThumbList.register({
	edit : function(properties){
		wcm.CrashBoarder.get('layout-addedit').show({
			title : '布局新建/修改页面',
			src : 'layout/layout_addedit.jsp?LayoutId='+properties['id'],
			maskable:true,
			width:'480px',
			height:'240px',
			callback : function(params){
				// refresh layout list 
				refresh();
				this.close();
			}
		});		
	},
	'delete' : function(properties){
		var ids = properties['id'];
		Ext.Msg.confirm("确定要删除该布局吗？",function(){
			BasicDataHelper.Call('wcm61_layout','delete',{ObjectIds : ids},true,function(_trans, _json){
				// refresh  list-data
				refresh();
			});
		});
	}
});

// add new styleSheet
function updateDynamicPageStyle(sCssText){
	if($('dynamicStyle').styleSheet)
		$('dynamicStyle').styleSheet.cssText = sCssText;
	else
		$('dynamicStyle').innerHTML=sCssText;
}
/** 权限调整，当没有权限时将按钮置灰色**/
PageContext.addListener('afterrender', function(transport, json){
	var bCanAdd = transport.getResponseHeader("bCanAdd");
	if(bCanAdd == 'false'){
		Element.addClassName($('add_layout'), "disableCls");
	}
	var bCanDelete = transport.getResponseHeader("bCanDelete");
	if(bCanDelete == 'false'){
		Element.addClassName($('delete'), "disableCls");
	}
});

