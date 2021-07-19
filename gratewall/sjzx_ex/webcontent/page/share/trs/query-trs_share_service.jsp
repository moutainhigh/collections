<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询trs接口列表</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<style type="text/css">
li.disabled {cursor: default;}
.pack-up .pack-list hr.hid{display: none;}
.modal_window .pack-list hr.hid{display:block;width: 600px;
.choose_date{width: 196px; height: 1.8em; display:block;font-size: 62.5%; }
}
</style>
</head>

<script language="javascript">
// 增加trs接口
function func_record_addRecord()
{
	var page = new pageDefine( "insert-trs_share_service.jsp", "增加trs接口", "modal", 1000, 800 );
	page.addRecord();
}
//测试TRS接口

function func_record_testRecord_trs(){
	var id = getFormFieldValues("record:trs_service_no");
	var trs_data_base = getFormFieldValues("record:trs_data_base");
	var page = new pageDefine( "test-user_svr_trs.jsp","服务参数测试","modal", 1000, 800);
	page.addValue( id, "svrNo" );
	page.addValue( trs_data_base, "trs_data_base" );
	page.addParameter( "record:trs_service_name", "trs_service_name" );
	page.addParameter( "record:service_targets_id", "service_targets_id" );
	page.updateRecord( );
}
// 修改trs接口
function func_record_updateRecord(idx)
{

    var svrId = getFormFieldValue("record:trs_service_id", idx);
	var page = new pageDefine( "/txn40300004.do", "修改trs接口","modal", 1000, 800);
	//var page = new pageDefine( "/txn40300004.do", "修改trs接口","modal", document.body.clientWidth, document.body.clientHeight );
	page.addValue( svrId, "primary-key:trs_service_id" );
	page.updateRecord();
}
//配置规则
function func_record_ruleRecord(idx)
{
	var svrId = getFormFieldValue("record:trs_service_id", idx);
	var page = new pageDefine( "/txn40300010.do", "配置trs规则","modal", 1000, 800);
	page.addValue( svrId, "primary-key:trs_service_id" );
	page.updateRecord();
}
//查看服务表
function func_viewConfig(idx)
{
	var svrId = getFormFieldValue("record:trs_service_id", idx);
	var page = new pageDefine( "/txn40300009.do", "查看服务表","modal", 1000, 800);
	page.addValue( svrId, "primary-key:trs_service_id" );
	page.updateRecord();
}

// 删除trs接口
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40300005.do", "删除trs接口" );
	page.addParameter( "record:trs_service_id", "primary-key:trs_service_id" );
	page.deleteRecord( "是否删除选中的记录" );
}
// 修改单条启用停用状态
function func_record_changeOneStatus(service_id,service_state)
{
	var page = new pageDefine( "/txn40300008.do", "启用/停用" );
	if(service_state==='Y'){
		service_state = 'N';
	}else{
		service_state = 'Y';
	}
	page.addValue( service_id, "primary-key:trs_service_id" );
	page.addValue( service_state, "primary-key:service_state" );
	page.deleteRecord( "是否修改服务状态" );
}
function func_deleteRecord(idx)
{
	var page = new pageDefine( "/txn40300005.do", "删除服务表" );
	page.addValue( idx, "primary-key:trs_service_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

function func_record_export(idx)
{
	
	var svrId = getFormFieldValue("record:trs_service_id", idx);
	var page = new pageDefine( "/txn40300013.do", "导出服务信息");
	page.addValue( svrId, "primary-key:trs_service_id" );
	
	//alert(getFormFieldValue("record:interface_id"));
	//alert(getFormFieldValue("record:service_targets_id"));
	//alert(getFormFieldValue("record:service_state"));
	//alert(getFormFieldValue("record:service_type"));
	
	var stateCode = getFormFieldValue("record:service_state", idx);
	var stateValue="停用";
	if(stateCode=="Y")
	stateValue="启用";
	
	page.addValue( getFormFieldValue("record:interface_id", idx), "record:interface_id" );
	page.addValue( getFormFieldValue("record:service_targets_id", idx), "record:service_targets_id" );
	page.addValue( stateValue, "record:service_state" );
	//page.addValue( getFormFieldValue("record:service_type", idx), "record:service_type" );
	page.addValue( "TrsService", "record:service_type" );
	page.goPage();
}
//查看服务对象
function func_viewFwdx(idx) {
	
	var svrId = getFormFieldValue("record:service_targets_id1", idx);
	var page = new pageDefine( "/txn201009.do", "查看服务对象","modal" );
	page.addValue( svrId, "primary-key:service_targets_id" );
	page.updateRecord();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

	var ids = getFormAllFieldValues("record:trs_service_id");
	var service_state = document.getElementsByName("record:service_state");
	for(var i=0; i<ids.length; i++){  
	   var htm = '<a href="#" title="修改" onclick="func_record_updateRecord('+i+')"><div class="edit"></div></a>&nbsp;';
	   
	   htm += '<a href="#" title="删除" onclick="func_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   if(service_state[i].value=="Y"){
	   htm+='<a href="#" title="点击停用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+service_state[i].value+'\');"><div class="run"></div></a>';
	   }else
	   {
	   htm+='<a href="#" title="点击启用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+service_state[i].value+'\');"><div class="stop"></div></a>';
	   }
	   htm += '<a href="#" title="配置规则" onclick="func_record_ruleRecord('+i+')"><div class="config"></div></a>&nbsp;';
	   htm+='<a href="#" title="下载" onclick="func_record_export('+i+');"><div class="download"></div></a>&nbsp;&nbsp;&nbsp;';
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	var names = getFormAllFieldValues("record:trs_service_name");
	var targetnames = getFormAllFieldValues("record:service_targets_id");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="点击查看详细信息" onclick="func_viewConfig(\''+i+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:trs_service_name")[i].innerHTML =htm;
	   
	   var htm2 = '<a href="#" title="点击查看详细信息" onclick="func_viewFwdx(\''
			+ i + '\');">' + targetnames[i] + '</a>';
		document.getElementsByName("span_record:service_targets_id")[i].innerHTML = htm2;
	   
	}
	
	var date_s = document.getElementsByName("span_record:created_time");
	for(var ii=0; ii<date_s.length; ii++){
		date_s[ii].innerHTML = date_s[ii].innerHTML.substr(0,10);
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>
<freeze:title caption="查询对象列表"/>
<gwssi:panel action="txn40300001" target="" parts="t2,t1,t3" styleClass="wrapper">
  <gwssi:cell id="t2" name="服务对象" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" maxsize="10" />
  <gwssi:cell id="t1" name="服务状态" key="service_state" data="svrState" />
  <gwssi:cell id="t3" name="创建日期" key="created_time" data="svrInterface" date="true"/>
 </gwssi:panel>
<freeze:form action="/txn40300001">
  <freeze:frame property="select-key" >
     <freeze:hidden property="service_targets_id" caption="服务对象" />
     <freeze:hidden property="service_state" caption="服务状态" />
     <freeze:hidden property="created_time" caption="创建日期" />
  </freeze:frame>
  <freeze:grid property="record" checkbox="false" multiselect="false" fixrow='false' caption="查询trs接口列表" keylist="trs_service_id" width="95%" navbar="bottom" >
      <freeze:button name="record_addRecord" caption="增加trs接口" txncode="40300003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord()"/>
      <freeze:button name="record_testRecord_new" caption="trs接口"  enablerule="0" enablerule="2" hotkey="ADD" align="right" onclick="func_record_testRecord_trs()" visible="false"/>
      
      <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
   	 <freeze:hidden property="service_targets_id1" caption="服务对象" />
      <freeze:cell property="service_targets_id" caption="服务对象" valueset="资源管理_服务对象名称" style="width:20%; text-align:center;" />
      <freeze:cell property="trs_service_name" caption="服务名称" style="width:20%" />
      <freeze:cell property="trs_service_no" caption="服务编号" align="center"  style="width:20%" />
      <freeze:cell property="yhxm" caption="最后修改人" align="center"  style="width:20%" />
       <freeze:cell property="lasttime" caption="最后修改日期" align="center"  style="width:20%" />
      <freeze:cell property="oper" nowrap="true" caption="操作" align="center" style="width:75px;" />
      
      <freeze:hidden property="trs_service_id" caption="服务ID" style="width:10%" visible="false"/>
      <freeze:hidden property="trs_data_base" caption="服务库" style="width:20%" visible="false" />
      <freeze:hidden property="trs_column" caption="展示字段" style="width:20%" visible="false" />
      <freeze:hidden property="trs_search_column" caption="检索字段" style="width:20%" visible="false" />
      <freeze:hidden property="service_state" caption="是否启用" style="width:11%" />
      <freeze:hidden property="is_markup" caption="是否有效" style="width:9%" />
      <freeze:hidden property="creator_id" caption="创建人ID" style="width:11%" />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="最后修改时间" style="width:12%" />
      <freeze:hidden property="service_description" caption="服务说明" style="width:20%" visible="false" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
