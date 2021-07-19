<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
	<title>��ѯ������������Ϣ�б�</title>
</head>

<style >
.activerow{cursor: default;}
</style>

<script language="javascript">

// ���ӹ�����������Ϣ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_share_dataitem.jsp", "���ӹ�����������Ϣ", "modal" );
	page.addRecord();
}

// �޸Ĺ�����������Ϣ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn20301024.do", "�޸Ĺ�����������Ϣ", "modal" );
	page.addParameter( "record:share_dataitem_id", "primary-key:share_dataitem_id" );
	page.updateRecord();
}

// ɾ��������������Ϣ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn20301025.do", "ɾ��������������Ϣ" );
	page.addParameter( "record:share_dataitem_id", "primary-key:share_dataitem_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids =document.getElementsByName("span_record:code_table");
	var value=getFormAllFieldValues("record:code_table");
	
	for(var i=0; i<ids.length; i++){
		var val=value[i];
		if(val==null||val==""){
		}else{
		  ids[i].innerHTML='<a href="javascript:func_record_querycode(\''+value[i]+'\');" title="" >'+val+'</a>';
		}
    }
	var keys = document.getElementsByName('span_record:is_key');
	for(var i=0; i<keys.length; i++){
		if(keys[i].innerText == '��'){
			keys[i].innerText = '';
		}
	}
}
function func_record_querycode(val){
   
    var url="txn20301026.do?select-key:code_table="+val;
    var page = new pageDefine( url, "�鿴���뼯��Ϣ", "���뼯��ѯ" );
	page.addRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<br/>
	<freeze:errors />

	<freeze:form action="/txn20301021">
		<freeze:frame property="select-key" width="95%">
		</freeze:frame>

		<freeze:block property="fatherRecord" caption="" width="95%">
			<freeze:cell property="service_targets_name" caption="ҵ��ϵͳ"
				style="width:95%" />
			<freeze:cell property="topics_name" caption="����" style="width:95%" />
			<freeze:cell property="table_name_cn" caption="����������"
				style="width:95%" />
			<freeze:cell property="table_name_en" caption="������" style="width:95%" />

		</freeze:block>
		<freeze:grid checkbox="false" property="record" caption="��ѯ������������Ϣ�б�"
			keylist="share_dataitem_id" width="95%" navbar="bottom" fixrow="false" rowselect="false" >
			<freeze:hidden property="share_dataitem_id" caption="����������ID"
				style="width:10%" visible="false" />
			<freeze:hidden property="share_table_id" caption="�����ID"
				style="width:12%" />
				
			<freeze:cell property="@rowid" caption="���" align="center" style="width:5%"/>
			<freeze:cell property="dataitem_name_en" caption="����������"
				style="width:20%" align="center"/>
			<freeze:cell property="dataitem_name_cn" caption="��������������"
				style="width:20%" align="center"/>
			<freeze:hidden property="dataitem_type" caption="����������"
				style="width:12%" align="center"/>
			<freeze:cell property="dataitem_long" caption="�������"
				style="width:12%" align="center"/>
			<freeze:hidden property="code_table_name" caption="ϵͳ������"
				style="width:12%" align="center"/>
			<freeze:cell property="is_key" caption="�Ƿ�����" valueset="��Դ����_�Ƿ�����" align="center" style="width:10%" />
			<freeze:cell property="code_table" caption="���뼯" align="center" style="width:10%" />
			<freeze:hidden property="dataitem_desc" caption="����"
				style="width:20%" visible="false" />
			<freeze:hidden property="show_order" caption="��ʾ˳��" style="width:10%" />
			<freeze:hidden property="is_markup" caption="��Ч���" style="width:10%" />
		</freeze:grid>

	</freeze:form>
</freeze:body>
<!--     
	  <freeze:button name="record_addRecord" caption="���ӹ�����������Ϣ" txncode="20301023" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĺ�����������Ϣ" txncode="20301024" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��������������Ϣ" txncode="20301025" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      -->
</freeze:html>
