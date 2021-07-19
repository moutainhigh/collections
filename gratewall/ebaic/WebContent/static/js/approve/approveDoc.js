define(['require','jquery','approvecommon'], function(require,$, approvecommon){
	
	var pic = {
		_init : function(){
			var that = this;
			require(['domReady'], function (domReady) {
				domReady(function (){
					$.ajax({
		        		url: '../../../pdf/v2/getHtml.do?time='+new Date().getTime(),
		        		data: {gid: jazz.util.getParameter('gid')||''},
						dataType:'json',
		        		success : function(data) {
		        			if(data && data.length > 0){
		        				var chapter = '';
		        				var content = '';
		        				for(var i = 0, len = data.length; i < len; i++){
		        					chapter += '<a class="chapter_content" href="#'+data[i].href+'">'+data[i].title+'</a>';
		        					content += '<div id="' + data[i].href + '">' + data[i].htmlCont + '</div>';
		        				}
		        				$('#chapter').append(chapter);
		        				$('#contentMain').append(content);
		        				$('.chapter_content').on('click',function(){
		        					$(this).siblings().removeClass('selected');
		        					$(this).addClass('selected');
		        					var item = $($(this).attr('href'));
		        					var top = item.offset().top;
		        					$(parent.window).scrollTop(top);
		        				});
		        				$('.chapter_content').first().addClass('selected');
		        				var width = $('#chapter').width();
		        				$('#fixed_content').css('width',width);
		        				var height = $('#fixed_content').height();
		        				$('#contentMain').css('padding-top',height);
		        				var bodyHeight = $('body').height();
		        				$('#frametabcontent',window.parent.document).css('height',bodyHeight);
		        			}
			        	},
	        			error : function(){
	        				jazz.info('文书加载失败！');
	        			}
		            });
					
					$(parent.window).scroll(function(){
						var top = parseInt($(parent.window).scrollTop()) - 32;
						if(top > 0){
							$("#fixedTitle").css({top:top});
						}else{
							$("#fixedTitle").css({top:0});
						}
						
						});
					
				});//end of dom ready
			});//end of require
		}// end of _init
	};
	pic._init();
	return pic;
});


