/**
 * 
 */
package com.gwssi.sysmgr.priv.datapriv.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gwssi.sysmgr.priv.datapriv.exception.RoleNotFoundException;



/**
 * @author ����
 * ��ɫ��Ϣ�־���
 */
public class RoleDAO {
	private static final Logger log = Logger.getLogger(DBase.class);
	private static RoleDAO inst = new RoleDAO(); 		// Ψһʵ��
	private Map rolesMap = new HashMap();
	
	protected RoleDAO(){
	}
	
	static public RoleDAO getInst(){
		return inst;
	}

	/**
	 * ��ջ���
	 **/
	public void init(){
		rolesMap.clear();
	}
	/**
	 * ��ȡ��ɫ�ɷ��ʵĹ����б�
	 * @param roleId ��ɫID
	 * @return �����б�
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	public List getRoleFuncs(String roleId) throws SQLException, RoleNotFoundException{
		Map role = getRoleInfo(roleId);
		return (List) role.get("Funcs");
	}
	
	/**
	 * ��ȡ��ɫ�ɷ��ʵ�����Ȩ���б�
	 * @param roleId ��ɫID
	 * @return ����Ȩ���б�
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	public List getRoleDataAccGroups(String roleId) throws SQLException, RoleNotFoundException{
		Map role = getRoleInfo(roleId);
		return (List) role.get("DataAccGrp");		
	}

	/**
	 * ��ȡ��ɫ��ĳ�����¿ɷ��ʵ�����Ȩ���б�
	 * @param roleId ��ɫID
	 * @param operId ����ID
	 * @return ����Ȩ���б�
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	public List getRoleDataAccGroups(String roleId,String operId) throws SQLException, RoleNotFoundException{
		Map role = getRoleInfo(roleId);
		List dataAccGrps = (List) role.get("FuncDataAccGrp");
		List result = new ArrayList();
		for(int i = 0; i < dataAccGrps.size(); i++){
			Map dataAccGrp = (Map) dataAccGrps.get(i);
			if(dataAccGrp.get("TXNCODE").equals(operId)){
				result.add(dataAccGrp.get("DATAACCGRPID"));
			}
		}
		log.debug("RoleTxnGroupId:Txn:" + operId + "Grpid:" + result);
		return result;
	}
	
	/**
	 * ��ȡ��ɫ��Ϣ
	 * @param roleId
	 * @return ��ɫ��ϢMap
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	public Map getRoleInfo(String roleId) throws SQLException, RoleNotFoundException{
		if(rolesMap.containsKey(roleId)){
			Map user = (Map) rolesMap.get(roleId);
			log.debug("�Ѵӻ����������ɫ��Ϣ��");
			return user;
		}
		
		// �����ݿ��ȡ��ɫ��Ϣ
		String sql = "select ROLEID,ROLENAME,FUNCLIST from operrole_new " +
				"WHERE ROLEID = " + roleId ;
		List roles = DBase.getInst().query(sql);
		if(roles.size() > 0){
			Map role = (Map) roles.get(0);
			// ���ɽ�ɫ�����б�
			List funcs = new ArrayList();
			try{
				String funcidString = role.get("FUNCLIST").toString();
				String[] funids = funcidString.split(",");
				for(int i = 0; i < funids.length; i++){
					funcs.add(funids[i]);
				}
			} catch(NullPointerException e){
				log.debug("��ɫδ���书��");
			}
			role.put("Funcs", funcs);
			role.put("FuncDataAccGrp", getDataAccsFromRoleFunction(roleId));
			role.put("DataAccGrp", getDataAccsFromRole(roleId));
			rolesMap.put(roleId, role);
			log.debug("IDΪ[" + roleId + "]�Ľ�ɫ��Ϣ�ѻ���");
			
			return role;
		}
		
		log.debug("IDΪ[" + roleId + "]�Ľ�ɫ������");
		throw new RoleNotFoundException();
	}
	
	/**
	 * ��ȡ��ɫ�ɷ��ʵ�����Ȩ�����б�
	 * @param roleId ��ɫID
	 * @return ����Ȩ�����б�
	 * @throws SQLException
	 */
	private List getDataAccsFromRole(String roleId) throws SQLException{
		// ��ý�ɫ������Ȩ�����б�
		String sql = "select distinct DATAACCGRPID from DATAACCDISP where DATAACCDISPOBJ = '1' and " +
				"OBJECTID = " + roleId;
		List dataAccs = DBase.getInst().getFieldList(sql);
		return dataAccs;
	}
	
	/**
	 * ��ȡ��ɫ����������Ȩ����Ĺ�������
	 * @param roleId ��ɫ
	 * @return ����������Ȩ����Ĺ�������
	 * @throws SQLException
	 */
	private List getDataAccsFromRoleFunction(String roleId) throws SQLException{
		// ��ý�ɫ������Ȩ�����б�
		String sql = "select A.TXNCODE,B.DATAACCGRPID from OperRoleFun A " +
				"inner join DATAACCDISP B ON A.ROLEACCID = B.OBJECTID AND " +
				"B.DATAACCDISPOBJ = '2' where A.ROLEID = " + roleId; 
		List dataAccs = DBase.getInst().query(sql);
		return dataAccs;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DBase.getInst().open();
			RoleDAO.getInst().getRoleFuncs("101");
			RoleDAO.getInst().getRoleFuncs("101");
			RoleDAO.getInst().getRoleFuncs("10021054");
			RoleDAO.getInst().getRoleFuncs("10021054");
			DBase.getInst().close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (RoleNotFoundException e) {
			e.printStackTrace();
		}
	}
}
