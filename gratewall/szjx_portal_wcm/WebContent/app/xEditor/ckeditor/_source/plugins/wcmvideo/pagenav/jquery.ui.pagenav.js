/**
*Title： jquery.ui.pagenav.js
*Description：
* 		分页导航组件
* 		wiki地址：http://wiki.dev2.trs.cn/pages/viewpage.action?pageId=3605863
* Copyright: www.trs.com.cn
* Company: 	北京拓尔思信息技术股份有限公司
* Parameters:
*	Hisotry			Who				What
*  2011.12.13		zhangxinjian	add
*/
(function($, undefined){
	var baseClses = "ui-pagenav ui-widget";

	$.widget( "ui.pagenav", {
		options: {
			pageDesc:[
				'<ul class="pageHtml">{#pageHtml}</ul>',
				'<span class="pageCount">共<b>{#pageCount}</b>页</span>',
				'<span class="recordNum"><b>{#recordNum}</b>条记录</span>',
				'<span class="pageSize"><input type="text" data-init-value="{#pageSize}" value="{#pageSize}"></input>条/页</span>',
				'<span class="pageIndex">跳转到第<input type="text" data-init-value="{#pageIndex}" value="{#pageIndex}" />页</span>'
			].join(""),
			recordNum: 0,
			pageSize: 20,//分页大小，每页多少条记录
			maxPageSize:1000,
			pageIndex:1,//当前页
			pageLength:11,//分页导航长度，分页导航显示多少个页
			maxPageLength:20,//最大分页导航长度
			displayPrevNextPage:false,//显示上一页、下一页
			prevPageText:"&lt;prev",//上一页按钮（链接）的显示文本，默认为“<prev”
			nextPageText:"next&gt;",//下一页按钮（链接）的显示文本，默认为“next>”
			callback:null //点击分页页码、修改分页大小、跳转到指定页的处理函数，由调用者实现。
		},
		_create : function(){
			this.destroy();//销毁上次产生的分页导航
			
			if(this.options.recordNum <= 0){//记录条数不能小于等于0
				return;
			}
			if(this.options.pageSize < 1 ){//分页大小pageSize不能小于1
				this.options.pageSize = 20;
			}
			if(this.options.pageIndex < 1 ){//当前页pageIndex不能小于1
				this.options.pageIndex = 1;
			}
			if(this.options.pageLength < 3 ){//分页导航长度pageLength不能小于3
				this.options.pageLength = 11;
			}
			//确保 pageLength <= maxPageLength
			this.options.pageLength = Math.min(this.options.pageLength, this.options.maxPageLength);

			//确保 pageSize <= maxPageSize 
			this.options.pageSize = Math.min(this.options.pageSize, this.options.maxPageSize);
			this.options.pageCount = Math.ceil(this.options.recordNum / this.options.pageSize);
			//确保 pageIndex <= pageCount
			this.options.pageIndex = Math.min(this.options.pageIndex, this.options.pageCount);
			//防止prevPageText参数传空字符
			if(this.options.prevPageText == ""){
				this.options.prevPageText = "&lt;prev";
			}
			//防止nextPageText参数传空字符
			if(this.options.nextPageText == ""){
				this.options.nextPageText = "next&gt;";
			}
			
			/*
			 * 【部分分页导航】：以当前页为核心的部分分页导航。
			 * 例如有这样的分页导航：当前页是4，总共20页，分页导航显示7个页码，即 1 ... 2 3 【4】 5 6 ... 20
			 * 可以看出以当前页为核心的【部分分页导航】即为两个...之间的部分分页2 3 【4】 5 6。
			 * 【部分分页导航】的【起始页】的页码命名为startIndex，【结束页】的页码命名为endIndex。
			 * 其中，startIndex可以为首页，即页码为1，例如当前页为3时有：1 2 【3】 4 5 6... 20
			 *      endIndex可以为尾页，这个例子中即页码为20，例如当前页为18时有：1...15 16 【17】 18 19 20
			 */
			var startIndex = 0;
			var endIndex = 0;
			//当前页前跨度，即当前页前面的页数（除去首页）
			var beforeSpan = Math.floor( (this.options.pageLength - 3) / 2 );
			//当前页后跨度，即当前页后面的页数（除去尾页）
			var afterSpan = Math.ceil( (this.options.pageLength -3 ) / 2);

			//计算startIndex和endIndex
			/*
			1.分页导航长度（显示的分页个数）大于等于分页总数时，则分页导航将显示所有页

			2.分页导航长度小于分页总数
			2.1当前页是首页时，则除去当前页和尾页还要显示pageLength-1页

			2.2当前页在首页、尾页之间时，构造除去首页、尾页之外以当前页为核心的部分分页导航，
			部分分页导航在后面的处理中会合并到最终的分页导航中，下面2.2.1和2.2.2为计算部分分页导航的思路：
			2.2.1部分分页导航的起始页小于2时，即在首页和当前页之间的页数小于当前页前跨度时，
				部分分页导航的结束页要往后扩展首页和当前页之间页数减去当前页前跨度的页数，
				同时部分分页导航的起始页赋值为2.
			2.2.2部分分页导航的结束页大于倒数第二页时，即在当前页和尾页之间的页数小于当前页后跨度时，
				部分分页的起始页要向前扩展当前页和尾页之间的页数减去当前页后跨度的页数，
				同时部分分页的结束页赋值为倒数第二页（pageCount-1）

			2.3当前页是尾页时，出去首页和当前页（尾页），还要显示pageLength-1页
			*/

			//分页导航长度大于等于分页总数
			if(this.options.pageLength >= this.options.pageCount){
				startIndex = 1;
				endIndex = this.options.pageCount;
			}else{//分页导航长度小于分页总数
				if(this.options.pageIndex == 1){//当前页是首页
					startIndex = 1;
					endIndex = this.options.pageIndex + ( this.options.pageLength - 2 );
				}else if(this.options.pageIndex > 1 && this.options.pageIndex < this.options.pageCount){//当前页在首页、尾页之间
					//除首页之外，以当前页为核心的部分分页导航的起始页
					startIndex = this.options.pageIndex - beforeSpan;
					//除尾页之外，以当前页为核心的部分分页导航的结束页
					endIndex = this.options.pageIndex + afterSpan;

					//当前页在首页、尾页之间时，startIndex至少是2
					if(startIndex < 2 ){
						endIndex += ( 2 - startIndex);
						startIndex = 2;
					}
					//当前页在首页、尾页之间时，endIndex至多是pageCount-1
					if(endIndex > (this.options.pageCount - 1) ){
						startIndex -= ( endIndex - (this.options.pageCount - 1) );
						endIndex = this.options.pageCount - 1;
					}
				}else if(this.options.pageIndex == this.options.pageCount){//当前页是尾页
					startIndex = this.options.pageCount - (this.options.pageLength - 2 );
					endIndex = this.options.pageCount;
				}
			}

			var pageIndex = this.options.pageIndex;//当前页的索引，从1开始
			var aHtml = [];

			for(var index = startIndex; index <= endIndex; index++){
				aHtml.push('<li', index == pageIndex? " class='currpage'" : "", '><a href="#">', index, '</a></li>');
			}
			if(startIndex > 2){//append .. after the first page.
				aHtml.splice(0, 0, '<li class="ellipsis">...</li>');
			}
			if(startIndex > 1){//append the first page.
				aHtml.splice(0, 0, '<li><a href="#">', 1, '</a></li>');
			}
			if(endIndex < (this.options.pageCount - 1)){//append .. before the last page.
				aHtml.push('<li class="ellipsis">...</li>');
			}
			if(endIndex < this.options.pageCount){//append the last page.
				aHtml.push('<li><a href="#">', this.options.pageCount, '</a></li>');
			}
			if(this.options.displayPrevNextPage && pageIndex > 1){//添加"上一页"
				aHtml.splice(0, 0, '<li  class="prevNext"><a class="prevPage" href="#">', this.options.prevPageText, '</a></li>');
			}
			if(this.options.displayPrevNextPage && pageIndex < this.options.pageCount){//添加"下一页"
				aHtml.push('<li  class="prevNext"><a class="nextPage" href="#">', this.options.nextPageText, '</a></li>');
			}

			this.options.pageHtml = aHtml.join("");

			var caller = this;
			var sHtml = this.options.pageDesc.replace(/\{#([a-z]+)\}/ig, function($0, $1){
				return caller.options[$1]||"";
			});
			this.element.html(sHtml);
			this.element.addClass(baseClses);

			//bind the links events.
			$(this.element).click(function(event){
				var dom = event.target;				
				if(dom.tagName != 'A') return;
				var targetPage = $(dom).text();
				if(caller.options.displayPrevNextPage){//当设置显示上一页、下一页时
					if($(dom).hasClass("prevPage")){
						targetPage = pageIndex - 1;
					}
					if($(dom).hasClass("nextPage")){
						targetPage = pageIndex + 1;
					}
				}				
				if(caller.options.callback) caller.options.callback(targetPage, caller.options.pageSize);
				return false;
			});

			//bind the pageSize input events.
			$(".pageSize input", this.element).bind({
				keydown : function(event){//event.which==13代表按下了回车键
							var code = event.which;
							if(code == 13){
								pageSizeChangeHandler(this,caller);
							} 
				},
				blur : function(event){
							pageSizeChangeHandler(this,caller);
				},
				focus : function(){
					$(this).select();
				}
			});

			//bind the pageIndex input events.
			$(".pageIndex input", this.element).bind({
				keydown : function(event){
							var code = event.which;
							if(code == 13){//event.which==13代表按下了回车键
								pageIndexChangeHandler(this, caller);
							}
				},
				blur : function(event){
							pageIndexChangeHandler(this, caller);
				},
				focus : function(){
					$(this).select();
				}
			});
		},
		destroy: function() {
			this.element.removeClass(baseClses);
			$(".pageIndex input", this.element).unbind();
			$(".pageSize input", this.element).unbind();
			$(this.element).empty();//清空容器的内容
			$.Widget.prototype.destroy.call( this );
		}
	});

	/* 功能：改变分页大小时的处理函数
	*  parameters
	*        obj pageSize的input输入框节点
	*        caller pagenav的实例
	*/
	function pageSizeChangeHandler(obj, caller){
		var pageSize = parseInt($(obj).val(), 10);
		//pageSize is not number
		if(isNaN(pageSize)) {
			$(obj).val($(obj).attr("data-init-value"));
			alert("请输入数字！");
			return;
		}
		if(pageSize <= 0){
			$(obj).val($(obj).attr("data-init-value"));
			alert("请输入大于0数值！");
			return;
		}
		//make sure that pageSize <= maxPageSize
		pageSize = Math.min(pageSize, caller.options.maxPageSize);
		$(obj).attr("data-init-value",pageSize);
		if(caller.options.callback) caller.options.callback(1, pageSize);
	}

	/* 功能：改变跳转至某页的输入框时的处理函数
	*  parameters
	*        obj pageSize的input输入框节点
	*        caller pagenav的实例
	*/
	function pageIndexChangeHandler(obj, caller){
		if($(obj).val() == $(obj).attr("data-init-value")) return;
		var pageIndex = parseInt($(obj).val(), 10);
		//pageSize is not number
		if(isNaN(pageIndex)) {
			$(obj).val($(obj).attr("data-init-value"));
			alert("请输入数字！");
			return;
		}
		pageIndex = Math.min(Math.max(1, pageIndex), caller.options.pageCount);
		$(obj).attr("data-init-value", pageIndex);
		if(caller.options.callback) caller.options.callback(pageIndex, caller.options.pageSize);
	}

})(jQuery);
