<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ֪ͨ�����б�</title>
</head>

<script language="javascript">

function func_record_testRecord(){
	
	var page = new pageDefine( "test-user_svr.jsp","�û��������","modal", document.body.clientWidth, document.body.clientHeight);
	
	page.addValue( "lztest1", "svrId");
	page.updateRecord( );
}

function func_record_testRecord_pri(){
	
	var page = new pageDefine( "test-user_svr_pri.jsp","�û��������","modal", document.body.clientWidth, document.body.clientHeight);
	
	page.addValue( "lztest1", "svrId");
	page.updateRecord( );
}

// ����֪ͨ����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-zw_tzgl_jbxx.jsp", "����֪ͨ����" );
	page.addRecord();
}

// �޸�֪ͨ����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn315001004.do", "�޸�֪ͨ����" );
	page.addParameter( "record:jbxx_pk", "primary-key:jbxx_pk" );
	page.updateRecord();
}

// �鿴֪ͨ����
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn315001006.do", "�鿴֪ͨ����" );
	page.addParameter( "record:jbxx_pk", "primary-key:jbxx_pk" );
	page.updateRecord();
}

//���������ϸ
function tzmx(index){   
    var gridname = getGridDefine("record");
    var param = "primary-key:jbxx_pk=" + gridname.getAllFieldValues( "jbxx_pk" )[index];
    var page = new pageDefine( "/txn315001006.do?"+param, "�鿴֪ͨ��ϸ", "window", 800, 600);
    page.updateRecord( );  
}

// ɾ��֪ͨ����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn315001005.do", "ɾ��֪ͨ����" );
	page.addParameter( "record:jbxx_pk", "primary-key:jbxx_pk" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ֪ͨ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn315001001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="tzmc" caption="֪ͨ����" datatype="string" maxlength="255" style="width:95%"/>
      <freeze:text property="fbsj" caption="����ʱ��" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="tzzt" caption="֪ͨ״̬" datatype="string" maxlength="2" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ֪ͨ�����б�" keylist="jbxx_pk" width="95%" navbar="bottom" fixrow="true">
  	  <freeze:button name="record_testRecord" caption="���ڲ���"  enablerule="0" hotkey="ADD" align="right" onclick="func_record_testRecord_pri();" visible="false"/>
  	  <freeze:button name="record_testRecord" caption="����"  enablerule="0" hotkey="ADD" align="right" onclick="func_record_testRecord();" visible="false"/>
      <freeze:button name="record_addRecord" caption="����" txncode="315001003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="315001004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_updateRecord" caption="�鿴" txncode="315001006" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="315001005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="jbxx_pk" caption="֪ͨ���-����" style="width:10%" visible="false"/>
      <freeze:cell property="tzmc" caption="֪ͨ����" style="width:22%" />
      <freeze:cell property="fbrid" caption="������ID" style="width:13%" />
      <freeze:cell property="fbrmc" caption="����������" style="width:13%" />
      <freeze:cell property="fbksid" caption="��������" style="width:13%" />
      <freeze:cell property="fbksmc" caption="��������" style="width:17%" />
      <freeze:cell property="fbsj" caption="����ʱ��" style="width:12%" />
      <freeze:cell property="tznr" caption="֪ͨ����" style="width:20%" visible="false" />
      <freeze:cell property="tzzt" caption="֪ͨ״̬" style="width:10%" />
      <freeze:cell property="jsrids" caption="������ids" style="width:20%" visible="false" />
      <freeze:cell property="jsrmcs" caption="����������" style="width:20%" visible="false" />
      <freeze:cell property="fj_fk" caption="����id" style="width:20%" visible="false" />
      <freeze:cell property="fjmc" caption="��������" style="width:20%" visible="false" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
