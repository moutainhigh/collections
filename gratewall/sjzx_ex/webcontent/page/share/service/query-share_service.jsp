<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ������б�</title>
<style type="text/css">
#t4 ul.pack-list li a span{/* display:none; */}
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
</style>
</head>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../../share/common/top_datepicker.html"></jsp:include>
<script type="text/javascript" >
function func_record_testRecord(){
	var type=getFormFieldValues("record:service_type");
	var id = getFormFieldValues("record:service_id")
	var old_service_no = getFormFieldValues("record:old_service_no");
	var url="test-user_svr.jsp";
	/* if(type=="SOCKET"){
	   url="test-user_svr_socket.jsp";
	}else if(type=="JMS��Ϣ"){
	   url="test-user_svr_jms.jsp";
	}else if(type=="WebService"){
	   url="test-user_svr.jsp";
	}else{
	  alert("�˷�����������û�д˹���!");
	  clickFlag=0;
	  return false;
	} */
	
	var page = new pageDefine( url,"�����������","modal", document.body.clientWidth, document.body.clientHeight);
	page.addValue( id, "svrId" );
	page.addValue( old_service_no, "old_service_no" );
	//page.addValue( "lztest1", "svrId");
	page.updateRecord( );
}

function func_record_testRecord_socket(){
	
	var type=getFormFieldValues("record:service_type");
	if(type=="SOCKET"){
	var id = getFormFieldValues("record:service_id");
	var old_service_no = getFormFieldValues("record:old_service_no");
	var page = new pageDefine( "test-user_svr_socket.jsp","�����������","modal", 
			document.body.clientWidth, document.body.clientHeight);
	page.addValue( id, "svrId" );
	page.addValue( old_service_no, "old_service_no" );
	//page.addValue( "lztest1", "svrId");
	page.updateRecord( );
	}else{
		alert("��ѡ���������ΪSOCKET�ķ�����в���!");
		clickFlag=0;
		return false;
	}
}
function func_record_testRecord_jms(){
	
	var type=getFormFieldValues("record:service_type");
	if(type=="JMS��Ϣ"){
	var id = getFormFieldValues("record:service_id");
	var old_service_no = getFormFieldValues("record:old_service_no");
	var page = new pageDefine( "test-user_svr_jms.jsp","�����������","modal", 
			document.body.clientWidth, document.body.clientHeight);
	page.addValue( id, "svrId" );
	page.addValue( old_service_no, "old_service_no" );
	//page.addValue( "lztest1", "svrId");
	page.updateRecord( );
	}else{
		alert("��ѡ���������ΪJMS��Ϣ�ķ�����в���!");
		clickFlag=0;
		return false;
	}
}
function func_record_ftp(service_id,service_no,service_targets_id,service_name,service_state){
		//var page = new pageDefine( "/page/share/ftp/service/insert-share_ftp_service.jsp","FTP��������","modal", document.body.clientWidth, document.body.clientHeight);
	
	if(service_state=="Y"){
		var page = new pageDefine( "/txn40401004.do", "FTP��������", "modal", document.clientWidth, document.clientHeight);
		page.addValue(service_id, "primary-key:service_id" );
		page.addValue(service_no, "primary-key:service_no" );
		page.addValue(service_name, "primary-key:service_name" );
		page.addValue(service_targets_id, "primary-key:service_targets_id" );
		page.updateRecord( );
	}else{
	   alert("������ͣ�ò�������!");
	   clickFlag=0;
	   return false;
	}
	
}
function func_record_testRecord1(){
	
	var target_id = getFormFieldValues("record:service_targets_id");
	//alert(target_id);
	var page = new pageDefine("/txn201001.ajax", "���ݽӿ�ID��ȡ���ݱ��б�");
	//alert(target_id);
	page.addValue(target_id, "select-key:service_targets_name");
	page.callAjaxService('getSvrUserPwd');
}

function getSvrUserPwd(errCode, errDesc, xmlResults){
	if (errCode != '000000') {
	    //alert('�������['+errCode+']==>'+errDesc);
	    return;
	  }
	var target_id = getFormFieldValues("record:service_targets_id");
	var id = getFormFieldValues("record:service_id");
	var svrNo = getFormFieldValues("record:service_no");
	var loginName = _getXmlNodeValues(xmlResults, "record:service_targets_no");
	var loginPwd = _getXmlNodeValues(xmlResults, "record:service_password");
	loginName = loginName[0];
	loginPwd = loginPwd[0];
	var page = new pageDefine( "test-user_svr.jsp","�����������","modal", document.body.clientWidth, document.body.clientHeight);
	page.addValue( id, "svrId" );
	page.addValue( svrNo, "svrNo");
	page.addValue( loginName, "loginName");
	page.addValue( loginPwd, "loginPwd");
	page.addValue( target_id, "targetId");
	page.updateRecord( );
}

function func_record_testRecord_pri(){
	var id = getFormFieldValues("record:service_id")
	var old_service_no = getFormFieldValues("record:old_service_no");
	var page = new pageDefine( "test-user_svr_pri.jsp","�����������","modal", document.body.clientWidth, document.body.clientHeight);
	page.addValue( id, "svrId" );
	page.addValue( old_service_no, "old_service_no" );
	//page.addValue( "lztest1", "svrId");
	page.updateRecord( );
}

// ���ӷ����
function func_record_addRecord()
{
	//var page = new pageDefine( "insert-share_service_old.jsp", "���ӷ����" );
	var page = new pageDefine( "insert-share_service.jsp", "���ӷ����","modal", "1000", "800");
	page.addRecord();
}

// �޸ķ����
function func_record_updateRecord(idx)
{
    var svrId = getFormFieldValue("record:service_id", idx);
	var page = new pageDefine( "/txn40200004.do", "�޸ķ����", "modal", "1000", "800");
	page.addValue( svrId, "primary-key:service_id" );
	page.updateRecord();
}
//�鿴�����
function func_viewConfig(idx)
{
	var svrId = getFormFieldValue("record:service_id", idx);
	var page = new pageDefine( "/txn40200014.do", "�鿴�����", "modal", "1000", "800");
	page.addValue( svrId, "primary-key:service_id" );
	page.addValue( "true", "inner-flag:enableCopy");
	page.updateRecord();
}
// ɾ�������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40200011.do", "ɾ�������", document.clientWidth, document.clientHeight);
	page.addParameter( "record:service_id", "primary-key:service_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ɾ������
function func_deleteRecord(idx,type)
{
	var page = new pageDefine( "/txn40200011.do", "ɾ�������" );
	page.addValue( idx, "primary-key:service_id" );
	page.addValue( type, "primary-key:service_type" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}
// �޸�����ͣ��״̬
function func_record_changeStatus()
{
	var page = new pageDefine( "/txn40200013.do", "����/ͣ��" );
	page.addParameter( "record:service_id", "primary-key:service_id" );
	page.addParameter( "record:service_state", "primary-key:service_state" );
	page.deleteRecord( "�Ƿ��޸ķ���״̬" );
}
// �޸ĵ�������ͣ��״̬
function func_record_changeOneStatus(service_id,service_type,service_state)
{
	var page = new pageDefine( "/txn40200013.do", "����/ͣ��", "modal" );
	page.addValue( service_id, "primary-key:service_id" );
	page.addValue( service_type, "primary-key:service_type" );
	if(service_state==="Y"){
		service_state = "N";
	}else{
		service_state = "Y";
	}
	page.addValue( service_state, "primary-key:service_state" );
	page.deleteRecord( "�Ƿ��޸ķ���״̬" );
}

//��������
function func_record_export(idx)
{

	var svrId = getFormFieldValue("record:service_id", idx);
	var page = new pageDefine( "/txn40200016.do", "����������Ϣ");
	page.addValue( svrId, "primary-key:service_id" );
	
	//alert(getFormFieldValue("record:interface_id"));
	//alert(getFormFieldValue("record:service_targets_id"));
	//alert(getFormFieldValue("record:service_state"));
	//alert(getFormFieldValue("record:service_type"));
	
	var stateCode = getFormFieldValue("record:service_state", idx);
	var stateValue="ͣ��";
	if(stateCode=="Y")
	stateValue="����";
	
	page.addValue( getFormFieldValue("record:interface_id", idx), "record:interface_id" );
	page.addValue( getFormFieldValue("record:service_targets_id", idx), "record:service_targets_id" );
	page.addValue( stateValue, "record:service_state" );
	page.addValue( getFormFieldValue("record:service_type", idx), "record:service_type" );
	page.goPage();
}
//�鿴�����������ӿ�����
function func_record_viewRecord(id)
{
	var interface_id = getFormFieldValue("record:interface_id1", id);
	var page = new pageDefine( "/txn401010.do", "�鿴�����������ӿ�����","modal" );
	page.addValue( interface_id, "primary-key:interface_id" );
	page.updateRecord();
}

//�鿴�������
function func_record_viewFwdx(idx) {
	
	var svrId = getFormFieldValue("record:service_targets_id1", idx);
	var page = new pageDefine( "/txn201009.do", "�鿴�������","modal" );
	page.addValue( svrId, "primary-key:service_targets_id" );
	page.updateRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:service_id");
	var service_state = document.getElementsByName("record:service_state");
	var service_type = getFormAllFieldValues("record:service_type");
	var service_no = getFormAllFieldValues("record:service_no");
	var service_name = getFormAllFieldValues("record:service_name"); 
	var service_targets_id=getFormAllFieldValues("record:service_targets_name"); 
	for(var i=0; i<ids.length; i++){  
	   if(service_state[i].value!="G"){//�鵵״̬�Ĺ�����񲻿��޸�
		   var htm = '<a href="#" title="�޸�" onclick="func_record_updateRecord('+i+')"><div class="edit"></div></a>';
		   htm += '<a href="#" title="ɾ��" onclick="func_deleteRecord(\''+ids[i]+'\',\''+service_type[i]+'\');"><div class="delete"></div></a>';
		   if(service_state[i].value=="Y"){
		   htm+='<a href="#" title="���ͣ��" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+service_type[i]+'\',\''+service_state[i].value+'\');"><div class="run"></div></a>';
		   }else
		   {
		   htm+='<a href="#" title="�������" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+service_type[i]+'\',\''+service_state[i].value+'\');"><div class="stop"></div></a>';
		   }
		   htm+='<a href="#" title="����" onclick="func_record_export('+i+');"><div class="download"></div></a>';
		   if(service_type[i]!=null&&service_type[i]=="FTP"){
		   	htm+='<a href="#" title="����FTP�������" onclick="func_record_ftp(\''+ids[i]+'\',\''+service_no[i]+'\',\''+service_targets_id[i]+'\',\''+service_name[i]+'\',\''+service_state[i].value+'\');"><div class="config"></div></a>';
		   }
		   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	   }
	 }
	 
	var names = getFormAllFieldValues("record:service_name");
	var names1 = getFormAllFieldValues("record:interface_id");
	var names2 = getFormAllFieldValues("record:service_targets_id");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_viewConfig(\''+i+'\');">'+ names[i]+'</a>';
	   document.getElementsByName("span_record:service_name")[i].innerHTML = htm;
	   //document.getElementsByName('span_record:service_name')[i].inner
	   
	   
	    htm2 = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_record_viewRecord(\''+i+'\');">'+ names1[i]+'</a>';
	   document.getElementsByName("span_record:interface_id")[i].innerHTML = htm2;
	   
	   
	   htm3 = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_record_viewFwdx(\''+i+'\');">'+ names2[i]+'</a>';
	   document.getElementsByName("span_record:service_targets_id")[i].innerHTML = htm3;
	   
	}
	
	//alert($(".btn_testnew"));
	$(".btn_testnew").hide();
	/* $('table#record tr:gt(0)').each(function(){
		$(this).find('td:last span').css('text-align','left');
	}) */
}
	_browse.execute('__userInitPage()');
</script>
<freeze:body>
<freeze:title caption="��ѯ������б�"/>
<freeze:errors/>
<gwssi:panel action="txn40200001" target="" parts="t1,t2,t3,t4" styleClass="wrapper">
  <gwssi:cell id="t1" name="��������" key="service_type"  data="svrType" />
  <gwssi:cell id="t2" name="����״̬" key="service_state" data="svrState" /> 
  <gwssi:cell id="t3" name="��������" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" maxsize="10" />
  <gwssi:cell id="t4" name="�����ӿ�" key="interface_id" data="pinyinInterface" isPinYinOrder="true" pop="false" move2top="true" maxsize="10" />
  <%-- <gwssi:cell id="t5" name="ѡ��ʱ��" key="created_time" data="svrInterface" date="true"/> --%>
 </gwssi:panel>

<freeze:form action="/txn40200001">
  <freeze:frame property="select-key" >
     <freeze:hidden property="service_type" caption="��������" />
     <freeze:hidden property="service_state" caption="����״̬" />
     <freeze:hidden property="service_targets_id" caption="��������" />
     <freeze:hidden property="interface_id" caption="�����ӿ�" />
     <freeze:hidden property="created_time" caption="ѡ��ʱ��" />
  </freeze:frame>
  <freeze:grid property="record" checkbox="false" multiselect="false" caption="��ѯ������б�" keylist="service_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="��������" txncode="40200006" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
   	  <freeze:button name="record_testRecord_old" caption="���Է���ӿ�"  enablerule="0" enablerule="2" hotkey="ADD" align="right" onclick="func_record_testRecord();"/>
       <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="service_id" caption="����ID" style="width:10%" visible="false"/>
      <freeze:hidden property="service_targets_id1" caption="�������" />
      <freeze:cell property="service_targets_id" caption="�������" align="center" valueset="��Դ����_��������������" style="width:10%" />
      <freeze:hidden property="service_targets_name" caption="�������" style="width:15%; text-align: center;" />
      <freeze:cell property="service_name" caption="��������"  style="" />
      <freeze:hidden property="servicold_service_noe_no" caption="������" style="width:20%" />
      <freeze:hidden property="old_service_no" caption="������" style="width:10%" />
      <freeze:hidden property="interface_id1" caption="�ӿ�����"  />
      <freeze:cell property="interface_id" caption="�����ӿ�����" valueset="�������_�ӿ�����" style="width:20%" />
      <freeze:cell property="service_type" caption="��������" align="center" valueset="��Դ����_����Դ����" style="width:75px;" />
      <freeze:hidden property="service_state" caption="����״̬" style="" /> 
    
      <freeze:cell property="yhxm" align="center" caption="����޸���" style="width:10%" />
        <freeze:cell property="last_modify_time" nowrap="true" caption="����޸�����" align="center" style="width:10%" />  
      <freeze:cell property="oper" nowrap="true" caption="����" style="width:130px; text-align: left;" />
      <freeze:hidden property="column_no" caption="������ID" style=" " />
      <freeze:hidden property="column_name_cn" caption="��������������" style=" " />
      <freeze:hidden property="column_alias" caption="���������" style=" " />
      <freeze:hidden property="sql" caption="SQL" style=" " />
      <freeze:hidden property="sql_one" caption="SQL1" style=" " />
      <freeze:hidden property="sql_two" caption="SQL2" style=" " />
      <freeze:hidden property="service_description" caption="����˵��" style=" " />
      <freeze:hidden property="regist_description" caption="����˵��" style=" " />

      <freeze:hidden property="last_modify_id" caption="����޸���ID" style=" " />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" style=" " />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
