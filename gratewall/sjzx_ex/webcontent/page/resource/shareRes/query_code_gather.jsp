<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>���뼯��ѯ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '�������' );	// /txn555551.do
}

// �� ��
function func_record_goBack()
{
	window.close();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴ҵ����뼯"/>
<freeze:errors/>

<freeze:form action="/txn20301026">

  <freeze:grid property="record" caption="��ѯҵ����뼯�б�"  width="95%" checkbox="false" navbar="bottom" fixrow="false" >
			
			<freeze:cell property="jcsjfx_dm" caption="����" style="width:20%" align="center"/>
			<freeze:cell property="jcsjfx_mc" caption="����ֵ" style="width:75%" align="left"/>
			
		</freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
