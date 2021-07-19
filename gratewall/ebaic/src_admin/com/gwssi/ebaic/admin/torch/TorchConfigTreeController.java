package com.gwssi.ebaic.admin.torch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;







import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gwssi.ebaic.admin.httpMode.TreeConfigBoHttpModel;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.torch.db.result.PageBo;
/**
 * 
 * @author rgm
 *
 */
@Controller
@RequestMapping("/admin/torch")
public class TorchConfigTreeController extends BaseController{
	
	@Resource
	private TorchConfigTreeService torchConfigTreeService;
	
	/**
	 * 获取菜单树
	 * 
	 * @param id
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ResponseBody
	@RequestMapping("/tree")
    public 	List<TreeConfigBoHttpModel> tree(String id) {
    	List<TreeConfigBoHttpModel> treeListModel = new ArrayList<TreeConfigBoHttpModel>();
    	List<Map<String, Object>> treeList = torchConfigTreeService.tree(id);
	   	for (Map<String, Object> map : treeList) {
	   		TreeConfigBoHttpModel tModel = new TreeConfigBoHttpModel();
    		tModel.setId((String) map.get("treeId"));
    		//stModel.setPid((String) map.get("treePid"));
    		tModel.setText((String) map.get("nodeName"));
    		//tModel.setTreeIndex((String) map.get("treeIndex"));
     	//	System.out.println("123");
     		//System.out.println((String) map.get("treeUrl"));
     	//	System.out.println((String) map.get("levelPath"));
     		Map attributes = new HashMap();
     		attributes.put("url", (String) map.get("treeUrl"));
     		attributes.put("levelPath", (String) map.get("levelPath"));
    		tModel.setAttributes(attributes);
    		if (torchConfigTreeService.countChindren((String) map.get("treeId"))>0) {
    			tModel.setState("closed");
 			}
   		    treeListModel.add(tModel);
	   	}
		return treeListModel;
	}
    
    /**
     * 根据treeId查询表sys_menutree_config中的数据
     * @param req
     * @param resp
     * @throws OptimusException
     */
    @RequestMapping("/queryMenuTreeConfigBytreeId")
	public void queryMenuTreeConfigBytreeId(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
    	//String treeId = (String)req.getAttr("treeId");
    	String treeId = ParamUtil.get("treeId");
		Map<String,Object> result = new HashMap<String,Object>();
		torchConfigTreeService.getMenuTreeBytreeId(treeId, result);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("menuTreeInfo");
		Map<String, Object> map = null;
		if(!list.isEmpty()){
			map =list.get(0);
		}else{
			throw new OptimusException("该menuTree不存在");
		}
		resp.addForm("menuTreeConfigPanel", map);
	}
    
    /**
     * 根据treeId查询表SYS_FUNCTION_CONFIG中的数据
     * @param req
     * @param resp
     * @throws OptimusException
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/queryFuncConfigBytreeId")
	public void queryFuncConfigBytreeId(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
//    	String treeId = (String)req.getAttr("treeId");
    	String treeId = ParamUtil.get("treeId");
    	Map<String,String> pageMap = new HashMap<String, String>();
		Map<String,Object> result = new HashMap<String,Object>();
		pageMap.put("treeId",treeId);
		
		//解析jazz  gridPanel  参数
		String postData = req.getParameter("postData");
		Map map = JSON.parseObject(postData);
		List list1 = (List)map.get("data");
		Map map0 = (Map)list1.get(0);
		Map map1 = (Map)list1.get(2);
		String pageSize = String.valueOf((Integer)map0.get("data"));
		String pageNo = String.valueOf((Integer)map1.get("data"));
		pageMap.put("pageSize",pageSize);
		pageMap.put("pageNo",pageNo);
		
		torchConfigTreeService.getFunctionBytreeId(pageMap, result);
		PageBo ret = (PageBo) result.get("functionConfigPanel");
		resp.setPaginationParams(generatePaginationParams(ret));
		if(ret!=null){
			resp.addGrid("functionConfigPanel", ret.getResult());
		}else{
			throw new OptimusException("该function不存在");
		}
	}
    
    /**
	 * 根据functionId清理单个func功能
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	 @RequestMapping("/delFunc")
	    public void delFunc(OptimusRequest req, OptimusResponse res) 
	            throws OptimusException {
		 	String functionId = ParamUtil.get("functionId");
		 	torchConfigTreeService.deleteFunc(functionId);
	        res.addAttr("del", "success");
	    }
	 /**
		 * 构造分页参数
		 * @param bo
		 * @return
		 */
		public Map<String, Object>generatePaginationParams(Object bo){
			if(bo == null){
				return null;
			}
			Map<String,Object> paginationParamsMap = new HashMap<String,Object>();
			if(bo instanceof PageBo){
				PageBo pageBo = (PageBo)bo;
				paginationParamsMap.put("page", pageBo.getPageIndex());
				paginationParamsMap.put("pagerows", pageBo.getPageRows());
				paginationParamsMap.put("totalrows", pageBo.getTotal());
			}
			return paginationParamsMap;
		}
	
	
}

