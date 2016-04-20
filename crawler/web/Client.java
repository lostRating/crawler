package web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Client {
	private HashSet<String> successResponse = new HashSet<String> (Arrays.asList("HTTP/1.1 200 OK"));
	
	public String getPage(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpUriRequest request = new HttpGet(url);
		for (int i = 0; i < 3; ++i) {
			try {
				CloseableHttpResponse response = httpClient.execute(request);
				String statusLine = response.getStatusLine().toString();
				String ret = EntityUtils.toString(response.getEntity(), "GBK");
				response.close();
				httpClient.close();
				if (successResponse.contains(statusLine))
					return ret;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	static public void main(String args[]) {
		Client client = new Client();
		System.out.println(client.getPage("http://www.baidu.com"));
	}
}
