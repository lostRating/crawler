package client;

import common.Core;
import event.*;
import com.google.gson.*;

public class ClientCore extends Core {

	@Override
	public void receiveEvent(String json) throws Exception {
		Event event = new Gson().fromJson(json, Event.class);
		if (event.event.equals("Job")) {
			Job job = new Gson().fromJson(json, Job.class);
			System.out.println(job.jobId);
		}
	}
}
