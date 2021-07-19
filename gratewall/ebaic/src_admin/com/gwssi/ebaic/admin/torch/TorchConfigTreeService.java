package com.gwssi.ebaic.admin.torch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.torch.dao.IBaseCommonDao;
import com.gwssi.torch.dao.TorchDaoManager;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.util.StringUtil;

@Service
public class TorchConfigTreeService {


	/**
	 * 左边的树
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> tree(String id) {
		IBaseCommonDao dao = TorchDaoManager.getDao();
		String sql = null;
		if (id != null && !id.trim().equals("")) {
			sql = "select * from SYS_MENUTREE_CONFIG t where t.tree_pid = '"+id+"' order by t.tree_index";
			
		}else{
			sql = "select * from SYS_MENUTREE_CONFIG t where t.tree_pid is null order by t.tree_index";
			
		}
		List<Map<String, Object>> treeParam = dao.list(sql);
		return treeParam;
	}
	
	public Long countChindren(String id){
		IBaseCommonDao dao = TorchDaoManager.getDao();
		return dao.queryOneNum("select count(*) from SYS_MENUTREE_CONFIG t where t.tree_pid = ? order by t.tree_index", id);
		
	}
	
	/**
	 * 根据treeId查询表SYS_FUNCTION_CONFIG中的数据
	 * @param pageMap
	 * @return
	 */
	public void getFunctionBytreeId(Map<String, String> params,Map<String, Object> result){
			if(StringUtils.isBlank(params.get("treeId"))){
				throw new RuntimeException("treeId can not null null.");
			}
			String querySql = "";
			IBaseCommonDao dao = TorchDaoManager.getDao();
			querySql = " select * from sys_function_config c where c.tree_id=? ";
			int pageSize, pageNo;
			String treeId = params.get("treeId");
			pageSize = StringUtil.string2Int(params.get("pageSize"),10);
			pageNo = StringUtil.string2Int(params.get("pageNo"), 1);
			PageBo ret = null;
			ret = dao.listForPage(querySql, pageNo, pageSize, treeId);
			result.put("functionConfigPanel", ret);
	}

	/**
	 * 根据treeId查询表sys_menutree_config中的数据 
	 * @param treeId
	 * @return
	 */
	public void getMenuTreeBytreeId(String treeId, Map<String, Object> result) {
		if(StringUtils.isBlank(treeId)){
			throw new RuntimeException("treeId can not null null.");
		}
		String querySql = "";
		IBaseCommonDao dao = TorchDaoManager.getDao();
		querySql = "select c.node_name,c.level_path,d.node_name as pname from sys_menutree_config c left join sys_menutree_config d on c.tree_pid = d.tree_id where c.tree_id = ?";
		List<Map<String, Object>> list = dao.list(querySql, treeId);
		result.put("menuTreeInfo", list);
		
	}
	
	/**
	 * 根据functionId清理单个func功能
	 * @param functionId
	 */
	public void deleteFunc(String functionId) {
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(functionId);
		String updateSql = " delete from SYS_FUNCTION_CONFIG s where s.function_id=? ";
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
		
	}
	

}
