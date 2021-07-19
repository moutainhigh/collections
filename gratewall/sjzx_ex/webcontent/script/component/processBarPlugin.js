/**
 * ���������
 * config����˵����
 */
var processBar = function(config){
	if (!config.id){
        alert("û�д��������ID������json���ò���id���ݹ�����");
        return;
    }else{
    	this.id = config.id;
    }
    
    if (!config.parentContainer){
        alert("û�ж��常������ID������json����parentContainer�������ݹ�����");
        return;
    }else{
    	if (!document.getElementById(config.parentContainer)){
    		alert("û���ҵ�IDΪ��" + config.parentContainer + "����Ԫ�أ�");
    		return;
    	}
    	this.parentContainer = config.parentContainer;
    }
    this.domParentContainer = document.getElementById(this.parentContainer);
    this.draw();
}

// ��ʼ����
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
// ���ðٷֱ�
processBar.prototype.setPercent = function(pecent){
    if(!isNaN(pecent)){
	document.getElementById(this.id + "_processBarPassedDiv").style.width = parseInt(pecent) + "%";
	}
}

// ������ʾ��Ϣ
processBar.prototype.setInfo = function(info){
	document.getElementById(this.id + "_processBarTextDiv").innerHTML = info;
}

// ����
processBar.prototype.reset = function(){
	this.draw();
}

// �õ��丸�ڵ�
processBar.prototype.getParentContainer = function(){
	return this.domParentContainer;
}

// ��ʾ
processBar.prototype.show = function(){
	this.domParentContainer.style.display = "";
}

// ����
processBar.prototype.hide = function(){
	this.domParentContainer.style.display = "none";
}