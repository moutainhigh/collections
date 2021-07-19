<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>ʹ�����ͳ��</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
#navbar{
	display:none;
}
</style>
</head>

<freeze:body>
<freeze:title caption="ʹ�����ͳ��"/>
<freeze:errors/>

<script language="javascript">
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{

}

function func_doQuery(){
    /*
	var startDate = getFormFieldValue("select-key:startDate");
	var endDate = getFormFieldValue("select-key:endDate");
	if(!startDate && !endDate ){
		alert("��ʾ��������������һ����ѯ����");
		return false;
	}*/
	
	var startObj = document.getElementById("select-key:startDate");	
	var endObj = document.getElementById("select-key:endDate");
	
	if(!checkInput(startObj,'�����ڡ�','3',false)||!checkInput(endObj,'�����ڡ�','3',false)){
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
            alert("����ʼ���ڡ����ܳ������죡");
            document.getElementById("select-key:startDate").select();
            return false;
		}		
	}
	
	if(start&&end){
        if(start>end){
            alert("�����ڡ��������ڱ�����ڿ�ʼ���ڣ�");
            document.getElementById("select-key:endDate").select();
            return false;
        }		
	}
	
	
  var page = new pageDefine( "/txn81100003.do", "ʹ�����ͳ��");
  page.addParameter("select-key:startDate","select-key:startDate");
  page.addParameter("select-key:endDate","select-key:endDate");
  page.goPage( );
}

function reset(){
	setFormFieldValue("select-key:startDate", 0, "");
	setFormFieldValue("select-key:endDate", 0, "");
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn81100002">
  <freeze:block property="select-key" caption="�û�����ͳ��"  width="95%">   
    <freeze:button name="record_addRecord" caption=" �� ѯ " enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
    <freeze:button name="record_resetRecord" caption=" �� �� " enablerule="0" hotkey="ADD" align="right" onclick="reset();"/>
    
    <freeze:datebox property="startDate" caption="������" style="width:90%" datatype="date"/> 
    <freeze:datebox property="endDate" caption="��" style="width:90%" datatype="date"/>   
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="ʹ�����ͳ���б�" width="95%" keylist="jgid_pk" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:cell property="@rowid" caption="���" align="middle" style="width:6%"/>
    <freeze:cell property="jgmc" caption="��������" width="20%" />
    <freeze:cell property="query_times" caption="���ݲ�ѯ����" width="18%" />
    <freeze:cell property="login_times" caption="��¼����" width="18%"/>
    <freeze:cell property="download_times" caption="���ش���" width="18%"/>
    <freeze:cell property="download_total" caption="��������" width="18%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
