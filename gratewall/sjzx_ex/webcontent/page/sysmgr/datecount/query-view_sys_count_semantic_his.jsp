<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="java.util.*"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��ʷ������ѯ�б�</title>
<style type="text/css">
#downloadTbl td{
	border-bottom:1px solid #2975AF;
	border-right:1px solid #2975AF;
}

.veticalScrollLockTr{
	position:relative;
	top:expression(this.offsetParent.scrollTop);
	z-index:20;
}

.veticalScrollLockTd {
	left: expression(parentNode.parentNode.parentNode.parentNode.scrollLeft);
	position: relative;
	z-index:10;
}
</style>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
<% 
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
	String selectKeySysName = context.getRecord("select-key").getValue("sys_name");
	String selectKeyTableName  = context.getRecord("select-key").getValue("table_name_cn");
	String classState  = context.getRecord("select-key").getValue("class_state");
	String year  = context.getRecord("select-key").getValue("year");
	String month  = context.getRecord("select-key").getValue("month");
	String showFull  = context.getRecord("select-key").getValue("show_full");
%>
</head>

<script language="javascript">

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var selectObject = document.getElementById("select-key:sys_name");
	selectObject.onchange = function(){
		queryZTTables(selectObject.options[selectObject.selectedIndex].sysname);
		setStateObj();
	}
	
	var tblObj = document.getElementById("select-key:table_name_cn");
	tblObj.onchange = function(){
		setStateObj();
	}
	
	queryAllZT();
	initClassState();
	
	var yearObj = document.getElementById("select-key:year");
	yearObj.innerHTML = "";
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth() + 1;
	var selectYear = '<%=year%>';
	if(selectYear != 'null'){
		year = selectYear;
	}
	var selectMonth = '<%=month%>';
	if(selectMonth != 'null'){
		month = selectMonth;
	}
	for(var i = 2007; i <= 2017; i++){
		var option = document.createElement("option");
		option.value = i;
		option.appendChild(document.createTextNode(i+"��"));
		if(year == i){
			option.selected = true;
		}
		yearObj.appendChild(option);
	}
	var monthObj = document.getElementById("select-key:month");
	monthObj.innerHTML = "";
	var months = ['һ','��','��','��','��','��','��','��','��','ʮ','ʮһ','ʮ��'];
	for(i = 1; i <= 12; i++){
		var option = document.createElement("option");
		option.appendChild(document.createTextNode(months[i-1]+"��"));
		if(i < 10){
			i = "0"+i;
		}
		option.value = i;
		if(month == i){
			option.selected = true;
		}
		monthObj.appendChild(option);
	}
}

function initClassState(){
	var class_state = document.getElementById("select-key:class_state");
	var selectKeyState = '<%=classState%>';
	var option = document.createElement("option");
    option.value = "1";
    var text = document.createTextNode("��ҵ");
	option.appendChild(text);
	if(selectKeyState == '1'){
		option.selected = true;
	}
	class_state.appendChild(option);
	
	var option = document.createElement("option");
    option.value = "2";
    var text = document.createTextNode("����");
	option.appendChild(text);
	if(selectKeyState == '2'){
		option.selected = true;
	}
	class_state.appendChild(option);
	
	var option = document.createElement("option");
    option.value = "3";
    var text = document.createTextNode("ע��");
	option.appendChild(text);
	if(selectKeyState == '3'){
		option.selected = true;
	}
	class_state.appendChild(option);
	
	
}

//��ѯ����������Ϣ
function queryAllZT(){
	$.get("<%=request.getContextPath()%>/txn50202006.ajax", function(xml){
		var selectObject = document.getElementById("select-key:sys_name");
		if (selectObject){
			//�������������
			selectObject.innerHTML = "";
			//����Ĭ��ѡ��
			var option = document.createElement("option");
			option.value = "";
			var text = document.createTextNode("ȫ��");
			option.sysname = '';
	        option.appendChild(text);
	        selectObject.appendChild(option);
	        
	        //ѭ���������������Ϣ
			var nodeArray = xml.selectNodes("//record");
			for (var i = 0; i < nodeArray.length; i++){
	            var nodeElement = nodeArray.item(i);
	            var optionValue = nodeElement.selectSingleNode("sys_id");
	            var optionText = nodeElement.selectSingleNode("sys_name");
	            var option = document.createElement("option");
	            option.value = optionText.text;
	            var text = document.createTextNode(optionText.text);
	            if('<%=selectKeySysName%>' == optionText.text){
	            	option.selected = true;
	            }
				option.sysname = optionValue.text;
	            option.appendChild(text);
	            selectObject.appendChild(option);
            }
		}
		queryZTTables(selectObject.options[selectObject.selectedIndex].sysname);
	});
}

function queryZTTables(ztParam){
	if(ztParam != ''){
		$.get("<%=request.getContextPath()%>/txn60210008.ajax?record:sys_id=" + ztParam, function(xml){
			var selectObject = document.getElementById("select-key:table_name_cn");
			if (selectObject){
				//�������������
				selectObject.innerHTML = "";
				//����Ĭ��ѡ��
				var option = document.createElement("option");
				option.value = "";
				var text = document.createTextNode("ȫ��");
		        option.appendChild(text);
		        selectObject.appendChild(option);
		        
		        //ѭ���������������Ϣ
				var nodeArray = xml.selectNodes("//record");
				for (var i = 0; i < nodeArray.length; i++){
		            var nodeElement = nodeArray.item(i);
		            var optionValue = nodeElement.selectSingleNode("table_no");
		            var optionText = nodeElement.selectSingleNode("table_name_cn");
		            var option = document.createElement("option");
		            option.value = optionText.text;
		            var text = document.createTextNode(optionText.text);
		            if('<%=selectKeyTableName%>' == optionText.text){
		            	option.selected = true;
		            }
		            option.appendChild(text);
		            selectObject.appendChild(option);
	            }
			}
		});
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��ʷ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn81200002">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="year" caption="��ѯ���" style="width:90%" />
      <freeze:select property="month" caption="��ѯ�·�" style="width:90%" />
      <freeze:select property="sys_name" caption="��������" style="width:90%"/>
      <freeze:select property="table_name_cn" caption="��������" style="width:90%"/>
      <freeze:select property="class_state" caption="��ҵ״̬" style="width:90%"/>
      <freeze:radio property="show_full" caption="��ʾȫ��" valueset="�Ƿ���������" style="width:90%" />
  </freeze:block>
  <br/>
  
  <% 
  	Recordset rs = context.getRecordset("record");
  	List keyList = new ArrayList();
  	Map map = new HashMap();
  	while(rs.hasNext()){
  		DataBus db = (DataBus)rs.next();
  		if(!keyList.contains(db.getValue("table_class_id"))){
  			keyList.add(db.getValue("table_class_id"));
  		}
  		String[] values = new String[2];
		values[0] = db.getValue("count_full");
		values[1] = db.getValue("count_incre");
		Map m = null;
  		if(map.containsKey(db.getValue("table_class_id"))){
  			m = (Map)map.get(db.getValue("table_class_id"));
  			Map dateMap = (Map)m.get("DATE");
  			dateMap.put(db.getValue("count_date"), values);
  			m.put("DATE",dateMap);
  		}else{
  			m = new TreeMap();
  			Map dateMap = new TreeMap();
  			dateMap.put(db.getValue("count_date"), values);
  			m.put("DATE",dateMap);
  		}
  		m.put("SYS_NAME",db.getValue("sys_name"));
  		m.put("TABLE_NAME_CN",db.getValue("table_name_cn"));
  		m.put("TABLE_NAME",db.getValue("table_name"));
  		m.put("CLASS_SORT",db.getValue("class_sort"));
  		m.put("CLASS_STATE",db.getValue("class_state"));
		map.put(db.getValue("table_class_id"), m);
  	}
  	session.setAttribute("keyList",keyList);
  	session.setAttribute("resultMap",map);
  	int _total = keyList.size();
  %>
  <div align="center">
  <%if(_total > 0){ %><span style="width:95%;text-align:right"><input type="button" value=" �� �� " class="menu" onclick="document.getElementById('htmlStr').value = document.getElementById('downloadTbl').innerHTML; document.forms[1].submit()"/></span><%} %>
  <div style="height:<%=(_total*22+40) > 450?450:(_total*23+63) %>px;WIDTH:95%;OVERFLOW-Y:auto;OVERFLOW-X:auto;">
  <table id="downloadTbl" class="frame-body" width="100%" cellpadding="0" cellspacing="0" border="0">
  				<tbody>
  					<% 
				   		if(_total > 0){
				   			Map m = (Map)map.get(keyList.get(0));
				      		Set dateSet = ((Map)((Map)m).get("DATE")).keySet();
				      		%>
				      		  <tr class="title-row veticalScrollLockTr">
						        <td colspan="<%=6+dateSet.size()%>" align="left" >��ʷ�����б�</td>
						      </tr>
							  <tr align="center" class="grid-headrow veticalScrollLockTr">
							  	<td align="center" nowrap="nowrap" class="veticalScrollLockTd">���</td>
						        <td align="center" nowrap="nowrap" class="veticalScrollLockTd">��������</td>
						        <td align="center" nowrap="nowrap" class="veticalScrollLockTd">������״̬</td>
							  	<td align="center" nowrap="nowrap">����</td>
						        <td align="center" nowrap="nowrap">�¾�����</td>
						        <td align="center" nowrap="nowrap">��������ֵ</td>
						        <% 
						        	Iterator it = dateSet.iterator();
						        	while(it.hasNext()){
						        		String date = (String)it.next();
						        		if(showFull.equals("1")){
						        			%>
						        			<td align="center" nowrap="nowrap"><%=date + " ȫ��"%></td>
						        		<%
						        		}else{
						        		%>
						        		<td align="center" nowrap="nowrap"><%=date + " ����"%></td>
						        		<%
						        		}
						        	}
						        %>
						      </tr>
				      		<%
				      		  for(int i = 0; i < _total; i++){
				      		  	 m = (Map)map.get(keyList.get(i));
				      		  	 %>
				      		  	 <tr align="center" class="<%= i%2 == 0? "evenrow" : "oddrow" %>">
							        <td align="center" nowrap="nowrap" class="veticalScrollLockTd"><%=i+1%></td>
							        <td align="center" nowrap="nowrap" class="veticalScrollLockTd"><%=m.get("SYS_NAME")%> - <%=m.get("TABLE_NAME_CN")%></td>
							        <td align="center" nowrap="nowrap" class="veticalScrollLockTd"><%=m.get("CLASS_SORT")==null?"&nbsp;":m.get("CLASS_SORT")%>&nbsp;<%=m.get("CLASS_STATE")==null?"&nbsp;":m.get("CLASS_STATE")%></td>
							        <td align="center" nowrap="nowrap"><%=m.get("TABLE_NAME")%></td>
							     	<% 
							     		Map dateMap = (Map)m.get("DATE");
							     		Set keySet = dateMap.keySet();
						        		int total = 0;
				      					it = keySet.iterator();
							        	while(it.hasNext()){
							        		String date = (String)it.next();
							        		int incre = com.gwssi.common.util.StringUtil.intValue(((String[])dateMap.get(date))[1]);
							        		total += incre;
							        	}
							        	int totalDay = dateMap.size();
							        	int avg = total / totalDay;
							        %>
							        <td align="center" nowrap="nowrap"><%=total%></td>
							        <td align="center" nowrap="nowrap"><%=avg%></td>
							        <%
				      					it = dateSet.iterator();
							        	while(it.hasNext()){
							        		String date = (String)it.next();
							        		int incre = 0;
							        		if(dateMap.get(date) != null){
							        			incre = com.gwssi.common.util.StringUtil.intValue(((String[])dateMap.get(date))[1]);
								        		if(showFull.equals("1")){
								        			incre = com.gwssi.common.util.StringUtil.intValue(((String[])dateMap.get(date))[0]);
								        		}
							        		}
							        		%>
							        		<td align="center" nowrap="nowrap"><%=incre%></td>
							        		<%
							        	}
							        %>
							     </tr>
							     
				      		  	 <%
				      		  }
				   		}else{
				   			%>
				   			<tr class="title-row">
						        <td colspan="6" align="left" >��ʷ�����б�</td>
						      </tr>
							  <tr align="center" class="grid-headrow">
						        <td align="center">���</td>
						        <td align="center">��������</td>
						        <td align="center">��������</td>
						        <td align="center">����</td>
						        <td align="center">��������</td>
						        <td align="center">����״̬</td>
						      </tr>
						      <tr align="center" class="oddrow">
						      <td colspan="6" ><span id="showError" style='FONT-SIZE: 12pt; WIDTH: 100%; COLOR: red; TEXT-ALIGN: center'>��ʾ��û��������ݣ�</span></td>
						      </tr>
				   			<%
				   		}
				      %>
  				</tbody>
  				
  </table>
  </div></div>
</freeze:form>
<form method="post" action="<%=request.getContextPath()%>/sysmgr/datecount/query-view_sys_count_semantic_his_download.jsp" target="_blank" >
	<input type="hidden" id="htmlStr" name="htmlStr" />
</form>
</freeze:body>
<script language="javascript">
function setStateObj(){
	var sysname = getFormFieldValue("select-key:sys_name");
	var tblname = getFormFieldValue("select-key:table_name_cn");
	var sysArray = ['��ҵ�Ǽ�����','����Ǽ�����','���˿�����'];
	var tblArray = ['��ҵ(����)','��Ҫ��Ա','Ͷ����','���幤�̻�������','�ʼ෨�˿�'];
	
	if(sysname != ''){//������ⲻ�ǡ�ȫ����
		if(exist(sysArray, sysname)){//�ж��Ƿ�����״̬������
			if(tblname != ''){//������ǡ�ȫ����
				if(exist(tblArray, tblname)){//�ж��Ƿ�����״̬�ı�
					setFormFieldDisabled("select-key:class_state", 0, false);
				}else{
					setFormFieldDisabled("select-key:class_state", 0, true);
				}
			}else{
				setFormFieldDisabled("select-key:class_state", 0, false);
			}
		}else{
			setFormFieldDisabled("select-key:class_state", 0, true);
		}
	}else{
		setFormFieldDisabled("select-key:class_state", 0, false);
	}
}

function exist(arrayObj, findValue){
	if(arrayObj){
		for(var i = 0; i < arrayObj.length; i++){
			if(findValue == arrayObj[i]){
				return true;
			}
		}
	}
	return false;
}

</script>
</freeze:html>
