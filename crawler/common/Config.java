package common;

public class Config {
	static public String host = "127.0.0.1";
	static public int clientPort = 8081;
	static public int serverPort = 8082;
	
	static public int eventPackageSize = 1000;
	static public int eventSenderSocketTimeout = 5000;
	static public int eventSenderRetryTimes = 3;
	static public int eventSenderFailWaitTime = 5000;
	
	static public int webClientRetryTimes = 3;
	static public int webClientFailWatiTime = 2000; 
	
	static public int serverWorkerSize = 20;
	static public int clientWorkerSize = 10;
	static public int workRetryTimes = 3;
	
	static public String databaseIp = "mydbinstance.coyk1getlquj.ap-northeast-1.rds.amazonaws.com";
	static public String databaseUserName = "awsuser";
	static public String databasePassword = "123456789";
	
	static public void load(String file) {
		
	}
}
