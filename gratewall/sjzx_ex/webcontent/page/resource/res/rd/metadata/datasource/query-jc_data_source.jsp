<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-2/master-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询数据源列表</title>
<style>
.even2,.even1{padding-left:0px;}
</style>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加数据源
function func_record_addRecord()
{
	var page = new pageDefine( "insert-jc_data_source.jsp", "增加数据源", "modal");
	page.addRecord();
}

// 修改数据源
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn8000104.do", "修改数据源", "modal");
	page.addParameter( "record:sys_rd_data_source_id", "primary-key:sys_rd_data_source_id" );
	page.updateRecord();
}

// 删除数据源
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn8000105.do", "删除数据源" );
	page.addParameter( "record:sys_rd_data_source_id", "primary-key:sys_rd_data_source_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

/**
 * 
 */
function func_syncTask(){
	var page = new pageDefine( "sync-jc_data_source.jsp", "同步数据源","modal" );
	page.addParameter("record:sys_rd_data_source_id","record:sys_rd_data_source_id");
	page.addParameter("record:db_server","record:db_server");
	page.addParameter("record:db_name","record:db_name");
	page.addParameter("record:db_schema","record:db_schema");
	page.addParameter("record:db_username","record:db_username");
	page.updateRecord();
}

// 维护明细表[jc_sys]
function func_go_jc_sys()
{
	// 维护明细表时，需要传递和主表[jc_data_source]的外键
	var page = new pageDefine( "/txn8000111.do", "业务系统表" );
	page.addParameter("record:db_username","select-key:db_username");
	page.addParameter( "record:db_name", "select-key:db_name" );
	page.addParameter("record:sys_rd_data_source_id","select-key:sys_rd_data_source_id");
	page.goPage();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var sync = document.getElementsByName("span_record:sync_menu");
	for(var ii=0;ii<sync.length;ii++){
		sync[ii].innerHTML = '<font title="同步数据源" style="cursor:hand;" color="#333">&nbsp;&nbsp;<b>同步</b></font>';
	}
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
	$(".grid-headrow td:eq(0)").css("width","30");
}

_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="查询数据源列表"/>
<freeze:errors/>

<freeze:form action="/txn8000101">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="db_name" caption="数据源名称" valueset="从配置文件取数据库列表" colspan="2" style="width:36.5%"/>
      
  </freeze:block>
<br />
  <freeze:grid property="record" caption="查询数据源列表" nowrap="true" keylist="sys_rd_data_source_id" width="95%" navbar="bottom" multiselect="false" rowselect="false" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加数据源" enablerule="0" hotkey="ADD" icon="/script/button-icon/icon_add.gif" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改数据源" enablerule="1" hotkey="UPDATE" icon="/script/button-icon/icon_update.gif" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除数据源" enablerule="2" hotkey="DELETE" icon="/script/button-icon/icon_delete.gif" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="go_jc_sys" caption="业务主题" enablerule="1" align="right" onclick="func_go_jc_sys();"/>
      <freeze:cell property="sys_rd_data_source_id" caption="ID" visible="false"/>
      <freeze:cell property="db_name" caption="数据源名称" style="width:10%" />
      <freeze:cell property="db_type" caption="数据源类型" style="width:10%" />
      <freeze:cell property="db_server" caption="数据库类型" style="width:10%" />
      <freeze:cell property="db_username" caption="用户" style="width:10%" />
      <freeze:cell property="sync_date" caption="同步日期" style="width:10%" />
      <freeze:cell property="creator" caption="创建人" style="width:10%" />
      <freeze:cell property="create_date" caption="创建时间" style="width:10%" />
      <freeze:link property="sync_menu" caption="同步数据源" value="同步" style="width:10%" onclick="func_syncTask();" />
      
      <freeze:cell property="db_url" caption="连接信息" visible="false"/>
      <freeze:cell property="db_driver" caption="驱动程序" visible="false"/>
      <freeze:cell property="value_class" caption="数据转换类" visible="false"/>
      <freeze:cell property="merge_flag" caption="合并公共配置信息" visible="false"/>
      <freeze:cell property="db_isolation" caption="事务隔离级" visible="false"/>
      <freeze:cell property="sync_table" caption="更新数据表" visible="false"/>
      <freeze:cell property="db_transaction" caption="事务类型" visible="false"/>
      <freeze:hidden property="db_schema" caption="数据库模式" style="width:12%" />
      <freeze:cell property="db_svrname" caption="实例名" visible="false"/>
      <freeze:cell property="db_address" caption="主机IP" visible="false"/>
      <freeze:cell property="db_port" caption="端口" visible="false"/>
      <freeze:cell property="timestamp" caption="时间戳" visible="false"/>
      <freeze:cell property="memo" caption="备注" visible="false" />
      <freeze:cell property="sync_flag" caption="同步标志" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
