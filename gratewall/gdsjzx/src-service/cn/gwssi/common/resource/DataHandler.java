package cn.gwssi.common.resource;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.SynReponseContext;

/**
 * 数据处理工具类
 * @author xue
 * @version 1.0
 * @since 2016/5/10
 */
public class DataHandler {
	
	/**
	 * Object转xml方法
	 * @param obj
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static String Object2xml(Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException { 
        Document document = DocumentHelper.createDocument(); 
        String rootname = obj.getClass().getSimpleName();//获得类名 
        Element nodesElement = document.addElement(rootname);
        if(obj!=null){
        	Object2xml(obj,nodesElement);
        }
        return doc2String(document); 
    } 
	
	private static void Object2xml(Object obj, Element nodesElement) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(obj!=null){
			for(Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				Field[] properties = clazz.getDeclaredFields();//获得实体类的所有属性 obj.getClass()
				for (int i = 0; i < properties.length; i++) {
					// System.out.println("get"+properties[i].getName().substring(0, 1).toUpperCase() +properties[i].getName().substring(1));
					// System.out.println(properties[i].getType());
					Method meth = clazz.getMethod("get"+ properties[i].getName().substring(0, 1).toUpperCase()+ properties[i].getName().substring(1));
					if (properties[i].getType().isInstance(new String())) {
						String str = meth.invoke(obj) == null ? "" : (String) meth.invoke(obj);
						nodesElement.addElement(properties[i].getName()).setText(str);
					} else if (properties[i].getType().isInstance(new ArrayList())) {
						list2xml((List) meth.invoke(obj), nodesElement,properties[i].getName());
					} else if (properties[i].getType().isInstance(new RequestContext())) {// 新增
						Element nodeElement = nodesElement.addElement("RequestContext");
						// nodeElement.addAttribute("type", "map");
						Object2xml((RequestContext) meth.invoke(obj), nodeElement);
					}else if (properties[i].getType().isInstance(new HashMap())) {// 新增
						map2xml((Map) meth.invoke(obj), nodesElement,properties[i].getName());
					} else {
						return;
					}
				}
			}
		}
	}
	
   /**
    * List2XML,目前支持 List<Map<String,String>>等只有 
    * List Map 组合的数据进行转换. 
    * @param list
    * @param element
    * @param fileName
    * @return
    */
	public static Element list2xml(List list,Element element,String fileName){
    	//Element element = nodesElement.addElement(fileName);
    	//element.addAttribute("type", "list"); 
    	if(list!=null && list.size()>0){
    		Element nodeElement = element.addElement(fileName);
            nodeElement.addAttribute("type", "list");
    		for (Object o : list) { 
    			if(o instanceof Map){
    				map2xml((Map) o,nodeElement,"map");
    			}else{
                    Element keyElement = nodeElement.addElement("result"); 
                    keyElement.addAttribute("type", "string"); 
                    keyElement.setText(o.toString()); 
    			}
                /*Map m = (Map) o; 
                for (Iterator iterator = m.entrySet().iterator(); iterator.hasNext();) { 
                    Entry entry = (Entry) iterator.next(); 
                    Element keyElement = nodeElement.addElement(entry.getKey().toString()); 
                    keyElement.addAttribute("type", "string"); 
                    keyElement.setText(entry.getValue().toString()); 
                }*/
            } 
    	}
        return element; 
    }
	
	/**
     * Map2XML,目前支持 Map<String,String>等只有 
     * Map 组合的数据进行转换. 
     * @param list
     * @param element
     * @param fileName
     * @return
    */
	public static Element map2xml(Map map,Element element,String fileName){
    	if(map!=null && map.size()>0){
            Element nodeElement = element.addElement(fileName);
            nodeElement.addAttribute("type", "map");
            for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) { 
                Entry entry = (Entry) iterator.next(); 
                Element keyElement = nodeElement.addElement(entry.getKey().toString()); 
                keyElement.addAttribute("type", "string"); 
                keyElement.setText(entry.getValue().toString()); 
            }
    	}
        return element; 
    }

    /**
     * xml转Object方法
     * @param xml
     * @param obj
     * @return
     * @throws DocumentException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
	public static Object xml2Object(String xml,Object obj) throws DocumentException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Document document = DocumentHelper.parseText(xml);  
        Element nodesElement = document.getRootElement(); 
        List nodes = nodesElement.elements(); 
        //System.out.println(nodesElement.getName()+"===");
        String strMap = "";
        String strList = "";
        for (Iterator its = nodes.iterator(); its.hasNext();) {  
            Element nodeElement = (Element) its.next(); 
            if(("list").equals(nodeElement.attributeValue("type"))){
            	List list = new ArrayList();
            	list = xml2List(nodeElement.asXML()); 
            	strList=nodeElement.getName();
            	if(strList!=null && !"".equals(strList)){
                	Method meth = obj.getClass().getMethod("set"+ strList.substring(0, 1).toUpperCase() + strList.substring(1),List.class);
                	meth.invoke(obj,list);
                }
            }else if(("map").equals(nodeElement.attributeValue("type"))){
            	Map map = new HashMap();
                map = xml2Map(nodeElement.asXML()); 
                strMap=nodeElement.getName();
                if(strMap!=null && !"".equals(strMap)){
                	Method meth = obj.getClass().getMethod("set"+ strMap.substring(0, 1).toUpperCase() + strMap.substring(1),Map.class);
                	meth.invoke(obj,map);
                }
            }else{
            	if("RequestContext".equals(nodeElement.getName())){
            		RequestContext requestContext = new RequestContext();
            		requestContext = (RequestContext) xml2Object(nodeElement.asXML(),requestContext); 
            		Method meth = obj.getClass().getMethod("set"+ nodeElement.getName().substring(0, 1).toUpperCase() + nodeElement.getName().substring(1),RequestContext.class);
                	meth.invoke(obj,requestContext);
            	}else{
            		Method meth = obj.getClass().getMethod("set"+ nodeElement.getName().substring(0, 1).toUpperCase() + nodeElement.getName().substring(1),String.class);
                	meth.invoke(obj,nodeElement.getText());
            	}
            }
        }  
        nodes = null;  
        nodesElement = null;  
        document = null; 
		return obj; 
    }
	
	/**
     * xml 2 list 
     * @param xml
     * @return
     * @throws DocumentException 
     */
	public static List xml2List(String xml) throws DocumentException { 
		List list = new ArrayList();
        Document document = DocumentHelper.parseText(xml);  
        Element nodeElement = document.getRootElement();  
        List node = nodeElement.elements();  
        for (Iterator it = node.iterator(); it.hasNext();) { 
        	Element elm = (Element) it.next();
			if("string".equals(elm.attributeValue("type"))){
				list.add(elm.getText());  
			}else{
				Map map = xml2Map(elm.asXML()); 
	            list.add(map);  
	            map = null;
			}
        }  
        node = null;  
        nodeElement = null;  
        document = null;  
        return list;  
    }
    
    /**
     * xml 2 map 
     * @param xml
     * @return
     * @throws DocumentException 
     */
	public static Map xml2Map(String xml) throws DocumentException {  
        Map map = new HashMap();  
        Document document = DocumentHelper.parseText(xml);  
        Element nodeElement = document.getRootElement();  
        List node = nodeElement.elements();  
        for (Iterator it = node.iterator(); it.hasNext();) { 
            Element elm = (Element) it.next(); 
            if("string".equals(elm.attributeValue("type"))){ 
            	map.put(elm.getName(), elm.getText()); 
            }else{ 
                map.put(elm.getName(), elm.getText());  
            } 
            elm = null;  
        }  
        node = null;  
        nodeElement = null;  
        document = null;  
        return map;  
    }

    /**
     * xml转为String 
     * @param document
     * @return
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
    
    public static void main(String[] args) throws DocumentException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	/*SynReponseContext  t = new SynReponseContext();
    	t.setCode("ss");
    	t.setDesc("");
    	t.setErrorMsg("");
    	List s = new ArrayList();
    	s.add("<col><serviceobjectname>GD-数据中心-01</serviceobjectname><servicename>广州市工商行政管理局</servicename><themename>zhuti1</themename><tablecode>null</tablecode><columncode>12345</columncode><servicecontentcondition>123123</servicecontentcondition><servicecontent>ceshi</servicecontent><servicecontentname>测试</servicecontentname></col>, <col><serviceobjectname>GD-数据中心-01</serviceobjectname><servicename>横琴新区</servicename><themename>zhuti1</themename><tablecode>null</tablecode><columncode>12345</columncode><servicecontentcondition>123123</servicecontentcondition><servicecontent>ceshi</servicecontent><servicecontentname>测试</servicecontentname></col>, <col><serviceobjectname>GD-数据中心-01</serviceobjectname><servicename>广州市工商行政管理局</servicename><themename>zhuti2</themename><tablecode>null</tablecode><columncode>12345</columncode><servicecontentcondition>123123</servicecontentcondition><servicecontent>ceshi</servicecontent><servicecontentname>测试</servicecontentname></col>, <col><serviceobjectname>GD-数据中心-01</serviceobjectname><servicename>横琴新区</servicename><themename>zhuti2</themename><tablecode>null</tablecode><columncode>12345</columncode><servicecontentcondition>123123</servicecontentcondition><servicecontent>ceshi</servicecontent><servicecontentname>测试</servicecontentname></col>");
    	t.setResponseResult(s);
    	String trdd = DataHandler.Object2xml(t);
    	System.out.println(trdd);*/
    	String trtr="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+

    	"<SynReponseContext>"+
    	   "<responseResult type=\"list\">"+
    	      "<result type=\"string\">&lt;col&gt;&lt;serviceobjectname&gt;GD-数据中心-01&lt;/serviceobjectname&gt;&lt;servicename&gt;广州市工商行政管理局&lt;/servicename&gt;&lt;themename&gt;zhuti1&lt;/themename&gt;&lt;tablecode&gt;null&lt;/tablecode&gt;&lt;columncode&gt;12345&lt;/columncode&gt;&lt;servicecontentcondition&gt;123123&lt;/servicecontentcondition&gt;&lt;servicecontent&gt;ceshi&lt;/servicecontent&gt;&lt;servicecontentname&gt;测试&lt;/servicecontentname&gt;&lt;/col&gt;</result>"+
    	      "<result type=\"string\">&lt;col&gt;&lt;serviceobjectname&gt;GD-数据中心-01&lt;/serviceobjectname&gt;&lt;servicename&gt;横琴新区&lt;/servicename&gt;&lt;themename&gt;zhuti1&lt;/themename&gt;&lt;tablecode&gt;null&lt;/tablecode&gt;&lt;columncode&gt;12345&lt;/columncode&gt;&lt;servicecontentcondition&gt;123123&lt;/servicecontentcondition&gt;&lt;servicecontent&gt;ceshi&lt;/servicecontent&gt;&lt;servicecontentname&gt;测试&lt;/servicecontentname&gt;&lt;/col&gt;</result>"+
    	      "<result type=\"string\">&lt;col&gt;&lt;serviceobjectname&gt;GD-数据中心-01&lt;/serviceobjectname&gt;&lt;servicename&gt;广州市工商行政管理局&lt;/servicename&gt;&lt;themename&gt;zhuti2&lt;/themename&gt;&lt;tablecode&gt;null&lt;/tablecode&gt;&lt;columncode&gt;12345&lt;/columncode&gt;&lt;servicecontentcondition&gt;123123&lt;/servicecontentcondition&gt;&lt;servicecontent&gt;ceshi&lt;/servicecontent&gt;&lt;servicecontentname&gt;测试&lt;/servicecontentname&gt;&lt;/col&gt;</result>"+
    	      "<result type=\"string\">&lt;col&gt;&lt;serviceobjectname&gt;GD-数据中心-01&lt;/serviceobjectname&gt;&lt;servicename&gt;横琴新区&lt;/servicename&gt;&lt;themename&gt;zhuti2&lt;/themename&gt;&lt;tablecode&gt;null&lt;/tablecode&gt;&lt;columncode&gt;12345&lt;/columncode&gt;&lt;servicecontentcondition&gt;123123&lt;/servicecontentcondition&gt;&lt;servicecontent&gt;ceshi&lt;/servicecontent&gt;&lt;servicecontentname&gt;测试&lt;/servicecontentname&gt;&lt;/col&gt;</result>"+
    	  " </responseResult>"+
    	  " <code>0</code>"+
    	  " <desc>获取数据成功！</desc>"+
    	   "<errorCode></errorCode>"+
    	  " <errorMsg></errorMsg>"+
    	"</SynReponseContext>";
    	SynReponseContext synReponseContext = new SynReponseContext();
    	synReponseContext = (SynReponseContext) DataHandler.xml2Object(trtr,synReponseContext);
    	System.out.println(synReponseContext.getCode());
    	System.out.println(synReponseContext.getDesc());
    	System.out.println(synReponseContext.getErrorCode());
    	System.out.println(synReponseContext.getErrorMsg());
    	List list = synReponseContext.getResponseResult();
    	System.out.println(list.size());
    	for(int j=0;j<list.size();j++){
    		String o = (String)list.get(j);
    		System.out.println(o);
    		/*if(o instanceof String){
    			System.out.println("err");
    		}
    		if(o instanceof Map){
    			for(){
    				
    			}
    		}*/
    	}
    	//SynReponseContext extends ReponseContextBase
    	/*Object object = new SynReponseContext() ;  
    	String rootname = object.getClass().getSimpleName();//获得类名 
    	//Field[] properties = obj.getClass().getDeclaredFields();//获得实体类的所有属性 
    	for(Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
    		rootname = clazz.getSimpleName();//获得类名 
    		System.out.println(rootname);
    		Field[] properties =  clazz.getDeclaredFields();
    		for (int i = 0; i < properties.length; i++) {
				System.out.println("get"+properties[i].getName().substring(0, 1).toUpperCase() +properties[i].getName().substring(1));
				//System.out.println(properties[i].getType());
    		}
    	}*/
	}

}