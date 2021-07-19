<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="pub.jsp"%>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="stockCode" class="col-sm-2 control-label">代码</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="stockCode" maxlength="6"/>
						</div>
					</div>
					<div class="form-group">
						<label for="stockName" class="col-sm-2 control-label">名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="stockName" disabled="disabled" />
						</div>
					</div>
					<div class="form-group">
						<label for="crate" class="col-sm-2 control-label">当前涨跌</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="crate" disabled="disabled" />
						</div>
					</div>
					<div class="form-group">
						<label for="cprice" class="col-sm-2 control-label">当前价</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="cprice" disabled="disabled" />
						</div>
					</div>
					<div class="form-group">
						<label for="buyPrice" class="col-sm-2 control-label">预设买入价</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="buyPrice" />
						</div>
					</div>
					<div class="form-group">
						<label for="buyPrice" class="col-sm-2 control-label">预设提醒价</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="buyPrice" />
						</div>
					</div>
					<div class="form-group">
						<label for="buyPrice" class="col-sm-2 control-label">股价上涨到</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="buyPrice" />
						</div>
					</div>
					<div class="form-group">
						<label for="buyPrice" class="col-sm-2 control-label">股价下跌到</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="buyPrice" />
						</div>
					</div>
					<div class="form-group">
						<label for="buyPrice" class="col-sm-2 control-label">日涨幅超</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="buyPrice" />
						</div>
					</div>
					<div class="form-group">
						<label for="buyPrice" class="col-sm-2 control-label">日跌幅超</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="buyPrice" />
						</div>
					</div>
					<div class="form-group">
						<label for="positionsLevel" class="col-sm-2 control-label">仓位控制</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="positionsLevel" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="checkbox">
								<label><input type="checkbox" />控制台</label>
								<label><input type="checkbox" />最重要</label>
								<label><input type="checkbox" />重要</label>
								<label><input type="checkbox" />最近关注</label>
								<label><input type="checkbox" />最近待关注</label>
								<label><input type="checkbox" />龙虎榜</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="radio">
								<label><input type="radio" />历史购买</label>
								<label><input type="radio" checked="checked"/>非历史购买</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="radio">
								<label><input type="radio" />新股与次新股</label>
								<label><input type="radio" checked="checked"/>非新股与次新股</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<a href="javascript:;" class="btn btn-default">保存</a>
							<a href="javascript:;" class="btn btn-default" onclick="clearDatas()">清空</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
$("#stockCode").on("input propertychange", function() {
	var txt = $(this).val();
	clearData();
	if(txt.length==6){
		
		$.ajax({
			url:"stock/getStockIsEixt",
			data:{
				 code:txt
				},
			success:function(data){
				if(data==0){
					$.ajax({
						url:"stock/getStockInfo",
						data:{
							 code:txt
							},
						success:function(data){
							$("#stockName").val(data.name);
							$("#cprice").val(data.cprice);
							$("#crate").val(data.crates);
						},
						error:function(data){
							
						}
					});
				}else{
					alert("已经存在");
					$("#stockCode").val("");
					$("#stockCode").focus();
				}
			},
			error:function(data){
				
			}
		});
		
	}
	
});


function clearData(){
	$("input[id!='stockCode']").val("");
}

function clearDatas(){
	$("input").val("");
}
</script>
</body>
</html>