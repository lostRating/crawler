package work;

import common.Config;

public class Worker extends Thread {
	WorkManager workManager;
	
	public Worker(WorkManager workerManager) {
		this.workManager = workerManager;
	}
	
	@Override
	public void run() {
		while (true) {
			Work work = workManager.getWork();
			if (work == null) continue;
			try {
				work.execute();
			} catch (Exception e) {
				e.printStackTrace();
				if (++work.failCount < Config.workRetryTimes)
					workManager.addWork(work);
			}
		}
	}
}
