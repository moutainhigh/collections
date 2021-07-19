<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.gwssi.common.database.DBOperation"%>
<%@page import="com.gwssi.common.database.DBOperationFactory"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<freeze:html width="900" height="350">
<head>
<title>�޸��û���Ϣ</title>
<%
DataBus context = (DataBus) request.getSession().getAttribute("oper-data");
DataBus contextDB = (DataBus)request.getAttribute("freeze-databus");
//System.out.println(contextDB);
String userRoleIds=contextDB.getRecord("record").getValue("roleids");
String roleIds=context.getValue("role-list");
//�ж��Ƿ���system��ɫ
boolean isSystem= context.getValue("role-list").indexOf("101")==-1 ? false : true; 

String[] ids=roleIds.trim().split(";");
String rids="";
for(int i=0;i<ids.length;i++){
	rids+= rids=="" ? "'"+ids[i]+"'" : ",'"+ids[i]+"'";
}
DBOperation operation = DBOperationFactory.createTimeOutOperation();
String sql="select ROLEID,ROLENAME from operrole_new where roleid in("+rids+") order by regdate";
System.out.println(sql);
List list=operation.select(sql);
boolean flag=true;
if(!isSystem){
	String[] userIds=userRoleIds.trim().split(",");
	for(int j=0;j<userIds.length;j++){
		if(roleIds.indexOf(userIds[j])==-1){
			flag=false;
		}
	}
	//System.out.println(flag);
	userRoleIds=userRoleIds.replaceFirst(",1681600","");
}

%>
</head>
<freeze:body>
<freeze:title caption="�޸��û���Ϣ"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/selectTree.js'></script>

<script language="javascript">
// ������Ϣ
function func_record_save_record(){
	var roleIds='<freeze:out value="${record.roleids}"/>';
	var userRoleIds='<freeze:out value="${oper-data.role-list}"/>';
	// У���û������Ƿ���ȷ
	if( checkJg() && checkCertStatus()){
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

// �������ĺϷ���
function checkPassword(){
	var psw = document.getElementById("record:yhmm").value;
	var psw1 = document.getElementById("record:yhmm1").value;
	if(psw==null || psw==""){
		return true;
	}else{
        return true;
	}
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
	hintArray.push("�û�����Ϊ��ʱ���޸�ԭ���롣����������㣺");
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

function checkJg(){

    var jgname = getFormFieldValue("record:jgname");
    if(jgname==""){
       alert("��ѡ������������");
       return false;
    }else{
       return true;
    }
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ruleSrc = '<%=request.getContextPath()%>/password.jsp';
		$.get(ruleSrc,function(xml){
			window.ruleXml = xml;
			document.getElementById("record:yhmm").hint = getHint(xml)||'';
		});
		$(document.getElementById("record:yhbh")).filterInput();
		$(document.getElementById("record:yhxm")).filterInput();
		$(".radioNew").hide();
		$(".radioNew").prev().show();
		/* $(".radioNew").each(function(index){
			$(this).click(function(){
				if(index<2){
					$(".radioNew")[(1-index)].style.backgroundPositionY='bottom';
					$(".radioNew")[(index)].style.backgroundPositionY='top';
					$(this).prev()[0].click();
				}else{
					$(".radioNew")[(5-index)].style.backgroundPositionY='bottom';
					$(".radioNew")[(index)].style.backgroundPositionY='top';
					$(this).prev()[0].click();
				}
			});
			$(this).next().click(function(){
				if(index<2){
					$(".radioNew")[(1-index)].style.backgroundPositionY='bottom';
					$(".radioNew")[(index)].style.backgroundPositionY='top';
					$(this).prev()[0].click();
				}else{
					$(".radioNew")[(5-index)].style.backgroundPositionY='bottom';
					$(".radioNew")[(index)].style.backgroundPositionY='top';
					$(this).prev()[0].click();
				}
			});
			if($(this).prev()[0].checked){
				$(".radioNew")[(index)].style.backgroundPositionY='top';
			}
		}); */
}
_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn807002">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="yhid_pk" caption="�û�ID" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸��û���Ϣ2434" width="95%" >
    <freeze:button name="record_saveAndExit" caption="����" txncode="807002" hotkey="SAVE" onclick="func_record_save_record();"/>
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="yhid_pk" caption="�û�ID" style="width:90%"/>
    <freeze:hidden property="yhbh" caption="�û����" style="width:90%"/>
    <freeze:text property="yhzh" caption="�û��˺�" style="width:90%" datatype="string" minlength="1" readonly="true" hint="�û��˺Ų����޸�"/>    
    <freeze:text property="yhxm" caption="�û�����" style="width:90%" datatype="string" maxlength="20" minlength="1"/>
    <freeze:hidden property="plxh" caption="�������" style="width:90%" datatype="string" maxlength="10"/>
    <freeze:text property="jgname" caption="<font color='red'>*��������</font>" style="width:60%"  readonly="true" 
    minlength="1"/>&nbsp;&nbsp;<table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td>
    <td>
    <a name="orgchoice" title="ѡ��" style="cursor:pointer;text-decoration:underline;" href="#" onclick="selectJG('tree','record:jgid_fk','record:jgname')">ѡ��</a>
   <!-- <input type="button" name="orgchoice" value="ѡ��" onclick="selectJG('tree','record:jgid_fk','record:jgname')" 
    class="menu"/>-->
    </td><td class="btn_right">
  </td></tr></table>   
    <freeze:radio property="sfyx" caption="ʹ��״̬"  valueset="syzt" style="width:90%"/>
    <freeze:hidden property="jgid_fk" caption="��������" style="width:90%" />
    <freeze:hidden property="yhmm" caption="�û�����" style="width:90%"   />    
    <freeze:hidden property="yhmm1" caption="ȷ������" style="width:90%"  />    
	<freeze:hidden property="mainrole" caption="��Ҫ��ɫ" style="width:90%"/>  
	<freeze:hidden property="mainrolename" caption="��Ҫ��ɫ����" style="width:90%"/> 
	<freeze:browsebox property="roleids" caption="��ɫ" multiple="true" valueset="��֯������ɫ" show="name" namebox="rolenames" notnull="true" colspan="2" style="width:96%" />
	<freeze:text property="maxline" caption="���ͬʱ������" datatype="number" maxlength="5" minlength="1" style="width:90%"/>
    <freeze:radio property="isneedkey" caption="֤���½" valueset="��������" style="width:90%"></freeze:radio>
    <freeze:text  property="keynumber" caption="֤����" style="width:90%"></freeze:text>
    <freeze:hidden property="jzjgname" caption="��ְ����" style="width:80%" />   
    <freeze:hidden property="jzjgid" caption="��ְ����id" style="width:80%" />    
    <freeze:hidden property="sfz" caption="���֤��" style="width:90%" datatype="idcard" maxlength="20" />
    <freeze:hidden property="zw" caption="ְλ" style="width:90%" datatype="string" maxlength="64"/>
    <freeze:hidden property="zyzz" caption="��Ҫְ��" style="width:96%" maxlength="255"/>
    <freeze:hidden property="gzdh" caption="�����绰" style="width:90%" datatype="phone" maxlength="20"/>
    <freeze:hidden property="lxdh" caption="��ϵ�绰" style="width:90%" datatype="phone" maxlength="20"/>
    <freeze:hidden property="dzyx" caption="�����ʼ�" style="width:96%" datatype="mail" maxlength="64"/>
    <freeze:hidden property="qtlxfs" caption="������ϵ��ʽ" style="width:96%" datatype="string" maxlength="64"/>
    <freeze:textarea property="bz" caption="��ע" style="width:96%" maxlength="255" colspan="3" rows="5" valign="center"/>
  </freeze:block>

</freeze:form>

</freeze:body>
</freeze:html>