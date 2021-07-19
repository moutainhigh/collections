<%@page import="com.gwssi.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询技术规则列表</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../share/common/top_datepicker.html"></jsp:include>
<style type="text/css">
li.disabled {cursor: default;}
.pack-up .pack-list hr.hid{display: none;}
.modal_window .pack-list hr.hid{display:block;width: 600px;
.choose_date{width: 196px; height: 1.8em; display:block;font-size: 62.5%; }
}
</style>
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
<freeze:title caption="查询技术标准列表"/>
<freeze:errors/>
<gwssi:panel action="txn603904" target="" parts="t1,t2" styleClass="wrapper">
  <gwssi:cell id="t1" name="技术规范" key="standard_id" data="staName" pop="true" move2top="true" maxsize="10" />
  <gwssi:cell id="t2" name="发布日期" key="enable_time" data="enable_time" date="true"/>
</gwssi:panel>
<freeze:form action="/txn603904">

  <freeze:frame property="select-key" >
     <freeze:hidden property="standard_id" caption="数据规范" />
     <freeze:hidden property="enable_time" caption="启用时间" />
  </freeze:frame>
  <freeze:grid property="record"   checkbox="false" caption="查询规则列表" keylist="standard_id" width="95%" navbar="bottom" >
      <freeze:button name="record_addRecord" caption="增加" txncode="603008" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="standard_id" caption="标准ID" style="width:10%" visible="false"/>
      <freeze:cell property="@rowid" align="center" caption="序号" style="width:5%" />
      <freeze:cell property="standard_name" align="center" caption="技术规范名称" style="width:30%" />
      <freeze:cell property="enable_time" align="center" caption="发布日期" style="width:18%" />
      <freeze:cell property="fjmc"  caption="文件名称" align="center" style="" />
      <%-- <freeze:cell property="specificate_desc"  caption="备注" align="center" style="width:20%"  /> --%>
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
