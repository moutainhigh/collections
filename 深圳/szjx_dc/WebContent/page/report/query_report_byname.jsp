<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<style type="text/css">
td {
	text-align: center;
}

.jazz-pagearea {
	height: 0px;
}
</style>

<script type="text/javascript" charset="UTF-8">
var reportNames='${sessionScope.reportNames}';
$(function () {
	//var reportName = "${param.reportName}";
	//var reportType = "${param.reportType}"; // 1 - 工商制式报表 2-质检报表 3-食药报表 4-综合12张
	var reportName = getUrlParam("reportName");
	//console.log(reportName);
	if (reportName.indexOf("%") != -1) {
		reportName=decodeURIComponent(reportName);
	}
	//console.log(reportName);
	//console.log(decodeURIComponent(reportName));
	
	
	alert(reportName)
	var reportType = getUrlParam("reportType");
	$('div[name="reportname"]').hiddenfield('setValue', reportName);
	$('div[name="reporttype"]').hiddenfield('setValue', reportType);
	queryUrl();
});

	function queryUrl() {
		$('#reportListGrid').gridpanel('option', 'dataurl',
				rootPath + '/cognosController/queryReport.do');
		$('#reportListGrid').gridpanel('query', [ 'formpanel' ]);
	}

	function reset() {
		$("#formpanel").formpanel('reset');
	}

	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		
		var r = window.location.search.substr(1).match(reg); //匹配目标参数
		if (r != null)
			return unescape(r[2]);
		return null; //返回参数值
	}
	
	function getDistrict(code){
		if(!code)
			return "";
		code = parseInt(code);
		switch(code){
			case 1      : return "深圳市";
			case 440303 : return "罗湖区";
			case 440304 : return "福田区";
			case 440305 : return "南山区";
			case 440306 : return "宝安区";
			case 440307 : return "龙岗区";
			case 440308 : return "盐田区";
			case 440309 : return "光明新区";
			case 440310 : return "坪山新区";
			case 440342 : return "龙华新区";
			case 440343 : return "大鹏新区";
			case 441521 : return "深汕区";
			case 440399 : return "其他";
		}
		return "";
	}

	//数据渲染函数
	function renderdata(event, obj) {
		var cleansession=false;
		var data = obj.data;
		var hunheqx=reportNames.indexOf(data.length==0?"31415":data[0]["reportname"],0)==-1?false:false;
		var zongquyu="${sessionScope.USER_REGION_CODE}";
		
		if("${sessionScope.USER_DOOR_ROLE_CODE}"=="2"){
		for (var i = 0; i < data.length; i++) {
			var isValid = data[i]["isvalid"];
			if (isValid=='0') {
				var htm = '<a href="javascript:void(0);" onclick="report_detials_chakan(\''
					+ data[i]["id"]
					+ '\',\''
					+ data[i]["reportname"]
					+ '\',\''
					+ data[i]["reporttype"] + '\')">' + "查看报表" + '</a>'+'&nbsp'+'&nbsp'+'&nbsp'
					+'<a href="javascript:void(0);" onclick="report_detials(\''
					+ data[i]["id"]
					+ '\',\''
					+ data[i]["reportname"]
					+ '\',\''
					+ data[i]["reporttype"] + '\')">'
					+"修改报表"+'</a>';
			}else{
				var htm = '<a><font color="red">'
					 + "报表已停用" + '</a>';
			}
				data[i]["edit"] = htm;
				data[i]["regcode"] = getDistrict(data[i]["regcode"]);
			//isValid = data[i]["isvalid"];
			/* if(isValid == '0'){ //报表是已启用的
				data[i]["isvalid"] = '<a href="javascript:void(0);" onclick="changeValid(\''+data[i]["id"]+'\','+isValid+')"><font color="green">启用</font></a>';
			}else{//停用中
				data[i]["isvalid"] = '<a href="javascript:void(0);" onclick="changeValid(\''+data[i]["id"]+'\','+isValid+')"><font color="red">停用</font></a>';
			} */
			}
	}else if (hunheqx) {
		for (var i = 0; i < data.length; i++) {
			var isValid = data[i]["isvalid"];
			var quyu=data[i]["regcode"];
			if (quyu==zongquyu) {
				if (isValid=='0') {
					var htm = '<a href="javascript:void(0);" onclick="report_detials_chakan(\''
						+ data[i]["id"]
						+ '\',\''
						+ data[i]["reportname"]
						+ '\',\''
						+ data[i]["reporttype"] + '\')">' + "查看报表" + '</a>'+'&nbsp'+'&nbsp'+'&nbsp'
						+'<a href="javascript:void(0);" onclick="report_detials(\''
						+ data[i]["id"]
						+ '\',\''
						+ data[i]["reportname"]
						+ '\',\''
						+ data[i]["reporttype"] + '\')">'
						+"修改报表"+'</a>';
				}else{
					var htm = '<a><font color="red">'
						 + "报表已停用" + '</a>';
				}
			}else{
				if (isValid=='0') {
					var htm = '<a href="javascript:void(0);" onclick="report_detials_chakan(\''
						+ data[i]["id"]
						+ '\',\''
						+ data[i]["reportname"]
						+ '\',\''
						+ data[i]["reporttype"] + '\')">' + "查看报表" + '</a>';
				}else{
					var htm = '<a><font color="red">'
						 + "报表已停用" + '</a>';
				}
			}
			
			data[i]["edit"] = htm;
			data[i]["regcode"] = getDistrict(data[i]["regcode"]);
		}
		
/* 		$.ajax({  
            type : "POST",  //提交方式  
            url :  rootPath+"/cognosController/removeSessionx.do",//路径  
            success : function(result) {//返回数据根据结果进行相应的处理  
            }  
        });   */
		//cleansession=false;
	}else {
		for (var i = 0; i < data.length; i++) {
			var isValid = data[i]["isvalid"];
			if (isValid=='0') {
				var htm = '<a href="javascript:void(0);" onclick="report_detials_chakan(\''
					+ data[i]["id"]
					+ '\',\''
					+ data[i]["reportname"]
					+ '\',\''
					+ data[i]["reporttype"] + '\')">' + "查看报表" + '</a>';
			}else{
				var htm = '<a><font color="red">'
					 + "报表已停用" + '</a>';
			}
			data[i]["edit"] = htm;
			data[i]["regcode"] = getDistrict(data[i]["regcode"]);
			
			//isValid = data[i]["isvalid"];
			/* if(isValid == '0'){ //报表是已启用的
				data[i]["isvalid"] = '<a href="javascript:void(0);" onclick="changeValid(\''+data[i]["id"]+'\','+isValid+')"><font color="green">启用</font></a>';
			}else{//停用中
				data[i]["isvalid"] = '<a href="javascript:void(0);" onclick="changeValid(\''+data[i]["id"]+'\','+isValid+')"><font color="red">停用</font></a>';
			} */
		}
	}
		return data;
	}
	
	function changeValid(id, isValid) {
		var msg = null;
		msg = isValid == 0 ? "是否停用?" : "是否启用?";
		if(confirm(msg)){
			$.ajax({
				   type: "POST",
				   url: rootPath+"/cognosController/changeValid.do",
				   data: {
					   id:id,
					   isValid:isValid == 0 ? 1 : 0
				   },
				   success: function(msg){
				     
				       queryUrl();
				   }
		      });
		}
	}
	

	function report_detials(id,name,type){
        var path = rootPath+"/page/report/report_detial.jsp?id="+id;
        if(type != '4'){
        if(name.indexOf("综合") != -1 || name == '内资4表' || 
           name == '个体5表'|| name == '竞争执法2表' || name == '消保6表' ||  name == '广告2表' || name == '广告3表' ||
   name == '商标4表'|| name == '商标评审1表' || name == '法制1表' || name=='科技2表' ||name=='计量2表' ||
           name=='器械注1表'||name.indexOf('器械监管')!=-1||name=='执业药师3表'||(name.indexOf('稽查')!=-1 && name!='稽查2-1表') ||
           name.indexOf('粤食药监')!=-1 || name=='食药基4-3表' ||name=='标准5表' ||name=='药化监管4表' ||(name=='法制2表' && type=='3')) {
    path += "&isFixed=1";
        
        }
        if((name.indexOf("内资") != -1 && name != '内资4表') ||
            name.indexOf("外资") != -1 || name.indexOf("个体") != -1 ||
            name.indexOf("农合") != -1 || (name.indexOf("竞争执法") != -1 && name != '竞争执法2表' ) || 
            name.indexOf("食品") != -1 ||name.indexOf("消保") !=-1||
            name.indexOf("无照经营") != -1 || name.indexOf("市场") != -1 || name == '广告1表' || name == '商标1表' ||
            name.indexOf('人事')!=-1 || name == '商标2表' || name == '商标3表' || name == '商标评审2表' || name == '法制2表' || name == '法制3表' ||
            name=='机构1表' || name=='法制4表' || name=='经费2表'||name=='计量1表'||name=='资产1表'||name.indexOf('应急')!=-1||
            name=='特16表'||name=='稽查2-1表') {
    path += "&isNowrap=1";
        
        }
        }else{
            path += "&isNowrap=1";
        }

window.open (path, '_blank' ) ;
}
	
	
	function report_detials_chakan(id,name,type){
        var path = rootPath+"/page/report/report_detial_chakan.jsp?id="+id;
        if(type != '4'){
        if(name.indexOf("综合") != -1 || name == '内资4表' || 
           name == '个体5表'|| name == '竞争执法2表' || name == '消保6表' ||  name == '广告2表' || name == '广告3表' ||
   name == '商标4表'|| name == '商标评审1表' || name == '法制1表' || name=='科技2表' ||name=='计量2表' ||
           name=='器械注1表'||name.indexOf('器械监管')!=-1||name=='执业药师3表'||(name.indexOf('稽查')!=-1 && name!='稽查2-1表') ||
           name.indexOf('粤食药监')!=-1 || name=='食药基4-3表' ||name=='标准5表' ||name=='药化监管4表' ||(name=='法制2表' && type=='3')) {
    path += "&isFixed=1";
        
        }
        if((name.indexOf("内资") != -1 && name != '内资4表') ||
            name.indexOf("外资") != -1 || name.indexOf("个体") != -1 ||
            name.indexOf("农合") != -1 || (name.indexOf("竞争执法") != -1 && name != '竞争执法2表' ) || 
            name.indexOf("食品") != -1 ||name.indexOf("消保") !=-1||
            name.indexOf("无照经营") != -1 || name.indexOf("市场") != -1 || name == '广告1表' || name == '商标1表' ||
            name.indexOf('人事')!=-1 || name == '商标2表' || name == '商标3表' || name == '商标评审2表' || name == '法制2表' || name == '法制3表' ||
            name=='机构1表' || name=='法制4表' || name=='经费2表'||name=='计量1表'||name=='资产1表'||name.indexOf('应急')!=-1||
            name=='特16表'||name=='稽查2-1表') {
    path += "&isNowrap=1";
        
        }
        }else{
            path += "&isNowrap=1";
        }

window.open (path, '_blank' ) ;
}
	


</script>
</head>
<body>


	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="200"
		title="查询条件">
		<div id='reportname' name='reportname' vtype="hiddenfield"
			label="serviceid" labelAlign="right" width="400" labelwidth="130"></div>
		<div id='reporttype' name='reporttype' vtype="hiddenfield"
			label="serviceid" labelAlign="right" width="400" labelwidth="130"></div>
		<!-- <div name='reportname' vtype="comboxfield" label="报表名称"
			labelAlign="right" labelwidth='100px' width="410"
			dataurl='[{"text":"内资1表", "value":"内资1表" },regcode
{"text":"内资2表", "value":"内资2表" },regcode
{"text":"内资3表", "value":"内资3表" },
{"text":"个体1表", "value":"个体1表" },
{"text":"个体3表", "value":"个体3表" },
{"text":"综合1表", "value":"综合1表" },
{"text":"农合1表", "value":"农合1表" },
{"text":"个体4表", "value":"个体4表" },
{"text":"个体2表", "value":"个体2表" },
{"text":"外资3表", "value":"外资3表" },
{"text":"外资2表", "value":"外资2表" },
{"text":"外资1表", "value":"外资1表" }]'></div> -->
		<div name='regcode' vtype="comboxfield" label="区域"
			labelAlign="right" labelwidth='100px' width="410"
			dataurl='[ {"text":"深圳市", "value":"001" },
						{"text":"罗湖区", "value":"440303" },
						{"text":"福田区", "value":"440304" },
						{"text":"南山区", "value":"440305" },
						{"text":"宝安区", "value":"440306" },
						{"text":"龙岗区", "value":"440307" },
						{"text":"盐田区", "value":"440308" },
						{"text":"光明新区", "value":"440309" },
						{"text":"坪山新区", "value":"440310" },
						{"text":"龙华新区", "value":"440342" },
						{"text":"大鹏新区", "value":"440343" },
					    {"text":"深汕区", "value":"441521" },
						{"text":"其他", "value":"440399" }]'></div>
		<div name='year' vtype="comboxfield" label="年份"
			dataurl='[
{"text":"2017", "value":"2017" },{"text":"2018", "value":"2018"},
{"text":"2019", "value":"2019"},{"text":"2020", "value":"2020"},
{"text":"2021", "value":"2021"},{"text":"2022", "value":"2022"},{"text":"2023", "value":"2023"},{"text":"2024", "value":"2024"},{"text":"2025", "value":"2025"}
]'
			labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='bgq' vtype="comboxfield" label="报告期" labelAlign="right"
			labelwidth='100px' width="410"
			dataurl='[{"text":"1月", "value":"01-03" },
					{"text":"2月", "value":"02-03" },
					{"text":"3月", "value":"03-03" },
					{"text":"一季度季报", "value":"03-02" },
					{"text":"4月", "value":"04-03" },
					{"text":"5月", "value":"05-03" },
					{"text":"6月", "value":"06-03" },
					{"text":"二季度季报", "value":"06-02" },
					{"text":"上半年年报", "value":"06-01" },
					{"text":"7月", "value":"07-03" },
					{"text":"8月", "value":"08-03" },
					{"text":"9月", "value":"09-03" },
					{"text":"三季度季报", "value":"09-02" },
					{"text":"10月", "value":"10-03" },
					{"text":"11月", "value":"11-03" },
					{"text":"12月", "value":"12-03" },
					{"text":"四季度季报", "value":"12-02" },
					{"text":"年报", "value":"12-01" }]'></div>


		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="查询"
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>



	<div vtype="gridpanel" name="reportListGrid" id="reportListGrid"
		height="400" width="100%" datarender="renderdata" titledisplay="true"
		title="报表信息" dataurl="" layout="fit" showborder="false">
		<!--  <div name="toolbar" vtype="toolbar">
								<div name="add_button" vtype="button" text="下载"
									icon="../query/queryssuo.png" onClick="addUser();"></div>
		</div>  -->
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column">
			<div>
				<!-- 单行表头 -->
				<div name='id' text="" textalign="left" visible="false"></div>
				<div name='reportname' text="报表名称" textalign="center"></div>
				<div name='regcode' text="区域" textalign="center"></div>
				<div name='year' text="年份" textalign="center"></div>
				<div name='bgq' text="报告期" textalign="center"></div>
				<!-- <div name='isvalid' text="状态" textalign="center"></div> -->
				<div name='edit' text="操作" textalign="center"></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</body>
</html>