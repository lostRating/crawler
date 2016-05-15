package work;

import socket.EventSender;

public class EventSendWork extends Work {
	EventSender eventSender;
	
	public EventSendWork(EventSender eventSender) {
		super("EventSend");
		this.eventSender = eventSender;
	}

	@Override
	public void execute() throws Exception {
		eventSender.send();
	}
}
