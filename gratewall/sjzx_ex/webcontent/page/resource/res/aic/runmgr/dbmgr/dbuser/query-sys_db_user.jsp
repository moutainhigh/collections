<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>数据库共享对象列表</title>
</head>

<script language="javascript">

// 增加服务对象
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_db_user.jsp", "增加数据库共享对象", "modal" ,650,350);
	page.addRecord();
}

// 修改数据库共享对象
function func_record_updateRecord(index)
{
    var id = getFormFieldText("record:sys_db_user_id",index);
	var page = new pageDefine( "/txn52101004.do", "修改数据库共享对象", "modal" ,650,350);
	page.addValue( id, "record:sys_db_user_id" );
	page.updateRecord();
}

// 删除数据库共享对象
function func_record_deleteRecord()
{
	var page = new pageDefine( "txn52101005.do", "删除数据库共享对象" );
	page.addParameter( "record:sys_db_user_id", "record:sys_db_user_id" );
	page.addParameter( "record:login_name", "record:login_name" );
	page.addParameter("record:user_type","record:user_type");
	page.deleteRecord( "是否删除选中的用户?" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var states = getFormAllFieldValues("record:state");
    var	grid = getGridDefine( "record", 0 );
    var flagBoxs = grid.getFlagBoxs();
    var stateTds = document.getElementsByName("span_record:state");
    var operationSpan = document.getElementsByName("span_record:oper");
	for (var i=0; i < operationSpan.length; i++){
		if(states[i] == '1'){
			stateTds[i].style.textAlign = 'right';
			operationSpan[i].innerHTML += "<a onclick='func_record_updateRecord(" + i + ");' href='#'>修改</a>";
		}else{
	    	operationSpan[i].innerHTML += "<a onclick='func_record_updateRecord(" + i + ");' href='#'>修改</a>&nbsp;&nbsp;<a onclick='func_viewConfig(" + i + ");' href='#'>视图配置</a>&nbsp;&nbsp;<a onclick='func_selfConfig(" + i + ");' href='#'>灵活配置</a>";
	    }
	    flagBoxs[i].attachEvent("onclick", checkButtonByData);
	}
	document.getElementById("record:_select-all").attachEvent("onclick", checkButtonByData);	
}

function changeState(){
	var page = new pageDefine( arguments[0], "改变数据库共享对象状态");
	page.addParameter("record:sys_db_user_id","record:sys_db_user_id");
	page.addParameter("record:login_name","record:login_name");
	page.addParameter("record:user_type","record:user_type");
    if(arguments[1]=='1'){
       page.deleteRecord("确认启用？");
    }else{
       page.deleteRecord("确认停用？");
    }
}

function checkButtonByData(){
	var stateArray = getFormFieldValues("record:state");	
	var hasConfigArray = getFormFieldValues("record:hasConfig");

	if(stateArray==null||stateArray==""){
		return;
	}

	var startFlag = false;
	var stopFlag = false;
	var dFlag = false;
	for( var i=0;i<stateArray.length;i++){
		if(stateArray[i]=="0"){
		   startFlag = true;
		}

		if(stateArray[i]=="1"){
		    stopFlag = true;
		}
		
		if(hasConfigArray[i]=="1"){
		    dFlag = true;
		}	
	}

	if(startFlag){
        document.getElementById("record_record_startRecord").disabled=true;
	}else{
	    document.getElementById("record_record_startRecord").disabled=false;
	}
	if(stopFlag){
        document.getElementById("record_record_stopRecord").disabled=true;
	}else{
	    document.getElementById("record_record_stopRecord").disabled=false;
	}
	if(dFlag){
        document.getElementById("record_record_deleteRecord").disabled=true;
	}else{
	    document.getElementById("record_record_deleteRecord").disabled=false;
	}
}

function func_record_initPwd(){
	var page = new pageDefine( "txn52101010.ajax", "初始化数据库共享对象密码");
	page.addParameter("record:sys_db_user_id","record:sys_db_user_id");
	page.addParameter("record:login_name","record:login_name");
	page.addParameter("record:user_type","record:user_type");
	page.callAjaxService("initResponse");
}

function func_viewConfig(index){
    var state = getFormFieldText("record:state",index);
    var name = getFormFieldText("record:login_name",index);
	if(state == "0"){
		var page = new pageDefine( "../dbcfg/main-config.jsp", "查询视图配置" );
		page.addValue(name, "record:login_name");
		page.goPage();
	}else{
		alert("当前用户已被停用，请先启用此用户！");
	}    
}

function func_selfConfig(index){
    var state = getFormFieldText("record:state",index);
    var id = getFormFieldText("record:sys_db_user_id",index);
	if(state == "0"){
		var page = new pageDefine( "/txn52103011.do", "灵活配置列表" );
		page.addValue(id, "select-key:sys_db_user_id");
		page.goPage();
	}else{
		alert("当前用户已被停用，请先启用此用户！");
	}    
}

function initResponse(errCode, errDesc, xmlResults){
	if(errCode != '000000'){
      alert("处理错误："+errDesc);
    }else{
      alert("操作成功！");
    }
    clickFlag=0;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="数据库共享对象列表"/>
<freeze:errors/>

<freeze:form action="/txn52101001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="login_name" caption="用户代码：" datatype="string" maxlength="20" style="width:60%"/>
      <freeze:select property="state" caption="用户状态：" valueset="user_state" style="width:60%"/>
      <freeze:select property="user_type" caption="用户类型：" valueset="user_type" style="width:60%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="数据库共享对象列表" keylist="sys_db_user_id" width="95%" navbar="bottom" fixrow="false" rowselect="false">
      <freeze:button name="record_addRecord" caption="增加"  enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改"  enablerule="1" align="right" onclick="func_record_updateRecord();" visible="false"/>
      <freeze:button name="record_startRecord" caption="启用"  enablerule="2" align="right" onclick="changeState('txn52101006.do','1');"/>
      <freeze:button name="record_stopRecord" caption="停用"  enablerule="2" align="right" onclick="changeState('txn52101007.do','0');"/>
      <freeze:button name="record_deleteRecord" caption="删除"  enablerule="2" align="right" onclick="func_record_deleteRecord();"/>
      
      <freeze:button name="record_initPwd" caption="密码初始化"  enablerule="2" align="right" onclick="func_record_initPwd();" visible="false"/>
      
      <freeze:hidden property="sys_db_user_id" caption="数据库共享对象编号"/>
      <freeze:hidden property="state" caption="状态"/>
      <freeze:hidden property="login_name" caption="用户名"/>
      <freeze:hidden property="user_name" caption="名称" />
      <freeze:hidden property="user_type" caption="用户类型"/>
      <freeze:hidden property="grant_table" caption="授权表"/>
      <freeze:hidden property="hasConfig" caption="是否配置视图"/>
      <freeze:cell property="@rowid" caption="序号" align="center" style="width:4%"/>
      <freeze:cell property="login_name" caption="用户代码" style="width:10%" align="left"/>
      <freeze:cell property="user_name" caption="用户名称" style="width:14%" align="left" />
      <freeze:cell property="password" caption="用户密码" style="width:8%" align="left" />
      <freeze:cell property="create_by" caption="创建人" style="width:10%" align="left"/>
      <freeze:cell property="create_date" caption="创建日期" style="width:10%" align="left" />
      <freeze:cell property="user_type" caption="用户类型" style="width:8%" align="left" valueset="user_type" />
      <freeze:cell property="state" caption="状态" style="width:8%" align="left" valueset="user_state" />
      <freeze:cell property="hasConfig" caption="是否配置" style="width:8%" align="left" valueset="布尔型数" />
	  <freeze:cell property="oper" caption="操作" align="left" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
