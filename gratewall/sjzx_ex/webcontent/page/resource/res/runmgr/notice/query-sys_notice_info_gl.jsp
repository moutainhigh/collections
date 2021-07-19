<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<title>��ѯ�����б�</title>
<style type="text/css">
	td#td_sys_notice_title span{
		width:80% !important;
	}
</style>
</head>

<script language="javascript">

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$(document).ready(function(){
		var leftTopCell = $("table#record tr[@class!='grid-headrow']").get();		
		for (var index = 0; index < leftTopCell.length; index ++){
			var td_sys_notice_title = document.getElementsByName("td_sys_notice_title")[index];
			var sys_notice_id = document.getElementsByName("record:sys_notice_id")[index].value;
			//alert(sys_notice_id);
			//td_sys_notice_title.innerHTML = '<a href="#" onclick="funcQueryInfo("'+sys_notice_id+'");">' + td_sys_notice_title.innerHTML + '</a>';
			var hasfj = document.getElementsByName("record:hasfj")[index].value;
			if(hasfj=="Y"){
			   td_sys_notice_title.innerHTML += '<img border="0" src="<%=request.getContextPath()%>/images/fujian.png" onclick="chakanfj('+index+')"></img>';
			}		
		}
	});	
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();event.cancelBubble=true;' title='�޸�' href='#'><div class='edit'></div></a>";
	}	
	operationSpan = null;
	$("#customPageRowSeleted option:gt(0)").remove();
}
//�鿴���� 
function chakanfj(index){
    var sys_notice_id = getFormFieldValue('record:sys_notice_id',index);
    $.ajax({
	  type: "post",
	  url: "<%=request.getContextPath()%>/txn53000011.ajax?primary-key:sys_notice_id=" + sys_notice_id,
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert(xmlResults.selectSingleNode("//context/error-desc").text);
	  		return false;
	  	}else{
	  		var sys_notice_id = _getXmlNodeValues( xmlResults, "/context/record/sys_notice_id" );
			var sys_notice_filepath = _getXmlNodeValues( xmlResults, "/context/record/sys_notice_filepath" );
	        var page = new pageDefine("/txn53000006.do", "�鿴����", "_self");  
	        page.addValue(sys_notice_id,"record:sys_notice_id");	
	        page.addValue(sys_notice_filepath,"record:sys_notice_filepath");
	        page.goPage();
	  	}
	  }
	});
}

/**�����ظ�����
_browse.execute(function(){
	var gridDataTable = document.getElementById("record");
	for ( var i = 1; i < gridDataTable.rows.length ; i++ ){
		gridDataTable.rows[i].attachEvent("onclick", checkButtonByData);
	}
	document.getElementById("record:_select-all").attachEvent("onclick", checkButtonByData);
});
**/
function checkButtonByData(){
    
	var sfyxArray = getFormFieldValues("record:sys_notice_state");	
	if(sfyxArray==""){return;}
	var sFlag = false;
	
	for( var i=0;i<sfyxArray.length;i++){
	   if(!sFlag){
		   if(sfyxArray[i]=="1"){
		      document.getElementById("record_record_deleteRecord_s").disabled = "-1";
		      sFlag = true;
		   }
	   }
	}
	if(!sFlag){
       document.getElementById("record_record_deleteRecord_s").removeAttribute("disabled");
	}
}

_browse.execute("__userInitPage()");
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn53000013">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="sys_notice_matter" caption="����" datatype="string" style="width:90%"/>
      <freeze:text property="sys_notice_org" caption="������λ" datatype="string" style="width:90%"/>
      <freeze:text property="sys_notice_promulgator" caption="������" datatype="string" style="width:90%"/>
      <freeze:datebox property="sys_notice_date_s"  caption="��������" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%" notnull="true"/>
        </td><td width='5%'>��</td><td width='45%'>
      <freeze:datebox property="sys_notice_date_e" caption="��������" style="width:100%" colspan="0" notnull="true"/>
        </td></tr></table> 
 </freeze:block>
  <br>
  <freeze:grid property="record" caption="�����б�" keylist="sys_notice_id" width="95%" checkbox="false" navbar="bottom" rowselect="true" fixrow="false" rowselect="false" >
   <freeze:hidden property="sys_notice_id" caption="����ID" style="width:15%" />
      <freeze:hidden property="sys_notice_filepath" caption="����" style="width:15%" />
      <freeze:hidden property="hasfj" caption="�Ƿ��������" style="width:15%" />
      <freeze:hidden property="isnew" caption="�Ƿ��·���" style="width:15%" />
      <freeze:hidden property="sys_notice_state" caption="����״̬" style="width:20%" />
      <freeze:cell property="@rowid" caption="���" align="middle" style="width:5%"/>
      <freeze:cell property="sys_notice_title" caption="����" style="" />       
      <freeze:cell property="sys_notice_promulgator" caption="������" style="width:10%" />
      <freeze:cell property="sys_notice_org" caption="������λ" style="width:25%" />
      <freeze:cell property="sys_notice_date" caption="����ʱ��" style="width:10%" />
      <freeze:cell property="sys_notice_state" caption="����״̬" valueset="����״̬" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
