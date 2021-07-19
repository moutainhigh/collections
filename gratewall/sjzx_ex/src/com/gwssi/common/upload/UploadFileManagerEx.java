package com.gwssi.common.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
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

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.upload.*;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.file.manage.vo.VoXtCcglWjlb;
import com.gwssi.file.manage.vo.VoXtCcglWjys;

public class UploadFileManagerEx
{
	
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
            .getLogger(UploadFileManagerEx.class.getName());

    private String root;

    private String cclbbh_pk;

    private String ejmlgz;

    private String DEFAULT_SECOND_DIR = "default";
	

    /**
     * ҵ��ģ���ϴ��ļ��ӿڹ��캯��
     * 
     * @param fileType --
     *            �ļ����
     * @throws TxnException
     */
    public UploadFileManagerEx(String fileType) throws TxnException {
        getRootPathByType(fileType);
    }

    /**
     * ���浥������
     * 
     * @param voFile
     *            �����̨�ϴ��ļ�VO����
     * @return DataBus : ��������� ����ӳ������-Constants.file_id��������-Constants.file_name��
     *         �ֶ�����sys.Constantsά��
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, TxnContext context)
            throws TxnException {

        return saveFile(voFile, "", context);
    }

    /**
     * ���浥������
     * 
     * @param voFile
     *            �����̨�ϴ��ļ�VO����
     * @param ywbz :
     *            ҵ��ע�����ڴ洢����ҵ��ģ������ֵ
     * @return DataBus : ��������� ����ӳ������-Constants.file_id��������-Constants.file_name��
     *         �ֶ�����sys.Constantsά��
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, TxnContext context)
            throws TxnException {
        return saveFile(voFile, ywbz, null, context);
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
     * @return DataBus : ��������� ����ӳ������-Constants.file_id��������-Constants.file_name��
     *         �ֶ�����sys.Constantsά��
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, String ejml,
            TxnContext context) throws TxnException {
        return saveFile(voFile, ywbz, ejml, "", context);
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
     * @return DataBus : ��������� ����ӳ������-Constants.file_id��������-Constants.file_name��
     *         �ֶ�����sys.Constantsά��
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, String ejml,
            String xm_fk, TxnContext context) throws TxnException {
        if (voFile == null || voFile.getOriFileName().trim().length() == 0) {
            return null;
        }

        // ��ȡ�ϴ�������Ϣ
        String oriFileName = voFile.getOriFileName().trim();// ��������(ԭʼ�ļ���)
        String tempFileName = voFile.getValue(VoCtrl.ITEM_SVR_FILENAME);// ��ʱ�ļ�����
        String uploadFileName="";
        
        
        if(tempFileName.indexOf("\\")!=-1){
        	//log.debug("�ļ�·����ʾ����\\");
        	uploadFileName = tempFileName.substring(tempFileName
                    .lastIndexOf(FileConstant.PATH_SEPERATOR1) + 1);
        }else{
        	//log.debug("�ļ�·����ʾ����/");
        	uploadFileName = tempFileName.substring(tempFileName
                    .lastIndexOf(FileConstant.PATH_SEPERATOR) + 1);
        }
       /* String uploadFileName = tempFileName.substring(tempFileName
                .lastIndexOf(FileConstant.PATH_SEPERATOR1) + 1);*/
        
        
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
        String cjsj = CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT14);
        // ����ϴ�������ȫ·�����ļ����͵ĸ�Ŀ¼+����Ŀ¼+�ļ�����
        
        String prePath = root + FileConstant.PATH_SEPERATOR + secondDir;
        String filePath = prePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        log.info("�ϴ��ļ��������ļ���:"+filePath);
        DataBus result = new DataBus();
        // �����ļ�
        createFile(tempFileName, filePath);
        // ɾ����ʱ�ļ�
        delFile(tempFileName);
        try {
            DataBus db = new DataBus();
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

            BaseTable filetable = TableFactory.getInstance().getTableObject(
                    this, WJYS_TABLE);
            filetable.executeFunction(INSERT_FUNCTION, context, db, "wjys");

            // ���ûش�ֵ
            result.put(FileConstant.file_id, context.getRecord("wjys").getValue(
                    VoXtCcglWjys.ITEM_YSBH_PK));
            result.put(FileConstant.file_name, context.getRecord("wjys").getValue(
                    VoXtCcglWjys.ITEM_WJMC));
        } catch (Exception e) {
            log.error(e);
            log.debug("�ļ��������!");
            //throw new TxnErrorException(ErrorConstant.SQL_EXECUTE_ERROR,
              //      BJAISConfig.get("ggkz.ccgl.wjys.add.saveerror"));
        }
        // ȥ����ʱ���
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
    public void deleteFile(String ysbh_pks, TxnContext context)
            throws TxnException {
        // �жϸ���IDֵ�Ƿ�Ϊ��
        if (ysbh_pks == null || ysbh_pks.length() <= 0){
        	log.debug("����ӳ������ֵΪ��!");
        	/*throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
                    BJAISConfig.get("ggkz.ccgl.wjys.select.ysidnullerror"));*/
        	
        }
            

        // �����ݵĸ���ӳ������
        String[] ysbhs = null;
        if (ysbh_pks.indexOf(FileConstant.ID_SEPERATOR) != -1) {
            ysbhs = ysbh_pks.split(FileConstant.ID_SEPERATOR);
        } else if (ysbh_pks.indexOf(";") != -1) {
            ysbhs = ysbh_pks.split(";");
        } else {
            ysbhs = new String[1];
            ysbhs[0] = ysbh_pks.trim();
        }

        log.debug("��ɾ����������ֵ��" + ysbh_pks);
        // ѭ��ɾ�����и���
        for (int i = 0; i < ysbhs.length; i++) {
            // ��������������ֵΪ��ת����һ������ֵ
            if (ysbhs[i] == null || ysbhs[i].length() <= 0) {
                continue;
            }

            // ��װ�ļ�ӳ���¼����
            DataBus indb = new DataBus();
            indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbhs[i]);
            indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
            indb.put(VoXtCcglWjys.ITEM_WYBS, "");
            indb.put(VoXtCcglWjys.ITEM_CCLJ, "");

            context.addRecord("wjys", indb);

            BaseTable filetable = TableFactory.getInstance().getTableObject(
                    this, WJYS_TABLE);
            try {
                // ��ѯ������¼������װ�����洢·����ɾ�������ϵ��ļ�
                filetable.executeFunction(SELECT_FUNCTION_PK, context, indb,
                        "wjys");

            } catch (TxnDataException ex) {
                log.error(ex);
                log.debug("����ӳ���¼Ϊ��!");
               /* if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                    throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                            BJAISConfig
                                    .get("ggkz.ccgl.wjys.select.ysnullerror"));
                } else {
                    throw ex;
                }*/
            }
            // ��װ�ļ��洢��ȫ·��
            String cclj = context.getRecord("wjys").getValue(
            		VoXtCcglWjys.ITEM_CCLJ);
            String wybs = context.getRecord("wjys").getValue(
            		VoXtCcglWjys.ITEM_WYBS);
            
            // ��������Ų�ѯ��Ŀ¼
            String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
            //String rootPath = getRootPathByLbPk(lbpk,context);
            String rootPath = getrootPathBycclb(lbpk);
            
            String prePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;

            // ɾ�������ϴ洢���ļ�
            delFile(prePath + FileConstant.PATH_SEPERATOR + wybs);
            log.debug("ɾ������·��:  " + prePath + FileConstant.PATH_SEPERATOR + wybs);

            // ɾ���������ļ�ӳ���¼
            filetable.executeFunction(DELETE_FUNCTION, context, indb, "wjys");

            context.remove("wjys");
        }
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
    
    /**
     * �����ļ�������ƻ�ȡ�洢��Ŀ¼���ļ������pk
     * 
     * @param fileType
     * @throws TxnException
     */
    private void getRootPathByType(String fileType) throws TxnException {
        BaseTable filetable = TableFactory.getInstance().getTableObject(this,
                WJLB_TABLE);
//        log.info("fileType = " + fileType);
        // �ж��ļ������Ƿ�Ϊ��
        if (fileType == null || fileType.length() == 0){
        	log.debug("�ļ��������Ϊ��!");
        	
        	//throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
              //      BJAISConfig.get("ggkz.ccgl.wjys.select.lbnamenullerror"));
        }
         /*root�޸�20130927-dwn*/   

       // �����ļ��������Ʋ�ѯ���°汾�ļ�����
        /*String sql = "select xt_ccgl_wjlb.* from xt_ccgl_wjlb where cclbmc ='"+fileType+"' and lbmcbb= (select max(lbmcbb) from xt_ccgl_wjlb where cclbmc ='"+fileType+"') ";
        //String sql = SQLConfig.get("604050200-0001", fileType);
        log.debug("�������ݿ��ѯ���: " + sql);
        DataBus db = new DataBus();
        filetable.executeSelect(sql, db, "wjlb");
        DataBus wjlb = db.getRecord("wjlb");

        // �жϲ�ѯ���°汾�ļ������Ƿ����
        if (wjlb == null || wjlb.isEmpty()){
        	log.debug("�ļ���𲻴���!");
        	//throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
            //      BJAISConfig.get("ggkz.ccgl.wjys.select.wjlbnullerror"));
        }
            

        // �����ļ����͸�·��������id������Ŀ¼������Ϣ
        root = wjlb.getValue(VoXtCcglWjlb.ITEM_CCGML);
        cclbbh_pk = wjlb.getValue(VoXtCcglWjlb.ITEM_CCLBBH_PK);
        ejmlgz = wjlb.getValue(VoXtCcglWjlb.ITEM_EJMLGZ);*/
        
        root = getRootPath(fileType);
        log.info("�ļ�·��Ϊ:"+root);
        cclbbh_pk = getcclbString(fileType);
        log.info("�ļ����ͱ���Ϊ:"+cclbbh_pk);
        ejmlgz = "1";

    }
    
    /**
     * 
     * getRootPath(�����ļ����ͻ�ȡ��Ŀ¼��Ϣ)    
     * TODO(����������������������� �C ��ѡ)    
     * TODO(�����������������ִ������ �C ��ѡ)    
     * TODO(�����������������ʹ�÷��� �C ��ѡ)    
     * TODO(�����������������ע������ �C ��ѡ)    
     * @param fileType
     * @return        
     * String       
     * @Exception �쳣����    
     * @since  CodingExample��Ver(���뷶���鿴) 1.1
     */
    private String getRootPath(String fileType){
    	String rootPath = "";
    	
    	if("file_upload".equals(fileType)){
    		rootPath = ExConstant.FILE_UPLOAD;
    	}else if ("collect".equals(fileType)){
    		rootPath = ExConstant.COLLECT_RECORD;
    	}else if("share".equals(fileType)){
    		rootPath = ExConstant.SHARE_RECORD;
    	}else if("report".equals(fileType)){
    		rootPath = ExConstant.REPORT;
    	}else if("collect_xml".equals(fileType)){
    		rootPath = ExConstant.COLLECT_XML;
    	}else if("share_xml".equals(fileType)){
    		rootPath = ExConstant.SHARE_XML;
    	}else if("res_tbl".equals(fileType)){
    		rootPath = ExConstant.RES_TBL_RECORD;
    	}else if("file_ftp".equals(fileType)){
    		rootPath = ExConstant.FILE_FTP;
    	}else if("file_database".equals(fileType)){
    		rootPath = ExConstant.FILE_DATABASE;
    	}else if("share_config".equals(fileType)){
    		rootPath = ExConstant.SHARE_CONFIG;
    	}else if("service_target".equals(fileType)){
    		rootPath = ExConstant.SERVICE_TARGET;
    	}else {
    		rootPath = "";
    	}
    	
    	return rootPath;
    }
    
    /**
     * 
     * getcclbString(�����ļ����ͻ�ȡ�洢������)    
     * TODO(����������������������� �C ��ѡ)    
     * TODO(�����������������ִ������ �C ��ѡ)    
     * TODO(�����������������ʹ�÷��� �C ��ѡ)    
     * TODO(�����������������ע������ �C ��ѡ)    
     * @param fileType
     * @return        
     * String       
     * @Exception �쳣����    
     * @since  CodingExample��Ver(���뷶���鿴) 1.1
     */
    private String getcclbString(String fileType){
    	String cclb = "";//�洢������
    	
    	if("file_upload".equals(fileType)){
    		cclb = "07";
    	}else if ("collect".equals(fileType)){
    		cclb = "02";
    	}else if("share".equals(fileType)){
    		cclb = "01";
    	}else if("report".equals(fileType)){
    		cclb = "03";
    	}else if("collect_xml".equals(fileType)){
    		cclb = "05";
    	}else if("share_xml".equals(fileType)){
    		cclb = "04";
    	}else if("res_tbl".equals(fileType)){
    		cclb = "06";
    	}else if("file_ftp".equals(fileType)){
    		cclb = "08";
    	}else if("file_database".equals(fileType)){
    		cclb = "10";
    	}else if("share_config".equals(fileType)){
    		cclb = "09";
    	}else if("service_target".equals(fileType)){
    		cclb = "12";
    	}else {
    		cclb = "";
    	}
    	
    	return cclb;
    }
    
    /**
     * 
     * getrootPathBycclb(���ݴ洢����ȡ��Ŀ¼ add by dwn 20140218)    
     * TODO(����������������������� �C ��ѡ)    
     * TODO(�����������������ִ������ �C ��ѡ)    
     * TODO(�����������������ʹ�÷��� �C ��ѡ)    
     * TODO(�����������������ע������ �C ��ѡ)    
     * @param cclb
     * @return        
     * String       
     * @Exception �쳣����    
     * @since  CodingExample��Ver(���뷶���鿴) 1.1
     */
    private String getrootPathBycclb(String cclb){
    	
    	String rootPath = "";
    	
    	if("07".equals(cclb)){
    		rootPath = ExConstant.FILE_UPLOAD;
    	}else if ("02".equals(cclb)){
    		rootPath = ExConstant.COLLECT_RECORD;
    	}else if("01".equals(cclb)){
    		rootPath = ExConstant.SHARE_RECORD;
    	}else if("03".equals(cclb)){
    		rootPath = ExConstant.REPORT;
    	}else if("05".equals(cclb)){
    		rootPath = ExConstant.COLLECT_XML;
    	}else if("04".equals(cclb)){
    		rootPath = ExConstant.SHARE_XML;
    	}else if("06".equals(cclb)){
    		rootPath = ExConstant.RES_TBL_RECORD;
    	}else if("08".equals(cclb)){
    		rootPath = ExConstant.FILE_FTP;
    	}else if("10".equals(cclb)){
    		rootPath = ExConstant.FILE_DATABASE;
    	}else if("09".equals(cclb)){
    		rootPath = ExConstant.SHARE_CONFIG;
    	}else if("12".equals(cclb)){
    		rootPath = ExConstant.SERVICE_TARGET;
    	}else {
    		rootPath = "";
    	}
    	
    	return rootPath;
    }
    
    
    /*
     * ���ļ�ϵͳ�д����ļ� @param srcFileName ���ļ�����Դȫ·���������ļ��� @param filePath
     * ���ļ�ϵͳ�д洢Ŀ��ȫ·���������ļ���
     */
    private void createFile(String srcFileName, String filePath)
            throws TxnErrorException {
        InputStream in = null; // ������
        OutputStream out = null; // �����

        try {
            log.debug("�ļ�����·��: " + filePath);
            if (filePath == null || filePath.length() <= 0){
            	log.debug("�ļ�·��Ϊ��!");
            	/*throw new TxnErrorException(
                        ErrorConstant.FILE_CREATEPATH_ERROR, BJAISConfig
                                .get("ggkz.ccgl.wjys.io.patherror"));*/
            }
                

            // ����·��
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // ������ΪԴ�ļ�
            in = new FileInputStream(srcFileName);
            // �����Ϊ�������˴洢λ��
            out = new FileOutputStream(file);

            // ������������
            exchangeStream(in, out);

        } catch (Exception ex) {
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
                log.error(ioe);
                log.debug("�ļ����رմ���!");
                /*throw new TxnErrorException(
                        ErrorConstant.FILE_CREATEPATH_ERROR, BJAISConfig
                                .get("ggkz.ccgl.wjys.io.closeerror"));*/
            }
        }
    }

    /*
     * ���ļ�ϵͳ�д����ļ� @param InputStream ���ļ�����Դ������������ @param filePath
     * ���ļ�ϵͳ�д洢Ŀ��ȫ·���������ļ���
     */
    public void createFile(InputStream in, String filePath)
            throws TxnErrorException {
        OutputStream out = null; // �����
        try {
            log.debug("�ļ�����·�� : " + filePath);
            if (filePath == null || filePath.length() <= 0){
            	log.debug("�ļ�·��Ϊ��!");
            	/*throw new TxnErrorException(
                        ErrorConstant.FILE_CREATEPATH_ERROR, BJAISConfig
                                .get("ggkz.ccgl.wjys.io.patherror"));*/
            }
                

            // ����·��
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // �����Ϊ�������˴洢λ��
            out = new FileOutputStream(file);
            // ������������
            exchangeStream(in, out);

        } catch (Exception ex) {
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
                log.error(ioe);
                log.debug("�ļ����رմ���!");
                /*throw new TxnErrorException(
                        ErrorConstant.FILE_CREATEPATH_ERROR, BJAISConfig
                                .get("ggkz.ccgl.wjys.io.closeerror"));*/
            }
        }
    }

    // �����ļ��洢·����ȡ�����
    public InputStream getOutputStream(String path) {
        InputStream result = null;
        try {
            result = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            log.error(e);
        }
        return result;
    }

    public String getRootDir() {
        return root;
    }

    public String getEjmlgz() {
        return ejmlgz;
    }

    public String getCclbbhPk() {
        return cclbbh_pk;
    }

    /*
     * ɾ���ļ�ϵͳ�е��ļ� @param fullPath ���ļ����ļ�ϵͳ�е�ȫ·���������ļ��� @return
     */
    public boolean delFile(String fullPath) {
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
    public String getSecondDir(String ejml) {
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
        createFolder(root + FileConstant.PATH_SEPERATOR + secondDir
                + FileConstant.PATH_SEPERATOR);
        log.debug("����Ŀ¼��" + secondDir);
        return secondDir;
    }

    /** �����ļ��洢ģ����ļ�ӳ������ֵ����������
     * @param fjid
     *            ����ID
     * @param filePath
     *            �ļ����·��
     * @param type
     *            �ļ�����
     * @return boolean
     * @author yangxf ����:copy��Ӧ�������ļ�ʵ�嵽ָ����Ŀ¼��
     */
    public boolean copyFileWithFjID(String fjid, String filePath, String type)
            throws TxnException {
        return copyFileWithFjID(fjid, null, filePath, type);
    }
    
    /** �����ļ�ȫ·������������
     * @param fullPath
     *            Դ�ļ�ȫ·��
     * @param filePath
     *            �ļ����·��
     * @param type
     *            �ļ�����
     * @return boolean
     * @author yangxf ����:copy��Ӧ�������ļ�ʵ�嵽ָ����Ŀ¼��
     */
    public boolean copyFileWithPath(String fullPath, String filePath, String type)
    throws TxnException {
        return copyFileWithFjID(null, fullPath, filePath, type);
    }
    
    private boolean copyFileWithFjID( String fjid, String orifullPath, String filePath, String type)
    throws TxnException {

        UploadFileManager uploadFileManager = null;
        FileInputStream stream = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        String str_fileFullPath = filePath.substring(0, filePath
                .lastIndexOf("/") + 1);
        log.info("ͨ������IDִ���ļ�copy�������ļ�copy��ȫ·��Ϊ:" + str_fileFullPath);

        // ����Ŀ¼
        FileManager.getInstance(filePath).checkFolder(str_fileFullPath);

        File file = new File(filePath);
        try {
            uploadFileManager = UploadFileManager.getInstance(type);           
            // ���ļ�������
            // ���Դ�ļ�ȫ·���������ݣ���ֱ�Ӹ���·������ļ���
            if(orifullPath!=null && orifullPath.length()>0){
                stream = (FileInputStream) uploadFileManager.getRemoteStreamByFilePath(orifullPath);
            }else{
            // ���Դ�ļ��ļ��洢id�������ݣ������idȡ·������ļ���
                stream = (FileInputStream) uploadFileManager
                    .getRemoteStreamByFileID(fjid);
            }
            log.info("��ʼ���ļ���!" + stream.toString());

            // д�ļ���
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);// ����һ���ϴ��ļ��������
            int i_bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((i_bytesRead = stream.read(buffer, 0, 1024)) > 0) {
                bos.write(buffer, 0, i_bytesRead);// ���ļ�д�������
            }
            bos.flush();
        } catch (Exception e) {           
            log.error("ͨ������ID�޷��ҵ���Ӧ�ĸ���ʵ�壬���ʵ������ "+e.fillInStackTrace());
            //throw new TxnErrorException(ErrorConstant.FILE_NOTEXIST,BJAISConfig.get("ggkz.ccgl.wjys.io.copyfileerror"));           
        
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (bos != null)
                    bos.close();
                if (stream != null)
                    stream.close();
            } catch (IOException ioe) {
                log.error("�ر��ļ�������"+ioe.fillInStackTrace());
                //throw new TxnErrorException(ErrorConstant.FILE_CLOSE_ERROR,BJAISConfig.get("ggkz.ccgl.wjys.io.closeerror"));           
                
            }
        }
        return true;
    }

    /*
     * ���ݶ���Ŀ¼����=���ڹ������ɶ���Ŀ¼ ��ʽΪ����/��/�ա��磺2006/2006-11/2006-11-20 @return
     */
    private String getDirByTime() {
        String year = CalendarUtil.getCurrYear();
        String month = CalendarUtil.getCurrMonth();
        String yyyymmdd = CalendarUtil
                .getCalendarByFormat(CalendarUtil.FORMAT11);
        String dir = year + FileConstant.PATH_SEPERATOR + year + "-" + month
                + FileConstant.PATH_SEPERATOR + yyyymmdd;

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
        String sql = "select ccgml from xt_ccgl_wjlb where cclbbh_pk = '"+cclbbh_pk+"' ";
        //String sql = SQLConfig.get("604050200-0003", cclbbh_pk);
        table.executeSelect(sql, context, "rootwjlb");
        
        DataBus wjlb = context.getRecord("rootwjlb");        
        // �жϲ�ѯ���°汾�ļ������Ƿ����
        if (wjlb == null || wjlb.isEmpty())
        	log.debug("�ļ���𲻴���!");
            //throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
            							//"�ļ���𲻴���!");
                                        //BJAISConfig.get(WJLBNULLERROR));

        // �����ļ����͸�·��������id������Ŀ¼������Ϣ
        rootPath = wjlb.getValue(VoXtCcglWjlb.ITEM_CCGML);
        context.remove("rootwjlb");     
        return rootPath;
    }
    
    
}
