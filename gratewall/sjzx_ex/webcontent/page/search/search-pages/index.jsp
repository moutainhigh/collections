<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/page/page-search.js"></script>
	<form action="<%=request.getContextPath()%>/search-pages/search.jsp" onsubmit="return checkInput()" method="post">
		<table width="95%" height="100" border="0" cellPadding="0" cellSpacing="0" align="center">
			<tr>
				<td align="center" valign="bottom"><input type="text" name="query" id="query" style="width:300px;"/> <input type="submit" value="  �� ��" style="background:url('../../images/search/searchButton.jpg') #EBEDDF no-repeat"/></td>
			</tr>
		</table>
	</form>
	<div id="queryHelpDiv" style="display:block">
  	<h3>ʹ��˵����</h3>
  	<ul>
		<li>1��������Ӧ������������������������������������ĵǼ���Ϣ��ϵͳ�����������ʡ����ʡ�˽Ӫ�����塢����ҵ�����ơ�����������������Ϣ��</li>
		<li>2������������Ҫ��
			<ul>
				<li>a)	�������һ�����֣�������������(��'������"��)��б��(��\��)�������ַ�������ֻ���롰�����С������й���������������������˾�������֡�</li>
				<li>b)	���������������ϴ󣬽�����ý�Ϊ�����Ĺؼ��ֽ��������Ի��������������</li>
				<li>c)	ϵͳ��������������������������������ָ�������Ϣ��������������й����������������������(�й�)���޹�˾���ڼ��������ֹ�˾���������м����������ŵĽ����</li>
				<li>d)	�����Ҫ����ؼ���������ʱ�򣬿��Բ��ÿո�ָ��ؼ��ֵ���ʽ���硰�й� ���������������</li>
				<li>e)	���ÿո�ָ����Ĺؼ��ֲ����Ⱥ�˳���硰�й� ������͡���� �й������������һ��</li>
			</ul>
		</li>
		<li>3��ע��������������������ʱ����֡����ݽ��շ����仯������ղ�ѯ������ʾ���������ڽ��ղ����˱�������ڴ��������²�ѯ��</li>
	</ul>
  </div>
</freeze:html>
