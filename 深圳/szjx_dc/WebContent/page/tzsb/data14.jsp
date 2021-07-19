<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>深圳市在用特种设备统计表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<style type="text/css">
</style>


</head>
<body>
	<div name="CPRQueryListPanel" id="formpanel" vtype="formpanel" width="100%" layout="table" style="text-align: center;" layoutconfig="{cols:2, columnwidth: ['50%','50%']}" labelstyleclass="labelstyle" title="特种设备施工告知受理情况统计查询" titledisplay="true" showborder="false" height="100%">


		<div name="tjxm" vtype="comboxfield"  label="统计项目" labelwidth="150" labelalign="right" width="400" valuetip="请选择统计项目……"  tooltip="" defaultvalue="施工告知统计(按受理单位)"></div><!-- dataurl='json/data14_gzlx.json' -->


		<div name="sggzlx" vtype="comboxfield" label="施工告知类型" labelwidth="150" labelalign="right" width="400" valuetip="请选择施工告知类型……"  tooltip=""></div><!-- dataurl='json/data14_sbsggz.json' -->


		<!-- <div name="sldw" vtype="comboxfield" label="受理单位" labelwidth="150" labelalign="right" width="400" valuetip="请选择受理单位……" dataurl='json/data14_sldw.json' tooltip="" rule="must"></div> -->


		<div name="sltime" lable="施工告知时间" labelwidth="0" labelalign="right" width="400" style="margin-left: 60px;">
			<div name="sltime_begin" vtype="datefield" label="施工告知时间" labelwidth="90" labelalign="right" width="205" valuetip="受理开始时间……" tooltip="" rule=""></div>

			<span id="toDates" style="text-align: center; height: 48px; line-height: 63px; margin-left: -11px; margin-right: -11px; color: #666; font-size: 12px">至</span>
			<div name="sltime_end" vtype="datefield" labelwidth="0" labelalign="right" width="110" valuetip="施工告知结束时间……" tooltip="" rule=""></div>
		</div>



		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="统计" icon="../query/queryssuo.png" onclick="queryUrl(this);"></div>
			<div name="reset_button" vtype="button" text="重置" icon="../query/queryssuo.png" click="reset();"></div>
		</div>


	</div>


	<script type="text/javascript" charset="UTF-8">
	
	
	
	
	
	$(function() {

		getcode();
		slType();
		function getcode() {
			$.ajax({
				type : "post",
				url : rootPath + "/data14/tjxm_code_value.do",
				data : "randan" + Math.random(),
				dataType : "text",
				async : false,
				cach : false,
				success : function(data) {
					var data = eval("(" + data + ")");
					var dataStr = data.data[0].data;
					for ( var i in dataStr) {
						$("div[name='tjxm']").comboxfield('addOption', dataStr[i].text, dataStr[i].value);
					}
				}
			});
		}
		
		
		
		function slType(){
			
			$.ajax({
				type : "post",
				url : rootPath + "/data14/sggzlx_code_value.do",
				data : "randan" + Math.random(),
				dataType : "text",
				async : false,
				cach : false,
				success : function(data) {
					var data = eval("(" + data + ")");
					var dataStr = data.data[0].data;
					for ( var i in dataStr) {
						$("div[name='sggzlx']").comboxfield('addOption', dataStr[i].text, dataStr[i].value);
					}
				}
			});
			
		} 

	});
	
	
	
	
	
		function reset() {
			$("#formpanel").formpanel('reset');
		}

		function queryUrl(obj) {
			var _this = $(obj);
			$("div[name='query_button']").button('disable');
			_this.removeAttr("onclick");
			_this.find("div.button-text ").html("查询中");
			var aa = $("#formpanel").formpanel('getValue');
			var htmls = "";
			var tjxm = aa.data.tjxm;
			var sggzlx = aa.data.sggzlx;
			var spBegTime = aa.data.sltime_begin;
			
			
			var spEndTime = aa.data.sltime_end;

			
			
			if (tjxm == "施工告知统计(按受理单位)") {

				htmls += "<a href='"+rootPath+"/data14/downExcelByDw.do'>下载</a>";
				htmls += "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>";
				htmls += "<tr><td colspan='23'><h1>特种设备施工告知受理情况统计查询(按受理单位)</h1></td></tr>";
				htmls += "<tr>";
				htmls += "<td rowspan='2'>辖区</td>";
				htmls += "<td colspan='2'>合计宗数</td>";
				htmls += "<td colspan='2'>锅炉</td>";
				htmls += "<td colspan='2'>压力容器</td>";
				htmls += "<td colspan='2'>电梯</td>";
				htmls += "<td colspan='2'>起重机械</td>";
				htmls += "<td colspan='2'>厂内机动车辆</td>";
				htmls += "<td colspan='2'>大型游乐设施</td>";
				htmls += "<td colspan='2'>压力管道</td>";
				htmls += "<td colspan='2'>客运索道</td>";
				htmls += "</tr>";
				htmls += "<tr>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "</tr>";

				$.ajax({
					type : "post",
					url : rootPath + "/data14/queryByDw.do",
					dataType : "text",
					async : false,
					cach : false,
					data : {
						"tjxm" : tjxm,
						"sggzlx" : sggzlx,
						"spBegTime" : spBegTime,
						"spEndTime" : spEndTime,
					},
					success : function(data) {
						$("div[name='query_button']").button('enable');
						_this.attr("onclick", "queryUrl(this);");
						_this.find(".button-text").html("查询");
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += "<tr>";
							htmls += "<td>" + dataObj[i].办理单位 + "</td>";
							htmls += "<td>" + dataObj[i].合计宗数 + "</td><td>"
									+ dataObj[i].合计台数 + "</td>";
							htmls += "<td>" + dataObj[i].锅炉宗数 + "</td><td>"
									+ dataObj[i].锅炉台数 + "</td>";
							htmls += "<td>" + dataObj[i].压力容器宗数 + "</td><td>"
									+ dataObj[i].压力容器台数 + "</td>";
							htmls += "<td>" + dataObj[i].电梯宗数 + "</td><td>"
									+ dataObj[i].电梯台数 + "</td>";
							htmls += "<td>" + dataObj[i].起重机械宗数 + "</td><td>"
									+ dataObj[i].起重机械台数 + "</td>";
							htmls += "<td>" + dataObj[i].场内机动车辆宗数 + "</td><td>"
									+ dataObj[i].场内机动车辆台数 + "</td>";
							htmls += "<td>" + dataObj[i].大型游乐设施宗数 + "</td><td>"
									+ dataObj[i].大型游乐设施台数 + "</td>";
							htmls += "<td>" + dataObj[i].压力管道宗数 + "</td><td>"
									+ dataObj[i].压力管道台数 + "</td>";
							htmls += "<td>" + dataObj[i].客运索道宗数 + "</td><td>"
									+ dataObj[i].客运索道台数 + "</td>";

							htmls += "</tr>";
						}

					}
				});
				htmls += "</table>";
				var newWim = open("down/data14_down.jsp", "_blank");

				window.setTimeout(function() {
					_this.find(".button-text").html("查询");
					newWim.document.body.innerHTML = htmls;
				}, 1000);

			} else {

				htmls += "<a href='"+rootPath+"/data14/downExcelByXiaQu.do'>下载</a>";
				htmls += "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>";
				htmls += "<tr><td colspan='23'><h1>特种设备施工告知受理情况统计查询(按辖区)</h1></td></tr>";
				htmls += "<tr>";
				htmls += "<td rowspan='2'>辖区</td>";
				htmls += "<td colspan='2'>合计宗数</td>";
				htmls += "<td colspan='2'>锅炉</td>";
				htmls += "<td colspan='2'>压力容器</td>";
				htmls += "<td colspan='2'>电梯</td>";
				htmls += "<td colspan='2'>起重机械</td>";
				htmls += "<td colspan='2'>厂内机动车辆</td>";
				htmls += "<td colspan='2'>大型游乐设施</td>";
				htmls += "<td colspan='2'>压力管道</td>";
				htmls += "<td colspan='2'>客运索道</td>";
				htmls += "</tr>";
				htmls += "<tr>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "<td>宗数</td><td>台数</td>";
				htmls += "</tr>";

				$.ajax({
					type : "post",
					url : rootPath + "/data14/queryByXiaQu.do",
					dataType : "text",
					async : false,
					cach : false,
					data : {
						"tjxm" : tjxm,
						"sggzlx" : sggzlx,
						"spBegTime" : spBegTime,
						"spEndTime" : spEndTime,
					},
					success : function(data) {
						$("div[name='query_button']").button('enable');
						_this.attr("onclick", "queryUrl(this);");
						_this.find(".button-text").html("查询");
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += "<tr>";
							htmls += "<td>" + dataObj[i].辖区 + "</td>";
							htmls += "<td>" + dataObj[i].合计宗数 + "</td><td>"
									+ dataObj[i].合计台数 + "</td>";
							htmls += "<td>" + dataObj[i].锅炉宗数 + "</td><td>"
									+ dataObj[i].锅炉台数 + "</td>";
							htmls += "<td>" + dataObj[i].压力容器宗数 + "</td><td>"
									+ dataObj[i].压力容器台数 + "</td>";
							htmls += "<td>" + dataObj[i].电梯宗数 + "</td><td>"
									+ dataObj[i].电梯台数 + "</td>";
							htmls += "<td>" + dataObj[i].起重机械宗数 + "</td><td>"
									+ dataObj[i].起重机械台数 + "</td>";
							htmls += "<td>" + dataObj[i].场内机动车辆宗数 + "</td><td>"
									+ dataObj[i].场内机动车辆台数 + "</td>";
							htmls += "<td>" + dataObj[i].大型游乐设施宗数 + "</td><td>"
									+ dataObj[i].大型游乐设施台数 + "</td>";
							htmls += "<td>" + dataObj[i].压力管道宗数 + "</td><td>"
									+ dataObj[i].压力管道台数 + "</td>";
							htmls += "<td>" + dataObj[i].客运索道宗数 + "</td><td>"
									+ dataObj[i].客运索道台数 + "</td>";

							htmls += "</tr>";
						}

					}
				});
				htmls += "</table>";
				var newWim = open("down/data14_down.jsp", "_blank");

				window.setTimeout(function() {
					_this.find(".button-text").html("查询");
					newWim.document.body.innerHTML = htmls;
				}, 1000);

			}
		}
	</script>
JDBC:ORACLE:THIN:@LOCALHOST:1521:ORCL
</body>
</html>