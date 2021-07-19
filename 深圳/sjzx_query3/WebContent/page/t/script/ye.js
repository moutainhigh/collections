function limConTdWidth(){
	$("#table_param  tr").each(function(){
		var s=$(this).children("td").eq(5).width(200);
	});
}
// 返 回
function func_record_goBack()
{
	goBack();	// /txn40200001.do
}

//鼠标放上时展示当前选中的代码值
function showSelectedValue(thistd){
  var selectOp = window.document.getElementById('pvs');
  if( selectOp.selectedIndex>-1){
		thistd.title=document.getElementById('pvs').options[selectOp.selectedIndex].text;
	}
	else{
		thistd.title = "";
	}
}
function deleteReturn(index){
	//return false;
	if(tab_index==1){
		var data = $("#table_param").tablet("getAllData");
		data = eval('('+ data +')');
		if(data.data.length == 0){
			$("#condition_cond").attr("disabled", true);
		}else{
			$("#condition_cond").attr("disabled", false);
		}
	}
}
var tab_index = 0;
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage(){
	 $.tabs({
        selector: "#tabs",
        selected: 0,
        click: function(index, instance) {
        	if(index==1){
        		tab_index = 1;
        		var data = $("#table_param").tablet("getAllData");
        		data = eval('('+ data +')');
        		if(data.data.length == 0){
        			$("#condition_cond").attr("disabled", true);
        		}else{
        			$("#condition_cond").attr("disabled", false);
        		}
        	}else if(index==2){
        	    tab_index = 2;
               // showSql();
          	var	tempParam=$("#table_param").tablet("getAllData");
   			bulidTableParam(tempParam);
   			tempParam=$("#selected_columns").tablet("getAllData");
          	bulidTableCondition(tempParam);
          }else{
        	  tab_index = 0;
          }
        }
      });
 
}

	var busiSystemData ;
	var currentSystem ;
	var busiTopicData ;
	var currentTopic ;
	var tblData ;
	var checkTblData = new Array;
	var initTbColsSelet = new Array; //初始化已选择数据列
	var checkedTblCount = 0;
	var leftTblData = new Array;
	var leftDataItem = new Array;
	var rightTblData = new Array;
	var rightDataItem = new Array;
	var condition = new Array;
	var condition_item = new Array;

// 加载服务对象列表（业务系统）
/*
$.ajax({
	  type: "post",
	  url: "<%=request.getContextPath()%>/txn401006.ajax",
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert(xmlResults.selectSingleNode("//context/error-desc").text);
	  		return false;
	  	}else{
	  		var datas = _getXmlNodeValues( xmlResults, "/context/dataString" );
	  		busiSystemData = eval('('+datas+')');
	  	}
	  }
});*/
	
$(function(){
	__userInitPage();
    //隐藏关联条件div
	$('#relation_div').hide();
	//初始化业务系统列表数据
	/*
	var opt={
		data:busiSystemData,
		onClick:function(event,key){
			getTopic(key,'busiTopic_div');
		}
	}
	$('#busiSystem_div').dataSelector(opt);
	*/
	
	
	//初始化业务主题列表数据
	var busiTopicData ;
	$.ajax({
		  type: "post",
		  url: "../../advQuery/getTopic.do",
		  async: false,
		  dataType:"json",
		  success: function(data){
			  busiTopicData = data.data[0].data;
			//组成第一个表选择数据对像
				var opt_Topic={
					data : busiTopicData,
					onClick:function(event,key){
						getTable(key,'tbl_div');
					}
				}
				$('#busiTopic_div').dataSelector(opt_Topic);//将表的数据对像传递给对应的主题表栏目
		  }
	});
	
	
	//初始化数据表列表数据,因为tblData默认为初始化为空
	var opt_table={
		data:tblData
	}
	$('#tbl_div').dataSelector(opt_table);//加载选择的数据表第二个栏目
	
	//已选数据表列表
	var opt_check={
		data:checkTblData
	}
	$('#checkTbl_div').dataSelector(opt_check);
	
	
	
	//初始化请选择数据列
	var opt_check={
		data:initTbColsSelet
	}
	$('#tab1_col_all_div').dataSelector(opt_check);

	
	
	//初始化结果集
	//初始化选择的数据列
	var tab1_col_selected_opt = {
			addDelete : true,
			data : [{
				"table" : "表名",
				"column" : "数据项名",
				/* "alias" : "别名", */
				"column_type" : "类型",
				"column_length":"长度",
			}],
			editable: 3,
			onClick : function(event){
			},
			shownum:5,
			onDelete: function(){
				var delData = arguments[0];
				if(delData){
					getColumnsByTable(delData.table.id);
					//alert("#tab1_table_all_div_id option#"+delData.table.id);
					$("#tab1_table_all_div_id option#"+delData.table.id).attr("selected", true);
				}
				//deleteReturn();
			}
		};
		$(tab1_col_selected_div).tablet(tab1_col_selected_opt);
	
	
	
	
	//初始化关联关系左表
	var opt_leftTable={
		multiple:false,
		data:leftTblData,
		size:1,
		onChange : function(event,key){
			alert('change'+key);
			getDataItemByKey(key,"leftItem_div");
		}
	}
	$('#leftTbl_div').dataSelector(opt_leftTable);
	
	//初始化关联关系右表
	var opt_rightTable={
		multiple:false,
		data:rightTblData,
		size:1,
		onChange : function(event,key){
			getDataItemByKey(key,"rightItem_div");
		}
	}
	$('#rightTbl_div').dataSelector(opt_rightTable);	
	
	//初始化关联关系左表字段列表
	var opt_leftDataItem={
		multiple:false,
		data:leftDataItem,
		size:1
	}
	$('#leftItem_div').dataSelector(opt_leftDataItem);
	
	//初始化关联关系左表字段列表
	var opt_rightDataItem={
		multiple:false,
		data:rightDataItem,
		size:1
	}
	$('#rightItem_div').dataSelector(opt_rightDataItem);
	
	//初始化配置查询条件数据表列表
	var opt_condition={
		multiple:false,
		data:condition,
		size:1,
		onChange:function(event,key){
			getDataItemByKey(key,"condition_item_div");
		}
	}
	$('#condition_div').dataSelector(opt_condition);
	
	//初始化配置查询条件数据项列表
	var opt_condition_item={
		multiple:false,
		data:condition_item,
		size:1
	}
	$('#condition_item_div').dataSelector(opt_condition_item);
	
	//初始化关联关系表头
	var data4selected_table = [{
		leftTable : "左表",
		leftColumn : "关联数据项",
		middleParen: "关联条件",
		rightTable : "右表",
		rightColumn : "关联数据项"
	   }];
    var opt_cond_tab = {
		addOper:true,
		data:data4selected_table
	};
	$("#selected_columns").tablet(opt_cond_tab);
    
    //初始化查询关系表头
	var param_table = [{
	    cond : "逻辑条件",
	    leftParen : "括弧",
		leftTable : "左表",
		leftColumn : "关联数据项",
		middleParen: "条件",
		paramValue : "参数值",
		rightParen : "括弧"
	    }
    ];
    var opt_param_table = {
		addOper: true,
		data: param_table,
		shownum: 8
	};
	$("#table_param").tablet(opt_param_table);
    
    var leftParen=[
                    {"title":"请选择","key":" "},
                    {"title":"(","key":"("},
                    {"title":"((","key":"(("},
                    {"title":"(((","key":"((("},
                    {"title":"((((","key":"(((("}
                    ];
    var opt_condition_left={
		multiple:false,
		data:leftParen,
		size:1,
		className: "sec_left"
	};
	$('#condition_left').dataSelector(opt_condition_left);
	
    var middleParen=[
                    {"title":"等于","key" :"="},
                    {"title":"大于" ,"key":">"},
                    {"title":"小于" ,"key":"<"},
                    {"title":"大于等于" ,"key":">="},
                    {"title":"小于等于" ,"key":"<="}
                    ];
                    
    var opt_condition_middle={
		multiple:false,
		data:middleParen,
		size:1,
		className: "sec_left"
	};
	$('#condition_middle').dataSelector(opt_condition_middle);
	
	var rightParen=[
	                {"title":"请选择","key":" "},
	                {"title":")","key":")"},
                    {"title":"))","key":"))"},
                    {"title":")))","key":")))"},
                    {"title":"))))","key":"))))"}
                    ];
    var opt_condition_right={
		multiple:false,
		data:rightParen,
		size:1,
		className: "sec_left"
	};
	$('#condition_right').dataSelector(opt_condition_right);
	//关联条件
	var condition_paren=[
                    {"title":"等于","key":"="},
                    {"title":"左连接","key":"left join"}
                    ];
    var opt_condition_paren={
		multiple: false,
		data: condition_paren,
		size: 1,
		className: "sec_left"
	};
	$('#condition_paren').dataSelector(opt_condition_paren);
	
	//查询条件
	var condition_cond=[
					{"title":" ", "key":" "},
                    {"title":"并且(AND)","key":"AND"},
                    {"title":"或者(OR)","key":"OR"}
                    ];
    var opt_condition_cond={
		multiple: false,
		data: condition_cond,
		size: 1,
		className: "sec_left"
	};
	$('#condition_cond').dataSelector(opt_condition_cond);
	setOperator('#condition_item_div', '#condition_middle');
})

//选择数据表的操作
function leftToRight(){
	var checkTab = $('#tbl_div').dataSelector('removeSelectedNodes');
	$('#checkTbl_div').dataSelector('addNodes',checkTab);
	$('#condition_div').dataSelector('addNodes',checkTab);
	$('#leftTbl_div').dataSelector('addNodes',checkTab);
	$('#rightTbl_div').dataSelector('addNodes',checkTab);
	var left_item= $('#leftItem_div').dataSelector("getAllNodes");
	var check_table= $('#checkTbl_div').dataSelector("getAllNodes");
	//alert(check_table.length);
	if(check_table.length>=2){
	   $('#relation_div').show();
	   $('#rightTbl_div').find("option:eq(1)")[0].selected = true;
	   getDataItemByKey( $('#rightTbl_div').find("option:selected").val(),"rightItem_div");
	}else{
	   $('#relation_div').hide();
	}
	//如果第一次填充数据表，加载数据字段的数据
	if(left_item.length==0){
	   //左表字段数据填充
	   getDataItemByKey(checkTab[0].key,"leftItem_div");
	   getDataItemByKey(checkTab[0].key,"condition_item_div");		
	}
	//填充预览标签已选表
	var tables=$('#checkTbl_div').dataSelector('getAllNodes');
	var tables_html="";
	for(var i=0;i<tables.length;i++){
	  tables_html+= (tables_html=="" ? tables[i].title : ","+tables[i].title);
	}
	$("#cond_checkTable").html(tables_html);
}

//选择数据表-到已选数据表
function checkTable(key){
	leftToRight();
}

//删除已选择表的操作
function backTable(key){
	//检查是否配置了表关联关系
	var selData = $("#selected_columns").tablet("getAllData");
	console.log(selData)
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(selData.data[ii].leftTable.id == key){
			alert("表 "+selData.data[ii].leftTable.name_cn+"["+selData.data[ii].leftTable.name_en+"] 已配置表关联条件,不能删除.");
			return;
		}
		if(selData.data[ii].rightTable.id == key){
			alert("表 "+selData.data[ii].rightTable.name_cn+"["+selData.data[ii].rightTable.name_en+"] 已配置表关联条件,不能删除.");
			return;
		}
	}
	
	//检查是否配置了查询条件
	selData = $("#table_param").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(selData.data[ii].leftTable.id == key){
			alert("表 "+selData.data[ii].leftTable.name_cn+"["+selData.data[ii].leftTable.name_en+"] 已配置查询条件,不能删除.");
			return;
		}
	}
    var backedTab = $('#checkTbl_div').dataSelector('removeSelectedNodes');
    
	//为了非当前主体下的表删除时不在列表中出现，删除时刷新一次重新获取，并删除重复。
	getTable($('#busiTopic_div_id').val(), 'tbl_div');
	//$('#tbl_div').dataSelector('addNodes',checkTab);
	$('#leftTbl_div').dataSelector('removeNodes',key);
	$('#rightTbl_div').dataSelector('removeNodes',key);
	$('#condition_div').dataSelector('removeNodes',key);
	var check_table= $('#checkTbl_div').dataSelector("getAllNodes");
	
	if(check_table.length == 0){
		//setFormFieldValue("record:sql", " ");
		document.getElementById('table_sql').value = "";
		$('#condition_item_div').find("option").remove();
	}else{
		var nowTbl = $('#condition_div_id').find("option:selected");
		var item_data=getDataItemByKey(nowTbl.val(),"condition_item_div");	
	}
	if(check_table.length>=2){
	   $('#relation_div').show();
	   if($('#leftTbl_div_id').find("option:selected").attr("id")!=$('#rightTbl_div_id').find("option[index=1]").attr("id"))
		   $('#rightTbl_div_id').find("option[index=1]").attr("selected", true);
	   else{
		   $('#rightTbl_div_id').find("option[index=0]").attr("selected", true);
	   }
	}else{
	   $('#relation_div').hide();
	   $('#leftItem_div_id').empty();
	   $('#leftItem_div_id').empty();
	}
	//alert("nihao");
}

function rightToLeft(){
	/* var keySingle = $('#checkTbl_div').dataSelector("getSelectedNodes");
	if(keySingle.length < 1)
		return;
	var selData = $("#selected_columns").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(selData.data[ii].leftTable.id == keySingle[0].key){
			alert("该表已配置表关联条件,不能删除.");
			return;
		}
		if(selData.data[ii].rightTable.id == keySingle[0].key){
			alert("该表已配置表关联条件,不能删除.");
			return;
		}
	}
	
	//检查是否配置了查询条件
	selData = $("#table_param").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(selData.data[ii].leftTable.id == keySingle[0].key){
			alert("该表已配置查询条件,不能删除.");
			return;
		}
	} */

	var checkResult = checkUsedTb();
	if(checkResult){
		var checkTab = $('#checkTbl_div').dataSelector('removeSelectedNodes');
		//为了非当前主体下的表删除时不在列表中出现，删除时刷新一次重新获取，并删除重复。
		getTable($('#busiTopic_div_id').val(), 'tbl_div');
		//$('#tbl_div').dataSelector('addNodes',checkTab);
		if(checkTab != "" ){
			for(var i=0;i<checkTab.length;i++){
			        var key = checkTab[i].key;
					$('#leftTbl_div').dataSelector('removeNodes',key);
					$('#rightTbl_div').dataSelector('removeNodes',key);
					$('#condition_div').dataSelector('removeNodes',key);
					var check_table= $('#checkTbl_div').dataSelector("getAllNodes");
					if(check_table.length>=2){
					   $('#relation_div').show();
					   if($('#leftTbl_div_id').find("option:selected").attr("id")!=$('#rightTbl_div_id').find("option[index=1]").attr("id"))
						   $('#rightTbl_div_id').find("option[index=1]").attr("selected", true);
					   else{
						   $('#rightTbl_div_id').find("option[index=0]").attr("selected", true);
					   }
					}else{
					   $('#relation_div').hide();
					   $('#leftItem_div_id').empty();
					   $('#leftItem_div_id').empty();
					}
			}
	    }
	}
}

//删除表之前检查是否在表关联条件和查询条件两处是否有引用
function checkUsedTb(){
	var flag = true;
	var keyAll = $('#checkTbl_div').dataSelector("getSelectedNodes");
	var selData = $("#selected_columns").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	var keys = '';
	for(var jj=0; jj<keyAll.length; jj++){
		keys += keyAll[jj].key;
	}
	for(var ii=0; ii<selData.data.length; ii++){
		if(keys.indexOf(selData.data[ii].leftTable.id) > -1){
			alert("表 "+selData.data[ii].leftTable.name_cn+"["+selData.data[ii].leftTable.name_en+"] 已配置表关联条件,不能删除.");
			flag = false;
			break;
		}
		if(keys.indexOf(selData.data[ii].rightTable.id) > -1){
			alert("表 "+selData.data[ii].rightTable.name_cn+"["+selData.data[ii].rightTable.name_en+"] 已配置表关联条件,不能删除.");
			flag = false;
			break;
		}
	}
	
	//检查是否配置了查询条件
	selData = $("#table_param").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(keys.indexOf(selData.data[ii].leftTable.id) > -1){
			alert("表 "+selData.data[ii].leftTable.name_cn+"["+selData.data[ii].leftTable.name_en+"] 已配置查询条件,不能删除.");
			flag = false;
			break;
		}
	}
	return flag;
}

function rightToLeftAll(){
	var checkResult = checkUsedTb();
	if(checkResult){
		var checkTab = $('#checkTbl_div').dataSelector('selectAllBlockNodes');
		rightToLeft();
	}
}

//最终提交方法
function getSelect(){
    //获取选择的数据表
	var e= $('#checkTbl_div').dataSelector("getAllNodes");
	var tableIds="";
	var tableNames="";
	var tableCodes="";
	if(e.length==0){
	  alert("请选择数据表!");
	  return;
	}else{
	  for(var ii=0;ii<e.length;ii++){
	    tableIds+= (tableIds=='' ? e[ii].key :','+e[ii].key);
	    tableNames+= (tableNames=='' ? e[ii].title :','+e[ii].title);
	    tableCodes+= (tableCodes=='' ? e[ii].code :','+e[ii].code);
	  }
	}
	setFormFieldValue("record:table_id", tableIds);
	setFormFieldValue("record:table_name_cn", tableNames);
	setFormFieldValue("record:table_code", tableCodes);
	//拼装sql
	setFormFieldValue("record:sql", createSql('1'));
	
	//获取表关联关系值
	var relation=$("#selected_columns").tablet("getAllData");
	if(relation!='{data:[]}'){
	   setFormFieldValue("record:condition", relation);
	   setFormFieldValue("record:sql", createSql('2'));
	}
	
	//获取查询条件值
	var param=$("#table_param").tablet("getAllData");
	if(param!='{data:[]}'){
	   setFormFieldValue("record:param", param);
	   setFormFieldValue("record:sql", createSql('3'));
	}
	//alert(getFormFieldValue("record:sql"));
	var checkResult=testSql(getFormFieldValue("record:sql"));
	if(checkResult==false){
		return;
	}
	
	
	//saveAndExit( '', '保存共享服务-基础接口表' );
}			


