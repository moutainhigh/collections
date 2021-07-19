package cn.gwssi.plugin.runtimemonitor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.session.PseudoTerminal;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.InvalidHostFileException;

/**
 * 通过SSH协议连接远程服务器，并且发送命令获取执行结果。
 * <p>
 * 备注：此类引用j2ssh-core-0.2.9.jar包连接远程服务器，并且发送命令获取执行结果，
 * <p>
 * 执行结果已经特殊处理，不适合所有命令， 默认获取echo命令的执行结果，若有其它需求，请自添加代码。
 * 
 * @author xxx
 * 
 */
public class SshClientTools {
	private String hostip = null;
	private int port;
	private String userName = null;
	private String password = null;
	private SshClient ssh = null;
	private SessionChannelClient session = null;

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setHostip(String hostip) {
		this.hostip = hostip;
	}

	public String getHostip() {
		return hostip;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * 构造函数，初始化远程主机ip，端口，用户名，密码信息。
	 * 
	 * @param host
	 *            主机ip地址
	 * @param port
	 *            ssh协议的端口号（如22）
	 * @param userName
	 *            用户名
	 * @param passwd
	 *            密码
	 * @throws Exception
	 */
	public SshClientTools(String host, int port, String userName, String passwd)
			throws Exception {
		if (host == null || host.length() == 0)
			throw new Exception("lose the host.");
		if (userName == null || userName.length() == 0)
			throw new Exception("lose the LoginName.");
		if (passwd == null || passwd.length() == 0)
			throw new Exception("lose the password.");

		this.setHostip(host);
		this.setPort(port);
		this.setUserName(userName);
		this.setPassword(passwd);
		this.ssh = new SshClient();
	}

	/**
	 * 连接主机
	 * 
	 * @author xxx
	 * @return true or false
	 * @throws Exception
	 */
	private boolean connect() throws Exception {
		if (ssh == null)
			throw new Exception("SshClient is null.");

		PasswordAuthenticationClient authentication = new PasswordAuthenticationClient();
		authentication.setUsername(this.userName);
		authentication.setPassword(this.password);
		ConsoleKnownHostsKeyVerification console;
		try {
			// console对象默认选择Always接受host key
			console = new ConsoleKnownHostsKeyVerification();
			ssh.connect(this.hostip, 22, console);
			if (ssh.authenticate(authentication) == AuthenticationProtocolState.COMPLETE)
				return true;
			else
				return false;
		} catch (InvalidHostFileException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功能：发送命令之后，从流中读取信息获取命令的执行结果。
	 * <p>
	 * 本函数设计只发送echo命令，若发送执行结果为多行的命令将无法获取正确的执行结果，如：ls、 top等。
	 * 
	 * <p>
	 * 以下到“备注”是登录信息举例：为能够在注释中显示，在@和\前添加了转移符
	 * <p>
	 * Last login: Mon Jan 30 02:12:27 2012
	 * <p>
	 * echo $PS1
	 * <p>
	 * exit
	 * <p>
	 * [root\@localhost ~]# echo $PS1
	 * <p>
	 * [\\u@\\h \\W]\\$
	 * <p>
	 * [root\@localhost ~]# exit
	 * <p>
	 * logout
	 * <p>
	 * 备注：因为流中包含上次登录信息、上次执行的命令和本次执行的命令及结果，
	 * <p>
	 * 在获取本次命令的执行结果时，按行读取流中所有信息存入集合，然后取集合中的数据 （ 当集合
	 * >=6时，取集合size()-3位置的数据），因为本函数默认会发送exit命令退出登录，所以集合中必须至少有6行结果（两条命令和两条执行结果，
	 * 登录提示信息和空行，另外可能还包含上次登录时执行的命令），如果集合大小小于6认为读取失败将返回null。
	 * 
	 * @author xxx
	 * @param strCommand
	 *            echo命令，通过echo来查询需要的信息。
	 * @return 返回执行结果或null
	 * @throws Exception
	 */
	public String executeCommand(SessionChannelClient session,String strCommand) throws Exception {
		ArrayList<String> array_result = new ArrayList<String>();
		if (session.requestPseudoTerminal("dumb", PseudoTerminal.ECHO, 0, 0, 0,
				null)) {
			if (session.startShell()) {
				// 发送命令
				OutputStream writer = session.getOutputStream();
				writer.write(strCommand.getBytes());
				writer.write("\n".getBytes());
				writer.flush();
				// 发送退出命令
				writer.write("exit\n".getBytes());
				writer.flush();
				// 读取流中信息
				BufferedReader in = new BufferedReader(new InputStreamReader(
						session.getInputStream()));
				BufferedReader err = new BufferedReader(new InputStreamReader(
						session.getStderrInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					array_result.add(line);
				}

				while ((line = err.readLine()) != null) {
				}
			}
		}
		if (array_result.size() >= 6)

			return array_result.toString();
		else
			return null;
	}

	public static void main(String args[]) {
		try {
			SshClientTools user = new SshClientTools("10.1.1.136", 22, "dsadm",
					"Ds21Adm05");
			user.connect();
			user.openSession();
			SessionChannelClient session=user.openSession();
			String retult=user.getDisk();
			System.out.println(retult);
			user.close(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private SessionChannelClient  openSession() throws Exception{
		if (ssh == null)
			throw new Exception("SshClient is null.");
		if (!ssh.isConnected()) {
			this.connect();
		}
		if (session==null){
			return 	session = ssh.openSessionChannel();
		}
		return session;
		
	}


	void close(SessionChannelClient session) throws IOException{
		if (session != null) {
			session.close();
		}
		if (ssh != null)
			ssh.disconnect();
	}

	
	public String getDisk() throws Exception {
		String result=this.executeCommand(session, " df ");
		return result;
	}
	
}


