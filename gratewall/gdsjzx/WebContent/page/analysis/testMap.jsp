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
	src="<%=request.getContextPath()%>/chart/Highmaps-4.2.3/js/highmaps.js"
	type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/chart/Highmaps-4.2.3/js/cn-all-sar-taiwan.js"
	type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/chart/Highmaps-4.2.3/js/drilldown.js"
	type="text/javascript"></script>
	<title>Insert title here</title>
<script type="text/javascript">

	$(function () {
	
		var qy={
		}
		
	
		var data = Highcharts.geojson(mapsdata);
		 $('#container').highcharts('Map', {
		   chart: {
                backgroundColor: '#4b96af'
            },
      title : {
	            text : '广东省企业分布情况'
	        },
	        subtitle: {
	            text: '',
	            floating: true,
	            align: 'right',
	            y: 50,
	            style: {
	                fontSize: '16px'
	            }
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle'
	        },
	        colorAxis: {
	            min: 0,
	            minColor: '#FFFFFF',
	            maxColor: '#000000',
	            tickPositions:[10,100,1000,10000,100000,1000000,1000000],
	      		ticklength:100
	        },
	        mapNavigation: {
	            enabled: true,
	            buttonOptions: {
	                verticalAlign: 'bottom'
	            }
	        },
	        plotOptions: {
	            map: {
	                states: {
	                    hover: {
	                        color: '#EEDD66'
	                    }
	                }
	            }
	        },
	        series : [{
	      
	            name: '地区',
	            mapData:data,
	            joinBy:'name',
	            data: [{
                        name: '广州市',
                         value:222
                    },{
                        name: '深圳市',
                         value:22111111
                    },{
                        name: '佛山市',
                         value:100100
                    }],
                dataLabels: {
                    enabled: true,
                   
                    format: '{point.name}'
                }
	        }],
	        drilldown: {
	            activeDataLabelStyle: {
	                color: '#FFFFFF',
	                textDecoration: 'none',
	                textShadow: '0 0 3px #000000'
	            },
	            drillUpButton: {
	                relativeTo: 'spacingBox',
	                position: {
	                    x: 0,
	                    y: 60
	                }
	            }
	        }
	    });
	});



</script>
</head>
<body>

	<span id="container" style="height:500px;width:800px;"></span>

</body>
</html>