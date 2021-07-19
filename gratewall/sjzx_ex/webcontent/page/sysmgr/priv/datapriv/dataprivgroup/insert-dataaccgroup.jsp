<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>

<freeze:include href="/script/lib/jquery.js"/>
<freeze:include href="/script/component/JqTree.js"/>
<freeze:include href="/script/plugins/jquery.form.js"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/JqTree.css"/>
<freeze:include href="/script/page/insert-dataaccgroup.js"/>

<freeze:html width="650">
<style>
	body{
		font-size:9pt;
	}
	
	td {
		font-size:9pt;
	}
	
	input.buttonface{
		COLOR: #000000;
		FONT-SIZE: 9pt;
		FONT-STYLE: normal;
		FONT-VARIANT: normal;
		FONT-WEIGHT: normal;
		LINE-HEIGHT: normal;
		background-image: url(../images/smallInput.gif);
		border: 1px solid #207FCF;
		height: 22px;
	}
</style>

<head>
<title>��������Ȩ���������Ϣ</title>
</head>

<script>
	var contextPath = "<%=request.getContextPath()%>";
	var dataaccgrpid = "<%=request.getParameter("primary-key:dataaccgrpid")%>";
	
	$(document).ready(function() {
		window.page = new pageInsertDataaccgroup.create(contextPath,dataaccgrpid);
	});
	
	function func_record_saveAndExit()
	{
		document.getElementById("record:saveXml").value = window.page.makeSaveXml().xml;
		var dataaccgrpname = document.getElementById("record:dataaccgrpname").value;
		if(!dataaccgrpname){
			alert("���Ʋ���Ϊ�գ�");
			return;
		}
		saveAndExit( '', '��������Ȩ����',"/txn1030041.do");
		
	}
	
	
</script>

<body style="margin:2;">
<div style="width:98%;margin-left:15;border:1pt solid #7BA9E9;">
	<div style="height:20px;width:100%;background-color:#6699FF;color:white">
		<div style="float:left;margin-left:15px;margin-top:3px;">����Ȩ�������</div>
	</div>
	<freeze:form action="/txn1030043" method="post">
	  	<table>
	  		<tr>
		  		<td style="color:red" align="right">*����Ȩ�������ƣ�</td>
		  		<td>
	  				<input type="hidden" property="dataaccgrpid" name="record:dataaccgrpid"/>
	  				<input type="hidden" property="dataacctype" value="1" name="record:dataacctype"/>
	  				<input maxLength="40" type="text" style="width:99%;" property="dataaccgrpname" name="record:dataaccgrpname" size="60"/></td></tr>
	  		<tr><td style="color:red" valign="top" align="right">*����Ȩ�ޣ�</td>
	  			<td>
	  				<div style="width:99%;border:1pt solid #7BA9E9;">
	  					<input type="hidden" name="record:saveXml"/>
	  					<div id="dataObjectTree" align="left" style="width:436px;height:320px;overflow-y:auto;overflow-x:hidden;">
					</div>
				</td>
			</tr>
	  		<tr><td style="color:red" align="right">*����Ȩ�޷��ʹ���</td><td>
	  			<select property="dataaccrule" name="record:dataaccrule">
					<option value="1">1-�������ѡ����</option>
					<option value="2">2-������ʷ�ѡ����</option>
				</select>
			</td></tr>
	  		<tr>
	  			<td  align="right">��ע��Ϣ��</td>
	  			<td>
	  				<textarea value="" property="dataaccgrpdesc" name="record:dataaccgrpdesc" rows="3" cols="60" type="_moz">
	  				</textarea>
	  			</td>
	  		</tr>
	  		<tr><td colspan="2" align="center">
	  		 <freeze:button styleClass="buttonface" name="record_saveAndExit"  caption="����" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
	  		 <input class="buttonface" onclick="goBack('txn1030041.do');" type="button" value=" �� �� "/></td></tr>
		</table>
		</freeze:form>
</div>
</body>
</freeze:html>