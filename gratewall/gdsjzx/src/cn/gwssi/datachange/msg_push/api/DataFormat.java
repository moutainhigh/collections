package cn.gwssi.datachange.msg_push.api;

import java.io.PrintWriter;
import java.util.Map;

/**
 * 数据格式化接口
 * @author wuyincheng ,Sep 12, 2016
 */
public interface DataFormat {
	
	public void formatOut(@SuppressWarnings("rawtypes") Map data, PrintWriter pw);
}
