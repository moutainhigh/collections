<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>增加共享服务基础接口配置信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jazz.js"	type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/page/seniorQuery/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/page/seniorQuery/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/jquery-ui.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/jquery-plugin-tab/jquery.tabs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<!-- 引入form表单 -->
<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/share/select.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/share/public-function.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/share/myform.js"></script> -->
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/share/share_interface.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/seniorQuery/script/share/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sczt/base64.js"></script>

<style type="text/css">
*{margin: 0;padding:0}
body{width: 980px;margin: 0 auto;}

.sec_left{
	color: #666;
}

.queryBtn{
	color: #666;
}


body{font-size: 12px;color: #666;background: #F8F9FB;overflow-x: hidden;}
td{font-size: 12px;color: #666;background: #DEE6E9;}
option{color: #666}
.readonly{background: #ccc;}

#getColumDetail tr:hover{background:#FBF8E5}

.noBorder{border: 0;border: none;}
</style>


<script>

function limConTdWidth(){
	$("#table_param  tr").each(function(){
		var s=$(this).children("td").eq(5).width(200);
	});
}
// 返 回
function func_record_goBack(){
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

</script>
<script>
	var busiSystemData ;
	var currentSystem ;
	var busiTopicData ;
	var currentTopic ;
	var tblData ;
	var checkTblData = new Array;
	var checkedTblCount = 0;
	var leftTblData = new Array;
	var leftDataItem = new Array;
	var rightTblData = new Array;
	var rightDataItem = new Array;
	var condition = new Array;
	var condition_item = new Array;

	
$(function(){
	__userInitPage();
    //隐藏关联条件div
	$('#relation_div').hide();
	//加载业务主题
	//发送异步ajax到后台请求得到对应的业务主题
	$.ajax({
		url:"../../advQuery/getTopic.do",
		type:"get",
		dataType:"json",
		success:function(data){
			busiTopicDataList = data.data[0].data;
			var opt_Topic={
					data : busiTopicDataList,
					onClick:function(event,key){
						alert(11);
						getTable(key,'tbl_div');
						//初始化数据表列表数据,因为tblData默认为初始化为空
						$("#checkTbl_div_id").empty();
						$("#rightTbl_div").hide();
						$("#leftTbl_div_id").empty();
						$("#leftItem_div_id").empty();
						$("#rightTbl_div_id").empty();
						$("#rightItem_div_id").empty();
						$("#condition_div_id").empty();
						$("#listColumn tbody").empty(); 
						
						$('#busiTopic_div').dataSelector(opt_Topic);
					}
				}
			//添加对应栏目
			$('#busiTopic_div').dataSelector(opt_Topic);//将表的数据对像传递给对应的主题表栏目
		}
	});
	
	var opt_table={
			data:tblData,
		}
  $('#tbl_div').dataSelector(opt_table);//加载选择的数据表第二个栏目
	
	
	
	//初始化已选数据表列表【注意是已经选择，他里面只有双击时间】
	var opt_check={
		data:checkTblData,
		onDblclick:function(event,key){
			
			$("#columnList").empty();//清空当前表的字段信息			
			
			var _html = $(this).html();
			//alert(11);
			//backTable(key);//148
			$.ajax({
				url:"/query/advQuery/getColumn.do?tableId="+key,
				type:"get",
				dataType:"json",
				success:function(data){
					$("#getSelectColumn tbody tr");
					var jsonStr  = data.data[0].data;
					//console.log(jsonStr[0].code +"=== > " +jsonStr[0].colType  );
					for (var i=0;i<jsonStr.length;i++) {//<td style='width:117px'>"+_html+"</td>
						$("#columnList").append("<option  data-keys="+jsonStr[i].key+" data-name_en="+encode(jsonStr[i].dcTableNameEn)+" data-name_cn="+encode(jsonStr[i].dcTableNameCn)+" data-collength="+jsonStr[i].colLenght+ " data-column_cn ="+encode(jsonStr[i].title)+" data-column_en ="+encode(jsonStr[i].code) +" data-columntype = "+encode(jsonStr[i].colType)+">"+jsonStr[i].title+"</option>");
   							// "<td style='width:0px' onclick='addAlia(this)' data-name_en="+jsonStr[i].dcTableNameEn+" data-name_cn="+jsonStr[i].dcTableNameCn+" data-column_cn ="+jsonStr[i].title+"   data-column_en ="+jsonStr[i].code+"></td>"+
   							//"<td class='alias' style='width:60px' data-name_en="+jsonStr[i].dcTableNameEn+" data-name_cn="+jsonStr[i].dcTableNameCn+" data-column_cn ="+jsonStr[i].title+"   data-column_en ="+jsonStr[i].code+">"+jsonStr[i].colType+"</td>"+
   							//"<td style=' width:60px'>"+jsonStr[i].colLenght+"</td>"+
   							// "<td nowrap style='width:60px'><a href='javascript:;' data-app="+jsonStr[i].key+" onclick='delItem(this)'>删除</a></td></tr>" 
   							
					}
					
					
					
					//$("#columnList").find("option").on("click",function(){
						$("#columnList").on('click',function (){
						//var opt = $(this).children('option:selected');
						var _this = $(this).children('option:selected');
						var key = _this.data("keys");
						var name_en = decode(_this.data("name_en"));
						var name_cn = decode(_this.data("name_cn"));
						var column_cn = decode(_this.data("column_cn"));
						var column_en = decode(_this.data("column_en"));
						var columntype = decode(_this.data("columntype"));
						var collength =_this.data("collength");
						var items = $("#selectColumnList option");
						var isExist = false;
						for (var i = 0; i < items.length; i++) {
							 if($("#selectColumnList").get(0).options[i].value == column_cn){     
				                   isExist = true;     
				                   break;     
					           }
					      } 
						var flag = false;
						var columnDetail = $("#getColumDetail tr");
						columnDetail.each(function(){
							var _this = $(this);
							var cn = decode(_this.data("column_cn")); //得到当前表的列中已经加入到当前的列表之中
							console.log(cn)
						});
						
						if(!isExist){
							_this.remove();
							$("#selectColumnList").append("<option data-keys="+key+" data-name_en="+encode(name_en)+" data-name_cn="+encode(name_cn) +" data-column_en ="+encode(column_en) +" data-column_cn="+encode(column_cn)+" data-collength="+collength+" data-columntype = "+encode(columntype)+" >"+column_cn+"</option>");
						}
						
						$("#selectColumnList").off("click").on("click",function(){
								var _this = $(this).children('option:selected');
								//var _this = $(this);
								var key = _this.data("keys");
								var name_en = decode(_this.data("name_en"));
								var name_cn = decode(_this.data("name_cn"));
								var column_cn = decode(_this.data("column_cn"));
								var column_en = decode(_this.data("column_en"));
								var columntype = decode(_this.data("columntype"));
								var collength =_this.data("collength");
								_this.remove();
								$("#getColumDetail").append("<tr data-keys="+key+" data-name_en="+encode(name_en)+" data-name_cn="+encode(name_cn) +" data-column_en ="+encode(column_en) +" data-column_cn="+encode(column_cn)+" data-collength="+collength+" data-columntype = "+encode(columntype)+"><td>"+name_cn+"</td><td>"+column_cn+"</td><td>"+columntype+"</td><td>"+collength+"</td><td><a href='javascript:;' onclick='clearSelect(this)'>删除</td></tr>");
							});
					});
					
				}
				
			});
		}}
	$('#checkTbl_div').dataSelector(opt_check);
	///初始化结束
	
	
	/********   配置查询范围开始    ***********/
	//出现配置查询范围拼接查询条件的大的DIV容器       【初始化关联关系右表,】
	var opt_rightTable={
		multiple:false,
		data:rightTblData,
		size:1,
		onChange : function(event,key){
			getDataItemByKey(key,"rightItem_div");
		}
	}
	$('#rightTbl_div').dataSelector(opt_rightTable);	
	
	/*第一个表开始*/
	//出现配置查询范围下方表选择的第一个下拉列【初始化关联关系左表】,不能同时选择两个相同的表并对其字段作为拼接查询
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

	//初始化关联关系左表字段列表，第一个表的字段
	var opt_leftDataItem={
		multiple:false,
		data:leftDataItem,
		size:1
	}
	$('#leftItem_div').dataSelector(opt_leftDataItem);//配置查询范围的数据项选择，用于拼接表字段的第一个下拉选中要配置作为查询条件的字段
	
	
	
	
	//初始化关联关系左表字段列表,即第二个表的字段
	var opt_rightDataItem={
		multiple:false,
		data:rightDataItem,
		size:1
	}
	$('#rightItem_div').dataSelector(opt_rightDataItem);
	
	/********   配置查询范围结束    ***********/
	
	/******  配置查询条件   *****/
	//初始化配置查询条件数据表列表
	var opt_condition={
		multiple:false,
		data:condition,
		size:1,
		onChange:function(event,key){
			//getColumnByKey(key,"checkTbl_div");
			getDataItemByKey(key,"condition_item_div");
		}
	}
	$('#checkTbl_div').dataSelector(opt_condition);//配置查询条件===>已经选择表
	
	//*叶*/
	var opt_showTableColumn={
			multiple:false,
			data:condition,
			size:1,
			onChange:function(event,key){
				getDataItemByKey(key,"condition_item_div");
			}
		}
		$('#condition_div').dataSelector(opt_condition);//配置查询条件===>选择表
	//*叶*/
	
	
	
	//初始化配置查询条件数据项列表===>选择数据项
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
	//setOperator('#condition_item_div', '#condition_middle');
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
	var keySingle = $('#checkTbl_div').dataSelector("getSelectedNodes");
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
	} 

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
/* function getSelect(){
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
	//setFormFieldValue("record:table_id", tableIds);
	//setFormFieldValue("record:table_name_cn", tableNames);
	//setFormFieldValue("record:table_code", tableCodes);
	//拼装sql
	//setFormFieldValue("record:sql", createSql('1'));
	
	//获取表关联关系值
	var relation=$("#selected_columns").tablet("getAllData");
	if(relation!='{data:[]}'){
	  // setFormFieldValue("record:condition", relation);
	  // setFormFieldValue("record:sql", createSql('2'));
	}
	
	//获取查询条件值
	var param=$("#table_param").tablet("getAllData");
	if(param!='{data:[]}'){
	  // setFormFieldValue("record:param", param);
	   //setFormFieldValue("record:sql", createSql('3'));
	}
	//alert(getFormFieldValue("record:sql"));
	var checkResult=testSql(getFormFieldValue("record:sql"));
	if(checkResult==false){
		return;
	}
	
	
}	 */		


</script>
<style>
.selectTopWap{
  background: #F7F7F7;
  width: 500px;
  padding-left:10px;
  margin-top: 4px;
  margin:5px auto;
}
.tabs{
	border-top: 1px solid #ccc;
	margin-top: -20px;
}
</style>
</head>
<body>
<div class="title_nav" >当前位置：高级查询> 高级查询> 高级查询配置 > <span>新增高级查询</span></div>

<div class="selectTopWap">
	<span>请选择主题:</span><select id="pid" onchange="gradeChange()" style="width:400px;margin:10px auto">
	    <option  value="0">请选择...</option>
	</select>
</div>
<br/><br/>

<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center" style="border-collapse:collapse;">
<tr>
<td>
<div style="width:100%;">
<dl class="tabs" id="tabs">
     <dt>
	        <a style="margin-left: -10px">配置查询范围</a>
	      	<a>配置查询条件</a>
	      	<a>预览</a>
     </dt>
      	
     <!-- 第1个标签页开始 -->
     <dd>
     <div>
      <table class="dd_table" border="0" cellpadding=3 cellspacing=0 width="100%" align="center">
		<tr>
		    <!-- <td width="100">选择主题：</td> -->
			<td width="10%">选择数据表：</td>
			<td width="5%" ></td>
			<td width="10%">已选数据表：</td>
			<td width="5%"></td>
			<td width="10%">数据项</td>
			<td width="5%" ></td>
			<td width="5%">已选择列</td>
			<td width="5%" ></td>
			<td width="10%">结果集</td>
		</tr>
			<tr>
				<td width="10%" valign="top">
					<div id="tbl_div"></div>
				</td>
				<td align="center" valign="middle" width="5%" valign="top">
	   				<!-- <button onclick="rightToLeft()"> &lt;&nbsp; </button>  -->
		   			<button onclick="leftToRight()" > &gt;&nbsp;</button> 
		   			<br/><br/>
		   			<!-- <button onclick="rightToLeftAll()"> &lt;&lt; </button>  -->
		   			<!-- <button onclick="leftToRightAll()"> &gt;&gt; </button>  -->
	   			</td>
	   			<td width="10%" valign="top"><div id="checkTbl_div"></div></td>
	   			<td align="center" valign="middle" width="5%" valign="top">
	   				<button onclick="leftToRight()" > &gt;&nbsp;</button> 
	   				<!-- <button onclick="rightToLeft()"> &lt;&nbsp; </button> 
		   			<button onclick="leftToRight()" > &gt;&nbsp;</button> 
		   			<br/><br/>
		   			<button onclick="rightToLeftAll()"> &lt;&lt; </button> 
		   			<button onclick="leftToRightAll()"> &gt;&gt; </button>  -->
	   			</td>
	   			<td width="5%" valign="top" >
	   				<select id="columnList" multiple="multiple" size="10" class="cssSelect"></select>
	   			<td width="5%" align="center" valign="middle">
	   			<!-- <button onclick="rightToLeft()"> &lt;&nbsp; </button>  -->
	   				<button onclick="leftToRight()" > &gt;&nbsp;</button> 
		   			<!-- <button onclick="leftToRight()" > &gt;&nbsp;</button> 
		   			<br/><br/>
		   			<button onclick="rightToLeftAll()"> &lt;&lt; </button> 
		   			<button onclick="leftToRightAll()"> &gt;&gt; </button> </td> -->
	   			<td width="5%" valign="top">
	   			<select id="selectColumnList" multiple="multiple" size="10" class="cssSelect"></select>
	   			
	   			</td>
	   			
	   			
	   			<td width="5%" align="center" valign="middle">
	   				<button onclick="leftToRight()" > &gt;&nbsp;</button> 
	   				<!-- <button onclick="rightToLeft()"> &lt;&nbsp; </button> 
		   			<button onclick="leftToRight()" > &gt;&nbsp;</button> 
		   			<br/><br/>
		   			<button onclick="rightToLeftAll()"> &lt;&lt; </button> 
		   			<button onclick="leftToRightAll()"> &gt;&gt; </button>  -->
		   		</td>
	   			
	   			<td valign="top" width="10%">
	   				<style>
	   					.selectColumsWrap{height: 200px;overflow-y:scroll; background:#DEE6E9}
	   					.selectColumsWrap th{font-weight: normal;font-size: 12px;padding: 6px 4px;}
	   					#getColumDetail tr:hover{background: #FFFFEE;}
	   					#getColumDetail td{text-align: center;padding:4px 5px;border-bottom: 1px solid #CFCFFE;
   						 border-left: 1px solid #CFCFFE;  background: #F7F7F7;}
	   				</style>
	   				<div class="selectColumsWrap" style="height: 200px;overflow-y: scroll">
	   						<table style="width:360px" border="0" cellspacing="0" cellpadding="0">
	   							<thead>
			   					<tr>
		   							<th nowrap width='10%'>表名</th>
		   							<th width='10%'>数据项名</th>
		   							<th width='10%'>类型</th>
	   								<th width='10%'>长度</th>
	   								<th width="10%">操作</th>
	   							</tr>
	   							</thead>
	   							<tbody id="getColumDetail">
	   							</tbody>
	   						</table></div>
	   			
	   			</td>
			</tr>
		</table>
		<br />
		<div id="relation_div">
			<table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
			<tr><td width="15%">表选择</td><td width="25%"><div id="leftTbl_div"></div></td><td colspan="2"></td>
				<td width="15%">表选择</td><td width="25%"><div id="rightTbl_div"></div></td>
			</tr>
			<tr><td width="15%">数据项选择</td><td width="25%"><div id="leftItem_div"></div></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;条件</td>
				<td><div id="condition_paren"></div></td>
				<td width="15%">数据项选择</td><td width="25%"><div id="rightItem_div"></div>
			</tr>
			<tr><td colspan="6" align="right"><input class="queryBtn" type="button" value="添加关联条件" onclick="addRelation();"/>&nbsp;&nbsp;</td></tr>
			<tr><td colspan="6"><div id="selected_columns"></div></td></tr>
			</table>
		</div><br />
		</div>
 </dd><!-- 第1个标签页结束 -->
 
 <!-- 第2个标签页开始 -->
  <dd><div>
  <table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
		<tr align="center">
		   <td width="5%">逻辑条件</td>
		   <td width="5%">括弧</td>
		   <td>选择表</td>
		   <td>选择数据项</td>
		   <td>条件</td>
		   <td>值</td>
		   <td>括弧</td>
		 </tr>
		 <tr>
		   <td><div id="condition_cond"></div></td><!-- 配置查询条件的逻辑条件 -->
		   <td><div id="condition_left"></div></td><!-- 配置查询条件的 括弧 -->
           <td><div id="condition_div"></div></td><!-- 配置选择表 -->
           <td><div id="condition_item_div"></div></td><!-- 选择数据项 -->
           <td><div id="condition_middle"></div></td><!-- 条件 -->
	       <td onmouseover="showSelectedValue(this);"><!-- 站位填充 -->
	      		<input class="queryBtn" type="text" name="param_value" id="paramValue"  class="readonly"/> 
	       		<select id='pvs' style='display:none;width:100px;'></select> 
	       </td>
	       <td><div id="condition_right"></div></td><!-- 括弧回来， 意味着sql语句结束 -->
	     </tr>
     <tr><td colspan="7" align="right"><input type="button" value="添加查询条件" onclick="addParam();"/>&nbsp;&nbsp;</td></tr>
     <tr><td colspan="7"><div id="table_param"></div></td></tr>
	</table><br /></div>
  </dd><!-- 第2个标签页结束 -->
  
  <!-- 第3个标签页开始 -->
  <dd>  <div>
  <style>
  .displaySql td{font-size: 14px;padding:4px 6px;}
  
  </style>
   <table class="dd_table displaySql" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
		<tr><td width="15%">选择数据表为:</td><td align="left"><div id="cond_checkTable">无</div></td></tr>
		<tr><td>数据表关联关系:</td><td><div id="cond_condition">无</div></td></tr>
		<tr><td>数据表查询条件:</td><td><div id="cond_param">无</div></td></tr>
		<tr><td>SQL语句:</td><td><!-- <div id="sql_last"></div> -->
			<textarea id="sql_last" style="width: 100%; height: 120px;" readOnly=""></textarea>
		</td></tr>
		<tr><td colspan="2"><a href="javascript:preview();">点击查看预览</a></td></tr>
		 <tr>
		 <td colspan="2">
			 <div class="dataListTable"  style="height:280px; min-width:980px ">
			 	<iframe id="listResult" height="100%" width="100%" scrolling="yes" allowtransparency="yes" frameborder="no" border="0" marginwidth="0" marginheight="0"   width="100%"></iframe>
			 </div>
		 </td></tr> 
	</table><br /></div>
  </dd><!-- 第3个标签页结束 -->
</dl>
</div>
</td>
</tr>
</table>


<!-- <form id="form1" name="form1" action="" method="post" target="preview_data">
  <input type="hidden" name="tableIds" id="tableIds"/>
  <input type="hidden" name="table_sql" id="table_sql"/>
  <input type="hidden" name="buildSql" id="buildSql"/>
</form>
 -->

<script type="text/javascript">
function delItem(obj){
	var _this  = $(obj);
	alert(_this.data("app"))
}

function editItem(obj){
	var _this  = $(obj);
	alert(_this.data("app"));
}


$(function(){
	$.ajax({
		url:"../../advQuery/getTopic.do",
		type:"get",
		dataType:"json",
		success:function(data){
			var datas = data.data[0].data
			for ( var i in datas) {
				$("#pid").append(" <option value="+datas[i].key+">"+datas[i].title+"</a>");
			}
		 }
	 }
  )
});

function gradeChange(){
	var key = $('#pid option:selected').val();
	var queryName = $('#pid option:selected').text();
	
	if(queryName=="请选择..."){
		$("#tbl_div").dataSelector('destroy');
	    $('#tbl_div').dataSelector([]);
	} 
	
	$.cookie("queryName",queryName);
	var tblData = [];
	$.ajax({
		url:"../../advQuery/getTable.do",
		data:{topicId:key},
		type:"get",
		dataType:"json",
		success:function(data){
			tblData = data.data[0].data;
			//将点击的数据添加到右边选中的内容到选择的数据表中
			var opt = {
						data : tblData,
						onClick : function(event, key) {
						    checkTable(key);//添加到已选择表
					    }
				}
			    $("#tbl_div").dataSelector('destroy');
			    $('#tbl_div').dataSelector(opt);
			    $("#checkTbl_div_id").empty();
				$("#rightTbl_div").show();
				$("#leftTbl_div_id").empty();
				$("#leftItem_div_id").empty();
				$("#rightTbl_div_id").empty();
				$("#rightItem_div_id").empty();
				$("#condition_div_id").empty();
				$("#listColumn tbody").empty(); 
				$("#selectColumnList").empty();
				$("#columnList").empty();
				$("#getColumDetail").empty();
				
				$("#relation_div").hide();//隐藏关联关系
			
			   deleteCheckTableFromTableList();
			
		}
		
	});
	
}


/* function gradeChange(){
	$.ajax({
		url:"../../advQuery/getTopic.do",
		type:"get",
		dataType:"json",
		success:function(data){
			busiTopicDataList = data.data[0].data;
			var opt_Topic={
					data : busiTopicDataList,
					onClick:function(event,key){
						getTable(key,'tbl_div');
						//初始化数据表列表数据,因为tblData默认为初始化为空
						/* $("#checkTbl_div_id").empty();
						$("#rightTbl_div").hide();
						$("#leftTbl_div_id").empty();
						$("#leftItem_div_id").empty();
						$("#rightTbl_div_id").empty();
						$("#rightItem_div_id").empty();
						$("#condition_div_id").empty();
						$("#listColumn tbody").empty(); 
						 */
						//$('#busiTopic_div').dataSelector(opt_Topic);
					//}
				//}
			//添加对应栏目
			//$('#tbl_div').dataSelector(opt_Topic);//将表的数据对像传递给对应的主题表栏目
	//	}
	//}); */
	
	
	
</script>
</body>
</html>
