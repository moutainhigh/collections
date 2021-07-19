<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>综合查询-查询结果</title>
<style type="text/css">
</style>
</head>
<%
	String json = (String) request.getAttribute("bo");
%>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js" type="text/javascript"></script>
<script type="text/javascript">
	var json =<%=json%>;
	var jsonstr = JSON.stringify(json);
	/* $.ajax({
	 type: "POST",
	 url:rootPath+"/comselect/toQuery.do",
	 data : {'mydata':jsonstr},
	 success: function(msg) {   
	 alert(msg);		   
	 },
	 error: function(error){
	 alert(error);
	
	 }
	 });  */
	$(function() {
		$("#gridpanel").gridpanel("option", "dataurlparams", {
			"mydata" : jsonstr
		});
		$("#gridpanel").gridpanel('option', 'dataurl',
				rootPath + "/comselect/toQuery.do");
		$("#gridpanel").gridpanel('reload');

	});

	function exportData(){

	    	  
	        jazz.confirm('导出?<br>该导出最多只能导出前5000条！！',function(){	
	        		post(rootPath + "/comselect/exportExcel.do", {mydata :jsonstr});
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
	        // alert(opt.name)        
	        temp.appendChild(opt);        
	    }        
	    document.body.appendChild(temp);        
	    temp.submit();        
	    return temp;        
	}     
	function renderdata(event, obj) {
		var data = obj.data;
		for (var i = 0; i < data.length; i++) {
			data[i]["entname"] = '<a href="javascript:void(0)" title="查看" onclick="regDetail(\''
					+ data[i]["pripid"]
					+ '\',\''
					+ data[i]["enttype"]
					+ '\')">' + data[i]["entname"] + ' </a>';
		}
		return data;
	}

<%-- 	function regDetail(id, enttype) {

		alert("id:" + id + "type:" + enttype);
			var economicproperty ="";
	  		if(enttype.startsWith("1")||enttype.startsWith("2")||enttype.startsWith("3")||enttype.startsWith("4")){//内资企业
	  			economicproperty="2";
	  		}else if(enttype.startsWith("5")||enttype.startsWith("6")||enttype.startsWith("7")){//外资企业
	  			economicproperty= "3";
	  		}else if(enttype.startsWith("9500")){//个体
	  			economicproperty= "1";
	  		}else if(enttype.startsWith("8")){//集团
	  			economicproperty= "4";
	  		}else{
	  		/*	System.out.println("错误企业类型---"+enttype);
	  			return "9999999";*/
	  			economicproperty= "2"; //暂时先写成2
	  		}
	  		var urlleft="<%=request.getContextPath()%>";
	  		var urlright="page/reg/regDetail.jsp";
	  		var url=urlleft+"/"+urlright+"?flag="+encode("0")+"&economicproperty="+encode(economicproperty)+"&priPid="+encode(id);
	  		window.open(url);

	} --%>
	function regDetail(id, enttype) {

	/* 	alert("id:" + id + "type:" + enttype); */
			var economicproperty ="";
	  		if(enttype.substring(this.length-"1".length)=="1"||enttype.substring(this.length-"2".length)=="2"||enttype.substring(this.length-"3".length)=="3"||enttype.substring(this.length-"4".length)=="4"){//内资企业
	  			economicproperty="2";
	  		}else if(enttype.substring(this.length-"5".length)=="5"||enttype.substring(this.length-"6".length)=="6"||enttype.substring(this.length-"7".length)=="7"){//外资企业
	  			economicproperty= "3";
	  		}else if(enttype.substring(this.length-"9500".length)=="9500"){//个体
	  			economicproperty= "1";
	  		}else if(enttype.substring(this.length-"8".length)=="8"){//集团
	  			economicproperty= "4";
	  		}else{
	  		/*	System.out.println("错误企业类型---"+enttype);
	  			return "9999999";*/
	  			economicproperty= "2"; //暂时先写成2
	  		}
	  		var urlleft="<%=request.getContextPath()%>";
	  		var urlright="page/reg/regDetail.jsp";
	  		var url=urlleft+"/"+urlright+"?flag="+encode("0")+"&economicproperty="+encode(economicproperty)+"&priPid="+encode(id);
	  		window.open(url);

	}
	
/* 	function startWith(str,start){
		if(str==null||str==""||start.length==0||str.length<satrt.length){
			return false;
		}
		if(str.substr(0,satrt.length)==str){
			return true;
		}else{
			return false;
		}
		
	} */
	String.prototype.startWith=function(str){
		var reg=new RegExp("^"+str);
		return reg.test(this);
		}
		//直接使用str.endWith("abc")方式调用即可
		String.prototype.endWith=function(str){
		var reg=new RegExp(str+"$");
		return reg.test(this);
		}
/*     String.prototype.startWith=function(str){  
        if(str==null||str==""||this.length==0||str.length>this.length)  
          return false;  
        if(this.substr(0,str.length)==str)  
          return true;  
        else  
          return false;  
        return true;  
    }  
    String.prototype.endWith=function(str){  
        if(str==null||str==""||this.length==0||str.length>this.length)  
          return false;  
        if(this.substring(this.length-str.length)==str)  
          return true;  
        else  
          return false;  
        return true;  
    }   */
</script>
<style>
		body{
	overflow-x: hidden;
	position:relative;

	background-color: #F8F9FB
	}
	.class_td1{
			width:15%;
			height:5%;
			padding:4px;
	}
	.class_td2{
			width:35%;
			height:5%;
	}
	
	
	.class_td4{
			width:15%;
			height:5%;
			padding:4px;
	}
	
	.class_td5{
			width:35%;
			height:5%;
	}
	
	

	
 	.font_table{
	height:100%;
	width:100%;
	font-family:'微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
	}
	
	.font_title{
	font-family:'微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
	color: #327bb9;
    font-size: 14px;
    font-weight: bold;
    margin-left: 5px;
    text-transform: none;
	}

	
	
	
table.font_table {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #a9c6c9;
	border-collapse: collapse;
}
table.font_table th {
	border-width: 1px;
	padding: 8px;
	font-size: 14px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.font_table td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
.thleft{
 text-align:left;
 padding
 }
 .thright{
 text-align:right;
 padding
 }

.hangjianju { line-height:30px;}
</style>



<body>
	<!-- 	<div class="title_nav">当前位置：资源管理 > <span>数据源管理</span></div> -->

		<table  class="font_table" >
			<tr>
				<th class="thleft"	style="width:50%" colspan="2">
				
					<font  size="5" color="red">以下信息仅供参考，不得对外提供</font>
				
				</th>
				<th class="thright" colspan="2">
								<input type="button"  onclick="exportData()" value="导出详细清单"/>
								<input type="button" onclick="javascript:window.opener=null;window.open('','_self');window.close();"  value="关闭窗口"/>
				</th>
			</tr>
			<tr>
				<th class="class_td5 thleft hangjianju" colspan="4" >
				
				</th>

				
			</tr>
		</table>


	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%"
		layout="fit" showborder="false" datarender="renderdata"
		rowselectable="false" titledisplay="false" isshowselecthelper="true"
		selecttype="0">
		<div name="toolbar" vtype="toolbar"></div>
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div>
				<!-- 单行表头 -->

				<%-- 					<div name="dataSourceType" text="数据源类型" textalign="center" sort="true"  width="14%"
					  dataurl="<%=contextpath%>/dataSource/findKeyValueDcDmDstypeBO.do"
					></div> --%>




				<div name='pripid' key="true" visible="false"></div>
				<div name='regno' text="注册号" textalign="center" width="15%"></div>
				<div name='entname' text="商事主体名称" textalign="center"></div>
				<div name='enttype' text="商事主体类型" textalign="center" width="15%"></div>


				<div name='estdate' text="成立日期" textalign="center" width="15%"></div>
				<div name='regcapcur' text="币种"  dataurl="<%=request.getContextPath()%>/comselect/code_value.do?type=regcapcur" textalign="center" width="10%"></div>
				<div name='reccap' text="认缴资本总额（万元）" textalign="center" width="15%"></div>
				<div name='regstate' text="商事主体状态" textalign="center"  dataurl="<%=request.getContextPath()%>/page/comselect/regstate.json"  width="10%"></div>
				<div name='checkState' text="对比状态" textalign="center"    width="10%"></div>
				
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" pagerows="50" id="grid_paginator"></div>
	</div>
</body>
</html>