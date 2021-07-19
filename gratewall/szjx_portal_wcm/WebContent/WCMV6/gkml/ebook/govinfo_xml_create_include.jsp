<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Hashtable" %>

<%@ page import="org.dom4j.DocumentHelper" %>
<%@ page import="org.dom4j.Element" %>

<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.components.metadata.definition.IClassInfoMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyMemory" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<%!
	/**
	*需要产生数据的年份范围
	*/
    private int[] m_aYearSpan = { 2001, 2008 };

	/**
	*需要产生数据的分类法Id序列
	*/
    private String m_sClassInfoIds = "832";//"244,315";

	/**
	*生成数据时，涉及到的表中的字段
	*/
    private String[] m_aFieldName = new String[] { "fileNum", "idxID",
            "acesmthd", "svobjcat", "Publisher", "PubDate", "Description",
            "Relation", "subcat","Keywords","Title" };;

	/**
	*产生数据时，是否输出调试信息
	*/
    private boolean m_bDebug = true;

	private int m_nMetaViewId = 39;

    private String m_sClassInfosTagName = "ClassInfos";

    private String m_sClassInfoTagName = "ClassInfo";

    private String m_sMetaDatasTagName = "MetaDatas";

    private String m_sMetaDataTagName = "MetaData";

    private String m_sPropertiesTagName = "Properties";

    private int m_nProcessIndex = 1;

    private WCMFilter m_oFilterOfYear = null;

    private MetaView m_oMetaView = null;

    private IClassInfoMgr m_oClassInfoMgr = null;


    /**
     * 设置要处理的年份范围。如： 2007表示要生成2007年的； 2003,2005表示要生成2003年到2005年的
     * 
     * @param sYearSpan
     */
    public void setYearSpan(String sYearSpan) {
        if (CMyString.isEmpty(sYearSpan)) {
            return;
        }
        int[] aTempYearSpan = CMyString.splitToInt(sYearSpan, ",");
        m_aYearSpan = new int[] { aTempYearSpan[0],
                aTempYearSpan.length > 1 ? aTempYearSpan[1] : aTempYearSpan[0] };
    }

    /**
     * 设置要处理的分类法序列，如：214，349表示处理分类法id为214和349的分类法
     * 
     * @param sClassInfos
     */
    public void setClassInfoIds(String sClassInfoIds) {
        m_sClassInfoIds = sClassInfoIds;
    }

    /**
     * 设置要处理的视图Id
     * 
     * @param iMetaViewId
     */
    public void setMetaViewId(int iMetaViewId) {
        m_nMetaViewId = iMetaViewId;
    }

    /**
     * 设置是否输出状态信息
     * 
     * @param isDebug
     */
    public void setDebug(boolean isDebug) {
        m_bDebug = isDebug;
    }

    /**
     * 产生书对应的xml串
     * 
     * @return
     */
    public Element toXML() throws Exception {
		m_nProcessIndex = 1;
        m_oMetaView = MetaView.findById(m_nMetaViewId);

        m_oClassInfoMgr = (IClassInfoMgr) DreamFactory
                .createObjectById("IClassInfoMgr");

        Element rootElement = DocumentHelper
                .createElement(m_sClassInfosTagName);

        for (int nYear = m_aYearSpan[0]; nYear <= m_aYearSpan[1]; nYear++) {
            appendClassInfoOfYear(nYear, rootElement);
        }

        if (m_bDebug) {
            System.out.println(CMyMemory.toMemoryInfo());
        }
        
        return rootElement;
    }

    /**
     * 追加每一年下的xml节点
     * 
     * @param nYear
     */
    private void appendClassInfoOfYear(int nYear, Element oNode)
            throws Exception {
        storeWCMFilter(nYear);

        //Element oClassInfoNode = oNode.addElement(m_sClassInfoTagName);
        //Element oProperties = oClassInfoNode.addElement(m_sPropertiesTagName);
        //oProperties.addElement("CName").addCDATA(int2StrOfYear(nYear) + "年");
		//得到目录导航对应的对象信息ClassInfos
        ClassInfos oClassInfos = ClassInfos.findByIds(null, m_sClassInfoIds);
		ClassInfo currClassInfo = (ClassInfo)oClassInfos.getAt(0);
		//得到目录导航的所有子分类的信息
		oClassInfos = m_oClassInfoMgr.queryChildren(null,currClassInfo, null);
		//开始生成XML文件
        appendClassInfos(oNode, oClassInfos);
    }

    private void storeWCMFilter(int nYear) throws Exception {
        WCMFilter filter = new WCMFilter();
        filter.setOrder("WCMMetaTableGovInfo.PubDate desc");
        filter.setWhere("WCMMetaTableGovInfo.PubDate >= ? and WCMMetaTableGovInfo.PubDate < ?");
        CMyDateTime dtStart = new CMyDateTime();
        dtStart.setDateTimeWithString(nYear + "-1-1 00:00:00");
        filter.addSearchValues(dtStart);

        CMyDateTime dtEnd = new CMyDateTime();
        dtEnd.setDateTimeWithString((nYear + 1) + "-1-1 00:00:00");
        filter.addSearchValues(dtEnd);

        m_oFilterOfYear = filter;
    }

    private void appendClassInfo(Element oNode, ClassInfo _oClassInfo)
            throws Exception {
        if (_oClassInfo == null) {
            return;
        }

        Element oClassInfoNode = oNode.addElement(m_sClassInfoTagName);

        // 追加当前分类的属性信息
		appendProperties(oClassInfoNode, _oClassInfo);

        // 追加当前分类的MetaDatas信息
		//得到当前分类的所有的子分类
		ClassInfos oClassInfos = m_oClassInfoMgr.queryChildren(null,_oClassInfo, null);
		ClassInfo oCurrClassInfo = null;
		//循环添加当前分类的所有子分类信息
		for(int i=0;i<oClassInfos.size();i++)
		{
			oCurrClassInfo = (ClassInfo)oClassInfos.getAt(i);
			appendMetaDatas(oClassInfoNode, oCurrClassInfo);
		}

        // 追加当前分类的子分类信息
        //ClassInfos oClassInfos = m_oClassInfoMgr.queryChildren(null,                _oClassInfo, null);
       // appendClassInfos(oClassInfoNode, oClassInfos);
    }

    /**
     * 追加该分类下的属性信息
     * 
     * @param oNode
     * @param _oClassInfo
     */
    private void appendProperties(Element oNode, ClassInfo _oClassInfo) {
        if (_oClassInfo == null) {
            return;
        }
        Element oProperties = oNode.addElement(m_sPropertiesTagName);
        oProperties.addElement("CName").addCDATA(_oClassInfo.getName());
    }

    /**
     * 追加该分类下的数据集信息
     * 
     * @param oNode
     * @param _oClassInfo
     */
    private void appendMetaDatas(Element oNode, ClassInfo _oClassInfo)
            throws Exception {
        if (_oClassInfo == null) {
            return;
        }
        MetaViewDatas oMetaDatas = new MetaViewDatas(m_oMetaView);
        oMetaDatas.open(getMetaDatasFilter(_oClassInfo));

        if (oMetaDatas == null || oMetaDatas.size() <= 0) {
            return;
        }
        Element oMetaDatasNode = oNode.addElement(m_sMetaDatasTagName);

        for (int i = 0, nSize = oMetaDatas.size(); i < nSize; i++) {
            MetaViewData oMetaData = (MetaViewData) oMetaDatas.getAt(i);
            if (oMetaData == null)
                continue;

            appendMetaData(oMetaDatasNode, oMetaData, _oClassInfo);
        }

    }

    public WCMFilter getMetaDatasFilter(ClassInfo _oClassInfo) {
        if (_oClassInfo == null) {
            return null;
        }

        WCMFilter filter = new WCMFilter("", "", "PubDate asc");
        StringBuffer sbWhere = new StringBuffer(300);
        sbWhere.append("exists(select XWCMClassInfoView.MetaDataId");
        sbWhere.append(" from XWCMClassInfoView");
        sbWhere.append(" where XWCMClassInfoView.ClassInfoId = ?");
        filter.addSearchValues(_oClassInfo.getId());
        sbWhere
                .append(" and XWCMClassInfoView.MetaDataId=WCMMetaTableGovInfo.MetaDataId) and exists(select 1 from wcmchnldoc where wcmchnldoc.docid=WCMMetaTableGovInfo.MetaDataId and wcmchnldoc.docstatus=10)");
        filter.setWhere(sbWhere.toString());
        filter.mergeWith(m_oFilterOfYear);
        return filter;
    }

    /**
     * 追加该分类下的单条数据信息
     * 
     * @param _oNode
     * @param _oMetaData
     * @param _oClassInfo
     * @throws Exception
     */
    private void appendMetaData(Element _oNode, MetaViewData _oMetaData,
            ClassInfo _oClassInfo) throws Exception {
        if (_oMetaData == null) {
            return;
        }

        // 预处理数据
        Hashtable propertis = new Hashtable();

        for (int i = 0; i < m_aFieldName.length; i++) {
            String sValue = _oMetaData.getRealProperty(m_aFieldName[i]);
            if (sValue == null || sValue.trim().equalsIgnoreCase("null")) {
                sValue = "";
            }
            propertis.put(m_aFieldName[i].toUpperCase(), sValue);
        }
       // propertis.put("CCODE", CMyString.showNull(_oClassInfo.getCode(), ""));

        CMyDateTime dtEefect = _oMetaData.getPropertyAsDateTime("efectdate");
        if (dtEefect == null || dtEefect.isNull()) {
            propertis.put("efectdate".toUpperCase(), propertis.get("PubDate"
                    .toUpperCase()));
        }

        Element eProperties = _oNode.addElement(m_sMetaDataTagName).addElement(
                m_sPropertiesTagName);
        Enumeration keys = propertis.keys();

        while (keys.hasMoreElements()) {
            String sKey = (String) keys.nextElement();
            String sValue = (String) propertis.get(sKey);
            eProperties.addElement(sKey).addCDATA(sValue);
        }

        if (m_bDebug) {
            System.out.println("已生成第" + (m_nProcessIndex++) + "条记录");
        }
    }

    /**
     * 追加该分类下的子分类信息
     * 
     * @param oNode
     * @param _oClassInfo
     */
    private void appendClassInfos(Element oNode, ClassInfos _oClassInfos)
            throws Exception {

        if (_oClassInfos == null || _oClassInfos.size() <= 0) {
            return;
        }
        //Element oClassInfosNode = oNode.addElement(m_sClassInfosTagName);
		Element oClassInfosNode = oNode;

        for (int i = 0, nSize = _oClassInfos.size(); i < nSize; i++) {
            ClassInfo oClassInfo = (ClassInfo) _oClassInfos.getAt(i);
            if (oClassInfo == null)
                continue;
            appendClassInfo(oClassInfosNode, oClassInfo);
        }
    }

    /**
     * 将年份转成大写格式
     * 
     * @param year
     * @return
     */
    private String int2StrOfYear(int nYear) {
        String[] aYearDesc = { "○", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        StringBuffer sb = new StringBuffer(4);

        String sYear = String.valueOf(nYear);
        for (int i = 0, length = sYear.length(); i < length; i++) {
            sb.append(aYearDesc[Integer.parseInt(sYear.substring(i, i + 1))]);
        }
        return sb.toString();
    }

%>