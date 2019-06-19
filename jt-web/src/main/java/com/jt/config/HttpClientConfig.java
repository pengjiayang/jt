package com.jt.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource(value="classpath:/properties/httpClient.properties")
public class HttpClientConfig {
	@Value("${http.maxTotal}")
	private Integer maxTotal;						//最大连接数1000

	@Value("${http.defaultMaxPerRoute}")
	private Integer defaultMaxPerRoute;				//最大并发链接数20

	@Value("${http.connectTimeout}")
	private Integer connectTimeout;					//创建链接的最大时间5000

	@Value("${http.connectionRequestTimeout}") 
	private Integer connectionRequestTimeout;		//链接获取超时时间500

	@Value("${http.socketTimeout}")
	private Integer socketTimeout;			  		//数据传输最长时间5000

	@Value("${http.staleConnectionCheckEnabled}")
	private boolean staleConnectionCheckEnabled; 	//提交时检查链接是否可用true

	//定义httpClient链接池
	@Bean(name="httpClientConnectionManager")
	public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
		manager.setMaxTotal(maxTotal);  //设定最大链接数
		manager.setDefaultMaxPerRoute(defaultMaxPerRoute);  //设定并发链接数
		return manager;
	}
	//定义HttpClient
	/**
	 * 实例化连接池，设置连接池管理器。
	 * 这里需要以参数形式注入上面实例化的连接池管理器
      @Qualifier 指定bean标签进行注入//这个对象一定是上满bean注解里面的对象
	 */
	@Bean(name = "httpClientBuilder")
	public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager")PoolingHttpClientConnectionManager httpClientConnectionManager){

		//HttpClientBuilder中的构造方法被protected修饰，所以这里不能直接使用new来实例化一个HttpClientBuilder,可以使用HttpClientBuilder提供的静态方法create()来获取HttpClientBuilder对象
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();//API规定只能通过create()建造对象
		httpClientBuilder.setConnectionManager(httpClientConnectionManager);
		return httpClientBuilder;
	}
	/**
	 * 	注入连接池，用于获取httpClient
	 * @param httpClientBuilder
	 * @return
	 */
	@Bean
	@Scope("prototype")//设置多例模式,单个以上会创建新的连接
	public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder){
		return httpClientBuilder.build();//建造者模式通过build获取对象
	}
	/**
	 * Builder是RequestConfig的一个内部类
	  * 通过RequestConfig的custom方法来获取到一个Builder对象
	  * 设置builder的连接信息
	 * @return
	 */
	@Bean(name = "builder")
	public RequestConfig.Builder getBuilder(){
		RequestConfig.Builder builder = RequestConfig.custom();
		return builder.setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setSocketTimeout(socketTimeout)
				.setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
	}

	/**
	 * 使用builder构建一个RequestConfig对象
	 * @param builder
	 * @return
	 */
	@Bean
	public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder){
		return builder.build();
	}
}
