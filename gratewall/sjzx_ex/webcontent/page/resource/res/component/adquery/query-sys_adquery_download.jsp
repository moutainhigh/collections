<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询高级查询及下载列表</title>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
DataBus info=context.getRecord("select-key");
%>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加高级查询
function func_record_addRecord()
{
	//alert("您好，区县用户只能下载本区县的数据！");	
	var page = new pageDefine( "/txn6025003.do", "增加查询" );
	page.addRecord();
}

// 增加下载
function func_record_addDownload()
{
	//alert("您好，区县用户只能下载本区县的数据！");
	var page = new pageDefine( "/txn6025009.do", "增加下载" );
	page.addRecord();
}

// 修改高级查询及下载
function func_record_updateRecord(idx,queryType)
{
	var id = getFormFieldValue("record:sys_advanced_query_id", idx);
	var page = new pageDefine( "/txn6025006.do", "修改高级查询");
	page.addValue( id, "select-key:sys_advanced_query_id" );
	page.addValue( queryType, "queryType" );
	page.addValue( id, "attribute-node:record_primary-key");
	page.addValue("update","action");
	page.updateRecord();
}

function func_record_execRecord(idx)
{
	var id = getFormFieldValue("record:sys_advanced_query_id", idx);
	var querytype = getFormFieldValue("record:query_type", idx);
	querytype = (querytype=='查询' ? 0 : 1);
   	var page = new pageDefine( "/txn6025004.do", "执行高级查询");
	page.addValue( id, "select-key:sys_advanced_query_id" );
	page.addValue( querytype, "select-key:queryType" );
   	page.goPage();
}

function func_record_detail(idx)
{
	var id = getFormFieldValue("record:sys_advanced_query_id", idx);
   	var page = new pageDefine( "/txn6025005.do", "高级查询详情","modal");
	page.addValue( id, "select-key:sys_advanced_query_id" );
   	page.goPage();
}

// 删除高级查询及下载
function func_record_deleteRecord(idx,qtype)
{
	var id = getFormFieldValue("record:sys_advanced_query_id", idx);
   	var page = new pageDefine( "/txn6025007.do", "删除查询详情","modal");
	page.addValue( id, "select-key:sys_advanced_query_id" );
	page.addValue( qtype, "queryType" );
   	page.goPage();
}

function resetForm(){
	setFormFieldValue("select-key:name", 0 ,"");
	setFormFieldValue("select-key:query_type", 0 ,"");
	setFormFieldValue("select-key:create_date_begin", 0 ,"");
	setFormFieldValue("select-key:create_date_end", 0 ,"");
}

function submitForm(){
  _querySubmit();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var query_type='<%=info.getValue("query_type")%>';
	setFormFieldValue("select-key:query_type", 0 ,query_type);
	var ids = getFormAllFieldValues("record:sys_advanced_query_id");
	var types = getFormAllFieldValues("record:query_type");
	for(var i=0;i<ids.length;i++){
	   if(types[i]=='查询'){
	   document.getElementsByName("span_record:operation")[i].innerHTML +='<a href="#" title="详情" onclick="func_record_detail('+i+');"><div class="detail"></div></a> <a href="#" title="修改" onclick="func_record_updateRecord('+i+',\'0\')"><div class="edit"></div></a> '+
	     '<a href="#" title="执行查询" onclick="func_record_execRecord('+i+');"><div class=\'run\'></div></a> '+
	     '<a href="#" title="删除" onclick="func_record_deleteRecord('+i+',\'0\');"><div class=\'delete\'></div></a>';
	   }else{
	   document.getElementsByName("span_record:operation")[i].innerHTML +='<a href="#" title="详情"  onclick="func_record_detail('+i+');"><div class="detail"></div></a> <a href="#" title="修改" onclick="func_record_updateRecord('+i+',\'1\')"><div class="edit"></div></a> '+
	     '<a href="#" title="执行下载" onclick="func_record_execRecord('+i+');"><div class=\'run\'></div></a></a> '
	     +'<a href="#" title="删除" onclick="func_record_deleteRecord('+i+',\'1\');"><div class=\'delete\'></div></a>';
	   
	   }
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询高级查询及下载列表"/>
<freeze:errors/>

<freeze:form action="/txn6025001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>
   <freeze:block property="select-key" caption="查询条件" width="95%">
      <freeze:text property="name" caption="名称" datatype="string" maxlength="30" style="width:90%"/>
       <td id='label:select-key:query_type'>类型</td>
      <td>
      <select name='select-key:query_type' id='select-key:query_type'  notnull='false' readOnly='false' style="width:90%" >
			<option value="">全部</option>
			<option value="查询">查询</option>
			<option value="下载">下载</option>
        </select></td></tr>
      <freeze:datebox property="create_date_begin" caption="创建日期" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>至</td><td width='45%'>
    <freeze:datebox property="create_date_end" caption="创建日期" style="width:100%" colspan="0"/>
    </td></tr></table>    
      <td colspan="2" ></td></tr>
      <freeze:button name="queryButton" caption="查 询" onclick="submitForm()"/>
      <freeze:button name="resetButton" caption="重 填" onclick="resetForm()"/>
  </freeze:block>
  <br/>

  <freeze:grid property="record" caption="高级查询及下载列表" width="95%" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:button name="record_addRecord" caption="新增查询" txncode="6025003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <freeze:button name="record_updateRecord" caption="新增下载" txncode="6025009" enablerule="0" hotkey="UPDATE" align="right" onclick="func_record_addDownload();"/>
      
      <freeze:cell align="middle" property="@rowid" caption="序号" style="width:5%"/>
      <freeze:cell property="name" caption="名称" style="width:25%" />
      <freeze:cell property="query_type" caption="类型" style="width:5%" />
      <freeze:cell property="create_date" caption="创建日期" style="width:13%" />
      <freeze:cell property="last_exec_date" caption="最后执行日期" style="width:13%" />
      <freeze:cell property="last_down_date" caption="最后下载时间" style="width:20%" />
      <freeze:cell property="operation" caption="操作" align="center" style="" />
      
      <freeze:hidden property="sys_svr_user_id" caption="用户id"  />
      <freeze:hidden property="is_mutil_download" caption="是否重复下载" />
      <freeze:hidden property="apply_count" caption="申请次数" />
      <freeze:hidden property="last_down_date" caption="最后下载时间" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
