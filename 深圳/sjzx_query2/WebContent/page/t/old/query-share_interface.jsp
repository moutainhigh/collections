<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询共享服务基础接口配置列表</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../common/top_datepicker.html"></jsp:include>
<style type="text/css">
li.disabled {cursor: default;}
.pack-up .pack-list hr.hid{display: none;}
.modal_window .pack-list hr.hid{display:block;width: 600px;
.choose_date{width: 196px; height: 1.8em; display:block;font-size: 62.5%; }
#t2 ul.pack-list li a span{display:none;}
#modal_window ul.pack-list li a span{display: none !important;}
#modal_window, #searchDiv{width:400px; }
.cont{width: 400px; height: 400px; overflow-y: auto; overflow-x: hidden; padding: 0; border: 1px solid #def; }
.albar{background: rgb(41, 140, 206) !important;margin:0px; text-align: center; line-height: 20px; height: 20px; color: white;}
.letter-list, .letter-list li{padding: 0; margin: 0; display: inline; text-align: center; color: #fff;}

.letter-list li:hover, .letter-list .on{background: rgb(102, 200, 232); cursor: default;}
.letter-list a{color: #fff; text-decoration: none; margin: 0 5px;}
.letter-list a:hover, .letter-list a:visited, .letter-list a:linked{text-decoration: underline; color: #fff;}
.cont .cont-area{ color: #036; font-weight: bold; margin-left: 5px;}
.cont .item{color:#333; padding-left:2px; cursor: pointer; height: 25px; line-height: 25px; border: 1px solid rgb(207,207,254); margin-bottom: 1px; font-size: 12px;}
.cont .item-hover{background: #cef;}
.cont .item .tnum{color: #888;}
.nodata{display: none;}
}
</style>
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
  
  var being_used = false;
  if(num && num[0]>0)
  {
	  being_used = true ;
    if(!confirm("重要提示：有服务调用此接口，修改时只允许在接口原有基础上添加表，不可删除已选数据表！点击【确定】进行修改")){
    	return;
    }
  	
  }
  var page = new pageDefine( "/txn401004.do", "修改共享服务基础接口配置","_blank" );
	var interface_id = id;
	page.addValue( interface_id, "primary-key:interface_id" );
	page.addValue( being_used, "primary-key:being_used" );
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
<freeze:title caption="查询共享服务接口列表"/>
<freeze:errors/>
<gwssi:panel action="txn40109002" target="" parts="t1,t2,t3" styleClass="wrapper">
  <gwssi:cell id="t1" name="接口状态" key="interface_state" data="itfState" />
  <gwssi:cell id="t2" name="基础接口" key="interface_id" data="itfName" isPinYinOrder="true" move2top="true" maxsize="10" />
  <gwssi:cell id="t3" name="创建时间" key="created_time" data="created_time" date="true"/>
</gwssi:panel>
<freeze:form action="/txn40109002.do">
  <freeze:frame property="select-key" >
     <freeze:hidden property="interface_state" caption="接口状态" />
     <freeze:hidden property="interface_id" caption="基础接口" />
     <freeze:hidden property="created_time" caption="创建时间" />
  </freeze:frame>
  <freeze:grid property="record" checkbox="false" caption="查询接口列表" keylist="interface_id" width="95%"   navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加接口" txncode="401003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button visible="false" name="record_deleteRecord" caption="删除接口" txncode="401005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
     <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="interface_name" caption="接口名称" style=" " />
      <freeze:cell property="table_name_cn" caption="接口配置数据表" style="width:30%;" />
      <freeze:hidden property="interface_state" caption="接口状态" valueset="资源管理_一般服务状态" align="center" style="width:8%" />
      <%-- <freeze:cell property="interface_description" caption="接口说明" style="" /> --%>
     
      <freeze:cell property="yhxm" align="center" caption="最后修改人" style="width:13%" />
       <freeze:cell property="last_time" align="center" caption="最后修改日期" style="width:13%" />
      <freeze:cell nowrap="true" property="operation" caption="操作" style="width:95px" align="center"/>
      
      <freeze:hidden property="is_markup" caption="代码表"  />
      <freeze:hidden property="creator_id" caption="创建人ID"  />
      <freeze:hidden property="interface_description" caption="接口说明"/>
      <freeze:hidden property="table_id" caption="表代码串" />
      <freeze:hidden property="interface_id" caption="接口ID"/>
      <freeze:hidden property="sql" caption="sql语句"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID"  />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
