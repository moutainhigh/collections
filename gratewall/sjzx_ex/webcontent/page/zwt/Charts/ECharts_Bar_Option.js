var option  = {
	    title : {
	        text: ''
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['']
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : true,
	            dataView : {readOnly: false},
	            magicType:['line', 'bar'],
	            restore : true,
	            saveAsImage : false
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'value',
	            boundaryGap : [0, 0.05],
	        	name : '万条'
	        }
	    ],
	    yAxis : [
	        {
	            type : 'category',
	            data : ['']
	        }
	    ],
	    series : [
	        {
	            name:'',
	            type:'bar',
	            data:['']
	        }
	    ]
	};