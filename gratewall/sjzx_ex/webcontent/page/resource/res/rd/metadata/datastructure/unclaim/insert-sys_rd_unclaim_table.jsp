<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加未认领表信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存未认领表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存未认领表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存未认领表' );	// /txn80002101.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加未认领表信息"/>
<freeze:errors/>

<freeze:form action="/txn80002103">
  <freeze:block property="record" caption="增加未认领表信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_rd_unclaim_table_id" caption="未认领表ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="unclaim_table_code" caption="未认领表" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_table_name" caption="未认领表名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="object_schema" caption="对象模式" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:text property="tb_index_name" caption="索引名称" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="tb_index_columns" caption="索引字段" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="tb_pk_name" caption="主键名" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="tb_pk_columns" caption="主键字段" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="cur_record_count" caption="当前记录数量" datatype="string" maxlength="" style="width:95%"/>
      <freeze:textarea property="remark" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="data_object_type" caption="数据对象类型" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
