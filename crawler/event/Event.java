package event;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;

import common.GsonUtils;

public class Event {
	public class SessionInfo {
		public String taskName;
		public EventType eventType;
		public String clientKey;
	}
	
	SessionInfo sessionInfo;
	Map<String, String> parameters;
	String handler;
	
	public Event() {
		sessionInfo = new SessionInfo();
		parameters = new HashMap<String, String>();
		handler = "";
	}
	
	public Event setTaskName(String taskName) {
		this.sessionInfo.taskName = taskName;
		return this;
	}
	
	public String getTaskName() {
		return this.sessionInfo.taskName;
	}
	
	public Event setEventType(EventType eventType) {
		this.sessionInfo.eventType = eventType;
		return this;
	}
	
	public EventType getEventType() {
		return this.sessionInfo.eventType;
	}
	
	public Event setClientKey(String clientKey) {
		this.sessionInfo.clientKey = clientKey;
		return this;
	}
	
	public String getClientKey() {
		return this.sessionInfo.clientKey;
	}
	
	public Event setParameter(String key, String value) {
		parameters.put(key, value);
		return this;
	}
	
	public String getParameter(String key) {
		String ret = null;
		if (parameters.containsKey(key))
			ret = parameters.get(key);
		return ret;
	}
	
	public Event setHandler(String handler) {
		this.handler = handler;
		return this;
	}
	
	public String getHandler() {
		if ("".equals(handler)) return null;
		return handler;
	}
	
	public String toJson() {
		return GsonUtils.getGson().toJson(this);
	}
	
	static public Event fromJson(String json) {
		return GsonUtils.getGson().fromJson(json, Event.class);
	}
}
