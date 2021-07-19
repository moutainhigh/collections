<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>变更信息</title>
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
	src="<%=request.getContextPath()%>/static/js/sczt/scztFirstFlow.js"></script>

<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/regDetail.css" />

<script type="text/javascript">
function fixColumn(event, obj) {// 维护按钮
	var data = obj.data;
	var priPid = getUrlParam("priPid");
	var entityNo = getUrlParam("entityNo");
	var economicproperty = getUrlParam("economicproperty");
	for(var i=0;i<data.length;i++){
		var htmCompare ='<div class="jazz-grid-cell-inner">'
			+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+priPid+'\',\''+entityNo+'\',\''+economicproperty+'\',\''+data[i]["alttime"]+'\',\''+data[i]["altdate"]+'\',\''+data[i]["entname"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
			+'</div>';
		data[i]["fix"] = htmCompare;
	}
	return data;
}

function viewDataSource(pripid,entityNo,economicproperty,alttime,altdate,entname){
	var title="详细信息";
	var frameurl = ""+'/page/reg/nzwz/nzwz_biangengxx_two.jsp?priPid='+pripid+'&entityNo='+entityNo+'&economicproperty='+economicproperty+'&alttime='+alttime+'&altdate='+altdate+'&entname='+entname;
	//createNewWindow1(title,frameurl);	
	createNewWindow(frameurl);	
}

function createNewWindow(frameurl){
	window.open(rootPath+frameurl);
}

<%-- function createNewWindow(title,frameurl){
    win = top.jazz.widget({ 
        vtype: 'window', 
        name: 'win', 
        title: title, 
        width: 750, 
        height: 400, 
        modal:true, 
        visible: true ,
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: false,
		minimizable : true, //是否显示最小化按钮
		titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
		frameurl: rootPath+frameurl
    });
} --%>
</script>


</head>
<body>
	<div vtype="gridpanel" name="jbxxGrid" height="90%" width="100%"
				id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="变更信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<!-- <div name='recid' text="记录ID" textalign="center" width="0%"></div>
						<div name='openo' text="业务编号" textalign="center" width="0%"></div>
						<div name='pripid' text="主体身份代码" textalign="center" width="0%"></div> -->
						<div name='altdate' text="变更日期" textalign="center" width="15%"></div>
						<!-- <div name='alttime' text="变更次数" textalign="center" width="10%"></div> -->
						<div name='altitems' text="变更事项" textalign="center" width="75%"></div>
						<!-- <div name='altaf' text="变更后内容" textalign="center" width="0%"></div> -->
						<div name='fix' text="操作" textalign="center" width="10%"></div>
					</div>
				</div>
				<!-- 表格 -->
				<div vtype="gridtable" name="grid_table" id="grid_table"
					rowrender=""></div>
				<!-- 卡片 -->
				<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" id="grid_paginator1"></div>

			</div>

		</div>
		<div>

			<div id="font" class="font_title"></div>
		</div>
		<div>

			<div id="formpanel" class="formpanel_table" height="150px">
			
		<!--  	<div class="font_title">详细信息</div>
				<table id="datashow" class="font_table">
					<tr class="class_hg">
						<th class="class_td4">变更项ID:</th>
						<th class="class_td5" id="row1" />
						<th class="class_td4">主体身份代码:</th>
						<th class="class_td5" id="row2"></th>
					</tr>
					<tr>
						<th class="class_td4">变更事项（中文名称）:</th>
						<th class="class_td5" id="row3"></th>
						<th class="class_td4">变更事项代码:</th>
						<th class="class_td5" id="row4" />

					</tr>
					<tr>
						<th class="class_td4">变更前内容:</th>
						<th class="class_td5" id="row5" />
						<th class="class_td4">变更后内容:</th>
						<th class="class_td5" id="row6"></th>
					</tr>
					<tr>
						<th class="class_td4">变更日期:</th>
						<th class="class_td5" id="row7" />
					</tr>
				</table>
			</div>
                  		</div>    -->
<!-- 
		<div>
			<div>
				jiange
			</div>

		</div>

		<div>
			<div vtype="gridpanel" name="qiyeleixingbiangeng" height="100%" width="100%"
				id='qiyeleixingbiangeng' dataloadcomplete="" datarender="renderdata()"
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="市场主体变更信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2">
				表头
				<div vtype="gridcolumn" name="grid_column111" width="100%">
					<div>
						<div name='altitemcn' text="变更事项（中文名称）" textalign="center"
							width="30%"></div>
						<div name='altbe' text="变更前内容" textalign="center" width="35%"></div>
						<div name='altaf' text="变更后内容" textalign="center" width="35%"></div>
					</div>
				</div>
				表格
				<div vtype="gridtable" name="grid_table111"
					rowrender=""></div>
				卡片
				<div vtype="gridcard" name="grid_card" width="205px" height="90px"></div>
				分页
				<div vtype="paginator" name="grid_paginator111"></div>

			</div>
			
			<div id="bijiao" width="100%" height="100%" vtype="panel"
				name="panel" layout="column"
				layoutconfig="{width: ['*','20','48%']}">

				<div>
					<div id="font_qian" height ="20%" class="font_title">变更前</div>
					<div vtype="gridpanel" name="gaibianqian" layout="fit"
						width="100%" id='gaibianqian' dataloadcomplete=""
						datarender="renderdata()" style="Position: Reletive"
						selecttype="1" titledisplay="false" title="改变前"
						defaultview="table" showborder="false" isshowselecthelper="false"
						selecttype="2">
						表头
						<div vtype="gridcolumn" name="grid_column" width="100%">
							<div>
								<div name='recid' text="变更项ID" textalign="center" width="0%"></div>
								<div name='pripid' text="主体身份代码" textalign="center" width="0%"></div>
								<div name='altitemCn' text="变更事项（中文名称）" textalign="center"
									width="30%"></div>
								<div name='altitem' text="变更事项代码" textalign="center" width="0%"></div>
								<div name='altbe' text="变更前内容" textalign="center" width="30%"></div>
								<div name='altaf' text="变更后内容" textalign="center" width="30%"></div>
								<div name='altdate' text="变更日期" textalign="center" width="10%"></div>
							</div>
						</div>
						表格
						<div vtype="gridtable" name="grid_table2" id="grid_table2"
							rowrender=""></div>
						卡片
						<div vtype="gridcard" name="grid_card" width="205px" height="90px"></div>
						分页
						<div vtype="paginator" name="grid_paginator2"></div>

					</div>

				</div>

				<div>

					<div>
						111
					</div>

				</div>

				<div>
					<div id="font_hou" height ="20%" class="font_title">变更后</div>
					<div vtype="gridpanel" name="gaibianhou" layout="fit" width="100%"
						id='gaibianhou' dataloadcomplete="" datarender="renderdata()"
						style="Position: Reletive" selecttype="1" titledisplay="false"
						title="改变后" defaultview="table" showborder="false"
						isshowselecthelper="false" selecttype="2">
						表头
						<div vtype="gridcolumn" name="grid_column" width="100%">
							<div>
								<div name='recid' text="变更项ID" textalign="center" width="0%"></div>
								<div name='pripid' text="主体身份代码" textalign="center" width="0%"></div>
								<div name='altitemCn' text="变更事项（中文名称）" textalign="center"
									width="30%"></div>
								<div name='altitem' text="变更事项代码" textalign="center" width="0%"></div>
								<div name='altbe' text="变更前内容" textalign="center" width="30%"></div>
								<div name='altaf' text="变更后内容" textalign="center" width="30%"></div>
								<div name='altdate' text="变更日期" textalign="center" width="10%"></div>
							</div>
						</div>
						表格
						<div vtype="gridtable" name="grid_table1" id="grid_table1"
							rowrender=""></div>
						卡片
						<div vtype="gridcard" name="grid_card" width="205px" height="90px"></div>
						分页
						<div vtype="paginator" name="grid_paginator1"></div>

					</div>

				</div>
			</div>

		</div> -->
<!-- 	</div> -->
</body>
</html>