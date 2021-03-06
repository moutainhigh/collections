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
<style type="text/css">
</style>
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	
	$(document).ready(function getcode(){
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getRegDepTree.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#dengjibumen").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#dengjibumen").comboxtreefield("reload");
				$("#chengbanbumen").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#chengbanbumen").comboxtreefield("reload");
				}
		});
		
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getWenTiLeiXingTree.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#wentileixing").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#wentileixing").comboxtreefield("reload");
				}
		});
		
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getKeTiLeiXingTree.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#ketileixing").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#ketileixing").comboxtreefield("reload");
				}
		});
		
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getZhuTiLeiXingTree.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#zhutileixing").comboxtreefield('option',"dataurl",JSON.parse(data));
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
				$("#hangyeleixing").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#hangyeleixing").comboxtreefield("reload");
				}
		});
		
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getWeiFaZhongLeiTree.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#weifazhonglei").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#weifazhonglei").comboxtreefield("reload");
				}
		});
		
		 $.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getXingZhengChuFaSelect.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#xingzhengchufa").comboxfield('option',"dataurl",JSON.parse(data));
				$("#xingzhengchufa").comboxfield("reload");
				}
		}); 
		/* $.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getZhiXingLeiBieSelect.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#zhixingleibie").comboxfield('option',"dataurl",JSON.parse(data));
				$("#zhixingleibie").comboxfield("reload");
				}
		}); */
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getJieShouFangShiSelect.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#jieshoufangshi").comboxfield('option',"dataurl",JSON.parse(data));
				$("#jieshoufangshi").comboxfield("reload");
				}
		});
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getWangZhanLeiXingSelect.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#wangzhanleixing").comboxfield('option',"dataurl",JSON.parse(data));
				$("#wangzhanleixing").comboxfield("reload");
				}
		});
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getQinQuanLeiXingSelect.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#qinquanleixing").comboxfield('option',"dataurl",JSON.parse(data));
				$("#qinquanleixing").comboxfield("reload");
				}
		});
		 $.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getYiWuLeiXingSelect.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#yiwuleixing").comboxfield('option',"dataurl",JSON.parse(data));
				$("#yiwuleixing").comboxfield("reload");
				}
		}); 
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getInfoTypeSelect.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#infotype").comboxfield('option',"dataurl",JSON.parse(data));
				$("#infotype").comboxfield("reload");
				}
		});
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/getChuFaZhongLeiSelect.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#chufazhonglei").comboxfield('option',"dataurl",JSON.parse(data));
				$("#chufazhonglei").comboxfield("reload");
				}
		});
		
		/* $.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/getStationType.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#combox_tree1").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#combox_tree1").comboxtreefield("reload");
				}
		}); */
	});
	
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var hbegintime=aa.data.hbegintime;
		var hendtime=aa.data.hendtime;
		var dengjibumen=aa.data.dengjibumen;
		var chengbanbumen=aa.data.chengbanbumen;
		var wentileixing=aa.data.wentileixing;
		var ketileixing=aa.data.ketileixing;
		var zhutileixing=aa.data.zhutileixing;
		var hangyeleixing=aa.data.hangyeleixing;
		var weifazhonglei=aa.data.weifazhonglei;
		var xingzhengchufa=aa.data.xingzhengchufa;
		//var zhixingleibie=aa.data.zhixingleibie;
		var jieshoufangshi=aa.data.jieshoufangshi;
		var infotype=aa.data.infotype;
		var wangzhanleixing=aa.data.wangzhanleixing;
		var qinquanleixing=aa.data.qinquanleixing;
		var yiwuleixing=aa.data.yiwuleixing;
		var chufazhonglei=aa.data.chufazhonglei;
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
	
	var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' 
				+ '\'' + begintime + '\'' + ',' 
				+ '\'' + endtime + '\'' + ',' 
				+ '\'' + hbegintime + '\'' + ',' 
				+ '\'' + hendtime + '\'' + ',' 
				+ '\'' + dengjibumen + '\'' + ',' 
				+ '\'' + chengbanbumen + '\'' + ',' 
				+ '\'' + wentileixing + '\'' + ',' 
				+ '\'' + ketileixing + '\'' + ',' 
				+ '\'' + zhutileixing + '\'' + ',' 
				+ '\'' + hangyeleixing + '\'' + ',' 
				+ '\'' + weifazhonglei + '\'' + ',' 
				+ '\'' + xingzhengchufa + '\'' + ',' 
				//+ '\'' + zhixingleibie + '\'' + ',' 
				+ '\'' + jieshoufangshi + '\'' + ',' 
				+ '\'' + infotype + '\'' + ',' 
				+ '\'' + wangzhanleixing + '\'' + ',' 
				+ '\'' + qinquanleixing + '\'' + ',' 
				+ '\'' + yiwuleixing + '\'' + ',' 
				+ '\'' + chufazhonglei + '\'' 
				+ ')" > ???     ???</a>' + '</div>' + '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' 
				+"<div id=\"??????????????????_19941\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1800 style='border-collapse:\n" + 
				" collapse;table-layout:automatic;width:1350pt'>\n" + 
				" <col width=288 style='mso-width-source:userset;mso-width-alt:9216;width:216pt'>\n" + 
				" <col width=72 span=21 style='width:54pt'>\n" + 
				" <tr height=27 style='height:20.25pt'>\n" + 
				"  <td colspan=22 height=27 class=xl6319941 width=1800 style='height:20.25pt;\n" + 
				"  width:1350pt'>????????????????????????????????????????????????</td>\n" + 
				" </tr>\n" + 
				" <tr height=38 style='height:28.5pt'>\n" + 
				"  <td height=38 class=xl6419941 width=288 style='height:28.5pt;border-top:none;\n" + 
				"  width:216pt'>??????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>?????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>?????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>???????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>?????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>??????????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>??????????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>??????????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>?????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>??????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>???????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>???????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>????????????</td>\n" + 
				" </tr>";


		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/zhiXingLeiBie.do",
			data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"hbegintime" : hbegintime,
				"hendtime" : hendtime,
				"dengjibumen" : dengjibumen,
				"chengbanbumen" : chengbanbumen,
				"wentileixing" : wentileixing,
				"ketileixing" : ketileixing,
				"zhutileixing" : zhutileixing,
				"hangyeleixing" : hangyeleixing,
				"weifazhonglei" : weifazhonglei,
				"xingzhengchufa" : xingzhengchufa,
				//"zhixingleibie" : zhixingleibie,
				"jieshoufangshi" : jieshoufangshi,
				"infotype" : infotype,
				"wangzhanleixing" : wangzhanleixing,
				"qinquanleixing" : qinquanleixing,
				"yiwuleixing" : yiwuleixing,
				"chufazhonglei" : chufazhonglei
				
			},
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				if (data==null||data=="null"||data.length==0) {
					alert("?????????");
					return;
				}
				var dataObj = eval("(" + data + ")");
				for (var i = 0, t = dataObj.length; i < t; i++) {
					htmls +=
						"<tr height=19 style='height:14.25pt'>\n" +
						" <td height=19 class=xl6519941 style='height:14.25pt;border-top:none'>"+dataObj[i].name+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].subnum+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].shushi+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].tiaojie+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].qizha+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].zhengyijine+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].wanhuijingjisunshi+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].jiabeipeichangjine+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].jingshenpeichangjine+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].lianshu+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].anzhi+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].weifasuode+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].chufajine+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].moshoujine+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].bianjiajine+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].jingjisunshizhi+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].daohuiwodian+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].chudongrenshu+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].chudongcheliang+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].shoushangrenshu+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].shouhairenshu+"</td>\n" + 
						" <td class=xl6619941 style='border-top:none;border-left:none'>"+dataObj[i].xishengrenshu+"</td>\n" + 
						"</tr>";
				}
			htmls += '</table>' + '</div>';
				 var newWim = open("shensujubao_zhixingleibie_down.jsp", "_blank");
					window.setTimeout(function() {
						newWim.document.body.innerHTML = htmls;
					}, 500); 
			}
		});
		
	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="?????????????????????????????????????????? ????????????">
		<div id="begintime" name='begintime' vtype="datefield"
			label="??????????????????" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="??????????????????"
			labelAlign="right" labelwidth='120px' width="410"></div>
			
		<div id="hbegintime" name='hbegintime' vtype="datefield"
			label="????????????????????????" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="hendtime" name='hendtime' vtype="datefield" label="????????????????????????"
			labelAlign="right" labelwidth='120px' width="410"></div>
			
		<div id='dengjibumen' name='dengjibumen' vtype="comboxtreefield" 
			label="????????????" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='chengbanbumen' name='chengbanbumen' vtype="comboxtreefield" 
			label="????????????" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='wentileixing' name='wentileixing' vtype="comboxtreefield" 
			label="??????????????????" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='ketileixing' name='ketileixing' vtype="comboxtreefield" 
			label="??????????????????" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='zhutileixing' name='zhutileixing' vtype="comboxtreefield" 
			label="??????????????????" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='hangyeleixing' name='hangyeleixing' vtype="comboxtreefield" 
			label="????????????" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='weifazhonglei' name='weifazhonglei' vtype="comboxtreefield" 
			label="??????????????????" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
			
		<div name='xingzhengchufa' id="xingzhengchufa" vtype="comboxfield"  multiple="false"
		 label="??????????????????" labelAlign="right" labelwidth='120px' width="410" ></div> 
		<!-- <div name='zhixingleibie' id="zhixingleibie" vtype="comboxfield"  multiple="false"
		 label="????????????" labelAlign="right" labelwidth='120px' width="410" ></div> -->
		<div name='jieshoufangshi' id="jieshoufangshi" vtype="comboxfield"  multiple="false"
		 label="????????????" labelAlign="right" labelwidth='120px' width="410" ></div>
		<div name='infotype' id="infotype" vtype="comboxfield"  multiple="false"
		 label="???????????????" labelAlign="right" labelwidth='120px' width="410" ></div>
		<div name='wangzhanleixing' id="wangzhanleixing" vtype="comboxfield"  multiple="false" 
		label="????????????" labelAlign="right" labelwidth='120px' width="410" ></div>
		<div name='qinquanleixing' id="qinquanleixing" vtype="comboxfield"  multiple="false" 
		label="????????????" labelAlign="right" labelwidth='120px' width="410" ></div>
		<div name='yiwuleixing' id="yiwuleixing" vtype="comboxfield"  multiple="false" 
		label="???????????????" labelAlign="right" labelwidth='120px' width="410" ></div> 
		<div name='chufazhonglei' id="chufazhonglei" vtype="comboxfield"  multiple="false" 
		label="????????????" labelAlign="right" labelwidth='120px' width="410" ></div>
		
		
		
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