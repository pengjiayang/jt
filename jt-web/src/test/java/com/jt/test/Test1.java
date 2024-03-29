package com.jt.test;

import java.io.IOException;

import org.apache.http.RequestLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class Test1 {
	@Test
	public void test01() throws ClientProtocolException, IOException{		
		CloseableHttpClient httpClient = HttpClients.createDefault();   //创建HTTPClient的实例
		String url = "https://item.jd.com/5236335.html?dist=jd";		//定义访问IP
		HttpPost httpPost = new HttpPost(url); 	//设定请求  
		CloseableHttpResponse response =  httpClient.execute(httpPost);	 //获取response对象
		String html = EntityUtils.toString(response.getEntity());  //获取页面信息
		System.out.println(html);
	}
	
	@Test
	public void testGet() throws ClientProtocolException, IOException{
		//定义httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String uri = "https://item.jd.com/5236335.html?dist=jd";
		HttpGet httpGet = new HttpGet(uri);

		//获取请求的全部参数   GET https://item.jd.com/5236335.html?dist=jd HTTP/1.1
		RequestLine requestLine = httpGet.getRequestLine();
		
		//获取请求方式     GET/POST
		requestLine.getMethod();
		
		//获取浏览器  http协议     HTTP/1.1
		requestLine.getProtocolVersion();
		
		//请求 uri：https://item.jd.com/5236335.html?dist=jd
		requestLine.getUri();

		
		//获取HTTP响应
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			System.out.println("获取请成功");
			//获取HTML
			String  html = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(html);
		}
	}
}
