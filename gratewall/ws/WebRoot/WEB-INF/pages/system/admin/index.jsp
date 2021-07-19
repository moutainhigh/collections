<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="pub.jsp"%>
<body>
	<style>
 body {
	overflow: hidden;
}
 
.models {
	margin-top: 54px;
}
</style>
	<%@include file="top.jsp"%>
	<div class='container-fluid models'>
		<div class="row clearfix">
			<div class="col-md-1 column">
				<%@include file="leftSider.jsp"%>
			</div>
			<div class="col-md-11 column">
				<%@include file="rightSider.jsp"%>
			</div>
		</div>
	</div>

	<script>
		$(".leftWrap li a").on("click", function() {
			var url = $(this).data("url");
			$("#mainFrame").attr("src", url);
		});

		function cmainFrame() {
			var hmain = document.getElementById("mainFrame");
			var bheight = document.documentElement.clientHeight;
			hmain.style.width = '100%';
			hmain.style.height = (bheight - 51) + 'px';
			//var bkbgjz = document.getElementById("bkbgjz");
			//bkbgjz .style.height = (bheight  - 41) + 'px';
			$(".leftWrap").css("height",bheight-51+"px");
		}
		cmainFrame();
		window.onresize = function() {
			cmainFrame();
		};
	</script>
</body>
</html>
