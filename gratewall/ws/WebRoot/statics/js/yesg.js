function loading() {
	var loader = $("#loader").html();
	if (loader == undefined) {
		$("body").append("<div id='loader' class='loading-layer animated bounceInRight'><img src='statics/images/loading.gif' width='30' height='30'/>数据准备中，马上就来 。。。</div>")
	}

}

function removeLoading() {
	$("#loader").slideUp(function() {
		$(this).remove();
	});
}

function dialog(option) {

}