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
	$(document).ready(function getcode() {
		
		//初始化，防止空的下拉框出现
		$("div[name='tjlx']").comboxfield('addOption','辖区设备运行状态',"0");
		$("div[name='tjlx']").comboxfield('addOption','设备品种运行状态',"1");
		$("div[name='tjlx']").comboxfield('option', 'defaultvalue', 0);
		//$("div[name='tjlx']").comboxfield("option", "change", changeType);//统计类型发生变化时触发
		$("div[name='sblb']").comboxfield("option", "disabled", true);
		
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
		
		
		
		
	});

	function changeType(){
		var text = $('div[name="tjlx"]').comboxfield('getValue');
		if(text==0){
			$("div[name='sblb']").comboxfield("option", "disabled", true);
			$("div[name='xq']").comboxfield("option", "disabled", false);
			$("div[name='sbjgs']").comboxfield("option", "disabled", false);
		}else{
			$("div[name='sblb']").comboxfield("option", "disabled", false);
			$("div[name='xq']").comboxfield("option", "disabled", true);
			$("div[name='sbjgs']").comboxfield("option", "disabled", true);
			
		}
	}
	
	//获取监管所---暂无
	function getAdminbrancode() {

		$("div[name='sbjgs']").comboxfield("option", "disabled", true);
		var text = $('div[name="xq"]').comboxfield('getValue');
		if (text != "") {
			$("div[name='sbjgs']").comboxfield("option", "disabled", false);
			$("div[name='sbjgs']").comboxfield("option", "dataurl", "../../tzsb/adminbrancode.do?code=" + text)
			$("div[name='sbjgs']").comboxfield("reload");
		}

	}

	function queryUrl(obj) {
		var _this = $(obj);
		var aa = $("#formpanel").formpanel('getValue');
		var htmls = "";
		var tongJiLeiXing = aa.data.tjlx;
		var regCode = aa.data.xq;
		var adminbrancode = aa.data.sbjgs;
		var sheBeiLeiBie = aa.data.sblb;
		_this.find(".button-text").html("查询中");
		
		if(tongJiLeiXing==0){
			htmls += "<a href='"+rootPath+"/data24/downExcelByXiaQu.do'>下载</a>";
			htmls += "<table   border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>" + "<tr><td colspan='8'><h1>特种设备统计（区域）</h1></td></tr>";
			htmls += "<tr><td>区域</td><td>在用</td><td>停用</td><td>在建</td><td>注销</td><td>报废</td><td>待核实</td><td>合计</td></tr>";
			
			var total1 = 0;
			var total2 = 0;
			var total3 = 0;
			var total4 = 0;
			var total5 = 0;
			var total6 = 0;
			var total7 = 0;
			var total8 = 0;
			$.ajax({
				type : "post",
				url : rootPath + "/data24/queryByXiaQu.do",
				dataType : "text",
				async : false,
				cach : false,
				data : {
					"tongJiLeiXing" : tongJiLeiXing,
					"regCode" : regCode,
					"adminbrancode" : adminbrancode,
					"sheBeiLeiBie" : sheBeiLeiBie
				},
				success : function(data) {
					_this.find(".button-text").html("查询");
					var dataObj = eval("(" + data + ")");
					for (var i = 0; i < dataObj.length; i++) {
						htmls += "<tr><td>" + dataObj[i].区域 + "</td><td>" + dataObj[i].在用 + "</td><td>" + dataObj[i].停用 + "</td><td>" + dataObj[i].在建 + "</td><td>" + dataObj[i].注销 + "</td><td>" + dataObj[i].报废 + "</td><td>" + dataObj[i].待核实 + "</td><td>" + dataObj[i].合计 + "</td></tr>";
					
						total1+=dataObj[i].区域;
						total2+=dataObj[i].在用;
						total3+=dataObj[i].停用;
						total4+=dataObj[i].在建;
						total5+=dataObj[i].注销;
						total6+=dataObj[i].报废;
						total7+=dataObj[i].待核实;
						total8+=dataObj[i].合计;
					}
					
					
					htmls+="<tr><td>总计</td><td>"+total2+"</td><td>"+total3+"</td><td>"+total4+"</td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td></tr>";
				}
			});
			htmls += "</table>";
			var newWim = open("down/data24_xq_down.jsp", "_blank");
			window.setTimeout(function() {
				_this.find(".button-text").html("查询");
				newWim.document.body.innerHTML = htmls;
			}, 1000);
		}else{
			htmls +="<a href='"+rootPath+"/data24/downExcelByPingZhong.do'>下载</a>";
			htmls +="<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'><tr><td colspan='8'><h1>特种设备统计（品种）</h1></td></tr>";
			htmls+="<tr><td>设备品种</td><td>在用</td><td>停用</td><td>在建</td><td>注销</td><td>报废</td><td>待核实</td><td>合计</td></tr>";
					
		 	
			var total1 = 0;
			var total2 = 0;
			var total3 = 0;
			var total4 = 0;
			var total5 = 0;
			var total6 = 0;
			var total7 = 0;
			$.ajax({
				type : "post",
				url : rootPath + "/data24/queryByPingZhong.do",
				dataType : "text",
				async : false,
				cach : false,
				data:{"regCode":regCode,"adminbrancode":adminbrancode,"sheBeiLeiBie":sheBeiLeiBie},
				success : function(data) {
					_this.find(".button-text").html("查询");
					var dataObj = eval("(" + data + ")");
					for (var i = 0; i < dataObj.length; i++) {
				 		htmls+="<tr>"+
						"				<td>"+dataObj[i].设备品种+"</td>"+
						"				<td>"+dataObj[i].在用+"</td>"+
						"				<td>"+dataObj[i].停用+"</td>"+
						"				<td>"+dataObj[i].在建+"</td>"+
						"				<td>"+dataObj[i].注销+"</td>"+
						"				<td>"+dataObj[i].报废+"</td>"+
						"				<td>"+dataObj[i].待核实+"</td>"+
						"				<td>"+dataObj[i].合计+"</td>"+
						"			</tr>"; 
						
						total1+=dataObj[i].在用;
						total2+=dataObj[i].停用;
						total3+=dataObj[i].在建;
						total4+=dataObj[i].注销;
						total5+=dataObj[i].报废;
						total6+=dataObj[i].待核实;
						total7+=dataObj[i].合计;
					}
					
					htmls+="<tr><td>总计</td><td>"+total1+"</td><td>"+total2+"</td><td>"+total3+"</td><td>"+total4+"</td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td></tr>";
				}
			});  
			
			
			htmls+="</table>";
			var newWim = open("down/data24_pz_down.jsp", "_blank");
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
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%" title="特种设备统计">

		<div id="tjlx" name='tjlx' vtype="comboxfield" label="统计类型" labelAlign="right" labelwidth='100px' width="310"  valuetip="请选择统计类型……"></div>

		<div id="xq" name='xq' vtype="comboxfield" label="辖区" labelAlign="right" labelwidth='100px' width="310" valuetip="请选择所属辖区……" dataurl=""></div>
		<div id="sbjgs" name='sbjgs' vtype="comboxfield" label="设备监管所" labelAlign="right" labelwidth='100px' width="310" valuetip="请选择所属监管所……"
			dataurl=""></div>
			
		<div id="sblb" name='sblb' vtype="comboxfield" label="设备类别" labelAlign="right" labelwidth='100px' width="310"></div>


		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" icon="../query/queryssuo.png" onclick="queryUrl(this);"></div>
			<div name="reset_button" vtype="button" text="重置" icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>