<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询技术规则列表</title>
</head>

<script language="javascript">

// 增加规则
function func_record_addRecord()
{
	var page = new pageDefine( "insert-tech_standard.jsp", "增加技术规则","modal");
	page.addRecord();
}

// 修改规则
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn603009.do", "修改技术规则" ,"modal");
	page.addParameter( "record:standard_id", "primary-key:standard_id" );
	page.updateRecord();
}
function func_record_updateRecord(idx)
{
    var svrId = getFormFieldValue("record:standard_id", idx);
	var page = new pageDefine( "/txn603009.do", "修改技术规则" ,"modal");
	page.addValue( svrId, "primary-key:standard_id" );
	page.updateRecord();
}
// 删除规则
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn603010.do", "删除规则" );
	page.addParameter( "record:standard_id", "primary-key:standard_id" );
	page.deleteRecord( "是否删除选中的记录" );
}
function func_deleteRecord(idx)
{
	var page = new pageDefine( "/txn603010.do", "删除规则" );
	page.addValue( idx, "primary-key:standard_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

//查看服务表
function func_viewConfig(idx)
{
	var svrId = getFormFieldValue("record:standard_id", idx);
	var page = new pageDefine( "/txn603017.do", "查看服务表","modal" );
	page.addValue( svrId, "primary-key:standard_id" );
	page.updateRecord();
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	 var ids = getFormAllFieldValues("record:standard_id");
	for(var i=0; i<ids.length; i++){  
	   var htm = '<a href="#" title="修改" onclick="func_record_updateRecord('+i+')"><div class="edit"></div></a>&nbsp;';
	   htm += '<a href="#" title="删除" onclick="func_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	var names = getFormAllFieldValues("record:standard_name");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="点击查看详细信息" onclick="func_viewConfig(\''+i+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:standard_name")[i].innerHTML =htm;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询技术规则列表"/>
<freeze:errors/>

<freeze:form action="/txn603006">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="standard_name" caption="技术规范名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="specificate_no" caption="类型分类号" datatype="string" maxlength="50" style="width:95%"/>
<freeze:datebox property="created_time_start" caption="规范发布日期" datatype="string" maxlength="11" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
      </td><td width='5%'>&nbsp;至：</td><td width='45%'>
      <freeze:datebox property="created_time_end" caption="规范发布日期" datatype="string" maxlength="11" style="width:100%" colspan="0"/>
      </td><td width='5%'></td></tr></table>  </freeze:block>
<br/>
  <freeze:grid property="record"   checkbox="false" caption="查询规则列表" keylist="standard_id" width="95%" navbar="bottom" >
      <freeze:button name="record_addRecord" caption="增加" txncode="603008" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="standard_id" caption="标准ID" style="width:10%" visible="false"/>
      <freeze:cell property="@rowid" align="center" caption="序号" style="width:5%" />
      <freeze:cell property="standard_name" align="center" caption="技术规范名称" style="width:20%" />
      <freeze:cell property="enable_time" align="center" caption="发布日期" style="width:18%" />
      <freeze:cell property="fjmc"  caption="文件名称" align="center" style="" />
      <freeze:cell property="specificate_desc"  caption="备注" align="center" style="width:20%"  />
      <freeze:cell property="oper" nowrap="true" caption="操作" align="center" style="width:65px;" />
      
      <freeze:hidden property="standard_type" caption="标准类型"  />
      <freeze:hidden property="specificate_type" caption="规则类型"  />
      <freeze:hidden property="issuing_unit" caption="发布单位"  />
      <freeze:hidden property="specificate_no" caption="类型分类号"  />
      <freeze:hidden property="specificate_status" caption="类型状态"  />
      <freeze:hidden property="is_markup" caption="代码集 无效 有效"  />
      <freeze:hidden property="creator_id" caption="创建人ID"  />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID"  />
      <freeze:hidden property="last_modify_time" caption="最后修改时间"  />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
