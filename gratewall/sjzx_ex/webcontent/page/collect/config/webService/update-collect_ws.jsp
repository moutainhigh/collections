<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<script
	src="<%=request.getContextPath()%> /script/common/js/validator.js"></script>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
	<title>�޸�webservice������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '���淽��' );	
}

// �� ��
function func_record_goBack()
{
	goBack();
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:errors />

	<freeze:form action="/txn30102113">
		<freeze:frame property="primary-key" width="95%">
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID"
				style="width:95%" />
		</freeze:frame>
		<freeze:block property="record" caption="�޸ķ�����Ϣ" width="95%">
			<freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE"
				onclick="func_record_saveAndExit();" />
			<freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE"
				onclick="func_record_goBack();" />
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="collect_task_id" caption="�ɼ�����ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="service_targets_id" caption="����ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:cell property="service_no" caption="����ID��" datatype="string"
				style="width:95%" />
			<freeze:hidden property="service_no" caption="����ID��" datatype="string"
				style="width:95%" />
			<freeze:cell property="method_name_en" caption="�������ƣ�" notnull="true"
				datatype="string" style="width:95%" />
			<freeze:hidden property="method_name_en" caption="�������ƣ�" notnull="true"
				datatype="string" style="width:95%" />
			<freeze:text property="method_name_cn" caption="������������"
				notnull="true" datatype="string" style="width:95%" />
			<freeze:browsebox property="collect_table" caption="��Ӧ�ɼ���"
				show="name" notnull="true" valueset="��Դ����_��������Ӧ�ɼ���"
				parameter="getParameter();" style="width:95%" />
			<freeze:select property="collect_mode" caption="�ɼ���ʽ" show="name"
				notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%" />
			<freeze:hidden property="is_encryption" caption="�Ƿ����"
				datatype="string" style="width:95%" />
			<freeze:select property="encrypt_mode" caption="���ܷ���" notnull="true"
				show="name" valueset="��Դ����_���ܷ���" value="01" style="width:95%" />
			<freeze:text property="web_name_space" caption="�����ռ�" colspan="2"
				notnull="false" style="width:95%" />
			<freeze:textarea property="method_description" caption="��������"
				colspan="2" rows="2" style="width:98%" />
			<freeze:hidden property="method_status" caption="����״̬"
				datatype="string" style="width:95%" />
		</freeze:block>
	</freeze:form>
</freeze:body>
</freeze:html>
