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

		$(".jazz-checkbox-item").css("width", "150px");
		getcode();

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

	function queryUrl(obj) {
		var _this = $(obj);
		var aa = $("#formpanel").formpanel('getValue');
		var htmls = "";
		var regCode = aa.data.xq;
		var adminbrancode = aa.data.sbjgs;
		var sheBeiLeiBie = aa.data.sblb;
		_this.find(".button-text").html("查询中");
		var xiaQu = $('div[name="xq"]').comboxfield('getText');
		var jianGuanSuo = $('div[name="sbjgs"]').comboxfield('getText');
		
		
		if(xiaQu==""){
			htmls += "<a href='"+rootPath+"/data18/downExcelByDingJianLv.do'>下载</a>";
			htmls += "<table id='lists' border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>" + "<tr><td colspan='15'><h1>特种设备定检率统计表</h1></td></tr>";
			htmls += "<tr><td>设备种类</td><td>统计类型</td><td>福田局</td><td>罗湖局</td><td>南山局</td><td>盐田局</td><td>宝安局</td><td>龙岗局</td><td>光明局</td><td>坪山局</td><td>龙华局</td><td>大鹏局</td><td>深汕监管局</td><td>未分派</td><td>合计</td></tr>";
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
						url : rootPath + "/data18/queryListByDingJianLv.do",
						dataType : "text",
						async : false,
						cach : false,
						data : {
							"regCode" : regCode,
							"adminbrancode" : adminbrancode,
							"sheBeiLeiBie" : sheBeiLeiBie,
							jianGuanSuo:jianGuanSuo
						},
						success : function(data) {
							_this.find(".button-text").html("查询");
							var dataObj = eval("(" + data + ")");
							for (var i = 0; i < dataObj.length; i++) {
								
								htmls += "<tr><td>" + dataObj[i].品种种类 + "</td><td>" + dataObj[i].统计类型 + "</td><td>" + dataObj[i].福田局 + "</td><td>" + dataObj[i].罗湖局+"</td><td>" + dataObj[i].南山局 + "</td><td>"
										+ dataObj[i].盐田局 + "</td><td>" + dataObj[i].宝安局 + "</td><td>" + dataObj[i].龙岗局 + "</td><td>" + dataObj[i].光明局 + "</td><td>" + dataObj[i].坪山局
										+ "</td><td>" + dataObj[i].龙华局 + "</td><td>" + dataObj[i].大鹏局 + "</td><td>" + dataObj[i].深汕监管局 + "</td><td>" + dataObj[i].未分派 + "</td><td>" + dataObj[i].合计 + "</td></tr>";

								total1 += dataObj[i].福田局;
								total2 += dataObj[i].罗湖局 ;
								total3 += dataObj[i].南山局;
								total4 += dataObj[i].盐田局;
								total5 += dataObj[i].宝安局;
								total6 += dataObj[i].龙岗局;
								total7 += dataObj[i].光明局;
								total8 += dataObj[i].坪山局;
								total9 += dataObj[i].龙华局;
								total10 += dataObj[i].大鹏局;
								total10 += dataObj[i].深汕监管局;
								total10 += dataObj[i].未分派;
								total12 += dataObj[i].合计;
							}

							/* htmls += "<tr><td>总计</td><td></td><td>" + total1 + "</td><td>" + total2 + "</td><td>" + total3 + "</td><td>" + total4 + "</td><td>" + total5 + "</td><td>" + total6
									+ "</td><td>" + total7 + "</td><td>" + total8 + "</td><td>" + total9 + "</td><td>" + total10 + "</td><td>" + total12
									+ "</td></tr>"; */
						}
					});
		}else{
			htmls += "<a href='"+rootPath+"/data18/downExcelByDingJianLv.do?type="+encodeURI(xiaQu)+"'>下载</a>";
			htmls += "<table id='lists'  border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>" + "<tr><td colspan='15'><h1>特种设备定检率统计表</h1></td></tr>";
		
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
						url : rootPath + "/data18/queryListByDingJianLv.do",
						dataType : "text",
						async : false,
						cach : false,
						data : {
							"regCode" : regCode,
							"adminbrancode" : adminbrancode,
							"sheBeiLeiBie" : sheBeiLeiBie,
							"jianGuanSuo":jianGuanSuo
						},
						success : function(data) {
							_this.find(".button-text").html("查询");
							
							var dataObj = eval("(" + data + ")");
							if(jianGuanSuo!=null&&jianGuanSuo!=""){
								htmls += "<tr><td>设备种类</td><td>统计类型</td><td>"+jianGuanSuo+"</td><td>合计</td></tr>";
								for (var i = 0; i < dataObj.length; i++) {
									var tempStr = "";
									htmls += "<tr><td>" + dataObj[i].品种种类 + "</td><td>" + dataObj[i].统计类型 + "</td><td>" +  dataObj[i].合计 +"</td><td>"+ dataObj[i].合计+"</td></tr>";
								}
							}else{
								htmls += "<tr><td>设备种类</td><td>统计类型</td><td>"+xiaQu+"</td><td>合计</td></tr>";
								for (var i = 0; i < dataObj.length; i++) {
									var tempStr = "";
									if(xiaQu=="福田局"){
										tempStr = dataObj[i].福田局;
									}else if(xiaQu=="罗湖局"){
										tempStr = dataObj[i].罗湖局;
									}else if(xiaQu=="南山局"){
										tempStr = dataObj[i].南山局;
									}else if(xiaQu=="盐田局"){
										tempStr = dataObj[i].盐田局;
									}else if(xiaQu=="宝安局"){
										tempStr = dataObj[i].宝安局;
									}else if(xiaQu=="龙岗局"){
										tempStr = dataObj[i].龙岗局;
									}else if(xiaQu=="光明局"){
										tempStr = dataObj[i].光明局;
									}else if(xiaQu=="坪山局"){
										tempStr = dataObj[i].坪山局;
									}else if(xiaQu=="龙华局"){
										tempStr = dataObj[i].龙华局;
									}else if(xiaQu=="大鹏局"){
										tempStr = dataObj[i].大鹏局;
									}else if(xiaQu=="深汕监管局"){
										tempStr = dataObj[i].深汕监管局;
									}else if(xiaQu=="未分派"){
										tempStr = dataObj[i].未分派;
									}
									
									htmls += "<tr><td>" + dataObj[i].品种种类 + "</td><td>" + dataObj[i].统计类型 + "</td><td>" + tempStr+"</td><td>"+ dataObj[i].合计+"</td></tr>";
									if(xiaQu=="福田局"){
										total12 += dataObj[i].福田局;
									}else if(xiaQu=="罗湖局"){
										total12 += dataObj[i].罗湖局;
									}else if(xiaQu=="南山局"){
										total12 += dataObj[i].南山局;
									}else if(xiaQu=="盐田局"){
										total12 += dataObj[i].盐田局;
									}else if(xiaQu=="宝安局"){
										total12 += dataObj[i].宝安局;
									}else if(xiaQu=="龙岗局"){
										total12 += dataObj[i].龙岗局;
									}else if(xiaQu=="光明局"){
										total12 += dataObj[i].光明局;
									}else if(xiaQu=="坪山局"){
										total12 += dataObj[i].坪山局;
									}else if(xiaQu=="龙华局"){
										total12 += dataObj[i].龙华局;
									}else if(xiaQu=="大鹏局"){
										total12 += dataObj[i].大鹏局;
									}else if(xiaQu=="深汕监管局"){
										tempStr = dataObj[i].深汕监管局;
									}else if(xiaQu=="未分派"){
											tempStr = dataObj[i].未分派;
										}
									
									total3 += dataObj[i].合计;
								}
								
								
							} 

							//htmls += "<tr><td>总计</td><td></td><td>" + total2 + "</td><td>" + total3 + "</td></tr>";
						}
					});
		}

	
		
		
		htmls += "</table>";
		var newWim = open("down/data18_down.jsp", "_blank");
		window.setTimeout(function() {
			_this.find(".button-text").html("查询");
			newWim.document.body.innerHTML = htmls;
		}, 1000);

	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%" title="特种设备定检率统计表">


		<div id="xq" name='xq' vtype="comboxfield" label="辖区" labelAlign="right" labelwidth='100px' width="420" valuetip="请选择所属辖区……" dataurl=""></div>
		<div id="sbjgs" name='sbjgs' vtype="comboxfield" label="设备监管所" labelAlign="right" labelwidth='100px' width="420" valuetip="请选择所属监管所……"
			dataurl=""></div>


		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" icon="../query/queryssuo.png" onclick="queryUrl(this);"></div>
			<div name="reset_button" vtype="button" text="重置" icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>