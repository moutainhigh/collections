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
	
	
<script src="<%=request.getContextPath()%>/chart/Highcharts-4.2.3/js/modules/drilldown.js"></script>
	
<script
	src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/js/analysis/picker.js"
	type="text/javascript"></script>

<title>Insert title here</title>
<script type="text/javascript">

  //定义一个Highcharts的变量，初始值为null
  var oChart = null;
  var draw = null;
  
  
  function drawHighCharts(title,xName,series){
   draw = {
		 chart: { 
		        renderTo: "container", 
		   	    type: 'column',
		   	    events: {
                drilldown: function (e) {
                    if (!e.seriesOptions) {
                        var chart = oChart,
                            drilldowns = {
                                '广东省地市': {
                                    name: '广东省地市1',
                                    data: [
                                        ['Cows', 2],
                                        ['Sheep', 3]
                                    ]
                                },
                                '湖南省地市': {
                                    name: '湖南省地市1',
                                    data: [
                                        ['Apples', 5],
                                        ['Oranges', 7],
                                        ['Bananas', 2]
                                    ]
                                },
                                '四川省地市': {
                                    name: '四川省地市1',
                                    data: [
                                        ['Toyota', 1],
                                        ['Volkswagen', 2],
                                        ['Opel', 5]
                                    ]
                                }
                            },
                            series = drilldowns[e.point.name];

                        // Show the loading label
                        chart.showLoading('Simulating Ajax ...');

                        setTimeout(function () {
                            chart.hideLoading();
                            chart.addSeriesAsDrilldown(e.point, series);
                        }, 1000);
                    }

                }
            }
		    }, 
		    title: {
	            text: title
	        },
	        subtitle: {
	            text: ''
	        },
	        xAxis: {
	        	 categories: xName,//x轴
	             crosshair: true,
	        	 labels: {
	        	 		step:1,
                        rotation: -45, //字体倾斜
                        align: 'right',
                        style: { font: 'normal 13px 宋体'}/* ,
                         autoRotation:[0],
                    	rotation:0,
                        useHTML:true,
                        formatter: function() {
						return '<div class="xLabel" style="width:1px;">'+ this.value+'</div>';
					} */
						}
	             
	        },
	        scrollbar: {
            enabled:true
        },
         plotOptions: {
            series: {
            	pointWidth: 10,
                cursor: 'pointer',
                point: {
                    events: {
                        click: function () {
                        getPointData(this);
                        }
                    }
                }
            }
        },
	        yAxis: {
	        	allowDecimals:false,
	        	endOnTick:true,
	            title: {
	                text: '注册资本金'//y轴标题
	            },
	         labels: {
					    formatter:function(){
					      return this.value;
					    }
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
	            borderWidth: 0,
	           // useHTML:true
	        },
		    series:series,
		    drilldown: {series: []}
	 };


		oChart = new Highcharts.Chart(draw);
}

//按点下钻
function getPointData(e){
	  /*    selectedPoints = oChart.getSelectedPoints();
        if (oChart.lbl) {
            oChart.lbl.destroy();
        }
        oChart.lbl = oChart.renderer.label('You selected ' + selectedPoints.length + ' points', 100, 60)
            .attr({
                padding: 10,
                r: 5,
                fill: Highcharts.getOptions().colors[1],
                zIndex: 5
            })
            .css({
                color: 'white'
            })
            .add(); */
             //添加跳转事件 --- 下钻（按柱子的信息）
            //window.open(e.options.url);
          //  var data="llllllll";
           // var name="test";
           	// openPostWindow(e.options.url, data, name);
}


//按产业下钻
function getSericeData(){
            selectedSeries = oChart.getSelectedSeries();
            
}
function hellow(e){
}

var xName="";
var yName="";
$(function () {
	//  oChart = new Highcharts.Chart(draw);
	 // oChart.chart[0].type="column";
	 
	  loadChars();
	//oChart.click=hellow();
});
	
//获取数据
function loadChars(){
	
//oChart.showLoading(); 
	$.ajax({
  			 url: '<%=request.getContextPath()%>/analysis/entestbreg.do',
				//	async : false,
					dataType : 'json',
					type : "POST", //请求方式
					success : function(data) {
					var xName=groupxName(data);
					var series=new Array();
					//按什么来分类 --- 图例部分
					series.push(groupSeries(data,"广东省地?2?"));
					series.push(groupSeries(data,"广东省地市"));
					series.push(groupSeries(data,"湖南省地市"));
					series.push(groupSeries(data,"四川省地市"));
					
					// 按区域分析 ---- 标题
					drawHighCharts("按区域分析",xName,series);
					
					//oChart.
					} 
				});
				// oChart.hideLoading(); 
	}



	//根据时间作为X轴。类别作为具体项的情况下
	function  groupSeries(data,groupName){
		var transdt=new Array();
		var transtr="";
		for(var i=0;i<data.length;i++){
			if(data[i].testdata2==groupName){
				var date=data[i].valuesum;
				if(transtr.indexOf(date)==-1){
					transtr=transtr+"/"+date;
				}
			}
		}
		var strList=transtr.split("/");
		for(var j=1;j<strList.length;j++){
			var mapData={"y":parseFloat(strList[j]),"name":groupName, drilldown: true}; 
			transdt.push(mapData);
		}
		//transdt.push(parseFloat(strList[j]));//drilldown: true
		var seriesJson={ "type":"column","name":groupName,"data":transdt};
		return seriesJson;
	}
	
	
	function  groupxName(data){
		var transdt=new Array();
		var transtr="";
		for(var i=0;i<data.length;i++){
				var date=data[i].transdt;
				if(transtr.indexOf(date)==-1){
					transtr=transtr+"/"+date;
			}
		}
		var strList=transtr.split("/");
		for(var j=1;j<strList.length;j++){
			transdt.push(strList[j]);
		}
		return transdt;
	}

    </script>
</head>
<body>



 <select id="selType" name="dept" style="width: 150px;" >
                <option value="1">按日</option>
                <option value="2">按周</option>
                <option value="3">按月</option>
                <option value="4">按季</option>
                <option value="5">按年</option>
            </select>
            
      <input id="starttime" class="Wdate" onclick="createWdatePicker();" />
       	选中时间段
            <input id="showstarttime" type="text" readonly="readonly" />
        		到
            <input id="showendtime" type="text" readonly="readonly" />        

	<span id="container" style="width:70%"></span>


	
</body>
</html>