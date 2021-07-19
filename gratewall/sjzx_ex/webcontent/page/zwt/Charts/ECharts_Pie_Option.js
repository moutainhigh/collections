var map={};
var taskmap={};
var timesmap={};
var amountmap={};
var option = {
	title : {
        text: '采集任务数据分布'
    },
	tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        data:['']
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
            radius : [40, 100],
            tooltip : {
                trigger: 'item',
                formatter: function(params){
                	var res=map[params[1]];
                	return res;
                }
            },
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
            data:['']
        },
       
        {
            name:'服务对象',
            type:'pie',
            radius : [120, 160],
            tooltip : {
                trigger: 'item',
                formatter: function(params){
                	//console.log(params[0]+"--"+params[1]+"--"+params[2]+"--"+params[3]);
                	
                	var res=map[params[1]];
                	return res;
                }
            },
            data:['']
        }
    ]
};


    function onPieSelect(param){
        var selected = param.selected;
        var serie;
        var str = '当前选择： ';
        for (var idx in selected) {
            serie = option.series[idx];
            for (var i = 0, l = serie.data.length; i < l; i++) {
                if (selected[idx][i]) {
                	window.location='/txn53000212.do?select-key:service_targets_id='+serie.data[i].sid
                		+'&select-key:startTime='+$('#startDay').val()
       				 	+'&select-key:endTime='+$('#endDay').val();
                	
                }
            }
        }
        //console.log(str);
    }

                    