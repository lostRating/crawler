package entry;

import socket.BaseSocketListener;

public class ServerEntry {
	public static void main(String[] args) {
		new BaseSocketListener(8014, "socket.StrReceiver").start();
	}
}
