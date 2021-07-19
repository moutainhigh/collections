<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="800" height="550">
<head>
<title>��������Դ��Ϣ</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '��������Դ��' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '��������Դ��' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '��������Դ��' );	// /txn20102001.do
}

// �� ��
function func_record_goBack()
{
	goBack("/txn20102001.do");	// /txn20102001.do
}

// ��������
function func_test_connection()
{
	
}

function funTBChanged(){
	var type = getFormFieldValue('record:data_source_type');
	
	if(type=='00'){
	//setDisplay("1",true);
	setDisplay("4",false);
	}else {
	//setDisplay("1",false);
	//setDisplay("2",true);
	}
}

function setDisplay(id,b){
	
	var preStr = "row_"+id;
	if(b=='true'||b==true){
		document.getElementById(preStr).style.display='block';
		var cells = document.getElementById(preStr).cells;
		for(var ii=0;ii<cells.length;ii++){
			cells[ii].className='odd1_b';
		}
	}else{
		document.getElementById(preStr).style.display='none';
	}
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$(".radioNew").each(function(index){
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).css("background-position-y","top");
			$(this).prev()[0].click();
		});
		$(this).next()[0].onclick=function(){
				$(this).prev()[0].click();
				$(".radioNew").css("background-position-y","bottom");
				$($(".radioNew")[index]).css("background-position-y","top");
			};
	});
	for(var ii=0;ii<2;ii++){
		if($($(".radioNew")[ii]).prev()[0].checked){
			$($(".radioNew")[ii]).css("background-position-y","top");
		}
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body onload="funTBChanged()">
<freeze:title caption="��������Դ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20102003">
  <freeze:block property="record" caption="��������Դ��Ϣ" width="95%">
  	  <freeze:button name="testConnection" caption="��������"  onclick="func_test_connection();"/>
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:radio property="data_source_type" caption="����Դ����" valueset="��Դ����_����Դ����" value="00" onclick="funTBChanged();" style="width:95%" colspan="2"/>
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" valueset="��Դ����_�����������"  style="width:95%" notnull="true"/>
      <freeze:text property="data_source_name" caption="����Դ����" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      <freeze:text property="db_username" caption="����Դ�û���" datatype="string" maxlength="50" style="width:95%" notnull="true"/>
      <freeze:password property="db_password" caption="����Դ����" maxlength="50" notnull="true" style="width:95%" />
      <freeze:text property="data_source_ip" caption="����ԴIP" datatype="string" maxlength="50" style="width:95%" notnull="true"/>
      <freeze:text property="access_port" caption="���ʶ˿�" datatype="string" maxlength="8" style="width:95%" notnull="true"/>
      
      <freeze:text property="db_type" caption="���ݿ�����" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="db_instance" caption="���ݿ�ʵ��" datatype="string" maxlength="30" style="width:95%"/>
      
      <freeze:text property="access_url" caption="����URL" datatype="string" notnull="" maxlength="255" colspan="2" style="width:98%"/>
      
      <freeze:textarea property="db_desc" caption="����Դ����" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      
      
      <freeze:hidden property="db_status" caption="����Դ״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>  
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
