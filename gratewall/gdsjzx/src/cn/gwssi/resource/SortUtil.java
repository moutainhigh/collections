package cn.gwssi.resource;

public class SortUtil {
	public static String[] bubbleSort(String []data) {  
    	String temp;  
        for (int i = 0; i < data.length; i++) {  
            int flag = 0;  
            for (int j = 0; j < data.length-i-1; j++) {
                if (Long.parseLong(data[j].split("-")[0])<Long.parseLong(data[j+1].split("-")[0])) {  
                    temp=data[j];  
                    data[j]=data[j+1];  
                    data[j+1]=temp;  
                    flag = 1;  
                }  
            }  
            if(flag==0)break;  
           /* System.out.print("第"+i+"遍:{");  
            for (int k = 0; k < data.length; k++) {  
                System.out.print(data[k]+",");  
            }  
            System.out.println("}"); */ 
        } 
        /*System.out.println("nihao");
        for (int k = 0; k < data.length; k++) {  
            System.out.print(data[k]+",");  
        }*/
		return data;
    }
	
	public static int [] bubbleSort(int []data){  
        int temp;  
        for (int i = 0; i < data.length; i++) {  
            int flag = 0;  
            for (int j = 0; j < data.length-i-1; j++) {  
                if (data[j]<data[j+1]) {  
                    temp=data[j];  
                    data[j]=data[j+1];  
                    data[j+1]=temp;  
                    flag = 1;  
                }  
            }  
            if(flag==0)break;  
            /*System.out.print("第"+i+"遍:{");  
            for (int k = 0; k < data.length; k++) {  
                System.out.print(data[k]+",");  
            }  
            System.out.println("}"); */ 
        } 
        /*System.out.println("nihao");
        for (int k = 0; k < data.length; k++) {  
            System.out.print(data[k]+",");  
        }*/
		return data;
    } 
}
