package cn.gwssi.datachange.msg_push.service;

import java.io.PrintWriter;
import java.util.Map;

import cn.gwssi.datachange.msg_push.api.DataFormat;
/**
 * 
 * @author wuyincheng ,Sep 12, 2016
 */
public class XmlDataFormat implements DataFormat {
	
	//写成xml的Node节点名称
	private static final String NODE_START = "<DATA>";
	private static final String NODE_END = "</DATA>";

	@Override
	public void formatOut(Map map, PrintWriter pw) {
		if(map != null && map.size() > 0) {
			pw.println(NODE_START);
			for(Object key : map.keySet()) {
				pw.write(' ');
				pw.write('<');
				pw.write(key.toString());
				pw.write('>');
				pw.write(map.get(key) == null ? "" : map.get(key).toString());
				pw.write('<');
				pw.write('/');
				pw.write(key.toString());
				pw.println('>');
			}
			pw.println(NODE_END);
		}
	}

}
