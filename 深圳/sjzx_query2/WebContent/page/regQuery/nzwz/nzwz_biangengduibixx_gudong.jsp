<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>股东变更信息</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var pripid;
	function initData(res){
		pripid = res.getAttr("pripid");
		beforeAfter = res.getAttr("beforeAfter");
		altitemcode = res.getAttr("altitemcode");
		regino = res.getAttr("regino");
		loadData(pripid,beforeAfter,altitemcode,regino);
	}
	function loadData(pripid,beforeAfter,altitemcode,regino){
		$('#jbxxGridBefore').gridpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzduibixxdata.do?pripid='+pripid+'&beforeAfter='+beforeAfter+'&altitemcode='+altitemcode+'&regino='+regino);
		$('#jbxxGridBefore').gridpanel('reload');
	}
	
	/*  //维护人员信息按钮 
	function fixColumn(event, obj) {
		var data = obj.data;
		//console.info(data);
		var pripid = parent.pripid;
		//var priPid = getUrlParam("priPid");
		//var entityNo = getUrlParam("entityNo");
		//var economicproperty = getUrlParam("economicproperty"); 
		for(var i=0;i<data.length;i++){
			var htm ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["persname"]+'\',\''+pripid+'\');">'+data[i]["persname"]+'</a>'
				+'</div>';
			data[i]["persname"] = htm;
		}
		return data;
	}
	
	function viewDataSource(persname,pripid){
		var title="详细信息";
		var frameurl = ""+'/trsquery/querynzwzrenyuanxxform.do?persname='+persname+'&pripid='+pripid;
		//createNewWindow1(title,frameurl);	
		createNewWindow(frameurl);	
	}
	
	function createNewWindow(title,frameurl){
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
			frameurl: rootPath+frameurl
	    });
	}   */
	
	
	function fixColumn(event, obj) {
		var data = obj.data;
		//console.info(data);
		var pripid = parent.pripid;
		//var priPid = getUrlParam("priPid");
		//var entityNo = getUrlParam("entityNo");
		//var economicproperty = getUrlParam("economicproperty"); 
		
		for(var i=0;i<data.length;i++){
			var htm ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" data-cerno="'+data[i]["cerno"]+'" onclick="viewDataSource(this);">查看(该操作会记录日志)</a>'
				+'</div>';
			data[i]["cerno"] = htm;
		}
		return data;
	}
	
	function viewDataSource(obj){
		var _this = $(obj);
		var certno = _this.data("cerno");
		_this.html(certno)
		$.ajax({
			url:rootPath+'/reg/showphone.do',
			data:{
				flag : "查询法定代表人证照号码",
				entname : parent.entname,
			},
			type:"post",
			dataType : 'json',
			success:function(data){

			}
		});
	}
</script>
</head>
<body>
     <div id="column_id" width="100%" height="100%" vtype="panel" name="panel" layout="column" layoutconfig="{border: true, width:['*','50%','200']}"> 
        <div> 
            <div vtype="gridpanel" name="jbxxGrid1" height="90%" width="100%"
				id='jbxxGridBefore' dataloadcomplete="" datarender=""
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="详细信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2" layoutconfig="{border: true}" datarender="fixColumn()">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='inv' text="投资人名称" textalign="center" width="22%"></div>
						<div name='invtype' text="投资人类型" textalign="center" width="15%"></div>
						<div name='certype' text="证件类型" textalign="center" width="15%"></div>
						<div name='cerno' text="证件号码" textalign="center" width="18%"></div>
						<div name='subconam' text="认缴出资额(万元)" textalign="center" width="16%"></div>
						<div name='conprop' text="出资比例" textalign="center" width="14%"></div>
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
