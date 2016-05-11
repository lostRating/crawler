package web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import common.Config;
import common.GsonUtils;

public class Client {
	private static String userAgent = "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2";
	private static HashSet<String> successResponse = new HashSet<String> (Arrays.asList("HTTP/1.1 200 OK"));
	
	static CloseableHttpClient client;
	
	static {
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		client = HttpClients.custom().setDefaultRequestConfig(config).build();
	}
	
	static CloseableHttpResponse doGet(String url, String cookie, String refer) throws IOException {
		HttpGet get = new HttpGet(url);
		if (cookie != null) {
			get.setHeader("Cookie", cookie);
		}
		if (refer != null) {
			get.setHeader("Referer", refer);
		}
		get.setHeader(HttpHeaders.USER_AGENT, userAgent);
		CloseableHttpResponse response = client.execute(get);
		return response;
	}
	
	static CloseableHttpResponse doPost(String url, String cookie, String refer) throws IOException {
		HttpPost post = new HttpPost(url);
		if (cookie != null) {
			post.setHeader("Cookie", cookie);
		}
		if (refer != null) {
			post.setHeader("Referer", refer);
		}
		CloseableHttpResponse response = client.execute(post);
		return response;
	}
	
	static public String getPage(String url) {
		for (int i = 0; i < Config.webClientRetryTimes; ++i) {
			try {
				CloseableHttpResponse response = doGet(url, null, null);
				String statusLine = response.getStatusLine().toString();
				/*HeaderIterator itr = response.headerIterator();
				while (itr.hasNext()) {
					System.out.println(itr.nextHeader());
				}*/
				String ret = EntityUtils.toString(response.getEntity());
				response.close();
				if (successResponse.contains(statusLine))
					return ret;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(Config.webClientFailWatiTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	static public void main(String args[]) {
		String page = getPage("https://en.wikipedia.org/wiki/Wiki");
		System.out.println(page);
	}
}
