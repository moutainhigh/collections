<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>�޸Ļ�����Ϣ</title>
<style>
.odd2_b,.odd1_b {
	white-space: nowrap;
}
</style>
</head>
<freeze:body>
<freeze:title caption="�޸Ļ�����Ϣ"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/selectTree.js'></script>
<script language="javascript">
// ������Ϣ
function func_record_saveAndExit(){

 	if(checkParentOrg()&&checkPlxh()){
  		saveRecord( "","�޸Ļ�����Ϣʧ��");
  		refreshOrgTree();
	}else{
		return false;
	}
}

//**** ˢ�����˵���������޸��˻������ƻ��ϼ�����ˢ�·���ˢ����
function refreshOrgTree(){

  var sjjg = document.getElementById("record:sjjgid_fk").value;
  var jgid = document.getElementById("record:jgid_pk").value; 
  var jgmc = document.getElementById("record:jgmc").value;

  if(jgmc != oldJgmc || sjjg != oldSjjgid_fk){

   var param = "select-key:selectedid="+jgid;

    window.parent.parent.location.href = "<%=request.getContextPath()%>/txn806001.do?"+param;
  }
	
}

function func_record_goBack(){
    //goBack( "/txn806001.do" );
	window.parent.parent.location.reload();
}
//�½��¼�����
function func_record_addNextOrg(){

	var win = parent.parent.getIframeByName('tree_view');
      	if( win == null ){
      		return false;
      	}
   var page = new pageDefine( "/txn806007.do", "���ӻ���", "modal", 650,400);
   page.addParameter("record:jgid_pk","record:jgid_pk");
   page.addParameter("record:jgid_pk","record:sjjgid_fk");
   page.addParameter("record:jgmc","record:sjjgname");
   page.addRecord( null,win);

}

// ɾ������
function func_record_deleteOrg(){

	var win = parent.parent.getIframeByName('tree_view');
      	if( win == null ){
      		return false;
      	}
  var page = new pageDefine( "/txn806005.do", "ɾ������");
  page.addParameter("primary-key:jgid_pk","primary-key:jgid_pk"); 
  page.addParameter("record:jgmc","primary-key:jgmc"); 
  page.deleteRecord("�Ƿ�ע���û�����");	 
 
}

//**** �ж���ѡ�ϼ������Ƿ�Ϊ������
function checkParentOrg(){
  var sjjg = document.getElementById("record:sjjgid_fk").value;
  var jgid = document.getElementById("record:jgid_pk").value;

	if(sjjg==jgid){
	    alert("�ϼ���������Ϊ����������ѡ������������");
		// �ÿ��ϼ�����ѡ����
		document.getElementById("record:sjjgid_fk").value = "";
        document.getElementById("record:sjjgname").value = "";
		return false;
	}else{
		return true;
	}
}

//** У������Ļ������Ϊ���ֺ�Ӣ����ĸ��ʽ
function checkOrgCode(){
	var jgbh = document.getElementById("record:jgbh").value;
	if(!validateWebPath(jgbh)){
		alert("�����������Ƿ��ַ������������ּ�Ӣ����ĸA-Z��a-z����ϡ�");
		return false;
	}
	return true;
}
function checkPlxh(){

	/*
	var plxh = document.getElementById("record:plxh").value;
    reg = new RegExp("^[0-9]*[0-9][0-9]*$");
	if (reg.test(plxh)==false){
		alert("����ȷ���롾������š�");
		document.getElementById("record:plxh").select();
		return false;
	}	
	*/
	return true;    
}
</script>

<freeze:form action="/txn806002">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="jgid_pk" caption="����ID" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ļ�����Ϣ" width="100%" >
    <freeze:button name="record_saveAndExit" caption="�� ��" txncode="806002" hotkey="SAVE" onclick="func_record_saveAndExit();"/>

    <freeze:hidden property="jgid_pk" caption="����ID" style="width:90%"/>
    <freeze:hidden property="sjjgname" caption="�ϼ�����" style="width:90%"/>
    <freeze:hidden property="jgbh" caption="�������" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:cell property="sjjgname" caption="�ϼ�������" style="width:90%" colspan="2" />
    <freeze:hidden property="sjjgid_fk" caption="�ϼ�����" style="width:90%" />
    <freeze:text property="jgmc" caption="��������" style="width:75%" datatype="string" maxlength="60" minlength="1"/>
    <freeze:text property="plxh" caption="�������" style="width:75%" datatype="string" maxlength="10"/>
    <freeze:textarea property="bz" caption="��ע" style="width:90%" maxlength="255" rows="5" colspan="2" valign="center"/>
  </freeze:block>

</freeze:form>
<script language="javascript">
	var oldJgmc = document.getElementById("record:jgmc").value;
	var oldSjjgid_fk = document.getElementById("record:sjjgid_fk").value;
	//���������ַ�
	$(document).ready(function(){
		$("input").filterInput();
	});
</script>
</freeze:body>
</freeze:html>
