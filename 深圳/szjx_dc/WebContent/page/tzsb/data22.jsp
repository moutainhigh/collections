<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
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
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}

	$(function() {
		$("div[name='tjlx']").radiofield("option", "change", changeType);//统计类型发生变化时触发
		$(".jazz-checkbox-item").css("width", "150px");
		getcode();

		
		
		$("#tjlx").find(".jazz-checkbox-label,.jazz-checkbox").click(function(){
			var _this = $(this);
			var index  = _this.parents(".jazz-checkbox-item").attr("index");
			if(index==1){
				$("#btnExport").addClass("display");
			}else{
				$("#btnExport").removeClass("display");
			}
		});
		
		
		
		function getcode() {
			$("div[name='sbjgs']").comboxfield("option", "disabled", true);
			$("div[name='xq']").comboxfield("option", "change", getAdminbrancode);
			$.ajax({
				type : "post",
				url : rootPath + "/tzsb/code_value.do",
				data : "randan" + Math.random(),
				dataType : "text",
				async : false,
				cach : false,
				success : function(data) {
					var data = eval("(" + data + ")");
					var dataStr = data.data[0].data;
					for ( var i in dataStr) {
						$("div[name='xq']").comboxfield('addOption', dataStr[i].text, dataStr[i].value);
					}
				}
			});
			
			// 取得设备状态
			$.ajax({
				type : "post",
				url : rootPath + "/data22/sldw_code_value.do",
				data : "randan" + Math.random(),
				dataType : "text",
				async : false,
				cach : false,
				success : function(data) {
					var data = eval("(" + data + ")");
					var dataStr = data.data[0].data;
					for ( var i in dataStr) {
						$("div[name='sbStatus']").comboxfield('addOption', dataStr[i].text, dataStr[i].value);
					}
				}
			});
		}

	});

	function getAdminbrancode() {

		$("div[name='sbjgs']").comboxfield("option", "disabled", true);
		var text = $('div[name="xq"]').comboxfield('getValue');
		if (text != "") {
			$("div[name='sbjgs']").comboxfield("option", "disabled", false);
			$("div[name='sbjgs']").comboxfield("option", "dataurl", "../../tzsb/adminbrancode.do?code=" + text)
			$("div[name='sbjgs']").comboxfield("reload");
		}

	}
	
	
	
	function changeType(){
		var text = $('div[name="tjlx"]').comboxfield('getValue');
		if(text==1){
			$("div[name='sblb']").comboxfield("option", "disabled", true);
		}else{
			$("div[name='sblb']").comboxfield("option", "disabled", false);
		}
	}
	
	
	
	

	function queryUrl(obj) {
		var _this = $(obj);

		var aa = $("#formpanel").formpanel('getValue');
		var htmls = "";

		var regCode = aa.data.xq;
		var adminbrancode = aa.data.sbjgs;
		var sheBeiLeiBie = aa.data.sblb;

		var type = aa.data.tjlx;//得到统计类型
		if (type == 0) {
			_this.find(".button-text").html("查询中");
			htmls += "<a href='"+rootPath+"/data22/downExcelByChangSuo.do'>下载</a>";
			htmls += "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>" + "<tr><td colspan='13'><h1>设备所在场所的统计查询(按设备所在区域统计)</h1></td></tr>";
			htmls += "<tr><td>区域</td><td>车站</td><td>口岸</td><td>客运码头</td><td>商场</td><td>学校</td><td>幼儿园</td><td>医院</td><td>体育场馆</td><td>展览馆</td><td>公园</td><td>地铁</td><td>合计</td></tr>";
			var total1 = 0;
			var total2 = 0;
			var total3 = 0;
			var total4 = 0;
			var total5 = 0;
			var total6 = 0;
			var total7 = 0;
			var total8 = 0;
			var total9 = 0;
			var total10 = 0;
			var total11 = 0;
			var total12 = 0;
			$.ajax({
				type : "post",
				url : rootPath + "/data22/queryByChangSuo.do",
				dataType : "text",
				async : false,
				cach : false,
				data : {
					"regCode" : regCode,
					"adminbrancode" : adminbrancode,
					"sheBeiLeiBie" : sheBeiLeiBie
				},
				success : function(data) {
					_this.find(".button-text").html("查询");
					var dataObj = eval("(" + data + ")");
					for (var i = 0; i < dataObj.length; i++) {
						htmls += "<tr><td>" + dataObj[i].equAddrCountyCode + "</td><td>" + dataObj[i].车站 + "</td><td>" + dataObj[i].口岸 + "</td><td>" + dataObj[i].客运码头 + "</td>" + "<td>"
								+ dataObj[i].商场 + "</td><td>" + dataObj[i].学校 + "</td><td>" + dataObj[i].幼儿园 + "</td><td>" + dataObj[i].医院 + "</td><td>" + dataObj[i].体育场馆 + "</td><td>"
								+ dataObj[i].展览馆 + "</td><td>" + dataObj[i].公园 + "</td><td>" + dataObj[i].地铁 + "</td><td>" + dataObj[i].合计 + "</td>" + "</tr>";
								
						total1+=dataObj[i].车站;
						total2+=dataObj[i].口岸;
						total3+=dataObj[i].客运码头 ;
						total4+=dataObj[i].商场 ;
						total5+=dataObj[i].学校;
						total6+=dataObj[i].幼儿园;
						total7+=dataObj[i].医院;
						total8+=dataObj[i].体育场馆 ;
						total9+=dataObj[i].展览馆 ;
						total10+=dataObj[i].公园 ;
						total11+=dataObj[i].地铁 ;
						total12+=dataObj[i].合计;
					}
					
					htmls +="<tr><td>总计</td><td>"+total1+"</td><td>"+total2+"</td><td>"+total3+"</td><td>"+total4+"</td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td><td>"+total12+"</td></tr>";
				}
			});

			htmls += "</table>";
			var newWim = open("down/data22_changsuo_down.jsp", "_blank");
			window.setTimeout(function() {
				_this.find(".button-text").html("查询");
				newWim.document.body.innerHTML = htmls;
			}, 1000);
			
			
		} else {
			var total1 = 0;
			var total2 = 0;
			var total3 = 0;
			var total4 = 0;
			var total5 = 0;
			var total6 = 0;
			var total7 = 0;
			var total8 = 0;
			var total9 = 0;
			var total10 = 0;
			var total11 = 0;
			var total12 = 0;
			_this.find(".button-text").html("查询中");
			htmls += "<a href='"+rootPath+"/data22/downExcelByType.do'>下载</a>";
			htmls += "<table   border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>" + "<tr><td colspan='13'><h1>设备所属类别统计查询</h1></td></tr>";
			htmls += "<tr><td>设备类型</td><td>车站</td><td>口岸</td><td>客运码头</td><td>商场</td><td>学校</td><td>幼儿园</td><td>医院</td><td>体育场馆</td><td>展览馆</td><td>公园</td><td>地铁</td><td>合计</td></tr>";
			$.ajax({
				type : "post",
				url : rootPath + "/data22/queryByType.do",
				dataType : "text",
				async : false,
				cach : false,
				data : {
					"regCode" : regCode,
					"adminbrancode" : adminbrancode,
					"sheBeiLeiBie" : sheBeiLeiBie
				},
				success : function(data) {
					_this.find(".button-text").html("查询");
					var dataObj = eval("(" + data + ")");
					for (var i = 0; i < dataObj.length; i++) {
						htmls += "<tr><td>" + dataObj[i].equAddrCountyCode + "</td>" + "	<td>" + dataObj[i].车站 + "</td><td>" + dataObj[i].口岸 + "</td><td>" + dataObj[i].客运码头 + "</td><td>" + dataObj[i].商场+ "</td><td>"
								+ dataObj[i].学校+ "</td><td>" + dataObj[i].幼儿园 + "</td><td>" + dataObj[i].医院 + "</td><td>" + dataObj[i].体育场馆 + "</td><td>" + dataObj[i].展览馆 + "</td><td>" + dataObj[i].公园 + "</td><td>" + dataObj[i].地铁 + "</td><td>" + dataObj[i].合计 + "</td></tr>";
						
						total1+=dataObj[i].车站;
						total2+=dataObj[i].口岸;
						total3+=dataObj[i].客运码头 ;
						total4+=dataObj[i].商场 ;
						total5+=dataObj[i].学校;
						total6+=dataObj[i].幼儿园;
						total7+=dataObj[i].医院;
						total8+=dataObj[i].体育场馆 ;
						total9+=dataObj[i].展览馆 ;
						total10+=dataObj[i].公园 ;
						total11+=dataObj[i].地铁 ;
						total12+=dataObj[i].合计;
					}
				}
			});
			htmls +="<tr><td>总计</td><td>"+total1+"</td><td>"+total2+"</td><td>"+total3+"</td><td>"+total4+"</td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td><td>"+total12+"</td></tr>";
			htmls += "</table>";
			var newWim = open("down/data22_type_down.jsp", "_blank");
			window.setTimeout(function() {
				_this.find(".button-text").html("查询");
				newWim.document.body.innerHTML = htmls;
			}, 1000);
		}
	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%" title="设备所在场所的统计查询">


		<div name='tjlx' id='tjlx' vtype="radiofield" label="统计类型" labelAlign="right"
			dataurl="[{checked: true,value: '0',text: '设备所在区域统计'},{value: '1',text: '设备所属类别统计'}]" width="450" labelwidth='80'></div>


		<div id="xq" name='xq' vtype="comboxfield" label="辖区" labelAlign="right" labelwidth='100px' width="420" valuetip="请选择所属辖区……" dataurl=""></div>
		<div id="sbjgs" name='sbjgs' vtype="comboxfield" label="设备监管所" labelAlign="right" labelwidth='100px' width="420" valuetip="请选择所属监管所……"
			dataurl=""></div>
		<div id="sblb" name='sblb' vtype="comboxfield" label="设备类别" labelAlign="right" labelwidth='100px' width="420"></div>
		
		<div id="sbStatus" name='sbStatus' vtype="comboxfield" label="设备状态" labelAlign="right" labelwidth='100px' width="420"></div>


		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" icon="../query/queryssuo.png" onclick="queryUrl(this);"></div>
			<div name="reset_button" vtype="button" text="重置" icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>