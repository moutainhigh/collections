<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���Ӽ�����λ�����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '���������λ����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���������λ����' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '���������λ����' );	// /txn301051.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn301051.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

function   checkIt()       
 {   var loc=getFormFieldValue("record:dwlb_dm");
       if(!/^[a-z0-9A-Z]+$/.test(loc))  
          {alert("������λ�������в�Ӧ���������ַ������ֻ�Ϊ��"); 
          return false;}
          else
          {
          return true;
          }
     }
function   check()       
 {   var loc=getFormFieldValue("record:dwlb_cn_mc");
      if(/[\'@$!`~?#,;^&\*\.\\]+/.test(loc))  
          {alert("������λ������������в�Ӧ���������ַ���Ϊ��"); 
          return false;}
          else
          {
          return true;
          }
     }

     


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӽ�����λ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn301053">
  <freeze:block property="record" caption="���Ӽ�����λ�����Ϣ" width="95%" columns="1" nowrap="true">
      
      <freeze:button name="record_saveAndContinue" caption="����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndExit();"/>
      
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="dwlb_dm" caption="������λ������" validator="checkIt()"  datatype="string"  maxlength="2" minlength="2" style="width:95%"/>
      <freeze:text property="dwlb_cn_mc" caption="������λ�����������" validator="check()" datatype="string" maxlength="255" minlength="1" colspan="2" style="width:95%"/>
      <freeze:text property="dwlb_cn_ms" caption="������λ�����������" datatype="string" maxlength="255" colspan="2" style="width:95%"/>
      <freeze:text property="dwlb_en_mc" caption="������λ���Ӣ������" datatype="string" maxlength="255" colspan="2" style="width:95%"/>
      <freeze:text property="dwlb_en_ms" caption="������λ���Ӣ������" datatype="string" maxlength="255" colspan="2" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
