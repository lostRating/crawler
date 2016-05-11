package server;

import socket.SocketListener;
import common.Config;

public class ServerEntry {
	static public void main(String args[]) {
		new SocketListener(Config.serverPort, new ServerCore()).start();
	}
}
