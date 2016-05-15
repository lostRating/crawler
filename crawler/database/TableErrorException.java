package database;

public class TableErrorException extends Exception {

	public TableErrorException() {
		super();
	}
	
	public TableErrorException(String msg) {
		super(msg);
	}
}
