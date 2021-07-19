$package('com.trs.pontoon');

$import('opensource.animator');
$import('com.trs.logger.Logger');
$import('com.trs.drag.SimpleDragger');
$importCSS('com.trs.pontoon.Pontoon');

com.trs.pontoon.imgPath = com.trs.util.Common.BASE + 'com.trs.dialog/img/';
var _DEFAULT_WIDTH = 200;
var _DEFAULT_HEIGHT = 100;
var _DELTA_HEIGHT = 5;
var _DELTA_WIDTH  = 5;
	
com.trs.pontoon.Pontoon = Class.create('pontoon.Pontoon');
com.trs.pontoon._Pontoons = [];
com.trs.pontoon.Pontoon.prototype = {
	m_bVisible: false,
	initialize: function(_sTitle, _content, _nWidth, _nHeight){
		if(typeof(_content) == 'object') {
			if(_content == null) {
				alert('传入需要呈现"浮筒"效果的对象不存在！');
				return;
			}
			if(_content.innerHTML == null) {
				alert('传入需要呈现"浮筒"效果的对象不是一个[html element]！');
				return;
			}
			this.content = _content.innerHTML;
			Element.remove(_content);
		}else{
			this.content = _content || '';
		}

		this.title = _sTitle || '系统提示信息';
		this.width = _nWidth || _DEFAULT_WIDTH;
		this.height = _nHeight || _DEFAULT_HEIGHT;

		this._init();
		com.trs.pontoon._Pontoons.push(this);
	},
	destroy:function(_bUnion){
		delete this.m_eDisplayRegion;
		delete this.activeRegion;
		delete this.ex;
		$destroy(this);
		if(!_bUnion&&com.trs.pontoon._Pontoons){
			com.trs.pontoon._Pontoons.without(this);
		}
	},
	_init : function(){
		var sBaseHtml = '\
			<div id="divPontoon" style="display: none; height:1; width:' + this.width + 'px;position:absolute; overflow:hidden;">\
				<div id="divHeader" title="双击标题可以隐藏">\
					<div id="divHeaderTitle" style="width:' + (this.width - 32) + 'px;">\
						' + this.title + '\
					</div>\
					<div id="divHeaderCloser" style="width:20px;">\
						X\
					</div>\
				</div>\
				<div id="divContent" style="width:' + (this.width - 2) + '; height:' + (this.height - 22) +'px;">\
					' + this.content + '\
				</div>\
			</div>';
		new Insertion.Bottom(document.body, sBaseHtml);
		
		Event.observe($('divHeaderCloser'), 'click', function(){
			this.toggle();
		}.bind(this));
		Event.observe($('divHeader'), 'dblclick', function(){
			this.toggle();
		}.bind(this));
		
		this.m_eDisplayRegion = this.posPontoon();

		//ge gfc add @ 2007-7-10 13:08 添加拖拽效果
		var drag=new com.trs.drag.SimpleDragger($('divHeader'), $('divPontoon'));
	},
	show : function(){
		if(this.m_bVisible) {
			return;
		}
		this.toggle();
	},
	hide : function(){
		if(!this.m_bVisible) {
			return;
		}
		this.toggle();
	},
	toggle : function(){
		var nHeight = this.getActiveRegion().height - _DELTA_HEIGHT - 1;
		if(!Element.visible(this.m_eDisplayRegion)) {
			this.m_eDisplayRegion.style.top = nHeight;
			Element.show(this.m_eDisplayRegion);
		}
		//return;
		if(this.ex == null) {			
			this.ex = new Animator({
				duration: 600,
				onComplete: function() {
					var nCurHeight = parseInt(this.m_eDisplayRegion.style.height);
					if(nCurHeight == 1){
						Element.hide(this.m_eDisplayRegion);
						this.m_bVisible = false;
					}else{
						Element.show(this.m_eDisplayRegion);
						this.m_bVisible = true;
					}

					if(!this.m_bVisible && this.m_fOnHide) {
						this.m_fOnHide();
					}
				}.bind(this)
			})
			.addSubject(new NumericalStyleSubject(this.m_eDisplayRegion, 'height', 1, this.height))
			.addSubject(new NumericalStyleSubject(this.m_eDisplayRegion, 'top', nHeight, nHeight - this.height));
		}else{
			this.posPontoon();
		}
		this.ex.toggle();
	},
	posPontoon : function(){
		this.activeRegion = $dim();
		var dv = $('divPontoon');
		dv.style.left = (this.activeRegion.width - this.width - _DELTA_WIDTH) + 'px';
		dv.style.top  = (this.activeRegion.height - this.height - _DELTA_HEIGHT) + 'px';

		//re-add the height-alternation
		var nHeight = this.getActiveRegion().height - _DELTA_HEIGHT - 1;
		if(this.ex != null) {
			this.ex.addSubject(new NumericalStyleSubject(dv, 'top', nHeight, nHeight - this.height));
		}//*/

		return dv;
	},
	getActiveRegion : function(){
		if(this.activeRegion == null) {
			this.activeRegion = $dim();
		}	
		return this.activeRegion;
	},
	getCurrentHeight : function(){
		if(this.m_eDisplayRegion == null) {
			return -1;
		}
		return parseInt(this.m_eDisplayRegion.style.height);
	},
	isVisible : function(){
		return this.m_bVisible;	
	},
	setOnHide : function(_f){
		this.m_fOnHide = _f || ProtoType.emptyFunction;
	},
	$ : function(_sId){
		return $(_sId);
	}
}

var Pontoon = com.trs.pontoon.Pontoon;
//*/
Event.observe(window,'unload',function(){
	if(com.trs.pontoon._Pontoons){
		for (var i = 0 , n = com.trs.pontoon._Pontoons.length; i < n; i++){
			var oPontoon = com.trs.pontoon._Pontoons[i];
			oPontoon.destroy(true);
		}
	}
	com.trs.pontoon._Pontoons = null;
});
