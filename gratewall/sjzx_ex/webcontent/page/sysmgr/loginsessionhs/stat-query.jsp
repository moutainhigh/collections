<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>�û�����ͳ��</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<freeze:body>
<freeze:title caption="�û�����ͳ��"/>
<freeze:errors/>

<script language="javascript">
//����ѡ��
function sjjg_select(){
	selectJG("tree","select-key:jgid_fk","select-key:orgname");
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{

}

function func_doQuery(){
	var jgid_fk = getFormFieldValue("select-key:jgid_fk");
	var login_date_from = getFormFieldValue("select-key:login_date_from");
	var login_date_to = getFormFieldValue("select-key:login_date_to");
	if(!jgid_fk && !login_date_from && !login_date_to ){
		alert("��ʾ��������������һ����ѯ����");
		return false;
	}
	
	var startObj = document.getElementById("select-key:login_date_from");	
	var endObj = document.getElementById("select-key:login_date_to");
	
	if(!checkInput(startObj,'����¼���ڡ�','3',false)||!checkInput(endObj,'����¼���ڡ�','3',false)){
	    return false;
	}
	
	var start = startObj.value;
	var end = endObj.value;
	if(start){
		var s_date = new Date();
		var currYear = s_date.getYear();
		var currMonth = s_date.getMonth() + 1;
		var currDay = s_date.getDate();
		var today = currYear+'-';
		if(currMonth>9){
	    today += currMonth+'-';
		}else{
	    today += '0'+currMonth+'-';
		}
		if(currDay>9){
	    today += currDay;
		}else{
	    today += '0'+currDay;
		}
		if(start>today){
            alert("����¼���ڡ���ʼ���ڲ��ܳ������죡");
            document.getElementById("select-key:login_date_from").select();
            return false;
		}		
	}
	
	if(start&&end){
        if(start>end){
            alert("����¼���ڡ��������ڱ�����ڿ�ʼ���ڣ�");
            document.getElementById("select-key:login_date_to").select();
            return false;
        }		
	}
	
	
  var page = new pageDefine( "/txn81100002.do", "�û�����ͳ��");
  page.addParameter("select-key:jgid_fk","select-key:jgid_fk");
  page.addParameter("select-key:orgname","select-key:orgname");
  page.addParameter("select-key:login_date_from","select-key:login_date_from");
  page.addParameter("select-key:login_date_to","select-key:login_date_to");
  page.goPage( );
}

function reset(){
	setFormFieldValue("select-key:jgid_fk", 0, "");
	setFormFieldValue("select-key:orgname", 0, "");
	setFormFieldValue("select-key:login_date_from", 0, "");
	setFormFieldValue("select-key:login_date_to", 0, "");
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn81100002">
  <freeze:block property="select-key" caption="�û�����ͳ��"  width="95%">   
    <freeze:button name="record_addRecord" caption=" �� ѯ " enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
    <freeze:button name="record_resetRecord" caption=" �� �� " enablerule="0" hotkey="ADD" align="right" onclick="reset();"/>
    <freeze:text property="orgname" caption="��������" style="width:70%" readonly="true" postfix="��<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select();'>ѡ��</a>��" />
    <freeze:datebox property="login_date_from"  caption="��¼����" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%" notnull="true"/>
        </td><td width='5%'>��</td><td width='45%'>
      <freeze:datebox property="login_date_to" caption="��¼����" style="width:100%" colspan="0" notnull="true"/>
        </td></tr></table>
    <freeze:hidden property="jgid_fk"></freeze:hidden>     
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="�û�����ͳ���б�" width="95%" keylist="yhid_pk" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:cell property="@rowid" caption="���" align="middle" style="width:6%"/>
    <freeze:cell property="jgmc_all" caption="��������" width="40%" />
    <freeze:cell property="yhxm" caption="�û�����" width="30%" />
    <freeze:cell property="login_times" caption="���ʴ���" width="24%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
