// 页面初始化。
$(function() {
	loadingPDF();
	eventBinding();
});



//加载初始化要审查的pdf
function loadingPDF() {
	var gid = jazz.util.getParameter('gid') || "";

	$.ajax({
		url : '../../../req/getPdfRelativePath.do',
		data : {
			gid : gid
		},
		dataType : "json",
		success : function(data) {
			var row = data.data.result;
			var pdfURL = "";
			var pageNo = "";
			if (row) {
				pdfURL = row.docRelativePath || "";
				pageNo = row.pageNo || "";
			}
			var options = {
				pdfOpenParams : {
					navpanes : 0,
					toolbar : 0,
					statusbar : 0,
					view : "FitV",
					page : pageNo
				}
			};
			PDFObject.embed(pdfURL, "#PDF", options);

		},
		error : function() {
			jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
		}

	});
}


//事件绑定
function eventBinding() {
	var gid = jazz.util.getParameter('gid') || "";
	$("#pass").on("click", function() {
		$.ajax({
			url : "",
			data : {
				gid : gid
			},
			dataType : "json",
			beforeSend : function(data) {

			},
			success : function(data) {

			},
			error : function(data) {
				jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
			}

		});
	});

	$("#nopass").on("click", function() {
		var reason = $("#suggestion").val();
		$.ajax({
			url : "",
			data : {
				gid : gid
			},
			dataType : "json",
			beforeSend : function(data) {

			},
			success : function(data) {
				var result = $.trim(data);
				$("#suggestion").val("");//清空当前已经输入的审核意见
				$("#hsSuggestion").val(result);//从 数据库中查询当前保存成功的审查意见

			},
			error : function(data) {
				jazz.info("数据断开连接，请重新尝试连接", function() {

				});
			}
		});
	});
}
