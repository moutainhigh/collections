/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'resource_data_query.jsp',
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
Event.observe(window, 'load', function(){
	new wcm.Button({
		id : 'btn-id-1',
		text : '新建字段',
		tip : '新建字段',
		renderTo : 'new_field',
		cmd : function(){
			this.disable();
		}
	}).show();
	new wcm.Button({
		id : 'btn-id-2',
		text : '批量设置字段属性',
		tip : '批量设置字段属性',
		renderTo : 'set_field_attr',
		cmd : function(){
			this.disable();
		}
	}).show();
	new wcm.Button({
		id : 'btn-id-3',
		text : '删除',
		tip : '删除',
		renderTo : 'delete_field',
		cmd : function(){
			this.disable();
		}
	}).show();
	new wcm.ButtonWithMore({
		id : 'btn-id-4',
		text : '修改资源结构名称',
		tip : '修改资源结构名称',
		renderTo : 'modify_view_name',
		cmd : function(){
			this.disable();
		},
		more : true,
		moreOpers : [
			{
				id : 'btn-id-5',
				text : '修改资源结构布局',
				tip : '修改资源结构布局',
				renderTo : '',
				cmd : function(){
					alert(1);
				}
			},
			{
				id : 'btn-id-6',
				text : '修改资源结构分类',
				tip : '修改资源结构分类',
				renderTo : '',
				cmd : function(){
					alert(2);
				}
			}
		]
	}).show();
});


