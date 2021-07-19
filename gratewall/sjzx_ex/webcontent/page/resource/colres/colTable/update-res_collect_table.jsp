<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %> /script/common/js/validator4m.js"></script>
<freeze:html width="1000" height="700">
<head>
<title>修改采集数据表信息</title>
<style type="text/css">
#dataitem_tb table{ 
	width: 98%;
}
</style>
</head>
<script type="text/javascript">
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "资源管理_数据项类型");
out.print("var dataitem_types = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("dataitem_types.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "资源管理_对应代码表");
out.print("var code_table_list = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("code_table_list.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
if(StringUtils.isNotBlank(context.getValue("jsondata"))){
    out.println("var jsondata="+context.getValue("jsondata")+";");
}else if(StringUtils.isNotBlank(context.getRecord("record").getValue("jsondata"))){
	out.println("var jsondata="+context.getRecord("record").getValue("jsondata")+";");
	out.println("var jsondata=jsondata.data ;");
}
%>
var now_adding = -1;
var is_name_used = 1;
var row_index = -1;
var now_line_no = -1;
var name_ens = new Array;
var all_save = false;
//全页面保存
 function func_record_saveRecord_all(){
	name_ens.length = 0; //清空数据项名称数组
	//重新赋值数据项名称数组，主要用于重名验证
	
	 if($("#dataitem_tb").find("tr:eq(1)").find(
		"td:eq(1)").text()==""){
		 alert("请先增加数据项，然后再保存!");
		 return false;
	 }
	
	
	
	 $('#dataitem_tb tr:gt(0)').each(function(index){
		 name_ens.push($(this).find("td:eq(1)").text());
	 });
	var flag = false;
	$('#dataitem_tb tr:gt(0)').each(function(index){
		//规则校验
		flag = checkItem(++index);
		if(flag == false){
			return false;
		}
	});
	
	
	//通过校验
	if(flag){
		//var dataStr = $('#dataitem_tb').tablet('getAllData');
		//dataStr = eval('('+dataStr+')');
		//dataStr = dataStr.data;
		var dataStr = "{'data': [";
		$('#dataitem_tb tr:gt(0)').each(function(index){
			var dataitem_name_en = $(this).find("td:eq(1)").text();
			var dataitem_name_cn = $(this).find("td:eq(2)").text();
			var dataitem_type = $(this).find("td:eq(3)").find(
					'select option:selected').val();
			var dataitem_long = $(this).find("td:eq(4)").text();
			var is_key = $(this).find("td:eq(5)").find('select option:selected').val();
			var code_table = $(this).find("td:eq(6)").find(
					'select option:selected').val();
			dataStr += "{'dataitem_name_en': '"+dataitem_name_en+"', 'dataitem_name_cn': '"+dataitem_name_cn+
				"', 'dataitem_type': '"+dataitem_type+"', 'dataitem_long': '"+dataitem_long+
				"', 'is_key': '"+is_key+"', 'code_table': '"+code_table+"' },";
		});
		dataStr = dataStr.replace(/,$/, "");
		dataStr += "]}";
		//alert(dataStr);
		var page = new pageDefine("/txn20201014.do", "保存采集资源表新字段");	
		//page.addValue(dataStr, "jsondata");
		//page.addParameter("record:collect_table_id","primary-key:collect_table_id");
		//page.updateRecord();
		setFormFieldValue('record:jsondata', dataStr);
	    saveRecord( '', '保存采集数据表数据项' );
	}
 }
 
 // 生成表
function func_record_createTable()
{
    var collect_table_id=getFormFieldValue('record:collect_table_id');
    //var ids = getFormAllFieldValues("dataItem:dataitem_name_en");
    var ids = new Array;
    $('#dataitem_tb').find("tr:gt(0)").each(function(){
    	ids.push($(this).find("td:eq(1)").text());
    })
    
    if(collect_table_id==null||collect_table_id==""){
	    alert("请先填写采集表信息!");
	    clickFlag=0;
    }else if(now_adding==1){
        alert("请先保存数据项!");
    }else if(ids==null||ids.length==0){
        alert("请先填写数据项信息!");
	    clickFlag=0;
    }
    else{
        //var key=getFormAllFieldValues("dataItem:is_key");
        var key = new Array;
        $('#dataitem_tb').find("tr:gt(0)").each(function(){
        	key.push($(this).find("td:eq(5) select option:selected").val());
        })
        var num=0;
	    if(ids!=null){
	    for(i=0;i<ids.length;i++){
	    if(key[i]=='1'){
	       num=num+1;
	      }
	     }
	    }
        if(num>1){
	        alert("只能有一个数据项是主键!");
		    clickFlag=0;
		    return false;
        }
    
        var page = new pageDefine( "/txn20201009.ajax", "生成采集数据表!");
        page.addParameter("record:table_name_en","primary-key:table_name_en");
	    page.callAjaxService('creatTableCheck');
	}
}
function creatTableCheck(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
			
		if(confirm("采集库已存在此数据表名称且数据表里已有数据，是否继续生成该数据表?")){
		   var page = new pageDefine( "/txn20201016.ajax", "生成采集数据表!");
  		   page.addParameter("record:collect_table_id","record:collect_table_id");
  		   page.addValue(is_name_used,"record:name_nums");
  		   page.callAjaxService('creatTable');
		}
		
	  		//alert("采集库已存在此数据表名称且数据表里已有数据,不能再生成该表!");
	  		//return false;
  			
  		}else if(is_name_used==-1){
	  		
	  		if(confirm("采集库已存在此数据表名称,但数据表里没有数据，是否继续生成该数据表?")){
			   var page = new pageDefine( "/txn20201016.ajax", "生成采集数据表!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
			}
			
			//alert("采集库已存在此数据表,不能再生成该表!");
  			//return false;
  		}else{
  		  if(confirm("是否生成数据表?")){
	  		   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
  		   }
  		}
}
 function creatTable(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		else{
		   alert("生成表成功!");
		   goBackWithUpdate();
		}
}
 
 
 function func_record_goBack()
{

	//var page = new pageDefine( "/txn20201001.do", "查询采集数据表");
	//page.updateRecord();
	
	
	//goBackWithUpdate();
	window.close();
}
// 增加采集数据项表信息
function func_record_addRecord()
{
	clickFlag=0; //忽略烽火台的按钮限制
	//自定义按钮禁用状态
	now_adding = 1;
	/* $('#btn_add').removeClass('btn_add').addClass('btn_add_disabled');
	$('#btn_add')[0].onclick = function(){
		alert("请完成本次添加后再次操作.");
	}; */
	var data = getFormFieldValue('record:collect_table_id');
	$("#dataitem_tb").tablet("insertRow", data);
}

	//保存数据项信息
	function checkItem(rowIndex) {
		//var e = event.srcElement;
		//rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
		var en = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").text();
		if (en == "UPDATE_DATE") {
			alert("第"+rowIndex+"行中, UPDATE_DATE为系统保留字段禁止使用！");
			return false;
		}
		var en = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").text();
		if (!checkEnName(en, '数据项名称', rowIndex)) {
			return false;
		}
		var tmpAry = new Array;
		tmpAry = tmpAry.concat(name_ens);
		tmpAry[rowIndex-1]="@";
		/* if(tmpAry.join(',').indexOf(en) > -1){
			alert("第"+rowIndex+"行数据项名称'"+en+"'重复!");
			return false;
		} */
		for(var ii=0; ii<tmpAry.length; ii++){
			if(en == tmpAry[ii]){
				alert("第"+rowIndex+"行和第"+(ii+1)+"行数据项名称'"+en+"'重复!");
				return false;
			}
		}
		var dataitem_type = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:eq(3)").find('select option:selected').val();
		var dataitem_long = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:eq(4)").text();
		if (!checkItemTypeLength(dataitem_type, dataitem_long, rowIndex)) {//校验数据项长度
			return false;
		}
		if (!check_int(dataitem_long, rowIndex)) {//校验数据项长度
			return false;
		}
		return true;
	}

	function saveAndCon(errCode, errDesc, xmlResults) {
		is_name_used[0] = 1;
		if (errCode != '000000') {
			alert('处理错误[' + errCode + ']==>' + errDesc);
			return;
		} else {
			if (!all_save) {
				alert("操作成功!");
			}
			var dataItem_id = _getXmlNodeValues(xmlResults,
					'record:collect_dataitem_id');
			dataItem_id = dataItem_id[0];
			$("#dataitem_tb")
					.find("tr:eq(" + now_line_no + ")")
					.find("td:last")
					.append(
							"<input name='collect_dataitem_id' type='hidden' value='"+dataItem_id+"' />");
			row_index = -1;
			now_adding = -1;
			$('#btn_add').removeClass('btn_add_disabled').addClass('btn_add');
			$('#btn_add')[0].onclick = function() {
				func_record_addRecord();
			}
			now_line_no = -1;
			//window.location.reload();
		}
		namechecked = true;
	}
	function deleteDataItem() {
		var e = event.srcElement;
		rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
		$("#dataitem_tb").find("tr:eq(" + rowIndex + ")").remove();
	}

	// 请在这里添加，页面加载完成后的用户初始化操作
	function __userInitPage() {
		var data = new Array;

		data.push({
			"rowIndex" : "序号",
			"dataitem_name_en" : "数据项名称",
			"dataitem_name_cn" : "中文名称",
			"dataitem_type" : "数据项类型",
			"dataitem_long" : "数据项长度",
			"is_key" : "是否主键",
			"code_table" : "对应代码表",
			"oper" : "操作"
		});
		var options = {
			data : data,
			editable : true,
			shownum : 10,
			lineNum : false,
			isAlias : false,
			shownum : 10,
			addDelete : false,
			dataitem_type : dataitem_types,
			code_table : code_table_list
		}
		$("#dataitem_tb").tablet(options);
		if(typeof jsondata != 'undefined'){
			$("#dataitem_tb").tablet("initRow", jsondata);
		}
		
		var ids =document.getElementsByName("span_dataItem:code_table");
		var value=getFormAllFieldValues("dataItem:code_table");
		
		for(var i=0; i<ids.length; i++){
			var val=value[i];
			if(val==null||val==""||val=="无"){
			}else{
			  ids[i].innerHTML='<a href="javascript:func_record_querycode(\''+value[i]+'\');" title="" >'+val+'</a>';
			}
	    }
		
		
	}
	function func_record_querycode(val){
	    var url="txn20301026.do?select-key:code_table="+val;
	    var page = new pageDefine( url, "查看代码集信息", "代码集查询","modal","600","400" );
		page.addRecord();
	}

	_browse.execute('__userInitPage()');
</script>
<freeze:body>
<freeze:title caption="修改采集数据表信息"/>
<freeze:errors/>

<freeze:form action="/txn20201002.do">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" style="width:95%"/>
  </freeze:frame>

<freeze:block property="record" caption="采集数据表信息" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称"   style="width:95%"/>
      <freeze:text property="table_name_en" caption="表名称" datatype="string" notnull="true" readonly="true" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="表中文名称" datatype="string" notnull="true"  style="width:95%"/>
      <freeze:select property="table_type" caption="表类型" show="name" valueset="资源管理_表类型" notnull="true" style="width:95%"/>
      <freeze:textarea property="table_desc" caption="表描述" colspan="2" rows="2"  style="width:98%"/>
      <freeze:cell property="crename" caption="创建人：" datatype="string"  style="width:95%"/>
      <freeze:cell property="cretime" caption="创建时间：" datatype="string"  style="width:95%"/>
      <freeze:cell property="modname" caption="最后修改人：" datatype="string"  style="width:95%"/>
      <freeze:cell property="modtime" caption="最后修改时间：" datatype="string"  style="width:95%"/>
      
      
      <freeze:hidden property="table_status" caption="表状态" datatype="string"  style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string"  style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="cj_ly" caption="采集来源" datatype="string"  style="width:95%"/>
      <freeze:hidden property="if_creat" caption="采集库是否生成采集表" datatype="string"  style="width:95%"/>
      <freeze:hidden property="jsondata" caption="字段json" style="width:95%"/>
  </freeze:block>
   <br/>
 
  <freeze:grid property="dataItem" caption="采集数据项列表" keylist="collect_dataitem_id" width="95%"  multiselect="false" checkbox="false" fixrow="false">
      
      <freeze:hidden property="collect_dataitem_id" caption="采集数据项ID" style="width:12%" />
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" style="width:12%" />
       <freeze:cell property="index" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="dataitem_name_en" caption="数据项名称" style="width:15%" />
      <freeze:cell property="dataitem_name_cn" caption="数据项中文名称" style="width:15%" />
      <freeze:cell property="dataitem_type" caption="数据项类型" show="name" valueset="资源管理_数据项类型" style="width:10%" />
      <freeze:cell property="dataitem_long" caption="数据项长度" style="width:10%" />
      <freeze:cell property="is_key" caption="是否主键"  valueset="是否[1是0否]" style="width:10%" />
      <freeze:cell property="is_code_table" caption="是否代码表" valueset="是否[1是0否]" style="width:10%" />
      <freeze:cell property="code_table" caption="对应代码表" valueset="资源管理_对应代码表" style="width:10%" />
      <freeze:cell property="dataitem_long_desc" caption="数据项描述" style="width:15%"  />
      <freeze:hidden property="is_markup" caption="有效标记" style="width:10%" />
      <freeze:hidden property="creator_id" caption="创建人ID" style="width:12%" />
      <freeze:hidden property="created_time" caption="创建时间" style="width:12%" />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="最后修改时间" style="width:12%" />
  </freeze:grid>
  
  
  
  <br>
  <table align='center' cellpadding=0 cellspacing=0 width="95%" >
  <tr><td><div title="增加数据项" onclick="func_record_addRecord();" id="btn_add" class="btn_add"></div></td></tr>
  </table>
  <div id="dataitem_tb"></div>
    <table align='center' cellpadding=0 cellspacing=0 width="95%" >

  <tr><td>
  
  
  
  <td align="center" height="50" valign="bottom">
    <div class="btn_save"  onclick="func_record_saveRecord_all();"></div>
&nbsp;&nbsp;&nbsp;&nbsp;
  <div class="btn_gen"  onclick="func_record_createTable();"></div>
&nbsp;&nbsp;&nbsp;&nbsp;
<div class="btn_cancel"  onclick="func_record_goBack();"></div>
  </td></tr>	
	
  </table>
</freeze:form>
</freeze:body>
</freeze:html>
