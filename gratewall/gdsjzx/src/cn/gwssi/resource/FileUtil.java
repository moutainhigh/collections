package cn.gwssi.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
	
	public static void main(String argv[])throws Exception{
        StringBuffer sb= new StringBuffer("");

        FileReader reader = new FileReader("C://Users//xue//Desktop//index.txt");

        BufferedReader br = new BufferedReader(reader);

        String str = null;
        while((str = br.readLine()) != null) {
              sb.append(str+"/n");
              //System.out.println("CREATE CLUSTERED INDEX "+str+"TIMESTAMP"+" ON dbo."+str+"(TIMESTAMP)");
              System.out.println("DROP INDEX "+str+"."+str+"TIMESTAMP");
        }
        br.close();
        reader.close();
		/*File F0 = new File("H:\\BaiduYunDownloadss");//创建目录
		System.out.println(F0.exists());//true 存在  false 不存在
		System.out.println(!F0.exists());// false 存在  true 不存在
		copyFileAndDir("E:\\WeChatFiles\\WeChat Files\\All Users","G:\\123");
		System.out.println("=====");
		Thread.sleep(10000);
		delFileAndDir("G:\\123");
		System.out.println("=====");
		Thread.sleep(10000);
		File desDir = new File("G:\\123");//创建目录
		if(!desDir.exists()){//如果不存在就新建
			if (!desDir.mkdir()){
	          System.out.println("目標文件夾不存，創建失敗!");
	        }
		}*/
	}

	/**
	 * 删除文件和目录
	 * @param src
	 */
	public static void delFileAndDir(String src){
		File desDir = new File(src);//创建目录
		if(desDir.isDirectory()){//目录
			File[] allFile = desDir.listFiles();
			int totalNum = allFile.length;
			if(totalNum>0){
				int currentFile = 0;
				String srcName = "";
			    for (currentFile = 0; currentFile < totalNum; currentFile++){	    
			        if (!allFile[currentFile].isDirectory()){  						
			        	srcName = allFile[currentFile].toString();
			            delFile(srcName);
			        }else{//如果是文件夾就采用遞歸處理
			        	delFileAndDir(allFile[currentFile].getPath().toString());
			        }
			    }
			}else{
				delFile(src);
			}
		}
		delFile(src);
	}
	
	/**
	 * 删除文件
	 * @param srcName
	 */
	public static void delFile(String srcName){
		File desDir = new File(srcName);//创建
		desDir.delete();
	}
	
	/**
	 * 创建文件或者目录
	 * @param fileNameOrPath
	 * @param falg (true是文件 false 目录)
	 * @throws IOException 
	 */
	public static File createFileOrDir(String fileNameOrPath,boolean falg) {
		File desNameOrDir = new File(fileNameOrPath);//创建目录或者文件
		if(!desNameOrDir.exists()){//如果不存在就新建
			if(falg){
				try {
					if (!desNameOrDir.createNewFile()){
					   System.out.println("目標文件不存，創建失敗!");
					}
				} catch (IOException e) {
					System.out.println("目標文不存，創建失敗!");
					desNameOrDir = null;
					e.printStackTrace();
				}
			}else{
				if (!desNameOrDir.mkdir()){
					desNameOrDir = null;
			        System.out.println("目標文件夾不存，創建失敗!");
			    }
			}
			
		}
		return desNameOrDir;
	}
	
	/**
	 * 拷贝文件和目录
	 * @param src
	 * @param des
	 */
	public static void copyFileAndDir(String src,String des) {
		File desDir = new File(des);//创建目录
		if(!desDir.exists()){//如果不存在就新建
			if (!desDir.mkdir()){
	          System.out.println("目標文件夾不存，創建失敗!");
	        }
		}
		File srcDir = new File(src);
		File[] allFile = srcDir.listFiles();//取得當前目錄下面的所有文件，將其放在文件數組中
		int totalNum = allFile.length; //取得當前文件夾中有多少文件（包括文件夾）
	    String srcName = "";
	    String desName = "";
	    int currentFile = 0;
	    for (currentFile = 0; currentFile < totalNum; currentFile++){	    //一個一個的拷貝文件
	        if (!allFile[currentFile].isDirectory()){  						//如果是文件是采用處理文件的方式
	        	srcName = allFile[currentFile].toString();
	        	desName = des + "\\" + allFile[currentFile].getName();
	            copyFile(srcName, desName);
	        }else{//如果是文件夾就采用遞歸處理
	        	//利用遞歸讀取文件夾中的子文件下的內容，再讀子文件夾下面的子文件夾下面的內容...递归调用
	        	copyFileAndDir(allFile[currentFile].getPath().toString(), des + "\\" + allFile[currentFile].getName().toString());
	        }
	    }
	}
	
	/**
	 * 拷贝文件
	 * @param srcName
	 * @param desName
	 */
	public static void copyFile(String srcName,String desName){
		FileInputStream FIS = null;
		FileOutputStream FOS = null;
		try {
			FIS = new FileInputStream(srcName);
			FOS = new FileOutputStream(desName);
			byte[] bt = new byte[1024];
			int readNum = 0;
			while ((readNum = FIS.read(bt)) != -1){
			    FOS.write(bt, 0, bt.length);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				FIS.close();
				FOS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
