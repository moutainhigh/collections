<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>

<freeze:html width="600" height="160">
<head>
<title>�û�����ά��</title>

</head>
<freeze:body>
<freeze:title caption="�û�����ά��"/>
<freeze:errors/>

<script language="javascript">
function checkPassword(){
	var jmm=document.getElementById("input-data:jmm").value;
	var yhmm=document.getElementById("input-data:yhmm").value;
	var qrmm=document.getElementById("input-data:qrmm").value;
	if(jmm==''){
		alert("����������룡");
		return false;
	}
	if(yhmm==''){
		alert("�����������룡");
		return false;
	}
	if(qrmm==''){
		alert("������ȷ�����룡");
		return false;
	}		
	if(jmm==yhmm){
		alert("�����벻�ܺ;�����һ�£�");
		return false;
	}
	if(yhmm!=qrmm){
		alert("ȷ������������벻һ�£�");
		return false;
	}
	return true;
}
function func_input_data_saveRecord(){
var yhmm=document.getElementById("input-data:yhmm").value;
var jmm=document.getElementById("input-data:jmm").value;
var qrmm=document.getElementById("input-data:qrmm").value;
if(checkPassword()&&checkPasswordRule(window.ruleXml,yhmm)){
    $.ajax({
	  type: "post",
	  url: "<%=request.getContextPath()%>/txn807006.ajax?input-data:yhmm=" + yhmm+"&input-data:jmm="+jmm+"&input-data:qrmm="+qrmm,
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert("ԭ�����������");
	  	}else{
	  	    alert("�����޸ĳɹ���");
	  	    window.returnValue='success'; 
	  	    window.close(); 
	  	    //parent.window.location='/logout.jsp';
			//document.getElementById("input-data:yhmm").value="";
			//document.getElementById("input-data:jmm").value="";
			//document.getElementById("input-data:qrmm").value="";	  	    
	  	}
	  }
	});      	
}else{
  	return false;
}
}

function checkPasswordRule(ruleXml,psw){
	if(!ruleXml)return true;
	//���볤��Ҫ��
	var pwLength_enable;
	var minLength;
	var pwLengthElement;
	//���븴�Ӷ�Ҫ��
	var complication_enable;
	var lowercase;
	var specialCharacter;
	var uppercase;
	var ArabicNumerals;
	var complicationElement;
	
	pwLengthElement 	= $('CheckRule/pwLength',ruleXml)[0];
	complicationElement = $('CheckRule/complication',ruleXml)[0];
	
	pwLength_enable = pwLengthElement.getAttribute('enable');
	if(pwLength_enable=='true'){
		minLength = $('minLength',pwLengthElement).text();
		if(psw.length<minLength){
			alert("�����������С��"+minLength+"λ��������һ������"+minLength+"λ�����룡");
			return false;
		}
	}
	
	complication_enable = complicationElement.getAttribute('enable');
	if(complication_enable=='true'){
		lowercase 			= $('lowercase',complicationElement).text();
		specialCharacter 	= $('specialCharacter',complicationElement).text();
		uppercase 			= $('uppercase',complicationElement).text();
		ArabicNumerals 		= $('ArabicNumerals',complicationElement).text();
		if(lowercase=='true'){
			//����Ҫ��Сд��ĸ
			if(!(/[a-z]/.test(psw))){
				alert('����Ҫ��Сд��ĸ');
				return false;
			}
		}
		if(specialCharacter=='true'){
			//����Ҫ�������ַ�!@#$%^&*()_+
			if(!(/\W/.test(psw))){
				alert('����Ҫ�������ַ�');
				return false;
			}
		}
		if(uppercase=='true'){
			//����Ҫ�д�д��ĸ
			if(!(/[A-Z]/.test(psw))){
				alert('����Ҫ�д�д��ĸ');
				return false;
			}
		}
		if(ArabicNumerals=='true'){
			//����Ҫ������
			if(!(/[0-9]/.test(psw))){
				alert('����Ҫ������');
				return false;
			}
		}
	}
	
	return true;
}

function getHint(ruleXml){
	if(!ruleXml)return;
	var hintArray = [];
	//���볤��Ҫ��
	var pwLength_enable;
	var minLength;
	var pwLengthElement;
	//���븴�Ӷ�Ҫ��
	var complication_enable;
	var lowercase;
	var specialCharacter;
	var uppercase;
	var ArabicNumerals;
	var complicationElement;
	
	pwLengthElement 	= $('CheckRule/pwLength',ruleXml)[0];
	complicationElement = $('CheckRule/complication',ruleXml)[0];
	
	pwLength_enable = pwLengthElement.getAttribute('enable');
	if(pwLength_enable=='true'){
		minLength = $('minLength',pwLengthElement).text();
		hintArray.push("���ڵ���"+minLength+"λ.");
	}
	
	complication_enable = complicationElement.getAttribute('enable');
	if(complication_enable=='true'){
		lowercase 			= $('lowercase',complicationElement).text();
		specialCharacter 	= $('specialCharacter',complicationElement).text();
		uppercase 			= $('uppercase',complicationElement).text();
		ArabicNumerals 		= $('ArabicNumerals',complicationElement).text();
		if(lowercase=='true'){
			hintArray.push('Сд��ĸ');
			hintArray.push('+');
		}
		if(specialCharacter=='true'){
			//����Ҫ�������ַ�
			hintArray.push('�����ַ�');
			hintArray.push('+');
		}
		if(uppercase=='true'){
			//����Ҫ�д�д��ĸ
			hintArray.push('��д��ĸ');
			hintArray.push('+');
		}
		if(ArabicNumerals=='true'){
			//����Ҫ������
			hintArray.push('����');
			hintArray.push('+');
		}
		hintArray.pop();
		hintArray.push('.');
	}
	
	return hintArray.join('');
}

$(document).ready(function(){
	var ruleSrc = '<%=request.getContextPath()%>/password.jsp';
	$.get(ruleSrc,function(xml){
		window.ruleXml = xml;
		$('span#passwordRule').html(getHint(xml));
	});
});
</script>

<freeze:form action="/txn807006">
  <freeze:block property="input-data" caption="�û�����ά��" width="90%" columns="1" >
    <freeze:button name="record_saveAndExit" caption="�޸�����"  hotkey="SAVE" onclick="func_input_data_saveRecord();"/>
    <freeze:password property="jmm" caption="������" style="width:90%" minlength="1"  redisplay="false"/>
    <freeze:password property="yhmm" caption="������" style="width:90%" minlength="1"  redisplay="false"/>
    <freeze:password property="qrmm" caption="ȷ������" style="width:90%" minlength="1" redisplay="false"/>
  </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>
