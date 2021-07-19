package cn.gwssi.quartz.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.gwssi.quartz.model.TPtTaskBO;
import cn.gwssi.quartz.model.TPtTaskLockBO;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.BeanUtil;

@Service
public class JobDataService  extends BaseService{

	/**
	 * get all jobtask
	 * @throws OptimusException 
	 */
	public List<TPtTaskBO> getJobList( ) throws OptimusException{
		//查询所有定时任务
		IPersistenceDAO  dao=getPersistenceDAO();
		List<TPtTaskBO> list=dao.queryForList(TPtTaskBO.class,"select * from T_PT_Task where  state ='1' ", null);
		return list;
	}
	
	
	public TPtTaskBO getJobById(String taskId) throws OptimusException{
		IPersistenceDAO  dao=getPersistenceDAO();
		List list=dao.queryForList("select * from T_PT_Task where taskID='"+taskId+"'", null);
		
		TPtTaskBO bo=new TPtTaskBO();
		if(list!=null&&list.size()>0){
			BeanUtil.mapToBean((Map<String, String>) list.get(0), bo);
		}
		
		return bo;
	}
	
	
//	public boolean catchTaskLock(String taskId) throws OptimusException, UnknownHostException{
//		IPersistenceDAO  dao=getPersistenceDAO();
//		int bl=dao.execute("update T_PT_Task set lockColumn='"+InetAddress.getLocalHost().toString()+"' where taskid='"+taskId+"' ", null);
//		return Boolean.valueOf(bl+"");
//	}
	
//	public boolean releaseTaskLock(String taskId) throws UnknownHostException, OptimusException{
//		IPersistenceDAO  dao=getPersistenceDAO();
//		int bl=dao.execute("update T_PT_Task set lockColumn='' where taskid='"+taskId+"' ", null);
//		return Boolean.valueOf(bl+"");
//	}
	
	/**查看该任务是否被锁住
	 * @param taskId
	 * @return
	 * @throws OptimusException
	 */
	public String getJobLockById(String taskId) throws OptimusException{
		String node="";
		IPersistenceDAO  dao=getPersistenceDAO();
		List<TPtTaskLockBO> list=dao.queryForList(TPtTaskLockBO.class,"select * from T_PT_Task_lock where taskID='"+taskId+"'", null);
		for(TPtTaskLockBO bo:list){
			node=bo.getNode();
		}
		return node;
	}
	/**锁住任务
	 * @param taskId
	 * @throws OptimusException
	 * @throws UnknownHostException 
	 */
	public void catchTaskLock(String taskId) throws OptimusException, UnknownHostException{
		IPersistenceDAO  dao=getPersistenceDAO();
		String sql="insert into T_PT_TASK_LOCK(taskid,node) values ('"+taskId+"','"+InetAddress.getLocalHost().toString()+"')";
		dao.execute(sql, null);
	}
	
	
	/**解锁任务
	 * @param taskId
	 * @throws OptimusException
	 */
	public void releaseTaskLock(String taskId) throws OptimusException{
		IPersistenceDAO  dao=getPersistenceDAO();
		String sql="delete from T_PT_TASK_LOCK where taskid='"+taskId+"'";
		dao.execute(sql, null);
	}
	
	
	/**解锁所有任务
	 * @throws OptimusException
	 */
	public void releaseALlTaskLock() throws OptimusException{
		IPersistenceDAO  dao=getPersistenceDAO();
		String sql="delete from T_PT_TASK_LOCK ";
		dao.execute(sql, null);
		
	}
	
	
}
