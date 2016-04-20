package server;

import socket.BaseConnector;
import socket.EventSender;
import event.*;

public class ServerEntry {
	static public void main(String args[]) {
		Job event = new Job();
		event.event = "Job";
		event.jobId = "321";
		new BaseConnector("127.0.0.1", 8081, new EventSender(event), 1000, 1).start();
	}
}
