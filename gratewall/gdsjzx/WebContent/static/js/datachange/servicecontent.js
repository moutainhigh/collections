var vData;
var cData;
var allData;
var allCData;
function getTable(val){
	$.ajax({
		url:rootPath+'/shareResource/selectShareTable.do',
		data:{
			subject : val
		},
		type:"post",
		success:function(data){
			vData = eval('(' + data + ')');
			var vlist = "";
			var vSelect = $("#listTRight option");
			if(vSelect.length>0){
				var arrList = new Array();
				for(var z=0;z<vSelect.length;z++){
					arrList.push(vSelect[z].value);
				}
				jQuery.each(vData.datalist, function(i, n) {
					allData.push(n.data);
					if($.inArray(n.data, arrList)>-1){
					}else{
						vlist += "<option value=" + n.data + ">" + n.text + "</option>";
					}
			    });
			}else{
				jQuery.each(vData.datalist, function(i, n) {
					allData.push(n.data);
					vlist += "<option value=" + n.data + ">" + n.text + "</option>";
			    });
			}
			$("#listTLeft").append(vlist);
		}
	});
}
//向右移动表
function moveright() {
    //数据option选中的数据集合赋值给变量vSelect
    var vSelect = $("#listTLeft option:selected");
    if(vSelect.length>0){
    	//克隆数据添加到listRight中
	    vSelect.clone().appendTo("#listTRight");
	    //同时移除listRight中的option
	    vSelect.remove();
	    tableColumn();
    }else{
    	jazz.info("请选择需要移动的表！");
    	return;
    }
}
//向左移动表
function moveleft() {
    var vSelect = $("#listTRight option:selected");
    if(vSelect.length>0){
    	for(var z=0;z<vSelect.length;z++){
    		//if($.inArray(vSelect[z].value, allData)>-1){
    			$("#listTLeft").append("<option value=" + vSelect[z].value + ">" + vSelect[z].text + "</option>");
    		//}
    	}
	    vSelect.remove();
	    var vSelect2 = $("#listCLeft option");
	    var vSelect3 = $("#listCRight option");
	    vSelect2.remove();
	    vSelect3.remove();
	    tableColumn();
    }else{
    	jazz.info("请选择需要移动的表！");
    	return;
    }
}
//向右移动表
function moverightAll() {
    //数据option选中的数据集合赋值给变量vSelect
    var vSelect = $("#listTLeft option");
    //克隆数据添加到listRight中
    vSelect.clone().appendTo("#listTRight");
    //同时移除listRight中的option
    vSelect.remove();
    tableColumn();
}
//向左移动表
function moveleftAll() {
    var vSelect = $("#listTRight option");
    for(var z=0;z<vSelect.length;z++){
		//if($.inArray(vSelect[z].value, allData)>-1){
			$("#listTLeft").append("<option value=" + vSelect[z].value + ">" + vSelect[z].text + "</option>");
		//}
	}
    vSelect.remove();
    tableColumn();
}
//更新数据项的表
//实现第一步  到 第二步表选择
function tableColumn(){
	 var vSelect = $("#listTRight option");
	 var vSelect1 = $("#listTable option");
     vSelect1.remove();
     vSelect.clone().appendTo("#listTable");
     allCData=new Array();
     if(vSelect.length>0){
    	 getColumn(vSelect.val(),vSelect.text());
     }
}

function getColumn(tablecode,tablename){
	$.ajax({
		url:rootPath+'/shareResource/selectShareColumn.do',
		data:{
			tablecode : tablecode,
			tabletext : tablename
		},
		type:"post",
		success:function(data){
			cData = eval('(' + data + ')');
			var vlist = "";
			var vSelect = $("#listCRight option");
			if(vSelect.length>0){
				var arrList = new Array();
				for(var z=0;z<vSelect.length;z++){
					arrList.push(vSelect[z].value);
				} 
				jQuery.each(cData.datalist, function(i, n) {
					allCData.push(n.data);
					if($.inArray(n.data, arrList)>-1){
					}else{
						vlist += "<option value=" + n.data + ">" + n.text + "</option>";
					}
			    });
			}else{
				jQuery.each(cData.datalist, function(i, n) {
					allCData.push(n.data);
					vlist += "<option value=" + n.data + ">" + n.text + "</option>";
			    });
			}
			$("#listCLeft").append(vlist);
		}
	});
}

function moverightC() {
    //数据option选中的数据集合赋值给变量vSelect
    var vSelect = $("#listCLeft option:selected");
    if(vSelect.length>0){
	    //克隆数据添加到listRight中
	    vSelect.clone().appendTo("#listCRight");
	    //同时移除listRight中的option
	    vSelect.remove();
    }else{
    	jazz.info("请选择需要移动的表！");
    	return;
    }
}
function moveleftC() {
    var vSelect = $("#listCRight option:selected");
    if(vSelect.length>0){
    	for(var z=0;z<vSelect.length;z++){
    	//	if($.inArray(vSelect[z].value, allCData)>-1){
    			$("#listCLeft").append("<option value=" + vSelect[z].value + ">" + vSelect[z].text + "</option>");
    		//}
    	}
	    vSelect.remove();
    }else{
    	jazz.info("请选择需要移动的表！");
    	return;
    }
}
function moverightAllC() {
    //数据option选中的数据集合赋值给变量vSelect
    var vSelect = $("#listCLeft option");
    //克隆数据添加到listRight中
    vSelect.clone().appendTo("#listCRight");
    //同时移除listRight中的option
    vSelect.remove();
}
function moveleftAllC() {
    var vSelect = $("#listCRight option");
    for(var z=0;z<vSelect.length;z++){
		//if($.inArray(vSelect[z].value, allCData)>-1){
			$("#listCLeft").append("<option value=" + vSelect[z].value + ">" + vSelect[z].text + "</option>");
		//}
	}
    vSelect.remove();
}
//拿到tab3的条件
function getCondition(){
	var condition="";
	var trs=$('#condition tr');
	for(var k=1;k<trs.length;k++){
		condition=condition+$('#condition').find("tr:eq("+k+")").find("td:eq(0) select option:selected").val();
		condition=condition+"-"+$('#condition').find("tr:eq("+k+")").find("td:eq(1) select option:selected").val();
		condition=condition+"-"+$('#condition').find("tr:eq("+k+")").find("td:eq(2) p").attr("value");
		condition=condition+"."+$('#condition').find("tr:eq("+k+")").find("td:eq(3) select option:selected").val();
		var td4=$('#condition').find("tr:eq("+k+")").find("td:eq(4) select option:selected").val();
		condition=condition+"-"+td4;
		if(td4 == " is not null " || td4==" is null "){
			condition=condition+"-";
		}else{
			condition=condition+"-'"+$('#condition').find("tr:eq("+k+")").find("td:eq(5) input").val()+"'";
		}
		condition=condition+"-"+$('#condition').find("tr:eq("+k+")").find("td:eq(6) select option:selected").val() + "//";
	}
	return condition;
}

//点击预览
function preview(){
	$("#tab4").empty();
	var tableCode=$('#listTRight option').val();
	var tableValue=$('#listTRight option').text();
	var columnCode="";
	var columnValue="";
	for(var k=0;k<$('#listCRight option').length;k++){
		columnCode +=$('#listCRight option:eq('+k+')').val();
		columnValue +=$('#listCRight option:eq('+k+')').text();
		if(k!=($('#listCRight option').length-1)){
			columnCode =columnCode+",";
			columnValue =columnValue+",";
		}
	}
	var condition=null;
	if($('#condition').find("tr").length>1 ){
	var con=$('#condition').find("tr:eq(1)");
		if($.trim(con.find("td:eq(5) input").val())!="" || con.find("td:eq(4) select option:selected").val()==" is not null " || con.find("td:eq(4) select option:selected").val()==" is null "){
			condition=getCondition();
		}
	}
	if(tableCode != null && columnCode != null && tableCode.trim() != "" && columnCode.trim() != ""){
		$.ajax({
			url:rootPath+'/dataservice/previewServiceContent.do',
			type:"post",
			data:{tableCode : tableCode,
				columnCode : columnCode,
				condition : condition
				},
			success:function(data){
				//var tableValuehtml="<div class='tablev'  ><label class='tables' >该查询的表名:</label><div class='tableg' >"+tableValue+"</div></div>";
				
				//var columnValuehtml="<div class='tablev'  ><label class='tables' >该查询的字段名:</label><div class='tableg'>"+columnValue+"</div></div>";
				var tableValuehtml="<div class='tablev' style='height: 30px;margin: 0;padding: 0;border : 1px black solid;width: 98%;display: inline-block;border-collapse: collapse;vertical-align: top;' ><label style='display: block;text-align: right;width: 119px;background-color: #dceeef;border-right: 1px solid #908686;height: 30px;margin: 0;line-height: 30px;padding: 0;clear: left;float: left;color: #666;font-family: '瀹嬩綋';font-size: 12px; font-weight: bold;word-wrap: break-word;border-collapse: collapse;'>该查询的表名:</label><div style='border-collapse: collapse; font-size: 12px; text-align: left; line-height: 30px;' >"+tableValue+"</div></div>";
				var columnValuehtml="<div class='tablev' style='height: 30px;margin: 0;padding: 0;border : 1px black solid;width: 98%;display: inline-block;border-collapse: collapse;vertical-align: top;' ><label style='display: block;text-align: right;width: 119px;background-color: #dceeef;border-right: 1px solid #908686;height: 30px;margin: 0;line-height: 30px;padding: 0;clear: left;float: left;color: #666;font-family: '瀹嬩綋';font-size: 12px; font-weight: bold;word-wrap: break-word;border-collapse: collapse;'>该查询的字段名:</label><div style='border-collapse: collapse; font-size: 12px; text-align: left; line-height: 30px;' >"+columnValue+"</div></div>";
				$("#tab4").append(tableValuehtml);
				$("#tab4").append(columnValuehtml);
				var fieldCodes=columnCode.split(",");
				var fields=columnValue.split(",");
				var fieldMap={};
				var field="";
				$("#tab4").append("<table class='gridtable' style='width:98%;border:1px black solid'></table>");
				for(var k=0;k<fields.length;k++){
					if(k==0){
					field="<tr>";
					}
					field=field+"<th>"+fields[k]+"</th>";
					if(k==fields.length-1){
					field=field + "</tr>";
					}
					fieldMap[fieldCodes[k]]=fields[k];
				}
				$("#tab4 table").append(field);
				for(var l=0;l<data.length;l++){
					var html="<tr>";
					for(var str in fieldMap){
						if(data[l][str.toLowerCase()]!=undefined){
							html=html+"<td>"+data[l][str.toLowerCase()] +"</td>";
						}
					}
						html=html+"</tr>";
					$("#tab4 table").append(html);
				}
			}
		});
	}else{
		jazz.info("请先填写完数据再预览");
		$('#tt').tabpanel('select', 0);  //不起作用？
	}
}
	
function addRow(){
	if(1<$('#condition').find("tr").length){
		$('#condition').append($('#condition tr:eq(1)').clone(true));
		$('#condition tr:gt(1) td:first select').css("display","inline");
		$('#condition tr:gt(1) td:last input').css("display","inline");
	};
}

function delRow(e){
	$(e).parent().parent().remove();
}
	
var eventClick=true;
// 
function loadCondition(){
	if(tab3Click){
	var vSelect1 = $("#listTable option");
	if(vSelect1[0]!=undefined){
		var tablecode=vSelect1[0].value;
		var tablename=vSelect1[0].text;
	}else{
		jazz.info("请先填写数据再点下一步！");
		return;
	}
	
	if($('#condition tr:eq(1) td:eq(2) ').find("p").length>0){
		$('#condition tr:eq(1) td:eq(2) p').remove();
	}
	$.ajax({
	url:rootPath+'/shareResource/selectShareColumn.do',
	data:{
		tablecode : tablecode,
		tabletext : tablename
	},
	type:"post",
	success:function(data){
		//if($('#condition tr:eq(1) td:eq(2) ').find("p").length==0){
		$('#condition tr:eq(1) td:eq(2) p').empty();
		$('#condition tr:eq(1) td:eq(3) select').empty();
			var cData = eval('(' + data + ')');
			var pHtml="<p value="+tablecode+">"+tablename+"</p>";
			$('#condition tr:eq(1) td:eq(2) ').append(pHtml);
			for(var i=0;i<cData.datalist.length;i++){
				var optionHtml="<option value="+cData.datalist[i].data+" field="+cData.datalist[i].fieldtype+">"+cData.datalist[i].text+"</option>";
				$('#condition tr:eq(1) td:eq(3) select').append(optionHtml);
				}
			if(update=='true'){
				echo(pHtml,cData,tablecode);
				}
			}
		});
	tab3Click=false;
	}
}
	
function echo(pHtml,cData,tablecode){
	var c1=condition.split("//");
	for(var i=1;i<c1.length-1;i++){
			addRow();
			/*$('#condition tr:eq('+(i+1)+') td:eq(2) ').empty();
			$('#condition tr:eq('+(i+1)+') td:eq(2) ').append(pHtml);
			var optionHtml="<option value="+cData.datalist[i].data+" field="+cData.datalist[i].fieldtype+">"+cData.datalist[i].text+"</option>";
			$('#condition tr:eq('+(i+1)+') td:eq(3) select').append(optionHtml);*/
	}
//	selFiledType(null);
	for(var i=0;i<c1.length-1;i++){
		var c2=c1[i].split("-");
		var c3=c2[2].split(".");
		var c4=c2[5].replace("'","");
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(0) select").val(c2[0]);          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(1) select").val(c2[1]);          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(2) p").attr("value",tablecode);                       
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(3) select").val(c3[1]);          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(4) select").val(c2[3]);          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(5) input ").val(c2[4].replace("'","").replace("'",""));                          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(6) select").val(c2[5]);
	}
}
	//	eventClick=false;
//	}
	
/*	function showTable(i,c1){
		console.info(c1);
		var c2=c1[i].split("-");
		var c3=c2[2].split(".");
		var c4=c2[5].replace("'","");
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(0) select").val(c2[0]);          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(1) select").val(c2[1]);          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(2) p").attr("value",c3[0]);                       
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(3) select").val(c3[1]);          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(4) select").val(c2[3]);          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(5) input ").val(c2[4].replace("'","").replace("'",""));                          
		$('#condition').find("tr:eq("+(i+1)+")").find("td:eq(6) select").val(c2[5]);
	}*/
function selFiledType(event){
	var n=$(event).find("option:selected").attr("field");
	var $sel=$(event).parent().parent().find("td:eq(4) select");
	$sel.empty();
	var td4="";
	if(event==undefined || event==null){
		n="varchar";
	}
	switch(n){
	case "varchar" :
		td4="<option value='='>等于</option>"+
			"<option value=' is not '>不等于</option>" +
			"<option value='>='>大于等于</option>"+
			"<option value='<='>小于等于</option>";
		break;
	case "number" :
		td4="<option value='='>等于</option>"+
			"<option value=' is not '>不等于</option>" +
			"<option value='>='>大于等于</option>"+
			"<option value='<='>小于等于</option>";
		break;
	case "date" :
		td4="<option value='='>等于</option>"+
			"<option value=' is not '>不等于</option>" +
			"<option value='>='>大于等于</option>"+
			"<option value='<='>小于等于</option>";
		break;
		td4="<option value='='>等于</option>"+
			"<option value=' is not '>不等于</option>" +
			"<option value='>='>大于等于</option>"+
			"<option value='<='>小于等于</option>";
		break;
	default:
		break;
	}
	$('#condition').find("tr").find("td:eq(4) select").append(td4);
}
var tab3Click=true;
/*$(function(){
	$('#litab3').click(loadCondition);
	$('#litab4').click(preview);
});*/
$(function(){
	$("#litab1").click(function(e){
	    e.stopPropagation();
	}); 
	$("#litab2").click(function(e){
	    e.stopPropagation();
	}); 
	$("#litab3").click(function(e){
	    e.stopPropagation();
	}); 
	$("#litab4").click(function(e){
	    e.stopPropagation();
	}); 
});
function changeCondition(event){
	var eval=$(event).val();
	if(eval==" is not null " || eval==" is null "){
		$(event).parent().parent().find("td:eq(5) input").hide();
	}else{
		$(event).parent().parent().find("td:eq(5) input").show();
	}
}