<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>�û��б�</title>
</head>
<freeze:body>
<freeze:title caption="�û��б�"/>
<freeze:errors/>
<script language='javascript' src='<%=request.getContextPath()%>/script/selectTree.js'></script>
<freeze:include href="/script/lib/jquery.js"></freeze:include>
<script language="javascript">
// �����û�
function func_record_addRecord(){
  var page = new pageDefine( "insert-yhgl.jsp", "�����û�", "modal", 650, 400);
  page.addRecord( );

}

// �޸��û�
function func_record_updateRecord(){
  var page = new pageDefine( "/txn807004.do", "�޸��û�", "modal", 650, 400);
  page.addParameter("record:yhid_pk","primary-key:yhid_pk");
  page.updateRecord( );
}

// ͣ���û�
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
	   alert("��ͣ�õ��û������ٴ�ͣ�ã�");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
	var page = new pageDefine( "/txn807005.do", "ͣ���û�");
	 page.addValue(yhid,"primary-key:yhid_pk");
	//page.deleteRecord("�Ƿ�ͣ��ѡ�е��û���");
	if(confirm("�Ƿ�ͣ��ѡ�е��û���")){
	   page.goPage();	
	}else{
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 		
	}
}

// �����û�
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
	   alert("�����õ��û������ٴ����ã�");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
    var page = new pageDefine( "/txn807008.do", "�����û�");
    page.addValue(yhid,"primary-key:yhid_pk");
    //page.deleteRecord("�Ƿ�����ѡ�е��û���");
   	if(confirm("�Ƿ�����ѡ�е��û���")){
	   page.goPage();	
	}else{
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 		
	}
}

//��ʼ������
function func_psw_inte(){
	var page = new pageDefine("/txn807007.do","��������");
	page.addParameter("record:yhid_pk","primary-key:yhid_pk");	
	page.addParameter("record:yhxm","primary-key:yhxm");	
	page.deleteRecord("�Ƿ�����ѡ���û������룿");
}
//����ѡ��
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
		var htm = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'><div title='�޸�' class='edit'></div></a>";
		if(states[i] == '1'){
			htm+='<a href="#" title="����" onclick="func_record_restarRecord(\''+yhids[i]+'\');"><div class="stop"></div></a>';
		}else{
			htm+='<a href="#" title="ͣ��" onclick="func_record_deleteRecord(\''+yhids[i]+'\');"><div class="run"></div></a>';
		}
		htm += "<a onclick='setCurrentRowChecked(\"record\");func_psw_inte();' title='�����ʼ��' href='javascript:;'><div class='pwd_init'></div></a>";
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

   <!-- <freeze:reset property="reset" caption="�� ��" onclick="resetAllValue()"></freeze:reset>  -->
<freeze:form action="/txn807001">
  <freeze:block theme="query"  property="select-key" caption="��ѯ����" width="95%" >
    <freeze:text property="yhzh" caption="�û��˺�" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:text property="yhxm" caption="�û�����" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:text property="jgname" caption="��������" style="width:70%" readonly="true" postfix="��<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select()'>ѡ��</a>��"></freeze:text>
    <freeze:hidden property="jgid_fk" />
    <freeze:select property="sfyx" caption="ʹ��״̬"  valueset="syzt" style="width:90%"/>    
    <freeze:select property="rolenames" caption="��ɫ" valueset="�û���ɫ��Ϣ" style="width:36.5%" colspan="2" show="code"/>   
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="�û��б�" keylist="yhid_pk" width="95%" navbar="bottom" fixrow="false" checkbox="false">
    <freeze:button name="record_addRecord" caption="����" txncode="807003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
  <%--   <freeze:button name="record_deleteRecord_s" caption="����" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_restarRecord();" visible="false"/>
    <freeze:button name="record_deleteRecord_e" caption="ͣ��"  enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>
    <freeze:button name="psw-inte" caption="�����ʼ��" enablerule="2" hotkey="UPDATE" align="right" onclick="func_psw_inte();" visible="false" />    
     --%><freeze:hidden property="yhid_pk" caption="�û�ID" style="width:10%"/>
    <freeze:hidden property="sfyx" caption="ʹ��״̬" style="width:10%"/>
	<freeze:hidden property="yhbh" caption="�û����" style="width:15%"/>
	<freeze:cell property="@rowid" caption="���" style="width:5%" align="center"/>
    <freeze:cell property="yhzh" caption="�û��˺�" style="width:10%"/>	
    <freeze:cell property="yhxm" caption="�û�����" style="width:8%"/>
    <freeze:cell property="jgname" caption="��������" style="width:18%" />   
    <freeze:cell property="rolenames" caption="��ɫ" style=""/>
    <freeze:cell property="sfyx" caption="ʹ��״̬" style="width:60px;" valueset="syzt"/>
    <freeze:cell property="operation" caption="����" align="center" style="width:75px" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
