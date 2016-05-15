package client;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;

import common.Core;
import common.Config;
import event.*;
import socket.BaseConnector;
import socket.EventReceiver;
import socket.EventSender;

import com.google.gson.*;

import web.Client;
import socket.EventSender;

public class ClientCore extends Core {
	private LinkedList<Event> jobQueue;
	private LinkedList<Thread> localWorkers;
	private LinkedList<Thread> proxyWorkers;
	private LinkedList<Thread> Senders;
	private String host;
	private int port;
	private String clientKey;
	
	public ClientCore(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	synchronized public void start() {
		clientKey = null;
		jobQueue = new LinkedList<Event>();
		
		new Thread() {
			@Override
			public void run() {
				if (clientKey == null) {
					//new EventSender(host, port, new Event().setEventType(EventType.ClientOnline)).send();
				}
				else return;
				try {
					Thread.sleep(100000);
				} catch (Exception e) {
				}
			}
		}.start();
	}
	
	private void initialize(Event event) {
		
		clientKey = event.getClientKey();
		System.out.println(clientKey);
	}
	
	@Override
	public void handleEvent(String ip, String json) {
/*		Event event = Event.fromJson(json);
		if (event.getEventType() != EventType.Initialize && clientKey == null) return;
		if (event.getEventType() == EventType.Initialize) {
			initialize(event);
		}*/
	}
}
