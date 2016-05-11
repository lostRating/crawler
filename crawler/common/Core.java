package common;

import socket.EventSender;

public abstract class Core {
	public abstract void handleEvent(String ip, String json);

	public abstract EventSender fetchEventSender();
}
