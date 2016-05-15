package socket;

import java.net.Socket;
import common.Core;
import com.google.gson.Gson;

public class EventReceiver extends BaseConnection {
	Core core;
	
	public EventReceiver(Socket socket, Core core) {
		this.setSocket(socket);
		this.core = core;
	}
	
	@Override
	protected void transaction() throws Exception {
		try {
			String ip = socket.getInetAddress().getHostAddress();
			
			String str = "";
			int cnt = this.readInt();
			for (int i = 0; i < cnt; ++i)
				str += this.readString();

			core.receiveEvent(ip, str);
			
			this.sendString("ACK");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
