<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>���ӹ��ܽ�����Ϣ</title>
</head>

<freeze:body>
<freeze:title caption="���ӹ��ܽ�����Ϣ"/>
<freeze:errors/>

<script language="javascript">
function func_record_saveRecord(){
  saveRecord( "", "���湦�ܽ�����Ϣ" );
}

function func_record_saveAndContinue(){
  saveAndContinue( "", "���湦�ܽ�����Ϣ������" );
}

function func_record_saveAndExit(){
  saveAndExit( "", "���湦�ܽ�����Ϣ������", "/txn980311.do" );
}

function func_record_goBack(){
  goBack( "/txn980311.do" );
}

// ���ؽ�����Ϣ
function loadTxnInfo()
{
	var txncode = getFormFieldValue( 'record:txncode' );
	beginLoadData( 'txninfo', 'iface=' + txncode );
}

function getTxnCode()
{
	return null;
}

function saveData(errCode, errDesc)
{
	if( errCode != '000000' ){
		alert( errCode + ':' + errDesc );
	}
	else{
		var desc = txninfo[0][9];
		var txnType = txninfo[0][3];
		if( txnType == 'system' ){
			alert( 'ϵͳ����[' + txninfo[0][2] +':' + desc + ']����Ҫ��Ȩ' );
			setFormFieldValue( 'record:txncode', 0, '' );
		}
		else if( txnType != 'check' ){
			alert( '��������[' + txninfo[0][2] +':' + desc + ']����Ҫ��Ȩ' );
			setFormFieldValue( 'record:txncode', 0, '' );
		}
		else{
			setFormFieldValue( 'record:txnname', 0, desc );
		}
	}
}
</script>

<freeze:form action="/txn980313">
  <freeze:block property="record" caption="���ӹ��ܽ�����Ϣ" width="95%" columns="1" captionWidth="0.2">
    <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="funccode" caption="���ܴ���" style="width:90%"/>
    <freeze:text property="txncode" caption="���״���" style="width:40%" datatype="string" maxlength="6" minlength="1" onchange="loadTxnInfo()"/>
    <freeze:text property="txnname" caption="��������" style="width:90%" datatype="string" maxlength="64"/>
    <freeze:hidden property="status" caption="״̬" style="width:40%" value="1"/>
    <freeze:text property="memo" caption="��ע��Ϣ" style="width:90%" datatype="string" maxlength="256"/>
  </freeze:block>
  
  <freeze:data name="txninfo" property="action" fields="" txnCode="iface" parameter="getTxnCode()" onload="saveData(errCode, errDesc)"/>
</freeze:form>
</freeze:body>
</freeze:html>
