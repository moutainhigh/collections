<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>

<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="850">
<head>
<title>����trs��������</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-boxy/js/jquery.boxy.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-boxy/js/jquery-webox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/service/share_service.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-tab/jquery.tabs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>

<link href="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
<!-- start add by dwn  20140225-->
<!-- end add by dwn  20140225-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/script/jquery-plugin-boxy/css/boxy.css"
	type="text/css" />
<style type="text/css">
</style>
</head>

<script language="javascript">
	var pop = false;
	// �� ��
	function func_record_saveRecord() {
		saveRecord('', '����trs����ӿ�');
	}

	// ���沢����
	function func_record_saveAndContinue() {
		saveAndContinue('', '����trs����ӿ�');
	}

	// ���沢�ر�
	function func_record_saveAndExit() {
		var limit_data1 = $(tab4_limit_div).tablet("getAllData");
		limit_data1 = eval("("+limit_data1+")");
		var limit_str=obj2str(limit_data1);
		setFormFieldValue( "record:limit_data" ,limit_str);
		saveAndExit('', '����trs����ӿ�'); // /txn40300001.do
	}

	// �� ��
	function func_record_goBack() {
		goBack(); // /txn40300001.do
	}

	// ����������ӣ�ҳ�������ɺ���û���ʼ������
	function __userInitPage() {
		initWeek();
		initTime();

		var limit_data = getFormFieldValue("record:limit_data");
		if(limit_data==''){
			limit_data={data:[]};
		}else{
		    limit_data = eval("(" + limit_data + ")");
		}
		var data_array = new Array;
		data_array.push({
			"week" : "����",
			"datesStr" : "��ʼʱ��-����ʱ��",
			//"end_time": "",
			"times_day" : "ÿ������ܴ���",
			"count_dat" : "���η�������",
			"total_count_day" : "ÿ�����������"
		});
		limit_data = limit_data.data;
		for ( var ii = 0; ii < limit_data.length; ii++) {
			data_array.push(limit_data[ii]);
		}
		var opts_lim = {
			data : data_array,
			shownum : 8
		}
		$("#tab4_limit_div").tablet(opts_lim);
	}

	_browse.execute('__userInitPage()');
</script>
<freeze:body>
	<freeze:title caption="����trs�����ӿ�" />
	<freeze:errors />
    <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="trs_service_id" caption="����ID" style="width:95%"/>
    </freeze:frame>
	<freeze:form action="/txn40300012.do" method="post">
		<freeze:block property="record" caption="�޸�trs�ӿ���Ϣ" width="95%">
			<freeze:hidden property="trs_service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%" />
			<freeze:cell property="service_targets_id" caption="�����������" valueset="��Դ����_��������������" show="name" style="width:95%" />
			<freeze:cell property="trs_service_name" caption="��������"  datatype="string" style="width:95%" />
			<freeze:hidden property="limit_data" datatype="string" style="width:95%" />
		</freeze:block>
	

	<br />

	<div>
		<table class="dd_table" border="1" cellpadding="3" cellspacing="0"
			width="95%" align="center">
			<tr style="padding-top: 5px;">
				<td>
					<table width="100%" border="0" align="center" class="frame-body"
						cellpadding="0" cellspacing="0">
						<tr>
							<td style="padding: 0;">
								<table width="100%" border="1"
									style="background-color: #dee6e9;; border-collapse: collapse;"
									align="center" cellpadding="2" cellspacing="0">
									<tr>
										<td nowrap align="left" width="150px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�޶�����</td>
										<td colspan=3 id="weekTd"></td>
									</tr>
									<tr>
										<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ʱ��<input
											id='limitTimeBtn' onFocus='this.blur();' type='checkbox'
											value='0' onclick="setlimitTime();" /></td>
										<td nowrap align='left' width="40%"><input id="datesStr"
											readonly type="hidden" value="����Ҳఴťѡ��ʱ��" style="width: 80%;" />
											<span id="dates" style="width: 75%">(ע���ص���ʱ��ν����Զ��ϲ�)</span>
											<div id="Date_zone"
												style="background: #fcfcfc; z-index: 9934; display: none; position: absolute; left: 400px; top: 160px; border: 1px solid #069;">
												��ʼ���ڣ�<input id="stime" readonly type="text" value='00:00'
													size='6' />�� �������ڣ�<input id="etime" readonly type="text"
													value="24:00" size='6' /> <br /> <input type="button"
													value="ȷ��" onclick="submitDate()" /><input type="button"
													value="�ر�" onclick="cancelDate()" /> <br />
											</div> <!--  �� <input id="stime" readonly="true" type="text" value='00:00' size='6' />&nbsp;�� &nbsp; <input id="etime" readonly="true" type="text" value="24:00" size='6' />
		  --> <input id="addDateBtn" type="button" onclick="showDate()"
											value="���" /></td>
										<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ÿ����ʴ���<input
											id='limitNumberBtn' onFocus='this.blur();' type='checkbox'
											value='0' onclick="setnumber();" /></td>
										<td nowrap align='left' width="30%">ÿ��&nbsp;<input
											id="limitNumber" type="text" size='6' />&nbsp;��
										</td>
									</tr>
									<tr>
										<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ÿ�η�������<input
											id='limitNumPerBtn' onFocus='this.blur();' type='checkbox'
											value='0' onclick="setnumPer();" /></td>
										<td nowrap align='left' width="40%">ÿ��&nbsp;<input
											id="limitNumPer" disabled="disabled" name="limitNumPer"
											type="text" size='6' />&nbsp;��
										</td>
										<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ÿ������<input
											id='limitTotalBtn' onFocus='this.blur();' type='checkbox'
											value='0' onclick="setTotal();" /></td>
										<td nowrap align='left' width="30%">ÿ��&nbsp;<input
											id='limitTotal' type="text" size='6' />&nbsp;��
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td
								style="text-align: right; padding-top: 5px; padding-right: 10px; background-color: white;"><span
								style="color: red;" id="tab4_error_msg">&nbsp;&nbsp;</span>&nbsp;&nbsp;
								<button onclick="addLimitData();">�����������</button>&nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td><div id="tab4_limit_div"></div></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<br/>
    <div style="text-align: center">
      <div title="����" class="btn_save" onclick="func_record_saveAndExit();"/></div>&nbsp;&nbsp;
      <div title="����" class="btn_cancel" onclick="func_record_goBack();"/></div>
    </div>
  </freeze:form>
</freeze:body>
</freeze:html>
