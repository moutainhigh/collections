var update;
var condition;
$(function(){
	$('#btn1').button('hide');
	$('#btn3').button('hide');
	update = jazz.util.getParameter("update");
	var fwnrid = jazz.util.getParameter("fwnrid");
	if(fwnrid != null){
		$('#formpanel_edit .jazz-panel-content').loading();
		$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/dataservice/serviceContentDetail.do?fwnrid='+fwnrid);
		$("#formpanel_edit").formpanel('reload', "null", function(){
		$('#formpanel_edit .jazz-panel-content').loading('hide');
		condition=$("div[name='servicecontentcondition']").hiddenfield('getValue');
		var columnname1 = $("div[name='columnname']").hiddenfield('getValue');
		var columncode1 = $("div[name='columncode']").hiddenfield('getValue');
		
		if(columncode1!="" && columnname1!="" ){
		columncode1=columncode1.split(",");
		columnname1=columnname1.split(",");
		}
		for(var i=0;i<columncode1.length;i++){
			$('#listCRight').append("<option value="+columncode1[i]+">"+columnname1[i]+"</option>");
			}
		});
	}else{
		
	}
	//var ct = $("div[name='contenttype']").radiofield('getValue');
	/* if(ct==0){
		$('#btnTableLeftAll').hide();
		$('#btnTableRightAll').hide();
		$("#listTLeft").removeAttr("multiple");
		$("#listTRight").removeAttr("multiple");
		//analysis();
	}else{ */
		$('#btnTableLeftAll').show();
		$('#btnTableRightAll').show();
		$("#listTLeft").attr("multiple",'multiple');
		$("#listTRight").attr("multiple",'multiple');
		//analysis();
	//}
	//analysis();
	$('div[name="contenttype"]').on("radiofielditemselect",function(event, ui){
		jazz.confirm('更改内容类型，配置数据表和配置数据项将会被清除！<br>是否确认执行？',
    			function(){
					var vSelect1 = $("#listTLeft option");
					var vSelect2 = $("#listTRight option");
					var vSelect3 = $("#listTable option");
					var vSelect4 = $("#listCLeft option");
					var vSelect5 = $("#listCRight option");
					vSelect1.remove();
					vSelect2.remove();
					vSelect3.remove();
					vSelect4.remove();
					vSelect5.remove();
					if(ui.value==1){
						$('#tab2').hide();
						$('#litab2').hide();
						$('#tt').tabpanel('select', 0);
						$("#listTLeft").attr("multiple",'multiple');
						$("#listTRight").attr("multiple",'multiple');
						$('#btnTableLeftAll').show();
						$('#btnTableRightAll').show();
					}else{
						//$('div[name="textfield_name"]').hide();
						$('#tab2').show();
						$('#litab2').show();
						$('#btnTableLeftAll').hide();
						$('#btnTableRightAll').hide();
						$("#listTLeft").removeAttr("multiple");
						$("#listTRight").removeAttr("multiple");
					} 
    			},
    			function(){
    				//alert('取消');
    			});
	});

	$('div[name="titlename"]').boxlist('option', {"click": function(e, ui){
		var vSelect = $("#listTLeft option");
	    vSelect.remove();
	    allData=new Array();
	    getTable(ui.val);
	}});
	//double click to move right
    $("#listTLeft").dblclick(function() {
    		var vSelect = $("#listTRight option");
    		
    		if(vSelect.length>0){
	    	jazz.info("只能选择一张表！");
	    		return;
	    	}
        moveright();
    });
    //double click to move left
    $("#listTRight").dblclick(function() {
    	//判断是否已经配置数据项
    	var vSelect = $("#listCLeft option");
    	var vSelect1 = $("#listCRight option");
    		moveleft();
    	if(vSelect.length>0 || vSelect1.length>0){
    				vSelect.remove();
    				vSelect1.remove();
    	}else{
    		moveleft();
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
 /*    $("#listTable").click(function() {
    	var vSelect = $("#listCLeft option");
	    vSelect.remove();
	    var vSelect1 = $("#listTable option:selected");
	    allCData=new Array();
        getColumn(vSelect1[0].value,vSelect1[0].text);
    }); */
    
  	//double click to move right
    $("#listCLeft").dblclick(function() {
        moverightC();
    });
    //double click to move left
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
	var condition=null;
		if($('#condition').find("tr").length>1 ){
		var con=$('#condition').find("tr:eq(1)");
			if($.trim(con.find("td:eq(5) input").val())!="" || con.find("td:eq(4) select option:selected").val()==" is not null " || con.find("td:eq(4) select option:selected").val()==" is null "){
				condition=getCondition();
			}
		}
	if(servicecontentname==''||isenabled == ''||servicecontentshow==''){
		jazz.info("有必填选项未填写，添加失败");
	}else{
		//var sql = $("div[name='sql']").textareafield('getValue');
		//var sqlnew = encodeURI(encodeURI(sql));
		//sqlnew = sqlnew.replace(/'/g, "#");
		//$("div[name='sql']").textareafield('setValue',sqlnew);
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
		var params = {
			url : rootPath+'/dataservice/saveServiceContent.do',
			components : [ 'formpanel_edit' ],
			params: {
				update : update,
				tableCode : tableCode,
				tableName : tableName,
				columnCode : columnCode,
				columnName : columnName,
				condition:condition
			},
			callback : function(data, r, res) { 
				if (res.getAttr("back") == 'success'){
					parent.queryUrl();
					parent.winEdit.window("close");
					jazz.info("保存成功！");
				}else if(res.getAttr("back") == 'sql_error'){
					jazz.error("保存sql不正确！");
				}else{
					jazz.error("添加服务对象失败！");
				}
			}
		};
		$.DataAdapter.submit(params);
	}
}
	
function delRow(e){
	$(e).parent().parent().remove();
}

function lastStep(){
	var index = $('#tt').tabpanel('getActiveIndex');
/* 	if(index==2){
			loadCondition();
			}
	if(index==3){
			preview();
			}; */
	if(index==1){
		$('#btn3').button('hide');
		$('#btn4').button('show');
	}else{
		$('#btn1').button('hide');
		$('#btn4').button('show');
	}
	
	if(index<4 || index>-1){
		$('#tt').tabpanel('select',index-1);
	}
	
}
function nextStep(){
	var index = $('#tt').tabpanel('getActiveIndex');
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
		$('#tt').tabpanel('select',index+1);
	}
}

/**============**/
$(window).load(function(){
	//要执行的方法体
	setTimeout('analysis()',1000);
});
function analysis(){
	var columnname = $("div[name='columnname']").hiddenfield('getValue');
	var columncode = $("div[name='columncode']").hiddenfield('getValue');
	var tablename = $("div[name='tablename']").hiddenfield('getValue');
	var tablecode = $("div[name='tablecode']").hiddenfield('getValue');
	$("div[name='columnname']").hide();
	$("div[name='columncode']").hide();
	$("div[name='tablename']").hide();
	$("div[name='tablecode']").hide();
	if($.trim(tablecode)!=""){
		var vlist ="";
		if(tablecode.indexOf(',')>-1){
			var strCode= new Array();  
			strCode=tablecode.split(","); 
			
			var strName= new Array();  
			strName=tablename.split(","); 
		    for (i=0;i<strCode.length ;i++ ){   
		    	vlist += "<option value=" + strCode[i] + ">" + strName[i] + "</option>";
		    } 
		}else{
            vlist = "<option value=" + tablecode + ">" + tablename + "</option>";
		}
		var vSelect = $("#listTRight option");
        vSelect.remove();
        var vSelect1 = $("#listTable option");
        vSelect1.remove();
        $("#listTRight").append(vlist);
        $("#listTable").append(vlist);
	}else{
		//jazz.info("数据表错误！");
		//parent.winEdit.window("close");
	}
	//var ct = $("div[name='contenttype']").radiofield('getValue');
	 
	//if(ct==1){
		//$('#tab2').hide();
		//$('#litab2').hide();
		$('#tt').tabpanel('select', 0);
		$("#listTLeft").attr("multiple",'multiple');
		$("#listTRight").attr("multiple",'multiple');
		$('#btnTableLeftAll').show();
		$('#btnTableRightAll').show();
		//analysis();
	/* }else{
		$('#tab2').show();
		$('#litab2').show();
		$('#btnTableLeftAll').hide();
		$('#btnTableRightAll').hide();
		$("#listTLeft").removeAttr("multiple");
		$("#listTRight").removeAttr("multiple"); 
		//analysis();
		
		if($.trim(columncode)!=""){
			var vlist ="";
			if(columncode.indexOf(',')>-1){
				var strCode= new Array();  
				strCode=columncode.split(","); 
				
				var strName= new Array();  
				strName=columnname.split(","); 
			    for (i=0;i<strCode.length ;i++ ){   
			    	vlist += "<option value=" + strCode[i] + ">" + strName[i] + "</option>";
			    } 
			}else{
				vlist = "<option value=" + columncode + ">" + columnname + "</option>";
			}
			var vSelect = $("#listCRight option");
	        vSelect.remove();
	        $("#listCRight").append(vlist);
		}else{
			//jazz.info("数据项错误！");
			//parent.winEdit.window("close");
		}
	}*/
	//加载列
    var vSelect1 = $("#listTable option");
    allCData=new Array();
    getColumn(vSelect1[0].value,vSelect1[0].text);
}