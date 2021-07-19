<%@page import="com.gwssi.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询采集数据表列表</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../../../share/common/top_datepicker.html"></jsp:include>
<style type="text/css">
li.disabled {cursor: default;}
.pack-up .pack-list hr.hid{display: none;}
.modal_window .pack-list hr.hid{display:block;width: 600px;
.choose_date{width: 196px; height: 1.8em; display:block;font-size: 62.5%; }
}
</style>
</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<script language="javascript">

// 增加采集数据表信息
function func_record_addRecord()
{

    var page = new pageDefine( "/txn20201007.do", "新增采集数据表信息", "modal");
    page.addValue(" ","primary-key:collect_table_id");
	page.addRecord();
}

// 查询采集数据表信息 用于修改
function func_record_updateRecord(idx,ly,ifCreat)
{
	var page;
	if(ly!=null&&ly=='<%=CollectConstants.TYPE_CJLY_OUT_NAME%>'&&ifCreat=='<%=CollectConstants.TYPE_IF_CREAT_NO%>'){
		 page = new pageDefine( "/txn20201004.do", "修改采集数据表信息","modal", "1000", document.clientHeight);
		 page.addValue(idx,"primary-key:collect_table_id");
		 page.updateRecord();
	}else if(ly!=null&&ly=='<%=CollectConstants.TYPE_CJLY_OUT_NAME%>'&&ifCreat=='<%=CollectConstants.TYPE_IF_CREAT_YES%>'){
		//alert("数据表已生成,不可以修改!");
	    //return;
	     page = new pageDefine( "/txn20201004.do", "修改采集数据表信息","modal", "1000", document.clientHeight);
		 page.addValue(idx,"primary-key:collect_table_id");
		 page.updateRecord();
	}
	else{
	    alert("内部采集库对象不允许操作!");
	    return;
	}
	
}
function checkCollectTableUse(id,ly){
	if(id==''){
		alert("未选择采集数据表");
		return;
	}
	if(ly!=null&&ly=='<%=CollectConstants.TYPE_CJLY_IN_NAME%>'){
		alert('内部采集库对象不允许删除！');
		return;
	}
	
	var page = new pageDefine( "/txn20201011.ajax", "检查采集数据表是否被引用");
	page.addValue(id, "primary-key:collect_table_id" );
	page.callAjaxService("checkNameUsed");
}

function checkNameUsed(errorCode, errDesc, xmlResult){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var num = _getXmlNodeValue(xmlResult, "record:collect_table_id");
		var id = _getXmlNodeValue(xmlResult, "primary-key:collect_table_id" );
		
		if(num==""||num==null){
			func_record_deleteRecord(id);
		}else{
			alert('采集任务已经使用此采集数据表,请不要删除!');
			return;
		}
	}
}

// 删除采集数据表信息
function func_record_deleteRecord(idx)
{
		var page = new pageDefine( "/txn20201005.do", "删除采集数据表信息" );
		page.addValue(idx,"primary-key:collect_table_id");
		page.deleteRecord( "是否删除选中的记录" );
}
//查看采集数据表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn20201006.do", "查看采集数据表信息", "modal" );
	page.addValue(idx, "primary-key:collect_table_id" );
	page.updateRecord();
}
//查看采集数据表信息
function importExcel()
{
	//var page = new pageDefine( "/txn20201010.do", "导入excel" );
	//page.updateRecord();
	var page = new pageDefine( "importExcel-res_collect_table.jsp", "导入excel");
	page.updateRecord();
}
//同步表结构
function synchroTable()
{
	 if(confirm("是否同步内部系统表结构?")){
	 	clickFlag=1;
		var page = new pageDefine( "/txn20201012.ajax", "同步表结构");
		_showProcessHintWindow("正在同步内部系统表结构,请稍候...");
		page.callAjaxService("systableCallBack");
		//page.updateRecord();
	 }else{
		 clickFlag=0;
	 }
	 
}

function systableCallBack(errorCode, errDesc, xmlResults) {

		if (errorCode != "000000") {
			_hideProcessHintWindow();
			alert("同步内部系统表结构失败!");
		} else {
			//var result = _getXmlNodeValue(xmlResults, "record:result");
			
			_hideProcessHintWindow();
			alert("同步内部系统表结构成功!");
			//_showProcessHintWindow("<span style='color:green;'>连接成功!</span><br/><a href='javascript:_hideProcessHintWindow();'>关闭</a>");
			window.location.href = window.location.href;
		}
	}
// 查看采集数据表信息
function func_record_viewRecord1(index)
{	
	var gridname = getGridDefine("record");
	var id = gridname.getAllFieldValues( "collect_table_id" )[index];
	var page = new pageDefine( "/txn20201006.do", "查看采集数据表信息","_blank");
	page.addValue( id, "primary-key:collect_table_id" );
	page.updateRecord();
}
//查看服务对象
function func_viewConfig(idx) {
	
	var svrId = getFormFieldValue("record:service_targets_id1", idx);
	var page = new pageDefine( "/txn201009.do", "查看服务对象","modal" );
	page.addValue( svrId, "primary-key:service_targets_id" );
	page.updateRecord();
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:collect_table_id");
	var ly=getFormAllFieldValues("record:cj_ly");//采集来源
	
	var if_creat=getFormAllFieldValues("record:if_creat");//是否已生成
	for(var i=0; i<ids.length; i++){
	  // var htm='<a href="#" title="查看" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	  
	   var htm='<a href="#" title="修改" onclick="func_record_updateRecord(\''+ids[i]+'\',\''+ly[i]+'\',\''+if_creat[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="删除" onclick="checkCollectTableUse(\''+ids[i]+'\',\''+ly[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	 
	var names = getFormAllFieldValues("record:table_name_en");
	var targetnames = getFormAllFieldValues("record:service_targets_id");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="点击查看详细信息" onclick="func_record_viewRecord1(\''+i+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:table_name_en")[i].innerHTML =htm;
	  
	   if(ly[i]!=null&&ly[i]=='外部采集库'){
		   var htm2 = '<a href="#" title="点击查看详细信息" onclick="func_viewConfig(\''
				+ i + '\');">' + targetnames[i] + '</a>';
			document.getElementsByName("span_record:service_targets_id")[i].innerHTML = htm2;
		   
	   }
	   
	 
	   
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询采集数据表列表"/>
<freeze:errors/>
<gwssi:panel action="txn20209001" target="" parts="t1,t2" styleClass="wrapper">
  <gwssi:cell id="t1" name="采集来源" key="cj_ly" data="colSource" />
  <gwssi:cell id="t2" name="服务对象" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget"  maxsize="10" />
  <%-- <gwssi:cell id="t3" name="表的类型" key="table_type" data="tabType" />
  <gwssi:cell id="t4" name="生成状态" key="if_creat" data="crtState" />  --%>
  <%-- <gwssi:cell id="t5" name="创建时间" key="created_time" data="created_time" date="true"/> --%>
</gwssi:panel>
<freeze:form action="/txn20209001">
  <freeze:frame property="select-key" >
     <freeze:hidden property="cj_ly" caption="采集来源" />
     <freeze:hidden property="service_targets_id" caption="服务对象ID" />
    <%--  <freeze:hidden property="table_type" caption="表的类型" />
     <freeze:hidden property="if_creat" caption="生成状态" />
     <freeze:hidden property="created_time" caption="创建时间" /> --%>
  </freeze:frame>
  <freeze:grid property="record" caption="查询采集数据表列表"  keylist="collect_table_id" width="95%"  multiselect="false" checkbox="false" navbar="bottom" >
      <freeze:button name="record_addRecord" caption="新建采集表" txncode="20201003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_importRecord" caption="导入表结构" txncode="20201010" enablerule="0" hotkey="UPDATE" align="right" onclick="importExcel();"/>
      <freeze:button name="record_syncRecord" caption="同步表结构" txncode="20201005" enablerule="0" hotkey="DELETE" align="right" onclick="synchroTable()"/>
      <freeze:hidden property="collect_table_id" caption="采集数据表ID"  />
       <freeze:hidden property="service_targets_id1" caption="服务对象ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width: 40px" align="center" />
      <freeze:cell property="cj_ly" caption="采集来源" show="name" valueset="资源管理_采集来源" style="width:20%" align="center"/>
      <freeze:cell  property="service_targets_id" caption="服务对象" show="name" valueset="资源管理_服务对象所有名称" style="text-align:center; width:15%" />
      <freeze:cell property="table_name_cn" caption="表中文名" style="width:15%" />
      <freeze:cell property="table_name_en" caption="表英文名" style="width:20%"/>
      <%-- <freeze:cell property="table_type" caption="表类型" show="name" valueset="资源管理_表类型" style="width:7%" /> --%>
      <freeze:hidden property="if_creat" caption="生成采集表状态" style="width:13%" /> 
      <freeze:hidden property="table_desc" caption="表描述"  visible="false" />
      <freeze:hidden property="table_status" caption="表状态" />
      <freeze:hidden property="is_markup" caption="有效标记"  />
     
     
      <freeze:cell property="name" align="center" caption="最后修改人"  style="width:10%"/>
	  <freeze:cell property="time" align="center" caption="最后修改日期"  style="width:10%"/>
	  <freeze:hidden property="last_modify_name" align="center" caption="最后修改人"  style="width:13%"/>
	  <freeze:hidden property="last_modify_time" align="center" caption="最后修改日期"  style="width:12%"/>
      <freeze:cell property="oper" caption="操作" align="center"  style="width:10%" />
    
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
