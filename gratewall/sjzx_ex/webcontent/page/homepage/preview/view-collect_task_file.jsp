<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html>
<head>
<title>�鿴�ļ��ϴ��ɼ�����Ϣ</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '�����ļ��ϴ��ɼ���' );	// /txn30301001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30301001.do  txn30101001
	//parent.window.location.href="txn30101001.do";
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn30101023" enctype="multipart/form-data">
  <freeze:block property="record" caption="�޸��ļ��ϴ��ɼ�����Ϣ" width="95%">
      
      <freeze:hidden property="file_upload_task_id" caption="�ļ��ϴ�����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string"  style="width:95%"/>
      
      <freeze:hidden property="service_targets_id" caption="�����������" style="width:95%"/>
      <freeze:hidden property="task_name" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:cell property="collect_table" caption="�ɼ���"   valueset="��Դ����_�ɼ���" show="name"  style="width:95%"/>
      <freeze:cell property="collect_mode" caption="�ɼ���ʽ" show="name"  valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>
      <freeze:cell property="task_description" caption="����˵��" colspan="2"  style="width:98%"/>
      <freeze:cell property="record" caption="����˵��" colspan="2"  style="width:98%"/>
     <%--  <tr >
      <td align="right" height="32">�ɼ��ļ�&nbsp;</td>
      <td colspan="3">
      <%
        DataBus context1 = (DataBus) request.getAttribute("freeze-databus");
        Recordset fileList=null;
        try{
          fileList = context1.getRecordset("fjdb");
      %>
      <br>
      <%
          if(fileList!=null && fileList.size()>0){
            for(int i=0;i<fileList.size();i++){
              DataBus file = fileList.get(i);
              String file_id = file.getValue(FileConstant.file_id);
              String file_name = file.getValue(FileConstant.file_name);
      %>
      <a href="#" onclick="downFile('<%=file_id%>')" title="����"><%=file_name %></a><br><br>
      <%}}
        }catch(Exception e){
	      System.out.println(e);
        }
      %>
      </td>
    </tr> --%>
      <freeze:hidden property="collect_file_name" caption="�ɼ��ļ�" accept="*.xls,*.txt,*.mdb" style="width:80%" maxlength="200" colspan="2"/>
      
      <freeze:hidden property="collect_type" caption="�ɼ�����"  value="01"  style="width:95%" />
      <freeze:hidden property="data_source_id" caption="����Դ"  style="width:95%"/>
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
      
      <freeze:hidden property="file_status" caption="�ļ�״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="file_description" caption="�ļ�����"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="collect_file_id" caption="�ɼ��ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="check_result_file_name" caption="У�����ļ�����"  maxlength="300" style="width:98%"/>
      <freeze:hidden property="check_result_file_id" caption="У�����ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
