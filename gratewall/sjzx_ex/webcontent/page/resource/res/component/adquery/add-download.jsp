<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
    System.out.println(context);
	String has_purv = context.getRecord("record").getValue("has_purv");
	String max_result = context.getRecord("record").getValue(
			"max_result");
	String reg_org = context.getRecord("record").getValue("reg_org");
	// �����쳣���
	if (has_purv == null) {
		has_purv = "0";
	}
	if (reg_org == null) {
		reg_org = "";
	}
%>
<script language="javascript">
//��ӷָ���
function addSeparateLine(){
	var ent_sort = document.getElementById("source_table");
	var sortArray = ent_sort.options;
	for (var i=0; i < sortArray.length; i++){
		if (sortArray[i].value == "ETLGCZH" || sortArray[i].value == "XTGL" ){
			var optgroup = document.createElement("optgroup");
			optgroup.label = "---------------";
			ent_sort.insertBefore(optgroup, sortArray[i]);
		}
	}
	sortArray = null;
	ent_sort = null;
}

</script>
<freeze:html width="650" height="350">
<head>
	<title>�����������</title>
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/stepHelp.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/connectConditionPluginJoin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/queryConditionPlugin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/generateTotalTable.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/page/page-ad-download.js"></SCRIPT>
	<script type="text/javascript">
	// ����ȫ�ֱ�����ά���������ͣ��������� �������أ��߼���ѯ
	var funcType = "download";
</script>
</head>
<freeze:body onload="addSeparateLine()">
	<freeze:title caption="��������" />
	<form
		action="<%=request.getContextPath()%>/dw/aic/gjcx/cxtjdy/preview.jsp"
		method="post" target="pageList_frameX" style="margin: 0; padding: 0">
		<input type="hidden" id="record:query_sql" name="record:query_sql" />
		<input type="hidden" id="record:query_dsql" name="record:query_dsql" />
		<input type="hidden" id="sqlCondition" name="sqlCondition" />
		<input type="hidden" id="record:columnsEnArray"
			name="record:columnsEnArray" />
		<input type="hidden" id="record:columnsCnArray"
			name="record:columnsCnArray" />
		<input type="hidden" id="has_purv" name="has_purv"
			value="<%=has_purv%>" />
		<input type="hidden" id="max_result" name="max_result"
			value="<%=max_result%>" />
		<input type="hidden" id="reg_org" name="reg_org" value="<%=reg_org%>" />
		<input type="hidden" id="displayType" name="displayType" />
		<input type="hidden" id="squeryid" />
		<div id="stepsContainerDiv" style="margin-left: 29.5px;"></div>
		<div id="step1DIV">
			<table width="95%" border="0" align="center" class="frame-body"
				cellpadding="0" cellspacing="0">
				<tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">���ݱ��ѯ����</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
				
				<tr class="framerow">
					<td style="border:2px solid #006699;" class="even2">
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
							<input type='button' id='goBack0' class="menu" value=' �� �� '
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center>
			</p>
		</div>

		<div id="step2DIV" style="display: none"></div>
		<div id="step2ButtonDiv" style="display: none">
			<p>
			<center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type="button" id="preStep1Button" class="menu" value="��һ��" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type="button" id="toStep3Button" class="menu" value="��һ��" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<!-- <input type="button" id="goBack1" class="menu" value=" �� �� " onclick="goBack();" /> -->
			</center>
			</p>
		</div>


		<div id="step3DIV" style="display: none">
			<table width="95%" border="0" align="center" class="frame-body"
				cellpadding="0" cellspacing="0">
				<tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">���ݱ��ѯ����</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
				
				<tr class="framerow">
					<td style="border:2px solid #006699;" class="even2">
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
				<!-- <input type='button' id='goBack2' class="menu" value=" �� �� " onclick="goBack();"/> -->
			</center>
			</p>
		</div>

		<div id="step4DIV" style="display: none">
			<div id="totalTableDiv"></div>
			<IFRAME id="pageList_frameX" name="pageList_frameX" frameBorder=0
				width="95%" align="center" height="300"></IFRAME>
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
							<input type='button' id='saveButton' class="menu" value="����Ϊģ��" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='downloadButton' class="menu"
								value="ֱ������" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' class="menu" value=" ���� "
								onclick="window.location='/txn6025001.do'" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<!-- <input type='button' id='goBack2' class="menu" value=" �� �� " onclick="goBack();"/> -->
			</center>
			</p>
			
		</div>
		<p align="center"><b>�����û�ֻ�����ر����ص�����</b></p>
			
	</form>
</freeze:body>
<script type="text/javascript">
	window.rootPath = "<%=request.getContextPath()%>";
	var doUpdate = null;
	var ajaxXml = null;
</script>
</freeze:html>
