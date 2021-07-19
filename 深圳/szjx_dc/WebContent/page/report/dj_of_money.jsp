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
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}

	$(document).ready(function getcode() {
		/*  $("body").ajaxStart(function(){
	       $("body").loading();
	      }); 
		 $("body").ajaxSuccess(function(){
			 $("body").loading("hide");
		        // $(this).empty(); // 或者直接清除
		      }); */
		/* $("#formpanel").loading();
		$("#formpanel").loading("hide"); */
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
		var shejiketi = aa.data.shejiketi;
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
				alert("初始日期大于截止日期");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			alert("日期为空");
			return;
		}
		var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' + 
		'\'' + begintime	 + '\'' + ',' + 
		'\'' + endtime		 + '\'' + ',' + 
		'\'' + hbegintime	 + '\'' + ',' + 
		'\'' + hendtime		 + '\'' + ',' + 
		'\'' + shejiketi	 + '\'' + ',' + 
		'\'' + infotype		 + '\'' + ',' + 
		'\'' + laiyuanfangshi	 + '\'' + ',' + 
		'\'' + jieshoufangshi	 + '\'' + ',' + 
		'\'' + shijianjibie	 + '\'' + ',' + 
		'\'' + renyuanshenfen	 + '\'' + ',' + 
		'\'' + zhutileixing	 + '\'' + ',' + 
		'\'' + hangyeleixing	 + '\'' + ',' + 
		'\'' + qiyeleixing	 + '\'' + ',' + 
		'\'' + wangzhanleixing	 + '\'' + ',' + 
		'\'' + guanjianzi	 + '\'' + ')" > 下     载</a>' + '</div>' + 
		"<div id=\"登记信息涉及金额统计表（新）_13607\" align=center x:publishsource=\"Excel\">\n" +
		"\n" + 
		"<table border=0 cellpadding=0 cellspacing=0 width=864 style='border-collapse:\n" + 
		" collapse;table-layout:automatic;width:648pt'>\n" + 
		" <col width=359 style='mso-width-source:userset;mso-width-alt:11488;width:269pt'>\n" + 
		" <col width=216 style='mso-width-source:userset;mso-width-alt:6912;width:162pt'>\n" + 
		" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
		" <col width=145 style='mso-width-source:userset;mso-width-alt:4640;width:109pt'>\n" + 
		" <tr height=27 style='height:20.25pt'>\n" + 
		"  <td colspan=4 height=27 class=xl6313607 width=864 style='height:20.25pt;\n" + 
		"  width:648pt'>登记信息涉及金额统计表</td>\n" + 
		" </tr>\n" + 
		" <tr height=19 style='height:14.25pt'>\n" + 
		"  <td height=19 class=xl6413607 style='height:14.25pt;border-top:none'>消费类型</td>\n" + 
		"  <td class=xl6413607 style='border-top:none;border-left:none'>涉及金额</td>\n" + 
		"  <td class=xl6413607 style='border-top:none;border-left:none'>案值</td>\n" + 
		"  <td class=xl6413607 style='border-top:none;border-left:none'>经济损失</td>\n" + 
		" </tr>";
		
$.ajax({
			type : "post",
			url : rootPath + "/quert12315Controller/queryDjMoney.do",
			data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"hbegintime" : hbegintime,
				"hendtime" : hendtime,
				"shejiketi" : shejiketi,
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
			beforeSend:function(){
				$("body").loading();
			},
			success : function(data) {
				$("body").loading("hide");
				var dataObj = eval("(" + data + ")");
				for (var j = 0,jj=dataObj.length; j < jj; j++) {
					for (var i = 0,ii=dataObj[j].length; i < ii; i++) {
						htmls+=
							"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl6513607 style='height:14.25pt;border-top:none'>" + dataObj[j][i].name + "</td>\n" + 
							" <td class=xl6613607 style='border-top:none;border-left:none'>" + dataObj[j][i].涉及金额 + "</td>\n" + 
							" <td class=xl6613607 style='border-top:none;border-left:none'>" + dataObj[j][i].案值 + "</td>\n" + 
							" <td class=xl6613607 style='border-top:none;border-left:none'>" + dataObj[j][i].挽回经济损失 + "</td>\n" + 
							"</tr>";

					}
				}
				
				htmls += '</table>' + '</div>';
			},
			 complete: function () {
			        $("body").loading("hide");
			    },
			error:function(e){
				jazz.log(e);
				$("body").loading("hide");
			}
		});
		var newWim = open("dj_of_money_down.jsp", "_blank");
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
		title="登记信息涉及金额统计表  统计条件">
		<div id="begintime" name='begintime' vtype="datefield" label="登记开始日期"
			labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="登记截止日期"
			labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="hbegintime" name='hbegintime' vtype="datefield"
			label="上期登记开始日期" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="hendtime" name='hendtime' vtype="datefield" label="上期登记截止日期"
			labelAlign="right" labelwidth='120px' width="410"></div>

		<div name='shejiketi' id="shejiketi" vtype="comboxfield"
			multiple="false" label="涉及客体类别" labelAlign="right"
			dataurl='[{"text":"工商","value":"CH20"},{"text":"消委会","value":"ZH18"},
					{"text":"知识产权","value":"ZH19"},{"text":"价格检查","value":"ZH20"},{"text":"质量监督","value":"ZH21"}]'
			labelwidth='120px' width="410"></div>

		<div name='infotype' id="infotype" vtype="comboxfield"
			multiple="false" label="信息件类型" labelAlign="right"
			dataurl='[{"text":"行政监察件","value":"8"},{"text":"市场监管投诉","value":"1"},{"text":"举报","value":"2"},{"text":"咨询","value":"3"},{"text":"建议","value":"4"},{"text":"消委会投诉","value":"6"},{"text":"其他","value":"9"}]'
			labelwidth='120px' width="410"></div>
		<div name='laiyuanfangshi' id="laiyuanfangshi" vtype="comboxfield"
			multiple="false" label="来源方式" labelAlign="right"
			dataurl='[{"text":"直接到咨询举报申诉中心","value":"01"},{"text":"维权联络点","value":"02"},{"text":"消协","value":"03"},{"text":"协作单位","value":"04"},{"text":"销售企业","value":"05"},{"text":"服务企业","value":"06"},{"text":"生产企业","value":"07"},{"text":"工商部门","value":"08"},{"text":"人大","value":"09"},{"text":"政协","value":"10"},{"text":"其他行政部门","value":"11"},{"text":"其他","value":"99"}]'
			labelwidth='120px' width="410"></div>
		<div name='jieshoufangshi' id="jieshoufangshi" vtype="comboxfield"
			multiple="false" label="接收方式" labelAlign="right"
			dataurl='[{"text":"电话(语音)","value":"1"},{"text":"短信","value":"2"},{"text":"来人","value":"3"},{"text":"来函","value":"4"},{"text":"传真","value":"5"},{"text":"互联网络","value":"6"},{"text":"留言","value":"7"},{"text":"市府12345转件","value":"8"},{"text":"其他","value":"9"},{"text":"政府在线","value":"a"},{"text":"电子邮件","value":"b"},{"text":"直通车","value":"c"},{"text":"来信","value":"d"},{"text":"上级部门交办","value":"e"},{"text":"民心桥","value":"f"},{"text":"其它部门转办","value":"g"},{"text":"三打两建办","value":"h"},{"text":"局长邮箱","value":"i"},{"text":"异地消费申诉转办件","value":"j"},{"text":"QQ服务","value":"k"},{"text":"12358全国价格举报平台来件","value":"l"},{"text":"微信平台","value":"m"},{"text":"315消费通","value":"n"},{"text":"消委会转件","value":"o"},{"text":"全国12315平台转件","value":"p"}]'
			labelwidth='120px' width="410"></div>
		<div name='shijianjibie' id="shijianjibie" vtype="comboxfield"
			multiple="false" label="事件级别" labelAlign="right"
			dataurl='[{"text":"特急","value":"1"},
						{"text":"紧急","value":"2"},
						{"text":"一般","value":"3"}]'
			labelwidth='120px' width="410"></div>
		<div name='renyuanshenfen' id="renyuanshenfen" vtype="comboxfield"
			multiple="false" label="人员身份" labelAlign="right"
			dataurl='[{"text":"城镇","value":"1"},
						{"text":"农村","value":"2"},
						{"text":"港澳台同胞","value":"3"},
						{"text":"外藉","value":"4"},
						{"text":"军人","value":"5"},
						{"text":"其他","value":"9"}]'
			labelwidth='120px' width="410"></div>

		<div id='zhutileixing' name='zhutileixing' vtype="comboxtreefield"
			label="涉及主体类型" labelalign="right" multiple="true" labelwidth='120px'
			width="410"></div>

		<div id='hangyeleixing' name='hangyeleixing' vtype="comboxtreefield"
			label="行业类型" labelalign="right" multiple="true" labelwidth='120px'
			width="410"></div>
		<div name='qiyeleixing' id="qiyeleixing" vtype="comboxtreefield"
			multiple="true" label="企业类型" labelAlign="right" labelwidth='120px'
			width="410"></div>
		<div name='wangzhanleixing' id="wangzhanleixing" vtype="comboxfield"
			multiple="false" label="网站类型" labelAlign="right"
			dataurl='[{"text":"交易平台类","value":"01"},{"text":"应用类","value":"02"},{"text":"服务类","value":"03"},{"text":"互联网门户","value":"04"},{"text":"其他","value":"05"}]'
			labelwidth='120px' width="410"></div>
		<div name='guanjianzi' id="guanjianzi" vtype="comboxfield"
			multiple="false" label="关键字" labelAlign="right" labelwidth='120px'
			width="410"></div>

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="查询"
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>