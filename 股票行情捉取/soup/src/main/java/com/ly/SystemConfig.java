package com.ly;

public class SystemConfig {
/*
	https://cloud.tencent.com/developer/article/1579729
	https://blog.csdn.net/qq_22003641/article/details/79137327
	https://blog.csdn.net/zhuyiquan/article/details/80148767
    http://www.selenium.org.cn/1680.html
		
*/
		
	//public static  String myAppendStr = " where benRiTotalHand - zuoRiTotalHand >0 and zuoRiTotalHand - qianRiTotalHand >0 order by benRiTotalHand/liuTongGu desc ,convert (closePrice, decimal(6, 2)) desc"; 
	public static  String myAppendStr = " where (benRiTotalHand)/(liuTongGu*1000000)*100  >=0.1 and convert((closePrice-prevClose)/prevClose*100,decimal(6,2))>2 order by   convert((closePrice-prevClose)/prevClose*100,decimal(6,2)) desc,benRiTotalHand/liuTongGu desc"; 
	
	
	//public static String condition = " where benRiTotalHand - zuoRiTotalHand >0 and zuoRiTotalHand - qianRiTotalHand >0 order by benRiTotalHand/liuTongGu desc";
	public static String condition = " where (benRiTotalHand)/(liuTongGu*1000000)*100  >=0.1 and convert((closePrice-prevClose)/prevClose*100,decimal(6,2))>2 order by   convert((closePrice-prevClose)/prevClose*100,decimal(6,2)) desc, benRiTotalHand/liuTongGu desc";
	
	
	//public static  String myAppendStrForShiRi = " where benRiTotalHand - zuoRiTotalHand >0  order by benRiTotalHand/liuTongGu desc ,convert (closePrice, decimal(6, 2)) desc"; 
	public static  String myAppendStrForShiRi = " where (benRiTotalHand)/(liuTongGu*1000000)*100  >=0.1  and convert((closePrice-prevClose)/prevClose*100,decimal(6,2))>2 order by   convert((closePrice-prevClose)/prevClose*100,decimal(6,2)) desc, benRiTotalHand/liuTongGu desc "; 
	
	
	//数据判断是否为空
	//https://www.cnblogs.com/crackedlove/p/10039939.html
	
	//git使用
    //https://blog.csdn.net/lhw_csd/article/details/81204202
	//https://blog.csdn.net/yang5726685/article/details/77044050 
	//https://www.jianshu.com/p/259fc4dd0c52
	//https://www.bbsmax.com/A/Gkz118OgzR/
	//https://jingyan.baidu.com/article/3ea51489b6c1ee52e61bba2f.html
	//https://www.baidu.com/s?ie=utf-8&f=3&rsv_bp=1&rsv_idx=1&tn=baidu&wd=eclipse%20git%E5%86%B2%E7%AA%81%E8%A7%A3%E5%86%B3&fenlei=256&oq=eclipse%2520git%25E4%25BD%25BF%25E7%2594%25A8%25E6%2595%2599%25E7%25A8%258B&rsv_pq=cfbf75a40003485a&rsv_t=3b1c7HBHFLifpspCOYn%2BKu4NIjrvgYRHKvorc1kL99mT2ft0mtARgKjbFFI&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_btype=i&inputT=7318&rsv_sug3=17&rsv_sug1=14&rsv_sug7=100&sug=eclipse%2520git%25E5%2586%25B2%25E7%25AA%2581%25E8%25A7%25A3%25E5%2586%25B3&rsv_n=1&bs=eclipse%20git%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B
	//git 冲突
	public static  String myAppendStrWeek = " where (benZhouTotalHand)/(liuTongGu*1000000)*100  >=0.1   and convert((closePrice-prevClose)/prevClose*100,decimal(6,2))>2 order by   convert((closePrice-prevClose)/prevClose*100,decimal(6,2)) desc, benZhouTotalHand/liuTongGu desc";
	
	
	
	
	//public static  String condintionForVol = " where  convert((closePrice-prevClose)/prevClose*100,decimal(6,2))>2 order by   convert((closePrice-prevClose)/prevClose*100,decimal(6,2)) desc, benZhouTotalHand/liuTongGu desc"; 
	public static  String condintionForVol = " order by   convert((closePrice-prevClose)/prevClose*100,decimal(6,2)) desc, benZhouTotalHand/liuTongGu desc"; 
	
	
	
	
	
	
	//2021---3-30修改的最新排序方式
	public static String orderStr = "order by  totalHand/liuTongGu, convert((closePrice-prevClose)/prevClose*100,decimal(6,2)) ";
}
