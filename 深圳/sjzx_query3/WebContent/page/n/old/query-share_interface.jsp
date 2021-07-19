<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����������ӿ������б�</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../common/top_datepicker.html"></jsp:include>
<style type="text/css">
li.disabled {cursor: default;}
.pack-up .pack-list hr.hid{display: none;}
.modal_window .pack-list hr.hid{display:block;width: 600px;
.choose_date{width: 196px; height: 1.8em; display:block;font-size: 62.5%; }
#t2 ul.pack-list li a span{display:none;}
#modal_window ul.pack-list li a span{display: none !important;}
#modal_window, #searchDiv{width:400px; }
.cont{width: 400px; height: 400px; overflow-y: auto; overflow-x: hidden; padding: 0; border: 1px solid #def; }
.albar{background: rgb(41, 140, 206) !important;margin:0px; text-align: center; line-height: 20px; height: 20px; color: white;}
.letter-list, .letter-list li{padding: 0; margin: 0; display: inline; text-align: center; color: #fff;}

.letter-list li:hover, .letter-list .on{background: rgb(102, 200, 232); cursor: default;}
.letter-list a{color: #fff; text-decoration: none; margin: 0 5px;}
.letter-list a:hover, .letter-list a:visited, .letter-list a:linked{text-decoration: underline; color: #fff;}
.cont .cont-area{ color: #036; font-weight: bold; margin-left: 5px;}
.cont .item{color:#333; padding-left:2px; cursor: pointer; height: 25px; line-height: 25px; border: 1px solid rgb(207,207,254); margin-bottom: 1px; font-size: 12px;}
.cont .item-hover{background: #cef;}
.cont .item .tnum{color: #888;}
.nodata{display: none;}
}
</style>
</head>

<script language="javascript">
var deleteID;
// ���ӹ����������ӿ�����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_interface.jsp", "���ӹ����������ӿ�����","modal");
	page.addRecord();
}

// �޸Ĺ����������ӿ�����
function func_record_updateRecord(id)
{
	var page = new pageDefine( "/txn401004.do", "�޸Ĺ����������ӿ�����","modal" );
	page.addValue( id, "primary-key:interface_id" );
	page.updateRecord();
}

// �鿴�����������ӿ�����
function func_record_viewRecord(id)
{
	var page = new pageDefine( "/txn401010.do", "�鿴�����������ӿ�����","modal" );
	page.addValue( id, "primary-key:interface_id" );
	page.updateRecord();
}
// ɾ�������������ӿ�����
function func_record_deleteRecord(id)
{
	var page = new pageDefine( "/txn401005.do", "ɾ����������ӿ�" );
	page.addParameter( "record:interface_id", "primary-key:interface_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

function deleteCallback(errCode, errDesc, xmlResults)
{
  if (errCode != '000000') {
    //alert('�������['+errCode+']==>'+errDesc);
    return;
  }
  var id = _getXmlNodeValues(xmlResults, "record:interface_id");
  var num = _getXmlNodeValues(xmlResults, "record:interfaceusednum");
  if(num && num[0]>0)
  {
    alert("�з�����ô˽ӿڣ�����ɾ����");
  	return;
  }
	var page = new pageDefine( "/txn401011.do", "ɾ����������ӿ�" );
	var interface_id = id;
	page.addValue(interface_id, "primary-key:interface_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}
function updateCallback(errCode, errDesc, xmlResults)
{
  if (errCode != '000000') {
    //alert('�������['+errCode+']==>'+errDesc);
    return;
  }
  var id = _getXmlNodeValues(xmlResults, "record:interface_id");
  var num = _getXmlNodeValues(xmlResults, "record:interfaceusednum");
  
  var being_used = false;
  if(num && num[0]>0)
  {
	  being_used = true ;
    if(!confirm("��Ҫ��ʾ���з�����ô˽ӿڣ��޸�ʱֻ�����ڽӿ�ԭ�л�������ӱ�����ɾ����ѡ���ݱ������ȷ���������޸�")){
    	return;
    }
  	
  }
  var page = new pageDefine( "/txn401004.do", "�޸Ĺ����������ӿ�����","_blank" );
	var interface_id = id;
	page.addValue( interface_id, "primary-key:interface_id" );
	page.addValue( being_used, "primary-key:being_used" );
	page.updateRecord();
}
// ɾ�������������ӿ�����
function func_deleteRecord(id)
{
  var page = new pageDefine("/txn401012.ajax", "�ӿ�ID��ѯ���ýӿڵķ�������");
  var interface_id = id;
  deleteID = id;
  if (interface_id && interface_id != '') {
    page.addValue(interface_id, "select-key:interface_id");
    page.callAjaxService('deleteCallback');
  }
}
//ɾ�������������ӿ�����
function func_updateRecord(id)
{
  var page = new pageDefine("/txn401012.ajax", "�ӿ�ID��ѯ���ýӿڵķ�������");
  var interface_id = id;
  if (interface_id && interface_id != '') {
    page.addValue(interface_id, "select-key:interface_id");
    page.callAjaxService('updateCallback');
  }
}
// �޸ĵ�������ͣ��״̬
function func_record_changeOneStatus(interface_id,interface_state)
{
	var page = new pageDefine( "/txn401013.do", "����/ͣ��" );
	if(interface_state === 'Y'){
		interface_state = 'N';
	}else{
		interface_state = 'Y';
	}
	page.addValue( interface_id, "primary-key:interface_id" );
	page.addValue( interface_state, "primary-key:interface_state" );
	page.deleteRecord( "�Ƿ��޸ķ�������ӿ�״̬" );
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:interface_id");
	var interface_state = document.getElementsByName("record:interface_state");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="�޸�" onclick="func_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="func_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>&nbsp;'; 
	   
	   if(interface_state[i].value=="Y"){
	   htm+='<a href="#" title="���ͣ��" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+interface_state[i].value+'\');"><div class="run"></div></a>';
	   }else
	   {
	   htm+='<a href="#" title="�������" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+interface_state[i].value+'\');"><div class="stop"></div></a>';
	   }
	   document.getElementsByName("span_record:operation")[i].innerHTML +=htm;
	}
	
	var names = getFormAllFieldValues("record:interface_name");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_record_viewRecord(\''+ids[i]+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:interface_name")[i].innerHTML =htm;
	}
	 
	var date_s = document.getElementsByName("span_record:created_time");
	for(var ii=0; ii<date_s.length; ii++){
		date_s[ii].innerHTML = date_s[ii].innerHTML.substr(0,10);
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�������ӿ��б�"/>
<freeze:errors/>
<gwssi:panel action="txn40109002" target="" parts="t1,t2,t3" styleClass="wrapper">
  <gwssi:cell id="t1" name="�ӿ�״̬" key="interface_state" data="itfState" />
  <gwssi:cell id="t2" name="�����ӿ�" key="interface_id" data="itfName" isPinYinOrder="true" move2top="true" maxsize="10" />
  <gwssi:cell id="t3" name="����ʱ��" key="created_time" data="created_time" date="true"/>
</gwssi:panel>
<freeze:form action="/txn40109002.do">
  <freeze:frame property="select-key" >
     <freeze:hidden property="interface_state" caption="�ӿ�״̬" />
     <freeze:hidden property="interface_id" caption="�����ӿ�" />
     <freeze:hidden property="created_time" caption="����ʱ��" />
  </freeze:frame>
  <freeze:grid property="record" checkbox="false" caption="��ѯ�ӿ��б�" keylist="interface_id" width="95%"   navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���ӽӿ�" txncode="401003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button visible="false" name="record_deleteRecord" caption="ɾ���ӿ�" txncode="401005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
     <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="interface_name" caption="�ӿ�����" style=" " />
      <freeze:cell property="table_name_cn" caption="�ӿ��������ݱ�" style="width:30%;" />
      <freeze:hidden property="interface_state" caption="�ӿ�״̬" valueset="��Դ����_һ�����״̬" align="center" style="width:8%" />
      <%-- <freeze:cell property="interface_description" caption="�ӿ�˵��" style="" /> --%>
     
      <freeze:cell property="yhxm" align="center" caption="����޸���" style="width:13%" />
       <freeze:cell property="last_time" align="center" caption="����޸�����" style="width:13%" />
      <freeze:cell nowrap="true" property="operation" caption="����" style="width:95px" align="center"/>
      
      <freeze:hidden property="is_markup" caption="�����"  />
      <freeze:hidden property="creator_id" caption="������ID"  />
      <freeze:hidden property="interface_description" caption="�ӿ�˵��"/>
      <freeze:hidden property="table_id" caption="����봮" />
      <freeze:hidden property="interface_id" caption="�ӿ�ID"/>
      <freeze:hidden property="sql" caption="sql���"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID"  />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
