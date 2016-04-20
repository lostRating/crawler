package socket;

import java.net.Socket;
import common.Core;
import event.Event;

public class EventSender extends BaseConnection {
	Event event;
	
	public EventSender(Event event) {
		this.event = event;
	}
	
	@Override
	protected void transaction() throws Exception {
		this.sendString(event.toJson());
	}
}
