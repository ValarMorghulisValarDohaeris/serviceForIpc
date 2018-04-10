package com.punuo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.punuo.mapper")
@SpringBootApplication
public class ServiceForIpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceForIpcApplication.class, args);
	}
}
