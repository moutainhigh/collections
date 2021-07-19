<%!
private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(WCMException.class);


public class MyDBManager {
    ConnectionPool m_oDBPool = null;

    public void connect(String _sURL, String _sUserName, String _sPassword)
            throws Exception {
        if (m_oDBPool != null) {
            return;
        }
        // 1 构造数据库连接配置对象
        DBConnectionConfig dbConfig = makeDBConfig(_sURL, _sUserName,
                _sPassword);

        // 2 启动数据库连接缓冲池

        try {
            m_oDBPool = new ConnectionPool(dbConfig);
        } catch (SQLException ex) {
            throw new WCMException(ExceptionNumber.ERR_CONNECTION_GETFAIL,
                    "连接数据库出现异常！数据库信息为：" + dbConfig, ex);
        }
    }

    private DBConnectionConfig makeDBConfig(String _sURL, String _sUserName,
            String _sPassword) {
        // 1.1 获取当前系统使用的配置（假定是同一类DB）
        DBManager dbMgr = DBManager.getDBManager();
        DBConnectionConfig currDBConfig = dbMgr.getDBConnConfig();

        DBConnectionConfig dbConfig = new DBConnectionConfig();
        dbConfig.setConnectionURL(_sURL);
        dbConfig.setConnectionUser(_sUserName);
        dbConfig.setConnectionPassword(_sPassword);
        dbConfig.setClassName(currDBConfig.getClassName());
        dbConfig.setCacheScheme(currDBConfig.getCacheScheme());
        dbConfig.setDowithClob(currDBConfig.isDowithClob());
        dbConfig.setInitConnects(5);
        dbConfig.setMaxConnects(20);
        return dbConfig;
    }

    public Connection getConnection() throws Exception {
        if (m_oDBPool == null)
            throw new Exception("Please connect db！");

        Connection oConn = null;
        try {
            oConn = m_oDBPool.getConnection(); // 取有效的数据库连接
        } catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_CONNECTION_GETFAIL,
                    I18NMessage.get(DBManager.class, "DBManager.label9",
                            "获取数据库连接时失败(Application.getConnection)"), ex);
        } // end try

        if (oConn == null) {
            throw new WCMException(ExceptionNumber.ERR_CONNECTION_GETFAIL,
                    I18NMessage.get(DBManager.class, "DBManager.label10",
                            "没有找到有效可用的数据库连接(Application.getConnection)"));
        } // end if
        return oConn;
    }

    /**
     * 释放数据库连接
     * 
     * @param _oConn
     *            数据库连接对象
     */
    public void freeConnection(Connection _oConn) {
        if (m_oDBPool == null)
            return;
        m_oDBPool.free(_oConn);
    }

    public void close() {
        if (m_oDBPool == null)
            return;
        m_oDBPool.close();
        m_oDBPool = null;
    }
}

%>