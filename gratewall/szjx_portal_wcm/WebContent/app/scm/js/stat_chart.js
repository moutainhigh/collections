//柱形
function columnChart(columnContainer,columnTitle,columnSubTitle,columnXAxis,columnYAxis,columnSeries,columnUnit,urlHost){
	var columnChart;
	columnChart = new Highcharts.Chart({
		chart: {
			renderTo: columnContainer,
			type: 'column',
			//spacingTop: 20
		},
		credits://设置右下角的标记。
		{
			enabled: false
		},
		title: {
			text:columnTitle,
			style:{
				color: '#41526C'
			}
		},
		legend: {
			width:740,
			backgroundColor: '#FFFFFF',
			verticalAlign: 'top',
			borderWidth: 0,
			x:0,
			y:34,
			itemStyle: {
				lineHeight: '16px'
			}
		},
		subtitle: {
			text: columnSubTitle
		},
		xAxis: {
			categories: columnXAxis
		},
		yAxis: {
			min: 0,
			allowDecimals:false,
			title: {
				text: columnYAxis
			}
		},
		exporting:{
			filename:'column-chart',
			url:urlHost+'/wcm/app/scm/stat/highchartsservletjsp.jsp'
		},
		tooltip: {
			formatter: function() {
				return ''+
					this.x +'月: '+ this.y +columnUnit;
			}
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
			series: columnSeries
	});
}
//线型
function lineChart(lineContainer,lineTitle,lineSubTitle,lineXAxis,lineYAxis,lineSeries,lineUnit,urlHost){
	var lineChart;
	lineChart = new Highcharts.Chart({
		chart: {
			renderTo: lineContainer,
			type: 'line',
			spacingTop:20
		},
		credits://设置右下角的标记。
		{
			enabled: false
		},
		title: {
			text: lineTitle,
			margin:50,
			style:{
				color: '#41526C'
			}
		},
		legend: {
			width:740,
			backgroundColor: '#FFFFFF',
			verticalAlign: 'top',
			borderWidth: 0,
			x:0,
			y:34,
			itemStyle: {
				lineHeight: '16px'
			}
		},
		subtitle: {
			text: lineSubTitle
		},
		xAxis: {
			categories: lineXAxis
		},
		yAxis: {
			min: 0,
			allowDecimals:false,
			title: {
				text: lineYAxis
			}
		},
		exporting:{
			filename:'line-chart',
			url:urlHost+'/wcm/app/scm/stat/highchartsservletjsp.jsp'
		},
		tooltip: {
			formatter: function() {
				return ''+
					this.x +'月: '+ this.y +lineUnit;
			}
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
			series: lineSeries
	});
}
//柱形适用原创率
function columnChart1(columnContainer,columnTitle,columnSubTitle,columnXAxis,columnYAxis,columnSeries,columnUnit,urlHost){
	var columnChart;
	columnChart = new Highcharts.Chart({
		chart: {
			renderTo: columnContainer,
			type: 'column',
			spacingTop: 20
		},
		credits://设置右下角的标记。
		{
			enabled: false
		},
		title: {
			text:columnTitle,
			margin:50,
			style:{
				color: '#41526C'
			}
		},
		legend: {
			width:740,
			backgroundColor: '#FFFFFF',
			verticalAlign: 'top',
			borderWidth: 0,
			x:0,
			y:34,
			itemStyle: {
				lineHeight: '16px'
			}
		},
		subtitle: {
			text: columnSubTitle
		},
		xAxis: {
			categories: columnXAxis
		},
		yAxis: {
			tickInterval: 20,//自定义刻度
			min: 0,// 最小值
			max:100,//最大值
			title: {
				text: columnYAxis
			},
			labels: {
				formatter: function() {
					return this.value +' %';
				}
			}
		},
		exporting:{
			filename:'column-chart',
			url:urlHost+'/wcm/app/scm/stat/highchartsservletjsp.jsp'
		},
		tooltip: {
			formatter: function() {
				return ''+
					this.x +'月: '+ this.y.toFixed(2) + columnUnit;
			}
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
			series: columnSeries
	});
}
//线型适用原创率
function lineChart1(lineContainer,lineTitle,lineSubTitle,lineXAxis,lineYAxis,lineSeries,lineUnit,urlHost){
	var lineChart;
	lineChart = new Highcharts.Chart({
		chart: {
			renderTo: lineContainer,
			type: 'line',
			spacingTop:20
		},
		credits://设置右下角的标记。
		{
			enabled: false
		},
		title: {
			text: lineTitle,
			margin:50,
			style:{
				color: '#41526C'
			}
		},
		subtitle: {
			text: lineSubTitle
		},
		xAxis: {
			categories: lineXAxis
		},
		yAxis: {
			tickInterval: 20,
			min: 0,
			max:100,
			title: {
				text: lineYAxis
			},
			labels: {
				formatter: function() {
					return this.value +' %';
				}
			}
		},
		exporting:{
			filename:'line-chart',
			url:urlHost+'/wcm/app/scm/stat/highchartsservletjsp.jsp'
		},
		legend: {
			width:740,
			backgroundColor: '#FFFFFF',
			verticalAlign: 'top',
			borderWidth: 0,
			x:0,
			y:34,
			itemStyle: {
				lineHeight: '16px'
			}
		},
		tooltip: {
			formatter: function() {
				return ''+
					this.x +'月: '+ this.y.toFixed(2) +lineUnit;
			}
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
			series: lineSeries
	});
}
//柱形适用被转发率和被评论率
function columnChart2(columnContainer,columnTitle,columnSubTitle,columnXAxis,columnYAxis,columnSeries,columnUnit,urlHost){
	var columnChart;
	columnChart = new Highcharts.Chart({
		chart: {
			renderTo: columnContainer,
			type: 'column',
			spacingTop: 20
		},
		credits://设置右下角的标记。
		{
			enabled: false
		},
		title: {
			text:columnTitle,
			margin:50,
			style:{
				color: '#41526C'
			}
		},
		legend: {
			width:740,
			backgroundColor: '#FFFFFF',
			verticalAlign: 'top',
			borderWidth: 0,
			x:0,
			y:34,
			itemStyle: {
				lineHeight: '16px'
			}
		},
		subtitle: {
			text: columnSubTitle
		},
		xAxis: {
			categories: columnXAxis
		},
		yAxis: {
			min: 0,// 最小值
			title: {
				text: columnYAxis
			},
			labels: {
				formatter: function() {
					return this.value +' %';
				}
			}
		},
		exporting:{
			filename:'column-chart',
			url:urlHost+'/wcm/app/scm/stat/highchartsservletjsp.jsp'
		},
		tooltip: {
			formatter: function() {
				return ''+
					this.x +'月: '+ this.y.toFixed(2) + columnUnit;
			}
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
			series: columnSeries
	});
}
//线型适用被转发率和被评论率
function lineChart2(lineContainer,lineTitle,lineSubTitle,lineXAxis,lineYAxis,lineSeries,lineUnit,urlHost){
	var lineChart;
	lineChart = new Highcharts.Chart({
		chart: {
			renderTo: lineContainer,
			type: 'line',
			spacingTop:20
		},
		credits://设置右下角的标记。
		{
			enabled: false
		},
		title: {
			text: lineTitle,
			margin:50,
			style:{
				color: '#41526C'
			}
		},
		subtitle: {
			text: lineSubTitle
		},
		xAxis: {
			categories: lineXAxis
		},
		yAxis: {
			min: 0,
			title: {
				text: lineYAxis
			},
			labels: {
				formatter: function() {
					return this.value +' %';
				}
			}
		},
		exporting:{
			filename:'line-chart',
			url:urlHost+'/wcm/app/scm/stat/highchartsservletjsp.jsp'
		},
		legend: {
			width:740,
			backgroundColor: '#FFFFFF',
			verticalAlign: 'top',
			borderWidth: 0,
			x:0,
			y:34,
			itemStyle: {
				lineHeight: '16px'
			}
		},
		tooltip: {
			formatter: function() {
				return ''+
					this.x +'月: '+ this.y.toFixed(2) +lineUnit;
			}
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
			series: lineSeries
	});
}
//饼型
function pieChart(pieContainer,lineTitle,lineSeries){
	 var circleChart;
	 circleChart = new Highcharts.Chart({
		chart: {
			renderTo: pieContainer,
			plotBackgroundColor: null,
			plotBorderWidth: 2,
			plotShadow: false
		},
		credits://设置右下角的标记。
		{
			text: "",//显示的文字
			//href: 'http://www.trs.com.cn' //路径
		},
		title: {
			text: lineTitle
		},
		tooltip: {
			formatter: function() {
				return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
			}
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					color: '#000000',
					connectorColor: '#000000',
					formatter: function() {
						return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
					}
				}
			}
		},
		series: [{
			type: 'pie',
			data: lineSeries
		}]
	});
}
function exportExcel(year,groupId,plat,postPage){
	if(year == null || year=="undefined" || year == ""){
		alert("请求参数【year】为空！");
		return false;
	}
	if(groupId == null || groupId=="undefined" || groupId == ""){
		alert("请求参数【groupId】为空！");
		return false;
	}
	if(plat == null || plat=="undefined" || plat == ""){
		alert("请求参数【plat】为空！");
		return false;
	}
	if(postPage == null || postPage=="undefined" || postPage == ""){
		alert("请求页面参数【postPage】为空！");
		return false;
	}
	$.ajax({
		type:"post",
		data:{StatYear:year,SCMGroupId:groupId,StatPlatform:plat},
		dataType:"text",
		url:postPage,
		success:function(data){
			if(data && data.indexOf("<excelfile>") != -1){
				var ix = data.indexOf("<excelfile>") + 11;
				var ixx = data.indexOf("</excelfile>");
				data = data.substring(ix,ixx);
				var frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
				var sUrl = "../../file/read_file.jsp?FileName="+data;
				frm.src = sUrl;
			}
		},
		error:function(){
			alert("失败！");
		}
	});
}