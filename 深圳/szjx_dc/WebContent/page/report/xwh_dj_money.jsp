<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>

<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	$(document).ready(function getcode() {
		
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getZhuTiLeiXingTree.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#zhutileixing").comboxtreefield('option', "dataurl", JSON.parse(data));
				$("#zhutileixing").comboxtreefield("reload");
			}
		});

		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getHangYeLeiXingTree.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#hangyeleixing").comboxtreefield('option', "dataurl", JSON.parse(data));
				$("#hangyeleixing").comboxtreefield("reload");
			}
		});
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getQiYeLeiXingTree.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#qiyeleixing").comboxtreefield('option', "dataurl", JSON.parse(data));
				$("#qiyeleixing").comboxtreefield("reload");
			}
		});
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getGuanJianZiSelect.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#guanjianzi").comboxfield('option', "dataurl", JSON.parse(data));
				$("#guanjianzi").comboxfield("reload");

			}
		});

	});
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var hbegintime = aa.data.hbegintime;
		var hendtime = aa.data.hendtime;
		//var shejiketi = aa.data.shejiketi;
		var infotype = aa.data.infotype;
		var laiyuanfangshi = aa.data.laiyuanfangshi;
		var jieshoufangshi = aa.data.jieshoufangshi;
		var shijianjibie = aa.data.shijianjibie;
		var renyuanshenfen = aa.data.renyuanshenfen;
		var zhutileixing = aa.data.zhutileixing;
		var hangyeleixing = aa.data.hangyeleixing;
		var qiyeleixing = aa.data.qiyeleixing;
		var wangzhanleixing = aa.data.wangzhanleixing;
		var guanjianzi = aa.data.guanjianzi;
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("??????????????????????????????");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			alert("????????????");
			return;
		}
		var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' + 
		'\'' + begintime	 + '\'' + ',' + 
		'\'' + endtime		 + '\'' + ',' + 
		'\'' + hbegintime	 + '\'' + ',' + 
		'\'' + hendtime		 + '\'' + ',' + 
		'\'' + infotype		 + '\'' + ',' + 
		'\'' + laiyuanfangshi	 + '\'' + ',' + 
		'\'' + jieshoufangshi	 + '\'' + ',' + 
		'\'' + shijianjibie	 + '\'' + ',' + 
		'\'' + renyuanshenfen	 + '\'' + ',' + 
		'\'' + zhutileixing	 + '\'' + ',' + 
		'\'' + hangyeleixing	 + '\'' + ',' + 
		'\'' + qiyeleixing	 + '\'' + ',' + 
		'\'' + wangzhanleixing	 + '\'' + ',' + 
		'\'' + guanjianzi	 + '\'' + ')" > ???     ???</a>' + '</div>' + 
		"<div id=\"??????????????????????????????????????????_13607\" align=center x:publishsource=\"Excel\">\n" +
		"\n" + 
		"<table border=0 cellpadding=0 cellspacing=0 width=864 style='border-collapse:\n" + 
		" collapse;table-layout:automatic;width:648pt'>\n" + 
		" <col width=359 style='mso-width-source:userset;mso-width-alt:11488;width:269pt'>\n" + 
		" <col width=216 style='mso-width-source:userset;mso-width-alt:6912;width:162pt'>\n" + 
		" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
		" <col width=145 style='mso-width-source:userset;mso-width-alt:4640;width:109pt'>\n" + 
		" <tr height=27 style='height:20.25pt'>\n" + 
		"  <td colspan=4 height=27 class=xl6313607 width=864 style='height:20.25pt;\n" + 
		"  width:648pt'>?????????????????????????????????</td>\n" + 
		" </tr>\n" + 
		" <tr height=19 style='height:14.25pt'>\n" + 
		"  <td height=19 class=xl6413607 style='height:14.25pt;border-top:none'>????????????</td>\n" + 
		"  <td class=xl6413607 style='border-top:none;border-left:none'>????????????</td>\n" + 
		"  <td class=xl6413607 style='border-top:none;border-left:none'>??????</td>\n" + 
		"  <td class=xl6413607 style='border-top:none;border-left:none'>????????????</td>\n" + 
		" </tr>";
				
		$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/queryXwhMoney.do",
					data : {
						"begintime" : begintime,
						"endtime" : endtime,
						"hbegintime" : hbegintime,
						"hendtime" : hendtime,
						"infotype" : infotype,
						"laiyuanfangshi" : laiyuanfangshi,
						"jieshoufangshi" : jieshoufangshi,
						"shijianjibie" : shijianjibie,
						"renyuanshenfen" : renyuanshenfen,
						"zhutileixing" : zhutileixing,
						"hangyeleixing" : hangyeleixing,
						"qiyeleixing" : qiyeleixing,
						"wangzhanleixing" : wangzhanleixing,
						"guanjianzi" : guanjianzi
					},
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr height=19 style='height:14.25pt'>\n" +
								" <td height=19 class=xl6513607 style='height:14.25pt;border-top:none'>" + dataObj[i].name + "</td>\n" + 
								" <td class=xl6613607 style='border-top:none;border-left:none'>" + dataObj[i].???????????? + "</td>\n" + 
								" <td class=xl6613607 style='border-top:none;border-left:none'>" + dataObj[i].?????? + "</td>\n" + 
								" <td class=xl6613607 style='border-top:none;border-left:none'>" + dataObj[i].?????????????????? + "</td>\n" + 
								"</tr>";
							/* htmls += 
								"<tr height=20 style='height:15.0pt'>\n" +
								"  <td height=20 class=xl6416545 width=530 style='height:15.0pt;width:398pt'>"+dataObj[i].????????????+"???</td>\n" + 
								"  <td class=xl6316545 width=90 style='width:68pt'><span lang=EN-US>"+dataObj[i].????????????+"???</span></td>\n" + 
								"  <td class=xl6316545 width=88 style='width:66pt'><span lang=EN-US>"+dataObj[i].??????+"???</span></td>\n" + 
								"  <td class=xl6316545 width=87 style='width:65pt'><span lang=EN-US>"+dataObj[i].????????????+"???</span></td>\n" + 
								" </tr>"; */
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("xwh_dj_money_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 200);

	}
</script>


</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="??????????????????????????????????????????  ????????????">
		<div id="begintime" name='begintime' vtype="datefield" label="??????????????????"
			labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="??????????????????"
			labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="hbegintime" name='hbegintime' vtype="datefield"
			label="????????????????????????" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="hendtime" name='hendtime' vtype="datefield" label="????????????????????????"
			labelAlign="right" labelwidth='120px' width="410"></div>

		<div name='infotype' id="infotype" vtype="comboxfield"
			multiple="false" label="???????????????" labelAlign="right"
			dataurl='[{"text":"???????????????","value":"8"},{"text":"??????????????????","value":"1"},{"text":"??????","value":"2"},{"text":"??????","value":"3"},{"text":"??????","value":"4"},{"text":"???????????????","value":"6"},{"text":"??????","value":"9"}]'
			labelwidth='120px' width="410"></div>
		<div name='laiyuanfangshi' id="laiyuanfangshi" vtype="comboxfield"
			multiple="false" label="????????????" labelAlign="right"
			dataurl='[{"text":"?????????????????????????????????","value":"01"},{"text":"???????????????","value":"02"},{"text":"??????","value":"03"},{"text":"????????????","value":"04"},{"text":"????????????","value":"05"},{"text":"????????????","value":"06"},{"text":"????????????","value":"07"},{"text":"????????????","value":"08"},{"text":"??????","value":"09"},{"text":"??????","value":"10"},{"text":"??????????????????","value":"11"},{"text":"??????","value":"99"}]'
			labelwidth='120px' width="410"></div>
		<div name='jieshoufangshi' id="jieshoufangshi" vtype="comboxfield"
			multiple="false" label="????????????" labelAlign="right"
			dataurl='[{"text":"??????(??????)","value":"1"},{"text":"??????","value":"2"},{"text":"??????","value":"3"},{"text":"??????","value":"4"},{"text":"??????","value":"5"},{"text":"????????????","value":"6"},{"text":"??????","value":"7"},{"text":"??????12345??????","value":"8"},{"text":"??????","value":"9"},{"text":"????????????","value":"a"},{"text":"????????????","value":"b"},{"text":"?????????","value":"c"},{"text":"??????","value":"d"},{"text":"??????????????????","value":"e"},{"text":"?????????","value":"f"},{"text":"??????????????????","value":"g"},{"text":"???????????????","value":"h"},{"text":"????????????","value":"i"},{"text":"???????????????????????????","value":"j"},{"text":"QQ??????","value":"k"},{"text":"12358??????????????????????????????","value":"l"},{"text":"????????????","value":"m"},{"text":"315?????????","value":"n"},{"text":"???????????????","value":"o"},{"text":"??????12315????????????","value":"p"}]'
			labelwidth='120px' width="410"></div>
		<div name='shijianjibie' id="shijianjibie" vtype="comboxfield"
			multiple="false" label="????????????" labelAlign="right"
			dataurl='[{"text":"??????","value":"1"},
						{"text":"??????","value":"2"},
						{"text":"??????","value":"3"}]'
			labelwidth='120px' width="410"></div>
		<div name='renyuanshenfen' id="renyuanshenfen" vtype="comboxfield"
			multiple="false" label="????????????" labelAlign="right"
			dataurl='[{"text":"??????","value":"1"},
						{"text":"??????","value":"2"},
						{"text":"???????????????","value":"3"},
						{"text":"??????","value":"4"},
						{"text":"??????","value":"5"},
						{"text":"??????","value":"9"}]'
			labelwidth='120px' width="410"></div>

		<div id='zhutileixing' name='zhutileixing' vtype="comboxtreefield"
			label="??????????????????" labelalign="right" multiple="true" labelwidth='120px'
			width="410"></div>

		<div id='hangyeleixing' name='hangyeleixing' vtype="comboxtreefield"
			label="????????????" labelalign="right" multiple="true" labelwidth='120px'
			width="410"></div>
		<div name='qiyeleixing' id="qiyeleixing" vtype="comboxtreefield"
			multiple="true" label="????????????" labelAlign="right" labelwidth='120px'
			width="410"></div>
		<div name='wangzhanleixing' id="wangzhanleixing" vtype="comboxfield"
			multiple="false" label="????????????" labelAlign="right"
			dataurl='[{"text":"???????????????","value":"01"},{"text":"?????????","value":"02"},{"text":"?????????","value":"03"},{"text":"???????????????","value":"04"},{"text":"??????","value":"05"}]'
			labelwidth='120px' width="410"></div>
		<div name='guanjianzi' id="guanjianzi" vtype="comboxfield"
			multiple="false" label="?????????" labelAlign="right" labelwidth='120px'
			width="410"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="??????"
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="??????"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>
