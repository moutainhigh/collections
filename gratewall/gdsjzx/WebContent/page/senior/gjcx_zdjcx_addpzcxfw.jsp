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

.boxlist-highlight {
    background-color: #E7E9EC;
}
li{
list-style-type:none;
}
#condition td{
	border-top:3px solid #fff;
	border-left:3px solid #fff;
	border-bottom:3px solid #fff;
	border:3px solid #fff;
}
</style>

<script>
//主题容器用来存值
 var bigmap=new Object();

//主题容器用来存值
 var bigmapCN=new Object();
 
//选择的表名  ---多选
var tableName="";
var tableNameCN="";		
var arrayN="";
var dataSel=new Array();
var saveTableName="";
//确认保存的主题
var saveThemeName="";
var saveThemeNameCN="";
var saveTableNameCN="";
$(function(){
	queryTabUrl();
});

function queryTabUrl() {
function showSel(e){
}


$.ajax({
		url:'http://localhost:8080/gdsjzx/report/year.do',
		async:false,
		data:{
			dataSel:dataSel,
		},
		type:"post",
		dataType : 'json',
		success:function(data){
			var st = '<option vl="12315" select="N" onClick="makeSel(this)">12315</option>';
			var st1 = '<option vl="ndbg" select="N" onClick="makeSel(this)">年度报告</option>';
			var st2 = '<option vl="sczt" select="N" onClick="makeSel(this)">市场主体</option>';
			$('#listId1').append(st);
			$('#listId1').append(st1);
			$('#listId1').append(st2);
		/* 	var st3 = '<option vl="12315" select="N" onClick="makeSel(this)">12315</option>';
			var st4 = '<option vl="ndbg" select="N" onClick="makeSel(this)">年度报告</option>';
			var st5 = '<option vl="sczt" select="N" onClick="makeSel(this)">市场主体</option>';
			$('#listId1').append(st3);
			$('#listId1').append(st4);
			$('#listId1').append(st5);
			var st11 = '<option vl="12315" select="N" onClick="makeSel(this)">12315</option>';
			var st12 = '<option vl="ndbg" select="N" onClick="makeSel(this)">年度报告</option>';
			var st22 = '<option vl="sczt" select="N" onClick="makeSel(this)">市场主体</option>';
			$('#listId1').append(st11);
			$('#listId1').append(st12);
			$('#listId1').append(st22);
			var st121 = '<option vl="12315" select="N" onClick="makeSel(this)">12315</option>';
			var st122 = '<option vl="ndbg" select="N" onClick="makeSel(this)">年度报告</option>';
			var st222 = '<option vl="sczt" select="N" onClick="makeSel(this)">市场主体</option>';
			$('#listId1').append(st121);
			$('#listId1').append(st122);
			$('#listId1').append(st222); */
		}
	});
}

		//单击主题
		var theme="";
		var themeCN="";
		function makeSel(e){
			var name=$(e).html();
			var val=$(e).attr("vl");
				theme=val;
				themeCN=name;
				tableName="";
				tableNameCN="";
			//	$(e).parent().find('option').removeClass("boxlist-highlight");
			//	$(e).addClass("boxlist-highlight");
				//1，选择完之后重新选择主题。2，刚开始选择
				mapTableName();
				checkSel();
				checkSelTable();
		}
		
		//回显选中主题名
		function backSel(){
			var len=$('#listId1').find('option').length;
			$('#listId1').find('option').removeClass("boxlist-highlight");
				for(var p=0;p<len;p++){
					if($('#listId1 option:eq('+p+')').html()==themeCN){
						$('#listId1 option:eq('+p+')').addClass("boxlist-highlight");
						$('#listId1 option:eq('+p+')').attr("select","Y");
					}
				}
		}
		//获取主题下面的所有表名
		function checkSel(){
			saveThemeName=theme;
			saveThemeNameCN=themeCN;
			//获取主题下面的表名
			$.ajax({
				url:'http://localhost:8080/gdsjzx/report/year.do',
				async:false,
				data:{
					theme:theme,
				},
				type:"post",
				dataType : 'json',
				success:function(data){
					$('#listId2 option').remove();
					$('#listId3 option').remove();
					/*
						加载对应主题的表
					*/
					 //这里缺少一个循环遍历表名塞到map里面
				 //alert(map['t_sczt_jbxx']);
					   
					   
					//alert(theme);
				if(theme=="sczt"){
				var map;
				var mapCN;
					if(bigmap['sczt']==undefined){
						//表名容器用来存值
 						map=new Object();
 						mapCN=new Object();
					}else{
						map=bigmap['sczt'];
						mapCN=bigmapCN['市场主体'];
					};
					if(map['t_sczt_jbxx']==undefined){
					 map['t_sczt_jbxx']='noCheck';
					 mapCN['基本信息']='noCheck';
					 var st = '<option vl="t_sczt_jbxx" select="N" onDblClick="dbSelTable(this)">基本信息</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map['t_sczt_jbxx']=='noCheck'){
					 	var st = '<option vl="t_sczt_jbxx" select="N" onDblClick="dbSelTable(this)">基本信息</option>';
						 $('#listId2').append(st);
					 }else{
						//map['t_sczt_jbxx']='check';
						$('#listId2 option[vl="t_sczt_jbxx"]').remove();
					  var st = '<option vl="t_sczt_jbxx" select="N" onDblClick="dbSelTable(this)">基本信息</option>';
					 $('#listId3').append(st);
						 }
					 }
					 
					 if(map['t_sczt_ryxx']==undefined){
					 map['t_sczt_ryxx']='noCheck';
					  mapCN['人员信息']='noCheck';
					 var st = '<option vl="t_sczt_ryxx" select="N" onDblClick="dbSelTable(this)">人员信息</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map['t_sczt_ryxx']=='noCheck'){
					 	var st = '<option vl="t_sczt_ryxx" select="N" onDblClick="dbSelTable(this)">人员信息</option>';
						 $('#listId2').append(st);
					 }else{
						//map['t_sczt_ryxx']='check';
						$('#listId2 option[vl="t_sczt_ryxx"]').remove();
					  var st = '<option vl="t_sczt_ryxx" select="N" onDblClick="dbSelTable(this)">人员信息</option>';
					 $('#listId3').append(st);
						 }
					 }
					 
					   if(map['t_sczt_lsxx']==undefined){
					 map['t_sczt_lsxx']='noCheck';
					 mapCN['隶属信息']='noCheck';
					 var st = '<option vl="t_sczt_lsxx" select="N" onDblClick="dbSelTable(this)">隶属信息</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map['t_sczt_lsxx']=='noCheck'){
					 	var st = '<option vl="t_sczt_lsxx" select="N" onDblClick="dbSelTable(this)">隶属信息</option>';
						 $('#listId2').append(st);
						// ,t_sczt_ryxx.PRIPID,t_sczt_ryxx.SOURCE,t_sczt_ryxx.ENTID,t_sczt_jbxx.SOURCE,t_sczt_lsxx.SOURCE,t_sczt_lsxx.ENTID
					 }else{
						map['t_sczt_lsxx']='check';
						$('#listId2 option[vl="t_sczt_lsxx"]').remove();
					  var st = '<option vl="t_sczt_lsxx" select="N" onDblClick="dbSelTable(this)">隶属信息</option>';
					 $('#listId3').append(st);
						 }
					 }
						bigmap['sczt']=map;
						bigmapCN['市场主体']=mapCN;
					}
					
					
					if(theme=="12315"){
					var map;
					var mapCN;
					if(bigmap['12315']==undefined){
						//表名容器用来存值
 						map=new Object();
 						mapCN=new Object();
					}else{
						map=bigmap['12315'];
						mapCN=bigmapCN['12315'];
					};
					
					if(map['t_12315_jbxx']==undefined){
					 map['t_12315_jbxx']='noCheck';
					 mapCN['基本信息']='noCheck';
					 var st = '<option vl="t_12315_jbxx" select="N" onDblClick="dbSelTable(this)">基本信息</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map['t_12315_jbxx']=='noCheck'){
					 	var st = '<option vl="t_12315_jbxx" select="N" onDblClick="dbSelTable(this)">基本信息</option>';
						 $('#listId2').append(st);
					 }else{
						//map['t_12315_jbxx']='check';
					  var st = '<option vl="t_12315_jbxx" select="N" onDblClick="dbSelTable(this)">基本信息</option>';
					 $('#listId3').append(st);
						 }
					 }
					 
					 if(map['t_12315_ryxx']==undefined){
					 map['t_12315_ryxx']='noCheck';
					 mapCN['人员信息']='noCheck';
					 var st = '<option vl="t_12315_ryxx" select="N" onDblClick="dbSelTable(this)">人员信息</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map['t_12315_ryxx']=='noCheck'){
					 	var st = '<option vl="t_12315_ryxx" select="N" onDblClick="dbSelTable(this)">人员信息</option>';
						 $('#listId2').append(st);
					 }else{
					  var st = '<option vl="t_12315_ryxx" select="N" onDblClick="dbSelTable(this)">人员信息</option>';
					 $('#listId3').append(st);
						 }
					 }
					   if(map['t_12315_lsxx']==undefined){
					 map['t_12315_lsxx']='noCheck';
					 mapCN['隶属信息']='noCheck';
					 var st = '<option vl="t_12315_lsxx" select="N" onDblClick="dbSelTable(this)">隶属信息</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map['t_12315_lsxx']=='noCheck'){
					 	var st = '<option vl="t_12315_lsxx" select="N" onDblClick="dbSelTable(this)">隶属信息</option>';
						 $('#listId2').append(st);
					 }else{
						map['t_12315_lsxx']='check';
					  var st = '<option vl="t_12315_lsxx" select="N" onDblClick="dbSelTable(this)">隶属信息</option>';
					 $('#listId3').append(st);
						 }
					 }
					bigmap['12315']=map;
					bigmapCN['12315']=mapCN;
					}
					if(theme=="ndbg"){
					var map;
					var mapCN;
						if(bigmap['ndbg']==undefined){
							//表名容器用来存值
	 						map=new Object();
	 						mapCN=new Object();
						}else{
							map=bigmap['ndbg'];
							 mapCN=bigmapCN['年度报告'];
						};
					if(map['t_ndbg_jbxx']==undefined){
					 map['t_ndbg_jbxx']='noCheck';
					 mapCN['基本信息']='noCheck';
					 var st = '<option vl="t_ndbg_jbxx" select="N" onDblClick="dbSelTable(this)">基本信息</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map['t_ndbg_jbxx']=='noCheck'){
					 	var st = '<option vl="t_ndbg_jbxx" select="N" onDblClick="dbSelTable(this)">基本信息</option>';
						 $('#listId2').append(st);
					 }else{
						map['t_ndbg_jbxx']='check';
					  var st = '<option vl="t_ndbg_jbxx" select="N" onDblClick="dbSelTable(this)">基本信息</option>';
					 $('#listId3').append(st);
						 }
					 }
					 
					 if(map['t_ndbg_ryxx']==undefined){
					 map['t_ndbg_ryxx']='noCheck';
					  mapCN['人员信息']='noCheck';
					 var st = '<option vl="t_ndbg_ryxx" select="N" onDblClick="dbSelTable(this)">人员信息</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map['t_ndbg_ryxx']=='noCheck'){
					 	var st = '<option vl="t_ndbg_ryxx" select="N" onDblClick="dbSelTable(this)">人员信息</option>';
						 $('#listId2').append(st);
					 }else{
						map['t_ndbg_ryxx']='check';
					  var st = '<option vl="t_ndbg_ryxx" select="N" onDblClick="dbSelTable(this)">人员信息</option>';
					 $('#listId3').append(st);
						 }
					 }
					 
					   if(map['t_ndbg_lsxx']==undefined){
					 map['t_ndbg_lsxx']='noCheck';
					   mapCN['隶属信息']='noCheck';
					 var st = '<option vl="t_ndbg_lsxx" select="N" onDblClick="dbSelTable(this)">隶属信息</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map['t_ndbg_lsxx']=='noCheck'){
					 	var st = '<option vl="t_ndbg_lsxx" select="N" onDblClick="dbSelTable(this)">隶属信息</option>';
						 $('#listId2').append(st);
					 }else{
						map['t_ndbg_lsxx']='check';
					  var st = '<option vl="t_ndbg_lsxx" select="N" onDblClick="dbSelTable(this)">隶属信息</option>';
					 $('#listId3').append(st);
						 }
					 }
					bigmap['ndbg']=map;
					bigmapCN['年度报告']=mapCN;
					}
				}
			});
		}
		//回显主体下的表名
		function backSelTable(tableName){
			saveThemeName=theme;
			saveThemeNameCN=themeCN;
			//alert(tableName);
			var tableLen=tableName.split(",");
			
			var l=$('#listId2').find('option');
			
			//获取主题下面的表名
			
			
			for(var t=1;t<tableLen.length;t++){
				for(var tt=0;tt<l.length;tt++){
					var pp=$('#listId2 option:eq('+tt+')').attr("vl");
					var html=$('#listId2 option:eq('+tt+')').html();
					if(tableLen[t]==pp){
						$('#listId3').append($('#listId2 option:eq('+tt+')').clone(true));
						$('#listId2 option:eq('+tt+')').remove();
						bigmap[theme][pp]='check';
						bigmapCN[themeCN][html]='check';
					}
				}
			}
		}

function saveName(){
	return saveTableName;
}

function saveNameCN(){
	return saveTableNameCN;
}

function saveTheme(){
	return saveThemeName;
}
function saveThemeCN(){
	return saveThemeNameCN;
}

	//选择的表名  ---多选
function dbSelTable(e){
	//alert(e.html());
	var name=$(e).html();
	var val=$(e).attr("vl");
	if(bigmap[theme][val]=='noCheck'){	
		bigmap[theme][val]='check';
		bigmapCN[themeCN][name]='check';
		 $('#listId3').append($(e));
		}else{
			bigmap[theme][val]='noCheck';
			bigmapCN[themeCN][name]='noCheck';
		 $('#listId2').append($(e));
		}
		//alert(theme);
		mapTableName();
		checkSelTable();
	}
	
	//从map里面加载表名到全局变量里面---
	function mapTableName(){
		var ccmap=bigmap[theme];
			tableName="";
				//如果遍历map
			for(var prop in ccmap){
			 //   if(ccmap.hasOwnProperty(prop)){
			    if(ccmap[prop]=='check'){
			    	tableName=tableName+","+prop;
			 //   }
			    }
			}
		var cnmap=bigmapCN[themeCN];
		
		tableNameCN="";
			//如果遍历map
		for(var prop in cnmap){
		    if(cnmap.hasOwnProperty(prop)){
		    if(cnmap[prop]=='check'){
		    	tableNameCN=tableNameCN+","+prop;
		   		 }
		    }
		}	
}
	//获取选择到的表名
function checkSelTable(){
			/*
				加载选择的表
			*/
			saveTableName=tableName;
			arrayN=saveTableName.split(",");
			saveTableNameCN=tableNameCN;
			arrayNCN=saveTableNameCN.split(",");
			
						
			//增加到最低几行
			var limitRows=arrayN.length-2;
			var l=$('#condition tr:gt(0)').length;
			if(l<limitRows){
			for(l;l<limitRows;l++){
				addRow();
				}
			}else if(l>limitRows){
				$('#condition tr:gt(1)').remove();
			}
			
			
			var con="";
			for(var i=1;i<arrayN.length;i++){
				con=con+"<option value='"+arrayN[i]+"'>"+arrayNCN[i]+"</option>";
			}
			//zheli
			for(var i=1;i<$("#condition tr").length;i++){
				$("#condition tr:eq("+i+") td:eq(2) select option").remove();
				$("#condition tr:eq("+i+") td:eq(5) select option").remove();
				$("#condition tr:eq("+i+") td:eq(2) select").append(con);
				$("#condition tr:eq("+i+") td:eq(5) select").append(con);
			}
			$('#tabName').val(tableName);
			
		
}


	//选择表加载表字段
	function selData(e){
	var data= $(e).attr("selected","selected").val();
	loadData(data,$(e).parent().parent().find("td:eq(3)").find("select"));
	}
	
	function selDataOther(e){
	var data= $(e).attr("selected","selected").val();
	loadData(data,$(e).parent().parent().find("td:eq(6)").find("select"));
	}
	
	//请求表字段
	function loadData(data,tab){
		$.ajax({
		url:'http://localhost:8080/gdsjzx/report/year.do',
		async:false,
		data:{
			tableName:data,
		},
		type:"post",
		dataType : 'json',
		success:function(data){
		//$('#listId2 > ul li').remove();
			/*
				加载对应主题的表
			*/
			var con="";
			con=con+"<option>1</option>";
			tab.find("option").remove();
			tab.append(con);
			}
		});
	}
	
	function addRow(){
			$('#condition').append($('#condition tr:eq(1)').clone(true));
			$('#condition tr:gt(1) td:last input').css("display","inline");
	}
	function delRow(e){
			$(e).parent().parent().remove();
	}
	function sqlcondition(){
		var sql ="";
		for(var i=1;i<$('#condition tr').length;i++){
			//alert(i);
			for(var j=0;j<$('#condition tr:eq(1) td').length-1;j++){
			var data=$('#condition tr:eq('+i+') td:eq('+j+') select').val();
				if($.trim(data)==""){
				sql=sql+"-"+"/";
				}else{
				sql=sql+data+"/";
				}
			}
		}
		return sql;
	}
	
	function sqlconditionCN(){
			var sql ="";
		for(var i=1;i<$('#condition tr').length;i++){
			//alert(i);
			for(var j=0;j<$('#condition tr:eq(1) td').length-1;j++){
			var data=$('#condition tr:eq('+i+') td:eq('+j+') select option:selected').text();
				if($.trim(data)==""){
				sql=sql+"-"+"/";
				}else{
				sql=sql+data+"/";
				}
			}
			
		}
		return sql;
	}
	
function resetSel1() {
		$('#listId2 > ul li').remove();
		resetSel2();
		tableName="";
}

function resetSel2() {
		$('#listId3 > ul li').remove();
}

//连接条件最低行数
function limitRows(){
	//最低条数=表-1-1一个空格
	var limitRows=arrayN.length-2;
	//实际有的条数=总条数-1
	var trLen=$('#condition tr').length-1;
	if(limitRows<=trLen){
		return true;
	};
	return false;
}

function hasRows(){
	//最低条数=表-1-1一个空格
	var limitRows=arrayN.length-2;
	
	return limitRows;
}



//----------------------------------------修改---------------------
function updatepzcxfw(){
	theme=$('#theme', parent.document).val();
	themeCN=$('#themeCN', parent.document).val();
	var checkTableName=$('#checkTableName', parent.document).val();
	var checkTableNameCN=$('#checkTableNameCN', parent.document).val();
	var sqlCondition=$('#sqlCondition', parent.document).val();
	var sqlConditionCN=$('#sqlConditionCN', parent.document).val();
	
	tableName=checkTableName;
	tableNameCN=checkTableNameCN;
	
    saveThemeName=theme;
    saveThemeNameCN=themeCN;
	//alert(theme+"===="+themeCN);
	checkSel();
	checkSelTable();
	backSel();
	backSelTable(tableName);
	var sqlConditions=sqlCondition.split("/");
	var sqlConditionCNs=sqlConditionCN.split("/");
	
		for(var p=1;p<(sqlConditions.length+1)/9;p++){
			$('#condition tr:eq(1)').clone(true).appendTo($('#condition'));
			$('#condition tr:gt(1) td:last input').css("display","inline");	
			}
			
		//alert("p");
		for(var k=0;k<sqlConditions.length-1;k++){
			if(sqlConditions[k]=="-"){
				sqlConditions[k]=" ";
			}
			if(sqlConditionCNs[k]=="-"){
				sqlConditionCNs[k]=" ";
			}
			$('#condition tr:eq('+(parseInt((k)/8)+1)+') td:eq('+k%8+') select').find("option[value='"+sqlConditions[k]+"']").attr("selected","selected");
		}
	
}

/* $(function(){
	 var map = new Map();
	 map.put("a", "aaa");
	 map.put("b","bbb");
	 map.put("cc","cccc");
	 map.put("c","ccc");
	 map.remove("cc");
	 var array = map.keySet();
	 for(var i in array) {
	 document.write("key:(" + array[i] +") <br>value: ("+map.get(array[i])+") <br>");
}
}) */
 	function simOptionClick4IE(type){ 
		var evt=window.event ; 
		var selectObj=evt?evt.srcElement:null;
		// IE Only 
		//alert((evt.offsetY > selectObj.offsetHeight || evt.offsetY<0 ));
		/* if (evt && selectObj && evt.offsetY && evt.button!=2 
		&& (evt.offsetY > selectObj.offsetHeight || evt.offsetY<0 ) )  */
		if (evt && selectObj && evt.offsetY && evt.button!=2 ) {
		
		// 记录原先的选中项 
		var oldIdx = selectObj.selectedIndex;
		alert(selectObj.selectedIndex);
		setTimeout(function(){ 
		var option=selectObj.options[selectObj.selectedIndex]; 
		// 此时可以通过判断 oldIdx 是否等于 selectObj.selectedIndex 
		// 来判断用户是不是点击了同一个选项,进而做不同的处理. 
		if(type=="theme"){
			makeSel(option);
		}else{
			if(type=="table"){
				dbSelTable(option);
			}
		}
		
		}, 60); 
		} 
		}
</script>
</head>
<body>

<style type="text/css">
.normal {
    background: white none repeat scroll 0 0;
    color: Blue;
    font-size: 14px;
    font-weight: bold;
    line-height: 71%;
}

</style>
<div id="tab2">    
		            <div id="column_id1" width="100%" height="20%" vtype="panel"  
				     	 layout="column" layoutconfig="{width: ['30%','30%','*','30%'],border:false}">
				    	 <div>
				    		<div name="c1" vtype="panel" title="主题信息" titledisplay="true" height="222">
				    			<select onclick="simOptionClick4IE('theme')" style="width:100%;height:100%" size="4" name="listId1" id="listId1" class="normal"> </select>
				    		</div>
				    	</div>
				    	<div>
				    		<div name="c2" vtype="panel" title="表信息" titledisplay="true" height="222">
				    			 <select onclick="simOptionClick4IE('table')" style="width:100%;height:100%" size="4" name="listId2" id="listId2" class="normal" title="双击可实现右移" multiple=”multiple”> </select>  
				    		</div>
				    	</div>
				    	<div>
				    		<div name="c3" vtype="panel" title="" titledisplay="false" height="222">
				    			<div align="center" style="vertical-align: middle;padding:25px;">
						    		<input value=">" type="button" id="btnCRight"  class="btn"/>
									<br/><br/>
		 							<input value=">>" type="button" id="btnCRightAll"  class="btn"/>
		 							<br/><br/>
		 							<input value="<<" type="button" id="btnCLeftAll" class="btn"/>
		 							<br/><br/>
		 							<input value="<" type="button" id="btnCLeft" class="btn"/>
	 							</div>
				    		</div>
				    	</div>
				    	<div>
				    		<div name="c4" vtype="panel" title="选择范围" titledisplay="true" height="222">
				    			<select style="width:100%;height:100%" size="4" name="listId3" id="listId3" class="normal" title="双击可实现左移" multiple=”multiple”></select> 
				    		</div>
				    	</div>
				    </div> 
					
		        </div>
		
		
	

<input type="hidden" id="tabName"/>		

<div style="margin-top:15px;background:#EEF5FD;border:1px solid #000;width:100%;height:57%" >
	<p>数据表查询条件 </p>
	<table id="condition" style="width:100%;">
			<tr><td>条件</td><td>括弧</td><td>数据表</td><td>表字段</td><td>连接关系</td><td>数据表</td><td>表字段</td><td>括弧</td><td>操作</td></tr>
		<tr>
			<td><select><option value=" "> </option><option value="and"> and </option><option value="or"> or </option></select></td>
			<td><select><option value=" "> </option><option value="(">(</option><option value="((">((</option><option value="(((">(((</option></select></td>
			<td><select onChange="selData(this)"><option></option></select></td>
			<td><select ><option></option></select></td>
			<td><select><option value="=">等于</option></select></td>
			<td><select onChange="selDataOther(this)"></select></td>
			<td><select ><option></option></select></td>
			<td><select><option value=" "> </option><option value=")">)</option><option value="))">))</option><option value=")))">)))</option></option></select></td>
			<td width="160px">
				<input type="button" onClick="addRow()" value="增加" style="background: #006599;color:#fff"/>
				<input style="display:none;" type="button" onClick="delRow(this)" value="删除" style="background: #006599;color:#fff"/>
			</td>
		</tr>
	</table>
</div>
</body>
</html>