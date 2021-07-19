<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>用户列表</title>
</head>
<freeze:body>
<freeze:title caption="用户列表"/>
<freeze:errors/>
<script language='javascript' src='<%=request.getContextPath()%>/script/selectTree.js'></script>
<freeze:include href="/script/lib/jquery.js"></freeze:include>
<script language="javascript">
// 增加用户
function func_record_addRecord(){
  var page = new pageDefine( "insert-yhgl.jsp", "增加用户", "modal", 650, 400);
  page.addRecord( );

}

// 修改用户
function func_record_updateRecord(){
  var page = new pageDefine( "/txn807004.do", "修改用户", "modal", 650, 400);
  page.addParameter("record:yhid_pk","primary-key:yhid_pk");
  page.updateRecord( );
}

// 停用用户
function func_record_deleteRecord(yhid){
  	var checkboxs= document.getElementsByName('record:_flag');
	var flag = false;
	var syzt = "";
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked==true){
		    syzt = getFormFieldValue("record:sfyx",i);
		    if(syzt=="1"){
			   flag = true;
			   break;
			}
		}
	}
	if(flag){
	   alert("已停用的用户不能再次停用！");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
	var page = new pageDefine( "/txn807005.do", "停用用户");
	 page.addValue(yhid,"primary-key:yhid_pk");
	//page.deleteRecord("是否停用选中的用户？");
	if(confirm("是否停用选中的用户？")){
	   page.goPage();	
	}else{
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 		
	}
}

// 启用用户
function func_record_restarRecord(yhid){

  	var checkboxs= document.getElementsByName('record:_flag');
	var flag = false;
	var syzt = "";
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked==true){
		    syzt = getFormFieldValue("record:sfyx",i);
		    if(syzt=="0"){
			   flag = true;
			   break;
			}
		}
	}
	if(flag){
	   alert("已启用的用户不能再次启用！");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
    var page = new pageDefine( "/txn807008.do", "启用用户");
    page.addValue(yhid,"primary-key:yhid_pk");
    //page.deleteRecord("是否启用选中的用户？");
   	if(confirm("是否启用选中的用户？")){
	   page.goPage();	
	}else{
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 		
	}
}

//初始化密码
function func_psw_inte(){
	var page = new pageDefine("/txn807007.do","重置密码");
	page.addParameter("record:yhid_pk","primary-key:yhid_pk");	
	page.addParameter("record:yhxm","primary-key:yhxm");	
	page.deleteRecord("是否重置选中用户的密码？");
}
//机构选择
function sjjg_select(){
selectJG("tree","select-key:jgid_fk","select-key:jgname");
}
//reset
function resetAllValue(){
	
	document.getElementById("select-key:yhzh").value="";
	document.getElementById("select-key:yhxm").value="";
	document.getElementById("select-key:jgname").value="";
	document.getElementById("select-key:jgid_fk").value="";
	document.getElementById("select-key:sfyx").value="";
}

function clearFormFieldValue(){
	
	document.getElementById("select-key:yhzh").value="";
	document.getElementById("select-key:yhxm").value="";
	document.getElementById("select-key:jgname").value="";
	document.getElementById("select-key:jgid_fk").value="";
	document.getElementById("select-key:sfyx").value="";
	document.getElementById("select-key:rolenames").value=""
}

function checkButtonByData(){
    
	var sfyxArray = getFormFieldValues("record:sfyx");	
	if(sfyxArray==""){return;}
	var sFlag = false;
	var eFlag = false;
	
	for( var i=0;i<sfyxArray.length;i++){
	   if(!sFlag){
		   if(sfyxArray[i]=="0"){
		      document.getElementById("record_record_deleteRecord_s").disabled = "-1";
		      sFlag = true;
		   }
	   }
	   if(!eFlag){
		   if(sfyxArray[i]=="1"){
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
	}
}

_browse.execute(function(){
	var gridDataTable = document.getElementById("record");
	/*for ( var i = 1; i < gridDataTable.rows.length ; i++ ){
		gridDataTable.rows[i].attachEvent("onclick", checkButtonByData);
	}
	document.getElementById("record:_select-all").attachEvent("onclick", checkButtonByData);*/
	var operationSpan = document.getElementsByName("span_record:operation");
	var states = getFormAllFieldValues("record:sfyx");
	var yhids = getFormAllFieldValues("record:yhid_pk");
	for (var i=0; i < operationSpan.length; i++){
		var htm = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'><div title='修改' class='edit'></div></a>";
		if(states[i] == '1'){
			htm+='<a href="#" title="启用" onclick="func_record_restarRecord(\''+yhids[i]+'\');"><div class="stop"></div></a>';
		}else{
			htm+='<a href="#" title="停用" onclick="func_record_deleteRecord(\''+yhids[i]+'\');"><div class="run"></div></a>';
		}
		htm += "<a onclick='setCurrentRowChecked(\"record\");func_psw_inte();' title='密码初始化' href='javascript:;'><div class='pwd_init'></div></a>";
		operationSpan[i].innerHTML=htm;
	}	
	var states = getFormAllFieldValues("record:sfyx");
	var stateTds = document.getElementsByName("span_record:sfyx");
	for(var i=0; i<states.length; i++){
		if(states[i] == '1'){
			stateTds[i].style.textAlign = 'right';
		}
	}		
});
</script>

   <!-- <freeze:reset property="reset" caption="重 填" onclick="resetAllValue()"></freeze:reset>  -->
<freeze:form action="/txn807001">
  <freeze:block theme="query"  property="select-key" caption="查询条件" width="95%" >
    <freeze:text property="yhzh" caption="用户账号" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:text property="yhxm" caption="用户姓名" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:text property="jgname" caption="所属机构" style="width:70%" readonly="true" postfix="（<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select()'>选择</a>）"></freeze:text>
    <freeze:hidden property="jgid_fk" />
    <freeze:select property="sfyx" caption="使用状态"  valueset="syzt" style="width:90%"/>    
    <freeze:select property="rolenames" caption="角色" valueset="用户角色信息" style="width:36.5%" colspan="2" show="code"/>   
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="用户列表" keylist="yhid_pk" width="95%" navbar="bottom" fixrow="false" checkbox="false">
    <freeze:button name="record_addRecord" caption="增加" txncode="807003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
  <%--   <freeze:button name="record_deleteRecord_s" caption="启用" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_restarRecord();" visible="false"/>
    <freeze:button name="record_deleteRecord_e" caption="停用"  enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>
    <freeze:button name="psw-inte" caption="密码初始化" enablerule="2" hotkey="UPDATE" align="right" onclick="func_psw_inte();" visible="false" />    
     --%><freeze:hidden property="yhid_pk" caption="用户ID" style="width:10%"/>
    <freeze:hidden property="sfyx" caption="使用状态" style="width:10%"/>
	<freeze:hidden property="yhbh" caption="用户编号" style="width:15%"/>
	<freeze:cell property="@rowid" caption="序号" style="width:5%" align="center"/>
    <freeze:cell property="yhzh" caption="用户账号" style="width:10%"/>	
    <freeze:cell property="yhxm" caption="用户姓名" style="width:8%"/>
    <freeze:cell property="jgname" caption="所属机构" style="width:18%" />   
    <freeze:cell property="rolenames" caption="角色" style=""/>
    <freeze:cell property="sfyx" caption="使用状态" style="width:60px;" valueset="syzt"/>
    <freeze:cell property="operation" caption="操作" align="center" style="width:75px" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
