<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询抽取服务管理列表</title>
</head>

<script language="javascript">
// 连接抽取服务管理
function func_connect()
{
	
	var page = new pageDefine("/txn501030002.ajax");

	page.addParameter("record:etl_hostname","record:etl_hostname");
	page.addParameter("record:etl_portno","record:etl_portno");
	page.addParameter("record:etl_domainname","record:etl_domainname");
	page.addParameter("record:rep_name_en","record:rep_name_en");
	
	page.addParameter("record:user_id","record:user_id");
	page.addParameter("record:user_password","record:user_password");
	page.addParameter("record:rep_foldername","record:rep_foldername");
	page.addParameter("record:wf_name","record:wf_name");	

	page.callAjaxService("Ajax_func_connect");	
}
//连接成功后给页面赋值
function func_connect_sucess(arg1,arg2,arg3){
		var wf_flag = document.getElementsByName("record:_flag");
		for( i=0;i<wf_flag.length;i++){
			var wf_radio = wf_flag[i];
			if(wf_radio.checked==true){
				//列表元素的顺序不能变化
				wf_radio.parentNode.parentNode.childNodes(4).firstChild.innerText=arg1;
				wf_radio.parentNode.parentNode.childNodes(5).firstChild.innerText=arg2;
				wf_radio.parentNode.parentNode.childNodes(6).firstChild.innerText=arg3;
				break;
			}
		}
}
//连接回调
function Ajax_func_connect(errCode,errDesc,xmlResults){
	if(errCode=="000000"){
		//返回连接成功结果
		var wf_db_source = _getXmlNodeValues(xmlResults,"record:wf_db_source").toString();
		var wf_db_target = _getXmlNodeValues(xmlResults,"record:wf_db_target").toString();
		var wf_state = _getXmlNodeValues(xmlResults,"record:wf_state").toString();
		func_connect_sucess(wf_db_source,wf_db_target,wf_state);
		alert("连接成功!");
	}else{
		alert(errDesc);
	}
}
//运行,调度前判断连接是否成功
function connect_failure(){
		var state = true;
		var wf_flag = document.getElementsByName("record:_flag");
		for( i=0;i<wf_flag.length;i++){
			var wf_radio = wf_flag[i];
			if(wf_radio.checked==true){
				//列表元素的顺序不能变化
				if(wf_radio.parentNode.parentNode.childNodes(4).firstChild.innerText!=""){
					state=false;
					break;
				}
			}
		}
		return state;
}
// 运行抽取服务管理
function func_run()
{
	var page = new pageDefine("view-workflow_execute_info.jsp", "查看抽取服务执行信息", "modal");	
	page.addParameter("record:wf_name","select-key:wf_name");
	page.addParameter("record:rep_foldername","select-key:rep_foldername");
	page.addParameter("record:workflow_id","select-key:workflow_id");
	page.addParameter("record:dbuser","select-key:dbuser");
	page.addParameter("record:domain_name","select-key:domain_name");
	page.addParameter("record:server_name","select-key:server_name");
	page.goPage();
	// page.callAjaxService("Ajax_func_run");
}

//运行回调
function Ajax_func_run(errCode,errDesc,xmlResults){
	if(errCode=="000000"){
		// var text = _getXmlNodeValues(xmlResults,"record:etl_hostname").toString();
		alert("该项抽取服务已经在后台开始运行了！\n由于数据量比较大，执行过程可能比较慢，请勿多次重复点击！");
	}else{
		alert(errDesc);
	}
}
// 调度抽取服务管理
function func_dispatch()
{
	var page = new pageDefine( "detail-scheduler.jsp", "调度抽取服务管理", "modal" );
	page.addParameter( "record:workflow_id", "record:workflow_id" );
	page.addParameter( "record:dbuser", "record:dbuser" );
	page.addParameter( "record:wf_name", "record:wf_name" );
	page.addParameter( "record:rep_foldername", "record:rep_foldername" );
	page.addParameter( "record:domain_name", "record:domain_name" );
	page.addParameter( "record:server_name", "record:server_name" );
	page.goPage();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询抽取服务管理列表"/>
<freeze:errors/>

<freeze:form action="/txn501030001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="rep_id" caption="项目名称" valueset="ETL项目" show="name" style="width:55%"/>
  </freeze:block>

  <freeze:grid property="record" caption="抽取服务列表" keylist="workflow_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_run" caption="运行" txncode="50106007" enablerule="1" align="right" onclick="func_run();"/>
      <freeze:button name="record_dispatch" caption="调度" txncode="50106006" enablerule="1" align="right" onclick="func_dispatch();"/>
      <freeze:hidden property="sys_etl_wf_id" caption="ID" style="width:10%" />    
      <freeze:hidden property="rep_folderid" caption="文件夹ID" style="width:15%" />
      <freeze:cell property="rep_foldername" caption="文件夹名称" align="center" style="width:15%" />
      <freeze:hidden property="workflow_id" caption="workflow_ID" style="width:15%" />
      <freeze:cell property="wf_name" caption="抽取服务名称" align="center" style="width:15%" />
      <freeze:cell property="wf_ms" caption="抽取服务描述" align="center" style="width:20%" />
      <freeze:cell property="wf_db_source" caption="源数据库" align="center" style="width:20%" />
      <freeze:cell property="wf_db_target" caption="目标数据库" align="center" style="width:20%" />
      <freeze:cell property="wf_state" caption="状态" align="center" style="width:10%" />
      <freeze:hidden property="dbuser" caption="数据库用户" style="width:10%" />
      <freeze:hidden property="domain_name" caption="域名" style="width:10%" />
      <freeze:hidden property="server_name" caption="服务名" style="width:10%" /> 
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
