var update;
$(function(){
	$('#btn1').button('hide');
	$('#btn3').button('hide');
	update = jazz.util.getParameter("update");
	$('#btnTableLeftAll').show();
	$('#btnTableRightAll').show();
	$("#listTLeft").attr("multiple",'multiple');
	$("#listTRight").attr("multiple",'multiple');

	$('div[name="titlename"]').boxlist('option', {"click": function(e, ui){
		var vSelect = $("#listTLeft option");
	    vSelect.remove();
	    allData=new Array();
	    getTable(ui.val);
	}});
	//double click to move right 双击左侧栏把表放进右侧栏
    $("#listTLeft").dblclick(function() {
		var vSelect = $("#listTRight option");
    	if(vSelect.length>0){
    	jazz.info("只能选择一张表！");
    		return;
    	}
        moveright();
    });
    //double click to move left 双击右侧栏把表放进左侧栏
    $("#listTRight").dblclick(function() {
    	//判断是否已经配置数据项
    	var vSelect = $("#listCLeft option");
    	var vSelect1 = $("#listCRight option");
    	moveleft();
    	if( vSelect1.length>0 || vSelect.length>0){
			vSelect.remove();
			vSelect1.remove();
    	}
    });
    //right move
    $("#btnTableRight").click(function() {
		var vSelect = $("#listTRight option");
    	if(vSelect.length>0){
    		jazz.info("只能选择一张表！");
    		return;
    	}
        moveright();
    });
    //left move 
    $("#btnTableLeft").click(function() {
        var vSelect = $("#listCLeft option");
    	var vSelect1 = $("#listCRight option");
    	if(vSelect.length>0 || vSelect1.length>0){
			moveleft();
			vSelect.remove();
			vSelect1.remove();
    	}
    });
  	//right move
    $("#btnTableRightAll").click(function() {
        moverightAll();
    });
    //left move 
    $("#btnTableLeftAll").click(function() {
    	var vSelect = $("#listCLeft option");
    	var vSelect1 = $("#listCRight option");
    	if(vSelect.length>0 || vSelect1.length>0){
			moveleftAll();
			vSelect.remove();
			vSelect1.remove();
    	}
    });
    //获取表的列
  /*   $("#listTable").click(function() {
    	var vSelect = $("#listCLeft option");
	    vSelect.remove();
	    var vSelect1 = $("#listTable option:selected");
	    allCData=new Array();
        getColumn(vSelect1[0].value,vSelect1[0].text);
    });
     */
  	//double click to move right 双击左侧栏把列放进右侧栏
    $("#listCLeft").dblclick(function() {
        moverightC();
    });
    //double click to move left 双击右侧栏把列放进左侧栏
    $("#listCRight").dblclick(function() {
        moveleftC();
    });
    //right move 
    $("#btnCRight").click(function() {
        moverightC();
    });
    //left move 
    $("#btnCLeft").click(function() {
        moveleftC();
    });
  	//right move
    $("#btnCRightAll").click(function() {
        moverightAllC();
    });
    //left move 
    $("#btnCLeftAll").click(function() {
        moveleftAllC();
    });
});
function back() {
	parent.winEdit.window("close");
}
function save() {
	var servicecontentname = $("div[name='servicecontentname']").textfield('getValue');
	var servicecontentshow = $("div[name='servicecontentshow']").textareafield('getValue');
	var isenabled = $("div[name='isenabled']").radiofield('getValue');
	if(servicecontentname==''||isenabled == ''||servicecontentshow==''){
		jazz.info("有必填选项未填写，添加失败");
	}else{
		var vSelect = $("#listTRight option");
		var cSelect = $("#listCRight option");
		var tableCode="";
		var tableName="";
		var columnCode="";
		var columnName="";
		if(vSelect.length>0){
			for(var z=0;z<vSelect.length;z++){
				tableCode += ","+vSelect[z].value;
				tableName += ","+vSelect[z].text;
			}
		}else{
			jazz.info("未配置数据表，请配置好数据表再保存！");
			return;
		}
		if(cSelect.length>0){
			for(var j=0;j<cSelect.length;j++){
				columnCode += ","+cSelect[j].value;
				columnName += ","+cSelect[j].text;
			}
		}else{
			jazz.info("未选择显示字段");
			return;
		}
		var condition=null;
		if($('#condition').find("tr").length>1 ){
		var con=$('#condition').find("tr:eq(1)");
			if($.trim(con.find("td:eq(5) input").val())!="" || con.find("td:eq(4) select option:selected").val()==" is not null " || con.find("td:eq(4) select option:selected").val()==" is null "){
				condition=getCondition();
			}
		}
		
		var params = {
			url : rootPath+'/dataservice/saveServiceContent.do',
			components : [ 'formpanel_edit' ],
			params: {
				update : update,
				tableCode : tableCode,
				tableName : tableName,
				columnCode : columnCode,
				columnName : columnName,
				condition  :condition
			},
			callback : function(data, r, res) { 
				if (res.getAttr("back") == 'success'){
					parent.queryUrl();
					parent.winEdit.window("close");
					jazz.info("保存成功！");
				}else if(res.getAttr("back") == 'sql_error'){
					jazz.error("保存sql不正确！");
				}else{
					if (res.getAttr("back") == 'name_unique')
						jazz.error("服务内容名称已存在！");
					else
						jazz.error("添加失败！");
				}
			}
		};
		$.DataAdapter.submit(params);
	}
}

function delRow(e){
	$(e).parent().parent().remove();
}

//上一步
function lastStep(){
	var index = $('#tab_name').tabpanel('getActiveIndex');
	if(index==1){
		$('#btn3').button('hide');
		$('#btn4').button('show');
	}else{
		$('#btn1').button('hide');
		$('#btn4').button('show');
	}
	if(index<4 || index>-1){
		$('#tab_name').tabpanel('select',index-1);
		/* if((index+1)==3){
			loadCondition();
		}
		if((index+1)==4){
			preview();
		} */
	}
}

//下一步
function nextStep(){
	var index = $('#tab_name').tabpanel('getActiveIndex');
	if(index==0){
		var vSelect1 = $("#listTRight option");
    	if(vSelect1.length<=0){
    		alert("请选择数据表，才能进入下一步！");
    		return;
    	}
	}
	if(index==1){
		var vSelect1 = $("#listCRight option");
    	if(vSelect1.length<=0){
    		alert("请选择数据项，才能进入下一步！");
    		return;
    	}
		loadCondition();
	}
	if(index==2){
		preview();
	};
	$('#btn3').button('show');
	if(index==2){
		$('#btn4').button('hide');
		$('#btn1').button('show');
	}
	if(index<4 || index>-1){
		$('#tab_name').tabpanel('select',index+1);
	}
}