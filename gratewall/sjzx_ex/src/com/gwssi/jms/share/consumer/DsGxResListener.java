package com.gwssi.jms.share.consumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.webservice.client.ClientDAO;
import com.gwssi.webservice.client.ClientDAOImpl;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.client.WsClient;
import com.gwssi.webservice.client.collect.DataHandleFactory;
import com.gwssi.webservice.client.collect.DataHandleInterface;

public class DsGxResListener implements MessageListener {
	protected static Logger	logger		= TxnLogger.getLogger(WsClient.class.getName());	// 日志
	
	
	
	
	public void onMessage(Message message)
	{

		TextMessage textMessage = (TextMessage) message;
		
		try {
			System.out.println(textMessage.getText());

			String dom = textMessage.getText();
			AnalyCollectFile acf = new AnalyCollectFile();
			String result=dom.replace("<row>", "\n<row>");
			result=result.replace("</row>", "\n</row>");
			acf.appendContext("E://share.txt", result);
			System.out.println("共享查询数据为============="+result);
			System.out.println("共享查询数据成功!!!!!");

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
