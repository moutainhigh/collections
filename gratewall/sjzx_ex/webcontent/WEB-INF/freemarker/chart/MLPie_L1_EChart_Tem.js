var option = {
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['']
    },
    toolbox: {
        show : true,
        feature : {
            mark : true,
            dataView : {readOnly: false},
            restore : true,
            saveAsImage : true
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
                {value:${qx_share_total}, name:'区县分局',link:'${qx_link2}'},
                {value:${in_share_total}, name:'内部系统',link:'${in_link2}'},
                {value:${out_share_total}, name:'外部系统',link:'${out_link2}'}
            ]
        },
        {
            name:'服务对象',
            type:'pie',
            radius : [170, 210],
            data:[
            	<#list legend_name as z>
            		{value:${z.TOTAL}, name:"${z.NAME}",link:'${z.LINK3}'},
            	</#list>
            ]
        }
    ]
}