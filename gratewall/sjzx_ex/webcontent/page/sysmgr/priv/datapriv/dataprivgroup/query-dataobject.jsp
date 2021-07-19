<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html  width="650" height="350">
<head>
<title>��ѯ����Ȩ�����͹����б�</title>
</head>

<style>
	select#s1{
		width:245px;
	}
	
	select#s2{
		width:245px;
	}
</style>

<script language="javascript">

// ��������Ȩ�����͹���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-dataobject.jsp", "��������Ȩ�����͹���", "modal" );
	page.addRecord();
}

// �޸�����Ȩ�����͹���
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103014.do", "�޸�����Ȩ�����͹���", "modal" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.updateRecord();
}

// ɾ������Ȩ�����͹���
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103015.do", "ɾ������Ȩ�����͹���" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

//���湦�����ù�ϵ
function func_record_saveFunctions(){
	var img = window.event.srcElement;
	if(img.tagName.toUpperCase()=="IMG"){
		var row = img.parentElement.parentElement.parentElement;
		var checkbox = row.firstChild.firstChild;
		checkbox.checked=true;
		//alert(row);
		var page = new pageDefine( "/txn103016.do", "��������Ȩ������" );
		page.addParameter( "record:objectid", "record:objectid" );
		page.addParameter( "record:functionIds", "record:functionIds" );
		//var objectid = document
		//var functionIds = 
		//page.addValue(objectid,"record:objectid");
		//page.addValue(functionIds,"record:functionIds");
		page.callService();
		//page
	}
}

//
function func_record_clearRecord(){
	if(window.confirm("ȷ�������")){
		var page = new pageDefine( "/txn103019.do", "ɾ������Ȩ����������" );
		page.addParameter( "record:objectid", "record:objectid" );
		page.callService("�����������Ȩ���������ã�");
	}else{
		var grid = getGridDefine("record", 0); 
		clickFlag = 0; 
		grid.checkMenuItem();
	}
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ����Ȩ�����͹����б�"/>
<freeze:errors/>

<freeze:form  action="/txn103011">
  <freeze:block property="select-key" submit=" �� ѯ " caption="��ѯ����" width="95%">
      <freeze:text property="objectsource" caption="����Ȩ����Դ" datatype="string" maxlength="500" style="width:95%"/>
  </freeze:block>

  <freeze:grid  property="record" caption="��ѯ����Ȩ�����͹����б�" keylist="objectid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_updateRecord" caption="�޸�����Ȩ�����͹���" txncode="103014" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_clearRecord" caption="��չ������ù�ϵ" txncode="103014" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_clearRecord();"/>
      
      <freeze:cell property="objectid" caption="����Ȩ�����ʹ���" style="width:20%" visible="false"/>
      <freeze:cell property="objectsource" caption="����Ȩ����Դ" style="width:20%" visible="true" />
      <freeze:browsebox property="functionIds" caption="���ù��ܹ�ϵ" valueset="���ܵ������ռ�" show="name" style="width:60%"  multiple="true" namebox="rolenames" width="500" height="500"/>
  	  <freeze:link  property="save" caption="�������ù��ܹ�ϵ"  img="images/save.jpg" style="width:20%;"  onclick="func_record_saveFunctions()" align="center"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
