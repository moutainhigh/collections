<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>����ҵ���ֶ���Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����ҵ���ֶζ���' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����ҵ���ֶζ���' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ҵ���ֶζ���' );	// /txn30403001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30403001.do
}

function getParameter()
{
	var sys_id = getFormFieldValue("record:sys_id");
	if (sys_id == null || sys_id == "")
	{
	    alert("��ѡ��ҵ������");
	    return;
	}else{
		var parameter = 'sys_id=' + sys_id;
		return parameter;
	}
}

function resetTableNo()
{
	if (getFormFieldValue("record:table_no") == null || getFormFieldValue("record:table_no") == "")
	{
	}
	else{
		setFormFieldValue("record:table_no",0,"");
	}
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var sys_no = getFormFieldValue("record:sys_no");
	var table_no = getFormFieldValue("record:table_no");
	if (sys_no == null || sys_no == "")
	{
		resetTableNo();
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����ҵ���ֶ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30403003">
  <freeze:block property="record" caption="����ҵ���ֶ���Ϣ" width="95%" height="80">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:browsebox property="sys_id" caption="ҵ������" notnull="true" valueset="ҵ��ϵͳ" show="name" style="width:95%" data="code" onchange="resetTableNo()"/>
      <freeze:browsebox property="table_no" caption="��������" notnull="true" valueset="ҵ��������ձ�" show="name" data="code" style="width:95%" parameter="getParameter()"/>
      <freeze:text property="column_name" caption="�ֶ���" datatype="string" maxlength="60" minlength="1" style="width:95%"/>
      <freeze:text property="column_name_cn" caption="�ֶ�������" datatype="string" maxlength="100" minlength="1" style="width:95%"/>
      <freeze:hidden property="column_order" caption="����˳��" datatype="number" maxlength="8" minlength="1" style="width:95%"/>
      <freeze:select property="edit_type" caption="�ֶ�����" notnull="true" valueset="�ֶ�����" style="width:95%"/>
      <freeze:text property="edit_content" caption="�ֶγ���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="demo" caption="��ע" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br/>
  <div align="center">
	  <div style="width:95%;" align="left">
	  	<h3>ʹ��˵����</h3>
	  	<ul>
			<li>1������Ǵ����ֶΣ����ڱ�ע����д�������CA01��</li>
		</ul>
	  </div>
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
