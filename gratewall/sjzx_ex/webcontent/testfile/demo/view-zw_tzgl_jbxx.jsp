<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�֪ͨ������Ϣ</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����֪ͨ����' );	// /txn315001001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn315001001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�֪ͨ������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn315001006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="jbxx_pk" caption="֪ͨ���-����" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�֪ͨ������Ϣ" width="95%">
      
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jbxx_pk" caption="֪ͨ���-����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="tzmc" caption="֪ͨ����" datatype="string"  colspan="2" style="width:98%"/>
      <freeze:hidden property="fbrid" caption="������ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fbrmc" caption="����������" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fbksid" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fbksmc" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fbsj" caption="����ʱ��" datatype="string"  style="width:95%"/>
      <freeze:hidden property="tznr" caption="֪ͨ����"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="tzzt" caption="֪ͨ״̬" datatype="string"  style="width:95%"/>
      <freeze:hidden property="jsrids" caption="������ids"  maxlength="4000" style="width:98%"/>
      <freeze:hidden property="jsrmcs" caption="����������"  maxlength="2000" style="width:98%"/>
      
      <tr>
      <td align="right" height="32">֪ͨ����&nbsp;</td>
      <td>
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
    </tr>
    <freeze:hidden property="fjmc" caption="֪ͨ����" style="width:76%" />
    <freeze:hidden property="fj_pk" caption="֪ͨ����id" style="width:90%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
