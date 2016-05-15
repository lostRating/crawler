package database;
/*
 * Define table class
 */

import java.util.ArrayList;
import java.util.HashSet;

public class Table {
	private String tableName;
	
	public class Schema {
		String columnName, type;
		boolean isPrimaryKey = false;
		boolean isIndex = false;
		boolean isUnique = false;
		boolean autoInc = false;
		
		Schema(String columnName, String type) {
			this.columnName = columnName;
			this.type = type;
		}
		
		Schema setPrimaryKey() {
			isPrimaryKey = true;
			return this;
		}
		
		public Schema setIndex() {
			isIndex = true;
			return this;
		}
		
		public Schema setUnique() {
			isUnique = true;
			return this;
		}
		
		public Schema setAutoInc() {
			autoInc = true;
			return this;
		}
	};
	
	ArrayList<Schema> schemas = new ArrayList<Schema>();
	
	public Table(String tableName) {
		this.tableName = tableName;
	}
	
	public Schema addSchema(String name, String type) {
		Schema sch = new Schema(name, type);
		sch.isPrimaryKey = false;
		sch.isIndex = false;
		schemas.add(sch);
		return sch;
	}
	
	public String getCreateTableStatement() {
		String res = "CREATE TABLE ";
		ArrayList<String> indexList = new ArrayList<String>();
		ArrayList<String> UniqueList = new ArrayList<String>();
		
		res += this.tableName + " ";
		res += "(" ;
		for (int i = 0 ; i < schemas.size(); ++i) {
			res += schemas.get(i).columnName + " " + schemas.get(i).type;
			if (schemas.get(i).autoInc)
				res += " NOT NULL AUTO_INCREMENT PRIMARY KEY";
			else if(schemas.get(i).isPrimaryKey)
				res += " PRIMARY KEY";
			if (schemas.get(i).isIndex)
				indexList.add(schemas.get(i).columnName);
			if (schemas.get(i).isUnique)
				UniqueList.add(schemas.get(i).columnName);
			res += ",";
		}
		
		for (int i = 0; i < indexList.size(); ++i)
			res += "INDEX(" + indexList.get(i) + "),";
		for (int i = 0; i < UniqueList.size(); ++i)
			res += "UNIQUE KEY(" + UniqueList.get(i) + "),";
		
		if (res.endsWith(","))
			res = res.substring(0, res.length()-1);
		res += ")" ;
		
		return res;		
	}
}
