<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta charset="UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,IE=9,chrome=1" />
<%-- <script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script> --%>
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
<script src="<%=request.getContextPath()%>/static/script/echarts.js"
		type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/static/script/shenzhen.js"
		type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/static/script/beijing.js"
		type="text/javascript"></script> 
<script type="text/javascript">
	//var datas;
	var myChart
	$(function(){
  		test();
 	}); 
	function test(){
	myChart = echarts.init(document.getElementById('main'));
	 myChart.showLoading();
	var uploadedDataURL ="<%=request.getContextPath()%>/static/script/shenzhe2.json";

	 $.get(uploadedDataURL, function(geoJson) {
		echarts.registerMap('shenzhe', geoJson);
		
		var geoCoordMap = {
			"大鹏局" : [ 114.5041, 22.550718 ],
			"宝安局" : [ 113.806805, 22.656723 ],
			"南山局" : [ 113.94045, 22.522974 ],
			"盐田局" : [ 114.267693, 22.580471 ],
			"龙岗局" : [ 114.224836, 22.648482 ],
			"罗湖局" : [ 114.145185, 22.551858 ],
			"龙华局" : [ 114.020059, 22.658082 ],
			"坪山局" : [ 114.343366, 22.662654 ],
			"福田局" : [ 114.051761, 22.54244 ],
			"光明局" : [ 113.922033, 22.733345 ]
		}

		var convertData = function(data) {
			var res = [];
			for (var i = 0; i < data.length; i++) {
				var geoCoord = geoCoordMap[data[i].name];
				if (geoCoord) {
					res.push({
						name : data[i].name,
						symbol : data[i].symbol,
						value : geoCoord.concat(data[i].value)
					});
				}
			}
			return res;
		};

		option = {
				backgroundColor: '#cde6c7',
			title : {
				text : '深圳市行政区域企业数量实时统计',
				subtext : '2017-03-23',
				x : 'center',
				textStyle : {
					color : '#000'
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : function(params) {
					return params.name + ' : ' + params.value[2];
				}
			},
			legend : {
				orient : 'vertical',
				y : 'bottom',
				x : 'right',
				data : [ 'pm2.5' ],
				textStyle : {
					color : '#fff'
				}
			},
			geo : {
				map : 'shenzhen',
				label : {
					normal : {
						show : false
					}

				},
				itemStyle : {
					normal : {
						borderColor : '#f0dc70',//设置区域边框颜色
						borderWidth:1,//省份的边框宽度
						color:'#ece2df'//地图背景颜色
						
					},
					
					emphasis : {
						areaColor : '#feeeed'
					}
				}
			},
			visualMap: {
	            min: 10000,
	            max: 800000,
	            text:['High','Low'],
	            realtime: false,
	            calculable: true,
	            inRange: {
	                color: ['lightskyblue','yellow', 'orangered']
	            }
	        },
			series : [ {
				type: 'scatter',
				coordinateSystem : 'geo',
				data : convertData(  [ {
					name : "大鹏局",
					symbol : 'pin',
					value : 149826
				}, {
					name : "宝安局",
					symbol : 'pin',
					value : 111456
				},

				{
					name : "南山局",
					symbol : 'pin',
					value : 108150
				}, {
					name : "盐田局",
					symbol : 'pin',
					value : 85693
				}, {
					symbol : "pin",
					name : "龙岗局",
					value : 72298
				},

				{
					name : "罗湖局",
					symbol : 'pin',
					value : 72116
				}, {
					name : "坪山局",
					symbol : 'pin',
					value : 68372
				}, {
					name : "福田局",
					symbol : 'pin',
					value : 61400
				}, {
					name : "光明局",
					symbol : 'pin',
					value : 50551
				}, {
					name : "龙华局",
					symbol : 'pin',
					value : 50010
				}

				] ),

				symbolSize : 50,
				label : {
					normal : {
						show : true,
						formatter : '{b}',
						textStyle : {
							color : '#000'
						}

					},
					emphasis : {
						textStyle : {
							color : '#000',
							fontWeight : 'bold',
							fontSize : 14
						}

					}

				},
				itemStyle : {
					normal : {
						color : 'red',
						borderWidth : 0,
						borderType : 'solid'
					},
					emphasis : {
						color : '#FFEA77',
						borderColor : 'red',
						borderWidth : 0

					}
				}
			} ]
		};
		
		myChart.hideLoading();
		myChart.setOption(option);
	}); 
	 
	
	}
	
	
	/*  window.setInterval(function () {
	    
	    refreshData(data);       
	},3000); */
	/* $.ajax({
		type : "post",
		url : rootPath
				+ "/cognosController/getHomePage.do",
		data : "timess=" + new Date() + "&id=" + Math.random(),
		dataType : "text",
		async : false,
		cach : false,
		success : function(res) {
			
			refreshData(res)
		}});
	
	function refreshData(datas){
	     if(!myChart){
	          return;
	     }
	     
	     //更新数据
	      var option = myChart.getOption();
	     alert(option.series[0].data);
	      option.series[0].data = convertData(datas);
	      myChart.setOption(option);   
	      test();
	} */
	 
	</script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="main" style="height:100%; width: 100%;"></div>
</body>
</html>