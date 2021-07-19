function myAjax(url){
	$.ajax({
		url : url,
		data : {},
		success : function(data) {
			var dataSet = data.remark;
			loadMyChart(dataSet);
		}

	});
	
}


function loadMyChart(dataSet) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main'));
	if (!$.isEmptyObject(dataSet)) {
		var month = [];
		var monthCount = [];
		for (var i = 0; i < dataSet.length; i++) {
			month.push(dataSet[i].addtime.split(" ")[0]);
			monthCount.push(dataSet[i].counts);
		}
		// 指定图表的配置项和数据
		var option = {
			xAxis : {
				data : month
			},
			yAxis : {},
			series : [ {
				name : '个数',
				type : 'bar',
				data : monthCount,
				itemStyle : {
					normal : {
						color : function(params) {
							// build a color map as your need.
							var colorList = [ '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B', '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD', '#D7504B', '#C6E579',
									'#F4E001', '#F0805A', '#26C0C0' ];
							return colorList[params.dataIndex]
						},
						label : {
							show : true,
							position : 'top',
							color : '#000'
						}
					}
				}
			} ],
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	} else {
		alert("数据有问题")
	}
}