var colorMap={};
$(function () {
	colorMap={
			"潮州市":{"color":"#FDF2B7","province":"445100","valuesumX":"1.97","valuesumY":"-0.3","valueiocX":"3.1","valueiocY":"-0.3","valuemissionX":"4.6","valuemissionY":"-0.3"},
			"汕头市":{"color":"#B1D9B4","province":"440500","valuesumX":"1.65","valuesumY":"-0.3","valueiocX":"2.45","valueiocY":"-0.3","valuemissionX":"3.65","valuemissionY":"-0.3"},
			"揭阳市":{"color":"#FDF2B7","province":"445200","valuesumX":"2.35","valuesumY":"0.5","valueiocX":"3.25","valueiocY":"0.5","valuemissionX":"4.65","valuemissionY":"0.5"},
			"汕尾市":{"color":"#FAD8E8","province":"441500","valuesumX":"2.2","valuesumY":"0.8","valueiocX":"2.97","valueiocY":"0.8","valuemissionX":"4.05","valuemissionY":"0.8"},
			"深圳市":{"color":"#FAD8E8","province":"440300","valuesumX":"2.55","valuesumY":"2.25","valueiocX":"4.05","valueiocY":"2.25","valuemissionX":"5.65","valuemissionY":"2.25"},
			"中山市":{"color":"#B0D8B3","province":"442000","valuesumX":"5.85","valuesumY":"2.91","valueiocX":"8.45","valueiocY":"2.91","valuemissionX":"11.20","valuemissionY":"2.91"},
			"江门市":{"color":"#FAD8E8","province":"440700","valuesumX":"2.30","valuesumY":"1.95","valueiocX":"3.65","valueiocY":"1.95","valuemissionX":"5.20","valuemissionY":"1.95"},
			"阳江市":{"color":"#FFFAB6","province":"441700","valuesumX":"-3.85","valuesumY":"0.49","valueiocX":"-2.85","valueiocY":"0.49","valuemissionX":"-1.75","valuemissionY":"0.49"},
			"珠海市":{"color":"#F4F8F1","province":"440400","valuesumX":"2.02","valuesumY":"2.75","valueiocX":"2.99","valueiocY":"2.75","valuemissionX":"4.22","valuemissionY":"2.75"},
			"梅州市":{"color":"#F4F8EA","province":"441400","valuesumX":"1.37","valuesumY":"0","valueiocX":"1.99","valueiocY":"0","valuemissionX":"2.8","valuemissionY":"0"},
			"河源市":{"color":"#FEF2B6","province":"441600","valuesumX":"1.7","valuesumY":"-0.4","valueiocX":"2.5","valueiocY":"-0.4","valuemissionX":"3.6","valuemissionY":"-0.4"},
			"惠州市":{"color":"#F4F8EA","province":"441300","valuesumX":"2.4","valuesumY":"1","valueiocX":"3.0","valueiocY":"1","valuemissionX":"3.9","valuemissionY":"1"},
			"东莞市":{"color":"#FEF9B5","province":"441900","valuesumX":"4.6","valuesumY":"2.6","valueiocX":"6","valueiocY":"2.6","valuemissionX":"7.8","valuemissionY":"2.6"},
			"广州市":{"color":"#FAD8E8","province":"440100","valuesumX":"-4.58","valuesumY":"-0.04","valueiocX":"-3.23","valueiocY":"-0.03","valuemissionX":"-2.0","valuemissionY":"-0.04"},
			"佛山市":{"color":"#F3F7E9","province":"440600","valuesumX":"-4.3","valuesumY":"-0.03","valueiocX":"-3.03","valueiocY":"-0.03","valuemissionX":"-1.8","valuemissionY":"-0.03"},
			"韶关市":{"color":"#B1D9B4","province":"440200","valuesumX":"-1.97","valuesumY":"0.09","valueiocX":"-1.25","valueiocY":"0.09","valuemissionX":"-0.4","valuemissionY":"0.09"},
			"清远市":{"color":"#D6E7F7","province":"441800","valuesumX":"-1.55","valuesumY":"0.15","valueiocX":"-0.99","valueiocY":"0.15","valuemissionX":"-0.24","valuemissionY":"0.15"},
			"肇庆市":{"color":"#FFFAB6","province":"441200","valuesumX":"-1.83","valuesumY":"0","valueiocX":"-1.10","valueiocY":"0","valuemissionX":"-0.2","valuemissionY":"0"},
			"云浮市":{"color":"#D6E7F7","province":"445300","valuesumX":"-2.31","valuesumY":"0.1","valueiocX":"-1.38","valueiocY":"0.1","valuemissionX":"-0.55","valuemissionY":"0.1"},
			"茂名市":{"color":"#F8BA63","province":"440900","valuesumX":"-2.11","valuesumY":"0.05","valueiocX":"-1.38","valueiocY":"0.05","valuemissionX":"-0.35","valuemissionY":"0.05"},
			"湛江市":{"color":"#FAD8E8","province":"440800","valuesumX":"-1.95","valuesumY":"0.2","valueiocX":"-1.15","valueiocY":"0.2","valuemissionX":"-0.3","valuemissionY":"0.2"},
			"顺德区":{"color":"#F8BA63","province":"440606"},
			"横琴新区":{"color":"#FAD8E8","province":"440003"}
	};
    Highcharts.setOptions({
        lang:{
            drillUpText:"返回 > {series.name}"
        }
    });
	/*function getPoint(e){
	}
	function getPointOver(datas){//移动端的名字
	 	mouseovermap(datas.target.properties.vheight);
	}
	function getPointOut(datas){//移动端的名字
		mouseoutmap();
	}*/
	var data =null;var data1 =null;var data2 =null;var data3 =null;
	$.ajax({
       type: "POST",
       async:false,
       url: "/gdsjzx/datatrs/querymapdata.do",
       contentType: "application/json; charset=utf-8",
       dataType:'json',
       success: function(mapdatas) {//0-地区 1-期末实有 2-代办业务数量 3-最新审办
    	   var json = eval('('+mapdatas[0].mapdata+')');
    	   var group=[];var group1=[];var group2=[];var group3=[];
    	   data = Highcharts.geojson(json);
    	   group=groupfunc(mapdatas,data,group,"0");
       	   var mapline=Highcharts.geojson(json, 'mapline');
       
       	   data1 = Highcharts.geojson(json);
       	   group1=groupfunc(mapdatas,data1,group1,"1");
         
       	   data2 = Highcharts.geojson(json);
       	   group2=groupfunc(mapdatas,data2,group2,"2");
         
       	   data3 = Highcharts.geojson(json);
       	   group3=groupfunc(mapdatas,data3,group3,"3");
       	   getCityMap(mapdatas,group,group1,group2,group3);
        }
	});
	$('rect[fill="#8085e9"]').hide(); 
	//$('rect[fill="#7cb5ec"]').hide();$('g[transform="translate(8,3)"]').hide(); 
});
                     
function groupfunc(mapdatas,data,group,type){
    $.each(data, function (k) {
    	this.color=colorMap[this.name].color;
    	this.value = k*10000;
    	this.events={};
    	for(var i=1;i<mapdatas.length;i++){
    		if(mapdatas[i].regorg==colorMap[this.name].province){
	    		if("0"==type){
    				this.properties={"vheight":(i-1)*25};
    				group.push(data[k]);
	    		}else if("1"==type){
					this.properties={"valuesum":mapdatas[i].valuesum+"户"};
					this.middleX=colorMap[this.name].valuesumX;
			  	    this.middleY=colorMap[this.name].valuesumY;
					group.push(data[k]);
	    		}else if("2"==type){
					this.properties={"valuemission":mapdatas[i].valuemission+"笔"};
					this.middleX=colorMap[this.name].valuemissionX;
					this.middleY=colorMap[this.name].valuemissionY;
					group.push(data[k]);
	    		}else{
					this.properties={"valueioc":mapdatas[i].valueioc+"笔"};
					this.middleX=colorMap[this.name].valueiocX;
					this.middleY=colorMap[this.name].valueiocY;
					//this.color="#F4F8EA";
					group.push(data[k]);
	    		}
    		}
		}
    });
	return group;
}
var chart;
function getCityMap(mapdatas,group,group1,group2,group3){
	delete mapdatas[0];
	var finalReal = 0;var doBusiness = 0;var yesDayBid = 0;
	var GDfinalReal = 0;var GDdoBusiness = 0;var GDyesDayBid = 0;
	var data1=[];var data2=[];var data3=[];
	for(var i=1;i<mapdatas.length;i++){
		if(mapdatas[i].regorg!='440003' && mapdatas[i].regorg!='440606'){
			finalReal += mapdatas[i].valuesum;
			doBusiness += mapdatas[i].valuemission;
			yesDayBid += mapdatas[i].valueioc;
	 	}
		if(mapdatas[i].regorg=='440000'){
			GDfinalReal += mapdatas[i].valuesum;
			GDdoBusiness += mapdatas[i].valuemission;
			GDyesDayBid += mapdatas[i].valueioc;
		}
		if(mapdatas[i].regorg=='440003'){
	 		data1.push(eval('['+mapdatas[i].valuesum+']'));
	 		data2.push(eval('['+mapdatas[i].valuemission+']'));
	 		data3.push(eval('['+mapdatas[i].valueioc+']'));
	 	}
		if(mapdatas[i].regorg=='440606'){
	 		data1.push(eval('['+mapdatas[i].valuesum+']'));
	 		data2.push(eval('['+mapdatas[i].valuemission+']'));
	 		data3.push(eval('['+mapdatas[i].valueioc+']'));
	 	}
	}
	
	$('#container2').highcharts('Map', {
		chart:{
			marginTop:100,
			height:504,
			width:1020,
			plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: 0,
			plotShadow: false,
			zIndex:-1,
			style:{
			},
			backgroundColor: 'rgba(0,0,0,0)',
			/*events: {
                load: onDraw,
                resize: onDraw
            }*/
		},
		title:{
			text:'',
			align:'center',
        	//verticalAlign:'bottom',
        	floating:false,
        	enabled:false
		},
		tooltip:{
			enabled:false
		},
        pane: {
            startAngle: 0,
            endAngle: 360,
            background: null
        },
        credits: {
            enabled:false
        },
        /*plotOptions: {
            series: {
                animation: 2000
            },
            credits: {
               enabled:false,
               text:"",
               href:""
            },
            gauge: {
                dial: {
                    backgroundColor: '#000000',
                    radius: '100%',
                    baseWidth: 100,
                    borderColor: '#000000',
                    borderWidth: 0,
                    topWidth: 1,
                    baseLength: '10%',
                    rearLength: '20%'
                },
                pivot: {
                    backgroundColor: '#000000',
                    radius: 10
                },
                dataLabels: {
                    enabled: false
                }
            }
        },*/
        mapNavigation: {
            enabled: false,//设置放大
            buttonOptions: {
                verticalAlign: 'bottom'
            }
        },
        subtitle:{
        	text:'',
        	align:'center',
        	//verticalAlign:'bottom',
        	floating:false
        },
        legend:{
        	//enabled:true,
        	enabled:false,
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            symbolWidth:10,
            symbolPadding:2,
            itemDistance:4
        },
        /*drilldown: {
            activeDataLabelStyle: {
                color: '#FFFFFF',
                textDecoration: 'none',
                textShadow: '0 0 3px #000000'
            },
            drillUpButton: {
                relativeTo: 'spacingBox',
                position: {
                    x: 10,
                    y: 26
                }
            }
        },*/
        series : [{
            data : group,
            name: '地区',
            dataLabels: {
                enabled: true,
                format: '{point.name}'
            }
        },{
            data : group1,
            name: '期末实有户数',
            dataLabels: {
                enabled: true,
                useHTML:true,
                format: '<div style="font-family:Arial,Helvetica,sans-serif,SimSun;font-weight:normal;"><span style="width:4px;height:0.8px;background-color:#434348;color:#434348;">[][]</span>{point.properties.valuesum}</div>'
            }
        },{
            data : group2,
            name: '已办业务量',
            dataLabels: {
                enabled: true,
                useHTML:true,
                format: '<div style="font-family:微软雅黑,宋体;font-weight:normal;"><span style="width:4px;height:0.8px;background-color:#90ED7D;color:#90ED7D;">[][]</span>{point.properties.valuemission}</div>'
            }
        },{
            data : group3,
            name: '最新申办量',
            dataLabels: {
                enabled: true,
                useHTML:true,
                format: '<div style="font-family:微软雅黑,宋体;font-weight:normal;font-size:12px;"><span style="width:4px;height:0.8px;background-color:#F7A35C;color:#F7A35C;">[][]</span>{point.properties.valueioc}</div>'
            }
        }/*,{
            name: '直属局(期末实有户数'+GDfinalReal+'户 已办业务量'+GDdoBusiness+'笔 最新申办量'+GDyesDayBid+'笔)',
            dataLabels: {
                enabled: true,
                useHTML:true,
                format: '<div style="font-family:Arial,Helvetica,sans-serif,SimSun;font-weight:normal;"><span style="width:4px;height:0.8px;background-color:#434348;color:#434348;">[][]</span>{point.properties.valuesum}</div>'
            }
        }*/] 
	},function(e){
		chart=e;
	 	//var categories=["横琴","顺德"];
 		//onCharts(categories,data1,data2,data3);
	
		//$('#highcharts-2').css('height','530px');
		//$('#highcharts-2 svg').css('height','530px');
		//$('#container #highcharts-2 svg').css('padding-top','80px');
		$('#highcharts-0').css('overflow','inherit');
		onDraw();
		
		  chart.renderer.label('<span style="margin-left:-130px;float:left;text-align:left;font-family:微软雅黑,宋体;"><span style="width:4px;height:0.8px;background-color:#434348;color:#434348;">[][]</span>期末实有户数&nbsp&nbsp&nbsp&nbsp</span>',
0, 0, 'rect',20, 0,true)
          .css({
              color: '#333333'
          })
          .attr({
              padding: 8,
              r: 5,
              zIndex: 6
          })
          .add();
		  
		  
		  
		  chart.renderer.label('<span style="margin-left:-50px;float:left;margi-left:3px;text-align:left;font-family:微软雅黑,宋体;"><span style="width:4px;height:0.8px;background-color:#F7A35C;color:#F7A35C;">[][]</span>最新申办&nbsp&nbsp&nbsp&nbsp</span>',
				  24, 0, 'rect',60, 0,true)
				            .css({
				                color: '#333333'
				            })
				            .attr({
				                padding: 8,
				                r: 5,
				                zIndex: 6
				            })
				            .add();
		  
		  
		  chart.renderer.label('<span style="margin-left:10px;float:left;margi-left:3px;text-align:left;font-family:微软雅黑,宋体;"><span style="width:4px;height:0.8px;background-color:#90ED7D;color:#90ED7D;">[][]</span>已办业务量</span>',
			48, 0, 'rect',100, 100,true)
				            .css({
				                color: '#333333'
				            })
				            .attr({
				                padding: 8,
				                r: 5,
				                zIndex: 6
				            })
				            .add();
		  chart.renderer.label('<span style="margin-left:10px;float:left;margin-top:-2px;margi-left:3px;text-align:left;font-family:微软雅黑,宋体;font-size:15px;">期末实有'+finalReal+'户   最新申办'+yesDayBid+'笔 已办业务'+doBusiness+'笔</span>',
					150, 0, 'rect',140, 100,true)
						            .css({
						                color: '#333333'
						            })
						            .attr({
						                padding: 8,
						                r: 5,
						                zIndex: 6
						            })
						            .add();

	});
}

var renderer;
//自贸区柱状图
function  onCharts(categories,data1,data2,data3){//横琴，顺德直线图
	var charts= new Highcharts.Chart({
		chart: {
	        renderTo: "container", 
	        type: 'column',
	        height:400,
	        width:260,
	        zIndex:0,
	        backgroundColor: 'rgba(0,0,0,0)',
	        plotBorderColor: '#346691',
            plotBorderWidth: 2
	    }, 
	    title: {
            text: '自贸区统计图'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
        	id: 'x-axis1',
        	categories:categories,//x轴
            crosshair: true
        },
        yAxis: {
        	id:'y-yaxis',
        	allowDecimals:false,
        	endOnTick:true,
            title: {
                text: ''//y轴标题 //办理业务数
            },
            labels: {
				formatter:function(){
				    return this.value;
				}
			}
        },
        tooltip: {
        	enabled:true
        },
        credits: {
        	enabled:false,
        	text:"",
            href:""
        },
        legend: {
            layout: 'horizontal',
            floating: true,
            align: 'left',
            verticalAlign: 'bottom',
            x: 50,
            y: 60,
            symbolPadding: 20,
            symbolWidth: 50,
            enabled:false
        },
        plotOptions: {
        }
	});
	charts.addSeries({
        name: ["期末实有户数"],
        type: 'column',
        color: '#08F',
        yAxis: 'y-yaxis',
        xAxis: 'x-axis1',
        data: data1,
        zIndex:1,
        market:{
        	height:10,
        	width:10
        }
    });
	charts.addSeries({
        name: ["已办业务量"],
        type: 'column',
        color: '#E8F',
        yAxis: 'y-yaxis',
        xAxis: 'x-axis1',
        data: data2,
        zIndex:1,
        market:{
        	height:10,
        	width:10
        }
	});	
	charts.addSeries({
		name: ["最新申办量"],
	    type: 'column',
	    color: '#38F',
	    yAxis: 'y-yaxis',
	    xAxis: 'x-axis1',
	    data:data3,
	    zIndex:1,
	    market:{
	    	height:10,
	    	width:10
	    }
	});
}

function onDraw(){//画线
	chart.renderer.path(["M", 616,201, "L",700,116,960,116]).attr({//河源
        'stroke-width': 1,
      	stroke: 'gray',
      	zIndex:10
	}).add();
	chart.renderer.path(["M", 530,150, "L",455,120,155,120]).attr({//韶关
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
	chart.renderer.path(["M", 465,176, "L",416,152,136,152]).attr({//清远
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 426,231, "L",378,193,118,193]).attr({//肇庆
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 510,260, "L",365,220,88,220]).attr({//广州
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 385,286, "L",324,274,60,274]).attr({//云浮
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 334,345, "L",300,316,40,316]).attr({//茂名
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 388,354, "L",276,355,20,355]).attr({//阳江
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 280,413, "L",252,393,0,393]).attr({//湛江
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 470,272, "L",354,247,84,247]).attr({//佛山
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 446,353, "L",574,495,954,495]).attr({//江门
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 525,346, "L",634,461,951,461]).attr({//珠海
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 698,195, "L",762,150,1026,150]).attr({//梅州
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 748,213, "L",796,185,1060,185]).attr({//潮州
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M",753,252, "L",800,232,1060,232]).attr({//汕头
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M",510,320, "L",680,430,990,430]).attr({//中山
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 537,288, "L",718,369,976,369]).attr({//东莞
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M", 588,275, "L",770,335,1030,335]).attr({//惠州
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M",655,280, "L",780,306,1040,306]).attr({//汕尾
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M",537,300, "L",680,399,990,399]).attr({//深圳
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    chart.renderer.path(["M",685,263, "L",828,268,1072,268]).attr({//揭阳
        'stroke-width': 1,
      	 stroke: 'gray',
      	 zIndex:10
    }).add();
    /*try{
    	  chart.reflow =true;
    }catch(e){
        for(var p in e){
            document.writeln(p + "=" + e[p]);
        }
    }*/
}

function goHome(){
	window.open("http://www.gwssi.com.cn/");
}

$(window).resize(function() {
	$('#highcharts-2').css('height','530px');
	$('#highcharts-2 svg').css('height','530px');
	$('#container #highcharts-2 svg').css('padding-top','80px');
	//padding-top 80px
});

