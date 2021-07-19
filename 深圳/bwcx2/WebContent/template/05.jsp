<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.myMore {
	position: absolute;
    right: -7px;
    top: 15px;
    background: url(statics/css/02more-bg.png);
    background-position-x: 100px;
    width: 100px;
    text-align: center;
    height: 34px;
    line-height: 34px;
}
</style>
<!--05影像资料-->
<div class="modb mb50">
	<div style="position: relative;">
		<h2 class="hd">
			<a href="more.jsp?cid=3" class="colTit" target="_blank">【&nbsp;&nbsp;&nbsp;影像资料&nbsp;&nbsp;&nbsp;】</a>
		</h2>
		<a href="more.jsp?cid=5"  class='myMore' target="_blank">更多>></a>
	</div>
	<div class="bd mb15" id="data05">
		<div class="fl">

		</div>



	</div>
	<!-- <a href="more.jsp?cid=5" class="hdMore" target="_blank">更多>></a> -->
	
	<script type="text/javascript">
	
	$.ajax({
		url : "getPubDocusWithThumb.do",
		type : "post",
		//async:false,//取消异步请求
		data : {
			pageSize : 4,
			pageNo : 1,
			cid : 5,
			thum : 1,
		},
		dataType : "json",
		beforeSend : function(data) {

		},
		success : function(data) {
			var _tm = "";
			var list = data.list;
			for (var i = 0; i < list.length; i++) {
				_tm += " <div class='imgTxtNews2' >";
				_tm += "<a href='detail.jsp?docid="+list[i].docid+"' class='tit' target='_blank' style='font-size:16px' >"+list[i].title+" </a>";//style='font-size:16px'
					_tm += "<div class='clear'>";
					_tm += "<a href='detail.jsp?docid="+list[i].docid+"' class='img' target='_blank'><img src='"+list[i].thumburl+"' /></a>";
					_tm += "<div class='txtNews'>";
					_tm += "	<p class='txt'>";
					_tm += "	"+list[i].contentmeta +"<a href='detail.jsp?docid="+list[i].docid+"' class='more' target='_blank'>[详细]</a>";
					_tm += "	</p>";
					_tm += "</div>";
					_tm += "</div>";
					_tm += "</div>";
			}
			$("#data05").append(_tm);
		}

	});
	
	</script>
</div>