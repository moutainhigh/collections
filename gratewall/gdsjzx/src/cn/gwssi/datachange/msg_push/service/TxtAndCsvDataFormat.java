package cn.gwssi.datachange.msg_push.service;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import cn.gwssi.datachange.msg_push.api.DataFormat;
/**
 * 
 * @author wuyincheng ,Sep 12, 2016
 */
public class TxtAndCsvDataFormat implements DataFormat {
	
	private boolean hasWriteHeader = false;
	
	private long index = 0;
	

	@Override
	public void formatOut(Map map, PrintWriter pw) {
		if(map != null && map.size() > 0) {
			if(!hasWriteHeader){
				pw.write("No.");
				pw.write(',');
				for(Object key : map.keySet()) {
					pw.write(key.toString());
					pw.write(',');
				}
				pw.println();
				hasWriteHeader = true;
			}
			pw.write("" + index ++);
			pw.write(',');
			for(Object key : map.keySet()) {
				pw.write(map.get(key) == null ? "" : map.get(key).toString());
				pw.write(',');
			}
			pw.println();
		}
	}

}
