/**
 * 帮助性步骤组件
 * 构造函数中，config可以接收三个参数：id, textArray, parentContainer
 * 提供一个对外的成员函数：setCurrentStep(number)
 */
var stepHelp = function(config){
	if (!config.id){
        alert("没有传入组件的ID！请在json中用参数id传递过来！");
        return;
    }
    
	if (!config.parentContainer){
		alert("没有定义父容器的ID！请在json中用parentContainer参数传递过来！");
		return;
	}
	
	if (!config.textArray){
		alert("没有定义步骤对应的文本数据！请在json中用textArray参数传递过来！");
		return;
	}
	
	if (!(config.textArray instanceof Array) || config.textArray.length < 1){
		alert("传入的textArray不是有效的数组！");
		return;
	}
	
	this.id					= config.id;
	this.parentContainer	= document.getElementById(config.parentContainer);
	this.textArray			= config.textArray;
	this.maxStep			= this.textArray.length;
	this.lastStep			= 0;
	this.init();
}

stepHelp.prototype = {
	init: function(){
		var parentContainer = this.parentContainer;
		
		// 添加后面的span和arrow
		for (var i = 0; i < this.maxStep; i ++){
			var tempContainer = document.createElement("span");
			
			var tempLink = document.createElement("a");
			tempLink.onclick = function(){
				return false;
			};
			tempLink.className = "stepHelpLink";
			
			var tempEm = document.createElement("em");
			tempEm.className = "stepHelpEm";
			
			var tempSpan = document.createElement("span");
			tempSpan.className = "stepHelpSpan";
			tempSpan.innerHTML = this.textArray[i];
			tempLink.appendChild(tempEm);
			tempEm.appendChild(tempSpan);
			tempContainer.appendChild(tempLink);
			parentContainer.appendChild(tempContainer);
			tempContainer = null;
			tempSpan = null;
			tempEm = null;
			tempLink = null;
			
			if (i != this.maxStep - 1){
				var tempArrowLink = document.createElement("a");
				tempArrowLink.className = "postStepHelpArrow";
				tempArrowLink.innerHTML = "<img src='/script/tabs/tabs-arrow.gif' border='0' />";
				parentContainer.appendChild(tempArrowLink);
				tempArrowLink = null;
			}
		}
		
		parentContainer = null;
		this.setCurrentStep(0);
	},
	
	setCurrentStep : function(number){
		var p = this;
		number = parseInt(number);
		p.parentContainer.childNodes[2 * p.lastStep].className = "";
		p.parentContainer.childNodes[2 * number].className = "selected";
		
		for (var i = 0; i < number && i < p.textArray.length; i++){
			if (p.parentContainer.childNodes[2*i + 1].className != "preStepHelpArrow"){
				p.parentContainer.childNodes[2*i + 1].className = "preStepHelpArrow";
			}
		}
		
		for (var j = number; j < p.textArray.length - 1; j++){
			if (p.parentContainer.childNodes[2*j + 1].className != "postStepHelpArrow"){
				p.parentContainer.childNodes[2*j + 1].className = "postStepHelpArrow";
			}
		}		
		p.lastStep = number;
	}
}