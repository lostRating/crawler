package server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import socket.BaseConnector;
import socket.EventSender;
import common.Config;
import event.Event;
import event.EventType;

public class ClientManager {
	ServerCore core;
	Map<String, Long> lastTime;
	
	public ClientManager(ServerCore core) {
		this.core = core;
		lastTime = new HashMap<String, Long>();
	}
	
	public void clientOnline(String ip) {
		clientOffline(ip);
		
		String clientKey;
		synchronized (clientKeyLock) {
			while (true) {
				clientKey = Long.toHexString(new Random().nextLong());
				if (clientKeyToJob.containsKey(clientKey)) continue;
				
				ipToClientKey.put(ip, clientKey);
				clientKeyToJob.put(clientKey, new LinkedList<Event>());
				break;
			}
		}
		
		Event event = new Event().setEventType(EventType.Initialize).setClientKey(clientKey);
		
		core.addEventSender(new EventSender(ip, Config.clientPort, event));
	}
	
	public void clientOffline(String ip) {
		String clientKey;
		synchronized (clientKeyLock) {
			if (ipToClientKey.containsKey(ip)) {
				clientKey = ipToClientKey.get(ip);
				core.addJobs(clientKeyToJob.get(ip));
				clientKeyToJob.remove(clientKey);
				ipToClientKey.remove(ip);
			}
		}
	}
}
