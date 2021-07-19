<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询采集任务列表</title>
</head>

<script language="javascript">

// 增加采集任务
function func_record_addRecord()
{
    //var page = new pageDefine( "/txn30101007.do", "增加采集任务");
    //page.addValue(" ","primary-key:collect_task_id");
    var page = new pageDefine( "/page/collect/config/insert-collect_task_tab.jsp", "增加采集任务");
	page.addRecord();
	
}

// 修改采集任务
function func_record_updateRecord(idx,type,status,collect_status)
{
	
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101004.do", "修改采集任务webservice" );
		page.addValue(idx,"primary-key:collect_task_id");
		page.updateRecord();
	}else if(type=="SOCKET"){
			var page = new pageDefine( "/txn30101052.do", "修改采集任务socket" );
			page.addValue(idx,"primary-key:collect_task_id");
			page.updateRecord();
	}else if(type=="JMS消息"){
			var page = new pageDefine( "/txn30101062.do", "修改采集任务jms" );
			page.addValue(idx,"primary-key:collect_task_id");
			page.updateRecord();
	}else if(type=="FTP"){
			var page = new pageDefine( "/txn30101013.do", "修改采集任务ftp" );
			page.addValue(idx,"primary-key:collect_task_id");
			page.updateRecord();
	}else if(type=="文件上传"){
		if(status=="N"){
			alert("停用的服务不允许进行修改操作!");
		}else if(status=="Y"){
			//if(collect_status!="未采集"){
				//alert("已完成的采集任务不允许对其进行修改!");
			//}else{
				
				var page = new pageDefine( "/txn30101022.do", "修改采集任务文件上传" );
				page.addValue(idx,"primary-key:collect_task_id");
				page.updateRecord();
			//}
			
		}	
	}else if(type=="数据库"){
		var page = new pageDefine( "/txn30101034.do", "修改采集任务数据库");
		page.addValue(idx,"primary-key:collect_task_id");
		page.updateRecord();
	}else {
		alert("正在努力建中……");
	}
	
}

// 删除采集任务
function func_record_deleteRecord(idx,type)
{
	if(type=="WebService"||type=="FTP"||type=="SOCKET"||type=="JMS消息"){
		var page = new pageDefine( "/txn30101005.do", "删除采集任务" );
		page.addValue(idx,"primary-key:collect_task_id" );
		page.deleteRecord( "是否删除选中的记录" );
	}else if(type=="文件上传"){
		var page = new pageDefine( "/txn30101024.do", "删除采集任务" );
		page.addValue(idx,"primary-key:collect_task_id" );
		page.deleteRecord( "是否删除选中的记录" );
	}else if(type=="数据库"){
		var page = new pageDefine( "/txn30101036.do", "删除采集任务" );
		page.addValue(idx,"primary-key:collect_task_id" );
		page.deleteRecord( "是否删除选中的记录" );
	}else {
		alert("正在努力建中……");
	}
}
//查看采集数据表信息
function func_record_viewRecord(idx,type)
{
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101006.do", "查看采集任务" );
		page.addValue(idx, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="SOCKET"||type=="JMS消息"){
	
		var page = new pageDefine( "/txn30101016.do", "查看采集任务" );
		page.addValue(idx, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="FTP"){
		var page = new pageDefine( "/txn30101015.do", "查看采集任务" );
		page.addValue(idx, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="文件上传"){
		alert("正在努力建中……");
	}else {
		alert("正在努力建中……");
	}
}
// 查看采集服务-页面按钮展示
function func_record_viewRecord1(index,type)
{	

	var gridname = getGridDefine("record");
	var id = gridname.getAllFieldValues( "collect_task_id" )[index];
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101006.do", "查看采集任务" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="SOCKET"||type=="JMS消息"){
		var page = new pageDefine( "/txn30101016.do", "查看采集任务" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="FTP"){
		var page = new pageDefine( "/txn30101015.do", "查看采集任务" );
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="文件上传"){
		var page = new pageDefine( "/txn30101025.do", "查看采集任务" );
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
		
	}else if(type=="数据库"){
		var page = new pageDefine( "/txn30101035.do", "查看采集任务" );
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
		
	}else {
		alert("正在努力建中……");
	}
}
//获取数据
function func_record_getData(idx,type)
{
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101008.ajax", "获取数据");
	 	page.addValue(idx,"primary-key:collect_task_id");
	 	page.addValue(type,"primary-key:collect_type");
		page.callAjaxService('getData');
	}else if(type=="FTP"){
		var page = new pageDefine( "/txn30101008.ajax", "获取数据");
	 	page.addValue(idx,"primary-key:collect_task_id");
	 	page.addValue(type,"primary-key:collect_type");
		page.callAjaxService('getData');
	}else if(type=="文件上传"){
		alert("正在努力建中……");
	}else {
		alert("正在努力建中……");
	}
}
function getData(errCode,errDesc,xmlResults){
		
		
		    alert("获取数据成功!");
}
function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:service_targets_id=' + getFormFieldValue('record:service_targets_id');
	return parameter;
}
function func_record_changeOneStatus(id,task_status,type)
{
	if(type=="WebService"||type=="SOCKET"||type=="JMS消息"||type=="FTP"||type=="文件上传"||type=="数据库"){
		var page = new pageDefine( "/txn30101010.do", "启用/停用" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.addValue( task_status, "primary-key:task_status" );
		page.deleteRecord( "是否修改服务状态" );
	//}else if(type=="文件上传"){
		//alert("正在努力建中……");
	}else {
		alert("正在努力建中……");
	}
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

	var ids = getFormAllFieldValues("record:collect_task_id");
	var data_source_id=getFormAllFieldValues("record:data_source_id");
	var task_status = getFormAllFieldValues("record:task_status");
	var type = getFormAllFieldValues("record:collect_type");
	var collect_status = getFormAllFieldValues("record:collect_status");
	
	var log_file_path =  getFormAllFieldValues("record:log_file_path");
	
	for(var i=0; i<ids.length; i++){
	   //var htm='<a href="#" title="查看" onclick="func_record_viewRecord(\''+ids[i]+'\',\''+type[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   var htm='<a href="#" title="修改" onclick="func_record_updateRecord(\''+ids[i]+'\',\''+type[i]+'\',\''+task_status[i]+'\',\''+collect_status[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="删除" onclick="func_record_deleteRecord(\''+ids[i]+'\',\''+type[i]+'\');"><div class="delete"></div></a>&nbsp;&nbsp;';
	   if(task_status[i]=="Y"){
	   htm+='<a href="#" title="停用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+task_status[i]+'\',\''+type[i]+'\');"><div class="run"></div></a>&nbsp;';
	   }else
	   {
	   htm+='<a href="#" title="启用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+task_status[i]+'\',\''+type[i]+'\');"><div class="stop"></div></a>&nbsp;';
	   }
	   
	   if(type[i]=="文件上传"){
	   
	   }  
	   //htm+='<a href="#" title="获取数据" onclick="func_record_getData(\''+ids[i]+'\',\''+type[i]+'\',\''+data_source_id[i]+'\');"><div class="download"></div></a>';
	   
	   //采集日志  add by dwn 20130625
	   var filepath = log_file_path[i];
		var num = filepath.lastIndexOf("/");
		var filename = filepath.substring(num+1);
		var url = "/downloadFile?file="+filepath+"&&fileName="+filename;
	   
	   var colSta_htm="";
	   if(collect_status[i]=="采集成功"){
	   		colSta_htm='<a href="'+url+'" title="下载日志文件" onclick=""><div class="collect_normal"></div></a>&nbsp;';
	   }else if(collect_status[i]=="采集失败"){
	   		colSta_htm='<a href="'+url+'" title="下载日志文件" onclick=""><div class="collect_failed"></div></a>&nbsp;';
	   }
	   document.getElementsByName("span_record:collect_status")[i].innerHTML =collect_status[i]+colSta_htm;
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	 
	 var names = getFormAllFieldValues("record:task_name");
	 for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="点击查看详细信息" onclick="func_record_viewRecord1(\''+i+'\',\''+type[i]+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:task_name")[i].innerHTML =htm;
	}
	
	
	
}

_browse.execute( '__userInitPage()' );

</script>
<freeze:body >
<freeze:title caption="查询采集任务列表"/>
<freeze:errors/>

<freeze:form action="/txn30101001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>
<freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" valueset="资源管理_采集任务服务对象名称"  style="width:95%"/>
      <freeze:text property="task_name" caption="任务名称"   style="width:95%"/>
      <freeze:select property="collect_type" caption="采集类型"  show="name" valueset="采集任务_采集类型"  style="width:95%"/>
      <freeze:select property="collect_status" caption="采集状态"  show="name" valueset="采集任务_采集状态"  style="width:95%"/>
      <freeze:select property="task_status" caption="任务状态" valueset="资源管理_一般服务状态" show="name" style="width:95%"/>
      <freeze:datebox property="created_time_start" caption="创建日期" prefix="<table width='95%' border='0' cellpadding='0' cellspacing='0'><tr><td width='47.5%'>" style="width:100%"/></td>
      <td width='5%'>至</td><td width='47.5%'>
      <freeze:datebox property="created_time_end" caption="创建日期" style="width:100%" colspan="0"/>
      </td></tr></table>
  </freeze:block>
<br/>
  <freeze:grid property="record" caption="查询采集任务列表" keylist="collect_task_id" width="95%"  multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加采集任务" txncode="30101003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
     
      <freeze:hidden property="collect_task_id" caption="采集任务ID" />
      <freeze:cell property="@rowid" caption="序号"  style="width:6%" align="center" />
      <freeze:cell property="service_targets_id" caption="服务对象" show="name" valueset="资源管理_服务对象所有名称" style="width:10%; text-align:center;" />
      <freeze:hidden property="data_source_id" caption="数据源" style="width:18%"/>
       <freeze:cell property="collect_type" caption="采集类型"  show="name" valueset="采集任务_采集类型" style="width:10%" />
      <freeze:cell property="task_name" caption="任务名称" style="width:20%"/>
      <freeze:hidden property="created_time" caption="创建时间" style="width:12%" />
      
      <freeze:cell property="scheduling_day1" caption="采集周期" style="width:30%"/>
      <freeze:cell property="start_time" caption="开始时间" style="width:10%"/>
      
      <freeze:cell property="oper" caption="操作" style="width:12%; text-align: center;" />
      
      <freeze:hidden property="task_description" caption="任务描述"   />
      <freeze:hidden property="record" caption="备案说明"   />
      <freeze:hidden property="task_status" caption="任务状态"  style="width:5%"  />
      <freeze:hidden property="is_markup" caption="有效标记"  />
      <freeze:hidden property="creator_id" caption="创建人ID"  />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" />
      <freeze:hidden property="last_modify_time" caption="最后修改时间" />
      <freeze:hidden property="log_file_path" caption="日志文件路径" />
  </freeze:grid>

</freeze:form>

</freeze:body>
</freeze:html>
