<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-1/detail-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸ļ�����λ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '���������λ��' );	// /txn301061.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn301061.do
}

// �����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

function   checkIt()       
 {   var loc=getFormFieldValue("record:jldw_dm");
       if(!/^[a-z0-9A-Z]+$/.test(loc))  
          {alert("������λ�������в�Ӧ���������ַ������ֻ�Ϊ��"); 
          return false;}
          else
          {
          return true;
          }
     }
function   checkMe()       
 {   var loc=getFormFieldValue("record:jldw_cn_mc");
       if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(loc)) 
          {alert("������λ������������в�Ӧ���������ַ���Ϊ��"); 
          return false;}
          else
          {
          return true;
          }
     }
     
function   check()       
 {   var loc=getFormFieldValue("record:jldw_cn_mc");
       if(!/^[\u4e00-\u9fa5](\s*[\u4e00-\u9fa5])*$/.test(loc))  
          {alert("������λ����������Ӧֻ���뺺��"); 
          return false;}
          else
          {
          return true;
          }
     }

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ļ�����λ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn301062">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="jldw_dm" caption="������λ����" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ļ�����λ��Ϣ" width="95%" columns="1" nowrap="true">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="jldw_dm" caption="������λ����" validator="checkIt()" datatype="string" maxlength="4" minlength="4" readonly="true" style="width:95%"/>
      <freeze:hidden property="dwlb_dm" caption="��λ������" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="jldw_cn_mc" caption="������λ��������" validator="checkMe()" datatype="string" maxlength="255" minlength="1" colspan="2" style="width:95%"/>
      <freeze:text property="jldw_sjz" caption="������λ����ֵ" notnull="true" datatype="double" style="width:95%"/>
      <freeze:text property="jldw_en_mc" caption="������λӢ������" datatype="string" maxlength="255" colspan="2" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>