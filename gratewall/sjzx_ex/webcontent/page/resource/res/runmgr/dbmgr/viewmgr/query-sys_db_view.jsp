<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>视图列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加共享服务
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_db_view.jsp", "增加视图" );
	page.addRecord();
}

// 修改共享服务
function func_record_updateRecord(index)
{
	if(index!=-1){
    	var _target = "<%=request.getContextPath()%>/dw/runmgr/dbmgr/viewmgr/update-sys_db_view.jsp";
		var page = new pageDefine( _target, "修改视图");		
	    var view_id = getFormFieldText("record:sys_db_view_id",index);
		page.addValue( view_id, "select-key:sys_db_view_id" );
		page.goPage();  
	}

}
// 删除共享服务
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn52102005.do", "删除视图" );
	page.addParameter( "record:sys_db_view_id", "primary-key:sys_db_view_id" );
	page.addParameter( "record:view_name", "primary-key:view_name" );
	page.deleteRecord("确认删除视图吗？");
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
    var	grid = getGridDefine( "record", 0 );
    var flagBoxs = grid.getFlagBoxs();
    var hasConfigArray = getFormAllFieldValues("record:hasConfig");
    var operationSpan = document.getElementsByName("span_record:oper");
	for (var i=0; i < operationSpan.length; i++){
	    if(hasConfigArray[i]=='1'){
	    	operationSpan[i].innerHTML += "<a title='查看' onclick='func_viewConfig(" + i + ");' href='#'><div class='detail'></div></a>";
	    }else{
	    	operationSpan[i].innerHTML += "<a title='修改' onclick='func_record_updateRecord(" + i + ");' href='#'><div class='edit'></div></a>&nbsp;&nbsp;<a onclick='func_viewConfig(" + i + ");' href='#'>查看</a>";
	    }
	    flagBoxs[i].attachEvent("onclick", checkButtonByData);
	}
	document.getElementById("record:_select-all").attachEvent("onclick", checkButtonByData);
}
function checkButtonByData(){
	var hasConfigArray = getFormFieldValues("record:hasConfig");
	var dFlag = false;
	for( var i=0;i<hasConfigArray.length;i++){
		if(hasConfigArray[i]=="1"){
		   dFlag = true;
		}
	}
	if(dFlag){
        document.getElementById("record_record_deleteRecord").disabled=true;
	}else{
	    document.getElementById("record_record_deleteRecord").disabled=false;
	}		
}
function submitForm(){
	if(compare_Date('create_start_date','create_end_date','提示：创建日期，起始日期大于结束日期！')){
		return;
	}
	_querySubmit();
}

function resetForm(){
	var strList ='view_code,view_name,create_start_date,create_end_date,create_by';
	resetAllFieldNull(strList);
}

function func_viewConfig(index){
    var viewId = getFormFieldText("record:sys_db_view_id",index);
    var _target = "<%=request.getContextPath()%>/dw/runmgr/dbmgr/viewmgr/detail-sys_db_view.jsp";
    //this.open("<%=request.getContextPath()%>/dw/runmgr/services/svrmgr/insert-sys_svr_service.jsp?action=update&id="+getFormFieldText("record:sys_svr_service_id",index),"_self");

	var page = new pageDefine( _target, "视图详情");
	page.addParameter( "select-key:view_code", "select-key:view_code" );
	page.addParameter( "select-key:view_name", "select-key:view_name" );
	page.addParameter( "select-key:create_by", "select-key:create_by" );
	page.addParameter( "select-key:create_date", "select-key:create_date" );
	page.addValue(viewId,"select-key:sys_db_view_id");
	page.goPage();  
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="视图列表"/>
<freeze:errors/>

<freeze:form action="/txn52102001">
  <freeze:block property="select-key" caption="查询条件" width="95%">
      <freeze:text property="view_code" caption="视图代码" datatype="string" maxlength="20" style="width:90%"/>
      <freeze:text property="view_name" caption="视图名称" datatype="string" maxlength="40" style="width:90%"/>
      <freeze:datebox property="create_start_date" caption="创建日期" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>至</td><td width='45%'>
    <freeze:datebox property="create_end_date" caption="创建日期" style="width:100%" colspan="0"/>
    </td></tr></table>
      <freeze:text property="create_by" caption="创建人" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:button name="queryButton" caption="查 询" onclick="submitForm()"/>
      <freeze:button name="resetButton" caption="重 填" onclick="resetForm()"/>      
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="视图列表" keylist="sys_db_view_id" width="95%" navbar="bottom" fixrow="false" align="center">
      <freeze:button name="record_addRecord" caption="增加" align="center" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" align="center" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="sys_db_view_id" caption="视图ID" style="width:11%" />
      <freeze:hidden property="view_name" caption="view_name" style="width:11%" />
      <freeze:cell property="@rowid" caption="序号" align="center" style="width:5%"/>
      <freeze:cell property="view_code" caption="视图代码" align="left" style="width:12%" />
      <freeze:cell property="view_name" caption="视图名称" align="left" style="width:30%" />
      <freeze:cell property="create_by" caption="创建人" align="left" style="width:10%" />
      <freeze:cell property="create_date" caption="创建日期" align="left" style="width:10%" />
      <freeze:cell property="hasConfig" caption="是否配置" style="width:10%" align="left" valueset="布尔型数" />
      <freeze:cell property="view_desc" caption="描述" align="left" style="width:15%" />
      <freeze:hidden property="hasConfig" caption="是否配置视图"/>
	  <freeze:cell property="oper" caption="操作" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
