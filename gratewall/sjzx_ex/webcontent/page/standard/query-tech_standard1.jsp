<%@page import="com.gwssi.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���������б�</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../share/common/top_datepicker.html"></jsp:include>
<style type="text/css">
li.disabled {cursor: default;}
.pack-up .pack-list hr.hid{display: none;}
.modal_window .pack-list hr.hid{display:block;width: 600px;
.choose_date{width: 196px; height: 1.8em; display:block;font-size: 62.5%; }
}
</style>
</head>

<script language="javascript">

// ���ӹ���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-tech_standard.jsp", "���Ӽ�������","modal");
	page.addRecord();
}

// �޸Ĺ���
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn603009.do", "�޸ļ�������" ,"modal");
	page.addParameter( "record:standard_id", "primary-key:standard_id" );
	page.updateRecord();
}
function func_record_updateRecord(idx)
{
    var svrId = getFormFieldValue("record:standard_id", idx);
	var page = new pageDefine( "/txn603009.do", "�޸ļ�������" ,"modal");
	page.addValue( svrId, "primary-key:standard_id" );
	page.updateRecord();
}
// ɾ������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn603010.do", "ɾ������" );
	page.addParameter( "record:standard_id", "primary-key:standard_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}
function func_deleteRecord(idx)
{
	var page = new pageDefine( "/txn603010.do", "ɾ������" );
	page.addValue( idx, "primary-key:standard_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

//�鿴�����
function func_viewConfig(idx)
{
	var svrId = getFormFieldValue("record:standard_id", idx);
	var page = new pageDefine( "/txn603017.do", "�鿴�����","modal" );
	page.addValue( svrId, "primary-key:standard_id" );
	page.updateRecord();
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	 var ids = getFormAllFieldValues("record:standard_id");
	for(var i=0; i<ids.length; i++){  
	   var htm = '<a href="#" title="�޸�" onclick="func_record_updateRecord('+i+')"><div class="edit"></div></a>&nbsp;';
	   htm += '<a href="#" title="ɾ��" onclick="func_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	var names = getFormAllFieldValues("record:standard_name");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_viewConfig(\''+i+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:standard_name")[i].innerHTML =htm;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ������׼�б�"/>
<freeze:errors/>
<gwssi:panel action="txn603904" target="" parts="t1,t2" styleClass="wrapper">
  <gwssi:cell id="t1" name="�����淶" key="standard_id" data="staName" pop="true" move2top="true" maxsize="10" />
  <gwssi:cell id="t2" name="��������" key="enable_time" data="enable_time" date="true"/>
</gwssi:panel>
<freeze:form action="/txn603904">

  <freeze:frame property="select-key" >
     <freeze:hidden property="standard_id" caption="���ݹ淶" />
     <freeze:hidden property="enable_time" caption="����ʱ��" />
  </freeze:frame>
  <freeze:grid property="record"   checkbox="false" caption="��ѯ�����б�" keylist="standard_id" width="95%" navbar="bottom" >
      <freeze:button name="record_addRecord" caption="����" txncode="603008" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="standard_id" caption="��׼ID" style="width:10%" visible="false"/>
      <freeze:cell property="@rowid" align="center" caption="���" style="width:5%" />
      <freeze:cell property="standard_name" align="center" caption="�����淶����" style="width:30%" />
      <freeze:cell property="enable_time" align="center" caption="��������" style="width:18%" />
      <freeze:cell property="fjmc"  caption="�ļ�����" align="center" style="" />
      <%-- <freeze:cell property="specificate_desc"  caption="��ע" align="center" style="width:20%"  /> --%>
      <freeze:cell property="oper" nowrap="true" caption="����" align="center" style="width:65px;" />
      
      <freeze:hidden property="standard_type" caption="��׼����"  />
      <freeze:hidden property="specificate_type" caption="��������"  />
      <freeze:hidden property="issuing_unit" caption="������λ"  />
      <freeze:hidden property="specificate_no" caption="���ͷ����"  />
      <freeze:hidden property="specificate_status" caption="����״̬"  />
      <freeze:hidden property="is_markup" caption="���뼯 ��Ч ��Ч"  />
      <freeze:hidden property="creator_id" caption="������ID"  />
      <freeze:hidden property="last_modify_id" caption="����޸���ID"  />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��"  />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
