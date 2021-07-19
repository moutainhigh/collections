/**
*
校验传入的序列是否连续，如果连续，则返回分隔的序列，否则返回false。
数据是否连续，举例如下，其中[a,b]看成是b分之a，即：a/b，

[1,1]为连续，因为加起来为1；
[1,2]，[1,2]为连续，因为加起来为1；
[1,3]，[1,3]，[1,3]为连续，因为加起来为1；
[1,2]，[1,4]，[1,4]为连续，因为加起来为1；
[1,2]，[1,2]，[1,3]，[1,3]，[1,3]为连续，因为是连续的1相加，即：1+1

但
[1,2]，[1,3]，[1,3]，[1,3]，[1,3]为不连续，因为加起来不是1的倍数
[1,2]，[1,3]，[1,2]，[1,3]，[1,3]为不连续，因为尽管加起来是1的倍数，但不是连续的1相加

*@param	items	一个数组，其中的每一个元素为长度为2的数组；如：[[1,2], [1,2], [1,3], [1,3], [1,3]]
*@return	如果连续，则返回分隔的序列，否则返回false
*/
function VerifySequence(items){
	/*
	*实现思路：依次遍历传入的数字，如果累加起来为1，则清零后继续累加；
	*直到所有的数据累加结束，则认为成功；或者中途出现累加结果大于1的情况则认为失败。
	*
	*如何进行数据相加：如果分母相同，则分子相加；否则先求分母最小公倍数，使分母增至相同公倍数时再分子相加
	*/
	var splitIndexes = [];
	var accumulatedItem = [0, 0];
	for(var index = 0; index < items.length; index++){

		//与下一个元素累加
		var composite = CommonMultipleTranslate(accumulatedItem, items[index]);

		accumulatedItem = [composite[0][0] + composite[1][0], composite[1][1]];

		//如果当前累加的值已经是1，则清零，继续下一个
		if(accumulatedItem[0] == accumulatedItem[1]){
			splitIndexes.push(index);
			accumulatedItem = [0, 0];
			continue;
		}
		if(accumulatedItem[0] > accumulatedItem[1]){
			return false;
		}
	}
	return splitIndexes;
	//return accumulatedItem[0] == accumulatedItem[1] ? splitIndexes : false;
}


/**
*对传入的数据进行最小公倍数的转换，如：
其中[a,b]看成是b分之a，即：a/b。
item1:[1,2]
item2:[1,3]
则返回：
[[3,6], [2,6]]

*@param	item1	包含两个元素的数组，如：[1,2]
*@param	item2	包含两个元素的数组，如：[1,2]
*@return		返回求最小公倍数后的数组[[a1,a2], [b1,b2]]，但如果a1或a2等于0，则原样返回
*/
function CommonMultipleTranslate(item1, item2){
	if(item1[0] == 0 || item1[1] == 0 || item1[1] == item2[1]){
		return [item1, item2];
	}
	if(item1[1] < item2[1] && (item2[1] % item1[1] == 0)){
		var t = item2[1] / item1[1];
		return [[t*item1[0], t*item1[1]], item2];
	}
	if(item1[1] > item2[1] && (item1[1] % item2[1] == 0)){
		var t = item1[1] / item2[1];
		return [item1, [t*item2[0], t*item2[1]]];
	}
	return [[item2[1]*item1[0], item2[1]*item1[1]], [item1[1]*item2[0], item1[1]*item2[1]]];
}