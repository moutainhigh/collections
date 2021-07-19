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
				$("#suoshubumen").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#suoshubumen").comboxtreefield("reload");
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
					url : rootPath + "/queryJieAn/getYeWuFanWeiTree.do",
					data : "id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						$("#yewufanwei").comboxtreefield('option',"dataurl",JSON.parse(data));
						$("#yewufanwei").comboxtreefield("reload");
						}
				}); 
			 
			 $.ajax({
					type : "post",
					url : rootPath + "/queryJieAn/getRenYuanShenFenSelect.do",
					data : "id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						$("#renyuanshenfen").comboxfield('option',"dataurl",JSON.parse(data));
						$("#renyuanshenfen").comboxfield("reload");
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
						$("#qiyeleixing").comboxtreefield('option',"dataurl",JSON.parse(data));
						$("#qiyeleixing").comboxtreefield("reload");
						}
				}); 
		/* $.ajax({
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
		}); */
		
		/* $.ajax({
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
		}); */
		
		
		/* $.ajax({
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
		}); */
		
	/* 	 $.ajax({
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
		});  */
		/*  $.ajax({
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
		});  */
		
		/* $.ajax({
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
		}); */
		/* $.ajax({
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
		}); */
		/*  $.ajax({
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
		});  */
		/* $.ajax({
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
		}); */
		/* $.ajax({
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
		}); */
		
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
		var infotype=aa.data.infotype;
		var dengjibumen=aa.data.dengjibumen;
		var chengbanbumen=aa.data.chengbanbumen;
		var jieshoufangshi=aa.data.jieshoufangshi;
		var suoshubumen=aa.data.suoshubumen;
		var wentileixing=aa.data.wentileixing;
		var yewufanwei=aa.data.yewufanwei;
		var renyuanshenfen=aa.data.renyuanshenfen;
		var hangyeleixing=aa.data.hangyeleixing;
		var qiyeleixing=aa.data.qiyeleixing;
		/* 
		var ketileixing=aa.data.ketileixing;
		var zhutileixing=aa.data.zhutileixing;
		var weifazhonglei=aa.data.weifazhonglei;
		var xingzhengchufa=aa.data.xingzhengchufa;
		//var zhixingleibie=aa.data.zhixingleibie;
		
		
		var wangzhanleixing=aa.data.wangzhanleixing;
		var qinquanleixing=aa.data.qinquanleixing;
		var yiwuleixing=aa.data.yiwuleixing;
		var chufazhonglei=aa.data.chufazhonglei; */
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
	
	var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' 
						+ '\'' + begintime     + '\'' + ',' 
						+ '\'' + endtime       + '\'' + ',' 
						+ '\'' + hbegintime    + '\'' + ',' 
						+ '\'' + hendtime      + '\'' + ',' 
						+ '\'' + infotype      + '\'' + ',' 
						+ '\'' + dengjibumen   + '\'' + ',' 
						+ '\'' + chengbanbumen + '\'' + ',' 
						+ '\'' + jieshoufangshi+ '\'' + ',' 
						+ '\'' + suoshubumen   + '\'' + ',' 
						+ '\'' + wentileixing  + '\'' + ',' 
						+ '\'' + yewufanwei    + '\'' + ',' 
						+ '\'' + renyuanshenfen+ '\'' + ',' 
						+ '\'' + hangyeleixing + '\'' + ',' 
						+ '\'' + qiyeleixing   + '\'' 
				
				+ ')" > 下     载</a>' + '</div>' + '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' 
				+
				"<div id=\"办结部门_28238\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1052 class=xl6328238\n" + 
				" style='border-collapse:collapse;table-layout:automatic;width:794pt'>\n" + 
				" <col class=xl6328238 width=152 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 4864;width:114pt'>\n" + 
				" <col class=xl6328238 width=90 span=10 style='mso-width-source:userset;\n" + 
				" mso-width-alt:2880;width:68pt'>\n" + 
				" <tr height=36 style='mso-height-source:userset;height:27.0pt'>\n" + 
				"  <td colspan=11 height=36 class=xl6428238 width=1052 style='height:27.0pt;\n" + 
				"  width:794pt'>咨询建议信息件涉及客体统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=38 style='height:28.5pt'>\n" + 
				"  <td height=38 class=xl6628238 width=152 style='height:28.5pt;border-top:none;\n" + 
				"  width:114pt'>承办部门</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>未回复</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>已回复</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>回访属实数</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>回访不属实数</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>合计</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>占总量（%）</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>上时段数据</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>比上时段同期增减（%）</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>去年同期数据</td>\n" + 
				"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>比去年同期增减（%）</td>\n" + 
				" </tr>";

		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/zj_SheJiKeTi.do",
			data : {
				"begintime"		  : begintime      ,
		        "endtime"         : endtime        ,
		        "hbegintime"      : hbegintime     ,
		        "hendtime"        : hendtime       ,
		        "infotype"        : infotype       ,
		        "dengjibumen"     : dengjibumen    ,
		        "chengbanbumen"   : chengbanbumen  ,
		        "jieshoufangshi"  : jieshoufangshi ,
		        "suoshubumen"     : suoshubumen    ,
		        "wentileixing"    : wentileixing   ,
		        "yewufanwei"      : yewufanwei     ,
		        "renyuanshenfen"  : renyuanshenfen ,
		        "hangyeleixing"   : hangyeleixing  ,
		        "qiyeleixing"     : qiyeleixing    
			},
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				if (data==null||data=="null"||data.length==0) {
					alert("无数据");
					return;
				}
				var dataObj = eval("(" + data + ")");
				for (var i = 0, t = dataObj.length; i < t; i++) {
					var datares=dataObj[i];
					for (var j = 0; j < datares.length; j++) {
						htmls +=

							"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6828238 style='height:14.25pt;border-top:none'>"+dataObj[i][j].name+"</td>\n" + 
							"  <td class=xl6728238 style='border-top:none;border-left:none'>"+dataObj[i][j].weihuifu+"</td>\n" + 
							"  <td class=xl6728238 style='border-top:none;border-left:none'>"+dataObj[i][j].yihuifu+"</td>\n" + 
							"  <td class=xl6728238 style='border-top:none;border-left:none'>"+dataObj[i][j].shushi+"</td>\n" + 
							"  <td class=xl6728238 style='border-top:none;border-left:none'>"+dataObj[i][j].bushushi+"</td>\n" + 
							"  <td class=xl6728238 style='border-top:none;border-left:none'>"+dataObj[i][j].heji+"</td>\n" + 
							"  <td class=xl6928238 style='border-top:none;border-left:none'>"+dataObj[i][j].zhanbi+"</td>\n" + 
							"  <td class=xl6728238 style='border-top:none;border-left:none'>"+dataObj[i][j].huanbishu+"</td>\n" + 
							"  <td class=xl6928238 style='border-top:none;border-left:none'>"+dataObj[i][j].huanbi+"</td>\n" + 
							"  <td class=xl6728238 style='border-top:none;border-left:none'>"+dataObj[i][j].tongbishu+"</td>\n" + 
							"  <td class=xl6928238 style='border-top:none;border-left:none'>"+dataObj[i][j].tongbi+"</td>\n" + 
							" </tr>";
					}
					if (i==t-1) {
						
					}else{
						htmls +=
							"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6828238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6928238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6928238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
							"  <td class=xl6928238 style='border-right:none;border-left:none'></td>\n" + 
							" </tr>";
						}
				}
				
			htmls += '</table>' + '</div>';
				 var newWim = open("zixunjianyi_shejiketi_down.jsp", "_blank");
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
		title="结案信息咨询建议涉及客体报表 统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="登记开始日期" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="登记截止日期"
			labelAlign="right" labelwidth='120px' width="410"></div>
			
		<div id="hbegintime" name='hbegintime' vtype="datefield"
			label="上期登记开始日期" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="hendtime" name='hendtime' vtype="datefield" label="上期登记截止日期"
			labelAlign="right" labelwidth='120px' width="410"></div>
	    
	    <div name='infotype' id="infotype" vtype="comboxfield"  multiple="false"
		 label="类型" labelAlign="right" dataurl='[ {"text":"咨询", "value":"3" },{"text":"建议", "value":"4" }]'
		  labelwidth='120px' width="410" ></div>
		 
		 
		<div id='dengjibumen' name='dengjibumen' vtype="comboxtreefield" 
			label="登记部门" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='chengbanbumen' name='chengbanbumen' vtype="comboxtreefield" 
			label="承办部门" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
	    <div name='jieshoufangshi' id="jieshoufangshi" vtype="comboxfield"  multiple="false"
		 label="接收方式" labelAlign="right" labelwidth='120px' width="410" ></div>
		 <div id='suoshubumen' name='suoshubumen' vtype="comboxtreefield" 
			label="咨询所属部门" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='wentileixing' name='wentileixing' vtype="comboxtreefield" 
			label="咨询问题" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
			
		<div id='yewufanwei' name='yewufanwei' vtype="comboxtreefield" 
			label="业务范围" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='renyuanshenfen' name='renyuanshenfen' vtype="comboxfield" 
			label="投诉方人员身份" labelalign="right" multiple="false" labelwidth='120px' width="410"  ></div>
		<div id='hangyeleixing' name='hangyeleixing' vtype="comboxtreefield" 
			label="被诉方行业类型" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
		<div id='qiyeleixing' name='qiyeleixing' vtype="comboxtreefield" 
			label="被诉方企业类型" labelalign="right" multiple="true" labelwidth='120px' width="410"  ></div>
			
		<!-- <div name='xingzhengchufa' id="xingzhengchufa" vtype="comboxfield"  multiple="false"
		 label="行政处罚措施" labelAlign="right" labelwidth='120px' width="410" ></div> 
		<div name='zhixingleibie' id="zhixingleibie" vtype="comboxfield"  multiple="false"
		 label="执行类别" labelAlign="right" labelwidth='120px' width="410" ></div> 
		
		
		<div name='wangzhanleixing' id="wangzhanleixing" vtype="comboxfield"  multiple="false" 
		label="网站类型" labelAlign="right" labelwidth='120px' width="410" ></div>
		<div name='qinquanleixing' id="qinquanleixing" vtype="comboxfield"  multiple="false" 
		label="侵权类型" labelAlign="right" labelwidth='120px' width="410" ></div>
		<div name='yiwuleixing' id="yiwuleixing" vtype="comboxfield"  multiple="false" 
		label="未履行义务" labelAlign="right" labelwidth='120px' width="410" ></div> 
		<div name='chufazhonglei' id="chufazhonglei" vtype="comboxfield"  multiple="false" 
		label="处罚种类" labelAlign="right" labelwidth='120px' width="410" ></div>
		 -->
		
		
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