package cn.gwssi.test;

import cn.gwssi.timertask.QuartzManager;
import cn.gwssi.timertask.log.ReadLogFIle2DB;

/**
 * cn.gwssi.test
 * TestTimerTask.java
 * 下午6:02:57
 * @author wuminghua
 */
public class TestTimerTask {

	    /**
	     * @param args
	     */
	    public static void main(String[] args) {
	        // TODO Auto-generated method stub
	        ReadLogFIle2DB job = new ReadLogFIle2DB();
	        String job_name ="11";
	        try {
	            QuartzManager.addJob(job_name,job,"0/5 * * * * ?");
	            
	            
	        }  catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
