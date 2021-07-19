<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html style="height:100%;">
<head>
<title></title>
 
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,IE=9,IE=EmulateIE7,chrome=1" />
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
<script src="<%=request.getContextPath()%>/static/script/echarts.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/shenzhen.js"
	type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body style="margin: 0px;height: 100%;">
<div id="mains" style="height:100%; width:100%;"></div>
	<!-- <div id="main" style="height: 100%; width: 100%;"></div> -->
	<script type="text/javascript">
	window.console = window.console || (function(){ 
		var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function(){}; 
		return c; 
		})(); 
	Date.prototype.Format = function (fmt) {
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt))
	fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o){
	    if (new RegExp("(" + k + ")").test(fmt)) {
	fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	}
	    }
	    return fmt;
	}
	var datas;
	var myChart = echarts.init(document.getElementById("mains"));
	$(function test(){
	var uploadedDataURL ="<%=request.getContextPath()%>/static/script/shenzhe2.json";
		$.get(uploadedDataURL, function(geoJson) {
			myChart.showLoading();
			$.ajax({
				type : "post",
				url : rootPath + "/cognosController/getHomePage.do",
				data : "timess=" +new Date().Format("yyyyMMdd"),
				dataType : "text",
				async : false,
				cache : true,
				success : function(ress) {
					datas = eval("(" + ress + ")");
				}
			});
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
				backgroundColor : '#d3d7d4',
				title : {
					text : '深圳市商事主体分布情况',
					subtext : '　　 　　　　　数据更新时间: '+new Date(new Date().getTime() - 86400000).Format("yyyy年MM月dd日")+' 21:00'+"\r\n"+"商事主体共 "+(datas.nums[0].sl+datas.nums[1].sl)+" 户,"+"其中"+datas.nums[0].t+" "+datas.nums[0].sl+" 户,"+datas.nums[1].t+" "+datas.nums[1].sl+" 户。",
					x : 'center',
					textStyle : {
						color : '#000',
						fontSize:'25px',
						textAlign:'right'
					}
				},
				tooltip : {
					trigger : 'item',
					formatter : function(params) {
						return params.name + ' : ' + params.value[2] +' 户';
					}
				},
				legend : {
					orient : 'vertical',
					y : 'bottom',
					x : 'right',
					
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
							borderWidth : 1,//省份的边框宽度
							 color : '#ece2df'  //地图背景颜色
						},
						emphasis : {
							areaColor : '#feeeed'
						}
					}
				},
				visualMap : {
					min : 10000,
					max : 800000,
					text : [ '多', '少' ],
					realtime : false,
					calculable : true,
					inRange : {
						color : [ 'lightskyblue', 'yellow', 'orangered' ]
					}
				},
				series : [ {
					type : 'scatter',
					coordinateSystem : 'geo',
					data : convertData(datas.mapvalue),
					symbolSize : 50,
					label : {
						normal : {
							show : true,
							formatter : '{b}',
							textStyle : {
								color : '#000',
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
			//myChart.hideLoading();
			myChart.setOption(option,true);
		});
	});
</script>

</body>
</html>