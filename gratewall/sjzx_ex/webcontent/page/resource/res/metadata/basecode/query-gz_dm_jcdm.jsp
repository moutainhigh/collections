<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-1/master-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询代码列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加基础代码
function func_record_addRecord()
{
	var page = new pageDefine( "insert-gz_dm_jcdm.jsp", "增加基础代码", "modal" );
	page.addRecord();
}

// 修改基础代码
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn301014.do", "修改基础代码", "modal" );
	page.addParameter( "record:jc_dm_id", "primary-key:jc_dm_id" );
	page.updateRecord();
}

// 删除基础代码
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn301015.do", "删除基础代码" );
	page.addParameter( "record:jc_dm_id", "primary-key:jc_dm_id" );

	page.addParameter( "record:jc_dm_id", "select-key:jc_dm_id" );
	page.addParameter( "record:jc_dm_mc", "select-key:jc_dm_mc" );	
	page.addParameter( "record:jc_dm_dm", "select-key:jc_dm_dm" );
	page.deleteRecord( "是否删除选中的记录" );
	//page.callAjaxService('doCallback');
}

function doCallback(errCode, errDesc, xmlResults)
{
    if (errCode != '000000'){
		alert(errDesc);
		return;
	}
}

function func_go_gz_dm_jcdm_fx()
{
	// 维护明细表时，需要传递和主表[gz_zb_ml]的外键
	var page = new pageDefine( "/txn3010101.do", "基础数据分项表", "model", 950, 650);
	page.addParameter( "record:jc_dm_id", "select-key:jc_dm_id" );
	page.addParameter( "record:jc_dm_mc", "select-key:jc_dm_mc" );
	page.addParameter( "record:jc_dm_dm", "select-key:jc_dm_dm" );
	page.addParameter( "record:jc_dm_bzly", "select-key:jc_dm_bzly" );
	page.goPage();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a title='修改' onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'><div class='edit'></div></a>&nbsp;<a title='分项维护' onclick='setCurrentRowChecked(\"record\");func_go_gz_dm_jcdm_fx();' href='#'><div class='config'></div></a>";
	}	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询代码列表"/>
<freeze:errors/>

<freeze:form action="/txn301011">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%" nowrap="true">
      <freeze:text property="jc_dm_dm" caption="代码" datatype="string" maxlength="255" style="width:90%"/>
      <freeze:text property="jc_dm_mc" caption="代码名称" datatype="string" maxlength="255" style="width:90%"/>
      <freeze:select property="jc_dm_bzly" caption="标准来源" valueset="基础代码标准来源"  style="width:90%"/>
      <freeze:text property="jcsjfx_mc" caption="分项名称" datatype="string" style="width:90%"/> 
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="代码列表"  keylist="jc_dm_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加" txncode="301013" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="301015" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="jc_dm_id" caption="代码ID" style="width:28%" visible="false"/>
      <freeze:cell property="jc_dm_dm" caption="代码" style="width:20%" />
      <freeze:cell property="jc_dm_mc" caption="代码名称" style="width:30%" />
      <freeze:cell property="jc_dm_bzly" caption="标准来源"  valueset="基础代码标准来源" style="width:30%" />
      <freeze:cell property="jc_dm_ms" caption="代码描述" style="width:30%" visible="false" />
      <freeze:cell property="operation" caption="操作" align="center" style="width:10%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
