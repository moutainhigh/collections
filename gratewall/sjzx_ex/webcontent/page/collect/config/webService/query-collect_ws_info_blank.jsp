<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<script
	src="<%=request.getContextPath()%> /script/common/js/validator.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
	<title>�޸�webservice������Ϣ</title>
</head>

<script language="javascript">



function getParameter()
{
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

	<freeze:form action="/txn30102111">
		<freeze:frame property="select-key" width="95%">
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID"
				style="width:95%" />
		</freeze:frame>
		
		<freeze:block property="record" caption="�޸ķ�����Ϣ" width="95%">
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="collect_task_id" caption="�ɼ�����ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="service_targets_id" caption="����ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:cell property="service_no" caption="����ID��" datatype="string"
				style="width:95%" />
			<freeze:cell property="method_name_en" caption="�������ƣ�"
				datatype="string" style="width:95%" />
			<freeze:cell property="method_name_cn" caption="������������"
				datatype="string" style="width:95%" />
			<freeze:cell property="collect_table" caption="��Ӧ�ɼ���" show="name"
				notnull="true" valueset="��Դ����_�ɼ���" style="width:95%" />
			<freeze:cell property="collect_mode" caption="�ɼ���ʽ" show="name"
				notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%" />
			<freeze:hidden property="is_encryption" caption="�Ƿ����"
				datatype="string" style="width:95%" />
			<freeze:cell property="encrypt_mode" caption="���ܷ���" notnull="true"
				show="name" valueset="��Դ����_���ܷ���" value="01" style="width:95%" />
			<freeze:cell property="method_description" caption="��������" colspan="2"
				style="width:98%" />
			<freeze:cell property="web_name_space" caption="�����ռ䣺" colspan="2"
				style="width:95%" />
			<freeze:hidden property="method_status" caption="����״̬"
				datatype="string" style="width:95%" />
		</freeze:block>
		<br>
		<freeze:grid property="dataItem" caption="�����б�"
			keylist="webservice_patameter_id" multiselect="false"
			checkbox="false" width="95%" fixrow="false">
			
			<freeze:hidden property="webservice_patameter_id" caption="����ID" />
			<freeze:hidden property="webservice_task_id" caption="����ID" />
			<freeze:cell property="patameter_type" caption="��������"
				style="width:25%"  align="center"/>
			<freeze:cell property="patameter_name" caption="������"
				style="width:40%"  align="center"/>
			<freeze:cell property="patameter_style" caption="������ʽ"
				valueset="�ɼ�����_������ʽ" style="width:20%"  align="center" />
			<freeze:cell property="oper" caption="����" align="center"
				style="width:15%" />
		</freeze:grid>
		
	</freeze:form>
</freeze:body>
</freeze:html>
