package event;

public class NewTask extends Event {
	public NewTask() {
		this.event = "NewTask";
	}
	public String taskName;
	public boolean needLogin;
	public boolean bind;
}
