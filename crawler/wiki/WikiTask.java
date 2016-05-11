package wiki;

import java.sql.ResultSet;
import java.util.ArrayList;

import server.ServerCore;
import database.DbConnector;
import database.Table;
import event.Task;

public class WikiTask extends Task {
	private ServerCore core;
	private DbConnector db;
	
	public WikiTask(ServerCore core) {
		this.core = core;
		db = new DbConnector();
	}

	@Override
	public void init() {
		try {
			db.init();
			Table wiki = new Table("WIKI");
			wiki.addSchema("logId", "INT").setAutoInc();
			wiki.addSchema("url", "VARCHAR(255)").setUnique();
			wiki.addSchema("data", "INT");
			wiki.addSchema("status", "VARCHAR(255)").setIndex();
			db.excuteSQL(wiki.getCreatTableStatement());
			ArrayList<String> key = new ArrayList<String>();
			ArrayList<String> value = new ArrayList<String>();
			key.add("url"); key.add("data"); key.add("status");
			value.add("https://en.wikipedia.org/wiki/Wiki"); value.add(""); value.add("pending");
			db.insert("WIKI", key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (true) {
			int cnt = core.restJobQueueSize();
			try {
				ResultSet res = db.getDb().createStatement().executeQuery("select * from WIKI where status = pending order by logId limit = " + String.valueOf(cnt));
				while (res.next()) {
					db.getDb().createStatement().execute("update WIKI");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
