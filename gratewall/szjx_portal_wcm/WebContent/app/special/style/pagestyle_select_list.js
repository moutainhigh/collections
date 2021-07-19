/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_pagestyle',
	methodName : 'tQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		OrderBy : "CrTime desc",
		"pageSize" : "6"
	}
});


wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			STYLEDESC : sValue
		});
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '页面风格'
});

window.m_cbCfg = {
	btns : [
		{
			text : '确定',
			cmd : function(){
				ok();
				return false;
			}
		},
		{
			extraCls : 'wcm-btn-close',
			text : '取消'
		}
	]
};
function ok(){
	var elements = document.getElementsByName("styleName");	
	var styleId = getParameter("selectedId");
	var cssFile = '';
	var styleName = '';

	if(elements.length <=0){
		return;
	}else{
		var hasChecked = false;
		for(var k=0; k < elements.length; k++){
			if(elements[k].checked){
				hasChecked = true;
				styleName = elements[k].value;
				styleId = elements[k].id;
				cssFile = elements[k].getAttribute('cssFile', 2);
				break;
			}

		}
		if(styleId == 0 && !hasChecked){
			Ext.Msg.alert("请选择专题页面风格！");
			return;
		}
	}
	var c_bWin = wcm.CrashBoarder.get(window);
	c_bWin.notify({"StyleId":styleId,"StyleName":styleName,"CssFile":cssFile});	
}

var markId = 0;
function mark(_markId){
	markId = _markId;
}

PageContext.addListener('afterrender', function(){
	var elements = document.getElementsByName("styleName");
	var selectedId = markId || getParameter("selectedId");
	for(var i=0,j=elements.length;i<j;i++){
		if(elements[i].id == selectedId){
			elements[i].checked = true;
			break;
		}
		if(elements[i].value == selectedId){
			elements[i].checked = true;
			break;
		}
	}
});