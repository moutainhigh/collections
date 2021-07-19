<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>�½��û���Ϣ</title>
</head>
<freeze:body onload="getOrgID()">
<freeze:title caption="�½��û���Ϣ"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/selectTree.js'></script>

<script language="javascript">
// ȫ�ֱ������ڼ�¼��ǰ����idֵ
var org;

// ��ʼ���������õ�ǰ����idֵ
function getOrgID(){
 org = document.getElementById("record:jgid_fk").value;
 }
 
 // ������Ϣ 
function func_record_saveRecord(){
	var psw = document.getElementById("record:yhmm").value;
	if(checkPasswordRule(window.ruleXml,psw)&&checkUserName() && checkPassword() && checkCertStatus()){
	 	var param = "select-key:jgid_fk="+org;
		saveAndExit("", "�����û���Ϣ", "/txn807011.do?"+param );
	}else{
		return false;
	}
}

// �����б�
function func_record_goBack(){
  var param = "select-key:jgid_fk="+org;

  goBack( "/txn807011.do?"+param );
}

// У��������û��˺��Լ��û����Ϊ���ֺ�Ӣ����ĸ��ʽ
function checkUserName(){
	var yhzh = document.getElementById("record:yhzh").value;
	var yhbh = document.getElementById("record:yhbh").value;

	if(!validateWebPath(yhzh)){
		alert("�û��˺�����Ƿ��ַ������������ּ�Ӣ����ĸA-Z��a-z����ϡ�");
		return false;
	}
	return true;
}

// �������ĺϷ���
function checkPassword(){
	var psw = document.getElementById("record:yhmm").value;
	var psw1 = document.getElementById("record:yhmm1").value;
	/*
	if(psw.length<6){
		alert("�����������С��6λ��������һ������6λ�����룡");
		return false;
	}
	*/
    if(psw!=psw1){
		alert("�����������벻һ�£�");
		return false;
	}
	return true;
}

function checkCertStatus(){
	var isneedkey = getFormFieldValue("record:isneedkey");
	var keynumber = getFormFieldValue("record:keynumber");
	if (isneedkey == '1' && !keynumber){
		alert("������֤���ţ�");
		document.getElementById("record:keynumber").focus();
		return false;
	}
	return true;
}

// ���email�ĺϷ���
function checkEmail(){
	var email = document.getElementById("record:dzyx");
	return isEMail(email);
}

// ������֤�ĺϷ���
function checkID(){
	var idcard = document.getElementById("record:sfz");
	var result = isWeb(idcard);
	if(!result){
		alert("��������֤�ź��зǷ��ַ���");
	}
	return result;
}

// ѡ�����
function chooseOrgs(){
	var jzjgids = document.getElementById("record:jzjgid").value;
	selectJG('check','record:jzjgid','record:jzjgname',jzjgids);
}
function checkPasswordRule(ruleXml,psw){
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
			//����Ҫ�������ַ�
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
		document.getElementById("record:yhmm").hint = getHint(xml)||'';
	});
	$(document.getElementById("record:yhbh")).filterInput();
	$(document.getElementById("record:yhxm")).filterInput();
	
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		if(index<2){
		$(this).click(function(){
			$($(".radioNew")[1-index]).css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		$(this).next()[0].onclick=function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		};}else{
		$(this).click(function(){
			$($(".radioNew")[5-index]).css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		$(this).next()[0].onclick=function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		};
		}
		
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
});

</script>

<freeze:form action="/txn807013">
  <freeze:block property="record" caption="�½��û���Ϣ" width="95%" >
    <freeze:button name="record_saveRecord" caption="�� ��" txncode="807013" hotkey="SAVE" onclick="func_record_saveRecord();"/>
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="yhid_pk" caption="�û�ID" style="width:100%"/>
    <freeze:text property="yhbh" caption="�û����" style="width:90%" datatype="number" maxlength="20" minlength="1"/>
    <freeze:text property="yhxm" caption="�û�����" style="width:90%" datatype="string" maxlength="20" minlength="1"/>
    <freeze:text property="yhzh" caption="�û��˺�" style="width:90%" datatype="string" maxlength="20" minlength="1" hint="����ֵ�����ظ�����Ϊ���ֻ�Ӣ����ĸA-Z��a-z�����"/>
	
	<freeze:text property="jgname" caption="<font color='red'>*��������</font>" style="width:60%" minlength="1" readonly="true"/>&nbsp;<input type="button" name="orgchoice" value="ѡ��" onclick="selectJG('tree','record:jgid_fk','record:jgname')" class="menu"/>
    <freeze:hidden property="jgid_fk" caption="��������" style="width:90%" minlength="1"/>
   
    <freeze:password property="yhmm" caption="<font color='red'>*�û�����</font>" style="width:90%" />
    <freeze:password property="yhmm1" caption="<font color='red'>*ȷ������</font>" style="width:90%" />    
    <freeze:browsebox property="mainrole" caption="��Ҫ��ɫ" valueset="��֯������ɫ" show="name" style="width:90%" namebox="mainrolename" notnull="true"/>    
    <freeze:text property="plxh" caption="�������" value="0" style="width:90%" datatype="string" maxlength="10"/>
      
    <freeze:browsebox property="roleids" caption="������ɫ" valueset="��֯������ɫ" show="name" style="width:96%"  multiple="true" namebox="rolenames" colspan="3"/> 
	<!--  
    <freeze:text property="jzjgname" caption="��ְ����" style="width:83%" colspan="3" readonly="true"/>&nbsp;&nbsp;<input type="button" name="orgchoice" value="ѡ��" onclick="chooseOrgs()" class="menu"/>
    -->
    <freeze:hidden property="jzjgname" caption="��ְ����" style="width:80%" />   
    <freeze:hidden property="jzjgid" caption="��ְ����id" style="width:80%" />   

    <freeze:hidden property="sfyx" caption="�Ƿ���Ч" style="width:90%" value="0"/>
    <freeze:text property="sfz" caption="���֤��" style="width:90%" datatype="idcard" maxlength="20"/>
    <freeze:text property="zw" caption="ְλ" style="width:90%" datatype="string" maxlength="64"/>
    <freeze:textarea property="zyzz" caption="��Ҫְ��" style="width:96%" maxlength="255" colspan="3" rows="5" valign="center"/>
    <freeze:text property="gzdh" caption="�����绰" style="width:90%" datatype="phone" maxlength="20"/>
    <freeze:text property="lxdh" caption="��ϵ�绰" style="width:90%" datatype="phone" maxlength="20"/>
    <freeze:text property="dzyx" caption="�����ʼ�" style="width:96%" datatype="mail" maxlength="64" colspan="3" />
    <freeze:text property="qtlxfs" caption="������ϵ��ʽ" style="width:96%" datatype="string" maxlength="64" colspan="3"/>
    <freeze:radio property="isneedkey" caption="֤���½" valueset="��������" style="width:90%" notnull="true"></freeze:radio>
    <freeze:text  property="keynumber" caption="֤����" style="width:90%"></freeze:text>
    <freeze:textarea property="bz" caption="��ע" style="width:96%" maxlength="255" colspan="3" rows="5" valign="center"/>
     </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>