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
	
	public class DbOperation {
		String tableName;
		ArrayList<String> keys;
		ArrayList<String> ops;
		ArrayList<String> vals;
		Integer limit;
		
		public DbOperation(String tableName) {
			this.tableName = tableName;
			keys = new ArrayList<String>();
			ops = new ArrayList<String>();
			vals = new ArrayList<String>();
			limit = null;
		}
	
		public DbOperation set(String key, String val) {
			keys.add(key);
			ops.add("=");
			vals.add(val);
			return this;
		}
		
		public DbOperation set(String key, String op, String val) {
			keys.add(key);
			ops.add(op);
			vals.add(val);
			return this;
		}
		
		public DbOperation top(int limit) {
			this.limit = new Integer(limit);
			return this;
		}
		
		public void insert() throws SQLException {
			String sql = "insert into " + tableName + "(";
			for (int i = 0; i < keys.size(); ++i) {
				String s = keys.get(i);
				sql += s;
				if (i + 1 < keys.size()) sql += ",";
				else sql += ")";
			}
			sql += " value(";
			for (int i = 0; i < vals.size(); ++i) {
				String s = "?";
				sql += s;
				if (i + 1 < vals.size()) sql += ",";
				else sql += ")";
			}
			sql += ";";
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			for (int i = 0; i < vals.size(); ++i) {
				stmt.setString(i + 1, vals.get(i));
			}
			stmt.execute();
		}
		
		public ResultSet select() throws SQLException {
			String sql = "select * from " + tableName;
			if (keys.size() > 0)
				sql += " where";
			for (int i = 0; i < keys.size(); ++i) {
				if (i > 0)
					sql += " and";
				sql += " " + keys.get(i) + " = ?";
			}
			if (limit != null)
				sql += " limit " + limit.intValue();
			System.out.println(sql);
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			for (int i = 0; i < vals.size(); ++i) {
				stmt.setString(i + 1, vals.get(i));
			}
			return stmt.executeQuery();
		}
	}
	
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
	
	public Connection getConnection() {
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
	
	public void init() throws SQLException {
		clear();
		executeSQL("CREATE DATABASE " + DbConfig.dbName);
	}
	
	public void clear() throws SQLException {
		executeSQL("DROP DATABASE IF EXISTS " + DbConfig.dbName);
	}
	
	public void executeSQL(String sql) throws SQLException {
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		stmt.execute(sql);
	}
	
	public DbOperation setTable(String tableName) {
		return new DbOperation(tableName);
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
			db.executeSQL(wiki.getCreateTableStatement());
			
			db.setTable("WIKI").set("url", "1").set("data", "2").set("status", "3").insert();
			db.setTable("WIKI").set("url", "4").set("data", "5").set("status", "6").insert();
			ResultSet res;
			res = db.setTable("WIKI").set("url", "1").top(1).select();
			//res = db.getDb().createStatement().executeQuery("SELECT * FROM WIKI WHERE status = 3 order BY logId limit 1");
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
