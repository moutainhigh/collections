<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>

<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="850">
<head>
<title>配置trs检索规则</title>
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
	// 保 存
	function func_record_saveRecord() {
		saveRecord('', '保存trs服务接口');
	}

	// 保存并继续
	function func_record_saveAndContinue() {
		saveAndContinue('', '保存trs服务接口');
	}

	// 保存并关闭
	function func_record_saveAndExit() {
		var limit_data1 = $(tab4_limit_div).tablet("getAllData");
		limit_data1 = eval("("+limit_data1+")");
		var limit_str=obj2str(limit_data1);
		setFormFieldValue( "record:limit_data" ,limit_str);
		saveAndExit('', '保存trs服务接口'); // /txn40300001.do
	}

	// 返 回
	function func_record_goBack() {
		goBack(); // /txn40300001.do
	}

	// 请在这里添加，页面加载完成后的用户初始化操作
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
			"week" : "星期",
			"datesStr" : "开始时间-结束时间",
			//"end_time": "",
			"times_day" : "每天访问总次数",
			"count_dat" : "单次访问条数",
			"total_count_day" : "每天访问总条数"
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
	<freeze:title caption="配置trs检索接口" />
	<freeze:errors />
    <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="trs_service_id" caption="服务ID" style="width:95%"/>
    </freeze:frame>
	<freeze:form action="/txn40300012.do" method="post">
		<freeze:block property="record" caption="修改trs接口信息" width="95%">
			<freeze:hidden property="trs_service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%" />
			<freeze:cell property="service_targets_id" caption="所属服务对象" valueset="资源管理_共享服务对象名称" show="name" style="width:95%" />
			<freeze:cell property="trs_service_name" caption="服务名称"  datatype="string" style="width:95%" />
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
										<td nowrap align="left" width="150px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限定星期</td>
										<td colspan=3 id="weekTd"></td>
									</tr>
									<tr>
										<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;访问时间<input
											id='limitTimeBtn' onFocus='this.blur();' type='checkbox'
											value='0' onclick="setlimitTime();" /></td>
										<td nowrap align='left' width="40%"><input id="datesStr"
											readonly type="hidden" value="点击右侧按钮选择时间" style="width: 80%;" />
											<span id="dates" style="width: 75%">(注：重叠的时间段将被自动合并)</span>
											<div id="Date_zone"
												style="background: #fcfcfc; z-index: 9934; display: none; position: absolute; left: 400px; top: 160px; border: 1px solid #069;">
												开始日期：<input id="stime" readonly type="text" value='00:00'
													size='6' />至 结束日期：<input id="etime" readonly type="text"
													value="24:00" size='6' /> <br /> <input type="button"
													value="确定" onclick="submitDate()" /><input type="button"
													value="关闭" onclick="cancelDate()" /> <br />
											</div> <!--  从 <input id="stime" readonly="true" type="text" value='00:00' size='6' />&nbsp;到 &nbsp; <input id="etime" readonly="true" type="text" value="24:00" size='6' />
		  --> <input id="addDateBtn" type="button" onclick="showDate()"
											value="添加" /></td>
										<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每天访问次数<input
											id='limitNumberBtn' onFocus='this.blur();' type='checkbox'
											value='0' onclick="setnumber();" /></td>
										<td nowrap align='left' width="30%">每天&nbsp;<input
											id="limitNumber" type="text" size='6' />&nbsp;次
										</td>
									</tr>
									<tr>
										<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每次访问总数<input
											id='limitNumPerBtn' onFocus='this.blur();' type='checkbox'
											value='0' onclick="setnumPer();" /></td>
										<td nowrap align='left' width="40%">每次&nbsp;<input
											id="limitNumPer" disabled="disabled" name="limitNumPer"
											type="text" size='6' />&nbsp;条
										</td>
										<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每天总数<input
											id='limitTotalBtn' onFocus='this.blur();' type='checkbox'
											value='0' onclick="setTotal();" /></td>
										<td nowrap align='left' width="30%">每天&nbsp;<input
											id='limitTotal' type="text" size='6' />&nbsp;条
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td
								style="text-align: right; padding-top: 5px; padding-right: 10px; background-color: white;"><span
								style="color: red;" id="tab4_error_msg">&nbsp;&nbsp;</span>&nbsp;&nbsp;
								<button onclick="addLimitData();">添加限制条件</button>&nbsp;&nbsp;</td>
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
      <div title="保存" class="btn_save" onclick="func_record_saveAndExit();"/></div>&nbsp;&nbsp;
      <div title="返回" class="btn_cancel" onclick="func_record_goBack();"/></div>
    </div>
  </freeze:form>
</freeze:body>
</freeze:html>
