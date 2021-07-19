<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>综合查询-查询结果</title>
<style type="text/css">
.hide,.none {
	display: none !important;
}

.show,.block {
	display: block;
}

.fl {
	float: left;
}

.fr {
	float: right;
}

#aspupload {
	position: fixed;
	bottom: 0;
	right: 2px;
	width: 577px;
	height: auto;
	overflow: hidden;
	z-index: 9999;
	border: 1px #056a5c solid;
	-webkit-box-shadow: 0 0 20px -2px rgba(0, 0, 0, 0.5);
	-moz-box-shadow: 0 0 20px -2px rgba(0, 0, 0, 0.5);
	-ms-box-shadow: 0 0 20px -2px rgba(0, 0, 0, 0.5);
	box-shadow: 0 0 20px -2px rgba(0, 0, 0, 0.5)
}

#aspupload .headbar {
	position: relative;
	height: 36px;
	color: #FFF;
	background: #629600
}

#aspupload .headbar .bar_title {
	font: normal 14px/35px 微软雅黑;
	margin-left: 12px
}

#aspupload .headbar .bar_total {
	position: absolute;
	right: 80px;
	line-height: 35px;
	color: #ffc
}

#aspupload .headbar .bar_total a {
	color: #ffc
}

#aspupload .headbar .bar_add {
	position: absolute;
	right: -15px;
	top: 2px;
	*top: 8px;
	width: 70px;
	line-height: 35px;
	text-align: center
}

#aspupload .headbar .bar_add a {
	color: #FFF;
	background: url(../static/script/uploadUI/del_img1.png) no-repeat;
	width: 16px;
	height: 16px;
	display: inline-block
}

#aspupload .headbar .bar_add a:hover {
	color: #FFF;
	background: url(../static/script/uploadUI/del_img.png) no-repeat;
	width: 16px;
	height: 16px;
	display: inline-block
}

a.shrink,a.enlarge {
	background: url(../static/script/uploadUI/load.png) no-repeat;
	display: block;
	width: 18px;
	height: 18px;
	position: absolute;
	right: 45px;
	top: 8px
}

#aspupload .headbar a.shrink {
	background-position: 0 -42px
}

#aspupload .headbar a.enlarge {
	background-position: -32px -44px
}

#aspupload .loadbar {
	height: 0;
	overflow: auto
}

#aspupload .loadbar ul li {
	height: 55px;
	border-bottom: 1px solid #dadada;
	border-top: 1px solid #f8f8f8
}

#aspupload .loadbar ul li .load_area {
	width: 545px;
	height: 40px;
	margin-left: 10px;
	margin-top: 15px
}

#aspupload .loadbar ul li .loadbar_area {
	float: left;
	width: 480px;
	height: 40px;
	margin-left: 10px
}

#aspupload .load_info {
	height: 22px
}

#aspupload .itembox {
	width: 422px
}

.filename,.filesize {
	padding: 6px;
	font-size: 12px;
}

.filename {
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis
}

.file_ico {
	background: url(../static/script/uploadUI/file.png);
	padding: 16px
}

#aspupload .load_info span a {
	color: #008f7c
}

.loading {
	clear: both;
	width: 478px;
	height: 10px;
	background: #DDD;
	border-width: 1px 1px 1px 0;
	border-style: solid;
	border-color: #CCC #FFF #FFF #FFF;
	font-size: 12px;
}

.up-prograss {
	background: #629600;
	height: 10px
}
</style>
</head>
<%
	String json = (String) request.getAttribute("bo");
	String excelFlag = (String) request.getAttribute("excelFlag");
%>
<!--<%String type = (String) request.getParameter("type");%>
-->
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.cookie.js" type="text/javascript"></script>

<script language="javascript">
function isEmpty(val) {
	val = $.trim(val);
	if (val == null)
		return true;
	if (val == undefined || val == 'undefined')
		return true;
	if (val == "")
		return true;
	if (val.length == 0)
		return true;
	if (!/[^(^\s*)|(\s*$)]/.test(val))
		return true;
	return false;
}

function isNotEmpty(val) {
	return !isEmpty(val);
}


	var json =<%=json%>;
	var jsonstr = JSON.stringify(json);
	var count;
	
	var excelFlag = <%=excelFlag%>;
	
	 $(function() {
		$.ajax({
			 type: "POST",
			 url:rootPath+"/comselect/codetype.do",
			 data:{ "data":jsonstr},
			 success: function(data) {
				 
				 var _html = "";
				 if(isNotEmpty(data.estdate_start)){
					 _html += "<li>成立起始日期:"+data.estdate_start+"</li>";
				 }
				 if(isNotEmpty(data.apprdate_start)){
					 _html += "<li>核准起始日期:"+data.apprdate_start+"</li>";
				 }
				 if(isNotEmpty(data.reccap_start)){
					 _html += "<li>认缴注册资本开始总额(万元):"+data.reccap_start+"</li>";
				 }
				 
				 if(isNotEmpty(data.regcapcur)){
					 _html += "<li>币种："+data.regcapcur+"</li>";
				 }
				 
				 if(isNotEmpty(data.entname)){
					 _html += "<li>商事主体名称(包括)："+data.entname+"</li>";
				 }
				 if(isNotEmpty(data.dom)){
					 _html += "<li>地址："+data.dom+"</li>";
				 }
				 if(isNotEmpty(data.enttype)){
					 var temp = "";
					 for(var i=0;i<data.enttype.length;i++){
						 if(isNotEmpty(data.enttype[i])){
							 temp += data.enttype[i]+",";
						 }
					 }
					 temp = temp.substring(0,temp.length-1);
					 _html += "<li>商事主体类型(包括)："+temp+"</li>";
				 }
				 if(isNotEmpty(data.estdate_end)){
					 _html += "<li>成立结束日期："+data.estdate_end+"</li>";
				 }
				 if(isNotEmpty(data.apprdate_end)){
					 _html += "<li>核准结束日期："+data.apprdate_end+"</li>";
				 }
				 if(isNotEmpty(data.reccap_end)){
					 _html += "<li>认缴注册资本结束总额(万元)："+data.reccap_end+"</li>";
				 }
				 if(isNotEmpty(data.industryphy)){
					 _html += "<li>行业类别："+data.industryphy+"</li>";
				 }
				 if(isNotEmpty(data.opscope)){
					 _html += "<li>经营范围(包括)：\""+data.opscope+"\"" + "</li>";
				 }
				 if(isNotEmpty(data.regorg)){
					 var temp = "";
					 for(var i=0;i<data.regorg.length;i++){
						 if(isNotEmpty(data.regorg[i])){
							 temp += " "+ data.regorg[i];
						 }
					 }
					 if(temp != ""){
						 _html += "<li>登记机关："+temp+"</li>";
					 }
				 }
				 if(isNotEmpty(data.regstate)){
					 var temp = "";
					 for(var i=0;i<data.regstate.length;i++){
						 if(isNotEmpty(data.regstate[i])){
							 temp +=data.regstate[i]+" ";
						 }
					 }
					 _html += "<li>注册状态："+temp+"</li>";
				 }
				 
				 if(isNotEmpty(data.regorg)){
					 var temp = "";
					 for(var i=0;i<data.regorg.length;i++){
						 if(isNotEmpty(data.regorg[i])){
							 temp +=data.regorg[i]+" ";
						 }
					 }
					 _html += "<li>管辖区域："+temp+"</li>";
				 }
				 if(isNotEmpty(data.adminbrancode)){
					 var temp = "";
					 for(var i=0;i<data.adminbrancode.length;i++){
						 if(isNotEmpty(data.adminbrancode[i])){
							 temp +=data.adminbrancode[i]+" ";
						 }
					 }
					// _html += "<li>所属监管所："+temp+"</li>";
				 }
				 if(isNotEmpty(data.gongzuowangge)){
					 var temp = "";
					 for(var i=0;i<data.gongzuowangge.length;i++){
						 if(isNotEmpty(data.gongzuowangge[i])){
							 temp +=data.gongzuowangge[i]+" ";
						 }
					 }
					// _html += "<li>工作网格："+temp+"</li>";
				 }
				 if(isNotEmpty(data.danyuanwangge)){
					 var temp = "";
					 for(var i=0;i<data.danyuanwangge.length;i++){
						 if(isNotEmpty(data.danyuanwangge[i])){
							 temp +=data.danyuanwangge[i]+" ";
						 }
					 }
					// _html += "<li>单元网格："+temp+"</li>";
				 }
				 
				
				 
				 $("#queryConditions").append(_html);
			 },
			 error: function(error){
			 	alert(error);
			
			 }
			 });
			
			
		
		$("#gridpanel").gridpanel("option", "dataurlparams", {
			"mydata" : jsonstr
		});
		$("#gridpanel").gridpanel('option', 'dataurl',rootPath + "/comselect/toQuery.do");
		$("#gridpanel").gridpanel('reload');
		
		if(!excelFlag){
			$("#excel").css("display", "none");
		}
		
		 $.ajax({
			 type: "POST",
			 url:rootPath+"/comselect/toQuery.do?cntFlag=1",
			 data:{"mydata":jsonstr},
			 dataType:"json",
			 success: function(data) {
				 document.getElementById("total").innerText = data;
			 }
		});
		//昨天的时间
		 var day1 = new Date();
		 day1.setTime(day1.getTime()-24*60*60*1000);
		 var s1 = day1.getFullYear()+"-" + (day1.getMonth()+1) + "-" + day1.getDate() +"  21:00";
		//time = year + "-" + month + "-" + date;
		document.getElementById("time").innerText = s1;
	}); 

	function exportData(){
        /* jazz.confirm('导出?<br>该导出最多只能导出前5000条！！',function(){	
       		post(rootPath + "/comselect/exportExcel.do", {mydata :jsonstr});
       	},function(){
       		
       	});  */
       	
	    viewSelectItem(excelFlag,count);    //弹出的窗口
	}
	
	function viewSelectItem(excelFlag,count){
		var title="导出参数";
		var frameurl=""+'/page/comselect/excelExport.html?data=' + encode(jsonstr)+'&excelFlag='+excelFlag+'&count='+count;
		var win = null;
		win = top.jazz.widget({
			vtype: 'window',
			name: 'topWindow',
	    	title: '导出参数',
	    	titlealign: 'left',
	    	titledisplay: true,
	        width: 550,
	        height: 380,
	        modal: true,        
	        visible: true,       
	        titleicon : "/query/static/images/other/notepad-.png",
			frameurl: "/query"+frameurl
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
	
	
	function renderdata(event, obj) {
		var data = obj.data;
		//count = data[data.length-1]["count"];
		//data.length = data.length-1;
		//removeElement(data.length-1,data);
		
		for (var i = 0; i < data.length; i++) {
			data[i]["entname"] = '<a href="javascript:void(0)" title="'+data[i]["entname"]+'"  onclick="regDetail(\''
					+ data[i]["pripid"]
					+ '\',\''
					+ data[i]["type"]
					+ '\',\''
					+ data[i]["opetype"]
					+ '\',\''
					+ data[i]["entstatus"]
					+ '\',\''
					+ data[i]["entid"]
					+ '\',\''
					+ data[i]["entname"]
					+ '\',\''
					+ data[i]["regno"]
					+ '\')">' + data[i]["entname"] + ' </a>';
					
			data[i]["file"] = '<a href="javascript:void(0)" onclick="regfile(\''
					+ data[i]["entid"]
					+ '\')">' + '影像' + ' </a>';
			
			if(data[i]["estdate"]){
				data[i]["estdate"] = data[i]["estdate"].substring(0,10);
			}
		}
		
		//document.getElementById("total").innerText = count;
		/* $.ajax({
			 type: "POST",
			 url:rootPath+"/comselect/queryCount.do",
			 async : false,
			 success: function(data) {
				var total = data;
				document.getElementById("skyTotal").innerText = total;
			 }
		 }); */
		return data;
	}
	
	/* function removeElement(index,array)//数组重构
	{
	 if(index>=0 && index<array.length)
	 {
	  for(var i=index; i<array.length; i++)
	  {
	  	 array[i] = array[i+1];
	  }
	  array.length = array.length-1;
	 }
	 return array;
	} */
	function regfile(entid){
		var url = "http://aaicweb03/EIMIS/frmShowEntImage.aspx?entid=" + entid;
		window.open(url);
	}
	function regDetail(id, enttype, opetype, entstatus, entid, entname, regno) {
			var economicproperty ="";
	  		if(enttype.substring(0,1)=="1"||enttype.substring(0,1)=="2"||enttype.substring(0,1)=="3"||enttype.substring(0,1)=="4" || enttype.substring(0,1)=="A" || enttype.substring(0,1)=="C" ){//内资企业
	  			economicproperty="2";
	  		}else if(enttype.substring(0,1)=="5"||enttype.substring(0,1)=="6"||enttype.substring(0,1)=="7" || enttype.substring(0,1)=="W" || enttype.substring(0,1)=="Y"  ){//外资企业
	  			economicproperty= "3";
	  		}else if(enttype.substring(0,2)=="95"){//个体
	  			economicproperty= "1";
	  		}else if(enttype.substring(0,1) == "8"){//集团
	  			economicproperty= "4";
	  		}else{
	  			economicproperty= "2"; //暂时先写成2
	  		}
	  		var urlleft="<%=request.getContextPath()%>";
		var urlright = "page/reg/regDetail.jsp";
		var url = urlleft + "/" + urlright + "?flag=" + encode("0")
				+ "&economicproperty=" + encode(economicproperty) + "&priPid="
				+ encode(id) + "&opetype=" + encode(opetype) + "&entstatus="
				+ encode(entstatus) + "&entid=" + encode(entid) +"&entname=" + encode(entname)
				+ "&regno=" + encode(regno); 
		window.open(url);

	}

	String.prototype.startWith = function(str) {
		var reg = new RegExp("^" + str);
		return reg.test(this);
	}
	//直接使用str.endWith("abc")方式调用即可
	String.prototype.endWith = function(str) {
		var reg = new RegExp(str + "$");
		return reg.test(this);
	}
	
	
	
</script>
<style>
body {
	overflow-x: hidden;
	position: relative;
	background-color: #F8F9FB
}

.class_td1 {
	width: 15%;
	height: 5%;
	padding: 4px;
}

.class_td2 {
	width: 35%;
	height: 5%;
}

.class_td4 {
	width: 15%;
	height: 5%;
	padding: 4px;
}

.class_td5 {
	width: 35%;
	height: 5%;
}

.font_table {
	height: 100%;
	width: 100%;
	font-family: '微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
}

.font_title {
	font-family: '微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
	color: #327bb9;
	font-size: 14px;
	font-weight: bold;
	margin-left: 5px;
	text-transform: none;
}

table.font_table {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
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

.thleft {
	text-align: left;
	padding
}

.thright {
	text-align: right;
	padding
}

.hangjianju {
	line-height: 30px;
}

.clear{clear:both;}
</style>



<body>

	<table class="font_table">
		<tr>
			<th class="thleft" style="width: 50%" colspan="2"><font size="5" color="red">以下信息仅供参考，不得对外提供</font></th>
			<th class="thright" colspan="2"><input type="button" onclick="exportData()" value="导出详细清单" id = "excel"/> <input type="button"
					onclick="javascript:window.opener=null;window.open('','_self');window.close();" value="关闭窗口" /></th>
		</tr>
		<tr>
			<th class="class_td5 thleft hangjianju" colspan="4"></th>


		</tr>
	</table>
	<style>
	ul,li{list-style: none;}
	
	#conditions{padding-left: 25px;}
	.conditionTitle {
		font-size: 12px;
		font-family: "微软雅黑";
		padding-left: 6px;
		color: red;
	}
	#queryConditions{max-height: 450px;max-width: 94%;margin:0 auto;}
	#queryConditions li{
			float: left;
		    padding: 2px 4px;
		    text-align: left;
		    max-height:149px;
		    overflow:hidden;
		    color: red;
		    white-space: pre-wrap;
	    }
	
</style>
	<div id="conditions">
		<h2 class="conditionTitle">当前查询条件为：</h2>
		<div class="clear"></div>
		 <ul id="queryConditions"></ul>
		<div style="color: red;clear: both;padding-top: 10px;padding-left: 4px">共<span id="total" style="font-weight: bold;padding: 4px;"></span>条记录(数据更新时间：<span id="time" style="font-weight: bold;padding: 4px;"></span>)。以下为查询结果的前100条。如有更多需求，请与信息中心联系，电话：83070056。</div>
	</div>


	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata" rowselectable="false"
		titledisplay="false" isshowselecthelper="true" selecttype="0">

		<!-- 	<div name="toolbar" vtype="toolbar"s>
				
		</div> -->


		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div id="dateShowRows">
				<div name='regno' text="统一社会信用代码/注册号" textalign="left" width="13%"></div>
				<div name='entname' text="商事主体名称" textalign="left" width="25%" ></div>
				<div name='file' text="档案" textalign="left" width="4%"></div>
				<div name='enttype' text="经济性质" textalign="left" width="22%" dataurl="<%=request.getContextPath()%>/comselect/code_value.do?type=enttype"></div>
				<div name='estdate' text="成立日期" textalign="left" width="7%"></div>
				<div name='regcapcur' text="币种" dataurl="<%=request.getContextPath()%>/comselect/code_value.do?type=regcapcur" textalign="left" width="8%"></div>
				<div name='reccap' text="认缴资本总额（万元）" textalign="right" width="11%"></div>
				<div name='regstate' text="商事主体状态" textalign="left" dataurl="<%=request.getContextPath()%>/page/comselect/regstate.json" width="10%"></div>

			</div>
		</div>
		<!-- 表格 -->
		 <div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div> 
		<!-- 分页 -->
		<!-- <div vtype="paginator" name="grid_paginator" pagerows="50" id="grid_paginator"></div> -->
	</div>

</body>
</html>