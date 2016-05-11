package client;

import socket.SocketListener;
import common.Config;

public class ClientEntry {
	static public void main(String args[]) {
		ClientCore core = new ClientCore(Config.host, Config.serverPort);
		new SocketListener(Config.clientPort, core).start();
		core.start();
	}
}
