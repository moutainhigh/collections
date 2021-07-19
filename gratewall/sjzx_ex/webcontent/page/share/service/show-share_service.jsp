<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>查看服务信息</title>
	<script type="text/javascript" src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-tab/jquery.tabs.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/service/share_service.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.js"></script>
	<link href="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.cssSelect1{
			width:120px !important;
			height: auto;
		}
		.cssSelect{
			width:120px !important;
			height: auto;
		}
	</style>
</head>

<script type="text/javascript">
var svrId = "";

// 保 存
function func_record_saveAndExit()
{
	var sava_data = getPostData();
	if(sava_data){
    	setFormFieldValue( "record:jsoncolumns" ,sava_data[0]);
    	setFormFieldValue( "record:sql" ,sava_data[1]);
		setFormFieldValue( "record:limit_data" ,sava_data[2]);
	saveAndExit( '', '修改共享服务的服务表' );	// /txn40200001.do
	}
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn40200001.do
}

function func_record_export()
{

	var svrId = getFormFieldValue("record:service_id");
	var page = new pageDefine( "/txn40200016.do", "导出服务信息");
	page.addValue( svrId, "primary-key:service_id" );
	
	//page.addParameter( "record:service_name", "record:service_name" );
	page.addParameter( "record:interface_id", "record:interface_id" );
	page.addParameter( "record:service_targets_id", "record:service_targets_id" );
	page.addParameter( "record:service_state", "record:service_state" );
	page.addParameter( "record:service_type", "record:service_type" );
	page.goPage();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	_browse.enableCopy = true;
	var jsoncolumn = getFormFieldValue( "record:jsoncolumns" );
	svrId = getFormFieldValue("record:service_id");
    if(jsoncolumn==''){
    	alert("数据获取失败，请检查该服务相关文件是否存在！");
    }else{
   //初始化服务限定条件表格
    var jsondata = eval('(' + jsoncolumn + ')'); 
   
   //所选字段
   var column_data=jsondata.columns;
   var column_html="";
   var tbTitleTmp = new Array;
   for(var i=0;i<column_data.length;i++){
     var column=column_data[i];
     if(tbTitleTmp.join(',').indexOf(column.table.name_en)==-1){
      	tbTitleTmp.push(column.table.name_en);
      	tbTitleTmp.push(column.table.name_cn);
     }
     //column_html+="["+column.table.name_cn+"("+column.table.name_en+")] 的 "+column.column.name_cn+"("+column.column.name_en+")<br/>";
   }
   for(var ii=0; ii<tbTitleTmp.length; ii=ii+2){
	   column_html += "<span style='color:red;'>[ "+tbTitleTmp[ii+1]+" ( "+tbTitleTmp[ii]+" ) ]</span><br />&nbsp;&nbsp;";
	   for(var i=0;i<column_data.length;i++){
		     var column=column_data[i];
		     if(tbTitleTmp[ii] == column.table.name_en){
				column_html+= column.column.name_cn+"("+column.column.name_en+"), &nbsp;";
		     }
		}
	   column_html = column_html.replace(/,\s*&nbsp;$/ig, " ");
	   column_html += "<br />";
   }
   document.getElementById('span_record:column_name_cn').innerHTML = column_html;
    
   //服务查询条件
   
   var condition=jsondata.conditions;
   var cond_html="";
   for(var i=0;i<condition.length;i++){
     var cond=condition[i];
     cond_html+=cond.logic.name_cn+cond.leftParen+"["+cond.table.name_cn+"] 的 "+cond.column.name_cn+" <span style='color:red'> "+cond.paren.name_cn+" </span>";
     cond_html+= cond.param_value.name_cn+cond.rightParen+"<br/>";
   }
   document.getElementById('span_record:service_condition').innerHTML=cond_html;
    
   //用户输入参数
   
   var param=jsondata.params;
   var param_html="";
   for(var i=0;i<param.length;i++){
     var parm=param[i];
     param_html+=parm.logic.name_cn+parm.leftParen+" ["+parm.table.name_cn+"] 的 "+parm.column.name_cn+" <span style='color:red'> "+parm.paren.name_cn+" </span>";
     param_html+= parm.param_value.name_cn+parm.rightParen+"<br/>";
   }
   document.getElementById('span_record:service_param').innerHTML=param_html; 
    
    initWeek();
	initTime();
	initNumber();
	initNumPer();
	initTotal();
    
    var limit_data = getFormFieldValue("record:limit_data");
	limit_data = eval("(" + limit_data + ")");
	var data_array = new Array;
	data_array.push({
						"week": "星期",
						"datesStr": "开始时间-结束时间",
				//		"end_time": "结束时间",
						"times_day": "访问次数",
						"count_dat": "每次访问总数",
						"total_count_day": "每天访问总数"
					});
	limit_data = limit_data.data;
	for(var ii=0; ii< limit_data.length; ii++){
		data_array.push(limit_data[ii]);
	}
	
    $(function() {
	//准备tab页
	    		var opts = {
	    		    addDelete : false,
					data: data_array,
					shownum : 8
				}
				$(tab4_limit_div).tablet(opts);
});

setTimeout(function(){
	getColumnsByTable($(tab1_table_all_div).find("option:first").val());
	$(tab1_table_all_div).find("option:first").attr("selected", true);
},1000);
document.getElementById("span_record:sql").title = "点击复制SQL语句";
document.getElementById("span_record:sql").onclick = function(){
	var dcontent = document.getElementById("span_record:sql").innerHTML;
	dcontent = dcontent.replace("&lt;", "<").replace("&gt;", ">");
	window.clipboardData.setData("Text", dcontent);
	alert("SQL语句已复制到剪贴板");
}
    }
    var is_month_data = getFormFieldValue("record:is_month_data");
	var visit_period = getFormFieldValue("record:visit_period");
	if(is_month_data=='N'){
		document.getElementById("span_record:is_month_data").innerHTML="不限制";
	}else{
		document.getElementById("span_record:is_month_data").innerHTML="限制";
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看服务表信息"/>
<freeze:errors/>
<freeze:form action="/txn40200010" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="service_id" caption="服务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="查看服务表信息" width="95%">
      
      <freeze:hidden property="service_id" caption="服务ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%" />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="column_no" caption="数据项ID"  style="width:98%"/>
      <freeze:hidden property="column_name_cn" caption="数据项中文名称"  style="width:98%"/>
      <freeze:hidden property="column_alias" caption="数据项别名"  style="width:98%"/>
      <freeze:hidden property="sort_column" caption="排序字段"  style="width:95%"/>
      <freeze:hidden property="sql_one" caption="SQL1" style="width:98%"/>
      <freeze:hidden property="sql_two" caption="SQL2"  style="width:98%"/>
      <freeze:hidden property="is_markup" caption="有效标记" value="Y" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID"  style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID"  style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间"  style="width:95%"/>
      <freeze:hidden property="jsoncolumns"  datatype="string"  style="width:95%"/>
      <freeze:hidden property="limit_data" datatype="string" style="width:95%" />
      <freeze:hidden property="is_month_data" datatype="string" />
      <freeze:hidden property="visit_period" datatype="string" />
      
      <freeze:cell property="service_name" caption="服务名称" datatype="string"  style="width:95%"/>
      <freeze:cell property="interface_id" caption="基础接口" valueset="共享服务_接口名称"  show="name" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="服务对象" valueset="资源管理_共享服务对象名称"  show="name" style="width:95%"/>
      <freeze:cell property="service_state" caption="服务状态" valueset="资源管理_归档服务状态" style="width:95%"/>
      <freeze:cell property="service_type" caption="服务类型" valueset="资源管理_数据源类型" style="width:95%"/>
      <freeze:hidden property="service_no" caption="服务编号" datatype="string" style="width:95%"/>
      <freeze:cell property="is_month_data" caption="限制访问当月数据" datatype="string" style="width:95%" />
      <freeze:cell property="visit_period" caption="访问时间间隔(天)" datatype="string" style="width:95%" />
      <freeze:cell property="old_service_no" caption="服务编号" align="left" style="width:95%" />
      <freeze:cell hint="点击复制SQL语句" property="sql" caption="SQL" colspan="2" style="width:98%"/>
      <freeze:cell disabled="true" property="service_description" caption="服务说明"  style="width:98%"/>
      <freeze:cell disabled="true" property="regist_description" caption="备案说明"  style="width:98%"/>
 <%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
 	Recordset fileList=null;
 	DataBus record = null;
    try{
    record	 = context.getRecord("record");
    String interfaceid= record.getValue("interface_id"); 
    	%>
    <input value="<%=interfaceid%>" type="hidden" name="record:interface_id2"/>

		<% 
    fileList = context.getRecordset("fjdb");
    if(fileList!=null && fileList.size()>0){
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
%>
<tr>
<td height="32" align="right">备案附件：&nbsp;</td>
<td colspan="3">
	
<a href="#" onclick="downFile('<%=file_id%>')" title="附件" ><%=file_name %></a>

</td>
</tr>

<% }
     }
   }catch(Exception e){
	   System.out.println(e);
   }
%>   

  </freeze:block>
   <br/>
	<freeze:block property="record" caption="查看服务配置信息" width="95%">
	  <freeze:cell property="column_name_cn" caption="服务所选字段" datatype="string"  style="width:95%" colspan="2"/>
	  <freeze:cell property="service_condition" caption="服务查询条件" datatype="string"  style="width:95%" colspan="2"/>
	  <freeze:cell property="service_param" caption="用户输入参数" datatype="string"  style="width:95%" colspan="2"/>
	  
	   <freeze:cell property="crename" caption="创建人" datatype="string"  style="width:95%"/>
  <freeze:cell property="cretime" caption="创建时间" datatype="string"  style="width:95%"/>
  <freeze:cell property="modname" caption="最后修改人" datatype="string"  style="width:95%"/>
  <freeze:cell property="modtime" caption="最后修改时间" datatype="string"  style="width:95%"/>
	</freeze:block>

<br />
<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
				<dl class="tabs" id="tabs">
      	<dt>
      		<span>服务限制条件</span>
      	</dt>
      	
      	<!-- 第四个标签页开始 -->
      	<dd><div>
      		 <table class="dd_table" border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
			<tr>
				<td><div id="tab4_limit_div"></div></td>
			</tr>
			</table>
			</div>
      	</dd>
      	<!-- 第四个标签页结束 -->
    </dl>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center" height="50" valign="bottom"><div class="btn_cancel"  onclick="func_record_goBack()"></div><input type="button" name="record_export_doc" value="     " class="btn_import" onclick="func_record_export();" /></td>
	
	</tr>
</table>
<!-- <p align="center" class="print-menu">
    	<table cellspacing='0' cellpadding='0' class='button_table'>
    	<tr><td class='btn_left'></td>
    	<td><input type="button" name="record_goBackNoUpdate" value="关 闭" class="menu" onclick="func_record_goBack()" />
    	</td><td class='btn_right'></td></tr>
    	</table>
    </p> -->
</freeze:form>
</freeze:body>
</freeze:html>
