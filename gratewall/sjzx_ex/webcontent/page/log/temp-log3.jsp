<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯϵͳ��־�б�</title>
</head>

<script language="javascript">



// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
	var value=getFormAllFieldValues("record:first_page_query_id");
	
	for(var i=0; i<value.length; i++){
		var val=value[i];
	
	document.getElementsByName("span_record:oper")[i].innerHTML='<a href="javascript:func_record_querycode(\''+val+'\');" title="" >�鿴��ϸ</a>';
	
    }
	var date_s1 = document.getElementsByName("span_record:query_time");
	for(var ii=0; ii<date_s1.length; ii++){
		date_s1[ii].innerHTML = date_s1[ii].innerHTML.substr(0,10);
	}
	
	
	
}
function func_record_querycode(first_page_query_id){
   
    var url="txn601016.do?select-key:first_page_query_id="+first_page_query_id;
    var page = new pageDefine( url, "��ѯϵͳ��־�б�", "ϵͳ��־" );
	page.addRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�������ͳ��"/>

<freeze:errors/>
<div align="center"><span style="color: #f00;">�������ݣ����ܲ�����</span></div>
<freeze:form action="/txn601011">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="opername" caption="�������"   style="width:95%"/>
      <freeze:select property="orgname" caption="ͳ������"   style="width:95%"/>
      <freeze:select property="123" caption="չ�ַ�ʽ"   style="width:95%"/>
      <freeze:select property="ssa" caption="����"   style="width:95%"/>

  </freeze:block>
<br/>
  
  <freeze:grid property="record"   checkbox="false" caption="��ѯϵͳ��־�б�" keylist="first_page_query_id" width="95%" navbar="bottom" >
     
      
      <freeze:hidden property="first_page_query_id" caption="����" style="width:10%" visible="false"/>
                  <freeze:cell property="opername" caption="��ض���"   align="center"  style="width:15%" />
                <freeze:cell property="username" caption="���ָ��"  align="center"  style="width:15%" />
           
          
            <freeze:cell property="orgname" caption="���ָ��ֵ"  align="center"   style="width:15%" />
            <freeze:cell property="query_time" caption="�Ƿ���"  align="center"    style="width:16%" />
            
            <freeze:cell property="state" caption="���ʱ��"   align="center"     style="width:15%" />
              <freeze:cell property="oper" caption="����"    align="center"        style="width:16%" />
            
      
      <freeze:hidden property="count" caption="����" style="width:10%" />
      <freeze:hidden property="num" caption="����" style="width:10%" />
      <freeze:hidden property="query_date" caption="��������" style="width:10%" />
      <freeze:hidden property="username" caption="�û���" style="width:12%" />
      <freeze:hidden property="opername" caption="��������" style="width:12%" />
      <freeze:hidden property="orgid" caption="��֯ID" style="width:12%" />
      <freeze:hidden property="orgname" caption="��֯����" style="width:16%" />
      <freeze:hidden property="ipaddress" caption="ip��ַ" style="width:12%" />
      <freeze:hidden property="first_cls" caption="first_cls" style="width:20%" />
      <freeze:hidden property="second_cls" caption="second_cls" style="width:20%" />
      <freeze:hidden property="operfrom" caption="operfrom" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
