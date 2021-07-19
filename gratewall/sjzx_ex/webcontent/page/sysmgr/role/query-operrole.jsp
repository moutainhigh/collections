<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>查询业务角色列表</title>
<freeze:include href="/script/lib/jquery162.js"/>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<freeze:body>
<freeze:title caption="查询业务角色列表"/>
<freeze:errors/>

<script language="javascript">
function func_record_addRecord(){
  var page = new pageDefine( "insert-operrole.jsp", "增加业务角色", "modal", 650, 300);
  page.addRecord( );
}

function func_record_updateRecord(){
  var page = new pageDefine( "/txn980324.do", "修改业务角色", "modal", 650, 300);
  page.addParameter("record:rolename","primary-key:rolename");
  page.updateRecord( );
}

function func_record_copyRecord(){
  var page = new pageDefine( "/txn980324.do", "复制业务角色", "modal", 650, 600);
  page.addParameter("record:rolename","primary-key:rolename");
  page.setForwardName( 'copy' );
  //处理页码不刷新的问题
  page.addRecord( );
  document.forms[0].submit();
}

function func_record_updateStatus(){
  var page = new pageDefine( "/txn980324.do", "修改业务角色的状态", "modal", 650, 360);
  page.addParameter("record:rolename","primary-key:rolename");
  page.setForwardName( 'status' );
  page.updateRecord( );
}

//function func_record_deleteRecord(){
  //var page = new pageDefine( "/txn980325.do", "删除业务角色");
 // page.addParameter("record:roleid","primary-key:roleid");
  //page.addParameter("record:rolename","primary-key:rolename");
 // page.deleteRecord("是否删除选中的记录");
//}

function func_record_setFuncList(){
  var page = new pageDefine( "/txn980324.do", "设置角色的权限", "modal", 500, 500);
  page.addParameter("record:rolename","primary-key:rolename");
  page.addParameter("record:status","record:status");
  page.setForwardName( 'priv' );
  page.goPage( );
}

function checkRecordStatus()
{
	//var grid = getGridDefine( 'record' );
  	//var flags = grid.getFieldValues( 'status' );
  	//if( flags != null && flags.length == 1 ){
  		//if( flags[0] == '禁止' ){
  			//grid.setMenuCaption( 'record_updateStatus', "启用" );
  		//}
  		//else{
  			//grid.setMenuCaption( 'record_updateStatus', "停用" );
  		//}
  	//}
}
// 停用用户
function func_record_deleteRecord(roleid){

  	var checkboxs= document.getElementsByName('record:_flag');
	var flag = false;
	var status = "";
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked==true){
		    status = getFormFieldValue("record:status",i);
		    if(status=="0"){
			   flag = true;
			   break;
			}
		}
	}
	if(flag){
	   alert("已停用的角色不能再次禁用！");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
	var page = new pageDefine( "/txn980330.do", "禁用角色");
	page.addValue(roleid,"primary-key:roleid");
	//page.addParameter("record:roleid","primary-key:roleid");
	page.addParameter("record:rolename","primary-key:rolename");
    page.addParameter("record:funclist","primary-key:funclist");
	//page.deleteRecord("是否停用选中的角色？");
	if(confirm("是否停用选中的角色？")){
	   page.goPage();	
	}else{
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 		   
	}
}

// 启用用户
function func_record_restarRecord(roleid){

  	var checkboxs= document.getElementsByName('record:_flag');
	var flag = false;
	var status = "";
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked==true){
		    status = getFormFieldValue("record:status",i);
		    if(status=="1"){
			   flag = true;
			   break;
			}
		}
	}
	if(flag){
	   alert("已启用的角色不能再次启用！");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
    var page = new pageDefine( "/txn980331.do", "启用角色");
    page.addValue(roleid,"primary-key:roleid");
   // page.addParameter("record:roleid","primary-key:roleid");
    page.addParameter("record:rolename","primary-key:rolename");
    page.addParameter("record:funclist","primary-key:funclist");
    //page.deleteRecord("是否启用选中的角色？");
    if(confirm("是否启用选中的角色？")){
	   page.goPage();	
	}else{
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 		   
	}
}

function checkButtonByData(){
    
	/*var sfyxArray = getFormFieldValues("record:status");	
	if(sfyxArray==""){return;}
	var sFlag = false;
	var eFlag = false;
	
	for( var i=0;i<sfyxArray.length;i++){
	   if(!sFlag){
		   if(sfyxArray[i]=="1"){
		      document.getElementById("record_record_deleteRecord_s").disabled = "-1";
		      sFlag = true;
		   }
	   }
	   if(!eFlag){
		   if(sfyxArray[i]=="0"){
		      document.getElementById("record_record_deleteRecord_e").disabled = "-1";
		      eFlag = true;
		   }
	   }	
	}
	if(!sFlag){
       document.getElementById("record_record_deleteRecord_s").removeAttribute("disabled");
	}
	if(!eFlag){
       document.getElementById("record_record_deleteRecord_e").removeAttribute("disabled");
	}*/
}

_browse.execute(function(){
	var gridDataTable = document.getElementById("record");
	for ( var i = 1; i < gridDataTable.rows.length ; i++ ){
		gridDataTable.rows[i].attachEvent("onclick", checkButtonByData);
	}
	//document.getElementById("record:_select-all").attachEvent("onclick", checkButtonByData);
	var operationSpan = document.getElementsByName("span_record:operation");
	var states = getFormAllFieldValues("record:status");
	var roleid = getFormAllFieldValues("record:roleid");
	for (var i=0; i < operationSpan.length; i++){
		var htm = "<a title='修改' onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'><div class='edit'></div></a>"+
		"<a title='授权' onclick='setCurrentRowChecked(\"record\");func_record_setFuncList();' href='#'><div class='authorize' ></div></a>&nbsp;"+
		"<a title='复制' onclick='setCurrentRowChecked(\"record\");func_record_copyRecord();' href='#'><div class='copy' ></div></a>";
		if(states[i] == '1'){
			htm+='<a href="#" title="停用" onclick="func_record_deleteRecord(\''+roleid[i]+'\');"><div class="run"></div></a>';
		}else{
			
			htm+='<a href="#" title="启用" onclick="func_record_restarRecord(\''+roleid[i]+'\');"><div class="stop"></div></a>';
		}
		operationSpan[i].innerHTML=htm;
	}
	
	
	
	var states = getFormAllFieldValues("record:status");
	var stateTds = document.getElementsByName("span_record:status");
	for(var i=0; i<states.length; i++){
		if(states[i] == '0'){
			stateTds[i].style.textAlign = 'right';
		}
	}			
});
</script>

<freeze:form action="/txn980321">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
<%--   	<freeze:button name="select_submit" action="submit" caption="查 询" align="right"/>
  	<freeze:button name="select_reset" action="reset" align="right"/>
  	<freeze:button name="select_goBack" action="close" align="right"/> --%>
    <freeze:text property="rolename" caption="角色名称" style="width:90%" datatype="string" maxlength="32"/>
    <freeze:text property="description" caption="角色描述" style="width:90%" datatype="string" maxlength="256"/>
    <!--  
    <freeze:text property="funclist" caption="功能代码" style="width:90%" datatype="string" maxlength="1024"/>
    <freeze:hidden property="groupname" caption="角色所属的功能组" style="width:90%"/>
    <freeze:select property="rolescope" caption="使用范围" valueset="角色使用范围" style="width:90%"/>
    -->
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="角色列表" keylist="rolename" width="95%" navbar="bottom" checkbox="false" fixrow="false">
    <freeze:button name="record_addRecord" caption="增加" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
    <freeze:hidden property="roleid" caption="角色编号" style="width:20%"/>
    <freeze:hidden property="status" caption="有效标志" style="width:10%" />
    <freeze:hidden property="funclist" caption="功能列表" style="width:10%"/>
    <freeze:cell property="@rowid" caption="序号" style="width:5%" align="center"/>
    <freeze:cell property="rolename" caption="角色名称" style="width:12%" align="center"/>
    <freeze:cell property="description" caption="角色描述" style="width:25%"/>
    <freeze:cell property="rolescope" caption="角色使用范围" valueset="角色使用范围" style="width:20%" visible="false"/>
    <freeze:cell property="groupname" caption="角色所属的功能组" style="width:10%" visible="false"/>
    <freeze:cell property="homepage" caption="主页面" style="width:20%" visible="false"/>
    <freeze:cell property="layout" caption="页面布局" style="width:10%" visible="false"/>
    <freeze:cell property="roletype" caption="角色类型：保留" style="width:10%" visible="false"/>
    <freeze:cell property="roletype2" caption="角色类型：保留" style="width:10%" visible="false"/>
    <freeze:cell property="status" caption="有效标志"  align="center" valueset="有效标志" style="width:5%" />
    <freeze:hidden property="maxcount" caption="最大查询记录数" style="width:8%" />
    <freeze:cell property="memo" caption="备注" style="width:10%" />
    <freeze:cell property="regname" caption="注册用户" style="width:10%" visible="false"/>
    <freeze:cell property="regdate" caption="注册日期" style="width:10%" visible="false"/>
    <freeze:cell property="modname" caption="修改用户" style="width:10%" visible="false"/>
    <freeze:cell property="moddate" caption="修改日期" style="width:10%" visible="false"/>
    <freeze:cell property="operation" caption="操作" align="center" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
