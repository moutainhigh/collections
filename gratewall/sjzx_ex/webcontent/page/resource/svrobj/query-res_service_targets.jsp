<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<freeze:html>
<head>
<title>查询对象列表</title>
</head>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../../share/common/top_datepicker.html"></jsp:include>
<script language="javascript">

// 增加对象
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_service_targets.jsp", "增加服务对象","modal");
	page.addRecord();
}

// 修改对象
function func_record_updateRecord(idx)
{
	var svrId = getFormFieldValue("record:service_targets_id", idx);
	var page = new pageDefine( "/txn201004.do", "修改服务对象","modal");
	page.addValue( svrId, "primary-key:service_targets_id" );
	page.updateRecord();
}
//查看对象
function func_viewConfig(idx)
{
	var svrId = getFormFieldValue("record:service_targets_id", idx);
	var page = new pageDefine( "/txn201009.do", "查看服务对象","modal" );
	page.addValue( svrId, "primary-key:service_targets_id" );
	page.updateRecord();
}
// 删除对象
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn201006.do", "删除服务对象" );
	page.addParameter( "record:service_targets_id", "primary-key:service_targets_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 删除单条对象
function func_deleteRecord(idx)
{
	var page = new pageDefine( "/txn201006.do", "删除服务对象" );
	page.addValue( idx, "primary-key:service_targets_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

//校验服务对象是否被引用
function checkServiceUse(id){
	if(id==''){
		alert("未选择服务对象");
		return;
	}
	var page = new pageDefine( "/txn201011.ajax", "检查服务对象是否被引用");
	page.addValue( id, "primary-key:service_targets_id" );
	page.callAjaxService("checkCallBack");
}

function checkCallBack(errorCode, errDesc, xmlResult){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{

		var num = _getXmlNodeValue( xmlResult, "record:service_targets_id" );
		var id = _getXmlNodeValue( xmlResult, "primary-key:service_targets_id" );
		
		if(num==""||num==null){
			func_deleteRecord(id);
		}else{
			alert('该服务对象有服务在使用，不允许删除!');
			return;
		}
	}
}

// 修改启用停用状态
function func_record_changeStatus()
{
	var page = new pageDefine( "/txn201010.do", "启用/停用" );
	page.addParameter( "record:service_targets_id", "primary-key:service_targets_id" );
	page.addParameter( "record:service_status", "primary-key:service_status" );
	page.deleteRecord( "您确定要启用该服务对象吗？" );
}
function func_record_changeOneStatus(id,service_status)
{
	var page = new pageDefine( "/txn201010.do", "启用/停用" );
	//var service_status = getFormFieldValue("record:service_status");
	page.addValue( id, "primary-key:service_targets_id" );
	page.addValue( service_status, "primary-key:service_status" );
	if(service_status == 'Y') {
		if(confirm("服务对象被停用后，将不再对其提供数据服务，您确定要停用吗？")){
			page.updateRecord( "服务对象被停用后，将不再对其提供数据服务，您确定要停用吗？" );
		}
		
	} else if(service_status == 'N') {
		if(confirm("您确定要启用该服务对象吗？")){
			page.updateRecord( "您确定要启用该服务对象吗？" );
		}
		
	} else {
		if(confirm("您确定要启用该服务对象吗？")){
			page.updateRecord( "您确定要启用该服务对象吗？" );
		}
		
	}
	
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:service_targets_id");
	var service_status = document.getElementsByName("record:service_status");
	var names = document.getElementsByName("span_record:service_targets_name");
	//var service_status = getFormAllFieldValues("record:service_status");
	for(var i=0; i<ids.length; i++){
	   names[i].innerHTML = '<a href="#" title="'+names[i].innerHTML+'" onclick="func_viewConfig(\''+i+'\');">'+names[i].innerHTML+'</a>';
	   var htm ='<a href="#" title="修改" onclick="func_record_updateRecord('+i+')"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="删除" onclick="checkServiceUse(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   if(service_status[i].value=="Y"){
	   htm+='<a href="#" title="停用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+service_status[i].value+'\');"><div class="run"></div></a>';
	   }else
	   {
	   htm+='<a href="#" title="启用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+service_status[i].value+'\');"><div class="stop"></div></a>';
	   }
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询对象列表"/>
<freeze:errors/>
<gwssi:panel action="txn201001" target="" parts="t1,t2" styleClass="wrapper">
   <gwssi:cell id="t1" name="服务对象" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" pop="true"  maxsize="10" />
   <!-- <gwssi:cell id="t2" name="创建时间 " key="created_time" data="svrInterface" date="true"/>-->
 </gwssi:panel>

<freeze:form action="/txn201001">
  <freeze:frame property="select-key" >
     <freeze:hidden property="service_targets_id" caption="服务对象ID" />
     <freeze:hidden property="created_time" caption="创建时间" />
  </freeze:frame>
  <freeze:grid property="record" checkbox="false" caption="查询服务对象列表" keylist="service_targets_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="新增" txncode="201003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="service_targets_id" caption="服务对象ID"  />
      <freeze:cell property="@rowid" caption="序号" align="middle" style="width:5%"/>
      <freeze:cell property="service_targets_name" caption="服务对象名称" align="center" style="width:35%" />
      <freeze:cell property="service_targets_no" caption="服务对象代码" align="center" style="width:20%" />
      <freeze:cell property="service_targets_type" align="center" caption="服务对象类型" valueset="资源管理_服务对象类型" style="width:15%" />
    
      <freeze:hidden property="creator_name" align="center" caption="创建人"  style="width:10%"/>
      <freeze:hidden property="created_time" align="center" caption="创建日期"  style="width:10%"/>
      <freeze:cell property="last_modify_name" align="center" caption="最后修改人"  style="width:13%"/>
      <freeze:cell property="last_modify_time" align="center" caption="最后修改日期"  style="width:12%"/>
      <freeze:hidden property="ip" caption="绑定IP"  />
     
      <freeze:hidden property="service_status" align="center" caption="服务状态" style="width:15%" valueset="资源管理_一般服务状态" />
      <freeze:cell property="oper" caption="操作" align="center" style="width:125px" />
      <freeze:hidden property="is_bind_ip" caption="是否绑定IP" valueset="布尔型数"  />
      <freeze:hidden property="service_desc" caption="服务对象描述"  visible="false" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
