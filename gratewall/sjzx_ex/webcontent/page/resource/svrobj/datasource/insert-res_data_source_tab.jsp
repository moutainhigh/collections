<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-3/frame-detail-update.jsp --%>

<freeze:html width="800" height="400">
<head><title>新建数据源</title></head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">
//webservice
function func_insert_webservice()
{

	//setTabUrl( "insert-res_data_source_webservice.jsp" );
	//var page = new pageDefine("insert-res_data_source_webservice.jsp","增加数据源","modal");
	//page.addRecord();
	var page = new pageDefine( "insert-res_data_source_webservice.jsp", "增加数据源");
    var win = window.frames('ajlist1');
  	document.getElementById("ajlist1").style.display = "block";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "none";
	
	page.goPage( null, win );
}

function func_insert_socket()
{
	var page = new pageDefine( "insert-res_data_source_socket.jsp", "增加数据源");
    var win = window.frames('ajlist4');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "block";
  	document.getElementById("ajlist5").style.display = "none";
  	
	
	page.goPage( null, win );
}

function func_insert_jms()
{
	var page = new pageDefine( "insert-res_data_source_jms.jsp", "增加数据源");
    var win = window.frames('ajlist5');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "block";
  	
	
	page.goPage( null, win );
}
//ftp
function func_insert_ftp()
{	
	var page = new pageDefine( "insert-res_data_source_ftp.jsp", "增加数据源");
    var win = window.frames('ajlist2');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "block";
  	document.getElementById("ajlist3").style.display = "none";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "none";
	
	page.goPage( null, win );
}

//database
function func_insert_database()
{	
	var page = new pageDefine( "insert-res_data_source_database.jsp", "增加数据源");
    var win = window.frames('ajlist3');
  	document.getElementById("ajlist1").style.display = "none";
  	document.getElementById("ajlist2").style.display = "none";
  	document.getElementById("ajlist3").style.display = "block";
  	document.getElementById("ajlist4").style.display = "none";
  	document.getElementById("ajlist5").style.display = "none";
	
	page.goPage( null, win );
}

function funTBChanged(){
	var type = getFormFieldValue('record:data_source_type');
	if(type=='00'){
		func_insert_webservice();
	}else if(type=='01') {
		func_insert_database();
	}else if(type=='02'){
		func_insert_ftp();
	}else if(type=='04'){
		func_insert_socket();
	}else{
		func_insert_jms();
	}
		
}
// 返 回
function func_record_goBack()
{
	window.returnValue='txn20102020.do';
	window.close();
	//_closeModalWindow( false, 1, "/script/insertsuccess.jsp?nextPage=txn20102001.do" );
}
// 请在这里添加，页面加载完成后的用户初始化操作
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
	
	for(var ii=0;ii<2;ii++){
		if($($(".radioNew")[ii]).prev()[0].checked){
			$($(".radioNew")[ii]).css("background-position-y","top");
		}
	}
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:body onload="funTBChanged()">
<freeze:title caption="新建数据源"/>
<freeze:errors/>

<freeze:form action="/txn20102001">
<!--  
<freeze:button name="webservice" caption="webservice"  onclick="func_insert_webservice();"  styleClass="menu"/>
<freeze:button name="ftp" caption="ftp"  onclick="func_insert_ftp();" styleClass="menu"/>
<freeze:button name="数据库" caption="数据库"  onclick="func_insert_database();" styleClass="menu"/>
-->
<freeze:block property="record" caption="选择数据源类型" width="95%" align="center">
<freeze:radio property="data_source_type" caption="数据源类型" valueset="资源管理_数据源类型" value="00" onclick="funTBChanged();" style="width:95%" colspan="2"/>
</freeze:block>
<br/>
<iframe id='ajlist1' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
<iframe id='ajlist2' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
<iframe id='ajlist3' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
<iframe id='ajlist4' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
<iframe id='ajlist5' scrolling='no' frameborder='0'  width='100%' style="display:none"></iframe>
	
	
	
</freeze:form>
</freeze:body>
</freeze:html>
