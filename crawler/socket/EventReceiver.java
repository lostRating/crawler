package socket;

import java.net.Socket;
import common.Core;
import com.google.gson.Gson;
import common.Event;

public class EventReceiver extends BaseConnection {
	private Core core;
	
	public EventReceiver(Socket socket, Core core) {
		this.setSocket(socket);
		this.core = core;
	}
	
	@Override
	protected void transaction() throws Exception {
		// TODO Auto-generated method stub
		Event event = new Gson().fromJson(this.readString(), Event.class);
		//core.handleJson(event);
	}

}