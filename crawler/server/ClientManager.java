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
	private ServerCore core;
	private Map<String, String> ipToClientKey;
	private Map<String, LinkedList<Event>> clientKeyToJob;
	private String clientKeyLock = new String("0"); //ipPortToClientKey, clientKeyToJob
	
	public ClientManager(ServerCore core) {
		this.core = core;
		ipToClientKey = new HashMap<String, String>();
		clientKeyToJob = new HashMap<String, LinkedList<Event>>();
	}
	
	public void clientOnline(String ip) {
		clientOffline(ip);
		
		String clientKey;
		synchronized (clientKeyLock) {
			while (true) {
				long num = new Random().nextLong();
				clientKey = Long.toHexString(num);
				if (clientKeyToJob.containsKey(clientKey)) continue;
				
				ipToClientKey.put(ip, clientKey);
				clientKeyToJob.put(clientKey, new LinkedList<Event>());
				break;
			}
		}
		
		Event event = new Event();
		event.setEventType(EventType.Initialize).setClientKey(clientKey);
		event.setParameter("local", String.valueOf(Config.clientLocalWorker));
		event.setParameter("proxy", String.valueOf(Config.clientProxyWorker));
		event.setParameter("sender", String.valueOf(Config.clientSender));
		
		for (int i = 0; i < 5; ++i)
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
