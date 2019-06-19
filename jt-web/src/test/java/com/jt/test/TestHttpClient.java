package com.jt.test;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClient {
	
	@Autowired
	private CloseableHttpClient client;
	
	@Test
	public void test01() throws ClientProtocolException, IOException {
//		CloseableHttpClient httpClient = HttpClients.createDefault();	//创建客户端
		String url = "https://www.baidu.com";//定义路径
		HttpGet httpGet = new HttpGet(url);//创建提交方式
		CloseableHttpResponse execute = client.execute(httpGet); //执行请求
		int status = execute.getStatusLine().getStatusCode();
//		Header[] headers = execute.getAllHeaders();
//		for (Header header : headers) {
//			System.out.println(header);
//		}
		if(status==200) {
			System.out.println("恭喜你请求成功");
			System.err.println(EntityUtils.toString(execute.getEntity()));
		}else {
			System.out.println("请求失败");
		}
	}
	
	@Autowired
	private HttpClientService httpClientService;
	//测试工具API
	@Test
	public void testuil() {
		String url = "https://www.baidu.com";
		String result = httpClientService.doGet(url);
		System.out.println(result);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
