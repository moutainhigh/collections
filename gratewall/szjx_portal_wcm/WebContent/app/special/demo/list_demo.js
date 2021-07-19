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
	showCrashBoard : function(){
		wcm.CrashBoarder.get('crash-borad').show({
			title : 'crash-board demo page.',
			src : 'demo/crashboard-demo.html',
			width:'700px',
			height:'300px',
			callback : function(params){
				this.close();
				alert("receive the params:\n" + params);
			}
		});		
	},
	showDialog : function(){
		wcm.CrashBoarder.get('dialog').show({
			title : 'crash-board demo page.',
			src : 'demo/dialog-demo.html',
			width:'700px',
			height:'300px',
			callback : function(params){
				this.close();
				alert("receive the params:\n" + params);
			}
		});		
	},
	showTabPanel : function(){
		wcm.CrashBoarder.get('tabpanel').show({
			title : 'crash-board demo page.',
			src : 'demo/tabpanel-demo.html',
			width:'700px',
			height:'300px',
			callback : function(params){
				this.close();
				alert("receive the params:\n" + params);
			}
		});		
	},
	'delete' : function(){
		alert(wcm.ThumbList.getSelectedThumbItemIds().join(","));	
		wcm.CrashBoarder.get('validator').show({
			title : 'crash-board demo page.',
			src : 'demo/validator-demo.html',
			width:'700px',
			height:'300px',
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
