<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>案件查询</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<style>
body {
	overflow-x: hidden;
	position: relative;
	height: 424px;
	background-color: #F8F9FB
}

.jazz-grid-tables {
	height: 100%;
	overflow-x: hidden;
	width: 100%;
}

.class_td1 {
	width: 15%;
	height: 5%;
	padding: 4px;
}

.class_td2 {
	width: 35%;
	height: 5%;
}

.class_td4 {
	width: 15%;
	height: 5%;
	padding: 4px;
}

.class_td5 {
	width: 35%;
	height: 5%;
}

.class_th77 {
	height: 0.1px;
	background-color: red;
}

.formpanel_table {
	display: none;
	background-color: #F8F9FB;
	width: 100%;
	height: 100%;
	margin: 0px;
	padding: 0px;
	position: relative;
	bottom: 0px;
}

.formpanel_table1 {
	display: none;
	background-color: #F8F9FB;
	width: 100%;
	height: 100%;
	margin: 0px;
	padding: 0px;
	position: fixed;
	bottom: 17px;
}

.font_table {
	height: 100%;
	width: 100%;
	font-family: '微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
}

.font_title {
	font-family: '微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
	color: #327bb9;
	font-size: 14px;
	font-weight: bold;
	margin-left: 5px;
	text-transform: none;
}

table.font_table {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #a9c6c9;
	border-collapse: collapse;
}

table.font_table th {
	border-width: 1px;
	padding: 8px;
	font-size: 14px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.font_table td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

.thleft {
	text-align: left;
	padding
}

.thright {
	text-align: right;
	padding
}

#div1 {
	display: inline-block;
}

.cleanButton {
	position: relative;
	right: -220px;
	font-family: '微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
	top: 13px;
}

.fontwidth {
	display: inline-block;
	width: 100px;
	/* font-size: 14px; */
	font-family: '微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
}

.inputjuzhong {
	vertical-align: middle;
	position: relative;
	left: 64px;
}

.hangjianju {
	line-height: 30px;
}

table.font_table input {
	position: relative;
	left: 65px;
}

#caseFiDate {
	position: relative;
	left: 53px;
}

#cloCaseDate {
	position: relative;
	left: 22px;
}
</style>
<script
	src="<%=request.getContextPath()%>/static/script/comselect/com_main.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//setAdminbrancode('caseNo', 'caseNo');
		setAdminbrancode('caseFiauth', 'caseFiauth');
	});

	function query() {
		var caseFiDate = $("div[name = 'caseFiDate']").datefield('getValue');
		var cloCaseDate = $("div[name = 'cloCaseDate']").datefield('getValue');

		$("#caseForm").attr(
				"action",
				rootPath + "/caseselect/toPage.do?caseFiDate=" + caseFiDate
						+ "&cloCaseDate=" + cloCaseDate);
		$("#caseForm").submit();
	}

	function reset() {
		$('form')[0].reset();
		//$("#formID").reset();
	}

	function validate(Time1, Time2) {
		var endTime = $("div[name = '" + Time1 + "']").datefield('getValue');
		var startTime = $("div[name = '" + Time2 + "']").datefield('getValue');

		if (!endTime) {
			endTime = "9999-12-31";
		}

		if (!startTime) {
			startTime = "0000-01-01";
		}

		var endTimeArray = endTime.split("-");
		var startTimeArray = startTime.split("-");

		if (endTimeArray[0] < startTimeArray[0]) {
			return false;
		} else if (endTimeArray[1] < startTimeArray[1]) {
			return false;
		} else if (endTimeArray[2] < startTimeArray[2]) {
			return false;
		}

		return true;
	}

	$(function() {
		$("div[name = 'caseFiDate']").datefield("option", "change", function() {
			if (!validate("cloCaseDate", "caseFiDate")) {
				jazz.warn("企业成立起始日期不能晚于结束日期!");
			}
		});
		$("div[name = 'cloCaseDate']").datefield("option", "change",
				function() {
					if (!validate("cloCaseDate", "caseFiDate")) {
						jazz.warn("企业成立起始日期不能晚于结束日期!");
					}
				});
	});

	//获取代码集
	function setAdminbrancode(type, id) {
		$.ajax({
			type : "POST",
			url : rootPath + "/caseselect/code_value.do",
			data : {
				'type' : type
			},
			dataType : 'json',
			success : function(data) {
				var data = data.data[0]["data"];
				var str = "";
				for (var i = 0; i < data.length; i++) {
					var text = data[i]["text"];
					var value = data[i]["value"];
					if (value.length > 0) {
						str = str + "<option value='"+value+"'>" + text
								+ "</option>";
					} else {
						str = str + "<option selected='selected'value=''>"
								+ text + "</option>";
					}

				}
				var name = "select[name='" + id + "']";
				$(name).append(str);
			},
			error : function(error) {
				jazz.error("获取代码集出错！");
			}
		});
	}
</script>

</head>
<body>
<div class="title_nav">当前位置：通用查询> 通用查询> <span>案件查询</span></div>
	<form method="post" target="_blank" id="caseForm" action="">

		<table id="datashow" class="font_table">
			<tr class="class_hg">
				<th class="class_td4">案件编号:</th>
				<th class="class_td5 thleft"><input type="text" name="caseNo"
					style="height: 25px; width: 300px" value="" /></th>
					</div>
				</th>
				<th class="class_td4">案件名称：</th>
				<th class="class_td5 thleft"><input type="text" name="caseName"
					style="height: 25px; width: 300px" value="" /></th>
			</tr>
			<tr>
				<th class="class_td4">立案机关:</th>
				<th class="class_td5 thleft">
					<div>
						<select name="caseFiauth" class="inputjuzhong"
							style="height: 25px; width: 300px" onchange="">
							<option selected="selected" value="">全部</option>
						</select>
					</div>
				</th>
				<th class="class_td4">证件号码：</th>
				<th class="class_td5 thleft" colspan="3"><input type="text"
					name="cerNo" style="height: 25px; width: 300px" value="" /></th>
			</tr>
			<tr>
				<th class="class_td4">立案日期:</th>
				<th class="class_td5 thleft hangjianju">
					<div name="caseFiDate" id="caseFiDate" vtype="datefield" label=""
						labelwidth="" labelalign="right" width="320" height="50"
						tooltip="" rule="" readonly=""></div>
				</th>
				<th class="class_td4">销案日期:</th>
				<th>
					<div name="cloCaseDate" id="cloCaseDate" vtype="datefield" label=""
						labelwidth="" labelalign="right" width="322" height="50"
						tooltip="" rule="" readonly=""></div>
				</th>
			</tr>
			<tr>
				<th class="class_td4">当事人类型:</th>
				<th class="class_td5 thleft hangjianju">
					<div>
						<select name="partyType" class="inputjuzhong"
							style="height: 25px; width:300px">
							<option selected="selected" value="0">自然人</option>
							<option value="1">法人或其他组织</option>
						</select>
					</div>
				</th>
				<th class="class_td4">当事人名称:</th>
				<th class="class_td5 thleft"><input type="text" name="unitName"
					style="height: 25px; width: 300px" value="" /></th>
			</tr>

			<!--  <tr>
				<th class="class_td4">注册号：</th>
				<th class="class_td5 thleft"><input type="text" name="regNo"
					style="height: 25px; width: 294px" value="" /></th>
				<th class="class_td4">统一社会信用代码:</th>
				<th class="class_td5 thleft"><input type="text" name="uniScid"
					style="height: 25px; width: 300px" value="" /></th>
			</tr>
			-->
		</table>
	</form>
	<div id="div1" style="height: 20px;"></div>
	<div id="toolbar" name="toolbar" vtype="toolbar">
		<div id="btn3" name="btn3" vtype="button" align="center"
			defaultview="1" text="开始查询" click="query()"></div>
		<div id="btn4" name="btn4" vtype="button" align="center"
			defaultview="1" text="清除查询条件" click="reset()"></div>
	</div>
</body>
</html>

