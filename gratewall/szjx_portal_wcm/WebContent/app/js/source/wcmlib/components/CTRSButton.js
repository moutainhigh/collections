/*wcm.CTRSButton*/
Ext.ns('wcm.CTRSButton');
wcm.CTRSButton = function(cntId){
	this.cntId = cntId;
};
(function(){
	var m_sTrsBtnTemplates = {
		'cnt' : [
			"<div class='ctrsbtns_outer' style='overflow:visible'>",
			"<div class='ctrsbtns_inner ctrsbtns_inner{1}'>",
			"{0}",
			"</div>",
			"</div>"
		].join(''),
		'item' : [
			"<div class='ctrsbtn' _action='{1}'>",
				"<div class='ctrsbtn_left'>",
					"<div class='ctrsbtn_right'>{0}</div>",
				"</div>",
			"</div>"
		].join('')
	};
	Ext.apply(wcm.CTRSButton.prototype, {
		m_myJsonBtns : {},
		m_myArrBtns : [],
		prepare : function(){
			var m_myJsonBtns = this.m_myJsonBtns = {};
			var m_myArrBtns = this.m_myArrBtns;
			for(var i=0,n=m_myArrBtns.length;i<n;i++){
				m_myJsonBtns[m_myArrBtns[i].id] = m_myArrBtns[i];
			}
		},
		draw : function(){
			$(this.cntId).innerHTML = this.getHtml();
		},
		getHtml : function(){
			var result = [];
			var m_myArrBtns = this.m_myArrBtns;
			for(var i=0,n=m_myArrBtns.length;i<n;i++){
				result.push(String.format(m_sTrsBtnTemplates.item, m_myArrBtns[i].html, m_myArrBtns[i].id));
			}
			var html = String.format(m_sTrsBtnTemplates.cnt, result.join('\n'), m_myArrBtns.length);
			return html;
		},
		getTrsBtnItem : function(target){
			while(target!=null&&target.tagName!='BODY'){
				if(Element.hasClassName(target, 'ctrsbtn'))return target;
				target = target.parentNode;
			}
			return null;
		},
		init : function(){
			this.draw();
			var caller = this;
			Ext.get(this.cntId).on('click', function(event, target){
				var trsbtnItem = caller.getTrsBtnItem(target);
				if(trsbtnItem==null)return;
				var action = trsbtnItem.getAttribute('_action', 2);
				var item = caller.m_myJsonBtns[action];
				if(!item || !item.action)return;
				item.action.apply(null, [event, target]);
			});
		},
		setButtons : function(buttons){
			this.m_myArrBtns = buttons;
			this.prepare();
		},
		getButtons : function(){
			return this.m_myArrBtns;
		}
	});
})();