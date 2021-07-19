<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/chart/Highcharts-4.2.3/js/highcharts.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/chart/Highmaps-4.2.3/js/highmaps.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/highstock.js"
	type="text/javascript"></script>



<title>Insert title here</title>
<script type="text/javascript">

$(function () {
	loadChars();
});


function draw(categoriesArray,seriesArray){
	 var chart = new Highcharts.Chart({
		 chart: { 
		        renderTo: "container", 
		        type: 'column'
		    }, 
		    title: {
	            text: '市场主体分析'
	        },
	        subtitle: {
	            text: ''
	        },
	        xAxis: {
	        	 categories: [categoriesArray],//x轴
	             crosshair: true
	        },
	        yAxis: {
	        	allowDecimals:false,
	        	endOnTick:true,
	        	max:200000000, // 定义Y轴 最大值  
                min:0, // 定义最小值
	            title: {
	                text: '注册资本金'//y轴标题
	            }
	        },tooltip: {
	         
	        },
	        credits: {
	        	text:"",
	        	href:''
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        
	        plotOptions: {
	        },
		    series: eval("["+seriesArray+"]")
	
	 }); 
	//设置图形对象属性
	

    
    

}



//获取数据
function loadChars(){
	$.ajax({
  	 url: '<%=request.getContextPath()%>/cognosController/readCognoxXML.do',
					async : true,
					data : {
						"reportPath" : "content/folder[@name='report']/report[@name='市场主体概况_主体类型']"
					}, //参数值
					dataType : 'text',
					type : "POST", //请求方式
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						//类别轴
						var categoriesArray = dataObj[1];
						//x轴及值
						var seriesArray = praseWdandValue(dataObj[0],
								dataObj[2]);
						 draw(categoriesArray,seriesArray);

					}
				});
	}

	//解析维度及度量 参数：度量值，维度
	function praseWdandValue(data1, data2) {
		var data = "";
		if (data2 != null && data2.length > 0) {
			for (var i = 0; i < data2.length; i++) {
				var ydata = 0;
				if (data1[i] != null && data1[i] != "") {
					ydata = data1[i];
				}
				//"name": name
				data += "{'name':'" + data2[i] + "'," + '"data":[' + ydata + "]},";
			}
		} else {
			data = data1;
		}
		return data.substr(0, data.length - 1);
	}

	//解析xml,并组织成json name为节点名称
	function prase(xml, name) {
		var value;
		$(xml).find(name).each(function(i) {
			if (i == 0) {
				value = '';
			}
			value += $(this).text() + ",";
			alert(i);
		});
		return value.substr(0, value.length - 1);
	}
</script>
</head>
<body>

	<span id="container" ></span>

</body>
</html>