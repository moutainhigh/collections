package com.gwssi.common.util;

import java.util.List;

/**
 * 针对List 对象的辅助处理类
 * @author andy.lee
 *
 */
public class ListUtil {
	/**
    * List对象中 元素位置交换
    * @param list
    * @param index_1
    * @param index_2
    */
    public static void swap(List list, int index_start, int index_end)
    {
	    if (index_start >= index_end){
	        return;
	    }
	    if (index_end >= list.size()){
	        return;
	    }
	    Object startObj = list.get(index_start);
	    Object endObj = list.get(index_end);
	
	    list.remove(startObj);
	    list.remove(endObj);
	
	    list.add(index_start, endObj);
	    list.add(index_end, startObj);
    }

    /**
     * 根据List里面其中的一个属性值进行排序
	 * @param list
	 *          The list which will be sorted.
	 * @param orderType
	 *          类型 "asc" and "desc"
	 * @param byName
	 */
    public static void sort(List list, String orderType, String byName){
    	try{
	    	int size = list.size();
	    	for (int i = 0; i < size - 1; i++){	
	    		for (int j = i + 1; j < size; j++){	
	    			Object obj_i = list.get(i);
	    			Object obj_j = list.get(j);	
	    			Object obj_forCompare_i = BeanUtil.getValue(obj_i, byName);
	    			Object obj_forCompare_j = BeanUtil.getValue(obj_j, byName);
	    			// 如果其中有一个为空值
	    			if (obj_forCompare_i == null || obj_forCompare_j == null){
	    				//将空值向后移动
	    				if (obj_forCompare_i == null && obj_forCompare_j != null){
	    					swap(list, i, j);
		    			}
	    				continue;
		    		}
	    			int compareResult = ObjectChecker.compareTo(obj_forCompare_i, obj_forCompare_j);
	    			if ((orderType.equals("asc") && compareResult > 0)
	    					|| (orderType.equals("desc") && compareResult < 0)){
		    			swap(list, i, j);
		    		}
		    	}
		    }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
