package cn.gwssi;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import cn.gwssi.resource.CodeToValue;

@Controller
@RequestMapping("/tool")
public class ToolController {
	private static  Logger log=Logger.getLogger(ToolController.class);
	@Autowired
	private ToolService toolService;
	
	@RequestMapping("/codeToValue")
	@ResponseBody
	public Map querydata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String flag = req.getParameter("flag");
		String code = req.getParameter("code");
		String tableName = req.getParameter("tableName");
		if(StringUtils.isBlank(tableName)){
			return CodeToValue.codeToValue(code, flag, toolService);
		}else{
			return CodeToValue.codeToValue(code, flag,tableName, toolService);
		}
	} 
	
	@RequestMapping("/codeToValues")
	@ResponseBody
	public List<Map> codeToValue(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String flag = req.getParameter("flag");
		String code = req.getParameter("code");
		String tableName = req.getParameter("tableName");
		if(StringUtils.isBlank(tableName)){//为空
			return CodeToValue.codeToValues(code, flag, toolService);
		}else{
			return CodeToValue.codeToValues(code, flag,tableName, toolService);
		}
	}
	
}
