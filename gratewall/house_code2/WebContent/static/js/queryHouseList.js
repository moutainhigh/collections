//ajax获取后台数据
function initHouseTable($dom, houseId,floor) {
	var _this = $($dom);
	var codeId = _this.data("code")
	var tbody = "";
	$.ajax({
		type : 'POST',
		dataType : "json",
		url : "../houseCode/getHouseInfo.do",// 请求的action路径页面
		data : {
			page : 1,
			buildingCode : houseId,
			floor:floor
		},
		beforeSend:function(){
			var _html = "<tr class='active'><td colspan='3' style='text-align:center'>查询中，请稍后。。。。</td></tr>";
			$("#list2 tbody").html(_html);
		},
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后处理函数。
			var dataCount = data.data[1].data;
			var data = data.data[0].data.rows;
			$("#list2 tbody").empty();
			var _html = "";
			if (data.length == 0) {
				return;
			}
			for (var i = 0; i < data.length; i++) {
				if (i % 2 == 0) {
					_html += "<tr class='active'>";
					_html += "<td>" + data[i].rn + "</td>";
					_html += "<td>" + data[i].houseId + "</td>";
					_html += "<td>" + data[i].houseAdd + "</td>";
					_html += "</tr>";
				} else {
					_html += "<tr class='success'>";
					_html += "<td>" + data[i].rn + "</td>";
					_html += "<td>" + data[i].houseId + "</td>";
					_html += "<td>" + data[i].houseAdd + "</td>";
					_html += "</tr>";
				}

			}
			$("#list2 tbody").append(_html);

			var pageCount = getPageCount(dataCount); // 取到pageCount的值
			var currentPage = 1; // 得到currentPage
			var options = {
				bootstrapMajorVersion : 3, // 版本
				currentPage : currentPage, // 当前页数
				totalPages : pageCount, // 总页数
				numberOfPages : 5,
				itemTexts : function(type, page, current) {
					switch (type) {
					case "first":
						return "首页";
					case "prev":
						return "上一页";
					case "next":
						return "下一页";
					case "last":
						return "末页";
					case "page":
						return page;
					}
				},// 点击事件，用于通过Ajax来刷新整个list列表
				onPageClicked : function(event, originalEvent, type, page) {
					$.ajax({
						url : "../houseCode/getHouseInfo.do",
						type : "Post",
						dataType : "json",
						data : {
							page : page,
							buildingCode : houseId,
							floor:floor
						},
						success : function(data) {
							var data = data.data[0].data.rows;
							$("#list2 tbody").empty();
							var _html = "";
							for (var i = 0; i < data.length; i++) {
								if (i % 2 == 0) {
									_html += "<tr class='active'>";
									_html += "<td>" + data[i].rn + "</td>";
									_html += "<td>" + data[i].houseId + "</td>";
									_html += "<td>" + data[i].houseAdd + "</td>";
									_html += "</tr>";
								} else {
									_html += "<tr class='success'>";
									_html += "<td>" + data[i].rn + "</td>";
									_html += "<td>" + data[i].houseId + "</td>";
									_html += "<td>" + data[i].houseAdd + "</td>";
									_html += "</tr>";
								}

							}
							$("#list2 tbody").append(_html);
						}
					});
				}
			};
			$('#example2').bootstrapPaginator(options);
		}
	});
}

function getPageCount(dataCount) {
	var pageCount = 0;
	if (dataCount == 0) {
		pageCount = 0;
	} else if (dataCount % 10 == 0) {
		pageCount = dataCount / 10;
	} else {
		pageCount = Math.ceil(dataCount / 10);
	}
	return pageCount;
}