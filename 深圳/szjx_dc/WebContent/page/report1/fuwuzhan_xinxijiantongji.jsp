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
		/* $('#yeWuLeiBie').comboxfield('addOption', "全部","");
		$('#yeWuLeiBie').comboxfield('addOption', "工商","CH20");
		$('#yeWuLeiBie').comboxfield('addOption', "消委会","ZH18");
		$('#yeWuLeiBie').comboxfield('addOption', "知识产权","ZH19");
		$('#yeWuLeiBie').comboxfield('addOption', "价格检查","ZH20");
		$('#yeWuLeiBie').comboxfield('addOption', "质量监督","ZH21"); */
		
	/* 	$.ajax({
			type : "post",
			url : rootPath + "/quert12315Controller/getRegCode.do",
			data : "begintime=" + begintime + "&endtime=" + endtime+"&regcode="+regcode
					+ "&timess=" + new Date() + "&id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var  i = 0; i < dataObj.length; i++) {
					$('#regcode').comboxfield('addOption', dataObj[i].name, dataObj[i].regdepcode);
				}
				}
		}); */
	});
	
	function isEmpty( time1, time2){
		var b=false;
		if (time1.length!=0&&time2.length==0) {
			b=false;
		}else if(time1.length==0&&time2.length!=0){
			b=false;
		}else{
			b=true;
		}
		return b;
	}
	
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		
		var paifakaishi=aa.data.paifakaishi;
		var paifajieshu=aa.data.paifajieshu;
		var jiedankaishi=aa.data.jiedankaishi;
		var jiedanjieshu=aa.data.jiedanjieshu;
		var huifukaishi=aa.data.huifukaishi;
		var huifujieshu=aa.data.huifujieshu;
		var banjiekaishi=aa.data.banjiekaishi;
		var banjiejieshu=aa.data.banjiejieshu;
		var shensuren=aa.data.shensuren;
		var shensuduixiang=aa.data.shensuduixiang;
		var jingbanren=aa.data.jingbanren;
		var tiaojiechenggong=aa.data.tiaojiechenggong;
		if (paifakaishi.length != 0 && paifajieshu.length != 0) {
			if (paifakaishi > paifajieshu) {
				alert("分派初始日期大于分派截止日期");
				return;
			}
		}
		/* if (isEmpty(paifakaishi,paifajieshu)) {
			alert("分派时间段需要填写开始时间和截止时间，或者不用选填分派时间！");
			return;
		} */
		
		if (jiedankaishi.length != 0 && jiedanjieshu.length != 0) {
			if (jiedankaishi > jiedanjieshu) {
				alert("接单初始日期大于接单截止日期");
				return;
			}
		}
		/* if (isEmpty(jiedankaishi,jiedanjieshu)) {
			alert("接单时间段需要填写开始时间和截止时间，或者不用选填接单时间！");
			return ;
		} */
		
		if (huifukaishi.length != 0 && huifujieshu.length != 0) {
			if (huifukaishi > huifujieshu) {
				alert("回复初始日期大于回复截止日期");
				return;
			}
		}
/* 		if (isEmpty(huifukaishi,huifujieshu)) {
			alert("回复时间段需要填写开始时间和截止时间，或者不用选填回复时间！");
			return ;
		} */
		
		if (banjiekaishi.length != 0 && banjiejieshu.length != 0) {
			if (banjiekaishi > banjiejieshu) {
				alert("办结初始日期大于办结截止日期");
				return;
			}
		}
		/* if (isEmpty(banjiekaishi,banjiejieshu)) {
			alert("办结时间段需要填写开始时间和截止时间，或者不用选填办结时间！");
			return ;
		} */
	
	var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' 
				+ '\'' + paifakaishi + '\'' + ',' 
				+ '\'' + paifajieshu + '\'' + ',' 
				+ '\'' + jiedankaishi + '\'' +','
				+ '\'' + jiedanjieshu + '\'' +','
				+ '\'' + huifukaishi + '\'' +','
				+ '\'' + huifujieshu + '\'' +','
				+ '\'' + banjiekaishi + '\'' +','
				+ '\'' + banjiejieshu + '\'' +','
				+ '\'' + shensuren + '\'' +','
				+ '\'' + shensuduixiang + '\'' +','
				+ '\'' + jingbanren + '\'' +','
				+ '\'' + tiaojiechenggong + '\'' 
				+ ')" > 下     载</a>' + '</div>' + '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' 
				+ "<div id=\"信息件统计表_30581\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1029 class=xl6330581\n" + 
				" style='border-collapse:collapse;table-layout:fixed;width:774pt'>\n" + 
				" <col class=xl6330581 width=58 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 1856;width:44pt'>\n" + 
				" <col class=xl6330581 width=80 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 2560;width:60pt'>\n" + 
				" <col class=xl6330581 width=120 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 3840;width:90pt'>\n" + 
				" <col class=xl6330581 width=72 style='width:54pt'>\n" + 
				" <col class=xl6330581 width=90 span=4 style='mso-width-source:userset;\n" + 
				" mso-width-alt:2880;width:68pt'>\n" + 
				" <col class=xl6330581 width=103 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 3296;width:77pt'>\n" + 
				" <col class=xl6330581 width=109 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 3488;width:82pt'>\n" + 
				" <col class=xl6330581 width=127 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 4064;width:95pt'>\n" + 
				" <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n" + 
				"  <td colspan=11 height=40 class=xl6430581 width=1029 style='height:30.0pt;\n" + 
				"  width:774pt'>服务站-信息件统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6630581 style='height:14.25pt;border-top:none'>编号</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>申诉人</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>申诉对象</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>申诉内容</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>派发时间</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>接单时间</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>回复时间</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>办结时间</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>服务站经办人</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>是否调解成功</td>\n" + 
				"  <td class=xl6630581 style='border-top:none;border-left:none'>涉及消费金额</td>\n" + 
				" </tr>";

		$.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/fuWuZhanXinXiJian.do",
			/* 			data : "begintime=" + begintime + "&endtime=" + endtime + "&infotype=" + infotype + "&yeWuLeiBie=" + yeWuLeiBie + "&timess=" + new Date() + "&id=" + Math.random(),
			 */data : {
				"paifakaishi" : paifakaishi,
				"paifajieshu" : paifajieshu,
				"jiedankaishi" : jiedankaishi,
				"jiedanjieshu" : jiedanjieshu,
				"huifukaishi" : huifukaishi,
				"huifujieshu" : huifujieshu,
				"banjiekaishi" : banjiekaishi,
				"banjiejieshu" : banjiejieshu,
				"shensuren" : shensuren,
				"shensuduixiang" : shensuduixiang,
				"jingbanren" : jingbanren,
				"tiaojiechenggong" : tiaojiechenggong
			},
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var i = 0, t = dataObj.length; i < t; i++) {

						htmls += "<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6730581 style='height:14.25pt;border-top:none'>"+dataObj[i].regino+"</td>\n" + 
							"  <td class=xl6730581 style='border-top:none;border-left:none'>"+ dataObj[i].persname+"</td>\n" + 
							"  <td class=xl6830581 width=120 style='border-top:none;border-left:none;\n" + 
							"  width:90pt'>"+ dataObj[i].invname+"</td>\n" + 
							"  <td class=xl6830581 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+ dataObj[i].content+"</td>\n" + 
							"  <td class=xl6930581 style='border-top:none;border-left:none'>"+ dataObj[i].transtime+"</td>\n" + 
							"  <td class=xl7030581 style='border-top:none;border-left:none'>"+ dataObj[i].receivetime+"</td>\n" + 
							"  <td class=xl7030581 style='border-top:none;border-left:none'>"+ dataObj[i].replytime+"</td>\n" + 
							"  <td class=xl7030581 style='border-top:none;border-left:none'>"+ dataObj[i].finishtime+"</td>\n" + 
							"  <td class=xl7030581 style='border-top:none;border-left:none'>"+ dataObj[i].operator+"</td>\n" + 
							"  <td class=xl7030581 style='border-top:none;border-left:none'>"+ dataObj[i].success+"</td>\n" + 
							"  <td class=xl7030581 style='border-top:none;border-left:none'>"+ dataObj[i].amountmoney+"</td>\n" + 
							" </tr>";
				}
				htmls += '</table>' + '</div>';
			}
		});
		var newWim = open("fuwuzhan_xinxijiantongji_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 500);

	}
</script>

</head>
<body >
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="服务站信息件统计表  统计条件">
		<div id="paifakaishi" name='paifakaishi' vtype="datefield"
			label="派发开始日期" labelAlign="right" labelwidth='100px' width="410"></div>
		<div id="paifajieshu" name='paifajieshu' vtype="datefield" label="派发截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
			
		<div id="jiedankaishi" name='jiedankaishi' vtype="datefield"
			label="接单开始日期" labelAlign="right" labelwidth='100px' width="410"></div>
		<div id="jiedanjieshu" name='jiedanjieshu' vtype="datefield" label="接单截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
			
		<div id="huifukaishi" name='huifukaishi' vtype="datefield"
			label="回复开始日期" labelAlign="right" labelwidth='100px' width="410"></div>
		<div id="huifujieshu" name='huifujieshu' vtype="datefield" label="回复截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
			
		<div id="banjiekaishi" name='banjiekaishi' vtype="datefield"
			label="办结开始日期" labelAlign="right" labelwidth='100px' width="410"></div>
		<div id="banjiejieshu" name='banjiejieshu' vtype="datefield" label="办结截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
			
		<div name='shensuren' id="shensuren" vtype="textfield"
			multiple="false" label="申诉人" labelAlign="right"
			labelwidth='100px' width="410"></div>
			
		<div name='shensuduixiang' id="shensuduixiang" vtype="textfield"
			multiple="false" label="申诉对象" labelAlign="right"
			labelwidth='100px' width="410"></div>
			
		<div name='jingbanren' id="jingbanren" vtype="textfield"
			multiple="false" label="服务站经办人" labelAlign="right"
			labelwidth='100px' width="410"></div>
			
		
		<div name='tiaojiechenggong' id="tiaojiechenggong" vtype="comboxfield"
			multiple="false" label="是否调解成功" labelAlign="right"
			dataurl='[{"text":"调解成功","value":"1"},{"text":"调解失败","value":"0"}]'
			labelwidth='100px' width="410"></div>
		
						
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