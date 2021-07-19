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
	src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"
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
		    series:series
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
            var data="llllllll";
            var name="test";
            openPostWindow(e.options.url, data, name);
}


	function openPostWindow(url, data, name)  
			      {  
			          var tempForm = document.createElement("form");  
			          tempForm.id="tempForm1";  
			          tempForm.method="post";  
			          tempForm.action=url;  
			          tempForm.target=name;  
			          
			          var hideInput = document.createElement("input");  
			          hideInput.type="hidden";  
			          hideInput.name= "content";
			          hideInput.value= data;
			          tempForm.appendChild(hideInput);   
			          
			          if (window.attachEvent) { 
			             tempForm.attachEvent("onsubmit",function(){ openWindow(name); });
			            } else if (window.addEventListener) { 
			             tempForm.addEventListener("onsubmit",function(){ openWindow(name); },false);
			            }       
			          document.body.appendChild(tempForm);  
			          
			           if (window.attachEvent) { 
			                tempForm.fireEvent("onsubmit");
			            } else if (window.addEventListener) { 
			          		 var event = document.createEvent("HTMLEvents");
					            event.initEvent("onsubmit", true, true);
					            tempForm.dispatchEvent(event);
			            }  
			       
			          tempForm.submit();
			          document.body.removeChild(tempForm);
			     }
     
 function openWindow(name)  
      {  
          window.open('about:blank',name,'top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');   
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
			transdt.push(parseFloat(strList[j]));
		}
		
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
	
	//--------------------------------时间控件----------------------
    /**
        选择时间的输入框被选中时，创建datepicker控件
        根据设置不同dateFmt 格式，使空间支持选择日，周，月，季，年
    */
    function createWdatePicker() {
  
        var type = $("#selType").val();
  
        if (type == 1) {//按日
            WdatePicker({
                readOnly : true,
                dateFmt : 'yyyy-MM-dd',
                onpicked : pickTime,
                isShowWeek : true,
                maxDate : '%y-%M-%d'
            });
        } else if (type == 2) {//按周
            WdatePicker({
                readOnly : true,
                dateFmt : 'yyyy-MM-dd',
                onpicked : pickTime,
                isShowWeek : true,
                maxDate : '%y-%M-%d'
            });
        } else if (type == 3) {//按月
            WdatePicker({
                readOnly : true,
                dateFmt : 'yyyy-MM',
                onpicked : pickTime,
                isShowWeek : true,
                maxDate : '%y-%M-%d'
            });
        } else if (type == 4) {//按季
            WdatePicker({
                readOnly : true,
                dateFmt : 'yyyy-MM',
                onpicked : pickTime,
                isShowWeek : true,
                maxDate : '%y-%M-%d'
            });
        } else if (type == 5) {//按年
            WdatePicker({
                readOnly : true,
                dateFmt : 'yyyy',
                onpicked : pickTime,
                isShowWeek : true,
                maxDate : '%y-%M-%d'
            });
        }
  
    };
     
    /**
        控件时间选中后，onpicked 事件对应的方法
        根据不同的选择类型：日周月季年，计算最终选中的时间段
    */
    function pickTime() {
        var type = $("#selType").val();
        if (type == 1) {//按日
            //获取控件选择的时间，格式是根据设置的dateFmt : 'yyyy-MM-dd'
            var datetime = $dp.cal.getDateStr();
            $("#showstarttime").val(datetime + " 00:00:00");
            $("#showendtime").val(datetime + " 23:59:59");
        } else if (type == 2) {//按周
            var datetime = $dp.cal.getDateStr();
            //获取星期几，控件提供的方法
            var dstart = -$dp.cal.getP('w', 'w');
            var dend = dstart + 6;
            //计算一周的开始日期和结束日期，这个方法文档说返回的是字符串，但是实际中返回的是控件定义的时间对象，所以后面得自己转换成字符串
            var datestart = $dp.$DV(datetime, {
                d : dstart
            });
            var dateend = $dp.$DV(datetime, {
                d : dend
            });
  
            $("#showstarttime").val(dateToString(datestart) + " 00:00:00");
            $("#showendtime").val(dateToString(dateend) + " 23:59:59");
        } else if (type == 3) {//按月
            var y = $dp.cal.getP('y', 'yyyy');
            var M = $dp.cal.getP('M', 'M');
  
            var d = new Date(y, M, 0);
  
            var datestart = {};
            datestart.y = y;
            datestart.M = M;
            datestart.d = 1;
            var dateend = {};
            dateend.y = y;
            dateend.M = M;
            //获取月的最后一天
            dateend.d = d.getDate();
  
            $("#showstarttime").val(dateToString(datestart) + " 00:00:00");
            $("#showendtime").val(dateToString(dateend) + " 23:59:59");
  
        } else if (type == 4) {//按季
            var y = $dp.cal.getP('y', 'yyyy');
            var M = $dp.cal.getP('M', 'M');
  
            //计算季度的开始月份和结束月份
            var startM = parseInt((M - 1) / 3) * 3 + 1;
            var endM = startM + 2;
  
            var d = new Date(y, endM, 0);
  
            var datestart = {};
            datestart.y = y;
            datestart.M = startM;
            datestart.d = 1;
            var dateend = {};
            dateend.y = y;
            dateend.M = endM;
            //获取最后一个月的最后一天
            dateend.d = d.getDate();
  
            $("#showstarttime").val(dateToString(datestart) + " 00:00:00");
            $("#showendtime").val(dateToString(dateend) + " 23:59:59");
  
        } else if (type == 5) {//按年
  
            var y = $dp.cal.getP('y', 'yyyy');
            //这里可以直接字符串拼凑写死，但是为了格式统一，还是创建对象在转化吧
            var datestart = {};
            datestart.y = y;
            datestart.M = 1;
            datestart.d = 1;
            var dateend = {};
            dateend.y = y;
            dateend.M = 12;
            dateend.d = 31;
  
            $("#showstarttime").val(dateToString(datestart) + " 00:00:00");
            $("#showendtime").val(dateToString(dateend) + " 23:59:59");
        }
  
    };
    /**
        控件返回的时间对象转换成字符串输出
        控件的时间对象有y,M,d,H,m,s属性，分别返回年，月，日，时，分，秒
    */
    function dateToString(date) {
        var strdate = "";
        strdate = strdate + date.y + "-";
        var M = date.M >= 10 ? date.M : ("0" + date.M);
        strdate = strdate + M;
        var d = date.d >= 10 ? date.d : ("0" + date.d);
        strdate = strdate + "-" + d;
        return strdate;
    };
    
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