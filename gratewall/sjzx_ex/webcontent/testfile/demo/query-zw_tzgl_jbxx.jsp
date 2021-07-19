<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询通知管理列表</title>
</head>

<script language="javascript">

function func_record_testRecord(){
	
	var page = new pageDefine( "test-user_svr.jsp","用户服务测试","modal", document.body.clientWidth, document.body.clientHeight);
	
	page.addValue( "lztest1", "svrId");
	page.updateRecord( );
}

function func_record_testRecord_pri(){
	
	var page = new pageDefine( "test-user_svr_pri.jsp","用户服务测试","modal", document.body.clientWidth, document.body.clientHeight);
	
	page.addValue( "lztest1", "svrId");
	page.updateRecord( );
}

// 增加通知管理
function func_record_addRecord()
{
	var page = new pageDefine( "insert-zw_tzgl_jbxx.jsp", "增加通知管理" );
	page.addRecord();
}

// 修改通知管理
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn315001004.do", "修改通知管理" );
	page.addParameter( "record:jbxx_pk", "primary-key:jbxx_pk" );
	page.updateRecord();
}

// 查看通知管理
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn315001006.do", "查看通知管理" );
	page.addParameter( "record:jbxx_pk", "primary-key:jbxx_pk" );
	page.updateRecord();
}

//会议管理明细
function tzmx(index){   
    var gridname = getGridDefine("record");
    var param = "primary-key:jbxx_pk=" + gridname.getAllFieldValues( "jbxx_pk" )[index];
    var page = new pageDefine( "/txn315001006.do?"+param, "查看通知明细", "window", 800, 600);
    page.updateRecord( );  
}

// 删除通知管理
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn315001005.do", "删除通知管理" );
	page.addParameter( "record:jbxx_pk", "primary-key:jbxx_pk" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询通知管理列表"/>
<freeze:errors/>

<freeze:form action="/txn315001001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="tzmc" caption="通知名称" datatype="string" maxlength="255" style="width:95%"/>
      <freeze:text property="fbsj" caption="发布时间" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="tzzt" caption="通知状态" datatype="string" maxlength="2" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询通知管理列表" keylist="jbxx_pk" width="95%" navbar="bottom" fixrow="true">
  	  <freeze:button name="record_testRecord" caption="二期测试"  enablerule="0" hotkey="ADD" align="right" onclick="func_record_testRecord_pri();" visible="false"/>
  	  <freeze:button name="record_testRecord" caption="测试"  enablerule="0" hotkey="ADD" align="right" onclick="func_record_testRecord();" visible="false"/>
      <freeze:button name="record_addRecord" caption="增加" txncode="315001003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改" txncode="315001004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_updateRecord" caption="查看" txncode="315001006" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="315001005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="jbxx_pk" caption="通知编号-主键" style="width:10%" visible="false"/>
      <freeze:cell property="tzmc" caption="通知名称" style="width:22%" />
      <freeze:cell property="fbrid" caption="发布人ID" style="width:13%" />
      <freeze:cell property="fbrmc" caption="发布人名称" style="width:13%" />
      <freeze:cell property="fbksid" caption="发布科室" style="width:13%" />
      <freeze:cell property="fbksmc" caption="发布名称" style="width:17%" />
      <freeze:cell property="fbsj" caption="发布时间" style="width:12%" />
      <freeze:cell property="tznr" caption="通知内容" style="width:20%" visible="false" />
      <freeze:cell property="tzzt" caption="通知状态" style="width:10%" />
      <freeze:cell property="jsrids" caption="接收人ids" style="width:20%" visible="false" />
      <freeze:cell property="jsrmcs" caption="接收人名称" style="width:20%" visible="false" />
      <freeze:cell property="fj_fk" caption="附件id" style="width:20%" visible="false" />
      <freeze:cell property="fjmc" caption="附件名称" style="width:20%" visible="false" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
