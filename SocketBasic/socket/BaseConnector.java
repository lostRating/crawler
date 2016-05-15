package socket;

import java.net.InetSocketAddress;
import java.net.Socket;

import work.WorkFailException;

public class BaseConnector extends Thread {
	private String host;
	private int port;
	private int timeout;
	private BaseConnection conn;

	public BaseConnector(String host, int port, BaseConnection conn, int timeout) {
		this.host = host;
		this.port = port;
		this.conn = conn;
		this.timeout = timeout;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setConnection(BaseConnection con) {
		this.conn = conn;
	}

	private Socket socket = null;

	@Override
	public void run() {
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(host, port), timeout);
			//System.out.println("Connected "+socket.getLocalPort()+" "+socket.getPort());
			conn.setSocket(socket);
			conn.run();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WorkFailException("socket.BaseConnector.run");
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
