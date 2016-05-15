package common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WorkManager {
	Lock lock = new ReentrantLock();
	Semaphore semp = new Semaphore(0);
	Map<String, LinkedList<Work>> workMap = new HashMap<String, LinkedList<Work>>();
	
	public WorkManager(int workerSize) {
		for (int i = 0; i < workerSize; ++i)
			new Worker(this).start();
	}
	
	synchronized Work getWork() {
		try {
			semp.acquire();
			
			LinkedList<Work> workQueue = null;
			int sum = 0;
			synchronized (workMap) {
				for (LinkedList<Work> works : workMap.values()) {
					sum += works.size();
					if (sum == 0) continue;
					if (new Random().nextInt(sum) < works.size())
						workQueue = works;
				}
			}
			return workQueue.pollFirst();
		} catch (Exception e) {
			e.printStackTrace();
			semp.release();
		}
		return null;
	}
	
	public void addWork(Work work) {
		String workType = work.getType();
		if (workMap.containsKey(workType)) {
			workMap.get(workType).add(work);
		} else synchronized (workMap) {
			if (!workMap.containsKey(workType)) {
				LinkedList<Work> workQueue = new LinkedList<Work>();
				workQueue.add(work);
				workMap.put(workType, workQueue);
			} else {
				workMap.get(workType).add(work);
			}
		}
		
		semp.release();
	}
}
