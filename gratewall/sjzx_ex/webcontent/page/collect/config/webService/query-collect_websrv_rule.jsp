<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>���Ӳɼ�������Ϣ</title>
</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>

<script language="javascript">
var collectType;

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����ɼ�����' );
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
	goBack();
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
	collectType = getFormFieldValue("record:collect_type");
    var parameter = "input-data:service_targets_id="+ getFormFieldValue('record:service_targets_id')+"&input-data:collectType="+collectType;
	return parameter;
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
		setFormFieldValue("record:task_name",name+"_Web Service");
		setFormFieldValue("record:data_source_id",0,"");
	}
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//alert(parent.document.getElementById("primary-key:collect_type").value);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӳɼ�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30101003" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%"/>
  </freeze:frame>
  
    <div style="width:95%;margin-left:20px" >
  <table style="width:65%"  >
   <tr>
    <td width="30%">
    	<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					��һ�������û�����Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%" >
    	<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					   �ڶ��������÷�����Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%">
 		<table width="100%" cellpadding="0" cellspacing="0">
 			<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="color:white;background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					�����������ù�����Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>   
     </td>
     
   </tr> 
  </table>
  </div>
  
 <br>

</freeze:form>
</freeze:body>
</freeze:html>
