package event;

public enum EventType {
	ClientOnline, //client -> server
	ClientOffline, //client -> server
	Initialize, //server -> client
	Restart, //server -> client
	Heartbeat, //server -> client -> server
	Job, //server -> client -> server
	NewTask; //user -> server
}
