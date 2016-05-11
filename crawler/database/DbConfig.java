package database;

public class DbConfig {
	static public String ip = "139.196.152.71";
	static public String userName = "user";
	static public String password = "123qwe";
	static public String dbName = "WIKI";
	
	static public String getUrl() {
		return "jdbc:mysql://" + ip;
	}
	
	static public String getDb() {
		return "jdbc:mysql://" + ip + "/" + dbName;
	}
}
