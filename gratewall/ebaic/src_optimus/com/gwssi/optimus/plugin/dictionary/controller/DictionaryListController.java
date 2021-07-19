package com.gwssi.optimus.plugin.dictionary.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.cache.CacheBlock;
import com.gwssi.optimus.core.cache.CacheManager;
import com.gwssi.optimus.core.cache.dictionary.DicDBManager;
import com.gwssi.optimus.core.cache.dictionary.DictionaryController;
import com.gwssi.optimus.core.cache.dictionary.DictionaryManager;
import com.gwssi.optimus.core.cache.dictionary.model.TPtDmsjbBO;
import com.gwssi.optimus.core.cache.dictionary.model.TPtDmsybBO;
import com.gwssi.optimus.core.common.Constants;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.plugin.dictionary.service.DictionaryService;

@Controller
@RequestMapping("/dictionaryList")
public class DictionaryListController extends BaseController {
	
	@Autowired
    private DictionaryService dictionaryService;
	
	protected final static Logger logger = LoggerFactory
			.getLogger(DictionaryController.class);

//	@RequestMapping("test")
//	public void queryDictionaryData(OptimusRequest req, OptimusResponse resp) {
//		System.out.println("page");
//		resp.addPage("/page/test123.html");
//	}

	@RequestMapping("query")
	public void queryDictionaryDef(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		TPtDmsybBO tPtDmsybBO = req.getForm("formpanel", TPtDmsybBO.class);
		List<TPtDmsybBO> dataList = new ArrayList<TPtDmsybBO>();
		if(StringUtils.isEmpty(tPtDmsybBO.getDmbMc())){
			dataList = dictionaryService.getDefById(tPtDmsybBO.getDmbId());
		}else if (StringUtils.isEmpty(tPtDmsybBO.getDmbId())){
			dataList = dictionaryService.getDefByName(tPtDmsybBO.getDmbMc());
		}else{
			dataList = dictionaryService.getDefByIdAndName(tPtDmsybBO.getDmbId(),tPtDmsybBO.getDmbMc());
		}
		resp.addGrid("gridpanel", dataList);
	}
	
	@RequestMapping("save")
	public void saveDictionaryDef(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		TPtDmsybBO tb = req.getForm("formpanel_edit",TPtDmsybBO.class);
		String update = req.getParameter("update");
		String back = "";
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){
			if(tb.getSjccLx().equals("3")){
				String sql = tb.getSql();
				if(StringUtils.isNotBlank(sql)){
					sql = sql.replaceAll("#", "'");
					try {
						sql = URLDecoder.decode(sql, "UTF-8");
						tb.setSql(sql);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if(dictionaryService.testSql(sql)){
						
					}else{
						back = "fail";
						resp.addAttr("back", back);
					}
				}else{
					back = "empty";
					resp.addAttr("back", back);
				}
			}
			if(!back.equals("fail")&&!back.equals("empty")){
				if(StringUtils.isEmpty(tb.getSql())){
					dictionaryService.updateDef(tb);
					resp.addAttr("back", "success");
				}else{
					back = "sql";
					resp.addAttr("back", back);
				}
			}
		}else{
			if(dictionaryService.getDefById(tb.getDmbId()).isEmpty()){
				if(tb.getSjccLx().equals("3")){
					String sql = tb.getSql();
					if(StringUtils.isNotBlank(sql)){
						sql = sql.replaceAll("#", "'");
						try {
							sql = URLDecoder.decode(sql, "UTF-8");
							tb.setSql(sql);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						if(dictionaryService.testSql(sql)){
							
						}else{
							back = "fail";
							resp.addAttr("back", back);
						}
					}else{
						back = "empty";
						resp.addAttr("back", back);
					}
				}
				if(!back.equals("fail")&&!back.equals("empty")){
					dictionaryService.saveDef(tb);
					resp.addAttr("back", "success");
				}
			}else{
				back = "sameId";
				resp.addAttr("back", back);
			}
		}
	}
	
	@RequestMapping("publish")
	public void publishDictionaryDef(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		List<Map<String, String>> list = req.getGrid("gridpanel");
		List<TPtDmsybBO> listBO = new ArrayList<TPtDmsybBO>();
		String back = "";
		if(Constants.DICTIONARY_USE_CACHE){
			if(list == null){
				DictionaryManager.loadCache(null);
			}else{
				for(Map<String, String> map : list){
					String dmbId = (String) map.get("dmbId");
					TPtDmsybBO tPtDmsybBO = DicDBManager.getDmsybBO(dmbId);
					listBO.add(tPtDmsybBO);
				}
				DictionaryManager.loadCache(listBO);
			}
			back = "success";
		}else{
			back = "nocache";
		}
		resp.addAttr("back", back);
	}
	
	@RequestMapping("delete")
	public void deleteDictionaryDef(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		List<Map<String, String>> list = req.getGrid("gridpanel");
		for(Map<String, String> map : list){
			String dmbId = (String) map.get("dmbId");
			TPtDmsybBO tPtDmsybBO = new TPtDmsybBO();
			tPtDmsybBO.setDmbId(dmbId);
			dictionaryService.deleteDef(tPtDmsybBO);
			dictionaryService.deleteDataByDmbId(dmbId);
			if(Constants.DICTIONARY_USE_CACHE){
			    CacheBlock cacheBlock = CacheManager.getBlock(Constants.CACHE_DICTIONARY_INDEX);
			    cacheBlock.remove("DEF:"+dmbId);
			    cacheBlock.remove(dmbId);
			}
		}
		resp.addAttr("back", "success");
	}
	
	@RequestMapping("editinit")
	public void editInitDictionaryDef(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String dmbId = req.getParameter("dmbId");
		TPtDmsybBO tPtDmsybBO = new TPtDmsybBO();
		if(StringUtils.isNotEmpty(dmbId)){
			List<TPtDmsybBO> dataList = dictionaryService.getDefById(dmbId);
			tPtDmsybBO = dataList.get(0);
		}
		resp.addForm("formpanel_edit", tPtDmsybBO);
	}
	
	@RequestMapping("details")
	public void detailsDictionaryData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
//		String dmbId = req.getParameter("dmbId");
//		List<TPtDmsybBO> dataList = new ArrayList<TPtDmsybBO>();
//		TPtDmsybBO tPtDmsybBO = new TPtDmsybBO();
//		if(StringUtils.isNotEmpty(dmbId)){
//			dataList = userService.getDefById(dmbId);
//			tPtDmsybBO = dataList.get(0);
//		}
//		resp.addAttr("dmbId", dmbId);
		resp.addPage("/page/details.html");
	}
	
	@RequestMapping("queryfordetails")
	public void queryDictionaryDetails(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String dmbId = req.getParameter("dmbId");
		List<TPtDmsjbBO> dataList = new ArrayList<TPtDmsjbBO>();
		if(StringUtils.isNotEmpty(dmbId)){
			dataList = dictionaryService.getDataById(dmbId,null);
		}
		resp.addGrid("gridpanel", dataList);
	}
	
	@RequestMapping("detailseditinit")
	public void detailsInitDictionaryData(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		String sjId = req.getParameter("sjId");
		TPtDmsjbBO tPtDmsjbBO = new TPtDmsjbBO();
		if(StringUtils.isNotEmpty(sjId)){
			List<TPtDmsjbBO> dataList = dictionaryService.getDataBySjId(sjId);
			tPtDmsjbBO = dataList.get(0);
		}
		resp.addForm("formpanel_edit", tPtDmsjbBO);
	}
	
//	@RequestMapping("detailsedit")
//	public void editDictionaryData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
//		String sjId = req.getParameter("sjId");
//		String dmbLx = req.getParameter("dmbLx");
//		resp.addAttr("sjId", sjId);
//		resp.addPage("/page/detailsedit.html");
//	}
	
	@RequestMapping("queryData")
	public void queryDictionaryData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		TPtDmsjbBO tPtDmsjbBO = req.getForm("formpanel", TPtDmsjbBO.class);
		String dmbId = tPtDmsjbBO.getDmbId();
		String dm = tPtDmsjbBO.getDm();
		String wb = tPtDmsjbBO.getWb();
		List<TPtDmsjbBO> dataList = new ArrayList<TPtDmsjbBO>();
		if(StringUtils.isNotEmpty(dmbId)){
			if(StringUtils.isEmpty(dm)){
				dataList = dictionaryService.getDataByWb(wb,dmbId);
			}else if (StringUtils.isEmpty(wb)){
				dataList = dictionaryService.getDataByDm(dm,dmbId);
			}else{
				dataList = dictionaryService.getDataByWbAndDm(wb,dm,dmbId);
			}
		}
		resp.addGrid("gridpanel", dataList);
	}
	
	@RequestMapping("deletedetails")
	public void deleteDictionaryData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		List<Map<String, String>> list = req.getGrid("gridpanel");
		for(Map<String,String> map : list){
			String dmbId = (String) map.get("dmbId");
			String sjId = (String) map.get("sjId");
			TPtDmsjbBO tPtDmsjbBO = new TPtDmsjbBO();
			tPtDmsjbBO.setDmbId(dmbId);
			tPtDmsjbBO.setSjId(sjId);
			dictionaryService.deleteData(tPtDmsjbBO);
		}
		resp.addAttr("back", "success");
	}
	
	@RequestMapping("detailssave")
	public void saveDictionaryData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		TPtDmsjbBO tb = req.getForm("formpanel_edit",TPtDmsjbBO.class);
		String update = req.getParameter("update");
		List<TPtDmsjbBO> list = dictionaryService.getDataById(tb.getDmbId(),tb.getSjId());
		boolean addFlag = true;
		String back = "";
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){
			for(TPtDmsjbBO tjb : list){
				if(tjb.getDm().equals(tb.getDm())){
					addFlag = false;
					back = "dmfail";
					break;
				}
				if(tjb.getWb().equals(tb.getWb())){
					addFlag = false;
					back = "wbfail";
					break;
				}
				if(tjb.getXh().compareTo(tb.getXh())==0){
					addFlag = false;
					back = "xhfail";
					break;
				}
			}
			if(addFlag){
				dictionaryService.updateData(tb);
				resp.addAttr("back", "success");
			}else{
				resp.addAttr("back", back);
			}
		}else{
			for(TPtDmsjbBO tjb : list){
				if(tjb.getSjId().equals(tb.getSjId())){
					addFlag = false;
					back = "idfail";
					break;
				}
				if(tjb.getDm().equals(tb.getDm())){
					addFlag = false;
					back = "dmfail";
					break;
				}
				if(tjb.getWb().equals(tb.getWb())){
					addFlag = false;
					back = "wbfail";
					break;
				}
				if(tjb.getXh().compareTo(tb.getXh())==0){
					addFlag = false;
					back = "xhfail";
					break;
				}
			}
			if(addFlag){
				dictionaryService.saveData(tb);
				resp.addAttr("back", "success");
			}else{
				resp.addAttr("back", back);
			}
		}
	}
	
}
