<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible"content="IE=8;IE=9;IE=EDGE"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导出参数配置</title>
</head>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js" type="text/javascript"></script>
<script>

var data = getUrlParam("data");

alert(decode(data));
var window;
function save(){
	var data = getUrlParam("data");
	var aa = $("#formpanel").formpanel('getValue');
	var jsonstr = JSON.stringify(aa);

	jazz.confirm('导出?<br>该导出最多只能导出前5000条！！',function(){	
	      post(rootPath + "/comselect/exportExcel.do", {mydata :data, select: jsonstr});
	       },function(){
	        		
	});
}

function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];                
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();               
}



function reset(){
	$("#formpanel").formpanel('reset');
}


 function getUrlParam(name) {
     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
     var r = window.location.search.substr(1).match(reg);  //匹配目标参数
     if (r != null) return unescape(r[2]); return null; //返回参数值
 }
</script>
<body>     
	<div id="formpanel" name="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table"      
		layoutconfig="{cols:3, columnwidth: ['33%','33%','*']}"> 
		<div name="regno" vtype="checkboxfield" labelalign="right" width="200"
			dataurl="[{checked: false,value: '注册号',text: '注册号'}]"></div>
		<div name="estdate" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '成立日期',text: '成立日期'}]"></div>
		<div name="apprdate" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '核准日期',text: '核准日期'}]"></div>
		<div name="reccap" vtype="checkboxfield" labelalign="right" width="200"
			dataurl="[{checked: false,value: '认缴资本总额',text: '认缴资本总额'}]"></div>
		<div name="regcapcur" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '币种',text: '币种'}]"></div>
		<div name="industryphy" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '行业类别',text: '行业类别'}]"></div>
		<div name="entname" vtype="checkboxfield" labelalign="right" width="200"
			dataurl="[{checked: false,value: '企业名称',text: '企业名称'}]"></div>
		<div name="opscope" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '经营范围',text: '经营范围'}]"></div>
		<div name="dom" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '地址',text: '地址'}]"></div>
		<div name="regorg" vtype="checkboxfield" labelalign="right" width="200"
			dataurl="[{checked: false,value: '管辖区域',text: '管辖区域'}]"></div>
		<div name="adminbrancode" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '所属监管所',text: '所属监管所'}]"></div>
		<div name="enttype" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '主体类型',text: '主体类型'}]"></div>
		<div name="regstate" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '注册状态',text: '注册状态'}]"></div>
		<div name="industryco" vtype="checkboxfield" label="" labelalign="right" width="200"
			dataurl="[{checked: false,value: '行业代码',text: '行业代码'}]"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar">          
			<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>          
			<div id="btn4" name="btn4" vtype="button" text="重 置" defaultview="1" align="center" click="reset()"></div>
		</div> 
	</div> 
</body>