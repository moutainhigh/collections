<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>自定义查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
td{
	text-align: center;
}
.jazz-pagearea{
	height: 0px;
}
</style>
<script>

function rowclick(event,data){
	window.location.href="<%=request.getContextPath()%>/page/general/query-panel-right.jsp?priPid="+data.pripid;
}


function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["pripid"];
		data[i]["cz"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\')">详情  </a><a href="javascript:void(0);" onclick="zhixing(\''+pripid+'\')">执行 </a><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">修改 </a><a href="javascript:void(0);" onclick="del(\''+pripid+'\')">删除 </a></div>';
	}
	return data;
}

function detail(pripid){
	winEdit = jazz.widget({
 	     vtype: 'window',
	   	     frameurl: './columnDetail.jsp?priPid='+pripid+'&update=true',
	  			name: 'win',
	  	    	title: '详细',
	  	    	titlealign: 'center',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}



function test(){
	var pzcxfw = {
		 		tabid: 'pzcxfw',	
		 		tabtitle: '配置查询范围',	
		 		tabindex: '1',	
		 		taburl: 'gjcx_zdjcx_addpzcxfw.jsp'
		 };
	var pzcxtj = {
		 		tabid: 'pzcxtj',	
		 		tabtitle: '配置查询条件',	
		 		tabindex: '2',	
		 		taburl: 'gjcx_zdjcx_addpzxctj.jsp'
		 };	
	var pzcxjg = {
		 		tabid: 'pzcxjg',	
		 		tabtitle: '配置查询结果',	
		 		tabindex: '3',	
		 		taburl: 'gjcx_zdjcx_addpzcxjg.jsp'
		 };
	
	var yl = {
		 		tabid: 'yl',	
		 		tabtitle: '预览',	
		 		tabindex: '4',	
		 		taburl: 'gjcx_zdjcx_addyl.jsp'
		 }; 
	$("#tab_name").tabpanel("addTab",pzcxfw);
	$("#tab_name").tabpanel("addTab",pzcxtj);
	$("#tab_name").tabpanel("addTab",pzcxjg);
	$("#tab_name").tabpanel("addTab",yl);
}
$(function(){
	test();
	$('#tab_name').tabpanel('select', 0);
})
var i =null;
function getActiveTabIndex(){
		//可以默认选择
		//$('#tab_name').tabpanel('select', 2);
		i = $('#tab_name').tabpanel('getActiveIndex');//active 得不到
		if(i==0){
		var flag=$("iframe[src='gjcx_zdjcx_addpzcxfw.jsp']")[0].contentWindow.limitRows();
			if(!flag){
				alert("缺少连接查询条件！");
					return ;
			}
		}
		if(i<3){
		i=i+1;
		$('#tab_name').tabpanel('select',i);
	}else{
		//终点tab
		i=0;
		$('#tab_name').tabpanel('select', i);
		}
		
		
		//选择表范围
		if(i==1){
			var checkTableName=$("iframe[src='gjcx_zdjcx_addpzcxfw.jsp']")[0].contentWindow.saveName();
			var theme=$("iframe[src='gjcx_zdjcx_addpzcxfw.jsp']")[0].contentWindow.saveTheme();
			var themeCN=$("iframe[src='gjcx_zdjcx_addpzcxfw.jsp']")[0].contentWindow.saveThemeCN();
			var checkTableNameCN=$("iframe[src='gjcx_zdjcx_addpzcxfw.jsp']")[0].contentWindow.saveNameCN();
			$('#checkTableNameCN').val(checkTableNameCN);
			$('#checkTableName').val(checkTableName);
			$('#theme').val(theme);
			$('#themeCN').val(themeCN);
			//$("iframe[src='gjcx_zdjcx_addpzcxfw.jsp']")[0].contentWindow.addDate();
			//alert(tname);
			$("iframe[src='gjcx_zdjcx_addpzxctj.jsp']")[0].contentWindow.loadtable();
			
			var sqlCondition=$("iframe[src='gjcx_zdjcx_addpzcxfw.jsp']")[0].contentWindow.sqlcondition();
			var sqlConditionCN=$("iframe[src='gjcx_zdjcx_addpzcxfw.jsp']")[0].contentWindow.sqlconditionCN();
			$('#sqlConditionCN').val(sqlConditionCN);
			$('#sqlCondition').val(sqlCondition);
			//alert($('#sqlCondition').val());
			//alert($('#sqlConditionCN').val());
			//alert(checkTableName);
		}
	
		if(i==2){
			$("iframe[src='gjcx_zdjcx_addpzcxjg.jsp']")[0].contentWindow.loadtable();
			var selCondition=$("iframe[src='gjcx_zdjcx_addpzxctj.jsp']")[0].contentWindow.selCondition();
			var selConditionCN=$("iframe[src='gjcx_zdjcx_addpzxctj.jsp']")[0].contentWindow.selConditionCN();
			$('#selCondition').val(selCondition);
			$('#selConditionCN').val(selConditionCN);
			//alert("11111"+$('#selCondition').val());
			//alert($('#selConditionCN').val());
		}
		
		if(i==3){
			$("iframe[src='gjcx_zdjcx_addyl.jsp']")[0].contentWindow.jointSql();
			$("iframe[src='gjcx_zdjcx_addpzcxjg.jsp']")[0].contentWindow.checkSelData();
			var selFields=$("iframe[src='gjcx_zdjcx_addpzcxjg.jsp']")[0].contentWindow.selFields();
			var selFieldsCN=$("iframe[src='gjcx_zdjcx_addpzcxjg.jsp']")[0].contentWindow.selFieldsCN();
			$('#selFields').val(selFields);
			$('#selFieldsCN').val(selFieldsCN);
			//alert($('#selFields').val());
		}
	}
	
	function getBackTabIndex(){
		i = $('#tab_name').tabpanel('getActiveIndex');//active 得不到
		if(i>0){
		i=i-1;
		$('#tab_name').tabpanel('select',i);
		}
	}
	
	
//--------------------------------------------修改-----------------------
function updateSearch(){

			$('#checkTableNameCN').val(",基本信息,人员信息,隶属信息");
			$('#theme').val("sczt");
			$('#themeCN').val("市场主体");
			$('#checkTableName').val(",t_sczt_jbxx,t_sczt_ryxx,t_sczt_lsxx");
			//$('#checkTableName').val(",t_sczt_jbxx,t_sczt_ryxx");
			//$('#checkTableNameCN').val(",基本信息,人员信息");
			$('#sqlCondition').val("-/(/t_sczt_ryxx/1/=/t_sczt_lsxx/1/)/and/-/t_sczt_jbxx/1/=/t_sczt_ryxx/1/-/-/(/t_sczt_ryxx/1/=/t_sczt_lsxx/1/)/and/(/t_sczt_jbxx/1/=/t_sczt_ryxx/1/)/");
			$('#sqlConditionCN').val("-/(/人员信息/1/等于/隶属信息/1/)/ and /-/基本信息/1/等于/人员信息/1/-/-/(/人员信息/1/等于/隶属信息/1/)/ and /(/基本信息/1/等于/人员信息/1/)/");
			$('#selCondition').val("and/(/t_sczt_jbxx/1/=/-/)/-/and/(/t_sczt_ryxx/1/=/-/-/-/and/(/t_sczt_lsxx/1/=/-/)/-/");
			$('#selConditionCN').val("and /(/基本信息/1/等于/-/)/-/and/(/人员信息/1/等于/-/-/-/and/(/隶属信息/1/等于/-/)/-/");
			$('#selFields').val(",t_sczt_ryxx.PRIPID,t_sczt_ryxx.SOURCE,t_sczt_ryxx.ENTID,t_sczt_jbxx.SOURCE,t_sczt_lsxx.SOURCE,t_sczt_lsxx.ENTID");
			$('#selFieldsCN').val(",人员信息.主键,人员信息.编号,人员信息.注册号,基本信息.编号,隶属信息.编号,隶属信息.注册号");
			$("iframe[src='gjcx_zdjcx_addpzcxfw.jsp']")[0].contentWindow.updatepzcxfw();
			$("iframe[src='gjcx_zdjcx_addpzxctj.jsp']")[0].contentWindow.updatepzxctj();
			$("iframe[src='gjcx_zdjcx_addpzcxjg.jsp']")[0].contentWindow.updatepzcxjg();
			$("iframe[src='gjcx_zdjcx_addyl.jsp']")[0].contentWindow.updateyl();
}

</script>


</head>
<body>
	<div onClick="updateSearch()">位置:高级查询>自定义查询>新增</div>
<div id="demo">
		<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="130"  width="100%" height="580" 
		layout="fit"  orientation="top" id="tab_name" >     
		    <ul>    
		        <li></li>    
		    </ul>    
		    <div>    
		        <div id="tab1">   
		        </div> 
		    </div>    
		</div>
	</div>
	<div id="toolbar1" name="toolbar1" vtype="toolbar" location="bottom" align="center">
			<div name="query_button1" vtype="button" text="返回" 
				icon="../query/queryssuo.png" onclick="getBackTabIndex();"></div>
			<div name="query_button" vtype="button" text="下一步" 
				icon="../query/queryssuo.png" onclick="getActiveTabIndex();"></div>
		</div>
	<!-- 主题 -->
	<input id="theme" type="hidden" value=""/>
	<input id="themeCN" type="hidden" value=""/>
	
	<!-- 表名 -->
	<input id="checkTableName" type="hidden" value=""/>
	<input id="checkTableNameCN" type="hidden" value=""/>
	
	<!-- 连接条件 -->
	<input id="sqlCondition" type="hidden" value=""/>
	<input id="sqlConditionCN" type="hidden" value=""/>
	
	<!-- 选择条件 -->
	<input id="selCondition" type="hidden" value=""/>
	<input id="selConditionCN" type="hidden" value=""/>
	
	<!-- 显示字段 -->
	<input id="selFields" type="hidden" value=""/>
	<input id="selFieldsCN" type="hidden" value=""/>
	
</body>
</html>