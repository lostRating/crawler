package work;

import common.Core;

public class EventHandleWork extends Work {
	String ip;
	String json;
	Core core;
	
	public EventHandleWork(String ip, String json, Core core) {
		super("EventHandle");
		this.ip = ip;
		this.json = json;
		this.core = core;
	}

	@Override
	public void execute() throws WorkFailException {
		core.handleEvent(ip, json);
	}
}
