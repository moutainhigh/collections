<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery162.js"></script>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="1024" height="700">
<head>
<title>查询表关系列表</title>
</head>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
DataBus db2 = context.getRecord("record2");
String sb1 = db2.getValue("table_fk_str");
String sb2 = db2.getValue("table_fk_name_str");
String sb3 = db2.getValue("ds_id_str");
String sb4 = db2.getValue("ds_name_str");
%>
<script language="javascript"> 
var sb1 = "";
var sb2 = "";
var sb3 = "";
var sb4 = "";
var asb1 = new Array;
var asb2 = new Array;
var asb3 = new Array;
var asb4 = new Array;

function getTableFkName() {
	var ss = ""
	var s2 = getFormFieldValue( "record2:columns" );
	if(asb1.length>0){
		for(var j=0; j<asb1.length; j++){
			if(asb1[j]==s2){
				ss = asb2[j]
				break;
			}
		}	
	}
	return ss;
}

function func_record_saveAndExit(){
   if( validateForm()){
   	var page = new pageDefine("/txn80002603.ajax", "新增表关系信息");
	page.addParameter( "select-key:sys_rd_table_id", "record:sys_rd_table_id" );
	page.addParameter( "select-key:sys_rd_system_id", "record:sys_rd_system_id" );
	page.addParameter( "select-key:sys_rd_data_source_id", "record:sys_rd_data_source_id" );
	page.addParameter( "select-key:table_code", "record:table_code" );
	page.addParameter( "select-key:table_name", "record:table_name" );
	page.addParameter( "record2:remarks", "record:remarks" );
	//主表关联字段
	page.addParameter( "record2:columns", "record:table_fk" );
	//主表关联字段中文
    page.addValue(getTableFkName(),"record:table_fk_name" );
    //关联表所属数据源
    page.addParameter( "record2:ref_sys_rd_data_source_id", "record:ref_sys_rd_data_source_id" );
    //关联表ID
    page.addValue( getRefTableId(), "record:ref_sys_rd_table_id" );
    //关联表
    page.addParameter( "record2:relation_table_code_str", "record:relation_table_code");
    //关联表中文名称
    page.addValue( getRefTableName(), "record:relation_table_name");
   //关联表字段
    page.addParameter( "record2:ref_columns", "record:relation_table_fk");
	//关联表字段中文
	 
	page.addValue( getRefColumnName(), "record:relation_table_fk_name");
	
    page.callAjaxService("callBack3()");
   }
}
function validateForm(){
	
	var columns =  getFormFieldValue( "record2:columns" );
	var ref_columns = getFormFieldValue( "record2:ref_columns" );
	var relation_table_code_str = getFormFieldValue( "record2:relation_table_code_str");
	
	if(columns==""||columns==null){
		alert("请选择主表关联字段")
		return false;
	}
	if(relation_table_code_str==""||relation_table_code_str==null){
		alert("请选择关联表")
		return false;
	}
	if(ref_columns==""||ref_columns==null){
		alert("请选择关联表关联字段")
		return false;
	}
	return true;
	
}
function callBack3(errCode, errDesc,xmlResults ){
	if(errCode!='000000'){
	    if(errCode=='999999'){
		    alert("关系已经存在！")
			
	    }else{
	    	alert("新增表关系时发生错误！")
			 
	    }
		return 
	}
		//刷新列表
    var page = new pageDefine("/txn80002606.do", "");
	page.addParameter( "record2:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record2:sys_rd_system_id", "select-key:sys_rd_system_id" );
	page.addParameter( "record2:sys_rd_data_source_id", "select-key:sys_rd_data_source_id" );
	page.addParameter( "record2:table_code", "select-key:table_code" );
	page.addParameter( "record2:table_name", "select-key:table_name" );
	
    page.goPage();
    
}
// 增加表关系
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_table_relation.jsp", "增加表关系", "modal" );
	page.addRecord();
}

// 修改表关系
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002604.do", "修改表关系", "modal" );
	page.addParameter( "record:sys_rd_table_relation_id", "primary-key:sys_rd_table_relation_id" );
	page.updateRecord();
}



function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:sys_rd_table_id=' + getFormFieldValue('select-key:sys_rd_table_id')
	return parameter;
}
function getParameter2(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:sys_rd_data_source_id=' + getFormFieldValue('record2:ref_sys_rd_data_source_id')
	return parameter;
}

function getParameter3(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:sys_rd_data_source_id=' + getFormFieldValue('record2:ref_sys_rd_data_source_id')
                    +'&input-data:sys_rd_table_id=' + getFormFieldValue('select-key:sys_rd_table_id')
	return parameter;
}

// 返 回
function func_record_goBack()
{
	//goBack("/txn80002201.do");	//
	goBack("/txn80002208.do");
}

function reset1(){
    var s =  getFormFieldValue("record2:ref_sys_rd_data_source_id") ;
	if(s==""||s==null){
		$("select[name='record2:relation_table_code_str']").html("<option name='record2:relation_table_code_str'>请选择</option>");
		$("select[name='record2:ref_columns']").html("<option name='record2:ref_columns'>请选择</option>");
	}else{
		AjaxGetTablesByDS();
	}
}

function AjaxGetTablesByDS(){
	var page = new pageDefine("/txn80002201.ajax", "根据数据源取表信息");
	page.addParameter( "record2:ref_sys_rd_data_source_id", "select-key:sys_rd_data_source_id" );
	page.addParameter( "record2:ref_sys_rd_data_source_id", "select-key:show_all" );
	page.callAjaxService("callBack()");

}

function reset3(){
	var s =  getFormFieldValue("record2:sys_rd_system_id_1") ; 
	if(s){
		var page = new pageDefine("/txn60210008.ajax?record:sys_id="+s, "根据主题取表信息");
		page.callAjaxService("callBack13()");
	}
}

var a= new Array;
var b = new Array;
var c = new Array;
var d = new Array;
var e = new Array;

function callBack13(errCode, errDesc,xmlResults ){
	if(errCode!='000000'){
		alert("根据主题取关联表信息时发生错误！")
		return
	}
	  a = _getXmlNodeValues(xmlResults,'context/record/table_name_cn');
	  b = _getXmlNodeValues(xmlResults,'context/record/table_name');
	  c = _getXmlNodeValues(xmlResults,'context/record/sys_id');
	  var f = new Array;
	  f = _getXmlNodeValues(xmlResults,'context/record/table_no');
	var s1 =  getFormFieldValue("record2:table_code") ;
	var str1="<option name='record2:relation_table_code_str'>请选择</option>" ;
	if(a.length>0){
		for(var j=0; j<a.length ; j++){
		   if(s1!=""&&s1!=a[j]){
		  	 str1 = str1 + "<option name='record2:relation_table_code_str' value='"+ f[j]+"'>" + a[j]+" ("+b[j] +")"+  "</option>"
		   }		
		}
	
	}
	$("select[name='record2:relation_table_code_str']").html(str1);

}

function callBack(errCode, errDesc,xmlResults ){
	if(errCode!='000000'){
		alert("取关联表信息时发生错误！")
		return
	}
	  a = _getXmlNodeValues(xmlResults,'context/record/table_name')
	  b = _getXmlNodeValues(xmlResults,'context/record/table_code')
	  c = _getXmlNodeValues(xmlResults,'context/record/sys_rd_table_id')
	var s1 =  getFormFieldValue("record2:table_code") ;
	var str1="<option name='record2:relation_table_code_str'>请选择</option>" ;
	if(a.length>0){
		for(var j=0; j<a.length ; j++){
		   if(s1!=""&&s1!=a[j]){
		  	 str1 = str1 + "<option name='record2:relation_table_code_str' value='"+ a[j]+"'>" + a[j]+" ("+b[j] +")"+  "</option>"
		   }		
		}
	
	}
	$("select[name='record2:relation_table_code_str']").html(str1);

}

function reset2(){
 	var s =  getFormFieldValue("record2:relation_table_code_str") ;
	if(s==""||s==null){
		$("select[name='record2:ref_columns']").html("<option name='record2:ref_columns'>请选择</option>");
	}else{
		AjaxGetColumnsByTable(s);
	}
}

function AjaxGetColumnsByTable(ss){
//	var ss =  getRefTableId();
 	if(ss!=null&&ss!=""){
 		var s = getFormFieldValue("record2:relation_table_code_str") ;
 		var page = new pageDefine("/txn60210009.ajax?record:table_no="+ss, "根据表信息取字段信息");
		page.callAjaxService("callBack2()");
 	}
	
}

function callBack2(errCode, errDesc,xmlResults ){
	if(errCode!='000000'){
		alert("取字段信息时发生错误！")
		return
	}
	  d = _getXmlNodeValues(xmlResults,'context/record/column_name_cn')
	  e = _getXmlNodeValues(xmlResults,'context/record/column_name')
	  //f = _getXmlNodeValues(xmlResults,'context/record/sys_rd_table_id')
	
	var str1="<option name='record2:ref_columns'>请选择</option>" ;
	if(d.length>0){
		for(var j=0; j<d.length ; j++){
			str1 = str1 + "<option name='record2:ref_columns' value='"+ d[j]+"'>" + d[j]+" ("+e[j] +")"+  "</option>"
		}
	
	}
	$("select[name='record2:ref_columns']").html(str1);
}


function getRefTableId(){
	var ss = ""
	if(a.length>0){
		var str = $("select[name='record2:relation_table_code_str']").val();
		if(str!=null&&str!=""){
			for(var j=0; j<a.length; j++){
				if(a[j]==str){
					ss = c[j]
					break;
				}
			}
		}
	}
	return ss;
}

function getRefTableName(){
	var ss = ""
	if(a.length>0){
		var str = $("select[name='record2:relation_table_code_str']").val();
		if(str!=null&&str!=""){
			for(var j=0; j<a.length; j++){
				if(a[j]==str){
					ss = b[j]
					break;
				}
			}
		}
	}
	return ss;
}


function getRefColumnName(){
	var ss = ""
	if(d.length>0){
		var str = $("select[name='record2:ref_columns']").val();
		if(str!=null&&str!=""){
			for(var j=0; j<d.length; j++){
				if(d[j]==str){
					ss = e[j]
					break;
				}
			}
		}
	}
	return ss;
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	sb1 = '<%=sb1%>';
	sb2 = '<%=sb2%>';
	sb3 = '<%=sb3%>';
	sb4 = '<%=sb4%>';
	
	asb1 =  sb1.split(",")
	asb2 =  sb2.split(",")
	asb3 =  sb3.split(",")
	asb4 =  sb4.split(",")
 
	var str1="<option name='record2:columns'>请选择</option>" ;
	if(asb1.length>0){
		for(var j=0; j<asb1.length ; j++){
			str1 = str1 + "<option name='record2:columns' value='"+ asb1[j]+"'>" + asb1[j]+" ("+asb2[j] +")"+  "</option>"
		}
	}
	
	$("select[name='record2:columns']").html(str1);

	var str2="<option name='record2:ref_sys_rd_data_source_id'>请选择</option>" ;
	if(asb3.length>0){
		for(var j=0; j<asb3.length ; j++){
		   
			str2 = str2 + "<option name='record2:ref_sys_rd_data_source_id' value='"+asb3[j]+"'>" +asb4[j] +  "</option>"
			
			
		}
	}
	
	$("select[name='record2:ref_sys_rd_data_source_id']").html(str2);
	
	$("select[name='record2:ref_columns']").html("<option name='record2:ref_columns'>请选择</option>" );
	
	
	//初始化删除按钮
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\"); func_record_deleteRecord();' href='#'>删除</a>";
	}		
	
	//修改展示方式：：表中文名（表英文名）
	var table_name = getFormFieldValue("record2:table_name");
	var table_code = getFormFieldValue("record2:table_code");
	setFormFieldValue("record2:table",table_name+"("+table_code+")");
	document.getElementById('record2:ref_sys_rd_data_source_id').value='d0b41f492e824e08ba060b0b6abe2733';
	
}

// 删除表关系
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002605.do", "删除表关系" );
	page.addParameter( "record:sys_rd_table_relation_id", "primary-key:sys_rd_table_relation_id" );
	page.deleteRecord( "是否删除选中的记录" ); 
		
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body onload="reset1()">
<freeze:title caption="维护表间关系"/>
<freeze:errors/>
<freeze:form action="/txn80002606">
 	<freeze:block property="select-key" width="95%">
      <freeze:hidden  property="sys_rd_table_id" caption="关联ID" style="width:95%"/>
      <freeze:hidden  property="sys_rd_system_id" caption="关联ID" style="width:95%"/>
      <freeze:hidden  property="sys_rd_data_source_id" caption="关联ID" style="width:95%"/>
      <freeze:hidden  property="table_code" caption="关联ID" style="width:95%"/>
      <freeze:hidden  property="table_name" caption="关联ID" style="width:95%"/>
   </freeze:block>
  
  <freeze:block property="record2" caption="维护表间关系" width="95%" columns="2">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_table_relation_id" caption="关联ID" datatype="string" maxlength="32" style="width:95%"/>
      <tr>
      	<td colspan='6'>主表信息</td>
      </tr>
      <freeze:cell property="sys_rd_data_source_id" caption="数据源：" valueset="取数据源" show="name" colspan="2" style="width:80%" />
      <freeze:cell property="table" caption="名称："   datatype="string" style="width:95%" />
      <freeze:cell property="sys_rd_system_id" caption="主题：" valueset="业务主题"  datatype="string"  style="width:80%" />
      <freeze:select caption="选择字段" property="columns" colspan="2" style="width:220"></freeze:select>
      <freeze:hidden property="table_name" caption="名称："   datatype="string" style="width:95%" />
      <freeze:hidden property="table_code" caption="代码："   datatype="string" style="width:95%" />
      <freeze:hidden property="table_fk" caption="主表字段" />
      <freeze:hidden property="table_fk_name" caption="主表字段名称" />
      <freeze:hidden property="sys_rd_table_id" caption="主表ID" datatype="string" maxlength="32" style="width:95%"/>
      <tr>
      	<td colspan='6'>请选择关联表信息</td>
      </tr> 
        
      <freeze:hidden caption="数据源" property="ref_sys_rd_data_source_id" colspan="2" onchange="reset1()"></freeze:hidden>
      <freeze:select property="sys_rd_system_id_1" caption="主题名称" valueset="业务主题" colspan="2" onchange="reset3();"  style="width:220"></freeze:select>
      <freeze:select property="relation_table_code_str" caption="关联表" colspan="2" onchange="reset2();" style="width:220"></freeze:select>
      <freeze:select caption="关联字段" property="ref_columns" colspan="2" style="width:220"></freeze:select>
      <freeze:hidden property="table_relation_type" caption="关联关系类型"  style="width:95%" />
    
      <freeze:hidden property="ref_sys_rd_table_id" caption="关联"  maxlength="100" style="width:95%"/>
      <freeze:hidden property="relation_table_name" caption="关联表中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="relation_table_fk" caption="关联表字段" datatype="string" maxlength="100" style="width:95%" />
      <freeze:hidden property="relation_table_fk_name" caption="关联表字段中文名称" datatype="string" maxlength="100" style="width:95%"/>
   
     
     <freeze:hidden property="relation_table_code" caption="关联表"  style="width:95%"/>
     <freeze:textarea property="remarks" caption="注释" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
     <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>
  <br/>
   
  <freeze:grid property="record" caption="查询表关系列表"  checkbox="false"  width="95%" fixrow="false" >
      <freeze:hidden property="sys_rd_table_relation_id" caption="关联ID" style="width:12%" />
      <freeze:hidden property="sys_rd_table_id" caption="主表ID" style="width:12%" />
      <freeze:hidden property="table_code" caption="主表代码" style="width:20%" />
      <freeze:hidden property="table_name" caption="主表中文名称" style="width:20%" />
      <freeze:hidden property="table_fk" caption="主表字段" style="width:20%" />
      <freeze:cell property="no" caption="序号"  style="width:6%" align="center" /> 
      <freeze:cell property="table_fk_name" caption="主表字段名称" style="width:20%" />
      <freeze:hidden property="relation_table_code" caption="关联表代码" style="width:20%" />
      <freeze:cell property="relation_table_name" caption="关联表中文名称" style="width:20%" />
      <freeze:hidden property="relation_table_fk" caption="关联表字段" style="width:20%" />
      <freeze:cell property="relation_table_fk_name" caption="关联表字段中文名称" style="width:20%" />
      <freeze:hidden property="sys_rd_system_id" caption="主表所属主题ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="主表所属数据源ID" style="width:12%" />
      
      <freeze:hidden property="ref_sys_rd_data_source_id" caption="关联表所属数据源ID" style="width:12%" />
      <freeze:hidden property="table_relation_type" caption="关联关系类型" style="width:10%" />    
      <freeze:hidden property="remarks" caption="注释" style="width:20%"  />
      <freeze:cell property="operation" caption="操作" align="center" style="width:5%" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
  </freeze:grid>
 </freeze:form>

</freeze:body>
</freeze:html>
