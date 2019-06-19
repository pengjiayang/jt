package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jt.interceptor.UserInteceptor;
//访问.html 就进行拦截
@Configuration
public class MvcConfigurer implements WebMvcConfigurer{
	
	@Autowired
	private UserInteceptor userInteceptor;
	
	//开启匹配后缀型配置
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(true);
	}
	//新增拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userInteceptor).addPathPatterns("/cart/**","/order/**");//  /**表示拦截下面的所有信息
	}
	
}
