package com.bwcx.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bwcx.dao.UpdateDao;
import com.bwcx.pojo.DocumentEntity;


@Service
public class UpdateContentTask {

	@Autowired
	private UpdateDao documentDao;
	
	
	
	@Scheduled(cron="0 40 9,11,14,16,20 * * MON-FRI ")
	public void updateContents() {
		
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