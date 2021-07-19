<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>

<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<title>��ѯϵͳ��־�б�</title>
<style type="text/css">
.selectMenu {
	padding: 2px;
	background-color: #2B92E8;
	COLOR: #fff;
}
</style>
</head>

<script type="text/javascript">
//TODO ��ť��Ҫ�滻
function _menuClick(id)
{
	func_show_selectPic();
}
function func_show_selectPic()
{
	
	//var show_type=getFormFieldValue("select-key:show_type");
	var page = new pageDefine( "/txn6020006.do", "�鿴ͼƬ");
	page.addParameter( "select-key:service_targets_id", "select-key:service_targets_id" );
	page.addParameter( "select-key:service_targets_type", "select-key:service_targets_type" );
	page.addParameter( "select-key:query_index", "select-key:query_index" );
	page.addParameter( "select-key:show_type", "select-key:show_type" );
	//page.addValue(show_type, "select-key:show_type" );
	page.addValue($("#show_num").val(), "select-key:show_num" );
    var win = window.frames('showPic');
  	document.getElementById("showPic").style.display = "block";	
	page.goPage( null, win );
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$('#t2').find('.pack-list li:eq(1)').hide();
	var show_type=getFormFieldValue("select-key:show_type");
	if(!show_type){//Ĭ��ͼ��ʽչʾ
		setFormFieldValue ('select-key:show_type','chart');
	}
	func_show_selectPic();
	//var $dateselect = $(document.getElementById('select-key:query_index'));
	//$dateselect.find('option:first').remove();
}

function doQuery(type){
	setFormFieldValue ('select-key:show_type',type);
	document.getElementById('chart_type').value=type;
	document.getElementById('chartId').className = '';
	document.getElementById('tableId').className = '';
	document.getElementById(type + "Id").className = 'selectMenu';
	func_show_selectPic();
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����δ�����ͳ��"/>

<freeze:errors/>

<gwssi:panel action="txn6020005" target="" parts="t1,t2" styleClass="wrapper">
   <gwssi:cell id="t1" name="�������" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" pop="true"  maxsize="10"  />
   <gwssi:cell id="t2" name="ͳ������" key="query_index"  data="query_index"  />
  </gwssi:panel>
<freeze:form action="/txn6020005">
	<freeze:frame property="select-key" >
     	<freeze:hidden property="query_index" caption="ͳ������" />
    	<freeze:hidden property="service_targets_id" caption="�������" />
    	<freeze:hidden property="service_targets_type" caption="�����������" />
    	<freeze:hidden property="show_type" caption="����չʾ��ʽ" />
  	  </freeze:frame>

<br/>
<div align="center">
 <div align="right" style="margin-right: 30px;"><a href="javascript:void('');" onclick="doQuery('chart');"><span id="chartId" class="selectMenu">ͼ��</span></a>|
 <a  href="javascript:void('');" onclick="doQuery('table');"><span id="tableId">���</span></a>
 ��ʾ����
 <select id="show_num" onchange="func_show_selectPic();">
   <option value="10">10</option>
   <option value="20">20</option>
   <option value="30">30</option>
   <option value="50">50</option>
   <option value="100">100</option>
   <option value="1000000">ȫ��</option>
 </select></div>
 <iframe id='showPic' scrolling='no' frameborder='0' align="middle" 
     width='95%' style="display:none;height:480px;"></iframe>
</div>
<input type="hidden" id="chart_type" value="chart"/>
</freeze:form>
</freeze:body>
</freeze:html>
