

function checkEnName(enName,msg)
{
	///У�����ݱ�Ӣ������
	if(/^\d+$/.test(enName)||/['"<>]/.test(enName)){
	alert(msg+'����ȫ��������!')
	return false;
	}else if(/.*[\u4e00-\u9fa5]+.*$/.test(enName)) {
	alert(msg+'���ܺ�������!')
	return false;
	}else if(!/[a-zA-Z]+/.test(enName)) {
	alert(msg+'���ٺ���һ����ĸ!')
	return false;
	}else if(!/^[a-zA-Z0-9_]{1,}$/.test(enName)) {
	alert(msg+'���ܺ��������ַ�!')
	return false;
	}else{
		var length=enName.length;
		var chEnd=enName.substr(length-1,1);
		var chBegin=enName.substr(0,1);
		if (chEnd == "_") {
			alert(msg+'������_��β!')
			return false;
		}else  if(!((chBegin>="a"&&chBegin<="z")||(chBegin>="A"&&chBegin<="Z"))){
			alert(msg+'��������ĸ��ͷ!')
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
		alert(msg+"���Ȳ��ܳ���"+len);
		return false;
	}else{
		return true;
	}
}

function checkItem(item,len,msg)
{
	
	if(item!=null&&""!=item&&item.length>len){
		alert(msg+"���Ȳ��ܳ���"+len);
	    return false;
	}else if(item==null||item==""){
		alert(msg+"����Ϊ��!");
		return false;
	}else{
		return true;
	}
}
function checkItemTypeLength(dataitem_type,dataitem_long)
{
	
	if(dataitem_type!=null&&(dataitem_type=="01"||dataitem_type=="02")&&(dataitem_long==null||dataitem_long=="")){
		alert('�������������!')
	    return false;
	}else{
		return true;
	}
}
function check_int(id)
{
 
    if(id==null||id==""){
		return true;
	}else if (!isNumber(id,"�������")) {
		return false;
	}else if (id >9999) {
		alert("������Ȳ��ܳ���4λ��!");
		return false;
	}
	else {
		return true;
	}
}
