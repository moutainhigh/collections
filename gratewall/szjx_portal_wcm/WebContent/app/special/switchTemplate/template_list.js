/*-------------中间内容请求的服务相关信息--------------*/
var nChannelId = getParameter('nChannelId');
var nTemplateId = getParameter('TemplateId');
var nSpecialId = getParameter('specialId');
Ext.apply(PageContext, {
	serviceId : 'wcm61_template',
	methodName : 'jQueryForTemplateSelect',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"HostType" : 101,
		"hostId" : nChannelId,
		"ContainsChildren" : false,
		OrderBy : "lastModifiedTime desc",
		"pageSize" : "8",
		"Visual" : 1,
		"TemplateType" : 1
	}
});
PageContext.addListener('afterrender', function(){
	//初始化选中状态
	if(document.getElementById("cbx_" + nTemplateId) != null){
		var dom = document.getElementById("cbx_" + nTemplateId)
		dom.checked = true;
	}
});

var selectTemplateId = nTemplateId;
Event.observe(document, 'click', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	if(dom.name == "RowId"){
		selectTemplateId = dom.value;
	}
});


wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			TEMPNAME : sValue
		});
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '模板'
});

var basicDataHelper = new com.trs.web2frame.BasicDataHelper();
window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG.TRUE || '确定',
			cmd : function(){
				this.hide();
				//debugger
				//var rst = $('grid_checkbox').checked ? {selectedIds: [], selectedNames: []} : buildValues();
				//传递值，发送保存模板请求
				if(selectTemplateId != nTemplateId){
					var params = {
						OBJECTID : nChannelId,
						OBJECTTYPE : 101,
						TEMPLATEID : selectTemplateId,
						TEMPLATETYPE : 1
					}
					basicDataHelper.call('wcm6_template', 'setDefaultTemplate', params, true, function(){
						this.close();
					}.bind(this));
				} else {
					this.close();
				}
				//构造url。
				var url = "design.jsp?specialId=" + nSpecialId + "&templateId=" + selectTemplateId + "&nChannelId="+ nChannelId;
				var rst = selectTemplateId;
				this.notify(url);
				return false;
			}
		},
		{
			extraCls : 'wcm-btn-close',
			text : '关闭'
		}
	]
};

