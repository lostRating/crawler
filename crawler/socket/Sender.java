package socket;

import common.Core;

public class Sender extends Thread {
	private Core core;
	
	public Sender(Core core) {
		this.core = core;
	}
	
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			EventSender eventSender = core.fetchEventSender();
			if (eventSender == null) {
				try {
					sleep(1000);
				} catch (Exception e) {
				}
				continue;
			}
			eventSender.send();
		}
	}
}
