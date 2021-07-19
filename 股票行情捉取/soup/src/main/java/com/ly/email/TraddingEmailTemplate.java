package com.ly.email;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.common.util.NumberToRMB;
import com.ly.common.util.RowColor;


@Component
public class TraddingEmailTemplate {
	
	//https://www.cnblogs.com/liuyitian/p/4065654.html
	//https://blog.csdn.net/hj7jay/article/details/56479431
	
	@Autowired
	private MyJavaMailSenderUtil srv;
	
	/*@Autowired
	private PDFUtil pdfutil;*/
	
	
	public static double get2Double(double a){  
	    DecimalFormat df=new DecimalFormat("0.00");  
	    return new Double(df.format(a).toString());  
	}  
	
	
	public static void main(String[] args) {
		System.out.println(get2Double(2.5867));;
	}
	
	
	public  void send(String title,List<?> stockList) throws Exception{
		StringBuffer sb = new StringBuffer();
		String code = "";
		String name  ="";
		String price  = "";
		String hangye = "";
		String diqu ="";
		String liuTongGuBen = "";
		String totalHand ="";
		sb.append("<table style='width: 100%; '>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("	<td style='background:url(https://rescdn.qqmail.com/zh_CN/htmledition/images/xinzhi/bg/a_10.jpg) repeat-x #bfdfec; min-height:550px; padding: 100px 55px 200px; '>");
		sb.append("	<div>");
		sb.append("	<table  style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		sb.append("<tr >");
		sb.append("<td "+RowColor.title+">序号</td>");
		sb.append("<td "+RowColor.title+">日K线</td>");
		sb.append("<td "+RowColor.title+">周K线</td>");
		sb.append("<td "+RowColor.title+">概要</td>");
		sb.append("</tr>");
		
		if (stockList != null && stockList.size() > 0) {
			
			for (int i = 0; i < stockList.size(); i++) {
				//sb.append("代码【" + stockList.get(i).getCode() + "】,名称【" + stockList.get(i).getName() + "】");
				
				
				//https://www.cnblogs.com/yangyi9343/p/4753675.html
				//https://www.cnblogs.com/a8457013/p/8135650.html
				//https://www.cnblogs.com/jiangyi-uestc/p/5689771.html
				//https://bbs.csdn.net/wap/topics/380039856
				//http://outofmemory.cn/code-snippet/83647/usage-POI-Java-mirror-jizhi-daochu-data-come-excel
				
				Field [] fields = stockList.get(i).getClass().getDeclaredFields();
				//System.out.println(fields);
				for (Field field : fields) {
					field.setAccessible(true); 
					if(field.getName().equals("code")){
						code =  (String) field.get(stockList.get(i));
					}
					
					
					if(field.getName().equals("name")){
						name =  (String) field.get(stockList.get(i));
					}
					
					if(field.getName().equals("closePrice")){
						price =  (String) field.get(stockList.get(i));
					}
					
					if(field.getName().equals("totalHand")){
						totalHand =  (String) field.get(stockList.get(i));
					}
					

					if(field.getName().equals("liuTongGu")){
						liuTongGuBen =  (String) field.get(stockList.get(i));
					}
					
					
				}
				
				String daypicUrl = "<img src='http://image.sinajs.cn/newchart/daily/n/CODE.gif'/>";
				String weekpicUrl = "<img src='http://image.sinajs.cn/newchart/weekly/n/CODE.gif'/>";
				//String monthUrl = "<img src='http://image.sinajs.cn/newchart/monthly/n/CODE.gif'/>";
				
				if(code.indexOf("6")==0){
					daypicUrl = daypicUrl.replace("CODE", "sh" +code);
					weekpicUrl = weekpicUrl.replace("CODE", "sh" +code);
					//monthUrl = monthUrl.replace("CODE", "sh" +code);
				}else{
					daypicUrl = daypicUrl.replace("CODE", "sz" +code);
					weekpicUrl = weekpicUrl.replace("CODE", "sz" +code);
					//monthUrl = monthUrl.replace("CODE", "sz" +code);
				}
			   
				
			  /* String daypicUrl = "<img src='http://120.78.225.98/soup/api/pic/day/"+code+".do'>";
			   String weekpicUrl = "<img src='http://120.78.225.98/soup/api/pic/week/"+code+".do'>";*/
				
				
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed(false);
				//本日
				Double amout1 = Double.valueOf(totalHand)*Double.valueOf(price)*100;
				String str = nf.format(amout1);
				
				String strAppend =",现量"+NumberToRMB.formatNum(str,false);
				double rat01 = (Double.valueOf(totalHand)*100)/(Double.valueOf(liuTongGuBen)*100000000)*100;
				String rates = ",本日换手:"+get2Double(rat01)+"%";
				
				String stockInfos = "代码："+code +",名称："+name +",现价："+price;
				
				
				String totalString = stockInfos+strAppend+rates;
			   
				if(i%2==0){
					sb.append("<tr>");
					sb.append("<td "+RowColor.evenLine+">"+(i+1)+"</td>");
					sb.append("<td "+RowColor.evenLine+">"+daypicUrl+"</td>");
					sb.append("<td "+RowColor.evenLine+">"+weekpicUrl+"</td>");
					//sb.append("<td "+RowColor.evenLine+">"+monthUrl+"</td>");
					sb.append("<td "+RowColor.evenLine+">"+totalString+"</td>");
					sb.append("</tr>");
				}else{
					sb.append("<tr>");
					sb.append("<td "+RowColor.oddLine+">"+(i+1)+"</td>");
					sb.append("<td "+RowColor.oddLine+">"+daypicUrl+"</td>");
					sb.append("<td "+RowColor.oddLine+">"+weekpicUrl+"</td>");
					//sb.append("<td "+RowColor.oddLine+">"+monthUrl+"</td>");
					sb.append("<td "+RowColor.oddLine+">"+totalString+"</td>");
					sb.append("</tr>");
				}
			}
			
			sb.append("	</table>");
			sb.append("	</div>");
			sb.append("	</td>");
			sb.append("	</tr>");
			sb.append("	</tbody>");
			sb.append("	</table>");
			
			srv.sendHtmlEmail(title, sb.toString(),true);
			
		
		}
		
	}
}
