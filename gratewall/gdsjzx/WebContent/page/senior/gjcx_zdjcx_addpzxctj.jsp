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
<script src="<%=request.getContextPath()%>/static/script/JAZZ-UI/demos/index/jazz-ui.js" type="text/javascript"></script>
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

		//加载选择表中的数据
		var tabName="";
		var tabNameCN="";
	  function loadtable(){
			tabName=$('#checkTableName', parent.document).val();
			tabNameCN=$('#checkTableNameCN', parent.document).val();
			loadTab();
	  }

	  function loadTab(){
	  //alert(tabName);
			var arrayN=tabName.split(",");
			var arrayNCN=tabNameCN.split(",");
			var trLen=$('#condition').find('tr').length;
			for(var j=1;j<trLen;j++){
			$('#condition tr:eq('+j+') td:eq(2) select').find('option').remove();
				}
				for(var i=1;i<arrayN.length;i++){
				for(var j=1;j<trLen;j++){
				var optionHtml="<option value="+arrayN[i]+">"+arrayNCN[i]+"</option>";
					$('#condition tr:eq('+j+') td:eq(2) select').append(optionHtml);
				}
			}
			
			var tableName=$('#condition tr:eq(1) td:eq(2) select').val();
			//alert(tableName);
			var tab=$('#condition tr:eq(1) td:eq(3) select');
			//初始化字段
			selTable(tableName,tab);
	  }
	  
	//选择表发送获取表字段
	function selTable(name,tab){
				//alert("ui.text");
				//获取主题下面的表名
		$.ajax({
			url:'http://localhost:8080/gdsjzx/report/year.do',
			async:false,
			data:{
				tableName:name,
			},
			type:"post",
			dataType : 'json',
			success:function(data){
				/*
					加载选择表下的数据
				*/
			tab.find('option').remove();				
			for(var i=0;i<3;i++){
				/*
				*跟据返回的元数据类型，判断后面的格式类型
				*/
				var optionHtml="<option>1</option>";
				tab.append(optionHtml);
					}
				}
		
		});
	};
	
	function addRow(){
			$('#condition').append($('#condition tr:eq(1)').clone(true));
			$('#condition tr:gt(1) td:last input').css("display","inline");
	}
	function delRow(e){
			$(e).parent().parent().remove();
	}
	function selTab(e){
			var tab=$(e).parent().parent().find("td:eq(3)").find("select");
			selTable($(e).val(),tab);
			//alert($(e).parent().val());
	}
	
	function selCondition(){
			var sql ="";
		for(var i=1;i<$('#condition tr').length;i++){
			//alert(i);
			for(var j=0;j<$('#condition tr:eq(1) td').length;j++){
			var data=$('#condition tr:eq('+i+') td:eq('+j+') select').val();
			//alert(data+"pppppppppppppp");
			if($.trim(data)==""){
				sql=sql+"-"+"/";
				}else{
				sql=sql+data+"/";
				}
			}
		}
		return sql;
	}
	
	function selConditionCN(){
			var sql ="";
		for(var i=1;i<$('#condition tr').length;i++){
			//alert(i);
			for(var j=0;j<$('#condition tr:eq(1) td').length;j++){
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
	
//---------------------------修改-----------------------
	function updatepzxctj(){
		loadtable();
		loadTab();
		var selCondition=$('#selCondition', parent.document).val();
		var selConditionCN=$('#selConditionCN', parent.document).val();
		
		var selConditions=selCondition.split("/");
		/* for(var r=0;r<selConditions.length-1;r++){
			alert(selConditions[r]);
		}
		alert(selCondition); */
	//	var selConditionCNs=selConditionCN.split("/");  
	//	alert(selConditions.length);
		for(var p=1;p<(selConditions.length-1)/8;p++){
			$('#condition tr:eq(1)').clone(true).appendTo($('#condition'));
			$('#condition tr:gt(1) td:last input').css("display","inline");	
			}
			
		//alert("p");
		for(var k=0;k<selConditions.length-1;k++){
			if(selConditions[k]=="-"){
				selConditions[k]=" ";
			}
			if(selConditions[k]=="-"){
				selConditions[k]=" ";
			}
			if((k+1)%8==0){
				continue;
			}
			$('#condition tr:eq('+(parseInt((k+1)/8)+1)+') td:eq('+k%8+') select').find("option[value='"+selConditions[k]+"']").attr("selected","selected");
		}
	}
</script>
</head>
<body>
<div style="margin-top:15px;background:#EEF5FD;border:1px solid #000;width:100%;height:80%" >
	<p>数据表查询条件 </p>
	<table id="condition" style="width:100%;">
			<tr><td>条件</td><td>括弧</td><td>数据表</td><td>表字段</td><td>连接关系</td><td>值</td><td>括弧</td><td>操作</td></tr>
		<tr>
			<td><select><option value=" "> </option><option value="and"> and </option><option value="or"> or </option></select></td>
			<td><select><option value=" "> </option><option value="(">(</option><option value="((">((</option><option value="(((">(((</option></select></td>
			<td><select onChange="selData(this)"><option></option></select></td>
			<td><select ><option ></option></select></td>
			<td><select><option value="=">等于</option></select></td>
			<td><select ><option></option></select></td>
			<td><select><option value=" "> </option><option value=")">)</option><option value="))">))</option><option value=")))">)))</option></option></select></td>
			<td width="160px">
				<input type="button" onClick="addRow()" value="增加" style="background: #006599;color:#fff"/>
				<input style="display:none;" type="button" onClick="delRow(this)" value="删除" style="background: #006599;color:#fff"/>
			</td>
		</tr>
	</table>
</div>
		<div id="sql"><p></p></div>
		<input type="hidden" id="tabSel"/>	
	
</body>
</html>