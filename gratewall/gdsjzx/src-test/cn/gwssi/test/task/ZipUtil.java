package cn.gwssi.test.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gwssi.common.model.RequestContext;

public class ZipUtil {
	
	
	public void createXML() {
		// 用工厂类创建一个document实例
		Document doc = DocumentHelper.createDocument();
		// 创建根元素emps
		Element rootEle = doc.addElement("emps");
		// 添加注释
		rootEle.addComment("这是一个dom4j生成的xml文件");
		// emps根节点下创建一个emp节点
		Element empEle = rootEle.addElement("emp");
		// emp添加属性id="1"
		empEle.addAttribute("id", "1");
		// emp节点下创建一个name节点
		Element nameEle = empEle.addElement("name");
		// name节点下创建一个文本节点zhangsan
		nameEle.setText("zhangsan");
		// 再为name节点创建一个兄弟节点
		Element sexEle = empEle.addElement("sex");
		sexEle.setText("man");
		// 将document中的内容写入文件中
		try {
			Writer out = new FileWriter("F:\\emps.xml");
			// 格式化输出,类型IE浏览一样
			OutputFormat format = OutputFormat.createPrettyPrint();
			// OutputFormat format = OutputFormat.createCompactFormat();
			format.setEncoding("UTF-8");
			// 创建写出对象
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			writer.close();
			System.out.println("生成emps.xml成功。");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("失败了。");
		}
	}
	
	public static String compress(String str) throws IOException {   
	    if (str == null || str.length() == 0) {   
	     return str;   
	   }  
	    
	    ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(str);
	    
	   ByteArrayOutputStream out = new ByteArrayOutputStream();   
	   GZIPOutputStream gzip = new GZIPOutputStream(out);
	   gzip.write(bo.toByteArray());   //str.getBytes()
	   gzip.close();
	   bo.close();
	   oo.close();
	   return out.toString("ISO-8859-1");   
	  }
	
	// 解压缩   
	 public static String uncompress(String str) throws IOException {   
	    if (str == null || str.length() == 0) {   
	      return str;   
	  }   
	   ByteArrayOutputStream out = new ByteArrayOutputStream();   
	   ByteArrayInputStream in = new ByteArrayInputStream(str   
	        .getBytes("ISO-8859-1"));   
	    GZIPInputStream gunzip = new GZIPInputStream(in);   
	    byte[] buffer = new byte[256];   
	    int n;   
	   while ((n = gunzip.read(buffer))>= 0) {   
	    out.write(buffer, 0, n);   
	    }   
	    // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)   
	    return out.toString();   
	  }   
	  
	  // 测试方法   
	  public static void main(String[] args) throws IOException {   
		  RequestContext t = new RequestContext();
	      //测试字符串   
	     String str="%5B%7B%22lastUpdateTime%22%3A%222011-10-28+9%3A39%3A41%22%2C%22smsList%22%3A%5B%7B%22liveState%22%3A%221";   
	         
	      System.out.println("原长度："+str.length());     
	         
	      System.out.println("压缩后："+ZipUtil.compress(str).length());     
	         
	    System.out.println("解压缩："+ZipUtil.uncompress(ZipUtil.compress(str)));   
	  }   
}
