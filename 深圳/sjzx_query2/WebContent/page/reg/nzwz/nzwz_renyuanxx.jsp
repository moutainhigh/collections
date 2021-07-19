<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>人员信息</title>
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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/js/sczt/sczt.js"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"></script>
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/regDetail.css" />

<script type="text/javascript">
	

	window.onload = function() {
		//altRows('datashow');

	};
	function fixColumn(event, obj) {// 维护按钮
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var htm ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''
						+data[i]["personid"]
						+ '\',\''
						+data[i]["name"]
						+ '\',\''
						+ data[i]["cerno"]
						+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["fix"] = htm;
			//data[i]["name"] = html;
			
			if(data[i]["natdate"]){
				data[i]["natdate"] = data[i]["natdate"].substring(0,10);
			}
			
			var cerNo = " ";
			if(isNotEmpty(data[i]["cerno"])){
				cerNo = data[i]["cerno"];
				var temp = cerNo;
				var str = cerNo.substring(cerNo.length - 4,cerNo.length);
				cerNo  = "查看(该操作会记录日志)";
				data[i]["cerno"] = '<div class="cerno"><a href="javascript:void(0);" data-cerno="'+temp+'"  onclick="viewCerno(this)">'  +cerNo + '</a></div>';
			}else{
				
			}
			

			/* if(data[i]["cerno"]){
				var cerno = data[i]["cerno"];
				var str = cerno.substring(cerno.length - 4,cerno.length);
				data[i]["cerno"] = cerno.replace(str, "****");
			} */
			var ent = '<a id="persname" href="javascript:void(0);" onclick="viewEntDetail(\''+data[i]["cernoLink"]+'\');">' + data[i]["name"] + '</a>';
			data[i]["name"] = ent;
		}
		return data;
	}
	
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

	
	
	function viewCerno(obj){
		var _this = $(obj);
		var cerno = _this.data("cerno");
		_this.parent().html(cerno);

		$.ajax({
			url:rootPath+'/reg/showCerno.do',
			data:{
				flag : "查询主要人员证照号码",
				/* entname : parent.entname, */
				persname : name
			},
			type:"post",
			dataType : 'json',
			success:function(data){

			}
		});
	}
	function viewEntDetail(cerno){
		window.open("../entlist.jsp?cerno=" + encode(cerno));
	}
	function viewDataSource(personid,name,cerno){
		var title="详细信息";
		var frameurl=""+'/trsquery/querynzwzrenyuanxxform.do?personid='+personid+"&cerno="+cerno+"&name="+encode(name)+"&entname="+encode(parent.entname);
		createNewWindow(title,frameurl);
	}
	var win;
	function createNewWindow(title,frameurl){
	    win = top.jazz.widget({ 
	    	  
	        vtype: 'window', 
	        name: 'win', 
	        title: title, 
	        width: 750, 
	        height: 480, 
	        modal:true, 
	        visible: true ,
			showborder : true, //true显示窗体边框    false不显示窗体边
			closestate: false,
			minimizable : true, //是否显示最小化按钮
			titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
			frameurl: rootPath+frameurl
	    }); 
	}
			
	
</script>


</head>
<body>
	<div vtype="gridpanel" name="jbxxGrid" height="438" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		style="Position: Reletive" selecttype="1" titledisplay="true"
		title="人员信息" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>

				<div name='name' text="姓名" textalign="center" width="15%"></div>
				<div name='sex' text="性别" textalign="center" width="10%"></div>
				<div name='certype' text="证照类型" textalign="center" width="30%"></div>
				<div name='cerno' text="证照号码" textalign="center" width="25%"></div>
				<div name='post' text="职务" textalign="center" width="10%"></div>
                <div name='fix' text="操作" textalign="center" width="10%"></div>

			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
		<!-- 卡片 -->
		<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" ></div>

	</div>
		
</body>
</html>