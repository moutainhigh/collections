<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="650" height="200">
<head>
	<title>�鵵������־</title>
</head>

<script language="javascript">


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

function func_record_goBack()
{
	goBack();	
}

function func_record_saveAndExit()
{
	//saveAndExit( '', '�鵵������־' );
	if(check()){
		
		var page = new pageDefine( "/txn6030014.ajax", "�鵵������־" );
		page.addParameter("record:created_time_start","record:created_time_start");
		page.addParameter("record:created_time_end","record:created_time_end");
		page.callAjaxService("checkBack");
	}
}

function checkBack(errorCode, errDesc, xmlResults){
	var count = _getXmlNodeValue(xmlResults,"record:count");
	alert("���ι��鵵������־ ��"+count+"�� ��");
	//goBackWithUpdate();
	//window.close();
	saveAndExit( '', '�鵵������־' );
	
}
function check(){
		var start = document.getElementById("record:created_time_start").value;	
		var end = document.getElementById("record:created_time_end").value;

		if(start&&end){
            if(start>end){
                alert("�������ڱ�����ڿ�ʼ���ڣ�");
                document.getElementById("record:created_time_end").select();
                return false;
            }		

		}else{
			alert("��ʼ���ںͽ������ڶ��Ǳ����");
			return false
		}
		return true;
}
_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
	<freeze:title caption="�鵵������־" />
	<freeze:errors />

	<freeze:form action="/txn6030014">

		<freeze:block property="record" caption="�޸Ĺ��ܴ�С���Ӧ��Ϣ" width="95%">
			<freeze:button name="record_saveAndExit" caption="�� ��"
				hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();" />
			<freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE"
				onclick="func_record_goBack();" />

			<freeze:datebox property="created_time_start" caption="��ʼʱ��"
				datatype="string" notnull="true" maxlength="30" style="width:95%" />
			<freeze:datebox property="created_time_end" caption="����ʱ��"
				datatype="string" notnull="true" maxlength="30" style="width:95%" />
		</freeze:block>

	</freeze:form>
</freeze:body>
</freeze:html>
