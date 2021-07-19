//待添加信息模板信息
define(['require','jquery', 'jazz','util','entCommon'], function(require,$, jazz,util,entCommon){
	var $_this = {
		/**
		 * 初始化
		 */
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					
					$("#backButton").on('click',$_this.backStep);
				});
			});
		},
		/**
		 * 返回
		 */
		backStep:function(){
			window.location.href="../../../page/apply/ent_account/home.html";
		}
		
	}
	$_this._init();
	return $_this;
});