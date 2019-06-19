package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.jt.mapper") //为mapper接口创建代理对象
public class SpringBootRun {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRun.class, args);
	}
}