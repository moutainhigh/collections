package cn.gwssi.broker.client.callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import cn.gwssi.common.model.AsynReponseContext;

public class MyCallBack extends CallBack{
	@Override
	public void execute(AsynReponseContext reponseContext) {
		System.out.println(reponseContext.getCode());
		System.out.println(reponseContext.getDesc());
		System.out.println(reponseContext.getErrorCode());
		System.out.println(reponseContext.getErrorMsg());
		
		List<String> list = (List) reponseContext.getResponseResult();
		if(list!=null && list.size()>0){
			for(String s:list){
				System.out.println("数据："+s);
			}
		}else{
			System.out.println("wu数据：");
		}
	}

	@Override
	public void execute(String path) {
		File filePath=new File(path);
		File[] file = filePath.listFiles();
		for (int i = 0; i < file.length; i++) {
			BufferedReader reader = null;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file[i]));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	                System.out.println("line " + line + ": " + tempString);
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
		}
	}

}
