<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>�޸ķ��������Ϣ</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
</head>

<script language="javascript">

function validate(){
	var login_name = getFormFieldValue("record:login_name");
	if(!login_name){
		alert("����д���û��˺š�!");
		return false;
	}
	var reg = new RegExp("[\u4e00-\u9fa5]+");
    if (reg.test(login_name)==true){
		alert("����ȷ��д���û����롿!\r\nע�⣺ֻ������ĸ�����ֻ��»��ߵ�Ӣ����ϣ�");
	    document.getElementById("record:login_name").select();
		return false;
	}
	
	var regExp = /[~\\\?'"\^\*]/;
	if(regExp.test(login_name)){
		alert("���û����ơ��к��в��Ϸ����ַ���" + login_name.match(regExp) + "�������������룡");
		return false;
	}
	
	var userName = getFormFieldValue("record:user_name");
	if(!userName){
		alert("����д���û����ơ�!");
		return false;
	}
	var regExp = /[~\\\?'"\^\*]/;
	if(regExp.test(userName)){
		alert("���û����ơ��к��в��Ϸ����ַ���" + userName.match(regExp) + "�������������룡");
		return false;
	}
	
	var user_type = getFormFieldValue("record:user_type");
	var reg = new RegExp("^\s*$");
    if (reg.test(user_type)==true){
		alert("��ѡ���û����͡�!");
		return false;
	}
	var isBind = document.getElementById("isip_bind");
	if(isBind.checked == true){
		var ip1 = document.getElementById("ip1").value.trim();
		var ip2 = document.getElementById("ip2").value.trim();
		var ip3 = document.getElementById("ip3").value.trim();
		var ip4 = document.getElementById("ip4").value.trim();
		var ip = ip1 + '.' + ip2 + '.' + ip3 + '.' + ip4;
		if(ip&&ip.length < 7){
			alert("����д����IP");
			return false;
	    }
	var reg = /^([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])$/;  
    var arr = reg.test(ip);  
     if(!arr){  
     	alert("��IP����.");
     	//for(var ii = 1;ii < 5; ii++)
		//	document.getElementById("ip"+ii).value = '';
         return false;
     }
     document.getElementById('record:is_ip_bind').value=1;
     document.getElementById('record:ip_bind').value=ip;
	}else{
	 document.getElementById('record:is_ip_bind').value=0;
     document.getElementById('record:ip_bind').value=' ';
	}
	
	return true;
}

// �� ��
function func_record_saveAndExit()
{
	if(!validate()){
		return;
	}
	
	saveAndExit( '', '�������������' );	// /txn50201001.do
}

// �� ��
function func_record_goBack()
{
	_closeModalWindow(true)	// /txn50201001.do
}

// ɾ���������
function func_record_deleteRecord()
{
	var id = getFormFieldValue("record:sys_svr_user_id");
	var name = getFormFieldValue("record:login_name");

	
	$.get("<%=request.getContextPath()%>/txn50201007.ajax?record:sys_svr_user_id="+id, function(xml){
		var errCode = _getXmlNodeValue( xml, "/context/error-code" );
		if(errCode != "000000"){
		    alert("�������" + _getXmlNodeValue( xml, "/context/error-desc" ));
		}else{
    		var cfgid = _getXmlNodeValue( xml, "record:sys_svr_config_id" );
    		if(cfgid){
    			alert("�û��Ѿ������÷��񣬽�ֹɾ����");
    			return;
    		}else{
				if(!confirm("�Ƿ�ɾ��ѡ�еļ�¼?")){
					return;
				}
				$.get("<%=request.getContextPath()%>/txn50201005.ajax?record:sys_svr_user_id="+id, function(xml){
					var errCode = _getXmlNodeValue( xml, "/context/error-code" );
					if(errCode != "000000"){
					    alert("�������" + _getXmlNodeValue( xml, "/context/error-desc" ));
					}
					func_record_goBack();
				});
			}
		}
	});
}

function checkExist(){
	var loginName = getFormFieldValue("record:login_name");
	var userName = getFormFieldValue("record:user_name");
	if(loginName.trim() == "" ){
		return;
	}
	$.get("<%=request.getContextPath()%>/txn50201009.ajax?select-key:login_name="+loginName+"&select-key:user_name="+userName, function(xml){
		var errCode = _getXmlNodeValue( xml, "/context/error-code" );
		if(errCode != "000000"){
		    alert("�������" + _getXmlNodeValue( xml, "/context/error-desc" ));
		}
		var existId = _getXmlNodeValue( xml, "/context/record/sys_svr_user_id" );
		var errorSpan = document.getElementById("frror");
		if(existId && existId != getFormFieldValue("record:sys_svr_user_id")){
			errorSpan.innerHTML = "<center><font color='red'>���û����롿���û����ơ��Ѵ��ڣ��������</font><center>";
			document.getElementById("record_record_saveAndExit").disabled = true;
			errorSpan.style.display = "block";
		}else{
			document.getElementById("record_record_saveAndExit").disabled = false;
			errorSpan.style.display = "none";
		}
	});
}

function __userInitPage(){
	var code = getFormFieldValue("record:user_code");
	if(code.length==2){
		code = code.substr(1,1);
		var radio_index = parseInt(code);
		$(".radioNew")[(radio_index-1)].style.backgroundPositionY='top';
	}
$(".radioNew").each(function(index){
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).css("background-position-y","top");
			$(this).prev()[0].click();
		});
		$($(this).next()[0]).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev().prev()[0].click();
			if($(this).prev().prev()[0].checked){
				$($(this).prev()[0]).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
	
  var is_ip_bind='<freeze:out value="${record.is_ip_bind}"/>';
  //var ip='<freeze:out value="${oper-data.ipaddress}"/>';
  var ip='<freeze:out value="${record.ip_bind}"/>';
   if(is_ip_bind=='1'){
    $("#isip_bind").attr("checked","true");
    $("#label_isip_bind").css("background-position-y","top")
    var ips=ip.split(".");
    for(var i=0;i<ips.length;i++){
      $("#ip"+(i+1)).attr("disabled",false);
      $("#ip"+(i+1)).val(ips[i]);
    }
  }
  	$("#padcool").next().next().remove();
	$("#padcool").next().remove();
}

function showIPRow(){
  if($("#label_isip_bind").css("background-position-y")=="bottom"){
  	$("#label_isip_bind").css("background-position-y","top");
    for(var i=1;i<=4;i++){
      $("#ip"+i).attr("disabled",false);
    }
   }else{
    for(var i=1;i<=4;i++){
      $("#ip"+i).attr("disabled",true);
      $("#label_isip_bind").css("background-position-y","bottom");
    }
  }
}

window.onload=function(){
	window.onunload = null;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ķ��������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn50201002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_svr_user_id" caption="���������" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ķ��������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_svr_user_id" caption="���������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="login_name" caption="�û��˺�" onblur="checkExist();" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:text property="user_name" caption="�û�����" onblur="checkExist();" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:select property="user_type" caption="�û�����" valueset="user_type" show="name" style="width:95%" notnull="true"/>
      <td width="15%" align="center"><input style="margin-left:-1000px;" type="checkbox" id="isip_bind" /><label for="isip_bind" style="width:60px;" class="checkboxNew" id="label_isip_bind" onclick="showIPRow();">��IP</label></td>
	  <td id="padcool" colspan="0" width="35%">
	  	<input disabled="true" style="width:19%;" id="ip1" name="ip1" value="" maxlength="3" type="text" title="��IP��ַ" >.
	 	 <input disabled="true" style="width:19%;" id="ip2" name="ip2" value="" maxlength="3" type="text" title="��IP��ַ" >.
	 	 <input  disabled="true" style="width:19%;" id="ip3" name="ip3" value="" maxlength="3" type="text" title="��IP��ַ" >.
	 	 <input  disabled="true" style="width:19%;" name="ip4" id="ip4" value="" maxlength="3" type="text" title="��IP��ַ" >
	  <freeze:radio property="user_code" valueset="�ӿ��û����" caption="�û����" colspan="2"  style="width:100%"/>
      <freeze:hidden property="is_ip_bind" caption="���������" datatype="string" maxlength="32"  style="width:95%"/>
      <freeze:hidden property="ip_bind" caption="���������" datatype="string" maxlength="32"  style="width:95%"/>
      <input type="hidden" name="record:old_login_name" value="<freeze:out value="${record.login_name}"/>"/>
      <input type="hidden" name="record:old_user_name" value="<freeze:out value="${record.user_name}"/>"/>
  </freeze:block>
  <br/>
  <div>
  	<h3>ʹ��˵����</h3>
  	<ul>
		<li>1�����û����롿ΪwebService�ͻ��˱����ṩ�ģ����ڵ�¼��֤���û����룻</li>
			<ul>
				<li style="color:red">���û����벻���������ַ���</li>
				<li style="color:red">���û����벻��Ϊ�գ�</li>
				<li style="color:red">���û����벻���ظ���</li>
				<li style="color:red">ֻ��������ĸ�����ֻ��»��ߵ�Ӣ����ϣ�</li>
			</ul>
		<li>2�����û����ơ�Ϊ���û����������ƣ����������û���
			<ul>
				<li style="color:red">���û����Ʋ���Ϊ�գ�</li>
				<li style="color:red">���û����Ʋ����ظ�</li>
			</ul>
		</li>
	</ul>
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
