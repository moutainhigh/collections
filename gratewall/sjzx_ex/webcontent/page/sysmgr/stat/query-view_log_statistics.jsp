<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��־ͳ���б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

function funTBChanged(){
	var count_type = getFormFieldValue('select-key:count_type');
	
	if(count_type=='1'){
	setDisplay("1",true);
	setDisplay("2",false);
	}else {
	setDisplay("1",false);
	setDisplay("2",true);
	}
}

function setDisplay(id,b){
	
	var preStr = "row_"+id;
	if(b=='true'||b==true){
		document.getElementById(preStr).style.display='block';
		var cells = document.getElementById(preStr).cells;
		for(var ii=0;ii<cells.length;ii++){
			cells[ii].className='odd1_b';
		}
	}else{
		document.getElementById(preStr).style.display='none';
	}
}

function selectFj(){
    var orgId = getFormFieldValue('select-key:sjjgid_fk');
    alert("orgId:"+orgId);
    if(orgId!=''){
        return 'orgId=' + orgId ;
    }
}

function getParameter(){
	var parameter='first_func_name='+getFormFieldValue('select-key:first_func_name');
	return parameter;
}

function doExport(){
	clickFlag=0;
	document.forms[0].action ="/txn620100208.do?";
	document.forms[0].submit();
	document.forms[0].action ="/txn620100201.do";
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
	for(var ii=0;ii<2;ii++){
		if($($(".radioNew")[ii]).prev()[0].checked){
			$($(".radioNew")[ii]).css("background-position-y","top");
		}
	}
}
function doSub(){
  var query_date_from = getFormFieldValue('select-key:query_date_from');
  var query_date_to = getFormFieldValue('select-key:query_date_to');
  
  if(query_date_from==''&&query_date_to==''){
  	alert('ִ����������������һ����ѯ����');
  	return false;
  }
  return true;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body onload="funTBChanged()">
<freeze:title caption="��ѯ��־ͳ���б�"/>
<freeze:errors/>

<freeze:form action="/txn620100201" onsubmit="return doSub();">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:radio property="count_type" caption="ͳ������" valueset="ͳ������" value="1" onclick="funTBChanged();" style="width:90%"/>
      <freeze:datebox property="query_date_from" caption="ִ������" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    	</td><td width='5%'>��</td><td width='45%'>
      <freeze:datebox property="query_date_to" caption="ִ������" style="width:100%" colspan="0"/>
        </td></tr></table>
      <freeze:browsebox property="first_func_name" caption="���ܴ���" valueset="��ȡ���ܴ���" show="name" style="width:90%" />
      <freeze:browsebox property="second_func_name" caption="����С��" valueset="��ȡ����С��" parameter="getParameter()" show="name" style="width:90%" />
      <freeze:browsebox property="sjjgid_fk" caption="�����־�" valueset="�����־�" show="name"  style="width:36.5%" colspan="2"/>
  </freeze:block>
<br />
  <freeze:grid property="record" caption="��ѯ��־ͳ���б�" keylist="func_name" width="95%" navbar="bottom" fixrow="false" checkbox="false">
  	  <freeze:button name="export" caption="����"   align="right" onclick='doExport();'/>
      <freeze:button name="record_addRecord" caption="������־��ѯ" txncode="620100103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸���־��ѯ" txncode="620100104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ����־��ѯ" txncode="620100105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="@rowid" caption="���"  style="width:6%" align="center" />
      <freeze:cell property="first_func_name" align="center" caption="���ܴ���" style="width:20%" />
      <freeze:cell property="func_name" align="center" caption="����С��" style="width:30%" />
      <freeze:cell property="sjjgname" align="center" caption="�����־�" valueset="����" style="width:30%" />
      <freeze:cell property="querytimes" align="center" caption="ʹ�ô���" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
