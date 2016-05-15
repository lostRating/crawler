package wiki;

import java.sql.ResultSet;
import java.util.ArrayList;

import common.Config;
import server.ServerCore;
import database.DbConnector;
import database.Table;
import event.Task;

public class WikiTask extends Task {
	ServerCore core;
	
	public WikiTask(ServerCore core) {
		this.core = core;
		serverInit();
	}

	@Override
	public void serverInit() {
		try {
			DbConnector db = new DbConnector().setIp(Config.databaseIp).setDatabase("Wiki").setUserName(Config.databaseUserName).setPassword(Config.databasePassword);
			//db.ensure();
			db.init();
			
			Table wiki = new Table("WIKI");
			wiki.addSchema("logId", "INT").setAutoInc();
			wiki.addSchema("url", "VARCHAR(255)").setUnique();
			wiki.addSchema("data", "vARCHAR(255)");
			wiki.addSchema("status", "VARCHAR(255)").setIndex();
			
			db.createTable(wiki);
			db.setTable("WIKI").set("url", "https://en.wikipedia.org/wiki/Wiki").set("data", "").insert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
