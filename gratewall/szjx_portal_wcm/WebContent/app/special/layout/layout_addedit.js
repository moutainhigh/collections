window.m_cbCfg = {
	btns : [
		{
			text : '确定',
			id:'btnSave',
			cmd : function(){
				return save();
			}
		},
		{
			extraCls : 'wcm-btn-close',
			text : '取消'
		}
	]
};		

/*
*  验证布局比例值是否符合基本要求
*	1. 百分比，总和不能超过100
*	2. 固定比，总和不能超过5000（防止保存时服务器端报错）
*	3. 比例值中只能有一个自适应值
*/
function validateRatio(ratio,ratiotype){
	var ratios = ratio.split(":");
	var values=0;
	var message="";
	var vcharNum=0;
	// 计算总数
	for (var i = 0; i <ratios.length; i++){
		if(ratios[i]>0)
			values+=parseInt(ratios[i]);
		else{// 不是数字
			vcharNum++;
			continue;
		}
	}
	// 自适应列多于一个
	if(vcharNum>1)
		message = "布局比例最多只能有一列为自适应列！";
	else if(ratiotype==RATIO_TYPE_FIXED&& values>5000){//固定比
		message = "固定比总和大于5000，不符合要求！";
	}else if(ratiotype==RATIO_TYPE_PERCENTAGE&& values>100){//百分比
		message = "百分比总和大于100，不符合要求！";
	}
	if(message.length>0){
		Ext.Msg.alert(message);
		return false;
	}
	return true;
}

// 获取输入的比例值
function getRatioValue(){
	var value=[];
	var td = $("ratiovalue");
	var inputElems = td.getElementsByTagName("input");
	for(var i=0,j=0;i<inputElems.length;i++){
		if(!inputElems[i])
			continue;
		value[j]=inputElems[i].value;
		j++;
	}
	return value.join(":");
}
// 改变比例类型
function changeRatioType(_value){
	if(_value==RATIO_TYPE_FIXED){
		ratioText=" px ";
	}else{
		ratioText=" % ";
	}
	//changeColumns($("Columns").value);
	changeRatioTypeDoWith($("Columns").value);
}
// 保存布局
function save(){
	//保存之前去掉隐藏的input元素
	//获取所有的布局值输入框
	var allInputs = $("ratiovalue").getElementsByTagName("INPUT");
	for(var i=0;i<allInputs.length;i++){
		if(Element.getStyle(allInputs[i],"display")=="none")
			Element.remove(allInputs[i]);
	}
	//校验
	if(!ValidationHelper.doValid('postform')){
		return false;
	}
	// 获取参数
	var nRatioType= $("RatioType").value;
	var sRatio = getRatioValue();
	var sName = $("LayoutName").value.trim();
	// 判断布局比例值是否符合逻辑
	if(!validateRatio(sRatio,nRatioType))
		return false;
	// 发送参数
	var option={
		ObjectId:nLayoutId,
		LayoutName : sName,
		Columns: $("Columns").value,
		RatioType:nRatioType,
		Ratio: sRatio
	}

	//看是否已修改
	if(nRatioType==RatioType && ratio==sRatio && sName==Name){
		return true;
	}

	// 发送服务
	BasicDataHelper.Call('wcm61_layout', 'save',option, false, function(oTransport, oJson){
			var cbSelf = wcm.CrashBoarder.get(window);
			cbSelf.notify(true);
	});
	return false;
}
// 改变布局的列数
function changeColumns(_value){
		//获取所有的布局值输入框
		var allInputs = $("ratiovalue").getElementsByTagName("INPUT");
		for(var i=0;i<allInputs.length;i++){
			if(i<_value){
				Element.next(allInputs[i]).style.display="";
				allInputs[i].style.display="";
			}else{
				Element.next(allInputs[i]).style.display="none";
				allInputs[i].style.display="none";
			}
		}
		if(_value>allInputs.length){
			var html =""
			//需要增加一个input元素
			for(var i=0;i<_value-allInputs.length;i++){
				html+="<input name='' validation='type:string,format:/^[1-9]\\d*$|^\\*$/,warning:布局比例不符合要求,required:1,desc:布局比例' value='' onchange=changeRatioValue(this)  /> <span class='ratiotext'>"+ratioText+"</span>"
			}
			$("ratiovalue").innerHTML =$("ratiovalue").innerHTML+html;
		}
}
//初始化列
function initColumns(_value){
		var html="";
		ratioValues = ratio.split(":");
		for(var i=0;i<_value;i++){
			html+="<input name='' validation='type:string,format:/^[1-9]\\d*$|^\\*$/,warning:布局比例不符合要求,required:1,desc:布局比例' value='"+(ratioValues[i]?ratioValues[i]:"")+"'onchange=changeRatioValue(this) /> <span class='ratiotext'>"+ratioText+"</span>"
		}
		$("ratiovalue").innerHTML = html;
}

// 改变布局的比例类型时进行换算
// 宽度以950px进行计算
function changeRatioTypeDoWith(_value){
		//获取所有的布局值输入框
		var allInputs = $("ratiovalue").getElementsByTagName("INPUT");
		// 如果还没有，初始化一遍
		if(allInputs.length==0){
			initColumns(_value);// 添加初始化元素
			// 初始化换算后的值
			for(var i=0;i<allInputs.length;i++){
				if(allInputs[i].value=="*" || allInputs[i].value.length==0)
					continue;
				// 如果是百分比
				if($("RatioType").value==RATIO_TYPE_PERCENTAGE){
					allInputs[i].setAttribute("_value",parseInt(allInputs[i].value*pageWidth/100));
					allInputs[i].setAttribute("_type",RATIO_TYPE_PERCENTAGE);
				}
				// 如果是固定比
				if($("RatioType").value==RATIO_TYPE_FIXED){
					allInputs[i].setAttribute("_value",parseInt(100*allInputs[i].value/pageWidth));
					allInputs[i].setAttribute("_type",RATIO_TYPE_FIXED);
				}
			}
		}else{//否则进行数值替换
			for(var i=0;i<allInputs.length;i++){
				Element.next(allInputs[i]).innerHTML=ratioText;
				var sValue  = allInputs[i].value;
				if(sValue=="*" || sValue.length==0)
					continue;
				if(!allInputs[i].getAttribute("_value"))
					continue;
				if(allInputs[i].getAttribute("_type") && allInputs[i].getAttribute("_type")==$("RatioType").value)
					continue;
				allInputs[i].value =allInputs[i].getAttribute("_value");
				allInputs[i].setAttribute("_value",sValue);
				allInputs[i].setAttribute("_type",$("RatioType").value);
			}
		}
}
// 初始化
Event.observe(window,'load',function(){
		init();
});

function init(){
	pageWidth=pageWidth>0?pageWidth:950;
	//ValidationHelper.initValidation();
	$("RatioType").value=RatioType;
	$("Columns").value=Column;
	if($("LayoutName"))
		$("LayoutName").value=Name;
	changeRatioType($("RatioType").value);
}

// 修改布局时获取布局修改后的HTML内容
function onOk(){
	//保存之前去掉隐藏的input元素
	//获取所有的布局值输入框
	var allInputs = $("ratiovalue").getElementsByTagName("INPUT");
	for(var i=0;i<allInputs.length;i++){
		if(Element.getStyle(allInputs[i],"display")=="none")
			Element.remove(allInputs[i]);
	}
		//校验
	if(!ValidationHelper.doValid('postform')){
		return false;
	}
	//看是否已修改
	var nRatioType= $("RatioType").value;
	var sRatio = getRatioValue();
	if(nRatioType==RatioType &&　ratio==sRatio)
		return true;
	// 判断布局比例值是否符合逻辑
	if(!validateRatio(sRatio,nRatioType))
		return false;
	// 发送参数
	var option={
		RatioType:nRatioType,
		Columns: $("Columns").value,
		Ratio: sRatio
	}

	// 发送服务
	BasicDataHelper.Call('wcm61_layout', 'getLayoutHtml',option, false, function(oTransport, oJson){
			var cbSelf = wcm.CrashBoarder.get(window);
			cbSelf.notify({content:oTransport.responseText});
			cbSelf.close();
	});
	return false;
}

function changeRatioValue(el){
	if(el.value.length==0 || el.value=="*")
		el.setAttribute("_value","");
	// 如果是百分比
	if($("RatioType").value==RATIO_TYPE_PERCENTAGE){
		el.setAttribute("_type",RATIO_TYPE_PERCENTAGE);
		try{
			el.setAttribute("_value",parseInt(el.value*pageWidth/100));
		}catch(e){
			el.setAttribute("_value","");
		}
	}
	// 如果是固定比
	if($("RatioType").value==RATIO_TYPE_FIXED){
		el.setAttribute("_type",RATIO_TYPE_FIXED);
		try{
			el.setAttribute("_value",parseInt(100*el.value/pageWidth));
		}catch(e){
			el.setAttribute("_value","");
		}
	}
}

//注册校验成功时执行的回调函数
ValidationHelper.addValidListener(function(){
	//按钮有效处理
	wcmXCom.get('btnSave').enable();
}, "ratiovalue");

//注册校验失败时执行的回调函数
ValidationHelper.addInvalidListener(function(){
	//按钮失效处理
	wcmXCom.get('btnSave').disable();
}, "ratiovalue");
//初始化页面中需要校验的元素
ValidationHelper.initValidation();

Event.observe(window, 'unload', function(){
	// 解锁
	if(window.nLayoutId != 0){
		LockerUtil.unlock(nLayoutId, nObjType);
	}
});