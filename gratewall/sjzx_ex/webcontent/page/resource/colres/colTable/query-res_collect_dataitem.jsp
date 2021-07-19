<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询采集数据项列表</title>
</head>

<script language="javascript">

// 增加采集数据项表
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_collect_dataitem.jsp", "增加采集数据项表", "modal" );
	page.addRecord();
}

// 修改采集数据项表
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn20202004.do", "修改采集数据项表", "modal" );
	page.addParameter( "record:collect_dataitem_id", "primary-key:collect_dataitem_id" );
	page.updateRecord();
}

// 删除采集数据项表
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn20202005.do", "删除采集数据项表" );
	page.addParameter( "record:collect_dataitem_id", "primary-key:collect_dataitem_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids =document.getElementsByName("span_record:code_table");
	var value=getFormAllFieldValues("record:code_table");
	
	for(var i=0; i<ids.length; i++){
		var val=value[i];
		if(val==null||val==""){
		}else{
		  ids[i].innerHTML='<a href="javascript:func_record_querycode(\''+value[i]+'\');" title="" >'+val+'</a>';
		}
    }
}
function func_record_querycode(val){
	   
    var url="txn20301026.do?select-key:code_table="+val;
    var page = new pageDefine( url, "查看代码集信息", "代码集查询" );
	page.addRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询采集数据项列表"/>
<freeze:errors/>

<freeze:form action="/txn20202001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="dataitem_name_en" caption="数据项名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="dataitem_name_cn" caption="数据项中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="dataitem_type" caption="数据项类型" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="code_table" caption="对应代码表" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询采集数据项列表" keylist="collect_dataitem_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加采集数据项表" txncode="20202003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改采集数据项表" txncode="20202004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除采集数据项表" txncode="20202005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="collect_dataitem_id" caption="采集数据项ID" style="width:12%" />
      <freeze:cell property="collect_table_id" caption="采集数据表ID" style="width:12%" />
      <freeze:cell property="dataitem_name_en" caption="数据项名称" style="width:20%" />
      <freeze:cell property="dataitem_name_cn" caption="数据项中文名称" style="width:20%" />
      <freeze:cell property="dataitem_type" caption="数据项类型" style="width:12%" />
      <freeze:cell property="dataitem_long" caption="数据项长度" style="width:10%" />
      <freeze:cell property="is_key" caption="是否主键" style="width:10%" />
      <freeze:cell property="is_code_table" caption="是否代码表" style="width:10%" />
      <freeze:cell property="code_table" caption="对应代码表" style="width:12%" />
      <freeze:cell property="dataitem_long_desc" caption="数据项描述" style="width:20%" visible="false" />
      <freeze:cell property="is_markup" caption="有效标记" style="width:10%" />
      <freeze:cell property="creator_id" caption="创建人ID" style="width:12%" />
      <freeze:cell property="created_time" caption="创建时间" style="width:12%" />
      <freeze:cell property="last_modify_id" caption="最后修改人ID" style="width:12%" />
      <freeze:cell property="last_modify_time" caption="最后修改时间" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
