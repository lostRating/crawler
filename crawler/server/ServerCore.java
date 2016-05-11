package server;

import java.util.LinkedList;

import socket.EventSender;
import socket.Sender;

import com.google.gson.Gson;

import common.Config;
import common.GsonUtils;
import common.Core;
import event.Event;
import event.EventType;

public class ServerCore extends Core {
	private Manager manager;
	LinkedList<EventSender> eventSenders;
	LinkedList<Event> jobQueue;
	
	public ServerCore() {
		manager = new Manager(this);
		manager.start();
		eventSenders = new LinkedList<EventSender>();
		
		for (int i = 0; i < Config.serverSender; ++i)
			new Sender(this).start();
	}
	
	public void addEventSender(EventSender eventSender) {
		eventSenders.add(eventSender);
	}
	
	synchronized public EventSender fetchEventSender() {
		return eventSenders.pollFirst();
	}
	
	public int restJobQueueSize() {
		return Math.max(0, 20 - jobQueue.size());
	}
	
	public void addJobs(LinkedList<Event> jobs) {
		
	}
	
	@Override
	public void handleEvent(String ip, String json) {
		Event event = Event.fromJson(json);
		if (event.getHandler() != null) {
			
		} else if (event.getEventType() == EventType.ClientOnline) {
			manager.clientOnline(ip);
		} else if (event.getEventType() == EventType.ClientOffline) {
			manager.clientOffline(ip);
		}
	}
}
