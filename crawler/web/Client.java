package web;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class Client {
	
	static public void main(String args[]) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet("http://www.baidu.com");
		try {
			HttpResponse response = httpClient.execute(request);
			System.out.println("123");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
