<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��������б�</title>
</head>

<script language="javascript">

// �����������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_task_scheduling.jsp", "�����������", "modal");
	page.addRecord();
}

// �޸��������
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn30801004.do", "�޸��������", "modal" );
	page.addValue(idx,"primary-key:jhrw_id");
	page.updateRecord();
}

// ɾ���������
function func_record_deleteRecord(idx)
{
	var page = new pageDefine( "/txn30801005.do", "ɾ���������" );
	page.addValue(idx,"primary-key:jhrw_id");
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:jhrw_id");
	var week=getFormAllFieldValues("record:jhrw_zt");
	var weekday= new Array();
	var name="";
	for(var i=0; i<ids.length; i++){
	   name="";
	   var htm='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="func_record_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	   
	   if(week[i]!=null&&week[i]!=""){
	  
	   weekday=week[i].split(",");
	   for(var j=0;j<weekday.length;j++){
	      if(weekday[j]=="1"){
	         name+="����һ"+",";
	      }else if(weekday[j]=="2"){
	         name+="���ڶ�"+",";
	      }else if(weekday[j]=="3"){
	         name+="������"+",";
	      }else if(weekday[j]=="4"){
	         name+="������"+",";
	      }else if(weekday[j]=="5"){
	         name+="������"+",";
	      }else if(weekday[j]=="6"){
	         name+="������"+",";
	      }else if(weekday[j]=="7"){
	         name+="������"+",";
	      }
	   }
	    name=name.substring(0,name.length-1);
	   document.getElementsByName("span_record:jhrw_zt")[i].innerHTML =name;
	   }
	  
	 }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��������б�"/>
<freeze:errors/>

<freeze:form action="/txn30801001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="jhrw_mc" caption="�ƻ���������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="jhrw_lx" caption="�ƻ���������"  show="name" valueset="��Դ����_�ƻ���������"  style="width:95%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="��ѯ��������б�" keylist="jhrw_id" width="95%" multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="�����������" txncode="30801003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
     
      <freeze:hidden property="jhrw_id" caption="�ƻ�����ID"  />
      <freeze:hidden property="jhrw_mc" caption="�ƻ���������" style="color:#0000FF; text-decoration: underline;width:20%" onclick="func_record_viewRecord1();"/>
     
      <freeze:hidden property="jhrwzx_zt" caption="�ƻ�����ִ��״̬"  />
      <freeze:cell property="jhrw_lx" caption="�ƻ���������" valueset="��Դ����_�ƻ���������" style="width:10%" />
      <freeze:cell property="jhrw_sj" caption="�ƻ�����ʱ��" style="width:10%" />
      <freeze:cell property="jhrw_rq" caption="�ƻ���������" style="width:10%" />
      <freeze:hidden property="jhrw_zq" caption="�ƻ���������"  />
      <freeze:cell property="jhrw_zt" caption="�ƻ���������" style="width:25%" />
       <freeze:cell property="jhrwzx_cs" caption="�ƻ�����ִ�д���" style="width:15%" />
      <freeze:cell property="oper" caption="����" style="width:10%" />
      <freeze:hidden property="jhrwjs_sj" caption="�ƻ��������ʱ��"  />
      <freeze:hidden property="jhrwsczx_sj" caption="�ƻ������ϴ�ִ��ʱ��"  />
     
      <freeze:hidden property="creator_id" caption="������"  />
      <freeze:hidden property="created_time" caption="����ʱ��"  />
      <freeze:hidden property="last_modify_id" caption="�޸���"  />
      <freeze:hidden property="last_modify_time" caption="�޸�ʱ��"  />
      <freeze:hidden property="yx_bj" caption="��Ч���"  />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
