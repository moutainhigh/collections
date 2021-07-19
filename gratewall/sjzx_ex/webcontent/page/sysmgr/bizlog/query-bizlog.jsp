<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="800" height="350">
<head>
<title>��ѯҵ����־�б�</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<freeze:body>
<freeze:title caption="��ѯϵͳ������־"/>
<freeze:errors/>

<script language="javascript">
function func_record_addRecord(){
  var page = new pageDefine( "/txn981214.do", "�鿴ҵ����־��Ϣ", "modal", 650, 400);
 //var page = new pageDefine( "/txn981214.do", "�鿴ҵ����־��Ϣ");
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage( );
}
//����ѡ��
function sjjg_select(){
  selectJG("tree","select-key:jgid_fk","select-key:orgname");
}

function selectYh(){
    var parameter = getFormFieldValue('select-key:jgid_fk');
    if(parameter==""){
      alert("����ѡ�����");
      return;
    }
	return 'jgid_fk=' + parameter;
}
function resetYu(){
    setFormFieldValue('select-key:opername',0,"");
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_addRecord();' title='�鿴��־' href='#'><div class='detail'></div></a>";
	}		
	document.getElementById('select-key:orgname').readOnly =true;
}

function clearChoose(){
	setFormFieldValue('select-key:orgname',0,"");
	setFormFieldValue('select-key:jgid_fk',0,"");
}

function clearFormFieldValue( formName )
{
	// ȡFORM
	if( formName == null ){
		var	obj = window.event.srcElement;
		if( obj == null || obj.form == null ){
			return;
		}
		
		formName = obj.form.name;
	}
	
	// ȡ�ֶ��б�
	var	fieldList = _getFormFieldList( formName );
	if( fieldList == null ){
		return;
	}
	
	// �������
	var	obj;
	var	value;
	for( var ii=0; ii<fieldList.length; ii++ ){
		field = fieldList[ii];
		
		// �ȼ��original:�ֶ�
		obj = document.getElementsByName('original:' + field.fieldName);
		if( obj != null && obj.length > field.index ){
			obj = obj[field.index];
			value = obj.value;
		}
		else{
			value = getFormFieldDefaultValue( field.fieldName, field.index );
			if( value == null ){
				value = '';
			}
		}
		
		setFormFieldValue( field.fieldName, field.index, value );
	}
	setFormFieldValue('select-key:orgname',0,"");
	setFormFieldValue('select-key:jgid_fk',0,"");
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn981211">
  <freeze:block theme="query" property="select-key" caption="��ѯҵ����־"  width="95%">   
    <freeze:text property="orgname" caption="��������" style="width:70%"  postfix="��<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select();resetYu();'>ѡ��</a>��"></freeze:text>
    <freeze:hidden property="jgid_fk"></freeze:hidden>
    <freeze:browsebox property="opername" caption="����Ա����" style="width:90%"  valueset="�������û�" show="name" data="name" parameter="selectYh();" /> 
    <freeze:text property="username" caption="�û��ʺ�" style="width:90%" datatype="string" />   
    <freeze:datebox property="regdate_from" caption="��������" style="width:90%" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    	</td><td width='5%'>��</td><td width='45%'>
      <freeze:datebox property="regdate_to" caption="ִ������" style="width:100%" colspan="0"/>
        </td></tr></table>       
  </freeze:block>
  <freeze:grid property="record" caption="ҵ����־�б�" keylist="flowno" width="95%" rowselect="false" multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:hidden property="flowno" caption="��ˮ��" width="10%" />
    <freeze:hidden property="reqflowno" caption="������ˮ��" width="10%" />
    <freeze:cell property="@rowid" caption="���" width="11%" align="center"/>
    <freeze:hidden property="username" caption="�û��ʺ�" align="center" width="11%"/>
    <freeze:cell property="opername" caption="����Ա����" align="center" width="12%"/>
    <freeze:cell property="orgid" caption="��������" width="10%" align="center" visible="false"/>
    <freeze:cell property="orgname" caption="��������" width="20%" align="center"/>
    <freeze:cell property="regdate" caption="��������" datatype="date" align="center" width="15%"/>
    <freeze:cell property="regtime" caption="����ʱ��" datatype="date" align="center" width="10%"/>
    <freeze:cell property="trdcode" caption="������" width="8%" visible="false"/>
    <freeze:cell property="trdname" caption="��������" align="center"/>
    <freeze:cell property="trdtype" caption="��������" width="12%" visible="false"/>
    <freeze:cell property="errcode" caption="�������" width="8%" visible="false"/>
    <freeze:cell property="operation" caption="����" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
