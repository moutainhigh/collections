package com.gwssi.common.util;



/**
 * ����У�鸨��������
 * @author andy
 *
 */
public class ObjectChecker {
	
	
	/**
	 * ȡ���ַ������� ȫ���ַ�����Ϊ�����ַ�������
	 * @param obj
	 * @return
	 */
	public static int getLength(Object obj){
		String value = String.valueOf(obj);
		int size = 0;
		for (int i = 0 ; i < value.length();i++){
			char c = value.charAt(i);
			if((c >='0'&&c <= '9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z')){    
				size++;    
			}else if(Character.isLetter(c)){
			     size += 2;    
			}else{    
				size++;    
			}    
		}
		return size;
	}
    
    /**
     * ͨ������str�ַ�����size�����н�ȡ�ַ���������char���ȴ����ȡ�����ʵĳ���
     * @param str ��Ҫ��ȡ���ַ���
     * @param size ��ȡ��ʵ���ַ������ȣ���ǣ�
     * @return ���ؽ�ȡ����ַ������������̳���1
     */
    public static String substrByLength(String str,int size){
        if (getLength(str) <= size){
            return str;
        }
        int currLength = size;
        int strLength = str.length();
        String strTemp = str;
        int btLength = 0;
        while (btLength - size != 0 && btLength - size != -1){
            if (strLength > currLength){
                strTemp = str.substring(0,currLength-1);
            }            
            btLength = getLength(strTemp);
            currLength--;
        }
        return strTemp;
    }
	
    /**
    * �Ƚ���������Ĵ�С�� <br>
    * ��� obj_1 ���� obj_2������1�� <br>
    * ��� obj_1 ���� obj_2������0�� <br>
    * ��� obj_1 С�� obj_2������-1�� <br>
    * 
    * @param obj_1
    * @param obj_2
    * @return
    */
    public static int compareTo(Object obj_1, Object obj_2){
    	Class type = obj_1.getClass();
    	if (type.equals(String.class)){
    		String str_1 = (String) obj_1;
    		String str_2 = (String) obj_2;
    		return str_1.compareTo(str_2);
    	} else if (type.equals(Integer.class)){
    		Integer int_1 = (Integer) obj_1;
    		Integer int_2 = (Integer) obj_2;
    		return int_1.compareTo(int_2);
    	} else if (type.equals(Long.class)){
    		Long long_1 = (Long) obj_1;
    		Long long_2 = (Long) obj_2;
    		return long_1.compareTo(long_2);
    	} else if (type.equals(Double.class)){
    		Double double_1 = (Double) obj_1;
    		Double double_2 = (Double) obj_2;
    		return double_1.compareTo(double_2);
    	}
    	return 0;
    }
}
