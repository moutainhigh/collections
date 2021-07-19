package cn.gwssi.etlschedule;

import ch.ethz.ssh2.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/** */

/**
 * Զ��ִ��shell�ű��� 
 * @author l 
 */ 
public class RmtShellExecutor { 

	/** *//**  */ 
	private Connection conn; 
	/** *//** Զ�̻���IP */ 
	private String     ip; 
	/** *//** �û��� */ 
	private String     usr; 
	/** *//** ���� */ 
	private String     psword; 
	private String     charset = Charset.defaultCharset().toString(); 

	private static final int TIME_OUT = 1000 * 5 * 60; 

	public static int CONNCNT=0;
	public static final int MAXCONN=20;

	//private Session session;

	/** *//** 
	 * ���캯�� 
	 * @param ip 
	 * @param usr 
	 * @param ps 
	 */ 
	public RmtShellExecutor(String ip, String usr, String ps) { 
		this.ip = ip; 
		this.usr = usr; 
		this.psword = ps; 
		try {
			login();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	/** *//** 
	 * ��¼ 
	 * 
	 * @return 
	 * @throws IOException 
	 */ 
	private boolean login() throws IOException { 
		conn = new Connection(ip); 
		conn.connect(); 
		return conn.authenticateWithPassword(usr, psword); 
	} 

	/** *//** 
	 * ִ�нű� 
	 * 
	 * @param cmds 
	 * @return 
	 * @throws Exception 
	 */ 
	public List exec(String cmds) { 
		InputStream stdOut = null; 
		InputStream stdErr = null; 
		String outStr = ""; 
		String outErr = ""; 
		List processList = null;
		String line =null;
		int ret = -1; 
		// Open a new {@link Session} on this connection 
		Session session=null;
		try {
			session = getSession();
			if(session==null)return processList;	
			session.execCommand(cmds);

			stdOut = new StreamGobbler(session.getStdout()); 
			//outStr = processStream(stdOut, charset); 

			//stdErr = new StreamGobbler(session.getStderr()); 
			//outErr = processStream(stdErr, charset); 

			session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdOut));
			processList=new ArrayList();
			while ((line = reader.readLine()) != null) { 
				processList.add(line);
			}
			/*System.out.println("outStr=" + outStr); 
	        System.out.println("outErr=" + outErr); */

			ret = session.getExitStatus().intValue(); 
			if(session!=null)session.close();
			if(stdOut != null)stdOut.close();
			//if(stdErr != null)stdErr.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally{
			if(session!=null)session.close();
			if(stdOut != null)
				try {
					stdOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		// Execute a command on the remote machine. 




		return processList; 
	} 

	private Session getSession(){
		Session session=null;
		try {
			session=conn.openSession();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return session;
	}

	/** *//** 
	 * @param in 
	 * @param charset 
	 * @return 
	 * @throws IOException 
	 * @throws
	 */ 
	private String processStream(InputStream in, String charset) throws Exception { 
		byte[] buf = new byte[2048]; 
		StringBuilder sb = new StringBuilder(); 
		while (in.read(buf) != -1) { 
			sb.append(new String(buf, charset)); 
		} 
		return sb.toString(); 
	} 

	public void fileUpload(String file) throws Exception{
		try { 
			if (login()) { 
				// Open a new {@link Session} on this connection 
				SCPClient client=new SCPClient(conn);
				client.put(file, "/data/sjzx/param");

			} else { 
				throw new Exception("��¼Զ�̻���ʧ��" + ip); // �Զ����쳣�� ʵ���� 
			} 
		} finally { 
			if (conn != null) { 
				conn.close(); 
			}
		}
	}

	public void logOff(){

		if(conn != null)conn.close();
	}
} 