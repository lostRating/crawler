package work;

import java.sql.SQLException;

import database.DbConnector;

public class DatabaseWriteWork extends Work {
	DbConnector db;
	String sql;
	
	public DatabaseWriteWork(DbConnector db, String sql) {
		super("DatabaseWrite");
		this.db = db;
		this.sql = sql;
	}

	@Override
	public void execute() throws WorkFailException, SQLException {
		db.executeSQL(sql);
	}
}
