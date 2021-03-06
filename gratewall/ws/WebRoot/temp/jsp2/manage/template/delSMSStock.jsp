<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>

#isLongHuBang,#recentImp,#mostImp,#waitImp {
	top: 50%;
	width: 75px;
	height: 40px;
}

.toggle {
	position: absolute;
	border: 2px solid #444249;
	border-radius: 20px;
	-webkit-transition: border-color .6s ease-out;
	transition: border-color .6s ease-out;
	box-sizing: border-box;
}

.toggle.toggle-on {
	border-color: rgba(137, 194, 217, .4);
	-webkit-transition: all .5s .15s ease-out;
	transition: all .5s .15s ease-out;
}

.toggle-button {
	position: absolute;
	top: 4px;
	width: 28px;
	bottom: 4px;
	right: 39px;
	background-color: #444249;
	border-radius: 19px;
	cursor: pointer;
	-webkit-transition: all .3s .1s, width .1s, top .1s, bottom .1s;
	transition: all .3s .1s, width .1s, top .1s, bottom .1s;
}

.toggle-on .toggle-button {
	top: 3px;
	width: 65px;
	bottom: 3px;
	right: 3px;
	border-radius: 23px;
	background-color: #89c2da;
	box-shadow: 0 0 16px #4b7a8d;
	-webkit-transition: all .2s .1s, right .1s;
	transition: all .2s .1s, right .1s;
}

.toggle-text-on {
	position: absolute;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	line-height: 36px;
	text-align: center;
	font-family: 'Quicksand', sans-serif;
	font-size: 18px;
	font-weight: normal;
	cursor: pointer;
	-webkit-user-select: none; /* Chrome/Safari */
	-moz-user-select: none; /* Firefox */
	-ms-user-select: none; /* IE10+ */
	color: rgba(0, 0, 0, 0);
}

.toggle-on .toggle-text-on {
	color: #3b6a7d;
	-webkit-transition: color .3s .15s;
	transition: color .3s .15s;
}

.toggle-text-off {
	position: absolute;
	top: 0;
	bottom: 0;
	right: 6px;
	line-height: 36px;
	text-align: center;
	font-family: 'Quicksand', sans-serif;
	font-size: 14px;
	font-weight: bold;
	-webkit-user-select: none; /* Chrome/Safari */
	-moz-user-select: none; /* Firefox */
	-ms-user-select: none; /* IE10+ */
	cursor: pointer;
	color: #444249;
}

.toggle-on .toggle-text-off {
	color: rgba(0, 0, 0, 0);
}
/* used for streak effect */
.glow-comp {
	position: absolute;
	opacity: 0;
	top: 10px;
	bottom: 10px;
	left: 10px;
	right: 10px;
	border-radius: 6px;
	background-color: rgba(75, 122, 141, .1);
	box-shadow: 0 0 12px rgba(75, 122, 141, .2);
	-webkit-transition: opacity 4.5s 1s;
	transition: opacity 4.5s 1s;
}

.toggle-on .glow-comp {
	opacity: 1;
	-webkit-transition: opacity 1s;
	transition: opacity 1s;
}
</style>

</style>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			????????????
			</span>
		</h3>
	</div>
	<div class="panel-body">
		<form class="form-horizontal" role="form">
			<div class="form-group">
				<label for="stockCode" class="col-sm-2 control-label">??????</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="stockCode" placeholder="??????6???????????????" maxlength="6" data-toggle="popover" data-placement="top" data-content="????????????" />
				</div>
			</div>
			<div class="form-group">
				<label for="stockName" class="col-sm-2 control-label">????????????</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" disabled id="stockName" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a class="btn btn-default" onclick="saveStock(this)">??????</a>
				</div>
			</div>
		</form>
	</div>
</div>

<script>
	$('.toggle').click(function(e) {
		e.preventDefault(); // The flicker is a codepen thing
		$(this).toggleClass('toggle-on');
	});
</script>
<!-- http://www.htmleaf.com/css3/css3donghua/20141122560.html -->


<script>
	$('#stockCode').on('input propertychange', function() {
		var stockCode = $(this).val();
		if (stockCode.length == 6) {
			getStockIsExit(stockCode);
		} else {
			$("[data-toggle='popover']").popover('hide');
			$("#stockCode").parents(".form-group").removeClass("has-error");
			var re = /^[0-9]+.?[0-9]*$/; //?????????????????????????????? //??????????????? /^[1-9]+[0-9]*]*$/ 
			if (!re.test(stockCode)) {
				alert("???????????????????????????");
				$("#stockCode").focus();
				$("#stockCode").select();
			}
		}
	});

	function getStockIsExit(stockCode) {
		$.ajax({
			url : "stock/isHas",
			data : {
				stockCode : stockCode
			},
			success : function(data) {
				var data = data;
				if (data == "1") {
					$("[data-toggle='popover']").popover('show');
					$("#stockCode").parents(".form-group").addClass("has-error");
				} else {
					$("[data-toggle='popover']").popover('hide');
					$("#stockCode").parents(".form-group").removeClass("has-error");
				}
			}
		})
	}

	function saveStock(obj) {
		var _this = $(obj);
		_this.removeAttr("onclick");
		var stockCode = $("#stockCode").val();
		var isMostImp =  $("#mostImp").hasClass("toggle-on");
		var isRecentImp =  $("#recentImp").hasClass("toggle-on"); 
		var isWaitImp =  $("#waitImp").hasClass("toggle-on");
		var isLongHuBang = $("#isLongHuBang").hasClass("toggle-on");
		var planPrice = $("#planPrice").val();
		var mostImp = 0;
		var recentImp = 0;
		var waitImp = 0;
		var longHuBang = 0;
		
		if(isMostImp){
			mostImp = 1;
		}
		
		if(isRecentImp){
			recentImp = 1;
		}
		
		
		if(isWaitImp){
			waitImp = 1;
		}
		if(isLongHuBang){
			longHuBang = 1;
		}
		
		if (isEmpty(stockCode)) {
			alert("????????????????????????")
			return;
		}
		$.ajax({
			url : "stock/saveStock",
			data : {
				 stockCode : stockCode,
				 mostImp:mostImp,
			     recentImp:recentImp,
			     waitImp:waitImp,
			     longHuBang:longHuBang,
			     planPrice:planPrice
			},
			success : function(data) {
				_this.attr("onclick","saveStock(this)");
				console.log(data)
			},
			error : function(data) {
				_this.attr("onclick","saveStock(this)");
			}
		})
	}
</script>