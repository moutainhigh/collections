<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<title>��ѯϵͳ��־�б�</title>
</head>

<script language="javascript">
/* var checkName="";
function formSubmit(){
	console.log(checkName);
}
function checkchange(){
	if($('#nameinput').val()==checkName){
		return;
	}else{
		checkName=$('#nameinput').val();
		formSubmit();
	}
} */
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	/*
	var value=getFormAllFieldValues("record:first_page_query_id");
	
	for(var i=0; i<value.length; i++){
		var val=value[i];
	
	document.getElementsByName("span_record:oper")[i].innerHTML='<a href="javascript:func_record_querycode(\''+val+'\');" title="" >�鿴��ϸ</a>';
	
    }
    */
   /*  var t2html='<div class="pack-up" id="t2" >'
		+'<ul class="pack-list" id="t2_ul" ><li class="disabled" id="username"><a>����������</a></li>'
		+'<li ><input id="nameinput" type="text" style="width:180px;" onpropertychange="checkchange()" ></input></li></ul></div>';
	$('#t1').after(t2html);//�������ѡ��� */	
    //$('#t2').find('.pack-list li:eq(1)').hide();
	
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
<freeze:title caption="��ѯϵͳ������־�б�"/>
<freeze:errors/>
<gwssi:panel action="txn601011" target="" parts="t1,t2" styleClass="wrapper">
 	<gwssi:cell id="t1" name="����������" key="username" data="username" />
	<gwssi:cell id="t2" name="��&nbsp;��&nbsp;ʱ&nbsp;��" key="created_time" data="created_time" date="true"/>
   
 </gwssi:panel>

<freeze:form action="/txn601011">
  
<br/>
  <freeze:grid property="record"   checkbox="false" caption="��ѯϵͳ��־�б�" keylist="first_page_query_id" width="95%" rowselect="false" navbar="bottom" >
      <freeze:cell property="@rowid" caption="���" align="middle" style="width:5%"/>
      <freeze:hidden property="username" caption="�������˺�"  align="center"  style="width:8%" />
      <freeze:cell property="opername" caption="����������"   align="center"  style="width:10%" />
      <freeze:cell property="orgname" caption="���������ڻ���"  align="center"   style="width:20%" />
      <freeze:cell property="first_cls" caption="��������" align="center"style="width:15%" />
      <freeze:cell property="second_cls" caption="�������" align="center" style="width:15%" />
      <freeze:cell property="query_time" caption="����ʱ��"  align="center"  style="width:10%" />
      
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
