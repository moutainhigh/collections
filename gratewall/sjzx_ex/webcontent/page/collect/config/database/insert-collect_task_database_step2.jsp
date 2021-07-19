<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�޸Ĳɼ�������Ϣ</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">
// �� ��
function func_record_saveRecord(){
   
	document.forms["CollectTaskUpdateForm"].setAttribute("action", "/txn30101032.do");
	saveRecord( '', '����ɼ�������Ϣ' );
	/**
		  var page = new pageDefine( "/txn30101002.do", "����ɼ�������Ϣ");
	      page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      page.addParameter("record:task_name","record:task_name");
	      page.addParameter("record:data_source_id","record:data_source_id");
	      page.addParameter("record:collect_type","record:collect_type");
	      page.addParameter("record:task_description","record:task_description");
	      page.addParameter("record:record","record:record");
	      page.addParameter("record:task_status","record:task_status");
	      page.addParameter("record:is_markup","record:is_markup");
	      page.addParameter("record:creator_id","record:creator_id");
	      page.addParameter("record:created_time","record:created_time");
	      page.addParameter("record:last_modify_id","record:last_modify_id");
	      page.addParameter("record:last_modify_time","record:last_modify_time");
	      page.addParameter("record:fj_fk","record:fj_fk");
	      page.addParameter("record:fjmc","record:fjmc");
	      page.updateRecord();
	
	var page = new pageDefine("/txn30101000.ajax", "�������Դ�����Ƿ�ʹ��");	
	page.addParameter("record:data_source_id","primary-key:data_source_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.callAjaxService('nameCheckCallback');
	*/
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("����Դ�����Ѿ�ʹ��");
  		}else{
  		  var page = new pageDefine( "/txn30101032.ajax", "����ɼ�������Ϣ");
	      page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      page.addParameter("record:task_name","record:task_name");
	      page.addParameter("record:data_source_id","record:data_source_id");
	      page.addParameter("record:collect_type","record:collect_type");
	      page.addParameter("record:task_description","record:task_description");
	      page.addParameter("record:record","record:record");
	      page.addParameter("record:task_status","record:task_status");
	      page.addParameter("record:is_markup","record:is_markup");
	      page.addParameter("record:creator_id","record:creator_id");
	      page.addParameter("record:created_time","record:created_time");
	      page.addParameter("record:last_modify_id","record:last_modify_id");
	      page.addParameter("record:last_modify_time","record:last_modify_time");
	      page.callAjaxService('updateTable');
  		}
}
function updateTable(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("����ɹ�!");
		}
}

// �� �沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ɼ������' );	// /txn30101001.do
}

// �� ��
function func_record_goBack()
{
	var page = new pageDefine( "/txn30101001.do", "��ѯ�ɼ�����");
	page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
	page.addParameter("select-key:collect_type","select-key:collect_type");
	page.addParameter("select-key:task_status","select-key:task_status");
	page.updateRecord();
	//goBack();	// /txn30101001.do
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�//����Դ�����ݿ����ʹ���Ϊ01
    var parameter = "input-data:service_targets_id="+ getFormFieldValue('record:service_targets_id')+"&input-data:collectType=<%=CollectConstants.TYPE_CJLX_FILEUPLOAD%>";
	return parameter;
}

// ��ȡ�ɼ������Ӧ����
function func_record_getFunction()
{
    var collect_task_id=getFormFieldValue('record:collect_task_id');
	if(collect_task_id==null||collect_task_id==""){
	    alert("������д�ɼ�������Ϣ!");
	    clickFlag=0;
	    return false;
    }
	var page = new pageDefine( "/txn30101009.do", "��ȡ�ɼ������Ӧ����");
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:data_source_id","record:data_source_id");
	page.updateRecord();
	//page.callAjaxService('getFun');
}
function getFun(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("��ȡ�����ɹ�!");
		}
}
// �޸ķ�����Ϣ
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn30501009.do", "�޸ķ�����Ϣ","modal");
	page.addValue(idx,"primary-key:database_task_id");
	var collect_task_id=getFormFieldValue('record:collect_task_id');
	page.addValue(collect_task_id,"primary-key:collect_task_id");
	//var service_targets_id=getFormFieldValue("record:service_targets_id");
	//page.addValue(service_targets_id,"primary-key:service_targets_id");
	
	page.updateRecord();
}
// ɾ��������Ϣ
function func_record_deleteRecord(idx)
{

if(confirm("�Ƿ�ɾ��ѡ�еļ�¼")){
	var page = new pageDefine( "/txn30501013.do", "ɾ��������Ϣ","" );
	page.addValue(idx,"primary-key:database_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.goPage();
	}
	
}
//�鿴�ɼ����������Ϣ
function func_record_viewRecord(idx)
{
	var task_name = getFormFieldValue("record:task_name");
	var page = new pageDefine( "/txn30501008.do", "�鿴������Ϣ", "modal" );
	page.addValue(idx,"primary-key:database_task_id");
	page.addValue(task_name,"primary-key:task_name");
	
	page.updateRecord();
}
//�ı�������
function changeTarget()
{
	if (getFormFieldValue("record:service_targets_id") != null && getFormFieldValue("record:service_targets_id") != "")
	{
		var name=document.getElementById("record:_tmp_service_targets_id").value;
		setFormFieldValue("record:task_name",name+"_Web Service");
		setFormFieldValue("record:data_source_id",0,"");
	}
}
//���Ʋɼ�����
function chooseCjzq(){
 var cjzq=getFormFieldValue("record:scheduling_day1");
 if(cjzq==null||""==cjzq){
 	//var page = new pageDefine( "/page/collect/config/schedule/insert-collect_task_scheduling.jsp", "�����������", "modal");
	//page.addRecord();
	window.showModalDialog("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
	//window.open("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", null, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
 }else{
 	window.showModalDialog("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
 	//var page = new pageDefine( "/txn30801004.do", "�޸��������", "modal");
	//page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	//page.updateRecord();
 }
}
// ҳ����ת
function grid_goRecord(gridName, index, totalRow, flag)
{

  if( flag == null || flag == '' ){
  	flag = 'go';
  }
  
  var pageRowField = getAttributeObject(gridName, index, 'page-row');
  var startRowField = getAttributeObject(gridName, index, 'start-row');
  
  var pageRow=parseInt(pageRowField.value);
  //DC2-jufeng-20120724 add pageRow selected
  /*var ss = document.getElementById('customPageRowSeleted')
  var pageRow2 = parseInt( ss.options[ss.selectedIndex].text );
  if(pageRow2){
 	 pageRow = pageRow2
  }*/
  if( pageRow > 500 ){
    pageRow = 500;
  }
  else if( pageRow < 1 ){
    pageRow = 10;
  }
  
  if( flag == 'go' ){
  	var objs = document.getElementsByName( gridName + '_selectPage' );
  	var pageno = parseInt(objs[index].value);
  }
  else{
  	var pageno = parseInt(flag);
  }
  
  var startRow = (pageno-1) * pageRow + 1;
  pageRowField.value = pageRow;
  startRowField.value = startRow;
  
  var action =  pageRowField.form.action+'?primary-key:collect_task_id=' + getFormFieldValue("record:collect_task_id");
  action += (action.indexOf('?') > 0) ? '&' : '?';
  
  var param = 'attribute-node:' + gridName + '_page-row=' + pageRow
		+ '&attribute-node:' + gridName + '_start-row=' + startRow
		+ '&attribute-node:' + gridName + '_record-number=' + totalRow;
	
  //alert("111  \n"+pageRow2 +'\n 222 \n'+param)
  
  pageRowField = startRowField = null;
  // modified By WeiQiang 2009-02-12
  // fixed the bug that when change grid's page and the open type is new-window, window will be close
  // alert( action+param );
  var nextPage = action+param+ '&primary-key:' + gridName ;
  // goWindowBack( action+param );
	
	// ȱʡ��������һ��ҳ��
	if( nextPage == null ){
		nextPage = _prePageName;
	}
	
	// ȡ�������ݱ�־
	var addr = _addHrefParameter( nextPage, 'inner-flag:back-flag', 'true' );
 	// alert("ȫ·��:" + addr);
 	
 	// �򿪴��ڵ�����
	if( openWindowType == 'subframe' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'subframe' );
	}else if ( openWindowType == 'new-window' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'new-window' );
	}else{
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'window' );
	}
	
	// ����ȫ·��
	addr = _browse.resolveURL( addr );
	// alert("ȫ·��:" + addr);
 	// ת������
 	addr = page_setSubmitData( window, addr );
	window.location = addr;
  
}


/**
//���Ʋɼ�����
function chooseCjzq(){
 var cjzq=getFormFieldValue("record:interval_time");
 if(cjzq==null||""==cjzq){
 	var page = new pageDefine( "/page/collect/config/schedule/insert-collect_task_scheduling.jsp", "�����������", "modal");
	page.addRecord();
 }else{
 	var page = new pageDefine( "/txn30801004.do", "�޸��������", "modal");
	page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
 }
}
*/

// ���Ӳɼ����������Ϣ
function func_record_addRecord()
{
	var page = new pageDefine( "/txn30501007.do", "���Ӳɼ���������Ϣ", "modal" );
	var collect_task_id=getFormFieldValue('record:collect_task_id');
	page.addValue(collect_task_id,"primary-key:collect_task_id");
	page.addRecord();
}

function func_record_prev(){
	//alert('prev');
	var page = new pageDefine( "/txn301010300.do", "���òɼ���Ϣ");
	var taskId = getFormFieldValue("primary-key:collect_task_id");
	page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	page.goPage();
}

function func_record_next(){ 
	var page = new pageDefine( "/txn301010344.do", "���òɼ�����");
	var taskId = getFormFieldValue("primary-key:collect_task_id");
	page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
   
	var ids = getFormAllFieldValues("dataItem:database_task_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="�鿴" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   htm+='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="func_record_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĳɼ�������Ϣ"/>
<freeze:errors/>
<freeze:form action="/txn30101042" >
  <freeze:frame property="primary-key" width="95%" >
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%"/>
  </freeze:frame>
  <freeze:frame property="record" width="95%" >
      <freeze:hidden property="task_name" caption="�ɼ���������" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%"/>
  </freeze:frame>
  
  <div style="width: 95%;margin-left:33px;">
		<table style="width: 80%;align:left;">
			<tr>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								��һ�������û�����Ϣ
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="color: white; background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								�ڶ��������òɼ�����Ϣ
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								�����������ù�����Ϣ
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>

			</tr>
		</table>
	</div>

   <freeze:grid property="dataItem" caption="�����б�" keylist="database_task_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:button name="record_addRecord" caption="���������" txncode="20202003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="database_task_id" caption="����ID"  />
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="collect_table" caption="Ŀ��ɼ���" valueset="��Դ����_�ɼ���" style="width:20%" />
      <freeze:cell property="collect_mode" caption="�ɼ���ʽ" valueset="��Դ����_�ɼ���ʽ" style="width:15%" />
      <freeze:cell property="source_collect_table" caption="Դ�ɼ���"   style="width:15%" />
      <freeze:cell property="source_collect_column" caption="�����ֶ�" style="width:30%" />
      <freeze:cell property="oper" caption="����" align="center" style="width:15%" />
  </freeze:grid>
  
  <table align='center' cellpadding=0 cellspacing=0 width="95%">
			<tr>
				<td align="center" height="50">
					<div class="btn_prev" onclick="func_record_prev();"></div>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<div class="btn_next" onclick="func_record_next();"></div>
				</td>
			</tr>
	</table>
</freeze:form>
</freeze:body>
</freeze:html>
