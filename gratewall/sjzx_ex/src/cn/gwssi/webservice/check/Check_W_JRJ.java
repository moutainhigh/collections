package cn.gwssi.webservice.check;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.util.UnicodeReader;
import com.gwssi.webservice.server.GSGeneralWebService;

/**
 * ���ھִ�����
 * @author Administrator
 *
 */
public class Check_W_JRJ implements ICheckRule
{
	protected static Logger	logger	= TxnLogger
			.getLogger(Check_W_JRJ.class
					.getName());	// ��־

	@Override
	public boolean checkRule(Map map) throws IOException
	{
		logger.info("����������������֤��ʼ");
		//���ļ��л�ȡTOKENֵ
		String TOKEN="";
		String path=new Check_W_JRJ().getClass().getResource("/").getPath();
		logger.debug(path);
		File f = new File(path+"verify.properties");
		if (f.isFile() && f.exists()) {
			FileInputStream read = new FileInputStream(f);
			BufferedReader reader = new BufferedReader(new UnicodeReader(
					read, Charset.defaultCharset().name()));
			try {
				String line=reader.readLine();
				while(line!=null){
					String[] keyValue=line.split("=");
					if("W_JRJ".equals(keyValue[0])){
						TOKEN=keyValue[1];
						logger.debug("��ȡ��W_JRJ��TOKENֵ="+TOKEN);
						break;
					}
					line=reader.readLine();
				}
				if("".equals(TOKEN)){
					logger.info("Check_W_JRJ ��ȡTOKENʧ��");					
					return false;
				}
			} catch (Exception e) {
				logger.error("Check_W_JRJ ��ȡTOKENʧ��");					
				return false;
			}finally{
				read.close();
			}			
		}
		
		String nonce=(String)map.get("NONCE");//�����
		String timestamp =(String)map.get("TIMESTAMP");//ʱ���
		String signature=(String)map.get("SIGNATURE");//ǩ��
		if(StringUtils.isBlank(nonce) 
				||StringUtils.isBlank(timestamp)
				||StringUtils.isBlank(signature)){
			logger.error("Check_W_JRJ ȱ�ٱ�Ҫ��У����Ϣ");
			return false;
		}
		String[] params = new String[] { TOKEN, String.valueOf(timestamp),String.valueOf(nonce) };
		Arrays.sort(params);
		String input =StringUtils.join(params, "");
		logger.debug("sha1У�������ַ���Ϊ="+input);
		String expect = DigestUtils.sha1Hex(input);
		logger.debug("���ɵ�ǩ��="+expect);
		if (!expect.equals(signature)) {
			//ǩ������
			logger.error("Check_W_JRJǩ������");
			return false;
		}else{
			//У��ͨ��
			logger.info("Check_W_JRJУ��ͨ��");
			return true;
		}
		
	}
	

}
