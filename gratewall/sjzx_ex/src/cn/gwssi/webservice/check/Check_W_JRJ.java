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
 * 金融局处理方法
 * @author Administrator
 *
 */
public class Check_W_JRJ implements ICheckRule
{
	protected static Logger	logger	= TxnLogger
			.getLogger(Check_W_JRJ.class
					.getName());	// 日志

	@Override
	public boolean checkRule(Map map) throws IOException
	{
		logger.info("共享服务特殊加密验证开始");
		//从文件中获取TOKEN值
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
						logger.debug("获取到W_JRJ的TOKEN值="+TOKEN);
						break;
					}
					line=reader.readLine();
				}
				if("".equals(TOKEN)){
					logger.info("Check_W_JRJ 获取TOKEN失败");					
					return false;
				}
			} catch (Exception e) {
				logger.error("Check_W_JRJ 获取TOKEN失败");					
				return false;
			}finally{
				read.close();
			}			
		}
		
		String nonce=(String)map.get("NONCE");//随机数
		String timestamp =(String)map.get("TIMESTAMP");//时间戳
		String signature=(String)map.get("SIGNATURE");//签名
		if(StringUtils.isBlank(nonce) 
				||StringUtils.isBlank(timestamp)
				||StringUtils.isBlank(signature)){
			logger.error("Check_W_JRJ 缺少必要的校验信息");
			return false;
		}
		String[] params = new String[] { TOKEN, String.valueOf(timestamp),String.valueOf(nonce) };
		Arrays.sort(params);
		String input =StringUtils.join(params, "");
		logger.debug("sha1校验输入字符串为="+input);
		String expect = DigestUtils.sha1Hex(input);
		logger.debug("生成的签名="+expect);
		if (!expect.equals(signature)) {
			//签名不符
			logger.error("Check_W_JRJ签名不符");
			return false;
		}else{
			//校验通过
			logger.info("Check_W_JRJ校验通过");
			return true;
		}
		
	}
	

}
