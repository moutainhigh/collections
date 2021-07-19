<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.gwssi.common.database.DBOperation"%>
<%@page import="com.gwssi.common.database.DBOperationFactory"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<freeze:html width="900" height="450">
<head>
<title>�½��û���Ϣ</title>
<%
DataBus context = (DataBus) request.getSession().getAttribute("oper-data");
String roleIds=context.getValue("role-list");
//�ж��Ƿ���system��ɫ
boolean isSystem= context.getValue("role-list").indexOf("101")==-1 ? false : true; 
String[] ids=roleIds.trim().split(";");
String rids="";
for(int i=0;i<ids.length;i++){
	rids+= rids=="" ? "'"+ids[i]+"'" : ",'"+ids[i]+"'";
}
DBOperation operation = DBOperationFactory.createTimeOutOperation();
String sql="select ROLEID,ROLENAME from operrole_new where roleid in("+rids+")";
List list=operation.select(sql);
//System.out.println(RoleDAO.getInst().getRoleInfo("102").size());
%>
</head>
<freeze:body>
	<freeze:title caption="�½��û���Ϣ" />
	<script type="text/javascript" src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/script/selectTree.js'></script>
	<script type="text/javascript">
// ������Ϣ
function func_record_saveRecord(){
//У���û������Ƿ���ȷ
	var psw = document.getElementById("record:yhmm").value;
	if(checkUserName()&&checkJg()&&checkCertStatus()){
		saveAndExit("","�����û���Ϣʧ��" ,"/txn807001.do");
	}else{
	  	return false;
	}
	
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

// �����б�
function func_record_goBack(){
  goBack( "/txn807001.do" );
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

// У������������Ƿ�Ϊ6λ�����Լ�ȷ�������Ƿ���ȷ
function checkPassword(){
	var psw = document.getElementById("record:yhmm").value;
	var psw1 = document.getElementById("record:yhmm1").value;
	
	//if(psw.length<6){
	//	alert("�����������С��6λ��������һ������6λ�����룡");
	//	return false;
	//}
    if(psw!=psw1){
		alert("�����������벻һ�£�");
		return false;
	}
	return true;
}

// У������ĵ�������
function checkEmail(){
	var email = document.getElementById("record:dzyx");
	return isEMail(email);
}

// У����������֤
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
});

function checkJg(){

    var jgname = getFormFieldValue("record:jgname");
    if(jgname==""){
       alert("��ѡ������������");
       return false;
    }else{
       return true;
    }
}

function valYhzh(){
    var yhzh = document.getElementById("record:yhzh").value;
    if(yhzh==""){
      return;
    }
    $.ajax({
	  type: "post",
	  url: "<%=request.getContextPath()%>/txn807009.ajax?select-key:yhzh=" + yhzh,
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert("�û��˺š�"+yhzh+"���Ѵ��ڣ�");
	  		document.getElementById("record:yhzh").value = "";
	  		document.getElementById("record:yhzh").focus();
	  	}
	  }
	});      
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$(".radioNew").hide();
	$(".radioNew").prev().show();
	/* $(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("position","absolute");
		$($(this).prev()[0]).css("left","-1000");
		$($(this).prev()[0]).css("top","-1000");
		//$($(this).prev()[0]).css("margin-bottom","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
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
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	}); */
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:form action="/txn807003">
		<freeze:block property="record" caption="�½��û���Ϣ1" width="95%">
			<freeze:button name="record_saveRecord" caption="�� ��"
				txncode="807003" hotkey="SAVE" onclick="func_record_saveRecord();" />
			<freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE"
				onclick="func_record_goBack();" />
			<freeze:hidden property="yhid_pk" caption="�û�ID" style="width:90%" />
			<freeze:hidden property="yhbh" caption="�û����" style="width:90%" />
			<freeze:text property="yhzh" caption="�û��˺�" style="width:90%"
				datatype="string" maxlength="20" minlength="1" onblur="valYhzh();"
				hint="����ֵ�����ظ�����Ϊ���ֻ�Ӣ����ĸA-Z��a-z�����" />
			<freeze:text property="yhxm" caption="�û�����" style="width:90%"
				datatype="string" maxlength="20" minlength="1" />
			<freeze:hidden property="plxh" caption="�������" style="width:90%"
				value="0" datatype="string" maxlength="10" />
			<freeze:text property="jgname"
				caption="<div style='color:red;display:inline;'>*��������</div>" style="width:60%"
				datatype="string" readonly="true" />&nbsp;
			<a name="orgchoice" title="ѡ��" style="cursor:pointer;text-decoration:underline;" href="#" onclick="selectJG('tree','record:jgid_fk','record:jgname')">ѡ��</a>
			<freeze:hidden property="jgid_fk" caption="��������" style="width:90%"
				minlength="1" />
			<freeze:text property="maxline" caption="���ͬʱ������" datatype="number"
				maxlength="5" value="1" minlength="1" style="width:90%" />
			<freeze:hidden property="yhmm"
				caption="<span style='color:red'>*�û�����</span>"
				value="E3CEB5881A0A1FDAAD01296D7554868D" style="width:90%" />
			<freeze:hidden property="yhmm1"
				caption="<span style='color:red'>*ȷ������</span>"
				value="E3CEB5881A0A1FDAAD01296D7554868D" style="width:90%" />
			<freeze:hidden property="mainrole" caption="��Ҫ��ɫ" />
			<freeze:hidden property="mainrolename" caption="��Ҫ��ɫ����" />
			<freeze:browsebox property="roleids" caption="��ɫ" multiple="true"
				valueset="��֯������ɫ" show="name" namebox="rolenames" notnull="true"
				colspan="2" style="width:96%" />
			<freeze:radio property="isneedkey" caption="֤���½" valueset="��������"
				notnull="true" style="width:90%" value="0"></freeze:radio>
			<freeze:text property="keynumber" caption="֤����" style="width:90%"></freeze:text>
			<freeze:hidden property="jzjgname" caption="��ְ����" style="width:80%" />
			<freeze:hidden property="jzjgid" caption="��ְ����id" style="width:80%" />
			<freeze:hidden property="sfyx" caption="�Ƿ���Ч" style="width:90%"
				value="0" />
			<freeze:hidden property="sfz" caption="���֤��" style="width:90%"
				datatype="idcard" maxlength="20" />
			<freeze:hidden property="zw" caption="ְλ" style="width:90%"
				datatype="string" maxlength="64" />
			<freeze:hidden property="zyzz" caption="��Ҫְ��" style="width:96%"
				maxlength="255" />
			<freeze:hidden property="gzdh" caption="�����绰" style="width:90%"
				datatype="phone" maxlength="20" />
			<freeze:hidden property="lxdh" caption="��ϵ�绰" style="width:90%"
				datatype="phone" maxlength="20" />
			<freeze:hidden property="dzyx" caption="�����ʼ�" style="width:96%"
				datatype="mail" maxlength="64" />
			<freeze:hidden property="qtlxfs" caption="������ϵ��ʽ" style="width:96%"
				datatype="string" maxlength="64" />
			<freeze:textarea property="bz" caption="��ע" style="width:96%"
				maxlength="255" colspan="3" rows="5" valign="center" />
		</freeze:block>

	</freeze:form>
</freeze:body>
</freeze:html>