<%@page import="cn.gwssi.common.context.TxnContext"%>
<%@page import="cn.gwssi.common.context.DataBus"%>
<%@page import="com.gwssi.common.util.UuidGenerator"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="800" height="380">
<head>
<style>
span.IPDiv{background:#ffffff;width:120;font-size:9pt;text-align:center;border:2 ridge threedshadow;border-right:inset threedhighlight;border-bottom:inset threedhighlight;
}
input.IPInput{width:24;font-size:9pt;text-align:center;border-width:0;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/uploadfile.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/util/HZtoPY.js"></script>
<title>���Ӷ�����Ϣ</title>
<%
TxnContext context = (TxnContext) request.getAttribute("freeze-databus");
if(null==context){
	context=new TxnContext();
	DataBus db=new DataBus();
	String pwd=UuidGenerator.getRandomPwd(8);
	db.setValue("service_password", pwd);
	context.addRecord("record", db);
	request.setAttribute("freeze-databus", context);
}
%>
</head>

<script language="javascript">
	var is_name_used = 1;
	var is_code_used = 1;

var ipfieldsNUM = 0;

function newIPfield(){
	if(ipfieldsNUM >= 9)
	{
		alert("����10��IP��ַ��");
		return;
	}
	var tmpStr=[];           //����һ������
	for(var i=0;i<4;i++)     //ͨ��ѭ��-ÿ��3λ�����һ����
	tmpStr[i]="<input onkeyup=\"return ip_keyup()\" class=\"IPInput\" name=\"IPInput\" type=\"text\" size=\"3\" maxlength=\"3\" onkeydown='ip_keydown()'>"+(i==3?"":".");
	var htmlbutton = '<button  class="IPInput" onclick="delIPfield(this)" >ɾ��</button>';
	document.getElementById("padcool").innerHTML+="<div><span class=\"IPDiv\">"+tmpStr.join("")+"</span>"+htmlbutton+"</div>";//����ַ���
	ipfieldsNUM++;
}

function delIPfield(obj){
		var div = obj.parentNode; 
		div.parentNode.removeChild(div); 
		ipfieldsNUM--;
}

function setService_targets_no(){
	setShowOrderValue();
	var service_targets_no = "";
	var firstword = getFormFieldValue('record:service_targets_type');
	if(firstword == "") return;
	var secondword = getFormFieldValue('record:service_targets_name');
	if(secondword == "") return;
	if(firstword=='001')
	{
		firstword = "N_";
	}
	else if(firstword=='002')
	{
		firstword = "W_";
	}
	else if(firstword=='000')
	{
		firstword = "Q_";
	}
    secondword = makePy(secondword);
	service_targets_no = firstword+secondword[0] ;
	setFormFieldValue('record:service_targets_no',service_targets_no);
	//checkNoUsed();
}
	
//��ȡ�������ֵ
function setShowOrderValue(){
	NameAndNocheckes = false;
	nameCheckAlerted = false;
	var page = new pageDefine("/txn201014.ajax", "��ȡ���");	
	page.addParameter("record:service_targets_type","select-key:service_targets_type");
	page.callAjaxService('setShowOrderValueCallback');
}
function setShowOrderValueCallback(errCode,errDesc,xmlResults){
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		var showOrderValue =_getXmlNodeValues(xmlResults,'record:toporder');
		if(showOrderValue[0]>0){
		 	setFormFieldValue('record:show_order',showOrderValue[0]);
		 	return;
		}
}


	
var nameCheckAlerted = false;
function checkNameUsed(){
	NameAndNocheckes = false;
	nameCheckAlerted = false;
	var page = new pageDefine("/txn201007.ajax", "����Ƿ��Ѿ�ʹ��");	
	var service_targets_name=getFormFieldValue('record:service_targets_name');
	page.addValue(service_targets_name,"select-key:service_targets_name");
	page.callAjaxService('nameCheckCallback');
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used[0] = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		
		  if(is_name_used[0]>0){
		    nameCheckAlerted = true;
  			alert("������������Ѵ��ڣ�����������");
  			return;
  			}
  		checkNoUsed();
}

function checkNoUsed(){
//    if(nameCheckAlerted){
 //  	    nameCheckAlerted = false;
 //   	return;
 //   }
	
	var page = new pageDefine("/txn201007.ajax", "����Ƿ��Ѿ�ʹ��");	
	var service_targets_no=getFormFieldValue('record:service_targets_no');
	page.addValue(service_targets_no,"select-key:service_targets_no");
	page.callAjaxService('noCheckCallback');
}

function noCheckCallback(errCode,errDesc,xmlResults){
		is_code_used[1] = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_code_used=_getXmlNodeValues(xmlResults,'record:code_nums');
  			if(is_code_used[1]>0 ){
  				//alert("�����������Ѵ��ڣ����޸�");
  			}
  		
  if(is_name_used[0]>0 && is_code_used[1]>0 ){
  	alert("����������ƺʹ����Ѵ��ڣ�����������");
	return;
  }
  if(is_name_used[0]>0){
  	alert("������������Ѵ��ڣ�����������");
	return;
  }
  if(is_code_used[1]>0 ){
  	alert("�����������Ѵ��ڣ�����������");
	return;
  }
  
    var isBind = document.getElementById("isip_bind");
	if(isBind.checked == true){
	var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;  
	var ips = document.getElementsByName("IPInput");
	var ip ="";
	var showIp="";
	for(var i=0;i<ips.length;i++){
		//alert(i+"="+ips[i].value);
		   if((i+1)%4==0){
		   ip +=ips[i].value+".";
		   if(ip!="....")
		   {
		     ip=ip.substring(0,ip.length-1);
			     var arr = reg.test(ip);  
				if(!arr){  
					alert("��������ȷ��ip��ַ,���� 192.168.1.1");
					//for(var ii = 1;ii < 5; ii++)
					//	document.getElementById("ip"+ii).value = '';
					return;
			}
			 showIp += ip+","; 
			}
			ip="";
		   }
		   else{
				ip +=ips[i].value+".";
			}
		
	}
	showIp=showIp.substring(0,showIp.length-1);
	if(showIp==''){
		alert("����ѡ����IP��������Ҫ��������һ����Чip��ַ");
		return false;
	}
	document.getElementById('record:is_bind_ip').value='Y';
     document.getElementById('record:ip').value=showIp;
	}else{
		alert("����������һ��ip��ַ");
		return;
		
	/*  document.getElementById('record:is_bind_ip').value='N';
     document.getElementById('record:ip').value=' '; */
	}
	saveAndExit( '', '�����������' );	
	//return true;
}

var NameAndNocheckes = false;

function validate(){
  checkNameUsed();
  

}
// �� ��
function func_record_saveRecord()
{
	
	if(!validate()){
		return;
	}
	
	saveRecord( '', '�����������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	if(!validate()){
		return;
	}
	saveAndContinue( '', '�����������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	//if(!validate()){
	//	return;
	//}
	//saveAndExit( '', '�����������' );	// /txn201001.do
	validate();
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn201001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	showIPRow();
	setShowOrderValue();
	//�ֶ����ÿ��
	$(document.getElementById('record:show_order')).width(document.getElementById('record:service_targets_no').clientWidth);
}

function showIPRow(){
	var isBind = document.getElementById("isip_bind");
	if(isBind.checked == true){
			document.getElementById("label_check").style.backgroundPositionY="top";
		document.getElementById("isip_bind").value = 'Y';
		document.getElementById("padcool").disabled = false;
		$(".IPInput").attr("disabled",false);
		}else{
			document.getElementById("isip_bind").value = 'N';
			document.getElementById("label_check").style.backgroundPositionY="bottom";
			document.getElementById("padcool").disabled = true;
			$(".IPInput").attr("disabled","disabled");
	}
}

function ip_keyup(){
	if(event.keyCode != 8 && event.srcElement.value.length == 3){
		if(event.srcElement.nextSibling 
				&& event.srcElement.nextSibling.nextSibling 
				&& event.srcElement.nextSibling.nextSibling.tagName.toUpperCase() == 'INPUT' ){
			event.srcElement.nextSibling.nextSibling.focus();
		}
	}
}
function ip_keydown(){
	if(event.keyCode==39){
		event.keyCode=9;
	}else if(event.keyCode==8){
		if(event.srcElement.value.length == 0){
			if(event.srcElement.previousSibling 
					&& event.srcElement.previousSibling.previousSibling 
					&& event.srcElement.previousSibling.previousSibling.tagName.toUpperCase() == 'INPUT' ){
				event.srcElement.previousSibling.previousSibling.select();
			}
		}
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӷ�����Ϣ"/>
<freeze:errors/>
<!-- 
   <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
     
 -->
<freeze:form action="/txn201003" enctype="multipart/form-data">
  <freeze:block property="record" caption="���Ӷ�����Ϣ" width="95%">
      <freeze:hidden property="is_bind_ip" caption="�Ƿ��IP" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="ip" caption="IP" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string"  style="width:95%"/>
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="����" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="service_targets_id" caption="�������ID" datatype="string" maxlength="32" style="width:95%"/>
      
      <freeze:select property="service_targets_type" caption="�����������" valueset="��Դ����_�����������" onchange="setService_targets_no();" show="name" style="width:95%" notnull="true"/>
      <freeze:text property="service_targets_name" caption="�����������" onBlur="javascript:setService_targets_no();" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      <freeze:text property="service_targets_no" caption="����������"   datatype="string" maxlength="50" style="width:95%" notnull="true"/>
      <freeze:hidden property="is_markup"  caption="��Ч���" value="Y" style="width:45%" />
      <freeze:text property="service_password" caption="�������" maxlength="50" style="width:95%" readonly="true" />
      <freeze:select property="service_status" caption="����״̬" value="Y" valueset="��Դ����_һ�����״̬" style="width:95%" notnull="true"/>
     <%--  <freeze:select property="is_formal" caption="�Ƿ���ʽ" value="N" valueset="��Դ����_�������_�Ƿ���ʽ����" notnull="true" style="width:95%"/> --%>
      <freeze:text property="show_order" notnull="true" datatype="int" hint="������������" numberformat="/^[1-9]\d*$/"  caption="��ʾ˳��" style="width:37.5%" />
      <freeze:textarea property="service_desc" caption="�����������" colspan="2" rows="2" maxlength="1000" style="width:98%"/>

     
</tr>
     <tr>
      <td width="15%" align="center"><font color="red">*��IP</font><input type="checkbox" style="margin-top:-1000px;" id="isip_bind" value="N" onclick="showIPRow();" />
	 	<label id="label_check" class="checkboxNew" for="isip_bind"></label>
	  </td>
	  <td id="padcool" disabled=��disabled�� colspan="3" width="35%">
	  <button class="IPInput" onclick="javascript:newIPfield();">����IP��ַ</button>
	 <div id="IPDiv">  <span class="IPDiv">
	    <INPUT onkeyup="return ip_keyup()" onkeydown="ip_keydown()" class=IPInput name=IPInput maxLength=3 size=3>.<INPUT onkeyup="return ip_keyup()"  onkeydown="ip_keydown()" class=IPInput name=IPInput maxLength=3 size=3>.<INPUT onkeyup="return ip_keyup()"  onkeydown="ip_keydown()" class=IPInput name=IPInput maxLength=3 size=3>.<INPUT onkeyup="return ip_keyup()"  onkeydown="ip_keydown()" class=IPInput name=IPInput maxLength=3 size=3>
	  	
	  	</span><button  class="IPInput" onclick="delIPfield(this)" >ɾ��</button></div>
	  	
  	<freeze:file property="fjmc1" caption="����˵���ļ�" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="����"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
