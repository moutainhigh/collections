package com.gwssi.datacenter.dataSource.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.datacenter.dataSource.service.DataSourceManagerService;
import com.gwssi.datacenter.dataSource.service.DataStructLoadBySqlService;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.TreeUtil;

/**
 * @ClassName: DataStructLoadController
 * @Description:数据源结构装载管理 包括数据源的 表结构 字段 视图 触发器 存储过程
 * @author chaihaowei
*/
@Controller
@RequestMapping("/dataSource")
public class DataStructLoadController {
	
    @Autowired
    private DataSourceManagerService dataSourceManagerService;  
    
    @Autowired
    private DataStructLoadBySqlService dataStructLoadbySqlService;
	/**
	 * 数据源结构装载界面 跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getData_Struct_LoadHtml")
	public void getDataSource_manageHtml(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		resp.addPage("/page/datasource/data_struct_load.jsp");
	}
	/**
	 * 列出该数据源的当前用户表 页面    跳转到data_struct_table.jsp
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getdata_struct_dual")
	public void getDataSource_addHtml(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		//1加载数据源表结构以及表所对应的字段  type是:"table"
		String type= req.getParameter("type");
		
		//获取数据源主键
		String pkDcDataSource=req.getParameter("pkDcDataSource");
		if(type==null||type.equals("")||type.equals("")){
			type="";
		}
		if(StringUtils.isBlank(pkDcDataSource)){
			pkDcDataSource="";
		}
		resp.addAttr("type",type);
		resp.addAttr("pkDcDataSource",pkDcDataSource);
		resp.addPage("/page/datasource/data_struct_table.jsp");  
	}	
	
	/**
	 * 获取当前数据源用户表的Table树
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/query_data_table")
    public void queryAuthServiceTree(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
       	String pkDcDataSource = req.getParameter("pkDcDataSource");
		DcDataSourceBO smser = new DcDataSourceBO();
		smser=dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource);
		
    	//List listPost = dataStructLoadService.findCurrUserTable(smser);
		
		//当前表树 id 和 pid
    	List listPost = dataStructLoadbySqlService.findCurrUserTable(smser);
        Map rootNode = new HashMap();
        rootNode.put("name", smser.getDataSourceName());
        rootNode.put("id", "system");
        rootNode.put("open", true);
		listPost.add(rootNode);
    	
    	
    	
    	//已经加载过的树表
    	List currdatasource =dataStructLoadbySqlService.findTableTreeCurrDataSource(smser);	
    	if(currdatasource.size()>0){
    		List tree=TreeUtil.makeCheckedTree(listPost, currdatasource, "id");		
    		res.addTree("listPost", tree);
    	}else{
    		res.addTree("listPost", listPost);	
    	}
    }
	
	/**
     *保存 当前数据源的表以及字段 
     * @param req
     * @param res
     * @throws OptimusException
     */
    @RequestMapping("/saveTableAndFields")
    public void saveTableAndFields(OptimusRequest req, OptimusResponse res) throws OptimusException 
             {
    	
    	//获取所需要加载的表名
        String funcIdsStr = (String)req.getAttr("funcIdsStr");
        List<String> tablenames = new ArrayList<String>();
        if(StringUtils.isEmpty(funcIdsStr)){
        }else{
        	//表名列
        	tablenames = Arrays.asList(funcIdsStr.split(","));
        }
        
        //获取数据源主键
        String pkDcDataSource=(String)req.getAttr("pkDcDataSource");
        
        //获取当前数据源BO
		DcDataSourceBO smser = new DcDataSourceBO();
		smser=dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource);
		
        //boolean  t1 = dataStructLoadService.saveTableAndFileds(tablenames,smser);
		
		//进行保存操作
		
        boolean t1;
		try {
			t1 = dataStructLoadbySqlService.addTableAndFileds(tablenames,smser);
		} catch (OptimusException e) {		
			t1=false;
			e.printStackTrace();
		}
		String	back="failure";
        if(t1){
        	back="success";
        	//更新数据源最后最初加载人 加载时间等
        	dataStructLoadbySqlService.updateDataSourceTimeAndUser(smser);
        }

	
		
		res.addAttr("back", back);


    } 
    /**
     * 加载视图
     * @param req
     * @param res
     * @throws OptimusException
     */
    @RequestMapping("/saveViews")
    public void saveViews(OptimusRequest req, OptimusResponse res) throws OptimusException 
             {
        //获取数据源主键
        String pkDcDataSource=(String)req.getAttr("pkId");
        //获取当前数据源BO
		DcDataSourceBO smser =null;
		smser=dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource);
		
        boolean t1=false;
		try {
			t1 = dataStructLoadbySqlService.addViews(smser);
			t1=true;
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String back=null;
        if(t1){
			back="success";
			dataStructLoadbySqlService.updateDataSourceTimeAndUser(smser);
        }else{
        	back="failure";
        }
		
		res.addAttr("back", back);
    }  	
    /**
     * 加载全部 包括表结构   视图 等
     * @param req
     * @param res
     * @throws OptimusException
     */
    @RequestMapping("/saveAll")
    public void saveAll(OptimusRequest req, OptimusResponse res) throws OptimusException 
             {
    	//判断是否成功 
     	boolean success=true;
     	
        //获取数据源主键
        String pkDcDataSource=(String)req.getAttr("pkId");
        //获取当前数据源BO
		DcDataSourceBO smser =null;
		smser=dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource);
		
         Map<String, Boolean> t1= new HashMap<String,Boolean>();
		try {
			t1 = dataStructLoadbySqlService.addAll(smser);
		} catch (OptimusException e) {
			success=false;
			e.printStackTrace();
		}
         
        

/*         for (Map.Entry entry : t1.entrySet()) {  
             String key = (String) entry.getKey( );   
             Boolean t12=(Boolean) entry.getValue();
             if(!t12){
            	 success=false;
             }
         }  */
        String back=null;
        if(success){
			back="success";
			dataStructLoadbySqlService.updateDataSourceTimeAndUser(smser);
        }else{
        	back="failure";
        }
		
		res.addAttr("back", back);
    }  	    
    
    /**
     * 加载触发器
     * @param req
     * @param res
     * @throws OptimusException
     */
    @RequestMapping("/saveTrigger")
    public void saveTrigger(OptimusRequest req, OptimusResponse res) throws OptimusException 
{
        //获取数据源主键
        String pkDcDataSource=(String)req.getAttr("pkId");
        //获取当前数据源BO
		DcDataSourceBO smser =null;
		smser=dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource);
		
        boolean t1=false;
		try {
			t1 = dataStructLoadbySqlService.insertTrigger(smser);
			t1=true;
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String back=null;
        if(t1){
			back="success";
			dataStructLoadbySqlService.updateDataSourceTimeAndUser(smser);
        }else{
        	back="failure";
        }
		
		res.addAttr("back", back);
    }  	
    /**
     * 加载存储过程
     * @param req
     * @param res
     * @throws OptimusException
     */
    @RequestMapping("/saveProcedure")
    public void saveProcedure(OptimusRequest req, OptimusResponse res) throws OptimusException 
            {
        //获取数据源主键
        String pkDcDataSource=(String)req.getAttr("pkId");
        //获取当前数据源BO
		DcDataSourceBO smser =null;
		smser=dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource);
		
        boolean t1=false;
		try {
			t1 = dataStructLoadbySqlService.insertProcedure(smser);
			t1=true;
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String back=null;
        if(t1){
			back="success";
			dataStructLoadbySqlService.updateDataSourceTimeAndUser(smser);
	
        }else{
        	back="failure";
        }
		
		res.addAttr("back", back);
    }  
	/**
	 * 数据源 验证 没有封装的ajax验证 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/DataSourceTestbyPKAjax")
	@ResponseBody
	public Map DataSourceTestbyPKAjax(OptimusRequest req, OptimusResponse resp) 
	        throws Exception {
		Map map=new HashMap();
		//测试是能正常连接 
		//	1 true 正常连接  
		// 	2   false 连接失败）
		Boolean dataSourceConnect =false;
		
		String pkID = req.getParameter("pkId").toString();
		DcDataSourceBO datasource = dataSourceManagerService.findDcDataSourceBOByPK(pkID);
		//数据源类型
		// dataSourceType =datasource.getDataSourceType();
		String dbType =datasource.getDbType();
		if(dbType.equals("oracle")){
			dataSourceConnect= dataSourceManagerService.dodataSourceConnectDbExists(datasource);
			//dataSourceConnect= dataSourceManagerService.dataSourceConnect(datasource,datasource.getPkDcDataSource());

			
		}else{
			//错误----
		}
		if(dataSourceConnect){
			map.put("back", "success");
			//resp.addAttr("back","success");
		}else{
			map.put("back", "fail");
			map.put("msg", "数据源连接失败 ，不能正常加载，请检查数据源状态！");
		//	resp.addAttr("back","failure");
		}
		return map;
	}			
}