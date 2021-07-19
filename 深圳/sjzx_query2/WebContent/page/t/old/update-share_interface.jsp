<%@ page contentType="text/html; charset=GBK" %>
<%@page import="cn.gwssi.common.context.DataBus"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="350">
<head>
<title>修改共享服务基础接口配置信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-tab/jquery.tabs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/share/share_interface.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
<%
DataBus db = (DataBus)request.getAttribute("freeze-databus");
%>
<style type="text/css">
.sec_left{
}
</style>
</head>

<script language="javascript">
var busiSystemData ;
var currentSystem ;
var busiTopicData=eval('(<%=db.getValue("dataSystem")%>)'); ;
var currentTopic ;
var tblData =eval('(<%=db.getValue("dataTable")%>)');
var checkTblData = eval('(<%=db.getValue("dataString")%>)');
var ori_checkTblData = eval('(<%=db.getValue("dataString")%>)');
var checkedTblCount = 0;
var leftTblData = eval('(<%=db.getValue("dataString")%>)');
var leftDataItem = new Array;
var rightTblData = eval('(<%=db.getValue("dataString")%>)');
var rightDataItem = new Array;
var condition = eval('(<%=db.getValue("dataString")%>)');
var condition_item = new Array;
var selected_service_targets_id = '<%=db.getValue("selected_service_targets_id")%>';
var selected_topics_id = '<%=db.getValue("selected_topics_id")%>';
ori_tableCondition = eval('(<%=db.getValue("tableCondition")%>)');
ori_tableParam = eval('(<%=db.getValue("tableParam")%>)');

function limConTdWidth(){
$("#table_param  tr").each(function(){
var s=$(this).children("td").eq(5).width(200);
});
}

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存共享服务-基础接口表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存共享服务-基础接口表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存共享服务-基础接口表' );	// /txn401001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn401001.do
}

//鼠标放上时展示当前选中的代码值
function showSelectedValue(thistd)
{
   var selectOp = window.document.getElementById('pvs');
  if( selectOp.selectedIndex>-1){
	thistd.title=document.getElementById('pvs').options[selectOp.selectedIndex].text;
	}
	else{
	thistd.title="";
	}
}

function deleteReturn(index){
	//return false;
	var data = $("#table_param").tablet("getAllData");
	data = eval('('+ data +')');
	if(data.data.length == 0){
		$("#condition_cond").attr("disabled", true);
	}else{
		$("#condition_cond").attr("disabled", false);
	}
}
var being_used = 'false';
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	being_used = getFormFieldValue('primary-key:being_used');
	 $.tabs({
        selector: "#tabs",
        selected: 0,
        click: function(index, instance) {
        	if(index==1){
        		var data = $("#table_param").tablet("getAllData");
        		data = eval('('+ data +')');
        		if(data.data.length == 0){
        			$("#condition_cond").attr("disabled", true);
        		}else{
        			$("#condition_cond").attr("disabled", false);
        			$("#condition_cond option[value='']").remove();
        			$("#condition_cond option[value=' ']").remove();
        		}
        	}
          if(index==2){
        	showSql();
          	var	tempParam=$("#table_param").tablet("getAllData");
   			bulidTableParam(tempParam);
   			tempParam=$("#selected_columns").tablet("getAllData");
          	bulidTableCondition(tempParam);
        }
        }
      });
      //加载服务对象列表（业务系统）
$.ajax({
	  type: "get",
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
});
	
$(function(){
	//初始化业务系统列表数据
	var opt={
		data:busiSystemData,
		onClick:function(event,key){
			getTopic(key,'busiTopic_div');
		}
	}
	$('#busiSystem_div').dataSelector(opt);
	$("#"+selected_service_targets_id).attr("selected", true);
	
	//初始化业务主题列表数据
	var opt_Topic={
		data:busiTopicData, 
		onClick: function(event, key){
           getTable(key, 'tbl_div');
        }
	}
	$('#busiTopic_div').dataSelector(opt_Topic);
	$("#"+selected_topics_id).attr("selected", true);
	
	//初始化数据表列表数据	
	var opt_table={
		data:tblData,
		onClick: function(event, key){
            checkTable(key);
        }
	}
	$('#tbl_div').dataSelector(opt_table);	
	
	//初始化已选数据表列表
	var opt_check={
		data: checkTblData,
		onDblclick:function(event,key){
			backTable(key);
		}}
	$('#checkTbl_div').dataSelector(opt_check);
	
	//初始化关联关系左表
	var opt_leftTable={
		multiple: false,
		data: leftTblData,
		size: 1,
		onChange:function(event,key){
			getDataItemByKey(key,"leftItem_div");
		}
	}
	$('#leftTbl_div').dataSelector(opt_leftTable);
	
	//初始化关联关系右表
	var opt_rightTable={
		multiple:false,
		data:rightTblData,
		size:1,
		onChange:function(event,key){
			getDataItemByKey(key,"rightItem_div");
		}
	}
	$('#rightTbl_div').dataSelector(opt_rightTable);	
	
	//初始化关联关系左表字段列表
	var opt_leftDataItem={
		multiple:false,
		data:{},
		size:1
	}
	$('#leftItem_div').dataSelector(opt_leftDataItem);
	
	//初始化关联关系左表字段列表
	$('#rightItem_div').dataSelector(opt_leftDataItem);
	$('#condition_item_div').dataSelector(opt_leftDataItem);
	
	//填充数据项列表
	if(checkTblData.length>0){
	  getDataItemByKey(checkTblData[0].key,"leftItem_div");
	  getDataItemByKey(checkTblData[0].key,"rightItem_div");
	  getDataItemByKey(checkTblData[0].key,"condition_item_div");
	}
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
		onDelete:re_cond_init,
		data:data4selected_table
	};
	$("#selected_columns").tablet(opt_cond_tab);
	$("#selected_columns").tablet("addData",eval('(<%=db.getValue("tableCondition")%>)'));
	if(being_used == 'true'){
		//如果接口正在使用 屏蔽掉删除操作
		$("#selected_columns").find('td a').remove();
	}
	//初始化预览标签
	bulidTableCondition(eval('(<%=db.getValue("tableCondition")%>)'));
    
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
		addOper:true,
		onDelete:re_param_init,
		data:param_table
	};
	$("#table_param").tablet(opt_param_table);
	$("#table_param").tablet("addData",eval('(<%=db.getValue("tableParam")%>)'));
	if(being_used == 'true'){
		//如果接口正在使用 屏蔽掉删除操作
		$("#table_param").find('td a').remove();
	}
	
	//初始化预览标签
	bulidTableParam(eval('(<%=db.getValue("tableParam")%>)'));
	
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
                    {"title":" ","key":" "},
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
	//填充预览标签已选表
	var tables=$('#checkTbl_div').dataSelector('getAllNodes');
	var tables_html="";
	for(var i=0;i<tables.length;i++){
	  tables_html+= (tables_html=="" ? tables[i].title : ","+tables[i].title);
	}
	$("#cond_checkTable").html(tables_html);
	setOperator('#condition_item_div', '#condition_middle');
	var check_table= $('#checkTbl_div').dataSelector("getAllNodes");
	if(check_table.length>=2){
	   $('#relation_div').show();
	//   $('#rightTbl_div').find("option:eq(1)")[0].selected = true;
		if($('#leftTbl_div_id').find("option:selected").attr("id")!=$('#rightTbl_div_id').find("option[index=1]").attr("id"))
		   $('#rightTbl_div_id').find("option[index=1]").attr("selected", true);
	   else{
		   $('#rightTbl_div_id').find("option[index=0]").attr("selected", true);
	   }
	   getDataItemByKey( $('#rightTbl_div').find("option:selected").val(),"rightItem_div");
	}else{
	   $('#relation_div').hide();
	}
	
	deleteCheckTableFromTableList();
	
});

//设置选择为空不为空时后面的输入框不可输入
/* $('#condition_middle_id').change(function(){
    var p1=$(this).children('option:selected').val();//这就是selected的值
    if(p1=="is null" || p1=="is not null")
    {
    	$('#paramValue').attr("disabled",true);
    	$('#paramValue').attr("value",'');
    }
    else
    {
    	$('#paramValue').attr("disabled",false);
    }
    }); */
    
limConTdWidth();
}

	_browse.execute( '__userInitPage()' );
</script>
<script language=JavaScript>

//选择数据表的操作
function leftToRight(){
	var checkTab = $('#tbl_div').dataSelector('removeSelectedNodes');
	$('#checkTbl_div').dataSelector('addNodes',checkTab);
	$('#condition_div').dataSelector('addNodes',checkTab);
	$('#leftTbl_div').dataSelector('addNodes',checkTab);
	$('#rightTbl_div').dataSelector('addNodes',checkTab);
	var left_item= $('#leftItem_div').dataSelector("getAllNodes");
	var check_table= $('#checkTbl_div').dataSelector("getAllNodes");
	if(check_table.length>=2){
	   $('#relation_div').show();
	   $('#rightTbl_div').find("option:eq(1)")[0].selected = true;
	   getDataItemByKey( $('#rightTbl_div').find("option:selected").val(),"rightItem_div");
	}else{
	   $('#relation_div').hide();
	   $('#leftItem_div_id').empty();
	   $('#leftItem_div_id').empty();
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

function checkTable(key){
	leftToRight();
}

function doShowRelationDiv(){
	var checkedTab = $('#checkTbl_div').dataSelector('getAllNodes');
	if( $('#relation_div').is(':hidden')){
		$('#relation_div').show();
		$('#leftTbl_div').dataSelector('addNodes',checkedTab);
		$('#rightTbl_div').dataSelector('addNodes',checkedTab);
	} else {
	
	}
}
function rightToLeft(){
	if(being_used == 'true'){
		var nodes = $('#checkTbl_div').dataSelector('getSelectedNodes');
		for(var jj =0;jj<nodes.length;jj++){
			for(var ii=0; ii<ori_checkTblData.length; ii++){
				if( ori_checkTblData[ii].key == nodes[jj].key){
					alert("该表为接口原有配置表，不能删除.");
					return;
				}
			}
		}
		
	}
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

function rightToLeftAll(){
	var checkTab = $('#checkTbl_div').dataSelector('selectAllBlockNodes');
	rightToLeft();
}

//删除已选择表的操作
function backTable(key){
	var selTblData = $("#table_param").tablet("getAllData");
	selTblData = eval("("+selTblData+")") || {"data": []};
	if(being_used == 'true'){
		for(var ii=0; ii<ori_checkTblData.length; ii++){
			if( ori_checkTblData[ii].key == key){
				alert("该表为接口原有配置表，不能删除.");
				return;
			}
		}
	}
	
	for(var ii=0; ii<selTblData.data.length; ii++){
		
		
		if(selTblData.data[ii].leftTable.id == key){
			
			alert("该表已配置查询条件,不能删除.");
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
		setFormFieldValue("record:sql", " ");
		document.getElementById('table_sql').value = "";
		$('#condition_item_div').find("option").remove();
	}else{
		var nowTbl = $('#condition_div_id').find("option:selected");
		var item_data=getDataItemByKey(nowTbl.val(),"condition_item_div");	
	}
	if(check_table.length>=2){
	   $('#relation_div').show();
	}else{
	   $('#relation_div').hide();
	   $('#leftItem_div_id').empty();
	   $('#leftItem_div_id').empty();
	}
}

//最终提交方法
function getSelect(){
    //获取选择的数据表
	var e = $('#checkTbl_div').dataSelector("getAllNodes");
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
	var checkResult=testSql(getFormFieldValue("record:sql"));
	if(checkResult==false){
	  return;
	}
	setFormFieldValue("record:interface_name", getFormFieldValue("record:interface_name"));
	setModifyFlag(true);
	saveAndExit( '', '保存共享服务-基础接口表' );
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn40200001.do
}					
</script>
<freeze:body>
<freeze:title caption="修改基础接口配置信息"/>
<freeze:errors/>

<freeze:form action="/txn401002">
<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="interface_id" caption="接口ID" style="width:95%"/>
      <freeze:hidden property="being_used" caption="是否被使用" style="width:95%"/>
  </freeze:frame>
<freeze:block property="record" caption="修改基础接口" width="95%">
	<freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="getSelect();"/>
	<freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
  <freeze:hidden property="interface_id" caption="接口ID" datatype="string" maxlength="32" style="width:95%"/>
  <freeze:text property="interface_name" caption="接口名称" datatype="string" maxlength="100" notnull="true" style="width:75%" colspan="2"/>
  <freeze:textarea property="interface_description" caption="接口说明"  maxlength="50"  style="width:75%" colspan="2"/>
  <freeze:cell property="crename" caption="创建人：" datatype="string"  style="width:95%"/>
  <freeze:cell property="cretime" caption="创建时间：" datatype="string"  style="width:95%"/>
  <freeze:cell property="modname" caption="最后修改人：" datatype="string"  style="width:95%"/>
  <freeze:cell property="modtime" caption="最后修改时间：" datatype="string"  style="width:95%"/>
 
  <freeze:hidden property="table_id" caption="接口表ID串" datatype="string"/>
  <freeze:hidden property="table_name_cn" caption="接口表名称串" datatype="string"/>
  <freeze:hidden property="table_code" caption="接口表名称串" datatype="string"/>
  <freeze:hidden property="sql" caption="接口表名称串" datatype="string"/>
  <freeze:hidden property="condition" caption="关联条件" datatype="string"/>
  <freeze:hidden property="param" caption="查询条件" datatype="string"/>
  <freeze:hidden property="interface_state" caption="接口状态" datatype="string"/>
  <freeze:hidden property="is_markup" caption="是否有效" datatype="string"/>
  <freeze:hidden property="creator_id" caption="创建人ID" datatype="string"/>
  <freeze:hidden property="created_time" caption="创建时间" datatype="string" visible="false"/>
</freeze:block>
</freeze:form>
<Br/>
<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td><div style="width:100%;">
<dl class="tabs" id="tabs">
     <dt>
	        <a>配置查询范围</a>
	      	<a>配置查询条件</a>
	      	<a>预览</a>
     </dt>
     <dd>
      <div>
         <table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
		<tr><td width="120">选择业务系统：</td>
			<td width="120">选择主题：</td>
			<td width="120">选择数据表：</td>
			<td width="50">&nbsp;</td>
			<td width="120">已选数据表：</td>
			</tr>
			<tr>
				<td width="120">
					<div id="busiSystem_div"></div>
				</td>
				<td width="120">
					<div id="busiTopic_div"></div>
				</td>
				<td width="120">
					<div id="tbl_div"></div>
				</td>
				<td align="center" valign="middle" width="50">
				<!--  
	   				<button onclick="leftToRight()" > >&nbsp;</button><br/>
	   				<button onclick="leftToRightAll()" > >> </button><br/>
	   			-->	
	 	  			<button onclick="rightToLeft()"> <&nbsp; </button><br/>
	   				<button onclick="rightToLeftAll()" > << </button> 
	   			</td>
	   			<td width="120"><div id="checkTbl_div"></div></td>
			</tr>
		</table>
		<br />
		<div id="relation_div">
			<table style="width: 100%;background-color: #dee6e9; border:1px solid #069;border-collapse:collapse;" cellpadding=2 cellspacing=0 border=1 align="center">
			<tr><td width="15%">表选择</td><td width="25%"><div id="leftTbl_div"></div></td><td colspan="2"></td>
				<td width="15%">表选择</td><td width="25%"><div id="rightTbl_div"></div></td>
			</tr>
			<tr><td width="15%">数据项选择</td><td width="25%"><div id="leftItem_div"></div></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;条件</td>
				<td><div id="condition_paren"></div></td>
				<td width="15%">数据项选择</td><td width="25%"><div id="rightItem_div"></div>
			</tr>
			<tr><td colspan="6" align="right"><input type="button" value="添加关联条件" onclick="addRelation();"/>&nbsp;&nbsp;</td></tr>
			<tr><td colspan="6"><div id="selected_columns"></div></td></tr>
			</table>
			<br />
		</div>
  </div>
 </dd>
  <dd>
  
<div>
    <table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
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
		   <td><div id="condition_cond"></div></td>
		   <td><div id="condition_left"></div></td>
           <td><div id="condition_div"></div></td>
           <td><div id="condition_item_div"></div></td>
           <td><div id="condition_middle"></div></td>
	       <td onmouseover="showSelectedValue(this);">
	       <input type="text" id="paramValue" name="paramValue" size="8"/>
	       <select id='pvs' style='display:none;width:100px;'></select> 
	       </td>
	       <td><div id="condition_right"></div></td>
	     </tr>
     <tr><td colspan="7" align='right'><input type="button" value="添加查询条件" onclick="addParam();"/>&nbsp;&nbsp;</td></tr>
     <tr><td colspan="7"><div id="table_param"></div></td></tr>
	</table>
    </div>
  </dd>
  <dd>  
   <div>
    <table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
		<tr><td colspan="6">配置预览：</td></tr>
		<tr><td width="15%">选择数据表为:</td><td align="left"><div id="cond_checkTable">无</div></td></tr>
		<tr><td>数据表关联关系:</td><td><div id="cond_condition">无</div></td></tr>
		<tr><td>数据表查询条件:</td><td><div id="cond_param">无</div></td></tr>
		<tr><td>SQL:</td><td><div id="sql_last"></div></td></tr>
		<tr><td colspan="6"><a href="javascript:preview();">点击查看预览</a></td></tr>
		<tr><td colspan="6"><iframe id="preview_data" name="preview_data" src="" width="100%" style="width: 100%;height:200px;"></iframe></td></tr>
	</table>
  </div></dd>
</dl></div></td></tr></table>

<form id="form1" name="form1" action="/page/share/interfaces/preview_interface.jsp" method="post" target="preview_data">
  <input type="hidden" name="tableIds" id="tableIds"/>
  <input type="hidden" name="table_sql" id="table_sql"/>
</form>
</freeze:body>
</freeze:html>
