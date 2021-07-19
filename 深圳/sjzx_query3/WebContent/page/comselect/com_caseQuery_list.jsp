<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>综合查询-行政处罚-查询结果</title>
<style type="text/css">
</style>
</head>
<%
	String json = (String) request.getAttribute("bo");
%>
<% 
	String type = (String) request.getParameter("type");
%>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js" type="text/javascript"></script>
<script language="javascript">
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
					rootPath + "/caseselect/toQuery.do");
		$("#gridpanel").gridpanel('reload');

	});

	function exportData(){
	        jazz.confirm('导出?<br>该导出最多只能导出前5000条！！',function(){	
	        		post(rootPath + "/caseselect/exportExcel.do", {mydata :jsonstr});
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
	    //return temp;        
	}     
	
	
	
	//目前列表中点击一条记录后的详细信息页面没完善 
	
	function renderdata(event, obj) {
		var data = obj.data;
		for (var i = 0; i < data.length; i++) {
			data[i]["casename"] = '<a href="javascript:void(0)" title="查看" onclick="regDetail(\''
					+ data[i]["id"]
					+ '\',\''
					+ data[i]["enttype"]
					+ '\')">' + data[i]["casename"] + ' </a>';
		}
		return data;
	}
	
	
	 function regDetail(id, enttype) {
		 
	  		var urlleft= "<%=request.getContextPath()%>";
	  		var urlright="page/caseType/caseDetail.jsp";
	  		var url=urlleft+"/"+urlright+"?&priPid="+encode(id)+ "&caseType=" + encode("0");
	  		window.open(url);

	}
	 
	 
	/* 
	String.prototype.startWith=function(str){
		var reg=new RegExp("^"+str);
		return reg.test(this);
		}
		//直接使用str.endWith("abc")方式调用即可
		String.prototype.endWith=function(str){
		var reg=new RegExp(str+"$");
		return reg.test(this);
	} */
		
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
			<div id = "dateShowRows">
				<!-- 单行表头 -->
				<%-- 
					<div name="dataSourceType" text="数据源类型" textalign="center" sort="true"  width="14%"
						  dataurl="<%=contextpath%>/dataSource/findKeyValueDcDmDstypeBO.do">
				  	</div>
				 --%>
				<div name='id' key="true" visible="false"></div> 
				<div name='caseno' text="案件编号" textalign="center" width="20%"></div>
				<div name='casename' text="案件名称" textalign="center" width = "20%"></div>
				<div name='caseval' text="案值" textalign="center" width="20%"></div>

				<div name='casestate' text="案件状态" textalign="center" width="15%" dataurl = "<%=request.getContextPath()%>/caseselect/code_value.do?type=caseState"></div>
				<div name='partytype' text="当事人类型"  textalign="center" dataurl="[{text:'自然人',value:'0'},{text:'法人或其他组织',value:'1'}]" width="10%"></div>
				<div name='unitname' text="当事人姓名" textalign="center" width="15%"></div>

			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" pagerows="50" id="grid_paginator"></div>
	</div>
</body>
</html>