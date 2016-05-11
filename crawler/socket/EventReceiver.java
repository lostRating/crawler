package socket;

import java.net.Socket;
import common.Core;
import com.google.gson.Gson;

public class EventReceiver extends BaseConnection {
	private Core core;
	
	public EventReceiver(Socket socket, Core core) {
		this.setSocket(socket);
		this.core = core;
	}
	
	@Override
	protected void transaction() throws Exception {
		String ip = socket.getInetAddress().getHostAddress();
		int port = socket.getPort(); 
				
		String str = "";
		int cnt = this.readInt();
		for (int i = 0; i < cnt; ++i)
			str += this.readString();
		core.handleEvent(ip, str);
	}
}
