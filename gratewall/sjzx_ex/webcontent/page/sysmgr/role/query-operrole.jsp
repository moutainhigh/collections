<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>��ѯҵ���ɫ�б�</title>
<freeze:include href="/script/lib/jquery162.js"/>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<freeze:body>
<freeze:title caption="��ѯҵ���ɫ�б�"/>
<freeze:errors/>

<script language="javascript">
function func_record_addRecord(){
  var page = new pageDefine( "insert-operrole.jsp", "����ҵ���ɫ", "modal", 650, 300);
  page.addRecord( );
}

function func_record_updateRecord(){
  var page = new pageDefine( "/txn980324.do", "�޸�ҵ���ɫ", "modal", 650, 300);
  page.addParameter("record:rolename","primary-key:rolename");
  page.updateRecord( );
}

function func_record_copyRecord(){
  var page = new pageDefine( "/txn980324.do", "����ҵ���ɫ", "modal", 650, 600);
  page.addParameter("record:rolename","primary-key:rolename");
  page.setForwardName( 'copy' );
  //����ҳ�벻ˢ�µ�����
  page.addRecord( );
  document.forms[0].submit();
}

function func_record_updateStatus(){
  var page = new pageDefine( "/txn980324.do", "�޸�ҵ���ɫ��״̬", "modal", 650, 360);
  page.addParameter("record:rolename","primary-key:rolename");
  page.setForwardName( 'status' );
  page.updateRecord( );
}

//function func_record_deleteRecord(){
  //var page = new pageDefine( "/txn980325.do", "ɾ��ҵ���ɫ");
 // page.addParameter("record:roleid","primary-key:roleid");
  //page.addParameter("record:rolename","primary-key:rolename");
 // page.deleteRecord("�Ƿ�ɾ��ѡ�еļ�¼");
//}

function func_record_setFuncList(){
  var page = new pageDefine( "/txn980324.do", "���ý�ɫ��Ȩ��", "modal", 500, 500);
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
  		//if( flags[0] == '��ֹ' ){
  			//grid.setMenuCaption( 'record_updateStatus', "����" );
  		//}
  		//else{
  			//grid.setMenuCaption( 'record_updateStatus', "ͣ��" );
  		//}
  	//}
}
// ͣ���û�
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
	   alert("��ͣ�õĽ�ɫ�����ٴν��ã�");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
	var page = new pageDefine( "/txn980330.do", "���ý�ɫ");
	page.addValue(roleid,"primary-key:roleid");
	//page.addParameter("record:roleid","primary-key:roleid");
	page.addParameter("record:rolename","primary-key:rolename");
    page.addParameter("record:funclist","primary-key:funclist");
	//page.deleteRecord("�Ƿ�ͣ��ѡ�еĽ�ɫ��");
	if(confirm("�Ƿ�ͣ��ѡ�еĽ�ɫ��")){
	   page.goPage();	
	}else{
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 		   
	}
}

// �����û�
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
	   alert("�����õĽ�ɫ�����ٴ����ã�");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
    var page = new pageDefine( "/txn980331.do", "���ý�ɫ");
    page.addValue(roleid,"primary-key:roleid");
   // page.addParameter("record:roleid","primary-key:roleid");
    page.addParameter("record:rolename","primary-key:rolename");
    page.addParameter("record:funclist","primary-key:funclist");
    //page.deleteRecord("�Ƿ�����ѡ�еĽ�ɫ��");
    if(confirm("�Ƿ�����ѡ�еĽ�ɫ��")){
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
		var htm = "<a title='�޸�' onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'><div class='edit'></div></a>"+
		"<a title='��Ȩ' onclick='setCurrentRowChecked(\"record\");func_record_setFuncList();' href='#'><div class='authorize' ></div></a>&nbsp;"+
		"<a title='����' onclick='setCurrentRowChecked(\"record\");func_record_copyRecord();' href='#'><div class='copy' ></div></a>";
		if(states[i] == '1'){
			htm+='<a href="#" title="ͣ��" onclick="func_record_deleteRecord(\''+roleid[i]+'\');"><div class="run"></div></a>';
		}else{
			
			htm+='<a href="#" title="����" onclick="func_record_restarRecord(\''+roleid[i]+'\');"><div class="stop"></div></a>';
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
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
<%--   	<freeze:button name="select_submit" action="submit" caption="�� ѯ" align="right"/>
  	<freeze:button name="select_reset" action="reset" align="right"/>
  	<freeze:button name="select_goBack" action="close" align="right"/> --%>
    <freeze:text property="rolename" caption="��ɫ����" style="width:90%" datatype="string" maxlength="32"/>
    <freeze:text property="description" caption="��ɫ����" style="width:90%" datatype="string" maxlength="256"/>
    <!--  
    <freeze:text property="funclist" caption="���ܴ���" style="width:90%" datatype="string" maxlength="1024"/>
    <freeze:hidden property="groupname" caption="��ɫ�����Ĺ�����" style="width:90%"/>
    <freeze:select property="rolescope" caption="ʹ�÷�Χ" valueset="��ɫʹ�÷�Χ" style="width:90%"/>
    -->
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ɫ�б�" keylist="rolename" width="95%" navbar="bottom" checkbox="false" fixrow="false">
    <freeze:button name="record_addRecord" caption="����" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
    <freeze:hidden property="roleid" caption="��ɫ���" style="width:20%"/>
    <freeze:hidden property="status" caption="��Ч��־" style="width:10%" />
    <freeze:hidden property="funclist" caption="�����б�" style="width:10%"/>
    <freeze:cell property="@rowid" caption="���" style="width:5%" align="center"/>
    <freeze:cell property="rolename" caption="��ɫ����" style="width:12%" align="center"/>
    <freeze:cell property="description" caption="��ɫ����" style="width:25%"/>
    <freeze:cell property="rolescope" caption="��ɫʹ�÷�Χ" valueset="��ɫʹ�÷�Χ" style="width:20%" visible="false"/>
    <freeze:cell property="groupname" caption="��ɫ�����Ĺ�����" style="width:10%" visible="false"/>
    <freeze:cell property="homepage" caption="��ҳ��" style="width:20%" visible="false"/>
    <freeze:cell property="layout" caption="ҳ�沼��" style="width:10%" visible="false"/>
    <freeze:cell property="roletype" caption="��ɫ���ͣ�����" style="width:10%" visible="false"/>
    <freeze:cell property="roletype2" caption="��ɫ���ͣ�����" style="width:10%" visible="false"/>
    <freeze:cell property="status" caption="��Ч��־"  align="center" valueset="��Ч��־" style="width:5%" />
    <freeze:hidden property="maxcount" caption="����ѯ��¼��" style="width:8%" />
    <freeze:cell property="memo" caption="��ע" style="width:10%" />
    <freeze:cell property="regname" caption="ע���û�" style="width:10%" visible="false"/>
    <freeze:cell property="regdate" caption="ע������" style="width:10%" visible="false"/>
    <freeze:cell property="modname" caption="�޸��û�" style="width:10%" visible="false"/>
    <freeze:cell property="moddate" caption="�޸�����" style="width:10%" visible="false"/>
    <freeze:cell property="operation" caption="����" align="center" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
