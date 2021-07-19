package com.gwssi.report.ebusiness;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.report.service.EBusinessService;

@Controller
@RequestMapping("bus")
public class EBussinessController {
	private static Logger log = Logger.getLogger(EBussinessController.class);
	
	@Autowired
	private EBusinessService ebusinessService;
	
	
	public int compareDate(Date dt1,Date dt2){
        if (dt1.getTime() > dt2.getTime()) {
            System.out.println("dt1 在dt2前");
            return 1;
        } else if (dt1.getTime() < dt2.getTime()) {
            System.out.println("dt1在dt2后");
            return -1;
        } else {//相等
            return 0;
        }
    }
	
	
	@RequestMapping("/ebus")
	public void getQueryByDate(OptimusRequest req, OptimusResponse resp)	throws OptimusException, Exception {
		String data1 = req.getParameter("data1");
		String data2 = req.getParameter("data2");
		String data3 = req.getParameter("data3");
		String data4 = req.getParameter("data4");
		if(StringUtils.isEmpty(data1)){
			data1 = (String) req.getAttr("data1");
		}
		if(StringUtils.isEmpty(data2)){
			data2 = (String) req.getAttr("data2");
		}
		if(StringUtils.isEmpty(data3)){
			data3 = (String) req.getAttr("data3");
		}
		if(StringUtils.isEmpty(data4)){
			data4 = (String) req.getAttr("data4");
		}
		
		
		
		
		List list = ebusinessService.getList(data1, data2);
		if(list!=null&&list.size()>0) {
			resp.addAttr("msg", list);
		}else {
			resp.addAttr("msg", "0");
		}
	}
}
