package common;

public abstract class Work {
	String workType;
	int failCount = 0;
	
	public Work(String workType) {
		this.workType = workType;
	}
	
	public String getType() {
		return workType;
	}
	
	public abstract void execute() throws WorkFailException;
}
