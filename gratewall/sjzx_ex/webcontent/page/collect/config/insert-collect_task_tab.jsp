<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template master-detail-3/frame-detail-update.jsp --%>

<freeze:html width="1000" height="400">
<head><title>�½��ɼ�����1</title></head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">
//webservice
function func_insert_webservice()
{
	var page = new pageDefine( "/txn30101007.do", "���Ӳɼ�����webservice");
	page.addValue("<%=CollectConstants.TYPE_CJLX_WEBSERVICE%>","primary-key:collect_type");
    var win = window.frames('ajlist1');
  	document.getElementById("ajlist1").style.display = "block";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "none";
  	document.getElementById("ajlist6").style.display = "none";
  	document.getElementById("ajlist7").style.display = "none";
	
	page.goPage( null, win );
}
//socket
function func_insert_socket()
{

	var page = new pageDefine( "/txn30101051.do", "���Ӳɼ�����socket");
	page.addValue(" ","primary-key:collect_task_id");
	page.addValue("<%=CollectConstants.TYPE_CJLX_SOCKET%>","primary-key:collect_type");
    var win = window.frames('ajlist6');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "none";
  	document.getElementById("ajlist6").style.display = "block";
  	document.getElementById("ajlist7").style.display = "none";
	page.goPage( null, win );
}

//jms
function func_insert_jms()
{

	var page = new pageDefine( "/txn30101061.do", "���Ӳɼ�����jms");
	page.addValue(" ","primary-key:collect_task_id");
	page.addValue("<%=CollectConstants.TYPE_CJLX_JMS%>","primary-key:collect_type");
    var win = window.frames('ajlist5');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "block";
  	document.getElementById("ajlist6").style.display = "none";
  	document.getElementById("ajlist7").style.display = "none";
	page.goPage( null, win );
}
//ftp
function func_insert_ftp()
{	

	var page = new pageDefine( "/txn30101011.do", "���Ӳɼ�����ftp");
	//page.addValue(" ","primary-key:collect_task_id");
	page.addValue(" ","record:collect_task_id");
	page.addValue("add","select-key:flag");
	page.addValue("<%=CollectConstants.TYPE_CJLX_FTP%>","primary-key:collect_type");
    var win = window.frames('ajlist2');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "block";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "none";
  	document.getElementById("ajlist6").style.display = "none";
  	document.getElementById("ajlist7").style.display = "none";
	page.goPage( null, win );
}

//database
function func_insert_database()
{	
	//var page = new pageDefine( "/txn30101030.do", "�������ݿ�ɼ�����");
	var page = new pageDefine( "/txn301010300.do", "�������ݿ�ɼ�����");
	page.addValue(" ","primary-key:collect_task_id");
    var win = window.frames('ajlist3');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "block";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "none";
  	document.getElementById("ajlist6").style.display = "none";
  	document.getElementById("ajlist7").style.display = "none";
	
	page.goPage( null, win );
}

//MQ
function func_insert_mq()
{	
	alert("��δ����");
	//var page = new pageDefine( "insert-res_data_source_database.jsp", "��������Դ");
    //var win = window.frames('ajlist4');
  	//document.getElementById("ajlist1").style.display = "none";
  	//document.getElementById("ajlist2").style.display = "none";
  	//document.getElementById("ajlist3").style.display = "none";
  	//document.getElementById("ajlist4").style.display = "block";
  	//document.getElementById("ajlist5").style.display = "none";
  	//document.getElementById("ajlist6").style.display = "none";
	
	//page.goPage( null, win );
}

//fileupload
function func_insert_fileupload()
{	
	var page = new pageDefine( "/page/collect/config/fileupload/insert-collect_task_file.jsp", "�����ļ��ϴ��ɼ���������");
    var win = window.frames('ajlist6');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "none";
  	document.getElementById("ajlist6").style.display = "block";
  	document.getElementById("ajlist7").style.display = "none";
	
	page.goPage( null, win );
}

function func_insert_etl(){
	var page = new pageDefine( "/page/collect/etl/insert-etl_subject.jsp", "����ETL�ɼ�����");
    var win = window.frames('ajlist7');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "none";
  	document.getElementById("ajlist6").style.display = "none";
  	document.getElementById("ajlist7").style.display = "block";
	
	page.goPage( null, win );
	
}



function funTBChanged(){
	var type = getFormFieldValue('record:collect_type');
	if(type=='00'){ 
		func_insert_webservice();
	}else if(type=='01') {
		func_insert_fileupload();
	}else if(type=='02'){
		func_insert_ftp();
	}else if (type=='03'){
		func_insert_database();
	}else if (type=='04'){
		func_insert_jms();
	}else if (type=='05'){
		func_insert_socket();
	}else if (type=='06'){
		func_insert_etl();
	}
	else {
		alert("��ѡ��ɼ��������ͣ�");
		return ;
	}
		
}
// �� ��
function func_record_goBack()
{
	_closeModalWindow( false, 1, "/txn30101001.do" );
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$(".radioNew").each(function(index){
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).css("background-position-y","top");
			$(this).prev()[0].click();
		});
		$(this).next()[0].onclick=function(){
				$(this).prev()[0].click();
				$(".radioNew").css("background-position-y","bottom");
				$($(".radioNew")[index]).css("background-position-y","top");
			};
	});
	
	for(var ii=0;ii<6;ii++){
		if($($(".radioNew")[ii]).prev()[0].checked){
			$($(".radioNew")[ii]).css("background-position-y","top");
		}
	}
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:body onload="funTBChanged()">
<freeze:title caption="�½��ɼ�����"/>
<freeze:errors/>

<freeze:form action="/txn30101001">
<!--  
<freeze:button name="webservice" caption="webservice"  onclick="func_insert_webservice();"  styleClass="menu"/>
<freeze:button name="ftp" caption="ftp"  onclick="func_insert_ftp();" styleClass="menu"/>
<freeze:button name="���ݿ�" caption="���ݿ�"  onclick="func_insert_database();" styleClass="menu"/>
-->
<freeze:block property="record" caption="ѡ��ɼ�����" width="95%" align="center">
<freeze:radio property="collect_type" caption="�ɼ�����" valueset="�ɼ�����_�ɼ�����"  value="00" onclick="funTBChanged();" style="width:100%" colspan="4"/>
</freeze:block>
<br/>
<iframe id='ajlist1' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
<iframe id='ajlist2' scrolling='no' frameborder='0'  width='100%' height='700px' style="display:none"></iframe>
<iframe id='ajlist3' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
<iframe id='ajlist4' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
<iframe id='ajlist5' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
<iframe id='ajlist6' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
<iframe id='ajlist7' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
	
	
	
</freeze:form>
</freeze:body>
</freeze:html>
