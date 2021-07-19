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

// ����֪ͨͨ���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_notice_info.jsp", "��������", "modal" );
	page.addRecord();
}

// �޸�֪ͨͨ���
function func_record_updateRecord()
{
	var index = getSelectedRowid("record");
	var sys_notice_state = getFormFieldText("record:sys_notice_state",index);    
/** ȡ���������֪ͨ����ɾ��
	if(sys_notice_state=="1"){
	    alert("�ѷ��������ϲ����޸ģ�");
	    var grid = getGridDefine("record", 0); 
        clickFlag = 0; 
        grid.checkMenuItem(); 	
	    return false;
	}
**/
	var page = new pageDefine( "/txn53000004.do", "�޸�����", "modal" );
	page.addParameter( "record:sys_notice_id", "primary-key:sys_notice_id" );
	page.updateRecord();
}

// ɾ��֪ͨͨ���
function func_record_deleteRecord()
{
/** ȡ���������֪ͨ����ɾ��
  	var checkboxs= document.getElementsByName('record:_flag');
	var flag = false;
	var sys_notice_state = "";
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked==true){
		    sys_notice_state = getFormFieldValue("record:sys_notice_state",i);
		    if(sys_notice_state=="1"){
			   flag = true;
			   break;
			}
		}
	}
	if(flag){
	   alert("�ѷ��������ϲ���ɾ����");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
**/	
	var page = new pageDefine( "/txn53000005.do", "ɾ������" );
	page.addParameter( "record:sys_notice_id", "primary-key:sys_notice_id" );
	page.addParameter( "record:sys_notice_date", "primary-key:sys_notice_date" );
	page.addParameter( "record:sys_notice_filepath", "primary-key:sys_notice_filepath" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

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

function funcQueryInfo(){
    //alert(event.srcElement.tagName);
	if ((event.srcElement.tagName.toUpperCase() == 'LABEL')||(event.srcElement.tagName.toUpperCase() == 'INPUT')||(event.srcElement.tagName.toUpperCase() == 'IMG')){
		return;
	}
	var index = getSelectedRowid("record");
	var sys_notice_id = getFormFieldText("record:sys_notice_id",index);  
	var page = new pageDefine("/txn53000007.do", "��ϸ��Ϣ","model");	
	page.addValue( sys_notice_id, "primary-key:sys_notice_id" );
	page.goPage();
}

function func_record_pubRecord(){
// 	ȡ�������ظ�����������
  	var checkboxs= document.getElementsByName('record:_flag');
	var flag = false;
	var sys_notice_state = "";
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked==true){
		    sys_notice_state = getFormFieldText("record:sys_notice_state",i);
		    if(sys_notice_state=="1"){
			   flag = true;
			   break;
			}
		}
	}
	if(flag){
	   alert("�ѷ��������ϲ����ٴη�����");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}

    var page = new pageDefine( "/txn53000010.do", "��������");
    page.addParameter("record:sys_notice_id","record:sys_notice_id");
   	if(confirm("�Ƿ񷢲�ѡ�е����ϣ�")){
	   page.goPage();	
	}else{
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 		   
	}
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
<freeze:title caption="��ѯ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn53000001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="sys_notice_matter" caption="����" datatype="string" style="width:90%"/>
      <freeze:text property="sys_notice_org" caption="������λ" datatype="string" style="width:90%"/>
      <freeze:text property="sys_notice_promulgator" caption="������" datatype="string" style="width:90%"/>
      <freeze:datebox property="sys_notice_date_s"  caption="��������" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%" notnull="true"/>
        </td><td width='5%'>��</td><td width='45%'>
      <freeze:datebox property="sys_notice_date_e" caption="��������" style="width:100%" colspan="0" notnull="true"/>
        </td></tr></table> 
        <freeze:select property="sys_notice_state" caption="����״̬" valueset="����״̬" style="width:36.5%" colspan="2" />
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="�����б�" keylist="sys_notice_id" width="95%" checkbox="true" navbar="bottom" rowselect="true" fixrow="false" onclick="funcQueryInfo();" >
      <freeze:button name="record_addRecord" caption="����" txncode="53000003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="53000005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_deleteRecord_s" caption="����" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_pubRecord();"/>
      <freeze:hidden property="sys_notice_id" caption="����ID" style="width:15%" />
      <freeze:hidden property="sys_notice_filepath" caption="����" style="width:15%" />
      <freeze:hidden property="hasfj" caption="�Ƿ��������" style="width:15%" />
      <freeze:hidden property="isnew" caption="�Ƿ��·���" style="width:15%" />
      <freeze:hidden property="sys_notice_state" caption="����״̬" style="width:20%" />
      <freeze:cell property="@rowid" caption="���" align="middle" style="width:5%"/>
      <freeze:cell property="sys_notice_title" caption="����" style="width:32%" />       
      <freeze:cell property="sys_notice_promulgator" caption="������" style="width:10%" />
      <freeze:cell property="sys_notice_org" caption="������λ" style="width:20%" />
      <freeze:cell property="sys_notice_date" caption="����ʱ��" style="width:10%" />
      <freeze:cell property="sys_notice_state" caption="����״̬" valueset="����״̬" style="width:10%" />
      <freeze:cell property="operation" caption="����" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
