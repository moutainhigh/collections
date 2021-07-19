<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-2/detail-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询主题列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加主题
function func_record_addRecord()
{
	var page = new pageDefine( "insert-jc_sys.jsp", "增加主题", "modal" );
	
	// 输入外键信息
	page.addParameter("select-key:db_username","record:db_username");
	page.addParameter( "select-key:db_name", "record:db_name" );
	page.addParameter("select-key:sys_rd_data_source_id","record:sys_rd_data_source_id");
	page.addRecord();
}

// 修改主题
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn8000114.do", "修改主题", "modal" );
	page.addParameter( "record:sys_rd_system_id", "primary-key:sys_rd_system_id" );
	page.updateRecord();
}

// 删除主题
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn8000115.do", "删除主题" );
	page.addParameter( "record:sys_rd_system_id", "primary-key:sys_rd_system_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	// /txn8000101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$(".grid-headrow td:eq(0)").css("width","30");
}

_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="查询主题列表"/>
<freeze:errors/>

<freeze:form action="/txn8000111">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
  	  <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" datatype="string" style="width:95%"/>
      <freeze:hidden property="db_username" caption="用户" datatype="string" style="width:39%"/>
      <freeze:text property="db_name" caption="数据源名称" datatype="string" colspan="2" style="width:39%" readonly="true"/>
      <freeze:text property="sys_no" caption="主题编号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="sys_name" caption="主题名称" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询主题列表" nowrap="true" keylist="sys_rd_system_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加主题" enablerule="0" hotkey="ADD" align="right" icon="/script/button-icon/icon_add.gif" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改主题" enablerule="1" hotkey="UPDATE" icon="/script/button-icon/icon_update.gif" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除主题" enablerule="2" hotkey="DELETE" icon="/script/button-icon/icon_delete.gif" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_goBackNoUpdate" caption="返 回" enablerule="0" hotkey="CLOSE" align="right" icon="/script/button-icon/icon_goback.gif" onclick="func_record_goBackNoUpdate();"/>
      <freeze:cell property="sys_rd_system_id" caption="主键" visible="false"/>
      <freeze:cell property="sys_no" caption="主题编号" style="width:20%" />
      <freeze:cell property="sys_name" caption="主题名称" style="width:20%" />
      <freeze:cell property="sys_simple" caption="业务系统" style="width:20%" />
      <freeze:cell property="memo" caption="备注" style="width:20%"  />
      <freeze:cell property="isshow" caption="是否可用" valueset="布尔型数" style="width:10%"  />
      <freeze:hidden property="db_schame" caption="数据库模式" />
      <freeze:hidden property="timestamp" caption="时间戳" />
      <freeze:hidden property="sort" caption="排序" />
      <freeze:hidden property="db_name" caption="数据源" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
