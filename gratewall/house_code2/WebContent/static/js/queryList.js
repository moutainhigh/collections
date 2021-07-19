//ajax获取后台数据
function initTable($dom, dataFrom) {
	var tbody = "";
	$.ajax({
		type : 'POST',
		dataType : "json",
		url : "../houseCode/getBuildingInfo.do",// 请求的action路径页面
		data : {
			page : 1,
			qu : dataFrom.qu,
			jieDao : dataFrom.jieDao,
			sheQu : dataFrom.sheQu,
			lu : dataFrom.lu,
			jianZhuWu : dataFrom.jianZhuWu,
			louDong:dataFrom.louDong,
			louCeng:dataFrom.louCeng,
			fangHao:dataFrom.fangHao,
			FangWuBianMa:dataFrom.FangWuBianMa,
			FangWuDiZhi:dataFrom.FangWuDiZhi,
			imageCode:dataFrom.imageCode
		},
		error : function() {// 请求失败处理函数
			alert('请求失败');
			$dom.html("查询");
			$dom.attr("onclick", "query(this)");
			$dom.removeClass("nonAllow");
		},
		success : function(data) { // 请求成功后处理函数。
			$dom.attr("onclick", "query(this)");
			$dom.removeClass("nonAllow");
			$dom.html("查询");
			var toMany  =  data.data[0].data;
			if(toMany=="limit"){
				alert("查询次数过多，请过一段时间再来");
			}else if(toMany=="vcodes"){
				alert("验证码不正确");
			}else{
				var dataCount = data.data[1].data;
				var tokens = data.data[2].data;
				var data = data.data[0].data.rows;
				getCode();
				$("#list tbody").empty();
				var _html = "";
				if (data.length == 0) {
					//$dom.attr("onclick", "query(this)");
					if (confirm("没有找到对应的房屋信息，需要进行反馈吗？")) {
						showFeedBack();
						$("#queryPanel").addClass("hide toFee");
					}
					return;
				}
				for (var i = 0; i < data.length; i++) {
					if (i % 2 == 0) {
						_html += "<tr class='active'>";
						_html += "<td>" + data[i].rn + "</td>";
						_html += "<td>" + data[i].district + "</td>";
						_html += "<td>" + data[i].street + "</td>";
						_html += "<td>" + data[i].community + "</td>";
						_html += "<td>" + data[i].road + "</td>";
						_html += "<td>" + data[i].village + "</td>";
						_html += "<td>" + data[i].building + "</td>";
						_html += "<td>" + data[i].floor + "</td>";
						_html += "<td><a href='javascript:;' onclick='showDetail(this)' data-code=\"" +data[i].buildingId + "\" data-floor="+data[i].floor+">房屋信息</a></td>";
						_html += "</tr>";
					} else {
						_html += "<tr class='success'>";
						_html += "<td>" + data[i].rn + "</td>";
						_html += "<td>" + data[i].district + "</td>";
						_html += "<td>" + data[i].street + "</td>";
						_html += "<td>" + data[i].community + "</td>";
						_html += "<td>" + data[i].road + "</td>";
						_html += "<td>" + data[i].village + "</td>";
						_html += "<td>" + data[i].building + "</td>";
						_html += "<td>" + data[i].floor + "</td>";
						_html += "<td><a href='javascript:;' onclick='showDetail(this)' data-code=\"" + data[i].buildingId + "\" data-floor="+data[i].floor+">房屋信息</a></td>";
						_html += "</tr>";
					}

				}
				$("#list tbody").append(_html);

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
							url : "../houseCode/getBuildingInfo.do",
							type : "Post",
							dataType : "json",
							data : {
								page : page,
								qu : dataFrom.qu,
								jieDao : dataFrom.jieDao,
								sheQu : dataFrom.sheQu,
								lu : dataFrom.lu,
								jianZhuWu : dataFrom.jianZhuWu,
								louDong:dataFrom.louDong,
								louCeng:dataFrom.louCeng,
								fangHao:dataFrom.fangHao,
								FangWuBianMa:dataFrom.FangWuBianMa,
								FangWuDiZhi:dataFrom.FangWuDiZhi,
								imageCode:dataFrom.imageCode,
								tokens:tokens
							},
							beforeSend:function(){
								$("#list tbody").empty();
								var _html = "<tr class='active'><td colspan='9'  rowspan='10' style='text-align:center;'>查询中。。。。</td></tr>";
								$("#list tbody").append(_html);
							},
							success : function(data) {
								var data = data.data[0].data.rows;
								$("#list tbody").empty();
								var _html = "";
								for (var i = 0; i < data.length; i++) {
									if (i % 2 == 0) {
										_html += "<tr class='active'>";
										_html += "<td>" + data[i].rn + "</td>";
										_html += "<td>" + data[i].district + "</td>";
										_html += "<td>" + data[i].street + "</td>";
										_html += "<td>" + data[i].community + "</td>";
										_html += "<td>" + data[i].road + "</td>";
										_html += "<td>" + data[i].village + "</td>";
										_html += "<td>" + data[i].building + "</td>";
										_html += "<td>" + data[i].floor + "</td>";
										_html += "<td><a href='javascript:;' onclick='showDetail(this)' data-code=\"" + data[i].buildingId + "\" data-floor="+data[i].floor+">房屋信息</a></td>";
										_html += "</tr>";
									} else {
										_html += "<tr class='success'>";
										_html += "<td>" + data[i].rn + "</td>";
										_html += "<td>" + data[i].district + "</td>";
										_html += "<td>" + data[i].street + "</td>";
										_html += "<td>" + data[i].community + "</td>";
										_html += "<td>" + data[i].road + "</td>";
										_html += "<td>" + data[i].village + "</td>";
										_html += "<td>" + data[i].building + "</td>";
										_html += "<td>" + data[i].floor + "</td>";
										_html += "<td><a href='javascript:;' onclick='showDetail(this)' data-code=\"" + data[i].buildingId + "\" data-floor="+data[i].floor+">房屋信息</a></td>";
										_html += "</tr>";
									}
								}
								$("#list tbody").append(_html);
							}
						});
					}
				};
				$('#example').bootstrapPaginator(options);
			}
			
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