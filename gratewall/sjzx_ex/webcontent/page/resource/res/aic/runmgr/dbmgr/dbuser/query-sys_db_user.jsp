<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ݿ⹲������б�</title>
</head>

<script language="javascript">

// ���ӷ������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_db_user.jsp", "�������ݿ⹲�����", "modal" ,650,350);
	page.addRecord();
}

// �޸����ݿ⹲�����
function func_record_updateRecord(index)
{
    var id = getFormFieldText("record:sys_db_user_id",index);
	var page = new pageDefine( "/txn52101004.do", "�޸����ݿ⹲�����", "modal" ,650,350);
	page.addValue( id, "record:sys_db_user_id" );
	page.updateRecord();
}

// ɾ�����ݿ⹲�����
function func_record_deleteRecord()
{
	var page = new pageDefine( "txn52101005.do", "ɾ�����ݿ⹲�����" );
	page.addParameter( "record:sys_db_user_id", "record:sys_db_user_id" );
	page.addParameter( "record:login_name", "record:login_name" );
	page.addParameter("record:user_type","record:user_type");
	page.deleteRecord( "�Ƿ�ɾ��ѡ�е��û�?" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
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
			operationSpan[i].innerHTML += "<a onclick='func_record_updateRecord(" + i + ");' href='#'>�޸�</a>";
		}else{
	    	operationSpan[i].innerHTML += "<a onclick='func_record_updateRecord(" + i + ");' href='#'>�޸�</a>&nbsp;&nbsp;<a onclick='func_viewConfig(" + i + ");' href='#'>��ͼ����</a>&nbsp;&nbsp;<a onclick='func_selfConfig(" + i + ");' href='#'>�������</a>";
	    }
	    flagBoxs[i].attachEvent("onclick", checkButtonByData);
	}
	document.getElementById("record:_select-all").attachEvent("onclick", checkButtonByData);	
}

function changeState(){
	var page = new pageDefine( arguments[0], "�ı����ݿ⹲�����״̬");
	page.addParameter("record:sys_db_user_id","record:sys_db_user_id");
	page.addParameter("record:login_name","record:login_name");
	page.addParameter("record:user_type","record:user_type");
    if(arguments[1]=='1'){
       page.deleteRecord("ȷ�����ã�");
    }else{
       page.deleteRecord("ȷ��ͣ�ã�");
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
	var page = new pageDefine( "txn52101010.ajax", "��ʼ�����ݿ⹲���������");
	page.addParameter("record:sys_db_user_id","record:sys_db_user_id");
	page.addParameter("record:login_name","record:login_name");
	page.addParameter("record:user_type","record:user_type");
	page.callAjaxService("initResponse");
}

function func_viewConfig(index){
    var state = getFormFieldText("record:state",index);
    var name = getFormFieldText("record:login_name",index);
	if(state == "0"){
		var page = new pageDefine( "../dbcfg/main-config.jsp", "��ѯ��ͼ����" );
		page.addValue(name, "record:login_name");
		page.goPage();
	}else{
		alert("��ǰ�û��ѱ�ͣ�ã��������ô��û���");
	}    
}

function func_selfConfig(index){
    var state = getFormFieldText("record:state",index);
    var id = getFormFieldText("record:sys_db_user_id",index);
	if(state == "0"){
		var page = new pageDefine( "/txn52103011.do", "��������б�" );
		page.addValue(id, "select-key:sys_db_user_id");
		page.goPage();
	}else{
		alert("��ǰ�û��ѱ�ͣ�ã��������ô��û���");
	}    
}

function initResponse(errCode, errDesc, xmlResults){
	if(errCode != '000000'){
      alert("�������"+errDesc);
    }else{
      alert("�����ɹ���");
    }
    clickFlag=0;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ݿ⹲������б�"/>
<freeze:errors/>

<freeze:form action="/txn52101001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="login_name" caption="�û����룺" datatype="string" maxlength="20" style="width:60%"/>
      <freeze:select property="state" caption="�û�״̬��" valueset="user_state" style="width:60%"/>
      <freeze:select property="user_type" caption="�û����ͣ�" valueset="user_type" style="width:60%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="���ݿ⹲������б�" keylist="sys_db_user_id" width="95%" navbar="bottom" fixrow="false" rowselect="false">
      <freeze:button name="record_addRecord" caption="����"  enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�"  enablerule="1" align="right" onclick="func_record_updateRecord();" visible="false"/>
      <freeze:button name="record_startRecord" caption="����"  enablerule="2" align="right" onclick="changeState('txn52101006.do','1');"/>
      <freeze:button name="record_stopRecord" caption="ͣ��"  enablerule="2" align="right" onclick="changeState('txn52101007.do','0');"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��"  enablerule="2" align="right" onclick="func_record_deleteRecord();"/>
      
      <freeze:button name="record_initPwd" caption="�����ʼ��"  enablerule="2" align="right" onclick="func_record_initPwd();" visible="false"/>
      
      <freeze:hidden property="sys_db_user_id" caption="���ݿ⹲�������"/>
      <freeze:hidden property="state" caption="״̬"/>
      <freeze:hidden property="login_name" caption="�û���"/>
      <freeze:hidden property="user_name" caption="����" />
      <freeze:hidden property="user_type" caption="�û�����"/>
      <freeze:hidden property="grant_table" caption="��Ȩ��"/>
      <freeze:hidden property="hasConfig" caption="�Ƿ�������ͼ"/>
      <freeze:cell property="@rowid" caption="���" align="center" style="width:4%"/>
      <freeze:cell property="login_name" caption="�û�����" style="width:10%" align="left"/>
      <freeze:cell property="user_name" caption="�û�����" style="width:14%" align="left" />
      <freeze:cell property="password" caption="�û�����" style="width:8%" align="left" />
      <freeze:cell property="create_by" caption="������" style="width:10%" align="left"/>
      <freeze:cell property="create_date" caption="��������" style="width:10%" align="left" />
      <freeze:cell property="user_type" caption="�û�����" style="width:8%" align="left" valueset="user_type" />
      <freeze:cell property="state" caption="״̬" style="width:8%" align="left" valueset="user_state" />
      <freeze:cell property="hasConfig" caption="�Ƿ�����" style="width:8%" align="left" valueset="��������" />
	  <freeze:cell property="oper" caption="����" align="left" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
