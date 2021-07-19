package com.gwssi.rodimus.sms.service.unicom.support;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.optimus.core.exception.OptimusException;

/**
 * 短信工具类
 * @author LXB
 *
 */	
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SMSUtils {
	
	private static final Logger logger = Logger.getLogger(SMSUtils.class);
	private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	
	private final static String SMS_URL = null;
	private static final String SMS_VALIDATION = null;
	private static final String SMS_BID = null;

	@Autowired
	private SMSService smsService;
    
    static {
//    	Properties props = ConfigManager.getProperties("ems");
//        SMS_URL = props.getProperty("sms.url");
//        SMS_VALIDATION = props.getProperty("sms.validation");
//        SMS_BID = props.getProperty("sms.bid");
    }
	private SMSUtils(){
		
	}
	
	private static SMSUtils utils = new SMSUtils();
	
	public static SMSUtils getInstance() {
		return utils;
	}
	
	/**
	 * 发送短信
	 * @param msgXML
	 * @return
	 * @throws OptimusException
	 */
	public String sendSMS(String msgXML)throws OptimusException{

        SMSClient client = new SMSClient(SMS_URL);
        SMSPortType service = client.getSMSHttpPort();
                
        String sms = null;
        try {
        	sms = service.addSMSList(SMS_VALIDATION, msgXML);
            if("success".equals(sms)){
            	//发送成功
            }
		} catch (Exception e) {
			throw new OptimusException("发送短信失败"+e.getMessage());
		}
        String report=null;
        try {
        	report = service.getReportSMSList(SMS_VALIDATION, SMS_BID);
		} catch (Exception e) {
			throw new OptimusException("获取短信回执失败："+e.getMessage());
		}
        
        return report;
	}
	
	/**
	 * 生成发送XML
	 * @param contentList
	 * @return
	 */
	public String getSendXML( List<Map> contentList,String reqState) {
		if(contentList==null || contentList.size()==0){
			logger.debug("获取到的短信内容为空");
			return null;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String date = sf.format(new Date());
		StringBuilder strXML = new StringBuilder();
	    strXML.append(XML_HEAD);
	    strXML.append("<SMS type=\"send\">");
	    int len = contentList.size();
	    for (int i =0;i<len;i++) {
	    	Map<String,String> tmp = contentList.get(i);
	    	String mobTel=tmp.get("mobTel");
	    	if(StringUtils.isBlank(mobTel)){
	    		logger.debug("没有移动电话码。");
	    		continue;
	    	}
	    	String smsId=getSmsId(i);
	    	tmp.put("smsId", smsId);
	    	tmp.put("sendTime", date);
			strXML.append("<Message SmsID=\"" )
				.append(smsId)//按规则生成
				.append("\" Bid=\"" )
				.append(SMS_BID)
				.append("\" RecvNum=\"" )
				.append(mobTel)
				.append("\" Content=\"" )
				.append(tmp.get("content"))
				.append("\" />");
		}
		strXML.append("</SMS>");
		logger.debug("短信XML="+strXML.toString());
		return strXML.toString();
	}

	
	/**
	 * 按规则生成sms_id
	 * @param i
	 * @return
	 */
	private String getSmsId(int i){
		return SMS_BID+i;
	}
	
	
	
	/**
	 * 解析短信回执xml
	 * 将xml转换成Map，key为smsId，value为Message内容的Map对象
	 * @param xml
	 * @return
	 * @throws OptimusException
	 */
	public Map<String,Map<String,String >> reportToMap(String xml)throws OptimusException{
		Map<String,Map<String,String >> reportsMap = new HashMap<String,Map<String,String >>();
		try {
			
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputStream in = new ByteArrayInputStream (xml.getBytes("utf-8"));
			Document doc = docBuilder.parse(in);
			Node root = doc.getFirstChild();
			String strType = root.getAttributes().getNamedItem("type").getTextContent();
			
			if (!strType.equalsIgnoreCase("report")) {
				reportsMap = null;
			    return reportsMap;
			}	
			NodeList nodeList = root.getChildNodes();
			for(int i = 0;i<nodeList.getLength();i++){
				Node node = nodeList.item(i);
				if(node.getNodeName().equalsIgnoreCase("Message")){
					Map<String,String> tmp = new HashMap<String,String>();
					NamedNodeMap attrList = node.getAttributes();
					String strSmsID = attrList.getNamedItem("SmsID").getTextContent();
					String strSendNum = attrList.getNamedItem("SendNum").getTextContent();
					String bid = attrList.getNamedItem("Bid").getTextContent();
					String strReportTime = attrList.getNamedItem("ReportTime").getTextContent();
					String strStatus = attrList.getNamedItem("Status").getTextContent();
					//tmp.put("SmsID", strSmsID);
					tmp.put("SendNum", strSendNum);
					tmp.put("bid", bid);
					tmp.put("ReportTime", strReportTime);
					tmp.put("Status", strStatus);
					reportsMap.put(strSmsID, tmp);
				}
			}
		
			
		} catch (Exception e) {
			throw new OptimusException("解析回执出错："+e.getMessage());
		}
		return reportsMap;
	}
	
	/**
	 * 发送单条短信
	 * 用于预审通过
	 * @param reqBo
	 * @throws OptimusException
	 */
	public void sendSingleSMS(BeWkReqBO reqBo)throws OptimusException{
		Map reqMap = new HashMap();
		reqMap.put("requisitionId", reqBo.getRequisitionId());
		reqMap.put("entName", reqBo.getEntName());
		reqMap.put("linkman", reqBo.getLinkman());
		reqMap.put("mobTel", reqBo.getMobTel());
		reqMap.put("state", reqBo.getState());
		List list = new ArrayList();
		list.add(reqBo);
		
		//生成短信XML
		String xmlContent = getSendXML(list,reqBo.getState());
		//发送短信
		String reportXML  = sendSMS(xmlContent);
		//解析短信回执
		Map xmlMap = reportToMap(reportXML);
		//插入短信记录
		smsService.insertSMS(list,xmlMap);
	}
	
	
	public static void test(){
//		//获取短信操作实例
//		SMSUtils smsUtils = SMSUtils.getInstance();
//		//生成短信XML
//		String xmlContent = smsUtils.getSendXML(list,"2");
//		//发送短信
//		String reportXML  = smsUtils.sendSMS(xmlContent);
//		//解析短信回执
//		Map xmlMap = smsUtils.reportToMap(reportXML);
//		//插入短信记录
//		smsService.insertSMS(list,xmlMap);

	
	}
	
	public static void main(String[] args) {
		//发送信息
		SMSUtils smsUtils = SMSUtils.getInstance();
		String msgXML="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<SMS type=\"send\">" +
				"<Message SmsID=\"UE01234567890123450\" Bid=\"userTest1\" RecvNum=\"18600000000\" Content=\"短信测试1\"/>" +
				"<Message SmsID=\"UE02234567890123451\" Bid=\"userTest2\" RecvNum=\"18611111111\" Content=\"短信测试2\"/>" +
				"</SMS>";
		try {
			@SuppressWarnings("unused")
			String report = smsUtils.sendSMS(msgXML);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		
		
		
		
		//解析回执
		String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<SMS type=\"report\">" +
				"<Message SmsID=\"UE01234567890123450\" SendNum=\"18600000000\" Bid=\"userTest1\" Status=\"0\" ReportTime=\"2010-01-01 00:00:00\"/>" +
				"<Message SmsID=\"UE02234567890123451\" SendNum=\"18611111111\" Bid=\"userTest2\" Status=\"1\" ReportTime=\"2010-01-01 00:00:00\"/>" +
				"</SMS>";
		
		try {
			Map<String,Map<String,String >> map=smsUtils.reportToMap(xml);
			System.out.println(map);
		} catch (OptimusException e) {
			System.out.println(e.getMessage());
		}
	}

}
