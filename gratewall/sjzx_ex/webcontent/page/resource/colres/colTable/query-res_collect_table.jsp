<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询采集数据表列表</title>
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
	if(ly!=null&&ly=='<%=CollectConstants.TYPE_CJLY_OUT_NAME%>'&&ifCreat=='<%=CollectConstants.TYPE_IF_CREAT_NO_NAME%>'){
		 page = new pageDefine( "/txn20201004.do", "修改采集数据表信息","modal", "1000", document.clientHeight);
		 page.addValue(idx,"primary-key:collect_table_id");
		 page.updateRecord();
	}else if(ly!=null&&ly=='<%=CollectConstants.TYPE_CJLY_OUT_NAME%>'&&ifCreat=='<%=CollectConstants.TYPE_IF_CREAT_YES_NAME%>'){
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
	 	//clickFlag=1;
		var page = new pageDefine( "/txn20201012.ajax", "同步表结构");
		_showProcessHintWindow("正在同步内部系统表结构,请稍候...");
		page.callAjaxService("systableCallBack");
		//page.updateRecord();
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
			window.location.href="/txn20201001.do";
		}
	}
// 查看采集数据表信息
function func_record_viewRecord1(index)
{	
	var gridname = getGridDefine("record");
	var id = gridname.getAllFieldValues( "collect_table_id" )[index];
	var page = new pageDefine( "/txn20201006.do", "查看采集数据表信息","modal");
	page.addValue( id, "primary-key:collect_table_id" );
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
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="点击查看详细信息" onclick="func_record_viewRecord1(\''+i+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:table_name_en")[i].innerHTML =htm;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询采集数据表列表"/>
<freeze:errors/>

<freeze:form action="/txn20201001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="cj_ly" caption="采集来源"  show="name" valueset="资源管理_采集来源"  style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" valueset="资源管理_服务对象名称"  style="width:95%"/>
      <freeze:select property="table_type" caption="表类型"  show="name" valueset="资源管理_表类型"  style="width:95%"/>
      <freeze:select property="if_creat" caption="生成采集表状态"  show="name" valueset="资源管理_生成状态"  style="width:95%"/>
      <freeze:text property="table_name_en" caption="表名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="表中文名称" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
<br/>
  <freeze:grid property="record" caption="查询采集数据表列表" keylist="collect_table_id" width="95%" multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="新建采集表" txncode="20201003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_importRecord" caption="导入表结构" txncode="20201010" enablerule="0" hotkey="UPDATE" align="right" onclick="importExcel();"/>
      <freeze:button name="record_syncRecord" caption="同步表结构" txncode="20201005" enablerule="0" hotkey="DELETE" align="right" onclick="synchroTable()"/>
      <freeze:hidden property="collect_table_id" caption="采集数据表ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:4%" align="center" />
      <freeze:cell property="cj_ly" caption="采集来源" show="name" valueset="资源管理_采集来源" style="width:10%" />
      <freeze:cell property="service_targets_id" caption="所属服务对象" show="name" valueset="资源管理_服务对象所有名称" style="width:14%" />
      <freeze:cell property="table_name_en" caption="表名称" style="width:17%"/>
      <freeze:cell property="table_name_cn" caption="表中文名称" style="width:15%" />
      <freeze:cell property="table_type" caption="表类型" show="name" valueset="资源管理_表类型" style="width:7%" />
      <freeze:cell property="if_creat" caption="生成采集表状态" show="name" valueset="资源管理_生成状态" style="width:13%" />
      <freeze:cell property="table_desc" caption="表描述"  visible="false" />
      <freeze:hidden property="table_status" caption="表状态" />
      <freeze:hidden property="is_markup" caption="有效标记"  />
      <freeze:hidden property="creator_id" caption="创建人ID"  />
      <freeze:cell property="created_time" caption="创建时间"  style="width:10%"/>
      <freeze:cell property="oper" caption="操作" align="center"  style="width:10%" />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID"  />
      <freeze:hidden property="last_modify_time" caption="最后修改时间" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
