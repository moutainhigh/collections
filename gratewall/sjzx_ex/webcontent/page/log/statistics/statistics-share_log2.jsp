<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<title>��ѯϵͳ��־�б�</title>
</head>

<script language="javascript">
//TODO ��ť��Ҫ�滻
function _menuClick(id)
{
	func_show_selectPic();
}
function func_show_selectPic()
{
	//var page = new pageDefine( "statistics-share_log_showPic1.jsp", "��������Դ");
	var page = new pageDefine( "/txn6020004.do", "�鿴ͼƬ");
	
	var chk_value =[];  
	$('input[name="query_index"]:checked').each(function(){  
	chk_value.push($(this).val());  
	});  
	
	page.addValue( chk_value, "select-key:query_index" );
	//page.addValue( id, "primary-key:interface_id" );
	//page.addParameter( "select-key:service_targets", "primary-key:interface_id" );
	page.addParameter( "select-key:service_targets", "select-key:service_targets_id" );
	//page.addParameter( "select-key:query_index", "select-key:query_index" );
	page.addParameter( "select-key:statistical_cycle", "select-key:statistical_cycle" );
	page.addParameter( "select-key:show_type", "select-key:show_type" );
	page.addParameter( "select-key:statistical_granularity", "select-key:statistical_granularity" );
	
    var win = window.frames('showPic');
  	document.getElementById("showPic").style.display = "block";
	page.goPage( null, win );
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	func_show_selectPic();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�������ͳ��"/>

<freeze:errors/>
<freeze:form action="/txn6020001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="service_targets" caption="�������" valueset="��Դ����_�����������" data="mix"  style="width:95%"/>
      <freeze:select property="show_type" caption="չ�ַ�ʽ"  value="02" valueset="��־����_չ�ַ�ʽ" style="width:95%"/>
      <freeze:select property="statistical_cycle" caption="ͳ������"  value="01" valueset="��־����_ͳ������"  style="width:95%"/>
      <freeze:select property="statistical_granularity" caption="ͳ������" value="00" valueset="��־����_ͳ������"  style="width:95%"/>
     
           
          <tr> 
      <td width="15%" align="right" class="odd12" id="label:select-key:query_index">
      ָ�꣺
      </td>
      <td width="35%" class="odd12">
      <input type="checkbox" id="query_index0" value="����������" name="query_index" checked="checked">���������� 
      <input type="checkbox" id="query_index1" value="���ô���" name="query_index" checked="checked">���ô��� 
      </td>
      <td width="15%" class="odd12"></td>
       <td width="35%" class="odd12"></td>
      </tr>
  </freeze:block>
<br/>
  <div align="center">
 <iframe id='showPic' scrolling='no' frameborder='0' align="middle" width='95%' style="display:none;height:480px;"></iframe>
</div>
</freeze:form>
</freeze:body>
</freeze:html>
