<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ɼ���־�б�</title>
</head>

<script language="javascript">


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
	var value=getFormAllFieldValues("record:collect_joumal_id");
	
	for(var i=0; i<value.length; i++){
		var val=value[i];
	
	document.getElementsByName("span_record:oper")[i].innerHTML='<a href="javascript:func_record_querycode(\''+val+'\');" title="" ><div class="detail"></div></a>';
	
    }
    /*
	var date_s = document.getElementsByName("span_record:task_start_time");
	for(var ii=0; ii<date_s.length; ii++){
		date_s[ii].innerHTML = date_s[ii].innerHTML.substr(0,10);
	}
	var date_s1 = document.getElementsByName("span_record:task_end_time");
	for(var ii=0; ii<date_s1.length; ii++){
		date_s1[ii].innerHTML = date_s1[ii].innerHTML.substr(0,10);
	}
	*/
}

function func_record_querycode(collect_joumal_id){
    var url="txn6011006.do?select-key:collect_joumal_id="+collect_joumal_id;
    var page = new pageDefine( url, "��ѯ�ɼ���־�б�","modal", 750, 300);
    page.goPage();
}

function check(){
		var start = document.getElementById("select-key:created_time_start").value;	
		var end = document.getElementById("select-key:created_time_end").value;
		if(start&&end){
            if(start>end){
                alert("��ʱ�䷶Χ���������ڱ�����ڿ�ʼ���ڣ�");
                document.getElementById("select-key:created_time_end").select();
                return false;
            }		

		}
		return true;
}
function submitForm(){
	if(!check()){
		return false;
	}
}

// �鵵�ɼ���־
function func_record_addRecord()
{
	var page = new pageDefine( "update-collect_archive.jsp", "�ɼ���־�鵵","modal");
	page.addRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<!-- <freeze:button name="record_addRecord" caption="�鵵"  enablerule="0"  align="right" onclick=""/>
 <freeze:select property="return_codes" caption="ִ��״̬"  valueset="��־��ѯ_ִ��״̬" style="width:95%"/>
 -->
<freeze:body>
<freeze:title caption="��ѯ�ɼ���־�б�"/>
<freeze:errors/>

<freeze:form action="/txn6011011" onsubmit="return submitForm();">

  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:browsebox property="service_targets_name" caption="�������"  valueset="��Դ����_�����������"  show="name" style="width:95%"/>
      <freeze:text property="task_name" caption="��������" style="width:95%" />
      <freeze:datebox property="created_time_start" caption="ʱ�䷶Χ"
				datatype="string" maxlength="30"
				prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>"
				style="width:100%" />
			</td>
			<td width='5%'>&nbsp;����</td>
			<td width='45%'><freeze:datebox property="created_time_end"
					caption="ʱ�䷶Χ" datatype="string" maxlength="30" style="width:100%"
					colspan="0" /></td>
			<td width='5%'></td>
			</tr>
			</table>
  </freeze:block>
  
<br>
  <freeze:grid property="record"   checkbox="false" caption="��ѯ�ɼ���־�б�" keylist="collect_joumal_id" width="95%" navbar="bottom" >
  	  <freeze:button name="record_backupRecord" caption="�鵵" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="collect_joumal_id" caption="�ɼ���־ID" style="width:10%" visible="false"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:12%" />
      <freeze:hidden property="service_targets_id" caption="�������ID" style="width:12%" />
      <freeze:hidden property="task_id" caption="����ID" style="width:12%" />
      <freeze:hidden property="service_no" caption="������" style="width:95%" />
      <freeze:hidden property="return_codes" caption="���ش���" style="width:95%" />
      <freeze:hidden property="task_consume_time" caption="��λ����" style="width:10%" />
      
      <freeze:cell property="@rowid" caption="���"  align="center"   style="width:50px;" />
      <freeze:cell property="service_targets_name" caption="�������"  align="center"  valueset="��Դ����_�����������" style="width:10%" />
      <freeze:cell property="task_name" caption="��������"  align="center"   style="" />
     <%--  <freeze:cell property="method_name_cn" caption="��������"  align="center"   style="width:12%" />
      <freeze:cell property="collect_table_name" caption="�ɼ���"  align="center"   style="width:10%" />  
      <freeze:cell property="collect_type" caption="�ɼ�����"  align="center" valueset="��Դ����_����Դ����" style="width:10%" /> --%>
      <freeze:cell property="task_start_time" caption="��ʼʱ��"  align="center"  style="width:17%" />
      <freeze:cell property="task_end_time" caption="����ʱ��"    align="center"   style="width:17%" />
      <freeze:cell property="collect_data_amount" caption="�ɼ�����"    align="center"   style="width:8%" />
      <freeze:cell property="return_codes" caption="���ؽ��"      align="center"      valueset="" style="width:8%" />
      <freeze:cell property="oper" caption="����" align="center" style="width:50px" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
