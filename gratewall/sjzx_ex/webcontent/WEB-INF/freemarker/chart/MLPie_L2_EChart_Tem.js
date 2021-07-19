 {
    title : {
        text: '${sys_name}[${date}]共享服务分布',
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['${y_name}']
    },
    toolbox: {
        show : true,
        feature : {
            mark : true,
            dataView : {readOnly: false},
            magicType:['line', 'bar'],
            restore : true,
            saveAsImage : true
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'value',
            boundaryGap : [0, 0.01]
        }
    ],
    yAxis : [
        {
            type : 'category',
            data : [
            <#list sys as x>"${x.NAME}",</#list>
            ""
            ]
        }
    ],
    series : [
        {
            name:'${y_name}',
            type:'bar',
            data:[<#list sys as x>{value:${x.TOTAL},link:'${x.LINK3}'},</#list>]
        }
    ]
};  