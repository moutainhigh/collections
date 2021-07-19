Ext.ns('wcm.special.state');

/**
*以内存数组的方式，用于保存设计页面中的状态，以便可以进行redo,undo操作。
*为了防止浏览器死机导致的问题，后期可以尝试使用类似cookie等这种方式
*/
wcm.special.state.ArrayStateProvider = function(){
	var maxCount = 21;//允许撤销的最大次数+1
	var index = -1;//记录当前正在使用的状态索引位
	var states = [];//用户存储已经保存的状态信息
	return {
		/**
		*常量标示，在getState时使用，用于redo时获取状态
		*/
		TYPE_REDO : 1,
		/**
		*常量标示，在getState时使用，用于undo时获取状态
		*/
		TYPE_UNDO : -1,
		/**
		*用于获取当前状态
		*当nTypeFlag=TYPE_REDO时，获取redo时的状态，内部索引位将递增；
		*当nTypeFlag=TYPE_UNDO时，获取undo时的状态，内部索引位将递减；
		*但nTypeFlag为其它值时，获取索引位当前状态，内部索引位不发生变化；
		*如何内部没有有效的状态值，则返回null；
		*/
		getState : function(nTypeFlag){
			switch(nTypeFlag){
				case this.TYPE_UNDO:
					if(index <= 0) return null;
					return states[--index];
				case this.TYPE_REDO:
					if(index >= states.length - 1) return null;
					return states[++index];
				default:
					return states[index];
			}
		},
		/**
		*测试当前状态信息
		*当nTypeFlag=TYPE_REDO时，测试是否可以执行redo操作；
		*当nTypeFlag=TYPE_UNDO时，测试是否可以执行undo操作；
		*如何内部没有有效的状态值，则返回null；
		*/
		testState : function(nTypeFlag){
			switch(nTypeFlag){
				case this.TYPE_UNDO:
					if(index <= 0) return null;
					return states[index-1];
				case this.TYPE_REDO:
					if(index >= states.length - 1) return null;
					return states[index+1];
				default:
					return states[index];
			}
		},
		/**
		*保存一个新状态，同时内部索引位将进行+1操作，
		*可以通过接口getState(TYPE_UNDO)来获取该状态；
		*/
		saveState : function(state){
			if(!state) return;
			if(index + 1 > maxCount - 1){
				states.shift(0);
				index--;
			}
			states[++index] = state;
			//设置length为index+1,之前index+1之后的状态将废弃
			states.length = index + 1;
		}
	};
}();


(function(){
	/**
	*用于提供一些调用接口，收集和还原页面的当前状态，
	*从而进行设计页面的撤销和重做操作;
	*内部支持savestate和restorestate两个事件，分别
	*用于进行保存状态之前和恢复状态之后做一些额外处理，
	*如：保存状态时，先保留有焦点的元素，然后在恢复状态时，恢复有焦点的元素等；
	*不过state对象自身，只能存入类似id这样的字符串信息，而不能是dom元素本身，
	*因为撤销和重做时，整个页面的“wcm-mustExistId”区域已经重新加载了。
	*/	
	function CStateHandler(stateProvider){
		this.stateProvider = stateProvider;
		this.addEvents('savestate', 'restorestate','aftersavestate');			
	};
	
	Ext.extend(CStateHandler, wcm.util.Observable, {
		/**
		*获取当前的状态提供者
		*/
		getStateProvider : function(){
			return this.stateProvider;
		},
		/**
		*保存设计页面当前状态
		*/
		saveState : function(){
			//获取document.body第一个dom子节点的内容，以便后期进行恢复
			//这里隐含了一个关键点，即：第一个节点为真实的可视化内容
			var state = {html : Element.first(document.body).innerHTML};
			//触发savestate事件，以便收集一些其他需要定制的状态信息
			this.fireEvent('savestate', state);
			//保存已经收集之后的状态对象
			this.stateProvider.saveState(state);
			// 触发aftersave事件，当保存一个状态之后需要刷新导航树等操作
			this.fireEvent('aftersavestate', state);
		},
		/**
		*还原设计页面到指定状态
		*/
		restoreState : function(state){
			//用状态中的html恢复document.body第一个dom子节点的内容
			//这里隐含了一个关键点，即：第一个节点为真实的可视化内容
			Element.update(Element.first(document.body), state.html);
			//触发restorestate事件，以便还原一些其他已经定制的状态信息
			this.fireEvent('restorestate', state);
		},
		/**
		*执行重做操作
		*/
		redo : function(){
			var stateProvider = this.stateProvider;
			var state = stateProvider.getState(stateProvider.TYPE_REDO);
			if(!state) return;
			this.restoreState(state);
		},
		/**
		*执行撤销操作
		*/
		undo : function(){
			var stateProvider = this.stateProvider;
			var state = stateProvider.getState(stateProvider.TYPE_UNDO);
			if(!state) return;
			this.restoreState(state);
		}
	});
	wcm.special.state.StateHandler = new CStateHandler(wcm.special.state.ArrayStateProvider);
})();