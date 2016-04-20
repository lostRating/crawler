package client;

import socket.SocketListener;

public class ClientEntry {
	static public void main(String args[]) {
		new SocketListener(8081, new ClientCore()).start();
	}
}
