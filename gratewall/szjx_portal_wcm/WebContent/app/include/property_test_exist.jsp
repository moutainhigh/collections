<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" errorPage="error.jsp"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.cms.ObjectTypeServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.database.FieldInfo" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.persistent.ObjectTypesFinder" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.Types" %>
<%
	//wenyh@2009-04-02 ff提交过来的数据是utf8的,统一编码.是否需要拦截处理所有?
	request.setCharacterEncoding("ISO8859-1");

	//toUpperCase
	Map parameters = toUpperCase(request);

	//优先处理存放路径
	String sDataPath = (String)parameters.get("DATAPATH");
	if(sDataPath != null){
		String sSiteId = (String)parameters.get("SITEID");
		String sParentId = (String)parameters.get("PARENTID");
		String sChnlId = (String)parameters.get("CHANNELID");
		if(sChnlId == null && sSiteId == null){
			throw new WCMException(LocaleServer.getString("test_exist.noId","没有传入站点ID[siteid]或栏目ID[channelid]"));
		}
		int nSiteId = -1, nChnlId = -1, nParentId = -1;
		if(sChnlId != null){
			nChnlId = Integer.parseInt(sChnlId);
		}
		if(sSiteId != null){
			nSiteId = Integer.parseInt(sSiteId);
		}
		if(sParentId != null){
			nParentId = Integer.parseInt(sParentId);
		}		
		if(nChnlId != -1){
			out.print(existsSimilarChannelDataPath(nChnlId, nSiteId, nParentId, sDataPath));
		}else if(nSiteId != -1){
			out.print(existsSimilarSiteDataPath(nSiteId, sDataPath));
		}
		return;
	}

	int nObjType = Integer.parseInt((String)parameters.get("OBJTYPE"));
	parameters.remove("OBJTYPE");
	int nObjId = Integer.parseInt((String)parameters.get("OBJID"));
	parameters.remove("OBJID");

	BaseObj obj = getBaseObj(nObjType);
	String sTableName = obj.getDbTableName();
	String sIdFieldName = obj.getIdFieldName();

	parameters = filterInvalidFields(parameters, sTableName);

	out.clear();
	out.print(checkExists(sTableName, sIdFieldName, nObjId, parameters));
%>
<%!
	/**
	*将request里面的变量名转成大写
	*/
	private Map toUpperCase(ServletRequest request){
		Map originalParameters = request.getParameterMap();
		Map parameters = new HashMap();
		for(Iterator itr = originalParameters.entrySet().iterator(); itr.hasNext(); ){
			Map.Entry entry = (Map.Entry)itr.next();
			String sKey = (String)entry.getKey();
			parameters.put(sKey.toUpperCase(), CMyString.getStr(request.getParameter(sKey)));
		}	
		return parameters;
	}

	/**
	*根据_nObjType获取到一个具体的BaseObj实例
	*/
	private BaseObj getBaseObj(int _nObjType) throws Exception{
        Class clazz = ObjectTypesFinder.findObjectClassByType(_nObjType);
		if(clazz == null){
			clazz = ObjectTypeServer.get(_nObjType);
		}
		return (BaseObj) clazz.newInstance();
	}

	/**
	*过滤掉_parameters里面不在表_sTableName中的字段
	*/
	private Map filterInvalidFields(Map _parameters, String _sTableName) throws Exception{
		Map parameters = new HashMap();
		DBManager dgManager = DBManager.getDBManager();
		for(Iterator itr = _parameters.entrySet().iterator(); itr.hasNext(); ){
			Map.Entry entry = (Map.Entry)itr.next();
			String sKey = (String)entry.getKey();
            FieldInfo fi = dgManager.getFieldInfo(_sTableName, sKey);
            if (fi == null){
                continue;
			}

			String _sValue = String.valueOf(entry.getValue()).trim();

			// 2.1保证其它类型的字段不录入""
			int nType = fi.getDataType().getType();

			// 3.类型转换
			Object oValue = _sValue;
			try {
				switch (nType) {
				// 浮点数
				case Types.NUMERIC:
				case Types.DECIMAL: {
					_sValue = _sValue.trim();
					if (fi.getDataScale() == 0) {// 整数
						oValue = new Long(_sValue);
						break;
					}

					oValue = new Double(_sValue);
					break;

				}
				case Types.FLOAT:
				case Types.DOUBLE: {
					_sValue = _sValue.trim();
					oValue = new Double(_sValue);
					break;
				}
				case java.sql.Types.CHAR: // added by hxj
				case java.sql.Types.VARCHAR: {
					oValue = (_sValue == null ? "" : _sValue);
					break;
				}
					// 整数
				case Types.INTEGER:
				case Types.SMALLINT:
				case Types.TINYINT:
				case Types.BIGINT: {
					_sValue = _sValue.trim();
					oValue = new Long(_sValue);
					break;
				}
				}
				parameters.put(sKey, oValue);
			}catch(Exception e){
				parameters.put(sKey, _sValue);
			}

			/*
			*这个地方如果是字符串类型的字段，但如果传入int型数据，将可能出现错误,
			*所以需要用上面的case语句进行判断
			//wenyh@2010-01-26 避免隐式转换发生(sybase会产生错误,一般来说,隐式转换效率也有损伤)
			try{
				Integer.parseInt(value);
				parameters.put(sKey, Integer.valueOf(value));
			}catch(Exception e){
				parameters.put(sKey, value);
			}
			//parameters.put(sKey, entry.getValue());
			*/
		}	
		return parameters;
	}
	
	/**
	*根据_mProperties构造的名值对条件，判断指定的表_sTableName里面是否存在记录
	*/
	private boolean checkExists(String _sTableName, String _sIdFieldName,
			int _nObjId, Map _mProperties) throws Exception{
		StringBuffer sbSQL = new StringBuffer(100);
		sbSQL.append("select count(*) from ").append(_sTableName).append(" where ");
		sbSQL.append(_sIdFieldName).append("!=").append(_nObjId);

		List parameters = Arrays.asList(_mProperties.values().toArray()); 
		for(Iterator itr = _mProperties.entrySet().iterator(); itr.hasNext(); ){
			Map.Entry entry = (Map.Entry)itr.next();
			String sKey = (String)entry.getKey();
			sbSQL.append(" and ").append(sKey).append("=?");
		}	
		return DBManager.getDBManager().sqlExecuteIntQuery(sbSQL.toString(), parameters) > 0;
	}

    /**
     * 是否存在相同存放路径的站点
     * 
     * @throws Throwable
     */
    public boolean existsSimilarSiteDataPath(int _nSiteId, String _sDataPath)
            throws Throwable {
        // 1.2 根据：当前对象的ID/存放路径 构造SQL
        WCMFilter existFilter = new WCMFilter(
                WebSite.DB_TABLE_NAME,
                "SiteId<>?" // and 前后需要空格
                        + " and exists(select FolderId from WCMFolderPublishConfig "//
                        + "where FolderType="
                        + WebSite.OBJ_TYPE
                        + " and FolderId=WCMWebSite.SiteId and DataPath=?)",
                "", "SiteId");
        existFilter.addSearchValues(_nSiteId);
        existFilter.addSearchValues(_sDataPath);

        // 2. 权限校验

        // 3.执行操作（是否存在相同名称的站点）
        return DBManager.getDBManager().sqlExecuteIntQuery(existFilter) > 0;
    }

    /**
     * 在指定的站点或者栏目下是否存在相同存放路径的栏目
     * 
     * @throws Throwable
     */
    public boolean existsSimilarChannelDataPath(int _nChnlId, int _nSiteId, int _nParentId, String _sDataPath)
            throws Throwable {
        // 1.2 根据 站点ID/父对象ID/当前对象ID/存放路径 构造查询SQL
        WCMFilter existFilter = new WCMFilter(
                Channel.DB_TABLE_NAME,
                "SiteId=? and ParentId=? and ChannelId<>? " //
                        + "and exists(select FolderId from WCMFolderPublishConfig "//
                        + "where FolderType="
                        + Channel.OBJ_TYPE
                        + " and FolderId=WCMChannel.ChannelId and DataPath=?)",
                "", "ChannelId");
        if (_nChnlId != 0) {
            Channel chnl = Channel.findById(_nChnlId);
            if (chnl == null) {
                throw new WCMException(CMyString.format(LocaleServer.getString("test_exist.getId.failed","无法获取到[ID={0}]的栏目!"),new int[]{_nChnlId}));
            }
            // else
            _nSiteId = chnl.getSiteId();
            _nParentId = chnl.getParentId();
        }

        existFilter.addSearchValues(_nSiteId);
        existFilter.addSearchValues(_nParentId);
        existFilter.addSearchValues(_nChnlId);
        existFilter.addSearchValues(_sDataPath);

        // 3.执行操作（是否存在相同名称的站点）
        return DBManager.getDBManager().sqlExecuteIntQuery(existFilter) > 0;
    }
%>