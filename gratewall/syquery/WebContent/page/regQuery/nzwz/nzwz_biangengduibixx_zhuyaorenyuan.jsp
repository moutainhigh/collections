<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主要人员变更</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var pripid;
	var regino;
	function initData(res){
		pripid = res.getAttr("pripid");
		beforeAfter = res.getAttr("beforeAfter");
		altitemcode = res.getAttr("altitemcode");
		regino = res.getAttr("regino");
		//alert(regino);
		loadData(pripid,beforeAfter,altitemcode,regino);
	}
	function loadData(pripid,beforeAfter,altitemcode,regino){
		$('#jbxxGridBefore').gridpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzduibixxdata.do?pripid='+pripid+'&beforeAfter='+beforeAfter+'&altitemcode='+altitemcode+'&regino='+regino);
		$('#jbxxGridBefore').gridpanel('reload');
	}
	
	//维护人员信息按钮 
	function fixColumn(event, obj) {
		var data = obj.data;
		//console.info(data);
		/* var priPid = getUrlParam("priPid");
		var entityNo = getUrlParam("entityNo");
		var economicproperty = getUrlParam("economicproperty"); */
		for(var i=0;i<data.length;i++){
			var htm ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["cerno"]+'\');">'+data[i]["persname"]+'</a>'
				+'</div>';
			data[i]["persname"] = htm;
		}
		return data;
	}
	
	function viewDataSource(cerno){
		var title="详细信息";
		var frameurl=""+'/trsquery/queryRenYuanPersonId.do?cerno='+cerno+'&pripid='+pripid;
		//createNewWindow1(title,frameurl);	
		createNewWindow(title,frameurl);	
	}
	
	function createNewWindow(title,frameurl){
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
</script>
</head>
<body>
     <div id="column_id" width="100%" height="100%" vtype="panel" name="panel" layout="column" layoutconfig="{border: true, width:['*','50%','200']}"> 
        <div> 
            <div vtype="gridpanel" name="jbxxGrid1" height="90%" width="100%"
				id='jbxxGridBefore' dataloadcomplete="" 
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="详细信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2" layoutconfig="{border: true}">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='persname' text="姓名" textalign="center" width="15%"></div>
						<div name='certype' text="证件类型" textalign="center" width="35%"></div>
						<div name='cerno' text="证件号码" textalign="center" width="30%"></div>
						<div name='post' text="职务" textalign="center" width="20%"></div>
						<!-- <div name='posbrform' text="职务产生方式" textalign="center" width="20%"></div>
						<div name='offhfrom' text="任职起始日期" textalign="center" width="20%"></div>
						<div name='offhto' text="任职截止日期" textalign="center" width="20%"></div>
						<div name='appointunit' text="委派单位" textalign="center" width="20%"></div> -->
					</div>
				</div>
				<!-- 表格 -->
				<div vtype="gridtable" name="grid_table" id="grid_table"
					rowrender=""></div>
				<!-- 分页 -->
				<!-- <div vtype="paginator" name="grid_paginator" id="grid_paginator1"></div> -->
			</div> 
        </div> 
    </div> 
</body>
</html>
