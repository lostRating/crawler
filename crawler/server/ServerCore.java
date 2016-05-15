package server;

import java.util.LinkedList;

import socket.EventReceiver;
import socket.EventSender;
import work.EventHandleWork;
import work.EventSendWork;
import work.WorkManager;

import com.google.gson.Gson;

import common.Config;
import common.GsonUtils;
import common.Core;
import event.Event;
import event.EventType;

public class ServerCore extends Core {
	ClientManager clientManager;
	LinkedList<EventSender> eventSenders;
	LinkedList<Event> jobQueue;
	
	public ServerCore() {
		clientManager = new ClientManager(this);
		workManager = new WorkManager(Config.serverWorkerSize);
	}
	
	public void addEventSender(EventSender eventSender) {
		workManager.addWork(new EventSendWork(eventSender));
	}
	
	/*public void addJobs(LinkedList<Event> jobs) {
		
	}*/
	
	@Override
	public void handleEvent(String ip, String json) {
	/*	Event event = Event.fromJson(json);
		if (event.getHandler() != null) {
			
		} else if (event.getEventType() == EventType.ClientOnline) {
			clientManager.clientOnline(ip);
		} else if (event.getEventType() == EventType.ClientOffline) {
			clientManager.clientOffline(ip);
		}*/
	}
}
