package common;

import work.EventHandleWork;
import work.WorkManager;

public abstract class Core {
	protected WorkManager workManager;
	
	final public void receiveEvent(String ip, String json) {
		workManager.addWork(new EventHandleWork(ip, json, this));
	}
	
	public abstract void handleEvent(String ip, String json);
}
