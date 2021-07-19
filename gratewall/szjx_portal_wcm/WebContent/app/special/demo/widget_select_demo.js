/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'demo/query_demo.jsp',//'wcm61_widget',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

//register for list query
wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			cName : sValue
		});
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '资源'
});

//register for toolbar
wcm.Toolbar.register({
	showSelectWidget : function(){
		wcm.CrashBoarder.get('addWidgetToSpecial').show({
			title : '添加资源到专题.',
			src : 'widget/widget_select_list.html',
			width:'900px',
			height:'500px',
			callback : function(params){
				this.close();
				alert("receive the params:\n" + params);
			}
		});		
	}
});

//register for thumb list cmds.
wcm.ThumbList.register({
	edit : function(properties){
		alert('edit:' + properties['id']);
	},
	'export' : function(properties){
		alert('export:' + properties['id']);
	},
	'delete' : function(properties){
		alert('delete:' + properties['id']);
	}
});
