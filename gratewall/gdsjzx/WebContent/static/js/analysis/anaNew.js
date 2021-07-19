//是否为变更
$(function () {
	
	//区域1
	initMultSelect('regorg_p');
	initMultSelect('regorg'); 
	//行业
	initMultSelect('indus_p1'); 
	initMultSelect('indus_p2'); 
	initMultSelect('indus'); 
	//企业
	initMultSelect('ent'); 
	//资金规模
	initMultSelect('dom_p'); 
	initMultSelect('dom');
	//经济性质
	initMultSelect('econ_p'); 
	initMultSelect('econ');
	//组织形式
	initMultSelect('organ_p'); 
	initMultSelect('organ');
});
//组织形式
function getOrganData(request){
	var t = "";
	$("#organ_p").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	if(t.length > 2){//说明第一级有多个选择
		request.organ_p = t;
		return ;
	}
	t = "";
	//$("#sourceCode option:selected").each(function(index,element){ 
	$("#organ").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	}); 
	if(t != "")
		request.organ = t;
}
//经济性质
function getEconData(request){
	var t = "";
	$("#econ_p").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	if(t.length > 2){//说明第一级有多个选择
		request.econ_p = t;
		return ;
	}
	t = "";
	//$("#sourceCode option:selected").each(function(index,element){ 
	$("#econ").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	}); 
	if(t != "")
		request.econ = t;
}
//资金规模
function getDomData(request){
	var t = "";
	$("#dom_p").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	if(t.length > 4){//说明第一级有多个选择
		request.dom_p = t;
		return ;
	}
	t = "";
	//$("#sourceCode option:selected").each(function(index,element){ 
	$("#dom").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	}); 
	if(t != "")
		request.dom = t;
}
//企业
function getEntData(request){
	var t = "";
	//$("#sourceCode option:selected").each(function(index,element){ 
	$("#ent").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	}); 
	if(t != "")
		request.ent = t;
}
//初始化企业请求数据
//初始化行业请求数据
function getIndusData(request){
	var t = "";
	$("#indus_p1").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	if(t.length > 2){//说明第一级有多个选择
		request.indus_p1 = t;
		return ;
	}
	t = "";
	$("#indus_p2").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	if(t.length > 1){//说明第二级有多个选择
		request.indus_p2 = t;
		return ;
	}
	t = "";
	$("#indus").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	if(t != "")
		request.indus = t;
}
//初始化区域/单位请求数据
function getRegorgData(request){
	var t = "";
	$("#regorg_p").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	if(t.length > 4){//说明第一级有多个选择
		request.regorg_p = t;
		return ;
	}
	t = "";
	//$("#sourceCode option:selected").each(function(index,element){ 
	$("#regorg").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	}); 
	if(t != "")
		request.regorg = t;
}
//登记机关列表

function initMultSelect(name,callback) {
	//尝试清除如果已经存在的
	//alert(name + ":" + $("#" + name).next().attr('class'));
	if($("#" + name).next().attr('class') == 'multiSelectOptions'){
		$("#" + name).next().remove();
	}
	$("#" + name).multiSelect({
		selectAll: false,
		oneOrMoreSelected: '*',
		selectAllText: '全选',
		noneSelected: '请选择'
	}, function(data){
		if(callback)
			callback(data);
		if($("#" + name).length > 0){
			$("#errRed").empty();
			
			}
		else{$("#errRed").text('请选择')};
			//addCss();
	})
}