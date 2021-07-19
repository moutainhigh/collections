<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="950" height="350">
<head>
<title>���Ӳɼ�������Ϣ</title>
</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	//document.forms["CollectTaskUpdateForm"].setAttribute("action", "/txn30101003.do");
	saveRecord( '', '����ɼ�����' );
	//parent.window.location.reload();
	/**var page = new pageDefine("/txn30101000.ajax", "�������Դ�����Ƿ�ʹ��");	 ///���������Դ�����Ƿ�ʹ��
	page.addParameter("record:data_source_id","primary-key:data_source_id");
	page.callAjaxService('nameCheckCallback');*/
	
}

// ���Ӳɼ����������Ϣ
function func_record_addRecord()
{
    var collect_table_id=getFormFieldValue('record:collect_table_id');
    if(collect_table_id==null||collect_table_id==""){
	    alert("������д�ɼ�����Ϣ!");
	    clickFlag=0;
    }else{
	var page = new pageDefine( "/txn30501003.do", "���Ӳɼ����ݿ���������Ϣ", "modal" );
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	page.addRecord();
	}
	 
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
  		  var page = new pageDefine( "/txn30101003.do", "����ɼ������");
  		  
	     /** page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      page.addParameter("record:task_name","record:task_name");
	      page.addParameter("record:data_source_id","record:data_source_id");
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
	      page.callAjaxService('insertTask');*/
  		
  		}
}
function insertTask(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    var collect_task_id=_getXmlNodeValues(xmlResults,'record:collect_task_id');
		    setFormFieldValue("record:collect_task_id",collect_task_id);
		    setFormFieldValue("primary-key:collect_task_id",collect_task_id);
		    alert("����ɹ�!");
		}
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����ɼ������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ɼ������' );	// /txn30101001.do
}

// �� ��
function func_record_goBack()
{
	parent.window.location.href="txn30101001.do";	
	//parent.goBack();
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�//����Դ�����ݿ����ʹ���Ϊ01
    var parameter = "input-data:service_targets_id="+ getFormFieldValue('record:service_targets_id')+"&input-data:collectType=<%=CollectConstants.TYPE_CJLX_FILEUPLOAD%>";
	return parameter;
}
// �޸ķ�����Ϣ
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn30102004.do", "�޸ķ�����Ϣ","modal");
	page.addValue(idx,"primary-key:webservice_task_id");
	var service_targets_id=getFormFieldValue("record:service_targets_id");
	page.addValue(service_targets_id,"primary-key:service_targets_id");
	
	page.updateRecord();
}
// ɾ��������Ϣ
function func_record_deleteRecord(idx)
{
if(confirm("�Ƿ�ɾ��ѡ�еļ�¼")){
	var page = new pageDefine( "/txn30102005.do", "ɾ��������Ϣ" );
	page.addValue(idx,"primary-key:webservice_task_id");
	page.addParameter("record:service_targets_id","primary-key:service_targets_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.addParameter("record:fj_fk","record:fj_fk");
	page.addParameter("record:fjmc","record:fjmc");
	//page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" ); 
	page.updateRecord();
	}
	
}
//�鿴�ɼ����������Ϣ
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30102006.do", "�鿴�ɼ����񷽷���Ϣ", "modal" );
	page.addValue(idx, "primary-key:webservice_task_id" );
	var collect_task_id=getFormFieldValue('record:collect_task_id');
	page.addValue(collect_task_id,"primary-key:collect_task_id");
	page.updateRecord();
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
    
    var page = new pageDefine("/txn30101009.ajax", "��ȡ�ɼ������Ӧ����");	 ///���������Դ�����Ƿ�ʹ��
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:data_source_id","record:data_source_id");
	page.callAjaxService('getData');
    
    
	//var page = new pageDefine( "/txn30101009.ajax", "��ȡ�ɼ������Ӧ����");
	
	//page.updateRecord();
}
function getData(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("��ȡ�����ɹ�!");
		}
}


function changeTarget()
{
	if (getFormFieldValue("record:service_targets_id") != null && getFormFieldValue("record:service_targets_id") != "")
	{
		var name=document.getElementById("record:_tmp_service_targets_id").value;
		setFormFieldValue("record:task_name",name+"_���ݿ�");
		setFormFieldValue("record:data_source_id",0,"");
	}
}
//���Ʋɼ�����
function chooseCjzq(){
var collect_task_id=getFormFieldValue('record:collect_task_id');
	if(collect_task_id==null||collect_task_id==""){
	    alert("���ȱ���ɼ�������Ϣ!");
	    clickFlag=0;
	    return false;
    }
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



// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids = getFormAllFieldValues("dataItem:webservice_task_id");
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
<freeze:title caption="���Ӳɼ�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30101041" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%"/>
  </freeze:frame>
  
  <div style="width:95%;margin-left:33px" >
  <table style="width:65%"  >
   <tr>
   
    <td width="30%">
   
    	<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="color:white;background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					��һ�������û�����Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%" >
    	<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					   �ڶ��������òɼ�����Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%">
 		<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					�����������ù�����Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>   
     </td>
     
   </tr> 
  </table>
  </div>
  
  <freeze:block property="record" caption="���Ӳɼ�������Ϣ" width="95%">
      <freeze:button name="record_nextRecord" caption="��һ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" notnull="true" valueset="��Դ����_�����������" onchange="changeTarget()"   style="width:95%"/>
      <freeze:text property="task_name" caption="��������" datatype="string" notnull="true" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="data_source_id" caption="����Դ" show="name" valueset="��Դ����_��������Ӧ����Դ" notnull="true" parameter="getParameter()"  style="width:38.5%" colspan="2"/>
      <freeze:hidden property="scheduling_day1" caption="�ɼ�����" datatype="string" readonly="true" style="width:80%"/><!--  &nbsp;<INPUT TYPE="button" Value="����" onclick="chooseCjzq()" class="FormButton"> -->
      <freeze:hidden property="collect_type" caption="�ɼ�����" value="03" datatype="string" maxlength="20" />
      <freeze:textarea property="task_description" caption="����˵��" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:textarea property="record" caption="����˵��" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="fjmc" caption="�ļ��ϴ�" />
      <freeze:hidden property="fj_fk" caption="�ļ��ϴ�id" style="width:90%" />
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"  />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="task_status" caption="����״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="task_scheduling_id" caption="�ƻ�����ID"  />
      <freeze:hidden property="scheduling_type" caption="�ƻ���������"  />
      <freeze:hidden property="start_time" caption="�ƻ�����ʼʱ��"  />
      <freeze:hidden property="end_time" caption="�ƻ��������ʱ��"  />
      <freeze:hidden property="scheduling_week" caption="�ƻ���������"  />
      <freeze:hidden property="scheduling_day" caption="�ƻ���������"  />
      <freeze:hidden property="scheduling_count" caption="�ƻ�����ִ�д���"  />
      <freeze:hidden property="interval_time" caption="ÿ�μ��ʱ��"  />
    
<%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
    Recordset fileList=null;
    try{
     fileList = context.getRecordset("fjdb");

    if(fileList!=null && fileList.size()>0){
    	out.println("<tr><td height=\"32\" align=\"right\">�����ļ���</td><td colspan=\"3\"><table >");
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
%>
<tr id='<%=file_id%>'>
 <td><a href="#" onclick="downFile('<%=file_id%>')" title="����" ><%=file_name %></a></td>
 <td><a href="#" onclick="delChooseFile('<%=file_id%>','record:delIDs','<%=file_name%>','record:delNAMEs')"  title="ɾ��" ><span class="delete">&nbsp;&nbsp;</span></a>
</tr>
<%  }
    out.println("</table></td></tr>"); 
   }
   }catch(Exception e){
	   System.out.println(e);
   }
%>   
  <!-- <span class="btn_add" onclick="addNewRowEx('record:fjmc1','&nbsp;�ϴ��ļ���','80%')" title="����">&nbsp;&nbsp;</span> -->
<freeze:file property="fjmc1" caption="�����ļ�" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="����"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table>

  </freeze:block>
 <br>
  
</freeze:form>
</freeze:body>
</freeze:html>