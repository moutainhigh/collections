/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_special',
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
			SPECIALNAME : sValue
		});
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '专题'
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
	var elements = document.getElementsByName("specialName");	
	if(elements.length <=0){
		return;
	}else{
		var hasChecked = false;
		var specialId = 0;
		for(var k=0; k < elements.length; k++){
			if(elements[k].checked){
				hasChecked = true;
				specialId = elements[k].id;
				break;
			}

		}
		if(!hasChecked){
			Ext.Msg.alert("请先选择要类似创建的专题！");
			return;
		}
	}
	try{
		if(top.ProcessBar) top.ProcessBar.start("类似创建专题！");
	}catch(error){}
		
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var oParams = {"ObjectId":specialId};
	oHelper.Call("wcm61_special", "createFrom", oParams, false, function(_trans, _json){
		try{
			if(top.ProcessBar) top.ProcessBar.close();
		}catch(error){}

		var c_bWin = wcm.CrashBoarder.get(window);
		c_bWin.notify();	
	});
}