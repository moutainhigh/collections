package com.gwssi.ad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class txttest {

	public static void main(String[] args) {
		txttest tt =new txttest();
		List ouList = tt.getOU();
		
		System.out.println(ouList);
		
//		File file = new File("D:/man.txt");
//		BufferedReader reader = null;
//		String tempString = null;
//		int line = 1;
//
//		try {
//			reader = new BufferedReader(new InputStreamReader(
//					new FileInputStream(file), "gbk"));
//			while ((tempString = reader.readLine()) != null) {
//				System.out.println(tempString.trim());
//				line++;
//			}
//			reader.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (reader != null) {
//				try {
//					reader.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}
	
	public List getOU(){
		
		List ouList = new ArrayList();
		
		File file = new File("D:/man.txt");
		BufferedReader reader = null;
		String tempString = null;
		int line = 1;

		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "gbk"));
			while ((tempString = reader.readLine()) != null) {
				if(!"".equals(tempString)){
					ouList.add(tempString.trim());
				}
				
				line++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ouList;
	}
		
}