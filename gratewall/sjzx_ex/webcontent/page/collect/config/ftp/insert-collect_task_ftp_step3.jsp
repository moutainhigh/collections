<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>���Ӳɼ�������Ϣftp</title>

</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{

 	document.forms["CollectTaskUpdateForm"].setAttribute("action", "/txn30101103.do");
	saveRecord( '', '����ɼ�����' );
	func_record_goBack();
	/**var page = new pageDefine("/txn30101000.ajax", "�������Դ�����Ƿ�ʹ��");	 ///���������Դ�����Ƿ�ʹ��
	page.addParameter("record:data_source_id","primary-key:data_source_id");
	page.callAjaxService('nameCheckCallback');*/
}
//�����ݱ������ת�زɼ������ѯ�б�
function func_record_goBack()
{
	var flag = getFormFieldValue("record:flag");
	if(flag=="add"){//����ҳ��
		parent.window.location.href="txn30101001.do";	
	}else if (flag="edit"){//�༭ҳ��
		window.location.href="txn30101001.do";
	}
	
}

// �� �صڶ���
function func_record_prev()
{
	var page = new pageDefine( "/txn30101105.do", "���òɼ���Ϣ");
	page.addParameter("select-key:collect_task_id","select-key:collect_task_id");
	page.addParameter("select-key:task_name","select-key:task_name");
	page.addParameter("select-key:flag","select-key:flag");
	page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
	page.goPage();
}


//���Ʋɼ�����
function chooseCjzq(){
var collect_task_id=getFormFieldValue('select-key:collect_task_id');
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


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	/* var collect_task_id = getFormFieldValue("record:collect_task_id");
	 //var service_targets_id1 = getFormFieldValue("select-key:service_targets_id");
	 var task_scheduling_id = getFormFieldValue("record:task_scheduling_id");
	alert("collect_task_id="+collect_task_id+"--task_scheduling_id="+task_scheduling_id);  */
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӳɼ�������Ϣ"/>
<freeze:errors/>
<div style="width: 95%;margin-left:20px;">
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
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								�ڶ����������ļ���Ϣ
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
								�����������ù�����Ϣ
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>

			</tr>
		</table>
	</div>
<freeze:form action="/txn30101102" enctype="multipart/form-data">
 <freeze:block property="select-key" caption="���Ӳɼ�������Ϣ" >
	  <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" />
      <freeze:hidden property="service_targets_id" caption="�����������"  />
      <freeze:hidden property="task_name" caption="��������" />
      <freeze:hidden property="flag" caption="������������Ǳ༭"  />
       
	 </freeze:block>
  <freeze:block property="record" caption="�鿴�ɼ�������Ϣ" width="95%">
      <freeze:text property="scheduling_day1" caption="�ɼ�����" datatype="string" readonly="true" style="width:80%"/>&nbsp;<INPUT TYPE="button" Value="����" onclick="chooseCjzq()" class="FormButton">
       	
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="�����������" show="name" valueset="��Դ����_���������������"    style="width:95%"/>
      <freeze:cell property="task_name" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:cell property="task_status" caption="����״̬" datatype="string" valueset="��Դ����_�鵵����״̬" style="width:95%"/>
      <freeze:cell property="data_source_id" caption="����Դ" show="name" valueset="��Դ����_����Դ"  style="width:95%"/>
      <freeze:hidden property="collect_type" caption="�ɼ�����" datatype="string" maxlength="20" />
      <freeze:cell property="task_description" colspan="2"  caption="����˵��"  style="width:98%"/>
      <freeze:cell property="record" caption="����˵��" colspan="2" style="width:98%"/>
      <freeze:hidden property="fjmc" caption="�ļ��ϴ�" />
      <freeze:hidden property="fj_fk" caption="�ļ��ϴ�id" style="width:90%" />
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"  />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      
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
      <freeze:hidden property="log_file_path" caption="��־�ļ�·��"  />
      <freeze:hidden property="collect_status" caption="�ɼ�״̬"  />
    
       <%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
    Recordset fileList=null;
    try{
    fileList = context.getRecordset("fjdb");
    if(fileList!=null && fileList.size()>0){
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
%>
	<tr>
	<td height="32" align="right">��������&nbsp;</td>
	<td colspan="3">
		
	<a href="#" onclick="downFile('<%=file_id%>')" title="����" ><%=file_name %></a>
	</td>
	</tr>
	
	<% }
	     }
	   }catch(Exception e){
		   System.out.println(e);
	   }
	%>   
    
  </freeze:block>
  
  <table align='center' cellpadding=0 cellspacing=0 width="95%">
			<tr>
				<td align="center" height="50">
					<div class="btn_prev" onclick="func_record_prev();"></div>					
					&nbsp;&nbsp;&nbsp;&nbsp;
					<div class="btn_save" onclick="func_record_saveRecord();"></div>
				</td>
			</tr>
	</table>

   <freeze:grid property="dataItem" caption="�ļ��б�" keylist="ftp_task_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:hidden property="ftp_task_id" caption="ftp����ID"  />
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="file_name_cn" caption="�ļ���������" style="width:20%" />
      <freeze:cell property="file_name_en" caption="�ļ�����" style="width:15%" />
      <freeze:cell property="collect_mode" caption="�ɼ���ʽ"   valueset="��Դ����_�ɼ���ʽ" style="width:15%" /> 
      <freeze:cell property="collect_table" caption="�ɼ���" align="center"  valueset="��Դ����_�ɼ���" show="name" style="width:20%"/>
      						       
      <freeze:cell property="file_description" caption="�ļ�����" style="width:30%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
