<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加自定义服务</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/DemoStyle.css">
</head>
 
<script language="javascript">

// 保 存
function func_record_saveRecord()
{
  if(!validateForm()){
    return;
  }
	//saveRecord( '', '保存自定义服务' );
	saveService(false);
}

// 保存并继续
function func_record_saveAndContinue()
{
  if(!validateForm()){
    return;
  }
	//saveAndContinue( '', '保存自定义服务' );
	saveService(false);
}

// 保存并关闭
function func_record_saveAndExit()
{
  if(!validateForm()){
    return;
  }
	//saveAndExit( '', '保存自定义服务' );	// /txn50202001.do
	saveService(true);
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn50202001.do
}

function saveService(ifClose){
	var page = new pageDefine( "/txn50202011.ajax", "保存共享服务管理");
	page.addParameter("record:name", "record:name" );
	page.addParameter("record:dczd_dm", "record:dczd_dm" );
	page.addParameter("record:table_no", "record:table_no" );
	page.addParameter("record:column_no", "record:column_no" );
	page.addParameter("record:param_columns", "record:param_columns" );
	page.addParameter("record:service_desc", "record:service_desc" );
	page.addParameter("record:svr_code", "record:svr_code" );
	page.addParameter("record:max_records", "record:max_records" );
	page.addParameter("record:service_type", "record:service_type" );
	if(ifClose){
		page.callAjaxService('saveAndClose');
	}else{
		page.callAjaxService('justSave');
	}
}

function justSave(errCode, errDesc, xmlResults){ 
    if(errCode != '000000'){
      alert("处理错误："+errDesc);
      return;
    }else{
    	alert("保存成功！");
    }
}

function saveAndClose(errCode, errDesc, xmlResults){ 
    if(errCode != '000000'){
      alert("处理错误："+errDesc);
      return;
    }else{
    	alert("保存成功！");
    	_closeModalWindow(false, 1,"/txn50202001.do");
    }
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
}

//=======================================================================
      
function validateForm(){
  if(getFormFieldValue("record:max_records").trim() == ""){
    alert("请输入[最大记录数]");
    document.getElementById("record:max_records").focus();
    return false;
  }
  var _total = getFormFieldValue("record:max_records");
  var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
  if (reg.test(_total)==false){
	alert("请正确设置[最大记录数]");
	return false;
  }
  
  _total = parseInt(getFormFieldValue("record:max_records"));
  if(_total < -1){
    alert("请正确设置[最大记录数]");
    document.getElementById("record:max_records").select();
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
  if(getFormFieldValue("record:table_no") == ""){
    alert("请输入自定义类名！");
    return;
  }
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
  var tbl = document.getElementById("columnList");
  var childs = tbl.childNodes;
  for(var i=childs.length-1; i>=0; i--){
    tbl.removeChild(childs[i]);
  }
  
  for(var i = 0;i<column_names.length;i++){
    tbl.appendChild(createRow(i+1,column_names[i],column_name_cns[i]));
  }  
  document.getElementById("recordTbl").style.display = "block";
  
  column_names = _getXmlNodeValues(xmlResults,"param-column:column_name");
  column_name_cns = _getXmlNodeValues(xmlResults,"param-column:column_name_cn");
  tbl = document.getElementById("paramcolumnList");
  childs = tbl.childNodes;
  for(var i=childs.length-1; i>=0; i--){
    tbl.removeChild(childs[i]);
  }
  
  for(var i = 0;i<column_names.length;i++){
    tbl.appendChild(createRow(i+1,column_names[i],column_name_cns[i]));
  }
  document.getElementById("recordTbl2").style.display = "block";
  
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
  var cell = document.createElement("td");
  cell.setAttribute("id","td_column_name");
  cell.setAttribute("align","center");
  var _span = document.createElement("span");
  _span.setAttribute("id","span_record:svr_code");
  _span.setAttribute("style","WIDTH: 100%; TEXT-ALIGN: center");
  _span.innerText = idx;
  cell.appendChild(_span);
  row.appendChild(cell);
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

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加自定义服务"/>
<freeze:errors/>

<freeze:form action="/txn50202011">
  <freeze:block property="record" caption="增加自定义服务" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="svr_code" caption="服务代码" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:text property="name" caption="名称" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:text property="table_no" caption="定义服务类：" datatype="string" maxlength="20" minlength="1" style="width:78%"/><input type="button" value="查询" class="menu" onclick="analyseClass()"/>
      <freeze:hidden property="service_type" caption="服务类型" value="自定义服务"/>
      <freeze:text property="max_records" caption="<font color=red>*最大记录数</font>" datatype="int" style="width:95%"/>
      <freeze:textarea property="service_desc" caption="备注" colspan="2" rows="4" maxlength="800" style="width:95%"/>
      </td>
      </tr>
      <tr>
      <td>字段信息：</td>
      <td colspan="3">
      <div>
      <center>
      
      <TABLE id="recordTbl" cellSpacing="0" cellPadding="0" style="width:100%" align="center" border="0" name="frame_record" style="display:none">
        <TBODY>
          <TR>
            <TD>
              <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
                <TR class=title-row>
                  <TD><IMG src="<%=request.getContextPath()%>/script/title/frame-icon.gif" border=0> 共享字段列表</TD>
                </TR>
              </TBODY>
              </TABLE>
            </TD>
          </TR>
          <TR>
            <TD>
              <DIV class=pages id=div_record>
                <INPUT id=attribute-node:record_selected-key type=hidden name=attribute-node:record_selected-key>
                <INPUT id=attribute-node:record_sort-column type=hidden name=attribute-node:record_sort-column> 
                <!-- 行动作在grid_initRowStyle中初始化 -->
                <TABLE class=frame-body id=record style="WORD-BREAK: break-all" cellSpacing=1 cellPadding=1 width="100%" border=0 noWrap="">
                <TR class=grid-headrow height=21>
                  <TD align=middle width=12%>序号</TD>
                  <TD id=label:record:svr_code style="CURSOR: default" align=middle width="22%">字段名称</TD>
                  <TD id=label:record:name style="CURSOR: default" align=middle width="22%">字段描述</TD>
                </TR>
                <TBODY id="columnList"></TBODY>
                </TABLE>
              </DIV>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      
      <br>
      <TABLE id="recordTbl2" cellSpacing="0" cellPadding="0" style="width:100%" align="center" border="0" name="frame_record" style="display:none">
        <TBODY>
          <TR>
            <TD>
              <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
                <TR class=title-row>
                  <TD><IMG src="<%=request.getContextPath()%>/script/title/frame-icon.gif" border=0> 参数字段列表</TD>
                </TR>
              </TBODY>
              </TABLE>
            </TD>
          </TR>
          <TR>
            <TD>
              <DIV class=pages id=div_record>
                <INPUT id=attribute-node:record_selected-key type=hidden name=attribute-node:record_selected-key>
                <INPUT id=attribute-node:record_sort-column type=hidden name=attribute-node:record_sort-column> 
                <!-- 行动作在grid_initRowStyle中初始化 -->
                <TABLE class=frame-body id=record style="WORD-BREAK: break-all" cellSpacing=1 cellPadding=1 width="100%" border=0 noWrap="">
                <TR class=grid-headrow height=21>
                  <TD align=middle width=12%>序号</TD>
                  <TD id=label:record:svr_code style="CURSOR: default" align=middle width="22%">字段名称</TD>
                  <TD id=label:record:name style="CURSOR: default" align=middle width="22%">字段描述</TD>
                </TR>
                <TBODY id="paramcolumnList"></TBODY>
                </TABLE>
              </DIV>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      </center>
      </div>
  </freeze:block>
  
</freeze:form>

</freeze:body>
</freeze:html>
