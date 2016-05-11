package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DbConnector {
	Connection getUrl() {
		try {
			Connection conn = DriverManager.getConnection(
					DbConfig.getUrl(),
					DbConfig.userName,
					DbConfig.password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Connection getDb() {
		try {
			Connection conn = DriverManager.getConnection(
					DbConfig.getDb(),
					DbConfig.userName,
					DbConfig.password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DbConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean init() throws SQLException {
		clear();
		Connection conn = getUrl();
		Statement stmt = conn.createStatement();
		return stmt.execute("CREATE DATABASE " + DbConfig.dbName);
	}
	
	public boolean clear() throws SQLException {
		Connection con = getUrl();
		Statement stmt = con.createStatement();
		return stmt.execute("DROP DATABASE IF EXISTS " + DbConfig.dbName);
	}
	
	public boolean excuteSQL(String sql) throws SQLException {
		Connection con = getDb();
		Statement stmt = con.createStatement();
		return stmt.execute(sql);
	}
	
	public boolean insert(String table, ArrayList<String> key, ArrayList<String> value) throws SQLException {
		String sql = "insert into " + table + "(";
		for (int i = 0; i < key.size(); ++i) {
			String s = key.get(i);
			sql += s;
			if (i + 1 < key.size()) sql += ",";
			else sql += ")";
		}
		sql += " value(";
		for (int i = 0; i < value.size(); ++i) {
			String s = "?";
			sql += s;
			if (i + 1 < value.size()) sql += ",";
			else sql += ")";
		}
		sql += ";";
		PreparedStatement stmt = getDb().prepareStatement(sql);
		for (int i = 0; i < value.size(); ++i) {
			stmt.setString(i + 1, value.get(i));
		}
		return stmt.execute();
	}
	
	static public void main(String args[]) {
		try {
			DbConnector db = new DbConnector();
			db.init();
			Table wiki = new Table("WIKI");
			wiki.addSchema("logId", "INT").setAutoInc();
			wiki.addSchema("url", "VARCHAR(255)").setUnique();
			wiki.addSchema("data", "VARCHAR(8000)");
			wiki.addSchema("status", "VARCHAR(255)").setIndex();
			db.excuteSQL(wiki.getCreatTableStatement());
			ArrayList<String> key = new ArrayList<String>();
			ArrayList<String> value = new ArrayList<String>();
			key.add("url"); key.add("data"); key.add("status");
			value.add("1"); value.add("2"); value.add("3");
			db.insert("WIKI", key, value);
			key = new ArrayList<String>();
			value = new ArrayList<String>();
			key.add("url"); key.add("data"); key.add("status");
			value.add("2"); value.add("2"); value.add("3");
			db.insert("WIKI", key, value);
			ResultSet res = db.getDb().createStatement().executeQuery("SELECT * FROM WIKI WHERE status = 3 order BY logId limit 1");
			while (res.next()) {
				System.out.println("logId = " + res.getLong(1));
				System.out.println("url = " + res.getString(2));
				System.out.println("data = " + res.getString(3));
				System.out.println("status = " + res.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
