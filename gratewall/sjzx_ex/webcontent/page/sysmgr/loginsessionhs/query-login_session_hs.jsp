<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>��ѯϵͳ�����û��б�</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<freeze:body>
<freeze:title caption="��ѯϵͳ�����û��б�"/>
<freeze:errors/>

<script language="javascript">
function func_record_addRecord(){
  var page = new pageDefine( "/txn981214.do", "�鿴ҵ����־��Ϣ", "modal", 650, 400);
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage( );
}
//����ѡ��
function sjjg_select(){
selectJG("tree","select-key:jgid_fk","select-key:orgname");
}

function selectYh(){
    var parameter = getFormFieldValue('select-key:jgid_fk');
    if(parameter==""){
      alert("����ѡ�����");
      return;
    }
	return 'jgid_fk=' + parameter;
}
function resetYu(){
    setFormFieldValue('select-key:opername',0,"");
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_addRecord();' href='#'>�鿴��־</a>";
	}		
}

function func_doQuery(){
	document.forms[0].submit();
}

function clearFormFieldValue(){
	document.forms[0].reset();
	document.getElementById("select-key:orgname").value = "";
	document.getElementById("select-key:username").value = "";
	document.getElementById("select-key:jgid_fk").value = "";
	document.getElementById("select-key:opername").value = "";
	
	setFormFieldValue("select-key:count_date",' ');
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn81100001">
  <freeze:block theme="query" property="select-key" caption="��ѯϵͳ�����û�"  width="95%">   
    <freeze:button name="record_addRecord" caption=" �� ѯ " txncode="60210003" enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
    <freeze:button name="record_restRecord" caption=" �� �� " txncode="60210003" enablerule="0" hotkey="RESET" align="right" onclick="reset();"/>
    <freeze:text property="orgname" caption="��������" style="width:70%"  readonly="true" postfix="��<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select();resetYu();'>ѡ��</a>��"></freeze:text>
    <freeze:hidden property="jgid_fk"></freeze:hidden>
    <freeze:select property="rolenames" caption="��ɫ" valueset="�û���ɫ��Ϣ" style="width:90%" show="code"/>
    <freeze:text property="opername" caption="�û�����" style="width:90%" datatype="string" /> 
    <freeze:text property="username" caption="�û��ʺ�" style="width:90%" datatype="string" />   
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="ϵͳ�����û�" keylist="flowno" width="95%" rowselect="false" multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:cell property="yhzh" caption="�û��ʺ�" width="10%" />
    <freeze:cell property="yhxm" caption="�û�����" width="10%" />
    <freeze:cell property="rolenames" caption="��ɫ" />
    <freeze:cell property="jgname" caption="��������" width="18%"/>
    <freeze:cell property="ipaddress" caption="IP��ַ" width="13%"/>
    <freeze:cell property="login_time" caption="��¼ʱ��" datatype="date" style="width:140px"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
