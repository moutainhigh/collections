/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'resource_query.jsp',//'wcm61_widget',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

//register for list query
/*wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			cName : sValue
		});
	}
});*/


