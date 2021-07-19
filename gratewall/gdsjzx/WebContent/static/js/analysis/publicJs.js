/* 	 0--市场主体概况  '期末和期末资本金   包括注销吊销...'  
		 1--企业设立登记  // 没有固定指标 
		 2--市场准入和退出   '本期和本期资本金'     
		 3--业务办理情况 */
$(function () {
	var t = "${param.at}";
	//init measure data && set titile
	if("0" == t){//为市场主体概况，否则为设立登记
		document.title = '市场主体概况' + document.title.substr(6, document.title.length);
		$("#busType").append("<option selected value='001001'>期末实有户数</option>");
		$("#busType").append("<option value='002001'>期末实有注册资本(金)(万元)</option>");
	}else if("1" == t){
		$("#busTypeLabe").hide();
		$("#busType").hide();
	}else if("2" == t){
		$("#busType").append("<option selected value='001002'>本期登记户数</option>");
		$("#busType").append("<option value='002005'>本期登记注册资本(金)(万元)</option>");
	}else{
	
	}
	initMeasureData();
	
	
	//init measure data
	
	//加载企业类型
	initEnterpriseType();
	//initIndusCodes();
	$("#selData").bind("click", initCharts);
	//$("#selData_2").bind("click", initSecCharts);
	//$("#busType").bind("change",changeBusType);
	//$("#chartChange").bind("click", showFir);
	$("#selData_2").hide();
	$("#chartChange").hide();
	 
	
});
