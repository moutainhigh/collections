<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>配置查询结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<style type="text/css">
td {
	text-align: center;
}

.jazz-pagearea {
	height: 0px;
}

.boxlist-highlight {
	background-color: #E7E9EC;
}

li {
	list-style-type: none;
}

#condition td {
	border-top: 3px solid #fff;
	border-left: 3px solid #fff;
	border-bottom: 3px solid #fff;
	border: 3px solid #fff;
}
</style>
<script>
//主题容器用来存值
 var bigmap=new Object();

//主题容器用来存值
 var bigmapCN=new Object();
 
var tabName="";
var tabNameCN="";

//单击选择表名
var tName;
var tNameCN;

//{aaaa1:aaaa1,bbbb1,cccc1}{表名:字段}

var fieldsName="";
var fieldsNameCN="";
function loadtable(){
	tabName=$('#checkTableName', parent.document).val();
	tabNameCN=$('#checkTableNameCN', parent.document).val();
	//alert(tabName);
	var TName=tabName.split(",");
	var TNameCN=tabNameCN.split(",");
	$('#listId1').find('option').remove();
		for(var i=1;i<TName.length;i++){
				var st = '<option vl="'+TName[i]+'"  onClick="makeSel(this)">'+TNameCN[i]+'</option>';
				$('#listId1').append(st);
			}
}


	function makeSel(e){
		    tNameCN=$(e).html();
			tName=$(e).attr("vl");
			tableName="";
			tableNameCN="";
			
			checkSelTable();
	}
	
	function backSel(){
				
			//$('#listId1 option[vl="'+tName+'"]').parent().find('li').removeClass("boxlist-highlight");
			//$('#listId1 option[vl="'+tName+'"]').addClass("boxlist-highlight");
			$('#listId1 option[vl="'+tName+'"]').attr("selected","selected");
			
	}
	
		//获取表下面的所有字段
		function checkSelTable(){
			if(tName==""){
				return;
			}
			//获取表下面的字段
			$.ajax({
				url:'http://localhost:8080/gdsjzx/report/year.do',
				async:false,
				data:{
					tableName:tName,
				},
				type:"post",
				dataType : 'json',
				success:function(data){
					$('#listId2 option').remove();
					//$('#listId3 option').remove();
					/*
						加载对应主题的表
					*/
					 //这里缺少一个循环遍历表名塞到map里面
					var map;
					var mapCN;
					if(bigmap[tName]==undefined){
						//表名容器用来存值
 						map=new Object();
 						mapCN=new Object();
					}else{
						map=bigmap[tName];
						mapCN=bigmapCN[tNameCN];
					};
				if(map[tName+'.PRIPID']==undefined){
						 map[tName+'.PRIPID']='noCheck';
						 mapCN[tNameCN+'.主键']=tName+'.PRIPID';
						 var st = '<option vl="'+tName+'.PRIPID'+'" select="N" ondblclick="dbSelTable(this)">'+tNameCN+'.主键'+'</option>';
						 $('#listId2').append(st);
					 }else{
					 if(map[tName+'.PRIPID']=='noCheck'){
					 	 var st = '<option vl="'+tName+'.PRIPID'+'" select="N" ondblclick="dbSelTable(this)">'+tNameCN+'.主键'+'</option>';
						 $('#listId2').append(st);
						 
					 }else{
						$('#listId2 option[vl="'+tName+'.PRIPID'+'"]').remove();
						
						if($('#listId3 option[vl="'+tName+'.PRIPID'+'"]')==undefined){
						  var st = '<option vl="'+tName+'.PRIPID'+'" select="N" ondblclick="dbSelTable(this)">'+tNameCN+'.主键'+'</option>';
					    $('#listId3').append(st);
							}
					   
						 }
					 }
					 
					 if(map[tName+'.SOURCE']==undefined){
					 map[tName+'.SOURCE']='noCheck';
					  mapCN[tNameCN+'.编号']=tName+'.SOURCE';
					 var st = '<option vl="'+tName+'.SOURCE'+'" select="N" ondblclick="dbSelTable(this)">'+tNameCN+'.编号'+'</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map[tName+'.SOURCE']=='noCheck'){
					 	var st = '<option vl="'+tName+'.SOURCE'+'" select="N" ondblclick="dbSelTable(this)">'+tNameCN+'.编号'+'</option>';
						 $('#listId2').append(st);
					 }else{
						//map['t_sczt_ryxx']='check';
						$('#listId2 option[vl="'+tName+'.SOURCE'+'"]').remove();
						if($('#listId3 option[vl="'+tName+'.SOURCE'+'"]')==undefined){
							var st = '<option vl="'+tName+'.SOURCE'+'" select="N" ondblclick="dbSelTable(this)">'+tNameCN+'.编号'+'</option>';
							$('#listId3').append(st);
							}
						 }
					 }
					 
					   if(map[tName+'.ENTID']==undefined){
					 map[tName+'.ENTID']='noCheck';
					 mapCN[tNameCN+'.注册号']=tName+'.ENTID';
					 var st = '<option vl="'+tName+'.ENTID'+'" select="N" ondblclick="dbSelTable(this)">'+tNameCN+'.注册号'+'</option>';
					 $('#listId2').append(st);
					 }else{
					 if(map[tName+'.ENTID']=='noCheck'){
					 	var st = '<option vl="'+tName+'.ENTID'+'" select="N" ondblclick="dbSelTable(this)">'+tNameCN+'.注册号'+'</option>';
						 $('#listId2').append(st);
					 }else{
						map[tName+'.ENTID']='check';
						$('#listId2 option[vl="'+tName+'.ENTID'+'"]').remove();
						if($('#listId3 option[vl="'+tName+'.ENTID'+'"]')==undefined){
						  var st = '<option vl="'+tName+'.ENTID'+'" select="N" ondblclick="dbSelTable(this)">'+tNameCN+'.注册号'+'</option>';
						 $('#listId3').append(st);
						}
						 }
					 }
						bigmap[tName]=map;
						bigmapCN[tNameCN]=mapCN;
					}
				});
			}
	
//选择的表名  ---多选
function dbSelTable(e){
	//alert(e.html());
	var name=$(e).html();
	var val=$(e).attr("vl");
	
	var tabIndex=val.indexOf(".");
	tName=val.substring(0,tabIndex);
	
	if(bigmap[tName][val]=='noCheck'){		
		bigmap[tName][val]='check';
		//bigmapCN[tNameCN][name]='check';
		 $('#listId3').append($(e));
		}else{
			bigmap[tName][val]='noCheck';
			 if($('#listId2 option:eq(0)').attr('vl').indexOf(tName)!=-1){
				 $('#listId2').append($(e));
			 }else{
			 	 $(e).remove();
			 }
		}
		//createOption1(bigmap[tName],bigmapCN[tNameCN]);
		//checkSelData();
		//$(e).remove();
	}
		
			function createOption1(map,mapCN){
		/*层级关系 
		 bigmap[tName]=map;
		 bigmapCN[tNameCN]=mapCN;
		 map[tName+'.PRIPID']='noCheck';
		 mapCN[tNameCN+'.主键']=tName+'.PRIPID'; */
			$('#listId2 option').remove();
			
			
			for(var mapkey in map){
				for(var mapkeyCN in mapCN){
					if(mapCN[mapkeyCN]=mapkey){
				var html='<option ondblclick="dbSelTable(this)" '+
					'vl="'+mapkey+'">'+mapkeyCN+'</option>';	
					//if(map[mapkey]=='noCheck'){
						$('#listId2').append($(html));
					//	}				
					}
				}
			
			}
		}
		
		function createOption(map,mapCN){
		/*层级关系 
		 bigmap[tName]=map;
		 bigmapCN[tNameCN]=mapCN;
		 map[tName+'.PRIPID']='noCheck';
		 mapCN[tNameCN+'.主键']=tName+'.PRIPID'; */
		 //	alert(mapCN);
		 //$('#listId3 option').remove();
			//alert();
			
		/* 	for(var p=0;p<$('#listId3 option').length;p++){
				var value=$('#listId3 option:eq('+p+')');
					if(value.attr('vl').indexOf(tName)){
						value.remove();
					}
			} */
			$('#listId2 option').remove();
			for(var key in mapCN){
			
			var html='<option ondblclick="dbSelTable(this)" '+
					'vl="'+mapCN[key]+'">'+key+'</option>';
			//noCheck没有选择，在表2里面
			   if(map[mapCN[key]]=='noCheck'){
			   		if($('#listId3 option[vl="'+mapCN[key]+'"]')!=undefined){
			   		$('#listId3 option[vl="'+mapCN[key]+'"]').remove();
			   		}
					$('#listId2').append($(html));
				}else{
				if($('#listId2 option[vl="'+mapCN[key]+'"]')!=undefined){
			   		$('#listId2 option[vl="'+mapCN[key]+'"]').remove();
			   		}
					$('#listId3').append($(html));
				}
			}
		}
			
		//选择的字段名
		function checkSelData(selFields,selFieldsCN){
				for(var bigkey in bigmap){ 
				   for(var key in bigmap[bigkey]){
				   for(var s=1;s<selFields.length;s++){
				   	if(selFields[s]==key){
				   	//<option ondblclick="dbSelTable(this)" select="N" vl="t_sczt_lsxx.SOURCE">隶属信息.编号</option>
				   		$('#listId3').append($('<option ondblclick="dbSelTable(this)" select="N" vl="'+selFields[s]+'">'+selFieldsCN[s]+'</option>').clone(true));
						$('#listId2 option[vl="'+selFields[s]+'"]').remove();
						bigmap[bigkey][selFields[s]]='check';
						//alert("进来了");
						continue;
						}
				   	}
				   }  
				}  
				
				/* for(var bigkeyCN in bigmapCN){ 
				   for(var keyCN in bigmapCN[bigkeyCN]){
				   for(var s=1;s<selFields.length;s++){
				   	if(selFields[s]==keyCN){
				   		$('#listId3').append($('<option[vl="'+selFields[s]+'"]></option>').clone(true));
						$('#listId2 option[vl="'+selFields[s]+'"]').remove();
						bigmap[bigkeyCN][selFields[s]]='check';
						//alert("进来了");
						continue;
						}
				   	}
				   }  
				}   */
				//checkSelTable();
			//	var data = eval("(" + bigmap + ")");
				/* for(var k=0;k<bigmap.length;k++){
					alert("ll");
					for(var g=0;g<bigmap[k].length;g++){
						alert("value="+bigmap[k][g]);
					}
				} */
		}
	function selFields(){
		return fieldsName;
	}
	function selFieldsCN(){
		return fieldsNameCN;
	}
	function resetSel1() {
		$('#listId2 option').remove();
		resetSel2();
		fieldsName="";
	}
	function resetSel2() {
			$('#listId3 option').remove();
	}

//---------------------------------------------修改------------------------

function updatepzcxjg(){
		loadtable();
		fieldsName=$('#selFields', parent.document).val();
		fieldsNameCN=$('#selFieldsCN', parent.document).val();
		var selFields=fieldsName.split(",");
		var selFieldsCN=fieldsNameCN.split(",");
		
		var checkTableName=$('#checkTableName', parent.document).val();
		var checkTableNameCN=$('#checkTableNameCN', parent.document).val();
//----zheli	
		var checkTableNames=checkTableName.split(",");
	for(var s=1;s<selFields.length;s++){
		//alert(selFields[s]);
		var fieldName=selFields[s];
		var fieldNameCN=selFieldsCN[s];
			var tabIndex=fieldName.indexOf(".");
			var tableShow=fieldName.substring(0,tabIndex);
			var tabIndexCN=fieldNameCN.indexOf(".");
			var tableShowCN=fieldNameCN.substring(0,tabIndexCN);
			if(tName==tableShow){
				continue;
			}else{
				tName=tableShow;
				tNameCN=tableShowCN;
			}
			backSel();
			checkSelTable();
	}
		//checkSelTable();
		checkSelData(selFields,selFieldsCN);
		
	
	}
	
	
</script>
</head>
<body>
	<div id="tab2">
		<div id="column_id1" width="100%" height="100%" vtype="panel"
			layout="column"
			layoutconfig="{width: ['25%','12%','25%','13%','25%'],border:false}">
			<div>
				<div name="c1" vtype="panel" title="表信息" titledisplay="true"
					height="222">
					<select style="width:100%;height:100%" size="4" name="listId1"
						id="listId1" class="normal">
					</select>
				</div>
			</div>
			<div>
				<div name="c3" vtype="panel" title="" titledisplay="false"
					height="222">
					<div align="center" style="vertical-align: middle;padding:25px;">
						<input value=">" type="button" id="btnCRight" class="btn"
							onClick="checkSelTable();" /> <br />
						<br /> <input value=">>" type="button" id="btnCRightAll"
							class="btn" /> <br />
						<br /> <input value="<<" type="button" id="btnCLeftAll"
							class="btn" /> <br />
						<br /> <input value="<" type="button" id="btnCLeft" class="btn"
							onClick="resetSel1()" />
					</div>
				</div>
			</div>
			<div>
				<div name="c2" vtype="panel" title="字段信息" titledisplay="true"
					height="222">
					<select style="width:100%;height:100%" size="4" name="listId2"
						id="listId2" class="normal" title="双击可实现右移" multiple=”multiple”>
					</select>
				</div>
			</div>
			<div>
				<div name="c3" vtype="panel" title="" titledisplay="false"
					height="222">
					<div align="center" style="vertical-align: middle;padding:25px;">
						<input value=">" type="button" id="btnCRight" class="btn"
							onClick="checkSelData()" /> <br />
						<br /> <input value=">>" type="button" id="btnCRightAll"
							class="btn" /> <br />
						<br /> <input value="<<\" type="button" id="btnCLeftAll"
							class="btn" /> <br />
						<br /> <input value="<\"   type="button" id="btnCLeft" class="btn"
							onClick="resetSel2();" />
					</div>
				</div>
			</div>
			<div>
				<div name="c4" vtype="panel" title="展示范围" titledisplay="true"
					height="222">
					<select style="width:100%;height:100%" size="4" name="listId3"
						id="listId3" class="normal" title="双击可实现左移" multiple=”multiple”></select>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
