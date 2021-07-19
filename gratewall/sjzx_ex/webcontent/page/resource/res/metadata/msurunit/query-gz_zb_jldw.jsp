<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-2/detail-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询计量单位列表</title>
</head>

<script language="javascript">

// 增加计量单位
function func_record_addRecord()
{
	var page = new pageDefine( "insert-gz_zb_jldw.jsp", "增加计量单位", "modal" );
	
	// 输入外键信息
	page.addParameter( "select-key:dwlb_dm", "record:dwlb_dm" );	
	page.addParameter( "select-key:dwlb_cn_mc", "record:dwlb_cn_mc" );	
	page.addRecord();
}

// 修改计量单位
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn301064.do", "修改计量单位", "modal" );
	page.addParameter( "record:jldw_dm", "primary-key:jldw_dm" );
	page.addParameter( "select-key:dwlb_cn_mc", "select-key:dwlb_cn_mc" );
	page.updateRecord();
}

// 删除计量单位
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn301065.do", "删除计量单位" );
	page.addParameter( "record:jldw_dm", "select-key:jldw_dm" );
	page.addParameter( "record:jldw_cn_mc", "select-key:jldw_cn_mc" );
	page.deleteRecord( "是否删除选中的记录" );
}


// 返 回
function func_record_goBackNoUpdate()
{
	 goBackWithUpdate("/txn301051.do");	// /txn301051.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询计量单位列表"/>
<freeze:errors/>

<freeze:form action="/txn301061">
  <freeze:frame property="select-key" width="95%">
  <freeze:hidden property="dwlb_dm" caption="计量单位类别代码"/>
  <freeze:hidden property="dwlb_cn_mc" caption="计量单位类别中文名称"/>
  </freeze:frame>

  <freeze:grid property="record" caption="计量单位列表" keylist="jldw_dm" width="95%" navbar="bottom" fixrow="false">
      <freeze:if test="${ !(empty select-key.dwlb_dm) }">
        <freeze:button name="record_addRecord" caption="增加" txncode="301063" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      </freeze:if>
      <freeze:button name="record_updateRecord" caption="修改" txncode="301064" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="301065" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_goBackNoUpdate" caption="返 回" enablerule="0" hotkey="CLOSE" align="right" onclick="func_record_goBackNoUpdate();"/>     
      <freeze:cell property="jldw_dm" caption="计量单位代码" style="width:17%" />
      <freeze:cell property="dwlb_dm" caption="计量单位类别代码" style="width:10%" visible="false"/>
      <freeze:cell property="jldw_cn_mc" caption="计量单位中文名称" style="width:34%" />
      <freeze:cell property="jldw_sjz" caption="计量单位数据值" style="width:17%" />
      <freeze:cell property="jldw_en_mc" caption="计量单位英文名称" style="width:32%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
