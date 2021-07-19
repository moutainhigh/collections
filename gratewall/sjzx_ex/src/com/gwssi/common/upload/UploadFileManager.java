package com.gwssi.common.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.file.manage.vo.VoXtCcglWjlb;
import com.gwssi.file.manage.vo.VoXtCcglWjys;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoCtrl;
import cn.gwssi.common.context.vo.VoFileInfo;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

/**
 * @desc �����ϴ�������
 * ��������:�ϴ����޸ġ�ɾ������ȡ�ļ�·����
 * �������ݰ��������֣��洢�����ϵĸ������ģ����ݿ����ļ�ӳ���¼��
 * �������������������ֵĸ�������ͬ������
 * ֧�����ָ�����Դ�����̨��������VoFileInfo;��������InputStream
 * @author adaFang
 * @version 1.0
 *
 */
public class UploadFileManager extends TxnService
{

	private static final String WJLBNULLERROR = "ggkz.ccgl.wjys.select.wjlbnullerror";

    // �ļ���������
    private static final String WJLB_TABLE = "xt_ccgl_wjlb";

    // �ļ�ӳ�������
    private static final String WJYS_TABLE = "xt_ccgl_wjys";

    // ��������¼��ѯ
    private static final String SELECT_FUNCTION_PK = "select one xt_ccgl_wjys by pk";

    // �޸��ļ����Ƽ�¼
    private static final String UPDATE_FILE_FUNCTION = "update file name xt_ccgl_wjys";    

    // �޸��ļ����Ƽ�¼
    private static final String UPDATE_FILEWYBS_FUNCTION = "update file wybs xt_ccgl_wjys";

    // ���Ӽ�¼
    private static final String INSERT_FUNCTION = "insert one xt_ccgl_wjys";

    // ɾ����¼
    private static final String DELETE_FUNCTION = "delete one xt_ccgl_wjys";

    private final static Logger log = TxnLogger
            .getLogger(UploadFileManager.class.getName());

    // �ļ���Ŀ¼
    private static String root;

    // �洢���������ֵ
    private static String cclbbh_pk;

    // ����Ŀ¼����
    private static String ejmlgz;

    // Ĭ���ļ�Ŀ¼������û�����ö���Ŀ¼�����
    private static final String DEFAULT_SECOND_DIR = "default";
	
	@Override
	protected void prepare(TxnContext arg0) throws TxnException
	{
		// TODO Auto-generated method stub
		
	}
	
	private UploadFileManager(String fileType) throws TxnException {
        getRootPathByType(fileType);
    }

    /**
     * ��ȡ�ϴ�������ʵ��
     * 
     * @param fileType :
     *            �ļ����������ConstUploadFileTypeͳһά��
     * @return UploadFileManager : �ϴ�����ʵ��
     * @throws TxnException
     */
    public static UploadFileManager getInstance(String fileType)
                                                                throws TxnException {

        UploadFileManager manager = new UploadFileManager(fileType);
        return manager;
    }

    /**
     * ���浥������
     * 
     * @param voFile
     *            �����̨�ϴ��ļ�VO����
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile) throws TxnException {
        return saveFile(voFile, "");
    }

    /**
     * ���浥������
     * 
     * @param voFile
     *            �����̨�ϴ��ļ�VO����
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz) throws TxnException {
        return saveFile(voFile, ywbz, null);
    }

    /**
     * ���浥������
     * 
     * @param voFile
     *            �����̨�ϴ��ļ�VO����
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @param ejml
     *            ������Ŀ¼����
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, String ejml)
                                                                        throws TxnException {
        return saveFile(voFile, ywbz, ejml, "");
    }

    /**
     * ���浥������
     * 
     * @param voFile
     *            �����̨�ϴ��ļ�VO����
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @param ejml
     *            ������Ŀ¼����
     * @param xm_fk :
     *            ��Ŀ���� �����ֳ��������ݽ������޸�ʱ�䣺2007��01��25�գ�
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, String ejml,
            String xm_fk) throws TxnException {
        if (voFile == null || voFile.getOriFileName().trim().length() == 0)
            return null;
        
        // ��ȡ�ϴ�������Ϣ
        String oriFileName = voFile.getOriFileName().trim();// ��������(ԭʼ�ļ���)
        String tempFileName = voFile.getValue(VoCtrl.ITEM_SVR_FILENAME);// ��ʱ�ļ�����
        //log.debug("Upload:::tempFileName==="+tempFileName);
        String uploadFileName = tempFileName.substring(tempFileName
                .lastIndexOf(FileConstant.PATH_SEPERATOR1) + 1);
        
        
        /**
         * bug�޸ģ��޸ĸ���������Ӣ�Ķ���ʱ�ļ�����������
         * �޸��ˣ�������
         * �޸�ʱ�䣺2007-8-4
         */
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ�� "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ������"+oriFileName);
        
        // ��ȡ����Ŀ¼
        String secondDir = getSecondDir(ejml);
        // �����ļ�����ʱ��
        String cjsj = CalendarUtil.getCurrentDateTime();
        // ����ϴ�������ȫ·�����ļ����͵ĸ�Ŀ¼+����Ŀ¼+�ļ�����
        String prePath = root + FileConstant.PATH_SEPERATOR + secondDir;
        String filePath = prePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        // �½��ش�����DataBus
        DataBus result = new DataBus();
        
        // �����ļ�Ŀ¼�ļ�
        createFile(tempFileName, filePath);
        // ɾ����ʱ�ļ�
        delFile(tempFileName);
        
        try {
            TxnContext context = new TxnContext();
            DataBus db = new DataBus();
            // �����������ݽڵ�
            db.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
            db.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
            db.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
            db.put(VoXtCcglWjys.ITEM_WJZT, FileConstant.status_inuse);
            db.put(VoXtCcglWjys.ITEM_CCLJ, secondDir);
            db.put(VoXtCcglWjys.ITEM_CJSJ, cjsj);
            db.put(VoXtCcglWjys.ITEM_SCXGSJ, cjsj);
            db.put(VoXtCcglWjys.ITEM_BZ, "");
            db.put(VoXtCcglWjys.ITEM_YWBZ, ywbz);
            db.put(VoXtCcglWjys.ITEM_YSBH_PK, "");
            // ****** ������Ŀ�����������ֳ���������ݽ��� @20070125 by ada
            db.put(VoXtCcglWjys.ITEM_XM_FK, xm_fk);
            
            context.addRecord("wjys", db);
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, WJYS_TABLE);
            table.executeFunction(INSERT_FUNCTION, context, db, "wjys");
            
            // ���ظ���idֵ�͸���nameֵ
            result.put(FileConstant.file_id, context.getRecord("wjys")
                    .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
            result.put(FileConstant.file_name, context.getRecord("wjys")
                    .getValue(VoXtCcglWjys.ITEM_WJMC));

            // ���context�е��ļ�ӳ�������
            context.remove("wjys");
            
        } catch (Exception e) {
            log.error("�ļ�ӳ���ϵ�洢���ݿ����"+e.fillInStackTrace());
            log.equals("��������ʧ��!");
            //throw new TxnErrorException(ErrorConstant.FILE_CREATE_ERROR,BJAISConfig.get("ggkz.ccgl.wjys.io.createerror"));
        }

        return result;
    }

    /**
     * ���浥������
     * 
     * @param InputStream
     *            ���ϴ��ļ���������
     * @param oriFileName
     *            ���ļ�����
     * @param uploadFileName
     *            ���洢�ļ�������
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @param ejml
     *            ������Ŀ¼����
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus saveFile(InputStream fileStream, String oriFileName,
            String uploadFileName, String ywbz, String ejml)
                                                            throws TxnException {
        return saveFile(fileStream, oriFileName, uploadFileName, ywbz, ejml, "");
    }

    /**
     * ���浥������
     * 
     * @param InputStream
     *            ���ϴ��ļ���������
     * @param oriFileName
     *            ���ļ�����
     * @param uploadFileName
     *            ���洢�ļ�������
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @param ejml
     *            ������Ŀ¼����
     * @param xm_fk :
     *            ��Ŀ���� �����ֳ��������ݽ������޸�ʱ�䣺2007��01��25�գ�
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus saveFile(InputStream fileStream, String oriFileName,
            String uploadFileName, String ywbz, String ejml, String xm_fk)
                                                                          throws TxnException {

        String secondDir = getSecondDir(ejml);
        String cjsj = CalendarUtil.getCurrentDateTime();
        // ����ϴ�������ȫ·�����ļ����͵ĸ�Ŀ¼+����Ŀ¼+�ļ�����
        String prePath = root + FileConstant.PATH_SEPERATOR + secondDir;
        String filePath = prePath + FileConstant.PATH_SEPERATOR + uploadFileName;

        // �����ļ�
        createFile(fileStream, filePath);

        
        /**
         * bug�޸ģ��޸ĸ���������Ӣ�Ķ���ʱ�ļ�����������
         * �޸��ˣ�������
         * �޸�ʱ�䣺2007-8-4
         */
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ�� "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ������"+oriFileName);
        
        TxnContext context = new TxnContext();
        DataBus db = new DataBus();  
        // �����������ݽڵ�
        db.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
        db.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        db.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        db.put(VoXtCcglWjys.ITEM_WJZT, FileConstant.status_inuse);
        db.put(VoXtCcglWjys.ITEM_CCLJ, secondDir);
        db.put(VoXtCcglWjys.ITEM_CJSJ, cjsj);
        db.put(VoXtCcglWjys.ITEM_SCXGSJ, cjsj);
        db.put(VoXtCcglWjys.ITEM_BZ, "");
        db.put(VoXtCcglWjys.ITEM_YWBZ, ywbz);
        db.put(VoXtCcglWjys.ITEM_YSBH_PK, "");
        // ****** ������Ŀ�����������ֳ���������ݽ��� @20070125 by ada
        db.put(VoXtCcglWjys.ITEM_XM_FK, xm_fk);

        context.addRecord("wjys", db);
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        table.executeFunction(INSERT_FUNCTION, context, db, "wjys");

        log.debug("���渽�����ֵ��" + context.getRecord("wjys"));
        
        // ���ûش�ֵ
        DataBus result = new DataBus();
        // ���ظ���idֵ�͸���nameֵ
        result.put(FileConstant.file_id, context.getRecord("wjys")
                .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
        result.put(FileConstant.file_name, context.getRecord("wjys")
                .getValue(VoXtCcglWjys.ITEM_WJMC));

        // ���context�е��ļ�ӳ�������
        context.remove("wjys");
        return result;
    }

    /**
     * ���浥�����������ڷ��̨Action���׵��ö���������ʽ�ϴ�����
     * 
     * @param InputStream
     *            ���ϴ��ļ���������
     * @param oriFileName
     *            ���ļ�����
     * @param uploadFileName
     *            ���洢�ļ�������
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @param ejml
     *            ������Ŀ¼����
     * @param context :
     *            ���̨��������
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus saveFile(InputStream fileStream, String oriFileName,
            String uploadFileName, String ywbz, String ejml, TxnContext context)
                                                                                throws TxnException {
        return saveFile(fileStream, oriFileName, uploadFileName, ywbz, ejml,
                        "", context);
    }

    /**
     * ���浥�����������ڷ��̨Action���׵��ö���������ʽ�ϴ�����
     * 
     * @param InputStream
     *            ���ϴ��ļ���������
     * @param oriFileName
     *            ���ļ�����
     * @param uploadFileName
     *            ���洢�ļ�������
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @param ejml
     *            ������Ŀ¼����
     * @param xm_fk :
     *            ��Ŀ���� �����ֳ��������ݽ������޸�ʱ�䣺2007��01��25�գ�
     * @param context :
     *            ���̨��������
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus saveFile(InputStream fileStream, String oriFileName,
            String uploadFileName, String ywbz, String ejml, String xm_fk,
            TxnContext context) throws TxnException {
        String secondDir = getSecondDir(ejml);
        String cjsj = CalendarUtil.getCurrentDateTime();
        // ����ϴ�������ȫ·�����ļ����͵ĸ�Ŀ¼+����Ŀ¼+�ļ�����
        String prePath = root + FileConstant.PATH_SEPERATOR + secondDir;
        String filePath = prePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        // �����ļ�
        createFile(fileStream, filePath);
        
        
        /**
         * bug�޸ģ��޸ĸ���������Ӣ�Ķ���ʱ�ļ�����������
         * �޸��ˣ�������
         * �޸�ʱ�䣺2007-8-4
         */
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ�� "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ������"+oriFileName);
        
        // ��װ�������ݴ����ļ�ӳ���
        DataBus db = new DataBus();
        // �����������ݽڵ�
        db.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
        db.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        db.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        db.put(VoXtCcglWjys.ITEM_WJZT, FileConstant.status_inuse);
        db.put(VoXtCcglWjys.ITEM_CCLJ, secondDir);
        db.put(VoXtCcglWjys.ITEM_CJSJ, cjsj);
        db.put(VoXtCcglWjys.ITEM_SCXGSJ, cjsj);
        db.put(VoXtCcglWjys.ITEM_BZ, "");
        db.put(VoXtCcglWjys.ITEM_YWBZ, ywbz);
        db.put(VoXtCcglWjys.ITEM_YSBH_PK, "");
        // ****** ������Ŀ�����������ֳ���������ݽ��� @20070125 by ada
        db.put(VoXtCcglWjys.ITEM_XM_FK, xm_fk);
        
        context.addRecord("wjys", db);
        // �������ݿ��
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        table.executeFunction(INSERT_FUNCTION, context, db, "wjys");

        log.debug("���渽�����ֵ��" + context.getRecord("wjys"));
        // ���ظ�������idֵ�͸�������ֵ
        DataBus result = new DataBus();
        result.put(FileConstant.file_id, context.getRecord("wjys")
                .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
        result.put(FileConstant.file_name, context.getRecord("wjys")
                .getValue(VoXtCcglWjys.ITEM_WJMC));

        log.debug("�ش�����idֵ��"
                  + context.getRecord("wjys").getValue("ysbh_pk"));
        // ���context�е��ļ�ӳ�������
        context.remove("wjys");
        return result;
    }

    public DataBus updateFile(InputStream fileStream, String ysbh_pk,
            String oriFileName, String uploadFileName, String ywbz)
                                                                   throws TxnException {
        // �����޸�ʱ��
        String scxgsj = CalendarUtil.getCurrentDateTime();
        
        
        /**
         * bug�޸ģ��޸ĸ���������Ӣ�Ķ���ʱ�ļ�����������
         * �޸��ˣ�������
         * �޸�ʱ�䣺2007-8-4
         */
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ�� "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ������"+oriFileName);
        
        // ��װ������Ϣ
        TxnContext context = new TxnContext();
        DataBus indb = new DataBus();
        indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
        indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        indb.put(VoXtCcglWjys.ITEM_SCXGSJ, scxgsj);
        context.addRecord("wjys", indb);
        
        // ��ѯԭ������Ϣ,����ɾ��ԭ����
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);      
        try{
            // ��ȡ�ļ��洢ԭ�洢·����Ϣ
            table.executeFunction(SELECT_FUNCTION_PK, context, indb, "wjys");
        } catch (TxnDataException ex) {
            log.error(ex);
            log.error("����ӳ���¼Ϊ��!");
            /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
            }else{
                throw ex;
            }*/
            
        }
        // �����ļ�ӳ���¼��װ�����ڴ��̿ռ�Ķ����洢·��
        String cclj = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLJ);
        String wybs = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_WYBS);
        
        // ��������Ų�ѯ��Ŀ¼
        String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
        String rootPath = getRootPathByLbPk(lbpk,context);
        
        // ԭ�������·����Ϣ
        String oldprePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;
        
        String newprePath = root + FileConstant.PATH_SEPERATOR + cclj;

        // ɾ��ԭ����
        delFile(oldprePath + FileConstant.PATH_SEPERATOR + wybs);

        String filePath = newprePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        // �����¸���
        createFile(fileStream, filePath);

        indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        
        // ���¸���ӳ����Ϣ
        table.executeFunction(UPDATE_FILEWYBS_FUNCTION, context, indb, "wjys");
        
        // �ش�����id/nameֵ
        DataBus result = new DataBus();
        result.put(FileConstant.file_id, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
           result.put(FileConstant.file_name, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_WJMC));

        // ���context�е��ļ�ӳ�������
        context.remove("wjys");
        return result;
    }

    /**
     * ���¸�����ֻ�޸ĸ���������Ϣ�����ڷ��̨Action���׵��ö���������ʽ�ϴ���
     * 
     * @param ysbh_pk :
     *            ��������idֵ
     * @param oriFileName
     *            ���ļ�����
     * @param uploadFileName
     *            ���洢�ļ�������
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @param context :
     *            ���̨��������
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus updateFile(String ysbh_pk, String oriFileName,
            String uploadFileName, String ywbz, TxnContext context)
                                                                   throws TxnException {
        return updateFile(null, ysbh_pk, oriFileName, uploadFileName, ywbz,
                          context);
    }

    /**
     * ���¸�����ֻ�޸ĸ���������Ϣ�����ڷ��̨Action���׵��ö���������ʽ�ϴ���
     * 
     * @param fileStream :
     *            �ϴ��ļ���������
     * @param ysbh_pk :
     *            ��������idֵ
     * @param oriFileName
     *            ���ļ�����
     * @param uploadFileName
     *            ���洢�ļ�������
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @param context :
     *            ���̨��������
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus updateFile(InputStream fileStream, String ysbh_pk,
            String oriFileName, String uploadFileName, String ywbz,
            TxnContext context) throws TxnException {
        // �����޸�ʱ��
        String scxgsj = CalendarUtil.getCurrentDateTime();
        
        
        /**
         * bug�޸ģ��޸ĸ���������Ӣ�Ķ���ʱ�ļ�����������
         * �޸��ˣ�������
         * �޸�ʱ�䣺2007-8-4
         */
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ�� "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ������"+oriFileName);
        
        // ��װ������Ϣ
        DataBus indb = new DataBus();        
        indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
        if (uploadFileName != null && !"".equals(uploadFileName)) {
            indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        }
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        
        
        indb.put(VoXtCcglWjys.ITEM_SCXGSJ, scxgsj);
        context.addRecord("wjys", indb);
        
        // ��ѯԭ������Ϣ,����ɾ��ԭ����
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        try{
            // ��ȡ�ļ��洢ԭ�洢·����Ϣ
            table.executeFunction(SELECT_FUNCTION_PK, context, indb, "wjys");
        } catch (TxnDataException ex) {
            log.error(ex);
            log.error("����ӳ���¼Ϊ��!");
            /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                                            BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
            }else{
                throw ex;
            }*/
        }
        try {
            // ���޸Ĵ��ݵ��ļ�����Ϊ�գ�������ϴ�Ŀ¼��ԭ�ļ�
            // ���򲻸���Ŀ¼�µ��ļ�
            if (fileStream != null && (fileStream.read() != -1)) {
                // �����ļ�ӳ���¼��װ�����ڴ��̿ռ�Ķ����洢·��
                String cclj = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLJ);
                String wybs = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_WYBS);
                
                // ��������Ų�ѯ��Ŀ¼
                String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
                String rootPath = getRootPathByLbPk(lbpk,context);
                
                // ԭ�������·����Ϣ
                String oldprePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;
                
                String newprePath = root + FileConstant.PATH_SEPERATOR + cclj;
                
                
                // ɾ��ԭ����
                delFile(oldprePath + FileConstant.PATH_SEPERATOR + wybs);

                String filePath = newprePath + FileConstant.PATH_SEPERATOR + uploadFileName;
                // �����¸���
                createFile(fileStream, filePath);
            }
        } catch (IOException e) {
            log.error(e);
            log.error("�ļ����رմ���!");
            /*throw new TxnErrorException(
                                        ErrorConstant.FILE_CREATEPATH_ERROR,
                                        BJAISConfig.get("ggkz.ccgl.wjys.io.closeerror"));*/
        }
       
        // �޸��ļ�����
        String updateFunction = UPDATE_FILE_FUNCTION;
        
        // �޸��ļ�Ψһ��ʶ���ļ�����
        if (uploadFileName != null && !"".equals(uploadFileName)) {
            indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
            updateFunction = UPDATE_FILEWYBS_FUNCTION;
        }

        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        
        // ���¸���ӳ����Ϣ
        table.executeFunction(updateFunction , context, indb, "wjys");
        
        // �ش�����id/nameֵ
        DataBus result = new DataBus();
        result.put(FileConstant.file_id, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
           result.put(FileConstant.file_name, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_WJMC));

        // ���context�е��ļ�ӳ�������
        context.remove("wjys");
        return result;
    }

    /**
     * �޸ĵ�������
     * 
     * @param voFile
     *            �����̨�ϴ��ļ�VO����
     * @param ysbh_pk :
     *            ����ӳ������PKֵ
     * @return DataBus : ��������� ����ӳ������-FileConstant.file_id��������-FileConstant.file_name��
     *         �ֶ�����sys.FileConstantά��
     * @throws TxnException
     */
    public DataBus updateFile(VoFileInfo voFile, String ysbh_pk)
                                                                throws TxnException {
        if (voFile == null || voFile.getOriFileName().trim().length() == 0) {
            return null;
        }
        // ��ȡ�ϴ�������Ϣ
        String oriFileName = voFile.getOriFileName().trim();// ��������(ԭʼ�ļ���)
        String tempFileName = voFile.getValue(VoCtrl.ITEM_SVR_FILENAME);// ��ʱ�ļ�����
        String uploadFileName = tempFileName.substring(tempFileName
                .lastIndexOf(FileConstant.PATH_SEPERATOR) + 1);

        
        /**
         * bug�޸ģ��޸ĸ���������Ӣ�Ķ���ʱ�ļ�����������
         * �޸��ˣ�������
         * �޸�ʱ�䣺2007-8-4
         */
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ�� "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("�滻Ӣ�Ķ��š�Ӣ�ĵ����ſ�ʼ������"+oriFileName);
        
        String scxgsj = CalendarUtil.getCurrentDateTime(); // �����޸�ʱ��
        
        // ��װ������Ϣ
        TxnContext context = new TxnContext();
        DataBus indb = new DataBus();
        indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
        indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        indb.put(VoXtCcglWjys.ITEM_SCXGSJ, scxgsj);
        // ���ò�ѯ�ش�����
        context.addRecord("wjys", indb);

        // ��ѯԭ������Ϣ,����ɾ��ԭ����
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        try{
            // ��ȡ�ļ��洢ԭ�洢·����Ϣ 
            table.executeFunction(SELECT_FUNCTION_PK, context, indb, "wjys");
        } catch (TxnDataException ex) {
            log.error(ex);
            log.error("����ӳ���¼Ϊ��!");
            /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                                            BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
            }else{
                throw ex;
            }*/
        }
        // �����ļ�ӳ���¼��װ�����ڴ��̿ռ�Ķ����洢·��
        String cclj = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLJ);
        String wybs = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_WYBS);

        // ��������Ų�ѯ��Ŀ¼
        String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
        String rootPath = getRootPathByLbPk(lbpk,context);
        
        String oldprePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;
        String newprePath = root + FileConstant.PATH_SEPERATOR + cclj;
        
        // ɾ��ԭ����
        delFile(oldprePath + FileConstant.PATH_SEPERATOR + wybs);

        String filePath = newprePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        // �����¸���
        createFile(tempFileName, filePath);

        // ɾ����ʱ�ļ�
        delFile(tempFileName);

        indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        
        // ���¸���ӳ����Ϣ
        table.executeFunction(UPDATE_FILEWYBS_FUNCTION, context, indb, "wjys");

        log.debug("�޸ĸ������ֵ�� " + context.getRecord("wjys"));
        DataBus result = new DataBus();
        // �ش�����id/nameֵ
        result.put(FileConstant.file_id, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
           result.put(FileConstant.file_name, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_WJMC));

        // ���context�е��ļ�ӳ�������
        context.remove("wjys");
        return result;
    }

    /**
     * ɾ���������� ͬʱɾ���ļ�ϵͳ�д洢���ļ����ļ�ӳ����Ӧ�ֶ�
     * 
     * @param ysbh_pks
     *            ������ӳ������
     * @throws TxnException
     */
    public void deleteFile(String ysbh_pks) throws TxnException {
        TxnContext context = new TxnContext();
        
        // �жϸ���IDֵ�Ƿ�Ϊ��
        if(ysbh_pks == null || ysbh_pks.length()<=0){
        	log.debug("����ӳ���¼Ϊ��!");
        	/*throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
            BJAISConfig.get("ggkz.ccgl.wjys.select.ysidnullerror"));*/
        	
        }
            
        
        // ��ָ���ֵ�������ΪӢ�İ�Ƕ���
        String[] ysbhs = ysbh_pks.split(FileConstant.ID_SEPERATOR);
        log.debug("��ɾ����������ֵ��" + ysbh_pks);
        // ѭ��ɾ��
        for (int i = 0; i < ysbhs.length; i++) {           
            DataBus indb = new DataBus();
            log.debug("����ֵ :��" + ysbhs[i]);
            // ��������������ֵΪ��ת����һ������ֵ
            if (ysbhs[i] == null || ysbhs[i].length() <= 0) {
                continue;
            }

            // ��װ�ļ�ӳ���¼����
            indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbhs[i]);
            indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
            indb.put(VoXtCcglWjys.ITEM_WYBS, "");
            indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
            context.addRecord("wjys", indb);
            
            // ��ѯԭ������Ϣ,����ɾ��ԭ����
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, WJYS_TABLE);
            try{
                // ��ȡ�ļ��洢ԭ�洢·����Ϣ 
                table.executeFunction(SELECT_FUNCTION_PK, context, indb, "wjys");
            } catch (TxnDataException ex) {
                log.error(ex);
                log.debug("����ӳ���¼Ϊ��!");
                /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                    throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                                                BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
                }else{
                    throw ex;
                }*/
            }
            // ��װ�ļ��洢��ȫ·��
            String cclj = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLJ);
            String wybs = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_WYBS);

            // ��������Ų�ѯ��Ŀ¼
            String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
            String rootPath = getRootPathByLbPk(lbpk,context);
            
            String prePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;
            // ɾ������
            delFile(prePath + FileConstant.PATH_SEPERATOR + wybs);
            log.debug("ɾ������·��:  " + prePath + FileConstant.PATH_SEPERATOR + wybs);
            // ɾ��������¼
            table.executeFunction(DELETE_FUNCTION, context, indb, "wjys");

            // ���context�е��ļ�ӳ�������
            context.remove("wjys");
        }
    }

    /**
     * ��������ֵ��ȡ������������
     * @param fileID ��������ֵ
     * @return
     * @throws TxnException
     */
    public InputStream getRemoteStreamByFileID(String fileID)
                                                             throws TxnException {
        InputStream result = null;
        TxnContext context = new TxnContext();
        
        // ���ݸ���id���ȫ·��
        String path = getFullPathByFileID(fileID,context);
        
        // ����ȫ·����ø�����������
        result = getRemoteStreamByFilePath(path);
        
        return result;
    }
    
    public InputStream getRemoteStreamByFilePath(String fullPath)
    throws TxnException {
        
        InputStream result = null;
        // ����ȫ·�������ļ���
        try {
            result = new FileInputStream(new File(fullPath));
        } catch (FileNotFoundException e) {
            log.error("����ȫ·�������ļ�����"+e.fillInStackTrace());
            log.error("ͨ������ID�޷��ҵ���Ӧ�ĸ���ʵ�壬���ʵ������!");
            //throw new TxnErrorException(ErrorConstant.FILE_READERROR,BJAISConfig.get("ggkz.ccgl.wjys.io.copyfileerror"));
        }

        return result;
    }

    /**
     * ���ظ���ȫ·�� ����ʱ��:20070131 by ada
     * 
     * @param fileID :
     *            ����ID
     * @param context :
     *            ���̨���ݽṹ����
     * @return String : ����ȫ·��
     * @throws TxnException
     */
    public String getFullPathByFileID(String fileID, TxnContext context)
                                                                        throws TxnException {
        String path = null;

        DataBus indb = new DataBus();
        // ��װ�洢�ļ�ӳ��������ݿ����
        indb.put(VoXtCcglWjys.ITEM_YSBH_PK, fileID);
        indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, "");
        indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
        indb.put(VoXtCcglWjys.ITEM_WYBS, "");  
        context.addRecord("input", indb);
        context.addRecord("wjys", indb);
        
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        try{
            // ��ȡ�ļ��洢ԭ�洢·����Ϣ 
            table.executeFunction(SELECT_FUNCTION_PK, context, "input", "wjys");       
        } catch (TxnDataException ex) {
            log.error(ex);
            log.error("����ӳ���¼Ϊ��!");
            /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                                            BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
            }else{
                throw ex;
            }*/
        }
            DataBus db = context.getRecord("wjys");

        // �����ļ�ӳ���¼��װ�����ڴ��̿ռ�Ķ����洢·��
        String cclj = db.getValue(VoXtCcglWjys.ITEM_CCLJ);
        String wybs = db.getValue(VoXtCcglWjys.ITEM_WYBS);
        
        // ��������Ų�ѯ��Ŀ¼
        String lbpk = db.getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
        String rootPath = getRootPathByLbPk(lbpk,context);

        // �����ļ����ʹ洢��Ŀ¼�Ͷ����洢·����װ�ļ��洢ȫ·��
        path = rootPath.concat(FileConstant.PATH_SEPERATOR).concat(cclj).concat(FileConstant.PATH_SEPERATOR).concat(wybs);
        log.debug("����id: " + fileID + " �ļ�ȫ·�� ��" + path);
        
        

        // ���context�е��ļ�ӳ�������
        context.remove("input");
        context.remove("wjys");
        return path;
    }

    /**
     * �����ļ�������ƻ�ȡ�洢��Ŀ¼���ļ������pk
     * 
     * @param fileType
     * @throws TxnException
     */
    private void getRootPathByType(String fileType) throws TxnException {

        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJLB_TABLE);
        // �����ļ��������Ʋ�ѯ���°汾�ļ�����
        //String sql = SQLConfig.get("604050200-0001", fileType);
        String sql = "select xt_ccgl_wjlb.* from xt_ccgl_wjlb where cclbmc ='"+fileType+"' and lbmcbb= (select max(lbmcbb) from xt_ccgl_wjlb where cclbmc ="+fileType+"') ";
       
        DataBus db = new DataBus();
        table.executeSelect(sql, db, "wjlb");
        DataBus wjlb = db.getRecord("wjlb");
        
        // �жϲ�ѯ���°汾�ļ������Ƿ����
        if (wjlb == null || wjlb.isEmpty()){
        	log.debug("�ļ���𲻴���!");
        	/*throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
                    BJAISConfig.get(WJLBNULLERROR));*/
        }
            

        // �����ļ����͸�·��������id������Ŀ¼������Ϣ
        root = wjlb.getValue(VoXtCcglWjlb.ITEM_CCGML);
        cclbbh_pk = wjlb.getValue(VoXtCcglWjlb.ITEM_CCLBBH_PK);
        ejmlgz = wjlb.getValue(VoXtCcglWjlb.ITEM_EJMLGZ);
        
        log.debug("��Ŀ¼�� " + root);
        log.debug("�洢���������ֵ�� " + cclbbh_pk);
        log.debug("����Ŀ¼���� " + ejmlgz);
    }

    /*
     * ������������ ������ ��������� @param input ���������� @param output ��������� @throws
     * IOException
     */
    private void exchangeStream(InputStream input, OutputStream output)
                                                                       throws IOException {
        int readLen = 0;
        byte[] readBytes = new byte[8 * 1024];
        while ((readLen = input.read(readBytes)) > 0) {
            output.write(readBytes, 0, readLen);
        }
        output.flush();
    }

    /*
     * ���ļ�ϵͳ�д����ļ� @param srcFileName ���ļ�����Դȫ·���������ļ��� @param filePath
     * ���ļ�ϵͳ�д洢Ŀ��ȫ·���������ļ���
     */
    private void createFile(String srcFileName, String filePath) throws TxnErrorException {
        
        InputStream in = null; // ������
        OutputStream out = null; // �����
        try {
            log.debug(" �����ļ�ȫ·���� " + filePath);          
            // ����·��
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            in = new FileInputStream(srcFileName); // ������ΪԴ�ļ�
            out = new FileOutputStream(file); // �����Ϊ�������˴洢λ��

            // ������������
            exchangeStream(in, out);
            
        } catch (Exception ex) {
                log.error("�������Ĵ�������: "+ex);
                log.error("��������ʧ��!");
            //throw new TxnErrorException(ErrorConstant.ACTION_ADDCODE_ERROR,BJAISConfig.get("ggkz.ccgl.wjys.io.createerror"));
        } finally {
            try {
                // �ر�������
                if(in!=null)
                    in.close();
                if(out!=null)
                    out.close();
            } catch (IOException ioe) {
                log.error(ioe);
                log.debug("�ļ����رմ���!");
                /*throw new TxnErrorException(
                                            ErrorConstant.FILE_CREATEPATH_ERROR,
                                            BJAISConfig.get("ggkz.ccgl.wjys.io.closeerror"));*/
            }
        }
    }

    /*
     * ���ļ�ϵͳ�д����ļ� @param InputStream ���ļ�����Դ������������ @param filePath
     * ���ļ�ϵͳ�д洢Ŀ��ȫ·���������ļ���
     */
    private void createFile(InputStream in, String filePath) throws TxnErrorException {
        OutputStream out = null; // �����
        try {
            log.debug("���̴����ϴ��ļ�·���� " + filePath);
            // ����·��
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            
            out = new FileOutputStream(file); // �����Ϊ�������˴洢λ��
            
            // ������������
            exchangeStream(in, out);

        }catch (Exception ex) {
            log.error(ex);
            log.debug("�ļ�·��Ϊ��!");
            /*throw new TxnErrorException(ErrorConstant.FILE_CREATEPATH_ERROR,
                                        BJAISConfig.get("ggkz.ccgl.wjys.io.patherror"));*/
        } finally {
            try {
                // �ر�������
                if(in!=null)
                    in.close();
                if(out!=null)
                    out.close();
            } catch (IOException ioe) {
                log.error("�ر��ļ������� "+ioe);
                /*throw new TxnErrorException(
                                            ErrorConstant.FILE_CREATEPATH_ERROR,
                                            BJAISConfig.get("ggkz.ccgl.wjys.io.closeerror"));*/
            }
        }
    }

    /*
     * ɾ���ļ�ϵͳ�е��ļ� @param fullPath ���ļ����ļ�ϵͳ�е�ȫ·���������ļ��� @return
     */
    private boolean delFile(String fullPath) {
        boolean result = false;
        File file = new File(fullPath);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    /*
     * �����ļ��������õĶ���Ŀ¼�������ɶ���Ŀ¼ @param ejml ��ҵ��ģ�鴫�ݵĶ���Ŀ¼�� @return
     */
    private String getSecondDir(String ejml) {
        String secondDir = null;
        
        // �������Ŀ¼���ݲ��������ö���Ŀ¼Ϊ���ݵ�·��
        if((ejml != null && ejml.length() > 0)){
            secondDir = ejml;
        }
        // ���ⷽʽ����ҵ��ģ���ṩ����Ŀ¼
        else if ("0".equals(ejmlgz) && (ejml != null && ejml.length() > 0)) {
            secondDir = ejml;
        }
        // ϵͳʱ�䷽ʽ������ϵͳʱ�䴴������Ŀ¼ 
        else if ("1".equals(ejmlgz)) {
            secondDir = getDirByTime();
        }
        // Ĭ�϶���Ŀ¼�����ڳ���δ�������Ŀ¼ʱʹ�� 
        else {
            secondDir = DEFAULT_SECOND_DIR;
        }

        // �ڴ洢���̴�������Ŀ¼�ļ���
        createFolder(root + FileConstant.PATH_SEPERATOR + secondDir + FileConstant.PATH_SEPERATOR);
        log.debug("��������Ŀ¼��" + secondDir);
        return secondDir;
    }

    /*
     * ���ݶ���Ŀ¼����=���ڹ������ɶ���Ŀ¼����ʽΪ����/��/�ա��磺2006/2006-11/2006-11-20 @return
     */
    private String getDirByTime() {
        String year = CalendarUtil.getCurrYear();
        String month = CalendarUtil.getCurrMonth();
        String yyyymmdd = CalendarUtil
                .getCalendarByFormat(CalendarUtil.FORMAT11);
        String dir = year + FileConstant.PATH_SEPERATOR + year + "-" + month + FileConstant.PATH_SEPERATOR + yyyymmdd;

        return dir;
    }

    /*
     * ����Ŀ¼ @param fullPath
     */
    private void createFolder(String fullPath) {
        FileManager fileManager = FileManager.getInstance(root);
        fileManager.checkFolder(fullPath);
    }
    
    protected String getRootPathByLbPk(String cclbbh_pk, TxnContext context) throws TxnException{
        String rootPath;
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJLB_TABLE);
        // �����ļ���������ֵ��ѯ�ļ��洢��·��
       // String sql = SQLConfig.get("604050200-0003", cclbbh_pk);
        String sql = "select ccgml from xt_ccgl_wjlb where cclbbh_pk = '"+cclbbh_pk+"'";
        table.executeSelect(sql, context, "rootwjlb");
        
        DataBus wjlb = context.getRecord("rootwjlb");        
        // �жϲ�ѯ���°汾�ļ������Ƿ����
        if (wjlb == null || wjlb.isEmpty()){
        	
        	log.debug("�ļ����Ϊ��");
        	/*throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
                    BJAISConfig.get(WJLBNULLERROR));*/
        }
            

        // �����ļ����͸�·��������id������Ŀ¼������Ϣ
        rootPath = wjlb.getValue(VoXtCcglWjlb.ITEM_CCGML);
        context.remove("rootwjlb");     
        return rootPath;
    }

}
