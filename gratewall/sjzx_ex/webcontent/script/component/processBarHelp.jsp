<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�б�</title>
<style type="text/css">

</style>
<script type="text/javascript" src="processBarPlugin.js"></script>
</head>
<freeze:body>
	<h1>����������ҳ�棺</h1>
	<p><b>ʹ�÷���</b>��<br />����ͨ��new processBar����һ��processBar����</p>
	<p><b>���÷���</b>��<br />
	1. setPecent: ��������ɰٷֱ�<br />
	2. setInfo	: ������ʾ��Ϣ</p>
	<div id="testDiv"></div>
<script type="text/javascript">
   var pBar = new processBar({
       id					: "pBar",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
       parentContainer		: "testDiv"  // �丸�ڵ��ID
   });
   
   // ��ʾ�ٷֱ�����Ӧ�Ľڵ��ַ�����������Ϊ����ʾ
   // pBar.setPecent(5);
   // ��ʾ��ʾ��Ϣ�Ľڵ��ַ�����������Ϊ����ʾ
   // pBar.setInfo("��������xls�ļ�...");
   timeoutFunc(0);
   
	function timeoutFunc(pecent){
		pBar.setPercent(10 * ++pecent);
		pBar.setInfo("�������ɵ�" + pecent + "��xls�ļ�...");
		if (pecent >= 10){
			pBar.setInfo("���.");
			return;
		}
		setTimeout(function(){
	       timeoutFunc(pecent);
	    }, 500);
	}
</script>
</freeze:body>
</freeze:html>