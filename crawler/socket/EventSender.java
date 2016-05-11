package socket;

import java.net.Socket;

import common.Config;
import common.Core;
import event.Event;

public class EventSender extends BaseConnection {
	private String ip;
	private int port;
	public Event event;
	
	public EventSender(String ip, int port, Event event) {
		this.ip = ip;
		this.port = port;
		this.event = event;
	}
	
	@Override
	protected void transaction() throws Exception {
		String str = event.toJson();
		int size = Config.eventPackageSize;
		int cnt = (str.length() - 1) / size + 1;
		this.sendInt(cnt);
		for (int i = 0; i < cnt; ++i) {
			int l = i * size;
			int r = Math.min(l + size, str.length());
			this.sendString(str.substring(l, r));
		}
	}
	
	public void send() {
		for (int i = 0; i < Config.eventSenderRetryTimes; ++i) {
			try {
				new BaseConnector(ip, port, this, Config.eventSenderDefaultTimeout, 1).start();
				return;
			} catch (Exception e) {
				try {
					Thread.sleep(Config.eventSenderFailWaitTime);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}