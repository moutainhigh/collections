<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="600" height="350">
<head>
<title>�鿴��������Ԫ</title>
<style>
body{
background:#ffffff;
}
</style>
</head>

<script language="javascript">

// ����
function func_record_goBack()
{
    goBack();
}

// �� ��
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{

}

_browse.execute( __userInitPage );

</script>
<freeze:body>
	<freeze:title caption="�鿴��������Ԫ" />
	<freeze:errors />

	<freeze:form action="/txn7000306">
		<freeze:block property="record"  caption="�鿴��������Ԫ"
			captionWidth="0.45" width="95%">
			<freeze:hidden property="sys_rd_standard_dataelement_id" caption="��������ԪID"
				datatype="string" style="width:95%" />

			<freeze:cell property="identifier" caption="��ʶ��" datatype="string"
				style="width:95%" />
			<freeze:cell property="standard_category" caption="�淶����"
				valueset="�淶����" style="width:95%" />

			<freeze:cell property="cn_name" caption="��������" style="width:95%" />
			<freeze:cell property="en_name" caption="Ӣ������" style="width:95%" />

			<freeze:cell property="column_nane" caption="�ֶ���" datatype="string"
				style="width:95%" />
			<freeze:cell property="data_type" caption="��������" valueset="�ֶ���������"
				style="width:95%" />

			<freeze:cell property="data_format" caption="��ʽ" datatype="string"
				style="width:95%" />
			<freeze:cell property="value_domain" caption="ֵ��" datatype="string"
				style="width:95%" />

			<freeze:cell property="unit" caption="������λ" datatype="string"
				style="width:95%" />
			<freeze:cell property="synonyms" caption="ͬ���" datatype="string"
				style="width:95%" />

			<freeze:cell property="version" caption="�汾" datatype="string"
				style="width:95%" />
			<freeze:cell property="representation" caption="˵��" datatype="string"
				style="width:95%" />

			<freeze:cell property="memo" caption="��ע" style="width:98%" />
			
		</freeze:block>
		<p align="center" class="print-menu">
			<!--  <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
			
		<table cellspacing="0" cellpadding="0" class="button_table">
			<tr>
				<td class="btn_left"></td>
				<td>
					<input type="button" name="record_goBackNoUpdate" value="�ر�"
						class="menu" onclick="func_record_goBackNoUpdate();"
						style='width: 60px'>
				</td>
				<td class="btn_right"></td>
			</tr>
		</table>
		</p>
		<!--  <p align="center" class="print-hide">&nbsp;</p>-->
	</freeze:form>
</freeze:body>
</freeze:html>