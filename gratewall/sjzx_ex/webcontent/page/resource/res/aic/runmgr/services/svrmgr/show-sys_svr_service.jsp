<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="650" height="350">
<head>
	<title>��ѯ��Χ����</title>
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/stepHelp.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/connectConditionPlugin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/queryConditionPlugin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/generateTotalTable.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/page/insert-sys_svr_service.js"></SCRIPT>
<style>
#totalTableDiv .leftTitle{
	background:url(/module/layout/layout-weiqiang/images_new/r_list_l.jpg) no-repeat !important;
}
#totalTableDiv .secTitle{
	background: #006699 !important;
}
#totalTableDiv .rightTitle{
	background:url(/module/layout/layout-weiqiang/images_new/r_list_r.jpg) no-repeat !important;
}
</style>	

</head>
<%
	String svrId = request
				.getParameter("record:sys_svr_service_id");
%>
<freeze:body>
	<freeze:title caption="�鿴����������Ϣ" />
	<form action="preview.jsp" method="post" target="pageList_frameX"
		style="margin: 0; padding: 0">
		<input type="hidden" id="record:query_sql" name="record:query_sql" />
		<input type="hidden" id="record:columnsEnArray"
			name="record:columnsEnArray" />
		<input type="hidden" id="record:columnsCnArray"
			name="record:columnsCnArray" />
		<div id="stepsContainerDiv" style="display: none"></div>
		<div id="step1DIV" style="display: none">
			<table width="95%" border="0" align="center" class="frame-body"
				cellpadding="0" cellspacing="1">
				<tr class="title-row">
					<td>
						���ݱ��ѯ����
					</td>
				</tr>
				<tr class="framerow">
					<td>
						<div id="div1" style="height: 100%; width: 100%;"></div>
					</td>
				</tr>
			</table>
			<div id="div2" style="margin-top: 10px;"></div>
			<p>
			<center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='toStep2Button' class="menu" value='��һ��' />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack0' class="menu" value=' �� �� ' onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center>
			</p>
		</div>

		<div id="step3DIV" style="display: none">
			<table width="95%" border="0" align="center" class="frame-body"
				cellpadding="0" cellspacing="1">
				<tr class="title-row">
					<td>
						���ݱ��ѯ����
					</td>
				</tr>
				<tr class="framerow">
					<td>
						<div id="columnsContainerDiv" style="height: 100%; width: 100%;"></div>
					</td>
				</tr>
			</table>
		</div>
		<div id="step3ButtonDiv" style="display: none">
			<p>
			<center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preStep2Button' class="menu" value="��һ��" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preViewButton' class="menu" value="��һ��" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack2' class="menu" value=" �� �� "
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center>
			</p>
		</div>

		<div id="step4DIV">
			<div id="totalTableDiv"></div>
			<br>
			<div align="center">
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack0' class="menu" value=' �� �� '
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</div>
		</div>
		<div id="step4ButtonDiv" style="display: none">
			<p>
			<center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preStep3Button' class="menu" value="��һ��" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='saveButton' class="menu" value=" �� �� " />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack2' class="menu" value=" �� �� "
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center>
			</p>
		</div>
	</form>
</freeze:body>
<script type="text/javascript">
  window.rootPath = "<%=request.getContextPath()%>";
  var ajaxXml;
  var doUpdate = function(connObj, obj2, dataObj){
	  	function getParamCN(v){
			switch(v){
				case 'AND':
					return '����';
					break;
				case 'OR':
					return '����';
					break;
				case '>':
					return '����';
					break;
				case '<':
					return 'С��';
					break;
				case '<>':
					return '������';
					break;
				case '=':
					return '����';
					break;
				case 'like':
					return '����';
					break;
				case '=%2B':
					return '������';
					break;
				default:
					return '';
					break;			
			}
		}
		
	  	function doInitQuery(){
			$.get("<%=request.getContextPath()%>/txn50202004.ajax?select-key:sys_svr_service_id=<%=svrId%>", function(xml){
				ajaxXml = xml;
				//������ѡ�����ݱ�
			    fillXmlToSelect(xml, "tbl-info", "selected_table", "table_no", "table_name_cn", {ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"});
			    document.getElementById("selected_table").oncontentchange();
			    var step1Params = xml.selectNodes("//record");
			    if(step1Params.length > 0){
			    	var con1 = document.getElementById(connObj.id + "Condition");
			    	con1.options.length = 0;
			    	con1.options.add(createOption('AND','����'));
			    	con1.options.add(createOption('OR','����'));
			    	con1.disabled = false;
				    
				    for(var i = 0; i < step1Params.length; i++){
				    	var step1Param = step1Params[i].selectSingleNode("params").text;
				    	if(step1Param){
					    	var jsonObj = eval("(" + step1Param + ")");
					    	
				    		addToArray(connObj.connJSONArray, jsonObj);
				    		
				    		var dispContent = "<font color='red'>"+getParamCN(jsonObj.condition) + " </font>" +
				    					      "<font color='red'>"+jsonObj.preParen + " </font>" +
				    					      jsonObj.tableOneNameCn + "<font color='red'> �� </font>" +
				    					      jsonObj.columnOneNameCn + " " +
				    					      "<font color='red'>"+getParamCN(jsonObj.relation) + " </font>" +
				    					      jsonObj.tableTwoNameCn + "<font color='red'> �� </font>" +
				    					      jsonObj.columnTwoNameCn + " " +
				    					      "<font color='red'>"+jsonObj.postParen + " </font>";
				    		
				    		insertParamRow(connObj, dispContent);
				    	}
				    }
			    }
			    step1Params = null;
			    
			    fillXmlToSelect(xml, "col-info", "selected_"+obj2.selectPrefix, "column_no", "column_name_cn", {tblid:"table_no", colName:"column_name", tblName:"table_name", tblNameCn:"table_name_cn"});
			    //dataObj.doSelectQuery("/txn50202006.ajax", null, null, "source_" + dataObj.selectPrefix, "sys_id", "sys_name");
	  	
			  	var totalTable = new generateTotalTable({
		           id					: "totalTable",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
		           parentContainer		: "totalTableDiv", // �丸�ڵ��ID
		           columnSelect			: "selected_columnTable",
		           tableSelect			: "selected_table",
		           connectConditionTable: "conditionTbl_conn",
		           tableParamOnColumn	: ["tblid", "tblNameCn", "tblName"],  // ����Ϊ��ID(Value)����������(Text)����Ӣ����(��������)
		           sysParamOnTable		: ["ztid", "ztName", "ztNo"],   	  // ����Ϊ����ID(Value)������������(Text)������Ӣ����(��������)
		           columnParamOnColumn	: ["colId", "colNameCn", "colName"]	  // ����Ϊ�ֶ�ID(Value)���ֶ�������(Text)���ֶ�Ӣ����(��������)
		        });
			    totalTable.write();
			});
	  	};
	  	
	  	doInitQuery();
	  	
	  	function insertParamRow(p, paramStr){
	  		var objTable = document.getElementById("conditionTbl_" + p.id);
			objTable.border = 1;
			var newTr = objTable.insertRow();
			//newTr.id = "curRow_"+rowIndex;
			newTr.borderColor = "#CCCCCC";
			newTr.height = 30;
		
			//�����
			var newTd0 = newTr.insertCell();
			var newTd1 = newTr.insertCell();
		
			//���������ݺ�����
			newTd0.innerHTML = paramStr; 
			newTd0.width = "90%";
			newTd0.align = "left";
			newTd1.innerHTML= "<a href='javascript:void(0)' id='deleteConnCondition_" + p.id + "'>ɾ��</a>";
			newTd1.width = "10%";
			newTd1.align = "center";
			
			document.getElementById(p.id + "hideRow").style.display = "block";
			
			var delButton = document.getElementsByName("deleteConnCondition_"+p.id);
		    var obj = delButton[delButton.length - 1];
		    if (obj){
		    	obj.onclick = function(){
		    		if(confirm("ȷ��ɾ����������")){
						p.deleteCondition(this.parentNode.parentNode.rowIndex);
		    		}
		    	};
		    }
	  	}
  		
	}
</script>
</freeze:html>
