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
	<!-- 04方案计划 -->
	<div class="modb mb50">
		<div style="position: relative;">
		<h2 class="hd">
			<a href="more.jsp?cid=3" class="colTit" target="_blank">【&nbsp;&nbsp;&nbsp;方案计划&nbsp;&nbsp;&nbsp;】</a>
		</h2>
		<a href="more.jsp?cid=4"  class='myMore' target="_blank">更多>></a>
	</div>
		<div class="bd mb15">
			<div class="clear">
				<ul class="newsList3" id="data04">
				</ul>
			</div>
		</div>
		<!-- <a href="more.jsp?cid=4" class="hdMore" target="_blank">更多>></a> -->
	</div>
	<script>
			$.ajax({
				url:"getPubDocsByChannel.do",
				type:"post",
				//async:false,//取消异步请求
				data:{pageNo:1,pageSize:12,cid:4},
				dataType:"json",
				beforeSend:function(data){
					
				},
				
				success:function(data){
					var _tm = "";
					var list = data.list;
					
					for (var i = 0; i < list.length; i++) {
						var isFile = list[i].isFile;
						if(isFile>0){
							_tm +="<li><a  href='attach/"+list[i].docid+".do'	target='_blank' > "+list[i].title+"</a></li>";
						}else{
							_tm +="<li><a  href='detail.jsp?docid="+list[i].docid+"'	target='_blank' > "+list[i].title+"</a></li>";
						}
						
					}
					$("#data04").append(_tm);
				}
				
			});
	
	</script>