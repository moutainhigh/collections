<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询数据规则列表</title>
</head>

<script language="javascript">

// 增加规则
function func_record_addRecord()
{
	var page = new pageDefine( "insert-data_standard.jsp", "增加规则","modal" );
	page.addRecord();
}

// 修改规则
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn603004.do", "修改规则","modal" );
	page.addParameter( "record:standard_id", "primary-key:standard_id" );
	page.updateRecord();
}
function func_record_updateRecord(idx)
{
    var svrId = getFormFieldValue("record:standard_id", idx);
	var page = new pageDefine( "/txn603004.do", "修改规则","modal" );
	page.addValue( svrId, "primary-key:standard_id" );
	page.updateRecord();
}
// 删除规则
function func_deleteRecord(idx)
{
	var page = new pageDefine( "/txn603005.do", "删除规则" );
	page.addValue( idx, "primary-key:standard_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn603005.do", "删除规则" );
	page.addParameter( "record:standard_id", "primary-key:standard_id" );
	page.deleteRecord( "是否删除选中的记录" );
}
//查看服务表
function func_viewConfig(idx)
{
	var svrId = getFormFieldValue("record:standard_id", idx);
	var page = new pageDefine( "/txn603016.do", "查看服务表" ,"modal");
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
<freeze:title caption="查询数据规则列表"/>
<freeze:errors/>

<freeze:form action="/txn603001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="standard_name" caption="数据规范名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="specificate_no" caption="类型分类号" datatype="string" maxlength="50" style="width:95%"/>
<freeze:datebox property="created_time_start" caption="启用时间" datatype="string" maxlength="11" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
      </td><td width='5%'>&nbsp;至：</td><td width='45%'>
      <freeze:datebox property="created_time_end" caption="启用时间" datatype="string" maxlength="11" style="width:100%" colspan="0"/>
      </td><td width='5%'></td></tr></table>  </freeze:block>
<br/>
  <freeze:grid property="record" checkbox="false"   caption="查询规则列表" keylist="standard_id" width="95%" navbar="bottom" >
      <freeze:button name="record_addRecord" caption="增加" txncode="603003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="standard_id" caption="标准ID" style="width:10%" visible="false"/>
      <freeze:cell property="@rowid" align="center" caption="序号" style="width:5%" />
      <freeze:cell property="standard_name" align="center" caption="数据规范名称" style="width:20%" />
      <freeze:cell property="issuing_unit" align="center" caption="发布单位" style="width:12%" />
      <freeze:cell property="specificate_no" caption="类型分类号" style="width:10%" align="center"/>
	  <freeze:cell property="enable_time" align="center" caption="启用时间" style="width:12%" />
      <freeze:cell property="fjmc" caption="文件名称" style="width:31%" align="center"/>

      <freeze:cell property="oper" nowrap="true" caption="操作" align="center" style="width:10%"/>
      
      <freeze:hidden property="standard_type" caption="标准类型"  />
      <freeze:hidden property="specificate_type" caption="规则类型"  />
      
      <freeze:hidden property="specificate_status" caption="类型状态"  />
      <freeze:hidden property="is_markup" caption="代码集 无效 有效"  />
      <freeze:hidden property="creator_id" caption="创建人ID"  />
      
      <freeze:hidden property="specificate_status" align="center" caption="备注"  />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID"  />
      <freeze:hidden property="last_modify_time" caption="最后修改时间"  />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
