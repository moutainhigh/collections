package com.ly.common.utils;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



//https://blog.csdn.net/mr_orange_klj/article/details/78563906
//https://blog.csdn.net/qq_32611951/article/details/78048309
public class ReadTxtUtils {

	/*public static String ReadTxtFile(String strFilePath) throws IOException {
		String path = strFilePath;
		StringBuffer sb  = new StringBuffer();
		// 打开文件
		File file = new File(path);
		// 如果path是传递过来的参数，可以做一个非目录的判断
		InputStream instream = new FileInputStream(file);
		if (instream != null) {
			InputStreamReader inputreader = new InputStreamReader(instream,"GBK");
			BufferedReader buffreader = new BufferedReader(inputreader);
			//BufferedReader buffreader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
			String line;
			// 分行读取
			while ((line = buffreader.readLine()) != null) {
				//System.out.println();
				String str = line.substring(0,line.length()-8);
				System.out.println(str);
				sb.append(str);
				//newList.add(line.substring(0,line.length()-8));
			}
			buffreader.close();
			instream.close();
		}
		return sb.toString();
	}*/
	
	//https://blog.csdn.net/sinat_22797429/article/details/53118365
   public static List ReadTxtFile(String filepath){
	   List list = new ArrayList<String>();
      // StringBuilder result = new StringBuilder();
       try{
          // BufferedReader br = new BufferedReader(new FileReader(filepath));//构造一个BufferedReader类来读取文件
           BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"GBK"));//构造一个BufferedReader类来读取文件
           String tempLineStr = null;
           while((tempLineStr = br.readLine())!=null){//使用readLine方法，一次读一行
               //result.append(System.lineSeparator()+s);
        	   //System.out.println(s);
               list.add(tempLineStr);
           }
           br.close();    
       }catch(Exception e){
           e.printStackTrace();
       }
       return list;
   }
   
   
   
   public static List<String> getLines(String fileName){   
       List<String> lines=new ArrayList<String>();   
       try {   
          BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));   
           String line= null;   
           while ((line= br.readLine()) != null) {   
              lines.add(line);   
          }   
          br.close();   
       } catch (FileNotFoundException e){   
       }catch (IOException e){}   
       return lines;   
   }  

	
	public static void main(String[] args) throws IOException {
		ReadTxtUtils.ReadTxtFile("C:\\new_tdx\\T0002\\export\\沪深Ａ股20181203.txt");
	}
}