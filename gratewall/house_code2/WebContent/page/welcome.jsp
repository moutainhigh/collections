<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>房屋编码查询</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Cache-Control" content="no-transform" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0,user-scalable=no" />
<link rel="shortcut icon" href="../static/images/01_home_page_house_onebit_16px_151_easyicon.net.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/bt/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/TzAdmin.css">
<script src="<%=basePath%>/static/bt/js/jquery.min.js"></script>
<script src="<%=basePath%>/static/bt/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/static/bt/js/bootstrap-paginator.js"></script>
<style>
.dropdown-menu{
	width: 100%;
}

</style>
</head>

<body>
<div style="height: 350px;background: url(<%=basePath%>/static/images/indexBg2.jpg);background-size: cover;"></div>
	<div class="container">
		<div class="row clearfix" >
			<div class="col-md-12 column">
				<div>
					<form class="bs-example bs-example-form" role="form" style="padding:40px">
						<div class="row">
							<div class="col-lg-2"></div>
							<div class="col-lg-8">
								<div style="position: relative;">
									<div class="input-group">
										<input type="text" id="queryTxt" class="form-control" > <span class="input-group-btn" >
											<button class="btn btn-primary" type="button">房屋查询</button>
										</span>
									</div>
									<div id="tianChongBox">
										<ul class="dropdown-menu" id="lists">
										</ul>
									</div>
								</div>
								<div>
									<a class="label label-default" href="hs/page/admin.html">高级查询</a>
								</div>
							</div>
							<div class="col-lg-2"></div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
	function isChinese(txt){
		 var re = /[^\u4e00-\u9fa5]/;  
		 if(re.test(txt)) return false;  
		 return true;  
	}
	
		//查找
		$('#queryTxt').on('input propertychange', function() {
			var _this = $(this);
			var keyword = _this.val();
			
			if(isChinese(keyword)){
				keyword = encodeURI(keyword);
				$(".dropdown-menu").addClass("show");
				
				var path = "<%=basePath%>";
				$.ajax({
					url :path + "/getHouse/getHouseByKeyWord.do",//查询后台
					type : "post",
					data : {
						keyword : keyword
					},
					dataType:"json",
					beforeSend : function() {
						
					},
					success : function(data) {
						var data = data.data[0].data;
						var _html ="";
						$("#lists").empty();
						$.each(data,function(i,datas){
							_html +="<li onclick='showDetails(this)' ><a target='_blank' href='"+path+"/page/showDetail.html?houserId="+datas.houseId+"'>"+datas.houseAdd+"</a></li>";
						})
						$("#lists").append(_html);
						
					},
					error : function(data) {

					}
				})
			}
			
			
		});
		
		function hideBox(){
			$(".dropdown-menu").removeClass("show");
		}
		
		
		
		function showDetails(obj){
			hideBox();
		}
	</script>
</body>
</html>
