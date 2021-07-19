import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bwcx.dao.DocumentDao;
import com.bwcx.pojo.DocumentEntity;

public class Test02 {
	/*public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
		 DocumentDao documentDao = ac.getBean(DocumentDao.class);
		 Map map = new HashMap();
		 map.put("cid", "1");
		 map.put("pageBegin", 0);
		 map.put("pageEnd", 100);
		 List<DocumentEntity> list = documentDao.getDocumentsByChannel(map);
		 
		 if(list!=null&&list.size()>0) {
			 for (int j = 0; j < list.size(); j++) {
				 DocumentEntity doc = list.get(j) ;
				 
				 if(doc!=null) {
						//String tempStr = doc.getContents();
						String tempStr = doc.getContentsPlainText();
						//System.out.println(tempStr);
						StringBuffer sb = new StringBuffer();
						StringBuffer sbEd = new StringBuffer();
						StringBuffer sbContents = new StringBuffer();
						String [] temp = tempStr.split("　　");
						for (String string : temp) {
							if(string.contains("？")) {
								string = string.replace("？", " ");
							}
							sb.append("<p>"+string+"</p>");
							sbEd.append("<p>"+string+"</p>");
							sbEd.append("<p>"+string+"</p>");
						}
						doc.setTranformeContents(sb.toString());
						doc.setContentToEd(sbEd.toString());
						
						documentDao.save(doc);
					}
			}
		 }
	}*/
	
	
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
		 DocumentDao documentDao = ac.getBean(DocumentDao.class);
		 List<DocumentEntity> list = documentDao.getPublishFromWcm();
		 System.out.println("===========> " + list.size());
		 if(list!=null&&list.size()>0) {
			 for (int j = 0; j < list.size(); j++) {
				 DocumentEntity doc = list.get(j) ;
				 
				 if(doc!=null) {
						//String tempStr = doc.getContents();
						String tempStr = doc.getContentsPlainText();
						//System.out.println(tempStr);
						StringBuffer sb = new StringBuffer();
						StringBuffer sbEd = new StringBuffer();
						StringBuffer sbContents = new StringBuffer();
						String [] temp = tempStr.split("　　");
						
						for (int i = 0; i < temp.length-1; i++) {
							if(temp[i].contains("？")) {
								temp[i] = temp[i].replace("？", " ");
							}
							sb.append("<p>"+temp[i]+"</p>");
							sbEd.append("<p>"+temp[i]+"</p>");
							sbEd.append("<p>"+temp[i]+"</p>");
							
						}
						doc.setTranformeContents(sb.toString());
						doc.setContentToEd(sbEd.toString());
						
						documentDao.save(doc);
					}
			}
		 }
	}
}
