function init(){
	ValidationHelper.addValidListener(function(){
		//按钮有效处理
		wcmXCom.get('btnSave').enable();
	}, "frm");
	ValidationHelper.addInvalidListener(function(){
		//按钮失效处理
		wcmXCom.get('btnSave').disable();
	}, "frm");
	ValidationHelper.initValidation();
}
function checkStyleName(){
	if($('ObjectId').value > 0) return;//因为在编辑的时候，不允许修改英文名称，所以做重名的判断就没必要了
	BasicDataHelper.Call('wcm61_pagestyle', 'checkStyleName', {StyleName : $('StyleName').value, objectId : $('ObjectId').value}, true, 
		function(_transport,_json){
			Element.hide('processbar');
			Element.hide('wcm-shield');
			var bExsit = $a(_json, 'result');
			if(bExsit == 'true'){
				$('StyleName').setAttribute("NameCanUsed", false);
				ValidationHelper.failureRPCCallBack("当前的英文名字已经被使用，请重新输入！");
			}else{
				$('StyleName').setAttribute("NameCanUsed", true);
				ValidationHelper.successRPCCallBack();
			}
		}
	);
}
function checkStyleDesc(){
	BasicDataHelper.Call('wcm61_pagestyle', 'checkStyleDesc', {StyleDesc : $('StyleDesc').value, objectId : $('ObjectId').value}, true, 
		function(_transport,_json){
			var bExsit = $a(_json, 'result');
			if(bExsit == 'true'){
				$('StyleDesc').setAttribute("DescCanUsed", false);
				ValidationHelper.failureRPCCallBack("当前的中文名字已经被使用，请重新输入！");
			}else{
				$('StyleDesc').setAttribute("DescCanUsed", true);
				ValidationHelper.successRPCCallBack();
			}
		}
	);
}
function save(){
	// 数据校验
	if(!ValidationHelper.doValid("frm")){
		ValidationHelper.failureRPCCallBack();
		return false;
	}
	if($('StyleName').getAttribute("NameCanUsed") == 'false'){
		checkStyleName();
		return false;
	}
	if($('StyleDesc').getAttribute("DescCanUsed") == 'false'){
		checkStyleDesc();
		return false;
	}
	if(top.ProcessBar)
		top.ProcessBar.start("新建页面风格！");
	// 发送请求
	BasicDataHelper.Call('wcm61_pagestyle', 'save', 'frm', true, function(_transport,_json){
		if(top.ProcessBar)
			top.ProcessBar.close();
		wcm.CrashBoarder.get(window).notify(_json);
	});
	return false;
}
window.m_cbCfg = {
	btns : [
		{
			text : '确定',
			id : 'btnSave',
			cmd : function(){
				return save();
			}
		},{
			text : '取消',
			extraCls : 'wcm-btn-close'
		}
	]
}