<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询共享服务基础接口配置列表</title>
</head>

<script language="javascript">
var deleteID;
// 增加共享服务基础接口配置
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_interface.jsp", "增加共享服务基础接口配置","modal");
	page.addRecord();
}

// 修改共享服务基础接口配置
function func_record_updateRecord(id)
{
	var page = new pageDefine( "/txn401004.do", "修改共享服务基础接口配置","modal" );
	page.addValue( id, "primary-key:interface_id" );
	page.updateRecord();
}

// 查看共享服务基础接口配置
function func_record_viewRecord(id)
{
	var page = new pageDefine( "/txn401010.do", "查看共享服务基础接口配置","modal" );
	page.addValue( id, "primary-key:interface_id" );
	page.updateRecord();
}
// 删除共享服务基础接口配置
function func_record_deleteRecord(id)
{
	var page = new pageDefine( "/txn401005.do", "删除共享基础接口" );
	page.addParameter( "record:interface_id", "primary-key:interface_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

function deleteCallback(errCode, errDesc, xmlResults)
{
  if (errCode != '000000') {
    //alert('处理错误['+errCode+']==>'+errDesc);
    return;
  }
  var id = _getXmlNodeValues(xmlResults, "record:interface_id");
  var num = _getXmlNodeValues(xmlResults, "record:interfaceusednum");
  if(num && num[0]>0)
  {
    alert("有服务调用此接口，不能删除！");
  	return;
  }
	var page = new pageDefine( "/txn401011.do", "删除共享基础接口" );
	var interface_id = id;
	page.addValue(interface_id, "primary-key:interface_id" );
	page.deleteRecord( "是否删除选中的记录" );
}
function updateCallback(errCode, errDesc, xmlResults)
{
  if (errCode != '000000') {
    //alert('处理错误['+errCode+']==>'+errDesc);
    return;
  }
  var id = _getXmlNodeValues(xmlResults, "record:interface_id");
  var num = _getXmlNodeValues(xmlResults, "record:interfaceusednum");
  if(num && num[0]>0)
  {
    alert("有服务调用此接口，不能修改！");
  	return;
  }
    var page = new pageDefine( "/txn401004.do", "修改共享服务基础接口配置","modal" );
	var interface_id = id;
	page.addValue( interface_id, "primary-key:interface_id" );
	page.updateRecord();
}
// 删除共享服务基础接口配置
function func_deleteRecord(id)
{
  var page = new pageDefine("/txn401012.ajax", "接口ID查询调用接口的服务数量");
  var interface_id = id;
  deleteID = id;
  if (interface_id && interface_id != '') {
    page.addValue(interface_id, "select-key:interface_id");
    page.callAjaxService('deleteCallback');
  }
}
//删除共享服务基础接口配置
function func_updateRecord(id)
{
  var page = new pageDefine("/txn401012.ajax", "接口ID查询调用接口的服务数量");
  var interface_id = id;
  if (interface_id && interface_id != '') {
    page.addValue(interface_id, "select-key:interface_id");
    page.callAjaxService('updateCallback');
  }
}
// 修改单条启用停用状态
function func_record_changeOneStatus(interface_id,interface_state)
{
	var page = new pageDefine( "/txn401013.do", "启用/停用" );
	if(interface_state === 'Y'){
		interface_state = 'N';
	}else{
		interface_state = 'Y';
	}
	page.addValue( interface_id, "primary-key:interface_id" );
	page.addValue( interface_state, "primary-key:interface_state" );
	page.deleteRecord( "是否修改服务基础接口状态" );
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:interface_id");
	var interface_state = document.getElementsByName("record:interface_state");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="修改" onclick="func_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="删除" onclick="func_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>&nbsp;'; 
	   
	   if(interface_state[i].value=="Y"){
	   htm+='<a href="#" title="点击停用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+interface_state[i].value+'\');"><div class="run"></div></a>';
	   }else
	   {
	   htm+='<a href="#" title="点击启用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+interface_state[i].value+'\');"><div class="stop"></div></a>';
	   }
	   document.getElementsByName("span_record:operation")[i].innerHTML +=htm;
	}
	
	var names = getFormAllFieldValues("record:interface_name");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="点击查看详细信息" onclick="func_record_viewRecord(\''+ids[i]+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:interface_name")[i].innerHTML =htm;
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

<freeze:form action="/txn40109002">
  <freeze:grid property="record" checkbox="false" caption="查询接口列表" keylist="interface_id" width="100%"   navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加接口" txncode="401003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button visible="false" name="record_deleteRecord" caption="删除接口" txncode="401005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
     <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="interface_name" caption="接口名称" style="width:20%" />
      <freeze:cell property="table_name_cn" caption="接口配置数据表" style="width:30%;" />
      <freeze:hidden property="interface_state" caption="接口状态" valueset="资源管理_一般服务状态" align="center" style="width:8%" />
      <freeze:cell property="interface_description" caption="接口说明" style="" />
      <freeze:cell property="created_time" align="center" caption="创建时间" style="width:12%" />
      <freeze:cell nowrap="true" property="operation" caption="操作" style="width:95px" align="center"/>
      
      <freeze:hidden property="is_markup" caption="代码表"  />
      <freeze:hidden property="creator_id" caption="创建人ID"  />
      <freeze:hidden property="interface_description" caption="接口说明"/>
      <freeze:hidden property="table_id" caption="表代码串" />
      <freeze:hidden property="interface_id" caption="接口ID"/>
      <freeze:hidden property="sql" caption="sql语句"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID"  />
      <freeze:hidden property="last_modify_time" caption="最后修改时间"  />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
