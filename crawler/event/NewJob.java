package event;

public class NewJob extends Event {
	public NewJob() {
		this.event = "NewJob";
	}
	public String taskName;
	public String jobId;
	public String url;
	public String cookie; //"local"
	public String proxy;
}
