package cn.gwssi.common.resource;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 数据处理工具类(文件读写及list与xml转化)
 * @author xue
 * @version 1.0
 * @since 2016/5/25
 */
public class BeanXmlUtil {
	 /** 
     * 类转xml方法. 
     * @param data List<?> 
     * @return String 
     * @throws 
     
    public  String map2xml(Map map) { 
        Document document = DocumentHelper.createDocument(); 
        Element nodesElement = document.addElement("ROOT"); 
        map2xml(map,nodesElement); 
        return doc2String(document); 
    } */ 

     /** 
     * 类转xml方法. 
     * @param data List<?> 
     * @return String 
     * @throws 
     */ 
    public static String list2xml(List<?> list) { 
        Document document = DocumentHelper.createDocument(); 
        Element nodesElement = document.addElement("DATA"); 
        list2xml(list,nodesElement); 
        return doc2String(document); 
    } 

    /** 
     * xml转为String. 
     * @param document 
     * @return 
     * @throws 
     */ 
    public static String doc2String(Document document) {  
        String s = "";  
        try {  
            // 使用输出流来进行转化  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
            // 使用UTF-8编码  
            OutputFormat format = new OutputFormat("   ", true, "UTF-8");  
            XMLWriter writer = new XMLWriter(out, format);  
            writer.write(document);  
            s = out.toString("UTF-8");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return s;  
    } 

   /** 
     * List2XML,目前支持List<List> List<Map> List<Map<String,List>>等只有 
     * List Map 组合的数据进行转换. 
     * @param list 
     * @param element 
     * @return 
     * @throws 
     */ 
	public static Element list2xml(List list,Element element){ 
        if(list!=null && list.size()>0){
        	int i = 0; 
        	for (Object o : list) { 
                Element nodeElement = element.addElement("LIST"); 
                if (o instanceof Map) { 
                    nodeElement.addAttribute("type", "o"); 
                    Map m = (Map) o; 
                    for (Iterator iterator = m.entrySet().iterator(); iterator.hasNext();) { 
                        Entry entry = (Entry) iterator.next(); 
                        Element keyElement = nodeElement.addElement(entry.getKey().toString()); 
                        if (entry.getValue() instanceof List) { 
                            keyElement.addAttribute("type", "l"); 
                            list2xml((List) entry.getValue(),keyElement); 
                        } else { 
                            keyElement.addAttribute("type", "s"); 
                            keyElement.setText(entry.getValue().toString()); 
                        } 
                    } 
                } else if (o instanceof List) { 
                    nodeElement.addAttribute("type", "l"); 
                    list2xml((List)o,nodeElement); 
                } 
                else { 
                    Element keyElement = nodeElement.addElement("value"); 
                    keyElement.addAttribute("num", String.valueOf(i)); 
                    keyElement.setText(String.valueOf(o)); 
                } 
                i++; 
            } 
        }
        return element; 
    } 

    /** 
     * xml转List方法. 
     * @param xml 
     * @return List<?> 
     * @throws 
     */ 
	public static List<?> xml2List(String xml){ 
        try {
        	List list = new ArrayList();  
        	if(StringUtils.isNotBlank(xml)){
                Document document = DocumentHelper.parseText(xml);  
                Element nodesElement = document.getRootElement();  
                List nodes = nodesElement.elements();  
                for (Iterator its = nodes.iterator(); its.hasNext();) {  
                    Element nodeElement = (Element) its.next();  
                    if(("l").equals(nodeElement.attributeValue("type"))){ 
                        List s = xml2List(nodeElement.asXML()); 
                        list.add(s);  
                        s = null;  
                    }else if(("o").equals(nodeElement.attributeValue("type"))){ 
                        Map map = xml2Map(nodeElement.asXML()); 
                        list.add(map);  
                        map = null;  
                    }else{ 
                        list.add(nodeElement.getText()); 
                    } 
                }  
                nodes = null;  
                nodesElement = null;  
                document = null; 
        	}
            return list;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 

     /** 
     * xml 2 map 
     * @param xml 
     * @return 
     */  
	public static Map xml2Map(String xml) {  
        try {  
            Map map = new HashMap();  
            Document document = DocumentHelper.parseText(xml);  
            Element nodeElement = document.getRootElement();  
            List node = nodeElement.elements();  
            for (Iterator it = node.iterator(); it.hasNext();) { 
                Element elm = (Element) it.next(); 
                if("l".equals(elm.attributeValue("type"))){ 
                    map.put(elm.getName(), xml2List(elm.asXML()));  
                }else if("o".equals(elm.attributeValue("type"))){ 
                    map.put(elm.getName(), xml2Map(elm.asXML()));  
                }else{ 
                    map.put(elm.getName(), elm.getText());  
                } 
                elm = null;  
            }  
            node = null;  
            nodeElement = null;  
            document = null;  
            return map;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
	
	/**
	 * 写入文件
	 * @param text
	 * @param path
	 * @param charset
	 */
	public static void StringTofile(String text, String path, String charset) {
		BufferedOutputStream bos = null;
		//BufferedWriter out = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(path));
			bos.write(text.getBytes(charset));
			bos.flush();
			/*out = new BufferedWriter(new FileWriter(path));
	        out.write(text);
	        out.flush();*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(bos!=null){
					bos.close();
				}
				/*if(out!=null){
					out.close();
				}*/
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 读取文件返回字符串
	 * @param path
	 * @param charset
	 * @return
	 */
	public static String fileToString(String path, String charset) {
		InputStreamReader read = null;
		StringBuffer str = new StringBuffer();
		try {
			read = new InputStreamReader(new FileInputStream(path), charset);
			BufferedReader in = new BufferedReader(read);
			String line = null;
			while ((line = in.readLine()) != null) {
				str.append(line+"\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(read!=null){
					read.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str.toString();
	}
	
	public static void main(String[] args) {
		/*List<Map<String, String>> responseResult = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String,String>();
		map.put("bv", "123");
		map.put("av", "456");
		responseResult.add(map);
		map = new HashMap<String,String>();
		map.put("yu", "567");
		map.put("jy", "442");
		responseResult.add(map);
		//String str = list2xml(responseResult);
		//System.out.println(str);
		BeanXmlUtil.StringTofile(BeanXmlUtil.list2xml(responseResult), "E:\\data\\"+ServiceConstants.CLIENT_CAHCHE_FILE_NAME, ServiceConstants.UTFCHARSET);
		List<Map<String,String>> cacheResult = (List<Map<String, String>>) BeanXmlUtil.xml2List(BeanXmlUtil.fileToString("E:\\data\\"+ServiceConstants.CLIENT_CAHCHE_FILE_NAME, ServiceConstants.UTFCHARSET));
		*/
		List<Map<String,String>> cacheResult = (List<Map<String, String>>)BeanXmlUtil.xml2List("<r><t type=\"o\"><a>1</a><b>2</b><c>3</c></t></r>");
		Map<String,String> str =  BeanXmlUtil.xml2Map("<r><a>1</a><b>2</b><c>3</c></r>");
		System.out.println(str);
	}
}
