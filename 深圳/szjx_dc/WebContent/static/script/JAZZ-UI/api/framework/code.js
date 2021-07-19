//new UvumiTextarea({
	//selector:'textarea.withoutCounter',
	//maxChar:0
//});
function copyCode(obj){
	if(obj.value==""){
        alert("The content is null");
        return ;
    }
	var rng = document.body.createTextRange();
	rng.moveToElementText(obj);
	rng.scrollIntoView();
	rng.select();
	rng.execCommand("Copy");
	rng.collapse(false);
}

function runCode(obj) {
	if(obj==""){
		alert("The content is null");
		return ;
	}

	var winname = window.open('', "_blank", '');
	winname.document.open('text/html', 'replace');

 		winname.document.writeln(obj);	
 	
    winname.document.close();

}

function saveCode(obj) {
	if(obj.value==""){
        alert("The content is null");
        return ;
    }
	var winname = window.open('', '_blank', '');
	winname.document.open('text/html', 'replace');
	winname.document.writeln(obj.value);
	winname.document.close();
	winname.document.execCommand('saveas','','savecode.htm');
	winname.close();
}

function toogleCode(textarea){
	if(document.getElementById(textarea).style.display == "block"){
		document.getElementById(textarea).style.display = 'none';
	}else{
		document.getElementById(textarea).style.display = 'block';
	}
}

function run(url) {

	 window.open(url, "_blank", '');

}