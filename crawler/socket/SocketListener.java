package socket;

import java.net.ServerSocket;
import java.net.Socket;
import common.Core;

public class SocketListener extends Thread {
	private int port;
	private Core core;
	
	public SocketListener(int port, Core core) {
		this.port = port;
		this.core = core;
	}
	
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		while (true) {
			try {
				serverSocket = new ServerSocket(port);
				while (true) {
					Socket socket = serverSocket.accept();
					new EventReceiver(socket, core).start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (serverSocket != null)
						serverSocket.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
