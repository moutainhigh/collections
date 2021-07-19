<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-2/master-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询计量单位类别列表</title>
</head>

<script language="javascript">

// 增加计量单位类别
function func_record_addRecord()
{
	var page = new pageDefine( "insert-gz_zb_jldw_fl.jsp", "增加计量单位类别", "modal" );
	page.addRecord();

}

// 修改计量单位类别
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn301054.do", "修改计量单位类别", "modal" );
	page.addParameter( "record:dwlb_dm", "primary-key:dwlb_dm" );
	page.updateRecord();
}

// 删除计量单位类别
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn301055.do", "删除计量单位类别" );
	page.addParameter( "record:dwlb_dm", "select-key:dwlb_dm" );
	page.addParameter( "record:dwlb_cn_mc", "select-key:dwlb_cn_mc" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 维护明细表[gz_zb_jldw]
function func_go_gz_zb_jldw()
{
	// 维护明细表时，需要传递和主表[gz_zb_jldw_fl]的外键
	var page = new pageDefine( "/txn301061.do", "计量单位表" );
	page.addParameter( "record:dwlb_dm", "select-key:dwlb_dm" );
	page.addParameter( "record:dwlb_cn_mc", "select-key:dwlb_cn_mc" );
	page.goPage();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询计量单位类别列表"/>
<freeze:errors/>

<freeze:form action="/txn301051">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%" captionWidth="0.4" nowrap="true">
      <freeze:text property="dwlb_dm" caption="计量单位类别代码" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="dwlb_cn_mc" caption="计量单位类别中文名称" datatype="string" maxlength="255" style="width:95%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="计量单位类别列表" keylist="dwlb_dm" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加" txncode="301053" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改" txncode="301054" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="301055" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="go_gz_zb_jldw" caption="计量单位" txncode="301061" enablerule="1" align="right" onclick="func_go_gz_zb_jldw();"/>
      <freeze:cell property="dwlb_dm" caption="计量单位类别代码" style="width:15%" />
      <freeze:cell property="dwlb_cn_mc" caption="计量单位类别中文名称" style="width:19%" />
      <freeze:cell property="dwlb_cn_ms" caption="计量单位类别中文描述" style="width:25%" />
      <freeze:cell property="dwlb_en_mc" caption="计量单位类别英文名称" style="width:19%" />
      <freeze:cell property="dwlb_en_ms" caption="计量单位类别英文描述" style="width:22%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
