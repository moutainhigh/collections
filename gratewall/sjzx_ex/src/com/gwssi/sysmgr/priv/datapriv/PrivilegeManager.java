package com.gwssi.sysmgr.priv.datapriv;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.dao.ds.ConnectFactory;
import cn.gwssi.common.dao.ds.source.DBController;

import com.gwssi.sysmgr.priv.datapriv.dao.DBase;
import com.gwssi.sysmgr.priv.datapriv.exception.DataObjNotFoundException;
import com.gwssi.sysmgr.priv.datapriv.exception.RoleNotFoundException;
import com.gwssi.sysmgr.priv.datapriv.exception.UserNotFoundException;

/**
 * 访问许可管理类
 * 
 * @author 周扬
 */
public class PrivilegeManager {
	private static PrivilegeManager inst = null;

	private static final Logger log = Logger
			.getLogger(BaseDataPurviewItem.class);

	private Document doc = null;

	private HashMap classMap;

	private String path;

	protected PrivilegeManager() {
		classMap = new HashMap();
		path = getClass().getProtectionDomain().getCodeSource().getLocation()
				.getPath();
		try {
			if (path.indexOf("WEB-INF/classes/") >= 0)
				path = path.substring(0,
						path.lastIndexOf("/WEB-INF/classes/") + 17);
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		path += "privilegeConfig.xml";
		log.debug(path);
	}

	/**
	 * 获取唯一实例
	 * 
	 * @return 实例
	 */
	public static PrivilegeManager getInst() {
		if (inst == null)
			inst = new PrivilegeManager();
		return inst;
	}

	/**
	 * 获取用户在某功能下某一数据类型的数据访问许可
	 * 
	 * @param userId
	 *            用户id
	 * @param operId
	 *            功能id
	 * @param dataObj
	 *            数据对象类型
	 * @param dynamicParams
	 *            可扩展参数
	 * @return 返回数据访问许可
	 * @throws IOException
	 * @throws JDOMException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws DataObjNotFoundException
	 * @throws RoleNotFoundException
	 * @throws SQLException
	 * @throws UserNotFoundException
	 * @throws TxnException
	 */
	public IUserPrivilege getPrivilege(String userId, String operId,
			String privilegeType, Map dynamicParams) throws JDOMException,
			IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException, UserNotFoundException, SQLException,
			RoleNotFoundException, DataObjNotFoundException, TxnException {
		openXmlConfig();
		IDataPurviewItem privilegeClass = getDataPurviewItemClass(privilegeType);
		// DBase.getInst().open();
		Element dataSourceNode = (Element) XPath.selectSingleNode(doc,
				"//PrivilegeConfig/DataSource");
		String dataSource = dataSourceNode.getText();
		log.debug(dataSource);

		DBController dc = ConnectFactory.getInstance()
				.getConnection(dataSource);
		DBase.getInst().setConn(dc.getConnection());
		return privilegeClass.getPrivilege(userId, operId, privilegeType,
				dynamicParams);
	}

	/**
	 * 返回所有权限类型
	 * 
	 * @return 权限类型字符串集合
	 * @throws JDOMException
	 * @throws IOException
	 */
	public List getPrivilegeType() throws JDOMException, IOException {
		List result = new ArrayList();

		openXmlConfig();
		Element root = doc.getRootElement();
		List dataClasses = XPath.selectNodes(root, "DataPrivilegeClass/Data");
		for (int i = 0; i < dataClasses.size(); i++) {
			Element dataClass = (Element) dataClasses.get(i);
			result.add(dataClass.getAttributeValue("name"));
		}

		log.debug("All data privilege class:" + result);

		return result;
	}

	/**
	 * <B>根据权限类型和权限项目上级代码获取权限的项目</B><BR>
	 * 返回的权限集合为MAP对象，MAP对象中的字段如下：<BR>
	 * <TABLE BORDER=1 cellspacing=0 cellpadding=0>
	 * <TR>
	 * <TD>ID</TD>
	 * <TD>权限的ID（唯一码）</TD>
	 * </TR>
	 * <TR>
	 * <TD>CODE</TD>
	 * <TD>权限编码</TD>
	 * </TR>
	 * <TR>
	 * <TD>NAME</TD>
	 * <TD>权限名称</TD>
	 * </TR>
	 * <TR>
	 * <TD>BRANCHNUM</TD>
	 * <TD>权限的分支数</TD>
	 * </TR>
	 * </TABLE>
	 * 
	 * @param privilegeType
	 *            权限类型
	 * @param parentCode
	 *            权限项目上级代码
	 * @return 权限项目集合
	 * @throws JDOMException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws TxnException
	 */
	public List getPrivilegeItem(String privilegeType, String parentCode)
			throws JDOMException, IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			TxnException {
		openXmlConfig();
		IDataPurviewItem privilegeClass = getDataPurviewItemClass(privilegeType);

		Map dynamicParams = getDynamicParams(privilegeType);

		Element dataSourceNode = (Element) XPath.selectSingleNode(doc,
				"//PrivilegeConfig/DataSource");
		String dataSource = dataSourceNode.getText();
		DBController dc = ConnectFactory.getInstance()
				.getConnection(dataSource);
		DBase.getInst().setConn(dc.getConnection());

		return privilegeClass.getPurviewItem(parentCode, dynamicParams);
	}

	/**
	 * <B>根据权限类型和权限项目id获取权限的项目</B><BR>
	 * 返回的权限为MAP对象，MAP对象中的字段如下：<BR>
	 * <TABLE BORDER=1 cellspacing=0 cellpadding=0>
	 * <TR>
	 * <TD>ID</TD>
	 * <TD>权限的ID（唯一码）</TD>
	 * </TR>
	 * <TR>
	 * <TD>CODE</TD>
	 * <TD>权限编码</TD>
	 * </TR>
	 * <TR>
	 * <TD>NAME</TD>
	 * <TD>权限名称</TD>
	 * </TR>
	 * <TR>
	 * <TD>BRANCHNUM</TD>
	 * <TD>权限的分支数</TD>
	 * </TR>
	 * </TABLE>
	 * 
	 * @param privilegeType
	 *            权限类型
	 * @param parentCode
	 *            权限项目上级代码
	 * @return 权限项目
	 * @throws JDOMException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws TxnException
	 */
	public Map getPrivilegeItemById(String privilegeType, String id)
			throws JDOMException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			IOException, TxnException {
		openXmlConfig();
		IDataPurviewItem privilegeClass = getDataPurviewItemClass(privilegeType);
		Map dynamicParams = getDynamicParams(privilegeType);

		Element dataSourceNode = (Element) XPath.selectSingleNode(doc,
				"//PrivilegeConfig/DataSource");
		String dataSource = dataSourceNode.getText();

		DBController dc = ConnectFactory.getInstance()
				.getConnection(dataSource);
		DBase.getInst().setConn(dc.getConnection());
		return privilegeClass.getPrivilegeItemById(id, dynamicParams);
	}

	/**
	 * 清空数据权限缓存
	 * 
	 * @param privilegeType
	 * @throws JDOMException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public void init(String privilegeType) throws JDOMException, IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		openXmlConfig();
		IDataPurviewItem privilegeClass = getDataPurviewItemClass(privilegeType);
		privilegeClass.init();
	}

	private Map getDynamicParams(String privilegeType) throws JDOMException {
		Element root = doc.getRootElement();
		Element classNode = (Element) XPath.selectSingleNode(root,
				"DataPrivilegeClass/Data[@name='" + privilegeType + "']");
		Map dynamicParams = new HashMap();
		List params = XPath.selectNodes(classNode, "Param");
		for (int i = 0; i < params.size(); i++) {
			Element param = (Element) params.get(i);
			String paramName = param.getAttributeValue("name");
			String paramValue = param.getAttributeValue("value");
			dynamicParams.put(paramName, paramValue);
		}

		return dynamicParams;
	}

	private IDataPurviewItem getDataPurviewItemClass(String privilegeType)
			throws JDOMException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Element root = doc.getRootElement();
		Element classNode = (Element) XPath.selectSingleNode(root,
				"DataPrivilegeClass/Data[@name='" + privilegeType + "']");
		String className = classNode.getAttributeValue("class");
		IDataPurviewItem privilegeClass = (IDataPurviewItem) classMap
				.get(className);
		if (privilegeClass == null) {
			privilegeClass = (IDataPurviewItem) Class.forName(className)
					.newInstance();
			classMap.put(className, privilegeClass);
		}

		return privilegeClass;
	}

	private void openXmlConfig() throws JDOMException, IOException {
		if (doc == null) {
			SAXBuilder builder = new SAXBuilder();
			doc = (Document) builder.build(new File(path));
		}
	}

	/**
	 * 获取用户在某功能下某一数据类型的数据访问许可
	 * 
	 * @param userId
	 *            用户id
	 * @param operId
	 *            功能id
	 * @param dataObj
	 *            数据对象类型
	 * @return 返回数据访问许可
	 * @throws DataObjNotFoundException
	 * @throws RoleNotFoundException
	 * @throws SQLException
	 * @throws UserNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 * @throws JDOMException
	 * @throws TxnException
	 */
	public IUserPrivilege getPrivilege(String userId, String operId,
			String dataObj) throws JDOMException, IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, UserNotFoundException, SQLException,
			RoleNotFoundException, DataObjNotFoundException, TxnException {
		return getPrivilege(userId, operId, dataObj, new HashMap());
	}

	public static void main(String[] args) throws UserNotFoundException,
			RoleNotFoundException, DataObjNotFoundException,
			ClassNotFoundException, SQLException, JDOMException, IOException,
			InstantiationException, IllegalAccessException, TxnException {
		DBase.getInst().open();
		IUserPrivilege pri = PrivilegeManager.getInst().getPrivilege(
				"168c03b3bac34131ae9d935b8ceb550c", "40101008", "统计制度");
		// Map list = PrivilegeManager.getInst().getPrivilegeItemById("机构",
		// "15d5bb9bff0f4a13a6e99be06fd8cc74");
		System.out.println(pri.getPrivilegeList());
		DBase.getInst().close();
		// System.out.print(list);
	}
}
