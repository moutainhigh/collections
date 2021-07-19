CKEDITOR.plugins.add( 'bottombutton',
{
	init:function(editor){
		/*
		*插件底部按钮的初始化：定义按钮参数，调用BtnHandler来添加按钮
		*/
		var ButtonArray = [{
			title	: "源码",
			key		: "source",
			order	: 1,
			cmd		: function(){
				var dom = document.getElementById("source");
				if(editor.mode != 'source') {
					function addEvent( obj, type, fn ) {
						if ( obj.attachEvent ) {
							obj['e'+type+fn] = fn;
							obj[type+fn] = function(){obj['e'+type+fn]( window.event );}
							obj.attachEvent( 'on'+type, obj[type+fn] );
						} else
							obj.addEventListener( type, fn, false );
					}
					dom.style.backgroundColor = "#95b3d9";
					dom.style.color = "#fff";
					addEvent(dom, "mouseover",
						function(){
							dom.style.backgroundColor = "#95b3d9";
							dom.style.color = "#fff";
						});
					addEvent(dom, "mouseout",
						function(){
							dom.style.backgroundColor = "#c3d9f5";//"#a3d7ff";
							dom.style.color = "#013987";
						});
				}else {
					dom.style.backgroundColor = "#c3d9f5";
					dom.style.color = "#013987";
				}
				editor.getCommand( 'source' ).exec();
			}
		},{
			title	: "一键排版",
			key		: "key4format",
			order	: 2,
			cmd		: function(){
				editor.getCommand( 'key4formatCommand' ).exec();
			}
			
		}];
		//var tempArray = BtnHandler.addBtns(ButtonArray);
		//BtnHandler.render(tempArray);
		BtnHandler.addBtns(ButtonArray);
		
		editor.on( 'instanceReady', function( e ){
			BtnHandler.render();
		});
	}
});
(function(){
	var containerId = 'editor_Bottom';
	/*
	*对按钮的初始化，绑定单击事件
	*/
	function initEvent(bottomArea,array){
		addEvent(bottomArea,"click",
			function(e) {
				e = window.event || e; 
				var srcElement = e.srcElement || e.target;
				var currElementId = srcElement.id;
				for (var i = 0; i < array.length; i++) {
					if (currElementId == array[i].key) {
						var _fn = array[i].cmd;
						break;
					}
				}
				if(_fn) {
					_fn();
				}
			});
		addEvent(bottomArea,"mouseover",
			function(e) {
				e = window.event || e; 
				var srcElement = e.srcElement || e.target;
				var currElementId = srcElement.id;
				var currElement = document.getElementById(currElementId);
				if(currElementId != containerId) {
					if(CKEDITOR.instances.editor.mode != 'source') {
						currElement.style.backgroundColor = "#c3d9f5";
						currElement.style.color = "#013987";
					}
				}
			});
		addEvent(bottomArea, "mouseout",
			function(e){
				e = window.event || e; 
				var srcElement = e.srcElement || e.target;
				var currElementId = srcElement.id;
				var currElement = document.getElementById(currElementId);
				if(currElementId != containerId) {
					if(CKEDITOR.instances.editor.mode != 'source') {
						currElement.style.backgroundColor = "white";
						currElement.style.color = "#013987";
					}
				}
			});
		function addEvent( obj, type, fn ) {
			if ( obj.attachEvent ) {
				obj['e'+type+fn] = fn;
				obj[type+fn] = function(){obj['e'+type+fn]( window.event );}
				obj.attachEvent( 'on'+type, obj[type+fn] );
			} else
				obj.addEventListener( type, fn, false );
		}
		function removeEvent( obj, type, fn ) {
			if ( obj.detachEvent ) {
				obj.detachEvent( 'on'+type, obj[type+fn] );
				obj[type+fn] = null;
			} else
				obj.removeEventListener( type, fn, false );
		}
	}
	/*
	*根据传入数组的order属性来排序按钮
	*/
	function creatComparisonFunction(propertyName){
		return function(object1,object2){
			var value1 = object1[propertyName];
			var value2 = object2[propertyName];
			if(value1<value2){
				return -1;
			}else if(value1>value2){
				return 1;
			}else {
				return 0;
			}
		};
	}
/*
*定义一个底部按钮对象，用于加载新添加的按钮
	*有两个方法，添加按钮addBtns和画出按钮（包括在画完之后对按钮绑定事件的初始化）
*/
	window.BtnHandler = {
		btnCache : [],
		addBtns : function(_array){
			BtnHandler.btnCache = BtnHandler.btnCache.concat(_array);
			//对rts按照数组中的order进行排序
			BtnHandler.btnCache.sort(creatComparisonFunction("order"));

			return BtnHandler.btnCache;
		},
		render : function(){
			var _tempArray = this.btnCache;
			var rts = [];
			for (var i=0; i<_tempArray.length; i++) {
				var _button = _tempArray[i];
				var _id = _button.key;
				var value = _button.title;
				rts.push("<span class='editor_bottom_button' id='" + _id + "'>" + value + "</span>");
			}
			var bottomArea = document.getElementById(containerId);
			bottomArea.innerHTML = rts.join('');
			initEvent(bottomArea,_tempArray);
		}
	};
})();