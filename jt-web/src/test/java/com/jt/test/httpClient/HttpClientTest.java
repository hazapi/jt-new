package com.jt.test.httpClient;

import java.io.IOException;
import org.junit.Test;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	
	@Test
	public void testGet() throws ClientProtocolException, IOException{
		//定义httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "https://www.dangdang.com";
		HttpGet httpGet = new HttpGet(url);
		//HttpPost httpPost = new HttpPost(url);
		
		//获取HTTP响应
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		//CloseableHttpResponse httpResponse1 = httpClient.execute(httpPost);
		
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			System.out.println("获取请成功");
			//获取HTML
			String  html = EntityUtils.toString(httpResponse.getEntity());
			//String  html1 = EntityUtils.toString(httpResponse1.getEntity());
			System.out.println(html);
			//System.out.println(html1);
		}
	}
}
