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
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}

	$(function() {

		
		$("div[name='tjxm']").comboxfield('addOption', "在用设备统计", 0);
		$("div[name='tjxm']").comboxfield('addOption', "在用设备使用单位统计", 1);
		$("div[name='tjxm']").comboxfield('option', 'defaultvalue', 0);
		
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
		//var aa = $("#formpanel").formpanel('getValue');
		var aa = $("#formpanel").formpanel('getText');
		var htmls = "";
		var regCode = aa.data.xq;
		var adminbrancode = aa.data.sbjgs;
		var sheBeiLeiBie = aa.data.sblb;
		var tjxm = aa.data.tjxm;
			if(tjxm=="在用设备统计"){
				_this.find(".button-text").html("查询中");
				htmls += "<a href='"+rootPath+"/data17/downExcelByZaiYong.do'>下载</a>";
				htmls += "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>" + "<tr><td colspan='10'><h1>深圳市在用特种设备统计表</h1></td></tr>";
				htmls += "<tr><td>项目</td><td>锅炉</td><td width='250'><table><tr><td colspan='2'>压力容器</td></tr><tr><td class='lay01'>合计</td><td  class='lay02'>非简单压力容器</td></tr></table></td><td>电梯</td><td>起重机械</td><td>场车</td><td>游乐设施</td><td>压力管道</td><td>客运索道</td><td>合计</td></tr>";
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
					url : rootPath + "/data17/queryListByZaiYong.do",
					dataType : "text",
					async : false,
					cach : false,
					data : {
						"regCode" : regCode,
						"adminbrancode" : adminbrancode,
						"sheBeiLeiBie" : sheBeiLeiBie
					},
					success : function(data) {
						//<td><table><tr><td class='lay01'>" + dataObj[i].压力容器 + "</td><td class='lay02'>" + dataObj[i].非简单压力容器 + "</td></tr><table></td>
						_this.find(".button-text").html("查询");
						
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += "<tr><td>" + dataObj[i].项目+ "</td><td>" + dataObj[i].锅炉 + "</td><td><table><tr><td class='lay01'>"+dataObj[i].压力容器合计+"</td><td class='lay02'>"+dataObj[i].非简单压力容器+"</td></tr></table></td><td>" + dataObj[i].电梯 + "</td><td>"+ dataObj[i].起重机械 + "</td><td>" + dataObj[i].场车 + "</td><td>" + dataObj[i].游乐设施 + "</td><td>" + dataObj[i].压力管道 + "</td><td>" + dataObj[i].客运索道 + "</td><td>"+ dataObj[i].合计 + "</td></tr>";
									
							total1+=dataObj[i].项目;
							total2+=dataObj[i].锅炉;
							total3+=dataObj[i].压力容器合计 ;
							total4+=dataObj[i].非简单压力容器 ;
							total5+=dataObj[i].电梯;
							total6+=dataObj[i].起重机械;
							total7+=dataObj[i].场车;
							total8+=dataObj[i].游乐设施 ;
							total9+=dataObj[i].压力管道 ;
							total10+=dataObj[i].客运索道 ;
							total11+=dataObj[i].合计;
						}
						
						//htmls +="<tr><td>总计</td><td>"+total2+"</td><td><table><tr><td class='lay01'>"+total3+"</td><td class='lay02'>"+total4+"</td></tr></table></td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td></tr>";
					}
				});
	
				htmls += "</table>";
				var newWim = open("down/data17_down.jsp", "_blank");
				window.setTimeout(function() {
					_this.find(".button-text").html("查询");
					newWim.document.body.innerHTML = htmls;
				}, 1000);
				
		}else{
			
			
			//按使用单位查询
			_this.find(".button-text").html("查询中");
			htmls += "<a href='"+rootPath+"/data17/downExcelByDangWei.do'>下载</a>";
			htmls += "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>" + "<tr><td colspan='10'><h1>深圳市在用设备按使用单位统计表</h1></td></tr>";
			htmls += "<tr><td>辖区</td><td>锅炉</td><td width='250'><table><tr><td colspan='2'>压力容器</td></tr><tr><td class='lay01'>合计</td><td  class='lay02'>非简单压力容器</td></tr></table></td><td>电梯</td><td>起重机械</td><td>场车</td><td>游乐设施</td><td>压力管道</td><td>客运索道</td><td>特种设备</td></tr>";
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
				url : rootPath + "/data17/queryShiYongDanWei.do",
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
						htmls += "<tr><td>" + dataObj[i].项目+ "</td><td>" + dataObj[i].锅炉 + "</td><td><table><tr><td class='lay01'>"+dataObj[i].压力容器合计+"</td><td class='lay02'>"+dataObj[i].非简单压力容器+"</td></tr></table></td><td>" + dataObj[i].电梯 + "</td><td>"
								+ dataObj[i].起重机械 + "</td><td>" + dataObj[i].场车 + "</td><td>" + dataObj[i].游乐设施 + "</td><td>" + dataObj[i].压力管道 + "</td><td>" + dataObj[i].客运索道 + "</td><td>"
								+ dataObj[i].合计 + "</td></tr>";
								
						//total1+=dataObj[i].equAddrCountyCode;
						total2+=dataObj[i].锅炉;
						total3+=dataObj[i].压力容器合计 ;
						total4+=dataObj[i].非简单压力容器 ;
						total5+=dataObj[i].电梯;
						total6+=dataObj[i].起重机械;
						total7+=dataObj[i].场车;
						total8+=dataObj[i].游乐设施 ;
						total9+=dataObj[i].压力管道 ;
						total10+=dataObj[i].客运索道 ;
						total11+=dataObj[i].合计;
					}
					
					//htmls +="<tr><td>总计</td><td>"+total2+"</td><td><table><tr><td class='lay01'>"+total3+"</td><td class='lay02'>"+total4+"</td></tr></table></td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td></tr>";
				}
			});

			htmls += "</table>";
			var newWim = open("down/data17_down.jsp", "_blank");
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
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%" title="深圳市在用特种设备统计表">


		<div id="tjxm" name='tjxm' vtype="comboxfield" label="统计项目" labelAlign="right" labelwidth='100px' width="420" valuetip="请选择统计项目……" dataurl=""></div>
		
		
		
		<div id="xq" name='xq' vtype="comboxfield" label="辖区" labelAlign="right" labelwidth='100px' width="420" valuetip="请选择所属辖区……" dataurl=""></div>
		
		
		
		<div id="sbjgs" name='sbjgs' vtype="comboxfield" label="设备监管所" labelAlign="right" labelwidth='100px' width="420" valuetip="请选择所属监管所……"
			dataurl=""></div>
			

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="统计" icon="../query/queryssuo.png" onclick="queryUrl(this);"></div>
			<div name="reset_button" vtype="button" text="重置" icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>