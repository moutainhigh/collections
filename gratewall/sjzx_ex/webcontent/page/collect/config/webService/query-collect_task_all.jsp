<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询采集任务列表</title>
</head>

<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<script type="text/javascript">

// 增加采集任务
function func_record_addRecord()
{
    //var page = new pageDefine( "/txn30101007.do", "增加采集任务");
    //page.addValue(" ","primary-key:collect_task_id");
    //跳转到新增采集任务的标签页
    var page = new pageDefine( "/page/collect/config/insert-collect_task_tab.jsp", "增加采集任务");
	page.addRecord();
	
}

// 修改采集任务
function func_record_updateRecord(idx,type,status,collect_status)
{
	
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101004.do", "修改采集任务webservice" );
		page.addValue(idx,"primary-key:collect_task_id");
		//page.updateRecord();
	}else if(type=="SOCKET"){
			var page = new pageDefine( "/txn30101052.do", "修改采集任务socket" );
			page.addValue(idx,"primary-key:collect_task_id");
			//page.updateRecord();
	}else if(type=="JMS消息"){
			var page = new pageDefine( "/txn30101062.do", "修改采集任务jms" );
			page.addValue(idx,"primary-key:collect_task_id");
			//page.updateRecord();
	}else if(type=="FTP"){
			var page = new pageDefine( "/txn30101013.do", "修改采集任务ftp" );
			page.addValue(idx,"primary-key:collect_task_id");
			//page.updateRecord();
	}else if(type=="文件上传"){
		if(status=="N"){
			alert("停用的服务不允许进行修改操作!");
			return;
		}else if(status=="Y"){
			//if(collect_status!="未采集"){
				//alert("已完成的采集任务不允许对其进行修改!");
			//}else{
				
				var page = new pageDefine( "/txn30101022.do", "修改采集任务文件上传" );
				page.addValue(idx,"primary-key:collect_task_id");
				//page.updateRecord();
			//}
			
		}	
	}else if(type=="数据库"){
		//var page = new pageDefine( "/txn30101034.do", "修改采集任务数据库");
		//page.addValue(idx,"primary-key:collect_task_id");
		//page.updateRecord();
		var page = new pageDefine( "/txn30101037.do", "修改采集任务数据库");
		//var taskId = getFormFieldValue("primary-key:collect_task_id");
		page.addValue(idx,"primary-key:collect_task_id");
		//page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
		page.goPage();
	}else if(type=="ETL"){
		var page = new pageDefine( "/txn30300004.do", "修改采集任务数据库");
		page.addValue(idx,"primary-key:etl_id");
		//page.updateRecord();
	}else {
		alert("正在努力建中……");
		return;
	}
	page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
	page.addParameter("select-key:collect_type","select-key:collect_type");
	page.addParameter("select-key:task_status","select-key:task_status");
	page.updateRecord();
	
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
	}else if(type=="ETL"){
		var page = new pageDefine( "/txn30300005.do", "删除采集任务" );
		page.addValue(idx,"primary-key:etl_id" );
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
		var page = new pageDefine( "/txn30101006.do", "查看采集任务", "modal", "800", "600" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="SOCKET"||type=="JMS消息"){
		var page = new pageDefine( "/txn30101016.do", "查看采集任务", "modal", "800", "600" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="FTP"){
		var page = new pageDefine( "/txn30101015.do", "查看采集任务", "modal", "800", "600" );
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="文件上传"){
		var page = new pageDefine( "/txn30101025.do", "查看采集任务" , "modal", "800", "600");
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
		
	}else if(type=="数据库"){
		var page = new pageDefine( "/txn30101035.do", "查看采集任务", "modal", "800", "600" );
		page.addValue(id, "primary-key:collect_task_id" );
		
		page.updateRecord();
		
	}else if(type=="ETL"){
		var page = new pageDefine( "/txn30300008.do", "查看采集任务", "modal", "800", "600" );
		page.addValue(id, "primary-key:etl_id" );
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

	var ids = getFormAllFieldValues("record:collect_task_id");
	var data_source_id=getFormAllFieldValues("record:data_source_id");
	var task_status = getFormAllFieldValues("record:task_status");
	var type = getFormAllFieldValues("record:collect_type");
	var collect_status = getFormAllFieldValues("record:collect_status");
	
	var log_file_path =  getFormAllFieldValues("record:log_file_path");
	//alert(ids.length);
	for(var i=0; i<ids.length; i++){
	   //var htm='<a href="#" title="查看" onclick="func_record_viewRecord(\''+ids[i]+'\',\''+type[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   var htm='';
	   if(task_status[i]!="G"){//非归档记录才可以修改
		   htm='<a href="#" title="修改" onclick="func_record_updateRecord(\''+ids[i]+'\',\''+type[i]+'\',\''+task_status[i]+'\',\''+collect_status[i]+'\');"><div class="edit"></div></a>&nbsp;';
		   htm+='<a href="#" title="删除" onclick="func_record_deleteRecord(\''+ids[i]+'\',\''+type[i]+'\');"><div class="delete"></div></a>&nbsp;&nbsp;';
		   if(type[i]=="ETL" ){
		   }else{
		   		if(task_status[i]=="Y"){
		   		htm+='<a href="#" title="停用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+task_status[i]+'\',\''+type[i]+'\');"><div class="run"></div></a>&nbsp;';
		   	  }else
		   	  {
		   		htm+='<a href="#" title="启用" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+task_status[i]+'\',\''+type[i]+'\');"><div class="stop"></div></a>&nbsp;';
		   	  }
		   }
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
	   //console.log(document.getElementsByName("span_record:collect_status").length);
	   //document.getElementsByName("span_record:collect_status")[i].innerHTML =collect_status[i]+colSta_htm;
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	 
	 var names = getFormAllFieldValues("record:task_name");
	 var targetnames = getFormAllFieldValues("record:service_targets_id");
	 for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="点击查看详细信息" onclick="func_record_viewRecord1(\''+i+'\',\''+type[i]+'\');">'+names[i]+'</a>';
	   
	   
	   if(type[i]=="文件上传"){
	   		var filepath = log_file_path[i];
	   		
			var num = filepath.lastIndexOf("/");
			var filename = filepath.substring(num+1);
			var url = "/downloadFile?file="+filepath+"&&fileName="+filename;
	   
	   		var colSta_htm="";
	   		
	   		//00:未采集；01：正在采集；02：采集成功；03：采集失败
	   		if(collect_status[i]=="02"){
	   			colSta_htm='<a href="'+url+'" title="下载日志文件" onclick=""><div class="collect_normal"></div></a>&nbsp;';
	   		}else if(collect_status[i]=="03"){
	   			colSta_htm='<a href="'+url+'" title="下载日志文件" onclick=""><div class="collect_failed"></div></a>&nbsp;';
	   		}
	   		htm = htm + colSta_htm;
	   		
	   }
	   
	   document.getElementsByName("span_record:task_name")[i].innerHTML =htm;
	   
	   var htm2 = '<a href="#" title="点击查看详细信息" onclick="func_viewConfig(\''
			+ i + '\');">' + targetnames[i] + '</a>';
		document.getElementsByName("span_record:service_targets_id")[i].innerHTML = htm2;
	}
	
	
	
}

_browse.execute( '__userInitPage()' );

</script>
<freeze:body >
<freeze:title caption="查询采集任务列表"/>
<freeze:errors/>
<gwssi:panel action="txn30101001" target="" parts="t1,t2,t3" styleClass="wrapper">
  <gwssi:cell id="t1" name="对象类型" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" maxsize="10" />
  <gwssi:cell id="t2" name="采集类型" key="collect_type" data="type_data" pop="false" move2top="false" maxsize="10" />
  <gwssi:cell id="t3" name="任务状态" key="task_status" data="state_data"/>
</gwssi:panel>
<freeze:form action="/txn30101001.do">
  <freeze:grid property="record" caption="查询采集任务列表" keylist="collect_task_id" width="95%"  multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加采集任务" txncode="30101003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
     
      <freeze:hidden property="collect_task_id" caption="采集任务ID" />
      <freeze:hidden property="service_targets_id1" caption="服务对象ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="service_targets_id" caption="服务对象" show="name" valueset="资源管理_服务对象所有名称" style="width:10%; text-align:center;" />
      <freeze:cell property="task_name" caption="任务名称" style="width:20%"/>
      <freeze:cell property="collect_type" caption="采集类型" valueset="采集任务_采集类型" style="width:10%;text-align: center;" />
      <freeze:cell property="scheduling_day1" caption="采集周期" style="width:18%;text-align: center;"/>
      <freeze:cell property="start_time" caption="开始时间" style="width:10%;text-align: center;"/>
       <freeze:cell property="name" align="center" caption="最后修改人"  style="width:10%"/>
	  <freeze:cell property="time" align="center" caption="最后修改日期"  style="width:10%"/>
      <freeze:cell property="oper" caption="操作" style="width:12%; text-align: center;" />
      
      <freeze:hidden property="data_source_id" caption="数据源" style="width:18%"/>
      <freeze:hidden property="created_time" caption="创建时间" style="width:12%" />
      <freeze:hidden property="task_description" caption="任务描述"   />
      <freeze:hidden property="record" caption="备案说明"   />
      <freeze:hidden property="task_status" caption="任务状态"  style="width:5%"  />
      <freeze:hidden property="is_markup" caption="有效标记"  />
      <freeze:hidden property="creator_id" caption="创建人ID"  />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" />
      <freeze:hidden property="last_modify_time" caption="最后修改时间" />
      <freeze:hidden property="log_file_path" caption="日志文件路径" />
      <freeze:hidden property="collect_status" caption="采集状态"  />
  </freeze:grid>

</freeze:form>

</freeze:body>
</freeze:html>
