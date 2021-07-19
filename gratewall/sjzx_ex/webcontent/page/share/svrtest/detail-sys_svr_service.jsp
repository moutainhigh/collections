<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<script language="javascript">
		
//�����û��������ֶ����֣������ύʱ��������ֵ
var userParamArray = new Array();
function viewDetailInfo(){
	$.get("<%= request.getContextPath()%>/txn50204002.ajax?select-key:sys_svr_user_id=<%= request.getParameter("sys_svr_user_id")%>&select-key:sys_svr_service_id=<%= request.getParameter("sys_svr_service_id")%>", function(xml){
		var code = _getXmlNodeValue(xml,'record:svr_code');
		var name = _getXmlNodeValue(xml,'record:svr_name');
		var createDate = _getXmlNodeValue(xml,'record:create_date');
		var createBy = _getXmlNodeValue(xml,'record:create_by');
		var maxNum = _getXmlNodeValue(xml,'record:max_records');
		var create_date = _getXmlNodeValue(xml,'record:create_date');
		var service_type = _getXmlNodeValue(xml,'record:svr_type');
		var desc = _getXmlNodeValue(xml,'record:svr_desc');
		var colNameEnArray = _getXmlNodeValue(xml,'config-inf:permit_column_en_array');
		var colNameCnArray = _getXmlNodeValue(xml,'config-inf:permit_column_cn_array');
		
		setFormFieldText('record:svr_code',code);
		document.getElementById("SVR_CODE").value = code;
		document.getElementById("colNameEnArray").value = colNameEnArray;
		document.getElementById("colNameCnArray").value = colNameCnArray;
		setFormFieldText('record:svr_name',name);
		setFormFieldText('record:create_date',createDate);
		setFormFieldText('record:create_by',createBy);
		setFormFieldText('record:svr_type',service_type);
		setFormFieldText('record:max_records',maxNum);
		document.getElementById("JSJLS").value = maxNum;
		setFormFieldText('userParam:jsjls',maxNum);
		if(desc == ''){
			desc = ' ';
		}
		setFormFieldText('record:svr_desc',''+desc);
		
		//�������õı����Ӳ���
		var params = _getXmlNodeValues(xml, "record:params");
		//����ʱ���õĲ���
		var configParams = _getXmlNodeValues(xml, "config-param:params");
		//�Ƿ�������ϵͳ����
		var haveSysParam = false;
		//�Ƿ��������û�����
		var haveUserParam = false;
		
		for(var i = 0; i < params.length; i++){
			if(params[i].trim() != ''){
				var svrParamJSON = eval("(" + params[i] + ")");
				insertSysParamRow("sysParam", svrParamJSON.paramText, "left");
				haveSysParam = true;
			}
		}
		for(var i = 0; i < configParams.length; i++){
			if(configParams[i].trim() != ''){
				var configParamJSON = eval("(" + configParams[i] + ")");
				if(configParamJSON.paramType == "0"){
					insertSysParamRow("sysParam", configParamJSON.paramText, "left");
					haveSysParam = true;
				}else{
					insertUserParamRow(configParamJSON);
					haveUserParam = true;
					if(!existInArray(userParamArray, configParamJSON.columnOneName)){
						userParamArray.push(configParamJSON.columnOneName);
					}
				}
			}
		}
		if(!haveSysParam){
			insertSysParamRow("sysParam", "<font color='red'>��ϵͳ����!</font>", "center");
		}
		if(!haveUserParam){
			insertSysParamRow("userParam", "<font color='red'>���û�����!</font>", "center");
		}
	});
}

function existInArray(arr, v){
	if(arr == null || arr.length == 0)
		return false;
	for(var i=0;i<arr.length;i++){
		if(v.toString() == arr[i].toString()){
			return true;
		}
	}
	
	return false;
}

function insertSysParamRow(tblId, paramStr, align){
	var objTable = document.getElementById(tblId);
	objTable.border = 1;
	var newTr = objTable.insertRow();
	newTr.borderColor = "#CCCCCC";
	newTr.height = 30;
	
	//�����
	var newTd0 = newTr.insertCell();
	
	//���������ݺ�����
	newTd0.innerHTML = "&nbsp;&nbsp;"+paramStr; 
	newTd0.width = "90%";
	newTd0.align = align;
	newTd0.className = "framerow";
}

function insertUserParamRow(JSONObj){
	var objTable = document.getElementById("userParam");
	objTable.border = 1;
	var newTr = objTable.insertRow();
	newTr.borderColor = "#CCCCCC";
	newTr.height = 30;
	
	//�����
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	
	//���������ݺ�����
	newTd0.innerHTML = "&nbsp;&nbsp;"+JSONObj.paramText.replace("������ֵ��", ""); 
	newTd0.width = "30%";
	newTd0.className = "framerow";
	
	newTd1.innerHTML = "<input type='text' id='"+JSONObj.columnOneName+"' name='"+JSONObj.columnOneName+"'/>"; 
	newTd1.width = "70%";
	newTd1.className = "framerow";
}


function visitWs(){
	var post = "";
	for(var i = 0; i < userParamArray.length; i++){
		var params = document.getElementsByName(userParamArray[i]);
		post += userParamArray[i] + "=";
		for(var j = 0; j < params.length; j++){
			post += params[j].value;
			if(j != params.length -1){
				post += ",";
			}
		}
		
		if(i != userParamArray.length -1){
			post += "&";
		}
	}
    document.userParamForm.action = "wstest.jsp?"+post+"&<%=request.getQueryString()%>";
    document.userParamForm.target = "_self";
	document.userParamForm.submit();
}

</script>
<freeze:body>
<freeze:title caption="���񼰲�����Ϣ"/>
	<freeze:block property="record" columns="2" bodyline="true" caption="������Ϣ" border="1" width="95%" align="center" captionWidth="0.2">
		<freeze:cell property="svr_code" caption="�������&nbsp;" style="width:95%"/>
		<freeze:cell property="svr_name" caption="��������&nbsp;" style="width:95%"/>
		<freeze:cell property="create_date" caption="��������&nbsp;" style="width:95%"/>
		<freeze:cell property="create_by" caption="��&nbsp;��&nbsp;��&nbsp;" style="width:95%"/>
		<freeze:cell property="svr_type" caption="��������&nbsp;" style="width:95%"/>
		<freeze:cell property="max_records" caption="����¼��&nbsp;" style="width:95%"/>
		<freeze:cell property="svr_desc" caption="��������&nbsp;" style="width:95%"/>
	</freeze:block>
	<br />
	  <table width="95%" border="1" align="center" cellpadding="0" cellspacing="0" class="frame-body" frameType="block">
		  <tr>
			<td height="26" colspan="4" class="title-row" align="left" >ϵͳ����</td>
		  </tr>
		  <tbody id="sysParam"></tbody>
	  </table>
	  <br />
	<form name="userParamForm" action="wstest.jsp" method="post" />
	  <table width="95%" border="1" align="center" cellpadding="0" cellspacing="0" class="frame-body" frameType="block">
		  <tr>
			<td height="26" colspan="2" class="title-row" align="left" >�û�����</td>
		  </tr>
		  <tbody id="userParam"></tbody>
		  <tr>
			<td class="framerow" colspan="2" align="center" >
				<input type="hidden" name="SYS_SVR_USER_ID" id="SYS_SVR_USER_ID" value="<%= request.getParameter("sys_svr_user_id")%>" />
				<input type="hidden" name="SYS_SVR_SERVICE_ID" id="SYS_SVR_SERVICE_ID" value="<%= request.getParameter("sys_svr_service_id")%>" />
				<input type="hidden" name="SVR_CODE" id="SVR_CODE" value="" />
				<input type="hidden" name="colNameEnArray" id="colNameEnArray" />
				<input type="hidden" name="colNameCnArray" id="colNameCnArray" />
				<input type="hidden" name="KSJLS" id="KSJLS" value="1"/>
				<input type="hidden" name="JSJLS" id="JSJLS" />
				<input type="button" class="menu" value="�ύ��ѯ" onclick="visitWs();" />
				<input type="button" class="menu" value=" �� �� " onclick="window.open('<%=request.getContextPath()%>/txn50204001.do?<%=request.getQueryString()%>','_self')")/></td>
		  </tr>
	  </table>
	</form>
</freeze:body>
<script language="javascript">
viewDetailInfo();
</script>
</freeze:html>