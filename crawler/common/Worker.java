package common;

public class Worker extends Thread {
	WorkManager workManager;
	
	public Worker(WorkManager workerManager) {
		this.workManager = workerManager;
	}
	
	@Override
	public void run() {
		while (true) {
			Work work = workManager.getWork();
			try {
				work.execute();
			} catch (WorkFailException e) {
				e.printStackTrace();
				if (++work.failCount < Config.workRetryTimes)
					workManager.addWork(work);
			}
		}
	}
}
