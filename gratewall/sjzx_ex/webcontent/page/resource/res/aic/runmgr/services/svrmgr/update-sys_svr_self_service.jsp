<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改自定义服务</title>
</head>

<script language="javascript">

// 保存并关闭
function func_record_saveAndExit()
{ 
  if(!validateForm()){
    return;
  }
	saveAndExit( '', '保存自定义服务' );	// /txn50202001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn50202001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
  
}

//=======================================================================
      
function validateForm(){
  var _total = getFormFieldValue("record:max_records");
  
  var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
  if (reg.test(_total)==false){
	alert("请正确设置[最大记录数]");
	return false;
  }
  
  _total = parseInt(getFormFieldValue("record:max_records"));
  if(_total < -1){
    alert("请正确设置[最大记录数]");
    return false;
  }
  
  if(_total > 2000){
  	if(!confirm("建议[最大记录数]小于2000条，否则可能造成查询失败！是否继续？")){
	    document.getElementById("record:max_records").select();
	    return false;
    }
  }
  
  return true;
}

function analyseClass(){
  var page = new pageDefine( "/txn50202010.ajax", "查询自定义服务类信息");
  page.addValue( getFormFieldValue("record:table_no"), "record:table_no" );
  page.callAjaxService('showColumns');
}

function showColumns(errCode,errDesc,xmlResults){
  if(errCode != "000000"){
    alert("处理错误：" + errDesc);
    return;
  }
  
  //var column_nos = _getXmlNodeValues(xmlResults,"record:column_no");
  var column_names = _getXmlNodeValues(xmlResults,"shared-column:column_name");
  var column_name_cns = _getXmlNodeValues(xmlResults,"shared-column:column_name_cn");
  var tbl = document.getElementById("shared-column");
  var childs = tbl.childNodes;
  for(var i=childs.length-1; i>=0; i--){
    tbl.removeChild(childs[i]);
  }
  
  var _body = document.createElement("tbody");
  for(var i = 0;i<column_names.length;i++){
    _body.appendChild(createRow(i+1,column_names[i],column_name_cns[i]));
  }  
  
  tbl.appendChild(_body);
  
  
  column_names = _getXmlNodeValues(xmlResults,"param-column:column_name");
  column_name_cns = _getXmlNodeValues(xmlResults,"param-column:column_name_cn");
  tbl = document.getElementById("param-column");
  childs = tbl.childNodes;
  for(var i=childs.length-1; i>=0; i--){
    tbl.removeChild(childs[i]);
  }
  _body = document.createElement("tbody");
  for(var i = 0;i<column_names.length;i++){
    _body.appendChild(createRow(i+1,column_names[i],column_name_cns[i]));
  }
  tbl.appendChild(_body);
  
  var _URL = _getXmlNodeValues(xmlResults,"record:visit_URL");
  document.getElementById("record:visit_url").innerText = _URL;
}

function createRow(idx,columnName,columnNameCn){

  var _class = "evenrow";
  if((idx % 2) == 0){
    _class = "oddrow";
  }
  var row = document.createElement("tr");
  row.setAttribute("id", "row_"+idx);
  row.setAttribute("class", _class);
  
  //序号列
  var cell = document.createElement("td");
  cell.setAttribute("id","td_column_name");
  cell.setAttribute("align","center");
  var _span = document.createElement("span");
  _span.setAttribute("id","span_record:svr_code");
  _span.setAttribute("style","WIDTH: 100%; TEXT-ALIGN: center");
  _span.innerText = idx;
  cell.appendChild(_span);
  row.appendChild(cell);
  
  //字段名称列
  cell = document.createElement("td");
  cell.setAttribute("id","td_column_name");
  cell.setAttribute("align","center");
  cell.setAttribute("class ","framerow");
  var _span = document.createElement("span");
  _span.setAttribute("id","span_record:svr_code");
  _span.setAttribute("style","WIDTH: 100%; TEXT-ALIGN: center");
  _span.innerText = columnName;
  cell.appendChild(_span);
  row.appendChild(cell);
  
  //字段描述列
  cell = document.createElement("td");
  cell.setAttribute("id","td_column_name");
  cell.setAttribute("align","center");
  cell.setAttribute("class ","framerow");
  var _span = document.createElement("span");
  _span.setAttribute("id","span_record:svr_code");
  _span.setAttribute("style","WIDTH: 100%; TEXT-ALIGN: center");
  _span.innerText = columnNameCn;
  cell.appendChild(_span);
  row.appendChild(cell);
 
  return row;
}

</script>
<freeze:body>
<freeze:title caption="增加自定义服务"/>
<freeze:errors/>

<freeze:form action="/txn50202013">
  <freeze:block property="record" caption="修改自定义服务" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_svr_service_id" caption="共享服务编号" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="svr_code" caption="服务代码" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:text property="name" caption="名称" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:text property="table_no" caption="定义服务类：" datatype="string" maxlength="20" minlength="1" style="width:78%" /><input type="button" value="显示" class="menu" onclick="analyseClass()"/>
      <freeze:text property="max_records" caption="<font color=red>*最大记录数</font>" datatype="int" style="width:95%"/>
      <freeze:textarea property="service_desc" caption="备注" valign="center" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      </td>
      </tr>
      <tr>
      <td>字段信息：</td>
      <td colspan="3">
      <div>
      		<center>
		      <table class="frame-body" style="width:100%">
      			<tr class="grid-headrow" align="center">
      				<td>序号</td>
      				<td>字段名称</td>
      				<td>字段描述</td>
      			</tr>
      			<tbody id="shared-column" ></tbody>
      		</table><br>
      		<table class="frame-body" style="width:100%">
      			<tr class="grid-headrow" align="center">
      				<td>序号</td>
      				<td>字段名称</td>
      				<td>字段描述</td>
      			</tr>
      			<tbody id="param-column" ></tbody>
      		</table>
		      </center>
      </div>
  </freeze:block>
  
</freeze:form>

</freeze:body>
<script language="javascript">
analyseClass();
</script>
</freeze:html>
