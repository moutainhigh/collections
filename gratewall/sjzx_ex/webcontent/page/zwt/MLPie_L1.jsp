<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html >
    <head>
        <title>Chart</title>

        <script type="text/javascript" src="/page/zwt/Charts/jquery.min.js"></script>
        <script type="text/javascript" src="/page/zwt/js/echarts-all.js" charset="utf-8"></script>
        <!-- <script type="text/javascript" src="/page/zwt/js/echarts-plain-map.js"></script>
         -->
        <script type="text/javascript" src="/page/zwt/js/MLPie_L1.js"></script>
        <script type="text/javascript" src="/page/zwt/js/MLPie_L1_ECharts.js"></script>

        <style type="text/css">
            body { background:#ebf0f5;}
            .tbss{border-collapse:collapse;}
            .tbss td, .tbss th{
            	height: 22px;
            	line-height: 22px;
            	border: 1px solid #999;
            	font-size: 12px;
            	color: #333;
            	padding:1px 3px;
            }
            .tbss th{background-color:#8AB0D7; border:1px solid #8AB0D7; color:white;}
            .tbtitle{text-align:left; font-size:12px;  font-weight: bold;}
        </style>
        <script type="text/javascript">
       
         function initTable(){
         //table1
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="服务对象";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[0].object_num+"个";
         tr.appendChild(td); 
         document.getElementById("table1").appendChild(tr);
         //tr2
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="共享服务个数";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[0].service_num+"个";
         tr.appendChild(td); 
         document.getElementById("table1").appendChild(tr);
         
         //table2
         //tr1
         if(table_obj[1].WebService!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="WebService服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].WebService+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].数据库!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="数据库服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].数据库+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].FTP!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="FTP服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].FTP+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].SOCKET!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="SOCKET服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].SOCKET+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].JMS消息!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="JMS消息服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].JMS消息+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         
         //table3
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="批量服务";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[2].批量+"个";
         tr.appendChild(td); 
         document.getElementById("table3").appendChild(tr);
         //tr
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="单条服务";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[2].单条+"个";
         tr.appendChild(td); 
         document.getElementById("table3").appendChild(tr);
         
         //table4
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="共享数据量";
         tr.appendChild(td);
         var td = document.createElement("td");
         
         td.innerHTML="共"+table_obj[3].共享数据量.this_share_total+"条";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[3].共享数据量.last_share_total+"条";
         tr.appendChild(td); 
         document.getElementById("table4").appendChild(tr);
         //tr2
          var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="共享次数";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[3].共享次数.this_exec_counts+"次";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[3].共享次数.last_exec_counts+"次";
         tr.appendChild(td); 
         document.getElementById("table4").appendChild(tr);
         
         //table5
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="批量共享数据量";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[4].批量.this_share_total+"条";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[4].批量.last_share_total+"条";
         tr.appendChild(td); 
         document.getElementById("table5").appendChild(tr);
         //tr2
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="单条共享数据量";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[4].单条.this_share_total+"条";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[4].单条.last_share_total+"条";
         tr.appendChild(td); 
         document.getElementById("table5").appendChild(tr);
         newPie();
         }
         
         function newPie(){
        	 var chartPie = echarts.init(document.getElementById('chartdiv'));
             var option111 = {
             	    tooltip : {
             	        trigger: 'item',
             	        formatter: "{a} <br/>{b} : {c} ({d}%)"
             	    },
             	    legend: {
             	        selectedMode: false, 
             	        orient : 'vertical',
             	        x : 'left',
             	        data:[""]
             	    },
             	    toolbox: {
             	        show : true,
             	        feature : {
             	            mark : true,
             	            dataView : {readOnly: false},
             	            restore : true,
             	            saveAsImage : false
             	        }
             	    },
             	    calculable : false,
             	    series : [
             	        {
             	            name:'服务对象类型',
             	            type:'pie',
             	            selectedMode: 'single',
             	            radius : [60, 140],
             	            itemStyle : {
             	                normal : {
             	                    label : {
             	                        position : 'inner'
             	                    },
             	                    labelLine : {
             	                        show : false
             	                    }
             	                }
             	            },
             	            data:[
             	                {value:1100, name:'区县分局'},
             	                {value:1200, name:'内部系统'},
             	                {value:1200, name:'外部系统'}
             	            ]
             	        },
             	        {
             	            name:'服务对象',
             	            type:'pie',
             	            radius : [170, 210],
             	            data:[
             	                {value:100, name:"东城分局"},
             	                {value:100, name:"西城分局"},
             	                {value:100, name:"朝阳分局"},
             	                {value:100, name:"海淀分局"},
             	                {value:100, name:"丰台分局"},
             	                {value:100, name:"通州分局"},
             	                {value:100, name:"顺义分局"},
             	                {value:100, name:"昌平分局"},
             	                {value:100, name:"怀柔分局"},
             	                {value:100, name:"密云分局"},
             	                {value:100, name:"延庆分局"},
             	                {value:100, name:"案件系统"},
             	                {value:100, name:"12315系统"},
             	                {value:100, name:"登记系统"},
             	                {value:100, name:"信用系统"},
             	                {value:100, name:"网上登记"},
             	                {value:100, name:"网格监管"},
             	                {value:100, name:"市场监管"},
             	                {value:100, name:"农资监管"},
             	                {value:100, name:"广告系统"},
             	                {value:100, name:"企业E网通"},
             	                {value:100, name:"电子商务"},
             	                {value:100, name:"商品监管"},
             	                {value:100, name:"地税局"},
             	                {value:100, name:"食品监管"},
             	                {value:100, name:"手机查询用户"},
             	                {value:100, name:"住建委"},
             	                {value:100, name:"私营个体经济网"},
             	                {value:100, name:"国家工商总局"},
             	                {value:100, name:"西城信息办"},
             	                {value:100, name:"高法"},
             	                {value:100, name:"经信委资源中心"},
             	                {value:100, name:"海淀区信息办"},
             	                {value:100, name:"质监局"},
             	                {value:100, name:"国税局"}
             	            ]
             	        }
             	    ]
             	};
             chartPie.setOption(option);
             chartPie.on(echarts.config.EVENT.PIE_SELECTED, function(param){
            	    var selected = param.selected;
            	    var serie;
            	    var str = '当前选择： ';
            	    
            	    for (var idx in selected) {
            	        serie = option.series[idx];
            	        for (var i = 0, l = serie.data.length; i < l; i++) {
            	            if (selected[idx][i]) {
            	                str += '【系列' + idx + '】' + serie.name + ' : ' + 
            	                       '【数据' + i + '】' + serie.data[i].name + ' ';
            	                 
            	                document.getElementById('form1').action=serie.data[i].link;
            	            }
            	        }
            	    }
            	    document.getElementById('form1').submit();
            	    //alert(str);
            	   // document.getElementById('wrong-message').innerHTML = str;
            	})
         }
         
        </script>

    </head>
    <body onload="initTable()">
    <table width="95%" align="center" cellpadding="0" cellspacing="0">
    <tr>
    <td width="65%"><div id="chartdiv" style="height:500px;width: 100%;">Chart will load here</div></td>
    <td>
    	 <div class="tbtitle">总体分布情况 </div>
          <Table id="table1" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0" >
            <tr><td>服务对象类型</td><td>共3大类</td></tr>
            
          </Table><Br/>
                      <div class="tbtitle">共享服务分布</div>
          <Table id="table2" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>统计指标</th>
              <th>条数</th>
              
            </tr>
          </Table><br>
          <Table id="table3" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>统计指标</th>
              <th>条数</th>
            </tr>
            
          </Table><Br/>
            <div class="tbtitle">共享服务情况</div>
          <Table id="table4" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0" >
            <tr>
              <th>统计指标</th>
              <th>当月</th>
              <th>上月</th>
            </tr>
            
          </Table><br>
          <Table id="table5" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>统计指标</th>
              <th>当月</th>
              <th>上月</th>
            </tr>
           
          </Table>
    </td>
    </tr>
    </table>
<form action="/txn53000211.do?select-key:service_targets_id=1735dcb0036c477aad1d96aa6aa74db9&select-key:count_type=00" method="post" id="form1" name="form1" target="_self">
  <input type="hidden" name="select-key:service_targets_id" id="select-key:service_targets_id"/>
  <input type="hidden" name="select-key:service_targets_name" id="select-key:service_targets_name"/>
</form>
    </body>
</html>