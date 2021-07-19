<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--2 开始媒体新闻-->
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
<div class="modb mb50" style='margin-top:100px'>
	<div style="position: relative;">
		<h2 class="hd">
			<a href="more.jsp?cid=2" class="colTit" target="_blank">【&nbsp;&nbsp;&nbsp;媒体报道&nbsp;&nbsp;&nbsp;】</a>
		</h2>
		<a href="more.jsp?cid=2"  class='myMore' target="_blank">更多>></a>
	</div>
	<div class="bd mb15">
		<div>
			<div class="clear" id="shows">
				
			</div>
			<div class="clear">
				
				<ul class="newsList3" id="data02Title">
				
				</ul>
			</div>
		</div>
		<div>
			<div class="clear" id="shows2">
				
			</div>
			<div class="clear">
				
				<ul class="newsList3" id="data02Title2">
				
				</ul>
			</div>
		</div>
	</div>
	
	
		<script>

		$.ajax({
			url : "getPubDocusWithThumb.do",
			type : "get",
			//async:false,//取消异步请求
			data : {
				pageNo : 1,
				pageSize : 2,
				cid : 2,
				thum : 1,
			},
			dataType : "json",
			beforeSend : function(data) {
	
			},
			success : function(data) {
				var _tm = "";
				var list = data.list;
				for (var i = 0; i < list.length; i++) {
					_tm += " <div class='imgTxtNews2'>";
					_tm += "<a href='detail.jsp?docid="+list[i].docid+"' class='tit' target='_blank'>"+list[i].title+" </a>";
						_tm += "<div class='clear'>";
						_tm += "<a href='detail.jsp?docid="+list[i].docid+"' class='img' target='_blank'><img src='"+list[i].thumburl+"'  /></a>";
						_tm += "<div class='txtNews'>";
						_tm += "	<p class='txt'>";
						_tm += "	"+list[i].contentmeta +"<a href='detail.jsp?docid="+list[i].docid+"' class='more' target='_blank'>[详细]</a>";
						_tm += "	</p>";
						_tm += "</div>";
						_tm += "</div>";
						_tm += "</div>";
				}
				$("#shows").append(_tm);
			}
	
		});
			
		
		
		$.ajax({
			url:"getPubDocsByChannel.do",
			type:"get",
			//async:false,//取消异步请求
			data:{pageNo:1,pageSize:8,cid:2},
			dataType:"json",
			beforeSend:function(data){
				
			},
			
			success:function(data){
				var _tm = "";
				var list = data.list;
				for (var i = 0; i < list.length; i++) {
					if(i>1){
						_tm +="<li><a href='detail.jsp?docid="+list[i].docid+"'	target='_blank'>"+list[i].title+" </a></li>";
					}
				}
				$("#data02Title").append(_tm);
			}
			
		});
	</script>
</div>