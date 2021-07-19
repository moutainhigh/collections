ValidationHelper.validByValidation = function(sValidation, sValue){
	if(!sValidation) return true;
	var validation = eval('({' + sValidation + '})');

	//valid required.
	if(validation["required"] == 1 && sValue.length <= 0){
		Ext.Msg.alert((validation["desc"] || "") + "不能为空");
		return false;
	}

	//valid max length.
	var maxLen = validation['max_len'] || 0;
	if(maxLen > 0 && sValue.length > maxLen){
		Ext.Msg.alert(String.fromat("{0}大于最大长度[{1}]",validation["desc"] || "",maxLen));
		return false;
	}	
	return true;
};

function validData(){	
	var components = com.trs.ui.ComponentMgr.getAllComponents();
	for (var i = 0; i < components.length; i++){
		var validation = components[i].getProperty('validation');
		if(!validation) continue;
		if(!ValidationHelper.validByValidation(validation, components[i].getValue())) return false;
	}
	return true;
};

/*内部页面的保存措施，
*这个方法名必须为makeData，
*参数fn是一个函数
*makeData里面要做的只是校验，和自身组装数据的操作
*组装好数据后调用fn方法，参数是容器的id，或者是一个json，如{name:value}
*/
function makeData(fn){
	com.trs.ui.XAppendixMgr.upload(
		function(){
			//校验
			if(!validData()){
				return false;
			}
			//调用父页面的保存
			fn('data');
		},
		function(){
			Ext.Msg.alert(arguments[0]);
		}
	);
}



/*
*初始化校验处理
*需要在父页面按钮画完之后（init里面做），本页面也需要加载完
*/
function initValidation(){
	ValidationHelper.addValidListener(function(){
		wcmXCom.get('valueSetBtn').enable();
	});
	ValidationHelper.addInvalidListener(function(){
		wcmXCom.get('valueSetBtn').disable();
	});
	ValidationHelper.initValidation();
};

document.getElementsByClassName = function(cls, p) {
	if(p && p.getElementsByClassName) return p.getElementsByClassName(cls);
	var arr = ($(p) || document.body).getElementsByTagName('*');
	var rst = [];
	var regExp = new RegExp("(^|\\s)" + cls + "(\\s|$)");
	for(var i=0,n=arr.length;i<n;i++){
		if (arr[i].className.match(regExp))
			rst.push(arr[i]);
	}
	return rst;
};


//处理资源转换时资源变量值的初始化
Event.observe(window, 'load', function(){
	if(!window.$OldWidgetInstParameters) return;
	if($OldWidgetInstParameters.length <= 0) return;

	for(var index = 0; index < $OldWidgetInstParameters.length; index++){
		var parameter = $OldWidgetInstParameters[index];
		if(!parameter) continue;
		var field = com.trs.ui.ComponentMgr.get(parameter['name']);
		try{
			if(field) field.setValue(parameter['value']);
		}catch(error){
			//just skip it.
		}
		
	}
});