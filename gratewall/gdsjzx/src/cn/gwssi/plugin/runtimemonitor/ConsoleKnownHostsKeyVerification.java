package cn.gwssi.plugin.runtimemonitor;

import java.io.File;
import java.io.IOException;

import com.sshtools.j2ssh.transport.AbstractKnownHostsKeyVerification;
import com.sshtools.j2ssh.transport.InvalidHostFileException;
import com.sshtools.j2ssh.transport.publickey.SshPublicKey;

/**
 * 功能：模仿j2ssh-core-0.2.9.jar包中AbstractKnownHostsKeyVerification类，
 * 修改其中getResponse方法，去掉从用户输入流中获取接受主机公匙代码，默认接受。
 * <p>
 * 备注：初次连接某远程主机时，需要用户输入Yes\No\Always来是否接受主机公匙，
 * <p>
 * 此程序在后台运行不与用户交互，因此修改此类默认接受远程主机公匙。
 * SshClientTools类中连接主机时SshClient.connect(String hostname, int port,
 * HostKeyVerification hosts)，hosts参数使用此类的对象
 * 
 * @author xxx
 * 
 */
public class ConsoleKnownHostsKeyVerification extends
		AbstractKnownHostsKeyVerification {
	/**
	 * <p>
	 * Constructs the verification instance with the default known_hosts file
	 * from $HOME/.ssh/known_hosts.
	 * </p>
	 * 
	 * @throws InvalidHostFileException
	 *             if the known_hosts file is invalid.
	 * 
	 * @since 0.2.0
	 */
	public ConsoleKnownHostsKeyVerification() throws InvalidHostFileException {
		super(new File(System.getProperty("user.home"), ".ssh" + File.separator
				+ "known_hosts").getAbsolutePath());
	}

	/**
	 * <p>
	 * Constructs the verification instance with the specified known_hosts file.
	 * </p>
	 * 
	 * @param knownhosts
	 *            the path to the known_hosts file
	 * 
	 * @throws InvalidHostFileException
	 *             if the known_hosts file is invalid.
	 * 
	 * @since 0.2.0
	 */
	public ConsoleKnownHostsKeyVerification(String knownhosts)
			throws InvalidHostFileException {
		super(knownhosts);
	}

	/**
	 * <p>
	 * Prompts the user through the console to verify the host key.
	 * </p>
	 * 
	 * @param host
	 *            the name of the host
	 * @param pk
	 *            the current public key of the host
	 * @param actual
	 *            the actual public key supplied by the host
	 * 
	 * @since 0.2.0
	 */
	public void onHostKeyMismatch(String host, SshPublicKey pk,
			SshPublicKey actual) {
		try {
			System.out.println("The host key supplied by " + host + " is: "
					+ actual.getFingerprint());
			System.out.println("The current allowed key for " + host + " is: "
					+ pk.getFingerprint());
			getResponse(host, pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Prompts the user through the console to verify the host key.
	 * </p>
	 * 
	 * @param host
	 *            the name of the host
	 * @param pk
	 *            the public key supplied by the host
	 * 
	 * @since 0.2.0
	 */
	public void onUnknownHost(String host, SshPublicKey pk) {
		try {
			System.out.println("The host " + host
					+ " is currently unknown to the system");
			System.out.println("The host key fingerprint is: "
					+ pk.getFingerprint());
			getResponse(host, pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户输入的信息，判断是否接受主机公匙
	 * <p>
	 * 修改：xxx ，去掉从流中获取信息，直接接受公匙，注释掉的代码为源码
	 * 
	 * @param host
	 *            主机ip
	 * @param pk
	 *            主机公匙
	 * @throws InvalidHostFileException
	 * @throws IOException
	 */
	private void getResponse(String host, SshPublicKey pk)
			throws InvalidHostFileException, IOException {
		if (isHostFileWriteable()) {
			allowHost(host, pk, true);
		}
		/**
		 * String response = ""; BufferedReader reader = new BufferedReader(new
		 * InputStreamReader( System.in));
		 * 
		 * while (!(response.equalsIgnoreCase("YES") ||
		 * response.equalsIgnoreCase("NO") ||
		 * (response.equalsIgnoreCase("ALWAYS") && isHostFileWriteable()))) {
		 * String options = (isHostFileWriteable() ? "Yes|No|Always" :
		 * "Yes|No");
		 * 
		 * if (!isHostFileWriteable()) { System.out.println(
		 * "Always option disabled, host file is not writeable"); }
		 * 
		 * System.out.print("Do you want to allow this host key? [" + options +
		 * "]: "); response = reader.readLine(); }
		 * 
		 * if (response.equalsIgnoreCase("YES")) { allowHost(host, pk, false); }
		 * 
		 * if (response.equalsIgnoreCase("NO")) {
		 * System.out.println("Cannot continue without a valid host key");
		 * System.exit(1); }
		 * 
		 * if (response.equalsIgnoreCase("ALWAYS") && isHostFileWriteable()) {
		 * allowHost(host, pk, true); }
		 */
	}
}
