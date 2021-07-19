/**
 * 进度条组件
 * config参数说明：
 */
var processBar = function(config){
	if (!config.id){
        alert("没有传入组件的ID！请在json中用参数id传递过来！");
        return;
    }else{
    	this.id = config.id;
    }
    
    if (!config.parentContainer){
        alert("没有定义父容器的ID！请在json中用parentContainer参数传递过来！");
        return;
    }else{
    	if (!document.getElementById(config.parentContainer)){
    		alert("没有找到ID为【" + config.parentContainer + "】的元素！");
    		return;
    	}
    	this.parentContainer = config.parentContainer;
    }
    this.domParentContainer = document.getElementById(this.parentContainer);
    this.draw();
}

// 开始绘制
processBar.prototype.draw = function(){
	var p = this;
	var parentContainer = document.getElementById(p.parentContainer);
	parentContainer.innerHTML = "";
	
	var processBarDiv = document.createElement("div");
	processBarDiv.className = "processBarDiv";
	parentContainer.appendChild(processBarDiv);
	
	var processBarPassedDiv = document.createElement("div");
	processBarPassedDiv.className = "processBarPassedDiv";
	processBarPassedDiv.id = p.id + "_processBarPassedDiv";
	processBarDiv.appendChild(processBarPassedDiv);
	processBarPassedDiv = null;
	
	var processBarTextDiv = document.createElement("div");
	processBarTextDiv.className = "processBarTextDiv";
	processBarTextDiv.id = p.id + "_processBarTextDiv";
	processBarDiv.appendChild(processBarTextDiv);
	processBarTextDiv = null;
	p = null;
}
// 设置百分比
processBar.prototype.setPercent = function(pecent){
    if(!isNaN(pecent)){
	document.getElementById(this.id + "_processBarPassedDiv").style.width = parseInt(pecent) + "%";
	}
}

// 设置显示信息
processBar.prototype.setInfo = function(info){
	document.getElementById(this.id + "_processBarTextDiv").innerHTML = info;
}

// 重置
processBar.prototype.reset = function(){
	this.draw();
}

// 得到其父节点
processBar.prototype.getParentContainer = function(){
	return this.domParentContainer;
}

// 显示
processBar.prototype.show = function(){
	this.domParentContainer.style.display = "";
}

// 隐藏
processBar.prototype.hide = function(){
	this.domParentContainer.style.display = "none";
}