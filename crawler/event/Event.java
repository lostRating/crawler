package event;

import com.google.gson.*;

public class Event {
	public String event = "";
	public String phase = "Send";
	public String status = "";
	public String content = "";
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public void setBackFailContent(String s) {
		this.phase = "Back";
		this.status = "Fail";
		this.content = s;
	}
}
