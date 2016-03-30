package entry;

import socket.BaseConnector;

public class ClientEntry {
	public static void main(String[] args) {
		for (int i = 0; i < 1; i++)
			new BaseConnector("127.0.0.1", 8014, "socket.StrSender", 1000, 0).start();
	}
}
