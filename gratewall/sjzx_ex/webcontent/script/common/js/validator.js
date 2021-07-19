

function checkEnName(enName,msg)
{
	///校验数据表英文名字
	if(/^\d+$/.test(enName)||/['"<>]/.test(enName)){
	alert(msg+'不能全部是数字!')
	return false;
	}else if(/.*[\u4e00-\u9fa5]+.*$/.test(enName)) {
	alert(msg+'不能含有中文!')
	return false;
	}else if(!/[a-zA-Z]+/.test(enName)) {
	alert(msg+'至少含有一个字母!')
	return false;
	}else if(!/^[a-zA-Z0-9_]{1,}$/.test(enName)) {
	alert(msg+'不能含有特殊字符!')
	return false;
	}else{
		var length=enName.length;
		var chEnd=enName.substr(length-1,1);
		var chBegin=enName.substr(0,1);
		if (chEnd == "_") {
			alert(msg+'不能以_结尾!')
			return false;
		}else  if(!((chBegin>="a"&&chBegin<="z")||(chBegin>="A"&&chBegin<="Z"))){
			alert(msg+'必须以字母开头!')
			return false;
		}
		else {
			return true;
		}
	}
}


function checkItemLength(item,len,msg)
{
	
	if(item==null||item==""){
	    return true;
	}else if(item.length>len){
		alert(msg+"长度不能超过"+len);
		return false;
	}else{
		return true;
	}
}

function checkItem(item,len,msg)
{
	
	if(item!=null&&""!=item&&item.length>len){
		alert(msg+"长度不能超过"+len);
	    return false;
	}else if(item==null||item==""){
		alert(msg+"不能为空!");
		return false;
	}else{
		return true;
	}
}
function checkItemTypeLength(dataitem_type,dataitem_long)
{
	
	if(dataitem_type!=null&&(dataitem_type=="01"||dataitem_type=="02")&&(dataitem_long==null||dataitem_long=="")){
		alert('请输入数据项长度!')
	    return false;
	}else{
		return true;
	}
}
function check_int(id)
{
 
    if(id==null||id==""){
		return true;
	}else if (!isNumber(id,"数据项长度")) {
		return false;
	}else if (id >9999) {
		alert("数据项长度不能超过4位数!");
		return false;
	}
	else {
		return true;
	}
}
