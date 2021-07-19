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
	src="<%=request.getContextPath()%>/static/js/sczt/sczt.js"></script>

<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/regDetail.css" />

<script type="text/javascript">
/* window.onload = function() {
	//altRows('datashow');

}; */
function fixColumn(event, obj) {// 维护按钮
	var data = obj.data;
	for(var i=0;i<data.length;i++){//变更事项换行
		var altitemcode = data[i]["altitemcode"];
		if(altitemcode == '03' || altitemcode == '30' || altitemcode == '05' || altitemcode == '70' || altitemcode == '74'
				|| altitemcode == '24' || altitemcode == '02' || altitemcode == '05'){
			//var altitem = data[i]["altitem"].trim();
			var altitem = $.trim(data[i]["altitem"]);
			var rowdataaltitem = '<div class="jazz-grid-cell-inner">';
			
			for(var y=0;y<altitem.length/17;y++){
				var rowdata = altitem.substring(y*17,(y+1)*17);  //每17个字符换行一次 
				rowdata = rowdata +"<br>";
				rowdataaltitem = rowdataaltitem + rowdata;
			}
			rowdataaltitem = rowdataaltitem + '</div>';
			data[i]["altitem"] = rowdataaltitem;
		}
		if(altitemcode == '10'){//经营范围换行
			var rowbe = $.trim(data[i]["altbe"]);
			var rowaf = $.trim(data[i]["altaf"]);
			
			var rowdatafinalbe = '<div class="jazz-grid-cell-inner">';//首行缩进
			var rowdatafinalaf = '<div class="jazz-grid-cell-inner">';
			
			for(var y=0;y<rowbe.length/32;y++){
				var rowdata = rowbe.substring(y*32,(y+1)*32);
				rowdata = rowdata +"<br>";
				rowdatafinalbe = rowdatafinalbe + rowdata;
			}
			rowdatafinalbe = rowdatafinalbe + '</div>';
			
			for(var y=0;y<rowaf.length/32;y++){
				var rowdata = rowaf.substring(y*32,(y+1)*32);
				rowdata = rowdata +"<br>";
				rowdatafinalaf = rowdatafinalaf + rowdata;
			}
			rowdatafinalaf = rowdatafinalaf + '</div>';
			
			data[i]["altbe"] = rowdatafinalbe;
			data[i]["altaf"] = rowdatafinalaf;
			 
		}else{
			if(data[i]["describe"] == '2'){
				var htmBefore ='<div class="jazz-grid-cell-inner">'
					+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["recid"]+'\',\''+ data[i]["altitemcode"]+'\',\''+ data[i]["pripid"]+'\',\''+'1'+'\',\''+data[i]["regino"]+'\');"><img src="'+rootPath+'/static/images/other/xiangqing.png" width="25px" height="12px" border="0"/></a>'
					+'</div>';
				data[i]["altbe"] = htmBefore;
				//alert(htmBefore);
				
				var htmAfter ='<div class="jazz-grid-cell-inner">'
					+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["recid"]+'\',\''+ data[i]["altitemcode"]+'\',\''+ data[i]["pripid"]+'\',\''+'2'+'\',\''+data[i]["regino"]+'\');"><img src="'+rootPath+'/static/images/other/xiangqing.png" width="25px" height="12px" border="0"/></a>'
					+'</div>';
				data[i]["altaf"] = htmAfter;
				//alert(htmAfter);
				
				var htmCompare ='<div class="jazz-grid-cell-inner">'
					+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["recid"]+'\',\''+ data[i]["altitemcode"]+'\',\''+ data[i]["pripid"]+'\',\''+'3'+'\',\''+data[i]["regino"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
					+'</div>';
				data[i]["fix"] = htmCompare;
				
			}else if(data[i]["describe"] == '3'){
				
				$.ajax({
					url:'../../../trsquery/queryRenYuanNames.do',
					data:{
						pripid : data[i]["pripid"],
						regino : data[i]["regino"],
						altitemcode : data[i]["altitemcode"]
					},
					type:"get",
					async:false,
					dataType:"json",
					success:function(dataRen){
						var bgtype1 = dataRen[0]["bgtype"];
						var bgtype2 = dataRen[1]["bgtype"];
						
						var persname1 = dataRen[0]["persname"];
						var persname2 = dataRen[1]["persname"];
						
						if(bgtype1 == '1'){
							/* $("div[name = 'altbe']").html(persname1);
							$("div[name = 'altaf']").html(persname2); */
							
							var persname1html ='<div class="jazz-grid-cell-inner">'
								+persname1+'</div>';
							data[i]["altbe"] = persname1html;
							
							var persname2html ='<div class="jazz-grid-cell-inner">'
								+persname2+'</div>';
							data[i]["altaf"] = persname2html;
							
							if(altitemcode == '03'){//负责人变更 人员姓名加超链接
								var fuZeRenBgBefore ='<div class="jazz-grid-cell-inner">'
									+'<a href="javascript:void(0);" title="查看" onclick="viewDataSourcePerson(\''+data[i]["pripid"]+'\',\''+data[i]["regino"]+'\',\''+1+'\');">'+persname1+'</a>'
									+'</div>';
								data[i]["altbe"] = fuZeRenBgBefore;
								
								var fuZeRenBgAfter ='<div class="jazz-grid-cell-inner">'
									+'<a href="javascript:void(0);" title="查看" onclick="viewDataSourcePerson(\''+data[i]["pripid"]+'\',\''+data[i]["regino"]+'\',\''+2+'\');">'+persname2+'</a>'
									+'</div>';
								data[i]["altaf"] = fuZeRenBgAfter;
							}
							
						}else{
							var persname1html ='<div class="jazz-grid-cell-inner">'
								+persname2+'</div>';
							data[i]["altbe"] = persname1html;
							
							var persname2html ='<div class="jazz-grid-cell-inner">'
								+persname1+'</div>';
							data[i]["altaf"] = persname2html;
							
							if(altitemcode == '03'){//负责人变更 人员姓名加超链接

								var fuZeRenBgBefore ='<div class="jazz-grid-cell-inner">'
									+'<a href="javascript:void(0);" title="查看" onclick="viewDataSourcePerson(\''+data[i]["pripid"]+'\',\''+data[i]["regino"]+'\',\''+1+'\');">'+persname2+'</a>'
									+'</div>';
								data[i]["altbe"] = fuZeRenBgBefore;
								
								var fuZeRenBgAfter ='<div class="jazz-grid-cell-inner">'
									+'<a href="javascript:void(0);" title="查看" onclick="viewDataSourcePerson(\''+data[i]["pripid"]+'\',\''+data[i]["regino"]+'\',\''+2+'\');">'+persname1+'</a>'
									+'</div>';
								data[i]["altaf"] = fuZeRenBgAfter;
							}
						}
					}	
				});
			}
		}
		data[i]['altdate'] = data[i]['altdate'].substring(0,10);
	}
	return data;
}

function viewDataSource(recid, altitemcode, pripid,beforeAfter,regino){
	var entname = getUrlParam("entname");
	var altdate = getUrlParam("altdate");
	var alttime = getUrlParam("alttime");
	var title="详细信息:"+entname+" 变更日期:"+altdate+" 第"+alttime+"次变更";
	//var type="view";
	var frameurl = ""+'/trsquery/querynzwzbiangengxxform.do?pripid='+pripid+'&beforeAfter='+beforeAfter+'&altitemcode='+altitemcode+'&regino='+regino;
	createNewWindow(frameurl);
	//createNewWindow(title,frameurl);	
}

var win;

function createNewWindow(frameurl){
	window.open(rootPath+frameurl,page='详细信息');
}

function viewDataSourcePerson(pripid,regino,bgFlag){
	var title="详细信息";
	var frameurl=""+'/trsquery/queryFuZeRenPersonId.do?pripid='+pripid+'&regino='+regino+'&bgFlag='+bgFlag;
	//createNewWindow1(title,frameurl);	
	createNewWindowCK(title,frameurl);	
}

function createNewWindowCK(title,frameurl){
    win = top.jazz.widget({ 
        vtype: 'window', 
        name: 'win', 
        title: title, 
        width: 750, 
        height: 530, 
        modal:true, 
        visible: true ,
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: false,
		minimizable : true, //是否显示最小化按钮
		frameurl: rootPath+frameurl
    });
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
	<div vtype="gridpanel" name="jbxxGrid" height="" width="100%"
				id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="变更信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%" height="80%">
					<div>
						<!-- <div name='recid' text="记录ID" textalign="center" width="0%"></div>
						<div name='openo' text="业务编号" textalign="center" width="0%"></div>
						<div name='pripid' text="主体身份代码" textalign="center" width="0%"></div> -->
						<div name='altdate' text="变更日期" textalign="center" width="7%"></div>
						<div name='altitem' text="变更事项" textalign="center" width="19%"></div>
						<div name='altbe' text="变更前内容" textalign="center" width="35%"></div>
						<div name='altaf' text="变更后内容" textalign="center" width="35%"></div>
						<div name='fix' text="操作" textalign="center" width="4.3%"></div>
					</div>
				</div>
				
				<!-- 表格 -->
				<div vtype="gridtable" name="grid_table" id="grid_table"
					rowrender=""></div>
				<!-- 卡片 -->
				<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
				
				<!-- <div class="but">
					<div name="apptEdit_back_button" vtype="button" text="返回"></div> 
				</div> -->
				<!-- <a href="javascript:history.go(-1)">返回</a> -->
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" id="grid_paginator1"></div>
				
			</div>
		</div>
		<div>
			<div id="font" class="font_title"></div>
		</div>
		<div>
			<div id="formpanel" class="formpanel_table" height="150px">
			
</body>
</html>